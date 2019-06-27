package org.jfree.data.statistics;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import org.jfree.util.ObjectUtilities;



























































































public class BoxAndWhiskerItem
  implements Serializable
{
  private static final long serialVersionUID = 7329649623148167423L;
  private Number mean;
  private Number median;
  private Number q1;
  private Number q3;
  private Number minRegularValue;
  private Number maxRegularValue;
  private Number minOutlier;
  private Number maxOutlier;
  private List outliers;
  
  public BoxAndWhiskerItem(Number mean, Number median, Number q1, Number q3, Number minRegularValue, Number maxRegularValue, Number minOutlier, Number maxOutlier, List outliers)
  {
    this.mean = mean;
    this.median = median;
    this.q1 = q1;
    this.q3 = q3;
    this.minRegularValue = minRegularValue;
    this.maxRegularValue = maxRegularValue;
    this.minOutlier = minOutlier;
    this.maxOutlier = maxOutlier;
    this.outliers = outliers;
  }
  



















  public BoxAndWhiskerItem(double mean, double median, double q1, double q3, double minRegularValue, double maxRegularValue, double minOutlier, double maxOutlier, List outliers)
  {
    this(new Double(mean), new Double(median), new Double(q1), new Double(q3), new Double(minRegularValue), new Double(maxRegularValue), new Double(minOutlier), new Double(maxOutlier), outliers);
  }
  








  public Number getMean()
  {
    return mean;
  }
  




  public Number getMedian()
  {
    return median;
  }
  




  public Number getQ1()
  {
    return q1;
  }
  




  public Number getQ3()
  {
    return q3;
  }
  




  public Number getMinRegularValue()
  {
    return minRegularValue;
  }
  




  public Number getMaxRegularValue()
  {
    return maxRegularValue;
  }
  




  public Number getMinOutlier()
  {
    return minOutlier;
  }
  




  public Number getMaxOutlier()
  {
    return maxOutlier;
  }
  




  public List getOutliers()
  {
    if (outliers == null) {
      return null;
    }
    return Collections.unmodifiableList(outliers);
  }
  





  public String toString()
  {
    return super.toString() + "[mean=" + mean + ",median=" + median + ",q1=" + q1 + ",q3=" + q3 + "]";
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BoxAndWhiskerItem)) {
      return false;
    }
    BoxAndWhiskerItem that = (BoxAndWhiskerItem)obj;
    if (!ObjectUtilities.equal(mean, mean)) {
      return false;
    }
    if (!ObjectUtilities.equal(median, median)) {
      return false;
    }
    if (!ObjectUtilities.equal(q1, q1)) {
      return false;
    }
    if (!ObjectUtilities.equal(q3, q3)) {
      return false;
    }
    if (!ObjectUtilities.equal(minRegularValue, minRegularValue))
    {
      return false;
    }
    if (!ObjectUtilities.equal(maxRegularValue, maxRegularValue))
    {
      return false;
    }
    if (!ObjectUtilities.equal(minOutlier, minOutlier)) {
      return false;
    }
    if (!ObjectUtilities.equal(maxOutlier, maxOutlier)) {
      return false;
    }
    if (!ObjectUtilities.equal(outliers, outliers)) {
      return false;
    }
    return true;
  }
}
