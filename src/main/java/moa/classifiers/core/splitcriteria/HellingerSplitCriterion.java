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
 * File name: 	HellingerSplitCriterion.java
 * Package: moa.classifiers.core.splitcriteria
 * Created:	March 25th, 2015
 * Author:	Rob Lyon
 * 
 * Contact:	rob@scienceguyrob.com or robert.lyon@postgrad.manchester.ac.uk
 * Web:		<http://www.scienceguyrob.com> or <http://www.cs.manchester.ac.uk> 
 *          or <http://www.jb.man.ac.uk>
 */
package moa.classifiers.core.splitcriteria;

import com.github.javacliparser.FloatOption;

import moa.core.ObjectRepository;
import moa.options.AbstractOptionHandler;
import moa.tasks.TaskMonitor;
import weka.core.Utils;

public class HellingerSplitCriterion extends AbstractOptionHandler implements SplitCriterion 
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
	 * instance, given a two class input distribution, this function will determine the entropy of
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
	 * zero instances at the first branch, and postSplitDists[0][1] is the number of class one instances at 
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
		return computeHellinger(postSplitDists);
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