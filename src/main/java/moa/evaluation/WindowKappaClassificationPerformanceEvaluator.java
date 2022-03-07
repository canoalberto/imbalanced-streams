package moa.evaluation;

import moa.core.Example;
import moa.core.Measurement;
import moa.core.ObjectRepository;
import moa.core.Utils;
import moa.options.AbstractOptionHandler;

import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstanceImpl;
import com.yahoo.labs.samoa.instances.Prediction;

import moa.tasks.TaskMonitor;

/**
 * Kappa classification evaluator that updates evaluation results using a sliding window.
 * 
 * @author Alberto Cano
 */
public class WindowKappaClassificationPerformanceEvaluator extends AbstractOptionHandler implements ClassificationPerformanceEvaluator {

	private static final long serialVersionUID = 1L;

	public IntOption widthOption = new IntOption("width", 'w', "Size of Window", 500);

	protected int confusionMatrix[][];

	protected int numClasses;

	protected int totalObservedInstances;

	protected int positionWindow;

	protected int windowClassActual[];

	protected int windowClassPredicted[];

	@Override
	public void reset() {
		reset(this.numClasses);
	}

	public void reset(int numClasses) {
		this.numClasses = numClasses;
		this.positionWindow = 0;
		this.totalObservedInstances = 0;
		this.confusionMatrix = new int[numClasses][numClasses];
		this.windowClassActual = new int[this.widthOption.getValue()];
		this.windowClassPredicted = new int[this.widthOption.getValue()];
	}

	@Override
	public void addResult(Example<Instance> exampleInstance, double[] classVotes) {
		InstanceImpl inst = (InstanceImpl)exampleInstance.getData();
		double weight = inst.weight();

		if (inst.classIsMissing() == false){
			int trueClass = (int) inst.classValue();
			int predictedClass = Utils.maxIndex(classVotes);

			if (weight > 0.0) {
				// // initialize evaluator
				if (totalObservedInstances == 0) {
					reset(inst.dataset().numClasses());
				}

				this.totalObservedInstances++;

				if(totalObservedInstances > this.widthOption.getValue())
				{
					this.confusionMatrix[windowClassPredicted[positionWindow]][windowClassActual[positionWindow]]--;
				}

				this.windowClassActual[positionWindow] = trueClass;
				this.windowClassPredicted[positionWindow] = predictedClass;
				this.confusionMatrix[predictedClass][trueClass]++;

				positionWindow = (positionWindow + 1) % this.widthOption.getValue();
			}
		}
	}

	protected double Kappa(int[][] confusionMatrix)
	{
		int correctedClassified = 0;
		int numberInstancesTotal = 0;
		int[] numberInstances = new int[numClasses];
		int[] predictedInstances = new int[numClasses];

		for(int i = 0; i < numClasses; i++)
		{
			correctedClassified += confusionMatrix[i][i];

			for(int j = 0; j < numClasses; j++)
			{
				numberInstances[i] += confusionMatrix[j][i];
				predictedInstances[i] += confusionMatrix[i][j];
			}

			numberInstancesTotal += numberInstances[i];
		}

		double mul = 0;

		for(int i = 0; i < numClasses; i++)
			mul += numberInstances[i] * predictedInstances[i];

		if(numberInstancesTotal*numberInstancesTotal - mul  != 0)
			return ((numberInstancesTotal * correctedClassified) - mul) / (double) ((numberInstancesTotal*numberInstancesTotal) - mul);
		else
			return 1.0;
	}

	protected double Accuracy(int[][] confusionMatrix)
	{
		int correctedClassified = 0;
		int numberInstancesTotal = 0;

		for(int i = 0; i < numClasses; i++)
		{
			correctedClassified += confusionMatrix[i][i];

			for(int j = 0; j < numClasses; j++)
				numberInstancesTotal += confusionMatrix[i][j];
		}

		return correctedClassified / (double) numberInstancesTotal;
	}

	public double getAccuracy() {
		return Accuracy(this.confusionMatrix);
	}
	
	public double getKappa() {
		return Kappa(this.confusionMatrix);
	}

	@Override
	public Measurement[] getPerformanceMeasurements() {

		Measurement[] measurement = new Measurement[3 + numClasses*numClasses];

		measurement[0] = new Measurement("Accuracy", Accuracy(this.confusionMatrix));
		measurement[1] = new Measurement("Kappa", Kappa(this.confusionMatrix));
		measurement[2] = new Measurement("Classes", numClasses);
		
		for(int i = 0; i < numClasses; i++) {
			for(int j = 0; j < numClasses; j++) {
				measurement[3 + i*numClasses + j] = new Measurement("CM["+i+"]["+j+"]", this.confusionMatrix[i][j]);
			}
		}

		return measurement;
	}

	@Override
	public void getDescription(StringBuilder sb, int indent) {
		Measurement.getMeasurementsDescription(getPerformanceMeasurements(), sb, indent);
	}

	@Override
	public void prepareForUseImpl(TaskMonitor monitor,
			ObjectRepository repository) {
	}

	@Override
	public void addResult(Example<Instance> arg0, Prediction arg1) {
		throw new RuntimeException("Designed for scoring classifiers");
	}
}