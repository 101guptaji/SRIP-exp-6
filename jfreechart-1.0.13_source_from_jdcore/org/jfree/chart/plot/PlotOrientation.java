package org.jfree.chart.plot;

import java.io.ObjectStreamException;
import java.io.Serializable;
















































public final class PlotOrientation
  implements Serializable
{
  private static final long serialVersionUID = -2508771828190337782L;
  public static final PlotOrientation HORIZONTAL = new PlotOrientation("PlotOrientation.HORIZONTAL");
  


  public static final PlotOrientation VERTICAL = new PlotOrientation("PlotOrientation.VERTICAL");
  



  private String name;
  



  private PlotOrientation(String name)
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
    if (!(obj instanceof PlotOrientation)) {
      return false;
    }
    PlotOrientation orientation = (PlotOrientation)obj;
    if (!name.equals(orientation.toString())) {
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
    Object result = null;
    if (equals(HORIZONTAL)) {
      result = HORIZONTAL;
    }
    else if (equals(VERTICAL)) {
      result = VERTICAL;
    }
    return result;
  }
}
