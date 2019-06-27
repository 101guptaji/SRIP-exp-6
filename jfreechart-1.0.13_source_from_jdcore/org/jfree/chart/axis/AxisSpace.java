package org.jfree.chart.axis;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.PublicCloneable;




























































public class AxisSpace
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -2490732595134766305L;
  private double top;
  private double bottom;
  private double left;
  private double right;
  
  public AxisSpace()
  {
    top = 0.0D;
    bottom = 0.0D;
    left = 0.0D;
    right = 0.0D;
  }
  




  public double getTop()
  {
    return top;
  }
  




  public void setTop(double space)
  {
    top = space;
  }
  




  public double getBottom()
  {
    return bottom;
  }
  




  public void setBottom(double space)
  {
    bottom = space;
  }
  




  public double getLeft()
  {
    return left;
  }
  




  public void setLeft(double space)
  {
    left = space;
  }
  




  public double getRight()
  {
    return right;
  }
  




  public void setRight(double space)
  {
    right = space;
  }
  





  public void add(double space, RectangleEdge edge)
  {
    if (edge == null) {
      throw new IllegalArgumentException("Null 'edge' argument.");
    }
    if (edge == RectangleEdge.TOP) {
      top += space;
    }
    else if (edge == RectangleEdge.BOTTOM) {
      bottom += space;
    }
    else if (edge == RectangleEdge.LEFT) {
      left += space;
    }
    else if (edge == RectangleEdge.RIGHT) {
      right += space;
    }
    else {
      throw new IllegalStateException("Unrecognised 'edge' argument.");
    }
  }
  




  public void ensureAtLeast(AxisSpace space)
  {
    top = Math.max(top, top);
    bottom = Math.max(bottom, bottom);
    left = Math.max(left, left);
    right = Math.max(right, right);
  }
  






  public void ensureAtLeast(double space, RectangleEdge edge)
  {
    if (edge == RectangleEdge.TOP) {
      if (top < space) {
        top = space;
      }
    }
    else if (edge == RectangleEdge.BOTTOM) {
      if (bottom < space) {
        bottom = space;
      }
    }
    else if (edge == RectangleEdge.LEFT) {
      if (left < space) {
        left = space;
      }
    }
    else if (edge == RectangleEdge.RIGHT) {
      if (right < space) {
        right = space;
      }
    }
    else {
      throw new IllegalStateException("AxisSpace.ensureAtLeast(): unrecognised AxisLocation.");
    }
  }
  









  public Rectangle2D shrink(Rectangle2D area, Rectangle2D result)
  {
    if (result == null) {
      result = new Rectangle2D.Double();
    }
    result.setRect(area.getX() + left, area.getY() + top, area.getWidth() - left - right, area.getHeight() - top - bottom);
    




    return result;
  }
  







  public Rectangle2D expand(Rectangle2D area, Rectangle2D result)
  {
    if (result == null) {
      result = new Rectangle2D.Double();
    }
    result.setRect(area.getX() - left, area.getY() - top, area.getWidth() + left + right, area.getHeight() + top + bottom);
    




    return result;
  }
  







  public Rectangle2D reserved(Rectangle2D area, RectangleEdge edge)
  {
    Rectangle2D result = null;
    if (edge == RectangleEdge.TOP) {
      result = new Rectangle2D.Double(area.getX(), area.getY(), area.getWidth(), top);


    }
    else if (edge == RectangleEdge.BOTTOM) {
      result = new Rectangle2D.Double(area.getX(), area.getMaxY() - top, area.getWidth(), bottom);



    }
    else if (edge == RectangleEdge.LEFT) {
      result = new Rectangle2D.Double(area.getX(), area.getY(), left, area.getHeight());


    }
    else if (edge == RectangleEdge.RIGHT) {
      result = new Rectangle2D.Double(area.getMaxX() - right, area.getY(), right, area.getHeight());
    }
    


    return result;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AxisSpace)) {
      return false;
    }
    AxisSpace that = (AxisSpace)obj;
    if (top != top) {
      return false;
    }
    if (bottom != bottom) {
      return false;
    }
    if (left != left) {
      return false;
    }
    if (right != right) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 23;
    long l = Double.doubleToLongBits(top);
    result = 37 * result + (int)(l ^ l >>> 32);
    l = Double.doubleToLongBits(bottom);
    result = 37 * result + (int)(l ^ l >>> 32);
    l = Double.doubleToLongBits(left);
    result = 37 * result + (int)(l ^ l >>> 32);
    l = Double.doubleToLongBits(right);
    result = 37 * result + (int)(l ^ l >>> 32);
    return result;
  }
  




  public String toString()
  {
    return super.toString() + "[left=" + left + ",right=" + right + ",top=" + top + ",bottom=" + bottom + "]";
  }
}
