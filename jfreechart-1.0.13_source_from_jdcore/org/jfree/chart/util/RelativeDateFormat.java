package org.jfree.chart.util;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;















































































































public class RelativeDateFormat
  extends DateFormat
{
  private long baseMillis;
  private boolean showZeroDays;
  private boolean showZeroHours;
  private NumberFormat dayFormatter;
  private String positivePrefix;
  private String daySuffix;
  private NumberFormat hourFormatter;
  private String hourSuffix;
  private NumberFormat minuteFormatter;
  private String minuteSuffix;
  private NumberFormat secondFormatter;
  private String secondSuffix;
  private static long MILLISECONDS_IN_ONE_HOUR = 3600000L;
  



  private static long MILLISECONDS_IN_ONE_DAY = 24L * MILLISECONDS_IN_ONE_HOUR;
  


  public RelativeDateFormat()
  {
    this(0L);
  }
  




  public RelativeDateFormat(Date time)
  {
    this(time.getTime());
  }
  





  public RelativeDateFormat(long baseMillis)
  {
    this.baseMillis = baseMillis;
    showZeroDays = false;
    showZeroHours = true;
    positivePrefix = "";
    dayFormatter = NumberFormat.getNumberInstance();
    daySuffix = "d";
    hourFormatter = NumberFormat.getNumberInstance();
    hourSuffix = "h";
    minuteFormatter = NumberFormat.getNumberInstance();
    minuteSuffix = "m";
    secondFormatter = NumberFormat.getNumberInstance();
    secondFormatter.setMaximumFractionDigits(3);
    secondFormatter.setMinimumFractionDigits(3);
    secondSuffix = "s";
    


    calendar = new GregorianCalendar();
    numberFormat = new DecimalFormat("0");
  }
  







  public long getBaseMillis()
  {
    return baseMillis;
  }
  








  public void setBaseMillis(long baseMillis)
  {
    this.baseMillis = baseMillis;
  }
  







  public boolean getShowZeroDays()
  {
    return showZeroDays;
  }
  







  public void setShowZeroDays(boolean show)
  {
    showZeroDays = show;
  }
  









  public boolean getShowZeroHours()
  {
    return showZeroHours;
  }
  









  public void setShowZeroHours(boolean show)
  {
    showZeroHours = show;
  }
  









  public String getPositivePrefix()
  {
    return positivePrefix;
  }
  









  public void setPositivePrefix(String prefix)
  {
    if (prefix == null) {
      throw new IllegalArgumentException("Null 'prefix' argument.");
    }
    positivePrefix = prefix;
  }
  






  public void setDayFormatter(NumberFormat formatter)
  {
    if (formatter == null) {
      throw new IllegalArgumentException("Null 'formatter' argument.");
    }
    dayFormatter = formatter;
  }
  






  public String getDaySuffix()
  {
    return daySuffix;
  }
  






  public void setDaySuffix(String suffix)
  {
    if (suffix == null) {
      throw new IllegalArgumentException("Null 'suffix' argument.");
    }
    daySuffix = suffix;
  }
  






  public void setHourFormatter(NumberFormat formatter)
  {
    if (formatter == null) {
      throw new IllegalArgumentException("Null 'formatter' argument.");
    }
    hourFormatter = formatter;
  }
  






  public String getHourSuffix()
  {
    return hourSuffix;
  }
  






  public void setHourSuffix(String suffix)
  {
    if (suffix == null) {
      throw new IllegalArgumentException("Null 'suffix' argument.");
    }
    hourSuffix = suffix;
  }
  






  public void setMinuteFormatter(NumberFormat formatter)
  {
    if (formatter == null) {
      throw new IllegalArgumentException("Null 'formatter' argument.");
    }
    minuteFormatter = formatter;
  }
  






  public String getMinuteSuffix()
  {
    return minuteSuffix;
  }
  






  public void setMinuteSuffix(String suffix)
  {
    if (suffix == null) {
      throw new IllegalArgumentException("Null 'suffix' argument.");
    }
    minuteSuffix = suffix;
  }
  






  public String getSecondSuffix()
  {
    return secondSuffix;
  }
  






  public void setSecondSuffix(String suffix)
  {
    if (suffix == null) {
      throw new IllegalArgumentException("Null 'suffix' argument.");
    }
    secondSuffix = suffix;
  }
  




  public void setSecondFormatter(NumberFormat formatter)
  {
    if (formatter == null) {
      throw new IllegalArgumentException("Null 'formatter' argument.");
    }
    secondFormatter = formatter;
  }
  










  public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition)
  {
    long currentMillis = date.getTime();
    long elapsed = currentMillis - baseMillis;
    String signPrefix;
    String signPrefix; if (elapsed < 0L) {
      elapsed *= -1L;
      signPrefix = "-";
    }
    else {
      signPrefix = positivePrefix;
    }
    
    long days = elapsed / MILLISECONDS_IN_ONE_DAY;
    elapsed -= days * MILLISECONDS_IN_ONE_DAY;
    long hours = elapsed / MILLISECONDS_IN_ONE_HOUR;
    elapsed -= hours * MILLISECONDS_IN_ONE_HOUR;
    long minutes = elapsed / 60000L;
    elapsed -= minutes * 60000L;
    double seconds = elapsed / 1000.0D;
    
    toAppendTo.append(signPrefix);
    if ((days != 0L) || (showZeroDays)) {
      toAppendTo.append(dayFormatter.format(days) + getDaySuffix());
    }
    if ((hours != 0L) || (showZeroHours)) {
      toAppendTo.append(hourFormatter.format(hours) + getHourSuffix());
    }
    
    toAppendTo.append(minuteFormatter.format(minutes) + getMinuteSuffix());
    
    toAppendTo.append(secondFormatter.format(seconds) + getSecondSuffix());
    
    return toAppendTo;
  }
  







  public Date parse(String source, ParsePosition pos)
  {
    return null;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof RelativeDateFormat)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    RelativeDateFormat that = (RelativeDateFormat)obj;
    if (baseMillis != baseMillis) {
      return false;
    }
    if (showZeroDays != showZeroDays) {
      return false;
    }
    if (showZeroHours != showZeroHours) {
      return false;
    }
    if (!positivePrefix.equals(positivePrefix)) {
      return false;
    }
    if (!daySuffix.equals(daySuffix)) {
      return false;
    }
    if (!hourSuffix.equals(hourSuffix)) {
      return false;
    }
    if (!minuteSuffix.equals(minuteSuffix)) {
      return false;
    }
    if (!secondSuffix.equals(secondSuffix)) {
      return false;
    }
    if (!dayFormatter.equals(dayFormatter)) {
      return false;
    }
    if (!hourFormatter.equals(hourFormatter)) {
      return false;
    }
    if (!minuteFormatter.equals(minuteFormatter)) {
      return false;
    }
    if (!secondFormatter.equals(secondFormatter)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 193;
    result = 37 * result + (int)(baseMillis ^ baseMillis >>> 32);
    
    result = 37 * result + positivePrefix.hashCode();
    result = 37 * result + daySuffix.hashCode();
    result = 37 * result + hourSuffix.hashCode();
    result = 37 * result + minuteSuffix.hashCode();
    result = 37 * result + secondSuffix.hashCode();
    result = 37 * result + secondFormatter.hashCode();
    return result;
  }
  




  public Object clone()
  {
    RelativeDateFormat clone = (RelativeDateFormat)super.clone();
    dayFormatter = ((NumberFormat)dayFormatter.clone());
    secondFormatter = ((NumberFormat)secondFormatter.clone());
    return clone;
  }
  




  public static void main(String[] args)
  {
    GregorianCalendar c0 = new GregorianCalendar(2006, 10, 1, 0, 0, 0);
    GregorianCalendar c1 = new GregorianCalendar(2006, 10, 1, 11, 37, 43);
    c1.set(14, 123);
    
    System.out.println("Default: ");
    RelativeDateFormat rdf = new RelativeDateFormat(c0.getTime().getTime());
    System.out.println(rdf.format(c1.getTime()));
    System.out.println();
    
    System.out.println("Hide milliseconds: ");
    rdf.setSecondFormatter(new DecimalFormat("0"));
    System.out.println(rdf.format(c1.getTime()));
    System.out.println();
    
    System.out.println("Show zero day output: ");
    rdf.setShowZeroDays(true);
    System.out.println(rdf.format(c1.getTime()));
    System.out.println();
    
    System.out.println("Alternative suffixes: ");
    rdf.setShowZeroDays(false);
    rdf.setDaySuffix(":");
    rdf.setHourSuffix(":");
    rdf.setMinuteSuffix(":");
    rdf.setSecondSuffix("");
    System.out.println(rdf.format(c1.getTime()));
    System.out.println();
  }
}
