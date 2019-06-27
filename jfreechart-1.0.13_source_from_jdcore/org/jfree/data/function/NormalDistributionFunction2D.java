package org.jfree.data.function;













public class NormalDistributionFunction2D
  implements Function2D
{
  private double mean;
  











  private double std;
  











  private double factor;
  










  private double denominator;
  











  public NormalDistributionFunction2D(double mean, double std)
  {
    if (std <= 0.0D) {
      throw new IllegalArgumentException("Requires 'std' > 0.");
    }
    this.mean = mean;
    this.std = std;
    
    factor = (1.0D / (std * Math.sqrt(6.283185307179586D)));
    denominator = (2.0D * std * std);
  }
  




  public double getMean()
  {
    return mean;
  }
  




  public double getStandardDeviation()
  {
    return std;
  }
  






  public double getValue(double x)
  {
    double z = x - mean;
    return factor * Math.exp(-z * z / denominator);
  }
}
