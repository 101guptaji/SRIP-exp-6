package org.jfree.chart.axis;

import java.util.Date;

public abstract interface Timeline
{
  public abstract long toTimelineValue(long paramLong);
  
  public abstract long toTimelineValue(Date paramDate);
  
  public abstract long toMillisecond(long paramLong);
  
  public abstract boolean containsDomainValue(long paramLong);
  
  public abstract boolean containsDomainValue(Date paramDate);
  
  public abstract boolean containsDomainRange(long paramLong1, long paramLong2);
  
  public abstract boolean containsDomainRange(Date paramDate1, Date paramDate2);
}
