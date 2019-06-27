package org.jfree.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;



































































public class StandardDialFrame
  extends AbstractDialLayer
  implements DialFrame, Cloneable, PublicCloneable, Serializable
{
  static final long serialVersionUID = 1016585407507121596L;
  private double radius;
  private transient Paint backgroundPaint;
  private transient Paint foregroundPaint;
  private transient Stroke stroke;
  
  public StandardDialFrame()
  {
    backgroundPaint = Color.gray;
    foregroundPaint = Color.black;
    stroke = new BasicStroke(2.0F);
    radius = 0.95D;
  }
  






  public double getRadius()
  {
    return radius;
  }
  







  public void setRadius(double radius)
  {
    if (radius <= 0.0D) {
      throw new IllegalArgumentException("The 'radius' must be positive.");
    }
    
    this.radius = radius;
    notifyListeners(new DialLayerChangeEvent(this));
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
  







  public Shape getWindow(Rectangle2D frame)
  {
    Rectangle2D f = DialPlot.rectangleByRadius(frame, radius, radius);
    
    return new Ellipse2D.Double(f.getX(), f.getY(), f.getWidth(), f.getHeight());
  }
  






  public boolean isClippedToWindow()
  {
    return false;
  }
  










  public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view)
  {
    Shape window = getWindow(frame);
    
    Rectangle2D f = DialPlot.rectangleByRadius(frame, radius + 0.02D, radius + 0.02D);
    
    Ellipse2D e = new Ellipse2D.Double(f.getX(), f.getY(), f.getWidth(), f.getHeight());
    

    Area area = new Area(e);
    Area area2 = new Area(window);
    area.subtract(area2);
    g2.setPaint(backgroundPaint);
    g2.fill(area);
    
    g2.setStroke(stroke);
    g2.setPaint(foregroundPaint);
    g2.draw(window);
    g2.draw(e);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StandardDialFrame)) {
      return false;
    }
    StandardDialFrame that = (StandardDialFrame)obj;
    if (!PaintUtilities.equal(backgroundPaint, backgroundPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(foregroundPaint, foregroundPaint)) {
      return false;
    }
    if (radius != radius) {
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
    long temp = Double.doubleToLongBits(radius);
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
