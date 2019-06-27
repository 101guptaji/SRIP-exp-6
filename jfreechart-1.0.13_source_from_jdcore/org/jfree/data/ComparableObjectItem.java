package org.jfree.data;

import java.io.Serializable;
import org.jfree.util.ObjectUtilities;


























































public class ComparableObjectItem
  implements Cloneable, Comparable, Serializable
{
  private static final long serialVersionUID = 2751513470325494890L;
  private Comparable x;
  private Object obj;
  
  public ComparableObjectItem(Comparable x, Object y)
  {
    if (x == null) {
      throw new IllegalArgumentException("Null 'x' argument.");
    }
    this.x = x;
    obj = y;
  }
  




  protected Comparable getComparable()
  {
    return x;
  }
  




  protected Object getObject()
  {
    return obj;
  }
  





  protected void setObject(Object y)
  {
    obj = y;
  }
  
















  public int compareTo(Object o1)
  {
    if ((o1 instanceof ComparableObjectItem)) {
      ComparableObjectItem that = (ComparableObjectItem)o1;
      return x.compareTo(x);
    }
    




    int result = 1;
    

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
    if (!(obj instanceof ComparableObjectItem)) {
      return false;
    }
    ComparableObjectItem that = (ComparableObjectItem)obj;
    if (!x.equals(x)) {
      return false;
    }
    if (!ObjectUtilities.equal(this.obj, obj)) {
      return false;
    }
    return true;
  }
  





  public int hashCode()
  {
    int result = x.hashCode();
    result = 29 * result + (obj != null ? obj.hashCode() : 0);
    return result;
  }
}
