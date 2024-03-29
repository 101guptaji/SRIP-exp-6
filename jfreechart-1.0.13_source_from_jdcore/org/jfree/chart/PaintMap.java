package org.jfree.chart;

import java.awt.Paint;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PaintUtilities;


























































public class PaintMap
  implements Cloneable, Serializable
{
  static final long serialVersionUID = -4639833772123069274L;
  private transient Map store;
  
  public PaintMap()
  {
    store = new HashMap();
  }
  










  public Paint getPaint(Comparable key)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    return (Paint)store.get(key);
  }
  








  public boolean containsKey(Comparable key)
  {
    return store.containsKey(key);
  }
  









  public void put(Comparable key, Paint paint)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    store.put(key, paint);
  }
  


  public void clear()
  {
    store.clear();
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof PaintMap)) {
      return false;
    }
    PaintMap that = (PaintMap)obj;
    if (store.size() != store.size()) {
      return false;
    }
    Set keys = store.keySet();
    Iterator iterator = keys.iterator();
    while (iterator.hasNext()) {
      Comparable key = (Comparable)iterator.next();
      Paint p1 = getPaint(key);
      Paint p2 = that.getPaint(key);
      if (!PaintUtilities.equal(p1, p2)) {
        return false;
      }
    }
    return true;
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    stream.writeInt(store.size());
    Set keys = store.keySet();
    Iterator iterator = keys.iterator();
    while (iterator.hasNext()) {
      Comparable key = (Comparable)iterator.next();
      stream.writeObject(key);
      Paint paint = getPaint(key);
      SerialUtilities.writePaint(paint, stream);
    }
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    store = new HashMap();
    int keyCount = stream.readInt();
    for (int i = 0; i < keyCount; i++) {
      Comparable key = (Comparable)stream.readObject();
      Paint paint = SerialUtilities.readPaint(stream);
      store.put(key, paint);
    }
  }
}
