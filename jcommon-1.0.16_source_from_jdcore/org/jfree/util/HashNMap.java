package org.jfree.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;





















































public class HashNMap
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = -670924844536074826L;
  
  private static final class EmptyIterator
    implements Iterator
  {
    EmptyIterator(HashNMap.1 x0)
    {
      this();
    }
    












    public boolean hasNext()
    {
      return false;
    }
    





    public Object next()
    {
      throw new NoSuchElementException("This iterator is empty.");
    }
    













    public void remove()
    {
      throw new UnsupportedOperationException("This iterator is empty, no remove supported.");
    }
    

    private EmptyIterator() {}
  }
  

  private static final Iterator EMPTY_ITERATOR = new EmptyIterator(null);
  



  private HashMap table;
  



  private static final Object[] EMPTY_ARRAY = new Object[0];
  


  public HashNMap()
  {
    table = new HashMap();
  }
  




  protected List createList()
  {
    return new ArrayList();
  }
  







  public boolean put(Object key, Object val)
  {
    List v = (List)table.get(key);
    if (v == null) {
      List newList = createList();
      newList.add(val);
      table.put(key, newList);
      return true;
    }
    
    v.clear();
    return v.add(val);
  }
  









  public boolean add(Object key, Object val)
  {
    List v = (List)table.get(key);
    if (v == null) {
      put(key, val);
      return true;
    }
    
    return v.add(val);
  }
  







  public Object getFirst(Object key)
  {
    return get(key, 0);
  }
  








  public Object get(Object key, int n)
  {
    List v = (List)table.get(key);
    if (v == null) {
      return null;
    }
    return v.get(n);
  }
  





  public Iterator getAll(Object key)
  {
    List v = (List)table.get(key);
    if (v == null) {
      return EMPTY_ITERATOR;
    }
    return v.iterator();
  }
  




  public Iterator keys()
  {
    return table.keySet().iterator();
  }
  




  public Set keySet()
  {
    return table.keySet();
  }
  







  public boolean remove(Object key, Object value)
  {
    List v = (List)table.get(key);
    if (v == null) {
      return false;
    }
    
    if (!v.remove(value)) {
      return false;
    }
    if (v.size() == 0) {
      table.remove(key);
    }
    return true;
  }
  




  public void removeAll(Object key)
  {
    table.remove(key);
  }
  


  public void clear()
  {
    table.clear();
  }
  





  public boolean containsKey(Object key)
  {
    return table.containsKey(key);
  }
  





  public boolean containsValue(Object value)
  {
    Iterator e = table.values().iterator();
    boolean found = false;
    while ((e.hasNext()) && (!found)) {
      List v = (List)e.next();
      found = v.contains(value);
    }
    return found;
  }
  






  public boolean containsValue(Object key, Object value)
  {
    List v = (List)table.get(key);
    if (v == null) {
      return false;
    }
    return v.contains(value);
  }
  





  public boolean contains(Object value)
  {
    if (containsKey(value)) {
      return true;
    }
    return containsValue(value);
  }
  




  public Object clone()
    throws CloneNotSupportedException
  {
    HashNMap map = (HashNMap)super.clone();
    table = new HashMap();
    Iterator iterator = keys();
    while (iterator.hasNext()) {
      Object key = iterator.next();
      List list = (List)table.get(key);
      if (list != null) {
        table.put(key, ObjectUtilities.clone(list));
      }
    }
    return map;
  }
  







  public Object[] toArray(Object key, Object[] data)
  {
    if (key == null) {
      throw new NullPointerException("Key must not be null.");
    }
    List list = (List)table.get(key);
    if (list != null) {
      return list.toArray(data);
    }
    if (data.length > 0) {
      data[0] = null;
    }
    return data;
  }
  






  public Object[] toArray(Object key)
  {
    if (key == null) {
      throw new NullPointerException("Key must not be null.");
    }
    List list = (List)table.get(key);
    if (list != null) {
      return list.toArray();
    }
    return EMPTY_ARRAY;
  }
  






  public int getValueCount(Object key)
  {
    if (key == null) {
      throw new NullPointerException("Key must not be null.");
    }
    List list = (List)table.get(key);
    if (list != null) {
      return list.size();
    }
    return 0;
  }
}
