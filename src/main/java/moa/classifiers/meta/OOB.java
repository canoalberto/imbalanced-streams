
/**
 * Author: Leandro L. Minku (leandro.minku@leicester.ac.uk)
 * Implementation of Oversampling Online Bagging as in
 * WANG, S.; MINKU, L.L.; YAO, X. "Dealing with Multiple Classes in Online Class Imbalance Learning", 
 * Proceedings of the 25th International Joint Conference on Artificial Intelligence (IJCAI'16), July 2016
 * 
 * Please note that this was not the implementation used in the experiments done in the paper above.
 * However, it implements the algorithm proposed in that paper. So, it should reflect those results.
 * 
 */

// <---le19/01/18 modification to start class size as equal for all classes

package moa.classifiers.meta;

import com.github.javacliparser.FloatOption;
import com.yahoo.labs.samoa.instances.Instance;

import moa.core.Measurement;
import moa.core.MiscUtils;

public class OOB extends OzaBag {
	
	private static final long serialVersionUID = 1L;
	
	public FloatOption theta = new FloatOption("theta", 't',
            "The time decay factor for class size.", 0.9, 0, 1);
	
	protected double classSize[]; // time-decayed size of each class

    @Override
    public String getPurposeString() {
        return "Oversampling on-line bagging of Wang et al IJCAI 2016.";
    }
	
	public OOB() {
		super();
		classSize = null;
	}
	
	@Override
    public void trainOnInstanceImpl(Instance inst) {
		
		updateClassSize(inst);
		double lambda = calculatePoissonLambda(inst);
		
		for (int i = 0; i < this.ensemble.length; i++) {
            int k = MiscUtils.poisson(lambda, this.classifierRandom);
            if (k > 0) {
                Instance weightedInst = (Instance) inst.copy();
                weightedInst.setWeight(inst.weight() * k);
                this.ensemble[i].trainOnInstance(weightedInst);
            }
        }
    }
	
	protected void updateClassSize(Instance inst) {
		if (this.classSize == null) {
			classSize = new double[inst.numClasses()];

			// <---le19/01/18 modification to start class size as equal for all classes
			for (int i=0; i<classSize.length; ++i) {
				classSize[i] = 1d/classSize.length;
			}
		}
		
		for (int i=0; i<classSize.length; ++i) {
			classSize[i] = theta.getValue() * classSize[i] + (1d - theta.getValue()) * ((int) inst.classValue() == i ? 1d:0d); 
		}
	}
	
	// classInstance is the class corresponding to the instance for which we want to calculate lambda
	// will result in an error if classSize is not initialised yet
	public double calculatePoissonLambda(Instance inst) {
		double lambda = 1d;
		int majClass = getMajorityClass();
		
		lambda = classSize[majClass] / classSize[(int) inst.classValue()];
		
		return lambda;
	}

	// will result in an error if classSize is not initialised yet
	public int getMajorityClass() {
		int indexMaj = 0;

		for (int i=1; i<classSize.length; ++i) {
			if (classSize[i] > classSize[indexMaj]) {
				indexMaj = i;
			}
		}
		return indexMaj;
	}
	
	// will result in an error if classSize is not initialised yet
	public int getMinorityClass() {
		int indexMin = 0;

		for (int i=1; i<classSize.length; ++i) {
			if (classSize[i] <= classSize[indexMin]) {
				indexMin = i;
			}
		}
		return indexMin;
	}
	
	// will result in an error if classSize is not initialised yet
	@Override
    protected Measurement[] getModelMeasurementsImpl() {
		Measurement [] measure = super.getModelMeasurementsImpl();
		Measurement [] measurePlus = null;
		
		if (classSize != null) {
			measurePlus = new Measurement[measure.length + classSize.length];
			for (int i=0; i<measure.length; ++i) {
				measurePlus[i] = measure[i];
			}

			for (int i=0; i<classSize.length; ++i) {
				String str = "size of class " + i;
				measurePlus[measure.length+i] = new Measurement(str,classSize[i]);
			}
		} else
			measurePlus = measure;
		
		return measurePlus;
    }
}



