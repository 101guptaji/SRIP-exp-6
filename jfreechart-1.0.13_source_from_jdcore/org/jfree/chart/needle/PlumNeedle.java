package org.jfree.chart.needle;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;






















































public class PlumNeedle
  extends MeterNeedle
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -3082660488660600718L;
  
  public PlumNeedle() {}
  
  protected void drawNeedle(Graphics2D g2, Rectangle2D plotArea, Point2D rotate, double angle)
  {
    Arc2D shape = new Arc2D.Double(2);
    double radius = plotArea.getHeight();
    double halfX = plotArea.getWidth() / 2.0D;
    double diameter = 2.0D * radius;
    
    shape.setFrame(plotArea.getMinX() + halfX - radius, plotArea.getMinY() - radius, diameter, diameter);
    

    radius = Math.toDegrees(Math.asin(halfX / radius));
    shape.setAngleStart(270.0D - radius);
    shape.setAngleExtent(2.0D * radius);
    
    Area s = new Area(shape);
    
    if ((rotate != null) && (angle != 0.0D))
    {
      getTransform().setToRotation(angle, rotate.getX(), rotate.getY());
      s.transform(getTransform());
    }
    
    defaultDisplay(g2, s);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof PlumNeedle)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    return super.hashCode();
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
