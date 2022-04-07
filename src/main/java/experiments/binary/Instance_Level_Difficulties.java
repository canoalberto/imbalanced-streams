package experiments.binary;

import utils.Utils;

public class Instance_Level_Difficulties {

	public static void main(String[] args) throws Exception {

		String[] generators = new String[] {
				// Static IR + Static B/R/BR
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.8 -b 0.2 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.6 -b 0.4 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.4 -b 0.6 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.2 -b 0.8 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.0 -b 1 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.8 -b 0 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.6 -b 0 -p 0.4 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.4 -b 0 -p 0.6 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.2 -b 0 -p 0.8 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.0 -b 0 -p 1 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.6 -b 0.2 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 0.2 -b 0.4 -p 0.4 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.8 -b 0.2 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.6 -b 0.4 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.4 -b 0.6 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.2 -b 0.8 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.0 -b 1 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.8 -b 0 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.6 -b 0 -p 0.4 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.4 -b 0 -p 0.6 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.2 -b 0 -p 0.8 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.0 -b 0 -p 1 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.6 -b 0.2 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 0.2 -b 0.4 -p 0.4 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.8 -b 0.2 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.6 -b 0.4 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.4 -b 0.6 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.2 -b 0.8 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.0 -b 1 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.8 -b 0 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.6 -b 0 -p 0.4 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.4 -b 0 -p 0.6 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.2 -b 0 -p 0.8 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.0 -b 0 -p 1 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.6 -b 0.2 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 0.2 -b 0.4 -p 0.4 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 1 -b 0 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.8 -b 0.2 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.6 -b 0.4 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.4 -b 0.6 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.2 -b 0.8 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.0 -b 1 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.8 -b 0 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.6 -b 0 -p 0.4 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.4 -b 0 -p 0.6 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.2 -b 0 -p 0.8 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.0 -b 0 -p 1 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.6 -b 0.2 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.1 -s 0.2 -b 0.4 -p 0.4 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 1 -b 0 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.8 -b 0.2 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.6 -b 0.4 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.4 -b 0.6 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.2 -b 0.8 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.0 -b 1 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.8 -b 0 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.6 -b 0 -p 0.4 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.4 -b 0 -p 0.6 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.2 -b 0 -p 0.8 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.0 -b 0 -p 1 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.6 -b 0.2 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.2 -s 0.2 -b 0.4 -p 0.4 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 1 -b 0 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.8 -b 0.2 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.6 -b 0.4 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.4 -b 0.6 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.2 -b 0.8 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.0 -b 1 -p 0 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.8 -b 0 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.6 -b 0 -p 0.4 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.4 -b 0 -p 0.6 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.2 -b 0 -p 0.8 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.0 -b 0 -p 1 -o 0 -u",		
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.6 -b 0.2 -p 0.2 -o 0 -u",
				"moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.5 -s 0.2 -b 0.4 -p 0.4 -o 0 -u",
				
				// Static IR + Move
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
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
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
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
		  						 
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 0) "
								 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 0) "
								 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 0) "
								 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 0) "
								 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 0) "
								 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 0) "
								 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 0) "
		  						 + "-p 40000 -w 1",		
		  						 
		  		// Static IR + Split
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",		 
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",		
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",			
		  						 
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",		 
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",		
		  						 
				// Static IR + Merge
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",		 
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",		
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",			
		  						 
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",		 
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.02 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",	
		  					
		  		// Increasing IR + increasing B/R/BR
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 0.8 -b 0.2 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 2 -a 5 -n 1 -m 0.20 -s 0.6 -b 0.4 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 3 -a 5 -n 1 -m 0.10 -s 0.4 -b 0.6 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 4 -a 5 -n 1 -m 0.05 -s 0.2 -b 0.8 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 1 -i 5 -a 5 -n 1 -m 0.01 -s 0.0 -b 1.0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 0.8 -b 0 -p 0.2 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 2 -a 5 -n 1 -m 0.20 -s 0.6 -b 0 -p 0.4 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 3 -a 5 -n 1 -m 0.10 -s 0.4 -b 0 -p 0.6 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 4 -a 5 -n 1 -m 0.05 -s 0.2 -b 0 -p 0.8 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 1 -i 5 -a 5 -n 1 -m 0.01 -s 0.0 -b 0 -p 1.0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 0.8 -b 0.1 -p 0.1 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 2 -a 5 -n 1 -m 0.20 -s 0.6 -b 0.2 -p 0.2 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 3 -a 5 -n 1 -m 0.10 -s 0.4 -b 0.3 -p 0.3 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 4 -a 5 -n 1 -m 0.05 -s 0.2 -b 0.4 -p 0.4 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 1 -i 5 -a 5 -n 1 -m 0.01 -s 0.0 -b 0.5 -p 0.5 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 0.8 -b 0.2 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 2 -a 5 -n 1 -m 0.20 -s 0.6 -b 0.4 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 3 -a 5 -n 1 -m 0.10 -s 0.4 -b 0.6 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 4 -a 5 -n 1 -m 0.05 -s 0.2 -b 0.8 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 1 -i 5 -a 5 -n 1 -m 0.01 -s 0.0 -b 1.0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 0.8 -b 0 -p 0.2 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 2 -a 5 -n 1 -m 0.20 -s 0.6 -b 0 -p 0.4 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 3 -a 5 -n 1 -m 0.10 -s 0.4 -b 0 -p 0.6 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 4 -a 5 -n 1 -m 0.05 -s 0.2 -b 0 -p 0.8 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 1 -i 5 -a 5 -n 1 -m 0.01 -s 0.0 -b 0 -p 1.0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 0.8 -b 0.1 -p 0.1 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 2 -a 5 -n 1 -m 0.20 -s 0.6 -b 0.2 -p 0.2 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 3 -a 5 -n 1 -m 0.10 -s 0.4 -b 0.3 -p 0.3 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 4 -a 5 -n 1 -m 0.05 -s 0.2 -b 0.4 -p 0.4 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 1 -i 5 -a 5 -n 1 -m 0.01 -s 0.0 -b 0.5 -p 0.5 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
		  						 
				 // Increasing IR + Move
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.50 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.20 -s 1 -b 0 -p 0 -o 0 -u) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 3 -i 3 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 4 -i 4 -a 5 -n 1 -m 0.05 -s 1 -b 0 -p 0 -o 0 -u) -r 4 "
		  						 + "-d (moa.streams.generators.ImbalancedGenerator -r 5 -i 5 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",	
		  						 
				 // Increasing IR + Split
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 1 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 5 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",	
		  						 
				 // Increasing IR + Merge
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 50000",
				"ConceptDriftStream -s (moa.streams.generators.ImbalancedGenerator -r 1 -i 1 -a 5 -n 5 -m 0.10 -s 1 -b 0 -p 0 -o 0 -u) -r 1 "
		  + "-d (moa.streams.generators.ImbalancedGenerator -r 2 -i 2 -a 5 -n 1 -m 0.01 -s 1 -b 0 -p 0 -o 0 -u) "
		  						 + "-p 100000 -w 1",			  						 
		};
		
		String[] generatorsFilename = new String[] {
				"N1-IR100-B0-P0",
				"N1-IR100-B20-P0",
				"N1-IR100-B40-P0",
				"N1-IR100-B60-P0",
				"N1-IR100-B80-P0",
				"N1-IR100-B100-P0",
				"N1-IR100-B0-P20",
				"N1-IR100-B0-P40",
				"N1-IR100-B0-P60",
				"N1-IR100-B0-P80",
				"N1-IR100-B0-P100",
				"N1-IR100-B20-P20",
				"N1-IR100-B40-P40",
				"N1-IR50-B0-P0",
				"N1-IR50-B20-P0",
				"N1-IR50-B40-P0",
				"N1-IR50-B60-P0",
				"N1-IR50-B80-P0",
				"N1-IR50-B100-P0",
				"N1-IR50-B0-P20",
				"N1-IR50-B0-P40",
				"N1-IR50-B0-P60",
				"N1-IR50-B0-P80",
				"N1-IR50-B0-P100",
				"N1-IR50-B20-P20",
				"N1-IR50-B40-P40",
				"N1-IR20-B0-P0",
				"N1-IR20-B20-P0",
				"N1-IR20-B40-P0",
				"N1-IR20-B60-P0",
				"N1-IR20-B80-P0",
				"N1-IR20-B100-P0",
				"N1-IR20-B0-P20",
				"N1-IR20-B0-P40",
				"N1-IR20-B0-P60",
				"N1-IR20-B0-P80",
				"N1-IR20-B0-P100",
				"N1-IR20-B20-P20",
				"N1-IR20-B40-P40",
				"N1-IR10-B0-P0",
				"N1-IR10-B20-P0",
				"N1-IR10-B40-P0",
				"N1-IR10-B60-P0",
				"N1-IR10-B80-P0",
				"N1-IR10-B100-P0",
				"N1-IR10-B0-P20",
				"N1-IR10-B0-P40",
				"N1-IR10-B0-P60",
				"N1-IR10-B0-P80",
				"N1-IR10-B0-P100",
				"N1-IR10-B20-P20",
				"N1-IR10-B40-P40",
				"N1-IR5-B0-P0",
				"N1-IR5-B20-P0",
				"N1-IR5-B40-P0",
				"N1-IR5-B60-P0",
				"N1-IR5-B80-P0",
				"N1-IR5-B100-P0",
				"N1-IR5-B0-P20",
				"N1-IR5-B0-P40",
				"N1-IR5-B0-P60",
				"N1-IR5-B0-P80",
				"N1-IR5-B0-P100",
				"N1-IR5-B20-P20",
				"N1-IR5-B40-P40",				
				"N1-IR1-B0-P0",
				"N1-IR1-B20-P0",
				"N1-IR1-B40-P0",
				"N1-IR1-B60-P0",
				"N1-IR1-B80-P0",
				"N1-IR1-B100-P0",
				"N1-IR1-B0-P20",
				"N1-IR1-B0-P40",
				"N1-IR1-B0-P60",
				"N1-IR1-B0-P80",
				"N1-IR1-B0-P100",
				"N1-IR1-B20-P20",
				"N1-IR1-B40-P40",
				
				"Gradual-Move-N1-IR1-B0-P0",
				"Gradual-Move-N1-IR5-B0-P0",
				"Gradual-Move-N1-IR10-B0-P0",
				"Gradual-Move-N1-IR20-B0-P0",
				"Gradual-Move-N1-IR50-B0-P0",
				"Gradual-Move-N1-IR100-B0-P0",
				
				"Sudden-Move-N1-IR1-B0-P0",
				"Sudden-Move-N1-IR5-B0-P0",
				"Sudden-Move-N1-IR10-B0-P0",
				"Sudden-Move-N1-IR20-B0-P0",
				"Sudden-Move-N1-IR50-B0-P0",
				"Sudden-Move-N1-IR100-B0-P0",
				
				"Gradual-Split-IR1-B0-P0",
				"Gradual-Split-IR5-B0-P0",
				"Gradual-Split-IR10-B0-P0",
				"Gradual-Split-IR20-B0-P0",
				"Gradual-Split-IR50-B0-P0",
				"Gradual-Split-IR100-B0-P0",
				
				"Sudden-Split-IR1-B0-P0",
				"Sudden-Split-IR5-B0-P0",
				"Sudden-Split-IR10-B0-P0",
				"Sudden-Split-IR20-B0-P0",
				"Sudden-Split-IR50-B0-P0",
				"Sudden-Split-IR100-B0-P0",
				
				"Gradual-Merge-IR1-B0-P0",
				"Gradual-Merge-IR5-B0-P0",
				"Gradual-Merge-IR10-B0-P0",
				"Gradual-Merge-IR20-B0-P0",
				"Gradual-Merge-IR50-B0-P0",
				"Gradual-Merge-IR100-B0-P0",
				
				"Sudden-Merge-IR1-B0-P0",
				"Sudden-Merge-IR5-B0-P0",
				"Sudden-Merge-IR10-B0-P0",
				"Sudden-Merge-IR20-B0-P0",
				"Sudden-Merge-IR50-B0-P0",
				"Sudden-Merge-IR100-B0-P0",
				
				"Gradual-N1-Increasing-IR-Increasing-Borderline",
				"Gradual-N1-Increasing-IR-Increasing-Rare",
				"Gradual-N1-Increasing-IR-Increasing-Borderline+Rare",
				"Sudden-N1-Increasing-IR-Increasing-Borderline",
				"Sudden-N1-Increasing-IR-Increasing-Rare",
				"Sudden-N1-Increasing-IR-Increasing-Borderline+Rare",
				
				"Gradual-Move-N1-Increasing-IR-B0-P0",
				"Sudden-Move-N1-Increasing-IR-B0-P0",
				
				"Gradual-Split-Increasing-IR-B0-P0",
				"Sudden-Split-Increasing-IR-B0-P0",
				
				"Gradual-Merge-Increasing-IR-B0-P0",
				"Sudden-Merge-Increasing-IR-B0-P0",
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

		String resultsPath = "D:/DataStreams-Imbalanced/binary/instance_level_difficulties/";

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