package org.jfree.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;








































































public class StandardDialRange
  extends AbstractDialLayer
  implements DialLayer, Cloneable, PublicCloneable, Serializable
{
  static final long serialVersionUID = 345515648249364904L;
  private int scaleIndex;
  private double lowerBound;
  private double upperBound;
  private transient Paint paint;
  private double innerRadius;
  private double outerRadius;
  
  public StandardDialRange()
  {
    this(0.0D, 100.0D, Color.white);
  }
  






  public StandardDialRange(double lower, double upper, Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    scaleIndex = 0;
    lowerBound = lower;
    upperBound = upper;
    innerRadius = 0.48D;
    outerRadius = 0.52D;
    this.paint = paint;
  }
  






  public int getScaleIndex()
  {
    return scaleIndex;
  }
  







  public void setScaleIndex(int index)
  {
    scaleIndex = index;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public double getLowerBound()
  {
    return lowerBound;
  }
  







  public void setLowerBound(double bound)
  {
    if (bound >= upperBound) {
      throw new IllegalArgumentException("Lower bound must be less than upper bound.");
    }
    
    lowerBound = bound;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public double getUpperBound()
  {
    return upperBound;
  }
  







  public void setUpperBound(double bound)
  {
    if (bound <= lowerBound) {
      throw new IllegalArgumentException("Lower bound must be less than upper bound.");
    }
    
    upperBound = bound;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public void setBounds(double lower, double upper)
  {
    if (lower >= upper) {
      throw new IllegalArgumentException("Lower must be less than upper.");
    }
    
    lowerBound = lower;
    upperBound = upper;
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
  






  public double getInnerRadius()
  {
    return innerRadius;
  }
  







  public void setInnerRadius(double radius)
  {
    innerRadius = radius;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public double getOuterRadius()
  {
    return outerRadius;
  }
  







  public void setOuterRadius(double radius)
  {
    outerRadius = radius;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  





  public boolean isClippedToWindow()
  {
    return true;
  }
  









  public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view)
  {
    Rectangle2D arcRectInner = DialPlot.rectangleByRadius(frame, innerRadius, innerRadius);
    
    Rectangle2D arcRectOuter = DialPlot.rectangleByRadius(frame, outerRadius, outerRadius);
    

    DialScale scale = plot.getScale(scaleIndex);
    if (scale == null) {
      throw new RuntimeException("No scale for scaleIndex = " + scaleIndex);
    }
    
    double angleMin = scale.valueToAngle(lowerBound);
    double angleMax = scale.valueToAngle(upperBound);
    
    Arc2D arcInner = new Arc2D.Double(arcRectInner, angleMin, angleMax - angleMin, 0);
    
    Arc2D arcOuter = new Arc2D.Double(arcRectOuter, angleMax, angleMin - angleMax, 0);
    

    g2.setPaint(paint);
    g2.setStroke(new BasicStroke(2.0F));
    g2.draw(arcInner);
    g2.draw(arcOuter);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StandardDialRange)) {
      return false;
    }
    StandardDialRange that = (StandardDialRange)obj;
    if (scaleIndex != scaleIndex) {
      return false;
    }
    if (lowerBound != lowerBound) {
      return false;
    }
    if (upperBound != upperBound) {
      return false;
    }
    if (!PaintUtilities.equal(paint, paint)) {
      return false;
    }
    if (innerRadius != innerRadius) {
      return false;
    }
    if (outerRadius != outerRadius) {
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
    temp = Double.doubleToLongBits(innerRadius);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(outerRadius);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    result = 37 * result + HashUtilities.hashCodeForPaint(paint);
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
