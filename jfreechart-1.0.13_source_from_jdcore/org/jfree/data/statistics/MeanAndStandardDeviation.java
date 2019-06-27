package org.jfree.data.statistics;

import java.io.Serializable;
import org.jfree.util.ObjectUtilities;



























































public class MeanAndStandardDeviation
  implements Serializable
{
  private static final long serialVersionUID = 7413468697315721515L;
  private Number mean;
  private Number standardDeviation;
  
  public MeanAndStandardDeviation(double mean, double standardDeviation)
  {
    this(new Double(mean), new Double(standardDeviation));
  }
  






  public MeanAndStandardDeviation(Number mean, Number standardDeviation)
  {
    this.mean = mean;
    this.standardDeviation = standardDeviation;
  }
  




  public Number getMean()
  {
    return mean;
  }
  









  public double getMeanValue()
  {
    double result = NaN.0D;
    if (mean != null) {
      result = mean.doubleValue();
    }
    return result;
  }
  




  public Number getStandardDeviation()
  {
    return standardDeviation;
  }
  








  public double getStandardDeviationValue()
  {
    double result = NaN.0D;
    if (standardDeviation != null) {
      result = standardDeviation.doubleValue();
    }
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MeanAndStandardDeviation)) {
      return false;
    }
    MeanAndStandardDeviation that = (MeanAndStandardDeviation)obj;
    if (!ObjectUtilities.equal(mean, mean)) {
      return false;
    }
    if (!ObjectUtilities.equal(standardDeviation, standardDeviation))
    {

      return false;
    }
    return true;
  }
  






  public String toString()
  {
    return "[" + mean + ", " + standardDeviation + "]";
  }
}
