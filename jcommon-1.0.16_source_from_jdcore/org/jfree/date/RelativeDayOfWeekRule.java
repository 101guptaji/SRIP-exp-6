package org.jfree.date;

















public class RelativeDayOfWeekRule
  extends AnnualDateRule
{
  private AnnualDateRule subrule;
  














  private int dayOfWeek;
  














  private int relative;
  















  public RelativeDayOfWeekRule()
  {
    this(new DayAndMonthRule(), 2, 1);
  }
  








  public RelativeDayOfWeekRule(AnnualDateRule subrule, int dayOfWeek, int relative)
  {
    this.subrule = subrule;
    this.dayOfWeek = dayOfWeek;
    this.relative = relative;
  }
  





  public AnnualDateRule getSubrule()
  {
    return subrule;
  }
  





  public void setSubrule(AnnualDateRule subrule)
  {
    this.subrule = subrule;
  }
  




  public int getDayOfWeek()
  {
    return dayOfWeek;
  }
  





  public void setDayOfWeek(int dayOfWeek)
  {
    this.dayOfWeek = dayOfWeek;
  }
  






  public int getRelative()
  {
    return relative;
  }
  






  public void setRelative(int relative)
  {
    this.relative = relative;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    RelativeDayOfWeekRule duplicate = (RelativeDayOfWeekRule)super.clone();
    
    subrule = ((AnnualDateRule)duplicate.getSubrule().clone());
    return duplicate;
  }
  









  public SerialDate getDate(int year)
  {
    if ((year < 1900) || (year > 9999))
    {
      throw new IllegalArgumentException("RelativeDayOfWeekRule.getDate(): year outside valid range.");
    }
    


    SerialDate result = null;
    SerialDate base = subrule.getDate(year);
    
    if (base != null) {
      switch (relative) {
      case -1: 
        result = SerialDate.getPreviousDayOfWeek(dayOfWeek, base);
        
        break;
      case 0: 
        result = SerialDate.getNearestDayOfWeek(dayOfWeek, base);
        
        break;
      case 1: 
        result = SerialDate.getFollowingDayOfWeek(dayOfWeek, base);
        
        break;
      }
      
    }
    
    return result;
  }
}
