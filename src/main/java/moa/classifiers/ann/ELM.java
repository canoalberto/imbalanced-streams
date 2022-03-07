
/*
 * Author: Artur Szumaczuk (artur.szumaczuk@gmail.com)
 * Implementation of Extreme Learning Machine as in
 * Huang G.-B. et all “Extreme learning machine: Theory and applications"
 * Neurocomputing, vol. 70, pp. 489-501, 2006
 */

package moa.classifiers.ann;

import com.github.javacliparser.FlagOption;
import com.github.javacliparser.FloatOption;
import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Attribute;
import com.yahoo.labs.samoa.instances.Instance;
import linalg.Linalg;
import moa.classifiers.AbstractClassifier;
import moa.classifiers.MultiClassClassifier;
import moa.core.Measurement;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.inverse.InvertMatrix;
import org.nd4j.linalg.ops.transforms.Transforms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ELM extends AbstractClassifier implements MultiClassClassifier
{
    public interface SerializableFunction<T,R> extends Function<T,R>, Serializable {}

    public interface SerializableConsumer<T> extends Consumer<T>, Serializable {}

    public IntOption initialBatchSize = new IntOption("initialBatchSize", 'i',
        "The number of instances used to initialize ELM", 1000);

    public IntOption hiddenNeuronsCount = new IntOption("hiddenNeuronsCount", 'h',
        "The number of hidden neurons of SLFN", 100);

    public FlagOption usePesudoinverse = new FlagOption("usePesudoinverse", 'p',
        "Use Moore-Penrose inverse to calculate the inverse of the matrix");

    public FloatOption epsilon = new FloatOption("epsilon", 'e',
        "Very small threshold value used for calculation", 0.00001);

    protected SerializableConsumer<Instance> learn;

    protected SerializableFunction<INDArray, INDArray> invert;

    protected INDArray alpha, beta, bias, P, X, T;

    protected int currBatchInstanceCount;

    @Override
    public String getPurposeString() {
        return "Extreme Learning Machine for multiclass classification";
    }

    @Override
    public double[] getVotesForInstance(Instance instance) {
        if(beta == null) return new double[0];
		return Transforms.sigmoid(getInstanceData(instance).mmul(alpha).add(bias)).mmul(beta).data().asDouble();
    }

    @Override
    public void resetLearningImpl() {
        alpha = null;
        beta = null;
        bias = null;
        P = null;
        X = null;
        T = null;
        currBatchInstanceCount = 0;
        learn = this::allocateResources;
        invert = usePesudoinverse.isSet()
            ? (INDArray x) -> Linalg.MoorePenroseInverse(x, epsilon.getValue())
            : (INDArray x) -> InvertMatrix.invert(x, false);
    }

    @Override
    public void trainOnInstanceImpl(Instance instance) {
        learn.accept(instance);
    }

    @Override
    protected Measurement[] getModelMeasurementsImpl() {
        return new Measurement[0];
    }

    @Override
    public void getModelDescription(StringBuilder out, int i) {}

    @Override
    public boolean isRandomizable() {
        return false;
    }

    public void initialize(ELM elm)
    {
        resetLearningImpl();
        alpha = elm.alpha.dup();
        bias = elm.bias.dup();
        P = elm.P.dup();
        beta = elm.beta.dup();
        learn = this::NoOp;
    }

    public void initialize(List<Instance> initialBatch)
    {
        initialBatchSize.setValue(initialBatch.size());
        resetLearningImpl();
        for(Instance instance: initialBatch) {
            learn.accept(instance);
        }
    }

    public INDArray getAlphaDup()
    {
        return alpha.dup();
    }

    public INDArray getBiasDup()
    {
        return bias.dup();
    }

    public static INDArray getInstanceData(Instance instance)
    {
        List<Double> input = new ArrayList<>();
        for(int i = 0; i < instance.numInputAttributes(); ++i)
        {
            int attrIndex = modelAttIndexToInstanceAttIndex(i, instance);
            Attribute attr = instance.attribute(attrIndex);
            if(attr.isNumeric())
            {
                input.add(instance.value(attrIndex));
            }
            else
            {
                int startIndex = input.size(), valueCount = attr.numValues();
                for(int j = 0; j < valueCount; ++j) input.add(0.0);
                input.set(startIndex + (int)instance.value(attrIndex), 1.0);
            }
        }
        
        // TODO: PATCH TO FIX ARRAY TO MATRIX AS INPUT
        float[][] data = new float[1][input.size()];
        
        for(int i = 0; i < input.size(); i++)
        	data[0][i] = input.get(i).floatValue();
        
        return Nd4j.create(data);
    }

    public static INDArray getInstanceTarget(Instance instance)
    {
    	float[][] data = new float[1][instance.numClasses()];
    	data[0][(int)instance.classValue()] = 1.0f;
        return Nd4j.create(data);
    }

    protected void allocateResources(Instance instance)
    {
        int inputSize = getInstanceData(instance).columns();
        int hidCnt = hiddenNeuronsCount.getValue();
        int iniCnt = initialBatchSize.getValue();
        alpha = Nd4j.rand(inputSize, hidCnt).muli(2).addi(-1);
        bias = Nd4j.rand(1, hidCnt).muli(2).addi(-1);
        X = Nd4j.zeros(iniCnt, inputSize);
        T = Nd4j.zeros(iniCnt, instance.numClasses());
        learn = this::initialize;
        learn.accept(instance);
    }

    protected void initialize(Instance instance)
    {
        if(fill(instance))
        {
            update();
            learn = this::NoOp;
        }
    }

    protected void NoOp(Instance instance){}

    protected boolean fill(Instance instance)
    {
        X.putRow(currBatchInstanceCount, getInstanceData(instance));
        T.putRow(currBatchInstanceCount, getInstanceTarget(instance));
        return ++currBatchInstanceCount == X.rows();
    }

    protected void update()
    {
        INDArray H = Transforms.sigmoid(X.mmul(alpha).addiRowVector(bias)), Ht = H.transpose();
        P = invert.apply(Ht.mmul(H));
        beta = P.mmul(Ht).mmul(T);
        currBatchInstanceCount = 0;
    }
}
