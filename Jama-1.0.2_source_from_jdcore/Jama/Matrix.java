package Jama;

import Jama.util.Maths;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StreamTokenizer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Vector;




























































public class Matrix
  implements Cloneable, Serializable
{
  private double[][] A;
  private int m;
  private int n;
  
  public Matrix(int paramInt1, int paramInt2)
  {
    m = paramInt1;
    n = paramInt2;
    A = new double[paramInt1][paramInt2];
  }
  





  public Matrix(int paramInt1, int paramInt2, double paramDouble)
  {
    m = paramInt1;
    n = paramInt2;
    A = new double[paramInt1][paramInt2];
    for (int i = 0; i < paramInt1; i++) {
      for (int j = 0; j < paramInt2; j++) {
        A[i][j] = paramDouble;
      }
    }
  }
  





  public Matrix(double[][] paramArrayOfDouble)
  {
    m = paramArrayOfDouble.length;
    n = paramArrayOfDouble[0].length;
    for (int i = 0; i < m; i++) {
      if (paramArrayOfDouble[i].length != n) {
        throw new IllegalArgumentException("All rows must have the same length.");
      }
    }
    A = paramArrayOfDouble;
  }
  





  public Matrix(double[][] paramArrayOfDouble, int paramInt1, int paramInt2)
  {
    A = paramArrayOfDouble;
    m = paramInt1;
    n = paramInt2;
  }
  





  public Matrix(double[] paramArrayOfDouble, int paramInt)
  {
    m = paramInt;
    n = (paramInt != 0 ? paramArrayOfDouble.length / paramInt : 0);
    if (paramInt * n != paramArrayOfDouble.length) {
      throw new IllegalArgumentException("Array length must be a multiple of m.");
    }
    A = new double[paramInt][n];
    for (int i = 0; i < paramInt; i++) {
      for (int j = 0; j < n; j++) {
        A[i][j] = paramArrayOfDouble[(i + j * paramInt)];
      }
    }
  }
  








  public static Matrix constructWithCopy(double[][] paramArrayOfDouble)
  {
    int i = paramArrayOfDouble.length;
    int j = paramArrayOfDouble[0].length;
    Matrix localMatrix = new Matrix(i, j);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int k = 0; k < i; k++) {
      if (paramArrayOfDouble[k].length != j) {
        throw new IllegalArgumentException("All rows must have the same length.");
      }
      
      for (int i1 = 0; i1 < j; i1++) {
        arrayOfDouble[k][i1] = paramArrayOfDouble[k][i1];
      }
    }
    return localMatrix;
  }
  


  public Matrix copy()
  {
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[i][j] = A[i][j];
      }
    }
    return localMatrix;
  }
  


  public Object clone()
  {
    return copy();
  }
  



  public double[][] getArray()
  {
    return A;
  }
  



  public double[][] getArrayCopy()
  {
    double[][] arrayOfDouble = new double[m][n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[i][j] = A[i][j];
      }
    }
    return arrayOfDouble;
  }
  



  public double[] getColumnPackedCopy()
  {
    double[] arrayOfDouble = new double[m * n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[(i + j * m)] = A[i][j];
      }
    }
    return arrayOfDouble;
  }
  



  public double[] getRowPackedCopy()
  {
    double[] arrayOfDouble = new double[m * n];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[(i * n + j)] = A[i][j];
      }
    }
    return arrayOfDouble;
  }
  



  public int getRowDimension()
  {
    return m;
  }
  



  public int getColumnDimension()
  {
    return n;
  }
  






  public double get(int paramInt1, int paramInt2)
  {
    return A[paramInt1][paramInt2];
  }
  








  public Matrix getMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Matrix localMatrix = new Matrix(paramInt2 - paramInt1 + 1, paramInt4 - paramInt3 + 1);
    double[][] arrayOfDouble = localMatrix.getArray();
    try {
      for (int i = paramInt1; i <= paramInt2; i++) {
        for (int j = paramInt3; j <= paramInt4; j++) {
          arrayOfDouble[(i - paramInt1)][(j - paramInt3)] = A[i][j];
        }
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
    return localMatrix;
  }
  






  public Matrix getMatrix(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    Matrix localMatrix = new Matrix(paramArrayOfInt1.length, paramArrayOfInt2.length);
    double[][] arrayOfDouble = localMatrix.getArray();
    try {
      for (int i = 0; i < paramArrayOfInt1.length; i++) {
        for (int j = 0; j < paramArrayOfInt2.length; j++) {
          arrayOfDouble[i][j] = A[paramArrayOfInt1[i]][paramArrayOfInt2[j]];
        }
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
    return localMatrix;
  }
  







  public Matrix getMatrix(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    Matrix localMatrix = new Matrix(paramInt2 - paramInt1 + 1, paramArrayOfInt.length);
    double[][] arrayOfDouble = localMatrix.getArray();
    try {
      for (int i = paramInt1; i <= paramInt2; i++) {
        for (int j = 0; j < paramArrayOfInt.length; j++) {
          arrayOfDouble[(i - paramInt1)][j] = A[i][paramArrayOfInt[j]];
        }
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
    return localMatrix;
  }
  







  public Matrix getMatrix(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    Matrix localMatrix = new Matrix(paramArrayOfInt.length, paramInt2 - paramInt1 + 1);
    double[][] arrayOfDouble = localMatrix.getArray();
    try {
      for (int i = 0; i < paramArrayOfInt.length; i++) {
        for (int j = paramInt1; j <= paramInt2; j++) {
          arrayOfDouble[i][(j - paramInt1)] = A[paramArrayOfInt[i]][j];
        }
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
    return localMatrix;
  }
  






  public void set(int paramInt1, int paramInt2, double paramDouble)
  {
    A[paramInt1][paramInt2] = paramDouble;
  }
  







  public void setMatrix(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Matrix paramMatrix)
  {
    try
    {
      for (int i = paramInt1; i <= paramInt2; i++) {
        for (int j = paramInt3; j <= paramInt4; j++) {
          A[i][j] = paramMatrix.get(i - paramInt1, j - paramInt3);
        }
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
  }
  





  public void setMatrix(int[] paramArrayOfInt1, int[] paramArrayOfInt2, Matrix paramMatrix)
  {
    try
    {
      for (int i = 0; i < paramArrayOfInt1.length; i++) {
        for (int j = 0; j < paramArrayOfInt2.length; j++) {
          A[paramArrayOfInt1[i]][paramArrayOfInt2[j]] = paramMatrix.get(i, j);
        }
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
  }
  






  public void setMatrix(int[] paramArrayOfInt, int paramInt1, int paramInt2, Matrix paramMatrix)
  {
    try
    {
      for (int i = 0; i < paramArrayOfInt.length; i++) {
        for (int j = paramInt1; j <= paramInt2; j++) {
          A[paramArrayOfInt[i]][j] = paramMatrix.get(i, j - paramInt1);
        }
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
  }
  






  public void setMatrix(int paramInt1, int paramInt2, int[] paramArrayOfInt, Matrix paramMatrix)
  {
    try
    {
      for (int i = paramInt1; i <= paramInt2; i++) {
        for (int j = 0; j < paramArrayOfInt.length; j++) {
          A[i][paramArrayOfInt[j]] = paramMatrix.get(i - paramInt1, j);
        }
      }
    } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
      throw new ArrayIndexOutOfBoundsException("Submatrix indices");
    }
  }
  



  public Matrix transpose()
  {
    Matrix localMatrix = new Matrix(n, m);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[j][i] = A[i][j];
      }
    }
    return localMatrix;
  }
  



  public double norm1()
  {
    double d1 = 0.0D;
    for (int i = 0; i < n; i++) {
      double d2 = 0.0D;
      for (int j = 0; j < m; j++) {
        d2 += Math.abs(A[j][i]);
      }
      d1 = Math.max(d1, d2);
    }
    return d1;
  }
  



  public double norm2()
  {
    return new SingularValueDecomposition(this).norm2();
  }
  



  public double normInf()
  {
    double d1 = 0.0D;
    for (int i = 0; i < m; i++) {
      double d2 = 0.0D;
      for (int j = 0; j < n; j++) {
        d2 += Math.abs(A[i][j]);
      }
      d1 = Math.max(d1, d2);
    }
    return d1;
  }
  



  public double normF()
  {
    double d = 0.0D;
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        d = Maths.hypot(d, A[i][j]);
      }
    }
    return d;
  }
  



  public Matrix uminus()
  {
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[i][j] = (-A[i][j]);
      }
    }
    return localMatrix;
  }
  




  public Matrix plus(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[i][j] = (A[i][j] + A[i][j]);
      }
    }
    return localMatrix;
  }
  




  public Matrix plusEquals(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        A[i][j] += A[i][j];
      }
    }
    return this;
  }
  




  public Matrix minus(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[i][j] = (A[i][j] - A[i][j]);
      }
    }
    return localMatrix;
  }
  




  public Matrix minusEquals(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        A[i][j] -= A[i][j];
      }
    }
    return this;
  }
  




  public Matrix arrayTimes(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[i][j] = (A[i][j] * A[i][j]);
      }
    }
    return localMatrix;
  }
  




  public Matrix arrayTimesEquals(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        A[i][j] *= A[i][j];
      }
    }
    return this;
  }
  




  public Matrix arrayRightDivide(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[i][j] = (A[i][j] / A[i][j]);
      }
    }
    return localMatrix;
  }
  




  public Matrix arrayRightDivideEquals(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        A[i][j] /= A[i][j];
      }
    }
    return this;
  }
  




  public Matrix arrayLeftDivide(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[i][j] = (A[i][j] / A[i][j]);
      }
    }
    return localMatrix;
  }
  




  public Matrix arrayLeftDivideEquals(Matrix paramMatrix)
  {
    checkMatrixDimensions(paramMatrix);
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        A[i][j] /= A[i][j];
      }
    }
    return this;
  }
  




  public Matrix times(double paramDouble)
  {
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[i][j] = (paramDouble * A[i][j]);
      }
    }
    return localMatrix;
  }
  




  public Matrix timesEquals(double paramDouble)
  {
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        A[i][j] = (paramDouble * A[i][j]);
      }
    }
    return this;
  }
  





  public Matrix times(Matrix paramMatrix)
  {
    if (m != n) {
      throw new IllegalArgumentException("Matrix inner dimensions must agree.");
    }
    Matrix localMatrix = new Matrix(m, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    double[] arrayOfDouble1 = new double[n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble1[j] = A[j][i];
      }
      for (j = 0; j < m; j++) {
        double[] arrayOfDouble2 = A[j];
        double d = 0.0D;
        for (int k = 0; k < n; k++) {
          d += arrayOfDouble2[k] * arrayOfDouble1[k];
        }
        arrayOfDouble[j][i] = d;
      }
    }
    return localMatrix;
  }
  




  public LUDecomposition lu()
  {
    return new LUDecomposition(this);
  }
  




  public QRDecomposition qr()
  {
    return new QRDecomposition(this);
  }
  




  public CholeskyDecomposition chol()
  {
    return new CholeskyDecomposition(this);
  }
  




  public SingularValueDecomposition svd()
  {
    return new SingularValueDecomposition(this);
  }
  




  public EigenvalueDecomposition eig()
  {
    return new EigenvalueDecomposition(this);
  }
  




  public Matrix solve(Matrix paramMatrix)
  {
    return m == n ? new LUDecomposition(this).solve(paramMatrix) : new QRDecomposition(this).solve(paramMatrix);
  }
  





  public Matrix solveTranspose(Matrix paramMatrix)
  {
    return transpose().solve(paramMatrix.transpose());
  }
  



  public Matrix inverse()
  {
    return solve(identity(m, m));
  }
  



  public double det()
  {
    return new LUDecomposition(this).det();
  }
  



  public int rank()
  {
    return new SingularValueDecomposition(this).rank();
  }
  



  public double cond()
  {
    return new SingularValueDecomposition(this).cond();
  }
  



  public double trace()
  {
    double d = 0.0D;
    for (int i = 0; i < Math.min(m, n); i++) {
      d += A[i][i];
    }
    return d;
  }
  





  public static Matrix random(int paramInt1, int paramInt2)
  {
    Matrix localMatrix = new Matrix(paramInt1, paramInt2);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < paramInt1; i++) {
      for (int j = 0; j < paramInt2; j++) {
        arrayOfDouble[i][j] = Math.random();
      }
    }
    return localMatrix;
  }
  





  public static Matrix identity(int paramInt1, int paramInt2)
  {
    Matrix localMatrix = new Matrix(paramInt1, paramInt2);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < paramInt1; i++) {
      for (int j = 0; j < paramInt2; j++) {
        arrayOfDouble[i][j] = (i == j ? 1.0D : 0.0D);
      }
    }
    return localMatrix;
  }
  






  public void print(int paramInt1, int paramInt2)
  {
    print(new PrintWriter(System.out, true), paramInt1, paramInt2);
  }
  





  public void print(PrintWriter paramPrintWriter, int paramInt1, int paramInt2)
  {
    DecimalFormat localDecimalFormat = new DecimalFormat();
    localDecimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
    localDecimalFormat.setMinimumIntegerDigits(1);
    localDecimalFormat.setMaximumFractionDigits(paramInt2);
    localDecimalFormat.setMinimumFractionDigits(paramInt2);
    localDecimalFormat.setGroupingUsed(false);
    print(paramPrintWriter, localDecimalFormat, paramInt1 + 2);
  }
  









  public void print(NumberFormat paramNumberFormat, int paramInt)
  {
    print(new PrintWriter(System.out, true), paramNumberFormat, paramInt);
  }
  














  public void print(PrintWriter paramPrintWriter, NumberFormat paramNumberFormat, int paramInt)
  {
    paramPrintWriter.println();
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        String str = paramNumberFormat.format(A[i][j]);
        int k = Math.max(1, paramInt - str.length());
        for (int i1 = 0; i1 < k; i1++)
          paramPrintWriter.print(' ');
        paramPrintWriter.print(str);
      }
      paramPrintWriter.println();
    }
    paramPrintWriter.println();
  }
  






  public static Matrix read(BufferedReader paramBufferedReader)
    throws IOException
  {
    StreamTokenizer localStreamTokenizer = new StreamTokenizer(paramBufferedReader);
    






    localStreamTokenizer.resetSyntax();
    localStreamTokenizer.wordChars(0, 255);
    localStreamTokenizer.whitespaceChars(0, 32);
    localStreamTokenizer.eolIsSignificant(true);
    Vector localVector = new Vector();
    

    while (localStreamTokenizer.nextToken() == 10) {}
    if (ttype == -1)
      throw new IOException("Unexpected EOF on matrix read.");
    do {
      localVector.addElement(Double.valueOf(sval));
    } while (localStreamTokenizer.nextToken() == -3);
    
    int i = localVector.size();
    double[] arrayOfDouble = new double[i];
    for (int j = 0; j < i; j++)
      arrayOfDouble[j] = ((Double)localVector.elementAt(j)).doubleValue();
    localVector.removeAllElements();
    localVector.addElement(arrayOfDouble);
    while (localStreamTokenizer.nextToken() == -3)
    {
      localVector.addElement(arrayOfDouble = new double[i]);
      j = 0;
      do {
        if (j >= i) { throw new IOException("Row " + localVector.size() + " is too long.");
        }
        arrayOfDouble[(j++)] = Double.valueOf(sval).doubleValue();
      } while (localStreamTokenizer.nextToken() == -3);
      if (j < i) { throw new IOException("Row " + localVector.size() + " is too short.");
      }
    }
    j = localVector.size();
    double[][] arrayOfDouble1 = new double[j][];
    localVector.copyInto(arrayOfDouble1);
    return new Matrix(arrayOfDouble1);
  }
  






  private void checkMatrixDimensions(Matrix paramMatrix)
  {
    if ((m != m) || (n != n)) {
      throw new IllegalArgumentException("Matrix dimensions must agree.");
    }
  }
}
