package org.jfree.chart.urls;

import java.io.Serializable;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.ObjectUtilities;





































































public class StandardXYURLGenerator
  implements XYURLGenerator, Serializable
{
  private static final long serialVersionUID = -1771624523496595382L;
  public static final String DEFAULT_PREFIX = "index.html";
  public static final String DEFAULT_SERIES_PARAMETER = "series";
  public static final String DEFAULT_ITEM_PARAMETER = "item";
  private String prefix;
  private String seriesParameterName;
  private String itemParameterName;
  
  public StandardXYURLGenerator()
  {
    this("index.html", "series", "item");
  }
  






  public StandardXYURLGenerator(String prefix)
  {
    this(prefix, "series", "item");
  }
  










  public StandardXYURLGenerator(String prefix, String seriesParameterName, String itemParameterName)
  {
    if (prefix == null) {
      throw new IllegalArgumentException("Null 'prefix' argument.");
    }
    if (seriesParameterName == null) {
      throw new IllegalArgumentException("Null 'seriesParameterName' argument.");
    }
    
    if (itemParameterName == null) {
      throw new IllegalArgumentException("Null 'itemParameterName' argument.");
    }
    
    this.prefix = prefix;
    this.seriesParameterName = seriesParameterName;
    this.itemParameterName = itemParameterName;
  }
  









  public String generateURL(XYDataset dataset, int series, int item)
  {
    String url = prefix;
    boolean firstParameter = url.indexOf("?") == -1;
    url = url + (firstParameter ? "?" : "&amp;");
    url = url + seriesParameterName + "=" + series + "&amp;" + itemParameterName + "=" + item;
    
    return url;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StandardXYURLGenerator)) {
      return false;
    }
    StandardXYURLGenerator that = (StandardXYURLGenerator)obj;
    if (!ObjectUtilities.equal(prefix, prefix)) {
      return false;
    }
    if (!ObjectUtilities.equal(seriesParameterName, seriesParameterName))
    {
      return false;
    }
    if (!ObjectUtilities.equal(itemParameterName, itemParameterName))
    {
      return false;
    }
    return true;
  }
}
