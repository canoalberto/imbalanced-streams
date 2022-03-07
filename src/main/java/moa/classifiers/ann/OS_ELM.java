
/*
 * Author: Artur Szumaczuk (artur.szumaczuk@gmail.com)
 * Implementation of Online Sequential Extreme Learning Machine as in
 * Liang N.-Y. et all “A Fast and Accurate Online Sequential Learning Algorithm for Feedforward Networks"
 * IEEE Transactions on Neural Networks, vol. 17, no. 6, pp. 1411-1423, 2006
 */

package moa.classifiers.ann;

import com.github.javacliparser.IntOption;
import com.yahoo.labs.samoa.instances.Instance;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

public class OS_ELM extends ELM {

    public IntOption batchSize = new IntOption("batchSize", 'b',
        "The size of the batch to train the classifier", 1);

    @Override
    public String getPurposeString() {
        return "Online Sequential Extreme Learning Machine for multiclass classification";
    }

    @Override
    public void initialize(ELM elm)
    {
        super.initialize(elm);
        setLearningMethod();
    }

    @Override
    protected void initialize(Instance instance)
    {
        super.initialize(instance);
        if(currBatchInstanceCount == 0)
        {
            setLearningMethod();
        }
    }

    private void setLearningMethod()
    {
        int size = batchSize.getValue();
        if(size == 1)
        {
            learn = this::trainPerInstance;
            X = null;
            T = null;
        }
        else
        {
            learn = this::trainPerBatch;
            X = Nd4j.zeros(size, alpha.rows());
            T = Nd4j.zeros(size, beta.columns());
        }
    }

    private void trainPerInstance(Instance instance)
    {
        INDArray h = Transforms.sigmoid(getInstanceData(instance).mmul(alpha).add(bias).transpose());
        INDArray tt = getInstanceTarget(instance);
        P = P.sub(P.mmul(h).mmul(h.transpose()).mmul(P).div(1 + h.transpose().mmul(P).mmul(h).getDouble(0,0)));
        beta = beta.add(P.mmul(h).mmul(tt.sub(h.transpose().mmul(beta))));
    }

    private void trainPerBatch(Instance instance)
    {
        if(fill(instance))
        {
            INDArray H = Transforms.sigmoid(X.mmul(alpha).addiRowVector(bias)), Ht = H.transpose();
            P = P.sub(P.mmul(Ht).mmul(invert.apply(Nd4j.diag(Nd4j.ones(H.rows())).add(H.mmul(P).mmul(Ht)))).mmul(H).mmul(P));
            beta = beta.add(P.mmul(Ht).mmul(T.sub(H.mmul(beta))));
            currBatchInstanceCount = 0;
        }
    }
}
