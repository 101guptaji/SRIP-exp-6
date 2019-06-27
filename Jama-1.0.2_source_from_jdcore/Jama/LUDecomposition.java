package Jama;

import java.io.Serializable;





































public class LUDecomposition
  implements Serializable
{
  private double[][] LU;
  private int m;
  private int n;
  private int pivsign;
  private int[] piv;
  
  public LUDecomposition(Matrix paramMatrix)
  {
    LU = paramMatrix.getArrayCopy();
    m = paramMatrix.getRowDimension();
    n = paramMatrix.getColumnDimension();
    piv = new int[m];
    for (int i = 0; i < m; i++) {
      piv[i] = i;
    }
    pivsign = 1;
    
    double[] arrayOfDouble2 = new double[m];
    


    for (int j = 0; j < n; j++)
    {


      for (int k = 0; k < m; k++) {
        arrayOfDouble2[k] = LU[k][j];
      }
      
      double d;
      
      for (k = 0; k < m; k++) {
        double[] arrayOfDouble1 = LU[k];
        


        i1 = Math.min(k, j);
        d = 0.0D;
        for (int i2 = 0; i2 < i1; i2++) {
          d += arrayOfDouble1[i2] * arrayOfDouble2[i2];
        }
        
        int tmp185_183 = k; double[] tmp185_182 = arrayOfDouble2; double tmp190_189 = (tmp185_182[tmp185_183] - d);tmp185_182[tmp185_183] = tmp190_189;arrayOfDouble1[j] = tmp190_189;
      }
      


      k = j;
      for (int i1 = j + 1; i1 < m; i1++) {
        if (Math.abs(arrayOfDouble2[i1]) > Math.abs(arrayOfDouble2[k])) {
          k = i1;
        }
      }
      if (k != j) {
        for (i1 = 0; i1 < n; i1++) {
          d = LU[k][i1];LU[k][i1] = LU[j][i1];LU[j][i1] = d;
        }
        i1 = piv[k];piv[k] = piv[j];piv[j] = i1;
        pivsign = (-pivsign);
      }
      


      if (((j < m ? 1 : 0) & (LU[j][j] != 0.0D ? 1 : 0)) != 0) {
        for (i1 = j + 1; i1 < m; i1++) {
          LU[i1][j] /= LU[j][j];
        }
      }
    }
  }
  


































































  public boolean isNonsingular()
  {
    for (int i = 0; i < n; i++) {
      if (LU[i][i] == 0.0D)
        return false;
    }
    return true;
  }
  



  public Matrix getL()
  {
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (i > j) {
          arrayOfDouble[i][j] = LU[i][j];
        } else if (i == j) {
          arrayOfDouble[i][j] = 1.0D;
        } else {
          arrayOfDouble[i][j] = 0.0D;
        }
      }
    }
    return localMatrix;
  }
  



  public Matrix getU()
  {
    Matrix localMatrix = new Matrix(n, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i <= j) {
          arrayOfDouble[i][j] = LU[i][j];
        } else {
          arrayOfDouble[i][j] = 0.0D;
        }
      }
    }
    return localMatrix;
  }
  



  public int[] getPivot()
  {
    int[] arrayOfInt = new int[m];
    for (int i = 0; i < m; i++) {
      arrayOfInt[i] = piv[i];
    }
    return arrayOfInt;
  }
  



  public double[] getDoublePivot()
  {
    double[] arrayOfDouble = new double[m];
    for (int i = 0; i < m; i++) {
      arrayOfDouble[i] = piv[i];
    }
    return arrayOfDouble;
  }
  




  public double det()
  {
    if (m != n) {
      throw new IllegalArgumentException("Matrix must be square.");
    }
    double d = pivsign;
    for (int i = 0; i < n; i++) {
      d *= LU[i][i];
    }
    return d;
  }
  






  public Matrix solve(Matrix paramMatrix)
  {
    if (paramMatrix.getRowDimension() != m) {
      throw new IllegalArgumentException("Matrix row dimensions must agree.");
    }
    if (!isNonsingular()) {
      throw new RuntimeException("Matrix is singular.");
    }
    

    int i = paramMatrix.getColumnDimension();
    Matrix localMatrix = paramMatrix.getMatrix(piv, 0, i - 1);
    double[][] arrayOfDouble = localMatrix.getArray();
    int k;
    int i1;
    for (int j = 0; j < n; j++) {
      for (k = j + 1; k < n; k++) {
        for (i1 = 0; i1 < i; i1++) {
          arrayOfDouble[k][i1] -= arrayOfDouble[j][i1] * LU[k][j];
        }
      }
    }
    
    for (j = n - 1; j >= 0; j--) {
      for (k = 0; k < i; k++) {
        arrayOfDouble[j][k] /= LU[j][j];
      }
      for (k = 0; k < j; k++) {
        for (i1 = 0; i1 < i; i1++) {
          arrayOfDouble[k][i1] -= arrayOfDouble[j][i1] * LU[k][j];
        }
      }
    }
    return localMatrix;
  }
}
