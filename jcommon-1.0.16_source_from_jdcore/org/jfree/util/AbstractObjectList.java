package org.jfree.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
























































public class AbstractObjectList
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 7789833772597351595L;
  public static final int DEFAULT_INITIAL_CAPACITY = 8;
  private transient Object[] objects;
  private int size = 0;
  

  private int increment = 8;
  


  protected AbstractObjectList()
  {
    this(8);
  }
  




  protected AbstractObjectList(int initialCapacity)
  {
    this(initialCapacity, initialCapacity);
  }
  






  protected AbstractObjectList(int initialCapacity, int increment)
  {
    objects = new Object[initialCapacity];
    this.increment = increment;
  }
  







  protected Object get(int index)
  {
    Object result = null;
    if ((index >= 0) && (index < size)) {
      result = objects[index];
    }
    return result;
  }
  





  protected void set(int index, Object object)
  {
    if (index < 0) {
      throw new IllegalArgumentException("Requires index >= 0.");
    }
    if (index >= objects.length) {
      Object[] enlarged = new Object[index + increment];
      System.arraycopy(objects, 0, enlarged, 0, objects.length);
      objects = enlarged;
    }
    objects[index] = object;
    size = Math.max(size, index + 1);
  }
  


  public void clear()
  {
    Arrays.fill(objects, null);
    size = 0;
  }
  




  public int size()
  {
    return size;
  }
  







  protected int indexOf(Object object)
  {
    for (int index = 0; index < size; index++) {
      if (objects[index] == object) {
        return index;
      }
    }
    return -1;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    
    if (obj == this) {
      return true;
    }
    
    if (!(obj instanceof AbstractObjectList)) {
      return false;
    }
    
    AbstractObjectList other = (AbstractObjectList)obj;
    int listSize = size();
    for (int i = 0; i < listSize; i++) {
      if (!ObjectUtilities.equal(get(i), other.get(i))) {
        return false;
      }
    }
    return true;
  }
  




  public int hashCode()
  {
    return super.hashCode();
  }
  








  public Object clone()
    throws CloneNotSupportedException
  {
    AbstractObjectList clone = (AbstractObjectList)super.clone();
    if (objects != null) {
      objects = new Object[objects.length];
      System.arraycopy(objects, 0, objects, 0, objects.length);
    }
    

    return clone;
  }
  








  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    int count = size();
    stream.writeInt(count);
    for (int i = 0; i < count; i++) {
      Object object = get(i);
      if ((object != null) && ((object instanceof Serializable))) {
        stream.writeInt(i);
        stream.writeObject(object);
      }
      else {
        stream.writeInt(-1);
      }
    }
  }
  









  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    objects = new Object[size];
    int count = stream.readInt();
    for (int i = 0; i < count; i++) {
      int index = stream.readInt();
      if (index != -1) {
        set(index, stream.readObject());
      }
    }
  }
}
