package org.jfree.data.xy;

import java.io.Serializable;
import org.jfree.util.ObjectUtilities;



























































public class XYDataItem
  implements Cloneable, Comparable, Serializable
{
  private static final long serialVersionUID = 2751513470325494890L;
  private Number x;
  private Number y;
  
  public XYDataItem(Number x, Number y)
  {
    if (x == null) {
      throw new IllegalArgumentException("Null 'x' argument.");
    }
    this.x = x;
    this.y = y;
  }
  





  public XYDataItem(double x, double y)
  {
    this(new Double(x), new Double(y));
  }
  




  public Number getX()
  {
    return x;
  }
  










  public double getXValue()
  {
    return x.doubleValue();
  }
  




  public Number getY()
  {
    return y;
  }
  









  public double getYValue()
  {
    double result = NaN.0D;
    if (y != null) {
      result = y.doubleValue();
    }
    return result;
  }
  





  public void setY(double y)
  {
    setY(new Double(y));
  }
  





  public void setY(Number y)
  {
    this.y = y;
  }
  





  public int compareTo(Object o1)
  {
    int result;
    




    int result;
    



    if ((o1 instanceof XYDataItem)) {
      XYDataItem dataItem = (XYDataItem)o1;
      double compare = x.doubleValue() - dataItem.getX().doubleValue();
      int result;
      if (compare > 0.0D) {
        result = 1;
      } else {
        int result;
        if (compare < 0.0D) {
          result = -1;
        }
        else {
          result = 0;
        }
        
      }
      

    }
    else
    {
      result = 1;
    }
    
    return result;
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYDataItem)) {
      return false;
    }
    XYDataItem that = (XYDataItem)obj;
    if (!x.equals(x)) {
      return false;
    }
    if (!ObjectUtilities.equal(y, y)) {
      return false;
    }
    return true;
  }
  





  public int hashCode()
  {
    int result = x.hashCode();
    result = 29 * result + (y != null ? y.hashCode() : 0);
    return result;
  }
  





  public String toString()
  {
    return "[" + getXValue() + ", " + getYValue() + "]";
  }
}
