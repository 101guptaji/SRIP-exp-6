package Jama;

import java.io.Serializable;


































public class CholeskyDecomposition
  implements Serializable
{
  private double[][] L;
  private int n;
  private boolean isspd;
  
  public CholeskyDecomposition(Matrix paramMatrix)
  {
    double[][] arrayOfDouble = paramMatrix.getArray();
    n = paramMatrix.getRowDimension();
    L = new double[n][n];
    isspd = (paramMatrix.getColumnDimension() == n);
    
    for (int i = 0; i < n; i++) {
      double[] arrayOfDouble1 = L[i];
      double d1 = 0.0D;
      for (int j = 0; j < i; j++) {
        double[] arrayOfDouble2 = L[j];
        double d2 = 0.0D;
        for (int k = 0; k < j; k++) {
          d2 += arrayOfDouble2[k] * arrayOfDouble1[k];
        }
        double tmp151_150 = ((arrayOfDouble[i][j] - d2) / L[j][j]);d2 = tmp151_150;arrayOfDouble1[j] = tmp151_150;
        d1 += d2 * d2;
        isspd &= arrayOfDouble[j][i] == arrayOfDouble[i][j];
      }
      d1 = arrayOfDouble[i][i] - d1;
      isspd &= d1 > 0.0D;
      L[i][i] = Math.sqrt(Math.max(d1, 0.0D));
      for (j = i + 1; j < n; j++) {
        L[i][j] = 0.0D;
      }
    }
  }
  





































































  public boolean isSPD()
  {
    return isspd;
  }
  



  public Matrix getL()
  {
    return new Matrix(L, n, n);
  }
  






  public Matrix solve(Matrix paramMatrix)
  {
    if (paramMatrix.getRowDimension() != n) {
      throw new IllegalArgumentException("Matrix row dimensions must agree.");
    }
    if (!isspd) {
      throw new RuntimeException("Matrix is not symmetric positive definite.");
    }
    

    double[][] arrayOfDouble = paramMatrix.getArrayCopy();
    int i = paramMatrix.getColumnDimension();
    int k;
    int m;
    for (int j = 0; j < n; j++) {
      for (k = 0; k < i; k++) {
        for (m = 0; m < j; m++) {
          arrayOfDouble[j][k] -= arrayOfDouble[m][k] * L[j][m];
        }
        arrayOfDouble[j][k] /= L[j][j];
      }
    }
    

    for (j = n - 1; j >= 0; j--) {
      for (k = 0; k < i; k++) {
        for (m = j + 1; m < n; m++) {
          arrayOfDouble[j][k] -= arrayOfDouble[m][k] * L[m][j];
        }
        arrayOfDouble[j][k] /= L[j][j];
      }
    }
    

    return new Matrix(arrayOfDouble, n, i);
  }
}
