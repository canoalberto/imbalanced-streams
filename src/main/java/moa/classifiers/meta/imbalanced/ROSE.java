package moa.classifiers.meta.imbalanced;

import java.util.ArrayList;

import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Instances;
import com.yahoo.labs.samoa.instances.InstancesHeader;

import moa.AbstractMOAObject;
import moa.capabilities.CapabilitiesHandler;
import moa.capabilities.Capability;
import moa.capabilities.ImmutableCapabilities;
import moa.classifiers.AbstractClassifier;
import moa.classifiers.MultiClassClassifier;
import moa.classifiers.core.driftdetection.ChangeDetector;
import moa.classifiers.trees.RandomSubspaceHT;
import moa.core.DoubleVector;
import moa.core.InstanceExample;
import moa.core.Measurement;
import moa.core.MiscUtils;
import moa.core.Utils;
import moa.evaluation.WindowKappaClassificationPerformanceEvaluator;
import moa.options.ClassOption;

/**
 * ROSE: Robust Online Self-Adjusting Ensemble for Continual Learning on Imbalanced Drifting Data Streams
 * 
 * @author Alberto Cano
 */
public class ROSE extends AbstractClassifier implements MultiClassClassifier, CapabilitiesHandler {

	private static final long serialVersionUID = 1L;

	public ClassOption treeLearnerOption = new ClassOption("treeLearner", 'l', "RandomSubspaceHT", RandomSubspaceHT.class,  "RandomSubspaceHT");

	public IntOption ensembleSizeOption = new IntOption("ensembleSize", 's', "The number of trees.", 10, 1, Integer.MAX_VALUE);

	public FloatOption lambdaOption = new FloatOption("lambda", 'a', "The lambda parameter for bagging.", 6.0, 1.0, Float.MAX_VALUE);

	public ClassOption driftDetectionMethodOption = new ClassOption("driftDetectionMethod", 'x', "Change detector for drifts and its parameters", ChangeDetector.class, "ADWINChangeDetector -a 1.0E-5");

	public ClassOption warningDetectionMethodOption = new ClassOption("warningDetectionMethod", 'p', "Change detector for warnings (start training bkg learner)", ChangeDetector.class, "ADWINChangeDetector -a 1.0E-4");

	public FloatOption percentageFeaturesMean = new FloatOption("percentageFeaturesMean", 'm', "Mean for percentage of featues selected", 0.7, 0, 1);

	public FloatOption theta = new FloatOption("theta", 't', "The time decay factor for class size.", 0.99, 0, 1);
	
	public IntOption windowSizeOption = new IntOption("window", 'w', "The number of instances in the sliding window.", 500, 1, Integer.MAX_VALUE);
	
	protected ROSEBaseLearner[] ensemble;
	protected ROSEBaseLearner[] ensembleBackground;
	protected InstancesTimestap[] instancesClass;

	protected double classSize[];
	protected long instancesSeen;
	protected long firstWarningOn;
	protected boolean warningDetected;
	protected WindowKappaClassificationPerformanceEvaluator evaluator;

	@Override
	public void resetLearningImpl() {
		this.warningDetected = true;
		this.firstWarningOn = 0;
		this.instancesClass = null;
		this.ensemble = null;
		this.ensembleBackground = null;
		this.classSize = null;
		this.instancesSeen = 0;
		this.evaluator = new WindowKappaClassificationPerformanceEvaluator();
	}

	@Override
	public void trainOnInstanceImpl(Instance instance) {
		this.instancesSeen++;
		
		this.instancesClass[(int) instance.classValue()].add(instance, instancesSeen);

		if(this.ensemble == null) {
			initEnsemble(instance);
		}

		for (int i=0; i < classSize.length; i++) {
			classSize[i] = theta.getValue() * classSize[i] + (1d - theta.getValue()) * ((int) instance.classValue() == i ? 1d:0d); 
		}

		double lambda = lambdaOption.getValue() + lambdaOption.getValue() * Math.log(classSize[Utils.maxIndex(classSize)] / classSize[(int) instance.classValue()]);

		for (int i = 0; i < this.ensemble.length; i++) {
			DoubleVector vote = new DoubleVector(this.ensemble[i].getVotesForInstance(instance));
			InstanceExample example = new InstanceExample(instance);
			this.ensemble[i].evaluator.addResult(example, vote.getArrayRef());

			int k = MiscUtils.poisson(lambda, this.classifierRandom);
			if (k > 0) {
				this.ensemble[i].trainOnInstance(instance, k, this.instancesSeen);
			}
		}

		if(!this.warningDetected) {
			for (int i = 0; i < this.ensemble.length; i++) {
				if(this.ensemble[i].warningDetected) {
					this.warningDetected = true;
					this.firstWarningOn = instancesSeen;
					break;
				}
			}

			if(this.warningDetected) {
				int numberAttributes = instance.numAttributes() - 1;

				RandomSubspaceHT treeLearner = (RandomSubspaceHT) getPreparedClassOption(this.treeLearnerOption);
				treeLearner.resetLearning();

				WindowKappaClassificationPerformanceEvaluator classificationEvaluator = new WindowKappaClassificationPerformanceEvaluator();

				for(int i = 0; i < this.ensembleSizeOption.getValue(); i++) {
					int subspaceSize = 1 + (int) Math.floor(numberAttributes/2) + this.classifierRandom.nextInt((int)Math.ceil(numberAttributes/2));

					if (subspaceSize > numberAttributes) {
						subspaceSize = numberAttributes;
					} else if (subspaceSize <= 0) {
						subspaceSize = 1;
					}

					treeLearner.subspaceSizeOption.setValue(subspaceSize);
					treeLearner.setRandomSeed(this.classifierRandom.nextInt(Integer.MAX_VALUE));

					this.ensembleBackground[i] = new ROSEBaseLearner(
							(RandomSubspaceHT)  treeLearner.copy(), 
							(WindowKappaClassificationPerformanceEvaluator) classificationEvaluator.copy(), 
							this.instancesSeen, 
							driftDetectionMethodOption,
							warningDetectionMethodOption,
							false);
				}
				
				int[] indexClass = new int[instance.numClasses()];
				Long[] oldestTimestamps = new Long[instance.numClasses()];
				
				do {
					Long oldestTimestamp = Long.MAX_VALUE;
					int nextClass = -1;
					
					for(int c = 0; c < instance.numClasses(); c++) {
						oldestTimestamps[c] = this.instancesClass[c].getTimestamp(indexClass[c]);
						
						if(oldestTimestamps[c] != null && oldestTimestamps[c] < oldestTimestamp) {
							oldestTimestamp = oldestTimestamps[c];
							nextClass = c;
						}
					}
					
					if(nextClass == -1) {
						break;
					}
					
					Instance windowInstance = this.instancesClass[nextClass].getInstance(indexClass[nextClass]);
					
					for (int i = 0; i < this.ensembleBackground.length; i++) {
						int k = MiscUtils.poisson(lambdaOption.getValue(), this.classifierRandom);
						if (k > 0) {
							this.ensembleBackground[i].trainOnInstance(windowInstance, k, this.instancesSeen);
						}
					}
					
					indexClass[nextClass]++;
					
				} while(true);
			}
		}

		if(this.warningDetected) {
			for (int i = 0; i < this.ensembleBackground.length; i++) {
				DoubleVector vote = new DoubleVector(this.ensembleBackground[i].getVotesForInstance(instance));
				InstanceExample example = new InstanceExample(instance);
				this.ensembleBackground[i].evaluator.addResult(example, vote.getArrayRef());

				int k = MiscUtils.poisson(lambda, this.classifierRandom);
				if (k > 0) {
					this.ensembleBackground[i].trainOnInstance(instance, k, this.instancesSeen);
				}
			}

			if(this.instancesSeen - this.firstWarningOn == this.evaluator.widthOption.getValue()) {
				// Compare the ensemble and the background ensemble. Select the best components

				ArrayList<ROSEBaseLearner> classifiers = new ArrayList<ROSEBaseLearner>();
				ArrayList<ROSEBaseLearner> selection = new ArrayList<ROSEBaseLearner>();
				ArrayList<Double> kappas = new ArrayList<Double>();
				ArrayList<Double> accuracies = new ArrayList<Double>();

				for (int i = 0; i < this.ensemble.length; i++) {
					classifiers.add(this.ensemble[i]);
					kappas.add(this.ensemble[i].evaluator.getKappa());
					accuracies.add(this.ensemble[i].evaluator.getAccuracy());
				}

				for (int i = 0; i < this.ensembleBackground.length; i++) {
					classifiers.add(this.ensembleBackground[i]);
					kappas.add(this.ensembleBackground[i].evaluator.getKappa());
					accuracies.add(this.ensembleBackground[i].evaluator.getAccuracy());
				}

				for (int i = 0; i < this.ensemble.length; i++) {

					double maxKappaAccuracy = -1;
					int maxKappaAccuracyClassifier = -1;

					for (int j = 0; j < this.ensemble.length + this.ensembleBackground.length - i; j++) {
						if(kappas.get(j) * accuracies.get(j) >= maxKappaAccuracy) {
							maxKappaAccuracy = kappas.get(j) * accuracies.get(j);
							maxKappaAccuracyClassifier = j;
						}
					}

					selection.add(classifiers.get(maxKappaAccuracyClassifier));

					classifiers.remove(maxKappaAccuracyClassifier);
					kappas.remove(maxKappaAccuracyClassifier);
					accuracies.remove(maxKappaAccuracyClassifier);
				}

				for (int i = 0; i < this.ensemble.length; i++) {
					this.ensemble[i] = selection.get(i);
				}

				for (int i = 0; i < this.ensembleBackground.length; i++) {
					this.ensembleBackground[i] = null;
				}

				this.warningDetected = false;
			}
		}
	}

	@Override
	public double[] getVotesForInstance(Instance instance) {
		if(this.ensemble == null) {
			initEnsemble(instance);
		}

		DoubleVector combinedVote = new DoubleVector();
		DoubleVector combinedVoteUnweighted = new DoubleVector();

		for(int i = 0; i < this.ensemble.length; i++) {
			DoubleVector vote = new DoubleVector(this.ensemble[i].getVotesForInstance(instance));
			if (vote.sumOfValues() > 0.0) {
				vote.normalize();

				combinedVoteUnweighted.addValues(vote);

				double kappa = this.ensemble[i].evaluator.getKappa();
				double accuracy = this.ensemble[i].evaluator.getAccuracy();

				if(kappa > 0.0) {
					vote.scaleValues(kappa * accuracy);
					combinedVote.addValues(vote);
				}
			}
		}

		if(combinedVote.sumOfValues() == 0) {
			return combinedVoteUnweighted.getArrayRef();
		} else {
			return combinedVote.getArrayRef();
		}
	}

	@Override
	public boolean isRandomizable() {
		return true;
	}

	@Override
	public void getModelDescription(StringBuilder arg0, int arg1) {
	}

	@Override
	protected Measurement[] getModelMeasurementsImpl() {
		return null;
	}

	protected void initEnsemble(Instance instance) {
		
		this.instancesClass = new InstancesTimestap[instance.numClasses()];
		
		for(int i = 0; i < instance.numClasses(); i++) {
			this.instancesClass[i] = new InstancesTimestap(this.getModelContext());
		}

		classSize = new double[instance.numClasses()];

		for (int i=0; i < classSize.length; i++) {
			classSize[i] = 1d / classSize.length;
		}

		int ensembleSize = this.ensembleSizeOption.getValue();
		int numberAttributes = instance.numAttributes() - 1;

		this.ensemble = new ROSEBaseLearner[ensembleSize];
		this.ensembleBackground = new ROSEBaseLearner[ensembleSize];

		WindowKappaClassificationPerformanceEvaluator classificationEvaluator = new WindowKappaClassificationPerformanceEvaluator();

		RandomSubspaceHT treeLearner = (RandomSubspaceHT) getPreparedClassOption(this.treeLearnerOption);
		treeLearner.resetLearning();

		// Primary ensemble
		for(int i = 0; i < ensembleSize; i++) {
			int subspaceSize = 1 + (int) Math.floor(numberAttributes/2) + this.classifierRandom.nextInt((int)Math.ceil(numberAttributes/2));

			if (subspaceSize > numberAttributes) {
				subspaceSize = numberAttributes;
			} else if (subspaceSize <= 0) {
				subspaceSize = 1;
			}

			treeLearner.subspaceSizeOption.setValue(subspaceSize);
			treeLearner.setRandomSeed(this.classifierRandom.nextInt(Integer.MAX_VALUE));

			this.ensemble[i] = new ROSEBaseLearner(
					(RandomSubspaceHT)  treeLearner.copy(), 
					(WindowKappaClassificationPerformanceEvaluator) classificationEvaluator.copy(), 
					this.instancesSeen, 
					driftDetectionMethodOption,
					warningDetectionMethodOption,
					false);
		}

		// Background ensemble
		for(int i = 0; i < this.ensembleSizeOption.getValue(); i++) {
			int subspaceSize = 1 + (int) Math.floor(numberAttributes/2) + this.classifierRandom.nextInt((int)Math.ceil(numberAttributes/2));

			if (subspaceSize > numberAttributes) {
				subspaceSize = numberAttributes;
			} else if (subspaceSize <= 0) {
				subspaceSize = 1;
			}

			treeLearner.subspaceSizeOption.setValue(subspaceSize);
			treeLearner.setRandomSeed(this.classifierRandom.nextInt(Integer.MAX_VALUE));

			this.ensembleBackground[i] = new ROSEBaseLearner(
					(RandomSubspaceHT)  treeLearner.copy(), 
					(WindowKappaClassificationPerformanceEvaluator) classificationEvaluator.copy(), 
					this.instancesSeen, 
					driftDetectionMethodOption,
					warningDetectionMethodOption,
					false);
		}
	}

	@Override
	public ImmutableCapabilities defineImmutableCapabilities() {
		if (this.getClass() == ROSE.class)
			return new ImmutableCapabilities(Capability.VIEW_STANDARD, Capability.VIEW_LITE);
		else
			return new ImmutableCapabilities(Capability.VIEW_STANDARD);
	}

	@Override
	public String getPurposeString() {
		return "ROSE: Robust Online Self-Adjusting Ensemble for Continual Learning on Imbalanced Drifting Data Streams";
	}

	/**
	 * Inner class that represents a single tree member of the forest. 
	 * It contains some analysis information, such as the numberOfDriftsDetected, 
	 */
	protected final class ROSEBaseLearner extends AbstractMOAObject {
		private static final long serialVersionUID = -3758478930527651262L;
		public long createdOn;
		public boolean warningDetected;
		public long lastDriftOn;
		public long lastWarningOn;
		public RandomSubspaceHT classifier;
		public boolean isBackgroundLearner;

		// The drift and warning object parameters. 
		protected ClassOption driftOption;
		protected ClassOption warningOption;

		// Drift and warning detection
		protected ChangeDetector driftDetectionMethod;
		protected ChangeDetector warningDetectionMethod;

		// Bkg learner
		protected ROSEBaseLearner bkgLearner;
		// Statistics
		public WindowKappaClassificationPerformanceEvaluator evaluator;
		protected int numberOfDriftsDetected;
		protected int numberOfWarningsDetected;

		public ROSEBaseLearner (RandomSubspaceHT instantiatedClassifier, WindowKappaClassificationPerformanceEvaluator evaluatorInstantiated, long instancesSeen, ClassOption driftOption, ClassOption warningOption, boolean isBackgroundLearner) {
			this.createdOn = instancesSeen;
			this.lastDriftOn = 0;
			this.lastWarningOn = 0;
			this.warningDetected = false;

			this.classifier = instantiatedClassifier;
			this.evaluator = evaluatorInstantiated;

			this.numberOfDriftsDetected = 0;
			this.numberOfWarningsDetected = 0;
			this.isBackgroundLearner = isBackgroundLearner;

			this.driftOption = driftOption;
			this.driftDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.driftOption)).copy();

			this.warningOption = warningOption;
			this.warningDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.warningOption)).copy();
		}

		public void reset() {
			if(this.bkgLearner != null) {
				this.classifier = this.bkgLearner.classifier;

				this.driftDetectionMethod = this.bkgLearner.driftDetectionMethod;
				this.warningDetectionMethod = this.bkgLearner.warningDetectionMethod;

				this.evaluator = this.bkgLearner.evaluator;
				this.createdOn = this.bkgLearner.createdOn;
				this.bkgLearner = null;
			}
			else {
				this.classifier.resetLearning();
				this.createdOn = instancesSeen;
				this.driftDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.driftOption)).copy();
			}

			this.lastWarningOn = 0;
			this.lastDriftOn = 0;
			this.warningDetected = false;
			this.evaluator.reset();
		}

		public void trainOnInstance(Instance instance, double weight, long instancesSeen) {
			Instance weightedInstance = (Instance) instance.copy();
			weightedInstance.setWeight(instance.weight() * weight);
			this.classifier.trainOnInstance(weightedInstance);

			if(this.bkgLearner != null)
				this.bkgLearner.classifier.trainOnInstance(weightedInstance);

			// Should it use a drift detector? Also, is it a backgroundLearner? If so, then do not "incept" another one. 
			if(!this.isBackgroundLearner) {
				boolean correctlyClassifies = this.classifier.correctlyClassifies(instance);
				// Check for warning only if useBkgLearner is active
				// Update the warning detection method
				this.warningDetectionMethod.input(correctlyClassifies ? 0 : 1);
				// Check if there was a change
				if(this.warningDetectionMethod.getChange()) {
					this.warningDetected = true;
					this.lastWarningOn = instancesSeen;
					this.numberOfWarningsDetected++;
					// Create a new bkgTree classifier
					RandomSubspaceHT bkgClassifier = (RandomSubspaceHT) this.classifier.copy();
					bkgClassifier.resetLearning();
					bkgClassifier.setup(this.classifier.listAttributes, this.classifier.instanceHeader);

					// Resets the evaluator
					WindowKappaClassificationPerformanceEvaluator bkgEvaluator = (WindowKappaClassificationPerformanceEvaluator) this.evaluator.copy();
					bkgEvaluator.reset();

					// Create a new bkgLearner object
					this.bkgLearner = new ROSEBaseLearner(bkgClassifier, bkgEvaluator, instancesSeen, this.driftOption, this.warningOption, true);

					// Update the warning detection object for the current object 
					// (this effectively resets changes made to the object while it was still a bkg learner). 
					this.warningDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.warningOption)).copy();
				} else {
					this.warningDetected = false;
				}

				/*********** drift detection ***********/

				// Update the DRIFT detection method
				this.driftDetectionMethod.input(correctlyClassifies ? 0 : 1);
				// Check if there was a change
				if(this.driftDetectionMethod.getChange()) {
					this.lastDriftOn = instancesSeen;
					this.numberOfDriftsDetected++;
					this.reset();
				}
			}
		}

		public double[] getVotesForInstance(Instance instance) {
			DoubleVector vote = new DoubleVector(this.classifier.getVotesForInstance(instance));
			return vote.getArrayRef();
		}

		@Override
		public void getDescription(StringBuilder sb, int indent) {
		}
	}
	
	private class InstancesTimestap  {
		private Instances instances;
		private ArrayList<Long> timestamps;
		
		public InstancesTimestap(InstancesHeader header) {
			instances = new Instances(header);
			timestamps = new ArrayList<Long>();
		}
		
		public void add(Instance instance, long timestamp) {
			instances.add(instance);
			timestamps.add(timestamp);
			
			if (instances.size() > windowSizeOption.getValue() / instance.numClasses()) {
				instances.delete(0);
				timestamps.remove(0);
			}
		}
		
		public Instance getInstance(int index) {
			if(index >= instances.size()) 
				return null;
			else
				return instances.get(index);
		}
		
		public Long getTimestamp(int index) {
			if(index >= timestamps.size())
				return null;
			else
				return timestamps.get(index);
		}
	}
}