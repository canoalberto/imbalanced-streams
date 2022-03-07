/*
 *    GaussianEstimator.java
 *    Copyright (C) 2007 University of Waikato, Hamilton, New Zealand
 *    @author Richard Kirkby (rkirkby@cs.waikato.ac.nz)
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program. If not, see <http://www.gnu.org/licenses/>.
 *    
 */
package moa.core;

import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import moa.AbstractMOAObject;

/**
 * Gaussian incremental estimator that uses incremental method that is more resistant to floating point imprecision.
 * for more info see Donald Knuth's "The Art of Computer Programming, Volume 2: Seminumerical Algorithms", section 4.2.2.
 *
 * @author Richard Kirkby (rkirkby@cs.waikato.ac.nz)
 * @version $Revision: 7 $
 */
public class GaussianEstimatorHistogram extends AbstractMOAObject {

    private static final long serialVersionUID = 1L;

    protected double weightSum;

    protected double mean;

    protected double varianceSum;
    
    protected double simpleSum;
    
    protected double simpleMean;
    
    protected double simpleVarianceSum;       
    
    //protected ArrayList<Double> attValDist = new ArrayList<Double>();

    public static final double NORMAL_CONSTANT = Math.sqrt(2 * Math.PI);

    public void addObservation(double value, double weight) {
        if (Double.isInfinite(value) || Double.isNaN(value)) {
            return;
        }
        if (this.weightSum > 0.0) {
            this.weightSum += weight;
            double lastMean = this.mean;
            this.mean += weight * (value - lastMean) / this.weightSum; 
            this.varianceSum += weight * (value - lastMean) * (value - this.mean);
            
            lastMean = this.simpleMean;
            this.simpleMean = ((lastMean * this.simpleSum) + value ) / (this.simpleSum + 1);
            this.simpleSum ++;
            this.simpleVarianceSum += (value - lastMean) * (value - this.mean);
            
        } else {
            this.mean = value;
            this.weightSum = weight;
            
            this.simpleMean = value;
            this.simpleSum = 1;
        }                
        //qui
        //attValDist.add(value);
        /*
        for (int i = 0; i < weight; i++) {
        	attValDist.add(value);
			
		}
		*/
        
    }

    public void addObservations(GaussianEstimatorHistogram obs) {
        // Follows Variance Combination Rule in Section 2 of
        // Brian Babcock, Mayur Datar, Rajeev Motwani, Liadan O'Callaghan:
        // Maintaining variance and k-medians over data stream windows. PODS 2003: 234-243
        //
        if ((this.weightSum > 0.0) && (obs.weightSum > 0.0)) {
            double oldMean = this.mean;
            this.mean = (this.mean * (this.weightSum / (this.weightSum + obs.weightSum)))
                    + (obs.mean * (obs.weightSum / (this.weightSum + obs.weightSum)));
            this.varianceSum += obs.varianceSum + (this.weightSum * obs.weightSum / (this.weightSum + obs.weightSum) *
                                 Math.pow(obs.mean-oldMean, 2));
            this.weightSum += obs.weightSum;
        }
    }

    public double getTotalWeightObserved() {
        return this.weightSum;
    }

    public double getMean() {
        return this.mean;
    }

    public double getStdDev() {
        return Math.sqrt(getVariance());
    }

    public double getVariance() {
        return this.weightSum > 1.0 ? this.varianceSum / (this.weightSum - 1.0)
                : 0.0;
    }
    
    public double getTotalInstancesObserved() {
        return this.simpleSum;
    }

    public double getSimpleMean() {
        return this.simpleMean;
    }

    public double getSimpleStdDev() {
        return Math.sqrt(getSimpleVariance());
    }

    public double getSimpleVariance() {
        return this.simpleSum > 1.0 ? this.simpleVarianceSum / (this.simpleSum - 1.0) : 0.0;
    }
    
    /*
    public double getQuantileDist(double q) {
    	Collections.sort(this.attValDist);
    	double[] array = this.attValDist.stream().mapToDouble(d -> d).toArray();
    	Percentile p = new Percentile();    	
    	return p.evaluate(array, q);
    }
    
    
    public double[] getAttValDist() {
    	Collections.sort(this.attValDist);
    	return this.attValDist.stream().mapToDouble(d -> d).toArray();
    }
	*/

    public double probabilityDensity(double value) {
        if (this.weightSum > 0.0) {
            double stdDev = getStdDev();
            if (stdDev > 0.0) {
                double diff = value - getMean();
                return (1.0 / (NORMAL_CONSTANT * stdDev))
                        * Math.exp(-(diff * diff / (2.0 * stdDev * stdDev)));
            }
            return value == getMean() ? 1.0 : 0.0;
        }
        return 0.0;
    }
    
    public double probabilityDensityHistogram(double value) {
        if (this.simpleSum > 0.0) {
            double stdDev = getSimpleStdDev();
            if (stdDev > 0.0) {
                double diff = value - getSimpleMean();
                return (1.0 / (NORMAL_CONSTANT * stdDev))
                        * Math.exp(-(diff * diff / (2.0 * stdDev * stdDev)));
            }
            return value == getSimpleMean() ? 1.0 : 0.0;
        }
        return 0.0;
    }

    public double[] estimatedWeight_LessThan_EqualTo_GreaterThan_Value(
            double value) {
        double equalToWeight = probabilityDensity(value) * this.weightSum;
        double stdDev = getStdDev();
        double lessThanWeight = stdDev > 0.0 ? moa.core.Statistics.normalProbability((value - getMean()) / stdDev)
                * this.weightSum - equalToWeight
                : (value < getMean() ? this.weightSum - equalToWeight : 0.0);
        double greaterThanWeight = this.weightSum - equalToWeight
                - lessThanWeight;
        if (greaterThanWeight < 0.0) {
            greaterThanWeight = 0.0;
        }
        return new double[]{lessThanWeight, equalToWeight, greaterThanWeight};
    }
    
    public double[] estimatedWeight_LessThan_EqualTo_GreaterThan_ValueHistogram(
            double value) {
        double equalToWeight = probabilityDensityHistogram(value) * this.simpleSum;
        double stdDev = getSimpleStdDev();
        double lessThanWeight = stdDev > 0.0 ? moa.core.Statistics.normalProbability((value - getSimpleMean()) / stdDev)
                * this.simpleSum - equalToWeight
                : (value < getSimpleMean() ? this.simpleSum - equalToWeight : 0.0);
        double greaterThanWeight = this.simpleSum - equalToWeight
                - lessThanWeight;
        if (greaterThanWeight < 0.0) {
            greaterThanWeight = 0.0;
        }
        return new double[]{lessThanWeight, equalToWeight, greaterThanWeight};
    }

    @Override
    public void getDescription(StringBuilder sb, int indent) {
        // TODO Auto-generated method stub
    }
}
