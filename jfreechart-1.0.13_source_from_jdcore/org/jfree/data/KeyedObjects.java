package org.jfree.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jfree.util.PublicCloneable;



















































public class KeyedObjects
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 1321582394193530984L;
  private List data;
  
  public KeyedObjects()
  {
    data = new ArrayList();
  }
  




  public int getItemCount()
  {
    return data.size();
  }
  








  public Object getObject(int item)
  {
    Object result = null;
    KeyedObject kobj = (KeyedObject)data.get(item);
    if (kobj != null) {
      result = kobj.getObject();
    }
    return result;
  }
  










  public Comparable getKey(int index)
  {
    Comparable result = null;
    KeyedObject item = (KeyedObject)data.get(index);
    if (item != null) {
      result = item.getKey();
    }
    return result;
  }
  








  public int getIndex(Comparable key)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    int i = 0;
    Iterator iterator = data.iterator();
    while (iterator.hasNext()) {
      KeyedObject ko = (KeyedObject)iterator.next();
      if (ko.getKey().equals(key)) {
        return i;
      }
      i++;
    }
    return -1;
  }
  




  public List getKeys()
  {
    List result = new ArrayList();
    Iterator iterator = data.iterator();
    while (iterator.hasNext()) {
      KeyedObject ko = (KeyedObject)iterator.next();
      result.add(ko.getKey());
    }
    return result;
  }
  









  public Object getObject(Comparable key)
  {
    int index = getIndex(key);
    if (index < 0) {
      throw new UnknownKeyException("The key (" + key + ") is not recognised.");
    }
    
    return getObject(index);
  }
  








  public void addObject(Comparable key, Object object)
  {
    setObject(key, object);
  }
  









  public void setObject(Comparable key, Object object)
  {
    int keyIndex = getIndex(key);
    if (keyIndex >= 0) {
      KeyedObject ko = (KeyedObject)data.get(keyIndex);
      ko.setObject(object);
    }
    else {
      KeyedObject ko = new KeyedObject(key, object);
      data.add(ko);
    }
  }
  











  public void insertValue(int position, Comparable key, Object value)
  {
    if ((position < 0) || (position > data.size())) {
      throw new IllegalArgumentException("'position' out of bounds.");
    }
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    int pos = getIndex(key);
    if (pos >= 0) {
      data.remove(pos);
    }
    KeyedObject item = new KeyedObject(key, value);
    if (position <= data.size()) {
      data.add(position, item);
    }
    else {
      data.add(item);
    }
  }
  






  public void removeValue(int index)
  {
    data.remove(index);
  }
  









  public void removeValue(Comparable key)
  {
    int index = getIndex(key);
    if (index < 0) {
      throw new UnknownKeyException("The key (" + key.toString() + ") is not recognised.");
    }
    
    removeValue(index);
  }
  




  public void clear()
  {
    data.clear();
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    KeyedObjects clone = (KeyedObjects)super.clone();
    data = new ArrayList();
    Iterator iterator = data.iterator();
    while (iterator.hasNext()) {
      KeyedObject ko = (KeyedObject)iterator.next();
      data.add(ko.clone());
    }
    return clone;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof KeyedObjects)) {
      return false;
    }
    KeyedObjects that = (KeyedObjects)obj;
    int count = getItemCount();
    if (count != that.getItemCount()) {
      return false;
    }
    
    for (int i = 0; i < count; i++) {
      Comparable k1 = getKey(i);
      Comparable k2 = that.getKey(i);
      if (!k1.equals(k2)) {
        return false;
      }
      Object o1 = getObject(i);
      Object o2 = that.getObject(i);
      if (o1 == null) {
        if (o2 != null) {
          return false;
        }
        
      }
      else if (!o1.equals(o2)) {
        return false;
      }
    }
    
    return true;
  }
  





  public int hashCode()
  {
    return data != null ? data.hashCode() : 0;
  }
}
