package moa.streams.generators.imbalanced;

import java.util.Random;

import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstancesHeader;

import moa.core.Example;
import moa.core.ObjectRepository;
import moa.options.AbstractOptionHandler;
import moa.options.ClassOption;
import moa.streams.InstanceStream;
import moa.tasks.TaskMonitor;

/**
 * Binary class imbalanced generator with static imbalance ratio through probabilistic distribution
 * 
 * @author Alberto Cano
 */

public class BinaryImbalancedGenerator extends AbstractOptionHandler implements InstanceStream {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String getPurposeString() {
		return "Generates a static imbalance ratio distribution from a provided stream generator.";
	}
	
	public IntOption seedOption = new IntOption("seed", 's',
			"Seed for random generation of classes distribution.", 1);
	
	public FloatOption imbalanceRatioOption = new FloatOption("imbalanceRatio", 'i',
			"Percentage of minority class examples (default 10%, i.e. IR 10)", 0.1, 0, 1);
	
	public ClassOption generatorOption = new ClassOption("generator", 'g',
			"Class of the stream generator", InstanceStream.class, "moa.streams.generators.AgrawalGenerator");
	
	protected InstanceStream generator;
	
	protected Random instanceRandom;
	
	@Override
	public InstancesHeader getHeader() {
		return generator.getHeader();
	}

	@Override
	public long estimatedRemainingInstances() {
		return generator.estimatedRemainingInstances();
	}

	@Override
	public boolean hasMoreInstances() {
		return generator.hasMoreInstances();
	}

	@Override
	public Example<Instance> nextInstance() {
		
		int expectedClass = instanceRandom.nextFloat() < imbalanceRatioOption.getValue() ? 1 : 0;
		Example<Instance> instance = null;
		
		do {
			instance = generator.nextInstance();
		} while(instance.getData().classValue() != expectedClass);
		
		return instance; 
	}

	@Override
	public boolean isRestartable() {
		return generator.isRestartable();
	}

	@Override
	public void restart() {
		instanceRandom = new Random(seedOption.getValue());
		generator.restart();
	}

	@Override
	public void getDescription(StringBuilder sb, int indent) {
	}

	@Override
	protected void prepareForUseImpl(TaskMonitor monitor, ObjectRepository repository) {
		generator = (InstanceStream) getPreparedClassOption(this.generatorOption);
		restart();
	}
}