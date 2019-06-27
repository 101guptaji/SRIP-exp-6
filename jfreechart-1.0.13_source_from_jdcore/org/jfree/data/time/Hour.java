package org.jfree.data.time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


















































































public class Hour
  extends RegularTimePeriod
  implements Serializable
{
  private static final long serialVersionUID = -835471579831937652L;
  public static final int FIRST_HOUR_IN_DAY = 0;
  public static final int LAST_HOUR_IN_DAY = 23;
  private Day day;
  private byte hour;
  private long firstMillisecond;
  private long lastMillisecond;
  
  public Hour()
  {
    this(new Date());
  }
  





  public Hour(int hour, Day day)
  {
    if (day == null) {
      throw new IllegalArgumentException("Null 'day' argument.");
    }
    this.hour = ((byte)hour);
    this.day = day;
    peg(Calendar.getInstance());
  }
  







  public Hour(int hour, int day, int month, int year)
  {
    this(hour, new Day(day, month, year));
  }
  








  public Hour(Date time)
  {
    this(time, TimeZone.getDefault(), Locale.getDefault());
  }
  






  /**
   * @deprecated
   */
  public Hour(Date time, TimeZone zone)
  {
    this(time, zone, Locale.getDefault());
  }
  









  public Hour(Date time, TimeZone zone, Locale locale)
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
    hour = ((byte)calendar.get(11));
    day = new Day(time, zone, locale);
    peg(calendar);
  }
  




  public int getHour()
  {
    return hour;
  }
  




  public Day getDay()
  {
    return day;
  }
  




  public int getYear()
  {
    return day.getYear();
  }
  




  public int getMonth()
  {
    return day.getMonth();
  }
  




  public int getDayOfMonth()
  {
    return day.getDayOfMonth();
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
    Hour result;
    
    Hour result;
    
    if (hour != 0) {
      result = new Hour(hour - 1, day);
    }
    else {
      Day prevDay = (Day)day.previous();
      Hour result; if (prevDay != null) {
        result = new Hour(23, prevDay);
      }
      else {
        result = null;
      }
    }
    return result;
  }
  

  public RegularTimePeriod next()
  {
    Hour result;
    
    Hour result;
    
    if (hour != 23) {
      result = new Hour(hour + 1, day);
    }
    else {
      Day nextDay = (Day)day.next();
      Hour result; if (nextDay != null) {
        result = new Hour(0, nextDay);
      }
      else {
        result = null;
      }
    }
    return result;
  }
  




  public long getSerialIndex()
  {
    return day.getSerialIndex() * 24L + hour;
  }
  









  public long getFirstMillisecond(Calendar calendar)
  {
    int year = day.getYear();
    int month = day.getMonth() - 1;
    int dom = day.getDayOfMonth();
    calendar.set(year, month, dom, hour, 0, 0);
    calendar.set(14, 0);
    
    return calendar.getTime().getTime();
  }
  









  public long getLastMillisecond(Calendar calendar)
  {
    int year = day.getYear();
    int month = day.getMonth() - 1;
    int dom = day.getDayOfMonth();
    calendar.set(year, month, dom, hour, 59, 59);
    calendar.set(14, 999);
    
    return calendar.getTime().getTime();
  }
  










  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Hour)) {
      return false;
    }
    Hour that = (Hour)obj;
    if (hour != hour) {
      return false;
    }
    if (!day.equals(day)) {
      return false;
    }
    return true;
  }
  





  public String toString()
  {
    return "[" + hour + "," + getDayOfMonth() + "/" + getMonth() + "/" + getYear() + "]";
  }
  









  public int hashCode()
  {
    int result = 17;
    result = 37 * result + hour;
    result = 37 * result + day.hashCode();
    return result;
  }
  






  public int compareTo(Object o1)
  {
    int result;
    





    if ((o1 instanceof Hour)) {
      Hour h = (Hour)o1;
      int result = getDay().compareTo(h.getDay());
      if (result == 0) {
        result = hour - h.getHour();
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
  









  public static Hour parseHour(String s)
  {
    Hour result = null;
    s = s.trim();
    
    String daystr = s.substring(0, Math.min(10, s.length()));
    Day day = Day.parseDay(daystr);
    if (day != null) {
      String hourstr = s.substring(Math.min(daystr.length() + 1, s.length()), s.length());
      

      hourstr = hourstr.trim();
      int hour = Integer.parseInt(hourstr);
      
      if ((hour >= 0) && (hour <= 23)) {
        result = new Hour(hour, day);
      }
    }
    
    return result;
  }
}
