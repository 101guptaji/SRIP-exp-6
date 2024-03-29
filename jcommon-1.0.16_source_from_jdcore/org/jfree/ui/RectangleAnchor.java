package org.jfree.ui;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.ObjectStreamException;
import java.io.Serializable;



















































public final class RectangleAnchor
  implements Serializable
{
  private static final long serialVersionUID = -2457494205644416327L;
  public static final RectangleAnchor CENTER = new RectangleAnchor("RectangleAnchor.CENTER");
  


  public static final RectangleAnchor TOP = new RectangleAnchor("RectangleAnchor.TOP");
  


  public static final RectangleAnchor TOP_LEFT = new RectangleAnchor("RectangleAnchor.TOP_LEFT");
  


  public static final RectangleAnchor TOP_RIGHT = new RectangleAnchor("RectangleAnchor.TOP_RIGHT");
  


  public static final RectangleAnchor BOTTOM = new RectangleAnchor("RectangleAnchor.BOTTOM");
  


  public static final RectangleAnchor BOTTOM_LEFT = new RectangleAnchor("RectangleAnchor.BOTTOM_LEFT");
  


  public static final RectangleAnchor BOTTOM_RIGHT = new RectangleAnchor("RectangleAnchor.BOTTOM_RIGHT");
  


  public static final RectangleAnchor LEFT = new RectangleAnchor("RectangleAnchor.LEFT");
  


  public static final RectangleAnchor RIGHT = new RectangleAnchor("RectangleAnchor.RIGHT");
  



  private String name;
  



  private RectangleAnchor(String name)
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
    if (!(obj instanceof RectangleAnchor)) {
      return false;
    }
    
    RectangleAnchor order = (RectangleAnchor)obj;
    if (!name.equals(name)) {
      return false;
    }
    
    return true;
  }
  




  public int hashCode()
  {
    return name.hashCode();
  }
  








  public static Point2D coordinates(Rectangle2D rectangle, RectangleAnchor anchor)
  {
    Point2D result = new Point2D.Double();
    if (anchor == CENTER) {
      result.setLocation(rectangle.getCenterX(), rectangle.getCenterY());
    }
    else if (anchor == TOP) {
      result.setLocation(rectangle.getCenterX(), rectangle.getMinY());
    }
    else if (anchor == BOTTOM) {
      result.setLocation(rectangle.getCenterX(), rectangle.getMaxY());
    }
    else if (anchor == LEFT) {
      result.setLocation(rectangle.getMinX(), rectangle.getCenterY());
    }
    else if (anchor == RIGHT) {
      result.setLocation(rectangle.getMaxX(), rectangle.getCenterY());
    }
    else if (anchor == TOP_LEFT) {
      result.setLocation(rectangle.getMinX(), rectangle.getMinY());
    }
    else if (anchor == TOP_RIGHT) {
      result.setLocation(rectangle.getMaxX(), rectangle.getMinY());
    }
    else if (anchor == BOTTOM_LEFT) {
      result.setLocation(rectangle.getMinX(), rectangle.getMaxY());
    }
    else if (anchor == BOTTOM_RIGHT) {
      result.setLocation(rectangle.getMaxX(), rectangle.getMaxY());
    }
    return result;
  }
  













  public static Rectangle2D createRectangle(Size2D dimensions, double anchorX, double anchorY, RectangleAnchor anchor)
  {
    Rectangle2D result = null;
    double w = dimensions.getWidth();
    double h = dimensions.getHeight();
    if (anchor == CENTER) {
      result = new Rectangle2D.Double(anchorX - w / 2.0D, anchorY - h / 2.0D, w, h);


    }
    else if (anchor == TOP) {
      result = new Rectangle2D.Double(anchorX - w / 2.0D, anchorY - h / 2.0D, w, h);


    }
    else if (anchor == BOTTOM) {
      result = new Rectangle2D.Double(anchorX - w / 2.0D, anchorY - h / 2.0D, w, h);


    }
    else if (anchor == LEFT) {
      result = new Rectangle2D.Double(anchorX, anchorY - h / 2.0D, w, h);


    }
    else if (anchor == RIGHT) {
      result = new Rectangle2D.Double(anchorX - w, anchorY - h / 2.0D, w, h);


    }
    else if (anchor == TOP_LEFT) {
      result = new Rectangle2D.Double(anchorX - w / 2.0D, anchorY - h / 2.0D, w, h);


    }
    else if (anchor == TOP_RIGHT) {
      result = new Rectangle2D.Double(anchorX - w / 2.0D, anchorY - h / 2.0D, w, h);


    }
    else if (anchor == BOTTOM_LEFT) {
      result = new Rectangle2D.Double(anchorX - w / 2.0D, anchorY - h / 2.0D, w, h);


    }
    else if (anchor == BOTTOM_RIGHT) {
      result = new Rectangle2D.Double(anchorX - w / 2.0D, anchorY - h / 2.0D, w, h);
    }
    

    return result;
  }
  





  private Object readResolve()
    throws ObjectStreamException
  {
    RectangleAnchor result = null;
    if (equals(CENTER)) {
      result = CENTER;
    }
    else if (equals(TOP)) {
      result = TOP;
    }
    else if (equals(BOTTOM)) {
      result = BOTTOM;
    }
    else if (equals(LEFT)) {
      result = LEFT;
    }
    else if (equals(RIGHT)) {
      result = RIGHT;
    }
    else if (equals(TOP_LEFT)) {
      result = TOP_LEFT;
    }
    else if (equals(TOP_RIGHT)) {
      result = TOP_RIGHT;
    }
    else if (equals(BOTTOM_LEFT)) {
      result = BOTTOM_LEFT;
    }
    else if (equals(BOTTOM_RIGHT)) {
      result = BOTTOM_RIGHT;
    }
    return result;
  }
}
