package Jama;

import Jama.util.Maths;
import java.io.Serializable;

















































public class EigenvalueDecomposition
  implements Serializable
{
  private int n;
  private boolean issymmetric;
  private double[] d;
  private double[] e;
  private double[][] V;
  private double[][] H;
  private double[] ort;
  private transient double cdivr;
  private transient double cdivi;
  
  private void tred2()
  {
    for (int i = 0; i < n; i++) {
      d[i] = V[(n - 1)][i];
    }
    
    double d1;
    
    for (i = n - 1; i > 0; i--)
    {


      d1 = 0.0D;
      double d2 = 0.0D;
      for (int k = 0; k < i; k++) {
        d1 += Math.abs(d[k]);
      }
      if (d1 == 0.0D) {
        e[i] = d[(i - 1)];
        for (k = 0; k < i; k++) {
          d[k] = V[(i - 1)][k];
          V[i][k] = 0.0D;
          V[k][i] = 0.0D;
        }
        
      }
      else
      {
        for (k = 0; k < i; k++) {
          d[k] /= d1;
          d2 += d[k] * d[k];
        }
        double d4 = d[(i - 1)];
        double d5 = Math.sqrt(d2);
        if (d4 > 0.0D) {
          d5 = -d5;
        }
        e[i] = (d1 * d5);
        d2 -= d4 * d5;
        d[(i - 1)] = (d4 - d5);
        for (int i1 = 0; i1 < i; i1++) {
          e[i1] = 0.0D;
        }
        


        for (i1 = 0; i1 < i; i1++) {
          d4 = d[i1];
          V[i1][i] = d4;
          d5 = e[i1] + V[i1][i1] * d4;
          for (int i2 = i1 + 1; i2 <= i - 1; i2++) {
            d5 += V[i2][i1] * d[i2];
            e[i2] += V[i2][i1] * d4;
          }
          e[i1] = d5;
        }
        d4 = 0.0D;
        for (i1 = 0; i1 < i; i1++) {
          e[i1] /= d2;
          d4 += e[i1] * d[i1];
        }
        double d6 = d4 / (d2 + d2);
        for (int i3 = 0; i3 < i; i3++) {
          e[i3] -= d6 * d[i3];
        }
        for (i3 = 0; i3 < i; i3++) {
          d4 = d[i3];
          d5 = e[i3];
          for (int i4 = i3; i4 <= i - 1; i4++) {
            V[i4][i3] -= d4 * e[i4] + d5 * d[i4];
          }
          d[i3] = V[(i - 1)][i3];
          V[i][i3] = 0.0D;
        }
      }
      d[i] = d2;
    }
    


    for (i = 0; i < n - 1; i++) {
      V[(n - 1)][i] = V[i][i];
      V[i][i] = 1.0D;
      d1 = d[(i + 1)];
      if (d1 != 0.0D) {
        for (j = 0; j <= i; j++) {
          d[j] = (V[j][(i + 1)] / d1);
        }
        for (j = 0; j <= i; j++) {
          double d3 = 0.0D;
          for (int m = 0; m <= i; m++) {
            d3 += V[m][(i + 1)] * V[m][j];
          }
          for (m = 0; m <= i; m++) {
            V[m][j] -= d3 * d[m];
          }
        }
      }
      for (int j = 0; j <= i; j++) {
        V[j][(i + 1)] = 0.0D;
      }
    }
    for (i = 0; i < n; i++) {
      d[i] = V[(n - 1)][i];
      V[(n - 1)][i] = 0.0D;
    }
    V[(n - 1)][(n - 1)] = 1.0D;
    e[0] = 0.0D;
  }
  







  private void tql2()
  {
    for (int i = 1; i < n; i++) {
      e[(i - 1)] = e[i];
    }
    e[(n - 1)] = 0.0D;
    
    double d1 = 0.0D;
    double d2 = 0.0D;
    double d3 = Math.pow(2.0D, -52.0D);
    int k; for (int j = 0; j < n; j++)
    {


      d2 = Math.max(d2, Math.abs(d[j]) + Math.abs(e[j]));
      k = j;
      while ((k < n) && 
        (Math.abs(e[k]) > d3 * d2))
      {

        k++;
      }
      



      if (k > j) {
        int m = 0;
        do {
          m += 1;
          


          double d5 = d[j];
          double d6 = (d[(j + 1)] - d5) / (2.0D * e[j]);
          double d7 = Maths.hypot(d6, 1.0D);
          if (d6 < 0.0D) {
            d7 = -d7;
          }
          d[j] = (e[j] / (d6 + d7));
          d[(j + 1)] = (e[j] * (d6 + d7));
          double d8 = d[(j + 1)];
          double d9 = d5 - d[j];
          for (int i2 = j + 2; i2 < n; i2++) {
            d[i2] -= d9;
          }
          d1 += d9;
          


          d6 = d[k];
          double d10 = 1.0D;
          double d11 = d10;
          double d12 = d10;
          double d13 = e[(j + 1)];
          double d14 = 0.0D;
          double d15 = 0.0D;
          for (int i3 = k - 1; i3 >= j; i3--) {
            d12 = d11;
            d11 = d10;
            d15 = d14;
            d5 = d10 * e[i3];
            d9 = d10 * d6;
            d7 = Maths.hypot(d6, e[i3]);
            e[(i3 + 1)] = (d14 * d7);
            d14 = e[i3] / d7;
            d10 = d6 / d7;
            d6 = d10 * d[i3] - d14 * d5;
            d[(i3 + 1)] = (d9 + d14 * (d10 * d5 + d14 * d[i3]));
            


            for (int i4 = 0; i4 < n; i4++) {
              d9 = V[i4][(i3 + 1)];
              V[i4][(i3 + 1)] = (d14 * V[i4][i3] + d10 * d9);
              V[i4][i3] = (d10 * V[i4][i3] - d14 * d9);
            }
          }
          d6 = -d14 * d15 * d12 * d13 * e[j] / d8;
          e[j] = (d14 * d6);
          d[j] = (d10 * d6);


        }
        while (Math.abs(e[j]) > d3 * d2);
      }
      d[j] += d1;
      e[j] = 0.0D;
    }
    


    for (j = 0; j < n - 1; j++) {
      k = j;
      double d4 = d[j];
      for (int i1 = j + 1; i1 < n; i1++) {
        if (d[i1] < d4) {
          k = i1;
          d4 = d[i1];
        }
      }
      if (k != j) {
        d[k] = d[j];
        d[j] = d4;
        for (i1 = 0; i1 < n; i1++) {
          d4 = V[i1][j];
          V[i1][j] = V[i1][k];
          V[i1][k] = d4;
        }
      }
    }
  }
  







  private void orthes()
  {
    int i = 0;
    int j = n - 1;
    
    for (int k = i + 1; k <= j - 1; k++)
    {


      double d1 = 0.0D;
      for (int i1 = k; i1 <= j; i1++) {
        d1 += Math.abs(H[i1][(k - 1)]);
      }
      if (d1 != 0.0D)
      {


        double d3 = 0.0D;
        for (int i3 = j; i3 >= k; i3--) {
          ort[i3] = (H[i3][(k - 1)] / d1);
          d3 += ort[i3] * ort[i3];
        }
        double d4 = Math.sqrt(d3);
        if (ort[k] > 0.0D) {
          d4 = -d4;
        }
        d3 -= ort[k] * d4;
        ort[k] -= d4;
        
        double d5;
        
        int i5;
        for (int i4 = k; i4 < n; i4++) {
          d5 = 0.0D;
          for (i5 = j; i5 >= k; i5--) {
            d5 += ort[i5] * H[i5][i4];
          }
          d5 /= d3;
          for (i5 = k; i5 <= j; i5++) {
            H[i5][i4] -= d5 * ort[i5];
          }
        }
        
        for (i4 = 0; i4 <= j; i4++) {
          d5 = 0.0D;
          for (i5 = j; i5 >= k; i5--) {
            d5 += ort[i5] * H[i4][i5];
          }
          d5 /= d3;
          for (i5 = k; i5 <= j; i5++) {
            H[i4][i5] -= d5 * ort[i5];
          }
        }
        ort[k] = (d1 * ort[k]);
        H[k][(k - 1)] = (d1 * d4);
      }
    }
    
    int m;
    
    for (k = 0; k < n; k++) {
      for (m = 0; m < n; m++) {
        V[k][m] = (k == m ? 1.0D : 0.0D);
      }
    }
    
    for (k = j - 1; k >= i + 1; k--) {
      if (H[k][(k - 1)] != 0.0D) {
        for (m = k + 1; m <= j; m++) {
          ort[m] = H[m][(k - 1)];
        }
        for (m = k; m <= j; m++) {
          double d2 = 0.0D;
          for (int i2 = k; i2 <= j; i2++) {
            d2 += ort[i2] * V[i2][m];
          }
          
          d2 = d2 / ort[k] / H[k][(k - 1)];
          for (i2 = k; i2 <= j; i2++) {
            V[i2][m] += d2 * ort[i2];
          }
        }
      }
    }
  }
  

  private void cdiv(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    double d1;
    
    double d2;
    if (Math.abs(paramDouble3) > Math.abs(paramDouble4)) {
      d1 = paramDouble4 / paramDouble3;
      d2 = paramDouble3 + d1 * paramDouble4;
      cdivr = ((paramDouble1 + d1 * paramDouble2) / d2);
      cdivi = ((paramDouble2 - d1 * paramDouble1) / d2);
    } else {
      d1 = paramDouble3 / paramDouble4;
      d2 = paramDouble4 + d1 * paramDouble3;
      cdivr = ((d1 * paramDouble1 + paramDouble2) / d2);
      cdivi = ((d1 * paramDouble2 - paramDouble1) / d2);
    }
  }
  










  private void hqr2()
  {
    int i = n;
    int j = i - 1;
    double d1 = 0;
    int k = i - 1;
    double d2 = Math.pow(2.0D, -52.0D);
    double d3 = 0.0D;
    double d4 = 0.0D;double d5 = 0.0D;double d6 = 0.0D;double d7 = 0.0D;double d8 = 0.0D;
    


    double d13 = 0.0D;
    for (int m = 0; m < i; m++) {
      if (((m < d1 ? 1 : 0) | (m > k ? 1 : 0)) != 0) {
        d[m] = H[m][m];
        e[m] = 0.0D;
      }
      for (i1 = Math.max(m - 1, 0); i1 < i; i1++) {
        d13 += Math.abs(H[m][i1]);
      }
    }
    


    m = 0;
    double d10; double d11; int i2; double d12; int i3; while (j >= d1)
    {


      i1 = j;
      while (i1 > d1) {
        d7 = Math.abs(H[(i1 - 1)][(i1 - 1)]) + Math.abs(H[i1][i1]);
        if (d7 == 0.0D) {
          d7 = d13;
        }
        if (Math.abs(H[i1][(i1 - 1)]) < d2 * d7) {
          break;
        }
        i1--;
      }
      



      if (i1 == j) {
        H[j][j] += d3;
        d[j] = H[j][j];
        e[j] = 0.0D;
        j--;
        m = 0;


      }
      else if (i1 == j - 1) {
        d10 = H[j][(j - 1)] * H[(j - 1)][j];
        d4 = (H[(j - 1)][(j - 1)] - H[j][j]) / 2.0D;
        d5 = d4 * d4 + d10;
        d8 = Math.sqrt(Math.abs(d5));
        H[j][j] += d3;
        H[(j - 1)][(j - 1)] += d3;
        d11 = H[j][j];
        


        if (d5 >= 0.0D) {
          if (d4 >= 0.0D) {
            d8 = d4 + d8;
          } else {
            d8 = d4 - d8;
          }
          d[(j - 1)] = (d11 + d8);
          d[j] = d[(j - 1)];
          if (d8 != 0.0D) {
            d[j] = (d11 - d10 / d8);
          }
          e[(j - 1)] = 0.0D;
          e[j] = 0.0D;
          d11 = H[j][(j - 1)];
          d7 = Math.abs(d11) + Math.abs(d8);
          d4 = d11 / d7;
          d5 = d8 / d7;
          d6 = Math.sqrt(d4 * d4 + d5 * d5);
          d4 /= d6;
          d5 /= d6;
          


          for (i2 = j - 1; i2 < i; i2++) {
            d8 = H[(j - 1)][i2];
            H[(j - 1)][i2] = (d5 * d8 + d4 * H[j][i2]);
            H[j][i2] = (d5 * H[j][i2] - d4 * d8);
          }
          


          for (i2 = 0; i2 <= j; i2++) {
            d8 = H[i2][(j - 1)];
            H[i2][(j - 1)] = (d5 * d8 + d4 * H[i2][j]);
            H[i2][j] = (d5 * H[i2][j] - d4 * d8);
          }
          


          for (i2 = d1; i2 <= k; i2++) {
            d8 = V[i2][(j - 1)];
            V[i2][(j - 1)] = (d5 * d8 + d4 * V[i2][j]);
            V[i2][j] = (d5 * V[i2][j] - d4 * d8);
          }
          
        }
        else
        {
          d[(j - 1)] = (d11 + d4);
          d[j] = (d11 + d4);
          e[(j - 1)] = d8;
          e[j] = (-d8);
        }
        j -= 2;
        m = 0;


      }
      else
      {


        d11 = H[j][j];
        d12 = 0.0D;
        d10 = 0.0D;
        if (i1 < j) {
          d12 = H[(j - 1)][(j - 1)];
          d10 = H[j][(j - 1)] * H[(j - 1)][j];
        }
        


        if (m == 10) {
          d3 += d11;
          for (i2 = d1; i2 <= j; i2++) {
            H[i2][i2] -= d11;
          }
          d7 = Math.abs(H[j][(j - 1)]) + Math.abs(H[(j - 1)][(j - 2)]);
          d11 = d12 = 0.75D * d7;
          d10 = -0.4375D * d7 * d7;
        }
        


        if (m == 30) {
          d7 = (d12 - d11) / 2.0D;
          d7 = d7 * d7 + d10;
          if (d7 > 0.0D) {
            d7 = Math.sqrt(d7);
            if (d12 < d11) {
              d7 = -d7;
            }
            d7 = d11 - d10 / ((d12 - d11) / 2.0D + d7);
            for (i2 = d1; i2 <= j; i2++) {
              H[i2][i2] -= d7;
            }
            d3 += d7;
            d11 = d12 = d10 = 0.964D;
          }
        }
        
        m += 1;
        


        i2 = j - 2;
        while (i2 >= i1) {
          d8 = H[i2][i2];
          d6 = d11 - d8;
          d7 = d12 - d8;
          d4 = (d6 * d7 - d10) / H[(i2 + 1)][i2] + H[i2][(i2 + 1)];
          d5 = H[(i2 + 1)][(i2 + 1)] - d8 - d6 - d7;
          d6 = H[(i2 + 2)][(i2 + 1)];
          d7 = Math.abs(d4) + Math.abs(d5) + Math.abs(d6);
          d4 /= d7;
          d5 /= d7;
          d6 /= d7;
          if (i2 == i1) {
            break;
          }
          if (Math.abs(H[i2][(i2 - 1)]) * (Math.abs(d5) + Math.abs(d6)) < d2 * (Math.abs(d4) * (Math.abs(H[(i2 - 1)][(i2 - 1)]) + Math.abs(d8) + Math.abs(H[(i2 + 1)][(i2 + 1)])))) {
            break;
          }
          

          i2--;
        }
        
        for (i3 = i2 + 2; i3 <= j; i3++) {
          H[i3][(i3 - 2)] = 0.0D;
          if (i3 > i2 + 2) {
            H[i3][(i3 - 3)] = 0.0D;
          }
        }
        


        for (i3 = i2; i3 <= j - 1; i3++) {
          int i4 = i3 != j - 1 ? 1 : 0;
          if (i3 != i2) {
            d4 = H[i3][(i3 - 1)];
            d5 = H[(i3 + 1)][(i3 - 1)];
            d6 = i4 != 0 ? H[(i3 + 2)][(i3 - 1)] : 0.0D;
            d11 = Math.abs(d4) + Math.abs(d5) + Math.abs(d6);
            if (d11 != 0.0D) {
              d4 /= d11;
              d5 /= d11;
              d6 /= d11;
            }
          }
          if (d11 == 0.0D) {
            break;
          }
          d7 = Math.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
          if (d4 < 0.0D) {
            d7 = -d7;
          }
          if (d7 != 0.0D) {
            if (i3 != i2) {
              H[i3][(i3 - 1)] = (-d7 * d11);
            } else if (i1 != i2) {
              H[i3][(i3 - 1)] = (-H[i3][(i3 - 1)]);
            }
            d4 += d7;
            d11 = d4 / d7;
            d12 = d5 / d7;
            d8 = d6 / d7;
            d5 /= d4;
            d6 /= d4;
            


            for (int i5 = i3; i5 < i; i5++) {
              d4 = H[i3][i5] + d5 * H[(i3 + 1)][i5];
              if (i4 != 0) {
                d4 += d6 * H[(i3 + 2)][i5];
                H[(i3 + 2)][i5] -= d4 * d8;
              }
              H[i3][i5] -= d4 * d11;
              H[(i3 + 1)][i5] -= d4 * d12;
            }
            


            for (i5 = 0; i5 <= Math.min(j, i3 + 3); i5++) {
              d4 = d11 * H[i5][i3] + d12 * H[i5][(i3 + 1)];
              if (i4 != 0) {
                d4 += d8 * H[i5][(i3 + 2)];
                H[i5][(i3 + 2)] -= d4 * d6;
              }
              H[i5][i3] -= d4;
              H[i5][(i3 + 1)] -= d4 * d5;
            }
            


            for (i5 = d1; i5 <= k; i5++) {
              d4 = d11 * V[i5][i3] + d12 * V[i5][(i3 + 1)];
              if (i4 != 0) {
                d4 += d8 * V[i5][(i3 + 2)];
                V[i5][(i3 + 2)] -= d4 * d6;
              }
              V[i5][i3] -= d4;
              V[i5][(i3 + 1)] -= d4 * d5;
            }
          }
        }
      }
    }
    


    if (d13 == 0.0D) {
      return;
    }
    double d14;
    for (j = i - 1; j >= 0; j--) {
      d4 = d[j];
      d5 = e[j];
      
      double d9;
      
      if (d5 == 0.0D) {
        i1 = j;
        H[j][j] = 1.0D;
        for (i2 = j - 1; i2 >= 0; i2--) {
          d10 = H[i2][i2] - d4;
          d6 = 0.0D;
          for (i3 = i1; i3 <= j; i3++) {
            d6 += H[i2][i3] * H[i3][j];
          }
          if (e[i2] < 0.0D) {
            d8 = d10;
            d7 = d6;
          } else {
            i1 = i2;
            if (e[i2] == 0.0D) {
              if (d10 != 0.0D) {
                H[i2][j] = (-d6 / d10);
              } else {
                H[i2][j] = (-d6 / (d2 * d13));
              }
              
            }
            else
            {
              d11 = H[i2][(i2 + 1)];
              d12 = H[(i2 + 1)][i2];
              d5 = (d[i2] - d4) * (d[i2] - d4) + e[i2] * e[i2];
              d9 = (d11 * d7 - d8 * d6) / d5;
              H[i2][j] = d9;
              if (Math.abs(d11) > Math.abs(d8)) {
                H[(i2 + 1)][j] = ((-d6 - d10 * d9) / d11);
              } else {
                H[(i2 + 1)][j] = ((-d7 - d12 * d9) / d8);
              }
            }
            


            d9 = Math.abs(H[i2][j]);
            if (d2 * d9 * d9 > 1.0D) {
              for (i3 = i2; i3 <= j; i3++) {
                H[i3][j] /= d9;
              }
              
            }
            
          }
        }
      }
      else if (d5 < 0.0D) {
        i1 = j - 1;
        


        if (Math.abs(H[j][(j - 1)]) > Math.abs(H[(j - 1)][j])) {
          H[(j - 1)][(j - 1)] = (d5 / H[j][(j - 1)]);
          H[(j - 1)][j] = (-(H[j][j] - d4) / H[j][(j - 1)]);
        } else {
          cdiv(0.0D, -H[(j - 1)][j], H[(j - 1)][(j - 1)] - d4, d5);
          H[(j - 1)][(j - 1)] = cdivr;
          H[(j - 1)][j] = cdivi;
        }
        H[j][(j - 1)] = 0.0D;
        H[j][j] = 1.0D;
        for (i2 = j - 2; i2 >= 0; i2--)
        {
          d14 = 0.0D;
          double d15 = 0.0D;
          for (int i6 = i1; i6 <= j; i6++) {
            d14 += H[i2][i6] * H[i6][(j - 1)];
            d15 += H[i2][i6] * H[i6][j];
          }
          d10 = H[i2][i2] - d4;
          
          if (e[i2] < 0.0D) {
            d8 = d10;
            d6 = d14;
            d7 = d15;
          } else {
            i1 = i2;
            if (e[i2] == 0.0D) {
              cdiv(-d14, -d15, d10, d5);
              H[i2][(j - 1)] = cdivr;
              H[i2][j] = cdivi;

            }
            else
            {
              d11 = H[i2][(i2 + 1)];
              d12 = H[(i2 + 1)][i2];
              double d16 = (d[i2] - d4) * (d[i2] - d4) + e[i2] * e[i2] - d5 * d5;
              double d17 = (d[i2] - d4) * 2.0D * d5;
              if (((d16 == 0.0D ? 1 : 0) & (d17 == 0.0D ? 1 : 0)) != 0) {
                d16 = d2 * d13 * (Math.abs(d10) + Math.abs(d5) + Math.abs(d11) + Math.abs(d12) + Math.abs(d8));
              }
              
              cdiv(d11 * d6 - d8 * d14 + d5 * d15, d11 * d7 - d8 * d15 - d5 * d14, d16, d17);
              H[i2][(j - 1)] = cdivr;
              H[i2][j] = cdivi;
              if (Math.abs(d11) > Math.abs(d8) + Math.abs(d5)) {
                H[(i2 + 1)][(j - 1)] = ((-d14 - d10 * H[i2][(j - 1)] + d5 * H[i2][j]) / d11);
                H[(i2 + 1)][j] = ((-d15 - d10 * H[i2][j] - d5 * H[i2][(j - 1)]) / d11);
              } else {
                cdiv(-d6 - d12 * H[i2][(j - 1)], -d7 - d12 * H[i2][j], d8, d5);
                H[(i2 + 1)][(j - 1)] = cdivr;
                H[(i2 + 1)][j] = cdivi;
              }
            }
            


            d9 = Math.max(Math.abs(H[i2][(j - 1)]), Math.abs(H[i2][j]));
            if (d2 * d9 * d9 > 1.0D) {
              for (i6 = i2; i6 <= j; i6++) {
                H[i6][(j - 1)] /= d9;
                H[i6][j] /= d9;
              }
            }
          }
        }
      }
    }
    


    for (int i1 = 0; i1 < i; i1++) {
      if (((i1 < d1 ? 1 : 0) | (i1 > k ? 1 : 0)) != 0) {
        for (i2 = i1; i2 < i; i2++) {
          V[i1][i2] = H[i1][i2];
        }
      }
    }
    


    for (i1 = i - 1; i1 >= d1; i1--) {
      for (i2 = d1; i2 <= k; i2++) {
        d8 = 0.0D;
        for (d14 = d1; d14 <= Math.min(i1, k); d14++) {
          d8 += V[i2][d14] * H[d14][i1];
        }
        V[i2][i1] = d8;
      }
    }
  }
  









  public EigenvalueDecomposition(Matrix paramMatrix)
  {
    double[][] arrayOfDouble = paramMatrix.getArray();
    n = paramMatrix.getColumnDimension();
    V = new double[n][n];
    d = new double[n];
    e = new double[n];
    
    issymmetric = true;
    int j; for (int i = 0; (i < n & issymmetric); i++) {
      for (j = 0; (j < n & issymmetric); j++) {
        issymmetric = (arrayOfDouble[j][i] == arrayOfDouble[i][j]);
      }
    }
    
    if (issymmetric) {
      for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
          V[i][j] = arrayOfDouble[i][j];
        }
      }
      

      tred2();
      

      tql2();
    }
    else {
      H = new double[n][n];
      ort = new double[n];
      
      for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
          H[j][i] = arrayOfDouble[j][i];
        }
      }
      

      orthes();
      

      hqr2();
    }
  }
  







  public Matrix getV()
  {
    return new Matrix(V, n, n);
  }
  



  public double[] getRealEigenvalues()
  {
    return d;
  }
  



  public double[] getImagEigenvalues()
  {
    return e;
  }
  



  public Matrix getD()
  {
    Matrix localMatrix = new Matrix(n, n);
    double[][] arrayOfDouble = localMatrix.getArray();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        arrayOfDouble[i][j] = 0.0D;
      }
      arrayOfDouble[i][i] = d[i];
      if (e[i] > 0.0D) {
        arrayOfDouble[i][(i + 1)] = e[i];
      } else if (e[i] < 0.0D) {
        arrayOfDouble[i][(i - 1)] = e[i];
      }
    }
    return localMatrix;
  }
}
