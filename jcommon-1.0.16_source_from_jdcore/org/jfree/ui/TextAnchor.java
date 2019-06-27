package org.jfree.ui;

import java.io.ObjectStreamException;
import java.io.Serializable;




















































public final class TextAnchor
  implements Serializable
{
  private static final long serialVersionUID = 8219158940496719660L;
  public static final TextAnchor TOP_LEFT = new TextAnchor("TextAnchor.TOP_LEFT");
  


  public static final TextAnchor TOP_CENTER = new TextAnchor("TextAnchor.TOP_CENTER");
  


  public static final TextAnchor TOP_RIGHT = new TextAnchor("TextAnchor.TOP_RIGHT");
  


  public static final TextAnchor HALF_ASCENT_LEFT = new TextAnchor("TextAnchor.HALF_ASCENT_LEFT");
  


  public static final TextAnchor HALF_ASCENT_CENTER = new TextAnchor("TextAnchor.HALF_ASCENT_CENTER");
  


  public static final TextAnchor HALF_ASCENT_RIGHT = new TextAnchor("TextAnchor.HALF_ASCENT_RIGHT");
  


  public static final TextAnchor CENTER_LEFT = new TextAnchor("TextAnchor.CENTER_LEFT");
  


  public static final TextAnchor CENTER = new TextAnchor("TextAnchor.CENTER");
  

  public static final TextAnchor CENTER_RIGHT = new TextAnchor("TextAnchor.CENTER_RIGHT");
  


  public static final TextAnchor BASELINE_LEFT = new TextAnchor("TextAnchor.BASELINE_LEFT");
  


  public static final TextAnchor BASELINE_CENTER = new TextAnchor("TextAnchor.BASELINE_CENTER");
  


  public static final TextAnchor BASELINE_RIGHT = new TextAnchor("TextAnchor.BASELINE_RIGHT");
  


  public static final TextAnchor BOTTOM_LEFT = new TextAnchor("TextAnchor.BOTTOM_LEFT");
  


  public static final TextAnchor BOTTOM_CENTER = new TextAnchor("TextAnchor.BOTTOM_CENTER");
  


  public static final TextAnchor BOTTOM_RIGHT = new TextAnchor("TextAnchor.BOTTOM_RIGHT");
  



  private String name;
  



  private TextAnchor(String name)
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
    if (!(o instanceof TextAnchor)) {
      return false;
    }
    
    TextAnchor order = (TextAnchor)o;
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
    TextAnchor result = null;
    if (equals(TOP_LEFT)) {
      result = TOP_LEFT;
    }
    else if (equals(TOP_CENTER)) {
      result = TOP_CENTER;
    }
    else if (equals(TOP_RIGHT)) {
      result = TOP_RIGHT;
    }
    else if (equals(BOTTOM_LEFT)) {
      result = BOTTOM_LEFT;
    }
    else if (equals(BOTTOM_CENTER)) {
      result = BOTTOM_CENTER;
    }
    else if (equals(BOTTOM_RIGHT)) {
      result = BOTTOM_RIGHT;
    }
    else if (equals(BASELINE_LEFT)) {
      result = BASELINE_LEFT;
    }
    else if (equals(BASELINE_CENTER)) {
      result = BASELINE_CENTER;
    }
    else if (equals(BASELINE_RIGHT)) {
      result = BASELINE_RIGHT;
    }
    else if (equals(CENTER_LEFT)) {
      result = CENTER_LEFT;
    }
    else if (equals(CENTER)) {
      result = CENTER;
    }
    else if (equals(CENTER_RIGHT)) {
      result = CENTER_RIGHT;
    }
    else if (equals(HALF_ASCENT_LEFT)) {
      result = HALF_ASCENT_LEFT;
    }
    else if (equals(HALF_ASCENT_CENTER)) {
      result = HALF_ASCENT_CENTER;
    }
    else if (equals(HALF_ASCENT_RIGHT)) {
      result = HALF_ASCENT_RIGHT;
    }
    return result;
  }
}
