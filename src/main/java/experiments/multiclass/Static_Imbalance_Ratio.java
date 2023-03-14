package experiments.multiclass;

import utils.Utils;

public class Static_Imbalance_Ratio {

	public static void main(String[] args) throws Exception {

		// 5 classes: distribution 50, 20, 10, 5, 1

		String[] generators = new String[] {
				"moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 5 -k 2 -t 0.0)",
				"moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomRBFGenerator -i 1 -r 1 -a 10 -c 5)",
				"moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.581395;0.232558;0.116279;0.058139;0.011629 -s (moa.streams.generators.RandomTreeGenerator -i 1 -r 1 -o 5 -u 5 -c 5)",
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

		String[] generatorsFilename = new String[generators.length];
		String[] algorithmsFilename = new String[algorithms.length];

		for(int gen = 0; gen < generators.length; gen++)
			generatorsFilename[gen] = generators[gen].replaceAll(" ", "").replaceAll("0.581395;0.232558;0.116279;0.058139;0.011629", "5_50-20-10-5-1").replaceAll("moa.streams.generators.imbalanced.MultiClassImbalancedGenerator", "ImbalancedGenerator").replaceAll("moa.streams.generators.imbalanced.", "").replaceAll("moa.streams.generators.", "").replaceAll("[()]", "");

		for(int alg = 0; alg < algorithms.length; alg++)
			algorithmsFilename[alg] = algorithms[alg].replaceAll(" ", "").replaceAll("moa.classifiers.meta.imbalanced.", "").replaceAll("moa.classifiers.meta.", "").replaceAll("moa.classifiers.trees.", "").replaceAll("moa.classifiers.ann.meta.", "").replaceAll("moa.classifiers.active.", "").replaceAll("[()]", "");

		int seed = 123456789;
		
		String resultsPath = "results/multiclass/static_IR/" + seed;
		
		// Executables
		System.out.println("===== Executables =====");
		for(int gen = 0; gen < generators.length; gen++) {
			for(int alg = 0; alg < algorithms.length; alg++) {
				String VMargs = "-Xms8g -Xmx128g -XX:ParallelGCThreads=12";
				String jarFile = "target/imbalanced-streams-1.0-jar-with-dependencies.jar";

				System.out.println("java " + VMargs + " -javaagent:sizeofag-1.0.4.jar -cp " + jarFile + " "
						+ "moa.DoTask EvaluateInterleavedTestThenTrain"
						+ " -e \"(MultiClassImbalancedPerformanceEvaluator -w 500)\""
						+ " -s \"(" + generators[gen] + ")\"" 
						+ " -l \"(" + algorithms[alg] + ")\""
						+ " -i 200000 -f 500" + " -r " + seed
						+ " -d " + resultsPath + "/" + algorithmsFilename[alg] + "-" + generatorsFilename[gen] + ".csv");
			}
		}

		// Show metrics for results
		System.out.println("===== Results =====");
		
		Utils.metric("Kappa", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("PMAUC", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("WMAUC", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("EWMAUC", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("Accuracy", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("G-Mean", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		
		Utils.metric("evaluation time (cpu seconds)", "last", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("model cost (RAM-Hours)", "averaged", resultsPath, algorithmsFilename, generatorsFilename);	
	}
}