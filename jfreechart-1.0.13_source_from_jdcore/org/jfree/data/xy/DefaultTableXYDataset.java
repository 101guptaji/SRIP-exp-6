package org.jfree.data.xy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.jfree.data.DomainInfo;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;








































































public class DefaultTableXYDataset
  extends AbstractIntervalXYDataset
  implements TableXYDataset, IntervalXYDataset, DomainInfo, PublicCloneable
{
  private List data = null;
  

  private HashSet xPoints = null;
  

  private boolean propagateEvents = true;
  

  private boolean autoPrune = false;
  

  private IntervalXYDelegate intervalDelegate;
  


  public DefaultTableXYDataset()
  {
    this(false);
  }
  






  public DefaultTableXYDataset(boolean autoPrune)
  {
    this.autoPrune = autoPrune;
    data = new ArrayList();
    xPoints = new HashSet();
    intervalDelegate = new IntervalXYDelegate(this, false);
    addChangeListener(intervalDelegate);
  }
  





  public boolean isAutoPrune()
  {
    return autoPrune;
  }
  






  public void addSeries(XYSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    if (series.getAllowDuplicateXValues()) {
      throw new IllegalArgumentException("Cannot accept XYSeries that allow duplicate values. Use XYSeries(seriesName, <sort>, false) constructor.");
    }
    


    updateXPoints(series);
    data.add(series);
    series.addChangeListener(this);
    fireDatasetChanged();
  }
  





  private void updateXPoints(XYSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' not permitted.");
    }
    HashSet seriesXPoints = new HashSet();
    boolean savedState = propagateEvents;
    propagateEvents = false;
    for (int itemNo = 0; itemNo < series.getItemCount(); itemNo++) {
      Number xValue = series.getX(itemNo);
      seriesXPoints.add(xValue);
      if (!xPoints.contains(xValue)) {
        xPoints.add(xValue);
        int seriesCount = data.size();
        for (int seriesNo = 0; seriesNo < seriesCount; seriesNo++) {
          XYSeries dataSeries = (XYSeries)data.get(seriesNo);
          if (!dataSeries.equals(series)) {
            dataSeries.add(xValue, null);
          }
        }
      }
    }
    Iterator iterator = xPoints.iterator();
    while (iterator.hasNext()) {
      Number xPoint = (Number)iterator.next();
      if (!seriesXPoints.contains(xPoint)) {
        series.add(xPoint, null);
      }
    }
    propagateEvents = savedState;
  }
  


  public void updateXPoints()
  {
    propagateEvents = false;
    for (int s = 0; s < data.size(); s++) {
      updateXPoints((XYSeries)data.get(s));
    }
    if (autoPrune) {
      prune();
    }
    propagateEvents = true;
  }
  




  public int getSeriesCount()
  {
    return data.size();
  }
  




  public int getItemCount()
  {
    if (xPoints == null) {
      return 0;
    }
    
    return xPoints.size();
  }
  







  public XYSeries getSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Index outside valid range.");
    }
    return (XYSeries)data.get(series);
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
    XYSeries s = (XYSeries)data.get(series);
    XYDataItem dataItem = s.getDataItem(item);
    return dataItem.getX();
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
    XYDataItem dataItem = ts.getDataItem(index);
    return dataItem.getY();
  }
  







  public Number getStartY(int series, int item)
  {
    return getY(series, item);
  }
  







  public Number getEndY(int series, int item)
  {
    return getY(series, item);
  }
  






  public void removeAllSeries()
  {
    for (int i = 0; i < data.size(); i++) {
      XYSeries series = (XYSeries)data.get(i);
      series.removeChangeListener(this);
    }
    

    data.clear();
    xPoints.clear();
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
      if (data.size() == 0) {
        xPoints.clear();
      }
      fireDatasetChanged();
    }
  }
  








  public void removeSeries(int series)
  {
    if ((series < 0) || (series > getSeriesCount())) {
      throw new IllegalArgumentException("Index outside valid range.");
    }
    

    XYSeries s = (XYSeries)data.get(series);
    s.removeChangeListener(this);
    data.remove(series);
    if (data.size() == 0) {
      xPoints.clear();
    }
    else if (autoPrune) {
      prune();
    }
    fireDatasetChanged();
  }
  





  public void removeAllValuesForX(Number x)
  {
    if (x == null) {
      throw new IllegalArgumentException("Null 'x' argument.");
    }
    boolean savedState = propagateEvents;
    propagateEvents = false;
    for (int s = 0; s < data.size(); s++) {
      XYSeries series = (XYSeries)data.get(s);
      series.remove(x);
    }
    propagateEvents = savedState;
    xPoints.remove(x);
    fireDatasetChanged();
  }
  







  protected boolean canPrune(Number x)
  {
    for (int s = 0; s < data.size(); s++) {
      XYSeries series = (XYSeries)data.get(s);
      if (series.getY(series.indexOf(x)) != null) {
        return false;
      }
    }
    return true;
  }
  


  public void prune()
  {
    HashSet hs = (HashSet)xPoints.clone();
    Iterator iterator = hs.iterator();
    while (iterator.hasNext()) {
      Number x = (Number)iterator.next();
      if (canPrune(x)) {
        removeAllValuesForX(x);
      }
    }
  }
  






  public void seriesChanged(SeriesChangeEvent event)
  {
    if (propagateEvents) {
      updateXPoints();
      fireDatasetChanged();
    }
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DefaultTableXYDataset)) {
      return false;
    }
    DefaultTableXYDataset that = (DefaultTableXYDataset)obj;
    if (autoPrune != autoPrune) {
      return false;
    }
    if (propagateEvents != propagateEvents) {
      return false;
    }
    if (!intervalDelegate.equals(intervalDelegate)) {
      return false;
    }
    if (!ObjectUtilities.equal(data, data)) {
      return false;
    }
    return true;
  }
  





  public int hashCode()
  {
    int result = data != null ? data.hashCode() : 0;
    result = 29 * result + (xPoints != null ? xPoints.hashCode() : 0);
    
    result = 29 * result + (propagateEvents ? 1 : 0);
    result = 29 * result + (autoPrune ? 1 : 0);
    return result;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultTableXYDataset clone = (DefaultTableXYDataset)super.clone();
    int seriesCount = data.size();
    data = new ArrayList(seriesCount);
    for (int i = 0; i < seriesCount; i++) {
      XYSeries series = (XYSeries)data.get(i);
      data.add(series.clone());
    }
    
    intervalDelegate = new IntervalXYDelegate(clone);
    
    intervalDelegate.setFixedIntervalWidth(getIntervalWidth());
    intervalDelegate.setAutoWidth(isAutoWidth());
    intervalDelegate.setIntervalPositionFactor(getIntervalPositionFactor());
    
    clone.updateXPoints();
    return clone;
  }
  







  public double getDomainLowerBound(boolean includeInterval)
  {
    return intervalDelegate.getDomainLowerBound(includeInterval);
  }
  







  public double getDomainUpperBound(boolean includeInterval)
  {
    return intervalDelegate.getDomainUpperBound(includeInterval);
  }
  







  public Range getDomainBounds(boolean includeInterval)
  {
    if (includeInterval) {
      return intervalDelegate.getDomainBounds(includeInterval);
    }
    
    return DatasetUtilities.iterateDomainBounds(this, includeInterval);
  }
  





  public double getIntervalPositionFactor()
  {
    return intervalDelegate.getIntervalPositionFactor();
  }
  







  public void setIntervalPositionFactor(double d)
  {
    intervalDelegate.setIntervalPositionFactor(d);
    fireDatasetChanged();
  }
  




  public double getIntervalWidth()
  {
    return intervalDelegate.getIntervalWidth();
  }
  





  public void setIntervalWidth(double d)
  {
    intervalDelegate.setFixedIntervalWidth(d);
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
}
