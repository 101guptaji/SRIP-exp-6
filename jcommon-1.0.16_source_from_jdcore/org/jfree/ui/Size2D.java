package org.jfree.ui;

import java.io.Serializable;
import org.jfree.util.PublicCloneable;































































public class Size2D
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 2558191683786418168L;
  public double width;
  public double height;
  
  public Size2D()
  {
    this(0.0D, 0.0D);
  }
  





  public Size2D(double width, double height)
  {
    this.width = width;
    this.height = height;
  }
  




  public double getWidth()
  {
    return width;
  }
  




  public void setWidth(double width)
  {
    this.width = width;
  }
  




  public double getHeight()
  {
    return height;
  }
  




  public void setHeight(double height)
  {
    this.height = height;
  }
  





  public String toString()
  {
    return "Size2D[width=" + width + ", height=" + height + "]";
  }
  






  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Size2D)) {
      return false;
    }
    Size2D that = (Size2D)obj;
    if (width != width) {
      return false;
    }
    if (height != height) {
      return false;
    }
    return true;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
