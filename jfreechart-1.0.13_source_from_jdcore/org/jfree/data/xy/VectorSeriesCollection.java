package org.jfree.data.xy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;





















































public class VectorSeriesCollection
  extends AbstractXYDataset
  implements VectorXYDataset, PublicCloneable, Serializable
{
  private List data;
  
  public VectorSeriesCollection()
  {
    data = new ArrayList();
  }
  





  public void addSeries(VectorSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    data.add(series);
    series.addChangeListener(this);
    fireDatasetChanged();
  }
  








  public boolean removeSeries(VectorSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    boolean removed = data.remove(series);
    if (removed) {
      series.removeChangeListener(this);
      fireDatasetChanged();
    }
    return removed;
  }
  






  public void removeAllSeries()
  {
    for (int i = 0; i < data.size(); i++) {
      VectorSeries series = (VectorSeries)data.get(i);
      series.removeChangeListener(this);
    }
    

    data.clear();
    fireDatasetChanged();
  }
  





  public int getSeriesCount()
  {
    return data.size();
  }
  









  public VectorSeries getSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds");
    }
    return (VectorSeries)data.get(series);
  }
  











  public Comparable getSeriesKey(int series)
  {
    return getSeries(series).getKey();
  }
  







  public int indexOf(VectorSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    return data.indexOf(series);
  }
  










  public int getItemCount(int series)
  {
    return getSeries(series).getItemCount();
  }
  







  public double getXValue(int series, int item)
  {
    VectorSeries s = (VectorSeries)data.get(series);
    VectorDataItem di = (VectorDataItem)s.getDataItem(item);
    return di.getXValue();
  }
  









  public Number getX(int series, int item)
  {
    return new Double(getXValue(series, item));
  }
  







  public double getYValue(int series, int item)
  {
    VectorSeries s = (VectorSeries)data.get(series);
    VectorDataItem di = (VectorDataItem)s.getDataItem(item);
    return di.getYValue();
  }
  









  public Number getY(int series, int item)
  {
    return new Double(getYValue(series, item));
  }
  







  public Vector getVector(int series, int item)
  {
    VectorSeries s = (VectorSeries)data.get(series);
    VectorDataItem di = (VectorDataItem)s.getDataItem(item);
    return di.getVector();
  }
  







  public double getVectorXValue(int series, int item)
  {
    VectorSeries s = (VectorSeries)data.get(series);
    VectorDataItem di = (VectorDataItem)s.getDataItem(item);
    return di.getVectorX();
  }
  







  public double getVectorYValue(int series, int item)
  {
    VectorSeries s = (VectorSeries)data.get(series);
    VectorDataItem di = (VectorDataItem)s.getDataItem(item);
    return di.getVectorY();
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof VectorSeriesCollection)) {
      return false;
    }
    VectorSeriesCollection that = (VectorSeriesCollection)obj;
    return ObjectUtilities.equal(data, data);
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    VectorSeriesCollection clone = (VectorSeriesCollection)super.clone();
    
    data = ((List)ObjectUtilities.deepClone(data));
    return clone;
  }
}
