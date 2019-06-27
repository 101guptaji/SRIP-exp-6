package org.jfree.data.general;

import org.jfree.data.xy.AbstractIntervalXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;




























































/**
 * @deprecated
 */
public class SubSeriesDataset
  extends AbstractIntervalXYDataset
  implements OHLCDataset, IntervalXYDataset, CombinationDataset
{
  private SeriesDataset parent = null;
  



  private int[] map;
  




  public SubSeriesDataset(SeriesDataset parent, int[] map)
  {
    this.parent = parent;
    this.map = map;
  }
  






  public SubSeriesDataset(SeriesDataset parent, int series)
  {
    this(parent, new int[] { series });
  }
  














  public Number getHigh(int series, int item)
  {
    return ((OHLCDataset)parent).getHigh(map[series], item);
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
    return ((OHLCDataset)parent).getLow(map[series], item);
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
    return ((OHLCDataset)parent).getOpen(map[series], item);
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
    return ((OHLCDataset)parent).getClose(map[series], item);
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
    return ((OHLCDataset)parent).getVolume(map[series], item);
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
  














  public Number getX(int series, int item)
  {
    return ((XYDataset)parent).getX(map[series], item);
  }
  










  public Number getY(int series, int item)
  {
    return ((XYDataset)parent).getY(map[series], item);
  }
  









  public int getItemCount(int series)
  {
    return ((XYDataset)parent).getItemCount(map[series]);
  }
  








  public int getSeriesCount()
  {
    return map.length;
  }
  






  public Comparable getSeriesKey(int series)
  {
    return parent.getSeriesKey(map[series]);
  }
  











  public Number getStartX(int series, int item)
  {
    if ((parent instanceof IntervalXYDataset)) {
      return ((IntervalXYDataset)parent).getStartX(map[series], item);
    }
    


    return getX(series, item);
  }
  








  public Number getEndX(int series, int item)
  {
    if ((parent instanceof IntervalXYDataset)) {
      return ((IntervalXYDataset)parent).getEndX(map[series], item);
    }
    


    return getX(series, item);
  }
  








  public Number getStartY(int series, int item)
  {
    if ((parent instanceof IntervalXYDataset)) {
      return ((IntervalXYDataset)parent).getStartY(map[series], item);
    }
    


    return getY(series, item);
  }
  








  public Number getEndY(int series, int item)
  {
    if ((parent instanceof IntervalXYDataset)) {
      return ((IntervalXYDataset)parent).getEndY(map[series], item);
    }
    


    return getY(series, item);
  }
  









  public SeriesDataset getParent()
  {
    return parent;
  }
  




  public int[] getMap()
  {
    return (int[])map.clone();
  }
}
