package org.jfree.data.time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;



















































































public class Millisecond
  extends RegularTimePeriod
  implements Serializable
{
  static final long serialVersionUID = -5316836467277638485L;
  public static final int FIRST_MILLISECOND_IN_SECOND = 0;
  public static final int LAST_MILLISECOND_IN_SECOND = 999;
  private Day day;
  private byte hour;
  private byte minute;
  private byte second;
  private int millisecond;
  private long firstMillisecond;
  
  public Millisecond()
  {
    this(new Date());
  }
  





  public Millisecond(int millisecond, Second second)
  {
    this.millisecond = millisecond;
    this.second = ((byte)second.getSecond());
    minute = ((byte)second.getMinute().getMinute());
    hour = ((byte)second.getMinute().getHourValue());
    day = second.getMinute().getDay();
    peg(Calendar.getInstance());
  }
  












  public Millisecond(int millisecond, int second, int minute, int hour, int day, int month, int year)
  {
    this(millisecond, new Second(second, minute, hour, day, month, year));
  }
  







  public Millisecond(Date time)
  {
    this(time, TimeZone.getDefault(), Locale.getDefault());
  }
  





  /**
   * @deprecated
   */
  public Millisecond(Date time, TimeZone zone)
  {
    this(time, zone, Locale.getDefault());
  }
  








  public Millisecond(Date time, TimeZone zone, Locale locale)
  {
    Calendar calendar = Calendar.getInstance(zone, locale);
    calendar.setTime(time);
    millisecond = calendar.get(14);
    second = ((byte)calendar.get(13));
    minute = ((byte)calendar.get(12));
    hour = ((byte)calendar.get(11));
    day = new Day(time, zone, locale);
    peg(calendar);
  }
  




  public Second getSecond()
  {
    return new Second(second, minute, hour, day.getDayOfMonth(), day.getMonth(), day.getYear());
  }
  






  public long getMillisecond()
  {
    return millisecond;
  }
  









  public long getFirstMillisecond()
  {
    return firstMillisecond;
  }
  









  public long getLastMillisecond()
  {
    return firstMillisecond;
  }
  







  public void peg(Calendar calendar)
  {
    firstMillisecond = getFirstMillisecond(calendar);
  }
  




  public RegularTimePeriod previous()
  {
    RegularTimePeriod result = null;
    if (millisecond != 0) {
      result = new Millisecond(millisecond - 1, getSecond());
    }
    else {
      Second previous = (Second)getSecond().previous();
      if (previous != null) {
        result = new Millisecond(999, previous);
      }
    }
    return result;
  }
  




  public RegularTimePeriod next()
  {
    RegularTimePeriod result = null;
    if (millisecond != 999) {
      result = new Millisecond(millisecond + 1, getSecond());
    }
    else {
      Second next = (Second)getSecond().next();
      if (next != null) {
        result = new Millisecond(0, next);
      }
    }
    return result;
  }
  




  public long getSerialIndex()
  {
    long hourIndex = day.getSerialIndex() * 24L + hour;
    long minuteIndex = hourIndex * 60L + minute;
    long secondIndex = minuteIndex * 60L + second;
    return secondIndex * 1000L + millisecond;
  }
  










  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Millisecond)) {
      return false;
    }
    Millisecond that = (Millisecond)obj;
    if (millisecond != millisecond) {
      return false;
    }
    if (second != second) {
      return false;
    }
    if (minute != minute) {
      return false;
    }
    if (hour != hour) {
      return false;
    }
    if (!day.equals(day)) {
      return false;
    }
    return true;
  }
  








  public int hashCode()
  {
    int result = 17;
    result = 37 * result + millisecond;
    result = 37 * result + getSecond().hashCode();
    return result;
  }
  




  public int compareTo(Object obj)
  {
    int result;
    



    int result;
    



    if ((obj instanceof Millisecond)) {
      Millisecond ms = (Millisecond)obj;
      long difference = getFirstMillisecond() - ms.getFirstMillisecond();
      int result; if (difference > 0L) {
        result = 1;
      } else {
        int result;
        if (difference < 0L) {
          result = -1;
        }
        else {
          result = 0;
        }
      }
    }
    else
    {
      int result;
      if ((obj instanceof RegularTimePeriod)) {
        RegularTimePeriod rtp = (RegularTimePeriod)obj;
        long thisVal = getFirstMillisecond();
        long anotherVal = rtp.getFirstMillisecond();
        result = thisVal == anotherVal ? 0 : thisVal < anotherVal ? -1 : 1;


      }
      else
      {


        result = 1;
      }
    }
    return result;
  }
  









  public long getFirstMillisecond(Calendar calendar)
  {
    int year = this.day.getYear();
    int month = this.day.getMonth() - 1;
    int day = this.day.getDayOfMonth();
    calendar.clear();
    calendar.set(year, month, day, hour, minute, second);
    calendar.set(14, millisecond);
    
    return calendar.getTime().getTime();
  }
  









  public long getLastMillisecond(Calendar calendar)
  {
    return getFirstMillisecond(calendar);
  }
}
