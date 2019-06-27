package org.jfree.chart.axis;

import java.io.Serializable;







































































public abstract class TickUnit
  implements Comparable, Serializable
{
  private static final long serialVersionUID = 510179855057013974L;
  private double size;
  private int minorTickCount;
  
  public TickUnit(double size)
  {
    this.size = size;
  }
  







  public TickUnit(double size, int minorTickCount)
  {
    this.size = size;
    this.minorTickCount = minorTickCount;
  }
  




  public double getSize()
  {
    return size;
  }
  






  public int getMinorTickCount()
  {
    return minorTickCount;
  }
  








  public String valueToString(double value)
  {
    return String.valueOf(value);
  }
  









  public int compareTo(Object object)
  {
    if ((object instanceof TickUnit)) {
      TickUnit other = (TickUnit)object;
      if (size > other.getSize()) {
        return 1;
      }
      if (size < other.getSize()) {
        return -1;
      }
      
      return 0;
    }
    

    return -1;
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof TickUnit)) {
      return false;
    }
    TickUnit that = (TickUnit)obj;
    if (size != size) {
      return false;
    }
    if (minorTickCount != minorTickCount) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    long temp = size != 0.0D ? Double.doubleToLongBits(size) : 0L;
    
    return (int)(temp ^ temp >>> 32);
  }
}
