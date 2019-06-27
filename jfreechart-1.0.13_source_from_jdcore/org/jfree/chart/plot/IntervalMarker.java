package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.Serializable;
import org.jfree.chart.event.MarkerChangeEvent;
import org.jfree.ui.GradientPaintTransformer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.util.ObjectUtilities;




























































public class IntervalMarker
  extends Marker
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -1762344775267627916L;
  private double startValue;
  private double endValue;
  private GradientPaintTransformer gradientPaintTransformer;
  
  public IntervalMarker(double start, double end)
  {
    this(start, end, Color.gray, new BasicStroke(0.5F), Color.gray, new BasicStroke(0.5F), 0.8F);
  }
  










  public IntervalMarker(double start, double end, Paint paint)
  {
    this(start, end, paint, new BasicStroke(0.5F), null, null, 0.8F);
  }
  














  public IntervalMarker(double start, double end, Paint paint, Stroke stroke, Paint outlinePaint, Stroke outlineStroke, float alpha)
  {
    super(paint, stroke, outlinePaint, outlineStroke, alpha);
    startValue = start;
    endValue = end;
    gradientPaintTransformer = null;
    setLabelOffsetType(LengthAdjustmentType.CONTRACT);
  }
  





  public double getStartValue()
  {
    return startValue;
  }
  







  public void setStartValue(double value)
  {
    startValue = value;
    notifyListeners(new MarkerChangeEvent(this));
  }
  




  public double getEndValue()
  {
    return endValue;
  }
  







  public void setEndValue(double value)
  {
    endValue = value;
    notifyListeners(new MarkerChangeEvent(this));
  }
  




  public GradientPaintTransformer getGradientPaintTransformer()
  {
    return gradientPaintTransformer;
  }
  






  public void setGradientPaintTransformer(GradientPaintTransformer transformer)
  {
    gradientPaintTransformer = transformer;
    notifyListeners(new MarkerChangeEvent(this));
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof IntervalMarker)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    IntervalMarker that = (IntervalMarker)obj;
    if (startValue != startValue) {
      return false;
    }
    if (endValue != endValue) {
      return false;
    }
    if (!ObjectUtilities.equal(gradientPaintTransformer, gradientPaintTransformer))
    {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
