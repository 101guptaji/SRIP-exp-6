package org.jfree.data.time;

import java.util.Calendar;
import java.util.TimeZone;
import org.jfree.data.DomainInfo;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.xy.AbstractIntervalXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
























































































public class DynamicTimeSeriesCollection
  extends AbstractIntervalXYDataset
  implements IntervalXYDataset, DomainInfo, RangeInfo
{
  public static final int START = 0;
  public static final int MIDDLE = 1;
  public static final int END = 2;
  private int maximumItemCount = 2000;
  

  protected int historyCount;
  

  private Comparable[] seriesKeys;
  

  private Class timePeriodClass = Minute.class;
  protected RegularTimePeriod[] pointsInTime;
  private int seriesCount;
  protected ValueSequence[] valueHistory;
  protected Calendar workingCalendar;
  private int position;
  private boolean domainIsPointsInTime;
  private int oldestAt;
  private int newestAt;
  private long deltaTime;
  private Long domainStart;
  private Long domainEnd;
  private Range domainRange;
  
  protected class ValueSequence
  {
    float[] dataPoints;
    
    public ValueSequence()
    {
      this(maximumItemCount);
    }
    




    public ValueSequence(int length)
    {
      dataPoints = new float[length];
      for (int i = 0; i < length; i++) {
        dataPoints[i] = 0.0F;
      }
    }
    





    public void enterData(int index, float value)
    {
      dataPoints[index] = value;
    }
    






    public float getData(int index)
    {
      return dataPoints[index];
    }
  }
  











































  private Float minValue = new Float(0.0F);
  

  private Float maxValue = null;
  




  private Range valueRange;
  




  public DynamicTimeSeriesCollection(int nSeries, int nMoments)
  {
    this(nSeries, nMoments, new Millisecond(), TimeZone.getDefault());
    newestAt = (nMoments - 1);
  }
  








  public DynamicTimeSeriesCollection(int nSeries, int nMoments, TimeZone zone)
  {
    this(nSeries, nMoments, new Millisecond(), zone);
    newestAt = (nMoments - 1);
  }
  








  public DynamicTimeSeriesCollection(int nSeries, int nMoments, RegularTimePeriod timeSample)
  {
    this(nSeries, nMoments, timeSample, TimeZone.getDefault());
  }
  












  public DynamicTimeSeriesCollection(int nSeries, int nMoments, RegularTimePeriod timeSample, TimeZone zone)
  {
    maximumItemCount = nMoments;
    historyCount = nMoments;
    seriesKeys = new Comparable[nSeries];
    
    for (int i = 0; i < nSeries; i++) {
      seriesKeys[i] = "";
    }
    newestAt = (nMoments - 1);
    valueHistory = new ValueSequence[nSeries];
    timePeriodClass = timeSample.getClass();
    

    if (timePeriodClass == Second.class) {
      pointsInTime = new Second[nMoments];
    }
    else if (timePeriodClass == Minute.class) {
      pointsInTime = new Minute[nMoments];
    }
    else if (timePeriodClass == Hour.class) {
      pointsInTime = new Hour[nMoments];
    }
    
    workingCalendar = Calendar.getInstance(zone);
    position = 0;
    domainIsPointsInTime = true;
  }
  











  public synchronized long setTimeBase(RegularTimePeriod start)
  {
    if (pointsInTime[0] == null) {
      pointsInTime[0] = start;
      for (int i = 1; i < historyCount; i++) {
        pointsInTime[i] = pointsInTime[(i - 1)].next();
      }
    }
    long oldestL = pointsInTime[0].getFirstMillisecond(workingCalendar);
    

    long nextL = pointsInTime[1].getFirstMillisecond(workingCalendar);
    

    deltaTime = (nextL - oldestL);
    oldestAt = 0;
    newestAt = (historyCount - 1);
    findDomainLimits();
    return deltaTime;
  }
  





  protected void findDomainLimits()
  {
    long startL = getOldestTime().getFirstMillisecond(workingCalendar);
    long endL;
    long endL; if (domainIsPointsInTime) {
      endL = getNewestTime().getFirstMillisecond(workingCalendar);
    }
    else {
      endL = getNewestTime().getLastMillisecond(workingCalendar);
    }
    domainStart = new Long(startL);
    domainEnd = new Long(endL);
    domainRange = new Range(startL, endL);
  }
  





  public int getPosition()
  {
    return position;
  }
  




  public void setPosition(int position)
  {
    this.position = position;
  }
  












  public void addSeries(float[] values, int seriesNumber, Comparable seriesKey)
  {
    invalidateRangeInfo();
    
    if (values == null) {
      throw new IllegalArgumentException("TimeSeriesDataset.addSeries(): cannot add null array of values.");
    }
    
    if (seriesNumber >= valueHistory.length) {
      throw new IllegalArgumentException("TimeSeriesDataset.addSeries(): cannot add more series than specified in c'tor");
    }
    
    if (valueHistory[seriesNumber] == null) {
      valueHistory[seriesNumber] = new ValueSequence(historyCount);
      
      seriesCount += 1;
    }
    


    int srcLength = values.length;
    int copyLength = historyCount;
    boolean fillNeeded = false;
    if (srcLength < historyCount) {
      fillNeeded = true;
      copyLength = srcLength;
    }
    
    for (int i = 0; i < copyLength; i++)
    {
      valueHistory[seriesNumber].enterData(i, values[i]);
    }
    if (fillNeeded) {
      for (i = copyLength; i < historyCount; i++) {
        valueHistory[seriesNumber].enterData(i, 0.0F);
      }
    }
    
    if (seriesKey != null) {
      seriesKeys[seriesNumber] = seriesKey;
    }
    fireSeriesChanged();
  }
  






  public void setSeriesKey(int seriesNumber, Comparable key)
  {
    seriesKeys[seriesNumber] = key;
  }
  







  public void addValue(int seriesNumber, int index, float value)
  {
    invalidateRangeInfo();
    if (seriesNumber >= valueHistory.length) {
      throw new IllegalArgumentException("TimeSeriesDataset.addValue(): series #" + seriesNumber + "unspecified in c'tor");
    }
    


    if (valueHistory[seriesNumber] == null) {
      valueHistory[seriesNumber] = new ValueSequence(historyCount);
      
      seriesCount += 1;
    }
    


    valueHistory[seriesNumber].enterData(index, value);
    
    fireSeriesChanged();
  }
  




  public int getSeriesCount()
  {
    return seriesCount;
  }
  









  public int getItemCount(int series)
  {
    return historyCount;
  }
  








  protected int translateGet(int toFetch)
  {
    if (oldestAt == 0) {
      return toFetch;
    }
    
    int newIndex = toFetch + oldestAt;
    if (newIndex >= historyCount) {
      newIndex -= historyCount;
    }
    return newIndex;
  }
  






  public int offsetFromNewest(int delta)
  {
    return wrapOffset(newestAt + delta);
  }
  






  public int offsetFromOldest(int delta)
  {
    return wrapOffset(oldestAt + delta);
  }
  






  protected int wrapOffset(int protoIndex)
  {
    int tmp = protoIndex;
    if (tmp >= historyCount) {
      tmp -= historyCount;
    }
    else if (tmp < 0) {
      tmp += historyCount;
    }
    return tmp;
  }
  






  public synchronized RegularTimePeriod advanceTime()
  {
    RegularTimePeriod nextInstant = pointsInTime[newestAt].next();
    newestAt = oldestAt;
    





    boolean extremaChanged = false;
    float oldMax = 0.0F;
    if (maxValue != null) {
      oldMax = maxValue.floatValue();
    }
    for (int s = 0; s < getSeriesCount(); s++) {
      if (valueHistory[s].getData(oldestAt) == oldMax) {
        extremaChanged = true;
      }
      if (extremaChanged) {
        break;
      }
    }
    if (extremaChanged) {
      invalidateRangeInfo();
    }
    
    float wiper = 0.0F;
    for (int s = 0; s < getSeriesCount(); s++) {
      valueHistory[s].enterData(newestAt, wiper);
    }
    
    pointsInTime[newestAt] = nextInstant;
    
    oldestAt += 1;
    if (oldestAt >= historyCount) {
      oldestAt = 0;
    }
    
    long startL = domainStart.longValue();
    domainStart = new Long(startL + deltaTime);
    long endL = domainEnd.longValue();
    domainEnd = new Long(endL + deltaTime);
    domainRange = new Range(startL, endL);
    fireSeriesChanged();
    return nextInstant;
  }
  




  public void invalidateRangeInfo()
  {
    maxValue = null;
    valueRange = null;
  }
  




  protected double findMaxValue()
  {
    double max = 0.0D;
    for (int s = 0; s < getSeriesCount(); s++) {
      for (int i = 0; i < historyCount; i++) {
        double tmp = getYValue(s, i);
        if (tmp > max) {
          max = tmp;
        }
      }
    }
    return max;
  }
  






  public int getOldestIndex()
  {
    return oldestAt;
  }
  




  public int getNewestIndex()
  {
    return newestAt;
  }
  






  public void appendData(float[] newData)
  {
    int nDataPoints = newData.length;
    if (nDataPoints > valueHistory.length) {
      throw new IllegalArgumentException("More data than series to put them in");
    }
    


    for (int s = 0; s < nDataPoints; s++)
    {

      if (valueHistory[s] == null) {
        valueHistory[s] = new ValueSequence(historyCount);
      }
      valueHistory[s].enterData(newestAt, newData[s]);
    }
    fireSeriesChanged();
  }
  







  public void appendData(float[] newData, int insertionIndex, int refresh)
  {
    int nDataPoints = newData.length;
    if (nDataPoints > valueHistory.length) {
      throw new IllegalArgumentException("More data than series to put them in");
    }
    

    for (int s = 0; s < nDataPoints; s++) {
      if (valueHistory[s] == null) {
        valueHistory[s] = new ValueSequence(historyCount);
      }
      valueHistory[s].enterData(insertionIndex, newData[s]);
    }
    if (refresh > 0) {
      insertionIndex++;
      if (insertionIndex % refresh == 0) {
        fireSeriesChanged();
      }
    }
  }
  




  public RegularTimePeriod getNewestTime()
  {
    return pointsInTime[newestAt];
  }
  




  public RegularTimePeriod getOldestTime()
  {
    return pointsInTime[oldestAt];
  }
  









  public Number getX(int series, int item)
  {
    RegularTimePeriod tp = pointsInTime[translateGet(item)];
    return new Long(getX(tp));
  }
  









  public double getYValue(int series, int item)
  {
    ValueSequence values = valueHistory[series];
    return values.getData(translateGet(item));
  }
  







  public Number getY(int series, int item)
  {
    return new Float(getYValue(series, item));
  }
  







  public Number getStartX(int series, int item)
  {
    RegularTimePeriod tp = pointsInTime[translateGet(item)];
    return new Long(tp.getFirstMillisecond(workingCalendar));
  }
  







  public Number getEndX(int series, int item)
  {
    RegularTimePeriod tp = pointsInTime[translateGet(item)];
    return new Long(tp.getLastMillisecond(workingCalendar));
  }
  







  public Number getStartY(int series, int item)
  {
    return getY(series, item);
  }
  







  public Number getEndY(int series, int item)
  {
    return getY(series, item);
  }
  

















  public Comparable getSeriesKey(int series)
  {
    return seriesKeys[series];
  }
  


  protected void fireSeriesChanged()
  {
    seriesChanged(new SeriesChangeEvent(this));
  }
  












  public double getDomainLowerBound(boolean includeInterval)
  {
    return domainStart.doubleValue();
  }
  








  public double getDomainUpperBound(boolean includeInterval)
  {
    return domainEnd.doubleValue();
  }
  








  public Range getDomainBounds(boolean includeInterval)
  {
    if (domainRange == null) {
      findDomainLimits();
    }
    return domainRange;
  }
  






  private long getX(RegularTimePeriod period)
  {
    switch (position) {
    case 0: 
      return period.getFirstMillisecond(workingCalendar);
    case 1: 
      return period.getMiddleMillisecond(workingCalendar);
    case 2: 
      return period.getLastMillisecond(workingCalendar);
    }
    return period.getMiddleMillisecond(workingCalendar);
  }
  














  public double getRangeLowerBound(boolean includeInterval)
  {
    double result = NaN.0D;
    if (minValue != null) {
      result = minValue.doubleValue();
    }
    return result;
  }
  







  public double getRangeUpperBound(boolean includeInterval)
  {
    double result = NaN.0D;
    if (maxValue != null) {
      result = maxValue.doubleValue();
    }
    return result;
  }
  







  public Range getRangeBounds(boolean includeInterval)
  {
    if (valueRange == null) {
      double max = getRangeUpperBound(includeInterval);
      valueRange = new Range(0.0D, max);
    }
    return valueRange;
  }
}
