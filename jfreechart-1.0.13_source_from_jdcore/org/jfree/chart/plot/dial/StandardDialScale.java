package org.jfree.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;




































































































































public class StandardDialScale
  extends AbstractDialLayer
  implements DialScale, Cloneable, PublicCloneable, Serializable
{
  static final long serialVersionUID = 3715644629665918516L;
  private double lowerBound;
  private double upperBound;
  private double startAngle;
  private double extent;
  private double tickRadius;
  private double majorTickIncrement;
  private double majorTickLength;
  private transient Paint majorTickPaint;
  private transient Stroke majorTickStroke;
  private int minorTickCount;
  private double minorTickLength;
  private transient Paint minorTickPaint;
  private transient Stroke minorTickStroke;
  private double tickLabelOffset;
  private Font tickLabelFont;
  private boolean tickLabelsVisible;
  private NumberFormat tickLabelFormatter;
  private boolean firstTickLabelVisible;
  private transient Paint tickLabelPaint;
  
  public StandardDialScale()
  {
    this(0.0D, 100.0D, 175.0D, -170.0D, 10.0D, 4);
  }
  













  public StandardDialScale(double lowerBound, double upperBound, double startAngle, double extent, double majorTickIncrement, int minorTickCount)
  {
    this.startAngle = startAngle;
    this.extent = extent;
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    tickRadius = 0.7D;
    tickLabelsVisible = true;
    tickLabelFormatter = new DecimalFormat("0.0");
    firstTickLabelVisible = true;
    tickLabelFont = new Font("Dialog", 1, 16);
    tickLabelPaint = Color.blue;
    tickLabelOffset = 0.1D;
    this.majorTickIncrement = majorTickIncrement;
    majorTickLength = 0.04D;
    majorTickPaint = Color.black;
    majorTickStroke = new BasicStroke(3.0F);
    this.minorTickCount = minorTickCount;
    minorTickLength = 0.02D;
    minorTickPaint = Color.black;
    minorTickStroke = new BasicStroke(1.0F);
  }
  








  public double getLowerBound()
  {
    return lowerBound;
  }
  









  public void setLowerBound(double lower)
  {
    lowerBound = lower;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  








  public double getUpperBound()
  {
    return upperBound;
  }
  









  public void setUpperBound(double upper)
  {
    upperBound = upper;
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
  







  public double getTickRadius()
  {
    return tickRadius;
  }
  







  public void setTickRadius(double radius)
  {
    if (radius <= 0.0D) {
      throw new IllegalArgumentException("The 'radius' must be positive.");
    }
    
    tickRadius = radius;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public double getMajorTickIncrement()
  {
    return majorTickIncrement;
  }
  







  public void setMajorTickIncrement(double increment)
  {
    if (increment <= 0.0D) {
      throw new IllegalArgumentException("The 'increment' must be positive.");
    }
    
    majorTickIncrement = increment;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  








  public double getMajorTickLength()
  {
    return majorTickLength;
  }
  







  public void setMajorTickLength(double length)
  {
    if (length < 0.0D) {
      throw new IllegalArgumentException("Negative 'length' argument.");
    }
    majorTickLength = length;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Paint getMajorTickPaint()
  {
    return majorTickPaint;
  }
  







  public void setMajorTickPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    majorTickPaint = paint;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Stroke getMajorTickStroke()
  {
    return majorTickStroke;
  }
  







  public void setMajorTickStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    majorTickStroke = stroke;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public int getMinorTickCount()
  {
    return minorTickCount;
  }
  







  public void setMinorTickCount(int count)
  {
    if (count < 0) {
      throw new IllegalArgumentException("The 'count' cannot be negative.");
    }
    
    minorTickCount = count;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  








  public double getMinorTickLength()
  {
    return minorTickLength;
  }
  







  public void setMinorTickLength(double length)
  {
    if (length < 0.0D) {
      throw new IllegalArgumentException("Negative 'length' argument.");
    }
    minorTickLength = length;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Paint getMinorTickPaint()
  {
    return minorTickPaint;
  }
  







  public void setMinorTickPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    minorTickPaint = paint;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  








  public Stroke getMinorTickStroke()
  {
    return minorTickStroke;
  }
  









  public void setMinorTickStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    minorTickStroke = stroke;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public double getTickLabelOffset()
  {
    return tickLabelOffset;
  }
  







  public void setTickLabelOffset(double offset)
  {
    tickLabelOffset = offset;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Font getTickLabelFont()
  {
    return tickLabelFont;
  }
  







  public void setTickLabelFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    tickLabelFont = font;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public Paint getTickLabelPaint()
  {
    return tickLabelPaint;
  }
  





  public void setTickLabelPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    tickLabelPaint = paint;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  







  public boolean getTickLabelsVisible()
  {
    return tickLabelsVisible;
  }
  








  public void setTickLabelsVisible(boolean visible)
  {
    tickLabelsVisible = visible;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  







  public NumberFormat getTickLabelFormatter()
  {
    return tickLabelFormatter;
  }
  








  public void setTickLabelFormatter(NumberFormat formatter)
  {
    if (formatter == null) {
      throw new IllegalArgumentException("Null 'formatter' argument.");
    }
    tickLabelFormatter = formatter;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  







  public boolean getFirstTickLabelVisible()
  {
    return firstTickLabelVisible;
  }
  








  public void setFirstTickLabelVisible(boolean visible)
  {
    firstTickLabelVisible = visible;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  





  public boolean isClippedToWindow()
  {
    return true;
  }
  











  public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view)
  {
    Rectangle2D arcRect = DialPlot.rectangleByRadius(frame, tickRadius, tickRadius);
    
    Rectangle2D arcRectMajor = DialPlot.rectangleByRadius(frame, tickRadius - majorTickLength, tickRadius - majorTickLength);
    

    Rectangle2D arcRectMinor = arcRect;
    if ((minorTickCount > 0) && (minorTickLength > 0.0D)) {
      arcRectMinor = DialPlot.rectangleByRadius(frame, tickRadius - minorTickLength, tickRadius - minorTickLength);
    }
    

    Rectangle2D arcRectForLabels = DialPlot.rectangleByRadius(frame, tickRadius - tickLabelOffset, tickRadius - tickLabelOffset);
    


    boolean firstLabel = true;
    
    Arc2D arc = new Arc2D.Double();
    Line2D workingLine = new Line2D.Double();
    for (double v = lowerBound; v <= upperBound; 
        v += majorTickIncrement) {
      arc.setArc(arcRect, startAngle, valueToAngle(v) - startAngle, 0);
      
      Point2D pt0 = arc.getEndPoint();
      arc.setArc(arcRectMajor, startAngle, valueToAngle(v) - startAngle, 0);
      
      Point2D pt1 = arc.getEndPoint();
      g2.setPaint(majorTickPaint);
      g2.setStroke(majorTickStroke);
      workingLine.setLine(pt0, pt1);
      g2.draw(workingLine);
      arc.setArc(arcRectForLabels, startAngle, valueToAngle(v) - startAngle, 0);
      
      Point2D pt2 = arc.getEndPoint();
      
      if ((tickLabelsVisible) && (
        (!firstLabel) || (firstTickLabelVisible))) {
        g2.setFont(tickLabelFont);
        g2.setPaint(tickLabelPaint);
        TextUtilities.drawAlignedString(tickLabelFormatter.format(v), g2, (float)pt2.getX(), (float)pt2.getY(), TextAnchor.CENTER);
      }
      



      firstLabel = false;
      

      if ((minorTickCount > 0) && (minorTickLength > 0.0D)) {
        double minorTickIncrement = majorTickIncrement / (minorTickCount + 1);
        
        for (int i = 0; i < minorTickCount; i++) {
          double vv = v + (i + 1) * minorTickIncrement;
          if (vv >= upperBound) {
            break;
          }
          double angle = valueToAngle(vv);
          
          arc.setArc(arcRect, startAngle, angle - startAngle, 0);
          
          pt0 = arc.getEndPoint();
          arc.setArc(arcRectMinor, startAngle, angle - startAngle, 0);
          
          Point2D pt3 = arc.getEndPoint();
          g2.setStroke(minorTickStroke);
          g2.setPaint(minorTickPaint);
          workingLine.setLine(pt0, pt3);
          g2.draw(workingLine);
        }
      }
    }
  }
  










  public double valueToAngle(double value)
  {
    double range = upperBound - lowerBound;
    double unit = extent / range;
    return startAngle + unit * (value - lowerBound);
  }
  








  public double angleToValue(double angle)
  {
    return NaN.0D;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StandardDialScale)) {
      return false;
    }
    StandardDialScale that = (StandardDialScale)obj;
    if (lowerBound != lowerBound) {
      return false;
    }
    if (upperBound != upperBound) {
      return false;
    }
    if (startAngle != startAngle) {
      return false;
    }
    if (extent != extent) {
      return false;
    }
    if (tickRadius != tickRadius) {
      return false;
    }
    if (majorTickIncrement != majorTickIncrement) {
      return false;
    }
    if (majorTickLength != majorTickLength) {
      return false;
    }
    if (!PaintUtilities.equal(majorTickPaint, majorTickPaint)) {
      return false;
    }
    if (!majorTickStroke.equals(majorTickStroke)) {
      return false;
    }
    if (minorTickCount != minorTickCount) {
      return false;
    }
    if (minorTickLength != minorTickLength) {
      return false;
    }
    if (!PaintUtilities.equal(minorTickPaint, minorTickPaint)) {
      return false;
    }
    if (!minorTickStroke.equals(minorTickStroke)) {
      return false;
    }
    if (tickLabelsVisible != tickLabelsVisible) {
      return false;
    }
    if (tickLabelOffset != tickLabelOffset) {
      return false;
    }
    if (!tickLabelFont.equals(tickLabelFont)) {
      return false;
    }
    if (!PaintUtilities.equal(tickLabelPaint, tickLabelPaint)) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = 193;
    
    long temp = Double.doubleToLongBits(lowerBound);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    
    temp = Double.doubleToLongBits(upperBound);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    
    temp = Double.doubleToLongBits(startAngle);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    
    temp = Double.doubleToLongBits(extent);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    
    temp = Double.doubleToLongBits(tickRadius);
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
    SerialUtilities.writePaint(majorTickPaint, stream);
    SerialUtilities.writeStroke(majorTickStroke, stream);
    SerialUtilities.writePaint(minorTickPaint, stream);
    SerialUtilities.writeStroke(minorTickStroke, stream);
    SerialUtilities.writePaint(tickLabelPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    majorTickPaint = SerialUtilities.readPaint(stream);
    majorTickStroke = SerialUtilities.readStroke(stream);
    minorTickPaint = SerialUtilities.readPaint(stream);
    minorTickStroke = SerialUtilities.readStroke(stream);
    tickLabelPaint = SerialUtilities.readPaint(stream);
  }
}
