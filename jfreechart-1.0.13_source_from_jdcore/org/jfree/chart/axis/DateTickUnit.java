package org.jfree.chart.axis;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.jfree.util.ObjectUtilities;

















































public class DateTickUnit
  extends TickUnit
  implements Serializable
{
  private static final long serialVersionUID = -7289292157229621901L;
  private DateTickUnitType unitType;
  private int count;
  private DateTickUnitType rollUnitType;
  private int rollCount;
  private DateFormat formatter;
  /**
   * @deprecated
   */
  public static final int YEAR = 0;
  /**
   * @deprecated
   */
  public static final int MONTH = 1;
  /**
   * @deprecated
   */
  public static final int DAY = 2;
  /**
   * @deprecated
   */
  public static final int HOUR = 3;
  /**
   * @deprecated
   */
  public static final int MINUTE = 4;
  /**
   * @deprecated
   */
  public static final int SECOND = 5;
  /**
   * @deprecated
   */
  public static final int MILLISECOND = 6;
  /**
   * @deprecated
   */
  private int unit;
  /**
   * @deprecated
   */
  private int rollUnit;
  
  public DateTickUnit(DateTickUnitType unitType, int multiple)
  {
    this(unitType, multiple, DateFormat.getDateInstance(3));
  }
  









  public DateTickUnit(DateTickUnitType unitType, int multiple, DateFormat formatter)
  {
    this(unitType, multiple, unitType, multiple, formatter);
  }
  












  public DateTickUnit(DateTickUnitType unitType, int multiple, DateTickUnitType rollUnitType, int rollMultiple, DateFormat formatter)
  {
    super(getMillisecondCount(unitType, multiple));
    if (formatter == null) {
      throw new IllegalArgumentException("Null 'formatter' argument.");
    }
    if (multiple <= 0) {
      throw new IllegalArgumentException("Requires 'multiple' > 0.");
    }
    if (rollMultiple <= 0) {
      throw new IllegalArgumentException("Requires 'rollMultiple' > 0.");
    }
    this.unitType = unitType;
    count = multiple;
    this.rollUnitType = rollUnitType;
    rollCount = rollMultiple;
    this.formatter = formatter;
    

    unit = unitTypeToInt(unitType);
    rollUnit = unitTypeToInt(rollUnitType);
  }
  






  public DateTickUnitType getUnitType()
  {
    return unitType;
  }
  




  public int getMultiple()
  {
    return count;
  }
  






  public DateTickUnitType getRollUnitType()
  {
    return rollUnitType;
  }
  






  public int getRollMultiple()
  {
    return rollCount;
  }
  






  public String valueToString(double milliseconds)
  {
    return formatter.format(new Date(milliseconds));
  }
  






  public String dateToString(Date date)
  {
    return formatter.format(date);
  }
  













  public Date addToDate(Date base, TimeZone zone)
  {
    Calendar calendar = Calendar.getInstance(zone);
    calendar.setTime(base);
    calendar.add(unitType.getCalendarField(), count);
    return calendar.getTime();
  }
  









  public Date rollDate(Date base)
  {
    return rollDate(base, TimeZone.getDefault());
  }
  














  public Date rollDate(Date base, TimeZone zone)
  {
    Calendar calendar = Calendar.getInstance(zone);
    calendar.setTime(base);
    calendar.add(rollUnitType.getCalendarField(), rollCount);
    return calendar.getTime();
  }
  





  public int getCalendarField()
  {
    return unitType.getCalendarField();
  }
  














  private static long getMillisecondCount(DateTickUnitType unit, int count)
  {
    if (unit.equals(DateTickUnitType.YEAR)) {
      return 31536000000L * count;
    }
    if (unit.equals(DateTickUnitType.MONTH)) {
      return 2678400000L * count;
    }
    if (unit.equals(DateTickUnitType.DAY)) {
      return 86400000L * count;
    }
    if (unit.equals(DateTickUnitType.HOUR)) {
      return 3600000L * count;
    }
    if (unit.equals(DateTickUnitType.MINUTE)) {
      return 60000L * count;
    }
    if (unit.equals(DateTickUnitType.SECOND)) {
      return 1000L * count;
    }
    if (unit.equals(DateTickUnitType.MILLISECOND)) {
      return count;
    }
    
    throw new IllegalArgumentException("The 'unit' argument has a value that is not recognised.");
  }
  












  private static DateTickUnitType intToUnitType(int unit)
  {
    switch (unit) {
    case 0:  return DateTickUnitType.YEAR;
    case 1:  return DateTickUnitType.MONTH;
    case 2:  return DateTickUnitType.DAY;
    case 3:  return DateTickUnitType.HOUR;
    case 4:  return DateTickUnitType.MINUTE;
    case 5:  return DateTickUnitType.SECOND;
    case 6:  return DateTickUnitType.MILLISECOND; }
    throw new IllegalArgumentException("Unrecognised 'unit' value " + unit + ".");
  }
  










  private static int unitTypeToInt(DateTickUnitType unitType)
  {
    if (unitType == null) {
      throw new IllegalArgumentException("Null 'unitType' argument.");
    }
    if (unitType.equals(DateTickUnitType.YEAR)) {
      return 0;
    }
    if (unitType.equals(DateTickUnitType.MONTH)) {
      return 1;
    }
    if (unitType.equals(DateTickUnitType.DAY)) {
      return 2;
    }
    if (unitType.equals(DateTickUnitType.HOUR)) {
      return 3;
    }
    if (unitType.equals(DateTickUnitType.MINUTE)) {
      return 4;
    }
    if (unitType.equals(DateTickUnitType.SECOND)) {
      return 5;
    }
    if (unitType.equals(DateTickUnitType.MILLISECOND)) {
      return 6;
    }
    
    throw new IllegalArgumentException("The 'unitType' is not recognised");
  }
  









  private static DateFormat notNull(DateFormat formatter)
  {
    if (formatter == null) {
      return DateFormat.getDateInstance(3);
    }
    
    return formatter;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DateTickUnit)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    DateTickUnit that = (DateTickUnit)obj;
    if (!unitType.equals(unitType)) {
      return false;
    }
    if (count != count) {
      return false;
    }
    if (!ObjectUtilities.equal(formatter, formatter)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 19;
    result = 37 * result + unitType.hashCode();
    result = 37 * result + count;
    result = 37 * result + formatter.hashCode();
    return result;
  }
  





  public String toString()
  {
    return "DateTickUnit[" + unitType.toString() + ", " + count + "]";
  }
  








































































  /**
   * @deprecated
   */
  public DateTickUnit(int unit, int count, DateFormat formatter)
  {
    this(unit, count, unit, count, formatter);
  }
  






  /**
   * @deprecated
   */
  public DateTickUnit(int unit, int count)
  {
    this(unit, count, null);
  }
  









  /**
   * @deprecated
   */
  public DateTickUnit(int unit, int count, int rollUnit, int rollCount, DateFormat formatter)
  {
    this(intToUnitType(unit), count, intToUnitType(rollUnit), rollCount, notNull(formatter));
  }
  









  /**
   * @deprecated
   */
  public int getUnit()
  {
    return unit;
  }
  



  /**
   * @deprecated
   */
  public int getCount()
  {
    return count;
  }
  






  /**
   * @deprecated
   */
  public int getRollUnit()
  {
    return rollUnit;
  }
  




  /**
   * @deprecated
   */
  public int getRollCount()
  {
    return rollCount;
  }
  









  /**
   * @deprecated
   */
  public Date addToDate(Date base)
  {
    return addToDate(base, TimeZone.getDefault());
  }
}
