package org.jfree.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jfree.util.ObjectUtilities;





















































public class LegendItemCollection
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 1365215565589815953L;
  private List items;
  
  public LegendItemCollection()
  {
    items = new ArrayList();
  }
  




  public void add(LegendItem item)
  {
    items.add(item);
  }
  




  public void addAll(LegendItemCollection collection)
  {
    items.addAll(items);
  }
  






  public LegendItem get(int index)
  {
    return (LegendItem)items.get(index);
  }
  




  public int getItemCount()
  {
    return items.size();
  }
  




  public Iterator iterator()
  {
    return items.iterator();
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof LegendItemCollection)) {
      return false;
    }
    LegendItemCollection that = (LegendItemCollection)obj;
    if (!items.equals(items)) {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    LegendItemCollection clone = (LegendItemCollection)super.clone();
    items = ((List)ObjectUtilities.deepClone(items));
    return clone;
  }
}
