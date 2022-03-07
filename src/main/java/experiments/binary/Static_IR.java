package experiments.binary;

import utils.Utils;

public class Static_IR {

	public static void main(String[] args) throws Exception {

		String[] generators = new String[] {
				// IR 100
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.0)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 1 -r 1 -a 10 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 1 -r 1 -o 5 -u 5 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.SineGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.TextGenerator -i 1 -a 100)",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u",

				// IR 50
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.0)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 1 -r 1 -a 10 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 1 -r 1 -o 5 -u 5 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.SineGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.TextGenerator -i 1 -a 100)",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u",

				// IR 20
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.0)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 1 -r 1 -a 10 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 1 -r 1 -o 5 -u 5 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.SineGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.TextGenerator -i 1 -a 100)",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u",

				// IR 10
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.10 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.10 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.10 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.0)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.10 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.10 -g (moa.streams.generators.RandomRBFGenerator -i 1 -r 1 -a 10 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.10 -g (moa.streams.generators.RandomTreeGenerator -i 1 -r 1 -o 5 -u 5 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.10 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.10 -g (moa.streams.generators.SineGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.10 -g (moa.streams.generators.TextGenerator -i 1 -a 100)",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u",

				// IR 5
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.20 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.20 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.20 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.0)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.20 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.20 -g (moa.streams.generators.RandomRBFGenerator -i 1 -r 1 -a 10 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.20 -g (moa.streams.generators.RandomTreeGenerator -i 1 -r 1 -o 5 -u 5 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.20 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.20 -g (moa.streams.generators.SineGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.20 -g (moa.streams.generators.TextGenerator -i 1 -a 100)",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u",

				// IR 1
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.0)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 1 -r 1 -a 10 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 1 -r 1 -o 5 -u 5 -c 2)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.SineGenerator -i 1 -f 1)",
				"moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.TextGenerator -i 1 -a 100)",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u",
		};

		String[] algorithms = new String[] {
				"moa.classifiers.meta.imbalanced.RebalanceStream -l (moa.classifiers.meta.TemporallyAugmentedClassifier)",
				"moa.classifiers.meta.imbalanced.CSMOTE -l (moa.classifiers.meta.AdaptiveRandomForest)",
				"moa.classifiers.meta.imbalanced.VFCSMOTE -l (moa.classifiers.meta.AdaptiveRandomForest)",
				"moa.classifiers.meta.CSARF",
				"moa.classifiers.trees.GHVFDT",
				"moa.classifiers.trees.HDVFDT",
				"moa.classifiers.meta.AdaptiveRandomForest",
				"moa.classifiers.meta.KUE",
				"moa.classifiers.meta.LeveragingBag",
				"moa.classifiers.meta.OzaBagAdwin",
				"moa.classifiers.meta.StreamingRandomPatches",
				"moa.classifiers.ann.meta.ESOS_ELM -c (OS_ELM -i 100 -h 70 -p) -e (WELM -h 70 -p)",
				"moa.classifiers.active.CALMID",
				"moa.classifiers.active.MicFoal",
				"moa.classifiers.meta.imbalanced.ROSE",														
				"moa.classifiers.meta.imbalanced.OnlineAdaBoost -l (moa.classifiers.meta.AdaptiveRandomForest)",
				"moa.classifiers.meta.imbalanced.OnlineAdaC2 -l (moa.classifiers.meta.AdaptiveRandomForest)",
				"moa.classifiers.meta.imbalanced.AdaptiveRandomForestResampling",
				"moa.classifiers.meta.imbalanced.SMOTEOB -l (moa.classifiers.meta.AdaptiveRandomForest)",
				"moa.classifiers.meta.imbalanced.OnlineSMOTEBagging -l (moa.classifiers.meta.AdaptiveRandomForest)",
				"moa.classifiers.meta.OOB",
				"moa.classifiers.meta.UOB",
				"moa.classifiers.meta.imbalanced.OnlineRUSBoost -l (moa.classifiers.meta.AdaptiveRandomForest)",
				"moa.classifiers.meta.imbalanced.OnlineUnderOverBagging -l (moa.classifiers.meta.AdaptiveRandomForest)",
		};

		String[] generatorsFilename = new String[generators.length];
		String[] algorithmsFilename = new String[algorithms.length];

		for(int gen = 0; gen < generators.length; gen++)
			generatorsFilename[gen] = generators[gen].replaceAll(" ", "").replaceAll("moa.streams.generators.imbalanced.", "").replaceAll("moa.streams.generators.", "").replaceAll("[()]", "");

		for(int alg = 0; alg < algorithms.length; alg++)
			algorithmsFilename[alg] = algorithms[alg].replaceAll(" ", "").replaceAll("moa.classifiers.meta.imbalanced.", "").replaceAll("moa.classifiers.meta.", "").replaceAll("moa.classifiers.trees.", "").replaceAll("moa.classifiers.ann.meta.", "").replaceAll("moa.classifiers.active.", "").replaceAll("[()]", "");


		System.out.println("===== Executables =====");
		for(int gen = 0; gen < generators.length; gen++) {
			for(int alg = 0; alg < algorithms.length; alg++) {
				String VMargs = "-Xms8g -Xmx1024g";

				String jarFile = "target/imbalanced-streams-1.0-jar-with-dependencies.jar";

				System.out.println("java " + VMargs + " -javaagent:sizeofag-1.0.4.jar -cp " + jarFile + " "
						+ "moa.DoTask EvaluateInterleavedTestThenTrain"
						+ " -e \"(ImbalancedPerformanceEvaluator -w 500)\""
						+ " -s \"(" + generators[gen] + ")\"" 
						+ " -l \"(" + algorithms[alg] + ")\""
						+ " -i 200000 -f 500"
						+ " -d results/static_IR/" + algorithmsFilename[alg] + "-" + generatorsFilename[gen] + ".csv");
			}
		}

		// Show metrics for results
		System.out.println("===== Results =====");
		String resultsPath =  "D:/DataStreams-Imbalanced/AUC/static_IR/";
		
		Utils.metric("Kappa", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("G-Mean", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("AUC", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		
		Utils.metric("evaluation time (cpu seconds)", "last", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("model cost (RAM-Hours)", "averaged", resultsPath, algorithmsFilename, generatorsFilename);	
	}
}