package moa.classifiers.meta;


import com.github.javacliparser.IntOption;
import com.github.javacliparser.MultiChoiceOption;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Prediction;

import moa.AbstractMOAObject;
import moa.capabilities.CapabilitiesHandler;
import moa.classifiers.Classifier;
import moa.classifiers.MultiClassClassifier;
import moa.classifiers.core.driftdetection.ChangeDetector;
import moa.classifiers.trees.ARFHoeffdingTree;
import moa.core.*;
import moa.evaluation.BasicClassificationPerformanceEvaluator;
import moa.evaluation.LearningPerformanceEvaluator;
import moa.options.AbstractOptionHandler;
import moa.options.ClassOption;
import moa.tasks.TaskMonitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Cost-sensitive Adaptive Random Forest (CSARF)
 * This method is a variant of Adaptive Random Forest which uses misclassification costs to handle imbalanced data stream mining.
 * @author Lucas Loezer (loezerl at ppgia dot pucpr dot br | loezer dot lucas at gmail dot com)
 */
public class CSARF extends AdaptiveRandomForest implements MultiClassClassifier, CapabilitiesHandler {
	private ExecutorService executor;

	protected CostMatrix costMatrix;
	protected ImbalancedWindow imbalancedWindow;
	protected OzaCosting ozaCosting;

	protected CSARFBaseLearner[] ensemble;

	protected static final int THRESHOLD_TRAINING = 0;
	protected static final int THRESHOLD_LOCAL = 1;
	protected static final int THRESHOLD_GLOBAL = 2;

	protected static final int PICEK_COST = 0;
	protected static final int OZA_COST = 1;
	protected int costMode = 0;

	//OPTIONS
	public IntOption imbalancedWindowSizeOption = new IntOption("imbalanceWindowSize", 'i',
			"Imbalanced Window Size.", 10000, 10, Integer.MAX_VALUE);

	public MultiChoiceOption thresholdModeOption = new MultiChoiceOption("thresholdMode", 't',
			"Defines how the cost threshold would be applied in the ensemble: training, local threshold and global threshold.",
			new String[]{"Training: the cost threshold is used to influences the base classifier decision during training." +
					"This is going to modify the base classifier's evaluation.",
					"Local threshold: the cost threshold is used to change the classifier's output before the combined vote.",
					"Global threshold: the cost threshold is used to change the combined ensemble final vote.",
	"Disable threshold"},
			new String[]{"t", "l", "g", "d"}, 2);

	public MultiChoiceOption costModeOption = new MultiChoiceOption("costMode", 'c',
			"Defines the cost strategy.",
			new String[]{"Picek", "OzaCosting"},
			new String[]{"p", "o"}, 0);

	public CSARF(){
		super();
	}

	public void resetLearningImpl() {
		// Reset attributes
		this.ensemble = null;
		this.subspaceSize = 0;
		this.instancesSeen = 0;
		this.evaluator = new BasicClassificationPerformanceEvaluator();
		this.costMatrix = null;
		this.imbalancedWindow = null;
		//setModelContext(null);

		// Multi-threading
		int numberOfJobs;
		if(this.numberOfJobsOption.getValue() == -1)
			numberOfJobs = Runtime.getRuntime().availableProcessors();
		else
			numberOfJobs = this.numberOfJobsOption.getValue();
		// SINGLE_THREAD and requesting for only 1 thread are equivalent.
		// this.executor will be null and not used...
		if(numberOfJobs != AdaptiveRandomForest.SINGLE_THREAD && numberOfJobs != 1)
			this.executor = Executors.newFixedThreadPool(numberOfJobs);
	}

	public double getExpectedCost(int classIndex){ return this.costMatrix.getCost(classIndex);}
	
    @Override
    public Classifier[] getSublearners() {
        /* Extracts the reference to the ARFHoeffdingTree object from within the ensemble of ARFBaseLearner's */
        Classifier[] forest = new Classifier[this.ensemble.length];
        for(int i = 0 ; i < forest.length ; ++i)
            forest[i] = this.ensemble[i].classifier;
        return forest;

    }

	@Override
	protected void initEnsemble(Instance instance) {
		// Init the ensemble.
		int ensembleSize = this.ensembleSizeOption.getValue();
		this.ensemble = new CSARFBaseLearner[ensembleSize];

		//:::::::::::::::::::::: SETUPS Threshold mode
		switch(this.costModeOption.getChosenIndex()) {
		case CSARF.PICEK_COST:
			this.costMode = PICEK_COST;
			break;
		case CSARF.OZA_COST:
			this.costMode = OZA_COST;
			this.ozaCosting = new OzaCosting(instance.numClasses());
			break;
		}

		//:: Setups CostMatrix and Imbalanced Window
		this.costMatrix = new CostMatrix(instance.numClasses());
		this.imbalancedWindow = new ImbalancedWindow(this.imbalancedWindowSizeOption.getValue());
		///////////////////////////////////////

		// Evaluator which holds MCC
		CSARFImbalancedClassificationPerformanceEvaluator classificationEvaluator = new CSARFImbalancedClassificationPerformanceEvaluator();
		classificationEvaluator.reset();

		this.subspaceSize = this.mFeaturesPerTreeSizeOption.getValue();

		// The size of m depends on:
		// 1) mFeaturesPerTreeSizeOption
		// 2) mFeaturesModeOption
		int n = instance.numAttributes()-1; // Ignore class label ( -1 )

		switch(this.mFeaturesModeOption.getChosenIndex()) {
		case AdaptiveRandomForest.FEATURES_SQRT:
			this.subspaceSize = (int) Math.round(Math.sqrt(n)) + 1;
			break;
		case AdaptiveRandomForest.FEATURES_SQRT_INV:
			this.subspaceSize = n - (int) Math.round(Math.sqrt(n) + 1);
			break;
		case AdaptiveRandomForest.FEATURES_PERCENT:
			// If subspaceSize is negative, then first find out the actual percent, i.e., 100% - m.
			double percent = this.subspaceSize < 0 ? (100 + this.subspaceSize)/100.0 : this.subspaceSize / 100.0;
			this.subspaceSize = (int) Math.round(n * percent);
			break;
		}
		// Notice that if the selected mFeaturesModeOption was
		//  AdaptiveRandomForest.FEATURES_M then nothing is performed in the
		//  previous switch-case, still it is necessary to check (and adjusted)
		//  for when a negative value was used.

		// m is negative, use size(features) + -m
		if(this.subspaceSize < 0)
			this.subspaceSize = n + this.subspaceSize;
		// Other sanity checks to avoid runtime errors.
		//  m <= 0 (m can be negative if this.subspace was negative and
		//  abs(m) > n), then use m = 1
		if(this.subspaceSize <= 0)
			this.subspaceSize = 1;
		// m > n, then it should use n
		if(this.subspaceSize > n)
			this.subspaceSize = n;

		ARFHoeffdingTree treeLearner = (ARFHoeffdingTree) getPreparedClassOption(this.treeLearnerOption);
		treeLearner.resetLearning();

		for(int i = 0 ; i < ensembleSize ; ++i) {
			treeLearner.subspaceSizeOption.setValue(this.subspaceSize);
			this.ensemble[i] = new CSARFBaseLearner(
					i,
					(ARFHoeffdingTree) treeLearner.copy(),
					(CSARFImbalancedClassificationPerformanceEvaluator) classificationEvaluator.copy(),
					this.instancesSeen,
					! this.disableBackgroundLearnerOption.isSet(),
					! this.disableDriftDetectionOption.isSet(),
					driftDetectionMethodOption,
					warningDetectionMethodOption,
					false);
		}
	}

	@Override
	public double[] getVotesForInstance(Instance instance) {
		Instance testInstance = instance.copy();
		if(this.ensemble == null)
			initEnsemble(testInstance);
		DoubleVector combinedVote = new DoubleVector();

		for(int i = 0 ; i < this.ensemble.length ; ++i) {
			DoubleVector vote = new DoubleVector(this.ensemble[i].getVotesForInstance(testInstance));
			if (vote.sumOfValues() > 0.0) {
				if(this.thresholdModeOption.getChosenIndex() == CSARF.THRESHOLD_LOCAL)
					vote = this.costMatrix.applyCostToOutput(vote);
				vote.normalize();

				double metric = 0.0;
				try{
					metric = this.ensemble[i].evaluator.getF1Statistic();
				}catch (NullPointerException ignored){}

				if(! this.disableWeightedVote.isSet() && metric > 0.0) {
					for(int v = 0 ; v < vote.numValues() ; ++v) {
						vote.setValue(v, vote.getValue(v) * metric);
					}
				}
				combinedVote.addValues(vote);
			}
		}
		if(combinedVote.sumOfValues() > 0.0 && this.thresholdModeOption.getChosenIndex() == CSARF.THRESHOLD_GLOBAL) {
			combinedVote = this.costMatrix.applyCostToOutput(combinedVote);
		}
		combinedVote.normalize();
		return combinedVote.getArrayRef();
	}

	@Override
	public void trainOnInstanceImpl(Instance instance) {
		++this.instancesSeen;
		if(this.ensemble == null)
			initEnsemble(instance);

		//Get ensemble's votes
		DoubleVector ensembleVote = new DoubleVector(this.getVotesForInstance(instance));

		// Populates Imbalance Window
		this.imbalancedWindow.addInstance(instance);
		if(costMode == CSARF.OZA_COST && ensembleVote.sumOfValues() > 0){
			//Updates OzaCosting counters
			ozaCosting.updateOzaCosting(new InstanceExample(instance), ensembleVote);
		}
		//Update CostMatrix
		updateCost();

		//Grants that the instances with positiveClass is going to be used.
		boolean positiveClass = (int) instance.classValue() != this.imbalancedWindow.getMajoritoryClassIndex();

		Collection<TrainingRunnable> trainers = new ArrayList<TrainingRunnable>();
		for (int i = 0 ; i < this.ensemble.length ; i++) {
			DoubleVector vote = new DoubleVector(this.ensemble[i].getVotesForInstance(instance));
			//before threshold
			if(this.thresholdModeOption.getChosenIndex() == CSARF.THRESHOLD_TRAINING) {
				vote = this.costMatrix.applyCostToOutput(vote);
			}
			vote.normalize();
			InstanceExample example = new InstanceExample(instance);
			this.ensemble[i].evaluator.addResult(example, vote.getArrayRef());

			int k = MiscUtils.poisson(this.lambdaOption.getValue(), this.classifierRandom);
			//::Checks if actual instance is the positive class
			if ((k > 0 || positiveClass)) {
				if(k <= 0) k = 1;
				if(this.executor != null) {
					TrainingRunnable trainer = new TrainingRunnable(this.ensemble[i],
							instance, k, this.instancesSeen);
					trainers.add(trainer);
				}
				else { // SINGLE_THREAD is in-place...
					this.ensemble[i].trainOnInstance(instance, k, this.instancesSeen);
				}
			}
		}
		if(this.executor != null) {
			try {
				this.executor.invokeAll(trainers);
			} catch (InterruptedException ex) {
				throw new RuntimeException("Could not call invokeAll() on training threads.");
			}
		}
	}

	public double[] getClassDistribution(){
		return this.imbalancedWindow.getDistribution();
	}

	protected final class CSARFBaseLearner extends AbstractMOAObject {
		public int indexOriginal;
		public long createdOn;
		public long lastDriftOn;
		public long lastWarningOn;
		public ARFHoeffdingTree classifier;
		public boolean isBackgroundLearner;

		// The drift and warning object parameters.
		protected ClassOption driftOption;
		protected ClassOption warningOption;

		// Drift and warning detection
		protected ChangeDetector driftDetectionMethod;
		protected ChangeDetector warningDetectionMethod;

		public boolean useBkgLearner;
		public boolean useDriftDetector;

		// Bkg learner
		protected CSARFBaseLearner bkgLearner;
		// Statistics
		public CSARFImbalancedClassificationPerformanceEvaluator evaluator;
		protected int numberOfDriftsDetected;
		protected int numberOfWarningsDetected;

		private void init(int indexOriginal, ARFHoeffdingTree instantiatedClassifier, CSARFImbalancedClassificationPerformanceEvaluator evaluatorInstantiated,
				long instancesSeen, boolean useBkgLearner, boolean useDriftDetector, ClassOption driftOption, ClassOption warningOption, boolean isBackgroundLearner) {
			this.indexOriginal = indexOriginal;
			this.createdOn = instancesSeen;
			this.lastDriftOn = 0;
			this.lastWarningOn = 0;

			this.classifier = instantiatedClassifier;
			//Usando Gini
			//            this.classifier.splitCriterionOption.setValueViaCLIString("GiniSplitCriterion");
			this.evaluator = evaluatorInstantiated;
			this.useBkgLearner = useBkgLearner;
			this.useDriftDetector = useDriftDetector;

			this.numberOfDriftsDetected = 0;
			this.numberOfWarningsDetected = 0;
			this.isBackgroundLearner = isBackgroundLearner;

			if(this.useDriftDetector) {
				this.driftOption = driftOption;
				this.driftDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.driftOption)).copy();
			}

			// Init Drift Detector for Warning detection.
			if(this.useBkgLearner) {
				this.warningOption = warningOption;
				this.warningDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.warningOption)).copy();
			}
		}

		public CSARFBaseLearner(int indexOriginal, ARFHoeffdingTree instantiatedClassifier, CSARFImbalancedClassificationPerformanceEvaluator evaluatorInstantiated,
				long instancesSeen, boolean useBkgLearner, boolean useDriftDetector, ClassOption driftOption, ClassOption warningOption, boolean isBackgroundLearner) {
			init(indexOriginal, instantiatedClassifier, evaluatorInstantiated, instancesSeen, useBkgLearner, useDriftDetector, driftOption, warningOption, isBackgroundLearner);
		}

		public void reset() {
			if(this.useBkgLearner && this.bkgLearner != null) {
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
			this.evaluator.reset();
		}

		public void trainOnInstance(Instance instance, double weight, long instancesSeen) {
			Instance weightedInstance = (Instance) instance.copy();
			weightedInstance.setWeight(instance.weight() * weight);
			this.classifier.trainOnInstance(weightedInstance);

			if(this.bkgLearner != null)
				this.bkgLearner.classifier.trainOnInstance(instance);

			// Should it use a drift detector? Also, is it a backgroundLearner? If so, then do not "incept" another one.
			if(this.useDriftDetector && !this.isBackgroundLearner) {
				boolean correctlyClassifies = this.classifier.correctlyClassifies(instance);
				// Check for warning only if useBkgLearner is active
				if(this.useBkgLearner) {
					// Update the warning detection method
					this.warningDetectionMethod.input(correctlyClassifies ? 0 : 1);
					// Check if there was a change
					if(this.warningDetectionMethod.getChange()) {
						this.lastWarningOn = instancesSeen;
						this.numberOfWarningsDetected++;
						// Create a new bkgTree classifier
						ARFHoeffdingTree bkgClassifier = (ARFHoeffdingTree) this.classifier.copy();
						bkgClassifier.resetLearning();

						// Resets the evaluator
						CSARFImbalancedClassificationPerformanceEvaluator bkgEvaluator = (CSARFImbalancedClassificationPerformanceEvaluator) this.evaluator.copy();
						bkgEvaluator.reset();

						// Create a new bkgLearner object
						this.bkgLearner = new CSARFBaseLearner(indexOriginal, bkgClassifier, bkgEvaluator, instancesSeen,
								this.useBkgLearner, this.useDriftDetector, this.driftOption, this.warningOption, true);

						// Update the warning detection object for the current object
						// (this effectively resets changes made to the object while it was still a bkg learner).
						this.warningDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.warningOption)).copy();
					}
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

	/***
	 * Inner class to assist with the multi-thread execution.
	 */
	protected class TrainingRunnable implements Runnable, Callable<Integer> {
		final private CSARFBaseLearner learner;
		final private Instance instance;
		final private double weight;
		final private long instancesSeen;

		public TrainingRunnable(CSARFBaseLearner learner, Instance instance,
				double weight, long instancesSeen) {
			this.learner = learner;
			this.instance = instance;
			this.weight = weight;
			this.instancesSeen = instancesSeen;
		}

		@Override
		public void run() {
			learner.trainOnInstance(this.instance, this.weight, this.instancesSeen);
		}

		@Override
		public Integer call() throws Exception {
			run();
			return 0;
		}
	}

	//:: CostMatrix
	protected class CostMatrix extends AbstractMOAObject{

		private int numOfClass;
		private Double[][] matrix;

		public CostMatrix(int numOfClass){
			this.numOfClass = numOfClass;
			this.initMatrix();
		}

		private void initMatrix(){
			this.matrix = new Double[this.numOfClass][this.numOfClass];
			for(int i = 0; i < this.numOfClass; ++i) {
				for(int j = 0; j < this.numOfClass; ++j) {
					this.setCell(i, j, i == j ? new Double(0.0D) : new Double(1.0D));
				}
			}
		}

		public final void setCell(int rowIndex, int columnIndex, Double value) {
			this.matrix[rowIndex][columnIndex] = value;
		}

		public final double getCell(int rowIndex, int columnIndex) {
			return this.matrix[rowIndex][columnIndex];
		}

		public double getCost(Instance e){
			int classPosition = (int) e.classValue();
			return getCost(classPosition);
		}

		private double getCost(int classPosition){
			double totalweight = 0;
			for (int i = 0; i < this.matrix[classPosition].length; i++) {
				totalweight += this.matrix[i][classPosition];
			}
			return totalweight;
		}

		//:: Threshold
		public DoubleVector applyCostToOutput(DoubleVector probs){
			for (int i = 0; i < probs.numValues(); i++) {
				double cost = getCost(i);
				probs.setValue(i, probs.getValue(i) * cost);
			}
			return probs;
		}

		public int getNumOfClass() {
			return numOfClass;
		}

		@Override
		public void getDescription(StringBuilder sb, int indent) {

		}
	}

	//:: Imbalanced Window
	protected class ImbalancedWindow extends AbstractMOAObject{
		private ArrayBlockingQueue<Double> window;
		private double[] distribution;

		public ImbalancedWindow(int capacity){
			this.window = new ArrayBlockingQueue<>(capacity);
		}

		public void addInstance(Example<Instance> instance){
			if(this.distribution == null){
				initVectors(instance.getData().numClasses());
			}
			try{
				this.window.add(instance.getData().classValue());
				this.distribution[(int) instance.getData().classValue()]++;
			}catch (Exception ignored){
				this.distribution[this.window.remove().intValue()]--;
				this.window.add(instance.getData().classValue());
				this.distribution[(int) instance.getData().classValue()]++;
			}
		}

		private void initVectors(int numClass){
			this.distribution = new double[numClass];
			for (int i = 0; i < this.distribution.length; i++) {
				this.distribution[i] = 0;
			}
		}

		public void addInstance(Instance instance){
			InstanceExample i = new InstanceExample(instance);
			this.addInstance(i);
		}

		public double[] getDistribution(){
			return this.distribution;
		}

		private double positiveProportion(){
			int positiveClass = getPositiveClassIndex();
			return this.distribution[positiveClass] / Utils.sum(this.distribution);
		}

		public int getPositiveClassIndex(){
			return Utils.minIndex(this.distribution);
		}

		public int getMajoritoryClassIndex(){
			return Utils.maxIndex(this.distribution);
		}

		public int size(){
			return this.window.size();
		}

		@Override
		public void getDescription(StringBuilder sb, int indent) {

		}
	}

	private void updateCost(){
		for (int i = 0; i < costMatrix.getNumOfClass(); i++) {
			for (int j = 0; j < costMatrix.getNumOfClass(); j++) {
				if(i != j){
					double cdistribution = this.imbalancedWindow.getDistribution()[j];
					double cost = cdistribution == 0 ? costMatrix.getCell(i, j) : 0;
					//                    double cost = 1;
					if(this.costMode == CSARF.PICEK_COST)
						cost = getPicekCost(cdistribution);
					else if(this.costMode == CSARF.OZA_COST)
						cost = ozaCosting.getOzaCost(j);
					if(cost != Double.NEGATIVE_INFINITY && cost != Double.POSITIVE_INFINITY)
						costMatrix.setCell(i, j, cost);
				}else{
					costMatrix.setCell(i, j, 0.0D);
				}
			}
		}
	}

	//:: Picek heuristic
	protected double getPicekCost(int classIndex){
		return this.imbalancedWindow.size() / (costMatrix.getNumOfClass() * this.imbalancedWindow.getDistribution()[classIndex]);
	}

	protected double getPicekCost(double distribution){
		return this.imbalancedWindow.size() / ((costMatrix.getNumOfClass() * distribution));
	}

	//:: OzaCosting heuristic
	protected class OzaCosting extends AbstractMOAObject{
		private double[] lambdaPerClass;
		private double[] lambdaCorrectClass;
		private double[] lambdaIncorrectClass;

		public OzaCosting(int numClass){
			this.lambdaPerClass = new double[numClass];
			this.lambdaCorrectClass = new double[numClass];
			this.lambdaIncorrectClass = new double[numClass];
			for (int i = 0; i < this.lambdaPerClass.length; i++) {
				this.lambdaPerClass[i] = 1.0;
				this.lambdaCorrectClass[i] = 0.0;
				this.lambdaIncorrectClass[i] = 0.0;
			}
		}

		public void updateOzaCosting(Example<Instance> instance, DoubleVector votes){
			int classIndex = (int) instance.getData().classValue();
			boolean correctClassified = Utils.maxIndex(votes.getArrayRef()) == classIndex;
			if (correctClassified) {
				lambdaCorrectClass[classIndex] += lambdaPerClass[classIndex];
				lambdaPerClass[classIndex] = lambdaPerClass[classIndex] * (instancesSeen / (2 * lambdaCorrectClass[classIndex]));
			} else {
				lambdaIncorrectClass[classIndex] += lambdaPerClass[classIndex];
				lambdaPerClass[classIndex] = lambdaPerClass[classIndex] * (instancesSeen / (2 * lambdaIncorrectClass[classIndex]));
			}
		}

		protected double getOzaCost(int classIndex){
			double eClass = this.lambdaCorrectClass[classIndex]/(this.lambdaCorrectClass[classIndex]+this.lambdaIncorrectClass[classIndex]);
			if(eClass == 0.0 || eClass > 0.5 || Double.isNaN(eClass)) return 1.0;
			double betaClass = eClass / (1 - eClass);
			return Math.log(1.0 / betaClass);
		}

		@Override
		public void getDescription(StringBuilder sb, int indent) {

		}
	}

	public class CSARFImbalancedClassificationPerformanceEvaluator extends AbstractOptionHandler
	implements LearningPerformanceEvaluator<Example<Instance>>  {

		private BasicClassificationPerformanceEvaluator basicEvaluator  = new BasicClassificationPerformanceEvaluator();

		private int numClasses                  = -1;
		private int confusionMatrix[][]         = null; // predicted as -> ground-truth
		private boolean multiClass = false;
		private double instancesSeen = 0.0;

		/**
		 * Resets this evaluator. It must be similar to
		 * starting a new evaluator from scratch.
		 */
		@Override
		public void reset() {
			/**
			 * Resets and prepares the basic evaluator.
			 */
			basicEvaluator = new BasicClassificationPerformanceEvaluator();
			basicEvaluator.f1PerClassOption.set();
			basicEvaluator.precisionPerClassOption.set();
			basicEvaluator.recallPerClassOption.set();
			basicEvaluator.precisionRecallOutputOption.set();
			basicEvaluator.prepareForUse();

			/**
			 * Resets the confusion matrix.
			 */
			confusionMatrix = null;
			numClasses      = -1;
		}

		/**
		 * Adds a learning result to this evaluator.
		 *
		 * @param testInst
		 * @param prediction
		 * @return an array of measurements monitored in this evaluator
		 */
		@Override
		public void addResult(Example<Instance> testInst, Prediction prediction) {
			this.addResult(testInst, prediction.getVotes());
		}

		@Override
		public void addResult(Example<Instance> example, double[] classVotes) {
			if(numClasses < 1){
				numClasses = example.getData().numClasses();
				confusionMatrix = new int[numClasses][numClasses];
				if(numClasses > 2){multiClass = true;}
			}
			basicEvaluator.addResult(example, classVotes);

			// updates the confusion matrix
			int pred = Utils.maxIndex(classVotes);
			int gt   = (int) Math.ceil(example.getData().classValue());
			confusionMatrix[pred][gt] += example.weight();
			//    tp+= s.isPositive && predictedClass == 1 ? 1:0;
			//    fp+= !s.isPositive && predictedClass == 1 ? 1:0;
			//    fn+= s.isPositive && predictedClass == 0 ? 1:0;
			instancesSeen++;
		}

		/**
		 * Gets the current measurements monitored by this evaluator.
		 *
		 * @return an array of measurements monitored by this evaluator
		 */
		@Override
		public Measurement[] getPerformanceMeasurements() {
			ArrayList<Measurement> measures = new ArrayList<Measurement>();
			/**
			 * Gets the basic metrics.
			 */
			Measurement mBasic[]  = basicEvaluator.getPerformanceMeasurements();
			for(Measurement m : mBasic) measures.add(new Measurement(m.getName(), m.getValue()));

			// Appends extra metrics, such as TP, TN, FP, FN, FPR and so forth
			// iterate over all metrics to calculate the metrics per class
			for (int i = 0; i < numClasses; i++) {
				/**
				 * adds the confusion matrix per class
				 */
				measures.addAll(getRates(i));
			}
			double avgFpr = 0.0;
			for (int i = 0; i < numClasses; i++) {
				/**
				 * The FPR.
				 */
				double fprClass = falsePositiveRate(i);
				measures.add(new Measurement("FPR class " + i, fprClass));
				avgFpr += fprClass;
			}
			measures.add(new Measurement("Avg. FPR", avgFpr / numClasses));

			measures.add(new Measurement("MCC", getMCC()));
			measures.add(new Measurement("MCC Multclass", getMCCMultiClass()));
			measures.add(new Measurement("MCC Multclass SK Learn", getMCCMultiClassSKLearner()));
			/**
			 * Creates final array with results
			 */
			Measurement[] result = new Measurement[measures.size()];
			int index = 0;
			for(Measurement m : measures){
				result[index] = m;
				index++;
			}
			return result;
		}

		public double getF1Statistic(){
			return this.basicEvaluator.getF1Statistic();
		}

		@Override
		protected void prepareForUseImpl(TaskMonitor monitor, ObjectRepository repository) {
			reset();
		}

		@Override
		public void getDescription(StringBuilder sb, int indent) {
			sb.append("Combines multiple evaluators to facilitate the comparison of different metrics.");
		}

		/**
		 * Returns an array with the TP, TN, FP, FN values for a positive class <b>classIndex</b>.
		 *
		 * @param classIndex
		 * @return an array with {TP, TN, FP, FN} format.
		 */
		public ArrayList<Measurement> getRates(int classIndex){
			ArrayList<Measurement> arr = new ArrayList<>();
			double tp = 0.0;
			double tn = 0.0;
			double fp = 0.0;
			double fn = 0.0;
			for(int i = 0; i < numClasses; i++){
				for(int j = 0; j < numClasses; j++){
					// TP = (i == j == classIndex)
					// TN = ((i == j) != classIndex)
					// FP = i == classIndex && j != i
					// FN = i != classIndex && j == classIndex
					double v = confusionMatrix[i][j];
					if(i == j && i == classIndex)               tp += v;
					else if(i == j && j != classIndex)          tn += v;
					else if(i == classIndex && i != j)          fp += v;
					else if(i != classIndex && j == classIndex) fn += v;
				}
			}
			arr.add(new Measurement("TP class " + classIndex, tp));
			arr.add(new Measurement("TN class " + classIndex, tn));
			arr.add(new Measurement("FP class " + classIndex, fp));
			arr.add(new Measurement("FN class " + classIndex, fn));
			return arr;
		}
		//TODO: Verificar se nao tem algo mais correto
		public double[] BETAgetRates(int classIndex){
			double[] resp = new double[4];
			double tp = 0.0;
			double tn = 0.0;
			double fp = 0.0;
			double fn = 0.0;
			for(int i = 0; i < numClasses; i++){
				for(int j = 0; j < numClasses; j++){
					double v = confusionMatrix[i][j];
					if(i == j && i == classIndex)               tp += v;
					else if(i == j && j != classIndex)          tn += v;
					else if(i == classIndex && i != j)          fp += v;
					else if(i != classIndex && j == classIndex) fn += v;
				}
			}
			resp[0] = tp;
			resp[1] = tn;
			resp[2] = fp;
			resp[3] = fn;
			return resp;
		}

		public double getMCCMultiClass(){
			double numerador = 0;
			for (int k = 0; k < numClasses; k++) {
				for (int l = 0; l < numClasses; l++) {
					for (int m = 0; m < numClasses; m++) {
						numerador+= confusionMatrix[k][k]*confusionMatrix[m][l] + confusionMatrix[l][k]*confusionMatrix[k][m];
					}
				}
			}
			if (numerador == 0.0) return -1.0;

			double denominador_one = 0.0;
			double denominador_two = 0.0;
			for (int k = 0; k < numClasses; k++) {
				double sum_one = 0.0;
				for (int l = 0; l < numClasses; l++) {
					sum_one+=confusionMatrix[l][k];
				}
				double sum_two = 0.0;
				for (int f = 0; f < numClasses; f++) {
					for (int g = 0; g < numClasses; g++) {
						if(f!=k){
							sum_two+=confusionMatrix[g][f];
						}
					}
				}
				denominador_one+= sum_one*sum_two;
			}
			denominador_one = Math.sqrt(denominador_one);
			if(denominador_one == 0.0) return 0.0;

			for (int k = 0; k < numClasses; k++) {
				double sum_one = 0.0;
				for (int l = 0; l < numClasses; l++) {
					sum_one+=confusionMatrix[k][l];
				}
				double sum_two = 0.0;
				for (int f = 0; f < numClasses; f++) {
					for (int g = 0; g < numClasses; g++) {
						if(f != k) sum_two+=confusionMatrix[f][g];
					}
				}
				denominador_two+= sum_one*sum_two;
			}
			denominador_two = Math.sqrt(denominador_two);
			if(denominador_two == 0.0) return 0.0;
			return  numerador/(denominador_one*denominador_two);
		}


		// https://scikit-learn.org/stable/modules/model_evaluation.html
		public double getMCCMultiClassSKLearner(){
			double correctlyPredictitedSamples = 0.0;
			double totalNumberOfSamples = instancesSeen;
			for (int i = 0; i < numClasses; i++) {
				correctlyPredictitedSamples+= confusionMatrix[i][i];
			}

			double numerador = 0.0;
			double sum_pk = 0.0;
			double sum_tk = 0.0;
			for (int k = 0; k < numClasses; k++) {
				double pk = 0;
				double tk = 0;
				for (int i = 0; i < numClasses; i++) {
					pk += confusionMatrix[k][i];
					tk += confusionMatrix[i][k];
				}
				numerador += pk * tk;

				sum_pk += Math.pow(pk, 2);
				sum_tk += Math.pow(tk, 2);
			}
			numerador = correctlyPredictitedSamples * totalNumberOfSamples - numerador;
			if (numerador == 0.0) return -999.0;

			double denominator = (Math.pow(totalNumberOfSamples, 2) - sum_pk) * (Math.pow(totalNumberOfSamples, 2) - sum_tk);
			denominator = Math.sqrt(denominator);
			if(denominator == 0.0) return 0.0;

			return  numerador/denominator;
		}

		/**
		 * Computes the FPR such that classIndex is the class to be considered as <b>positive</b>.
		 * @param classIndex the index of the positive class
		 * @return the FPR
		 */
		public double falsePositiveRate(int classIndex) {
			double incorrect = 0, total = 0;
			for (int i = 0; i < numClasses; i++) {
				if (i != classIndex) {
					for (int j = 0; j < numClasses; j++) {
						if (j == classIndex) {
							incorrect += confusionMatrix[i][j];
						}
						total += confusionMatrix[i][j];
					}
				}
			}
			return incorrect / total;
		}

		public double getMCC(){
			if(numClasses > 2){ return getMCCMultiClassSKLearner();}
			double[] rates = this.BETAgetRates(0);
			double tp=rates[0];
			double tn=rates[1];
			double fp=rates[2];
			double fn=rates[3];
			double n = tp * tn - fp * fn;
			double d = Math.sqrt((tp+fp)*(tp+fn)*(tn+fp)*(tn+fn));
			return n/d;
		}

		public double getMCCstats(){
			double ntotal = 0;
			for (int i = 0; i < numClasses; i++) {
				double[] rates = this.BETAgetRates(i);
				double tp=rates[0];
				double tn=rates[1];
				double fp=rates[2];
				double fn=rates[3];
				double n = tp * tn - fp * fn;
				double d = Math.sqrt((tp+fp)*(tp+fn)*(tn+fp)*(tn+fn));
				ntotal += n/d;
			}
			ntotal = ntotal/numClasses;
			return ntotal;
		}
	}	
}