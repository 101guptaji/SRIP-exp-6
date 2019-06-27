package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
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
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;










































































































public class XYPointerAnnotation
  extends XYTextAnnotation
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -4031161445009858551L;
  public static final double DEFAULT_TIP_RADIUS = 10.0D;
  public static final double DEFAULT_BASE_RADIUS = 30.0D;
  public static final double DEFAULT_LABEL_OFFSET = 3.0D;
  public static final double DEFAULT_ARROW_LENGTH = 5.0D;
  public static final double DEFAULT_ARROW_WIDTH = 3.0D;
  private double angle;
  private double tipRadius;
  private double baseRadius;
  private double arrowLength;
  private double arrowWidth;
  private transient Stroke arrowStroke;
  private transient Paint arrowPaint;
  private double labelOffset;
  
  public XYPointerAnnotation(String label, double x, double y, double angle)
  {
    super(label, x, y);
    this.angle = angle;
    tipRadius = 10.0D;
    baseRadius = 30.0D;
    arrowLength = 5.0D;
    arrowWidth = 3.0D;
    labelOffset = 3.0D;
    arrowStroke = new BasicStroke(1.0F);
    arrowPaint = Color.black;
  }
  







  public double getAngle()
  {
    return angle;
  }
  






  public void setAngle(double angle)
  {
    this.angle = angle;
  }
  






  public double getTipRadius()
  {
    return tipRadius;
  }
  






  public void setTipRadius(double radius)
  {
    tipRadius = radius;
  }
  






  public double getBaseRadius()
  {
    return baseRadius;
  }
  






  public void setBaseRadius(double radius)
  {
    baseRadius = radius;
  }
  






  public double getLabelOffset()
  {
    return labelOffset;
  }
  







  public void setLabelOffset(double offset)
  {
    labelOffset = offset;
  }
  






  public double getArrowLength()
  {
    return arrowLength;
  }
  






  public void setArrowLength(double length)
  {
    arrowLength = length;
  }
  






  public double getArrowWidth()
  {
    return arrowWidth;
  }
  






  public void setArrowWidth(double width)
  {
    arrowWidth = width;
  }
  






  public Stroke getArrowStroke()
  {
    return arrowStroke;
  }
  






  public void setArrowStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' not permitted.");
    }
    arrowStroke = stroke;
  }
  






  public Paint getArrowPaint()
  {
    return arrowPaint;
  }
  






  public void setArrowPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    arrowPaint = paint;
  }
  














  public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info)
  {
    PlotOrientation orientation = plot.getOrientation();
    RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot.getDomainAxisLocation(), orientation);
    
    RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot.getRangeAxisLocation(), orientation);
    
    double j2DX = domainAxis.valueToJava2D(getX(), dataArea, domainEdge);
    double j2DY = rangeAxis.valueToJava2D(getY(), dataArea, rangeEdge);
    if (orientation == PlotOrientation.HORIZONTAL) {
      double temp = j2DX;
      j2DX = j2DY;
      j2DY = temp;
    }
    double startX = j2DX + Math.cos(angle) * baseRadius;
    double startY = j2DY + Math.sin(angle) * baseRadius;
    
    double endX = j2DX + Math.cos(angle) * tipRadius;
    double endY = j2DY + Math.sin(angle) * tipRadius;
    
    double arrowBaseX = endX + Math.cos(angle) * arrowLength;
    double arrowBaseY = endY + Math.sin(angle) * arrowLength;
    
    double arrowLeftX = arrowBaseX + Math.cos(angle + 1.5707963267948966D) * arrowWidth;
    
    double arrowLeftY = arrowBaseY + Math.sin(angle + 1.5707963267948966D) * arrowWidth;
    

    double arrowRightX = arrowBaseX - Math.cos(angle + 1.5707963267948966D) * arrowWidth;
    
    double arrowRightY = arrowBaseY - Math.sin(angle + 1.5707963267948966D) * arrowWidth;
    

    GeneralPath arrow = new GeneralPath();
    arrow.moveTo((float)endX, (float)endY);
    arrow.lineTo((float)arrowLeftX, (float)arrowLeftY);
    arrow.lineTo((float)arrowRightX, (float)arrowRightY);
    arrow.closePath();
    
    g2.setStroke(arrowStroke);
    g2.setPaint(arrowPaint);
    Line2D line = new Line2D.Double(startX, startY, endX, endY);
    g2.draw(line);
    g2.fill(arrow);
    

    double labelX = j2DX + Math.cos(angle) * (baseRadius + labelOffset);
    
    double labelY = j2DY + Math.sin(angle) * (baseRadius + labelOffset);
    
    g2.setFont(getFont());
    Shape hotspot = TextUtilities.calculateRotatedStringBounds(getText(), g2, (float)labelX, (float)labelY, getTextAnchor(), getRotationAngle(), getRotationAnchor());
    

    if (getBackgroundPaint() != null) {
      g2.setPaint(getBackgroundPaint());
      g2.fill(hotspot);
    }
    g2.setPaint(getPaint());
    TextUtilities.drawRotatedString(getText(), g2, (float)labelX, (float)labelY, getTextAnchor(), getRotationAngle(), getRotationAnchor());
    

    if (isOutlineVisible()) {
      g2.setStroke(getOutlineStroke());
      g2.setPaint(getOutlinePaint());
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
    if (!(obj instanceof XYPointerAnnotation)) {
      return false;
    }
    XYPointerAnnotation that = (XYPointerAnnotation)obj;
    if (angle != angle) {
      return false;
    }
    if (tipRadius != tipRadius) {
      return false;
    }
    if (baseRadius != baseRadius) {
      return false;
    }
    if (arrowLength != arrowLength) {
      return false;
    }
    if (arrowWidth != arrowWidth) {
      return false;
    }
    if (!arrowPaint.equals(arrowPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(arrowStroke, arrowStroke)) {
      return false;
    }
    if (labelOffset != labelOffset) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = super.hashCode();
    long temp = Double.doubleToLongBits(angle);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(tipRadius);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(baseRadius);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(arrowLength);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(arrowWidth);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    result = result * 37 + HashUtilities.hashCodeForPaint(arrowPaint);
    result = result * 37 + arrowStroke.hashCode();
    temp = Double.doubleToLongBits(labelOffset);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    return super.hashCode();
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
    SerialUtilities.writePaint(arrowPaint, stream);
    SerialUtilities.writeStroke(arrowStroke, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    arrowPaint = SerialUtilities.readPaint(stream);
    arrowStroke = SerialUtilities.readStroke(stream);
  }
}
