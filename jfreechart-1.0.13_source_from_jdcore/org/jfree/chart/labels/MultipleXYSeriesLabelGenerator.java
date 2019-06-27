package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jfree.chart.HashUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.PublicCloneable;



























































public class MultipleXYSeriesLabelGenerator
  implements XYSeriesLabelGenerator, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 138976236941898560L;
  public static final String DEFAULT_LABEL_FORMAT = "{0}";
  private String formatPattern;
  private String additionalFormatPattern;
  private Map seriesLabelLists;
  
  public MultipleXYSeriesLabelGenerator()
  {
    this("{0}");
  }
  




  public MultipleXYSeriesLabelGenerator(String format)
  {
    if (format == null) {
      throw new IllegalArgumentException("Null 'format' argument.");
    }
    formatPattern = format;
    additionalFormatPattern = "\n{0}";
    seriesLabelLists = new HashMap();
  }
  





  public void addSeriesLabel(int series, String label)
  {
    Integer key = new Integer(series);
    List labelList = (List)seriesLabelLists.get(key);
    if (labelList == null) {
      labelList = new ArrayList();
      seriesLabelLists.put(key, labelList);
    }
    labelList.add(label);
  }
  




  public void clearSeriesLabels(int series)
  {
    Integer key = new Integer(series);
    seriesLabelLists.put(key, null);
  }
  








  public String generateLabel(XYDataset dataset, int series)
  {
    if (dataset == null) {
      throw new IllegalArgumentException("Null 'dataset' argument.");
    }
    StringBuffer label = new StringBuffer();
    label.append(MessageFormat.format(formatPattern, createItemArray(dataset, series)));
    
    Integer key = new Integer(series);
    List extraLabels = (List)seriesLabelLists.get(key);
    if (extraLabels != null) {
      Object[] temp = new Object[1];
      for (int i = 0; i < extraLabels.size(); i++) {
        temp[0] = extraLabels.get(i);
        String labelAddition = MessageFormat.format(additionalFormatPattern, temp);
        
        label.append(labelAddition);
      }
    }
    return label.toString();
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
    MultipleXYSeriesLabelGenerator clone = (MultipleXYSeriesLabelGenerator)super.clone();
    
    seriesLabelLists = new HashMap();
    Set keys = seriesLabelLists.keySet();
    Iterator iterator = keys.iterator();
    while (iterator.hasNext()) {
      Object key = iterator.next();
      Object entry = seriesLabelLists.get(key);
      Object toAdd = entry;
      if ((entry instanceof PublicCloneable)) {
        PublicCloneable pc = (PublicCloneable)entry;
        toAdd = pc.clone();
      }
      seriesLabelLists.put(key, toAdd);
    }
    return clone;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MultipleXYSeriesLabelGenerator)) {
      return false;
    }
    MultipleXYSeriesLabelGenerator that = (MultipleXYSeriesLabelGenerator)obj;
    
    if (!formatPattern.equals(formatPattern)) {
      return false;
    }
    if (!additionalFormatPattern.equals(additionalFormatPattern))
    {
      return false;
    }
    if (!seriesLabelLists.equals(seriesLabelLists)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 127;
    result = HashUtilities.hashCode(result, formatPattern);
    result = HashUtilities.hashCode(result, additionalFormatPattern);
    result = HashUtilities.hashCode(result, seriesLabelLists);
    return result;
  }
}
