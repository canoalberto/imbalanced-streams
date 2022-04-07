package experiments.multiclass;

import utils.Utils;

public class Datasets {

	public static void main(String[] args) throws Exception {
		
		// Download datasets from https://drive.google.com/file/d/19F7krux2PnhFJzM7lOwNOLT26NHnZXbo/view?usp=sharing

		String[] datasets = new String[] {
				"activity",
				"connect-4",
				"CovPokElec",
				"covtype",
				"fars",
				"hypothyroid",
				"kddcup",
				"kr-vs-k",
				"lymph",
				"poker",
				"shuttle",
				"thyroid",
				"zoo",
				"CRIMES",
				"GAS",
				"OLYMPIC",
				"SENSOR",
				"TAGS",
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

		String resultsPath = "D:/DataStreams-Imbalanced/multiclass/datasets/";

		// Executables
		System.out.println("===== Executables =====");
		for(int gen = 0; gen < datasets.length; gen++) {
			for(int alg = 0; alg < algorithms.length; alg++) {
				String VMargs = "-Xms8g -Xmx1024g";
				String jarFile = "target/imbalanced-streams-1.0-jar-with-dependencies.jar";

				System.out.println("java " + VMargs + " -javaagent:sizeofag-1.0.4.jar -cp " + jarFile + " "
						+ "moa.DoTask EvaluateInterleavedTestThenTrain"
						+ " -e \"(MultiClassImbalancedPerformanceEvaluator -w 500)\""
						+ " -s \"(ArffFileStream -f arff-datasets-multiclass/" + datasets[gen] + ".arff)\"" 
						+ " -l \"(" + algorithms[alg] + ")\""
						+ " -i 200000 -f 500"
						+ " -d " + resultsPath + algorithmsFilename[alg] + "-" + datasets[gen] + ".csv");
			}
		}

		// Show metrics for results
		System.out.println("===== Results =====");
		
		Utils.metric("Kappa", "averaged", resultsPath, algorithmsFilename, datasets);
		Utils.metric("PMAUC", "averaged", resultsPath, algorithmsFilename, datasets);
		Utils.metric("WMAUC", "averaged", resultsPath, algorithmsFilename, datasets);
		Utils.metric("EWMAUC", "averaged", resultsPath, algorithmsFilename, datasets);
		Utils.metric("Accuracy", "averaged", resultsPath, algorithmsFilename, datasets);
		Utils.metric("G-Mean", "averaged", resultsPath, algorithmsFilename, datasets);
		
		Utils.metric("evaluation time (cpu seconds)", "last", resultsPath, algorithmsFilename, datasets);
		Utils.metric("model cost (RAM-Hours)", "averaged", resultsPath, algorithmsFilename, datasets);	
	}
}