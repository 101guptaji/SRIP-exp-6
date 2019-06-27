package org.jfree.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.jfree.chart.HashUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.Size2D;
import org.jfree.ui.TextAnchor;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;














































































public class DialValueIndicator
  extends AbstractDialLayer
  implements DialLayer, Cloneable, PublicCloneable, Serializable
{
  static final long serialVersionUID = 803094354130942585L;
  private int datasetIndex;
  private double angle;
  private double radius;
  private RectangleAnchor frameAnchor;
  private Number templateValue;
  private NumberFormat formatter;
  private Font font;
  private transient Paint paint;
  private transient Paint backgroundPaint;
  private transient Stroke outlineStroke;
  private transient Paint outlinePaint;
  private RectangleInsets insets;
  private RectangleAnchor valueAnchor;
  private TextAnchor textAnchor;
  
  public DialValueIndicator()
  {
    this(0);
  }
  




  public DialValueIndicator(int datasetIndex)
  {
    this.datasetIndex = datasetIndex;
    angle = -90.0D;
    radius = 0.3D;
    frameAnchor = RectangleAnchor.CENTER;
    templateValue = new Double(100.0D);
    formatter = new DecimalFormat("0.0");
    font = new Font("Dialog", 1, 14);
    paint = Color.black;
    backgroundPaint = Color.white;
    outlineStroke = new BasicStroke(1.0F);
    outlinePaint = Color.blue;
    insets = new RectangleInsets(4.0D, 4.0D, 4.0D, 4.0D);
    valueAnchor = RectangleAnchor.RIGHT;
    textAnchor = TextAnchor.CENTER_RIGHT;
  }
  







  public int getDatasetIndex()
  {
    return datasetIndex;
  }
  







  public void setDatasetIndex(int index)
  {
    datasetIndex = index;
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
    this.radius = radius;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public RectangleAnchor getFrameAnchor()
  {
    return frameAnchor;
  }
  







  public void setFrameAnchor(RectangleAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    frameAnchor = anchor;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Number getTemplateValue()
  {
    return templateValue;
  }
  







  public void setTemplateValue(Number value)
  {
    if (value == null) {
      throw new IllegalArgumentException("Null 'value' argument.");
    }
    templateValue = value;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public NumberFormat getNumberFormat()
  {
    return formatter;
  }
  







  public void setNumberFormat(NumberFormat formatter)
  {
    if (formatter == null) {
      throw new IllegalArgumentException("Null 'formatter' argument.");
    }
    this.formatter = formatter;
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
  






  public RectangleInsets getInsets()
  {
    return insets;
  }
  







  public void setInsets(RectangleInsets insets)
  {
    if (insets == null) {
      throw new IllegalArgumentException("Null 'insets' argument.");
    }
    this.insets = insets;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public RectangleAnchor getValueAnchor()
  {
    return valueAnchor;
  }
  







  public void setValueAnchor(RectangleAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    valueAnchor = anchor;
    notifyListeners(new DialLayerChangeEvent(this));
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
    

    FontMetrics fm = g2.getFontMetrics(font);
    String s = formatter.format(templateValue);
    Rectangle2D tb = TextUtilities.getTextBounds(s, g2, fm);
    

    Rectangle2D bounds = RectangleAnchor.createRectangle(new Size2D(tb.getWidth(), tb.getHeight()), pt.getX(), pt.getY(), frameAnchor);
    



    Rectangle2D fb = insets.createOutsetRectangle(bounds);
    

    g2.setPaint(backgroundPaint);
    g2.fill(fb);
    

    g2.setStroke(outlineStroke);
    g2.setPaint(outlinePaint);
    g2.draw(fb);
    


    double value = plot.getValue(datasetIndex);
    String valueStr = formatter.format(value);
    Point2D pt2 = RectangleAnchor.coordinates(bounds, valueAnchor);
    g2.setPaint(paint);
    g2.setFont(font);
    TextUtilities.drawAlignedString(valueStr, g2, (float)pt2.getX(), (float)pt2.getY(), textAnchor);
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DialValueIndicator)) {
      return false;
    }
    DialValueIndicator that = (DialValueIndicator)obj;
    if (datasetIndex != datasetIndex) {
      return false;
    }
    if (angle != angle) {
      return false;
    }
    if (radius != radius) {
      return false;
    }
    if (!frameAnchor.equals(frameAnchor)) {
      return false;
    }
    if (!templateValue.equals(templateValue)) {
      return false;
    }
    if (!font.equals(font)) {
      return false;
    }
    if (!PaintUtilities.equal(paint, paint)) {
      return false;
    }
    if (!PaintUtilities.equal(backgroundPaint, backgroundPaint)) {
      return false;
    }
    if (!outlineStroke.equals(outlineStroke)) {
      return false;
    }
    if (!PaintUtilities.equal(outlinePaint, outlinePaint)) {
      return false;
    }
    if (!insets.equals(insets)) {
      return false;
    }
    if (!valueAnchor.equals(valueAnchor)) {
      return false;
    }
    if (!textAnchor.equals(textAnchor)) {
      return false;
    }
    
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = 193;
    result = 37 * result + HashUtilities.hashCodeForPaint(paint);
    result = 37 * result + HashUtilities.hashCodeForPaint(backgroundPaint);
    
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
