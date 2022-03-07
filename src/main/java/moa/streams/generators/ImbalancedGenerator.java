package moa.streams.generators;

import com.yahoo.labs.samoa.instances.Attribute;
import com.yahoo.labs.samoa.instances.DenseInstance;

import moa.core.FastVector;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Instances;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import moa.core.InstanceExample;

import com.yahoo.labs.samoa.instances.InstancesHeader;

import moa.core.MiscUtils;
import moa.core.ObjectRepository;
import moa.options.AbstractOptionHandler;

import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.github.javacliparser.FlagOption;

import moa.streams.InstanceStream;
import moa.tasks.TaskMonitor;

public class ImbalancedGenerator extends AbstractOptionHandler implements
        InstanceStream {

    @Override
    public String getPurposeString() {
        return "Generates an imbalanced binary stream.";
    }

    private static final long serialVersionUID = 1L;

    public IntOption modelRandomSeedOption = new IntOption("modelRandomSeed",
            'r', "Seed for random generation of model.", 1);

    public IntOption instanceRandomSeedOption = new IntOption(
            "instanceRandomSeed", 'i',
            "Seed for random generation of instances.", 1);

    public IntOption numAttsOption = new IntOption("numAtts", 'a',
            "The number of attributes to generate.", 2, 2, Integer.MAX_VALUE);

    public IntOption numCentroidsOption = new IntOption("numCentroids", 'n',
            "The number of centroids in the model.", 1, 1, Integer.MAX_VALUE);

    public FloatOption positiveShareOption = new FloatOption("positiveShare", 'm',
    		"Share of the positive class instances in stream", 0.1, 0.0, 1.0);
    
    public FloatOption safeRatioOption = new FloatOption("safeRatio", 's',
    		"Ratio of safe examples in positive class", 1.0, 0.0, Float.MAX_VALUE);

    public FloatOption borderlineRatioOption = new FloatOption("borderlineRatio", 'b',
    		"Ratio of borderline examples in positive class", 0.0, 0.0, 100.0);
    
    public FloatOption rareRatioOption = new FloatOption("rareRatio", 'p',
    		"Ratio of borderline examples in positive class", 0.0, 0.0, 100.0);
    
    public FloatOption outlierRatioOption = new FloatOption("outlierRatio", 'o',
    		"Ratio of outlier examples in positive class", 0.0, 0.0, 100.0);
    
    public FlagOption uniformDistributionOption = new FlagOption("uniformDistribution", 'u',
    		"Examples in positive class centroids distributed uniformly");
    
    public FloatOption stdDevsOption = new FloatOption("stdDevs", 'g',
    		"Number of standard deviations fitting in centroids with normal distribution", 3.0, 0.1, Float.MAX_VALUE);
    
    /**
     * Cluster of positive class instances defined by a center of mass in attribute space
     * and a radius on each attribute axis. Ellipsoidal shape.
     */
    protected static class Centroid implements Serializable {

        private static final long serialVersionUID = 1L;

        public double[] centre;

        public double[] radiuses;
        
        public double borderlineRadius;
        
        public double stdDevs;
        
        private int dimensions;
                
        private String distribution;
        
        Centroid(int dimensions) {
        	this.dimensions = dimensions;
        	this.distribution = "normal";
        }
        
        public double[] nextSafePoint(Random random) {
        	double[] attrs = new double[dimensions];
    		do {
    		for (int i = 0; i < dimensions; i++) {
                attrs[i] = centre[i];
                if(isUniform()) {
                	attrs[i] += radiuses[i] * 2 * (random.nextDouble() - 0.5);
                }
                else {
                	attrs[i] += radiuses[i] * random.nextGaussian() / this.stdDevs;
                }
            }
    		} while(!withinSafeRegion(attrs));
    		return attrs;
        }
        
        public double[] nextBorderlinePoint(Random random) {
        	double[] attrs = new double[dimensions];
    		do {
    		for (int i = 0; i < dimensions; i++) {
                attrs[i] = centre[i] + 2 * (random.nextDouble() - 0.5);
            }
    		} while(!withinBorderlineRegion(attrs));
    		return attrs;
        }
        
        private Boolean withinSafeRegion(double[] attrs) {
        	return withinRegion(attrs, this.radiuses);
        }
        
        private Boolean withinBorderlineRegion(double[] attrs) {
        	double[] borderlineRadiuses = new double[dimensions];
        	for(int i = 0; i < dimensions; i++) borderlineRadiuses[i] = this.radiuses[i] + borderlineRadius;
        	return !withinSafeRegion(attrs) && withinRegion(attrs, borderlineRadiuses);
        }
        
        private Boolean withinOutlierRegion(double[] attrs) {
        	double outlierRadius = borderlineRadius * 2;
        	double[] outlierRadiuses = new double[dimensions];
        	for(int i = 0; i < dimensions; i++) outlierRadiuses[i] = this.radiuses[i] + outlierRadius;
        	return !withinRegion(attrs, outlierRadiuses);
        }
        
        public Boolean withinRegion(double[] attrs, double[] radiuses) {
            return euclideanDistanceFromCenter(attrs) < intersectingEuclideanDistanceFromCenter(attrs, radiuses);
        }
        
        public Boolean intersectingWithOtherCentroid(double[] otherCentre, double[] otherRadiuses, double otherBorderlineRadius) {
        	return intersectingEuclideanDistanceFrom(otherCentre, this.centre, this.radiuses) + intersectingEuclideanDistanceFrom(this.centre, otherCentre, otherRadiuses) + borderlineRadius + otherBorderlineRadius > euclideanDistanceBetween(this.centre, otherCentre);
        }
        
        private double euclideanDistanceFromCenter(double[] attrs) {
        	return euclideanDistanceBetween(attrs, centre);
        }
        
        private double intersectingEuclideanDistanceFromCenter(double[] attrs, double[] radiuses) {
        	return intersectingEuclideanDistanceFrom(attrs, this.centre, radiuses);
        }
        
        /**
         * Given an centroid defined by centre and radiuses, returns the point of intersection
         * of the line segment between the centre and attrs, and border of the centroid.
         */
        private double intersectingEuclideanDistanceFrom(double[] attrs, double[] centre, double[] radiuses) {
        	double currentRadius = radiuses[0];
        	double currentDistance = Math.sqrt(Math.pow(attrs[0] - centre[0], 2) + Math.pow(attrs[1] - centre[1], 2));
        	double a, b;
        	for (int i = 1; i < dimensions; i++) {
        		a = currentRadius;
        		b = radiuses[i];
        		double sine = (attrs[i] - centre[i]) / currentDistance;
        		double cosine = Math.sqrt(1.0 - sine * sine);
        		currentRadius = (a * b) / Math.sqrt(Math.pow(b * cosine, 2) + Math.pow(a * sine, 2));
        		currentDistance = Math.sqrt(Math.pow(attrs[i] - centre[i], 2) + Math.pow(currentDistance, 2));
        	}
        	return currentRadius;
        }
        
        private double euclideanDistanceBetween(double[] attrs1, double[] attrs2) {
        	double sum = 0;
        	for(int i = 0; i < dimensions; i++) {
        		sum += Math.abs(Math.pow(attrs1[i] - attrs2[i], dimensions));
        	}
        	return Math.pow(sum, 1.0 / dimensions);
        }
        
        public void setDistribution(String _distribution) {
        	if(_distribution == "uniform" || _distribution == "normal") {
        		this.distribution = _distribution;
        	}
        }
        
        private Boolean isUniform() {
        	return this.distribution == "uniform";
        }
    }
    
    
    /**
     * Data structure to represent positive class distribution.
     * The field values can be modified to cause concept drift.
     * originalDistribution holds distribution before modification.
     */
    protected static class Distribution implements Serializable {
        protected Centroid[] centroids;
        protected double[] centroidWeights;
        protected double positiveShare;
        protected double safeRatio;
        protected double borderlineRatio;
        protected double rareRatio;
        protected double outlierRatio;

        protected Distribution clone() {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(this);
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bais);
                return (Distribution) ois.readObject();
            } catch (IOException e) {
                return null;
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }

    protected InstancesHeader streamHeader;

    protected Distribution distribution;

    protected Random instanceRandom;
    
    private double[] lastRarePoint;
    private int consecutiveRarePoints = 0;

    @Override
    public void prepareForUseImpl(TaskMonitor monitor,
            ObjectRepository repository) {
        monitor.setCurrentActivity("Preparing imbalanced stream...", -1.0);
        this.distribution = new Distribution();
        this.distribution.positiveShare = this.positiveShareOption.getValue();
        this.distribution.safeRatio = this.safeRatioOption.getValue();
        this.distribution.borderlineRatio = this.borderlineRatioOption.getValue();
        this.distribution.rareRatio = this.rareRatioOption.getValue();
        this.distribution.outlierRatio = this.outlierRatioOption.getValue();
        generateHeader();
        generateCentroids();
        restart();
    }

    @Override
    public InstancesHeader getHeader() {
        return this.streamHeader;
    }

    @Override
    public long estimatedRemainingInstances() {
        return -1;
    }

    @Override
    public boolean hasMoreInstances() {
        return true;
    }

    @Override
    public boolean isRestartable() {
        return true;
    }

    @Override
    public void restart() {
        this.instanceRandom = new Random(this.instanceRandomSeedOption.getValue());
    }

    @Override
    public InstanceExample nextInstance() {
    	if(this.instanceRandom.nextDouble() < this.distribution.positiveShare)
    		return nextPositiveInstance();
    	else
    		return nextNegativeInstance();
    }
    
    protected InstanceExample nextPositiveInstance() {
    	Centroid centroid = this.distribution.centroids[MiscUtils.chooseRandomIndexBasedOnWeights(this.distribution.centroidWeights,
                this.instanceRandom)];
        int numAtts = this.numAttsOption.getValue();
        int instanceType = nextMinorityInstanceType();
        double[] attrs = new double[this.numAttsOption.getValue()];
        if (instanceType == 0) {
            attrs = centroid.nextSafePoint(this.instanceRandom);
        }
        else if (instanceType == 1) {
            attrs = centroid.nextBorderlinePoint(this.instanceRandom);
        }
        else if (instanceType == 2) {
        	attrs = nextRarePoint();
        }
        else if (instanceType == 3) {
        	attrs = nextOutlierPoint();
        }
        double[] attVals = new double[numAtts+1];
        for(int i = 0; i < numAtts; i++) {
        	attVals[i] = attrs[i];
        }
        Instance inst = new DenseInstance(1.0, attVals);
        inst.setDataset(getHeader());
        inst.setClassValue(1.0); // centroids are always positive class
        return new InstanceExample(inst);
    }
    
    
    /**
     * Randomly determine next positive instance type
     * 0 - safe
     * 1 - borderline
     * 2 - rare
     * 3 - outlier
     */
    private int nextMinorityInstanceType() {
    	double[] weights = { distribution.safeRatio, distribution.borderlineRatio, distribution.rareRatio, distribution.outlierRatio };
    	return MiscUtils.chooseRandomIndexBasedOnWeights(weights, this.instanceRandom);
    }
    
    /**
     * Negative instances are uniformly distributed across whole space,
     * except for safe centroids.
     */
    protected InstanceExample nextNegativeInstance() {
        int numAtts = this.numAttsOption.getValue();
        double[] attVals = new double[numAtts + 1];
        do {
        	for (int i = 0; i < numAtts; i++) {
        		attVals[i] = (this.instanceRandom.nextDouble() * 2.0) - 1.0;
            }
        } while(withinAnyCentroid(attVals));
      
        Instance inst = new DenseInstance(1.0, attVals);
        inst.setDataset(getHeader());
        inst.setClassValue(0.0);
        return new InstanceExample(inst);
    }
    
    protected double[] nextOutlierPoint() {
    	int numAtts = this.numAttsOption.getValue();
        double[] attrs = new double[numAtts];
    	do {
        	for (int i = 0; i < numAtts; i++) {
        		attrs[i] = (this.instanceRandom.nextDouble() * 2.0) - 1.0;
            }    		
    	} while(!withinOutlierCentroids(attrs));
        return attrs;
    }
    
    protected double[] nextRarePoint() {
    	if(lastRarePoint == null || consecutiveRarePoints > 2) {
    		lastRarePoint = nextOutlierPoint();
    		consecutiveRarePoints = 1;
    		return lastRarePoint;
    	}
    	else {
    		for (int i = 0; i < this.numAttsOption.getValue(); i++) {
    			lastRarePoint[i] += this.instanceRandom.nextDouble() * 0.02;
    		}
    		consecutiveRarePoints++;
    		return lastRarePoint;
    	}
    }
    
    protected Boolean withinAnyCentroid(double[] attrs) {
    	for(Centroid centroid : distribution.centroids) {
    		if(withinCentroid(attrs, centroid)) return true;
    	}
    	return false;
    }
    
    protected Boolean withinCentroid(double[] attrs, Centroid centroid) {
    	return centroid.withinSafeRegion(attrs);
    }
    
    protected Boolean withinOutlierCentroids(double[] attrs) {
    	for(Centroid centroid : distribution.centroids) {
    		if(!centroid.withinOutlierRegion(attrs)) return false;
    	}
    	return true;
    }

    protected void generateHeader() {
        FastVector attributes = new FastVector();
        for (int i = 0; i < this.numAttsOption.getValue(); i++) {
            attributes.addElement(new Attribute("att" + (i + 1)));
        }
        FastVector classLabels = new FastVector();
        classLabels.addElement("0.0");
        classLabels.addElement("1.0");
        attributes.addElement(new Attribute("class", classLabels));
        this.streamHeader = new InstancesHeader(new Instances(
                getCLICreationString(InstanceStream.class), attributes, 0));
        this.streamHeader.setClassIndex(this.streamHeader.numAttributes() - 1);
    }

    protected void generateCentroids() {
        Random modelRand = new Random(this.modelRandomSeedOption.getValue());
        distribution.centroids = new Centroid[this.numCentroidsOption.getValue()];
        distribution.centroidWeights = new double[distribution.centroids.length];
        for (int i = 0; i < distribution.centroids.length; i++) {
            distribution.centroids[i] = new Centroid(this.numAttsOption.getValue());
            double[] randRadiuses = new double[this.numAttsOption.getValue()];
            double radiusesSum = 0;
            double radiusCap = Math.pow(0.25 / distribution.centroids.length, 1.0 / this.numAttsOption.getValue());
            for (int j = 0; j < randRadiuses.length; j++) {
                randRadiuses[j] = radiusCap / 2 + (modelRand.nextDouble() * radiusCap / 2);
                radiusesSum += randRadiuses[j];
            }
            double randBorderlineRadius = (0.3 + modelRand.nextDouble() * 0.3) * radiusesSum / randRadiuses.length;
            double[] randCentre = new double[this.numAttsOption.getValue()];
            
            // try to place centroid in space without overlapping with others
            Boolean foundPlacement = false;
            while(!foundPlacement) {
	            for (int j = 0; j < randCentre.length; j++) {
	                randCentre[j] = modelRand.nextDouble() - 0.5;
	            }
	            foundPlacement = true;
	            for(int j = 0; j < i; j++) {
	            	if(distribution.centroids[j].intersectingWithOtherCentroid(randCentre, randRadiuses, randBorderlineRadius)) foundPlacement = false;
	            }
            }
            
            distribution.centroids[i].centre = randCentre;
            distribution.centroids[i].radiuses = randRadiuses;
            distribution.centroids[i].stdDevs = stdDevsOption.getValue();
            distribution.centroids[i].borderlineRadius = randBorderlineRadius;
            if (uniformDistributionOption.isSet())
            	distribution.centroids[i].setDistribution("uniform");
            distribution.centroidWeights[i] = radiusesSum; // the bigger the centroid, the more chance of instances in it
        }
    }

    @Override
    public void getDescription(StringBuilder sb, int indent) {
        // TODO Auto-generated method stub
    }
}
