package org.jfree.data.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import org.jfree.data.DomainInfo;
import org.jfree.data.DomainOrder;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.xy.AbstractIntervalXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.ObjectUtilities;











































































































public class TimeSeriesCollection
  extends AbstractIntervalXYDataset
  implements XYDataset, IntervalXYDataset, DomainInfo, Serializable
{
  private static final long serialVersionUID = 834149929022371137L;
  private List data;
  private Calendar workingCalendar;
  private TimePeriodAnchor xPosition;
  /**
   * @deprecated
   */
  private boolean domainIsPointsInTime;
  
  public TimeSeriesCollection()
  {
    this(null, TimeZone.getDefault());
  }
  






  public TimeSeriesCollection(TimeZone zone)
  {
    this(null, zone);
  }
  





  public TimeSeriesCollection(TimeSeries series)
  {
    this(series, TimeZone.getDefault());
  }
  









  public TimeSeriesCollection(TimeSeries series, TimeZone zone)
  {
    if (zone == null) {
      zone = TimeZone.getDefault();
    }
    workingCalendar = Calendar.getInstance(zone);
    data = new ArrayList();
    if (series != null) {
      data.add(series);
      series.addChangeListener(this);
    }
    xPosition = TimePeriodAnchor.START;
    domainIsPointsInTime = true;
  }
  








  /**
   * @deprecated
   */
  public boolean getDomainIsPointsInTime()
  {
    return domainIsPointsInTime;
  }
  






  /**
   * @deprecated
   */
  public void setDomainIsPointsInTime(boolean flag)
  {
    domainIsPointsInTime = flag;
    notifyListeners(new DatasetChangeEvent(this, this));
  }
  




  public DomainOrder getDomainOrder()
  {
    return DomainOrder.ASCENDING;
  }
  






  public TimePeriodAnchor getXPosition()
  {
    return xPosition;
  }
  






  public void setXPosition(TimePeriodAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    xPosition = anchor;
    notifyListeners(new DatasetChangeEvent(this, this));
  }
  




  public List getSeries()
  {
    return Collections.unmodifiableList(data);
  }
  




  public int getSeriesCount()
  {
    return data.size();
  }
  









  public int indexOf(TimeSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    return data.indexOf(series);
  }
  






  public TimeSeries getSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("The 'series' argument is out of bounds (" + series + ").");
    }
    
    return (TimeSeries)data.get(series);
  }
  







  public TimeSeries getSeries(Comparable key)
  {
    TimeSeries result = null;
    Iterator iterator = data.iterator();
    while (iterator.hasNext()) {
      TimeSeries series = (TimeSeries)iterator.next();
      Comparable k = series.getKey();
      if ((k != null) && (k.equals(key))) {
        result = series;
      }
    }
    return result;
  }
  








  public Comparable getSeriesKey(int series)
  {
    return getSeries(series).getKey();
  }
  





  public void addSeries(TimeSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    data.add(series);
    series.addChangeListener(this);
    fireDatasetChanged();
  }
  





  public void removeSeries(TimeSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    data.remove(series);
    series.removeChangeListener(this);
    fireDatasetChanged();
  }
  




  public void removeSeries(int index)
  {
    TimeSeries series = getSeries(index);
    if (series != null) {
      removeSeries(series);
    }
  }
  






  public void removeAllSeries()
  {
    for (int i = 0; i < data.size(); i++) {
      TimeSeries series = (TimeSeries)data.get(i);
      series.removeChangeListener(this);
    }
    

    data.clear();
    fireDatasetChanged();
  }
  








  public int getItemCount(int series)
  {
    return getSeries(series).getItemCount();
  }
  







  public double getXValue(int series, int item)
  {
    TimeSeries s = (TimeSeries)data.get(series);
    TimeSeriesDataItem i = s.getDataItem(item);
    RegularTimePeriod period = i.getPeriod();
    return getX(period);
  }
  







  public Number getX(int series, int item)
  {
    TimeSeries ts = (TimeSeries)data.get(series);
    TimeSeriesDataItem dp = ts.getDataItem(item);
    RegularTimePeriod period = dp.getPeriod();
    return new Long(getX(period));
  }
  






  protected synchronized long getX(RegularTimePeriod period)
  {
    long result = 0L;
    if (xPosition == TimePeriodAnchor.START) {
      result = period.getFirstMillisecond(workingCalendar);
    }
    else if (xPosition == TimePeriodAnchor.MIDDLE) {
      result = period.getMiddleMillisecond(workingCalendar);
    }
    else if (xPosition == TimePeriodAnchor.END) {
      result = period.getLastMillisecond(workingCalendar);
    }
    return result;
  }
  







  public synchronized Number getStartX(int series, int item)
  {
    TimeSeries ts = (TimeSeries)data.get(series);
    TimeSeriesDataItem dp = ts.getDataItem(item);
    return new Long(dp.getPeriod().getFirstMillisecond(workingCalendar));
  }
  








  public synchronized Number getEndX(int series, int item)
  {
    TimeSeries ts = (TimeSeries)data.get(series);
    TimeSeriesDataItem dp = ts.getDataItem(item);
    return new Long(dp.getPeriod().getLastMillisecond(workingCalendar));
  }
  








  public Number getY(int series, int item)
  {
    TimeSeries ts = (TimeSeries)data.get(series);
    TimeSeriesDataItem dp = ts.getDataItem(item);
    return dp.getValue();
  }
  







  public Number getStartY(int series, int item)
  {
    return getY(series, item);
  }
  







  public Number getEndY(int series, int item)
  {
    return getY(series, item);
  }
  










  public int[] getSurroundingItems(int series, long milliseconds)
  {
    int[] result = { -1, -1 };
    TimeSeries timeSeries = getSeries(series);
    for (int i = 0; i < timeSeries.getItemCount(); i++) {
      Number x = getX(series, i);
      long m = x.longValue();
      if (m <= milliseconds) {
        result[0] = i;
      }
      if (m >= milliseconds) {
        result[1] = i;
        break;
      }
    }
    return result;
  }
  







  public double getDomainLowerBound(boolean includeInterval)
  {
    double result = NaN.0D;
    Range r = getDomainBounds(includeInterval);
    if (r != null) {
      result = r.getLowerBound();
    }
    return result;
  }
  







  public double getDomainUpperBound(boolean includeInterval)
  {
    double result = NaN.0D;
    Range r = getDomainBounds(includeInterval);
    if (r != null) {
      result = r.getUpperBound();
    }
    return result;
  }
  







  public Range getDomainBounds(boolean includeInterval)
  {
    Range result = null;
    Iterator iterator = data.iterator();
    while (iterator.hasNext()) {
      TimeSeries series = (TimeSeries)iterator.next();
      int count = series.getItemCount();
      if (count > 0) {
        RegularTimePeriod start = series.getTimePeriod(0);
        RegularTimePeriod end = series.getTimePeriod(count - 1);
        Range temp;
        Range temp; if (!includeInterval) {
          temp = new Range(getX(start), getX(end));
        }
        else {
          temp = new Range(start.getFirstMillisecond(workingCalendar), end.getLastMillisecond(workingCalendar));
        }
        

        result = Range.combine(result, temp);
      }
    }
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof TimeSeriesCollection)) {
      return false;
    }
    TimeSeriesCollection that = (TimeSeriesCollection)obj;
    if (xPosition != xPosition) {
      return false;
    }
    if (domainIsPointsInTime != domainIsPointsInTime) {
      return false;
    }
    if (!ObjectUtilities.equal(data, data)) {
      return false;
    }
    return true;
  }
  





  public int hashCode()
  {
    int result = data.hashCode();
    result = 29 * result + (workingCalendar != null ? workingCalendar.hashCode() : 0);
    
    result = 29 * result + (xPosition != null ? xPosition.hashCode() : 0);
    
    result = 29 * result + (domainIsPointsInTime ? 1 : 0);
    return result;
  }
}
