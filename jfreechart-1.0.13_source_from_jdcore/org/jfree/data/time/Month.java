package org.jfree.data.time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.jfree.date.SerialDate;

















































































public class Month
  extends RegularTimePeriod
  implements Serializable
{
  private static final long serialVersionUID = -5090216912548722570L;
  private int month;
  private int year;
  private long firstMillisecond;
  private long lastMillisecond;
  
  public Month()
  {
    this(new Date());
  }
  





  public Month(int month, int year)
  {
    if ((month < 1) || (month > 12)) {
      throw new IllegalArgumentException("Month outside valid range.");
    }
    this.month = month;
    this.year = year;
    peg(Calendar.getInstance());
  }
  





  public Month(int month, Year year)
  {
    if ((month < 1) || (month > 12)) {
      throw new IllegalArgumentException("Month outside valid range.");
    }
    this.month = month;
    this.year = year.getYear();
    peg(Calendar.getInstance());
  }
  







  public Month(Date time)
  {
    this(time, TimeZone.getDefault());
  }
  







  /**
   * @deprecated
   */
  public Month(Date time, TimeZone zone)
  {
    this(time, zone, Locale.getDefault());
  }
  









  public Month(Date time, TimeZone zone, Locale locale)
  {
    Calendar calendar = Calendar.getInstance(zone, locale);
    calendar.setTime(time);
    month = (calendar.get(2) + 1);
    year = calendar.get(1);
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
  




  public int getMonth()
  {
    return month;
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
    Month result;
    

    Month result;
    

    if (month != 1) {
      result = new Month(month - 1, year);
    } else {
      Month result;
      if (year > 1900) {
        result = new Month(12, year - 1);
      }
      else {
        result = null;
      }
    }
    return result;
  }
  


  public RegularTimePeriod next()
  {
    Month result;
    

    Month result;
    

    if (month != 12) {
      result = new Month(month + 1, year);
    } else {
      Month result;
      if (year < 9999) {
        result = new Month(1, year + 1);
      }
      else {
        result = null;
      }
    }
    return result;
  }
  




  public long getSerialIndex()
  {
    return year * 12L + month;
  }
  






  public String toString()
  {
    return SerialDate.monthCodeToString(month) + " " + year;
  }
  









  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Month)) {
      return false;
    }
    Month that = (Month)obj;
    if (month != month) {
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
    result = 37 * result + month;
    result = 37 * result + year;
    return result;
  }
  






  public int compareTo(Object o1)
  {
    int result;
    





    if ((o1 instanceof Month)) {
      Month m = (Month)o1;
      int result = year - m.getYearValue();
      if (result == 0) {
        result = month - m.getMonth();
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
  











  public long getFirstMillisecond(Calendar calendar)
  {
    calendar.set(year, month - 1, 1, 0, 0, 0);
    calendar.set(14, 0);
    

    return calendar.getTime().getTime();
  }
  










  public long getLastMillisecond(Calendar calendar)
  {
    int eom = SerialDate.lastDayOfMonth(month, year);
    calendar.set(year, month - 1, eom, 23, 59, 59);
    calendar.set(14, 999);
    

    return calendar.getTime().getTime();
  }
  









  public static Month parseMonth(String s)
  {
    Month result = null;
    if (s == null) {
      return result;
    }
    
    s = s.trim();
    int i = findSeparator(s);
    String s2;
    String s1;
    String s2;
    boolean yearIsFirst;
    if (i == -1) {
      boolean yearIsFirst = true;
      String s1 = s.substring(0, 5);
      s2 = s.substring(5);
    }
    else {
      s1 = s.substring(0, i).trim();
      s2 = s.substring(i + 1, s.length()).trim();
      
      Year y1 = evaluateAsYear(s1);
      boolean yearIsFirst; if (y1 == null) {
        yearIsFirst = false;
      }
      else {
        Year y2 = evaluateAsYear(s2);
        boolean yearIsFirst; if (y2 == null) {
          yearIsFirst = true;
        }
        else
          yearIsFirst = s1.length() > s2.length();
      }
    }
    int month;
    Year year;
    int month;
    if (yearIsFirst) {
      Year year = evaluateAsYear(s1);
      month = SerialDate.stringToMonthCode(s2);
    }
    else {
      year = evaluateAsYear(s2);
      month = SerialDate.stringToMonthCode(s1);
    }
    if (month == -1) {
      throw new TimePeriodFormatException("Can't evaluate the month.");
    }
    if (year == null) {
      throw new TimePeriodFormatException("Can't evaluate the year.");
    }
    result = new Month(month, year);
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
}
