package org.jfree.ui;

import java.awt.geom.Dimension2D;
import java.io.Serializable;























































public class FloatDimension
  extends Dimension2D
  implements Serializable
{
  private static final long serialVersionUID = 5367882923248086744L;
  private float width;
  private float height;
  
  public FloatDimension()
  {
    width = 0.0F;
    height = 0.0F;
  }
  




  public FloatDimension(FloatDimension fd)
  {
    width = width;
    height = height;
  }
  





  public FloatDimension(float width, float height)
  {
    this.width = width;
    this.height = height;
  }
  




  public double getWidth()
  {
    return width;
  }
  




  public double getHeight()
  {
    return height;
  }
  




  public void setWidth(double width)
  {
    this.width = ((float)width);
  }
  




  public void setHeight(double height)
  {
    this.height = ((float)height);
  }
  








  public void setSize(double width, double height)
  {
    setHeight((float)height);
    setWidth((float)width);
  }
  





  public Object clone()
  {
    return super.clone();
  }
  









  public String toString()
  {
    return getClass().getName() + ":={width=" + getWidth() + ", height=" + getHeight() + "}";
  }
  







  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FloatDimension)) {
      return false;
    }
    
    FloatDimension floatDimension = (FloatDimension)o;
    
    if (height != height) {
      return false;
    }
    if (width != width) {
      return false;
    }
    
    return true;
  }
  





  public int hashCode()
  {
    int result = Float.floatToIntBits(width);
    result = 29 * result + Float.floatToIntBits(height);
    return result;
  }
}
