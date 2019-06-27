package org.jfree.data.xy;

import java.io.Serializable;
































































public class XYInterval
  implements Serializable
{
  private double xLow;
  private double xHigh;
  private double y;
  private double yLow;
  private double yHigh;
  
  public XYInterval(double xLow, double xHigh, double y, double yLow, double yHigh)
  {
    this.xLow = xLow;
    this.xHigh = xHigh;
    this.y = y;
    this.yLow = yLow;
    this.yHigh = yHigh;
  }
  




  public double getXLow()
  {
    return xLow;
  }
  




  public double getXHigh()
  {
    return xHigh;
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
    if (!(obj instanceof XYInterval)) {
      return false;
    }
    XYInterval that = (XYInterval)obj;
    if (xLow != xLow) {
      return false;
    }
    if (xHigh != xHigh) {
      return false;
    }
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
