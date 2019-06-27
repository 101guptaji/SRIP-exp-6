package Jama.examples;

import Jama.Matrix;
import java.text.DecimalFormat;
import java.util.Date;

public class MagicSquareExample
{
  public MagicSquareExample() {}
  
  public static Matrix magic(int paramInt)
  {
    double[][] arrayOfDouble = new double[paramInt][paramInt];
    int i;
    int j;
    int m;
    if (paramInt % 2 == 1) {
      i = (paramInt + 1) / 2;
      j = paramInt + 1;
      for (int k = 0; k < paramInt; k++) {
        for (m = 0; m < paramInt; m++) {
          arrayOfDouble[m][k] = (paramInt * ((m + k + i) % paramInt) + (m + 2 * k + j) % paramInt + 1);
        }
        
      }
      
    }
    else if (paramInt % 4 == 0) {
      for (i = 0; i < paramInt; i++) {
        for (j = 0; j < paramInt; j++) {
          if ((j + 1) / 2 % 2 == (i + 1) / 2 % 2) {
            arrayOfDouble[j][i] = (paramInt * paramInt - paramInt * j - i);
          } else {
            arrayOfDouble[j][i] = (paramInt * j + i + 1);
          }
          
        }
      }
    }
    else
    {
      i = paramInt / 2;
      j = (paramInt - 2) / 4;
      Matrix localMatrix = magic(i);
      int n; double d2; for (m = 0; m < i; m++) {
        for (n = 0; n < i; n++) {
          d2 = localMatrix.get(n, m);
          arrayOfDouble[n][m] = d2;
          arrayOfDouble[n][(m + i)] = (d2 + 2 * i * i);
          arrayOfDouble[(n + i)][m] = (d2 + 3 * i * i);
          arrayOfDouble[(n + i)][(m + i)] = (d2 + i * i);
        }
      }
      for (m = 0; m < i; m++) {
        for (n = 0; n < j; n++) {
          d2 = arrayOfDouble[m][n];arrayOfDouble[m][n] = arrayOfDouble[(m + i)][n];arrayOfDouble[(m + i)][n] = d2;
        }
        for (n = paramInt - j + 1; n < paramInt; n++) {
          d2 = arrayOfDouble[m][n];arrayOfDouble[m][n] = arrayOfDouble[(m + i)][n];arrayOfDouble[(m + i)][n] = d2;
        }
      }
      double d1 = arrayOfDouble[j][0];arrayOfDouble[j][0] = arrayOfDouble[(j + i)][0];arrayOfDouble[(j + i)][0] = d1;
      d1 = arrayOfDouble[j][j];arrayOfDouble[j][j] = arrayOfDouble[(j + i)][j];arrayOfDouble[(j + i)][j] = d1;
    }
    return new Matrix(arrayOfDouble);
  }
  

  private static void print(String paramString)
  {
    System.out.print(paramString);
  }
  

  public static String fixedWidthDoubletoString(double paramDouble, int paramInt1, int paramInt2)
  {
    DecimalFormat localDecimalFormat = new DecimalFormat();
    localDecimalFormat.setMaximumFractionDigits(paramInt2);
    localDecimalFormat.setMinimumFractionDigits(paramInt2);
    localDecimalFormat.setGroupingUsed(false);
    String str = localDecimalFormat.format(paramDouble);
    while (str.length() < paramInt1) {
      str = " " + str;
    }
    return str;
  }
  

  public static String fixedWidthIntegertoString(int paramInt1, int paramInt2)
  {
    String str = Integer.toString(paramInt1);
    while (str.length() < paramInt2) {
      str = " " + str;
    }
    return str;
  }
  














  public static void main(String[] paramArrayOfString)
  {
    print("\n    Test of Matrix Class, using magic squares.\n");
    print("    See MagicSquareExample.main() for an explanation.\n");
    print("\n      n     trace       max_eig   rank        cond      lu_res      qr_res\n\n");
    
    Date localDate1 = new Date();
    double d1 = Math.pow(2.0D, -52.0D);
    for (int i = 3; i <= 32; i++) {
      print(fixedWidthIntegertoString(i, 7));
      
      Matrix localMatrix1 = magic(i);
      
      int j = (int)localMatrix1.trace();
      print(fixedWidthIntegertoString(j, 10));
      
      Jama.EigenvalueDecomposition localEigenvalueDecomposition = new Jama.EigenvalueDecomposition(localMatrix1.plus(localMatrix1.transpose()).times(0.5D));
      
      double[] arrayOfDouble = localEigenvalueDecomposition.getRealEigenvalues();
      print(fixedWidthDoubletoString(arrayOfDouble[(i - 1)], 14, 3));
      
      int k = localMatrix1.rank();
      print(fixedWidthIntegertoString(k, 7));
      
      double d3 = localMatrix1.cond();
      print(d3 < 1.0D / d1 ? fixedWidthDoubletoString(d3, 12, 3) : "         Inf");
      

      Jama.LUDecomposition localLUDecomposition = new Jama.LUDecomposition(localMatrix1);
      Matrix localMatrix2 = localLUDecomposition.getL();
      Matrix localMatrix3 = localLUDecomposition.getU();
      int[] arrayOfInt = localLUDecomposition.getPivot();
      Matrix localMatrix4 = localMatrix2.times(localMatrix3).minus(localMatrix1.getMatrix(arrayOfInt, 0, i - 1));
      double d4 = localMatrix4.norm1() / (i * d1);
      print(fixedWidthDoubletoString(d4, 12, 3));
      
      Jama.QRDecomposition localQRDecomposition = new Jama.QRDecomposition(localMatrix1);
      Matrix localMatrix5 = localQRDecomposition.getQ();
      localMatrix4 = localQRDecomposition.getR();
      localMatrix4 = localMatrix5.times(localMatrix4).minus(localMatrix1);
      d4 = localMatrix4.norm1() / (i * d1);
      print(fixedWidthDoubletoString(d4, 12, 3));
      
      print("\n");
    }
    Date localDate2 = new Date();
    double d2 = (localDate2.getTime() - localDate1.getTime()) / 1000.0D;
    print("\nElapsed Time = " + fixedWidthDoubletoString(d2, 12, 3) + " seconds\n");
    
    print("Adios\n");
  }
}
