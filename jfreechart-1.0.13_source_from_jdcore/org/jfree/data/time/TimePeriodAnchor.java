package org.jfree.data.time;

import java.io.ObjectStreamException;
import java.io.Serializable;
















































public final class TimePeriodAnchor
  implements Serializable
{
  private static final long serialVersionUID = 2011955697457548862L;
  public static final TimePeriodAnchor START = new TimePeriodAnchor("TimePeriodAnchor.START");
  


  public static final TimePeriodAnchor MIDDLE = new TimePeriodAnchor("TimePeriodAnchor.MIDDLE");
  


  public static final TimePeriodAnchor END = new TimePeriodAnchor("TimePeriodAnchor.END");
  



  private String name;
  



  private TimePeriodAnchor(String name)
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
    if (!(obj instanceof TimePeriodAnchor)) {
      return false;
    }
    
    TimePeriodAnchor position = (TimePeriodAnchor)obj;
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
    if (equals(START)) {
      return START;
    }
    if (equals(MIDDLE)) {
      return MIDDLE;
    }
    if (equals(END)) {
      return END;
    }
    return null;
  }
}
