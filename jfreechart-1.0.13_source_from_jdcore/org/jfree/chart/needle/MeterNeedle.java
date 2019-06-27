package org.jfree.chart.needle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;























































public abstract class MeterNeedle
  implements Serializable
{
  private static final long serialVersionUID = 5203064851510951052L;
  private transient Paint outlinePaint = Color.black;
  

  private transient Stroke outlineStroke = new BasicStroke(2.0F);
  

  private transient Paint fillPaint = null;
  

  private transient Paint highlightPaint = null;
  

  private int size = 5;
  

  private double rotateX = 0.5D;
  

  private double rotateY = 0.5D;
  

  protected static AffineTransform transform = new AffineTransform();
  


  public MeterNeedle()
  {
    this(null, null, null);
  }
  






  public MeterNeedle(Paint outline, Paint fill, Paint highlight)
  {
    fillPaint = fill;
    highlightPaint = highlight;
    outlinePaint = outline;
  }
  




  public Paint getOutlinePaint()
  {
    return outlinePaint;
  }
  




  public void setOutlinePaint(Paint p)
  {
    if (p != null) {
      outlinePaint = p;
    }
  }
  




  public Stroke getOutlineStroke()
  {
    return outlineStroke;
  }
  




  public void setOutlineStroke(Stroke s)
  {
    if (s != null) {
      outlineStroke = s;
    }
  }
  




  public Paint getFillPaint()
  {
    return fillPaint;
  }
  




  public void setFillPaint(Paint p)
  {
    if (p != null) {
      fillPaint = p;
    }
  }
  




  public Paint getHighlightPaint()
  {
    return highlightPaint;
  }
  




  public void setHighlightPaint(Paint p)
  {
    if (p != null) {
      highlightPaint = p;
    }
  }
  




  public double getRotateX()
  {
    return rotateX;
  }
  




  public void setRotateX(double x)
  {
    rotateX = x;
  }
  




  public void setRotateY(double y)
  {
    rotateY = y;
  }
  




  public double getRotateY()
  {
    return rotateY;
  }
  





  public void draw(Graphics2D g2, Rectangle2D plotArea)
  {
    draw(g2, plotArea, 0.0D);
  }
  







  public void draw(Graphics2D g2, Rectangle2D plotArea, double angle)
  {
    Point2D.Double pt = new Point2D.Double();
    pt.setLocation(plotArea.getMinX() + rotateX * plotArea.getWidth(), plotArea.getMinY() + rotateY * plotArea.getHeight());
    


    draw(g2, plotArea, pt, angle);
  }
  










  public void draw(Graphics2D g2, Rectangle2D plotArea, Point2D rotate, double angle)
  {
    Paint savePaint = g2.getColor();
    Stroke saveStroke = g2.getStroke();
    
    drawNeedle(g2, plotArea, rotate, Math.toRadians(angle));
    
    g2.setStroke(saveStroke);
    g2.setPaint(savePaint);
  }
  









  protected abstract void drawNeedle(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D, Point2D paramPoint2D, double paramDouble);
  








  protected void defaultDisplay(Graphics2D g2, Shape shape)
  {
    if (fillPaint != null) {
      g2.setPaint(fillPaint);
      g2.fill(shape);
    }
    
    if (outlinePaint != null) {
      g2.setStroke(outlineStroke);
      g2.setPaint(outlinePaint);
      g2.draw(shape);
    }
  }
  





  public int getSize()
  {
    return size;
  }
  




  public void setSize(int pixels)
  {
    size = pixels;
  }
  




  public AffineTransform getTransform()
  {
    return transform;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MeterNeedle)) {
      return false;
    }
    MeterNeedle that = (MeterNeedle)obj;
    if (!PaintUtilities.equal(outlinePaint, outlinePaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(outlineStroke, outlineStroke)) {
      return false;
    }
    if (!PaintUtilities.equal(fillPaint, fillPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(highlightPaint, highlightPaint)) {
      return false;
    }
    if (size != size) {
      return false;
    }
    if (rotateX != rotateX) {
      return false;
    }
    if (rotateY != rotateY) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = HashUtilities.hashCode(193, fillPaint);
    result = HashUtilities.hashCode(result, highlightPaint);
    result = HashUtilities.hashCode(result, outlinePaint);
    result = HashUtilities.hashCode(result, outlineStroke);
    result = HashUtilities.hashCode(result, rotateX);
    result = HashUtilities.hashCode(result, rotateY);
    result = HashUtilities.hashCode(result, size);
    return result;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeStroke(outlineStroke, stream);
    SerialUtilities.writePaint(outlinePaint, stream);
    SerialUtilities.writePaint(fillPaint, stream);
    SerialUtilities.writePaint(highlightPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    outlineStroke = SerialUtilities.readStroke(stream);
    outlinePaint = SerialUtilities.readPaint(stream);
    fillPaint = SerialUtilities.readPaint(stream);
    highlightPaint = SerialUtilities.readPaint(stream);
  }
}
