package experiments.binary;

import utils.Utils;

public class Dynamic_Imbalance_Ratio_Increasing_Decreasing {

	public static void main(String[] args) throws Exception {

		String[] generators = new String[] {
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 6 -f 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.AgrawalGenerator -i 7 -f 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.AgrawalGenerator -i 8 -f 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.AgrawalGenerator -i 9 -f 1)) -r 9 "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "		  						 
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 6 -f 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.AssetNegotiationGenerator -i 7 -f 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.AssetNegotiationGenerator -i 8 -f 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.AssetNegotiationGenerator -i 9 -f 1)) -r 9 "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "		  						 
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.0)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 2 -t 0.0)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 2 -t 0.0)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 2 -t 0.0)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 2 -t 0.0)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 6 -a 10 -c 2 -k 2 -t 0.0)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.HyperplaneGenerator -i 7 -a 10 -c 2 -k 2 -t 0.0)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.HyperplaneGenerator -i 8 -a 10 -c 2 -k 2 -t 0.0)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.HyperplaneGenerator -i 9 -a 10 -c 2 -k 2 -t 0.0)) -r 9 "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "		  						 
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1",		 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.MixedGenerator -i 2 -f 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 4 -f 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 6 -f 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.MixedGenerator -i 7 -f 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.MixedGenerator -i 8 -f 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.MixedGenerator -i 9 -f 1)) -r 9 "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "		  						 
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1",		  	
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 6 -a 10 -c 2 -r 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.RandomRBFGenerator -i 7 -a 10 -c 2 -r 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.RandomRBFGenerator -i 8 -a 10 -c 2 -r 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.RandomRBFGenerator -i 9 -a 10 -c 2 -r 1)) -r 9 "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "		  						 
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 6 -o 5 -u 5 -c 2 -r 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.RandomTreeGenerator -i 7 -o 5 -u 5 -c 2 -r 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.RandomTreeGenerator -i 8 -o 5 -u 5 -c 2 -r 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.RandomTreeGenerator -i 9 -o 5 -u 5 -c 2 -r 1)) -r 9 "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "		  						 
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.SEAGenerator -i 2 -f 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.SEAGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 4 -f 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 6 -f 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.SEAGenerator -i 7 -f 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.SEAGenerator -i 8 -f 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.SEAGenerator -i 9 -f 1)) -r 9 "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "		  						 
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1",		  
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.SineGenerator -i 2 -f 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.SineGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.SineGenerator -i 4 -f 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.SineGenerator -i 6 -f 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.SineGenerator -i 7 -f 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.SineGenerator -i 8 -f 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.SineGenerator -i 9 -f 1)) -r 9 "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "		  						 
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.TextGenerator -i 1 -a 100)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.TextGenerator -i 2 -a 100)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.TextGenerator -i 3 -a 100)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.TextGenerator -i 4 -a 100)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.TextGenerator -i 5 -a 100)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.TextGenerator -i 6 -a 100)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.TextGenerator -i 7 -a 100)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.TextGenerator -i 8 -a 100)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.TextGenerator -i 9 -a 100)) -r 9 "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "		  						 
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1",	 
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 2 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 3 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 4 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 5 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 6 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 7 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 8 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 8 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 1 -i 9 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 9 "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "		  						 
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1) "
		  						 + "-p 22222 -w 1",	  	

				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 6 -f 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.AgrawalGenerator -i 7 -f 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.AgrawalGenerator -i 8 -f 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.AgrawalGenerator -i 9 -f 1)) -r 9 "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "		  						 
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 6 -f 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.AssetNegotiationGenerator -i 7 -f 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.AssetNegotiationGenerator -i 8 -f 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.AssetNegotiationGenerator -i 9 -f 1)) -r 9 "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "		  						 
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 2 -t 0.0)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 2 -t 0.0)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 2 -t 0.0)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 2 -t 0.0)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 2 -t 0.0)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 6 -a 10 -c 2 -k 2 -t 0.0)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.HyperplaneGenerator -i 7 -a 10 -c 2 -k 2 -t 0.0)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.HyperplaneGenerator -i 8 -a 10 -c 2 -k 2 -t 0.0)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.HyperplaneGenerator -i 9 -a 10 -c 2 -k 2 -t 0.0)) -r 9 "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "		  						 
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111",		 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.MixedGenerator -i 2 -f 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 4 -f 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 6 -f 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.MixedGenerator -i 7 -f 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.MixedGenerator -i 8 -f 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.MixedGenerator -i 9 -f 1)) -r 9 "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "		  						 
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111",		  	
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 6 -a 10 -c 2 -r 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.RandomRBFGenerator -i 7 -a 10 -c 2 -r 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.RandomRBFGenerator -i 8 -a 10 -c 2 -r 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.RandomRBFGenerator -i 9 -a 10 -c 2 -r 1)) -r 9 "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "		  						 
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111",	
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 6 -o 5 -u 5 -c 2 -r 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.RandomTreeGenerator -i 7 -o 5 -u 5 -c 2 -r 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.RandomTreeGenerator -i 8 -o 5 -u 5 -c 2 -r 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.RandomTreeGenerator -i 9 -o 5 -u 5 -c 2 -r 1)) -r 9 "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "		  						 
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111",	
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.SEAGenerator -i 2 -f 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.SEAGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 4 -f 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 6 -f 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.SEAGenerator -i 7 -f 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.SEAGenerator -i 8 -f 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.SEAGenerator -i 9 -f 1)) -r 9 "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "		  						 
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111",		  
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.SineGenerator -i 2 -f 1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.SineGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.SineGenerator -i 4 -f 1)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.SineGenerator -i 6 -f 1)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.SineGenerator -i 7 -f 1)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.SineGenerator -i 8 -f 1)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.SineGenerator -i 9 -f 1)) -r 9 "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "		  						 
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111",	
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.50 -g (moa.streams.generators.TextGenerator -i 1 -a 100)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.20 -g (moa.streams.generators.TextGenerator -i 2 -a 100)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.10 -g (moa.streams.generators.TextGenerator -i 3 -a 100)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.TextGenerator -i 4 -a 100)) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.TextGenerator -i 5 -a 100)) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 6 -i 0.05 -g (moa.streams.generators.TextGenerator -i 6 -a 100)) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 7 -i 0.10 -g (moa.streams.generators.TextGenerator -i 7 -a 100)) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 8 -i 0.20 -g (moa.streams.generators.TextGenerator -i 8 -a 100)) -r 8 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 9 -i 0.50 -g (moa.streams.generators.TextGenerator -i 9 -a 100)) -r 9 "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "		  						 
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 2 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 3 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 4 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 5 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 6 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 6 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 7 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 7 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 8 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 8 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 1 -i 9 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 9 "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "		  						 
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111) "
		  						 + "-p 22222 -w 11111",	  	
		  						 
		};
		
		String[] generatorsFilename = new String[] {
				"Sudden-AgrawalGenerator",
				"Sudden-AssetNegotiationGenerator",
				"Sudden-HyperplaneGenerator",
				"Sudden-MixedGenerator",
				"Sudden-RandomRBFGenerator",
				"Sudden-RandomTreeGenerator",
				"Sudden-SEAGenerator",
				"Sudden-SineGenerator",
				"Sudden-TextGenerator",
				"Sudden-ImbalancedGenerator-N1",
				
				"Gradual-AgrawalGenerator",
				"Gradual-AssetNegotiationGenerator",
				"Gradual-HyperplaneGenerator",
				"Gradual-MixedGenerator",
				"Gradual-RandomRBFGenerator",
				"Gradual-RandomTreeGenerator",
				"Gradual-SEAGenerator",
				"Gradual-SineGenerator",
				"Gradual-TextGenerator",
				"Gradual-ImbalancedGenerator-N1",
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

		String resultsPath = "D:/DataStreams-Imbalanced/binary/increasing_decreasing_IR/";

		// Executables
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
						+ " -d " + resultsPath + algorithmsFilename[alg] + "-" + generatorsFilename[gen] + ".csv");
			}
		}

		// Show metrics for results
		System.out.println("===== Results =====");
		
		Utils.metric("Kappa", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("AUC", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("sAUC", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("G-Mean", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("Accuracy", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		
		Utils.metric("evaluation time (cpu seconds)", "last", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("model cost (RAM-Hours)", "averaged", resultsPath, algorithmsFilename, generatorsFilename);	
	}
}