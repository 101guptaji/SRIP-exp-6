package org.jfree.data.statistics;

import java.io.Serializable;



























































public class HistogramBin
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 7614685080015589931L;
  private int count;
  private double startBoundary;
  private double endBoundary;
  
  public HistogramBin(double startBoundary, double endBoundary)
  {
    if (startBoundary > endBoundary) {
      throw new IllegalArgumentException("HistogramBin():  startBoundary > endBoundary.");
    }
    
    count = 0;
    this.startBoundary = startBoundary;
    this.endBoundary = endBoundary;
  }
  




  public int getCount()
  {
    return count;
  }
  


  public void incrementCount()
  {
    count += 1;
  }
  




  public double getStartBoundary()
  {
    return startBoundary;
  }
  




  public double getEndBoundary()
  {
    return endBoundary;
  }
  




  public double getBinWidth()
  {
    return endBoundary - startBoundary;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if ((obj instanceof HistogramBin)) {
      HistogramBin bin = (HistogramBin)obj;
      boolean b0 = startBoundary == startBoundary;
      boolean b1 = endBoundary == endBoundary;
      boolean b2 = count == count;
      return (b0) && (b1) && (b2);
    }
    return false;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
