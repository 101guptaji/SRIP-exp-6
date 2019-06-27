package org.jfree.chart.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

































































public class LogFormat
  extends NumberFormat
{
  private double base;
  private double baseLog;
  private String baseLabel;
  private String powerLabel;
  private boolean showBase;
  private NumberFormat formatter = new DecimalFormat("0.0#");
  




  public LogFormat()
  {
    this(10.0D, "10", true);
  }
  







  public LogFormat(double base, String baseLabel, boolean showBase)
  {
    this(base, baseLabel, "^", showBase);
  }
  











  public LogFormat(double base, String baseLabel, String powerLabel, boolean showBase)
  {
    if (baseLabel == null) {
      throw new IllegalArgumentException("Null 'baseLabel' argument.");
    }
    if (powerLabel == null) {
      throw new IllegalArgumentException("Null 'powerLabel' argument.");
    }
    this.base = base;
    baseLog = Math.log(this.base);
    this.baseLabel = baseLabel;
    this.showBase = showBase;
    this.powerLabel = powerLabel;
  }
  






  public NumberFormat getExponentFormat()
  {
    return (NumberFormat)formatter.clone();
  }
  






  public void setExponentFormat(NumberFormat format)
  {
    if (format == null) {
      throw new IllegalArgumentException("Null 'format' argument.");
    }
    formatter = format;
  }
  






  private double calculateLog(double value)
  {
    return Math.log(value) / baseLog;
  }
  









  public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos)
  {
    StringBuffer result = new StringBuffer();
    if (showBase) {
      result.append(baseLabel);
      result.append(powerLabel);
    }
    result.append(formatter.format(calculateLog(number)));
    return result;
  }
  










  public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos)
  {
    StringBuffer result = new StringBuffer();
    if (showBase) {
      result.append(baseLabel);
      result.append("^");
    }
    result.append(formatter.format(calculateLog(number)));
    return result;
  }
  








  public Number parse(String source, ParsePosition parsePosition)
  {
    return null;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof LogFormat)) {
      return false;
    }
    LogFormat that = (LogFormat)obj;
    if (base != base) {
      return false;
    }
    if (!baseLabel.equals(baseLabel)) {
      return false;
    }
    if (baseLog != baseLog) {
      return false;
    }
    if (showBase != showBase) {
      return false;
    }
    if (!formatter.equals(formatter)) {
      return false;
    }
    return super.equals(obj);
  }
  




  public Object clone()
  {
    LogFormat clone = (LogFormat)super.clone();
    formatter = ((NumberFormat)formatter.clone());
    return clone;
  }
}
