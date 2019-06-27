package org.jfree.data;

import java.io.Serializable;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
























































public class KeyedObject
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 2677930479256885863L;
  private Comparable key;
  private Object object;
  
  public KeyedObject(Comparable key, Object object)
  {
    this.key = key;
    this.object = object;
  }
  




  public Comparable getKey()
  {
    return key;
  }
  




  public Object getObject()
  {
    return object;
  }
  




  public void setObject(Object object)
  {
    this.object = object;
  }
  








  public Object clone()
    throws CloneNotSupportedException
  {
    KeyedObject clone = (KeyedObject)super.clone();
    if ((object instanceof PublicCloneable)) {
      PublicCloneable pc = (PublicCloneable)object;
      object = pc.clone();
    }
    return clone;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    
    if (!(obj instanceof KeyedObject)) {
      return false;
    }
    KeyedObject that = (KeyedObject)obj;
    if (!ObjectUtilities.equal(key, key)) {
      return false;
    }
    
    if (!ObjectUtilities.equal(object, object)) {
      return false;
    }
    
    return true;
  }
}
