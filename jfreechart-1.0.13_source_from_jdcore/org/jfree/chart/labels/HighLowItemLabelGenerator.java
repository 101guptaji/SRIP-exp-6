package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import org.jfree.chart.HashUtilities;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.PublicCloneable;


































































public class HighLowItemLabelGenerator
  implements XYItemLabelGenerator, XYToolTipGenerator, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 5617111754832211830L;
  private DateFormat dateFormatter;
  private NumberFormat numberFormatter;
  
  public HighLowItemLabelGenerator()
  {
    this(DateFormat.getInstance(), NumberFormat.getInstance());
  }
  








  public HighLowItemLabelGenerator(DateFormat dateFormatter, NumberFormat numberFormatter)
  {
    if (dateFormatter == null) {
      throw new IllegalArgumentException("Null 'dateFormatter' argument.");
    }
    
    if (numberFormatter == null) {
      throw new IllegalArgumentException("Null 'numberFormatter' argument.");
    }
    
    this.dateFormatter = dateFormatter;
    this.numberFormatter = numberFormatter;
  }
  









  public String generateToolTip(XYDataset dataset, int series, int item)
  {
    String result = null;
    
    if ((dataset instanceof OHLCDataset)) {
      OHLCDataset d = (OHLCDataset)dataset;
      Number high = d.getHigh(series, item);
      Number low = d.getLow(series, item);
      Number open = d.getOpen(series, item);
      Number close = d.getClose(series, item);
      Number x = d.getX(series, item);
      
      result = d.getSeriesKey(series).toString();
      
      if (x != null) {
        Date date = new Date(x.longValue());
        result = result + "--> Date=" + dateFormatter.format(date);
        if (high != null) {
          result = result + " High=" + numberFormatter.format(high.doubleValue());
        }
        
        if (low != null) {
          result = result + " Low=" + numberFormatter.format(low.doubleValue());
        }
        
        if (open != null) {
          result = result + " Open=" + numberFormatter.format(open.doubleValue());
        }
        
        if (close != null) {
          result = result + " Close=" + numberFormatter.format(close.doubleValue());
        }
      }
    }
    


    return result;
  }
  










  public String generateLabel(XYDataset dataset, int series, int category)
  {
    return null;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    HighLowItemLabelGenerator clone = (HighLowItemLabelGenerator)super.clone();
    

    if (dateFormatter != null) {
      dateFormatter = ((DateFormat)dateFormatter.clone());
    }
    if (numberFormatter != null) {
      numberFormatter = ((NumberFormat)numberFormatter.clone());
    }
    
    return clone;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof HighLowItemLabelGenerator)) {
      return false;
    }
    HighLowItemLabelGenerator generator = (HighLowItemLabelGenerator)obj;
    if (!dateFormatter.equals(dateFormatter)) {
      return false;
    }
    if (!numberFormatter.equals(numberFormatter)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 127;
    result = HashUtilities.hashCode(result, dateFormatter);
    result = HashUtilities.hashCode(result, numberFormatter);
    return result;
  }
}
