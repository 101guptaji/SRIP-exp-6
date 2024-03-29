package org.jfree.data.xy;

import java.io.Serializable;

























































public class YInterval
  implements Serializable
{
  private double y;
  private double yLow;
  private double yHigh;
  
  public YInterval(double y, double yLow, double yHigh)
  {
    this.y = y;
    this.yLow = yLow;
    this.yHigh = yHigh;
  }
  




  public double getY()
  {
    return y;
  }
  




  public double getYLow()
  {
    return yLow;
  }
  




  public double getYHigh()
  {
    return yHigh;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof YInterval)) {
      return false;
    }
    YInterval that = (YInterval)obj;
    if (y != y) {
      return false;
    }
    if (yLow != yLow) {
      return false;
    }
    if (yHigh != yHigh) {
      return false;
    }
    return true;
  }
}
