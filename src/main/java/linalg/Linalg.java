package linalg;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class Linalg
{
    public static INDArray MoorePenroseInverse(INDArray matrix, double epsilon)
    {
        int rows = matrix.rows(), cols = matrix.columns();
        INDArray S = Nd4j.zeros(rows < cols ? rows : cols);
        INDArray U = Nd4j.zeros(rows, rows);
        INDArray Vt = Nd4j.zeros(cols, cols);
        Nd4j.getBlasWrapper().lapack().gesvd(matrix.dup(), S, U, Vt);
        for(int i = 0; i < S.columns(); ++i)
        {
            double cellValue = S.getDouble(i);
            S.putScalar(i, Math.abs(cellValue) < epsilon ? 0.0 : 1.0 / cellValue);
        }
        return Vt.transpose().mmul(Nd4j.diag(S)).mmul(U.transpose());
    }
}
