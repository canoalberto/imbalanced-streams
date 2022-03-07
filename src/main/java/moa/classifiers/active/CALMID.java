//A Comprehensive Active Learning Method for Multiclass Imbalanced Data Streams with Concept Drift

package moa.classifiers.active;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;

import moa.capabilities.CapabilitiesHandler;
import moa.classifiers.AbstractClassifier;
import moa.classifiers.Classifier;
import moa.classifiers.MultiClassClassifier;
import moa.classifiers.core.driftdetection.ADWIN;
import moa.core.DoubleVector;
import moa.core.Measurement;
import moa.core.MiscUtils;
import moa.core.Utils;
import moa.options.ClassOption;


public class CALMID extends AbstractClassifier implements MultiClassClassifier,
CapabilitiesHandler {

    private static final long serialVersionUID = 1L;

    @Override
    public String getPurposeString() {
        return "A Comprehensive Active Learning Method for Multiclass Imbalanced Data Streams with Concept Drift.";
    }

    public ClassOption baseLearnerOption = new ClassOption("baseLearner", 
    		'l',  "Classifier to train.", Classifier.class, "trees.HoeffdingTree");

    public IntOption ensembleSizeOption = new IntOption("ensembleSize", 
    		'e', "The number of models in the bag.", 
    		10, 1, Integer.MAX_VALUE);

    public FloatOption weightShrinkOption = new FloatOption("weightShrink",
    		 'n',"The number to use to compute the weight of new instances.", 
    		 6, 0.0, Float.MAX_VALUE);

    public FloatOption deltaAdwinOption = new FloatOption("deltaAdwin", 
    		'a', "Delta of Adwin change detection", 
    		0.002, 0.0, 1.0);

    public FloatOption activelearningthreshold = new FloatOption("activethreshold", 
    		't',"The percentenge of random selection size.",
    		0.5, 0.0, 1.0);
    
    public FloatOption activelearningbudget = new FloatOption("activebudget", 
    		'b',"The percentenge of random selection size.",
    		0.2, 0.0, 1.0);
    
    public FloatOption randRatioOption = new FloatOption("randratio",
            'r', "random ratio to select instances for imbalance sampling.",
            0.1, 0.0, 1.0);
    
    public IntOption initInstanceNumOption = new IntOption("initInstanceNumber", 
    		'i', "The number of instances to init model at the begin.", 
    		500, 1, Integer.MAX_VALUE);
    
    public IntOption sizeWindowOption = new IntOption("sizeWindow",
            'w', "Size of window for imbalance sampling.",
            500, 100, Integer.MAX_VALUE);
    
    public IntOption seedOption = new IntOption("randomseed",
            's', "Seed for classifierRandom.",
            0, 0, Integer.MAX_VALUE); 
    
    protected   class Sample implements Comparable<Sample>  
    {  
    	int SequenceNo;  //the position of this sample in stream
    	double Diff;     // difficulty for classification
    	Instance Inst;
    	
        public Sample(int seqno ,double diff, Instance inst) {
            this.SequenceNo = seqno;
            this.Diff = diff;
            this.Inst= (Instance) inst.copy();            
        }

        public int compareTo(Sample s)  
        {    
            return this.SequenceNo - s.SequenceNo;  
        }  
    }  
  
    protected Sample[][] sampleArrays;  //sample sliding windows
    protected int[] curPosArrays;
    protected int sizeSampleWin ;
    
    protected int[] samplingWindow; //label sliding window
    protected int posWindow;
    protected int sizeWindow;
    protected double qtyNaNs;
    
    protected Classifier[] ensemble;
    protected ADWIN[] ADError;

    protected int numberOfChangesDetected;
    protected double budget,randRatio;
    protected double threshold;
    
	protected Random rd1 = new Random();
	
	protected int labelingcost;
	protected int processedinstance;
	protected double marginvalue;
	protected int curRealclassindex=0;
	protected int[] randImbalanceCount;
	protected boolean initFirsttime=true;

	protected int firstMaxIndex, secondMaxIndex;
	protected double[][] mutualMarginThreshold; //the asymmetric margin threshold matrix
	protected int numberOfClasses=2;
	protected int numInitInstance;
	   
	protected void addtosamplingWindow(int classindex) {
	       int forget = samplingWindow[posWindow];
	       if(forget != -1){
	    	   randImbalanceCount[forget]--;
	       }else qtyNaNs--;
	       if(classindex != -1) {
	    	   randImbalanceCount[classindex]++;
	       }else qtyNaNs++;
	       samplingWindow[posWindow] = classindex;
	       posWindow++;
	       if (posWindow == sizeWindow) {
	           posWindow = 0;
	       }
	   }

	protected void addtosampleArray(int seqno, double diff, Instance inst) {
	   int curClass =(int)inst.classValue();
	   int pos=(curPosArrays[curClass]+1)%this.sizeSampleWin;
	   curPosArrays[curClass]=pos;
	   sampleArrays[curClass][pos].SequenceNo=seqno;
	   sampleArrays[curClass][pos].Diff=diff;
	   sampleArrays[curClass][pos].Inst=inst.copy();
   }

	protected void trainBaseClassifier(int baseno) {
		double adaptedweight=0.0;
		Sample[] trainset;
		Instance inst;

		trainset=Arrays.copyOf(sampleArrays[0], this.sizeSampleWin*this.numberOfClasses);
    	for (int i = 1; i < this.numberOfClasses; i++) {
    		System.arraycopy(sampleArrays[i], 0, trainset, this.sizeSampleWin*i, this.sizeSampleWin);
    	}
    	Arrays.sort(trainset); 
    	
    	for (int j = 0; j < this.sizeSampleWin*this.numberOfClasses; j++) {
    		if(trainset[j].SequenceNo>0){
            	double diff = trainset[j].Diff;
            	inst=(Instance)trainset[j].Inst.copy();
    			if(diff<0)
            		adaptedweight=0.0;
            	else
            	{
            		double imb=1.0;
                    int numrandImbCount=Math.max(1, randImbalanceCount[(int)inst.classValue()]);  //�Է�imbΪ0.0��
                	imb=(double)numrandImbCount*this.numberOfClasses/ (double)(this.sizeWindow - this.qtyNaNs );
            		adaptedweight=Math.exp((double)(trainset[j].SequenceNo - this.processedinstance)/(double)this.sizeWindow)*Math.log1p(diff+1.0/imb);
            	}
	            double k = 0.0;
	            k = MiscUtils.poisson(this.weightShrinkOption.getValue(), this.classifierRandom);
	            if (k > 0 ) {
	            	inst.setWeight((inst.weight()+adaptedweight) * k);
	                this.ensemble[baseno].trainOnInstance(inst);
	            }
    		}
     	}
	 }

	
    @Override
    public void resetLearningImpl() {
        this.ensemble = new Classifier[this.ensembleSizeOption.getValue()];
        this.budget = this.activelearningbudget.getValue();
        this.threshold = this.activelearningthreshold.getValue();
    	this.numInitInstance=this.initInstanceNumOption.getValue();
        this.randRatio = this.randRatioOption.getValue();


        this.labelingcost = 0;
        this.processedinstance =0;
        this.marginvalue = 0;
        Classifier baseLearner = (Classifier) getPreparedClassOption(this.baseLearnerOption);
        baseLearner.resetLearning();
        for (int i = 0; i < this.ensemble.length; i++) {
            this.ensemble[i] = baseLearner.copy();
        }
        this.ADError = new ADWIN[this.ensemble.length];
        for (int i = 0; i < this.ensemble.length; i++) {
            this.ADError[i] = new ADWIN((double) this.deltaAdwinOption.getValue());
        }
        this.numberOfChangesDetected = 0;
        
    	this.sizeWindow=this.sizeWindowOption.getValue();
    	this.samplingWindow = new int[this.sizeWindow];
       	
       	for (int i = 0; i < this.sizeWindow; i++) {
       		samplingWindow[i]=-1; 
       	}
    	this.posWindow = 0;
    	this.qtyNaNs = sizeWindow;  
    	
        int t1 =this.seedOption.getValue();
        if(t1>0)
        	this.classifierRandom = new Random(t1);  
        else
        	this.classifierRandom = new Random();          
    }

    @Override
    public void trainOnInstanceImpl(Instance inst) {
         if(initFirsttime){
        	this.numberOfClasses=inst.numClasses();
        	this.sizeSampleWin =(int) Math.ceil((double)this.sizeWindow*this.randRatio/(double)this.numberOfClasses);
        	randImbalanceCount= new int[this.numberOfClasses];
        	mutualMarginThreshold= new double[this.numberOfClasses][this.numberOfClasses];
        	sampleArrays = new Sample[this.numberOfClasses][this.sizeSampleWin];
        	curPosArrays =new int[this.numberOfClasses];
        	
        	for (int i = 0; i < this.numberOfClasses; i++) {
        		randImbalanceCount[i]=0; 
        		curPosArrays[i]=-1;
        		for (int j = 0; j < this.numberOfClasses; j++) {
        			mutualMarginThreshold[i][j]= this.threshold;
        		}
        		for (int k = 0; k < this.sizeSampleWin; k++) {
        			sampleArrays[i][k]= new Sample(-1,0,inst);
        		}
        	}
        	initFirsttime=false;
        }
        	
        curRealclassindex=(int) inst.classValue();
        boolean Change = false;
        Instance weightedInst = (Instance) inst.copy();
    	double w = this.weightShrinkOption.getValue();  
       
        boolean learnornot = false;
        double[] count = getVotesForInstance(weightedInst);
        this.processedinstance ++;
    	double diff=0.0;
        double adaptedweight=0;
        double imb=1.0;
        
        if (this.processedinstance < this.numInitInstance  || count.length<1)
        {
        	learnornot = true;
        	diff=-1.0;   
       		if(this.processedinstance < this.numInitInstance)
       			addtosamplingWindow(curRealclassindex);           		
        }
        else
        {
        

          double costNow =(double) (this.labelingcost ) / (double)((double) this.processedinstance );
          if( costNow <this.budget)
          {
        	if(this.classifierRandom.nextDouble()  <  this.randRatio ){
            	addtosamplingWindow(curRealclassindex);      
	        	learnornot = true;     		
        	}
        	else
        		addtosamplingWindow(-1);
        	
            firstMaxIndex = Utils.maxIndex(count);
            double maxDistr=count[firstMaxIndex];
            if (count.length > 1){
	            count[firstMaxIndex]=-1.0;
	            secondMaxIndex = Utils.maxIndex(count);
	            this.marginvalue = maxDistr-count[secondMaxIndex];
            }
            else{
            	secondMaxIndex=1;
            	this.marginvalue=maxDistr;
            }
            	
            int numrandImbCount=Math.max(1, randImbalanceCount[curRealclassindex]); 
        	imb=(double)numrandImbCount*this.numberOfClasses/ (double)(this.sizeWindow - this.qtyNaNs );
        	
        	if (this.marginvalue < mutualMarginThreshold[firstMaxIndex][secondMaxIndex])
	        {
	        	learnornot = true;

	        	if(curRealclassindex==firstMaxIndex){
	        		mutualMarginThreshold[firstMaxIndex][secondMaxIndex] *= (1.0-0.01);
    	        	if(imb > 0.5)   
     	        		mutualMarginThreshold[firstMaxIndex][secondMaxIndex] *= (1.0-0.01);
    	        }
	        	else if(curRealclassindex==secondMaxIndex && imb > 0.5)
	        	{
	        		mutualMarginThreshold[firstMaxIndex][secondMaxIndex] *= (1.0-0.01);
	        	}
	        }
	        else{
        		double samplingbudget =  this.budget-costNow;
        		double p = this.marginvalue- mutualMarginThreshold[firstMaxIndex][secondMaxIndex];
	            samplingbudget = samplingbudget / (samplingbudget + p);
	        	if(this.classifierRandom.nextDouble()  < samplingbudget ){
	        		learnornot=true;
	        	}
        	
	        	if(learnornot){ 
	    	        if(curRealclassindex == secondMaxIndex){
	    	        	mutualMarginThreshold[firstMaxIndex][curRealclassindex] = Math.max( this.threshold,mutualMarginThreshold[firstMaxIndex][curRealclassindex]* (1.0+0.01));
	    	        }
	        	}
	        	
	         }
        	}
        }
        
        if (learnornot ==true)
        {
        	this.labelingcost ++;
        	if(diff<0)
        		adaptedweight=0;
        	else
        	{
        		double f;  
        		f=(curRealclassindex == firstMaxIndex )? 1.0 : -1.0;
        		double s;  
        		s=(curRealclassindex == secondMaxIndex )? 1.0 :0.0;
        		diff=(1-f*this.marginvalue)*Math.exp(1-f-s);        		
        		adaptedweight=Math.log1p(diff+1.0/imb); 

        	}
       	
        	addtosampleArray(this.processedinstance, diff, weightedInst);
        	
        	
	        for (int i = 0; i < this.ensemble.length; i++) {
	            double k = 0.0;
	            k = MiscUtils.poisson(w, this.classifierRandom);
	            if (k > 0 ) {
	                weightedInst.setWeight((inst.weight()+adaptedweight) * k);
	                this.ensemble[i].trainOnInstance(weightedInst);
	            }
	            boolean correctlyClassifies = this.ensemble[i].correctlyClassifies(weightedInst);
	            
	            double ErrEstim = this.ADError[i].getEstimation();
	            if (this.ADError[i].setInput(correctlyClassifies ? 0 : 1)) {
	                if (this.ADError[i].getEstimation() > ErrEstim) {
	                    Change = true;
	                }
	            }
	        }
	        if (Change) {
	            numberOfChangesDetected++;
	            double max = 0.0;
	            int imax = -1;
	            for (int i = 0; i < this.ensemble.length; i++) {
	                if (max < this.ADError[i].getEstimation()) {
	                    max = this.ADError[i].getEstimation();
	                    imax = i;
	                }
	            }
	            if (imax != -1) {
	                this.ensemble[imax].resetLearning();
	                trainBaseClassifier(imax);
	                this.ADError[imax] = new ADWIN((double) this.deltaAdwinOption.getValue());
	            }
	        }
        }
        
        
    }

    @Override
    public double[] getVotesForInstance(Instance inst) {
        DoubleVector combinedVote = new DoubleVector();
        for (int i = 0; i < this.ensemble.length; i++) {
            DoubleVector vote = new DoubleVector(this.ensemble[i].getVotesForInstance(inst));
            if (vote.sumOfValues() > 0.0) {
                vote.normalize();
                combinedVote.addValues(vote);
            }          
        }
        if ( combinedVote.sumOfValues() > 0.0) {
        	combinedVote.normalize();
        }
        else {
        	double[] count = new double[this.numberOfClasses];
        	for(int k=0; k<this.numberOfClasses;k++)
        		count[k]=1/(double)this.numberOfClasses;
        	combinedVote= new DoubleVector(count); 
        }
        
        return combinedVote.getArrayRef();
    }

    @Override
    public boolean isRandomizable() {
        return true;
    }

    @Override
    public void getModelDescription(StringBuilder out, int indent) {
        // TODO Auto-generated method stub
    }

    @Override
    protected Measurement[] getModelMeasurementsImpl() {
    	List<Measurement> measurementList = new LinkedList<Measurement>();
        measurementList.add(new Measurement("labelcost", (double)this.labelingcost/(double)this.processedinstance));
    	measurementList.add(new Measurement("change detections", this.numberOfChangesDetected));
         
        return measurementList.toArray(new Measurement[measurementList.size()]);
    }

    @Override
    public Classifier[] getSubClassifiers() {
        return this.ensemble.clone();
    }
}
