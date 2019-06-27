package org.jfree.ui;

import java.io.ObjectStreamException;
import java.io.Serializable;



















































public final class HorizontalAlignment
  implements Serializable
{
  private static final long serialVersionUID = -8249740987565309567L;
  public static final HorizontalAlignment LEFT = new HorizontalAlignment("HorizontalAlignment.LEFT");
  


  public static final HorizontalAlignment RIGHT = new HorizontalAlignment("HorizontalAlignment.RIGHT");
  


  public static final HorizontalAlignment CENTER = new HorizontalAlignment("HorizontalAlignment.CENTER");
  



  private String name;
  



  private HorizontalAlignment(String name)
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
    if (!(obj instanceof HorizontalAlignment)) {
      return false;
    }
    HorizontalAlignment that = (HorizontalAlignment)obj;
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
    HorizontalAlignment result = null;
    if (equals(LEFT)) {
      result = LEFT;
    }
    else if (equals(RIGHT)) {
      result = RIGHT;
    }
    else if (equals(CENTER)) {
      result = CENTER;
    }
    return result;
  }
}
