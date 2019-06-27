package org.jfree.chart.axis;

import java.io.ObjectStreamException;
import java.io.Serializable;














































public final class TickType
  implements Serializable
{
  public static final TickType MAJOR = new TickType("MAJOR");
  

  public static final TickType MINOR = new TickType("MINOR");
  


  private String name;
  



  private TickType(String name)
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
    if (!(obj instanceof TickType)) {
      return false;
    }
    
    TickType that = (TickType)obj;
    if (!name.equals(name)) {
      return false;
    }
    return true;
  }
  





  private Object readResolve()
    throws ObjectStreamException
  {
    Object result = null;
    if (equals(MAJOR)) {
      result = MAJOR;
    }
    else if (equals(MINOR)) {
      result = MINOR;
    }
    return result;
  }
}
