package org.jfree.data.xy;

import org.jfree.data.ComparableObjectItem;




















































public class XIntervalDataItem
  extends ComparableObjectItem
{
  public XIntervalDataItem(double x, double xLow, double xHigh, double y)
  {
    super(new Double(x), new YWithXInterval(y, xLow, xHigh));
  }
  




  public Number getX()
  {
    return (Number)getComparable();
  }
  




  public double getYValue()
  {
    YWithXInterval interval = (YWithXInterval)getObject();
    if (interval != null) {
      return interval.getY();
    }
    
    return NaN.0D;
  }
  





  public double getXLowValue()
  {
    YWithXInterval interval = (YWithXInterval)getObject();
    if (interval != null) {
      return interval.getXLow();
    }
    
    return NaN.0D;
  }
  





  public double getXHighValue()
  {
    YWithXInterval interval = (YWithXInterval)getObject();
    if (interval != null) {
      return interval.getXHigh();
    }
    
    return NaN.0D;
  }
}
