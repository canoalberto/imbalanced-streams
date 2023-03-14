package moa.classifiers.meta;

import java.util.ArrayList;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Prediction;

import moa.core.Example;
import moa.core.Measurement;
import moa.core.ObjectRepository;
import moa.core.Utils;
import moa.evaluation.BasicClassificationPerformanceEvaluator;
import moa.evaluation.LearningPerformanceEvaluator;
import moa.options.AbstractOptionHandler;
import moa.tasks.TaskMonitor;

public class CSARFImbalancedClassificationPerformanceEvaluator extends AbstractOptionHandler
	implements LearningPerformanceEvaluator<Example<Instance>>  {

		private BasicClassificationPerformanceEvaluator basicEvaluator  = new BasicClassificationPerformanceEvaluator();

		private int numClasses                  = -1;
		private int confusionMatrix[][]         = null; // predicted as -> ground-truth
		private boolean multiClass = false;
		private double instancesSeen = 0.0;

		/**
		 * Resets this evaluator. It must be similar to
		 * starting a new evaluator from scratch.
		 */
		@Override
		public void reset() {
			/**
			 * Resets and prepares the basic evaluator.
			 */
			basicEvaluator = new BasicClassificationPerformanceEvaluator();
			basicEvaluator.f1PerClassOption.set();
			basicEvaluator.precisionPerClassOption.set();
			basicEvaluator.recallPerClassOption.set();
			basicEvaluator.precisionRecallOutputOption.set();
			basicEvaluator.prepareForUse();

			/**
			 * Resets the confusion matrix.
			 */
			confusionMatrix = null;
			numClasses      = -1;
		}

		/**
		 * Adds a learning result to this evaluator.
		 *
		 * @param testInst
		 * @param prediction
		 * @return an array of measurements monitored in this evaluator
		 */
		@Override
		public void addResult(Example<Instance> testInst, Prediction prediction) {
			this.addResult(testInst, prediction.getVotes());
		}

		@Override
		public void addResult(Example<Instance> example, double[] classVotes) {
			if(numClasses < 1){
				numClasses = example.getData().numClasses();
				confusionMatrix = new int[numClasses][numClasses];
				if(numClasses > 2){multiClass = true;}
			}
			basicEvaluator.addResult(example, classVotes);

			// updates the confusion matrix
			int pred = Utils.maxIndex(classVotes);
			int gt   = (int) Math.ceil(example.getData().classValue());
			confusionMatrix[pred][gt] += example.weight();
			//    tp+= s.isPositive && predictedClass == 1 ? 1:0;
			//    fp+= !s.isPositive && predictedClass == 1 ? 1:0;
			//    fn+= s.isPositive && predictedClass == 0 ? 1:0;
			instancesSeen++;
		}

		/**
		 * Gets the current measurements monitored by this evaluator.
		 *
		 * @return an array of measurements monitored by this evaluator
		 */
		@Override
		public Measurement[] getPerformanceMeasurements() {
			ArrayList<Measurement> measures = new ArrayList<Measurement>();
			/**
			 * Gets the basic metrics.
			 */
			Measurement mBasic[]  = basicEvaluator.getPerformanceMeasurements();
			for(Measurement m : mBasic) measures.add(new Measurement(m.getName(), m.getValue()));

			// Appends extra metrics, such as TP, TN, FP, FN, FPR and so forth
			// iterate over all metrics to calculate the metrics per class
			for (int i = 0; i < numClasses; i++) {
				/**
				 * adds the confusion matrix per class
				 */
				measures.addAll(getRates(i));
			}
			double avgFpr = 0.0;
			for (int i = 0; i < numClasses; i++) {
				/**
				 * The FPR.
				 */
				double fprClass = falsePositiveRate(i);
				measures.add(new Measurement("FPR class " + i, fprClass));
				avgFpr += fprClass;
			}
			measures.add(new Measurement("Avg. FPR", avgFpr / numClasses));

			measures.add(new Measurement("MCC", getMCC()));
			measures.add(new Measurement("MCC Multclass", getMCCMultiClass()));
			measures.add(new Measurement("MCC Multclass SK Learn", getMCCMultiClassSKLearner()));
			/**
			 * Creates final array with results
			 */
			Measurement[] result = new Measurement[measures.size()];
			int index = 0;
			for(Measurement m : measures){
				result[index] = m;
				index++;
			}
			return result;
		}

		public double getF1Statistic(){
			return this.basicEvaluator.getF1Statistic();
		}

		@Override
		protected void prepareForUseImpl(TaskMonitor monitor, ObjectRepository repository) {
			reset();
		}

		@Override
		public void getDescription(StringBuilder sb, int indent) {
			sb.append("Combines multiple evaluators to facilitate the comparison of different metrics.");
		}

		/**
		 * Returns an array with the TP, TN, FP, FN values for a positive class <b>classIndex</b>.
		 *
		 * @param classIndex
		 * @return an array with {TP, TN, FP, FN} format.
		 */
		public ArrayList<Measurement> getRates(int classIndex){
			ArrayList<Measurement> arr = new ArrayList<>();
			double tp = 0.0;
			double tn = 0.0;
			double fp = 0.0;
			double fn = 0.0;
			for(int i = 0; i < numClasses; i++){
				for(int j = 0; j < numClasses; j++){
					// TP = (i == j == classIndex)
					// TN = ((i == j) != classIndex)
					// FP = i == classIndex && j != i
					// FN = i != classIndex && j == classIndex
					double v = confusionMatrix[i][j];
					if(i == j && i == classIndex)               tp += v;
					else if(i == j && j != classIndex)          tn += v;
					else if(i == classIndex && i != j)          fp += v;
					else if(i != classIndex && j == classIndex) fn += v;
				}
			}
			arr.add(new Measurement("TP class " + classIndex, tp));
			arr.add(new Measurement("TN class " + classIndex, tn));
			arr.add(new Measurement("FP class " + classIndex, fp));
			arr.add(new Measurement("FN class " + classIndex, fn));
			return arr;
		}
		//TODO: Verificar se nao tem algo mais correto
		public double[] BETAgetRates(int classIndex){
			double[] resp = new double[4];
			double tp = 0.0;
			double tn = 0.0;
			double fp = 0.0;
			double fn = 0.0;
			for(int i = 0; i < numClasses; i++){
				for(int j = 0; j < numClasses; j++){
					double v = confusionMatrix[i][j];
					if(i == j && i == classIndex)               tp += v;
					else if(i == j && j != classIndex)          tn += v;
					else if(i == classIndex && i != j)          fp += v;
					else if(i != classIndex && j == classIndex) fn += v;
				}
			}
			resp[0] = tp;
			resp[1] = tn;
			resp[2] = fp;
			resp[3] = fn;
			return resp;
		}

		public double getMCCMultiClass(){
			double numerador = 0;
			for (int k = 0; k < numClasses; k++) {
				for (int l = 0; l < numClasses; l++) {
					for (int m = 0; m < numClasses; m++) {
						numerador+= confusionMatrix[k][k]*confusionMatrix[m][l] + confusionMatrix[l][k]*confusionMatrix[k][m];
					}
				}
			}
			if (numerador == 0.0) return -1.0;

			double denominador_one = 0.0;
			double denominador_two = 0.0;
			for (int k = 0; k < numClasses; k++) {
				double sum_one = 0.0;
				for (int l = 0; l < numClasses; l++) {
					sum_one+=confusionMatrix[l][k];
				}
				double sum_two = 0.0;
				for (int f = 0; f < numClasses; f++) {
					for (int g = 0; g < numClasses; g++) {
						if(f!=k){
							sum_two+=confusionMatrix[g][f];
						}
					}
				}
				denominador_one+= sum_one*sum_two;
			}
			denominador_one = Math.sqrt(denominador_one);
			if(denominador_one == 0.0) return 0.0;

			for (int k = 0; k < numClasses; k++) {
				double sum_one = 0.0;
				for (int l = 0; l < numClasses; l++) {
					sum_one+=confusionMatrix[k][l];
				}
				double sum_two = 0.0;
				for (int f = 0; f < numClasses; f++) {
					for (int g = 0; g < numClasses; g++) {
						if(f != k) sum_two+=confusionMatrix[f][g];
					}
				}
				denominador_two+= sum_one*sum_two;
			}
			denominador_two = Math.sqrt(denominador_two);
			if(denominador_two == 0.0) return 0.0;
			return  numerador/(denominador_one*denominador_two);
		}


		// https://scikit-learn.org/stable/modules/model_evaluation.html
		public double getMCCMultiClassSKLearner(){
			double correctlyPredictitedSamples = 0.0;
			double totalNumberOfSamples = instancesSeen;
			for (int i = 0; i < numClasses; i++) {
				correctlyPredictitedSamples+= confusionMatrix[i][i];
			}

			double numerador = 0.0;
			double sum_pk = 0.0;
			double sum_tk = 0.0;
			for (int k = 0; k < numClasses; k++) {
				double pk = 0;
				double tk = 0;
				for (int i = 0; i < numClasses; i++) {
					pk += confusionMatrix[k][i];
					tk += confusionMatrix[i][k];
				}
				numerador += pk * tk;

				sum_pk += Math.pow(pk, 2);
				sum_tk += Math.pow(tk, 2);
			}
			numerador = correctlyPredictitedSamples * totalNumberOfSamples - numerador;
			if (numerador == 0.0) return -999.0;

			double denominator = (Math.pow(totalNumberOfSamples, 2) - sum_pk) * (Math.pow(totalNumberOfSamples, 2) - sum_tk);
			denominator = Math.sqrt(denominator);
			if(denominator == 0.0) return 0.0;

			return  numerador/denominator;
		}

		/**
		 * Computes the FPR such that classIndex is the class to be considered as <b>positive</b>.
		 * @param classIndex the index of the positive class
		 * @return the FPR
		 */
		public double falsePositiveRate(int classIndex) {
			double incorrect = 0, total = 0;
			for (int i = 0; i < numClasses; i++) {
				if (i != classIndex) {
					for (int j = 0; j < numClasses; j++) {
						if (j == classIndex) {
							incorrect += confusionMatrix[i][j];
						}
						total += confusionMatrix[i][j];
					}
				}
			}
			return incorrect / total;
		}

		public double getMCC(){
			if(numClasses > 2){ return getMCCMultiClassSKLearner();}
			double[] rates = this.BETAgetRates(0);
			double tp=rates[0];
			double tn=rates[1];
			double fp=rates[2];
			double fn=rates[3];
			double n = tp * tn - fp * fn;
			double d = Math.sqrt((tp+fp)*(tp+fn)*(tn+fp)*(tn+fn));
			return n/d;
		}

		public double getMCCstats(){
			double ntotal = 0;
			for (int i = 0; i < numClasses; i++) {
				double[] rates = this.BETAgetRates(i);
				double tp=rates[0];
				double tn=rates[1];
				double fp=rates[2];
				double fn=rates[3];
				double n = tp * tn - fp * fn;
				double d = Math.sqrt((tp+fp)*(tp+fn)*(tn+fp)*(tn+fn));
				ntotal += n/d;
			}
			ntotal = ntotal/numClasses;
			return ntotal;
		}
	}