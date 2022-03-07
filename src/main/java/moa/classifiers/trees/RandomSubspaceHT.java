package moa.classifiers.trees;

import java.util.ArrayList;

import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Instances;

/**
 * Hoeffding Tree on a fixed random subspace of features
 * 
 * @author Alberto Cano
 */
public class RandomSubspaceHT extends HoeffdingTree {

	private static final long serialVersionUID = 1L;

	public IntOption subspaceSizeOption = new IntOption("subspaceFeaturesSize", 'k', "Number of features", 1, 1, Integer.MAX_VALUE);

	public boolean[] listAttributes;

	public Instances instanceHeader;
	
	public void setup(boolean[] listAttibutes, Instances instanceHeader) {
		this.listAttributes = listAttibutes;
		this.instanceHeader = instanceHeader;
	}

	@Override
	public String getPurposeString() {
		return "ROSE Hoeffding Tree for data streams";
	}

	@Override
	public void resetLearningImpl() {
		super.resetLearningImpl();
	}

	@Override
	public void trainOnInstanceImpl(Instance instance) {
		if(this.listAttributes == null) {
			setupListAttributes(instance);
		}

		Instance instanceProjected = instance.copy();

		for(int att = instanceProjected.numAttributes()-2; att >= 0; att--) {
			if(this.listAttributes[att] == false) {
				instanceProjected.deleteAttributeAt(att);	
			}
		}

		instanceProjected.setDataset(instanceHeader);

		super.trainOnInstanceImpl(instanceProjected);
	}

	@Override
	public double[] getVotesForInstance(Instance instance) {
		if(this.listAttributes == null) {
			setupListAttributes(instance);
		}

		Instance instanceProjected = instance.copy();

		for(int att = instanceProjected.numAttributes()-2; att >= 0; att--) {
			if(this.listAttributes[att] == false) {
				instanceProjected.deleteAttributeAt(att);	
			}
		}

		instanceProjected.setDataset(instanceHeader);

		return super.getVotesForInstance(instanceProjected);
	}

	@Override
	public boolean isRandomizable() {
		return true;
	}

	protected void setupListAttributes(Instance instance) {
		int numberAttributes = instance.numAttributes() - 1;
		int subspaceSize = subspaceSizeOption.getValue();

		this.listAttributes = new boolean[numberAttributes];
		this.instanceHeader = new Instances(instance.dataset());

		ArrayList<Integer> attributesPool = new ArrayList<Integer>();

		for(int i = 0; i < numberAttributes; i++) {
			attributesPool.add(i);
		}

		for(int i = 0; i < subspaceSize; i++) {
			this.listAttributes[attributesPool.remove(this.classifierRandom.nextInt(attributesPool.size()))] = true;
		}

		for(int att = numberAttributes-1; att >= 0; att--) {
			if(this.listAttributes[att] == false) {
				this.instanceHeader.deleteAttributeAt(att);	
			}
		}
	}
}