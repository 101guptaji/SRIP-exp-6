package org.jfree.chart.axis;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
































































public class TickUnits
  implements TickUnitSource, Cloneable, Serializable
{
  private static final long serialVersionUID = 1134174035901467545L;
  private List tickUnits;
  
  public TickUnits()
  {
    tickUnits = new ArrayList();
  }
  





  public void add(TickUnit unit)
  {
    if (unit == null) {
      throw new NullPointerException("Null 'unit' argument.");
    }
    tickUnits.add(unit);
    Collections.sort(tickUnits);
  }
  






  public int size()
  {
    return tickUnits.size();
  }
  








  public TickUnit get(int pos)
  {
    return (TickUnit)tickUnits.get(pos);
  }
  







  public TickUnit getLargerTickUnit(TickUnit unit)
  {
    int index = Collections.binarySearch(tickUnits, unit);
    if (index >= 0) {
      index += 1;
    }
    else {
      index = -index;
    }
    
    return (TickUnit)tickUnits.get(Math.min(index, tickUnits.size() - 1));
  }
  










  public TickUnit getCeilingTickUnit(TickUnit unit)
  {
    int index = Collections.binarySearch(tickUnits, unit);
    if (index >= 0) {
      return (TickUnit)tickUnits.get(index);
    }
    
    index = -(index + 1);
    return (TickUnit)tickUnits.get(Math.min(index, tickUnits.size() - 1));
  }
  










  public TickUnit getCeilingTickUnit(double size)
  {
    return getCeilingTickUnit(new NumberTickUnit(size, NumberFormat.getInstance()));
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    TickUnits clone = (TickUnits)super.clone();
    tickUnits = new ArrayList(tickUnits);
    return clone;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof TickUnits)) {
      return false;
    }
    TickUnits that = (TickUnits)obj;
    return tickUnits.equals(tickUnits);
  }
}
