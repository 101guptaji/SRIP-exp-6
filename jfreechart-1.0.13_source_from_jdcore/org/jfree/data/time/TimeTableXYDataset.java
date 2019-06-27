package org.jfree.data.time;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.jfree.data.DefaultKeyedValues2D;
import org.jfree.data.DomainInfo;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.xy.AbstractIntervalXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.util.PublicCloneable;

























































































public class TimeTableXYDataset
  extends AbstractIntervalXYDataset
  implements Cloneable, PublicCloneable, IntervalXYDataset, DomainInfo, TableXYDataset
{
  private DefaultKeyedValues2D values;
  private boolean domainIsPointsInTime;
  private TimePeriodAnchor xPosition;
  private Calendar workingCalendar;
  
  public TimeTableXYDataset()
  {
    this(TimeZone.getDefault(), Locale.getDefault());
  }
  





  public TimeTableXYDataset(TimeZone zone)
  {
    this(zone, Locale.getDefault());
  }
  





  public TimeTableXYDataset(TimeZone zone, Locale locale)
  {
    if (zone == null) {
      throw new IllegalArgumentException("Null 'zone' argument.");
    }
    if (locale == null) {
      throw new IllegalArgumentException("Null 'locale' argument.");
    }
    values = new DefaultKeyedValues2D(true);
    workingCalendar = Calendar.getInstance(zone, locale);
    xPosition = TimePeriodAnchor.START;
  }
  












  public boolean getDomainIsPointsInTime()
  {
    return domainIsPointsInTime;
  }
  








  public void setDomainIsPointsInTime(boolean flag)
  {
    domainIsPointsInTime = flag;
    notifyListeners(new DatasetChangeEvent(this, this));
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
  









  public void add(TimePeriod period, double y, String seriesName)
  {
    add(period, new Double(y), seriesName, true);
  }
  












  public void add(TimePeriod period, Number y, String seriesName, boolean notify)
  {
    values.addValue(y, period, seriesName);
    if (notify) {
      fireDatasetChanged();
    }
  }
  









  public void remove(TimePeriod period, String seriesName)
  {
    remove(period, seriesName, true);
  }
  











  public void remove(TimePeriod period, String seriesName, boolean notify)
  {
    values.removeValue(period, seriesName);
    if (notify) {
      fireDatasetChanged();
    }
  }
  





  public void clear()
  {
    if (values.getRowCount() > 0) {
      values.clear();
      fireDatasetChanged();
    }
  }
  







  public TimePeriod getTimePeriod(int item)
  {
    return (TimePeriod)values.getRowKey(item);
  }
  




  public int getItemCount()
  {
    return values.getRowCount();
  }
  








  public int getItemCount(int series)
  {
    return getItemCount();
  }
  




  public int getSeriesCount()
  {
    return values.getColumnCount();
  }
  






  public Comparable getSeriesKey(int series)
  {
    return values.getColumnKey(series);
  }
  









  public Number getX(int series, int item)
  {
    return new Double(getXValue(series, item));
  }
  







  public double getXValue(int series, int item)
  {
    TimePeriod period = (TimePeriod)values.getRowKey(item);
    return getXValue(period);
  }
  









  public Number getStartX(int series, int item)
  {
    return new Double(getStartXValue(series, item));
  }
  








  public double getStartXValue(int series, int item)
  {
    TimePeriod period = (TimePeriod)values.getRowKey(item);
    return period.getStart().getTime();
  }
  









  public Number getEndX(int series, int item)
  {
    return new Double(getEndXValue(series, item));
  }
  








  public double getEndXValue(int series, int item)
  {
    TimePeriod period = (TimePeriod)values.getRowKey(item);
    return period.getEnd().getTime();
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
  






  private long getXValue(TimePeriod period)
  {
    long result = 0L;
    if (xPosition == TimePeriodAnchor.START) {
      result = period.getStart().getTime();
    }
    else if (xPosition == TimePeriodAnchor.MIDDLE) {
      long t0 = period.getStart().getTime();
      long t1 = period.getEnd().getTime();
      result = t0 + (t1 - t0) / 2L;
    }
    else if (xPosition == TimePeriodAnchor.END) {
      result = period.getEnd().getTime();
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
    List keys = values.getRowKeys();
    if (keys.isEmpty()) {
      return null;
    }
    
    TimePeriod first = (TimePeriod)keys.get(0);
    TimePeriod last = (TimePeriod)keys.get(keys.size() - 1);
    
    if ((!includeInterval) || (domainIsPointsInTime)) {
      return new Range(getXValue(first), getXValue(last));
    }
    
    return new Range(first.getStart().getTime(), last.getEnd().getTime());
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof TimeTableXYDataset)) {
      return false;
    }
    TimeTableXYDataset that = (TimeTableXYDataset)obj;
    if (domainIsPointsInTime != domainIsPointsInTime) {
      return false;
    }
    if (xPosition != xPosition) {
      return false;
    }
    if (!workingCalendar.getTimeZone().equals(workingCalendar.getTimeZone()))
    {

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
    TimeTableXYDataset clone = (TimeTableXYDataset)super.clone();
    values = ((DefaultKeyedValues2D)values.clone());
    workingCalendar = ((Calendar)workingCalendar.clone());
    return clone;
  }
}
