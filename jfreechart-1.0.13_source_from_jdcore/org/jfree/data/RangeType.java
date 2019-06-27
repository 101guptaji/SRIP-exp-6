package org.jfree.data;

import java.io.ObjectStreamException;
import java.io.Serializable;















































public final class RangeType
  implements Serializable
{
  private static final long serialVersionUID = -9073319010650549239L;
  public static final RangeType FULL = new RangeType("RangeType.FULL");
  

  public static final RangeType POSITIVE = new RangeType("RangeType.POSITIVE");
  


  public static final RangeType NEGATIVE = new RangeType("RangeType.NEGATIVE");
  



  private String name;
  



  private RangeType(String name)
  {
    this.name = name;
  }
  




  public String toString()
  {
    return name;
  }
  








  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof RangeType)) {
      return false;
    }
    RangeType that = (RangeType)obj;
    if (!name.equals(that.toString())) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    return name.hashCode();
  }
  





  private Object readResolve()
    throws ObjectStreamException
  {
    if (equals(FULL)) {
      return FULL;
    }
    if (equals(POSITIVE)) {
      return POSITIVE;
    }
    if (equals(NEGATIVE)) {
      return NEGATIVE;
    }
    return null;
  }
}
