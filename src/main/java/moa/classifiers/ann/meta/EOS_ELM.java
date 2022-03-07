
/*
 * Author: Artur Szumaczuk (artur.szumaczuk@gmail.com)
 * Implementation of Ensemble of Online Sequential Extreme Learning Machine as in
 * Lan Y. et all “Ensemble of online sequential extreme learning machine"
 * Neurocomputing, vol. 72, pp. 3391-3395, 2009
 */

package moa.classifiers.ann.meta;

import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import moa.classifiers.AbstractClassifier;
import moa.classifiers.MultiClassClassifier;
import moa.classifiers.ann.OS_ELM;
import moa.core.Measurement;
import moa.options.ClassOption;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EOS_ELM extends AbstractClassifier implements MultiClassClassifier {

    public ClassOption classifier = new ClassOption("classifier", 'c',
           "Classifier used in ensemble", OS_ELM.class, "moa.classifiers.ann.OS_ELM");

    public IntOption ensembleSize = new IntOption("ensembleSize", 's',
            "The number of OS-ELMs in the main ensemble", 10);

    public IntOption threadCount = new IntOption("threadCount", 't',
            "The number of threads to use for calculations", 4);

    public IntOption instanceLimit = new IntOption("instanceLimit", 'l',
            "The number of instances after which parallel executioner is disposed", 200000);

    private List<OS_ELM> ensemble = null;

    private int instanceCount, initialBatchSize;

    private ExecutorService executor;

    @Override
    public String getPurposeString() {
        return "Ensemble of Online Sequential Extreme Learning Machine";
    }

    @Override
    public double[] getVotesForInstance(Instance instance) {
        if(instanceCount < initialBatchSize) return new double[0];
        int numClasses = instance.numClasses();
        double[] output = new double[numClasses];
        try
        {
            List<Future<double[]>> ensembleVotes = executor.invokeAll(ensemble.stream()
                    .<Callable<double[]>>map(elm -> ()-> elm.getVotesForInstance(instance))
                    .collect(Collectors.toList()));
            for(Future<double[]> elmVote: ensembleVotes)
            {
                double[] votes = elmVote.get();
                for(int j = 0; j < votes.length; ++j) output[j] += votes[j];
            }
            for(int i = 0; i < numClasses; ++i) output[i] /= ensembleSize.getValue();
        }
        catch(Exception e){}
        return output;
    }

    @Override
    public void resetLearningImpl() {
        OS_ELM os_elm = (OS_ELM)getPreparedClassOption(classifier);
        os_elm.resetLearning();
        instanceCount = 0;
        initialBatchSize = os_elm.initialBatchSize.getValue();
        executor = Executors.newFixedThreadPool(threadCount.getValue());
        ensemble = IntStream.range(0, ensembleSize.getValue())
            .mapToObj(i -> (OS_ELM)os_elm.copy())
            .collect(Collectors.toList());
    }

    @Override
    public void trainOnInstanceImpl(Instance instance) {
        try
        {
            executor.invokeAll(ensemble.stream()
                    .<Callable<Void>>map(elm-> ()->{elm.trainOnInstance(instance); return null;})
                    .collect(Collectors.toList()));
        }
        catch(Exception e){}

        if(++instanceCount == instanceLimit.getValue())
        {
            executor.shutdown();
        }
    }

    @Override
    protected Measurement[] getModelMeasurementsImpl() {
        return new Measurement[0];
    }

    @Override
    public void getModelDescription(StringBuilder stringBuilder, int i) {

    }

    @Override
    public boolean isRandomizable() {
        return false;
    }
}