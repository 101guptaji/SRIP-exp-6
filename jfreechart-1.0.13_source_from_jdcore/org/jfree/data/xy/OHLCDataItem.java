package org.jfree.data.xy;

import java.io.Serializable;
import java.util.Date;










































































public class OHLCDataItem
  implements Comparable, Serializable
{
  private static final long serialVersionUID = 7753817154401169901L;
  private Date date;
  private Number open;
  private Number high;
  private Number low;
  private Number close;
  private Number volume;
  
  public OHLCDataItem(Date date, double open, double high, double low, double close, double volume)
  {
    if (date == null) {
      throw new IllegalArgumentException("Null 'date' argument.");
    }
    this.date = date;
    this.open = new Double(open);
    this.high = new Double(high);
    this.low = new Double(low);
    this.close = new Double(close);
    this.volume = new Double(volume);
  }
  




  public Date getDate()
  {
    return date;
  }
  




  public Number getOpen()
  {
    return open;
  }
  




  public Number getHigh()
  {
    return high;
  }
  




  public Number getLow()
  {
    return low;
  }
  




  public Number getClose()
  {
    return close;
  }
  




  public Number getVolume()
  {
    return volume;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof OHLCDataItem)) {
      return false;
    }
    OHLCDataItem that = (OHLCDataItem)obj;
    if (!date.equals(date)) {
      return false;
    }
    if (!high.equals(high)) {
      return false;
    }
    if (!low.equals(low)) {
      return false;
    }
    if (!open.equals(open)) {
      return false;
    }
    if (!close.equals(close)) {
      return false;
    }
    return true;
  }
  









  public int compareTo(Object object)
  {
    if ((object instanceof OHLCDataItem)) {
      OHLCDataItem item = (OHLCDataItem)object;
      return date.compareTo(date);
    }
    
    throw new ClassCastException("OHLCDataItem.compareTo().");
  }
}
