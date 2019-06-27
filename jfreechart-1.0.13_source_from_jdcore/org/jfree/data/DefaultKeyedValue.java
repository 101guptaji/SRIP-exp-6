package org.jfree.data;

import java.io.Serializable;
import org.jfree.util.PublicCloneable;

































































public class DefaultKeyedValue
  implements KeyedValue, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -7388924517460437712L;
  private Comparable key;
  private Number value;
  
  public DefaultKeyedValue(Comparable key, Number value)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    this.key = key;
    this.value = value;
  }
  




  public Comparable getKey()
  {
    return key;
  }
  




  public Number getValue()
  {
    return value;
  }
  




  public synchronized void setValue(Number value)
  {
    this.value = value;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DefaultKeyedValue)) {
      return false;
    }
    DefaultKeyedValue that = (DefaultKeyedValue)obj;
    
    if (!key.equals(key)) {
      return false;
    }
    if (value != null ? !value.equals(value) : value != null)
    {
      return false;
    }
    return true;
  }
  





  public int hashCode()
  {
    int result = key != null ? key.hashCode() : 0;
    result = 29 * result + (value != null ? value.hashCode() : 0);
    return result;
  }
  








  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultKeyedValue clone = (DefaultKeyedValue)super.clone();
    return clone;
  }
  





  public String toString()
  {
    return "(" + key.toString() + ", " + value.toString() + ")";
  }
}
