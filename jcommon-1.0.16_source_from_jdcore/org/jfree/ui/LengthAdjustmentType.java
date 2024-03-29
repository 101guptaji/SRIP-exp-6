package org.jfree.ui;

import java.io.ObjectStreamException;
import java.io.Serializable;



















































public final class LengthAdjustmentType
  implements Serializable
{
  private static final long serialVersionUID = -6097408511380545010L;
  public static final LengthAdjustmentType NO_CHANGE = new LengthAdjustmentType("NO_CHANGE");
  


  public static final LengthAdjustmentType EXPAND = new LengthAdjustmentType("EXPAND");
  


  public static final LengthAdjustmentType CONTRACT = new LengthAdjustmentType("CONTRACT");
  



  private String name;
  



  private LengthAdjustmentType(String name)
  {
    this.name = name;
  }
  




  public String toString()
  {
    return name;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof LengthAdjustmentType)) {
      return false;
    }
    LengthAdjustmentType that = (LengthAdjustmentType)obj;
    if (!name.equals(name)) {
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
    if (equals(NO_CHANGE)) {
      return NO_CHANGE;
    }
    if (equals(EXPAND)) {
      return EXPAND;
    }
    if (equals(CONTRACT)) {
      return CONTRACT;
    }
    return null;
  }
}
