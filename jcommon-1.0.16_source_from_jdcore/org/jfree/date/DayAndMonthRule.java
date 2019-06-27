package org.jfree.date;





















public class DayAndMonthRule
  extends AnnualDateRule
{
  private int dayOfMonth;
  


















  private int month;
  



















  public DayAndMonthRule()
  {
    this(1, 1);
  }
  












  public DayAndMonthRule(int dayOfMonth, int month)
  {
    setMonth(month);
    setDayOfMonth(dayOfMonth);
  }
  





  public int getDayOfMonth()
  {
    return dayOfMonth;
  }
  






  public void setDayOfMonth(int dayOfMonth)
  {
    if ((dayOfMonth < 1) || (dayOfMonth > SerialDate.LAST_DAY_OF_MONTH[month])) {
      throw new IllegalArgumentException("DayAndMonthRule(): dayOfMonth outside valid range.");
    }
    


    this.dayOfMonth = dayOfMonth;
  }
  









  public int getMonth()
  {
    return month;
  }
  






  public void setMonth(int month)
  {
    if (!SerialDate.isValidMonthCode(month)) {
      throw new IllegalArgumentException("DayAndMonthRule(): month code not valid.");
    }
    

    this.month = month;
  }
  







  public SerialDate getDate(int yyyy)
  {
    return SerialDate.createInstance(dayOfMonth, month, yyyy);
  }
}
