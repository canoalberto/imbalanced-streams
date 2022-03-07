//Multiclass Imbalanced and Concept Drift Network Traffic Classification Framework Based on Online Active Learning

package moa.classifiers.active;

import com.yahoo.labs.samoa.instances.Instance;
import moa.capabilities.CapabilitiesHandler;
import moa.capabilities.Capability;
import moa.capabilities.ImmutableCapabilities;
import moa.classifiers.AbstractClassifier;
import moa.classifiers.MultiClassClassifier;
import moa.core.DoubleVector;
import moa.core.InstanceExample;
import moa.core.Measurement;
import moa.core.Utils;
import moa.options.ClassOption;

import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import moa.evaluation.BasicClassificationPerformanceEvaluator;

import moa.learners.Learner;

public class MicFoal extends AbstractClassifier implements MultiClassClassifier, CapabilitiesHandler {

    @Override
    public String getPurposeString() {
        return "Online Classification Framework of Dynamic Imbalance Network Traffic based on Active Learning.";
    }
    
    private static final long serialVersionUID = 1L;

    public ClassOption learnerOption = new ClassOption("classifictor", 
    		'c',"Supervised learning classificator for network traffic.", MultiClassClassifier.class, "moa.classifiers.meta.AdaptiveRandomForest");
      
    public IntOption initInstanceNumOption = new IntOption("initInstanceNumber", 
    		'i', "The number of instances to init model at the begin.", 
    		500, 1, Integer.MAX_VALUE);

    public FloatOption activelearningthreshold = new FloatOption("activethreshold", 
    		't',"The percentenge of random selection size.",
    		0.9, 0.0, 1.0);
    
    public FloatOption activelearningbudget = new FloatOption("activebudget", 
    		'b',"The percentenge of random selection size.",
    		0.2, 0.0, 1.0);
    
    public FloatOption randRatioOption = new FloatOption("randratio",
            'r', "random ratio to select instances for imbalance sampling.",
            0.1, 0.0, 1.0);

    public IntOption Var_FixBudgetOption = new IntOption("budgetV_F",
            'g', "0:MicFoal_hw; 1: MicFoal; -1:MicFoal_h.", 1, -1, 2);
            //1: the complete MicFoal method; 
            //0: the fixed-Budget MicFoal_hw method with the hybrid label request strategy and the sample training weight formula;
            //-1: the fixed-Budget MicFoal_h method only with the hybrid label request strategy.


    public FloatOption lowF1ThresholdOption = new FloatOption("lowF1Threshold",
            'l', "if F1 score is lower than this threshold, the label cost budget will be enlarged.",
            0.6, 0.0, 1.0);

    public FloatOption highF1ThresholdOption = new FloatOption("highF1Threshold",
            'h', "if F1 score is higher than this threshold, the label cost budget will be reduced.",
            0.9, 0.0, 1.0);

    public IntOption seedOption = new IntOption("randomseed",
            's', "Seed for classifierRandom.",
            0, 0, Integer.MAX_VALUE);     
    
  	
    protected Learner supervisedLearner;
    protected long instancesSeen;
    protected int subspaceSize;
    protected BasicClassificationPerformanceEvaluator evaluator;
    
    protected int[] trueLabelSlidingWin, predictedLabelSlidingWin,randomLabelSlidingWin;
    //the true-label sliding window, predicted-label sliding window, random-label sliding window
    
    protected int posWindow=0;
    protected int sizeWindow; //the size of these label sliding windows
    protected int qtyNaNs;  // the number of placeholder(-1 represents place holder ) in the random-label sliding window
    
    protected double budget,randRatio;
    protected double threshold;
    
	protected Random rd1 = new Random();
	
	protected int labelingcost;
	public int[] randImbalanceCounter;
	protected int[] truelabelCounter; 
	protected int[] predlabelCounter; 
	protected int[] truePositiveCounter; 
	protected boolean[] seenClasses; // if seenClasses[i] is true, the ith class is seen so far
	
	protected double[] multilThresholds;	
	protected int numberOfClasses=2;
	protected int numClassesSeen=0; // The number of classes seen so far
	protected int numInitInstance;  
	protected double curClassImbRatio=1.0; 
	protected double classImbRatio[];
	protected boolean isUncertainty=false, isMinority = false;
	protected int newIndexseen=-1; 
	protected double curInstanceWeight=1.0; 

	protected int selectedby=-1; 
	//selectedby==-2: 	must be learned, as it is too difficult to predict (the length of prediction values[] is 0, that is count[].length==0)
	//selectedby==-1: 	not selected by acting learning, not to be learned;  
	//selectedby==0:	selected by the random strategy;
	//selectedby==1:	selected by the uncertain strategy; 
	//selectedby==2:	selected by the selective strategy;
	//selectedby==numInitInstance:	selected by initial, must be learned;
	
	public int instSelectedby()
	{
		  return selectedby;
	 }
	
	public void adjustBudget(boolean enlarge,double range )
	{
		if(this.Var_FixBudgetOption.getValue() != 1 ) 
			return;

		if(enlarge && this.budget*(1.0+range)<2.0*this.activelearningbudget.getValue() ){
			this.budget *=(1.0+range);
		}
		else if(this.budget*(1.0-range)>this.randRatio){
			this.budget *=(1.0 - range/10.0);
		}
		
	}
	
	public double[] PeriodicEvaluate() 
	{
		double[]  evalueateResults = new double[5]; //Precision, Recall, F1, numRowsofConfusionMatrix, newbudget
		evalueateResults[0]=0; //Precision
		evalueateResults[1]=0; //Recall
		evalueateResults[2]=0; //F1
		evalueateResults[3]=0; //numRowsofConfusionMatrix
		evalueateResults[4]=0; //newbudget		
			
	    int numRowsofConfusionMatrix=this.numberOfClasses; //number of rows of Confusion Matrix	
		
        for (int i = 0; i < this.numberOfClasses; i++) {
        	double tmpPreci=0;
        	if(this.predlabelCounter[i]>0)
        		tmpPreci=(double)this.truePositiveCounter[i]/(double)this.predlabelCounter[i];
 			evalueateResults[0] +=tmpPreci ;
 			double tmpRecall=0;
        	if(this.truelabelCounter[i]>0)
        		tmpRecall=(double)this.truePositiveCounter[i]/(double)this.truelabelCounter[i];
 			evalueateResults[1] +=tmpRecall;
 			
 			if(this.truelabelCounter[i]==0 && this.predlabelCounter[i]==0)
 				numRowsofConfusionMatrix--;
 			else if(tmpRecall + tmpPreci>0)
 				evalueateResults[2] += (2*tmpRecall*tmpPreci)/(tmpRecall+tmpPreci);	
        }
        if(numRowsofConfusionMatrix>0){
			evalueateResults[0] /=(double)numRowsofConfusionMatrix; //Precision
			evalueateResults[1]/=(double)numRowsofConfusionMatrix; //Recall
			evalueateResults[2]/=(double)numRowsofConfusionMatrix; //F1
			evalueateResults[3] =(double)numRowsofConfusionMatrix; 

			double lowf1threshold =this.lowF1ThresholdOption.getValue();
			double highf1threshold =this.highF1ThresholdOption.getValue();
			
			if(evalueateResults[2] < lowf1threshold)
				adjustBudget(true,(lowf1threshold-evalueateResults[2])/10.0); //enlarge,augment
			else if(evalueateResults[2] > highf1threshold)
				adjustBudget(false,(evalueateResults[2]-highf1threshold)/10.0); //shrink,reduce
        }
		evalueateResults[4] =this.budget;
        
		return evalueateResults;
	 }

	protected void AddtoSlidingWindows(int predictedLabelIndex,int trueLabelIndex) {
		
	       int trueLableDropped = this.trueLabelSlidingWin[this.posWindow];
	       int predLabelDropped=this.predictedLabelSlidingWin[this.posWindow];
	       int randLabelDropped = this.randomLabelSlidingWin[this.posWindow];
	       
	       if(randLabelDropped != -1 ){
	    	   this.randImbalanceCounter[randLabelDropped]--; // true-label by randomly selected will be dropped from the random label sliding window 
	       }
	       else 
	    	   this.qtyNaNs--; //when randLabelDropped ==-1, a placeholder -1 will be dropped, so the number of placeholders qtyNaNs subtracts 1;

	       if(trueLableDropped != -1){
	    	   this.truelabelCounter[trueLableDropped]--; // true-label will be dropped from the true-label sliding window 
	    	   if(predLabelDropped > -1) //predLabelDropped maybe equals -2 or -1; -2 means unpredictable
	    	   {
	    		   this.predlabelCounter[predLabelDropped]--;	 // predicted-label will be dropped from the predicted-label sliding window 
	    		   if(trueLableDropped == predLabelDropped) // a label predicted correct will be dropped
	    			   this.truePositiveCounter[predLabelDropped]--;  
	    	   }
	       }
	       
	       if(trueLabelIndex != -1) { 
    		   this.truelabelCounter[trueLabelIndex]++;   
	    	   if( this.selectedby == 0)
	    	   {
	    		   this.randImbalanceCounter[trueLabelIndex]++;
		    	   this.randomLabelSlidingWin[this.posWindow]= trueLabelIndex; //push a true-label into the random label sliding window
	    	   }
	    	   else{
		    	   this.randomLabelSlidingWin[this.posWindow]= -1; //a  placeholder -1 is pushed in the random label sliding window
	    		   this.qtyNaNs++; //number of placeholders adds 1
	    	   }
	    		   
	    	   if(predictedLabelIndex > -1) {
	    		   this.predlabelCounter[predictedLabelIndex]++;    
	    		   if(predictedLabelIndex == trueLabelIndex) //the prediction is  correct for this label being pushed
	    			   this.truePositiveCounter[predictedLabelIndex]++;
	    	   }
	    	   
		       if(!seenClasses[trueLabelIndex] ){// a new class is seen, the budget maybe be enlarged promptly
			       seenClasses[trueLabelIndex]=true; 
			       numClassesSeen++; //number of classes seen from beginning
			       if(newIndexseen > -1) 
			    	   newIndexseen = trueLabelIndex;  
			       else if(this.selectedby!=this.numInitInstance && (this.Var_FixBudgetOption.getValue() >= 1 )) {
			    	   newIndexseen = trueLabelIndex; 
				       this.budget +=0.1; 
			       }
		       }
		       else if ( (newIndexseen > -1) && (newIndexseen != trueLabelIndex) && (this.Var_FixBudgetOption.getValue() >= 1 )) {
			       newIndexseen = -1; 
			       this.budget -=0.1;
		       }
	       }
	       else{
	    	   this.randomLabelSlidingWin[this.posWindow]= -1; //a placeholder -1 is pushed into the random label sliding window
    		   this.qtyNaNs++; //number of placeholders in the random label sliding window adds 1
	       }

	       this.trueLabelSlidingWin[posWindow] = trueLabelIndex; //push the true-label (-1 represents placeholder) into the true-label sliding window
	       this.predictedLabelSlidingWin[posWindow] = predictedLabelIndex; //push the predicted-label (-1 represents placeholder) into the  predicted-label sliding window

	       this.posWindow++;
	       if (this.posWindow == this.sizeWindow) {
	    	   this.posWindow = 0;
	       }

	       if( this.selectedby == 0) // 
	       {
	    	   if(this.qtyNaNs>this.sizeWindow)
	    		   this.qtyNaNs = this.sizeWindow;
	    	   
	    	   int numRandomTimes = this.sizeWindow - this.qtyNaNs; //numRandomTimes maybe equal to 0	    	   
	    	   int numClassCurrent=0; //number of the classes in the true-label sliding window
		       for (int i = 0; i < this.numberOfClasses; i++)
		    	   if(this.truelabelCounter[i]>0) numClassCurrent++;
	    	   
		       for (int i = 0; i < this.numberOfClasses; i++) {  
		       		if(this.randImbalanceCounter[i]<=0)
		       			classImbRatio[i]=0.0;
		       		else
			       		classImbRatio[i]=(double)(this.randImbalanceCounter[i]*numClassCurrent)/ (double)(numRandomTimes+1); //numRandomTimes maybe equal to 0
		       }
	       }
	   }
	
	protected double CalculateWeight(Instance instance) {

		int curtrueLabelIndex=(int) instance.classValue();
		double[] count = getVotesForInstance(instance);
		
	    double diff=0.0;
	    double adaptedweight=0.0; 
	    int predictedLabelIndex=-2;
	    double predValue=0.0;
	    if(count.length>0){ //if count.length equals 0, this instance can not be predicted, so predictedLabelIndex = -2
	    	predictedLabelIndex = Utils.maxIndex(count);
	    	Double temp = new Double(count[predictedLabelIndex]);
	    	if(temp == null || count[predictedLabelIndex]<= 0.0){ //this instance can not be predicted, so predictedLabelIndex = -2
	    		predValue=0.0;
	    		predictedLabelIndex=-2;
	    	}
	    	else
	    		predValue=Math.min(1.0,count[predictedLabelIndex]);
	    }
	    double Delta =0;  //  0 <= Delta <=2
	    if( (predictedLabelIndex == curtrueLabelIndex)){//predicted correctly
	     	Delta =1.0 + this.multilThresholds[curtrueLabelIndex]-predValue;
	    	diff=Delta;  
	    }
	    else //predicted uncorrectly
    		diff=2*(this.numClassesSeen+1);
	    
	    diff=Math.abs(diff);
	    adaptedweight=0.0;
	    		
	    if(this.classImbRatio[curtrueLabelIndex]> ( 1.0/(double)(this.sizeWindow+1))) 
	    	adaptedweight=Math.log1p(diff+ (double)1.0/this.classImbRatio[curtrueLabelIndex]);  
	    else 
	    	adaptedweight=Math.log1p(diff + (double)(this.sizeWindow-this.qtyNaNs +1));  

	    return (double)(1.0 +adaptedweight);
	}
	 
	protected boolean isminority(Instance inst) {
        
    	int  trueLabelIndex =(int) inst.classValue();
    	this.curClassImbRatio = this.classImbRatio[trueLabelIndex];
    	if(this.curClassImbRatio <= 0.5)
    		this.isMinority=true; 
    	else
    		this.isMinority=false;
    	return this.isMinority;
    }
    

	protected boolean HybridStrategy(Instance inst) {
    
	    boolean learnornot = false;
	    double[] count = getVotesForInstance(inst);
	
	    this.isUncertainty=false;
	    selectedby=-1; //no selected by active learning
    	int  trueLabelIndex =(int) inst.classValue();
    	int predictedLabelIndex = -2; //can not be predicted
    	double rd=this.classifierRandom.nextDouble();
   	
	    if(rd  <  this.randRatio ){
	        	learnornot = true;
	        	selectedby=0;
	    }
    	
    	if(count.length<1)
    	{//this inst can not be predicted, so it must be learned, 
    		learnornot = true;
    		if(selectedby !=0) //not selected randomly
    			selectedby = -2; //predictedLabelIndex = -2, its label can not be predicted
    	}
    	else
    	{
    		predictedLabelIndex = Utils.maxIndex(count);
    	    Double temp = new Double(count[predictedLabelIndex]);
    	    if(temp == null || count[predictedLabelIndex]<= 0.0){ //this instance can not be predicted, so predictedLabelIndex = -2
	    		predictedLabelIndex=-2;
	    		learnornot = true;
	    		if(selectedby !=0) //not selected randomly
	    			selectedby = -2; //predictedLabelIndex = -2, its label can not be predicted
    	    }
    	    else if (this.instancesSeen < this.numInitInstance ) //At initial stage, each inst must be learned
    	    {
    	    	learnornot = true;
	    		if(selectedby !=0) //not selected randomly
	    			selectedby=this.numInitInstance;
    	    }
    	    else
    	    {
	    	  double costNow =(double) this.labelingcost / (double) this.instancesSeen ;
		      if( costNow <this.budget)
		      {
		        double maxDistr=Math.min(1.0,count[predictedLabelIndex]);
		        maxDistr=Math.max(0.0,maxDistr);
	
		    	if (maxDistr <= multilThresholds[predictedLabelIndex])  
		        {
	        		learnornot= true;
	 	        	this.isUncertainty=true;
		    		if(selectedby !=0) //not selected randomly
		    			selectedby=1;
		        	if(trueLabelIndex==predictedLabelIndex){
		        		multilThresholds[predictedLabelIndex] *= (1.0-0.01);
			        	if(!isminority(inst))  
		 	        		multilThresholds[predictedLabelIndex] *= (1.0-0.01);    	
			        }
		        }
		        else {
		    		double samplingbudget =  this.budget-costNow;
		    		double p = maxDistr- multilThresholds[predictedLabelIndex]; //as maxDistr > multilThresholds[predictedLabelIndex], p>0
		            samplingbudget = samplingbudget / (samplingbudget + p);
		        	if(this.classifierRandom.nextDouble()  < samplingbudget ){
		        		learnornot=true;
			    		if(selectedby !=0) //not selected randomly
			    			selectedby=2;
		        	}
		         }
		    	
		       }
	      	   if(learnornot && (trueLabelIndex != predictedLabelIndex) && !this.isUncertainty) 
	      			multilThresholds[predictedLabelIndex] = Math.max( this.threshold,multilThresholds[predictedLabelIndex]* (1.0+0.01));
    	     }
	    }
	    if(learnornot && trueLabelIndex >=0)
	    	AddtoSlidingWindows(predictedLabelIndex,trueLabelIndex);
	    else
	    {
	    	learnornot = false;
	    	AddtoSlidingWindows(-1,-1); //placeholder is -1	    	
	    }

	    return learnornot;
    }
	
    @Override
    public void trainOnInstanceImpl(Instance instance) {
        if(this.supervisedLearner == null) 
            initLeaner(instance);

    	++this.instancesSeen;
        curInstanceWeight=0.0; 
        boolean isMiniClass =isminority(instance);
        
        if (!HybridStrategy(instance))  //instance is not selected 
        	return;  

       this.labelingcost++;
       Instance trainingSample = instance.copy(); 
       if(this.Var_FixBudgetOption.getValue() >=0 )
       {
    	   curInstanceWeight = CalculateWeight(trainingSample);
       	   trainingSample.setWeight(curInstanceWeight);
       }              
       InstanceExample example = new InstanceExample(trainingSample);
       this.supervisedLearner.trainOnInstance(example);
    }  
    
    @Override
    public void resetLearningImpl() { 
    	this.supervisedLearner = null;
     }
    
    protected void initLeaner(Instance instance) {//
        // Inital myself
    	supervisedLearner=(Learner) getPreparedClassOption(this.learnerOption);
        this.instancesSeen = 0;
        this.budget = this.activelearningbudget.getValue();
        this.threshold = this.activelearningthreshold.getValue();
    	this.numInitInstance=this.initInstanceNumOption.getValue();
        this.randRatio = this.randRatioOption.getValue();
        this.sizeWindow = this.numInitInstance;
        this.labelingcost = 0;
     	this.qtyNaNs = sizeWindow;  //at inital stage, the random sliding window is filled by placeholders (-1) 
    	this.trueLabelSlidingWin = new int[this.sizeWindow];
    	this.randomLabelSlidingWin = new int[this.sizeWindow];
    	this.predictedLabelSlidingWin = new int[this.sizeWindow];
    	
       	for (int i = 0; i < this.sizeWindow; i++) {
       		this.trueLabelSlidingWin[i]=-1; 
       		this.predictedLabelSlidingWin[i]=-1;     	
       		this.randomLabelSlidingWin[i]=-1; 
       	}

        int randomSeed =this.seedOption.getValue();
        if(randomSeed>0)
        	this.classifierRandom = new Random(randomSeed);  
        else
        	this.classifierRandom = new Random(); 
        
       	this.numberOfClasses = instance.numClasses();
       	randImbalanceCounter= new int[this.numberOfClasses];
       	truePositiveCounter= new int[this.numberOfClasses];
       	predlabelCounter= new int[this.numberOfClasses];
       	truelabelCounter= new int[this.numberOfClasses];
       	seenClasses =new boolean[this.numberOfClasses];
       	
       	multilThresholds= new double[this.numberOfClasses];
       	classImbRatio = new double[this.numberOfClasses];
       	for (int i = 0; i < this.numberOfClasses; i++) {
       		randImbalanceCounter[i]=0; 
       		predlabelCounter[i]=0; 
       		truelabelCounter[i]=0; 
       		seenClasses[i]=false;
       		
       		truePositiveCounter[i]=0; 
       		
       		multilThresholds[i]= this.threshold;
       		classImbRatio[i]=1.0/(double)(this.sizeWindow+1); 
       	}
    }
    
    public void initParameters(int numclasses,double budg, double randratio, int  numInitInst) 
    {
 	   	this.numberOfClasses = numclasses;
	   	randImbalanceCounter= new int[this.numberOfClasses];
	   	truelabelCounter= new int[this.numberOfClasses];
	   	predlabelCounter= new int[this.numberOfClasses];
	   	truePositiveCounter= new int[this.numberOfClasses];
       	seenClasses =new boolean[this.numberOfClasses];
        this.instancesSeen = 0;
        this.budget = budg;
    	this.numInitInstance=numInitInst;
        this.randRatio = randratio;
        this.sizeWindow = this.numInitInstance;
        this.labelingcost = 0;
      	this.qtyNaNs = sizeWindow;  

	   	
	   	multilThresholds= new double[this.numberOfClasses];
	   	classImbRatio = new double[this.numberOfClasses];
	   	for (int i = 0; i < this.numberOfClasses; i++) {
	   		randImbalanceCounter[i]=0; 
	   		truelabelCounter[i]=0; 
	        predlabelCounter[i]=0; 
	   		truePositiveCounter[i]=0; 	   	
	   		seenClasses[i]=false;
	   		multilThresholds[i]= this.threshold;
       		classImbRatio[i]=1.0/(double)(this.sizeWindow+1);
	   	}
    	this.trueLabelSlidingWin = new int[this.sizeWindow];
    	this.predictedLabelSlidingWin = new int[this.sizeWindow];
    	this.randomLabelSlidingWin = new int[this.sizeWindow];
       	for (int i = 0; i < this.sizeWindow; i++) {
       		trueLabelSlidingWin[i]=-1; 
       		predictedLabelSlidingWin[i]=-1;
       		randomLabelSlidingWin[i]=-1; 
       	}	   	
    } 
    
    
    @Override
    public double[] getVotesForInstance(Instance instance) {
        Instance testInstance = instance.copy();
        if(this.supervisedLearner == null) 
            initLeaner(testInstance);
        InstanceExample example = new InstanceExample(testInstance);
        DoubleVector combinedVote = new DoubleVector(this.supervisedLearner.getVotesForInstance(example));
        if ( combinedVote.sumOfValues() > 0.0) {
        	combinedVote.normalize();
        	return combinedVote.getArrayRef();
        }
        else
        	return this.supervisedLearner.getVotesForInstance(example);        
    }

    @Override
    public boolean isRandomizable() {
        return true;
    }

    @Override
    public void getModelDescription(StringBuilder arg0, int arg1) {
    }


    @Override
    public ImmutableCapabilities defineImmutableCapabilities() {
        if (this.getClass() == MicFoal.class)
            return new ImmutableCapabilities(Capability.VIEW_STANDARD, Capability.VIEW_LITE);
        else
            return new ImmutableCapabilities(Capability.VIEW_STANDARD);
    }
    
    
    @Override
    protected Measurement[] getModelMeasurementsImpl() {
    	List<Measurement> measurementList = new LinkedList<Measurement>();
        measurementList.add(new Measurement("labelcost", (double)this.labelingcost/(double)this.instancesSeen));
       
        return measurementList.toArray(new Measurement[measurementList.size()]);
    }
    
    
	public int getsizeofWindow()
	{
		  return this.sizeWindow;
	 }
	
	public double getbudget()
	{
		  return this.budget;
	 }

	public int getnumClasses()
	{
		  return this.numberOfClasses;
	 }
	
	public double gettrainingWeight()
	{
		  return this.curInstanceWeight;
	 }


}