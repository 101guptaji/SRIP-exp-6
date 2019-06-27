package org.jfree.data.time.ohlc;

import org.jfree.data.ComparableObjectItem;
import org.jfree.data.time.RegularTimePeriod;






















































public class OHLCItem
  extends ComparableObjectItem
{
  public OHLCItem(RegularTimePeriod period, double open, double high, double low, double close)
  {
    super(period, new OHLC(open, high, low, close));
  }
  




  public RegularTimePeriod getPeriod()
  {
    return (RegularTimePeriod)getComparable();
  }
  




  public double getYValue()
  {
    return getCloseValue();
  }
  




  public double getOpenValue()
  {
    OHLC ohlc = (OHLC)getObject();
    if (ohlc != null) {
      return ohlc.getOpen();
    }
    
    return NaN.0D;
  }
  





  public double getHighValue()
  {
    OHLC ohlc = (OHLC)getObject();
    if (ohlc != null) {
      return ohlc.getHigh();
    }
    
    return NaN.0D;
  }
  





  public double getLowValue()
  {
    OHLC ohlc = (OHLC)getObject();
    if (ohlc != null) {
      return ohlc.getLow();
    }
    
    return NaN.0D;
  }
  





  public double getCloseValue()
  {
    OHLC ohlc = (OHLC)getObject();
    if (ohlc != null) {
      return ohlc.getClose();
    }
    
    return NaN.0D;
  }
}
