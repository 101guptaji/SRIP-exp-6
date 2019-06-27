package org.jfree.ui;

import java.io.ObjectStreamException;
import java.io.Serializable;



















































public final class VerticalAlignment
  implements Serializable
{
  private static final long serialVersionUID = 7272397034325429853L;
  public static final VerticalAlignment TOP = new VerticalAlignment("VerticalAlignment.TOP");
  


  public static final VerticalAlignment BOTTOM = new VerticalAlignment("VerticalAlignment.BOTTOM");
  


  public static final VerticalAlignment CENTER = new VerticalAlignment("VerticalAlignment.CENTER");
  



  private String name;
  



  private VerticalAlignment(String name)
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
    if (!(o instanceof VerticalAlignment)) {
      return false;
    }
    
    VerticalAlignment alignment = (VerticalAlignment)o;
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
    if (equals(TOP)) {
      return TOP;
    }
    if (equals(BOTTOM)) {
      return BOTTOM;
    }
    if (equals(CENTER)) {
      return CENTER;
    }
    
    return null;
  }
}
