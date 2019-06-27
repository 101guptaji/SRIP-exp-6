package org.jfree.chart.plot;

import java.io.ObjectStreamException;
import java.io.Serializable;



















































public final class SeriesRenderingOrder
  implements Serializable
{
  private static final long serialVersionUID = 209336477448807735L;
  public static final SeriesRenderingOrder FORWARD = new SeriesRenderingOrder("SeriesRenderingOrder.FORWARD");
  





  public static final SeriesRenderingOrder REVERSE = new SeriesRenderingOrder("SeriesRenderingOrder.REVERSE");
  



  private String name;
  



  private SeriesRenderingOrder(String name)
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
    if (!(obj instanceof SeriesRenderingOrder)) {
      return false;
    }
    SeriesRenderingOrder order = (SeriesRenderingOrder)obj;
    if (!name.equals(order.toString())) {
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
    if (equals(FORWARD)) {
      return FORWARD;
    }
    if (equals(REVERSE)) {
      return REVERSE;
    }
    return null;
  }
}
