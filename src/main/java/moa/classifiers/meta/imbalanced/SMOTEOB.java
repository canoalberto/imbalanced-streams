/*
 *    SMOTEOB.java
 * 
 *    @author 
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */
package moa.classifiers.meta.imbalanced;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Instances;
import moa.capabilities.CapabilitiesHandler;
import moa.capabilities.Capability;
import moa.capabilities.ImmutableCapabilities;
import moa.classifiers.AbstractClassifier;
import moa.classifiers.Classifier;
import moa.classifiers.MultiClassClassifier;
import moa.classifiers.core.attributeclassobservers.AttributeClassObserver;
import moa.classifiers.core.attributeclassobservers.GaussianNumericAttributeClassObserverHistogram;
import moa.classifiers.core.attributeclassobservers.NominalAttributeClassObserverHistogram;
import moa.classifiers.core.driftdetection.ADWIN;
import moa.classifiers.core.driftdetection.ChangeDetector;
import moa.classifiers.trees.HoeffdingAdaptiveTreeHistogram;
import moa.classifiers.trees.HoeffdingTreeHistogram.ActiveLearningNode;
import moa.classifiers.trees.HoeffdingTreeHistogram.FoundNode;
import moa.classifiers.trees.HoeffdingTreeHistogram.Node;
import moa.core.DoubleVector;
import moa.core.InstanceExample;
import moa.core.Measurement;
import moa.core.MiscUtils;
import moa.core.TimingUtils;
import moa.core.Utils;
import moa.options.ClassOption;
import moa.evaluation.BasicClassificationPerformanceEvaluator;
import moa.AbstractMOAObject;

import com.github.javacliparser.FlagOption;
import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.github.javacliparser.MultiChoiceOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

/**
 * SMOTEOB
 *
 * <p>SMOTE Online Bagging (SMOTEOB). The most important aspects of this 
 * ensemble classifier are: 
 * (1) random over-sampling the minority class through bagging (lamba = a * N_maj / N_min) and sketched-SMOTE (lambda = (1 - a) * N_maj / N_min)
 * (2) random under-sampling the majority class through bagging (lamba = a);
 * (3) inducing diversity through randomly selecting subsets of features 
 * (4) inducing diversity through an abstaining vote strategy</p>
 *
 * <p>See details in:<br> </p>
 *
 * <p>Parameters:</p> <ul>
 * <li>-l : Classifier to train</li>
 * <li>-s : The number of classifiers in the ensemble</li>
 * <li>-h : Histogram to use</li>
 * <li>-t : The starting threshold value to use for the abstaining vote strategy</li>
 * <li>-r : The adjustment factor rate value to use for the abstaining vote strategy</li>
 * <li>-m : The minimum number of samples observed in the minority class for applying VFBC-SMOTE</li>
 * <li>-f : The feature selection method to use</li>
 * <li>-x : The change detector strategy to use for drifts</li>
 * <li>-p : The change detector strategy to use for warnings</li>
 * <li>-d : Should use the drift detector in the ensemble?</li>
 * <li>-e : Seed option</li>
 * </ul>
 *
 * @author 
 * @version $Revision: 1 $
 */
public class SMOTEOB extends AbstractClassifier implements MultiClassClassifier,
                                                                        CapabilitiesHandler {

    @Override
    public String getPurposeString() {
        return "SMOTEOB that uses the online bagging strategy to under/over sampling the samples in input.";
    }
    
    private static final long serialVersionUID = 1L;

    
    public ClassOption baseLearnerOption = new ClassOption("baseLearner", 'l',
            "Classifier to train.", Classifier.class, "meta.AdaptiveRandomForest");  
    
    public IntOption ensembleSizeOption = new IntOption("ensembleSize", 's',
        "The number of learners.", 10, 1, Integer.MAX_VALUE);     
    
    public ClassOption baseHistogramOption = new ClassOption("baseHistogram", 'h',
            "Histrogram to use.", Classifier.class, "trees.HoeffdingAdaptiveTreeHistogram");     
        
    public FloatOption thresholdAbstainingOption = new FloatOption("thresholdAbstaining", 't',
        "The threshold parameter for abstaining vote.", 0.65, 0, 1);	
               
    public FloatOption rateAbstainingOption = new FloatOption("rateAbstaining", 'r',
        "The adjustment factor rate parameter for abstaining vote.", 0.01, 0, 1);
	
    
    public IntOption minSizeAllowedOption = new IntOption("minSizeAllowed", 'm',
            "Minimum number of samples in the minority class for applying SMOTE.",
            100, 1, Integer.MAX_VALUE); 
    
    public MultiChoiceOption featureSelectionOption = new MultiChoiceOption("featureSelection", 'f', 
            "Choose the feature selection method to use.",
            new String[]{"None", "First N Features", "Random N Features"},
            new String[]{"none", "first_n", "random_n"}, 0);
    
    public ClassOption driftDetectionMethodOption = new ClassOption("driftDetectionMethod", 'x',
            "Change detector for drifts and its parameters", ChangeDetector.class, "ADWINChangeDetector -a 1.0E-5");

    public ClassOption warningDetectionMethodOption = new ClassOption("warningDetectionMethod", 'p',
            "Change detector for warnings", ChangeDetector.class, "ADWINChangeDetector -a 1.0E-4");
    
    public FlagOption disableDriftDetectionOption = new FlagOption("disableDriftDetection", 'd',
            "Should use the drift detector in the ensemble?");  
   
    public IntOption seedOption = new IntOption("seed", 'e',
            "Seed option.",
            1, 1, Integer.MAX_VALUE); 
    
    protected static final int NONE = 0; 
    protected static final int FIRST_N = 1;
    protected static final int RANDOM_N = 2;	
	
    protected BaseLearner[] ensemble;    
    protected BasicClassificationPerformanceEvaluator evaluator;
    protected ArrayList<int[]> featuresSelected = new ArrayList<int[]>();  
    protected HoeffdingAdaptiveTreeHistogram histrogram;
    
    protected int minSizeAllowed;
    protected DoubleVector classDistribution;    
    protected DoubleVector bkgClassDistribution;
    protected ChangeDetector driftDetectionMethod;
    protected ChangeDetector warningDetectionMethod;
    protected boolean driftDetection;
    protected ArrayList<ADWIN> adwinEnsemble = new ArrayList<ADWIN>();
    
    protected double thresholdAbstaining;
    protected double rateAbstaining;
    protected int[] indexValues;            
    
    @Override
    public void resetLearningImpl() {
        // Reset attributes
        this.ensemble = null;
        this.evaluator = new BasicClassificationPerformanceEvaluator();
        this.featuresSelected = new ArrayList<int[]>();                
        this.histrogram = (HoeffdingAdaptiveTreeHistogram) getPreparedClassOption(this.baseHistogramOption);        
        this.minSizeAllowed = this.minSizeAllowedOption.getValue();
        this.classDistribution = new DoubleVector();
        this.bkgClassDistribution = null;
        this.driftDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.driftDetectionMethodOption)).copy();
      	this.warningDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.warningDetectionMethodOption)).copy();
        this.driftDetection = !this.disableDriftDetectionOption.isSet();
        if (this.driftDetection) {
        	for (int i = 0; i < this.ensembleSizeOption.getValue(); i++) {        	   	                	
        		this.adwinEnsemble.add(new ADWIN());
        	}        	
		}
        this.thresholdAbstaining = this.thresholdAbstainingOption.getValue();
        this.rateAbstaining = this.rateAbstainingOption.getValue();
        this.indexValues = null;
        this.classifierRandom = new Random(this.seedOption.getValue());  
    }

    @Override
    public void trainOnInstanceImpl(Instance instance) {
        if(this.ensemble == null) 
            initEnsemble(instance);
        
        boolean change = false;
        this.classDistribution.addToValue((int) instance.classValue(), 1);
        this.histrogram.trainOnInstance(instance); 
        
        double C = 0;
        int minorityClass = 0;
        int majorityClass = 0;        
        if (this.classDistribution.numValues() == instance.classAttribute().numValues()) {
        	minorityClass = getMinorityClass();
        	majorityClass = 1 - minorityClass;
        	C = this.classDistribution.getValue(majorityClass) / this.classDistribution.getValue(minorityClass);        	
        } else {
        	minorityClass = 1 - this.classDistribution.maxIndex();
        	majorityClass = this.classDistribution.maxIndex();
        	C = this.classDistribution.getValue(majorityClass) / 1;
        }
        
        List<Pair<FoundNode, Double>> leavesWeights = this.histrogram.getLeaves(null, -1, this.minSizeAllowed, minorityClass);
        
        Instance instanceFeatures = (Instance) instance.copy();        
        for (int i = 0 ; i < this.ensemble.length ; i++) {        	
        	//feature selection  
        	if (this.featureSelectionOption.getChosenIndex() != SMOTEOB.NONE) {
        		instanceFeatures = selectFeatures(i,instance);
        	}        
        	        	
            DoubleVector vote = new DoubleVector(this.ensemble[i].getVotesForInstance(instanceFeatures));
            InstanceExample example = new InstanceExample(instanceFeatures);
            this.ensemble[i].evaluator.addResult(example, vote.getArrayRef());
            
            double a = (double)(i + 1) / (double)this.ensemble.length;            
            
            //minority class sample -> random oversampling + SMOTE from histogram
            if (instance.classValue() == minorityClass) {
            	//random oversampling
            	
            	double lambda = a * C;            
            	int k = MiscUtils.poisson(lambda, this.classifierRandom);
            	if (k > 0) {            		            		
            		this.ensemble[i].trainOnInstance(instanceFeatures,k,i);             		            		
            	}
                //SMOTE from histogram
            	if ((int)this.classDistribution.getValue(minorityClass) > this.minSizeAllowed) {
            		double lambdaSMOTE = (1 - a) * C;            		
            		k = MiscUtils.poisson(lambdaSMOTE, this.classifierRandom);
            		if (k > 0 && leavesWeights.size() > 0) {            			                		
            			for (int b = 0; b < k; b++) {
            				Instance newInstance = generateNewInstance(minorityClass,leavesWeights,instanceFeatures,i);            				            				
            				this.ensemble[i].trainOnInstance(newInstance,1,i);            				            				
            			}
            		}            		
            	}	
            }
            //majority class sample -> random undersampling
            else {
            	double lambda = a;            	            	
            	int k = MiscUtils.poisson(lambda, this.classifierRandom);
            	if (k > 0) {            		            		
            		this.ensemble[i].trainOnInstance(instanceFeatures,k,i);            		    				
            	}
            } 
            
            if (this.driftDetection) {
            	boolean correctlyClassifies = this.ensemble[i].classifier.correctlyClassifies(instanceFeatures);
                double ErrEstim = this.adwinEnsemble.get(i).getEstimation();
                if (this.adwinEnsemble.get(i).setInput(correctlyClassifies ? 0 : 1)) {
                    if (this.adwinEnsemble.get(i).getEstimation() > ErrEstim) {
                        change = true;
                    }
                }
            }                                 
        }
        
        if (change && this.driftDetection) {
            double max = 0.0;
            int imax = -1;
            for (int i = 0; i < this.ensemble.length; i++) {
                if (max < this.adwinEnsemble.get(i).getEstimation()) {
                    max = this.adwinEnsemble.get(i).getEstimation();
                    imax = i;
                }
            }
            if (imax != -1) {
                this.ensemble[imax].classifier.resetLearning();                
                this.adwinEnsemble.set(imax, new ADWIN());
            }
        }
        
        //drift detection in class distribution
    	driftDetection(instance);
    }

    @Override
    public double[] getVotesForInstance(Instance instance) {        
        if(this.ensemble == null) 
            initEnsemble(instance);
        
        DoubleVector combinedVote = new DoubleVector();
        Instance instanceFeatures = (Instance) instance.copy();
        for(int i = 0 ; i < this.ensemble.length ; ++i) {
        	//feature selection  
        	if (this.featureSelectionOption.getChosenIndex() != SMOTEOB.NONE) {
        		instanceFeatures = selectFeatures(i,instance);
        	} 
        	
            DoubleVector vote = new DoubleVector(this.ensemble[i].getVotesForInstance(instanceFeatures));
            if (vote.sumOfValues() > 0.0) {
                vote.normalize();
                double predictedClassVote = vote.getValue(vote.maxIndex());
                //check abstaining/non-abstaining vote
                if (predictedClassVote >= this.thresholdAbstaining) {
                	combinedVote.addValues(vote);
                }               
            }
        }
        
        //update abstaining threshold
        int predictedClass = Utils.maxIndex(combinedVote.getArrayRef());        
        if (predictedClass == (int) instance.classValue()) {
        	if (this.thresholdAbstaining - this.rateAbstaining > 0) {
        		this.thresholdAbstaining -= this.rateAbstaining;
        	}
        }
        else {
        	if (this.thresholdAbstaining + this.rateAbstaining < 1) {
        		this.thresholdAbstaining += this.rateAbstaining;
        	}
        }
        
        return combinedVote.getArrayRef();
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
    	//init array of features that every ensemble must use
    	int ensembleSize = this.ensembleSizeOption.getValue();     	
    		
    	if (this.featureSelectionOption.getChosenIndex() != SMOTEOB.NONE) {
    		int nFeatures = instance.numAttributes() - 1;
            
            if (ensembleSize > nFeatures) {
            	//I can't have more ensembles than the possible # of features combinations
            	ensembleSize = nFeatures;
            }
                    
            if (ensembleSize == nFeatures) {
            	setAllCombinations(nFeatures);        	
            }
            else { 
            	switch(this.featureSelectionOption.getChosenIndex()) {
            	case SMOTEOB.RANDOM_N:            	
	            	this.featuresSelected.add(IntStream.range(0, nFeatures).toArray());        	 
	            	for (int i = 1; i < ensembleSize; i++) {
	            		boolean alreadyExisting = true;
	            		int[] featuresToKeep = {};        		
	            		while (alreadyExisting) {
	            			int[] featuresMask = this.classifierRandom.ints(nFeatures, 0, 2).toArray();        			
	                		featuresToKeep = IntStream.range(0, featuresMask.length).filter(obj -> (featuresMask[obj] == 1)).toArray();
	                		int[] fTK = featuresToKeep.clone();
	                		alreadyExisting = featuresSelected.stream().anyMatch(el -> Arrays.equals(el,fTK));	                		
	                	}	            		      
	            		this.featuresSelected.add(featuresToKeep);	            		
	            	}
	            	break;
            	case SMOTEOB.FIRST_N:
            		ArrayList<Integer> alreadyUsed  = new ArrayList<Integer>();
                	alreadyUsed.add(nFeatures);
                	this.featuresSelected.add(IntStream.range(0, nFeatures).toArray());
                	for (int i = 1; i < ensembleSize; i++) {
                		int nF = this.classifierRandom.nextInt(nFeatures-1) + 1;
                    	while (alreadyUsed.contains(nF)) {
                    		nF = this.classifierRandom.nextInt(nFeatures-1) + 1;
                    	}
                    	alreadyUsed.add(nF);
                    	this.featuresSelected.add(IntStream.range(0, nF).toArray());
                	}
            		break;
            	}
            }
    	}
        
        // Init the ensemble.
        this.ensemble = new BaseLearner[ensembleSize];
        BasicClassificationPerformanceEvaluator classificationEvaluator = new BasicClassificationPerformanceEvaluator();                  
        Classifier baseLearner = (Classifier) getPreparedClassOption(this.baseLearnerOption);
        baseLearner.resetLearning();
        
        for(int i = 0 ; i < ensembleSize ; ++i) {        	
            this.ensemble[i] = new BaseLearner((Classifier) baseLearner.copy(),(BasicClassificationPerformanceEvaluator) classificationEvaluator.copy());
        }                        
    }    

    //return all the possible combinations with all the groups 
    protected void setAllCombinations(int n){    	   
	    for (int r=1; r <= n; r ++) {
	    	this.featuresSelected.add(IntStream.range(0, r).toArray());
	    }	    
    }
    
    protected Instance selectFeatures(int i, Instance instance) {
    	int[] featuresToUse = this.featuresSelected.get(i);
    	Instance newInstance = (Instance) instance.copy();
    	Instances ds = new Instances(newInstance.dataset());
    	if (featuresToUse.length + 1 != instance.numAttributes()) {      		
    		for (int n = instance.numAttributes()-1; n >= 0 ; n--) {    			
				int instAttIndex = modelAttIndexToInstanceAttIndex(n, instance);
				int toCheck = n;
				if (!IntStream.of(featuresToUse).anyMatch(x -> x == toCheck) && instAttIndex < instance.numAttributes()) {
					ds.deleteAttributeAt(instAttIndex);        			
		        	newInstance.deleteAttributeAt(instAttIndex);
				}   			
			}        	       	        	
    	}    
    	newInstance.setDataset(ds);        	             	
    	return newInstance; 
    }
    
    protected int getMinorityClass() {
		if (this.classDistribution.getValue(0) <= this.classDistribution.getValue(1)) {
			return 0;
		} else {
			return 1;
		}		
	}
    
    private Instance generateNewInstance(int minClass, List<Pair<FoundNode, Double>> leavesWeight, Instance instance, int nEnsemble) {       	    	    		    	
    	//find a leaf based on weight
    	FoundNode selectedLeaf = new EnumeratedDistribution<>(leavesWeight).sample();    	    	    	    	    
        Node leafNode = selectedLeaf.node;
        if (leafNode == null) {        	
    		leafNode = selectedLeaf.parent;                               
        } 
                       
        double[] values = new double[instance.numAttributes()];
        int nF = 0;
        for (int i = 0; i < ((ActiveLearningNode) leafNode).getAttributeObservers().size(); i++) {  
        	if (this.featureSelectionOption.getChosenIndex() != SMOTEOB.NONE) {
        		int pos = i;
        		if (!IntStream.of(this.featuresSelected.get(nEnsemble)).anyMatch(x -> x == pos)) {
        			continue;
        		}
        	}
        	int instAttIndex = modelAttIndexToInstanceAttIndex(nF, instance);        	
            AttributeClassObserver obs = ((ActiveLearningNode) leafNode).getAttributeObservers().get(i);
            if (obs != null) {
            	if (obs instanceof GaussianNumericAttributeClassObserverHistogram) {            		
            		if (((GaussianNumericAttributeClassObserverHistogram) obs).getAttValDistPerClass().get(minClass) == null) {
            			values[instAttIndex] = 0;
            		} else {
            			values[instAttIndex] = ((GaussianNumericAttributeClassObserverHistogram) obs).getSampleFromBeta(minClass);            			            			           			
            		}                	
                }
                else if (obs instanceof NominalAttributeClassObserverHistogram) {
                	if (((NominalAttributeClassObserverHistogram) obs).attValDistPerClassSimple.get(minClass) == null) {                		
                		values[instAttIndex] = this.classifierRandom.nextInt(instance.attribute(instAttIndex).numValues());
                	} else { 
                		values[instAttIndex] = ((NominalAttributeClassObserverHistogram) obs).attValDistPerClass.get(minClass).maxIndex();
                	}                	            	
                }
            } else {
            	if (instance.attribute(instAttIndex).isNominal()) {
            		values[instAttIndex] = this.classifierRandom.nextInt(instance.attribute(instAttIndex).numValues());
            	} else {
            		values[instAttIndex] = 0;            		
            	}            	
            }
            nF ++; 
        }
        values[instance.classIndex()] = minClass;
                       
		this.indexValues = new int[instance.numAttributes()];
		for (int i = 0; i < instance.numAttributes(); i ++) {
			this.indexValues[i] = i;
		}
    	         
        //new synthetic instance
		Instance synthetic = (Instance) instance.copy();
		synthetic.addSparseValues(this.indexValues, values, instance.numAttributes());					
		synthetic.setWeight(1.0);
		
		return synthetic;    	    	
    }
    
    protected void driftDetection(Instance instance) {
    	// Update the warning detection method
    	if (this.bkgClassDistribution != null) {
    		this.bkgClassDistribution.addToValue((int) instance.classValue(), 1);
    	}    	
    	boolean correctlyClassifies = this.histrogram.correctlyClassifies(instance);
    	this.warningDetectionMethod.input(correctlyClassifies ? 0 : 1);
    	if(this.warningDetectionMethod.getChange()) {
    		this.bkgClassDistribution = new DoubleVector();
    		this.warningDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.warningDetectionMethodOption)).copy();
    	}
    	// Update the DRIFT detection method
        this.driftDetectionMethod.input(correctlyClassifies ? 0 : 1);
        // Check if there was a change
        if(this.driftDetectionMethod.getChange()) {            
        	if (this.bkgClassDistribution != null) {
        		this.classDistribution = new DoubleVector(this.bkgClassDistribution);
        		this.bkgClassDistribution = null;
        	}
        	this.driftDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.driftDetectionMethodOption)).copy();
        }	
    }

    @Override
    public ImmutableCapabilities defineImmutableCapabilities() {
        if (this.getClass() == SMOTEOB.class)
            return new ImmutableCapabilities(Capability.VIEW_STANDARD, Capability.VIEW_LITE);
        else
            return new ImmutableCapabilities(Capability.VIEW_STANDARD);
    }
    
    /**
     * Inner class that represents a single tree member of the forest. 
     * It contains some analysis information, such as the numberOfDriftsDetected, 
     */
    protected final class BaseLearner extends AbstractMOAObject {               
        public Classifier classifier;                
        public BasicClassificationPerformanceEvaluator evaluator;       
        

        private void init(Classifier instantiatedClassifier, BasicClassificationPerformanceEvaluator evaluatorInstantiated) {                     
            this.classifier = instantiatedClassifier;
            this.evaluator = evaluatorInstantiated;            
        }

        public BaseLearner(Classifier instantiatedClassifier, BasicClassificationPerformanceEvaluator evaluatorInstantiated) {
            init(instantiatedClassifier, evaluatorInstantiated);
        }

        public void reset() {            
          	this.classifier.resetLearning();                        
            this.evaluator.reset();
        }

        public void trainOnInstance(Instance instance, double weight, int i) {        	
    		Instance weightedInstance = (Instance) instance.copy();
            weightedInstance.setWeight(instance.weight() * weight);                      
            this.classifier.trainOnInstance(weightedInstance);       	                                 
        }

        public double[] getVotesForInstance(Instance instance) {
            DoubleVector vote = new DoubleVector(this.classifier.getVotesForInstance(instance));
            return vote.getArrayRef();
        }        
        
        @Override
        public void getDescription(StringBuilder sb, int indent) {
        }
    }        
}
