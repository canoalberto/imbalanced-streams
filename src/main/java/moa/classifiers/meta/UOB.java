/**
 * Author: Leandro L. Minku (leandro.minku@leicester.ac.uk)
 * Implementation of Undersampling Online Bagging as in
 * WANG, S.; MINKU, L.L.; YAO, X. "Dealing with Multiple Classes in Online Class Imbalance Learning", 
 * Proceedings of the 25th International Joint Conference on Artificial Intelligence (IJCAI'16), July 2016
 * 
 * Please note that this was not the implementation used in the experiments done in the paper above.
 * However, it implements the algorithm proposed in that paper. So, it should reflect those results.
 * 
 */

package moa.classifiers.meta;

import com.yahoo.labs.samoa.instances.Instance;

public class UOB extends OOB {

	@Override
	public String getPurposeString() {
		return "Undersampling on-line bagging of Wang et al IJCAI 2016.";
	}
	
	public UOB() {
		super();
	}
	
	// classInstance is the class corresponding to the instance for which we want to calculate lambda
	// will result in an error if classSize is not initialised yet
	@Override
	public double calculatePoissonLambda(Instance inst) {
		double lambda = 1d;
		int minClass = getMinorityClass();
		
		lambda = classSize[minClass] / classSize[(int) inst.classValue()];
		
		return lambda;
	}
	
	

}
