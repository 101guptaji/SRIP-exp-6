package libsvm;

import [I;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
import java.util.StringTokenizer;

































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































































public class svm
{
  public static final int LIBSVM_VERSION = 317;
  public static final Random rand = new Random();
  
  private static svm_print_interface svm_print_stdout = new svm_print_interface()
  {
    public void print(String paramAnonymousString)
    {
      System.out.print(paramAnonymousString);
      System.out.flush();
    }
  };
  
  private static svm_print_interface svm_print_string = svm_print_stdout;
  
  public svm() {}
  
  static void info(String paramString) { svm_print_string.print(paramString); }
  



  private static void solve_c_svc(svm_problem paramSvm_problem, svm_parameter paramSvm_parameter, double[] paramArrayOfDouble, Solver.SolutionInfo paramSolutionInfo, double paramDouble1, double paramDouble2)
  {
    int i = l;
    double[] arrayOfDouble = new double[i];
    byte[] arrayOfByte = new byte[i];
    


    for (int j = 0; j < i; j++)
    {
      paramArrayOfDouble[j] = 0.0D;
      arrayOfDouble[j] = -1.0D;
      if (y[j] > 0.0D) arrayOfByte[j] = 1; else { arrayOfByte[j] = -1;
      }
    }
    Solver localSolver = new Solver();
    localSolver.Solve(i, new SVC_Q(paramSvm_problem, paramSvm_parameter, arrayOfByte), arrayOfDouble, arrayOfByte, paramArrayOfDouble, paramDouble1, paramDouble2, eps, paramSolutionInfo, shrinking);
    

    double d = 0.0D;
    for (j = 0; j < i; j++) {
      d += paramArrayOfDouble[j];
    }
    if (paramDouble1 == paramDouble2) {
      info("nu = " + d / (paramDouble1 * l) + "\n");
    }
    for (j = 0; j < i; j++) {
      paramArrayOfDouble[j] *= arrayOfByte[j];
    }
  }
  

  private static void solve_nu_svc(svm_problem paramSvm_problem, svm_parameter paramSvm_parameter, double[] paramArrayOfDouble, Solver.SolutionInfo paramSolutionInfo)
  {
    int j = l;
    double d1 = nu;
    
    byte[] arrayOfByte = new byte[j];
    
    for (int i = 0; i < j; i++) {
      if (y[i] > 0.0D) {
        arrayOfByte[i] = 1;
      } else
        arrayOfByte[i] = -1;
    }
    double d2 = d1 * j / 2.0D;
    double d3 = d1 * j / 2.0D;
    
    for (i = 0; i < j; i++) {
      if (arrayOfByte[i] == 1)
      {
        paramArrayOfDouble[i] = Math.min(1.0D, d2);
        d2 -= paramArrayOfDouble[i];
      }
      else
      {
        paramArrayOfDouble[i] = Math.min(1.0D, d3);
        d3 -= paramArrayOfDouble[i];
      }
    }
    double[] arrayOfDouble = new double[j];
    
    for (i = 0; i < j; i++) {
      arrayOfDouble[i] = 0.0D;
    }
    Solver_NU localSolver_NU = new Solver_NU();
    localSolver_NU.Solve(j, new SVC_Q(paramSvm_problem, paramSvm_parameter, arrayOfByte), arrayOfDouble, arrayOfByte, paramArrayOfDouble, 1.0D, 1.0D, eps, paramSolutionInfo, shrinking);
    
    double d4 = r;
    
    info("C = " + 1.0D / d4 + "\n");
    
    for (i = 0; i < j; i++) {
      paramArrayOfDouble[i] *= arrayOfByte[i] / d4;
    }
    rho /= d4;
    obj /= d4 * d4;
    upper_bound_p = (1.0D / d4);
    upper_bound_n = (1.0D / d4);
  }
  

  private static void solve_one_class(svm_problem paramSvm_problem, svm_parameter paramSvm_parameter, double[] paramArrayOfDouble, Solver.SolutionInfo paramSolutionInfo)
  {
    int i = l;
    double[] arrayOfDouble = new double[i];
    byte[] arrayOfByte = new byte[i];
    

    int k = (int)(nu * l);
    
    for (int j = 0; j < k; j++)
      paramArrayOfDouble[j] = 1.0D;
    if (k < l)
      paramArrayOfDouble[k] = (nu * l - k);
    for (j = k + 1; j < i; j++) {
      paramArrayOfDouble[j] = 0.0D;
    }
    for (j = 0; j < i; j++)
    {
      arrayOfDouble[j] = 0.0D;
      arrayOfByte[j] = 1;
    }
    
    Solver localSolver = new Solver();
    localSolver.Solve(i, new ONE_CLASS_Q(paramSvm_problem, paramSvm_parameter), arrayOfDouble, arrayOfByte, paramArrayOfDouble, 1.0D, 1.0D, eps, paramSolutionInfo, shrinking);
  }
  


  private static void solve_epsilon_svr(svm_problem paramSvm_problem, svm_parameter paramSvm_parameter, double[] paramArrayOfDouble, Solver.SolutionInfo paramSolutionInfo)
  {
    int i = l;
    double[] arrayOfDouble1 = new double[2 * i];
    double[] arrayOfDouble2 = new double[2 * i];
    byte[] arrayOfByte = new byte[2 * i];
    

    for (int j = 0; j < i; j++)
    {
      arrayOfDouble1[j] = 0.0D;
      arrayOfDouble2[j] = (p - y[j]);
      arrayOfByte[j] = 1;
      
      arrayOfDouble1[(j + i)] = 0.0D;
      arrayOfDouble2[(j + i)] = (p + y[j]);
      arrayOfByte[(j + i)] = -1;
    }
    
    Solver localSolver = new Solver();
    localSolver.Solve(2 * i, new SVR_Q(paramSvm_problem, paramSvm_parameter), arrayOfDouble2, arrayOfByte, arrayOfDouble1, C, C, eps, paramSolutionInfo, shrinking);
    

    double d = 0.0D;
    for (j = 0; j < i; j++)
    {
      arrayOfDouble1[j] -= arrayOfDouble1[(j + i)];
      d += Math.abs(paramArrayOfDouble[j]);
    }
    info("nu = " + d / (C * i) + "\n");
  }
  

  private static void solve_nu_svr(svm_problem paramSvm_problem, svm_parameter paramSvm_parameter, double[] paramArrayOfDouble, Solver.SolutionInfo paramSolutionInfo)
  {
    int i = l;
    double d1 = C;
    double[] arrayOfDouble1 = new double[2 * i];
    double[] arrayOfDouble2 = new double[2 * i];
    byte[] arrayOfByte = new byte[2 * i];
    

    double d2 = d1 * nu * i / 2.0D;
    for (int j = 0; j < i; j++)
    {
      double tmp81_78 = Math.min(d2, d1);arrayOfDouble1[(j + i)] = tmp81_78;arrayOfDouble1[j] = tmp81_78;
      d2 -= arrayOfDouble1[j];
      
      arrayOfDouble2[j] = (-y[j]);
      arrayOfByte[j] = 1;
      
      arrayOfDouble2[(j + i)] = y[j];
      arrayOfByte[(j + i)] = -1;
    }
    
    Solver_NU localSolver_NU = new Solver_NU();
    localSolver_NU.Solve(2 * i, new SVR_Q(paramSvm_problem, paramSvm_parameter), arrayOfDouble2, arrayOfByte, arrayOfDouble1, d1, d1, eps, paramSolutionInfo, shrinking);
    

    info("epsilon = " + -r + "\n");
    
    for (j = 0; j < i; j++) {
      arrayOfDouble1[j] -= arrayOfDouble1[(j + i)];
    }
  }
  










  static decision_function svm_train_one(svm_problem paramSvm_problem, svm_parameter paramSvm_parameter, double paramDouble1, double paramDouble2)
  {
    double[] arrayOfDouble = new double[l];
    Solver.SolutionInfo localSolutionInfo = new Solver.SolutionInfo();
    switch (svm_type)
    {
    case 0: 
      solve_c_svc(paramSvm_problem, paramSvm_parameter, arrayOfDouble, localSolutionInfo, paramDouble1, paramDouble2);
      break;
    case 1: 
      solve_nu_svc(paramSvm_problem, paramSvm_parameter, arrayOfDouble, localSolutionInfo);
      break;
    case 2: 
      solve_one_class(paramSvm_problem, paramSvm_parameter, arrayOfDouble, localSolutionInfo);
      break;
    case 3: 
      solve_epsilon_svr(paramSvm_problem, paramSvm_parameter, arrayOfDouble, localSolutionInfo);
      break;
    case 4: 
      solve_nu_svr(paramSvm_problem, paramSvm_parameter, arrayOfDouble, localSolutionInfo);
    }
    
    
    info("obj = " + obj + ", rho = " + rho + "\n");
    


    int i = 0;
    int j = 0;
    for (int k = 0; k < l; k++)
    {
      if (Math.abs(arrayOfDouble[k]) > 0.0D)
      {
        i++;
        if (y[k] > 0.0D)
        {
          if (Math.abs(arrayOfDouble[k]) >= upper_bound_p) {
            j++;
          }
          
        }
        else if (Math.abs(arrayOfDouble[k]) >= upper_bound_n) {
          j++;
        }
      }
    }
    
    info("nSV = " + i + ", nBSV = " + j + "\n");
    
    decision_function localDecision_function = new decision_function();
    alpha = arrayOfDouble;
    rho = rho;
    return localDecision_function;
  }
  



  private static void sigmoid_train(int paramInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3)
  {
    double d3 = 0.0D;double d4 = 0.0D;
    

    for (int i = 0; i < paramInt; i++) {
      if (paramArrayOfDouble2[i] > 0.0D) d3 += 1.0D; else
        d4 += 1.0D;
    }
    int j = 100;
    double d5 = 1.0E-10D;
    double d6 = 1.0E-12D;
    double d7 = 1.0E-5D;
    double d8 = (d3 + 1.0D) / (d3 + 2.0D);
    double d9 = 1.0D / (d4 + 2.0D);
    double[] arrayOfDouble = new double[paramInt];
    




    double d1 = 0.0D;double d2 = Math.log((d4 + 1.0D) / (d3 + 1.0D));
    double d28 = 0.0D;
    double d10;
    for (i = 0; i < paramInt; i++)
    {
      if (paramArrayOfDouble2[i] > 0.0D) arrayOfDouble[i] = d8; else
        arrayOfDouble[i] = d9;
      d10 = paramArrayOfDouble1[i] * d1 + d2;
      if (d10 >= 0.0D) {
        d28 += arrayOfDouble[i] * d10 + Math.log(1.0D + Math.exp(-d10));
      } else
        d28 += (arrayOfDouble[i] - 1.0D) * d10 + Math.log(1.0D + Math.exp(d10));
    }
    for (int k = 0; k < j; k++)
    {

      double d13 = d6;
      double d14 = d6;
      double d15 = 0.0D;double d16 = 0.0D;double d17 = 0.0D;
      for (i = 0; i < paramInt; i++)
      {
        d10 = paramArrayOfDouble1[i] * d1 + d2;
        double d11; double d12; if (d10 >= 0.0D)
        {
          d11 = Math.exp(-d10) / (1.0D + Math.exp(-d10));
          d12 = 1.0D / (1.0D + Math.exp(-d10));
        }
        else
        {
          d11 = 1.0D / (1.0D + Math.exp(d10));
          d12 = Math.exp(d10) / (1.0D + Math.exp(d10));
        }
        double d27 = d11 * d12;
        d13 += paramArrayOfDouble1[i] * paramArrayOfDouble1[i] * d27;
        d14 += d27;
        d15 += paramArrayOfDouble1[i] * d27;
        double d26 = arrayOfDouble[i] - d11;
        d16 += paramArrayOfDouble1[i] * d26;
        d17 += d26;
      }
      

      if ((Math.abs(d16) < d7) && (Math.abs(d17) < d7)) {
        break;
      }
      
      double d18 = d13 * d14 - d15 * d15;
      double d19 = -(d14 * d16 - d15 * d17) / d18;
      double d20 = -(-d15 * d16 + d13 * d17) / d18;
      double d21 = d16 * d19 + d17 * d20;
      

      double d22 = 1.0D;
      while (d22 >= d5)
      {
        double d23 = d1 + d22 * d19;
        double d24 = d2 + d22 * d20;
        

        double d25 = 0.0D;
        for (i = 0; i < paramInt; i++)
        {
          d10 = paramArrayOfDouble1[i] * d23 + d24;
          if (d10 >= 0.0D) {
            d25 += arrayOfDouble[i] * d10 + Math.log(1.0D + Math.exp(-d10));
          } else {
            d25 += (arrayOfDouble[i] - 1.0D) * d10 + Math.log(1.0D + Math.exp(d10));
          }
        }
        if (d25 < d28 + 1.0E-4D * d22 * d21)
        {
          d1 = d23;d2 = d24;d28 = d25;
          break;
        }
        
        d22 /= 2.0D;
      }
      
      if (d22 < d5)
      {
        info("Line search fails in two-class probability estimates\n");
        break;
      }
    }
    
    if (k >= j)
      info("Reaching maximal iterations in two-class probability estimates\n");
    paramArrayOfDouble3[0] = d1;paramArrayOfDouble3[1] = d2;
  }
  
  private static double sigmoid_predict(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    double d = paramDouble1 * paramDouble2 + paramDouble3;
    if (d >= 0.0D) {
      return Math.exp(-d) / (1.0D + Math.exp(-d));
    }
    return 1.0D / (1.0D + Math.exp(d));
  }
  


  private static void multiclass_probability(int paramInt, double[][] paramArrayOfDouble, double[] paramArrayOfDouble1)
  {
    int k = 0;int m = Math.max(100, paramInt);
    double[][] arrayOfDouble = new double[paramInt][paramInt];
    double[] arrayOfDouble1 = new double[paramInt];
    double d2 = 0.005D / paramInt;
    int j;
    for (int i = 0; i < paramInt; i++)
    {
      paramArrayOfDouble1[i] = (1.0D / paramInt);
      arrayOfDouble[i][i] = 0.0D;
      for (j = 0; j < i; j++)
      {
        arrayOfDouble[i][i] += paramArrayOfDouble[j][i] * paramArrayOfDouble[j][i];
        arrayOfDouble[i][j] = arrayOfDouble[j][i];
      }
      for (j = i + 1; j < paramInt; j++)
      {
        arrayOfDouble[i][i] += paramArrayOfDouble[j][i] * paramArrayOfDouble[j][i];
        arrayOfDouble[i][j] = (-paramArrayOfDouble[j][i] * paramArrayOfDouble[i][j]);
      }
    }
    for (k = 0; k < m; k++)
    {

      double d1 = 0.0D;
      for (i = 0; i < paramInt; i++)
      {
        arrayOfDouble1[i] = 0.0D;
        for (j = 0; j < paramInt; j++)
          arrayOfDouble1[i] += arrayOfDouble[i][j] * paramArrayOfDouble1[j];
        d1 += paramArrayOfDouble1[i] * arrayOfDouble1[i];
      }
      double d3 = 0.0D;
      double d4; for (i = 0; i < paramInt; i++)
      {
        d4 = Math.abs(arrayOfDouble1[i] - d1);
        if (d4 > d3)
          d3 = d4;
      }
      if (d3 < d2)
        break;
      for (i = 0; i < paramInt; i++)
      {
        d4 = (-arrayOfDouble1[i] + d1) / arrayOfDouble[i][i];
        paramArrayOfDouble1[i] += d4;
        d1 = (d1 + d4 * (d4 * arrayOfDouble[i][i] + 2.0D * arrayOfDouble1[i])) / (1.0D + d4) / (1.0D + d4);
        for (j = 0; j < paramInt; j++)
        {
          arrayOfDouble1[j] = ((arrayOfDouble1[j] + d4 * arrayOfDouble[i][j]) / (1.0D + d4));
          paramArrayOfDouble1[j] /= (1.0D + d4);
        }
      }
    }
    if (k >= m) {
      info("Exceeds max_iter in multiclass_prob\n");
    }
  }
  

  private static void svm_binary_svc_probability(svm_problem paramSvm_problem, svm_parameter paramSvm_parameter, double paramDouble1, double paramDouble2, double[] paramArrayOfDouble)
  {
    int j = 5;
    int[] arrayOfInt = new int[l];
    double[] arrayOfDouble1 = new double[l];
    

    for (int i = 0; i < l; i++) arrayOfInt[i] = i;
    int k; int m; for (i = 0; i < l; i++)
    {
      k = i + rand.nextInt(l - i);
      m = arrayOfInt[i];arrayOfInt[i] = arrayOfInt[k];arrayOfInt[k] = m; }
    svm_problem localSvm_problem;
    int n; int i2; int i3; for (i = 0; i < j; i++)
    {
      k = i * l / j;
      m = (i + 1) * l / j;
      
      localSvm_problem = new svm_problem();
      
      l -= m - k;
      x = new svm_node[l][];
      y = new double[l];
      
      int i1 = 0;
      for (n = 0; n < k; n++)
      {
        x[i1] = x[arrayOfInt[n]];
        y[i1] = y[arrayOfInt[n]];
        i1++;
      }
      for (n = m; n < l; n++)
      {
        x[i1] = x[arrayOfInt[n]];
        y[i1] = y[arrayOfInt[n]];
        i1++;
      }
      i2 = 0;i3 = 0;
      for (n = 0; n < i1; n++) {
        if (y[n] > 0.0D) {
          i2++;
        } else
          i3++;
      }
      if ((i2 == 0) && (i3 == 0))
        for (n = k; n < m;) {
          arrayOfDouble1[arrayOfInt[n]] = 0.0D;n++; continue;
          if ((i2 > 0) && (i3 == 0))
            n = k; for (;;) { if (n < m) {
              arrayOfDouble1[arrayOfInt[n]] = 1.0D;n++; continue;
              if ((i2 == 0) && (i3 > 0))
                for (n = k; n < m;) {
                  arrayOfDouble1[arrayOfInt[n]] = -1.0D;n++; continue;
                  

                  svm_parameter localSvm_parameter = (svm_parameter)paramSvm_parameter.clone();
                  probability = 0;
                  C = 1.0D;
                  nr_weight = 2;
                  weight_label = new int[2];
                  weight = new double[2];
                  weight_label[0] = 1;
                  weight_label[1] = -1;
                  weight[0] = paramDouble1;
                  weight[1] = paramDouble2;
                  svm_model localSvm_model = svm_train(localSvm_problem, localSvm_parameter);
                  for (n = k; n < m; n++)
                  {
                    double[] arrayOfDouble2 = new double[1];
                    svm_predict_values(localSvm_model, x[arrayOfInt[n]], arrayOfDouble2);
                    arrayOfDouble1[arrayOfInt[n]] = arrayOfDouble2[0];
                    
                    arrayOfDouble1[arrayOfInt[n]] *= label[0];
                  }
                }
            } } } }
    sigmoid_train(l, arrayOfDouble1, y, paramArrayOfDouble);
  }
  


  private static double svm_svr_probability(svm_problem paramSvm_problem, svm_parameter paramSvm_parameter)
  {
    int j = 5;
    double[] arrayOfDouble = new double[l];
    double d1 = 0.0D;
    
    svm_parameter localSvm_parameter = (svm_parameter)paramSvm_parameter.clone();
    probability = 0;
    svm_cross_validation(paramSvm_problem, localSvm_parameter, j, arrayOfDouble);
    for (int i = 0; i < l; i++)
    {
      arrayOfDouble[i] = (y[i] - arrayOfDouble[i]);
      d1 += Math.abs(arrayOfDouble[i]);
    }
    d1 /= l;
    double d2 = Math.sqrt(2.0D * d1 * d1);
    int k = 0;
    d1 = 0.0D;
    for (i = 0; i < l; i++)
      if (Math.abs(arrayOfDouble[i]) > 5.0D * d2) {
        k += 1;
      } else
        d1 += Math.abs(arrayOfDouble[i]);
    d1 /= (l - k);
    info("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma=" + d1 + "\n");
    return d1;
  }
  


  private static void svm_group_classes(svm_problem paramSvm_problem, int[] paramArrayOfInt1, int[][] paramArrayOfInt2, int[][] paramArrayOfInt3, int[][] paramArrayOfInt4, int[] paramArrayOfInt5)
  {
    int i = l;
    int j = 16;
    int k = 0;
    Object localObject1 = new int[j];
    Object localObject2 = new int[j];
    int[] arrayOfInt1 = new int[i];
    
    int n;
    for (int m = 0; m < i; m++)
    {
      n = (int)y[m];
      
      for (int i1 = 0; i1 < k; i1++)
      {
        if (n == localObject1[i1])
        {
          localObject2[i1] += 1;
          break;
        }
      }
      arrayOfInt1[m] = i1;
      if (i1 == k)
      {
        if (k == j)
        {
          j *= 2;
          int[] arrayOfInt3 = new int[j];
          System.arraycopy(localObject1, 0, arrayOfInt3, 0, localObject1.length);
          localObject1 = arrayOfInt3;
          arrayOfInt3 = new int[j];
          System.arraycopy(localObject2, 0, arrayOfInt3, 0, localObject2.length);
          localObject2 = arrayOfInt3;
        }
        localObject1[k] = n;
        localObject2[k] = 1;
        k++;
      }
    }
    





    if ((k == 2) && (localObject1[0] == -1) && (localObject1[1] == 1))
    {
      n = localObject1[0];localObject1[0] = localObject1[1];localObject1[1] = n;
      n = localObject2[0];localObject2[0] = localObject2[1];localObject2[1] = n;
      for (m = 0; m < i; m++)
      {
        if (arrayOfInt1[m] == 0) {
          arrayOfInt1[m] = 1;
        } else {
          arrayOfInt1[m] = 0;
        }
      }
    }
    int[] arrayOfInt2 = new int[k];
    arrayOfInt2[0] = 0;
    for (m = 1; m < k; m++)
      arrayOfInt2[m] = (arrayOfInt2[(m - 1)] + localObject2[(m - 1)]);
    for (m = 0; m < i; m++)
    {
      paramArrayOfInt5[arrayOfInt2[arrayOfInt1[m]]] = m;
      arrayOfInt2[arrayOfInt1[m]] += 1;
    }
    arrayOfInt2[0] = 0;
    for (m = 1; m < k; m++) {
      arrayOfInt2[m] = (arrayOfInt2[(m - 1)] + localObject2[(m - 1)]);
    }
    paramArrayOfInt1[0] = k;
    paramArrayOfInt2[0] = localObject1;
    paramArrayOfInt3[0] = arrayOfInt2;
    paramArrayOfInt4[0] = localObject2;
  }
  



  public static svm_model svm_train(svm_problem paramSvm_problem, svm_parameter paramSvm_parameter)
  {
    svm_model localSvm_model = new svm_model();
    param = paramSvm_parameter;
    
    if ((svm_type == 2) || (svm_type == 3) || (svm_type == 4))
    {



      nr_class = 2;
      label = null;
      nSV = null;
      probA = null;probB = null;
      sv_coef = new double[1][];
      
      if ((probability == 1) && ((svm_type == 3) || (svm_type == 4)))
      {


        probA = new double[1];
        probA[0] = svm_svr_probability(paramSvm_problem, paramSvm_parameter);
      }
      
      decision_function localDecision_function = svm_train_one(paramSvm_problem, paramSvm_parameter, 0.0D, 0.0D);
      rho = new double[1];
      rho[0] = rho;
      
      int j = 0;
      
      for (int k = 0; k < l; k++)
        if (Math.abs(alpha[k]) > 0.0D) j++;
      l = j;
      SV = new svm_node[j][];
      sv_coef[0] = new double[j];
      sv_indices = new int[j];
      int m = 0;
      for (k = 0; k < l; k++) {
        if (Math.abs(alpha[k]) > 0.0D)
        {
          SV[m] = x[k];
          sv_coef[0][m] = alpha[k];
          sv_indices[m] = (k + 1);
          m++;
        }
      }
    }
    else
    {
      int i = l;
      int[] arrayOfInt1 = new int[1];
      int[][] arrayOfInt2 = new int[1][];
      int[][] arrayOfInt3 = new int[1][];
      int[][] arrayOfInt4 = new int[1][];
      int[] arrayOfInt5 = new int[i];
      

      svm_group_classes(paramSvm_problem, arrayOfInt1, arrayOfInt2, arrayOfInt3, arrayOfInt4, arrayOfInt5);
      int n = arrayOfInt1[0];
      [I local[I1 = arrayOfInt2[0];
      [I local[I2 = arrayOfInt3[0];
      [I local[I3 = arrayOfInt4[0];
      
      if (n == 1) {
        info("WARNING: training data in only one class. See README for details.\n");
      }
      svm_node[][] arrayOfSvm_node; = new svm_node[i][];
      
      for (int i1 = 0; i1 < i; i1++) {
        arrayOfSvm_node;[i1] = x[arrayOfInt5[i1]];
      }
      

      double[] arrayOfDouble1 = new double[n];
      for (i1 = 0; i1 < n; i1++)
        arrayOfDouble1[i1] = C;
      for (i1 = 0; i1 < nr_weight; i1++)
      {

        for (int i2 = 0; i2 < n; i2++)
          if (weight_label[i1] == local[I1[i2])
            break;
        if (i2 == n) {
          System.err.print("WARNING: class label " + weight_label[i1] + " specified in weight is not found\n");
        } else {
          arrayOfDouble1[i2] *= weight[i1];
        }
      }
      

      boolean[] arrayOfBoolean = new boolean[i];
      for (i1 = 0; i1 < i; i1++)
        arrayOfBoolean[i1] = false;
      decision_function[] arrayOfDecision_function = new decision_function[n * (n - 1) / 2];
      
      double[] arrayOfDouble2 = null;double[] arrayOfDouble3 = null;
      if (probability == 1)
      {
        arrayOfDouble2 = new double[n * (n - 1) / 2];
        arrayOfDouble3 = new double[n * (n - 1) / 2];
      }
      
      int i3 = 0;
      int i5; int i6; int i7; int i8; int i9; for (i1 = 0; i1 < n; i1++) {
        for (i4 = i1 + 1; i4 < n; i4++)
        {
          localObject = new svm_problem();
          i5 = local[I2[i1];i6 = local[I2[i4];
          i7 = local[I3[i1];i8 = local[I3[i4];
          l = (i7 + i8);
          x = new svm_node[l][];
          y = new double[l];
          
          for (i9 = 0; i9 < i7; i9++)
          {
            x[i9] = arrayOfSvm_node;[(i5 + i9)];
            y[i9] = 1.0D;
          }
          for (i9 = 0; i9 < i8; i9++)
          {
            x[(i7 + i9)] = arrayOfSvm_node;[(i6 + i9)];
            y[(i7 + i9)] = -1.0D;
          }
          
          if (probability == 1)
          {
            double[] arrayOfDouble4 = new double[2];
            svm_binary_svc_probability((svm_problem)localObject, paramSvm_parameter, arrayOfDouble1[i1], arrayOfDouble1[i4], arrayOfDouble4);
            arrayOfDouble2[i3] = arrayOfDouble4[0];
            arrayOfDouble3[i3] = arrayOfDouble4[1];
          }
          
          arrayOfDecision_function[i3] = svm_train_one((svm_problem)localObject, paramSvm_parameter, arrayOfDouble1[i1], arrayOfDouble1[i4]);
          for (i9 = 0; i9 < i7; i9++)
            if ((arrayOfBoolean[(i5 + i9)] == 0) && (Math.abs(alpha[i9]) > 0.0D))
              arrayOfBoolean[(i5 + i9)] = true;
          for (i9 = 0; i9 < i8; i9++)
            if ((arrayOfBoolean[(i6 + i9)] == 0) && (Math.abs(alpha[(i7 + i9)]) > 0.0D))
              arrayOfBoolean[(i6 + i9)] = true;
          i3++;
        }
      }
      

      nr_class = n;
      
      label = new int[n];
      for (i1 = 0; i1 < n; i1++) {
        label[i1] = local[I1[i1];
      }
      rho = new double[n * (n - 1) / 2];
      for (i1 = 0; i1 < n * (n - 1) / 2; i1++) {
        rho[i1] = rho;
      }
      if (probability == 1)
      {
        probA = new double[n * (n - 1) / 2];
        probB = new double[n * (n - 1) / 2];
        for (i1 = 0; i1 < n * (n - 1) / 2; i1++)
        {
          probA[i1] = arrayOfDouble2[i1];
          probB[i1] = arrayOfDouble3[i1];
        }
      }
      

      probA = null;
      probB = null;
      

      int i4 = 0;
      Object localObject = new int[n];
      nSV = new int[n];
      for (i1 = 0; i1 < n; i1++)
      {
        i5 = 0;
        for (i6 = 0; i6 < local[I3[i1]; i6++)
          if (arrayOfBoolean[(local[I2[i1] + i6)] != 0)
          {
            i5++;
            i4++;
          }
        nSV[i1] = i5;
        localObject[i1] = i5;
      }
      
      info("Total nSV = " + i4 + "\n");
      
      l = i4;
      SV = new svm_node[i4][];
      sv_indices = new int[i4];
      i3 = 0;
      for (i1 = 0; i1 < i; i1++) {
        if (arrayOfBoolean[i1] != 0)
        {
          SV[i3] = arrayOfSvm_node;[i1];
          sv_indices[(i3++)] = (arrayOfInt5[i1] + 1);
        }
      }
      int[] arrayOfInt6 = new int[n];
      arrayOfInt6[0] = 0;
      for (i1 = 1; i1 < n; i1++) {
        arrayOfInt6[i1] = (arrayOfInt6[(i1 - 1)] + localObject[(i1 - 1)]);
      }
      sv_coef = new double[n - 1][];
      for (i1 = 0; i1 < n - 1; i1++) {
        sv_coef[i1] = new double[i4];
      }
      i3 = 0;
      for (i1 = 0; i1 < n; i1++)
        for (i6 = i1 + 1; i6 < n; i6++)
        {




          i7 = local[I2[i1];
          i8 = local[I2[i6];
          i9 = local[I3[i1];
          int i10 = local[I3[i6];
          
          int i11 = arrayOfInt6[i1];
          
          for (int i12 = 0; i12 < i9; i12++)
            if (arrayOfBoolean[(i7 + i12)] != 0)
              sv_coef[(i6 - 1)][(i11++)] = alpha[i12];
          i11 = arrayOfInt6[i6];
          for (i12 = 0; i12 < i10; i12++)
            if (arrayOfBoolean[(i8 + i12)] != 0)
              sv_coef[i1][(i11++)] = alpha[(i9 + i12)];
          i3++;
        }
    }
    return localSvm_model;
  }
  


  public static void svm_cross_validation(svm_problem paramSvm_problem, svm_parameter paramSvm_parameter, int paramInt, double[] paramArrayOfDouble)
  {
    int[] arrayOfInt1 = new int[paramInt + 1];
    int j = l;
    int[] arrayOfInt2 = new int[j];
    Object localObject1;
    Object localObject2;
    int k;
    int m; if (((svm_type == 0) || (svm_type == 1)) && (paramInt < j))
    {

      int[] arrayOfInt3 = new int[1];
      int[][] arrayOfInt4 = new int[1][];
      int[][] arrayOfInt5 = new int[1][];
      int[][] arrayOfInt6 = new int[1][];
      
      svm_group_classes(paramSvm_problem, arrayOfInt3, arrayOfInt4, arrayOfInt5, arrayOfInt6, arrayOfInt2);
      
      int i2 = arrayOfInt3[0];
      localObject1 = arrayOfInt5[0];
      localObject2 = arrayOfInt6[0];
      

      int[] arrayOfInt7 = new int[paramInt];
      
      int[] arrayOfInt8 = new int[j];
      for (i = 0; i < j; i++)
        arrayOfInt8[i] = arrayOfInt2[i];
      int i4; int i5; for (int i3 = 0; i3 < i2; i3++)
        for (i = 0; i < localObject2[i3]; i++)
        {
          i4 = i + rand.nextInt(localObject2[i3] - i);
          i5 = arrayOfInt8[(localObject1[i3] + i4)];arrayOfInt8[(localObject1[i3] + i4)] = arrayOfInt8[(localObject1[i3] + i)];arrayOfInt8[(localObject1[i3] + i)] = i5;
        }
      for (i = 0; i < paramInt; i++)
      {
        arrayOfInt7[i] = 0;
        for (i3 = 0; i3 < i2; i3++)
          arrayOfInt7[i] += (i + 1) * localObject2[i3] / paramInt - i * localObject2[i3] / paramInt;
      }
      arrayOfInt1[0] = 0;
      for (i = 1; i <= paramInt; i++)
        arrayOfInt1[i] = (arrayOfInt1[(i - 1)] + arrayOfInt7[(i - 1)]);
      for (i3 = 0; i3 < i2; i3++)
        for (i = 0; i < paramInt; i++)
        {
          i4 = localObject1[i3] + i * localObject2[i3] / paramInt;
          i5 = localObject1[i3] + (i + 1) * localObject2[i3] / paramInt;
          for (int i6 = i4; i6 < i5; i6++)
          {
            arrayOfInt2[arrayOfInt1[i]] = arrayOfInt8[i6];
            arrayOfInt1[i] += 1;
          }
        }
      arrayOfInt1[0] = 0;
      for (i = 1; i <= paramInt; i++) {
        arrayOfInt1[i] = (arrayOfInt1[(i - 1)] + arrayOfInt7[(i - 1)]);
      }
    }
    else {
      for (i = 0; i < j; i++) arrayOfInt2[i] = i;
      for (i = 0; i < j; i++)
      {
        k = i + rand.nextInt(j - i);
        m = arrayOfInt2[i];arrayOfInt2[i] = arrayOfInt2[k];arrayOfInt2[k] = m;
      }
      for (i = 0; i <= paramInt; i++) {
        arrayOfInt1[i] = (i * j / paramInt);
      }
    }
    for (int i = 0; i < paramInt; i++)
    {
      k = arrayOfInt1[i];
      m = arrayOfInt1[(i + 1)];
      
      svm_problem localSvm_problem = new svm_problem();
      
      l = (j - (m - k));
      x = new svm_node[l][];
      y = new double[l];
      
      int i1 = 0;
      for (int n = 0; n < k; n++)
      {
        x[i1] = x[arrayOfInt2[n]];
        y[i1] = y[arrayOfInt2[n]];
        i1++;
      }
      for (n = m; n < j; n++)
      {
        x[i1] = x[arrayOfInt2[n]];
        y[i1] = y[arrayOfInt2[n]];
        i1++;
      }
      localObject1 = svm_train(localSvm_problem, paramSvm_parameter);
      if ((probability == 1) && ((svm_type == 0) || (svm_type == 1)))
      {


        localObject2 = new double[svm_get_nr_class((svm_model)localObject1)];
        for (n = k; n < m; n++) {
          paramArrayOfDouble[arrayOfInt2[n]] = svm_predict_probability((svm_model)localObject1, x[arrayOfInt2[n]], (double[])localObject2);
        }
      } else {
        for (n = k; n < m; n++)
          paramArrayOfDouble[arrayOfInt2[n]] = svm_predict((svm_model)localObject1, x[arrayOfInt2[n]]);
      }
    }
  }
  
  public static int svm_get_svm_type(svm_model paramSvm_model) {
    return param.svm_type;
  }
  
  public static int svm_get_nr_class(svm_model paramSvm_model)
  {
    return nr_class;
  }
  
  public static void svm_get_labels(svm_model paramSvm_model, int[] paramArrayOfInt)
  {
    if (label != null) {
      for (int i = 0; i < nr_class; i++)
        paramArrayOfInt[i] = label[i];
    }
  }
  
  public static void svm_get_sv_indices(svm_model paramSvm_model, int[] paramArrayOfInt) {
    if (sv_indices != null) {
      for (int i = 0; i < l; i++)
        paramArrayOfInt[i] = sv_indices[i];
    }
  }
  
  public static int svm_get_nr_sv(svm_model paramSvm_model) {
    return l;
  }
  
  public static double svm_get_svr_probability(svm_model paramSvm_model)
  {
    if (((param.svm_type == 3) || (param.svm_type == 4)) && (probA != null))
    {
      return probA[0];
    }
    
    System.err.print("Model doesn't contain information for SVR probability inference\n");
    return 0.0D;
  }
  


  public static double svm_predict_values(svm_model paramSvm_model, svm_node[] paramArrayOfSvm_node, double[] paramArrayOfDouble)
  {
    if ((param.svm_type == 2) || (param.svm_type == 3) || (param.svm_type == 4))
    {


      double[] arrayOfDouble1 = sv_coef[0];
      double d1 = 0.0D;
      for (i = 0; i < l; i++)
        d1 += arrayOfDouble1[i] * Kernel.k_function(paramArrayOfSvm_node, SV[i], param);
      d1 -= rho[0];
      paramArrayOfDouble[0] = d1;
      
      if (param.svm_type == 2) {
        return d1 > 0.0D ? 1.0D : -1.0D;
      }
      return d1;
    }
    

    int j = nr_class;
    int k = l;
    
    double[] arrayOfDouble2 = new double[k];
    for (int i = 0; i < k; i++) {
      arrayOfDouble2[i] = Kernel.k_function(paramArrayOfSvm_node, SV[i], param);
    }
    int[] arrayOfInt1 = new int[j];
    arrayOfInt1[0] = 0;
    for (i = 1; i < j; i++) {
      arrayOfInt1[i] = (arrayOfInt1[(i - 1)] + nSV[(i - 1)]);
    }
    int[] arrayOfInt2 = new int[j];
    for (i = 0; i < j; i++) {
      arrayOfInt2[i] = 0;
    }
    int m = 0;
    for (i = 0; i < j; i++) {
      for (n = i + 1; n < j; n++)
      {
        double d2 = 0.0D;
        int i1 = arrayOfInt1[i];
        int i2 = arrayOfInt1[n];
        int i3 = nSV[i];
        int i4 = nSV[n];
        

        double[] arrayOfDouble3 = sv_coef[(n - 1)];
        double[] arrayOfDouble4 = sv_coef[i];
        for (int i5 = 0; i5 < i3; i5++)
          d2 += arrayOfDouble3[(i1 + i5)] * arrayOfDouble2[(i1 + i5)];
        for (i5 = 0; i5 < i4; i5++)
          d2 += arrayOfDouble4[(i2 + i5)] * arrayOfDouble2[(i2 + i5)];
        d2 -= rho[m];
        paramArrayOfDouble[m] = d2;
        
        if (paramArrayOfDouble[m] > 0.0D) {
          arrayOfInt2[i] += 1;
        } else
          arrayOfInt2[n] += 1;
        m++;
      }
    }
    int n = 0;
    for (i = 1; i < j; i++) {
      if (arrayOfInt2[i] > arrayOfInt2[n])
        n = i;
    }
    return label[n];
  }
  

  public static double svm_predict(svm_model paramSvm_model, svm_node[] paramArrayOfSvm_node)
  {
    int i = nr_class;
    double[] arrayOfDouble;
    if ((param.svm_type == 2) || (param.svm_type == 3) || (param.svm_type == 4))
    {

      arrayOfDouble = new double[1];
    } else
      arrayOfDouble = new double[i * (i - 1) / 2];
    double d = svm_predict_values(paramSvm_model, paramArrayOfSvm_node, arrayOfDouble);
    return d;
  }
  
  public static double svm_predict_probability(svm_model paramSvm_model, svm_node[] paramArrayOfSvm_node, double[] paramArrayOfDouble)
  {
    if (((param.svm_type == 0) || (param.svm_type == 1)) && (probA != null) && (probB != null))
    {


      int j = nr_class;
      double[] arrayOfDouble = new double[j * (j - 1) / 2];
      svm_predict_values(paramSvm_model, paramArrayOfSvm_node, arrayOfDouble);
      
      double d = 1.0E-7D;
      double[][] arrayOfDouble1 = new double[j][j];
      
      int k = 0;
      for (int i = 0; i < j; i++)
        for (m = i + 1; m < j; m++)
        {
          arrayOfDouble1[i][m] = Math.min(Math.max(sigmoid_predict(arrayOfDouble[k], probA[k], probB[k]), d), 1.0D - d);
          arrayOfDouble1[m][i] = (1.0D - arrayOfDouble1[i][m]);
          k++;
        }
      multiclass_probability(j, arrayOfDouble1, paramArrayOfDouble);
      
      int m = 0;
      for (i = 1; i < j; i++)
        if (paramArrayOfDouble[i] > paramArrayOfDouble[m])
          m = i;
      return label[m];
    }
    
    return svm_predict(paramSvm_model, paramArrayOfSvm_node);
  }
  
  static final String[] svm_type_table = { "c_svc", "nu_svc", "one_class", "epsilon_svr", "nu_svr" };
  



  static final String[] kernel_type_table = { "linear", "polynomial", "rbf", "sigmoid", "precomputed" };
  


  public static void svm_save_model(String paramString, svm_model paramSvm_model)
    throws IOException
  {
    DataOutputStream localDataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(paramString)));
    
    svm_parameter localSvm_parameter = param;
    
    localDataOutputStream.writeBytes("svm_type " + svm_type_table[svm_type] + "\n");
    localDataOutputStream.writeBytes("kernel_type " + kernel_type_table[kernel_type] + "\n");
    
    if (kernel_type == 1) {
      localDataOutputStream.writeBytes("degree " + degree + "\n");
    }
    if ((kernel_type == 1) || (kernel_type == 2) || (kernel_type == 3))
    {

      localDataOutputStream.writeBytes("gamma " + gamma + "\n");
    }
    if ((kernel_type == 1) || (kernel_type == 3))
    {
      localDataOutputStream.writeBytes("coef0 " + coef0 + "\n");
    }
    int i = nr_class;
    int j = l;
    localDataOutputStream.writeBytes("nr_class " + i + "\n");
    localDataOutputStream.writeBytes("total_sv " + j + "\n");
    

    localDataOutputStream.writeBytes("rho");
    for (int k = 0; k < i * (i - 1) / 2; k++)
      localDataOutputStream.writeBytes(" " + rho[k]);
    localDataOutputStream.writeBytes("\n");
    

    if (label != null)
    {
      localDataOutputStream.writeBytes("label");
      for (k = 0; k < i; k++)
        localDataOutputStream.writeBytes(" " + label[k]);
      localDataOutputStream.writeBytes("\n");
    }
    
    if (probA != null)
    {
      localDataOutputStream.writeBytes("probA");
      for (k = 0; k < i * (i - 1) / 2; k++)
        localDataOutputStream.writeBytes(" " + probA[k]);
      localDataOutputStream.writeBytes("\n");
    }
    if (probB != null)
    {
      localDataOutputStream.writeBytes("probB");
      for (k = 0; k < i * (i - 1) / 2; k++)
        localDataOutputStream.writeBytes(" " + probB[k]);
      localDataOutputStream.writeBytes("\n");
    }
    
    if (nSV != null)
    {
      localDataOutputStream.writeBytes("nr_sv");
      for (k = 0; k < i; k++)
        localDataOutputStream.writeBytes(" " + nSV[k]);
      localDataOutputStream.writeBytes("\n");
    }
    
    localDataOutputStream.writeBytes("SV\n");
    double[][] arrayOfDouble = sv_coef;
    svm_node[][] arrayOfSvm_node = SV;
    
    for (int m = 0; m < j; m++)
    {
      for (int n = 0; n < i - 1; n++) {
        localDataOutputStream.writeBytes(arrayOfDouble[n][m] + " ");
      }
      svm_node[] arrayOfSvm_node1 = arrayOfSvm_node[m];
      if (kernel_type == 4) {
        localDataOutputStream.writeBytes("0:" + (int)0value);
      } else
        for (int i1 = 0; i1 < arrayOfSvm_node1.length; i1++)
          localDataOutputStream.writeBytes(index + ":" + value + " ");
      localDataOutputStream.writeBytes("\n");
    }
    
    localDataOutputStream.close();
  }
  
  private static double atof(String paramString)
  {
    return Double.valueOf(paramString).doubleValue();
  }
  
  private static int atoi(String paramString)
  {
    return Integer.parseInt(paramString);
  }
  
  public static svm_model svm_load_model(String paramString) throws IOException
  {
    return svm_load_model(new BufferedReader(new FileReader(paramString)));
  }
  

  public static svm_model svm_load_model(BufferedReader paramBufferedReader)
    throws IOException
  {
    svm_model localSvm_model = new svm_model();
    svm_parameter localSvm_parameter = new svm_parameter();
    param = localSvm_parameter;
    rho = null;
    probA = null;
    probB = null;
    label = null;
    nSV = null;
    Object localObject;
    for (;;)
    {
      String str1 = paramBufferedReader.readLine();
      String str2 = str1.substring(str1.indexOf(' ') + 1);
      
      if (str1.startsWith("svm_type"))
      {

        for (k = 0; k < svm_type_table.length; k++)
        {
          if (str2.indexOf(svm_type_table[k]) != -1)
          {
            svm_type = k;
            break;
          }
        }
        if (k == svm_type_table.length)
        {
          System.err.print("unknown svm type.\n");
          return null;
        }
      }
      else if (str1.startsWith("kernel_type"))
      {

        for (k = 0; k < kernel_type_table.length; k++)
        {
          if (str2.indexOf(kernel_type_table[k]) != -1)
          {
            kernel_type = k;
            break;
          }
        }
        if (k == kernel_type_table.length)
        {
          System.err.print("unknown kernel function.\n");
          return null;
        }
      }
      else if (str1.startsWith("degree")) {
        degree = atoi(str2);
      } else if (str1.startsWith("gamma")) {
        gamma = atof(str2);
      } else if (str1.startsWith("coef0")) {
        coef0 = atof(str2);
      } else if (str1.startsWith("nr_class")) {
        nr_class = atoi(str2);
      } else if (str1.startsWith("total_sv")) {
        l = atoi(str2); } else { int m;
        if (str1.startsWith("rho"))
        {
          k = nr_class * (nr_class - 1) / 2;
          rho = new double[k];
          localObject = new StringTokenizer(str2);
          for (m = 0; m < k; m++) {
            rho[m] = atof(((StringTokenizer)localObject).nextToken());
          }
        } else if (str1.startsWith("label"))
        {
          k = nr_class;
          label = new int[k];
          localObject = new StringTokenizer(str2);
          for (m = 0; m < k; m++) {
            label[m] = atoi(((StringTokenizer)localObject).nextToken());
          }
        } else if (str1.startsWith("probA"))
        {
          k = nr_class * (nr_class - 1) / 2;
          probA = new double[k];
          localObject = new StringTokenizer(str2);
          for (m = 0; m < k; m++) {
            probA[m] = atof(((StringTokenizer)localObject).nextToken());
          }
        } else if (str1.startsWith("probB"))
        {
          k = nr_class * (nr_class - 1) / 2;
          probB = new double[k];
          localObject = new StringTokenizer(str2);
          for (m = 0; m < k; m++) {
            probB[m] = atof(((StringTokenizer)localObject).nextToken());
          }
        } else if (str1.startsWith("nr_sv"))
        {
          k = nr_class;
          nSV = new int[k];
          localObject = new StringTokenizer(str2);
          for (m = 0; m < k; m++)
            nSV[m] = atoi(((StringTokenizer)localObject).nextToken());
        } else {
          if (str1.startsWith("SV")) {
            break;
          }
          


          System.err.print("unknown text in model file: [" + str1 + "]\n");
          return null;
        }
      }
    }
    

    int i = nr_class - 1;
    int j = l;
    sv_coef = new double[i][j];
    SV = new svm_node[j][];
    
    for (int k = 0; k < j; k++)
    {
      localObject = paramBufferedReader.readLine();
      StringTokenizer localStringTokenizer = new StringTokenizer((String)localObject, " \t\n\r\f:");
      
      for (int n = 0; n < i; n++)
        sv_coef[n][k] = atof(localStringTokenizer.nextToken());
      n = localStringTokenizer.countTokens() / 2;
      SV[k] = new svm_node[n];
      for (int i1 = 0; i1 < n; i1++)
      {
        SV[k][i1] = new svm_node();
        SV[k][i1].index = atoi(localStringTokenizer.nextToken());
        SV[k][i1].value = atof(localStringTokenizer.nextToken());
      }
    }
    
    paramBufferedReader.close();
    return localSvm_model;
  }
  


  public static String svm_check_parameter(svm_problem paramSvm_problem, svm_parameter paramSvm_parameter)
  {
    int i = svm_type;
    if ((i != 0) && (i != 1) && (i != 2) && (i != 3) && (i != 4))
    {



      return "unknown svm type";
    }
    

    int j = kernel_type;
    if ((j != 0) && (j != 1) && (j != 2) && (j != 3) && (j != 4))
    {



      return "unknown kernel type";
    }
    if (gamma < 0.0D) {
      return "gamma < 0";
    }
    if (degree < 0) {
      return "degree of polynomial kernel < 0";
    }
    

    if (cache_size <= 0.0D) {
      return "cache_size <= 0";
    }
    if (eps <= 0.0D) {
      return "eps <= 0";
    }
    if ((i == 0) || (i == 3) || (i == 4))
    {

      if (C <= 0.0D)
        return "C <= 0";
    }
    if ((i == 1) || (i == 2) || (i == 4))
    {

      if ((nu <= 0.0D) || (nu > 1.0D))
        return "nu <= 0 or nu > 1";
    }
    if ((i == 3) && 
      (p < 0.0D)) {
      return "p < 0";
    }
    if ((shrinking != 0) && (shrinking != 1))
    {
      return "shrinking != 0 and shrinking != 1";
    }
    if ((probability != 0) && (probability != 1))
    {
      return "probability != 0 and probability != 1";
    }
    if ((probability == 1) && (i == 2))
    {
      return "one-class SVM probability output not supported yet";
    }
    

    if (i == 1)
    {
      int k = l;
      int m = 16;
      int n = 0;
      Object localObject1 = new int[m];
      Object localObject2 = new int[m];
      int i2;
      int i3;
      for (int i1 = 0; i1 < k; i1++)
      {
        i2 = (int)y[i1];
        
        for (i3 = 0; i3 < n; i3++) {
          if (i2 == localObject1[i3])
          {
            localObject2[i3] += 1;
            break;
          }
        }
        if (i3 == n)
        {
          if (n == m)
          {
            m *= 2;
            int[] arrayOfInt = new int[m];
            System.arraycopy(localObject1, 0, arrayOfInt, 0, localObject1.length);
            localObject1 = arrayOfInt;
            
            arrayOfInt = new int[m];
            System.arraycopy(localObject2, 0, arrayOfInt, 0, localObject2.length);
            localObject2 = arrayOfInt;
          }
          localObject1[n] = i2;
          localObject2[n] = 1;
          n++;
        }
      }
      
      for (i1 = 0; i1 < n; i1++)
      {
        i2 = localObject2[i1];
        for (i3 = i1 + 1; i3 < n; i3++)
        {
          int i4 = localObject2[i3];
          if (nu * (i2 + i4) / 2.0D > Math.min(i2, i4)) {
            return "specified nu is infeasible";
          }
        }
      }
    }
    return null;
  }
  
  public static int svm_check_probability_model(svm_model paramSvm_model)
  {
    if (((param.svm_type != 0) && (param.svm_type != 1)) || (((probA != null) && (probB != null)) || (((param.svm_type == 3) || (param.svm_type == 4)) && (probA != null))))
    {


      return 1;
    }
    return 0;
  }
  
  public static void svm_set_print_string_function(svm_print_interface paramSvm_print_interface)
  {
    if (paramSvm_print_interface == null) {
      svm_print_string = svm_print_stdout;
    } else {
      svm_print_string = paramSvm_print_interface;
    }
  }
  
  static class decision_function
  {
    double[] alpha;
    double rho;
    
    decision_function() {}
  }
}
