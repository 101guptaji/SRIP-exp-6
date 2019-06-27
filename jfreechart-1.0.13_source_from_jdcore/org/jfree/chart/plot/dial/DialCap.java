package org.jfree.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
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








































































public class DialCap
  extends AbstractDialLayer
  implements DialLayer, Cloneable, PublicCloneable, Serializable
{
  static final long serialVersionUID = -2929484264982524463L;
  private double radius;
  private transient Paint fillPaint;
  private transient Paint outlinePaint;
  private transient Stroke outlineStroke;
  
  public DialCap()
  {
    radius = 0.05D;
    fillPaint = Color.white;
    outlinePaint = Color.black;
    outlineStroke = new BasicStroke(2.0F);
  }
  







  public double getRadius()
  {
    return radius;
  }
  








  public void setRadius(double radius)
  {
    if (radius <= 0.0D) {
      throw new IllegalArgumentException("Requires radius > 0.0.");
    }
    this.radius = radius;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Paint getFillPaint()
  {
    return fillPaint;
  }
  







  public void setFillPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    fillPaint = paint;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Paint getOutlinePaint()
  {
    return outlinePaint;
  }
  







  public void setOutlinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    outlinePaint = paint;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Stroke getOutlineStroke()
  {
    return outlineStroke;
  }
  







  public void setOutlineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    outlineStroke = stroke;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  





  public boolean isClippedToWindow()
  {
    return true;
  }
  











  public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view)
  {
    g2.setPaint(fillPaint);
    
    Rectangle2D f = DialPlot.rectangleByRadius(frame, radius, radius);
    
    Ellipse2D e = new Ellipse2D.Double(f.getX(), f.getY(), f.getWidth(), f.getHeight());
    
    g2.fill(e);
    g2.setPaint(outlinePaint);
    g2.setStroke(outlineStroke);
    g2.draw(e);
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DialCap)) {
      return false;
    }
    DialCap that = (DialCap)obj;
    if (radius != radius) {
      return false;
    }
    if (!PaintUtilities.equal(fillPaint, fillPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(outlinePaint, outlinePaint)) {
      return false;
    }
    if (!outlineStroke.equals(outlineStroke)) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = 193;
    result = 37 * result + HashUtilities.hashCodeForPaint(fillPaint);
    result = 37 * result + HashUtilities.hashCodeForPaint(outlinePaint);
    
    result = 37 * result + outlineStroke.hashCode();
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
    SerialUtilities.writePaint(fillPaint, stream);
    SerialUtilities.writePaint(outlinePaint, stream);
    SerialUtilities.writeStroke(outlineStroke, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    fillPaint = SerialUtilities.readPaint(stream);
    outlinePaint = SerialUtilities.readPaint(stream);
    outlineStroke = SerialUtilities.readStroke(stream);
  }
}
