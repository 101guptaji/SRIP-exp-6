package org.jfree.data.time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.jfree.date.SerialDate;









































































public class Quarter
  extends RegularTimePeriod
  implements Serializable
{
  private static final long serialVersionUID = 3810061714380888671L;
  public static final int FIRST_QUARTER = 1;
  public static final int LAST_QUARTER = 4;
  public static final int[] FIRST_MONTH_IN_QUARTER = { 0, 1, 4, 7, 10 };
  




  public static final int[] LAST_MONTH_IN_QUARTER = { 0, 3, 6, 9, 12 };
  


  private short year;
  


  private byte quarter;
  


  private long firstMillisecond;
  

  private long lastMillisecond;
  


  public Quarter()
  {
    this(new Date());
  }
  





  public Quarter(int quarter, int year)
  {
    if ((quarter < 1) || (quarter > 4)) {
      throw new IllegalArgumentException("Quarter outside valid range.");
    }
    this.year = ((short)year);
    this.quarter = ((byte)quarter);
    peg(Calendar.getInstance());
  }
  





  public Quarter(int quarter, Year year)
  {
    if ((quarter < 1) || (quarter > 4)) {
      throw new IllegalArgumentException("Quarter outside valid range.");
    }
    this.year = ((short)year.getYear());
    this.quarter = ((byte)quarter);
    peg(Calendar.getInstance());
  }
  







  public Quarter(Date time)
  {
    this(time, TimeZone.getDefault());
  }
  





  /**
   * @deprecated
   */
  public Quarter(Date time, TimeZone zone)
  {
    this(time, zone, Locale.getDefault());
  }
  









  public Quarter(Date time, TimeZone zone, Locale locale)
  {
    Calendar calendar = Calendar.getInstance(zone, locale);
    calendar.setTime(time);
    int month = calendar.get(2) + 1;
    quarter = ((byte)SerialDate.monthCodeToQuarter(month));
    year = ((short)calendar.get(1));
    peg(calendar);
  }
  




  public int getQuarter()
  {
    return quarter;
  }
  




  public Year getYear()
  {
    return new Year(year);
  }
  






  public int getYearValue()
  {
    return year;
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
    Quarter result;
    
    Quarter result;
    
    if (quarter > 1) {
      result = new Quarter(quarter - 1, year);
    } else {
      Quarter result;
      if (year > 1900) {
        result = new Quarter(4, year - 1);
      }
      else {
        result = null;
      }
    }
    return result;
  }
  

  public RegularTimePeriod next()
  {
    Quarter result;
    
    Quarter result;
    
    if (quarter < 4) {
      result = new Quarter(quarter + 1, year);
    } else {
      Quarter result;
      if (year < 9999) {
        result = new Quarter(1, year + 1);
      }
      else {
        result = null;
      }
    }
    return result;
  }
  




  public long getSerialIndex()
  {
    return year * 4L + quarter;
  }
  











  public boolean equals(Object obj)
  {
    if (obj != null) {
      if ((obj instanceof Quarter)) {
        Quarter target = (Quarter)obj;
        return (quarter == target.getQuarter()) && (year == target.getYearValue());
      }
      

      return false;
    }
    

    return false;
  }
  










  public int hashCode()
  {
    int result = 17;
    result = 37 * result + quarter;
    result = 37 * result + year;
    return result;
  }
  







  public int compareTo(Object o1)
  {
    int result;
    





    if ((o1 instanceof Quarter)) {
      Quarter q = (Quarter)o1;
      int result = year - q.getYearValue();
      if (result == 0) {
        result = quarter - q.getQuarter();
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
  





  public String toString()
  {
    return "Q" + quarter + "/" + year;
  }
  










  public long getFirstMillisecond(Calendar calendar)
  {
    int month = FIRST_MONTH_IN_QUARTER[quarter];
    calendar.set(year, month - 1, 1, 0, 0, 0);
    calendar.set(14, 0);
    

    return calendar.getTime().getTime();
  }
  










  public long getLastMillisecond(Calendar calendar)
  {
    int month = LAST_MONTH_IN_QUARTER[quarter];
    int eom = SerialDate.lastDayOfMonth(month, year);
    calendar.set(year, month - 1, eom, 23, 59, 59);
    calendar.set(14, 999);
    

    return calendar.getTime().getTime();
  }
  











  public static Quarter parseQuarter(String s)
  {
    int i = s.indexOf("Q");
    if (i == -1) {
      throw new TimePeriodFormatException("Missing Q.");
    }
    
    if (i == s.length() - 1) {
      throw new TimePeriodFormatException("Q found at end of string.");
    }
    
    String qstr = s.substring(i + 1, i + 2);
    int quarter = Integer.parseInt(qstr);
    String remaining = s.substring(0, i) + s.substring(i + 2, s.length());
    

    remaining = remaining.replace('/', ' ');
    remaining = remaining.replace(',', ' ');
    remaining = remaining.replace('-', ' ');
    

    Year year = Year.parseYear(remaining.trim());
    Quarter result = new Quarter(quarter, year);
    return result;
  }
}
