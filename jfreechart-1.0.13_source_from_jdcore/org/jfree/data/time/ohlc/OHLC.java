package org.jfree.data.time.ohlc;

import java.io.Serializable;




























































public class OHLC
  implements Serializable
{
  private double open;
  private double close;
  private double high;
  private double low;
  
  public OHLC(double open, double high, double low, double close)
  {
    this.open = open;
    this.close = close;
    this.high = high;
    this.low = low;
  }
  




  public double getOpen()
  {
    return open;
  }
  




  public double getClose()
  {
    return close;
  }
  




  public double getHigh()
  {
    return high;
  }
  




  public double getLow()
  {
    return low;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof OHLC)) {
      return false;
    }
    OHLC that = (OHLC)obj;
    if (open != open) {
      return false;
    }
    if (close != close) {
      return false;
    }
    if (high != high) {
      return false;
    }
    if (low != low) {
      return false;
    }
    return true;
  }
}
