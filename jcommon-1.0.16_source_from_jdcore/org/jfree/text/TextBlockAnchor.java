package org.jfree.text;

import java.io.ObjectStreamException;
import java.io.Serializable;



















































public final class TextBlockAnchor
  implements Serializable
{
  private static final long serialVersionUID = -3045058380983401544L;
  public static final TextBlockAnchor TOP_LEFT = new TextBlockAnchor("TextBlockAnchor.TOP_LEFT");
  


  public static final TextBlockAnchor TOP_CENTER = new TextBlockAnchor("TextBlockAnchor.TOP_CENTER");
  



  public static final TextBlockAnchor TOP_RIGHT = new TextBlockAnchor("TextBlockAnchor.TOP_RIGHT");
  



  public static final TextBlockAnchor CENTER_LEFT = new TextBlockAnchor("TextBlockAnchor.CENTER_LEFT");
  



  public static final TextBlockAnchor CENTER = new TextBlockAnchor("TextBlockAnchor.CENTER");
  


  public static final TextBlockAnchor CENTER_RIGHT = new TextBlockAnchor("TextBlockAnchor.CENTER_RIGHT");
  



  public static final TextBlockAnchor BOTTOM_LEFT = new TextBlockAnchor("TextBlockAnchor.BOTTOM_LEFT");
  


  public static final TextBlockAnchor BOTTOM_CENTER = new TextBlockAnchor("TextBlockAnchor.BOTTOM_CENTER");
  


  public static final TextBlockAnchor BOTTOM_RIGHT = new TextBlockAnchor("TextBlockAnchor.BOTTOM_RIGHT");
  



  private String name;
  



  private TextBlockAnchor(String name)
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
    if (!(o instanceof TextBlockAnchor)) {
      return false;
    }
    
    TextBlockAnchor other = (TextBlockAnchor)o;
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
    if (equals(TOP_CENTER)) {
      return TOP_CENTER;
    }
    if (equals(TOP_LEFT)) {
      return TOP_LEFT;
    }
    if (equals(TOP_RIGHT)) {
      return TOP_RIGHT;
    }
    if (equals(CENTER)) {
      return CENTER;
    }
    if (equals(CENTER_LEFT)) {
      return CENTER_LEFT;
    }
    if (equals(CENTER_RIGHT)) {
      return CENTER_RIGHT;
    }
    if (equals(BOTTOM_CENTER)) {
      return BOTTOM_CENTER;
    }
    if (equals(BOTTOM_LEFT)) {
      return BOTTOM_LEFT;
    }
    if (equals(BOTTOM_RIGHT)) {
      return BOTTOM_RIGHT;
    }
    return null;
  }
}
