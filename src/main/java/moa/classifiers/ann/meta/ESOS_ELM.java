
/*
 * Author: Artur Szumaczuk (artur.szumaczuk@gmail.com)
 * Implementation of Ensemble of Subset Online Sequential Extreme Learning Machine as in
 * Mirza B. et all “Ensemble of subset online sequential extreme learning machine for class imbalance and concept drift"
 * Neurocomputing, vol. 149, pp. 316-329, 2015
 */

package moa.classifiers.ann.meta;

import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import moa.classifiers.AbstractClassifier;
import moa.classifiers.MultiClassClassifier;
import moa.classifiers.ann.ELM;
import moa.classifiers.ann.OS_ELM;
import moa.classifiers.ann.WELM;
import moa.core.Measurement;
import moa.options.ClassOption;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ESOS_ELM extends AbstractClassifier implements MultiClassClassifier
{
    public ClassOption ensembleClassifier = new ClassOption("ensembleClassifier", 'c',
            "Classifier used in ensemble", OS_ELM.class, "moa.classifiers.ann.OS_ELM");

    public ClassOption elmStoreClassifier = new ClassOption("ELMStoreClassifier", 'e',
            "Classifier used in ELMStore", WELM.class, "moa.classifiers.ann.WELM");

    public IntOption ensembleSize = new IntOption("ensembleSize", 's',
            "The number of OS-ELMs in the main ensemble to start with", 15);

    public IntOption period = new IntOption("period", 'p',
            "The number of instances after which drift is detected", 1000);

    public FloatOption suddenDriftThreshold = new FloatOption("suddenDriftThreshold", 'u',
            "The drop below set % of previous G-mean value denotes sudden drift", 0.9, 0.0, 1.0);

    private List<BundledElm> ensemble, welms;
    private ELMStore elmStore;

    private List<Instance> initialBatch;

    private int instanceCount, initialBatchSize,
            majorityLernerIndex, minorityLearnerIndex;
    private int[] classCount;
    private int[][] confusionMatrix;
    private double ensembleGMean;
    private double[] ensembleVotes;

    private ELM.SerializableConsumer<Instance> learn;

    @Override
    public String getPurposeString()
    {
        return "Ensemble of subset online sequential extreme learning machine for binary classification." +
                "To be used only with prequential evaluation";
    }

    @Override
    public double[] getVotesForInstance(Instance instance)
    {
        if(ensemble.size() == 0) return new double[0];
        int numClasses = instance.numClasses();
        ensembleVotes = new double[numClasses];
        double[] dummyVotes = new double[0];
        try
        {
            List<double[]> elmVotes = ensemble.stream().map(elm -> {
                double[] votes = elm.getVotesForInstance(instance);
                return elm.canVote()
                        ? Arrays.stream(votes).map(d -> d * elm.getWeight()).toArray()
                        : dummyVotes;
            }).collect(Collectors.toList());
            for(double[] votes: elmVotes)
            {
                for(int j = 0; j < votes.length; ++j) ensembleVotes[j] += votes[j];
            }
        }
        catch(Exception e){}
        return ensembleVotes;
    }

    @Override
    public void resetLearningImpl()
    {
        initialBatchSize = ((OS_ELM)getPreparedClassOption(ensembleClassifier)).initialBatchSize.getValue()
                * ensembleSize.getValue();
        initialBatch = new ArrayList<>();
        ensemble = new ArrayList<>();
        welms = new ArrayList<>();
        classCount = new int[2];
        instanceCount = 0;
        confusionMatrix = new int[2][2];
        majorityLernerIndex = 0;
        minorityLearnerIndex = 0;
        ensembleGMean = Double.NaN;
        ensembleVotes = null;
        learn = this::createElmStore;
    }

    @Override
    public void trainOnInstanceImpl(Instance instance)
    {
        learn.accept(instance);
    }

    @Override
    protected Measurement[] getModelMeasurementsImpl()
    {
        return new Measurement[0];
    }

    @Override
    public void getModelDescription(StringBuilder stringBuilder, int i) {}

    @Override
    public boolean isRandomizable()
    {
        return false;
    }

    private void createElmStore(Instance instance)
    {
        int inputSize = ELM.getInstanceData(instance).columns();
        int hiddenNeuronCount = ((WELM)getPreparedClassOption(elmStoreClassifier)).hiddenNeuronsCount.getValue();
        INDArray alpha = Nd4j.rand(inputSize, hiddenNeuronCount).muli(2).addi(-1);
        INDArray bias = Nd4j.rand(1, hiddenNeuronCount).muli(2).addi(-1);
        elmStore = new ELMStore(alpha, bias);
        learn = this::initialize;
        learn.accept(instance);
    }

    private void initialize(Instance instance)
    {
        elmStore.addInstance(instance);
        initialBatch.add(instance);
        classCount[(int)instance.classValue()] += 1;
        if(initialBatch.size() == initialBatchSize)
        {
            int _ensembleSize = ensembleSize.getValue(),
                smaller_class = (classCount[0] <= classCount[1])? 0: 1,
                imbalanceRatio = classCount[1 - smaller_class] / classCount[smaller_class];
            List<List<Instance>> batches = IntStream.range(0, _ensembleSize)
                .mapToObj(i -> new ArrayList<Instance>())
                .collect(Collectors.toList());

            for(Instance inst: initialBatch)
            {
                if(inst.classValue() == smaller_class)
                {
                    for(int i = 0; i < imbalanceRatio; ++i)
                    {
                        batches.get(minorityLearnerIndex).add(inst);
                        minorityLearnerIndex = (minorityLearnerIndex + 1) % ensembleSize.getValue();
                    }
                }
                else
                {
                    batches.get(majorityLernerIndex).add(inst);
                    majorityLernerIndex = (majorityLernerIndex + 1) % ensembleSize.getValue();
                }
            }
            OS_ELM elm_model = (OS_ELM)getPreparedClassOption(ensembleClassifier);
            for(int i = 0; i < ensembleSize.getValue(); ++i)
            {
                OS_ELM elm = (OS_ELM)elm_model.copy();
                elm.initialize(batches.get(i));
                ensemble.add(new BundledElm(elm));
            }
            initialBatch.clear();
            learn = this::trainPerInstance;
            getVotesForInstance(instance);
            learn.accept(instance);
        }
    }

    private void trainPerInstance(Instance instance)
    {
        int trueClass = (int)instance.classValue(), predClass = ensembleVotes[0] > ensembleVotes[1]? 0: 1;
        elmStore.addInstance(instance);
        classCount[trueClass] += 1;
        confusionMatrix[trueClass][predClass] += 1;
        int _ensembleSize = ensembleSize.getValue(),
            smallerClass = (classCount[0] <= classCount[1])? 0: 1,
            imbalanceRatio = classCount[1 - smallerClass] / classCount[smallerClass];
        if(trueClass == smallerClass)
        {
            try
            {
                int elmCount = imbalanceRatio > _ensembleSize? _ensembleSize: imbalanceRatio;
                IntStream.range(0, elmCount)
                        .mapToObj(i -> ensemble.get((minorityLearnerIndex + i) % _ensembleSize))
                        .forEach(elm -> elm.trainOnInstance(instance));
                minorityLearnerIndex = (minorityLearnerIndex + elmCount) % _ensembleSize;
            }
            catch(Exception e){}
        }
        else
        {
            ensemble.get(majorityLernerIndex).trainOnInstance(instance);
            majorityLernerIndex = (majorityLernerIndex + 1) % ensembleSize.getValue();
        }

        welms.forEach(elm -> elm.trainOnInstance(instance));

        if(++instanceCount % period.getValue() == 0)
        {
            ensemble.forEach(elm -> {elm.updateGMean(); elm.updateWeight(); elm.resetConfusionMatrix();});
            welms.forEach(elm -> {elm.updateGMean(); elm.resetConfusionMatrix();});
            double norm = ensemble.stream().max(Comparator.comparingDouble(BundledElm::getWeight)).get().getWeight();
            ensemble.forEach(elm -> elm.weight /= norm);

            double lowestCanVoteGMean = ensemble.stream().filter(BundledElm::canVote)
                    .min(Comparator.comparingDouble(BundledElm::getGMean)).get().getGMean();
            ensemble.stream().filter(elm -> elm.getGMean() >= lowestCanVoteGMean)
                    .forEach(BundledElm::setCanVote);

            double newEnsembleGMean = GMean(confusionMatrix);

            if(detectDrift(newEnsembleGMean))
            {
                ensemble.forEach(BundledElm::unsetCanVote);
                BundledElm welm = new BundledElm(elmStore.createWELM());
                welms.add(welm);
                BundledElm bestElm = ensemble.stream().max(Comparator.comparingDouble(BundledElm::getGMean)).get(),
                        worstElm = ensemble.stream().min(Comparator.comparingDouble(BundledElm::getGMean)).get();
                worstElm.initialize(welm);
                elmStore = new ELMStore(bestElm.elm.getAlphaDup(), bestElm.elm.getBiasDup());
            }
            else if(welms.size() > 0)
            {
                BundledElm bestWelm = welms.stream().max(Comparator.comparingDouble(BundledElm::getGMean)).get(),
                        worstEnsembleElm = ensemble.stream().min(Comparator.comparingDouble(BundledElm::getGMean)).get();
                if(bestWelm.getGMean() > worstEnsembleElm.getGMean())
                {
                    worstEnsembleElm.initialize(bestWelm);
                }
            }

            ensembleGMean = newEnsembleGMean;
            confusionMatrix = new int[2][2];
        }
    }

    private boolean detectDrift(double newGMean)
    {
        boolean isSuddenDrift = newGMean < suddenDriftThreshold.getValue() * ensembleGMean;
        boolean isIncrementalDrift = false;
        /*
        Values of p should estimate the conditional probability of occurrence of newGMean under condition that
        underlying gaussian distribution did not changed(H0) or changed(H1). The issue is that the values of p do not
        fall into the expected range [0, 1].
         */
//        if(gMeans.size() >= 6 && gMeans.size() % 2 == 0)
//        {
//            double PI = 3.1415926535;
//            INDArray vals = Nd4j.create(gMeans.stream().mapToDouble(d -> d).toArray(), new int[]{2, gMeans.size() / 2}),
//                    var = vals.var(1),
//                    mean = vals.mean(1),
//                    x = Transforms.pow(var.mul(2 * PI), -0.5),
//                    y = Transforms.exp(Transforms.pow(Nd4j.create(new double[]{newGMean, newGMean}).sub(mean), 2).div(var.mul(2))),
//                    p = x.mul(y);
//            isIncrementalDrift = (p.getDouble(1) / p.getDouble(0)) >= 3;
//            if(isSuddenDrift || isIncrementalDrift) gMeans.clear();
//        }
//        gMeans.add(newGMean);

        return isSuddenDrift || isIncrementalDrift;
    }

    private double GMean(int[][] confusionMatrix)
    {
        return Math.sqrt(((double)confusionMatrix[0][0] / (confusionMatrix[0][0] + confusionMatrix[0][1]))
                * ((double)confusionMatrix[1][1] / (confusionMatrix[1][1] + confusionMatrix[1][0])));
    }

    private class BundledElm
    {
        private ELM elm;
        private int[][] confusionMatrix;
        private double weight;
        private double gmean;
        private boolean canVote;

        public BundledElm(ELM newElm)
        {
            elm = newElm;
            confusionMatrix = new int[2][2];
            weight = 1.0;
            gmean = Double.NaN;
            canVote = true;
        }

        public void initialize(BundledElm newElm)
        {
            elm.initialize(newElm.elm);
            confusionMatrix = new int[2][2];
            weight = 1.0;
            gmean = Double.NaN;
            canVote = true;
        }

        public double[] getVotesForInstance(Instance instance)
        {
            double[] votes = elm.getVotesForInstance(instance);
            confusionMatrix[(int)instance.classValue()][votes[0] > votes[1]? 0: 1] += 1;
            return votes;
        }

        public void trainOnInstance(Instance instance) {
            elm.trainOnInstance(instance);
        }

        public void setCanVote()
        {
            canVote = true;
        }

        public void unsetCanVote()
        {
            canVote = false;
        }

        public boolean canVote()
        {
            return canVote;
        }

        public double getWeight()
        {
            return weight;
        }

        public void updateWeight()
        {
            weight *= GMean(confusionMatrix);
        }

        public double getGMean()
        {
            return gmean;
        }

        public void updateGMean()
        {
            gmean = GMean(confusionMatrix);
        }

        public void resetConfusionMatrix()
        {
            confusionMatrix = new int[2][2];
        }
    }

    private class ELMStore
    {
        private int[] classCount;
        private INDArray alpha, bias;
        private INDArray[] K, Q;

        public ELMStore(INDArray alpha, INDArray bias)
        {
            classCount = new int[2];
            this.alpha = alpha;
            this.bias = bias;
            K = new INDArray[] {
                    Nd4j.zeros(alpha.columns(), alpha.columns()),
                    Nd4j.zeros(alpha.columns(), alpha.columns())};
            Q = new INDArray[] {
                    Nd4j.zeros(alpha.columns(), 2),
                    Nd4j.zeros(alpha.columns(), 2)};
        }

        public void addInstance(Instance instance)
        {
            INDArray ht = Transforms.sigmoid(ELM.getInstanceData(instance).mmul(alpha).add(bias));
            INDArray tt = ELM.getInstanceTarget(instance);
            int classIndex = (int)instance.classValue();
            K[classIndex].addi(ht.transpose().mmul(ht));
            Q[classIndex].addi(ht.transpose().mmul(tt));
            classCount[classIndex] += 1;
        }

        public WELM createWELM()
        {
            WELM welm = (WELM)((WELM)getPreparedClassOption(elmStoreClassifier)).copy();
            welm.initialize(K[0].mul(1.0/classCount[0]).add(K[1].mul(1.0/classCount[1])),
                    Q[0].mul(1.0/classCount[0]).add(Q[1].mul(1.0/classCount[1])),
                    alpha, bias);
            return welm;
        }

    }
}
