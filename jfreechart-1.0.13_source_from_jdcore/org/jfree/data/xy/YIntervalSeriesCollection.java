package org.jfree.data.xy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
























































public class YIntervalSeriesCollection
  extends AbstractIntervalXYDataset
  implements IntervalXYDataset, PublicCloneable, Serializable
{
  private List data;
  
  public YIntervalSeriesCollection()
  {
    data = new ArrayList();
  }
  





  public void addSeries(YIntervalSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    data.add(series);
    series.addChangeListener(this);
    fireDatasetChanged();
  }
  




  public int getSeriesCount()
  {
    return data.size();
  }
  









  public YIntervalSeries getSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds");
    }
    return (YIntervalSeries)data.get(series);
  }
  











  public Comparable getSeriesKey(int series)
  {
    return getSeries(series).getKey();
  }
  










  public int getItemCount(int series)
  {
    return getSeries(series).getItemCount();
  }
  







  public Number getX(int series, int item)
  {
    YIntervalSeries s = (YIntervalSeries)data.get(series);
    return s.getX(item);
  }
  








  public double getYValue(int series, int item)
  {
    YIntervalSeries s = (YIntervalSeries)data.get(series);
    return s.getYValue(item);
  }
  








  public double getStartYValue(int series, int item)
  {
    YIntervalSeries s = (YIntervalSeries)data.get(series);
    return s.getYLowValue(item);
  }
  








  public double getEndYValue(int series, int item)
  {
    YIntervalSeries s = (YIntervalSeries)data.get(series);
    return s.getYHighValue(item);
  }
  







  public Number getY(int series, int item)
  {
    YIntervalSeries s = (YIntervalSeries)data.get(series);
    return new Double(s.getYValue(item));
  }
  








  public Number getStartX(int series, int item)
  {
    return getX(series, item);
  }
  








  public Number getEndX(int series, int item)
  {
    return getX(series, item);
  }
  







  public Number getStartY(int series, int item)
  {
    YIntervalSeries s = (YIntervalSeries)data.get(series);
    return new Double(s.getYLowValue(item));
  }
  







  public Number getEndY(int series, int item)
  {
    YIntervalSeries s = (YIntervalSeries)data.get(series);
    return new Double(s.getYHighValue(item));
  }
  







  public void removeSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds.");
    }
    YIntervalSeries ts = (YIntervalSeries)data.get(series);
    ts.removeChangeListener(this);
    data.remove(series);
    fireDatasetChanged();
  }
  







  public void removeSeries(YIntervalSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    if (data.contains(series)) {
      series.removeChangeListener(this);
      data.remove(series);
      fireDatasetChanged();
    }
  }
  







  public void removeAllSeries()
  {
    for (int i = 0; i < data.size(); i++) {
      YIntervalSeries series = (YIntervalSeries)data.get(i);
      series.removeChangeListener(this);
    }
    data.clear();
    fireDatasetChanged();
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof YIntervalSeriesCollection)) {
      return false;
    }
    YIntervalSeriesCollection that = (YIntervalSeriesCollection)obj;
    return ObjectUtilities.equal(data, data);
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    YIntervalSeriesCollection clone = (YIntervalSeriesCollection)super.clone();
    
    data = ((List)ObjectUtilities.deepClone(data));
    return clone;
  }
}
