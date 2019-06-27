package org.jfree.ui;

import java.io.ObjectStreamException;
import java.io.Serializable;


















































public final class Layer
  implements Serializable
{
  private static final long serialVersionUID = -1470104570733183430L;
  public static final Layer FOREGROUND = new Layer("Layer.FOREGROUND");
  

  public static final Layer BACKGROUND = new Layer("Layer.BACKGROUND");
  


  private String name;
  



  private Layer(String name)
  {
    this.name = name;
  }
  




  public String toString()
  {
    return name;
  }
  








  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Layer)) {
      return false;
    }
    
    Layer layer = (Layer)o;
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
    Layer result = null;
    if (equals(FOREGROUND)) {
      result = FOREGROUND;
    }
    else if (equals(BACKGROUND)) {
      result = BACKGROUND;
    }
    return result;
  }
}
