package experiments.binary;

import utils.Utils;

public class Concept_Drift_Static_Imbalance_Ratio {

	public static void main(String[] args) throws Exception {

		String[] generators = new String[] {
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.01 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.01 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.01 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.02 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.02 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.02 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.05 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.05 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.05 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",			  						 
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.1 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.1 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.1 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.2 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.2 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.2 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.AgrawalGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.AgrawalGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.AgrawalGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.AgrawalGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.AgrawalGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.AssetNegotiationGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.AssetNegotiationGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.AssetNegotiationGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.AssetNegotiationGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.AssetNegotiationGenerator -i 5 -f 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 1 -a 10 -c 2 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 2 -a 10 -c 2 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 4 -a 10 -c 2 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 5 -a 10 -c 2 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.MixedGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.MixedGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.MixedGenerator -i 3 -f 1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.MixedGenerator -i 4 -f 2)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.MixedGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 2 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.SEAGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.SEAGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.SEAGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.SEAGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.SEAGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.SineGenerator -i 1 -f 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.SineGenerator -i 2 -f 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.SineGenerator -i 3 -f 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.SineGenerator -i 4 -f 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.SineGenerator -i 5 -f 1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 1 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 2 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 2 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 2 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 3 -i 0.5 -g (moa.streams.generators.HyperplaneGenerator -i 3 -a 10 -c 2 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 4 -i 0.5 -g (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 2 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.BinaryImbalancedGenerator -s 5 -i 0.5 -g (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 2 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
		};
		
		String[] generatorsFilename = new String[] {
				"Sudden-AgrawalGenerator-IR-0.01",
				"Sudden-AssetNegotiationGenerator-IR-0.01",
				"Sudden-HyperplaneGenerator-IR-0.01",
				"Sudden-MixedGenerator-IR-0.01",
				"Sudden-RandomRBFGenerator-IR-0.01",
				"Sudden-RandomTreeGenerator-IR-0.01",
				"Sudden-SEAGenerator-IR-0.01",
				"Sudden-SineGenerator-IR-0.01",
				"Sudden-ImbalancedGenerator-N1-IR-0.01",
				"Sudden-RBF-Tree-Hyperplane-IR-0.01",
				"Gradual-AgrawalGenerator-IR-0.01",
				"Gradual-AssetNegotiationGenerator-IR-0.01",
				"Gradual-HyperplaneGenerator-IR-0.01",
				"Gradual-MixedGenerator-IR-0.01",
				"Gradual-RandomRBFGenerator-IR-0.01",
				"Gradual-RandomTreeGenerator-IR-0.01",
				"Gradual-SEAGenerator-IR-0.01",
				"Gradual-SineGenerator-IR-0.01",
				"Gradual-ImbalancedGenerator-N1-IR-0.01",
				"Gradual-RBF-Tree-Hyperplane-IR-0.01",
				
				"Sudden-AgrawalGenerator-IR-0.02",
				"Sudden-AssetNegotiationGenerator-IR-0.02",
				"Sudden-HyperplaneGenerator-IR-0.02",
				"Sudden-MixedGenerator-IR-0.02",
				"Sudden-RandomRBFGenerator-IR-0.02",
				"Sudden-RandomTreeGenerator-IR-0.02",
				"Sudden-SEAGenerator-IR-0.02",
				"Sudden-SineGenerator-IR-0.02",
				"Sudden-ImbalancedGenerator-N1-IR-0.02",
				"Sudden-RBF-Tree-Hyperplane-IR-0.02",
				"Gradual-AgrawalGenerator-IR-0.02",
				"Gradual-AssetNegotiationGenerator-IR-0.02",
				"Gradual-HyperplaneGenerator-IR-0.02",
				"Gradual-MixedGenerator-IR-0.02",
				"Gradual-RandomRBFGenerator-IR-0.02",
				"Gradual-RandomTreeGenerator-IR-0.02",
				"Gradual-SEAGenerator-IR-0.02",
				"Gradual-SineGenerator-IR-0.02",
				"Gradual-ImbalancedGenerator-N1-IR-0.02",
				"Gradual-RBF-Tree-Hyperplane-IR-0.02",
				
				"Sudden-AgrawalGenerator-IR-0.05",
				"Sudden-AssetNegotiationGenerator-IR-0.05",
				"Sudden-HyperplaneGenerator-IR-0.05",
				"Sudden-MixedGenerator-IR-0.05",
				"Sudden-RandomRBFGenerator-IR-0.05",
				"Sudden-RandomTreeGenerator-IR-0.05",
				"Sudden-SEAGenerator-IR-0.05",
				"Sudden-SineGenerator-IR-0.05",
				"Sudden-ImbalancedGenerator-N1-IR-0.05",
				"Sudden-RBF-Tree-Hyperplane-IR-0.05",
				"Gradual-AgrawalGenerator-IR-0.05",
				"Gradual-AssetNegotiationGenerator-IR-0.05",
				"Gradual-HyperplaneGenerator-IR-0.05",
				"Gradual-MixedGenerator-IR-0.05",
				"Gradual-RandomRBFGenerator-IR-0.05",
				"Gradual-RandomTreeGenerator-IR-0.05",
				"Gradual-SEAGenerator-IR-0.05",
				"Gradual-SineGenerator-IR-0.05",
				"Gradual-ImbalancedGenerator-N1-IR-0.05",
				"Gradual-RBF-Tree-Hyperplane-IR-0.05",
				
				"Sudden-AgrawalGenerator-IR-0.1",
				"Sudden-AssetNegotiationGenerator-IR-0.1",
				"Sudden-HyperplaneGenerator-IR-0.1",
				"Sudden-MixedGenerator-IR-0.1",
				"Sudden-RandomRBFGenerator-IR-0.1",
				"Sudden-RandomTreeGenerator-IR-0.1",
				"Sudden-SEAGenerator-IR-0.1",
				"Sudden-SineGenerator-IR-0.1",
				"Sudden-ImbalancedGenerator-N1-IR-0.1",
				"Sudden-RBF-Tree-Hyperplane-IR-0.1",
				"Gradual-AgrawalGenerator-IR-0.1",
				"Gradual-AssetNegotiationGenerator-IR-0.1",
				"Gradual-HyperplaneGenerator-IR-0.1",
				"Gradual-MixedGenerator-IR-0.1",
				"Gradual-RandomRBFGenerator-IR-0.1",
				"Gradual-RandomTreeGenerator-IR-0.1",
				"Gradual-SEAGenerator-IR-0.1",
				"Gradual-SineGenerator-IR-0.1",
				"Gradual-ImbalancedGenerator-N1-IR-0.1",
				"Gradual-RBF-Tree-Hyperplane-IR-0.1",
				
				"Sudden-AgrawalGenerator-IR-0.2",
				"Sudden-AssetNegotiationGenerator-IR-0.2",
				"Sudden-HyperplaneGenerator-IR-0.2",
				"Sudden-MixedGenerator-IR-0.2",
				"Sudden-RandomRBFGenerator-IR-0.2",
				"Sudden-RandomTreeGenerator-IR-0.2",
				"Sudden-SEAGenerator-IR-0.2",
				"Sudden-SineGenerator-IR-0.2",
				"Sudden-ImbalancedGenerator-N1-IR-0.2",
				"Sudden-RBF-Tree-Hyperplane-IR-0.2",
				"Gradual-AgrawalGenerator-IR-0.2",
				"Gradual-AssetNegotiationGenerator-IR-0.2",
				"Gradual-HyperplaneGenerator-IR-0.2",
				"Gradual-MixedGenerator-IR-0.2",
				"Gradual-RandomRBFGenerator-IR-0.2",
				"Gradual-RandomTreeGenerator-IR-0.2",
				"Gradual-SEAGenerator-IR-0.2",
				"Gradual-SineGenerator-IR-0.2",
				"Gradual-ImbalancedGenerator-N1-IR-0.2",
				"Gradual-RBF-Tree-Hyperplane-IR-0.2",
				
				"Sudden-AgrawalGenerator-IR-0.5",
				"Sudden-AssetNegotiationGenerator-IR-0.5",
				"Sudden-HyperplaneGenerator-IR-0.5",
				"Sudden-MixedGenerator-IR-0.5",
				"Sudden-RandomRBFGenerator-IR-0.5",
				"Sudden-RandomTreeGenerator-IR-0.5",
				"Sudden-SEAGenerator-IR-0.5",
				"Sudden-SineGenerator-IR-0.5",
				"Sudden-ImbalancedGenerator-N1-IR-0.5",
				"Sudden-RBF-Tree-Hyperplane-IR-0.5",
				"Gradual-AgrawalGenerator-IR-0.5",
				"Gradual-AssetNegotiationGenerator-IR-0.5",
				"Gradual-HyperplaneGenerator-IR-0.5",
				"Gradual-MixedGenerator-IR-0.5",
				"Gradual-RandomRBFGenerator-IR-0.5",
				"Gradual-RandomTreeGenerator-IR-0.5",
				"Gradual-SEAGenerator-IR-0.5",
				"Gradual-SineGenerator-IR-0.5",
				"Gradual-ImbalancedGenerator-N1-IR-0.5",
				"Gradual-RBF-Tree-Hyperplane-IR-0.5",
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

		String resultsPath = "D:/DataStreams-Imbalanced/concept_drift_static_IR/";

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
		Utils.metric("G-Mean", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("AUC", "averaged", resultsPath, algorithmsFilename, generatorsFilename);
		
		Utils.metric("evaluation time (cpu seconds)", "last", resultsPath, algorithmsFilename, generatorsFilename);
		Utils.metric("model cost (RAM-Hours)", "averaged", resultsPath, algorithmsFilename, generatorsFilename);	
	}
}