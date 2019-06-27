package org.jfree.data.time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

































































































public class Week
  extends RegularTimePeriod
  implements Serializable
{
  private static final long serialVersionUID = 1856387786939865061L;
  public static final int FIRST_WEEK_IN_YEAR = 1;
  public static final int LAST_WEEK_IN_YEAR = 53;
  private short year;
  private byte week;
  private long firstMillisecond;
  private long lastMillisecond;
  
  public Week()
  {
    this(new Date());
  }
  





  public Week(int week, int year)
  {
    if ((week < 1) && (week > 53)) {
      throw new IllegalArgumentException("The 'week' argument must be in the range 1 - 53.");
    }
    
    this.week = ((byte)week);
    this.year = ((short)year);
    peg(Calendar.getInstance());
  }
  





  public Week(int week, Year year)
  {
    if ((week < 1) && (week > 53)) {
      throw new IllegalArgumentException("The 'week' argument must be in the range 1 - 53.");
    }
    
    this.week = ((byte)week);
    this.year = ((short)year.getYear());
    peg(Calendar.getInstance());
  }
  










  public Week(Date time)
  {
    this(time, TimeZone.getDefault(), Locale.getDefault());
  }
  






  /**
   * @deprecated
   */
  public Week(Date time, TimeZone zone)
  {
    this(time, zone, Locale.getDefault());
  }
  









  public Week(Date time, TimeZone zone, Locale locale)
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
    



    int tempWeek = calendar.get(3);
    if ((tempWeek == 1) && (calendar.get(2) == 11))
    {
      week = 1;
      year = ((short)(calendar.get(1) + 1));
    }
    else {
      week = ((byte)Math.min(tempWeek, 53));
      int yyyy = calendar.get(1);
      

      if ((calendar.get(2) == 0) && (week >= 52))
      {
        yyyy--;
      }
      year = ((short)yyyy);
    }
    peg(calendar);
  }
  




  public Year getYear()
  {
    return new Year(year);
  }
  




  public int getYearValue()
  {
    return year;
  }
  




  public int getWeek()
  {
    return week;
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
    Week result;
    

    Week result;
    

    if (week != 1) {
      result = new Week(week - 1, year);
    }
    else {
      Week result;
      if (year > 1900) {
        int yy = year - 1;
        Calendar prevYearCalendar = Calendar.getInstance();
        prevYearCalendar.set(yy, 11, 31);
        result = new Week(prevYearCalendar.getActualMaximum(3), yy);
      }
      else
      {
        result = null;
      }
    }
    return result;
  }
  



  public RegularTimePeriod next()
  {
    Week result;
    


    Week result;
    


    if (week < 52) {
      result = new Week(week + 1, year);
    }
    else {
      Calendar calendar = Calendar.getInstance();
      calendar.set(year, 11, 31);
      int actualMaxWeek = calendar.getActualMaximum(3);
      Week result;
      if (week < actualMaxWeek) {
        result = new Week(week + 1, year);
      } else {
        Week result;
        if (year < 9999) {
          result = new Week(1, year + 1);
        }
        else {
          result = null;
        }
      }
    }
    return result;
  }
  





  public long getSerialIndex()
  {
    return year * 53L + week;
  }
  










  public long getFirstMillisecond(Calendar calendar)
  {
    Calendar c = (Calendar)calendar.clone();
    c.clear();
    c.set(1, year);
    c.set(3, week);
    c.set(7, c.getFirstDayOfWeek());
    c.set(10, 0);
    c.set(12, 0);
    c.set(13, 0);
    c.set(14, 0);
    
    return c.getTime().getTime();
  }
  










  public long getLastMillisecond(Calendar calendar)
  {
    Calendar c = (Calendar)calendar.clone();
    c.clear();
    c.set(1, year);
    c.set(3, week + 1);
    c.set(7, c.getFirstDayOfWeek());
    c.set(10, 0);
    c.set(12, 0);
    c.set(13, 0);
    c.set(14, 0);
    
    return c.getTime().getTime() - 1L;
  }
  






  public String toString()
  {
    return "Week " + week + ", " + year;
  }
  










  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Week)) {
      return false;
    }
    Week that = (Week)obj;
    if (week != week) {
      return false;
    }
    if (year != year) {
      return false;
    }
    return true;
  }
  









  public int hashCode()
  {
    int result = 17;
    result = 37 * result + week;
    result = 37 * result + year;
    return result;
  }
  







  public int compareTo(Object o1)
  {
    int result;
    





    if ((o1 instanceof Week)) {
      Week w = (Week)o1;
      int result = year - w.getYear().getYear();
      if (result == 0) {
        result = week - w.getWeek();
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
  












  public static Week parseWeek(String s)
  {
    Week result = null;
    if (s != null)
    {

      s = s.trim();
      
      int i = findSeparator(s);
      if (i != -1) {
        String s1 = s.substring(0, i).trim();
        String s2 = s.substring(i + 1, s.length()).trim();
        
        Year y = evaluateAsYear(s1);
        
        if (y != null) {
          int w = stringToWeek(s2);
          if (w == -1) {
            throw new TimePeriodFormatException("Can't evaluate the week.");
          }
          
          result = new Week(w, y);
        }
        else {
          y = evaluateAsYear(s2);
          if (y != null) {
            int w = stringToWeek(s1);
            if (w == -1) {
              throw new TimePeriodFormatException("Can't evaluate the week.");
            }
            
            result = new Week(w, y);
          }
          else {
            throw new TimePeriodFormatException("Can't evaluate the year.");
          }
          
        }
      }
      else
      {
        throw new TimePeriodFormatException("Could not find separator.");
      }
    }
    

    return result;
  }
  









  private static int findSeparator(String s)
  {
    int result = s.indexOf('-');
    if (result == -1) {
      result = s.indexOf(',');
    }
    if (result == -1) {
      result = s.indexOf(' ');
    }
    if (result == -1) {
      result = s.indexOf('.');
    }
    return result;
  }
  









  private static Year evaluateAsYear(String s)
  {
    Year result = null;
    try {
      result = Year.parseYear(s);
    }
    catch (TimePeriodFormatException e) {}
    

    return result;
  }
  








  private static int stringToWeek(String s)
  {
    int result = -1;
    s = s.replace('W', ' ');
    s = s.trim();
    try {
      result = Integer.parseInt(s);
      if ((result < 1) || (result > 53)) {
        result = -1;
      }
    }
    catch (NumberFormatException e) {}
    

    return result;
  }
}
