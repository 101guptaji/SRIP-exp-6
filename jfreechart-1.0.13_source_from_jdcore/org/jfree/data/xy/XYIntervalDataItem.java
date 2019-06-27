package org.jfree.data.xy;

import org.jfree.data.ComparableObjectItem;























































public class XYIntervalDataItem
  extends ComparableObjectItem
{
  public XYIntervalDataItem(double x, double xLow, double xHigh, double y, double yLow, double yHigh)
  {
    super(new Double(x), new XYInterval(xLow, xHigh, y, yLow, yHigh));
  }
  




  public Double getX()
  {
    return (Double)getComparable();
  }
  




  public double getYValue()
  {
    XYInterval interval = (XYInterval)getObject();
    if (interval != null) {
      return interval.getY();
    }
    
    return NaN.0D;
  }
  





  public double getXLowValue()
  {
    XYInterval interval = (XYInterval)getObject();
    if (interval != null) {
      return interval.getXLow();
    }
    
    return NaN.0D;
  }
  





  public double getXHighValue()
  {
    XYInterval interval = (XYInterval)getObject();
    if (interval != null) {
      return interval.getXHigh();
    }
    
    return NaN.0D;
  }
  





  public double getYLowValue()
  {
    XYInterval interval = (XYInterval)getObject();
    if (interval != null) {
      return interval.getYLow();
    }
    
    return NaN.0D;
  }
  





  public double getYHighValue()
  {
    XYInterval interval = (XYInterval)getObject();
    if (interval != null) {
      return interval.getYHigh();
    }
    
    return NaN.0D;
  }
}
