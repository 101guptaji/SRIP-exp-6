import java.io.DataOutputStream;
import libsvm.svm;

class svm_predict
{
  private static libsvm.svm_print_interface svm_print_null = new libsvm.svm_print_interface()
  {
    public void print(String paramAnonymousString) {}
  };
  
  private static libsvm.svm_print_interface svm_print_stdout = new libsvm.svm_print_interface()
  {
    public void print(String paramAnonymousString)
    {
      System.out.print(paramAnonymousString);
    }
  };
  
  private static libsvm.svm_print_interface svm_print_string = svm_print_stdout;
  
  svm_predict() {}
  
  static void info(String paramString) { svm_print_string.print(paramString); }
  

  private static double atof(String paramString)
  {
    return Double.valueOf(paramString).doubleValue();
  }
  
  private static int atoi(String paramString)
  {
    return Integer.parseInt(paramString);
  }
  
  private static void predict(java.io.BufferedReader paramBufferedReader, DataOutputStream paramDataOutputStream, libsvm.svm_model paramSvm_model, int paramInt) throws java.io.IOException
  {
    int i = 0;
    int j = 0;
    double d1 = 0.0D;
    double d2 = 0.0D;double d3 = 0.0D;double d4 = 0.0D;double d5 = 0.0D;double d6 = 0.0D;
    
    int k = svm.svm_get_svm_type(paramSvm_model);
    int m = svm.svm_get_nr_class(paramSvm_model);
    double[] arrayOfDouble = null;
    Object localObject;
    if (paramInt == 1)
    {
      if ((k == 3) || (k == 4))
      {

        info("Prob. model for test data: target value = predicted value + z,\nz: Laplace distribution e^(-|z|/sigma)/(2sigma),sigma=" + svm.svm_get_svr_probability(paramSvm_model) + "\n");
      }
      else
      {
        localObject = new int[m];
        svm.svm_get_labels(paramSvm_model, (int[])localObject);
        arrayOfDouble = new double[m];
        paramDataOutputStream.writeBytes("labels");
        for (int n = 0; n < m; n++)
          paramDataOutputStream.writeBytes(" " + localObject[n]);
        paramDataOutputStream.writeBytes("\n");
      }
    }
    for (;;)
    {
      localObject = paramBufferedReader.readLine();
      if (localObject == null)
        break;
      java.util.StringTokenizer localStringTokenizer = new java.util.StringTokenizer((String)localObject, " \t\n\r\f:");
      
      double d7 = atof(localStringTokenizer.nextToken());
      int i1 = localStringTokenizer.countTokens() / 2;
      libsvm.svm_node[] arrayOfSvm_node = new libsvm.svm_node[i1];
      for (int i2 = 0; i2 < i1; i2++)
      {
        arrayOfSvm_node[i2] = new libsvm.svm_node();
        index = atoi(localStringTokenizer.nextToken());
        value = atof(localStringTokenizer.nextToken());
      }
      
      double d8;
      if ((paramInt == 1) && ((k == 0) || (k == 1)))
      {
        d8 = svm.svm_predict_probability(paramSvm_model, arrayOfSvm_node, arrayOfDouble);
        paramDataOutputStream.writeBytes(d8 + " ");
        for (int i3 = 0; i3 < m; i3++)
          paramDataOutputStream.writeBytes(arrayOfDouble[i3] + " ");
        paramDataOutputStream.writeBytes("\n");
      }
      else
      {
        d8 = svm.svm_predict(paramSvm_model, arrayOfSvm_node);
        paramDataOutputStream.writeBytes(d8 + "\n");
      }
      
      if (d8 == d7)
        i++;
      d1 += (d8 - d7) * (d8 - d7);
      d2 += d8;
      d3 += d7;
      d4 += d8 * d8;
      d5 += d7 * d7;
      d6 += d8 * d7;
      j++;
    }
    if ((k == 3) || (k == 4))
    {

      info("Mean squared error = " + d1 / j + " (regression)\n");
      info("Squared correlation coefficient = " + (j * d6 - d2 * d3) * (j * d6 - d2 * d3) / ((j * d4 - d2 * d2) * (j * d5 - d3 * d3)) + " (regression)\n");

    }
    else
    {

      info("Accuracy = " + i / j * 100.0D + "% (" + i + "/" + j + ") (classification)\n");
    }
  }
  
  private static void exit_with_help()
  {
    System.err.print("usage: svm_predict [options] test_file model_file output_file\noptions:\n-b probability_estimates: whether to predict probability estimates, 0 or 1 (default 0); one-class SVM not supported yet\n-q : quiet mode (no outputs)\n");
    


    System.exit(1);
  }
  
  public static void main(String[] paramArrayOfString) throws java.io.IOException
  {
    int j = 0;
    svm_print_string = svm_print_stdout;
    

    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      if (paramArrayOfString[i].charAt(0) != '-') break;
      i++;
      switch (paramArrayOfString[(i - 1)].charAt(1))
      {
      case 'b': 
        j = atoi(paramArrayOfString[i]);
        break;
      case 'q': 
        svm_print_string = svm_print_null;
        i--;
        break;
      default: 
        System.err.print("Unknown option: " + paramArrayOfString[(i - 1)] + "\n");
        exit_with_help();
      }
    }
    if (i >= paramArrayOfString.length - 2) {
      exit_with_help();
    }
    try {
      java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.FileReader(paramArrayOfString[i]));
      DataOutputStream localDataOutputStream = new DataOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(paramArrayOfString[(i + 2)])));
      libsvm.svm_model localSvm_model = svm.svm_load_model(paramArrayOfString[(i + 1)]);
      if (j == 1)
      {
        if (svm.svm_check_probability_model(localSvm_model) == 0)
        {
          System.err.print("Model does not support probabiliy estimates\n");
          System.exit(1);
        }
        

      }
      else if (svm.svm_check_probability_model(localSvm_model) != 0)
      {
        info("Model supports probability estimates, but disabled in prediction.\n");
      }
      
      predict(localBufferedReader, localDataOutputStream, localSvm_model, j);
      localBufferedReader.close();
      localDataOutputStream.close();
    }
    catch (java.io.FileNotFoundException localFileNotFoundException)
    {
      exit_with_help();
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      exit_with_help();
    }
  }
}
