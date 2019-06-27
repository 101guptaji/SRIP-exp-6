package org.jfree.data.time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;





















































































public class Minute
  extends RegularTimePeriod
  implements Serializable
{
  private static final long serialVersionUID = 2144572840034842871L;
  public static final int FIRST_MINUTE_IN_HOUR = 0;
  public static final int LAST_MINUTE_IN_HOUR = 59;
  private Day day;
  private byte hour;
  private byte minute;
  private long firstMillisecond;
  private long lastMillisecond;
  
  public Minute()
  {
    this(new Date());
  }
  





  public Minute(int minute, Hour hour)
  {
    if (hour == null) {
      throw new IllegalArgumentException("Null 'hour' argument.");
    }
    this.minute = ((byte)minute);
    this.hour = ((byte)hour.getHour());
    day = hour.getDay();
    peg(Calendar.getInstance());
  }
  








  public Minute(Date time)
  {
    this(time, TimeZone.getDefault(), Locale.getDefault());
  }
  





  /**
   * @deprecated
   */
  public Minute(Date time, TimeZone zone)
  {
    this(time, zone, Locale.getDefault());
  }
  








  public Minute(Date time, TimeZone zone, Locale locale)
  {
    if (time == null) {
      throw new IllegalArgumentException("Null 'time' argument.");
    }
    if (zone == null) {
      throw new IllegalArgumentException("Null 'zone' argument.");
    }
    if (locale == null) {
      throw new IllegalArgumentException("Null 'locale' argument.");
    }
    Calendar calendar = Calendar.getInstance(zone, locale);
    calendar.setTime(time);
    int min = calendar.get(12);
    minute = ((byte)min);
    hour = ((byte)calendar.get(11));
    day = new Day(time, zone, locale);
    peg(calendar);
  }
  








  public Minute(int minute, int hour, int day, int month, int year)
  {
    this(minute, new Hour(hour, new Day(day, month, year)));
  }
  






  public Day getDay()
  {
    return day;
  }
  




  public Hour getHour()
  {
    return new Hour(hour, day);
  }
  






  public int getHourValue()
  {
    return hour;
  }
  




  public int getMinute()
  {
    return minute;
  }
  









  public long getFirstMillisecond()
  {
    return firstMillisecond;
  }
  









  public long getLastMillisecond()
  {
    return lastMillisecond;
  }
  







  public void peg(Calendar calendar)
  {
    firstMillisecond = getFirstMillisecond(calendar);
    lastMillisecond = getLastMillisecond(calendar);
  }
  

  public RegularTimePeriod previous()
  {
    Minute result;
    
    Minute result;
    
    if (minute != 0) {
      result = new Minute(minute - 1, getHour());
    }
    else {
      Hour h = (Hour)getHour().previous();
      Minute result; if (h != null) {
        result = new Minute(59, h);
      }
      else {
        result = null;
      }
    }
    return result;
  }
  

  public RegularTimePeriod next()
  {
    Minute result;
    
    Minute result;
    
    if (minute != 59) {
      result = new Minute(minute + 1, getHour());
    }
    else {
      Hour nextHour = (Hour)getHour().next();
      Minute result; if (nextHour != null) {
        result = new Minute(0, nextHour);
      }
      else {
        result = null;
      }
    }
    return result;
  }
  




  public long getSerialIndex()
  {
    long hourIndex = day.getSerialIndex() * 24L + hour;
    return hourIndex * 60L + minute;
  }
  










  public long getFirstMillisecond(Calendar calendar)
  {
    int year = this.day.getYear();
    int month = this.day.getMonth() - 1;
    int day = this.day.getDayOfMonth();
    
    calendar.clear();
    calendar.set(year, month, day, hour, minute, 0);
    calendar.set(14, 0);
    

    return calendar.getTime().getTime();
  }
  










  public long getLastMillisecond(Calendar calendar)
  {
    int year = this.day.getYear();
    int month = this.day.getMonth() - 1;
    int day = this.day.getDayOfMonth();
    
    calendar.clear();
    calendar.set(year, month, day, hour, minute, 59);
    calendar.set(14, 999);
    

    return calendar.getTime().getTime();
  }
  










  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Minute)) {
      return false;
    }
    Minute that = (Minute)obj;
    if (minute != minute) {
      return false;
    }
    if (hour != hour) {
      return false;
    }
    return true;
  }
  








  public int hashCode()
  {
    int result = 17;
    result = 37 * result + minute;
    result = 37 * result + hour;
    result = 37 * result + day.hashCode();
    return result;
  }
  






  public int compareTo(Object o1)
  {
    int result;
    





    if ((o1 instanceof Minute)) {
      Minute m = (Minute)o1;
      int result = getHour().compareTo(m.getHour());
      if (result == 0) {
        result = minute - m.getMinute();
      }
    }
    else
    {
      int result;
      if ((o1 instanceof RegularTimePeriod))
      {
        result = 0;


      }
      else
      {

        result = 1;
      }
    }
    return result;
  }
  









  public static Minute parseMinute(String s)
  {
    Minute result = null;
    s = s.trim();
    
    String daystr = s.substring(0, Math.min(10, s.length()));
    Day day = Day.parseDay(daystr);
    if (day != null) {
      String hmstr = s.substring(Math.min(daystr.length() + 1, s.length()), s.length());
      

      hmstr = hmstr.trim();
      
      String hourstr = hmstr.substring(0, Math.min(2, hmstr.length()));
      int hour = Integer.parseInt(hourstr);
      
      if ((hour >= 0) && (hour <= 23)) {
        String minstr = hmstr.substring(Math.min(hourstr.length() + 1, hmstr.length()), hmstr.length());
        


        int minute = Integer.parseInt(minstr);
        if ((minute >= 0) && (minute <= 59)) {
          result = new Minute(minute, new Hour(hour, day));
        }
      }
    }
    return result;
  }
}
