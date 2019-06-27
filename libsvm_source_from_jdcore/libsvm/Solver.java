package libsvm;

import java.io.PrintStream;








































































































































































































































































































class Solver
{
  int active_size;
  byte[] y;
  double[] G;
  static final byte LOWER_BOUND = 0;
  static final byte UPPER_BOUND = 1;
  static final byte FREE = 2;
  byte[] alpha_status;
  double[] alpha;
  QMatrix Q;
  double[] QD;
  double eps;
  double Cp;
  double Cn;
  double[] p;
  int[] active_set;
  double[] G_bar;
  int l;
  boolean unshrink;
  static final double INF = Double.POSITIVE_INFINITY;
  
  Solver() {}
  
  double get_C(int paramInt)
  {
    return y[paramInt] > 0 ? Cp : Cn;
  }
  
  void update_alpha_status(int paramInt) {
    if (alpha[paramInt] >= get_C(paramInt)) {
      alpha_status[paramInt] = 1;
    } else if (alpha[paramInt] <= 0.0D)
      alpha_status[paramInt] = 0; else
      alpha_status[paramInt] = 2; }
  
  boolean is_upper_bound(int paramInt) { return alpha_status[paramInt] == 1; }
  boolean is_lower_bound(int paramInt) { return alpha_status[paramInt] == 0; }
  boolean is_free(int paramInt) { return alpha_status[paramInt] == 2; }
  










  void swap_index(int paramInt1, int paramInt2)
  {
    Q.swap_index(paramInt1, paramInt2);
    int i = y[paramInt1];y[paramInt1] = y[paramInt2];y[paramInt2] = i;
    double d1 = G[paramInt1];G[paramInt1] = G[paramInt2];G[paramInt2] = d1;
    int j = alpha_status[paramInt1];alpha_status[paramInt1] = alpha_status[paramInt2];alpha_status[paramInt2] = j;
    double d2 = alpha[paramInt1];alpha[paramInt1] = alpha[paramInt2];alpha[paramInt2] = d2;
    d2 = p[paramInt1];p[paramInt1] = p[paramInt2];p[paramInt2] = d2;
    int k = active_set[paramInt1];active_set[paramInt1] = active_set[paramInt2];active_set[paramInt2] = k;
    double d3 = G_bar[paramInt1];G_bar[paramInt1] = G_bar[paramInt2];G_bar[paramInt2] = d3;
  }
  


  void reconstruct_gradient()
  {
    if (active_size == l) { return;
    }
    
    int k = 0;
    
    for (int j = active_size; j < l; j++) {
      G[j] = (G_bar[j] + p[j]);
    }
    for (j = 0; j < active_size; j++) {
      if (is_free(j))
        k++;
    }
    if (2 * k < active_size)
      svm.info("\nWARNING: using -h 0 may be faster\n");
    float[] arrayOfFloat;
    if (k * l > 2 * active_size * (l - active_size))
    {
      for (i = active_size; i < l; i++)
      {
        arrayOfFloat = Q.get_Q(i, active_size);
        for (j = 0; j < active_size; j++) {
          if (is_free(j)) {
            G[i] += alpha[j] * arrayOfFloat[j];
          }
        }
      }
    }
    for (int i = 0; i < active_size; i++) {
      if (is_free(i))
      {
        arrayOfFloat = Q.get_Q(i, l);
        double d = alpha[i];
        for (j = active_size; j < l; j++) {
          G[j] += d * arrayOfFloat[j];
        }
      }
    }
  }
  
  void Solve(int paramInt1, QMatrix paramQMatrix, double[] paramArrayOfDouble1, byte[] paramArrayOfByte, double[] paramArrayOfDouble2, double paramDouble1, double paramDouble2, double paramDouble3, SolutionInfo paramSolutionInfo, int paramInt2)
  {
    l = paramInt1;
    Q = paramQMatrix;
    QD = paramQMatrix.get_QD();
    p = ((double[])paramArrayOfDouble1.clone());
    y = ((byte[])paramArrayOfByte.clone());
    alpha = ((double[])paramArrayOfDouble2.clone());
    Cp = paramDouble1;
    Cn = paramDouble2;
    eps = paramDouble3;
    unshrink = false;
    


    alpha_status = new byte[paramInt1];
    for (int i = 0; i < paramInt1; i++) {
      update_alpha_status(i);
    }
    


    active_set = new int[paramInt1];
    for (i = 0; i < paramInt1; i++)
      active_set[i] = i;
    active_size = paramInt1;
    



    G = new double[paramInt1];
    G_bar = new double[paramInt1];
    
    for (i = 0; i < paramInt1; i++)
    {
      G[i] = p[i];
      G_bar[i] = 0.0D; }
    int m;
    for (i = 0; i < paramInt1; i++) {
      if (!is_lower_bound(i))
      {
        float[] arrayOfFloat1 = paramQMatrix.get_Q(i, paramInt1);
        double d1 = alpha[i];
        
        for (m = 0; m < paramInt1; m++)
          G[m] += d1 * arrayOfFloat1[m];
        if (is_upper_bound(i)) {
          for (m = 0; m < paramInt1; m++) {
            G_bar[m] += get_C(i) * arrayOfFloat1[m];
          }
        }
      }
    }
    
    i = 0;
    int j = Math.max(10000000, paramInt1 > 21474836 ? Integer.MAX_VALUE : 100 * paramInt1);
    int k = Math.min(paramInt1, 1000) + 1;
    int[] arrayOfInt = new int[2];
    
    while (i < j)
    {


      k--; if (k == 0)
      {
        k = Math.min(paramInt1, 1000);
        if (paramInt2 != 0) do_shrinking();
        svm.info(".");
      }
      
      if (select_working_set(arrayOfInt) != 0)
      {

        reconstruct_gradient();
        
        active_size = paramInt1;
        svm.info("*");
        if (select_working_set(arrayOfInt) != 0) {
          break;
        }
        k = 1;
      }
      
      m = arrayOfInt[0];
      int i1 = arrayOfInt[1];
      
      i++;
      


      float[] arrayOfFloat2 = paramQMatrix.get_Q(m, active_size);
      float[] arrayOfFloat3 = paramQMatrix.get_Q(i1, active_size);
      
      double d3 = get_C(m);
      double d4 = get_C(i1);
      
      double d5 = alpha[m];
      double d6 = alpha[i1];
      double d9;
      if (y[m] != y[i1])
      {
        d7 = QD[m] + QD[i1] + 2.0F * arrayOfFloat2[i1];
        if (d7 <= 0.0D)
          d7 = 1.0E-12D;
        d8 = (-G[m] - G[i1]) / d7;
        d9 = alpha[m] - alpha[i1];
        alpha[m] += d8;
        alpha[i1] += d8;
        
        if (d9 > 0.0D)
        {
          if (alpha[i1] < 0.0D)
          {
            alpha[i1] = 0.0D;
            alpha[m] = d9;
          }
          

        }
        else if (alpha[m] < 0.0D)
        {
          alpha[m] = 0.0D;
          alpha[i1] = (-d9);
        }
        
        if (d9 > d3 - d4)
        {
          if (alpha[m] > d3)
          {
            alpha[m] = d3;
            alpha[i1] = (d3 - d9);
          }
          

        }
        else if (alpha[i1] > d4)
        {
          alpha[i1] = d4;
          alpha[m] = (d4 + d9);
        }
        
      }
      else
      {
        d7 = QD[m] + QD[i1] - 2.0F * arrayOfFloat2[i1];
        if (d7 <= 0.0D)
          d7 = 1.0E-12D;
        d8 = (G[m] - G[i1]) / d7;
        d9 = alpha[m] + alpha[i1];
        alpha[m] -= d8;
        alpha[i1] += d8;
        
        if (d9 > d3)
        {
          if (alpha[m] > d3)
          {
            alpha[m] = d3;
            alpha[i1] = (d9 - d3);
          }
          

        }
        else if (alpha[i1] < 0.0D)
        {
          alpha[i1] = 0.0D;
          alpha[m] = d9;
        }
        
        if (d9 > d4)
        {
          if (alpha[i1] > d4)
          {
            alpha[i1] = d4;
            alpha[m] = (d9 - d4);
          }
          

        }
        else if (alpha[m] < 0.0D)
        {
          alpha[m] = 0.0D;
          alpha[i1] = d9;
        }
      }
      



      double d7 = alpha[m] - d5;
      double d8 = alpha[i1] - d6;
      
      for (int i3 = 0; i3 < active_size; i3++)
      {
        G[i3] += arrayOfFloat2[i3] * d7 + arrayOfFloat3[i3] * d8;
      }
      



      boolean bool1 = is_upper_bound(m);
      boolean bool2 = is_upper_bound(i1);
      update_alpha_status(m);
      update_alpha_status(i1);
      int i4;
      if (bool1 != is_upper_bound(m))
      {
        arrayOfFloat2 = paramQMatrix.get_Q(m, paramInt1);
        if (bool1) {
          for (i4 = 0; i4 < paramInt1; i4++)
            G_bar[i4] -= d3 * arrayOfFloat2[i4];
        }
        for (i4 = 0; i4 < paramInt1; i4++) {
          G_bar[i4] += d3 * arrayOfFloat2[i4];
        }
      }
      if (bool2 != is_upper_bound(i1))
      {
        arrayOfFloat3 = paramQMatrix.get_Q(i1, paramInt1);
        if (bool2) {
          for (i4 = 0; i4 < paramInt1; i4++)
            G_bar[i4] -= d4 * arrayOfFloat3[i4];
        }
        for (i4 = 0; i4 < paramInt1; i4++) {
          G_bar[i4] += d4 * arrayOfFloat3[i4];
        }
      }
    }
    

    if (i >= j)
    {
      if (active_size < paramInt1)
      {

        reconstruct_gradient();
        active_size = paramInt1;
        svm.info("*");
      }
      System.err.print("\nWARNING: reaching max number of iterations\n");
    }
    


    rho = calculate_rho();
    


    double d2 = 0.0D;
    
    for (int i2 = 0; i2 < paramInt1; i2++) {
      d2 += alpha[i2] * (G[i2] + p[i2]);
    }
    obj = (d2 / 2.0D);
    



    for (int n = 0; n < paramInt1; n++) {
      paramArrayOfDouble2[active_set[n]] = alpha[n];
    }
    
    upper_bound_p = paramDouble1;
    upper_bound_n = paramDouble2;
    
    svm.info("\noptimization finished, #iter = " + i + "\n");
  }
  







  int select_working_set(int[] paramArrayOfInt)
  {
    double d1 = Double.NEGATIVE_INFINITY;
    double d2 = Double.NEGATIVE_INFINITY;
    int i = -1;
    int j = -1;
    double d3 = Double.POSITIVE_INFINITY;
    
    for (int k = 0; k < active_size; k++) {
      if (y[k] == 1)
      {
        if ((!is_upper_bound(k)) && 
          (-G[k] >= d1))
        {
          d1 = -G[k];
          i = k;
        }
        

      }
      else if ((!is_lower_bound(k)) && 
        (G[k] >= d1))
      {
        d1 = G[k];
        i = k;
      }
    }
    
    k = i;
    float[] arrayOfFloat = null;
    if (k != -1) {
      arrayOfFloat = Q.get_Q(k, active_size);
    }
    for (int m = 0; m < active_size; m++) { double d4;
      double d6;
      double d5; if (y[m] == 1)
      {
        if (!is_lower_bound(m))
        {
          d4 = d1 + G[m];
          if (G[m] >= d2)
            d2 = G[m];
          if (d4 > 0.0D)
          {

            d6 = QD[k] + QD[m] - 2.0D * y[k] * arrayOfFloat[m];
            if (d6 > 0.0D) {
              d5 = -(d4 * d4) / d6;
            } else {
              d5 = -(d4 * d4) / 1.0E-12D;
            }
            if (d5 <= d3)
            {
              j = m;
              d3 = d5;
            }
            
          }
          
        }
      }
      else if (!is_upper_bound(m))
      {
        d4 = d1 - G[m];
        if (-G[m] >= d2)
          d2 = -G[m];
        if (d4 > 0.0D)
        {

          d6 = QD[k] + QD[m] + 2.0D * y[k] * arrayOfFloat[m];
          if (d6 > 0.0D) {
            d5 = -(d4 * d4) / d6;
          } else {
            d5 = -(d4 * d4) / 1.0E-12D;
          }
          if (d5 <= d3)
          {
            j = m;
            d3 = d5;
          }
        }
      }
    }
    

    if (d1 + d2 < eps) {
      return 1;
    }
    paramArrayOfInt[0] = i;
    paramArrayOfInt[1] = j;
    return 0;
  }
  
  private boolean be_shrunk(int paramInt, double paramDouble1, double paramDouble2)
  {
    if (is_upper_bound(paramInt))
    {
      if (y[paramInt] == 1) {
        return -G[paramInt] > paramDouble1;
      }
      return -G[paramInt] > paramDouble2;
    }
    if (is_lower_bound(paramInt))
    {
      if (y[paramInt] == 1) {
        return G[paramInt] > paramDouble2;
      }
      return G[paramInt] > paramDouble1;
    }
    
    return false;
  }
  

  void do_shrinking()
  {
    double d1 = Double.NEGATIVE_INFINITY;
    double d2 = Double.NEGATIVE_INFINITY;
    

    for (int i = 0; i < active_size; i++)
    {
      if (y[i] == 1)
      {
        if (!is_upper_bound(i))
        {
          if (-G[i] >= d1)
            d1 = -G[i];
        }
        if (!is_lower_bound(i))
        {
          if (G[i] >= d2) {
            d2 = G[i];
          }
        }
      }
      else {
        if (!is_upper_bound(i))
        {
          if (-G[i] >= d2)
            d2 = -G[i];
        }
        if (!is_lower_bound(i))
        {
          if (G[i] >= d1) {
            d1 = G[i];
          }
        }
      }
    }
    if ((!unshrink) && (d1 + d2 <= eps * 10.0D))
    {
      unshrink = true;
      reconstruct_gradient();
      active_size = l;
    }
    
    for (i = 0; i < active_size; i++) {
      if (be_shrunk(i, d1, d2))
      {
        active_size -= 1;
        while (active_size > i)
        {
          if (!be_shrunk(active_size, d1, d2))
          {
            swap_index(i, active_size);
            break;
          }
          active_size -= 1;
        }
      }
    }
  }
  
  double calculate_rho()
  {
    int i = 0;
    double d2 = Double.POSITIVE_INFINITY;double d3 = Double.NEGATIVE_INFINITY;double d4 = 0.0D;
    for (int j = 0; j < active_size; j++)
    {
      double d5 = y[j] * G[j];
      
      if (is_lower_bound(j))
      {
        if (y[j] > 0) {
          d2 = Math.min(d2, d5);
        } else {
          d3 = Math.max(d3, d5);
        }
      } else if (is_upper_bound(j))
      {
        if (y[j] < 0) {
          d2 = Math.min(d2, d5);
        } else {
          d3 = Math.max(d3, d5);
        }
      }
      else {
        i++;
        d4 += d5;
      }
    }
    double d1;
    if (i > 0) {
      d1 = d4 / i;
    } else {
      d1 = (d2 + d3) / 2.0D;
    }
    return d1;
  }
  
  static class SolutionInfo
  {
    double obj;
    double rho;
    double upper_bound_p;
    double upper_bound_n;
    double r;
    
    SolutionInfo() {}
  }
}
