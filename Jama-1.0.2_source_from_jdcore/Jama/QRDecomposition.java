package Jama;

import Jama.util.Maths;
import java.io.Serializable;



































public class QRDecomposition
  implements Serializable
{
  private double[][] QR;
  private int m;
  private int n;
  private double[] Rdiag;
  
  public QRDecomposition(Matrix paramMatrix)
  {
    QR = paramMatrix.getArrayCopy();
    m = paramMatrix.getRowDimension();
    n = paramMatrix.getColumnDimension();
    Rdiag = new double[n];
    

    for (int i = 0; i < n; i++)
    {
      double d1 = 0.0D;
      for (int j = i; j < m; j++) {
        d1 = Maths.hypot(d1, QR[j][i]);
      }
      
      if (d1 != 0.0D)
      {
        if (QR[i][i] < 0.0D) {
          d1 = -d1;
        }
        for (j = i; j < m; j++) {
          QR[j][i] /= d1;
        }
        QR[i][i] += 1.0D;
        

        for (j = i + 1; j < n; j++) {
          double d2 = 0.0D;
          for (int k = i; k < m; k++) {
            d2 += QR[k][i] * QR[k][j];
          }
          d2 = -d2 / QR[i][i];
          for (k = i; k < m; k++) {
            QR[k][j] += d2 * QR[k][i];
          }
        }
      }
      Rdiag[i] = (-d1);
    }
  }
  







  public boolean isFullRank()
  {
    for (int i = 0; i < n; i++) {
      if (Rdiag[i] == 0.0D)
        return false;
    }
    return true;
  }
  



  public Matrix getH()
  {
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (i >= j) {
          arrayOfDouble[i][j] = QR[i][j];
        } else {
          arrayOfDouble[i][j] = 0.0D;
        }
      }
    }
    return localMatrix;
  }
  



  public Matrix getR()
  {
    Matrix localMatrix = new Matrix(n, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i < j) {
          arrayOfDouble[i][j] = QR[i][j];
        } else if (i == j) {
          arrayOfDouble[i][j] = Rdiag[i];
        } else {
          arrayOfDouble[i][j] = 0.0D;
        }
      }
    }
    return localMatrix;
  }
  



  public Matrix getQ()
  {
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = n - 1; i >= 0; i--) {
      for (int j = 0; j < m; j++) {
        arrayOfDouble[j][i] = 0.0D;
      }
      arrayOfDouble[i][i] = 1.0D;
      for (j = i; j < n; j++) {
        if (QR[i][i] != 0.0D) {
          double d = 0.0D;
          for (int k = i; k < m; k++) {
            d += QR[k][i] * arrayOfDouble[k][j];
          }
          d = -d / QR[i][i];
          for (k = i; k < m; k++) {
            arrayOfDouble[k][j] += d * QR[k][i];
          }
        }
      }
    }
    return localMatrix;
  }
  






  public Matrix solve(Matrix paramMatrix)
  {
    if (paramMatrix.getRowDimension() != m) {
      throw new IllegalArgumentException("Matrix row dimensions must agree.");
    }
    if (!isFullRank()) {
      throw new RuntimeException("Matrix is rank deficient.");
    }
    

    int i = paramMatrix.getColumnDimension();
    double[][] arrayOfDouble = paramMatrix.getArrayCopy();
    
    int k;
    for (int j = 0; j < n; j++) {
      for (k = 0; k < i; k++) {
        double d = 0.0D;
        for (int i2 = j; i2 < m; i2++) {
          d += QR[i2][j] * arrayOfDouble[i2][k];
        }
        d = -d / QR[j][j];
        for (i2 = j; i2 < m; i2++) {
          arrayOfDouble[i2][k] += d * QR[i2][j];
        }
      }
    }
    
    for (j = n - 1; j >= 0; j--) {
      for (k = 0; k < i; k++) {
        arrayOfDouble[j][k] /= Rdiag[j];
      }
      for (k = 0; k < j; k++) {
        for (int i1 = 0; i1 < i; i1++) {
          arrayOfDouble[k][i1] -= arrayOfDouble[j][i1] * QR[k][j];
        }
      }
    }
    return new Matrix(arrayOfDouble, n, i).getMatrix(0, n - 1, 0, i - 1);
  }
}
