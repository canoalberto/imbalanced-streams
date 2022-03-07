/**
 *
 * This file is part of GHVFDT.
 *
 * GHVFDT is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GHVFDT is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GHVFDT.  If not, see <http://www.gnu.org/licenses/>.
 *
 * File name: 	GHDSplitCriterion.java
 * Package: moa.classifiers.core.splitcriteria
 * Created:	September 30th, 2013
 * Author:	Rob Lyon
 * 
 * Contact:	rob@scienceguyrob.com or robert.lyon@postgrad.manchester.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.cs.manchester.ac.uk> 
 *          or <http://www.jb.man.ac.uk>
 */
package moa.classifiers.core.splitcriteria;

import com.github.javacliparser.FloatOption;

import moa.classifiers.core.splitcriteria.SplitCriterion;
import moa.core.ObjectRepository;
import moa.options.AbstractOptionHandler;
import moa.tasks.TaskMonitor;
import weka.core.Utils;

/**
 * Implementation of the Hellinger Distance as a decision tree split criterion as described in
 * "Learning Decision Trees for Unbalanced Data", Cieslak and Chawla, 2008 and "Hellinger distance
 * decision trees are robust and skew-insensitive", Cieslak et al., 2012. This split criterion is to
 * be used on imbalanced data sets.
 *
 * @author Rob Lyon
 *
 * @version 1.0, 10/30/13
 */
public class GHDSplitCriterion extends AbstractOptionHandler implements SplitCriterion 
{
	//*****************************************
	//*****************************************
	//              Variables
	//*****************************************
	//*****************************************

	/**
	 * Used for serialization.
	 */
	private static final long serialVersionUID = 1L;

	public FloatOption minBranchFracOption = new FloatOption("minBranchFrac",'f',
			"Minimum fraction of weight required down at least two branches.",
			0.01, 0.0, 0.5);

	/**
	 * Computes the merit of splitting for a given distribution before the split and after it. For
	 * instance, given a two class input distribution this function will determine the entropy of
	 * the pre-split distribution, minus the entropy of splitting this distribution according to some 
	 * attribute.
	 * 
	 * Example:
	 * 
	 * preSplitDist = {100,200} where 100 is the number of instances belonging to class zero and 200
	 *                          is the number of instances belonging to class one.
	 * 
	 * postSplitDists = { {25,75} , {150,50} }
	 * 			    		|           |
	 *                      v           v
	 *                First branch  Second branch
	 *                
	 * To be clear, postSplitDists[0] contains the distribution for the first branch and postSplitDists[1]
	 * contains the distribution for the second branch. Then postSplitDists[0][0] contains the number of class
	 * zero instances at the first branch and postSplitDists[0][1] is the number of class one instances at 
	 * the first branch (defined similarly for the other branch).
	 * 
	 * @param preSplitDist the distribution before the split. For two class case preSplitDist
	 * 		  will contain two elements where preSplitDist[0] contains the number of class zero
	 *        instances and preSplitDist[1] contains the number of class one instances.
	 * @param postSplitDists the distribution after the split.
	 */
	@Override
	public double getMeritOfSplit(double[] preSplitDist,double[][] postSplitDists) 
	{
		if (numSubsetsGreaterThanFrac(postSplitDists, this.minBranchFracOption.getValue()) < 2) 

			return Double.NEGATIVE_INFINITY;

		//return computeEntropy(preSplitDist) - computeEntropy(postSplitDists);
		return computeHellinger(postSplitDists);
	}

	public static double computeEntropy(double[] dist) 
	{
		double entropy = 0.0;
		double sum = 0.0;

		for (double d : dist) 
		{
			if (d > 0.0) { // TODO: how small can d be before log2 overflows?
				entropy -= d * Utils.log2(d);
				sum += d;
			}
		}
		return sum > 0.0 ? (entropy + sum * Utils.log2(sum)) / sum : 0.0;
	}

	public static double computeEntropy(double[][] dists) 
	{
		double totalWeight = 0.0;
		double[] distWeights = new double[dists.length];

		for (int i = 0; i < dists.length; i++) {
			distWeights[i] = Utils.sum(dists[i]);
			totalWeight += distWeights[i];
		}

		double entropy = 0.0;

		for (int i = 0; i < dists.length; i++) {
			entropy += distWeights[i] * computeEntropy(dists[i]);
		}
		return entropy / totalWeight;
	}
	
	/**
	 * Hellinger distance as used by Cieslak and Chawla.
	 * @param dist the distribution of instances.
	 * @return the helllinger distance between them.
	 */
	public static double computeHellinger(double[][] dist) 
	{
		if(dist.length==1)
			return 0; // Can't compute distance here.

		double leftBranchNegatives = 0;
		double leftBranchPositives = 0;

		double rightBranchNegatives = 0;
		double rightBranchPositives = 0;

		try{ leftBranchNegatives = dist[0][0]; }catch(Exception e){return 0;}// Can't compute distance here.
		try{ leftBranchPositives = dist[0][1]; }catch(Exception e){return 0;}// Can't compute distance here.

		try{ rightBranchNegatives = dist[1][0]; }catch(Exception e){return 0;}// Can't compute distance here.
		try{ rightBranchPositives = dist[1][1]; }catch(Exception e){return 0;}// Can't compute distance here.

		double totalNegatives = leftBranchNegatives+rightBranchNegatives;
		double totalPositives = leftBranchPositives+rightBranchPositives;


		double hellinger = 0.0;

		hellinger= Math.pow(Math.sqrt(leftBranchNegatives/totalNegatives)-Math.sqrt(leftBranchPositives/totalPositives),2) + 
				Math.pow(Math.sqrt(rightBranchNegatives/totalNegatives)-Math.sqrt(rightBranchPositives/totalPositives),2);

		return Math.sqrt(hellinger);
	}
	
	/**
	 * Computes the Hellinger distance between two normal distributions P and Q.
	 * 
	 * The function used is:
	 * 
	 * H(P,Q)= 1 - SQRT( 2 * standard deviation of P * standard deviation of Q / variance of P + variance of Q) * e^{ -1/4 * ( (mean of P - mean of Q)^{2} / variance of P + variance of Q)}
	 * @param P_mean the mean of the positive distribution.
	 * @param P_variance the variance of the positive distribution.
	 * @param Q_mean the mean of the negative distribution.
	 * @param Q_variance the variance of the negative distribution.
	 * @return the Hellinger distance for the given class distribution.
	 */
	public static double computeHellinger(double P_mean, double P_variance,double Q_mean,double Q_variance) 
	{
		double hellinger = 0.0;

		double P_stdev = Math.sqrt(P_variance);
		double Q_stdev = Math.sqrt(Q_variance);

		hellinger = (double)1 - Math.sqrt(((double)2 * P_stdev * Q_stdev)/(P_variance+Q_variance)) * Math.exp((-(double)1/(double)4)*(Math.pow(P_mean-Q_mean, 2) / (P_variance+Q_variance)));
		return Math.sqrt(hellinger);
	}

	/**
	 * Gets the value of the range of splitting merit.
	 */
	@Override
	public double getRangeOfMerit(double[] preSplitDist) 
	{
		int numClasses = preSplitDist.length > 2 ? preSplitDist.length : 2;
		return Utils.log2(numClasses);
	}

	public static double computeHellinger(double[] dist) 
	{
		return 0;
	}

	public static int numSubsetsGreaterThanFrac(double[][] distributions,double minFrac) 
	{
		double totalWeight = 0.0;
		double[] distSums = new double[distributions.length];

		for (int i = 0; i < distSums.length; i++) 
		{
			for (int j = 0; j < distributions[i].length; j++) 
				distSums[i] += distributions[i][j];

			totalWeight += distSums[i];
		}

		int numGreater = 0;

		for (double d : distSums) 
		{
			double frac = d / totalWeight;

			if (frac > minFrac) 
				numGreater++;
		}
		return numGreater;
	}

	@Override
	public void getDescription(StringBuilder sb, int indent) {}

	@Override
	protected void prepareForUseImpl(TaskMonitor monitor,ObjectRepository repository) {}
}