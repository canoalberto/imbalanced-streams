/*
 *    HoeffdingAdaptiveTreeHistogram.java
 *    Copyright (C) 2008 University of Waikato, Hamilton, New Zealand
 *    @author Albert Bifet (abifet at cs dot waikato dot ac dot nz)
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program. If not, see <http://www.gnu.org/licenses/>.
 *    
 */
package moa.classifiers.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import moa.capabilities.Capability;
import moa.capabilities.ImmutableCapabilities;
import moa.classifiers.bayes.NaiveBayes;
import moa.classifiers.core.conditionaltests.InstanceConditionalTest;
import moa.classifiers.core.driftdetection.ADWIN;
import moa.core.DoubleVector;
import moa.core.MiscUtils;
import moa.core.Utils;
import com.yahoo.labs.samoa.instances.Instance;
import org.apache.commons.math3.util.Pair;

/**
 * Hoeffding Adaptive Tree Histogram for evolving data streams.
 *
 * <p>This adaptive Hoeffding Tree Histogram is an extension of the original 
 * Hoeffding Adaptive Tree to be used as histogram. It saves the number of
 * instances seen in every node and has a function to return all the leaves.</p>
 * See details in:</p>
 * <p>Adaptive Learning from Evolving Data Streams. Albert Bifet, Ricard Gavaldà.
 * IDA 2009</p>
 *
 * <ul>
 * <li> Same parameters as <code>HoeffdingTreeNBAdaptive</code></li>
 * <li> -l : Leaf prediction to use: MajorityClass (MC), Naive Bayes (NB) or NaiveBayes
 * adaptive (NBAdaptive).
 * </ul>
 *
 * @author Albert Bifet (abifet at cs dot waikato dot ac dot nz)
 * @version $Revision: 7 $
 */
public class HoeffdingAdaptiveTreeHistogram extends HoeffdingTreeHistogram {

    private static final long serialVersionUID = 1L;

    @Override
    public String getPurposeString() {
        return "Hoeffding Adaptive Tree for evolving data streams that uses ADWIN to replace branches for new ones.";
    }
    
 /*   public MultiChoiceOption leafpredictionOption = new MultiChoiceOption(
            "leafprediction", 'l', "Leaf prediction to use.", new String[]{
                "MC", "NB", "NBAdaptive"}, new String[]{
                "Majority class",
                "Naive Bayes",
                "Naive Bayes Adaptive"}, 2);*/

    public interface NewNode {        
		// Change for adwin
        //public boolean getErrorChange();
        public int numberLeaves();

        public double getErrorEstimation();

        public double getErrorWidth();

        public boolean isNullError();

        public void killTreeChilds(HoeffdingAdaptiveTreeHistogram ht);

        public void learnFromInstance(Instance inst, HoeffdingAdaptiveTreeHistogram ht, SplitNode parent, int parentBranch, boolean isAlternateTree);

        public void filterInstanceToLeaves(Instance inst, SplitNode myparent, int parentBranch, List<FoundNode> foundNodes,
                boolean updateSplitterCounts);
        
        public void getLeaves(SplitNode myparent, int parentBranch, List<Pair<FoundNode, Double>> nodeWeights, int minSizeAllowed, int minClass);
        
        public void getMisclassifiedLeaves(SplitNode myparent, int parentBranch, List<Pair<FoundNode, Double>> nodeWeights, int minSizeAllowed);
        
        public void getNumberInstancesInChildren(SplitNode myparent, int parentBranch, DoubleVector classesDistribution);
                
    }

    public static class AdaSplitNode extends SplitNode implements NewNode {

        private static final long serialVersionUID = 1L;

        protected Node alternateTree;        
                        
        protected ADWIN estimationErrorWeight;
        //public boolean isAlternateTree = false;

        public boolean ErrorChange = false;

        protected int randomSeed = 1;

        protected Random classifierRandom;

        //public boolean getErrorChange() {
        //		return ErrorChange;
        //}
        @Override
        public int calcByteSizeIncludingSubtree() {
            int byteSize = calcByteSize();
            if (alternateTree != null) {
                byteSize += alternateTree.calcByteSizeIncludingSubtree();
            }
            if (estimationErrorWeight != null) {
                byteSize += estimationErrorWeight.measureByteSize();
            }
            for (Node child : this.children) {
                if (child != null) {
                    byteSize += child.calcByteSizeIncludingSubtree();
                }
            }
            return byteSize;
        }
        
        public AdaSplitNode(InstanceConditionalTest splitTest,
                double[] classObservations, double[] classObservationsNode, double[] classObservationsSinceCreation, int size) {
            super(splitTest, classObservations, classObservationsNode, classObservationsSinceCreation, size);
            this.classifierRandom = new Random(this.randomSeed);
        }
        
        public AdaSplitNode(InstanceConditionalTest splitTest,
                double[] classObservations, double[] classObservationsNode, double[] classObservationsSinceCreation) {
            super(splitTest, classObservations,classObservationsNode, classObservationsSinceCreation);
            this.classifierRandom = new Random(this.randomSeed);
        }

        @Override
        public int numberLeaves() {
            int numLeaves = 0;
            for (Node child : this.children) {
                if (child != null) {
                    numLeaves += ((NewNode) child).numberLeaves();
                }
            }
            return numLeaves;
        }

        @Override
        public double getErrorEstimation() {
            return this.estimationErrorWeight.getEstimation();
        }

        @Override
        public double getErrorWidth() {
            double w = 0.0;
            if (isNullError() == false) {
                w = this.estimationErrorWeight.getWidth();
            }
            return w;
        }

        @Override
        public boolean isNullError() {
            return (this.estimationErrorWeight == null);
        }

        // SplitNodes can have alternative trees, but LearningNodes can't
        // LearningNodes can split, but SplitNodes can't
        // Parent nodes are allways SplitNodes
        @Override
        public void learnFromInstance(Instance inst, HoeffdingAdaptiveTreeHistogram ht, SplitNode parent, int parentBranch, boolean isAlternateTree) {
            int trueClass = (int) inst.classValue();
            this.observedClassDistributionNode.addToValue(trueClass,1); 
            //this.observedSinceCreation.addToValue(trueClass, 1); //qui
            this.observedSinceCreation.addToValue(trueClass, inst.weight());
            //New option vore
            int k = MiscUtils.poisson(1.0, this.classifierRandom);
            Instance weightedInst = (Instance) inst.copy();
            if (k > 0) {
                //weightedInst.setWeight(inst.weight() * k);
            }
            //Compute ClassPrediction using filterInstanceToLeaf
            //int ClassPrediction = Utils.maxIndex(filterInstanceToLeaf(inst, null, -1).node.getClassVotes(inst, ht));
            int ClassPrediction = 0;
            if (filterInstanceToLeaf(inst, parent, parentBranch).node != null) {
                ClassPrediction = Utils.maxIndex(filterInstanceToLeaf(inst, parent, parentBranch).node.getClassVotes(inst, ht));
            }

            boolean blCorrect = (trueClass == ClassPrediction);

            if (this.estimationErrorWeight == null) {
                this.estimationErrorWeight = new ADWIN();
            }
            double oldError = this.getErrorEstimation();
            this.ErrorChange = this.estimationErrorWeight.setInput(blCorrect == true ? 0.0 : 1.0);
            if (this.ErrorChange == true && oldError > this.getErrorEstimation()) {
                //if error is decreasing, don't do anything
                this.ErrorChange = false;
            }

            // Check condition to build a new alternate tree
            //if (this.isAlternateTree == false) {
            if (this.ErrorChange == true) {//&& this.alternateTree == null) {
                //Start a new alternative tree : learning node
                this.alternateTree = ht.newLearningNode();                
                //this.alternateTree.isAlternateTree = true;
                ht.alternateTrees++;                                                                                
            } // Check condition to replace tree
            else if (this.alternateTree != null && ((NewNode) this.alternateTree).isNullError() == false) {
                if (this.getErrorWidth() > 300 && ((NewNode) this.alternateTree).getErrorWidth() > 300) {
                    double oldErrorRate = this.getErrorEstimation();
                    double altErrorRate = ((NewNode) this.alternateTree).getErrorEstimation();
                    double fDelta = .05;
                    //if (gNumAlts>0) fDelta=fDelta/gNumAlts;
                    double fN = 1.0 / ((double) ((NewNode) this.alternateTree).getErrorWidth()) + 1.0 / ((double) this.getErrorWidth());
                    double Bound = (double) Math.sqrt((double) 2.0 * oldErrorRate * (1.0 - oldErrorRate) * Math.log(2.0 / fDelta) * fN);
                    if (Bound < oldErrorRate - altErrorRate) {
                        // Switch alternate tree
                        ht.activeLeafNodeCount -= this.numberLeaves();
                        ht.activeLeafNodeCount += ((NewNode) this.alternateTree).numberLeaves();                        
                        ht.observedClassDistribution.subtractValues(this.observedSinceCreation);
                        ht.observedClassDistribution.addValues(this.alternateTree.getObservedWhenCreated());
                        
                        this.observedSinceCreation = new DoubleVector(this.alternateTree.getObservedWhenCreated());                        
                        killTreeChilds(ht);
                        
                        if (parent != null) {
                            parent.setChild(parentBranch, this.alternateTree);
                            //((AdaSplitNode) parent.getChild(parentBranch)).alternateTree = null;
                        } else {
                            // Switch root tree
                            ht.treeRoot = ((AdaSplitNode) ht.treeRoot).alternateTree;
                        }
                        ht.switchedAlternateTrees++;
                    } else if (Bound < altErrorRate - oldErrorRate) {
                        // Erase alternate tree
                        if (this.alternateTree instanceof ActiveLearningNode) {
                            this.alternateTree = null;                            
                            //ht.activeLeafNodeCount--;
                        } else if (this.alternateTree instanceof InactiveLearningNode) {
                            this.alternateTree = null;                            
                            //ht.inactiveLeafNodeCount--;
                        } else {                        	                  	                        	                        	
                            ((AdaSplitNode) this.alternateTree).killTreeChilds(ht);                            
                        }
                        ht.prunedAlternateTrees++;                        
                    }
                }
            }
            //}
            //learnFromInstance alternate Tree and Child nodes
            if (this.alternateTree != null) {
                ((NewNode) this.alternateTree).learnFromInstance(weightedInst, ht, parent, parentBranch,true);                
            }
            int childBranch = this.instanceChildIndex(inst);
            Node child = this.getChild(childBranch);
            if (child != null) {
                ((NewNode) child).learnFromInstance(weightedInst, ht, this, childBranch, isAlternateTree);
            }
        }

        @Override
        public void killTreeChilds(HoeffdingAdaptiveTreeHistogram ht) {
            for (Node child : this.children) {
                if (child != null) {
                    //Delete alternate tree if it exists
                    if (child instanceof AdaSplitNode && ((AdaSplitNode) child).alternateTree != null) {
                        ((NewNode) ((AdaSplitNode) child).alternateTree).killTreeChilds(ht);
                        ht.prunedAlternateTrees++;
                    }
                    //Recursive delete of SplitNodes
                    if (child instanceof AdaSplitNode) {
                        ((NewNode) child).killTreeChilds(ht);
                    }
                    if (child instanceof ActiveLearningNode) {
                        child = null;
                        ht.activeLeafNodeCount--;
                    } else if (child instanceof InactiveLearningNode) {
                        child = null;
                        ht.inactiveLeafNodeCount--;
                    }
                }
            }
        }

        //New for option votes
        //@Override
        public void filterInstanceToLeaves(Instance inst, SplitNode myparent,
                int parentBranch, List<FoundNode> foundNodes,
                boolean updateSplitterCounts) {
            if (updateSplitterCounts) {
                this.observedClassDistribution.addToValue((int) inst.classValue(), inst.weight());
            }
            int childIndex = instanceChildIndex(inst);
            if (childIndex >= 0) {
                Node child = getChild(childIndex);
                if (child != null) {
                    ((NewNode) child).filterInstanceToLeaves(inst, this, childIndex,
                            foundNodes, updateSplitterCounts);
                } else {
                    foundNodes.add(new FoundNode(null, this, childIndex));
                }
            }
            if (this.alternateTree != null) {
                ((NewNode) this.alternateTree).filterInstanceToLeaves(inst, this, -999,
                        foundNodes, updateSplitterCounts);
            }
        }
        
        public void getLeaves(SplitNode myparent, int parentBranch, List<Pair<FoundNode, Double>> nodeWeights, int minSizeAllowed, int minClass) {          	
            for (Node child : this.children) {            
                if (child != null) {                	
                	((NewNode) child).getLeaves(this, this.children.indexOf(child), nodeWeights, minSizeAllowed, minClass);                	
                } else { 
                	/*
                	if (this.observedClassDistributionNode.getValue(minClass) >= minSizeAllowed) { //qui          	
                		nodeWeights.add(new Pair<FoundNode, Double>(new FoundNode(null, this, this.children.indexOf(child)),this.observedClassDistributionNode.getValue(minClass)));                		
                	}                    
                	*/
                	if (this.observedClassDistribution.getValue(minClass) >= minSizeAllowed) {     	
                		nodeWeights.add(new Pair<FoundNode, Double>(new FoundNode(null, this, this.children.indexOf(child)),this.observedClassDistribution.getValue(minClass)));                		
                	}                   	                 
                }
            }
            
            /*
             * I don't need these leaves
            if (this.alternateTree != null) {
                ((NewNode) this.alternateTree).getLeaves(this, -999, foundNodes);
            }
            */
        }
        
        public void getMisclassifiedLeaves(SplitNode myparent, int parentBranch, List<Pair<FoundNode, Double>> nodeWeights, int minSizeAllowed) {          	
            for (Node child : this.children) {            
                if (child != null) {                	
                	((NewNode) child).getMisclassifiedLeaves(this, this.children.indexOf(child), nodeWeights, minSizeAllowed);                	
                } else { 
                	/*
                	if (this.observedClassDistributionNode.sumOfValues() >= minSizeAllowed) { //qui          	
                		nodeWeights.add(new Pair<FoundNode, Double>(new FoundNode(null, this, this.children.indexOf(child)),this.observedClassDistributionNode.sumOfValues()));                		
                	}                    
                	*/
                	if (this.observedClassDistribution.sumOfValues() >= minSizeAllowed) {     	
                		nodeWeights.add(new Pair<FoundNode, Double>(new FoundNode(null, this, this.children.indexOf(child)),this.observedClassDistribution.sumOfValues()));                		
                	}                   	                 
                }
            }                       
        }        
        
        public void getNumberInstancesInChildren(SplitNode myparent, int parentBranch, DoubleVector classesDistribution) {
        	classesDistribution.addValues(this.observedClassDistributionNode);
        	for (Node child : this.children) {            
                if (child != null) {                	
                	((NewNode) child).getNumberInstancesInChildren(this, this.children.indexOf(child), classesDistribution);                	
                } 
            }        	                    
        }
                
    }

    public static class AdaLearningNode extends LearningNodeNBAdaptive implements NewNode {

        private static final long serialVersionUID = 1L;

        protected ADWIN estimationErrorWeight;

        public boolean ErrorChange = false;

        protected int randomSeed = 1;

        protected Random classifierRandom;
        
        //protected DoubleVector observedClassDistributionNode = new DoubleVector();

        @Override
        public int calcByteSize() {
            int byteSize = super.calcByteSize();
            if (estimationErrorWeight != null) {
                byteSize += estimationErrorWeight.measureByteSize();
            }
            return byteSize;
        }

        public AdaLearningNode(double[] initialClassObservations, double[] initialClassObservationsNode,double[] classObservationsSinceCreation) {
            super(initialClassObservations, initialClassObservationsNode, classObservationsSinceCreation);
            this.classifierRandom = new Random(this.randomSeed);
        }

        @Override
        public int numberLeaves() {
            return 1;
        }

        @Override
        public double getErrorEstimation() {
            if (this.estimationErrorWeight != null) {
                return this.estimationErrorWeight.getEstimation();
            } else {
                return 0;
            }
        }

        @Override
        public double getErrorWidth() {
            return this.estimationErrorWeight.getWidth();
        }

        @Override
        public boolean isNullError() {
            return (this.estimationErrorWeight == null);
        }

        @Override
        public void killTreeChilds(HoeffdingAdaptiveTreeHistogram ht) {
        }

        @Override
        public void learnFromInstance(Instance inst, HoeffdingAdaptiveTreeHistogram ht, SplitNode parent, int parentBranch, boolean isAlternateTree) {
            int trueClass = (int) inst.classValue();
            if (!isAlternateTree) {
            	ht.observedClassDistribution.addToValue(trueClass,1);
            }             
            this.observedClassDistributionNode.addToValue(trueClass, 1);
            //this.observedSinceCreation.addToValue(trueClass, 1); //qui
            //New option vore
            int k = MiscUtils.poisson(1.0, this.classifierRandom);
            Instance weightedInst = (Instance) inst.copy();
            if (k > 0) {
                weightedInst.setWeight(inst.weight() * k);
            }
            this.observedSinceCreation.addToValue(trueClass, weightedInst.weight()); //qui
            //Compute ClassPrediction using filterInstanceToLeaf
            int ClassPrediction = Utils.maxIndex(this.getClassVotes(inst, ht));

            boolean blCorrect = (trueClass == ClassPrediction);

            if (this.estimationErrorWeight == null) {
                this.estimationErrorWeight = new ADWIN();
            }
            double oldError = this.getErrorEstimation();
            this.ErrorChange = this.estimationErrorWeight.setInput(blCorrect == true ? 0.0 : 1.0);
            if (this.ErrorChange == true && oldError > this.getErrorEstimation()) {
                this.ErrorChange = false;
            }

            //Update statistics
            learnFromInstance(weightedInst, ht);	//inst

            //Check for Split condition
            double weightSeen = this.getWeightSeen();
            if (weightSeen
                    - this.getWeightSeenAtLastSplitEvaluation() >= ht.gracePeriodOption.getValue()) {
                ht.attemptToSplit(this, parent,
                        parentBranch);
                this.setWeightSeenAtLastSplitEvaluation(weightSeen);
            }


            //learnFromInstance alternate Tree and Child nodes
			/*if (this.alternateTree != null)  {
            this.alternateTree.learnFromInstance(inst,ht);
            }
            for (Node child : this.children) {
            if (child != null) {
            child.learnFromInstance(inst,ht);
            }
            }*/
        }

        @Override
        public double[] getClassVotes(Instance inst, HoeffdingTreeHistogram ht) {
            double[] dist;
            int predictionOption = ((HoeffdingAdaptiveTreeHistogram) ht).leafpredictionOption.getChosenIndex();
            if (predictionOption == 0) { //MC
                dist = this.observedClassDistribution.getArrayCopy();
            } else if (predictionOption == 1) { //NB
                dist = NaiveBayes.doNaiveBayesPrediction(inst,
                        this.observedClassDistribution, this.attributeObservers);
            } else { //NBAdaptive
                if (this.mcCorrectWeight > this.nbCorrectWeight) {
                    dist = this.observedClassDistribution.getArrayCopy();
                } else {
                    dist = NaiveBayes.doNaiveBayesPrediction(inst,
                            this.observedClassDistribution, this.attributeObservers);
                }
            }
            //New for option votes
            double distSum = Utils.sum(dist);
            if (distSum * this.getErrorEstimation() * this.getErrorEstimation() > 0.0) {
                Utils.normalize(dist, distSum * this.getErrorEstimation() * this.getErrorEstimation()); //Adding weight
            }
            return dist;
        }

        //New for option votes
        @Override
        public void filterInstanceToLeaves(Instance inst,
                SplitNode splitparent, int parentBranch,
                List<FoundNode> foundNodes, boolean updateSplitterCounts) {
            foundNodes.add(new FoundNode(this, splitparent, parentBranch));
        }              
        
        public void getLeaves(SplitNode splitparent, int parentBranch, List<Pair<FoundNode, Double>> nodeWeights, int minSizeAllowed, int minClass) {
        	if ((int) this.observedSinceCreation.getValue(minClass) >= minSizeAllowed) { 
        		nodeWeights.add(new Pair<FoundNode, Double>(new FoundNode(this, splitparent, parentBranch),this.observedSinceCreation.getValue(minClass)));
        	}        	
        }
        
        public void getMisclassifiedLeaves(SplitNode splitparent, int parentBranch, List<Pair<FoundNode, Double>> nodeWeights, int minSizeAllowed) {
        	if ((int) this.observedSinceCreation.sumOfValues() >= minSizeAllowed) { 
        		nodeWeights.add(new Pair<FoundNode, Double>(new FoundNode(this, splitparent, parentBranch),this.observedSinceCreation.sumOfValues()));
        	}        	
        }
        
        public void getNumberInstancesInChildren(SplitNode myparent, int parentBranch, DoubleVector classesDistribution) {
        	classesDistribution.addValues(this.observedClassDistributionNode);
        }
    }

    protected int alternateTrees;

    protected int prunedAlternateTrees;

    protected int switchedAlternateTrees;
    
    public DoubleVector observedClassDistribution = new DoubleVector(); 

    @Override
    protected LearningNode newLearningNode(double[] initialClassObservations, double[] initialClassObservationsNode, double[] classObservationsSinceCreation) {
        // IDEA: to choose different learning nodes depending on predictionOption
        return new AdaLearningNode(initialClassObservations,initialClassObservationsNode, classObservationsSinceCreation);
    }

   @Override
    protected SplitNode newSplitNode(InstanceConditionalTest splitTest,
            double[] classObservations, double[] initialClassObservationsNode, double[] classObservationsSinceCreation, int size) {
        return new AdaSplitNode(splitTest, classObservations, initialClassObservationsNode, classObservationsSinceCreation, size);
    }
   
    @Override
    protected SplitNode newSplitNode(InstanceConditionalTest splitTest,
            double[] classObservations, double[] initialClassObservationsNode, double[] classObservationsSinceCreation) {
        return new AdaSplitNode(splitTest, classObservations, initialClassObservationsNode, classObservationsSinceCreation);
    }

    @Override
    public void trainOnInstanceImpl(Instance inst) {
        if (this.treeRoot == null) {
            this.treeRoot = newLearningNode();
            this.activeLeafNodeCount = 1;
        }
        ((NewNode) this.treeRoot).learnFromInstance(inst, this, null, -1, false);
    }

    //New for options vote
    public FoundNode[] filterInstanceToLeaves(Instance inst,
            SplitNode parent, int parentBranch, boolean updateSplitterCounts) {
        List<FoundNode> nodes = new LinkedList<FoundNode>();
        ((NewNode) this.treeRoot).filterInstanceToLeaves(inst, parent, parentBranch, nodes,
                updateSplitterCounts);
        return nodes.toArray(new FoundNode[nodes.size()]);
    }
    
    public List<Pair<FoundNode, Double>> getLeaves(SplitNode parent, int parentBranch, int minSizeAllowed, int minClass) {
    	List<Pair<FoundNode, Double>> nodeWeights = new ArrayList<Pair<FoundNode, Double>>();        
    	if (this.treeRoot != null) {
    		((NewNode) this.treeRoot).getLeaves(parent,parentBranch,nodeWeights,minSizeAllowed,minClass);
    	}    	
        return nodeWeights;               
    }
    
    public List<Pair<FoundNode, Double>> getMisclassifiedLeaves(SplitNode parent, int parentBranch, int minSizeAllowed) {
    	List<Pair<FoundNode, Double>> nodeWeights = new ArrayList<Pair<FoundNode, Double>>();        
    	if (this.treeRoot != null) {
    		((NewNode) this.treeRoot).getMisclassifiedLeaves(parent,parentBranch,nodeWeights,minSizeAllowed);
    	}    	
        return nodeWeights;               
    }
    
    public DoubleVector getNumberInstancesInChildren(SplitNode parent, int parentBranch) {
    	DoubleVector classesDistribution = new DoubleVector();
    	((NewNode) this.treeRoot).getNumberInstancesInChildren(parent,parentBranch,classesDistribution);
    	return classesDistribution;
    }

    @Override
    public double[] getVotesForInstance(Instance inst) {
        if (this.treeRoot != null) {
            FoundNode[] foundNodes = filterInstanceToLeaves(inst,
                    null, -1, false);
            DoubleVector result = new DoubleVector();
            int predictionPaths = 0;
            for (FoundNode foundNode : foundNodes) {
                if (foundNode.parentBranch != -999) {
                    Node leafNode = foundNode.node;
                    if (leafNode == null) {
                        leafNode = foundNode.parent;
                    }
                    double[] dist = leafNode.getClassVotes(inst, this);
                    //Albert: changed for weights
                    //double distSum = Utils.sum(dist);
                    //if (distSum > 0.0) {
                    //	Utils.normalize(dist, distSum);
                    //}
                    result.addValues(dist);
                    //predictionPaths++;
                }
            }
            //if (predictionPaths > this.maxPredictionPaths) {
            //	this.maxPredictionPaths++;
            //}
            return result.getArrayRef();
        }
        return new double[0];
    }

    @Override
    public ImmutableCapabilities defineImmutableCapabilities() {
        if (this.getClass() == HoeffdingAdaptiveTreeHistogram.class)
            return new ImmutableCapabilities(Capability.VIEW_STANDARD, Capability.VIEW_LITE);
        else
            return new ImmutableCapabilities(Capability.VIEW_STANDARD);
    }
}
