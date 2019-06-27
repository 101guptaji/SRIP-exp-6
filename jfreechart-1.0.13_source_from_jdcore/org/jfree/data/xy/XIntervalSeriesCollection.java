package org.jfree.data.xy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;






















































public class XIntervalSeriesCollection
  extends AbstractIntervalXYDataset
  implements IntervalXYDataset, PublicCloneable, Serializable
{
  private List data;
  
  public XIntervalSeriesCollection()
  {
    data = new ArrayList();
  }
  





  public void addSeries(XIntervalSeries series)
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
  









  public XIntervalSeries getSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds");
    }
    return (XIntervalSeries)data.get(series);
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
    XIntervalSeries s = (XIntervalSeries)data.get(series);
    XIntervalDataItem di = (XIntervalDataItem)s.getDataItem(item);
    return di.getX();
  }
  








  public double getStartXValue(int series, int item)
  {
    XIntervalSeries s = (XIntervalSeries)data.get(series);
    return s.getXLowValue(item);
  }
  








  public double getEndXValue(int series, int item)
  {
    XIntervalSeries s = (XIntervalSeries)data.get(series);
    return s.getXHighValue(item);
  }
  








  public double getYValue(int series, int item)
  {
    XIntervalSeries s = (XIntervalSeries)data.get(series);
    return s.getYValue(item);
  }
  







  public Number getY(int series, int item)
  {
    XIntervalSeries s = (XIntervalSeries)data.get(series);
    XIntervalDataItem di = (XIntervalDataItem)s.getDataItem(item);
    return new Double(di.getYValue());
  }
  







  public Number getStartX(int series, int item)
  {
    XIntervalSeries s = (XIntervalSeries)data.get(series);
    XIntervalDataItem di = (XIntervalDataItem)s.getDataItem(item);
    return new Double(di.getXLowValue());
  }
  







  public Number getEndX(int series, int item)
  {
    XIntervalSeries s = (XIntervalSeries)data.get(series);
    XIntervalDataItem di = (XIntervalDataItem)s.getDataItem(item);
    return new Double(di.getXHighValue());
  }
  








  public Number getStartY(int series, int item)
  {
    return getY(series, item);
  }
  








  public Number getEndY(int series, int item)
  {
    return getY(series, item);
  }
  







  public void removeSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds.");
    }
    XIntervalSeries ts = (XIntervalSeries)data.get(series);
    ts.removeChangeListener(this);
    data.remove(series);
    fireDatasetChanged();
  }
  







  public void removeSeries(XIntervalSeries series)
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
      XIntervalSeries series = (XIntervalSeries)data.get(i);
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
    if (!(obj instanceof XIntervalSeriesCollection)) {
      return false;
    }
    XIntervalSeriesCollection that = (XIntervalSeriesCollection)obj;
    return ObjectUtilities.equal(data, data);
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    XIntervalSeriesCollection clone = (XIntervalSeriesCollection)super.clone();
    
    data = ((List)ObjectUtilities.deepClone(data));
    return clone;
  }
}
