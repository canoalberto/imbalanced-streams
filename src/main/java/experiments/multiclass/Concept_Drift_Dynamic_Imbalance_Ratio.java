package experiments.multiclass;

import utils.Utils;

public class Concept_Drift_Dynamic_Imbalance_Ratio {

	public static void main(String[] args) throws Exception {

String[] generators = new String[] {
				
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 3 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 3 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0164;0.8197;0.1639 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 3 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 3 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 3 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 3 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 3 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0164;0.8197;0.1639 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 3 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 3 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 3 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 3 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 3 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0164;0.8197;0.1639 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 3 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 3 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 3 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 3 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 3 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0164;0.8197;0.1639 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 3 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 3 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 3 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 3 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 3 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0164;0.8197;0.1639 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 3 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 3 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 3 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 3 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 3 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0164;0.8197;0.1639 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 3 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 3 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 3 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 3 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 3 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0164;0.8197;0.1639 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 3 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 3 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 3 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 3 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 3 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0164;0.8197;0.1639 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 3 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.8197;0.1639;0.0164 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 3 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.1639;0.0164;0.8197 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 3 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
				
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.5814;0.2326;0.1163;0.0581;0.0116 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 5 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.2326;0.1163;0.0581;0.0116;0.5814 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 5 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1163;0.0581;0.0116;0.5814;0.2326 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 5 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0581;0.0116;0.5814;0.2326;0.1163 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 5 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0116;0.5814;0.2326;0.1163;0.0581 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 5 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.5814;0.2326;0.1163;0.0581;0.0116 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.2326;0.1163;0.0581;0.0116;0.5814 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1163;0.0581;0.0116;0.5814;0.2326 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 5 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0581;0.0116;0.5814;0.2326;0.1163 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0116;0.5814;0.2326;0.1163;0.0581 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.5814;0.2326;0.1163;0.0581;0.0116 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.2326;0.1163;0.0581;0.0116;0.5814 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1163;0.0581;0.0116;0.5814;0.2326 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 5 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0581;0.0116;0.5814;0.2326;0.1163 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0116;0.5814;0.2326;0.1163;0.0581 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.5814;0.2326;0.1163;0.0581;0.0116 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.2326;0.1163;0.0581;0.0116;0.5814 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1163;0.0581;0.0116;0.5814;0.2326 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 5 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0581;0.0116;0.5814;0.2326;0.1163 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0116;0.5814;0.2326;0.1163;0.0581 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.5814;0.2326;0.1163;0.0581;0.0116 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 5 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.2326;0.1163;0.0581;0.0116;0.5814 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 5 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1163;0.0581;0.0116;0.5814;0.2326 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 5 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0581;0.0116;0.5814;0.2326;0.1163 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 5 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0116;0.5814;0.2326;0.1163;0.0581 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 5 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.5814;0.2326;0.1163;0.0581;0.0116 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.2326;0.1163;0.0581;0.0116;0.5814 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1163;0.0581;0.0116;0.5814;0.2326 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 5 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0581;0.0116;0.5814;0.2326;0.1163 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0116;0.5814;0.2326;0.1163;0.0581 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.5814;0.2326;0.1163;0.0581;0.0116 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.2326;0.1163;0.0581;0.0116;0.5814 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1163;0.0581;0.0116;0.5814;0.2326 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 5 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0581;0.0116;0.5814;0.2326;0.1163 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0116;0.5814;0.2326;0.1163;0.0581 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.5814;0.2326;0.1163;0.0581;0.0116 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 5 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.2326;0.1163;0.0581;0.0116;0.5814 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -c 5 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1163;0.0581;0.0116;0.5814;0.2326 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 5 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0581;0.0116;0.5814;0.2326;0.1163 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -c 5 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0116;0.5814;0.2326;0.1163;0.0581 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 5 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 10 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 10 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 10 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 10 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 10 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 10 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 10 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 10 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 10 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 10 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 10 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 10 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 10 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 10 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 10 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -n 100 -c 10 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -d 10 -c 10 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 10 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -d 10 -c 10 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -n 100 -c 10 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 10 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 10 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 10 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 10 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 10 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 10 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 10 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 10 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 10 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 10 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 10 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 10 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 10 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 10 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 10 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -n 100 -c 10 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.1508;0.1256;0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -d 10 -c 10 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.1005;0.0754;0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 10 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0503;0.0251;0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -d 10 -c 10 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0150;0.0050;0.2513;0.2010;0.1508;0.1256;0.1005;0.0754;0.0503;0.0251 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -n 100 -c 10 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 20 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 20 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 20 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 20 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 20 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 20 -n 100 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 20 -n 100 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 20 -n 100 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 20 -n 100 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 20 -n 100 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 20 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 20 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 20 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 20 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 20 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -n 100 -c 20 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -d 10 -c 20 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 20 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -d 10 -c 20 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -n 100 -c 20 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 20 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 20 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 20 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 20 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 20 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -n 100 -c 20 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -n 100 -c 20 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -n 100 -c 20 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -n 100 -c 20 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -n 100 -c 20 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -c 20 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -c 20 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -c 20 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -c 20 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -c 20 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -n 100 -c 20 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -d 10 -c 20 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 20 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -d 10 -c 20 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126;0.0075;0.0025;0.1256;0.1005;0.0754;0.0628;0.0503;0.0377;0.0251;0.0126 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -n 100 -c 20 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 30 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 30 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 30 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 30 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 30 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -c 30 -n 300 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -c 30 -n 300 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -c 30 -n 300 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -c 30 -n 300 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -c 30 -n 300 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -d 10 -c 30 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -d 10 -c 30 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -d 10 -c 30 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -d 10 -c 30 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -d 10 -c 30 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -n 200 -c 30 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -d 10 -c 30 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 30 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -d 10 -c 30 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -n 200 -c 30 -r 5)) -r 5 "
		  						 + "-p 40000 -w 1) "
								 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1) "
		  						 + "-p 40000 -w 1",		
		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017 -s (moa.streams.generators.HyperplaneGeneratorMC -i 1 -a 10 -c 30 -k 10 -t 0.1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503 -s (moa.streams.generators.HyperplaneGeneratorMC -i 2 -a 10 -c 30 -k 10 -t 0.1)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 30 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050 -s (moa.streams.generators.HyperplaneGeneratorMC -i 4 -a 10 -c 30 -k 10 -t 0.1)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670 -s (moa.streams.generators.HyperplaneGeneratorMC -i 5 -a 10 -c 30 -k 10 -t 0.1)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  						 
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -n 300 -c 30 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503 -s (moa.streams.generators.RandomRBFGenerator -i 2 -a 10 -n 300 -c 30 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251 -s (moa.streams.generators.RandomRBFGenerator -i 3 -a 10 -n 300 -c 30 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050 -s (moa.streams.generators.RandomRBFGenerator -i 4 -a 10 -n 300 -c 30 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -n 300 -c 30 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017 -s (moa.streams.generators.RandomTreeGenerator -i 1 -o 5 -u 5 -d 10 -c 30 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 5 -u 5 -d 10 -c 30 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251 -s (moa.streams.generators.RandomTreeGenerator -i 3 -o 5 -u 5 -d 10 -c 30 -r 3)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 5 -u 5 -d 10 -c 30 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670 -s (moa.streams.generators.RandomTreeGenerator -i 5 -o 5 -u 5 -d 10 -c 30 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",		  		
				"ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 1 -c 0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017 -s (moa.streams.generators.RandomRBFGenerator -i 1 -a 10 -n 200 -c 30 -r 1)) -r 1 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 2 -c 0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503 -s (moa.streams.generators.RandomTreeGenerator -i 2 -o 0 -u 10 -d 10 -c 30 -r 2)) -r 2 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 3 -c 0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251 -s (moa.streams.generators.HyperplaneGeneratorMC -i 3 -a 10 -c 30 -k 10 -t 0.1)) -r 3 "
		  + "-d (ConceptDriftStream -s (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 4 -c 0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050 -s (moa.streams.generators.RandomTreeGenerator -i 4 -o 0 -u 10 -d 10 -c 30 -r 4)) -r 4 "
		  						 + "-d (moa.streams.generators.imbalanced.MultiClassImbalancedGenerator -i 5 -c 0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0833;0.0670;0.0503;0.0419;0.0335;0.0251;0.0168;0.0084;0.0050;0.0017;0.0838;0.0670 -s (moa.streams.generators.RandomRBFGenerator -i 5 -a 10 -n 200 -c 30 -r 5)) -r 5 "
		  						 + "-p 40000 -w 20000) "
								 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000) "
		  						 + "-p 40000 -w 20000",	
		  						 
		};
		
		String[] generatorsFilename = new String[] {
				"Sudden-HyperplaneGenerator-C3",
				"Sudden-RandomRBFGenerator-C3",
				"Sudden-RandomTreeGenerator-C3",
				"Sudden-RBF-Tree-Hyperplane-C3",
				"Gradual-HyperplaneGenerator-C3",
				"Gradual-RandomRBFGenerator-C3",
				"Gradual-RandomTreeGenerator-C3",
				"Gradual-RBF-Tree-Hyperplane-C3",
				
				"Sudden-HyperplaneGenerator-C5",
				"Sudden-RandomRBFGenerator-C5",
				"Sudden-RandomTreeGenerator-C5",
				"Sudden-RBF-Tree-Hyperplane-C5",
				"Gradual-HyperplaneGenerator-C5",
				"Gradual-RandomRBFGenerator-C5",
				"Gradual-RandomTreeGenerator-C5",
				"Gradual-RBF-Tree-Hyperplane-C5",
				
				"Sudden-HyperplaneGenerator-C10",
				"Sudden-RandomRBFGenerator-C10",
				"Sudden-RandomTreeGenerator-C10",
				"Sudden-RBF-Tree-Hyperplane-C10",
				"Gradual-HyperplaneGenerator-C10",
				"Gradual-RandomRBFGenerator-C10",
				"Gradual-RandomTreeGenerator-C10",
				"Gradual-RBF-Tree-Hyperplane-C10",
				
				"Sudden-HyperplaneGenerator-C20",
				"Sudden-RandomRBFGenerator-C20",
				"Sudden-RandomTreeGenerator-C20",
				"Sudden-RBF-Tree-Hyperplane-C20",
				"Gradual-HyperplaneGenerator-C20",
				"Gradual-RandomRBFGenerator-C20",
				"Gradual-RandomTreeGenerator-C20",
				"Gradual-RBF-Tree-Hyperplane-C20",
				
				"Sudden-HyperplaneGenerator-C30",
				"Sudden-RandomRBFGenerator-C30",
				"Sudden-RandomTreeGenerator-C30",
				"Sudden-RBF-Tree-Hyperplane-C30",
				"Gradual-HyperplaneGenerator-C30",
				"Gradual-RandomRBFGenerator-C30",
				"Gradual-RandomTreeGenerator-C30",
				"Gradual-RBF-Tree-Hyperplane-C30",
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

		String resultsPath = "D:/DataStreams-Imbalanced/multiclass/concept_drift_dynamic_IR/";

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