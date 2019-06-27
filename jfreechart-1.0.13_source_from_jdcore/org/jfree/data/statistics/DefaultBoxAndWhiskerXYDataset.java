package org.jfree.data.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.util.ObjectUtilities;




















































































public class DefaultBoxAndWhiskerXYDataset
  extends AbstractXYDataset
  implements BoxAndWhiskerXYDataset, RangeInfo
{
  private Comparable seriesKey;
  private List dates;
  private List items;
  private Number minimumRangeValue;
  private Number maximumRangeValue;
  private Range rangeBounds;
  private double outlierCoefficient = 1.5D;
  






  private double faroutCoefficient = 2.0D;
  







  public DefaultBoxAndWhiskerXYDataset(Comparable seriesKey)
  {
    this.seriesKey = seriesKey;
    dates = new ArrayList();
    items = new ArrayList();
    minimumRangeValue = null;
    maximumRangeValue = null;
    rangeBounds = null;
  }
  











  public double getOutlierCoefficient()
  {
    return outlierCoefficient;
  }
  







  public void setOutlierCoefficient(double outlierCoefficient)
  {
    this.outlierCoefficient = outlierCoefficient;
  }
  








  public double getFaroutCoefficient()
  {
    return faroutCoefficient;
  }
  









  public void setFaroutCoefficient(double faroutCoefficient)
  {
    if (faroutCoefficient > getOutlierCoefficient()) {
      this.faroutCoefficient = faroutCoefficient;
    }
    else {
      throw new IllegalArgumentException("Farout value must be greater than the outlier value, which is currently set at: (" + getOutlierCoefficient() + ")");
    }
  }
  








  public int getSeriesCount()
  {
    return 1;
  }
  






  public int getItemCount(int series)
  {
    return dates.size();
  }
  






  public void add(Date date, BoxAndWhiskerItem item)
  {
    dates.add(date);
    items.add(item);
    if (minimumRangeValue == null) {
      minimumRangeValue = item.getMinRegularValue();

    }
    else if (item.getMinRegularValue().doubleValue() < minimumRangeValue.doubleValue())
    {
      minimumRangeValue = item.getMinRegularValue();
    }
    
    if (maximumRangeValue == null) {
      maximumRangeValue = item.getMaxRegularValue();

    }
    else if (item.getMaxRegularValue().doubleValue() > maximumRangeValue.doubleValue())
    {
      maximumRangeValue = item.getMaxRegularValue();
    }
    
    rangeBounds = new Range(minimumRangeValue.doubleValue(), maximumRangeValue.doubleValue());
    
    fireDatasetChanged();
  }
  






  public Comparable getSeriesKey(int i)
  {
    return seriesKey;
  }
  








  public BoxAndWhiskerItem getItem(int series, int item)
  {
    return (BoxAndWhiskerItem)items.get(item);
  }
  










  public Number getX(int series, int item)
  {
    return new Long(((Date)dates.get(item)).getTime());
  }
  









  public Date getXDate(int series, int item)
  {
    return (Date)dates.get(item);
  }
  










  public Number getY(int series, int item)
  {
    return getMeanValue(series, item);
  }
  







  public Number getMeanValue(int series, int item)
  {
    Number result = null;
    BoxAndWhiskerItem stats = (BoxAndWhiskerItem)items.get(item);
    if (stats != null) {
      result = stats.getMean();
    }
    return result;
  }
  







  public Number getMedianValue(int series, int item)
  {
    Number result = null;
    BoxAndWhiskerItem stats = (BoxAndWhiskerItem)items.get(item);
    if (stats != null) {
      result = stats.getMedian();
    }
    return result;
  }
  







  public Number getQ1Value(int series, int item)
  {
    Number result = null;
    BoxAndWhiskerItem stats = (BoxAndWhiskerItem)items.get(item);
    if (stats != null) {
      result = stats.getQ1();
    }
    return result;
  }
  







  public Number getQ3Value(int series, int item)
  {
    Number result = null;
    BoxAndWhiskerItem stats = (BoxAndWhiskerItem)items.get(item);
    if (stats != null) {
      result = stats.getQ3();
    }
    return result;
  }
  







  public Number getMinRegularValue(int series, int item)
  {
    Number result = null;
    BoxAndWhiskerItem stats = (BoxAndWhiskerItem)items.get(item);
    if (stats != null) {
      result = stats.getMinRegularValue();
    }
    return result;
  }
  







  public Number getMaxRegularValue(int series, int item)
  {
    Number result = null;
    BoxAndWhiskerItem stats = (BoxAndWhiskerItem)items.get(item);
    if (stats != null) {
      result = stats.getMaxRegularValue();
    }
    return result;
  }
  






  public Number getMinOutlier(int series, int item)
  {
    Number result = null;
    BoxAndWhiskerItem stats = (BoxAndWhiskerItem)items.get(item);
    if (stats != null) {
      result = stats.getMinOutlier();
    }
    return result;
  }
  








  public Number getMaxOutlier(int series, int item)
  {
    Number result = null;
    BoxAndWhiskerItem stats = (BoxAndWhiskerItem)items.get(item);
    if (stats != null) {
      result = stats.getMaxOutlier();
    }
    return result;
  }
  







  public List getOutliers(int series, int item)
  {
    List result = null;
    BoxAndWhiskerItem stats = (BoxAndWhiskerItem)items.get(item);
    if (stats != null) {
      result = stats.getOutliers();
    }
    return result;
  }
  







  public double getRangeLowerBound(boolean includeInterval)
  {
    double result = NaN.0D;
    if (minimumRangeValue != null) {
      result = minimumRangeValue.doubleValue();
    }
    return result;
  }
  







  public double getRangeUpperBound(boolean includeInterval)
  {
    double result = NaN.0D;
    if (maximumRangeValue != null) {
      result = maximumRangeValue.doubleValue();
    }
    return result;
  }
  







  public Range getRangeBounds(boolean includeInterval)
  {
    return rangeBounds;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DefaultBoxAndWhiskerXYDataset)) {
      return false;
    }
    DefaultBoxAndWhiskerXYDataset that = (DefaultBoxAndWhiskerXYDataset)obj;
    
    if (!ObjectUtilities.equal(seriesKey, seriesKey)) {
      return false;
    }
    if (!dates.equals(dates)) {
      return false;
    }
    if (!items.equals(items)) {
      return false;
    }
    return true;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultBoxAndWhiskerXYDataset clone = (DefaultBoxAndWhiskerXYDataset)super.clone();
    
    dates = new ArrayList(dates);
    items = new ArrayList(items);
    return clone;
  }
}
