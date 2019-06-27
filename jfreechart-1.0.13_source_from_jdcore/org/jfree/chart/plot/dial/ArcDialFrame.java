package org.jfree.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;
















































































public class ArcDialFrame
  extends AbstractDialLayer
  implements DialFrame, Cloneable, PublicCloneable, Serializable
{
  static final long serialVersionUID = -4089176959553523499L;
  private transient Paint backgroundPaint;
  private transient Paint foregroundPaint;
  private transient Stroke stroke;
  private double startAngle;
  private double extent;
  private double innerRadius;
  private double outerRadius;
  
  public ArcDialFrame()
  {
    this(0.0D, 180.0D);
  }
  






  public ArcDialFrame(double startAngle, double extent)
  {
    backgroundPaint = Color.gray;
    foregroundPaint = new Color(100, 100, 150);
    stroke = new BasicStroke(2.0F);
    innerRadius = 0.25D;
    outerRadius = 0.75D;
    this.startAngle = startAngle;
    this.extent = extent;
  }
  






  public Paint getBackgroundPaint()
  {
    return backgroundPaint;
  }
  







  public void setBackgroundPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    backgroundPaint = paint;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Paint getForegroundPaint()
  {
    return foregroundPaint;
  }
  







  public void setForegroundPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    foregroundPaint = paint;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Stroke getStroke()
  {
    return stroke;
  }
  







  public void setStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    this.stroke = stroke;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public double getInnerRadius()
  {
    return innerRadius;
  }
  







  public void setInnerRadius(double radius)
  {
    if (radius < 0.0D) {
      throw new IllegalArgumentException("Negative 'radius' argument.");
    }
    innerRadius = radius;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public double getOuterRadius()
  {
    return outerRadius;
  }
  







  public void setOuterRadius(double radius)
  {
    if (radius < 0.0D) {
      throw new IllegalArgumentException("Negative 'radius' argument.");
    }
    outerRadius = radius;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public double getStartAngle()
  {
    return startAngle;
  }
  







  public void setStartAngle(double angle)
  {
    startAngle = angle;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public double getExtent()
  {
    return extent;
  }
  







  public void setExtent(double extent)
  {
    this.extent = extent;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  








  public Shape getWindow(Rectangle2D frame)
  {
    Rectangle2D innerFrame = DialPlot.rectangleByRadius(frame, innerRadius, innerRadius);
    
    Rectangle2D outerFrame = DialPlot.rectangleByRadius(frame, outerRadius, outerRadius);
    
    Arc2D inner = new Arc2D.Double(innerFrame, startAngle, extent, 0);
    
    Arc2D outer = new Arc2D.Double(outerFrame, startAngle + extent, -extent, 0);
    
    GeneralPath p = new GeneralPath();
    Point2D point1 = inner.getStartPoint();
    p.moveTo((float)point1.getX(), (float)point1.getY());
    p.append(inner, true);
    p.append(outer, true);
    p.closePath();
    return p;
  }
  







  protected Shape getOuterWindow(Rectangle2D frame)
  {
    double radiusMargin = 0.02D;
    double angleMargin = 1.5D;
    Rectangle2D innerFrame = DialPlot.rectangleByRadius(frame, innerRadius - radiusMargin, innerRadius - radiusMargin);
    

    Rectangle2D outerFrame = DialPlot.rectangleByRadius(frame, outerRadius + radiusMargin, outerRadius + radiusMargin);
    

    Arc2D inner = new Arc2D.Double(innerFrame, startAngle - angleMargin, extent + 2.0D * angleMargin, 0);
    
    Arc2D outer = new Arc2D.Double(outerFrame, startAngle + angleMargin + extent, -extent - 2.0D * angleMargin, 0);
    

    GeneralPath p = new GeneralPath();
    Point2D point1 = inner.getStartPoint();
    p.moveTo((float)point1.getX(), (float)point1.getY());
    p.append(inner, true);
    p.append(outer, true);
    p.closePath();
    return p;
  }
  









  public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view)
  {
    Shape window = getWindow(frame);
    Shape outerWindow = getOuterWindow(frame);
    
    Area area1 = new Area(outerWindow);
    Area area2 = new Area(window);
    area1.subtract(area2);
    g2.setPaint(Color.lightGray);
    g2.fill(area1);
    
    g2.setStroke(stroke);
    g2.setPaint(foregroundPaint);
    g2.draw(window);
    g2.draw(outerWindow);
  }
  






  public boolean isClippedToWindow()
  {
    return false;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ArcDialFrame)) {
      return false;
    }
    ArcDialFrame that = (ArcDialFrame)obj;
    if (!PaintUtilities.equal(backgroundPaint, backgroundPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(foregroundPaint, foregroundPaint)) {
      return false;
    }
    if (startAngle != startAngle) {
      return false;
    }
    if (extent != extent) {
      return false;
    }
    if (innerRadius != innerRadius) {
      return false;
    }
    if (outerRadius != outerRadius) {
      return false;
    }
    if (!stroke.equals(stroke)) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = 193;
    long temp = Double.doubleToLongBits(startAngle);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(extent);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(innerRadius);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(outerRadius);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    result = 37 * result + HashUtilities.hashCodeForPaint(backgroundPaint);
    
    result = 37 * result + HashUtilities.hashCodeForPaint(foregroundPaint);
    
    result = 37 * result + stroke.hashCode();
    return result;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(backgroundPaint, stream);
    SerialUtilities.writePaint(foregroundPaint, stream);
    SerialUtilities.writeStroke(stroke, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    backgroundPaint = SerialUtilities.readPaint(stream);
    foregroundPaint = SerialUtilities.readPaint(stream);
    stroke = SerialUtilities.readStroke(stream);
  }
}
