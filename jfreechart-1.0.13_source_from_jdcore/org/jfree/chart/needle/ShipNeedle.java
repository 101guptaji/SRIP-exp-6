package org.jfree.chart.needle;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
























































public class ShipNeedle
  extends MeterNeedle
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 149554868169435612L;
  
  public ShipNeedle() {}
  
  protected void drawNeedle(Graphics2D g2, Rectangle2D plotArea, Point2D rotate, double angle)
  {
    GeneralPath shape = new GeneralPath();
    shape.append(new Arc2D.Double(-9.0D, -7.0D, 10.0D, 14.0D, 0.0D, 25.5D, 0), true);
    
    shape.append(new Arc2D.Double(0.0D, -7.0D, 10.0D, 14.0D, 154.5D, 25.5D, 0), true);
    
    shape.closePath();
    getTransform().setToTranslation(plotArea.getMinX(), plotArea.getMaxY());
    getTransform().scale(plotArea.getWidth(), plotArea.getHeight() / 3.0D);
    shape.transform(getTransform());
    
    if ((rotate != null) && (angle != 0.0D))
    {
      getTransform().setToRotation(angle, rotate.getX(), rotate.getY());
      shape.transform(getTransform());
    }
    
    defaultDisplay(g2, shape);
  }
  






  public boolean equals(Object object)
  {
    if (object == null) {
      return false;
    }
    if (object == this) {
      return true;
    }
    if ((super.equals(object)) && ((object instanceof ShipNeedle))) {
      return true;
    }
    return false;
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
