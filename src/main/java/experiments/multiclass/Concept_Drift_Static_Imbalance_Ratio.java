package experiments.multiclass;

import utils.Utils;

public class Concept_Drift_Static_Imbalance_Ratio {

	public static void main(String[] args) throws Exception {

		String[] generators = new String[] {
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 5 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 5 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 5 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 5 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 5 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 5 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 5 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 5 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 5 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 5 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 5 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 5 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 5 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 5 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 5 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 5 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",			  						 
		};
		
		String[] generatorsFilename = new String[] {
				"Sudden-HyperplaneGenerator",
				"Sudden-RandomRBFGenerator",
				"Sudden-RandomTreeGenerator",
				"Sudden-RBF-Tree-Hyperplane",
				
				"Gradual-HyperplaneGenerator",
				"Gradual-RandomRBFGenerator",
				"Gradual-RandomTreeGenerator",
				"Gradual-RBF-Tree-Hyperplane",
		};

		String[] algorithms = new String[] {
				"moa.classifiers.meta.CSARF",
				"moa.classifiers.trees.GHVFDT",
				"moa.classifiers.trees.HDVFDT",
				"moa.classifiers.meta.AdaptiveRandomForest",
				"moa.classifiers.meta.KUE",
				"moa.classifiers.meta.LeveragingBag",
				"moa.classifiers.meta.OzaBagAdwin",
				"moa.classifiers.meta.StreamingRandomPatches",
				"moa.classifiers.active.CALMID",
				"moa.classifiers.active.MicFoal",
				"moa.classifiers.meta.imbalanced.ROSE",
				"moa.classifiers.meta.imbalanced.OnlineAdaBoost -l (moa.classifiers.meta.AdaptiveRandomForest)",
				"moa.classifiers.meta.imbalanced.AdaptiveRandomForestResampling",
				"moa.classifiers.meta.OOB",
				"moa.classifiers.meta.UOB",
		};

		String[] algorithmsFilename = new String[algorithms.length];

		for(int alg = 0; alg < algorithms.length; alg++)
			algorithmsFilename[alg] = algorithms[alg].replaceAll(" ", "").replaceAll("moa.classifiers.meta.imbalanced.", "").replaceAll("moa.classifiers.meta.", "").replaceAll("moa.classifiers.trees.", "").replaceAll("moa.classifiers.ann.meta.", "").replaceAll("moa.classifiers.active.", "").replaceAll("[()]", "");

		String resultsPath = "D:/DataStreams-Imbalanced/multiclass/concept_drift_static_IR/";

		// Executables
		System.out.println("===== Executables =====");
		for(int gen = 0; gen < generators.length; gen++) {
			for(int alg = 0; alg < algorithms.length; alg++) {
				String VMargs = "-Xms8g -Xmx1024g";
				String jarFile = "target/imbalanced-streams-1.0-jar-with-dependencies.jar";

				System.out.println("java " + VMargs + " -javaagent:sizeofag-1.0.4.jar -cp " + jarFile + " "
						+ "moa.DoTask EvaluateInterleavedTestThenTrain"
						+ " -e \"(MultiClassImbalancedPerformanceEvaluator -w 500)\""
						+ " -s \"(" + generators[gen] + ")\"" 
						+ " -l \"(" + algorithms[alg] + ")\""
						+ " -i 200000 -f 500"
						+ " -d " + resultsPath + algorithmsFilename[alg] + "-" + generatorsFilename[gen] + ".csv");
			}
		}

		// Show metrics for results
		System.out.println("===== Results =====");
		
		Utils.metric("Kappa", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("PMAUC", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		
		Utils.metric("evaluation time (cpu seconds)", "last", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("model cost (RAM-Hours)", "averaged", resultsPath, algorithmsFilename, generatorsFilename);	
	}
}