package org.jfree.chart.urls;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import org.jfree.data.xy.XYDataset;

























































public class TimeSeriesURLGenerator
  implements XYURLGenerator, Serializable
{
  private static final long serialVersionUID = -9122773175671182445L;
  private DateFormat dateFormat = DateFormat.getInstance();
  

  private String prefix = "index.html";
  

  private String seriesParameterName = "series";
  

  private String itemParameterName = "item";
  









  public TimeSeriesURLGenerator() {}
  








  public TimeSeriesURLGenerator(DateFormat dateFormat, String prefix, String seriesParameterName, String itemParameterName)
  {
    if (dateFormat == null) {
      throw new IllegalArgumentException("Null 'dateFormat' argument.");
    }
    if (prefix == null) {
      throw new IllegalArgumentException("Null 'prefix' argument.");
    }
    if (seriesParameterName == null) {
      throw new IllegalArgumentException("Null 'seriesParameterName' argument.");
    }
    
    if (itemParameterName == null) {
      throw new IllegalArgumentException("Null 'itemParameterName' argument.");
    }
    

    this.dateFormat = ((DateFormat)dateFormat.clone());
    this.prefix = prefix;
    this.seriesParameterName = seriesParameterName;
    this.itemParameterName = itemParameterName;
  }
  







  public DateFormat getDateFormat()
  {
    return (DateFormat)dateFormat.clone();
  }
  






  public String getPrefix()
  {
    return prefix;
  }
  






  public String getSeriesParameterName()
  {
    return seriesParameterName;
  }
  






  public String getItemParameterName()
  {
    return itemParameterName;
  }
  








  public String generateURL(XYDataset dataset, int series, int item)
  {
    String result = prefix;
    boolean firstParameter = result.indexOf("?") == -1;
    Comparable seriesKey = dataset.getSeriesKey(series);
    if (seriesKey != null) {
      result = result + (firstParameter ? "?" : "&amp;");
      result = result + seriesParameterName + "=" + URLUtilities.encode(seriesKey.toString(), "UTF-8");
      
      firstParameter = false;
    }
    
    long x = dataset.getXValue(series, item);
    String xValue = dateFormat.format(new Date(x));
    result = result + (firstParameter ? "?" : "&amp;");
    result = result + itemParameterName + "=" + URLUtilities.encode(xValue, "UTF-8");
    

    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof TimeSeriesURLGenerator)) {
      return false;
    }
    TimeSeriesURLGenerator that = (TimeSeriesURLGenerator)obj;
    if (!dateFormat.equals(dateFormat)) {
      return false;
    }
    if (!itemParameterName.equals(itemParameterName)) {
      return false;
    }
    if (!prefix.equals(prefix)) {
      return false;
    }
    if (!seriesParameterName.equals(seriesParameterName)) {
      return false;
    }
    return true;
  }
}
