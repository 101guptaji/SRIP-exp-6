package org.jfree.chart.block;

import java.io.ObjectStreamException;
import java.io.Serializable;














































public final class LengthConstraintType
  implements Serializable
{
  private static final long serialVersionUID = -1156658804028142978L;
  public static final LengthConstraintType NONE = new LengthConstraintType("LengthConstraintType.NONE");
  


  public static final LengthConstraintType RANGE = new LengthConstraintType("RectangleConstraintType.RANGE");
  


  public static final LengthConstraintType FIXED = new LengthConstraintType("LengthConstraintType.FIXED");
  



  private String name;
  



  private LengthConstraintType(String name)
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
    if (!(obj instanceof LengthConstraintType)) {
      return false;
    }
    LengthConstraintType that = (LengthConstraintType)obj;
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
    if (equals(NONE)) {
      return NONE;
    }
    if (equals(RANGE)) {
      return RANGE;
    }
    if (equals(FIXED)) {
      return FIXED;
    }
    return null;
  }
}
