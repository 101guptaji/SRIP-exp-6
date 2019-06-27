package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import org.jfree.chart.HashUtilities;
import org.jfree.data.DataUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;



















































































public abstract class AbstractCategoryItemLabelGenerator
  implements PublicCloneable, Cloneable, Serializable
{
  private static final long serialVersionUID = -7108591260223293197L;
  private String labelFormat;
  private String nullValueString;
  private NumberFormat numberFormat;
  private DateFormat dateFormat;
  private NumberFormat percentFormat;
  
  protected AbstractCategoryItemLabelGenerator(String labelFormat, NumberFormat formatter)
  {
    this(labelFormat, formatter, NumberFormat.getPercentInstance());
  }
  











  protected AbstractCategoryItemLabelGenerator(String labelFormat, NumberFormat formatter, NumberFormat percentFormatter)
  {
    if (labelFormat == null) {
      throw new IllegalArgumentException("Null 'labelFormat' argument.");
    }
    if (formatter == null) {
      throw new IllegalArgumentException("Null 'formatter' argument.");
    }
    if (percentFormatter == null) {
      throw new IllegalArgumentException("Null 'percentFormatter' argument.");
    }
    
    this.labelFormat = labelFormat;
    numberFormat = formatter;
    percentFormat = percentFormatter;
    dateFormat = null;
    nullValueString = "-";
  }
  







  protected AbstractCategoryItemLabelGenerator(String labelFormat, DateFormat formatter)
  {
    if (labelFormat == null) {
      throw new IllegalArgumentException("Null 'labelFormat' argument.");
    }
    if (formatter == null) {
      throw new IllegalArgumentException("Null 'formatter' argument.");
    }
    this.labelFormat = labelFormat;
    numberFormat = null;
    percentFormat = NumberFormat.getPercentInstance();
    dateFormat = formatter;
    nullValueString = "-";
  }
  







  public String generateRowLabel(CategoryDataset dataset, int row)
  {
    return dataset.getRowKey(row).toString();
  }
  







  public String generateColumnLabel(CategoryDataset dataset, int column)
  {
    return dataset.getColumnKey(column).toString();
  }
  




  public String getLabelFormat()
  {
    return labelFormat;
  }
  




  public NumberFormat getNumberFormat()
  {
    return numberFormat;
  }
  




  public DateFormat getDateFormat()
  {
    return dateFormat;
  }
  









  protected String generateLabelString(CategoryDataset dataset, int row, int column)
  {
    if (dataset == null) {
      throw new IllegalArgumentException("Null 'dataset' argument.");
    }
    String result = null;
    Object[] items = createItemArray(dataset, row, column);
    result = MessageFormat.format(labelFormat, items);
    return result;
  }
  











  protected Object[] createItemArray(CategoryDataset dataset, int row, int column)
  {
    Object[] result = new Object[4];
    result[0] = dataset.getRowKey(row).toString();
    result[1] = dataset.getColumnKey(column).toString();
    Number value = dataset.getValue(row, column);
    if (value != null) {
      if (numberFormat != null) {
        result[2] = numberFormat.format(value);
      }
      else if (dateFormat != null) {
        result[2] = dateFormat.format(value);
      }
    }
    else {
      result[2] = nullValueString;
    }
    if (value != null) {
      double total = DataUtilities.calculateColumnTotal(dataset, column);
      double percent = value.doubleValue() / total;
      result[3] = percentFormat.format(percent);
    }
    
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AbstractCategoryItemLabelGenerator)) {
      return false;
    }
    
    AbstractCategoryItemLabelGenerator that = (AbstractCategoryItemLabelGenerator)obj;
    
    if (!labelFormat.equals(labelFormat)) {
      return false;
    }
    if (!ObjectUtilities.equal(dateFormat, dateFormat)) {
      return false;
    }
    if (!ObjectUtilities.equal(numberFormat, numberFormat)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 127;
    result = HashUtilities.hashCode(result, labelFormat);
    result = HashUtilities.hashCode(result, nullValueString);
    result = HashUtilities.hashCode(result, dateFormat);
    result = HashUtilities.hashCode(result, numberFormat);
    result = HashUtilities.hashCode(result, percentFormat);
    return result;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    AbstractCategoryItemLabelGenerator clone = (AbstractCategoryItemLabelGenerator)super.clone();
    
    if (numberFormat != null) {
      numberFormat = ((NumberFormat)numberFormat.clone());
    }
    if (dateFormat != null) {
      dateFormat = ((DateFormat)dateFormat.clone());
    }
    return clone;
  }
}
