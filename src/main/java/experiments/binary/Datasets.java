package experiments.binary;

import utils.Utils;

public class Datasets {

	public static void main(String[] args) throws Exception {

		String[] datasets = new String[] {
				"adult",
				"amazon-employee",
				"amazon",
				"census",
				"coil2000",
				"covtypeNorm-1-2vsAll",
				"creditcard",
				"Elec",
				"GMSC",
				"internet_ads",
				"KDDCup",
				"nomao",
				"PAKDD",
				"poker-lsn-1-2vsAll",
				"tripadvisor",
				"twitter",
				"SPAM",
				"WEATHER",
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

		String[] algorithmsFilename = new String[algorithms.length];

		for(int alg = 0; alg < algorithms.length; alg++)
			algorithmsFilename[alg] = algorithms[alg].replaceAll(" ", "").replaceAll("moa.classifiers.meta.imbalanced.", "").replaceAll("moa.classifiers.meta.", "").replaceAll("moa.classifiers.trees.", "").replaceAll("moa.classifiers.ann.meta.", "").replaceAll("moa.classifiers.active.", "").replaceAll("[()]", "");

		String resultsPath = "D:/DataStreams-Imbalanced/datasets-binary/";

		// Executables
		System.out.println("===== Executables =====");
		for(int gen = 0; gen < datasets.length; gen++) {
			for(int alg = 0; alg < algorithms.length; alg++) {
				String VMargs = "-Xms8g -Xmx1024g";
				String jarFile = "target/imbalanced-streams-1.0-jar-with-dependencies.jar";

				System.out.println("java " + VMargs + " -javaagent:sizeofag-1.0.4.jar -cp " + jarFile + " "
						+ "moa.DoTask EvaluateInterleavedTestThenTrain"
						+ " -e \"(ImbalancedPerformanceEvaluator -w 500)\""
						+ " -s \"(ArffFileStream -f arff-datasets-binary/" + datasets[gen] + ".arff)\"" 
						+ " -l \"(" + algorithms[alg] + ")\""
						+ " -i 200000 -f 500"
						+ " -d " + resultsPath + algorithmsFilename[alg] + "-" + datasets[gen] + ".csv");
			}
		}

		// Show metrics for results
		System.out.println("===== Results =====");
		
		Utils.metric("Kappa", "averaged", resultsPath, algorithmsFilename, datasets);
		Utils.metric("G-Mean", "averaged", resultsPath, algorithmsFilename, datasets);
		Utils.metric("AUC", "averaged", resultsPath, algorithmsFilename, datasets);
		
		Utils.metric("evaluation time (cpu seconds)", "last", resultsPath, algorithmsFilename, datasets);
		Utils.metric("model cost (RAM-Hours)", "averaged", resultsPath, algorithmsFilename, datasets);	
	}
}