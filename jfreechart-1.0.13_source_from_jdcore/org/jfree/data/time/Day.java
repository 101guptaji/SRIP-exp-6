package org.jfree.data.time;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.jfree.date.SerialDate;







































































public class Day
  extends RegularTimePeriod
  implements Serializable
{
  private static final long serialVersionUID = -7082667380758962755L;
  protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  



  protected static final DateFormat DATE_FORMAT_SHORT = DateFormat.getDateInstance(3);
  


  protected static final DateFormat DATE_FORMAT_MEDIUM = DateFormat.getDateInstance(2);
  


  protected static final DateFormat DATE_FORMAT_LONG = DateFormat.getDateInstance(1);
  


  private SerialDate serialDate;
  

  private long firstMillisecond;
  

  private long lastMillisecond;
  


  public Day()
  {
    this(new Date());
  }
  






  public Day(int day, int month, int year)
  {
    serialDate = SerialDate.createInstance(day, month, year);
    peg(Calendar.getInstance());
  }
  




  public Day(SerialDate serialDate)
  {
    if (serialDate == null) {
      throw new IllegalArgumentException("Null 'serialDate' argument.");
    }
    this.serialDate = serialDate;
    peg(Calendar.getInstance());
  }
  








  public Day(Date time)
  {
    this(time, TimeZone.getDefault(), Locale.getDefault());
  }
  





  /**
   * @deprecated
   */
  public Day(Date time, TimeZone zone)
  {
    this(time, zone, Locale.getDefault());
  }
  






  public Day(Date time, TimeZone zone, Locale locale)
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
    int d = calendar.get(5);
    int m = calendar.get(2) + 1;
    int y = calendar.get(1);
    serialDate = SerialDate.createInstance(d, m, y);
    peg(calendar);
  }
  








  public SerialDate getSerialDate()
  {
    return serialDate;
  }
  




  public int getYear()
  {
    return serialDate.getYYYY();
  }
  




  public int getMonth()
  {
    return serialDate.getMonth();
  }
  




  public int getDayOfMonth()
  {
    return serialDate.getDayOfMonth();
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
    int serial = serialDate.toSerial();
    if (serial > 2) {
      SerialDate yesterday = SerialDate.createInstance(serial - 1);
      return new Day(yesterday);
    }
    
    Day result = null;
    
    return result;
  }
  







  public RegularTimePeriod next()
  {
    int serial = serialDate.toSerial();
    if (serial < 2958465) {
      SerialDate tomorrow = SerialDate.createInstance(serial + 1);
      return new Day(tomorrow);
    }
    
    Day result = null;
    
    return result;
  }
  




  public long getSerialIndex()
  {
    return serialDate.toSerial();
  }
  










  public long getFirstMillisecond(Calendar calendar)
  {
    int year = serialDate.getYYYY();
    int month = serialDate.getMonth();
    int day = serialDate.getDayOfMonth();
    calendar.clear();
    calendar.set(year, month - 1, day, 0, 0, 0);
    calendar.set(14, 0);
    
    return calendar.getTime().getTime();
  }
  










  public long getLastMillisecond(Calendar calendar)
  {
    int year = serialDate.getYYYY();
    int month = serialDate.getMonth();
    int day = serialDate.getDayOfMonth();
    calendar.clear();
    calendar.set(year, month - 1, day, 23, 59, 59);
    calendar.set(14, 999);
    
    return calendar.getTime().getTime();
  }
  









  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Day)) {
      return false;
    }
    Day that = (Day)obj;
    if (!serialDate.equals(that.getSerialDate())) {
      return false;
    }
    return true;
  }
  








  public int hashCode()
  {
    return serialDate.hashCode();
  }
  




  public int compareTo(Object o1)
  {
    int result;
    



    int result;
    


    if ((o1 instanceof Day)) {
      Day d = (Day)o1;
      result = -d.getSerialDate().compare(serialDate);
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
    return serialDate.toString();
  }
  









  public static Day parseDay(String s)
  {
    try
    {
      return new Day(DATE_FORMAT.parse(s));
    }
    catch (ParseException e1) {
      try {
        return new Day(DATE_FORMAT_SHORT.parse(s));
      }
      catch (ParseException e2) {}
    }
    

    return null;
  }
}
