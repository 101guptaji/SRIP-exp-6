package org.jfree.date;

import java.util.Calendar;
import java.util.Date;






























































































public class SpreadsheetDate
  extends SerialDate
{
  private static final long serialVersionUID = -2039586705374454461L;
  private final int serial;
  private final int day;
  private final int month;
  private final int year;
  
  public SpreadsheetDate(int day, int month, int year)
  {
    if ((year >= 1900) && (year <= 9999)) {
      this.year = year;
    }
    else {
      throw new IllegalArgumentException("The 'year' argument must be in range 1900 to 9999.");
    }
    


    if ((month >= 1) && (month <= 12))
    {
      this.month = month;
    }
    else {
      throw new IllegalArgumentException("The 'month' argument must be in the range 1 to 12.");
    }
    


    if ((day >= 1) && (day <= SerialDate.lastDayOfMonth(month, year))) {
      this.day = day;
    }
    else {
      throw new IllegalArgumentException("Invalid 'day' argument.");
    }
    

    serial = calcSerial(day, month, year);
  }
  







  public SpreadsheetDate(int serial)
  {
    if ((serial >= 2) && (serial <= 2958465)) {
      this.serial = serial;
    }
    else {
      throw new IllegalArgumentException("SpreadsheetDate: Serial must be in range 2 to 2958465.");
    }
    



    int days = this.serial - 2;
    
    int overestimatedYYYY = 1900 + days / 365;
    int leaps = SerialDate.leapYearCount(overestimatedYYYY);
    int nonleapdays = days - leaps;
    
    int underestimatedYYYY = 1900 + nonleapdays / 365;
    
    if (underestimatedYYYY == overestimatedYYYY) {
      year = underestimatedYYYY;
    }
    else {
      int ss1 = calcSerial(1, 1, underestimatedYYYY);
      while (ss1 <= this.serial) {
        underestimatedYYYY += 1;
        ss1 = calcSerial(1, 1, underestimatedYYYY);
      }
      year = (underestimatedYYYY - 1);
    }
    
    int ss2 = calcSerial(1, 1, year);
    
    int[] daysToEndOfPrecedingMonth = AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH;
    

    if (isLeapYear(year)) {
      daysToEndOfPrecedingMonth = LEAP_YEAR_AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH;
    }
    


    int mm = 1;
    int sss = ss2 + daysToEndOfPrecedingMonth[mm] - 1;
    while (sss < this.serial) {
      mm += 1;
      sss = ss2 + daysToEndOfPrecedingMonth[mm] - 1;
    }
    month = (mm - 1);
    

    day = (this.serial - ss2 - daysToEndOfPrecedingMonth[month] + 1);
  }
  








  public int toSerial()
  {
    return serial;
  }
  




  public Date toDate()
  {
    Calendar calendar = Calendar.getInstance();
    calendar.set(getYYYY(), getMonth() - 1, getDayOfMonth(), 0, 0, 0);
    return calendar.getTime();
  }
  




  public int getYYYY()
  {
    return year;
  }
  




  public int getMonth()
  {
    return month;
  }
  




  public int getDayOfMonth()
  {
    return day;
  }
  









  public int getDayOfWeek()
  {
    return (serial + 6) % 7 + 1;
  }
  











  public boolean equals(Object object)
  {
    if ((object instanceof SerialDate)) {
      SerialDate s = (SerialDate)object;
      return s.toSerial() == toSerial();
    }
    
    return false;
  }
  






  public int hashCode()
  {
    return toSerial();
  }
  








  public int compare(SerialDate other)
  {
    return serial - other.toSerial();
  }
  







  public int compareTo(Object other)
  {
    return compare((SerialDate)other);
  }
  








  public boolean isOn(SerialDate other)
  {
    return serial == other.toSerial();
  }
  








  public boolean isBefore(SerialDate other)
  {
    return serial < other.toSerial();
  }
  








  public boolean isOnOrBefore(SerialDate other)
  {
    return serial <= other.toSerial();
  }
  








  public boolean isAfter(SerialDate other)
  {
    return serial > other.toSerial();
  }
  








  public boolean isOnOrAfter(SerialDate other)
  {
    return serial >= other.toSerial();
  }
  









  public boolean isInRange(SerialDate d1, SerialDate d2)
  {
    return isInRange(d1, d2, 3);
  }
  













  public boolean isInRange(SerialDate d1, SerialDate d2, int include)
  {
    int s1 = d1.toSerial();
    int s2 = d2.toSerial();
    int start = Math.min(s1, s2);
    int end = Math.max(s1, s2);
    
    int s = toSerial();
    if (include == 3) {
      return (s >= start) && (s <= end);
    }
    if (include == 1) {
      return (s >= start) && (s < end);
    }
    if (include == 2) {
      return (s > start) && (s <= end);
    }
    
    return (s > start) && (s < end);
  }
  











  private int calcSerial(int d, int m, int y)
  {
    int yy = (y - 1900) * 365 + SerialDate.leapYearCount(y - 1);
    int mm = SerialDate.AGGREGATE_DAYS_TO_END_OF_PRECEDING_MONTH[m];
    if ((m > 2) && 
      (SerialDate.isLeapYear(y))) {
      mm += 1;
    }
    
    int dd = d;
    return yy + mm + dd + 1;
  }
}
