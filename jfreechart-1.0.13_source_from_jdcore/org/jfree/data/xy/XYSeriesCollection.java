package org.jfree.data.xy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.HashUtilities;
import org.jfree.data.DomainInfo;
import org.jfree.data.DomainOrder;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.UnknownKeyException;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;










































































public class XYSeriesCollection
  extends AbstractIntervalXYDataset
  implements IntervalXYDataset, DomainInfo, RangeInfo, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -7590013825931496766L;
  private List data;
  private IntervalXYDelegate intervalDelegate;
  
  public XYSeriesCollection()
  {
    this(null);
  }
  




  public XYSeriesCollection(XYSeries series)
  {
    data = new ArrayList();
    intervalDelegate = new IntervalXYDelegate(this, false);
    addChangeListener(intervalDelegate);
    if (series != null) {
      data.add(series);
      series.addChangeListener(this);
    }
  }
  




  public DomainOrder getDomainOrder()
  {
    int seriesCount = getSeriesCount();
    for (int i = 0; i < seriesCount; i++) {
      XYSeries s = getSeries(i);
      if (!s.getAutoSort()) {
        return DomainOrder.NONE;
      }
    }
    return DomainOrder.ASCENDING;
  }
  





  public void addSeries(XYSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    data.add(series);
    series.addChangeListener(this);
    fireDatasetChanged();
  }
  





  public void removeSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds.");
    }
    

    XYSeries ts = (XYSeries)data.get(series);
    ts.removeChangeListener(this);
    data.remove(series);
    fireDatasetChanged();
  }
  





  public void removeSeries(XYSeries series)
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
      XYSeries series = (XYSeries)data.get(i);
      series.removeChangeListener(this);
    }
    

    data.clear();
    fireDatasetChanged();
  }
  




  public int getSeriesCount()
  {
    return data.size();
  }
  




  public List getSeries()
  {
    return Collections.unmodifiableList(data);
  }
  









  public int indexOf(XYSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    return data.indexOf(series);
  }
  









  public XYSeries getSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds");
    }
    return (XYSeries)data.get(series);
  }
  











  public XYSeries getSeries(Comparable key)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    Iterator iterator = data.iterator();
    while (iterator.hasNext()) {
      XYSeries series = (XYSeries)iterator.next();
      if (key.equals(series.getKey())) {
        return series;
      }
    }
    throw new UnknownKeyException("Key not found: " + key);
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
    XYSeries ts = (XYSeries)data.get(series);
    XYDataItem xyItem = ts.getDataItem(item);
    return xyItem.getX();
  }
  







  public Number getStartX(int series, int item)
  {
    return intervalDelegate.getStartX(series, item);
  }
  







  public Number getEndX(int series, int item)
  {
    return intervalDelegate.getEndX(series, item);
  }
  







  public Number getY(int series, int index)
  {
    XYSeries ts = (XYSeries)data.get(series);
    XYDataItem xyItem = ts.getDataItem(index);
    return xyItem.getY();
  }
  







  public Number getStartY(int series, int item)
  {
    return getY(series, item);
  }
  







  public Number getEndY(int series, int item)
  {
    return getY(series, item);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYSeriesCollection)) {
      return false;
    }
    XYSeriesCollection that = (XYSeriesCollection)obj;
    if (!intervalDelegate.equals(intervalDelegate)) {
      return false;
    }
    return ObjectUtilities.equal(data, data);
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    XYSeriesCollection clone = (XYSeriesCollection)super.clone();
    data = ((List)ObjectUtilities.deepClone(data));
    intervalDelegate = ((IntervalXYDelegate)intervalDelegate.clone());
    
    return clone;
  }
  




  public int hashCode()
  {
    int hash = 5;
    hash = HashUtilities.hashCode(hash, intervalDelegate);
    hash = HashUtilities.hashCode(hash, data);
    return hash;
  }
  







  public double getDomainLowerBound(boolean includeInterval)
  {
    if (includeInterval) {
      return intervalDelegate.getDomainLowerBound(includeInterval);
    }
    
    double result = NaN.0D;
    int seriesCount = getSeriesCount();
    for (int s = 0; s < seriesCount; s++) {
      XYSeries series = getSeries(s);
      double lowX = series.getMinX();
      if (Double.isNaN(result)) {
        result = lowX;

      }
      else if (!Double.isNaN(lowX)) {
        result = Math.min(result, lowX);
      }
    }
    
    return result;
  }
  








  public double getDomainUpperBound(boolean includeInterval)
  {
    if (includeInterval) {
      return intervalDelegate.getDomainUpperBound(includeInterval);
    }
    
    double result = NaN.0D;
    int seriesCount = getSeriesCount();
    for (int s = 0; s < seriesCount; s++) {
      XYSeries series = getSeries(s);
      double hiX = series.getMaxX();
      if (Double.isNaN(result)) {
        result = hiX;

      }
      else if (!Double.isNaN(hiX)) {
        result = Math.max(result, hiX);
      }
    }
    
    return result;
  }
  









  public Range getDomainBounds(boolean includeInterval)
  {
    if (includeInterval) {
      return intervalDelegate.getDomainBounds(includeInterval);
    }
    
    double lower = Double.POSITIVE_INFINITY;
    double upper = Double.NEGATIVE_INFINITY;
    int seriesCount = getSeriesCount();
    for (int s = 0; s < seriesCount; s++) {
      XYSeries series = getSeries(s);
      double minX = series.getMinX();
      if (!Double.isNaN(minX)) {
        lower = Math.min(lower, minX);
      }
      double maxX = series.getMaxX();
      if (!Double.isNaN(maxX)) {
        upper = Math.max(upper, maxX);
      }
    }
    if (lower > upper) {
      return null;
    }
    
    return new Range(lower, upper);
  }
  







  public double getIntervalWidth()
  {
    return intervalDelegate.getIntervalWidth();
  }
  





  public void setIntervalWidth(double width)
  {
    if (width < 0.0D) {
      throw new IllegalArgumentException("Negative 'width' argument.");
    }
    intervalDelegate.setFixedIntervalWidth(width);
    fireDatasetChanged();
  }
  




  public double getIntervalPositionFactor()
  {
    return intervalDelegate.getIntervalPositionFactor();
  }
  






  public void setIntervalPositionFactor(double factor)
  {
    intervalDelegate.setIntervalPositionFactor(factor);
    fireDatasetChanged();
  }
  




  public boolean isAutoWidth()
  {
    return intervalDelegate.isAutoWidth();
  }
  





  public void setAutoWidth(boolean b)
  {
    intervalDelegate.setAutoWidth(b);
    fireDatasetChanged();
  }
  







  public Range getRangeBounds(boolean includeInterval)
  {
    double lower = Double.POSITIVE_INFINITY;
    double upper = Double.NEGATIVE_INFINITY;
    int seriesCount = getSeriesCount();
    for (int s = 0; s < seriesCount; s++) {
      XYSeries series = getSeries(s);
      double minY = series.getMinY();
      if (!Double.isNaN(minY)) {
        lower = Math.min(lower, minY);
      }
      double maxY = series.getMaxY();
      if (!Double.isNaN(maxY)) {
        upper = Math.max(upper, maxY);
      }
    }
    if (lower > upper) {
      return null;
    }
    
    return new Range(lower, upper);
  }
  








  public double getRangeLowerBound(boolean includeInterval)
  {
    double result = NaN.0D;
    int seriesCount = getSeriesCount();
    for (int s = 0; s < seriesCount; s++) {
      XYSeries series = getSeries(s);
      double lowY = series.getMinY();
      if (Double.isNaN(result)) {
        result = lowY;

      }
      else if (!Double.isNaN(lowY)) {
        result = Math.min(result, lowY);
      }
    }
    
    return result;
  }
  







  public double getRangeUpperBound(boolean includeInterval)
  {
    double result = NaN.0D;
    int seriesCount = getSeriesCount();
    for (int s = 0; s < seriesCount; s++) {
      XYSeries series = getSeries(s);
      double hiY = series.getMaxY();
      if (Double.isNaN(result)) {
        result = hiY;

      }
      else if (!Double.isNaN(hiY)) {
        result = Math.max(result, hiY);
      }
    }
    
    return result;
  }
}
