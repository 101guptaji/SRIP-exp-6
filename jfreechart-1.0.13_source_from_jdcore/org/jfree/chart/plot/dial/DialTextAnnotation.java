package org.jfree.chart.plot.dial;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;




































































public class DialTextAnnotation
  extends AbstractDialLayer
  implements DialLayer, Cloneable, PublicCloneable, Serializable
{
  static final long serialVersionUID = 3065267524054428071L;
  private String label;
  private Font font;
  private transient Paint paint;
  private double angle;
  private double radius;
  private TextAnchor anchor;
  
  public DialTextAnnotation(String label)
  {
    if (label == null) {
      throw new IllegalArgumentException("Null 'label' argument.");
    }
    angle = -90.0D;
    radius = 0.3D;
    font = new Font("Dialog", 1, 14);
    paint = Color.black;
    this.label = label;
    anchor = TextAnchor.TOP_CENTER;
  }
  






  public String getLabel()
  {
    return label;
  }
  







  public void setLabel(String label)
  {
    if (label == null) {
      throw new IllegalArgumentException("Null 'label' argument.");
    }
    this.label = label;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Font getFont()
  {
    return font;
  }
  







  public void setFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    this.font = font;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Paint getPaint()
  {
    return paint;
  }
  







  public void setPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    this.paint = paint;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  







  public double getAngle()
  {
    return angle;
  }
  








  public void setAngle(double angle)
  {
    this.angle = angle;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  








  public double getRadius()
  {
    return radius;
  }
  









  public void setRadius(double radius)
  {
    if (radius < 0.0D) {
      throw new IllegalArgumentException("The 'radius' cannot be negative.");
    }
    
    this.radius = radius;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  







  public TextAnchor getAnchor()
  {
    return anchor;
  }
  







  public void setAnchor(TextAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    this.anchor = anchor;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  





  public boolean isClippedToWindow()
  {
    return true;
  }
  












  public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view)
  {
    Rectangle2D f = DialPlot.rectangleByRadius(frame, radius, radius);
    
    Arc2D arc = new Arc2D.Double(f, angle, 0.0D, 0);
    Point2D pt = arc.getStartPoint();
    g2.setPaint(paint);
    g2.setFont(font);
    TextUtilities.drawAlignedString(label, g2, (float)pt.getX(), (float)pt.getY(), anchor);
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DialTextAnnotation)) {
      return false;
    }
    DialTextAnnotation that = (DialTextAnnotation)obj;
    if (!label.equals(label)) {
      return false;
    }
    if (!font.equals(font)) {
      return false;
    }
    if (!PaintUtilities.equal(paint, paint)) {
      return false;
    }
    if (radius != radius) {
      return false;
    }
    if (angle != angle) {
      return false;
    }
    if (!anchor.equals(anchor)) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = 193;
    result = 37 * result + HashUtilities.hashCodeForPaint(paint);
    result = 37 * result + font.hashCode();
    result = 37 * result + label.hashCode();
    result = 37 * result + anchor.hashCode();
    long temp = Double.doubleToLongBits(angle);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(radius);
    result = 37 * result + (int)(temp ^ temp >>> 32);
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
    SerialUtilities.writePaint(paint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    paint = SerialUtilities.readPaint(stream);
  }
}
