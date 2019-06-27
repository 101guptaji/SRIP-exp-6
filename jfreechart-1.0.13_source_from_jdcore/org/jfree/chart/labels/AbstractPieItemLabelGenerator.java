package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.NumberFormat;
import org.jfree.chart.HashUtilities;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.PieDataset;



































































public class AbstractPieItemLabelGenerator
  implements Serializable
{
  private static final long serialVersionUID = 7347703325267846275L;
  private String labelFormat;
  private NumberFormat numberFormat;
  private NumberFormat percentFormat;
  
  protected AbstractPieItemLabelGenerator(String labelFormat, NumberFormat numberFormat, NumberFormat percentFormat)
  {
    if (labelFormat == null) {
      throw new IllegalArgumentException("Null 'labelFormat' argument.");
    }
    if (numberFormat == null) {
      throw new IllegalArgumentException("Null 'numberFormat' argument.");
    }
    if (percentFormat == null) {
      throw new IllegalArgumentException("Null 'percentFormat' argument.");
    }
    
    this.labelFormat = labelFormat;
    this.numberFormat = numberFormat;
    this.percentFormat = percentFormat;
  }
  





  public String getLabelFormat()
  {
    return labelFormat;
  }
  




  public NumberFormat getNumberFormat()
  {
    return numberFormat;
  }
  




  public NumberFormat getPercentFormat()
  {
    return percentFormat;
  }
  















  protected Object[] createItemArray(PieDataset dataset, Comparable key)
  {
    Object[] result = new Object[4];
    double total = DatasetUtilities.calculatePieDatasetTotal(dataset);
    result[0] = key.toString();
    Number value = dataset.getValue(key);
    if (value != null) {
      result[1] = numberFormat.format(value);
    }
    else {
      result[1] = "null";
    }
    double percent = 0.0D;
    if (value != null) {
      double v = value.doubleValue();
      if (v > 0.0D) {
        percent = v / total;
      }
    }
    result[2] = percentFormat.format(percent);
    result[3] = numberFormat.format(total);
    return result;
  }
  







  protected String generateSectionLabel(PieDataset dataset, Comparable key)
  {
    String result = null;
    if (dataset != null) {
      Object[] items = createItemArray(dataset, key);
      result = MessageFormat.format(labelFormat, items);
    }
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AbstractPieItemLabelGenerator)) {
      return false;
    }
    
    AbstractPieItemLabelGenerator that = (AbstractPieItemLabelGenerator)obj;
    
    if (!labelFormat.equals(labelFormat)) {
      return false;
    }
    if (!numberFormat.equals(numberFormat)) {
      return false;
    }
    if (!percentFormat.equals(percentFormat)) {
      return false;
    }
    return true;
  }
  





  public int hashCode()
  {
    int result = 127;
    result = HashUtilities.hashCode(result, labelFormat);
    result = HashUtilities.hashCode(result, numberFormat);
    result = HashUtilities.hashCode(result, percentFormat);
    return result;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    AbstractPieItemLabelGenerator clone = (AbstractPieItemLabelGenerator)super.clone();
    
    if (numberFormat != null) {
      numberFormat = ((NumberFormat)numberFormat.clone());
    }
    if (percentFormat != null) {
      percentFormat = ((NumberFormat)percentFormat.clone());
    }
    return clone;
  }
}
