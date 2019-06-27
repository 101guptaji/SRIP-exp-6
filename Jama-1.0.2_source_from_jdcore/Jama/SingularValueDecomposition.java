package Jama;

import Jama.util.Maths;
import java.io.Serializable;






































public class SingularValueDecomposition
  implements Serializable
{
  private double[][] U;
  private double[][] V;
  private double[] s;
  private int m;
  private int n;
  
  public SingularValueDecomposition(Matrix paramMatrix)
  {
    double[][] arrayOfDouble = paramMatrix.getArrayCopy();
    m = paramMatrix.getRowDimension();
    n = paramMatrix.getColumnDimension();
    





    int i = Math.min(m, n);
    s = new double[Math.min(m + 1, n)];
    U = new double[m][i];
    V = new double[n][n];
    double[] arrayOfDouble1 = new double[n];
    double[] arrayOfDouble2 = new double[m];
    int j = 1;
    int k = 1;
    



    int i1 = Math.min(m - 1, n);
    int i2 = Math.max(0, Math.min(n - 2, m));
    for (int i3 = 0; i3 < Math.max(i1, i2); i3++) {
      if (i3 < i1)
      {



        s[i3] = 0.0D;
        for (i4 = i3; i4 < m; i4++) {
          s[i3] = Maths.hypot(s[i3], arrayOfDouble[i4][i3]);
        }
        if (s[i3] != 0.0D) {
          if (arrayOfDouble[i3][i3] < 0.0D) {
            s[i3] = (-s[i3]);
          }
          for (i4 = i3; i4 < m; i4++) {
            arrayOfDouble[i4][i3] /= s[i3];
          }
          arrayOfDouble[i3][i3] += 1.0D;
        }
        s[i3] = (-s[i3]); }
      int i7;
      for (i4 = i3 + 1; i4 < n; i4++) {
        if (((i3 < i1 ? 1 : 0) & (s[i3] != 0.0D ? 1 : 0)) != 0)
        {


          double d1 = 0.0D;
          for (i7 = i3; i7 < m; i7++) {
            d1 += arrayOfDouble[i7][i3] * arrayOfDouble[i7][i4];
          }
          d1 = -d1 / arrayOfDouble[i3][i3];
          for (i7 = i3; i7 < m; i7++) {
            arrayOfDouble[i7][i4] += d1 * arrayOfDouble[i7][i3];
          }
        }
        



        arrayOfDouble1[i4] = arrayOfDouble[i3][i4];
      }
      if ((j & (i3 < i1 ? 1 : 0)) != 0)
      {



        for (i4 = i3; i4 < m; i4++) {
          U[i4][i3] = arrayOfDouble[i4][i3];
        }
      }
      if (i3 < i2)
      {



        arrayOfDouble1[i3] = 0.0D;
        for (i4 = i3 + 1; i4 < n; i4++) {
          arrayOfDouble1[i3] = Maths.hypot(arrayOfDouble1[i3], arrayOfDouble1[i4]);
        }
        if (arrayOfDouble1[i3] != 0.0D) {
          if (arrayOfDouble1[(i3 + 1)] < 0.0D) {
            arrayOfDouble1[i3] = (-arrayOfDouble1[i3]);
          }
          for (i4 = i3 + 1; i4 < n; i4++) {
            arrayOfDouble1[i4] /= arrayOfDouble1[i3];
          }
          arrayOfDouble1[(i3 + 1)] += 1.0D;
        }
        arrayOfDouble1[i3] = (-arrayOfDouble1[i3]);
        if (((i3 + 1 < m ? 1 : 0) & (arrayOfDouble1[i3] != 0.0D ? 1 : 0)) != 0)
        {


          for (i4 = i3 + 1; i4 < m; i4++) {
            arrayOfDouble2[i4] = 0.0D;
          }
          for (i4 = i3 + 1; i4 < n; i4++) {
            for (int i5 = i3 + 1; i5 < m; i5++) {
              arrayOfDouble2[i5] += arrayOfDouble1[i4] * arrayOfDouble[i5][i4];
            }
          }
          for (i4 = i3 + 1; i4 < n; i4++) {
            double d2 = -arrayOfDouble1[i4] / arrayOfDouble1[(i3 + 1)];
            for (i7 = i3 + 1; i7 < m; i7++) {
              arrayOfDouble[i7][i4] += d2 * arrayOfDouble2[i7];
            }
          }
        }
        if (k != 0)
        {



          for (i4 = i3 + 1; i4 < n; i4++) {
            V[i4][i3] = arrayOfDouble1[i4];
          }
        }
      }
    }
    


    i3 = Math.min(n, m + 1);
    if (i1 < n) {
      s[i1] = arrayOfDouble[i1][i1];
    }
    if (m < i3) {
      s[(i3 - 1)] = 0.0D;
    }
    if (i2 + 1 < i3) {
      arrayOfDouble1[i2] = arrayOfDouble[i2][(i3 - 1)];
    }
    arrayOfDouble1[(i3 - 1)] = 0.0D;
    
    int i8;
    
    if (j != 0) {
      for (i4 = i1; i4 < i; i4++) {
        for (i6 = 0; i6 < m; i6++) {
          U[i6][i4] = 0.0D;
        }
        U[i4][i4] = 1.0D;
      }
      for (i4 = i1 - 1; i4 >= 0; i4--) {
        if (s[i4] != 0.0D) {
          for (i6 = i4 + 1; i6 < i; i6++) {
            d3 = 0.0D;
            for (i8 = i4; i8 < m; i8++) {
              d3 += U[i8][i4] * U[i8][i6];
            }
            d3 = -d3 / U[i4][i4];
            for (i8 = i4; i8 < m; i8++) {
              U[i8][i6] += d3 * U[i8][i4];
            }
          }
          for (i6 = i4; i6 < m; i6++) {
            U[i6][i4] = (-U[i6][i4]);
          }
          U[i4][i4] = (1.0D + U[i4][i4]);
          for (i6 = 0; i6 < i4 - 1; i6++) {
            U[i6][i4] = 0.0D;
          }
        } else {
          for (i6 = 0; i6 < m; i6++) {
            U[i6][i4] = 0.0D;
          }
          U[i4][i4] = 1.0D;
        }
      }
    }
    


    if (k != 0) {
      for (i4 = n - 1; i4 >= 0; i4--) {
        if (((i4 < i2 ? 1 : 0) & (arrayOfDouble1[i4] != 0.0D ? 1 : 0)) != 0) {
          for (i6 = i4 + 1; i6 < i; i6++) {
            d3 = 0.0D;
            for (i8 = i4 + 1; i8 < n; i8++) {
              d3 += V[i8][i4] * V[i8][i6];
            }
            d3 = -d3 / V[(i4 + 1)][i4];
            for (i8 = i4 + 1; i8 < n; i8++) {
              V[i8][i6] += d3 * V[i8][i4];
            }
          }
        }
        for (i6 = 0; i6 < n; i6++) {
          V[i6][i4] = 0.0D;
        }
        V[i4][i4] = 1.0D;
      }
    }
    


    int i4 = i3 - 1;
    int i6 = 0;
    double d3 = Math.pow(2.0D, -52.0D);
    double d4 = Math.pow(2.0D, -966.0D);
    while (i3 > 0)
    {













      for (int i9 = i3 - 2; i9 >= -1; i9--) {
        if (i9 == -1) {
          break;
        }
        if (Math.abs(arrayOfDouble1[i9]) <= d4 + d3 * (Math.abs(s[i9]) + Math.abs(s[(i9 + 1)])))
        {
          arrayOfDouble1[i9] = 0.0D;
          break;
        } }
      int i10;
      if (i9 == i3 - 2) {
        i10 = 4;
      }
      else {
        for (int i11 = i3 - 1; i11 >= i9; i11--) {
          if (i11 == i9) {
            break;
          }
          double d7 = (i11 != i3 ? Math.abs(arrayOfDouble1[i11]) : 0.0D) + (i11 != i9 + 1 ? Math.abs(arrayOfDouble1[(i11 - 1)]) : 0.0D);
          
          if (Math.abs(s[i11]) <= d4 + d3 * d7) {
            s[i11] = 0.0D;
            break;
          }
        }
        if (i11 == i9) {
          i10 = 3;
        } else if (i11 == i3 - 1) {
          i10 = 1;
        } else {
          i10 = 2;
          i9 = i11;
        }
      }
      i9++;
      double d5;
      int i13;
      double d9;
      double d11; double d13; int i15; switch (i10)
      {


      case 1: 
        d5 = arrayOfDouble1[(i3 - 2)];
        arrayOfDouble1[(i3 - 2)] = 0.0D;
        for (i13 = i3 - 2; i13 >= i9; i13--) {
          d9 = Maths.hypot(s[i13], d5);
          d11 = s[i13] / d9;
          d13 = d5 / d9;
          s[i13] = d9;
          if (i13 != i9) {
            d5 = -d13 * arrayOfDouble1[(i13 - 1)];
            arrayOfDouble1[(i13 - 1)] = (d11 * arrayOfDouble1[(i13 - 1)]);
          }
          if (k != 0) {
            for (i15 = 0; i15 < n; i15++) {
              d9 = d11 * V[i15][i13] + d13 * V[i15][(i3 - 1)];
              V[i15][(i3 - 1)] = (-d13 * V[i15][i13] + d11 * V[i15][(i3 - 1)]);
              V[i15][i13] = d9;
            }
          }
        }
        
        break;
      


      case 2: 
        d5 = arrayOfDouble1[(i9 - 1)];
        arrayOfDouble1[(i9 - 1)] = 0.0D;
        for (i13 = i9; i13 < i3; i13++) {
          d9 = Maths.hypot(s[i13], d5);
          d11 = s[i13] / d9;
          d13 = d5 / d9;
          s[i13] = d9;
          d5 = -d13 * arrayOfDouble1[i13];
          arrayOfDouble1[i13] = (d11 * arrayOfDouble1[i13]);
          if (j != 0) {
            for (i15 = 0; i15 < m; i15++) {
              d9 = d11 * U[i15][i13] + d13 * U[i15][(i9 - 1)];
              U[i15][(i9 - 1)] = (-d13 * U[i15][i13] + d11 * U[i15][(i9 - 1)]);
              U[i15][i13] = d9;
            }
          }
        }
        
        break;
      





      case 3: 
        d5 = Math.max(Math.max(Math.max(Math.max(Math.abs(s[(i3 - 1)]), Math.abs(s[(i3 - 2)])), Math.abs(arrayOfDouble1[(i3 - 2)])), Math.abs(s[i9])), Math.abs(arrayOfDouble1[i9]));
        

        double d8 = s[(i3 - 1)] / d5;
        double d10 = s[(i3 - 2)] / d5;
        double d12 = arrayOfDouble1[(i3 - 2)] / d5;
        double d14 = s[i9] / d5;
        double d15 = arrayOfDouble1[i9] / d5;
        double d16 = ((d10 + d8) * (d10 - d8) + d12 * d12) / 2.0D;
        double d17 = d8 * d12 * (d8 * d12);
        double d18 = 0.0D;
        if (((d16 != 0.0D ? 1 : 0) | (d17 != 0.0D ? 1 : 0)) != 0) {
          d18 = Math.sqrt(d16 * d16 + d17);
          if (d16 < 0.0D) {
            d18 = -d18;
          }
          d18 = d17 / (d16 + d18);
        }
        double d19 = (d14 + d8) * (d14 - d8) + d18;
        double d20 = d14 * d15;
        


        for (int i16 = i9; i16 < i3 - 1; i16++) {
          double d21 = Maths.hypot(d19, d20);
          double d22 = d19 / d21;
          double d23 = d20 / d21;
          if (i16 != i9) {
            arrayOfDouble1[(i16 - 1)] = d21;
          }
          d19 = d22 * s[i16] + d23 * arrayOfDouble1[i16];
          arrayOfDouble1[i16] = (d22 * arrayOfDouble1[i16] - d23 * s[i16]);
          d20 = d23 * s[(i16 + 1)];
          s[(i16 + 1)] = (d22 * s[(i16 + 1)]);
          int i17; if (k != 0) {
            for (i17 = 0; i17 < n; i17++) {
              d21 = d22 * V[i17][i16] + d23 * V[i17][(i16 + 1)];
              V[i17][(i16 + 1)] = (-d23 * V[i17][i16] + d22 * V[i17][(i16 + 1)]);
              V[i17][i16] = d21;
            }
          }
          d21 = Maths.hypot(d19, d20);
          d22 = d19 / d21;
          d23 = d20 / d21;
          s[i16] = d21;
          d19 = d22 * arrayOfDouble1[i16] + d23 * s[(i16 + 1)];
          s[(i16 + 1)] = (-d23 * arrayOfDouble1[i16] + d22 * s[(i16 + 1)]);
          d20 = d23 * arrayOfDouble1[(i16 + 1)];
          arrayOfDouble1[(i16 + 1)] = (d22 * arrayOfDouble1[(i16 + 1)]);
          if ((j != 0) && (i16 < m - 1)) {
            for (i17 = 0; i17 < m; i17++) {
              d21 = d22 * U[i17][i16] + d23 * U[i17][(i16 + 1)];
              U[i17][(i16 + 1)] = (-d23 * U[i17][i16] + d22 * U[i17][(i16 + 1)]);
              U[i17][i16] = d21;
            }
          }
        }
        arrayOfDouble1[(i3 - 2)] = d19;
        i6 += 1;
        
        break;
      





      case 4: 
        if (s[i9] <= 0.0D) {
          s[i9] = (s[i9] < 0.0D ? -s[i9] : 0.0D);
          if (k != 0) {
            for (int i12 = 0; i12 <= i4; i12++) {
              V[i12][i9] = (-V[i12][i9]);
            }
          }
        }
        


        while ((i9 < i4) && 
          (s[i9] < s[(i9 + 1)]))
        {

          double d6 = s[i9];
          s[i9] = s[(i9 + 1)];
          s[(i9 + 1)] = d6;
          int i14; if ((k != 0) && (i9 < n - 1)) {
            for (i14 = 0; i14 < n; i14++) {
              d6 = V[i14][(i9 + 1)];V[i14][(i9 + 1)] = V[i14][i9];V[i14][i9] = d6;
            }
          }
          if ((j != 0) && (i9 < m - 1)) {
            for (i14 = 0; i14 < m; i14++) {
              d6 = U[i14][(i9 + 1)];U[i14][(i9 + 1)] = U[i14][i9];U[i14][i9] = d6;
            }
          }
          i9++;
        }
        i6 = 0;
        i3--;
      }
      
    }
  }
  








  public Matrix getU()
  {
    return new Matrix(U, m, Math.min(m + 1, n));
  }
  



  public Matrix getV()
  {
    return new Matrix(V, n, n);
  }
  



  public double[] getSingularValues()
  {
    return s;
  }
  



  public Matrix getS()
  {
    Matrix localMatrix = new Matrix(n, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[i][j] = 0.0D;
      }
      arrayOfDouble[i][i] = s[i];
    }
    return localMatrix;
  }
  



  public double norm2()
  {
    return s[0];
  }
  



  public double cond()
  {
    return s[0] / s[(Math.min(m, n) - 1)];
  }
  



  public int rank()
  {
    double d1 = Math.pow(2.0D, -52.0D);
    double d2 = Math.max(m, n) * s[0] * d1;
    int i = 0;
    for (int j = 0; j < s.length; j++) {
      if (s[j] > d2) {
        i++;
      }
    }
    return i;
  }
}
