package libsvm;
















abstract class Kernel
  extends QMatrix
{
  private svm_node[][] x;
  














  private final double[] x_square;
  














  private final int kernel_type;
  














  private final int degree;
  














  private final double gamma;
  














  private final double coef0;
  














  abstract float[] get_Q(int paramInt1, int paramInt2);
  














  abstract double[] get_QD();
  














  void swap_index(int paramInt1, int paramInt2)
  {
    svm_node[] arrayOfSvm_node = x[paramInt1];x[paramInt1] = x[paramInt2];x[paramInt2] = arrayOfSvm_node;
    if (x_square != null) { double d = x_square[paramInt1];x_square[paramInt1] = x_square[paramInt2];x_square[paramInt2] = d;
    }
  }
  
  private static double powi(double paramDouble, int paramInt) {
    double d1 = paramDouble;double d2 = 1.0D;
    
    for (int i = paramInt; i > 0; i /= 2)
    {
      if (i % 2 == 1) d2 *= d1;
      d1 *= d1;
    }
    return d2;
  }
  
  double kernel_function(int paramInt1, int paramInt2)
  {
    switch (kernel_type)
    {
    case 0: 
      return dot(x[paramInt1], x[paramInt2]);
    case 1: 
      return powi(gamma * dot(x[paramInt1], x[paramInt2]) + coef0, degree);
    case 2: 
      return Math.exp(-gamma * (x_square[paramInt1] + x_square[paramInt2] - 2.0D * dot(x[paramInt1], x[paramInt2])));
    case 3: 
      return Math.tanh(gamma * dot(x[paramInt1], x[paramInt2]) + coef0);
    case 4: 
      return x[paramInt1][((int)x[paramInt2][0].value)].value;
    }
    return 0.0D;
  }
  

  Kernel(int paramInt, svm_node[][] paramArrayOfSvm_node, svm_parameter paramSvm_parameter)
  {
    kernel_type = kernel_type;
    degree = degree;
    gamma = gamma;
    coef0 = coef0;
    
    x = ((svm_node[][])paramArrayOfSvm_node.clone());
    
    if (kernel_type == 2)
    {
      x_square = new double[paramInt];
      for (int i = 0; i < paramInt; i++)
        x_square[i] = dot(x[i], x[i]);
    } else {
      x_square = null;
    }
  }
  
  static double dot(svm_node[] paramArrayOfSvm_node1, svm_node[] paramArrayOfSvm_node2) {
    double d = 0.0D;
    int i = paramArrayOfSvm_node1.length;
    int j = paramArrayOfSvm_node2.length;
    int k = 0;
    int m = 0;
    while ((k < i) && (m < j))
    {
      if (index == index) {
        d += value * value;

      }
      else if (index > index) {
        m++;
      } else {
        k++;
      }
    }
    return d;
  }
  

  static double k_function(svm_node[] paramArrayOfSvm_node1, svm_node[] paramArrayOfSvm_node2, svm_parameter paramSvm_parameter)
  {
    switch (kernel_type)
    {
    case 0: 
      return dot(paramArrayOfSvm_node1, paramArrayOfSvm_node2);
    case 1: 
      return powi(gamma * dot(paramArrayOfSvm_node1, paramArrayOfSvm_node2) + coef0, degree);
    
    case 2: 
      double d1 = 0.0D;
      int i = paramArrayOfSvm_node1.length;
      int j = paramArrayOfSvm_node2.length;
      int k = 0;
      int m = 0;
      while ((k < i) && (m < j))
      {
        if (index == index)
        {
          double d2 = value - value;
          d1 += d2 * d2;
        }
        else if (index > index)
        {
          d1 += value * value;
          m++;
        }
        else
        {
          d1 += value * value;
          k++;
        }
      }
      
      while (k < i)
      {
        d1 += value * value;
        k++;
      }
      
      while (m < j)
      {
        d1 += value * value;
        m++;
      }
      
      return Math.exp(-gamma * d1);
    
    case 3: 
      return Math.tanh(gamma * dot(paramArrayOfSvm_node1, paramArrayOfSvm_node2) + coef0);
    case 4: 
      return 0value)].value;
    }
    return 0.0D;
  }
}
