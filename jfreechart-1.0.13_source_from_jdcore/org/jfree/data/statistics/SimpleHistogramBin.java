package org.jfree.data.statistics;

import java.io.Serializable;
import org.jfree.util.PublicCloneable;



































































public class SimpleHistogramBin
  implements Comparable, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 3480862537505941742L;
  private double lowerBound;
  private double upperBound;
  private boolean includeLowerBound;
  private boolean includeUpperBound;
  private int itemCount;
  
  public SimpleHistogramBin(double lowerBound, double upperBound)
  {
    this(lowerBound, upperBound, true, true);
  }
  









  public SimpleHistogramBin(double lowerBound, double upperBound, boolean includeLowerBound, boolean includeUpperBound)
  {
    if (lowerBound >= upperBound) {
      throw new IllegalArgumentException("Invalid bounds");
    }
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.includeLowerBound = includeLowerBound;
    this.includeUpperBound = includeUpperBound;
    itemCount = 0;
  }
  




  public double getLowerBound()
  {
    return lowerBound;
  }
  




  public double getUpperBound()
  {
    return upperBound;
  }
  




  public int getItemCount()
  {
    return itemCount;
  }
  




  public void setItemCount(int count)
  {
    itemCount = count;
  }
  







  public boolean accepts(double value)
  {
    if (Double.isNaN(value)) {
      return false;
    }
    if (value < lowerBound) {
      return false;
    }
    if (value > upperBound) {
      return false;
    }
    if (value == lowerBound) {
      return includeLowerBound;
    }
    if (value == upperBound) {
      return includeUpperBound;
    }
    return true;
  }
  







  public boolean overlapsWith(SimpleHistogramBin bin)
  {
    if (upperBound < lowerBound) {
      return false;
    }
    if (lowerBound > upperBound) {
      return false;
    }
    if (upperBound == lowerBound) {
      return (includeUpperBound) && (includeLowerBound);
    }
    if (lowerBound == upperBound) {
      return (includeLowerBound) && (includeUpperBound);
    }
    return true;
  }
  








  public int compareTo(Object obj)
  {
    if (!(obj instanceof SimpleHistogramBin)) {
      return 0;
    }
    SimpleHistogramBin bin = (SimpleHistogramBin)obj;
    if (lowerBound < lowerBound) {
      return -1;
    }
    if (lowerBound > lowerBound) {
      return 1;
    }
    
    if (upperBound < upperBound) {
      return -1;
    }
    if (upperBound > upperBound) {
      return 1;
    }
    return 0;
  }
  






  public boolean equals(Object obj)
  {
    if (!(obj instanceof SimpleHistogramBin)) {
      return false;
    }
    SimpleHistogramBin that = (SimpleHistogramBin)obj;
    if (lowerBound != lowerBound) {
      return false;
    }
    if (upperBound != upperBound) {
      return false;
    }
    if (includeLowerBound != includeLowerBound) {
      return false;
    }
    if (includeUpperBound != includeUpperBound) {
      return false;
    }
    if (itemCount != itemCount) {
      return false;
    }
    return true;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
