package org.jfree.chart.axis;

import java.io.ObjectStreamException;
import java.io.Serializable;















































public class DateTickUnitType
  implements Serializable
{
  public static final DateTickUnitType YEAR = new DateTickUnitType("DateTickUnitType.YEAR", 1);
  


  public static final DateTickUnitType MONTH = new DateTickUnitType("DateTickUnitType.MONTH", 2);
  


  public static final DateTickUnitType DAY = new DateTickUnitType("DateTickUnitType.DAY", 5);
  



  public static final DateTickUnitType HOUR = new DateTickUnitType("DateTickUnitType.HOUR", 11);
  



  public static final DateTickUnitType MINUTE = new DateTickUnitType("DateTickUnitType.MINUTE", 12);
  


  public static final DateTickUnitType SECOND = new DateTickUnitType("DateTickUnitType.SECOND", 13);
  


  public static final DateTickUnitType MILLISECOND = new DateTickUnitType("DateTickUnitType.MILLISECOND", 14);
  



  private String name;
  



  private int calendarField;
  



  private DateTickUnitType(String name, int calendarField)
  {
    this.name = name;
    this.calendarField = calendarField;
  }
  




  public int getCalendarField()
  {
    return calendarField;
  }
  




  public String toString()
  {
    return name;
  }
  







  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof DateTickUnitType)) {
      return false;
    }
    DateTickUnitType t = (DateTickUnitType)obj;
    if (!name.equals(t.toString())) {
      return false;
    }
    return true;
  }
  





  private Object readResolve()
    throws ObjectStreamException
  {
    if (equals(YEAR)) {
      return YEAR;
    }
    if (equals(MONTH)) {
      return MONTH;
    }
    if (equals(DAY)) {
      return DAY;
    }
    if (equals(HOUR)) {
      return HOUR;
    }
    if (equals(MINUTE)) {
      return MINUTE;
    }
    if (equals(SECOND)) {
      return SECOND;
    }
    if (equals(MILLISECOND)) {
      return MILLISECOND;
    }
    return null;
  }
}
