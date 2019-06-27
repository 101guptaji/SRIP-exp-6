package org.jfree.data.xy;

import java.io.Serializable;














































































































































































































































































































































class WindDataItem
  implements Comparable, Serializable
{
  private Number x;
  private Number windDir;
  private Number windForce;
  
  public WindDataItem(Number x, Number windDir, Number windForce)
  {
    this.x = x;
    this.windDir = windDir;
    this.windForce = windForce;
  }
  




  public Number getX()
  {
    return x;
  }
  




  public Number getWindDirection()
  {
    return windDir;
  }
  




  public Number getWindForce()
  {
    return windForce;
  }
  






  public int compareTo(Object object)
  {
    if ((object instanceof WindDataItem)) {
      WindDataItem item = (WindDataItem)object;
      if (x.doubleValue() > x.doubleValue()) {
        return 1;
      }
      if (x.equals(x)) {
        return 0;
      }
      
      return -1;
    }
    

    throw new ClassCastException("WindDataItem.compareTo(error)");
  }
  








  public boolean equals(Object obj)
  {
    if (this == obj) {
      return false;
    }
    if (!(obj instanceof WindDataItem)) {
      return false;
    }
    WindDataItem that = (WindDataItem)obj;
    if (!x.equals(x)) {
      return false;
    }
    if (!windDir.equals(windDir)) {
      return false;
    }
    if (!windForce.equals(windForce)) {
      return false;
    }
    return true;
  }
}
