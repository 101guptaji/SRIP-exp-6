package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.MessageFormat;
import org.jfree.chart.HashUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.PublicCloneable;




























































public class StandardXYSeriesLabelGenerator
  implements XYSeriesLabelGenerator, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 1916017081848400024L;
  public static final String DEFAULT_LABEL_FORMAT = "{0}";
  private String formatPattern;
  
  public StandardXYSeriesLabelGenerator()
  {
    this("{0}");
  }
  




  public StandardXYSeriesLabelGenerator(String format)
  {
    if (format == null) {
      throw new IllegalArgumentException("Null 'format' argument.");
    }
    formatPattern = format;
  }
  








  public String generateLabel(XYDataset dataset, int series)
  {
    if (dataset == null) {
      throw new IllegalArgumentException("Null 'dataset' argument.");
    }
    String label = MessageFormat.format(formatPattern, createItemArray(dataset, series));
    

    return label;
  }
  








  protected Object[] createItemArray(XYDataset dataset, int series)
  {
    Object[] result = new Object[1];
    result[0] = dataset.getSeriesKey(series).toString();
    return result;
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StandardXYSeriesLabelGenerator)) {
      return false;
    }
    StandardXYSeriesLabelGenerator that = (StandardXYSeriesLabelGenerator)obj;
    
    if (!formatPattern.equals(formatPattern)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 127;
    result = HashUtilities.hashCode(result, formatPattern);
    return result;
  }
}
