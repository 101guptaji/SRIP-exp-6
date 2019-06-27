package org.jfree.data.xy;

import java.io.Serializable;



















































public class XYCoordinate
  implements Comparable, Serializable
{
  private double x;
  private double y;
  
  public XYCoordinate()
  {
    this(0.0D, 0.0D);
  }
  





  public XYCoordinate(double x, double y)
  {
    this.x = x;
    this.y = y;
  }
  




  public double getX()
  {
    return x;
  }
  




  public double getY()
  {
    return y;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYCoordinate)) {
      return false;
    }
    XYCoordinate that = (XYCoordinate)obj;
    if (x != x) {
      return false;
    }
    if (y != y) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 193;
    long temp = Double.doubleToLongBits(x);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(y);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    return result;
  }
  





  public String toString()
  {
    return "(" + x + ", " + y + ")";
  }
  






  public int compareTo(Object obj)
  {
    if (!(obj instanceof XYCoordinate)) {
      throw new IllegalArgumentException("Incomparable object.");
    }
    XYCoordinate that = (XYCoordinate)obj;
    if (x > x) {
      return 1;
    }
    if (x < x) {
      return -1;
    }
    
    if (y > y) {
      return 1;
    }
    if (y < y) {
      return -1;
    }
    
    return 0;
  }
}
