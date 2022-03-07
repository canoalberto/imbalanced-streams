package moa.streams.generators;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.github.javacliparser.StringOption;

import moa.core.InstanceExample;
import moa.core.ObjectRepository;
import moa.tasks.TaskMonitor;

public class ImbalancedDriftGenerator extends ImbalancedGenerator {

    @Override
    public String getPurposeString() {
        return "Generates an imbalanced binary stream with concept drift.";
    }
   
    private static final long serialVersionUID = 1L;
    
    private abstract class DriftProgression {
    	protected int startInstance = 0, endInstance = 200000;
    	protected double startProgress = 0.0, endProgress = 1.0;
    	abstract double progress(int instancesPast);
    	
    	double speed() {
    		return endInstance - startInstance;
    	}
    	boolean isActive(int instancesPast)
		{
			return instancesPast >= startInstance && instancesPast <= endInstance;
		}
    }
        
    /**
     * Linear progression from start value to end value
     */
    private class IncrementalDriftProgression extends DriftProgression {    	
    	double progress(int instancesPast) {
    		try{
				if (instancesPast < startInstance || instancesPast > endInstance)
				{
					throw new Exception("Incremental drift triggered incorrectly.");
				}
				else {
					double instanceProgress = (double)(instancesPast - startInstance) / (endInstance - startInstance);
					return startProgress + instanceProgress * (endProgress - startProgress);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
    		return Double.NaN;
    	}
    }
    
    /**
     * End value when drift is active, start value otherwise
     */
    private class SuddenDriftProgression extends DriftProgression {
    	double progress(int instancesPast) {
    		try
			{
				if (instancesPast == startInstance) return endProgress;
				else throw new Exception("Sudden drift triggered incorrectly.");
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
    		return Double.NaN;
    	}
    }

    /**
     * While drift is active, linear progression from start value to end value
     * and back.
     */
    private class PeriodicDriftProgression extends DriftProgression {
    	double progress(int instancesPast) {
    		try
			{
				if (instancesPast < startInstance || endInstance < instancesPast) throw new Exception("Periodic drift triggered incorrectly.");
				else {
					double driftProgress = (double)(instancesPast - startInstance) / (endInstance - startInstance);
					if (driftProgress > 0.5) driftProgress = 1 - driftProgress;
					driftProgress *= 2;
					return startProgress + driftProgress * (endProgress - startProgress);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			return Double.NaN;
    	}
    }
    
    static abstract class Drift {
    	protected Random randomness; 
    	protected DriftProgression progression;
    	protected Distribution baseDistribution = null;
    	abstract void applyToImpl(Distribution distribution, int instancesPast);
    	void applyTo(Distribution distribution, int instancesPast)
		{
			if(!progression.isActive(instancesPast)) return;
			if(baseDistribution == null) baseDistribution = distribution.clone();
			applyToImpl(distribution, instancesPast);
		}
    }
    
    static class AppearingMinorityDrift extends Drift {

		void applyToImpl(Distribution distribution, int instancesPast) {
    		this.applyMinorityPresence(distribution, progression.progress(instancesPast));
    	}
		
		void applyMinorityPresence(Distribution distribution, double progress) {
			distribution.positiveShare = baseDistribution.positiveShare * progress;
    		distribution.borderlineRatio = baseDistribution.borderlineRatio * progress;
    		distribution.rareRatio = baseDistribution.rareRatio * progress;
    		distribution.outlierRatio = baseDistribution.outlierRatio * progress;
        	for (int centroidIdx = 0; centroidIdx < distribution.centroids.length; centroidIdx++) {
        		for (int radiusIdx = 0; radiusIdx < distribution.centroids[centroidIdx].radiuses.length; radiusIdx++) {
        			distribution.centroids[centroidIdx].radiuses[radiusIdx] = baseDistribution.centroids[centroidIdx].radiuses[radiusIdx] * progress;
        		}
        		distribution.centroids[centroidIdx].borderlineRadius = baseDistribution.centroids[centroidIdx].borderlineRadius * progress;
        	}
		}
    }
    
    static class DisappearingMinorityDrift extends AppearingMinorityDrift {

		void applyToImpl(Distribution distribution, int instancesPast) {
			double progress = 1.0 - progression.progress(instancesPast);
    		this.applyMinorityPresence(distribution, progress);
    	}	
    }
    
    static class CentroidsJitterDrift extends Drift {

		void applyToImpl(Distribution distribution, int instancesPast) {
        	for (Centroid centroid : distribution.centroids) {
        		for (int i = 0; i < centroid.centre.length; i++) {
        			centroid.centre[i] += (randomness.nextDouble() - 0.5) / progression.speed() * 100;
        		}
        	}
    	}
    }
    
    /**
     * Move each centroid to another point
     * Target points are chosen as to not overlap with original and newly positioned centroids
     */
    static class CentroidsMovementDrift extends Drift {
    	
    	private double[][] targetCenters = null;

		void applyToImpl(Distribution distribution, int instancesPast) {
			if (targetCenters == null) setupTargetCenters(distribution);
			double progress = progression.progress(instancesPast);
    		for (int centroidIdx = 0; centroidIdx < distribution.centroids.length; centroidIdx++) {
        		for (int radiusIdx = 0; radiusIdx < distribution.centroids[centroidIdx].radiuses.length; radiusIdx++) {
        			double position = baseDistribution.centroids[centroidIdx].centre[radiusIdx];
        			double target = targetCenters[centroidIdx][radiusIdx];
        			distribution.centroids[centroidIdx].centre[radiusIdx] = position + progress * (target - position);
        		}
        	}
    	}
		
		private void setupTargetCenters(Distribution distribution) {
			this.targetCenters = new double[distribution.centroids.length][];
			for (int centroidIdx = 0; centroidIdx < distribution.centroids.length; centroidIdx++) {
				targetCenters[centroidIdx] = new double[distribution.centroids[centroidIdx].radiuses.length];
				Boolean foundPlacement = false;
				while(!foundPlacement) {
	        		for (int radiusIdx = 0; radiusIdx < distribution.centroids[centroidIdx].radiuses.length; radiusIdx++) {
	        			double border = baseDistribution.centroids[centroidIdx].radiuses[radiusIdx] + baseDistribution.centroids[centroidIdx].borderlineRadius;
	        			targetCenters[centroidIdx][radiusIdx] = (2*randomness.nextDouble()-1)*(1-border);
	        		}
		            foundPlacement = true;
		            Centroid currentCentroid = baseDistribution.centroids[centroidIdx];
		            for(int j = 0; j < distribution.centroids.length; j++) {
		           		if(baseDistribution.centroids[j].intersectingWithOtherCentroid(targetCenters[centroidIdx], currentCentroid.radiuses, currentCentroid.borderlineRadius)) foundPlacement = false;
		            }
		            for(int j = 0; j < centroidIdx; j++) {
		            	Centroid placedCentroid = baseDistribution.centroids[j];
		            	double[] originalCenter = placedCentroid.centre.clone();
		            	placedCentroid.centre = targetCenters[j];
		            	if(placedCentroid.intersectingWithOtherCentroid(targetCenters[centroidIdx], currentCentroid.radiuses, currentCentroid.borderlineRadius)) foundPlacement = false;
		            	placedCentroid.centre = originalCenter;
		            }
				}
        	}
		}
    }
    
    static class MinorityShareDrift extends Drift {
    	void applyToImpl(Distribution distribution, int instancesPast) {
    		distribution.positiveShare = baseDistribution.positiveShare * progression.progress(instancesPast);
    	}
    }
    
    static class SafeRatioDrift extends Drift {
    	void applyToImpl(Distribution distribution, int instancesPast) {
    		distribution.safeRatio = baseDistribution.safeRatio * progression.progress(instancesPast);
    	}
    }
    
    static class BorderlineRatioDrift extends Drift {
    	void applyToImpl(Distribution distribution, int instancesPast) {
    		distribution.borderlineRatio = baseDistribution.borderlineRatio * progression.progress(instancesPast);
    	}
    }
    
    static class RareRatioDrift extends Drift {
    	void applyToImpl(Distribution distribution, int instancesPast) {
    		distribution.rareRatio = baseDistribution.rareRatio * progression.progress(instancesPast);
    	}
    }
    
    static class OutlierRatioDrift extends Drift {
    	void applyToImpl(Distribution distribution, int instancesPast) {
    		distribution.outlierRatio = baseDistribution.outlierRatio * progression.progress(instancesPast);
    	}
    }
    
    static class AppearingCentroidsDrift extends Drift {
    	void applyToImpl(Distribution distribution, int instancesPast) {
    		double progress = progression.progress(instancesPast);
    		for(int i = 0; i < distribution.centroids.length; i++) {
    			if (i < Math.floor(progress)) {
    				distribution.centroids[i].radiuses = baseDistribution.centroids[i].radiuses.clone();
    				distribution.centroids[i].borderlineRadius = baseDistribution.centroids[i].borderlineRadius;
    				distribution.centroidWeights[i] = 1;
    			} else {
    				for (int l = 0; l < distribution.centroids[i].radiuses.length; l++) {
        				distribution.centroids[i].radiuses[l] = 0;
    				}
    				distribution.centroids[i].borderlineRadius = 0;
    				distribution.centroidWeights[i] = 0;
    			}
    		}
    	}
    }
    
    /**
     * Move centroids away from one randomly determined point
     */
    static class SplittingClustersDrift extends Drift {
    	private double[] joinPoint = null;
    	
    	void applyToImpl(Distribution distribution, int instancesPast) {
    		if (joinPoint == null) prepareJoinPoint(distribution);
    		double progress = progression.progress(instancesPast);
    		for (int centroidIdx = 0; centroidIdx < distribution.centroids.length; centroidIdx++) {
        		for (int dimensionIdx = 0; dimensionIdx < distribution.centroids[centroidIdx].centre.length; dimensionIdx++) {
        			double originalDimension = baseDistribution.centroids[centroidIdx].centre[dimensionIdx];
        			distribution.centroids[centroidIdx].centre[dimensionIdx] = joinPoint[dimensionIdx] + progress * (originalDimension - joinPoint[dimensionIdx]);
        		}
        	}
    	}
    	
    	private void prepareJoinPoint(Distribution distribution) {
    		joinPoint = new double[distribution.centroids[0].radiuses.length];
    		Boolean foundPlacement = false;
    		while(!foundPlacement) {
    			for (int i = 0; i < distribution.centroids[0].radiuses.length; i++) {
    				joinPoint[i] = randomness.nextDouble() - 0.5;
    			}
    			foundPlacement = true;
    			for(Centroid centroid : distribution.centroids) {
    				if (centroid.intersectingWithOtherCentroid(joinPoint, centroid.radiuses, centroid.borderlineRadius)) foundPlacement = false;
    			}
    		}
    	}
    }
    
    /**
     * Decreases safe cluster radiuses replacing them with borderline region
     * Negative class starts overlapping with positive
     */
    static class BorderlineDrift extends Drift {
		void applyToImpl(Distribution distribution, int instancesPast) {
    		double borderlinePresence = progression.progress(instancesPast);
        	for (int centroidIdx = 0; centroidIdx < distribution.centroids.length; centroidIdx++) {
        		double borderlineRadius = baseDistribution.centroids[centroidIdx].borderlineRadius;
        		distribution.centroids[centroidIdx].borderlineRadius = borderlinePresence * borderlineRadius;
        		for (int radiusIdx = 0; radiusIdx < distribution.centroids[centroidIdx].radiuses.length; radiusIdx++) {
        			distribution.centroids[centroidIdx].radiuses[radiusIdx] = baseDistribution.centroids[centroidIdx].radiuses[radiusIdx] - (borderlinePresence * borderlineRadius * 0.5);
        		}
        	}
		}
    }
    
    /**
     * Changes the radiuses of centroids, producing longer shapes
     */
    static class ShapeshiftDrift extends Drift {
		void applyToImpl(Distribution distribution, int instancesPast) {
    		double progress = progression.progress(instancesPast);
        	for (int centroidIdx = 0; centroidIdx < distribution.centroids.length; centroidIdx++) {
        		for (int radiusIdx = 0; radiusIdx < distribution.centroids[centroidIdx].radiuses.length; radiusIdx++) {
        			int direction = radiusIdx % 2 == 0 ? 1 : -1;
        			distribution.centroids[centroidIdx].radiuses[radiusIdx] = baseDistribution.centroids[centroidIdx].radiuses[radiusIdx] * (1 + (progress - 0.5) * direction);
        		}
        	}
		}
    }
    
    /**
     * Multiple drifts definition separated by : character
     * Single drift definition consists of: drift-name/drift-progression,option=value,option2=value2
     * Drift progressions: incremental, sudden, periodic
     * Options: start, end (when the drift is active), value-start, value-end (progression values)
     * 
     * Example: clusters-movement/incremental,start=25000,end=75000
     */
    public StringOption driftOption = new StringOption("drift", 'd', "Concept drift definition", "");
    
    private Drift[] drifts;
    private int instancesPast = 0;
    
    @Override
    public void prepareForUseImpl(TaskMonitor monitor, ObjectRepository repository) {
    	super.prepareForUseImpl(monitor, repository);
    	setupDrift();
    }
    
    @Override
    public InstanceExample nextInstance() {
    	updateDrift();
    	return super.nextInstance();
    }
    
    private void setupDrift() {
    	Map<String, Class> driftsByName = new HashMap<String, Class>();
    	driftsByName.put("appearing-minority", AppearingMinorityDrift.class);
    	driftsByName.put("disappearing-minority", DisappearingMinorityDrift.class);
    	driftsByName.put("jitter", CentroidsJitterDrift.class);
    	driftsByName.put("clusters-movement", CentroidsMovementDrift.class);
    	driftsByName.put("appearing-clusters", AppearingCentroidsDrift.class);
    	driftsByName.put("splitting-clusters", SplittingClustersDrift.class);
    	driftsByName.put("borderline", BorderlineDrift.class);
    	driftsByName.put("shapeshift", ShapeshiftDrift.class);
    	driftsByName.put("minority-share", MinorityShareDrift.class);
    	driftsByName.put("safe-ratio", SafeRatioDrift.class);
    	driftsByName.put("borderline-ratio", BorderlineRatioDrift.class);
    	driftsByName.put("rare-ratio", RareRatioDrift.class);
    	driftsByName.put("outlier-ratio", OutlierRatioDrift.class);

    	if(this.driftOption.getValue().isEmpty())
		{
			this.drifts = new Drift[0];
			return;
		}
    	String[] segments = this.driftOption.getValue().split(":");
    	this.drifts = new Drift[segments.length];
    	for (int i = 0; i < segments.length; i++) {
    		String[] subsegments = segments[i].split(",");
    		String driftName = subsegments[0].split("/")[0];
    		String driftProgression = subsegments[0].split("/")[1];
    		Map<String, String> progressionOptions = new HashMap<String, String>();
    		for (int l = 1; l < subsegments.length; l++) {
    			String[] optionTuple = subsegments[l].split("=");
    			progressionOptions.put(optionTuple[0], optionTuple[1]);
    		}
			try {
				this.drifts[i] = (Drift) driftsByName.get(driftName).newInstance();
				this.drifts[i].randomness = this.instanceRandom;
				DriftProgression progression = null;
				if (driftProgression.equals("incremental")) progression = new IncrementalDriftProgression();
				if (driftProgression.equals("sudden")) progression = new SuddenDriftProgression();
				if (driftProgression.equals("periodic")) progression = new PeriodicDriftProgression();
				if (progressionOptions.containsKey("start")) progression.startInstance = Integer.parseInt(progressionOptions.get("start"));
				if (progressionOptions.containsKey("end")) progression.endInstance = Integer.parseInt(progressionOptions.get("end"));
				if (progressionOptions.containsKey("value-start")) progression.startProgress = Double.parseDouble(progressionOptions.get("value-start"));
				if (progressionOptions.containsKey("value-end")) progression.endProgress = Double.parseDouble(progressionOptions.get("value-end"));
				this.drifts[i].progression = progression;
				// TODO: other drift parameters
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    private void updateDrift() {
    	for (Drift drift : this.drifts) {
    		drift.applyTo(this.distribution, this.instancesPast);
    	}
    	this.instancesPast++;
    }
    
}
