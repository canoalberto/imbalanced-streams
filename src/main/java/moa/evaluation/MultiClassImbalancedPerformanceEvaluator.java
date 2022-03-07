/*
 *    WindowClassificationPerformanceEvaluator.java
 *    Copyright (C) 2009 University of Waikato, Hamilton, New Zealand
 *    @author Albert Bifet (abifet@cs.waikato.ac.nz)
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
package moa.evaluation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import moa.core.Example;
import moa.core.Measurement;
import moa.core.ObjectRepository;
import moa.options.AbstractOptionHandler;
import moa.tasks.TaskMonitor;

import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Prediction;

import weka.core.Utils;


/**
 * Classification evaluator that updates evaluation results using a sliding
 * window. Only to be used for binary classification problems with unweighted instances.
 * 
 * @author Albert Bifet (abifet at cs dot waikato dot ac dot nz)
 * @version $Revision: 7 $
 */
public class MultiClassImbalancedPerformanceEvaluator extends AbstractOptionHandler implements ClassificationPerformanceEvaluator, Serializable {

	private static final long serialVersionUID = 1L;

	public IntOption widthOption = new IntOption("width", 'w', "Size of Window", 500);

	protected double totalObservedInstances = 0;

	protected Estimator aucEstimator;

	protected int numClasses;

	public class Estimator implements Serializable {

		public class Score implements Comparable<Score>, Serializable {
			/**
			 * Predicted score of the example
			 */
			protected double[] value;

			/**
			 * true class label index of the example
			 */
			protected int realClass;

			/**
			 * Age of example - position in the window where the example was
			 * added
			 */
			protected int posWindow;

			//positive class index for this tree
			protected int pos_class;
			/**
			 * Constructor.
			 * 
			 * @param value
			 *            score value
			 * @param position
			 *            score position in window (defines its age)
			 * @param isPositive
			 *            true if the example's true label is positive
			 */
			public Score(int trueClass, double[] value, int position, int pos_class) {
				this.realClass = trueClass;
				this.value = value;
				this.posWindow = position;
				this.pos_class = pos_class;
			}

			/**
			 * Sort descending based on score value of the positive class of the current tree.
			 */
			@Override
			public int compareTo(Score o) {
				if (o.value[pos_class] < this.value[pos_class]) {
					return -1;
				} else if (o.value[pos_class] > this.value[pos_class]){
					return 1;
				} else {
					if (o.posWindow > this.posWindow) {
						return -1;
					} else if (o.posWindow < this.posWindow){
						return 1;
					} else {
						return 0;
					}
				}
			}

			@Override
			public boolean equals(Object o) {
				return (o instanceof Score) && ((Score)o).posWindow == this.posWindow;
			}

		}

		public class RB_tree{
			protected int pos_class;
			protected int neg_class;
			protected double numPos;
			protected double numNeg;
			protected TreeSet<Score> sortedScores;

			public RB_tree(int pos_class_idx, int neg_class_idx) {
				pos_class = pos_class_idx;
				neg_class = neg_class_idx;
				numPos = 0;
				numNeg = 0;
				sortedScores = new TreeSet<Score>();   
			}
		}

		protected List<RB_tree> rb_trees;//the set of red-black trees for any 2 classes (numClasses*(numClasses-1))

		protected Score[] window;//store current window of examples

		protected double[] predictions;//store correct/incorrect prediction results for all examples in the window: if the example is correctly classified or not: 1-correct, 0-incorrect.
		
	    protected int[] predictionsClass;

		protected int posWindow;//the position where the oldest example that needs to be removed (if window is full) and where the new example that needs to be added.

		protected int size;//window size

		protected double correctPredictions;//the number of correctly classified examples in the current window

		protected double[] correctPrediction_perclass; //the number of correctly classified examples in the current window, for each class (for calculating recall and g-mean)

		protected double[] totalObservedInstances_perclass_window;//the number of examples in the current window, for each class (for calculating recall and g-mean)

		protected double[] inst_classvotes;//the normalized votes for the current data sample

		protected int inst_trueClass;//the true class of the current data sample

		protected WindowAUCImbalancedPerformanceEvaluator[] auc_2c;//store 2-class version of AUC for each class being the positive class (for calculating Provost's weighted AUC)

		protected WindowAUCImbalancedPerformanceEvaluator[] ewauc_2c;//store 2-class version of AUC for each class being the positive class (for calculating Provost's weighted AUC but with equal weight)

	    protected double[] columnKappa;
	    protected double[] rowKappa;
		protected int confusionMatrix[][];
		
		public Estimator(int sizeWindow) {

			this.rb_trees = new ArrayList<RB_tree>();
			for(int i = 0; i < numClasses-1; i++) {
				for(int j = i+1; j < numClasses; j++) {
					RB_tree tree_i = new RB_tree(i,j);//a redblack tree with class i as the positive class
					RB_tree tree_j = new RB_tree(j,i);//a redblack tree with class j as the positive class
					this.rb_trees.add(tree_i);
					this.rb_trees.add(tree_j);
				}
			}

			this.size = sizeWindow;
			this.window = new Score[sizeWindow];
			this.predictions = new double[sizeWindow];
	    	this.predictionsClass = new int[sizeWindow];
			this.correctPrediction_perclass = new double[numClasses];
			this.totalObservedInstances_perclass_window = new double[numClasses];
			this.auc_2c = new WindowAUCImbalancedPerformanceEvaluator[numClasses];
			this.ewauc_2c = new WindowAUCImbalancedPerformanceEvaluator[numClasses];
			
	    	this.rowKappa = new double[numClasses];
	    	this.columnKappa = new double[numClasses];
	    	this.confusionMatrix = new int[numClasses][numClasses];

			this.posWindow = 0;   	
			this.correctPredictions = 0;
			for(int i = 0; i < numClasses; i++) {
				correctPrediction_perclass[i] = 0;
				totalObservedInstances_perclass_window[i] = 0;

				auc_2c[i] = new WindowAUCImbalancedPerformanceEvaluator();
				auc_2c[i].widthOption.setValue(size);
				auc_2c[i].reset(2);
				

				ewauc_2c[i] = new WindowAUCImbalancedPerformanceEvaluator();
				ewauc_2c[i].widthOption.setValue(size);
				ewauc_2c[i].reset(2);
			}

		}

		public void add(double[] score, int trueClass, boolean correctPrediction, int predictedClass) {

			this.inst_classvotes = score;
			this.inst_trueClass = trueClass;

			int[] tree_idx_add = this.find_trees(trueClass);
			// if the window is used and it's full			
			if (size > 0 && posWindow >= this.size) {
				int idx_remove = window[posWindow % size].realClass;//the class label of the example to be removed from the window
				int[] tree_idx_remove = this.find_trees(idx_remove);//find the indices of trees containing the class label of the example to be removed from the window
				correctPredictions -= predictions[posWindow % size];
				correctPrediction_perclass[idx_remove] -= predictions[posWindow % size];
				totalObservedInstances_perclass_window[idx_remove] -= 1;
				for(int i = 0; i < tree_idx_remove.length; i++) {
					RB_tree tree_i = this.rb_trees.get(tree_idx_remove[i]);
					// remove the oldest example from the tree with "trueClass"
					Score node = this.find_node(tree_i, posWindow);
					tree_i.sortedScores.remove(node);

					if (window[posWindow % size].realClass == tree_i.pos_class) {
						tree_i.numPos--;
					} else {
						tree_i.numNeg--;
					}  
				}
				
	    		this.rowKappa[predictionsClass[posWindow % size]] -= 1;
	            this.columnKappa[idx_remove] -= 1;
				this.confusionMatrix[predictionsClass[posWindow % size]][idx_remove]--;
			}

			// add new example
			Score newScore = new Score(trueClass, score, posWindow, -1);//new score for updating windows
			correctPredictions += correctPrediction ? 1 : 0;
			correctPrediction_perclass[trueClass] += correctPrediction ? 1 : 0;
			totalObservedInstances_perclass_window[trueClass] += 1;
	    	this.rowKappa[predictedClass] += 1;
	        this.columnKappa[trueClass] += 1;
			this.confusionMatrix[predictedClass][trueClass]++;
			
			if (size > 0) {
				window[posWindow % size] = newScore;
				predictions[posWindow % size] = correctPrediction ? 1 : 0;
				predictionsClass[posWindow % size] = predictedClass;
			}
			for(int i = 0; i < tree_idx_add.length; i++) {
				RB_tree tree_i = this.rb_trees.get(tree_idx_add[i]);
				Score newScore_tree = new Score(trueClass, score, posWindow, tree_i.pos_class);//new score for updating trees
				tree_i.sortedScores.add(newScore_tree);

				if (trueClass == tree_i.pos_class) {
					tree_i.numPos++;
				} else {
					tree_i.numNeg++;
				}
			}
			// posWindow needs to be always incremented to differentiate between examples in the red-black tree
			posWindow++;
		}

		public Score find_node(RB_tree tree_i, int posWindow) {
			Score node;
			Iterator<Score> iterator = tree_i.sortedScores.iterator();
			while(iterator.hasNext()) {
				node = iterator.next();
				if((node.posWindow % size) == (posWindow % size))             
					return node;
			}
			return null;
		}

		//return the tree indices of those involving class_idx
		public int[] find_trees(int class_idx) {
			int[] tree_idx = new int[2*(numClasses-1)];
			int t = 0;
			for(RB_tree tree_i: this.rb_trees) {
				if((tree_i.pos_class == class_idx) || (tree_i.neg_class == class_idx)) {
					tree_idx[t] = this.rb_trees.indexOf(tree_i);
					t++;
				}
			}
			return tree_idx;
		}

		//return the tree with given positive class index and negative class index
		public int find_onetree(int pos_class, int neg_class) {
			int idx=-1;
			for(RB_tree tree_i: this.rb_trees) {
				if((tree_i.pos_class == pos_class) && (tree_i.neg_class == neg_class)) {
					idx = this.rb_trees.indexOf(tree_i);
					break;
				}
			}
			return idx;
		}

		public double getPMAUC() {
			double pmAUC = 0;
			double c = 0;
			for(RB_tree tree_i: this.rb_trees) {
				c = c + this.getAUC(tree_i);
			}
			pmAUC = c/(numClasses*(numClasses-1));
			return pmAUC;
		}

		//calculate AUC for the given tree
		public double getAUC(RB_tree current_tree) {
			double AUC = 0;
			double c = 0;
			if (current_tree.numPos == 0 || current_tree.numNeg == 0) {
				return 0;
			}

			for (Score s : current_tree.sortedScores){
				if(s.realClass==current_tree.pos_class) {
					c += 1;
				} else {
					AUC += c;
				}
			}

			return AUC / (current_tree.numPos * current_tree.numNeg);
		}

		//calculate AUC based on the given positive class index and negative class index.
		public double getAUC(int pos_class, int neg_class) {
			double AUC = 0;
			double c = 0;
			int idx = this.find_onetree(pos_class, neg_class);
			RB_tree current_tree = this.rb_trees.get(idx);

			if (current_tree.numPos == 0 || current_tree.numNeg == 0) {
				return 0;
			}

			for (Score s : current_tree.sortedScores){
				if(s.realClass==pos_class) {
					c += 1;
				} else {
					AUC += c;
				}
			}

			return AUC / (current_tree.numPos * current_tree.numNeg);
		}


		/**
		 * Provost and Domingo's weighted AUC [2003Tree Induction for Probability-Based Ranking]:
		 * compute the expected AUC, which is the weighted average of the AUCs obtained taking each 
		 * class as the reference class in turn (i.e., making it class 0 and all other
		 * classes class 1). The weight of a class�s AUC is the class�s frequency in the data.
		 * */
		public double getWeightedAUC() {
			double wAUC = 0.0;
			double[] class_weights = new double[numClasses];

			// get class weights
			for(int c = 0; c < numClasses; c++) {
				if(totalObservedInstances>0 && totalObservedInstances<size)
					class_weights[c] = (double)totalObservedInstances_perclass_window[c]/totalObservedInstances;
				else if(totalObservedInstances==0)
					class_weights[c] = 0;
				else
					class_weights[c] = (double)totalObservedInstances_perclass_window[c]/size;
			}
			// get auc
			for(int c = 0; c < numClasses; c++) {   
				int trueClass;
				if(inst_trueClass==c) trueClass = 0;
				else trueClass = 1;

				double[] classVotes = new double[2];
				classVotes[0] = inst_classvotes[c];
				classVotes[1] = 1-inst_classvotes[c];

				auc_2c[c].getAucEstimator().add(classVotes[0], trueClass == 0, Utils.maxIndex(classVotes) == trueClass);//maxIndex(classVotes) or maxIndex(inst_classvotes)?
				double auc = auc_2c[c].getAucEstimator().getAUC();
				wAUC += auc*class_weights[c];
			}

			return wAUC;
		}


		/**
		 * Provost and Domingo's weighted AUC [2003Tree Induction for Probability-Based Ranking] but with 
		 * equal weights of 1/numClasses
		 * */
		public double getEqualWeightedAUC() {
			double ewAUC = 0.0;
			double[] class_weights = new double[numClasses];

			// get class weights
			for(int c = 0; c < numClasses; c++) {
				class_weights[c] = (double)1/numClasses;
			}
			// get auc
			for(int c = 0; c < numClasses; c++) { 
				int trueClass;
				if(inst_trueClass==c) trueClass = 0;
				else trueClass = 1;

				double[] classVotes = new double[2];
				classVotes[0] = inst_classvotes[c];
				classVotes[1] = 1-inst_classvotes[c];

				ewauc_2c[c].getAucEstimator().add(classVotes[0], trueClass == 0, Utils.maxIndex(classVotes) == trueClass);//maxIndex(classVotes) or maxIndex(inst_classvotes)?
				double auc = ewauc_2c[c].getAucEstimator().getAUC();
				ewAUC += (double) auc*class_weights[c];
			}

			return ewAUC;
		}

		/**
		 * Get the ratio of positive-class and negative-class in the corresponding RB-Tree, based on the 
		 * examples in the current window. 
		 * */
		public double getRatio(int pos_class, int neg_class) {
			int idx = this.find_onetree(pos_class, neg_class);
			RB_tree current_tree = this.rb_trees.get(idx);

			if(current_tree.numNeg == 0) {
				return Double.MAX_VALUE;
			} else {
				return current_tree.numPos/current_tree.numNeg;
			}
		}

		//Accuracy in the current window
		public double getAccuracy() {
			if (size > 0) {
				return totalObservedInstances > 0.0 ? correctPredictions / Math.min(size, totalObservedInstances) : 0.0;
			} else {
				return totalObservedInstances > 0.0 ? correctPredictions / totalObservedInstances : 0.0;
			}
		}
		
		public double getKappa() {
			double p0 = getAccuracy();
			double pc = 0.0;

			if (size > 0) {
				for (int i = 0; i < numClasses; i++) {
					pc += (this.rowKappa[i]/Math.min(size, totalObservedInstances)) * (this.columnKappa[i]/Math.min(size, totalObservedInstances));
				}
			} else {
				for (int i = 0; i < numClasses; i++) {
					pc += (this.rowKappa[i]/totalObservedInstances) * (this.columnKappa[i]/totalObservedInstances);
				}
			}
			return (p0 - pc) / (1.0 - pc);
		}

		//Recall of Class i in the current window
		public double getRecall(int class_idx) {
			return totalObservedInstances_perclass_window[class_idx] > 0.0 ? correctPrediction_perclass[class_idx] / totalObservedInstances_perclass_window[class_idx] : 0.0;
		}

		//G-mean in the current window
		public double getGmean() {
			double gmean = 1.0;
			for(int i = 0; i < numClasses; i++) {
				gmean = gmean * this.getRecall(i);
			}
			gmean = Math.pow(gmean, (double)1/numClasses);
			return gmean;
		}

	}


	public void reset(int numClasses) {
		this.numClasses = numClasses;

		this.aucEstimator = new Estimator(this.widthOption.getValue());
		this.totalObservedInstances = 0;
	}

	public void addResult(Instance inst, double[] classVotes) {

		double weight = inst.weight();
		int trueClass = (int) inst.classValue();

		if (weight > 0.0) {
			// // initialize evaluator
			if (totalObservedInstances == 0) {
				reset(inst.dataset().numClasses());
			}
			this.totalObservedInstances += 1;
			
			if(classVotes.length != numClasses) {
				classVotes = new double[this.numClasses];
				for(int i = 0; i < numClasses; i++)
					classVotes[i] = 1.0 / (double) numClasses;
			}
			
			double[] normalizedVote = classVotes.clone();//get a deep copy of classVotes
			
			for(int i = 0; i < normalizedVote.length; i++) {
				if(Double.isNaN(normalizedVote[i]))
					normalizedVote[i] = 0.0;
				if(Double.isInfinite(normalizedVote[i]))
					normalizedVote[i] = Double.MAX_VALUE;
			}
			
			if(Utils.sum(normalizedVote) == 0) { normalizedVote[0] = 1; }

			//// normalize and add score
			Utils.normalize(normalizedVote);
			
			this.aucEstimator.add(normalizedVote, trueClass, Utils.maxIndex(classVotes) == trueClass, Utils.maxIndex(classVotes));
		}
	}

	public Measurement[] getPerformanceMeasurements() {
		Measurement[] measurement = new Measurement[8 + numClasses*numClasses];

		measurement[0] = new Measurement("classified instances", this.totalObservedInstances);
		measurement[1] = new Measurement("PMAUC", this.aucEstimator.getPMAUC());
		measurement[2] = new Measurement("WMAUC", this.aucEstimator.getWeightedAUC());
		measurement[3] = new Measurement("EWMAUC", this.aucEstimator.getEqualWeightedAUC());
		measurement[4] = new Measurement("Accuracy", this.aucEstimator.getAccuracy());
		measurement[5] = new Measurement("Kappa", this.aucEstimator.getKappa());
		measurement[6] = new Measurement("G-Mean", this.aucEstimator.getGmean());
		measurement[7] = new Measurement("Classes", numClasses);

		for(int i = 0; i < numClasses; i++) {
			for(int j = 0; j < numClasses; j++) {
				measurement[8 + i*numClasses + j] = new Measurement("CM["+i+"]["+j+"]", this.aucEstimator.confusionMatrix[i][j]);
			}
		}

		return measurement;
	}

	@Override
	public void getDescription(StringBuilder sb, int indent) {
		Measurement.getMeasurementsDescription(getPerformanceMeasurements(),
				sb, indent);
	}

	@Override
	public void prepareForUseImpl(TaskMonitor monitor,
			ObjectRepository repository) {
	}

	public Estimator getAucEstimator() {
		return aucEstimator;
	}


	@Override
	public void reset() {
		reset(this.numClasses);
	}


	@Override
	public void addResult(Example<Instance> example, double[] classVotes) {
		addResult(example.getData(), classVotes);
	}

	@Override
	public void addResult(Example<Instance> testInst, Prediction prediction) {
		throw new RuntimeException("Designed for scoring classifiers");
	}
}