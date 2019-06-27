package org.jfree.date;















public class DayOfWeekInMonthRule
  extends AnnualDateRule
{
  private int count;
  













  private int dayOfWeek;
  













  private int month;
  














  public DayOfWeekInMonthRule()
  {
    this(1, 2, 1);
  }
  






  public DayOfWeekInMonthRule(int count, int dayOfWeek, int month)
  {
    this.count = count;
    this.dayOfWeek = dayOfWeek;
    this.month = month;
  }
  




  public int getCount()
  {
    return count;
  }
  




  public void setCount(int count)
  {
    this.count = count;
  }
  




  public int getDayOfWeek()
  {
    return dayOfWeek;
  }
  




  public void setDayOfWeek(int dayOfWeek)
  {
    this.dayOfWeek = dayOfWeek;
  }
  




  public int getMonth()
  {
    return month;
  }
  




  public void setMonth(int month)
  {
    this.month = month;
  }
  



  public SerialDate getDate(int year)
  {
    SerialDate result;
    


    if (count != 0)
    {
      SerialDate result = SerialDate.createInstance(1, month, year);
      while (result.getDayOfWeek() != dayOfWeek) {
        result = SerialDate.addDays(1, result);
      }
      result = SerialDate.addDays(7 * (count - 1), result);

    }
    else
    {
      result = SerialDate.createInstance(1, month, year);
      result = result.getEndOfCurrentMonth(result);
      while (result.getDayOfWeek() != dayOfWeek) {
        result = SerialDate.addDays(-1, result);
      }
    }
    
    return result;
  }
}
