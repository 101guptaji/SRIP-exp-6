package org.jfree.data.xy;

import java.util.Arrays;
import java.util.Date;
import org.jfree.util.PublicCloneable;




















































































public class DefaultHighLowDataset
  extends AbstractXYDataset
  implements OHLCDataset, PublicCloneable
{
  private Comparable seriesKey;
  private Date[] date;
  private Number[] high;
  private Number[] low;
  private Number[] open;
  private Number[] close;
  private Number[] volume;
  
  public DefaultHighLowDataset(Comparable seriesKey, Date[] date, double[] high, double[] low, double[] open, double[] close, double[] volume)
  {
    if (seriesKey == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    if (date == null) {
      throw new IllegalArgumentException("Null 'date' argument.");
    }
    this.seriesKey = seriesKey;
    this.date = date;
    this.high = createNumberArray(high);
    this.low = createNumberArray(low);
    this.open = createNumberArray(open);
    this.close = createNumberArray(close);
    this.volume = createNumberArray(volume);
  }
  








  public Comparable getSeriesKey(int series)
  {
    return seriesKey;
  }
  













  public Number getX(int series, int item)
  {
    return new Long(date[item].getTime());
  }
  











  public Date getXDate(int series, int item)
  {
    return date[item];
  }
  












  public Number getY(int series, int item)
  {
    return getClose(series, item);
  }
  









  public Number getHigh(int series, int item)
  {
    return high[item];
  }
  










  public double getHighValue(int series, int item)
  {
    double result = NaN.0D;
    Number high = getHigh(series, item);
    if (high != null) {
      result = high.doubleValue();
    }
    return result;
  }
  









  public Number getLow(int series, int item)
  {
    return low[item];
  }
  










  public double getLowValue(int series, int item)
  {
    double result = NaN.0D;
    Number low = getLow(series, item);
    if (low != null) {
      result = low.doubleValue();
    }
    return result;
  }
  









  public Number getOpen(int series, int item)
  {
    return open[item];
  }
  










  public double getOpenValue(int series, int item)
  {
    double result = NaN.0D;
    Number open = getOpen(series, item);
    if (open != null) {
      result = open.doubleValue();
    }
    return result;
  }
  









  public Number getClose(int series, int item)
  {
    return close[item];
  }
  










  public double getCloseValue(int series, int item)
  {
    double result = NaN.0D;
    Number close = getClose(series, item);
    if (close != null) {
      result = close.doubleValue();
    }
    return result;
  }
  









  public Number getVolume(int series, int item)
  {
    return volume[item];
  }
  










  public double getVolumeValue(int series, int item)
  {
    double result = NaN.0D;
    Number volume = getVolume(series, item);
    if (volume != null) {
      result = volume.doubleValue();
    }
    return result;
  }
  






  public int getSeriesCount()
  {
    return 1;
  }
  






  public int getItemCount(int series)
  {
    return date.length;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DefaultHighLowDataset)) {
      return false;
    }
    DefaultHighLowDataset that = (DefaultHighLowDataset)obj;
    if (!seriesKey.equals(seriesKey)) {
      return false;
    }
    if (!Arrays.equals(date, date)) {
      return false;
    }
    if (!Arrays.equals(open, open)) {
      return false;
    }
    if (!Arrays.equals(high, high)) {
      return false;
    }
    if (!Arrays.equals(low, low)) {
      return false;
    }
    if (!Arrays.equals(close, close)) {
      return false;
    }
    if (!Arrays.equals(volume, volume)) {
      return false;
    }
    return true;
  }
  







  public static Number[] createNumberArray(double[] data)
  {
    Number[] result = new Number[data.length];
    for (int i = 0; i < data.length; i++) {
      result[i] = new Double(data[i]);
    }
    return result;
  }
}
