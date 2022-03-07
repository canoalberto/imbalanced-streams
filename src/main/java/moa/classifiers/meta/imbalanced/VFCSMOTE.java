/*
 *    VFCSMOTE.java
 * 
 *    @author Alessio Bernardo (alessio dot bernardo at polimi dot com)
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

import moa.classifiers.AbstractClassifier;
import moa.classifiers.MultiClassClassifier;
import moa.classifiers.Classifier;
import moa.classifiers.core.attributeclassobservers.AttributeClassObserver;
import moa.classifiers.core.attributeclassobservers.GaussianNumericAttributeClassObserverHistogram;
import moa.classifiers.core.attributeclassobservers.NominalAttributeClassObserverHistogram;
import moa.classifiers.core.driftdetection.ChangeDetector;
import moa.classifiers.trees.HoeffdingAdaptiveTreeHistogram;
import moa.classifiers.trees.HoeffdingTreeHistogram.ActiveLearningNode;
import moa.classifiers.trees.HoeffdingTreeHistogram.FoundNode;
import moa.classifiers.trees.HoeffdingTreeHistogram.Node;
import moa.core.DoubleVector;
import moa.core.Measurement;
import moa.core.Utils;
import moa.options.ClassOption;

import com.github.javacliparser.FlagOption;
import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;



/**
 * VFCSMOTE
 *
 * <p>
 * This strategy saves all the mis-classified samples in a histogram managed by ADWIN.
 * There is also the possibility to save a percentage of correctly classified instances.  
 * In the meantime, a model is trained with the data in input. 
 * When the minority sample ratio is less than a certain threshold, an ONLINE (BORDERLINE) SMOTE version is applied.
 * A summary of a random minority sample is chosen from the histogram and a new synthetic sample is generated 
 * until the minority sample ratio is greater or equal than the threshold.
 * The model is then trained with the new samples generated.
 </p>
 *
 *
 * <p>Parameters:</p> <ul>
 * <li>-l : Classifier to train. Default is ARF</li>
 * <li>-h : Histogram to use. Default is HATHistogram, a modified version of HAT</li>
 * <li>-t : Threshold for the minority samples. Default is 0.5</li>
 * <li>-z : Percentage of correctly classified instances to save into the histrogram. Default is 0.0 </li>
 * <li>-m : Minimum number of samples in the minority class for applying SMOTE. Default is 100</li>
 * <li>-d : Should use ADWIN as drift detector? If enabled it is used by the method 
 * 	to track the performance of the classifiers and adapt when a drift is detected.</li>
 * </ul>
 *
 * @author Alessio Bernardo (alessio dot bernardo at polimi dot com) 
 * @version $Revision: 1 $
 */
public class VFCSMOTE extends AbstractClassifier implements MultiClassClassifier {

    @Override
    public String getPurposeString() {
        return "VFCSMOTE strategy that saves all the mis-classified samples in a histogram managed by ADWIN. When the minority class ratio is less than a threshold it generates some synthetic new samples using an ONLINE (BORDERLINE) SMOTE version";
    }
    
    private static final long serialVersionUID = 1L;
    
    public ClassOption baseLearnerOption = new ClassOption("baseLearner", 'l',
            "Classifier to train.", Classifier.class, "meta.AdaptiveRandomForest");        
    
    public ClassOption baseHistogramOption = new ClassOption("baseHistogram", 'h',
            "Histrogram to use.", Classifier.class, "trees.HoeffdingAdaptiveTreeHistogram");        
           
    public FloatOption thresholdOption = new FloatOption("threshold", 't',
            "Minority class samples threshold.",
            0.5, 0.0, 1.0); 
    
    public FloatOption percentageCorrectlyClassifiedOption = new FloatOption("percentageCorrectlyClassified", 'z',
            "Percentage of instances correctly classied to save.",
            0.0, 0.0, 1.0);     
    
    public IntOption minSizeAllowedOption = new IntOption("minSizeAllowed", 'm',
            "Minimum number of samples in the minority class for appling SMOTE.",
            100, 1, Integer.MAX_VALUE); 
    
    public ClassOption driftDetectionMethodOption = new ClassOption("driftDetectionMethod", 'x',
            "Change detector for drifts and its parameters", ChangeDetector.class, "ADWINChangeDetector -a 1.0E-5");

    public ClassOption warningDetectionMethodOption = new ClassOption("warningDetectionMethod", 'p',
            "Change detector for warnings", ChangeDetector.class, "ADWINChangeDetector -a 1.0E-4");
    
    public FlagOption disableDriftDetectionOption = new FlagOption("disableDriftDetection", 'd',
            "Should use ADWIN as drift detector?");  
   
    
    protected Classifier learner; 
    protected HoeffdingAdaptiveTreeHistogram histrogram;
 
    protected double threshold;
    protected double percentageCorrectlyClassified;
    protected int minSizeAllowed;
    protected int nCorrectlyClassified;
    protected int nCorrectlySaved;
    protected boolean driftDetection;
            
    protected DoubleVector generatedClassDistribution;
    protected DoubleVector classDistribution;
    protected DoubleVector bkgClassDistribution;            
	protected int[] indexValues;
	
	protected ChangeDetector driftDetectionMethod;
    protected ChangeDetector warningDetectionMethod;
	
 
    @Override
    public void resetLearningImpl() {     	    	
        this.learner = (Classifier) getPreparedClassOption(this.baseLearnerOption);         
        this.histrogram = (HoeffdingAdaptiveTreeHistogram) getPreparedClassOption(this.baseHistogramOption);          
        this.threshold = this.thresholdOption.getValue();
        this.percentageCorrectlyClassified = this.percentageCorrectlyClassifiedOption.getValue();        
        this.minSizeAllowed = this.minSizeAllowedOption.getValue();
        this.driftDetection = !this.disableDriftDetectionOption.isSet();
        this.learner.resetLearning();            
        this.generatedClassDistribution = new DoubleVector();
        this.classDistribution = new DoubleVector();
        this.bkgClassDistribution = null;      	      	      	   
      	this.indexValues = null;
      	this.driftDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.driftDetectionMethodOption)).copy();
      	this.warningDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.warningDetectionMethodOption)).copy();
      	this.nCorrectlyClassified = 0;
      	this.nCorrectlySaved = 0;
      	this.classifierRandom = new Random(this.randomSeed);
    }

    @Override
    public double[] getVotesForInstance(Instance instance) {
    	double[] prediction = this.learner.getVotesForInstance(instance);    	
        return prediction;
    }
    
    @Override
    public void trainOnInstanceImpl(Instance instance) {  
    	
    	this.learner.trainOnInstance(instance);
    	this.classDistribution.addToValue((int) instance.classValue(), 1);
    	    	
    	if (Utils.maxIndex(this.learner.getVotesForInstance(instance)) != instance.classValue()) {
    		this.histrogram.trainOnInstance(instance);
    	} 
    	//save percentageCorrectlyClassified of the others
    	else {
    		if (this.percentageCorrectlyClassified != 0.0) {
    			this.nCorrectlyClassified ++;
    			double perc = this.percentageCorrectlyClassified * this.nCorrectlyClassified;
    			int toSave = (int) perc;
    			if (toSave > this.nCorrectlySaved) {
    				this.nCorrectlySaved ++;
    				this.histrogram.trainOnInstance(instance);
    			}    			
    		}    		    		
    	}
    	  
    	//drift detection
    	driftDetection(instance);
		
		List<Pair<FoundNode, Double>> leavesWeights = new ArrayList<Pair<FoundNode, Double>>();
		int minClass = 0;
    	//check if the number of minority class samples are greater than -m
		boolean allowSMOTE = false;
		if (this.classDistribution.numValues() == instance.classAttribute().numValues()) {											
			int nMinClass = (int) this.classDistribution.minWeight();					
			if (nMinClass > this.minSizeAllowed) {
				allowSMOTE = true;
				//found real minority class
				minClass = getMinorityClass();
				//found leaves			
				leavesWeights = this.histrogram.getLeaves(null, -1, this.minSizeAllowed, minClass);	
			}					
		}		
		
		//Apply the online SMOTE version until the ratio will be equal to the threshold			
		while (this.threshold > calculateRatio() && allowSMOTE && leavesWeights.size() != 0) {											
			Instance newInstance = generateNewInstance(minClass,leavesWeights,instance);    		
    		this.generatedClassDistribution.addToValue(minClass, 1);
			this.learner.trainOnInstance(newInstance);			
		} 			
    }
 
    private double calculateRatio() {
    	int minClass = getMinorityClass();
    	return ( (double) this.classDistribution.getValue(minClass) + (double) this.generatedClassDistribution.getValue(minClass) ) / 
			   ( (double) this.classDistribution.getValue(0) + (double) this.generatedClassDistribution.getValue(0) + (double) this.classDistribution.getValue(1) + (double) this.generatedClassDistribution.getValue(1));    						    	
    }	   
    
    private Instance generateNewInstance(int minClass, List<Pair<FoundNode, Double>> leavesWeight, Instance instance) {       	    	    		    	
    	//find a leaf based on weight
    	FoundNode selectedLeaf = new EnumeratedDistribution<>(leavesWeight).sample();    	    	    	
      
        Node leafNode = selectedLeaf.node;
        if (leafNode == null) {        	
    		leafNode = selectedLeaf.parent;                               
        }                                                
        
        double[] values = new double[instance.numAttributes()];
        for (int i = 0; i < ((ActiveLearningNode) leafNode).getAttributeObservers().size(); i++) {  
        	int instAttIndex = modelAttIndexToInstanceAttIndex(i, instance);
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
            
        }
        values[instance.classIndex()] = minClass;
              
        if (this.indexValues == null) {    		
    		this.indexValues = new int[instance.numAttributes()];
    		for (int i = 0; i < instance.numAttributes(); i ++) {
    			this.indexValues[i] = i;
    		}
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
    	boolean correctlyClassifies = this.learner.correctlyClassifies(instance);
    	this.warningDetectionMethod.input(correctlyClassifies ? 0 : 1);
    	if(this.warningDetectionMethod.getChange()) {
    		this.bkgClassDistribution = new DoubleVector();
    		this.warningDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.warningDetectionMethodOption)).copy();
    	}
    	// Update the DRIFT detection method
        this.driftDetectionMethod.input(correctlyClassifies ? 0 : 1);
        // Check if there was a change
        if(this.driftDetectionMethod.getChange()) {            
            this.reset();
        }	
    }
    
    protected void reset() {
    	if (this.bkgClassDistribution != null) {
    		this.classDistribution = new DoubleVector(this.bkgClassDistribution);
    		this.bkgClassDistribution = null;
    	}
		if (this.driftDetection) {
			this.learner.resetLearning();
		}
		this.driftDetectionMethod = ((ChangeDetector) getPreparedClassOption(this.driftDetectionMethodOption)).copy();
    }
    
    @Override
    public boolean isRandomizable() {
    	if (this.learner != null) {
    		return this.learner.isRandomizable();	
    	}
    	else {
    		return false;
    	}
    }

    @Override
    public void getModelDescription(StringBuilder arg0, int arg1) {
    }

    @Override
    protected Measurement[] getModelMeasurementsImpl() {
        return null;
    }
    
    public String toString() {
        return "SMOTE online stategy using " + this.learner + " and ADWIN as sliding window";
    }

    public int getMinorityClass() {
		if ((this.classDistribution.getValue(0) + this.generatedClassDistribution.getValue(0)) <= 
				(this.classDistribution.getValue(1) + this.generatedClassDistribution.getValue(1))) {
			return 0;
		} else {
			return 1;
		}		
	}       
    
}