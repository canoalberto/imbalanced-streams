
/*
 * Author: Artur Szumaczuk (artur.szumaczuk@gmail.com)
 * Implementation of Weighted Extreme Learning Machine as in
 * Zong W. et all “Weighted extreme learning machine for imbalance learning"
 * Neurocomputing, vol. 101, pp. 229-242, 2013
 */

package moa.classifiers.ann;

import com.yahoo.labs.samoa.instances.Instance;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.ops.transforms.Transforms;

public class WELM extends ELM {

    protected int topX, botX;

    @Override
    public String getPurposeString() {
        return "Weighted Extreme Learning Machine for binary classification";
    }

    public void initialize(INDArray K, INDArray Q, INDArray alpha, INDArray bias)
    {
        resetLearningImpl();
        P = invert.apply(K);
        beta = P.mmul(Q);
        this.alpha = alpha;
        this.bias = bias;
        learn = this::NoOp;
    }

    @Override
    public void resetLearningImpl() {
        super.resetLearningImpl();
        topX = 0;
        botX = initialBatchSize.getValue() - 1;
    }

    @Override
    protected boolean fill(Instance instance)
    {
        int rowIndex = instance.classValue() == 1.0 ? topX++ : botX--;
        X.putRow(rowIndex, getInstanceData(instance));
        T.putRow(rowIndex, getInstanceTarget(instance));
        return topX > botX;
    }

    @Override
    protected void update()
    {
        INDArray H = Transforms.sigmoid(X.mmul(alpha).addiRowVector(bias)), Ht = H.transpose();
        int diagSize = H.rows();
        float[] weights = new float[diagSize];
        for(int i = 0, w = topX; i < topX; ++i) weights[i] = 1.0f / w;
        for(int i = topX, w = diagSize - topX; i < diagSize; ++i) weights[i] = 1.0f / w;
        INDArray W = Nd4j.diag(Nd4j.create(weights));
        P = invert.apply(Ht.mmul(W).mmul(H));
        beta = P.mmul(Ht).mmul(W).mmul(T);
        topX = 0;
        botX = initialBatchSize.getValue() - 1;
    }
}