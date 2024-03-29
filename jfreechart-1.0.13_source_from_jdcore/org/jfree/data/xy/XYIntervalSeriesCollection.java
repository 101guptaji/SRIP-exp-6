package org.jfree.data.xy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
























































public class XYIntervalSeriesCollection
  extends AbstractIntervalXYDataset
  implements IntervalXYDataset, PublicCloneable, Serializable
{
  private List data;
  
  public XYIntervalSeriesCollection()
  {
    data = new ArrayList();
  }
  





  public void addSeries(XYIntervalSeries series)
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
  









  public XYIntervalSeries getSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds");
    }
    return (XYIntervalSeries)data.get(series);
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
    XYIntervalSeries s = (XYIntervalSeries)data.get(series);
    return s.getX(item);
  }
  








  public double getStartXValue(int series, int item)
  {
    XYIntervalSeries s = (XYIntervalSeries)data.get(series);
    return s.getXLowValue(item);
  }
  








  public double getEndXValue(int series, int item)
  {
    XYIntervalSeries s = (XYIntervalSeries)data.get(series);
    return s.getXHighValue(item);
  }
  








  public double getYValue(int series, int item)
  {
    XYIntervalSeries s = (XYIntervalSeries)data.get(series);
    return s.getYValue(item);
  }
  








  public double getStartYValue(int series, int item)
  {
    XYIntervalSeries s = (XYIntervalSeries)data.get(series);
    return s.getYLowValue(item);
  }
  








  public double getEndYValue(int series, int item)
  {
    XYIntervalSeries s = (XYIntervalSeries)data.get(series);
    return s.getYHighValue(item);
  }
  







  public Number getY(int series, int item)
  {
    return new Double(getYValue(series, item));
  }
  







  public Number getStartX(int series, int item)
  {
    return new Double(getStartXValue(series, item));
  }
  







  public Number getEndX(int series, int item)
  {
    return new Double(getEndXValue(series, item));
  }
  








  public Number getStartY(int series, int item)
  {
    return new Double(getStartYValue(series, item));
  }
  








  public Number getEndY(int series, int item)
  {
    return new Double(getEndYValue(series, item));
  }
  







  public void removeSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds.");
    }
    XYIntervalSeries ts = (XYIntervalSeries)data.get(series);
    ts.removeChangeListener(this);
    data.remove(series);
    fireDatasetChanged();
  }
  







  public void removeSeries(XYIntervalSeries series)
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
      XYIntervalSeries series = (XYIntervalSeries)data.get(i);
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
    if (!(obj instanceof XYIntervalSeriesCollection)) {
      return false;
    }
    XYIntervalSeriesCollection that = (XYIntervalSeriesCollection)obj;
    return ObjectUtilities.equal(data, data);
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    XYIntervalSeriesCollection clone = (XYIntervalSeriesCollection)super.clone();
    
    int seriesCount = getSeriesCount();
    data = new ArrayList(seriesCount);
    for (int i = 0; i < data.size(); i++) {
      data.set(i, getSeries(i).clone());
    }
    return clone;
  }
}
