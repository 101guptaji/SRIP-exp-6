package org.jfree.data.xy;

import java.io.Serializable;





























































public class YWithXInterval
  implements Serializable
{
  private double y;
  private double xLow;
  private double xHigh;
  
  public YWithXInterval(double y, double xLow, double xHigh)
  {
    this.y = y;
    this.xLow = xLow;
    this.xHigh = xHigh;
  }
  




  public double getY()
  {
    return y;
  }
  




  public double getXLow()
  {
    return xLow;
  }
  




  public double getXHigh()
  {
    return xHigh;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof YWithXInterval)) {
      return false;
    }
    YWithXInterval that = (YWithXInterval)obj;
    if (y != y) {
      return false;
    }
    if (xLow != xLow) {
      return false;
    }
    if (xHigh != xHigh) {
      return false;
    }
    return true;
  }
}
