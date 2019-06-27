import java.io.PrintStream;
import libsvm.svm_parameter;
import libsvm.svm_problem;

class svm_train
{
  private svm_parameter param;
  private svm_problem prob;
  private libsvm.svm_model model;
  private String input_file_name;
  private String model_file_name;
  private String error_msg;
  private int cross_validation;
  private int nr_fold;
  private static libsvm.svm_print_interface svm_print_null = new libsvm.svm_print_interface() {
    public void print(String paramAnonymousString) {}
  };
  
  svm_train() {}
  
  private static void exit_with_help() {
    System.out.print("Usage: svm_train [options] training_set_file [model_file]\noptions:\n-s svm_type : set type of SVM (default 0)\n\t0 -- C-SVC\t\t(multi-class classification)\n\t1 -- nu-SVC\t\t(multi-class classification)\n\t2 -- one-class SVM\n\t3 -- epsilon-SVR\t(regression)\n\t4 -- nu-SVR\t\t(regression)\n-t kernel_type : set type of kernel function (default 2)\n\t0 -- linear: u'*v\n\t1 -- polynomial: (gamma*u'*v + coef0)^degree\n\t2 -- radial basis function: exp(-gamma*|u-v|^2)\n\t3 -- sigmoid: tanh(gamma*u'*v + coef0)\n\t4 -- precomputed kernel (kernel values in training_set_file)\n-d degree : set degree in kernel function (default 3)\n-g gamma : set gamma in kernel function (default 1/num_features)\n-r coef0 : set coef0 in kernel function (default 0)\n-c cost : set the parameter C of C-SVC, epsilon-SVR, and nu-SVR (default 1)\n-n nu : set the parameter nu of nu-SVC, one-class SVM, and nu-SVR (default 0.5)\n-p epsilon : set the epsilon in loss function of epsilon-SVR (default 0.1)\n-m cachesize : set cache memory size in MB (default 100)\n-e epsilon : set tolerance of termination criterion (default 0.001)\n-h shrinking : whether to use the shrinking heuristics, 0 or 1 (default 1)\n-b probability_estimates : whether to train a SVC or SVR model for probability estimates, 0 or 1 (default 0)\n-wi weight : set the parameter C of class i to weight*C, for C-SVC (default 1)\n-v n : n-fold cross validation mode\n-q : quiet mode (no outputs)\n");
    



























    System.exit(1);
  }
  

  private void do_cross_validation()
  {
    int j = 0;
    double d1 = 0.0D;
    double d2 = 0.0D;double d3 = 0.0D;double d4 = 0.0D;double d5 = 0.0D;double d6 = 0.0D;
    double[] arrayOfDouble = new double[prob.l];
    
    libsvm.svm.svm_cross_validation(prob, param, nr_fold, arrayOfDouble);
    int i; if ((param.svm_type == 3) || (param.svm_type == 4))
    {

      for (i = 0; i < prob.l; i++)
      {
        double d7 = prob.y[i];
        double d8 = arrayOfDouble[i];
        d1 += (d8 - d7) * (d8 - d7);
        d2 += d8;
        d3 += d7;
        d4 += d8 * d8;
        d5 += d7 * d7;
        d6 += d8 * d7;
      }
      System.out.print("Cross Validation Mean squared error = " + d1 / prob.l + "\n");
      System.out.print("Cross Validation Squared correlation coefficient = " + (prob.l * d6 - d2 * d3) * (prob.l * d6 - d2 * d3) / ((prob.l * d4 - d2 * d2) * (prob.l * d5 - d3 * d3)) + "\n");


    }
    else
    {

      for (i = 0; i < prob.l; i++)
        if (arrayOfDouble[i] == prob.y[i])
          j++;
      System.out.print("Cross Validation Accuracy = " + 100.0D * j / prob.l + "%\n");
    }
  }
  
  private void run(String[] paramArrayOfString) throws java.io.IOException
  {
    parse_command_line(paramArrayOfString);
    read_problem();
    error_msg = libsvm.svm.svm_check_parameter(prob, param);
    
    if (error_msg != null)
    {
      System.err.print("ERROR: " + error_msg + "\n");
      System.exit(1);
    }
    
    if (cross_validation != 0)
    {
      do_cross_validation();
    }
    else
    {
      model = libsvm.svm.svm_train(prob, param);
      libsvm.svm.svm_save_model(model_file_name, model);
    }
  }
  
  public static void main(String[] paramArrayOfString) throws java.io.IOException
  {
    svm_train localSvm_train = new svm_train();
    localSvm_train.run(paramArrayOfString);
  }
  
  private static double atof(String paramString)
  {
    double d = Double.valueOf(paramString).doubleValue();
    if ((Double.isNaN(d)) || (Double.isInfinite(d)))
    {
      System.err.print("NaN or Infinity in input\n");
      System.exit(1);
    }
    return d;
  }
  
  private static int atoi(String paramString)
  {
    return Integer.parseInt(paramString);
  }
  

  private void parse_command_line(String[] paramArrayOfString)
  {
    libsvm.svm_print_interface localSvm_print_interface = null;
    
    param = new svm_parameter();
    
    param.svm_type = 0;
    param.kernel_type = 2;
    param.degree = 3;
    param.gamma = 0.0D;
    param.coef0 = 0.0D;
    param.nu = 0.5D;
    param.cache_size = 100.0D;
    param.C = 1.0D;
    param.eps = 0.001D;
    param.p = 0.1D;
    param.shrinking = 1;
    param.probability = 0;
    param.nr_weight = 0;
    param.weight_label = new int[0];
    param.weight = new double[0];
    cross_validation = 0;
    

    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      if (paramArrayOfString[i].charAt(0) != '-') break;
      i++; if (i >= paramArrayOfString.length)
        exit_with_help();
      switch (paramArrayOfString[(i - 1)].charAt(1))
      {
      case 's': 
        param.svm_type = atoi(paramArrayOfString[i]);
        break;
      case 't': 
        param.kernel_type = atoi(paramArrayOfString[i]);
        break;
      case 'd': 
        param.degree = atoi(paramArrayOfString[i]);
        break;
      case 'g': 
        param.gamma = atof(paramArrayOfString[i]);
        break;
      case 'r': 
        param.coef0 = atof(paramArrayOfString[i]);
        break;
      case 'n': 
        param.nu = atof(paramArrayOfString[i]);
        break;
      case 'm': 
        param.cache_size = atof(paramArrayOfString[i]);
        break;
      case 'c': 
        param.C = atof(paramArrayOfString[i]);
        break;
      case 'e': 
        param.eps = atof(paramArrayOfString[i]);
        break;
      case 'p': 
        param.p = atof(paramArrayOfString[i]);
        break;
      case 'h': 
        param.shrinking = atoi(paramArrayOfString[i]);
        break;
      case 'b': 
        param.probability = atoi(paramArrayOfString[i]);
        break;
      case 'q': 
        localSvm_print_interface = svm_print_null;
        i--;
        break;
      case 'v': 
        cross_validation = 1;
        nr_fold = atoi(paramArrayOfString[i]);
        if (nr_fold < 2)
        {
          System.err.print("n-fold cross validation: n must >= 2\n");
          exit_with_help();
        }
        break;
      case 'w': 
        param.nr_weight += 1;
        
        Object localObject = param.weight_label;
        param.weight_label = new int[param.nr_weight];
        System.arraycopy(localObject, 0, param.weight_label, 0, param.nr_weight - 1);
        


        localObject = param.weight;
        param.weight = new double[param.nr_weight];
        System.arraycopy(localObject, 0, param.weight, 0, param.nr_weight - 1);
        

        param.weight_label[(param.nr_weight - 1)] = atoi(paramArrayOfString[(i - 1)].substring(2));
        param.weight[(param.nr_weight - 1)] = atof(paramArrayOfString[i]);
        break;
      case 'f': case 'i': case 'j': case 'k': case 'l': case 'o': case 'u': default: 
        System.err.print("Unknown option: " + paramArrayOfString[(i - 1)] + "\n");
        exit_with_help();
      }
      
    }
    libsvm.svm.svm_set_print_string_function(localSvm_print_interface);
    


    if (i >= paramArrayOfString.length) {
      exit_with_help();
    }
    input_file_name = paramArrayOfString[i];
    
    if (i < paramArrayOfString.length - 1) {
      model_file_name = paramArrayOfString[(i + 1)];
    }
    else {
      int j = paramArrayOfString[i].lastIndexOf('/');
      j++;
      model_file_name = (paramArrayOfString[i].substring(j) + ".model");
    }
  }
  

  private void read_problem()
    throws java.io.IOException
  {
    java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.FileReader(input_file_name));
    java.util.Vector localVector1 = new java.util.Vector();
    java.util.Vector localVector2 = new java.util.Vector();
    int i = 0;
    
    for (;;)
    {
      String str = localBufferedReader.readLine();
      if (str == null)
        break;
      java.util.StringTokenizer localStringTokenizer = new java.util.StringTokenizer(str, " \t\n\r\f:");
      
      localVector1.addElement(Double.valueOf(atof(localStringTokenizer.nextToken())));
      int k = localStringTokenizer.countTokens() / 2;
      libsvm.svm_node[] arrayOfSvm_node = new libsvm.svm_node[k];
      for (int m = 0; m < k; m++)
      {
        arrayOfSvm_node[m] = new libsvm.svm_node();
        index = atoi(localStringTokenizer.nextToken());
        value = atof(localStringTokenizer.nextToken());
      }
      if (k > 0) i = Math.max(i, 1index);
      localVector2.addElement(arrayOfSvm_node);
    }
    
    prob = new svm_problem();
    prob.l = localVector1.size();
    prob.x = new libsvm.svm_node[prob.l][];
    for (int j = 0; j < prob.l; j++)
      prob.x[j] = ((libsvm.svm_node[])localVector2.elementAt(j));
    prob.y = new double[prob.l];
    for (j = 0; j < prob.l; j++) {
      prob.y[j] = ((Double)localVector1.elementAt(j)).doubleValue();
    }
    if ((param.gamma == 0.0D) && (i > 0)) {
      param.gamma = (1.0D / i);
    }
    if (param.kernel_type == 4) {
      for (j = 0; j < prob.l; j++)
      {
        if (prob.x[j][0].index != 0)
        {
          System.err.print("Wrong kernel matrix: first column must be 0:sample_serial_number\n");
          System.exit(1);
        }
        if (((int)prob.x[j][0].value <= 0) || ((int)prob.x[j][0].value > i))
        {
          System.err.print("Wrong input format: sample_serial_number out of range\n");
          System.exit(1);
        }
      }
    }
    localBufferedReader.close();
  }
}
