package libsvm;











































































































































































































































































































final class Solver_NU
  extends Solver
{
  private Solver.SolutionInfo si;
  










































































































































































































































































































  Solver_NU() {}
  









































































































































































































































































































  void Solve(int paramInt1, QMatrix paramQMatrix, double[] paramArrayOfDouble1, byte[] paramArrayOfByte, double[] paramArrayOfDouble2, double paramDouble1, double paramDouble2, double paramDouble3, Solver.SolutionInfo paramSolutionInfo, int paramInt2)
  {
    si = paramSolutionInfo;
    super.Solve(paramInt1, paramQMatrix, paramArrayOfDouble1, paramArrayOfByte, paramArrayOfDouble2, paramDouble1, paramDouble2, paramDouble3, paramSolutionInfo, paramInt2);
  }
  







  int select_working_set(int[] paramArrayOfInt)
  {
    double d1 = Double.NEGATIVE_INFINITY;
    double d2 = Double.NEGATIVE_INFINITY;
    int i = -1;
    
    double d3 = Double.NEGATIVE_INFINITY;
    double d4 = Double.NEGATIVE_INFINITY;
    int j = -1;
    
    int k = -1;
    double d5 = Double.POSITIVE_INFINITY;
    
    for (int m = 0; m < active_size; m++) {
      if (y[m] == 1)
      {
        if ((!is_upper_bound(m)) && 
          (-G[m] >= d1))
        {
          d1 = -G[m];
          i = m;
        }
        

      }
      else if ((!is_lower_bound(m)) && 
        (G[m] >= d3))
      {
        d3 = G[m];
        j = m;
      }
    }
    
    m = i;
    int n = j;
    float[] arrayOfFloat1 = null;
    float[] arrayOfFloat2 = null;
    if (m != -1)
      arrayOfFloat1 = Q.get_Q(m, active_size);
    if (n != -1) {
      arrayOfFloat2 = Q.get_Q(n, active_size);
    }
    for (int i1 = 0; i1 < active_size; i1++) { double d6;
      double d8;
      double d7; if (y[i1] == 1)
      {
        if (!is_lower_bound(i1))
        {
          d6 = d1 + G[i1];
          if (G[i1] >= d2)
            d2 = G[i1];
          if (d6 > 0.0D)
          {

            d8 = QD[m] + QD[i1] - 2.0F * arrayOfFloat1[i1];
            if (d8 > 0.0D) {
              d7 = -(d6 * d6) / d8;
            } else {
              d7 = -(d6 * d6) / 1.0E-12D;
            }
            if (d7 <= d5)
            {
              k = i1;
              d5 = d7;
            }
            
          }
          
        }
      }
      else if (!is_upper_bound(i1))
      {
        d6 = d3 - G[i1];
        if (-G[i1] >= d4)
          d4 = -G[i1];
        if (d6 > 0.0D)
        {

          d8 = QD[n] + QD[i1] - 2.0F * arrayOfFloat2[i1];
          if (d8 > 0.0D) {
            d7 = -(d6 * d6) / d8;
          } else {
            d7 = -(d6 * d6) / 1.0E-12D;
          }
          if (d7 <= d5)
          {
            k = i1;
            d5 = d7;
          }
        }
      }
    }
    

    if (Math.max(d1 + d2, d3 + d4) < eps) {
      return 1;
    }
    if (y[k] == 1) {
      paramArrayOfInt[0] = i;
    } else
      paramArrayOfInt[0] = j;
    paramArrayOfInt[1] = k;
    
    return 0;
  }
  
  private boolean be_shrunk(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    if (is_upper_bound(paramInt))
    {
      if (y[paramInt] == 1) {
        return -G[paramInt] > paramDouble1;
      }
      return -G[paramInt] > paramDouble4;
    }
    if (is_lower_bound(paramInt))
    {
      if (y[paramInt] == 1) {
        return G[paramInt] > paramDouble2;
      }
      return G[paramInt] > paramDouble3;
    }
    
    return false;
  }
  
  void do_shrinking()
  {
    double d1 = Double.NEGATIVE_INFINITY;
    double d2 = Double.NEGATIVE_INFINITY;
    double d3 = Double.NEGATIVE_INFINITY;
    double d4 = Double.NEGATIVE_INFINITY;
    


    for (int i = 0; i < active_size; i++)
    {
      if (!is_upper_bound(i))
      {
        if (y[i] == 1)
        {
          if (-G[i] > d1) d1 = -G[i];
        }
        else if (-G[i] > d4) d4 = -G[i];
      }
      if (!is_lower_bound(i))
      {
        if (y[i] == 1)
        {
          if (G[i] > d2) d2 = G[i];
        }
        else if (G[i] > d3) { d3 = G[i];
        }
      }
    }
    if ((!unshrink) && (Math.max(d1 + d2, d3 + d4) <= eps * 10.0D))
    {
      unshrink = true;
      reconstruct_gradient();
      active_size = l;
    }
    
    for (i = 0; i < active_size; i++) {
      if (be_shrunk(i, d1, d2, d3, d4))
      {
        active_size -= 1;
        while (active_size > i)
        {
          if (!be_shrunk(active_size, d1, d2, d3, d4))
          {
            swap_index(i, active_size);
            break;
          }
          active_size -= 1;
        }
      }
    }
  }
  
  double calculate_rho() {
    int i = 0;int j = 0;
    double d1 = Double.POSITIVE_INFINITY;double d2 = Double.POSITIVE_INFINITY;
    double d3 = Double.NEGATIVE_INFINITY;double d4 = Double.NEGATIVE_INFINITY;
    double d5 = 0.0D;double d6 = 0.0D;
    
    for (int k = 0; k < active_size; k++)
    {
      if (y[k] == 1)
      {
        if (is_lower_bound(k)) {
          d1 = Math.min(d1, G[k]);
        } else if (is_upper_bound(k)) {
          d3 = Math.max(d3, G[k]);
        }
        else {
          i++;
          d5 += G[k];
        }
        

      }
      else if (is_lower_bound(k)) {
        d2 = Math.min(d2, G[k]);
      } else if (is_upper_bound(k)) {
        d4 = Math.max(d4, G[k]);
      }
      else {
        j++;
        d6 += G[k];
      }
    }
    
    double d7;
    
    if (i > 0) {
      d7 = d5 / i;
    } else
      d7 = (d1 + d3) / 2.0D;
    double d8;
    if (j > 0) {
      d8 = d6 / j;
    } else {
      d8 = (d2 + d4) / 2.0D;
    }
    si.r = ((d7 + d8) / 2.0D);
    return (d7 - d8) / 2.0D;
  }
}
