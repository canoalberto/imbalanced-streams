package moa.classifiers.meta;

import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Instances;

import moa.classifiers.AbstractClassifier;
import moa.classifiers.Classifier;
import moa.classifiers.MultiClassClassifier;
import moa.core.DoubleVector;
import moa.core.Measurement;
import moa.core.MiscUtils;
import moa.core.ObjectRepository;
import moa.options.ClassOption;
import moa.tasks.TaskMonitor;

/**
* Kappa Updated Ensemble for drifting data stream mining
* 
* @author Alberto Cano (acano@vcu.edu)
*/

public class KUE extends AbstractClassifier implements MultiClassClassifier {

	private static final long serialVersionUID = 1L;

	/**
	 * Type of classifier to use as a component classifier.
	 */
	public ClassOption learnerOption = new ClassOption("learner", 'l', "Classifier to train.", Classifier.class, "trees.HoeffdingTree -e 2000000 -g 100 -c 0.01");

	/**
	 * Number of component classifiers.
	 */
	public IntOption memberCountOption = new IntOption("memberCount", 'n', "The maximum number of classifiers in an ensemble.", 10, 1, Integer.MAX_VALUE);

	/**
	 * Number of new component classifiers.
	 */
	public IntOption newmemberCountOption = new IntOption("newmemberCount", 'g', "The new number of classifiers generated.", 1, 1, Integer.MAX_VALUE);

	/**
	 * Chunk size.
	 */
	public IntOption chunkSizeOption = new IntOption("chunkSize", 'c', "The chunk size used for classifier creation and evaluation.", 1000, 1, Integer.MAX_VALUE);

	/**
	 * Ensemble classifiers.
	 */
	protected Classifier[] learners;

	/**
	 * Candidate classifier.
	 */
	protected Classifier candidate;

	/**
	 * Current chunk of instances.
	 */
	protected Instances currentChunk;

	private int numberComponentsReplaced; 
	
	private int numberAbstentions;

	private double[] kappa;

	private boolean[][] useAttribute;

	@Override
	public void prepareForUseImpl(TaskMonitor monitor, ObjectRepository repository) {
		this.candidate = (Classifier) getPreparedClassOption(this.learnerOption);
		this.candidate.resetLearning();

		super.prepareForUseImpl(monitor, repository);
	}

	@Override
	public void resetLearningImpl() {
		this.currentChunk = null;
		this.learners = new Classifier[this.memberCountOption.getValue()];
		this.candidate = (Classifier) getPreparedClassOption(this.learnerOption);
		this.candidate.resetLearning();
	}

	@Override
	public void trainOnInstanceImpl(Instance inst) {

		if (this.currentChunk == null)
			this.currentChunk = new Instances(this.getModelContext());

		if(useAttribute == null)
		{
			int numAtts = this.getModelContext().numAttributes() - 1;
			int numClassifiers = this.memberCountOption.getValue();

			useAttribute = new boolean[numClassifiers][numAtts];

			for(int i = 0; i < numClassifiers; i++)
			{
				int used = 0;
				int numAttsSelected = 1 + this.classifierRandom.nextInt(numAtts);

				while(used < numAttsSelected)
				{
					int index = this.classifierRandom.nextInt(numAtts);

					if(useAttribute[i][index] == false)
					{
						useAttribute[i][index] = true;
						used++;
					}
				}
			}
		}

		this.currentChunk.add(inst);

		if (this.currentChunk.size() % this.chunkSizeOption.getValue() == 0) {
			this.processChunk();
		}
	}

	/**
	 * Determines whether the classifier is randomizable.
	 */
	public boolean isRandomizable() {
		return true;
	}

	/**
	 * Predicts a class for an example.
	 */
	public double[] getVotesForInstance(Instance inst) {
		DoubleVector combinedVote = new DoubleVector();

		if (kappa != null)
		{
			for (int i = 0; i < this.learners.length; i++)
			{
				if(kappa[i] < 0) continue;

				Instance copy = inst.copy();

				for(int j = 0; j < useAttribute[i].length; j++)
					if(useAttribute[i][j] == false)
						copy.setValue(j, 0);

				DoubleVector vote = new DoubleVector(this.learners[i].getVotesForInstance(copy));

				if (vote.sumOfValues() > 0.0) {
					vote.normalize();
					vote.scaleValues(kappa[i]);
					combinedVote.addValues(vote);
				}
			}
		}

		return combinedVote.getArrayRef();
	}

	@Override
	public void getModelDescription(StringBuilder out, int indent) {
	}

	@Override
	public Classifier[] getSubClassifiers() {
		return this.learners.clone();
	}

	/**
	 * Processes a chunk of instances.
	 * This method is called after collecting a chunk of examples.
	 */
	protected void processChunk() {

		numberAbstentions = 0;
		
		if (this.learners[0] == null)
		{
			for (int i = 0; i < this.learners.length; i++)
			{
				this.learners[i] = this.candidate.copy();
				this.trainOnChunk(this.learners[i], useAttribute[i]);
			}

			computeKappa();
		}
		else
		{
			for (int i = 0; i < this.learners.length; i++)
			{
				this.trainOnChunk(this.learners[i], useAttribute[i]);
			}

			computeKappa();

			int numAtts = this.getModelContext().numAttributes() - 1;

			numberComponentsReplaced = 0;

			for(int i = 0; i < newmemberCountOption.getValue(); i++)
			{
				int poorestClassifier = this.getPoorestClassifierIndex();
				Classifier addedClassifier = this.candidate.copy();

				int used = 0;
				int numAttsSelected = 1 + this.classifierRandom.nextInt(numAtts);
				boolean[] newAttributeArray = new boolean[numAtts];

				while(used < numAttsSelected)
				{
					int index = this.classifierRandom.nextInt(numAtts);

					if(newAttributeArray[index] == false)
					{
						newAttributeArray[index] = true;
						used++;
					}
				}

				this.trainOnChunk(addedClassifier, newAttributeArray);

				double newKappa = computeKappa(addedClassifier);

				if(newKappa > kappa[poorestClassifier])
				{
					this.learners[poorestClassifier] = addedClassifier;
					kappa[poorestClassifier] = newKappa;
					useAttribute[poorestClassifier] = newAttributeArray;
					numberComponentsReplaced++;
				}
			}
		}
		
		for (int i = 0; i < this.learners.length; i++)
			if(kappa[i] < 0)
				numberAbstentions++;

		this.currentChunk = null;
		this.candidate = (Classifier) getPreparedClassOption(this.learnerOption);
		this.candidate.resetLearning();
	}

	private void computeKappa() {
		kappa = new double[this.learners.length];

		for (int i = 0; i < this.learners.length; i++)
		{
			int[][] confusionMatrix = new int[modelContext.numClasses()][modelContext.numClasses()];

			for(int j = 0; j < currentChunk.size(); j++)
			{
				int predicted = maxIndex(this.learners[i].getVotesForInstance(currentChunk.instance(j)));
				int actual = (int) currentChunk.instance(j).classValue();
				confusionMatrix[predicted][actual]++;
			}

			kappa[i] = Kappa(confusionMatrix);
		}
	}

	private double computeKappa(Classifier addedClassifier) {
		int[][] confusionMatrix = new int[modelContext.numClasses()][modelContext.numClasses()];

		for(int j = 0; j < currentChunk.size(); j++)
		{
			int predicted = maxIndex(addedClassifier.getVotesForInstance(currentChunk.instance(j)));
			int actual = (int) currentChunk.instance(j).classValue();
			confusionMatrix[predicted][actual]++;
		}

		return Kappa(confusionMatrix);
	}

	/**
	 * Adds ensemble weights to the measurements.
	 */
	@Override
	protected Measurement[] getModelMeasurementsImpl() {
		Measurement[] measurements = new Measurement[2];
		measurements[0] = new Measurement("components replaced", numberComponentsReplaced);
		measurements[1] = new Measurement("number abstentions", numberAbstentions);
		return measurements;
	}

	/**
	 * Adds a classifier to the storage.
	 * 
	 * @param newClassifier
	 *            The classifier to add.
	 */
	protected Classifier addToStored(Classifier newClassifier) {
		Classifier addedClassifier = null;
		Classifier[] newStored = new Classifier[this.learners.length + 1];

		for (int i = 0; i < newStored.length; i++) {
			if (i < this.learners.length) {
				newStored[i] = this.learners[i];
			} else {
				newStored[i] = addedClassifier = newClassifier.copy();
			}
		}

		this.learners = newStored;

		return addedClassifier;
	}

	/**
	 * Finds the index of the classifier with the smallest weight.
	 * @return
	 */
	private int getPoorestClassifierIndex() {
		return minIndex(kappa);
	}

	/**
	 * Trains a component classifier on the most recent chunk of data.
	 * 
	 * @param classifierToTrain
	 *            Classifier being trained.
	 */
	private void trainOnChunk(Classifier classifierToTrain, boolean[] useAttribute) {
		
		for (int num = 0; num < this.chunkSizeOption.getValue(); num++) {

			Instance copy = this.currentChunk.instance(num).copy();

			for(int i = 0; i < useAttribute.length; i++)
				if(useAttribute[i] == false)
					copy.setValue(i, 0);

			int k = MiscUtils.poisson(1.0, this.classifierRandom);
			if (k > 0) {
				copy.setWeight(copy.weight() * k);
				classifierToTrain.trainOnInstance(copy);
			}
		}
	}

	private int minIndex(double[] doubles) {

		double minimum = Double.MAX_VALUE;
		int minIndex = -1;

		for (int i = 0; i < doubles.length; i++) {
			if ((i == 0) || (doubles[i] < minimum)) {
				minIndex = i;
				minimum = doubles[i];
			}
		}

		return minIndex;
	}

	private int maxIndex(double[] doubles) {

		double maximum = -Double.MAX_VALUE;
		int maxIndex = -1;

		for (int i = 0; i < doubles.length; i++) {
			if ((i == 0) || (doubles[i] > maximum)) {
				maxIndex = i;
				maximum = doubles[i];
			}
		}

		return maxIndex;
	}

	private double Kappa(int[][] confusionMatrix)
	{
		int correctedClassified = 0;
		int numberInstancesTotal = 0;
		int[] numberInstances = new int[confusionMatrix.length];
		int[] predictedInstances = new int[confusionMatrix.length];

		for(int i = 0; i < confusionMatrix.length; i++)
		{
			correctedClassified += confusionMatrix[i][i];

			for(int j = 0; j < confusionMatrix.length; j++)
			{
				numberInstances[i] += confusionMatrix[i][j];
				predictedInstances[j] += confusionMatrix[i][j];
			}

			numberInstancesTotal += numberInstances[i];
		}

		double mul = 0;

		for(int i = 0; i < confusionMatrix.length; i++)
			mul += numberInstances[i] * predictedInstances[i];

		if(numberInstancesTotal*numberInstancesTotal - mul  != 0)
			return ((numberInstancesTotal * correctedClassified) - mul) / (double) ((numberInstancesTotal*numberInstancesTotal) - mul);
		else
			return 1.0;
	} 
}