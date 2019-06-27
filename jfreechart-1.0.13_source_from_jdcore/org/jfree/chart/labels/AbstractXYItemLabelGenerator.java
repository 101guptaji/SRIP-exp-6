package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import org.jfree.chart.HashUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.ObjectUtilities;







































































public class AbstractXYItemLabelGenerator
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 5869744396278660636L;
  private String formatString;
  private NumberFormat xFormat;
  private DateFormat xDateFormat;
  private NumberFormat yFormat;
  private DateFormat yDateFormat;
  private String nullYString = "null";
  


  protected AbstractXYItemLabelGenerator()
  {
    this("{2}", NumberFormat.getNumberInstance(), NumberFormat.getNumberInstance());
  }
  













  protected AbstractXYItemLabelGenerator(String formatString, NumberFormat xFormat, NumberFormat yFormat)
  {
    if (formatString == null) {
      throw new IllegalArgumentException("Null 'formatString' argument.");
    }
    if (xFormat == null) {
      throw new IllegalArgumentException("Null 'xFormat' argument.");
    }
    if (yFormat == null) {
      throw new IllegalArgumentException("Null 'yFormat' argument.");
    }
    this.formatString = formatString;
    this.xFormat = xFormat;
    this.yFormat = yFormat;
  }
  













  protected AbstractXYItemLabelGenerator(String formatString, DateFormat xFormat, NumberFormat yFormat)
  {
    this(formatString, NumberFormat.getInstance(), yFormat);
    xDateFormat = xFormat;
  }
  
















  protected AbstractXYItemLabelGenerator(String formatString, NumberFormat xFormat, DateFormat yFormat)
  {
    this(formatString, xFormat, NumberFormat.getInstance());
    yDateFormat = yFormat;
  }
  












  protected AbstractXYItemLabelGenerator(String formatString, DateFormat xFormat, DateFormat yFormat)
  {
    this(formatString, NumberFormat.getInstance(), NumberFormat.getInstance());
    
    xDateFormat = xFormat;
    yDateFormat = yFormat;
  }
  






  public String getFormatString()
  {
    return formatString;
  }
  




  public NumberFormat getXFormat()
  {
    return xFormat;
  }
  




  public DateFormat getXDateFormat()
  {
    return xDateFormat;
  }
  




  public NumberFormat getYFormat()
  {
    return yFormat;
  }
  




  public DateFormat getYDateFormat()
  {
    return yDateFormat;
  }
  








  public String generateLabelString(XYDataset dataset, int series, int item)
  {
    String result = null;
    Object[] items = createItemArray(dataset, series, item);
    result = MessageFormat.format(formatString, items);
    return result;
  }
  






  public String getNullYString()
  {
    return nullYString;
  }
  











  protected Object[] createItemArray(XYDataset dataset, int series, int item)
  {
    Object[] result = new Object[3];
    result[0] = dataset.getSeriesKey(series).toString();
    
    double x = dataset.getXValue(series, item);
    if (xDateFormat != null) {
      result[1] = xDateFormat.format(new Date(x));
    }
    else {
      result[1] = xFormat.format(x);
    }
    
    double y = dataset.getYValue(series, item);
    if ((Double.isNaN(y)) && (dataset.getY(series, item) == null)) {
      result[2] = nullYString;

    }
    else if (yDateFormat != null) {
      result[2] = yDateFormat.format(new Date(y));
    }
    else {
      result[2] = yFormat.format(y);
    }
    
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AbstractXYItemLabelGenerator)) {
      return false;
    }
    AbstractXYItemLabelGenerator that = (AbstractXYItemLabelGenerator)obj;
    if (!formatString.equals(formatString)) {
      return false;
    }
    if (!ObjectUtilities.equal(xFormat, xFormat)) {
      return false;
    }
    if (!ObjectUtilities.equal(xDateFormat, xDateFormat)) {
      return false;
    }
    if (!ObjectUtilities.equal(yFormat, yFormat)) {
      return false;
    }
    if (!ObjectUtilities.equal(yDateFormat, yDateFormat)) {
      return false;
    }
    if (!nullYString.equals(nullYString)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 127;
    result = HashUtilities.hashCode(result, formatString);
    result = HashUtilities.hashCode(result, xFormat);
    result = HashUtilities.hashCode(result, xDateFormat);
    result = HashUtilities.hashCode(result, yFormat);
    result = HashUtilities.hashCode(result, yDateFormat);
    return result;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    AbstractXYItemLabelGenerator clone = (AbstractXYItemLabelGenerator)super.clone();
    
    if (xFormat != null) {
      xFormat = ((NumberFormat)xFormat.clone());
    }
    if (yFormat != null) {
      yFormat = ((NumberFormat)yFormat.clone());
    }
    if (xDateFormat != null) {
      xDateFormat = ((DateFormat)xDateFormat.clone());
    }
    if (yDateFormat != null) {
      yDateFormat = ((DateFormat)yDateFormat.clone());
    }
    return clone;
  }
}
