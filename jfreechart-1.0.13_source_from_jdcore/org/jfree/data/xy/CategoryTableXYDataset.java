package org.jfree.data.xy;

import org.jfree.data.DefaultKeyedValues2D;
import org.jfree.data.DomainInfo;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.util.PublicCloneable;






































































public class CategoryTableXYDataset
  extends AbstractIntervalXYDataset
  implements TableXYDataset, IntervalXYDataset, DomainInfo, PublicCloneable
{
  private DefaultKeyedValues2D values;
  private IntervalXYDelegate intervalDelegate;
  
  public CategoryTableXYDataset()
  {
    values = new DefaultKeyedValues2D(true);
    intervalDelegate = new IntervalXYDelegate(this);
    addChangeListener(intervalDelegate);
  }
  







  public void add(double x, double y, String seriesName)
  {
    add(new Double(x), new Double(y), seriesName, true);
  }
  








  public void add(Number x, Number y, String seriesName, boolean notify)
  {
    values.addValue(y, (Comparable)x, seriesName);
    if (notify) {
      fireDatasetChanged();
    }
  }
  





  public void remove(double x, String seriesName)
  {
    remove(new Double(x), seriesName, true);
  }
  






  public void remove(Number x, String seriesName, boolean notify)
  {
    values.removeValue((Comparable)x, seriesName);
    if (notify) {
      fireDatasetChanged();
    }
  }
  





  public int getSeriesCount()
  {
    return values.getColumnCount();
  }
  






  public Comparable getSeriesKey(int series)
  {
    return values.getColumnKey(series);
  }
  




  public int getItemCount()
  {
    return values.getRowCount();
  }
  







  public int getItemCount(int series)
  {
    return getItemCount();
  }
  








  public Number getX(int series, int item)
  {
    return (Number)values.getRowKey(item);
  }
  







  public Number getStartX(int series, int item)
  {
    return intervalDelegate.getStartX(series, item);
  }
  







  public Number getEndX(int series, int item)
  {
    return intervalDelegate.getEndX(series, item);
  }
  







  public Number getY(int series, int item)
  {
    return values.getValue(item, series);
  }
  







  public Number getStartY(int series, int item)
  {
    return getY(series, item);
  }
  







  public Number getEndY(int series, int item)
  {
    return getY(series, item);
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
  






  public boolean equals(Object obj)
  {
    if (!(obj instanceof CategoryTableXYDataset)) {
      return false;
    }
    CategoryTableXYDataset that = (CategoryTableXYDataset)obj;
    if (!intervalDelegate.equals(intervalDelegate)) {
      return false;
    }
    if (!values.equals(values)) {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    CategoryTableXYDataset clone = (CategoryTableXYDataset)super.clone();
    values = ((DefaultKeyedValues2D)values.clone());
    intervalDelegate = new IntervalXYDelegate(clone);
    
    intervalDelegate.setFixedIntervalWidth(getIntervalWidth());
    intervalDelegate.setAutoWidth(isAutoWidth());
    intervalDelegate.setIntervalPositionFactor(getIntervalPositionFactor());
    
    return clone;
  }
}
