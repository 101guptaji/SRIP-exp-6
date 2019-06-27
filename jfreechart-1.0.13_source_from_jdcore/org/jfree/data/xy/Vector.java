package org.jfree.data.xy;

import java.io.Serializable;
























































public class Vector
  implements Serializable
{
  private double x;
  private double y;
  
  public Vector(double x, double y)
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
  




  public double getLength()
  {
    return Math.sqrt(x * x + y * y);
  }
  




  public double getAngle()
  {
    return Math.atan2(y, x);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Vector)) {
      return false;
    }
    Vector that = (Vector)obj;
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
}
