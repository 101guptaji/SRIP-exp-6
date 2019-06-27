package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;






























































public class XYTextAnnotation
  extends AbstractXYAnnotation
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -2946063342782506328L;
  public static final Font DEFAULT_FONT = new Font("SansSerif", 0, 10);
  


  public static final Paint DEFAULT_PAINT = Color.black;
  

  public static final TextAnchor DEFAULT_TEXT_ANCHOR = TextAnchor.CENTER;
  

  public static final TextAnchor DEFAULT_ROTATION_ANCHOR = TextAnchor.CENTER;
  



  public static final double DEFAULT_ROTATION_ANGLE = 0.0D;
  



  private String text;
  



  private Font font;
  



  private transient Paint paint;
  



  private double x;
  



  private double y;
  



  private TextAnchor textAnchor;
  



  private TextAnchor rotationAnchor;
  


  private double rotationAngle;
  


  private transient Paint backgroundPaint;
  


  private boolean outlineVisible;
  


  private transient Paint outlinePaint;
  


  private transient Stroke outlineStroke;
  



  public XYTextAnnotation(String text, double x, double y)
  {
    if (text == null) {
      throw new IllegalArgumentException("Null 'text' argument.");
    }
    this.text = text;
    font = DEFAULT_FONT;
    paint = DEFAULT_PAINT;
    this.x = x;
    this.y = y;
    textAnchor = DEFAULT_TEXT_ANCHOR;
    rotationAnchor = DEFAULT_ROTATION_ANCHOR;
    rotationAngle = 0.0D;
    

    backgroundPaint = null;
    outlineVisible = false;
    outlinePaint = Color.black;
    outlineStroke = new BasicStroke(0.5F);
  }
  






  public String getText()
  {
    return text;
  }
  






  public void setText(String text)
  {
    if (text == null) {
      throw new IllegalArgumentException("Null 'text' argument.");
    }
    this.text = text;
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
  }
  






  public TextAnchor getTextAnchor()
  {
    return textAnchor;
  }
  







  public void setTextAnchor(TextAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    textAnchor = anchor;
  }
  






  public TextAnchor getRotationAnchor()
  {
    return rotationAnchor;
  }
  






  public void setRotationAnchor(TextAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    rotationAnchor = anchor;
  }
  






  public double getRotationAngle()
  {
    return rotationAngle;
  }
  






  public void setRotationAngle(double angle)
  {
    rotationAngle = angle;
  }
  







  public double getX()
  {
    return x;
  }
  







  public void setX(double x)
  {
    this.x = x;
  }
  







  public double getY()
  {
    return y;
  }
  







  public void setY(double y)
  {
    this.y = y;
  }
  








  public Paint getBackgroundPaint()
  {
    return backgroundPaint;
  }
  








  public void setBackgroundPaint(Paint paint)
  {
    backgroundPaint = paint;
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
  }
  






  public boolean isOutlineVisible()
  {
    return outlineVisible;
  }
  






  public void setOutlineVisible(boolean visible)
  {
    outlineVisible = visible;
  }
  















  public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info)
  {
    PlotOrientation orientation = plot.getOrientation();
    RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot.getDomainAxisLocation(), orientation);
    
    RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot.getRangeAxisLocation(), orientation);
    

    float anchorX = (float)domainAxis.valueToJava2D(x, dataArea, domainEdge);
    
    float anchorY = (float)rangeAxis.valueToJava2D(y, dataArea, rangeEdge);
    

    if (orientation == PlotOrientation.HORIZONTAL) {
      float tempAnchor = anchorX;
      anchorX = anchorY;
      anchorY = tempAnchor;
    }
    
    g2.setFont(getFont());
    Shape hotspot = TextUtilities.calculateRotatedStringBounds(getText(), g2, anchorX, anchorY, getTextAnchor(), getRotationAngle(), getRotationAnchor());
    

    if (backgroundPaint != null) {
      g2.setPaint(backgroundPaint);
      g2.fill(hotspot);
    }
    g2.setPaint(getPaint());
    TextUtilities.drawRotatedString(getText(), g2, anchorX, anchorY, getTextAnchor(), getRotationAngle(), getRotationAnchor());
    
    if (outlineVisible) {
      g2.setStroke(outlineStroke);
      g2.setPaint(outlinePaint);
      g2.draw(hotspot);
    }
    
    String toolTip = getToolTipText();
    String url = getURL();
    if ((toolTip != null) || (url != null)) {
      addEntity(info, hotspot, rendererIndex, toolTip, url);
    }
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYTextAnnotation)) {
      return false;
    }
    XYTextAnnotation that = (XYTextAnnotation)obj;
    if (!text.equals(text)) {
      return false;
    }
    if (x != x) {
      return false;
    }
    if (y != y) {
      return false;
    }
    if (!font.equals(font)) {
      return false;
    }
    if (!PaintUtilities.equal(paint, paint)) {
      return false;
    }
    if (!rotationAnchor.equals(rotationAnchor)) {
      return false;
    }
    if (rotationAngle != rotationAngle) {
      return false;
    }
    if (!textAnchor.equals(textAnchor)) {
      return false;
    }
    if (outlineVisible != outlineVisible) {
      return false;
    }
    if (!PaintUtilities.equal(backgroundPaint, backgroundPaint)) {
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
    result = 37 * text.hashCode();
    result = 37 * font.hashCode();
    result = 37 * result + HashUtilities.hashCodeForPaint(paint);
    long temp = Double.doubleToLongBits(x);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(y);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    result = 37 * result + textAnchor.hashCode();
    result = 37 * result + rotationAnchor.hashCode();
    temp = Double.doubleToLongBits(rotationAngle);
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
    SerialUtilities.writePaint(backgroundPaint, stream);
    SerialUtilities.writePaint(outlinePaint, stream);
    SerialUtilities.writeStroke(outlineStroke, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    paint = SerialUtilities.readPaint(stream);
    backgroundPaint = SerialUtilities.readPaint(stream);
    outlinePaint = SerialUtilities.readPaint(stream);
    outlineStroke = SerialUtilities.readStroke(stream);
  }
}
