package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import org.jfree.chart.HashUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;




































































public class BubbleXYItemLabelGenerator
  extends AbstractXYItemLabelGenerator
  implements XYItemLabelGenerator, PublicCloneable, Serializable
{
  static final long serialVersionUID = -8458568928021240922L;
  public static final String DEFAULT_FORMAT_STRING = "{3}";
  private NumberFormat zFormat;
  private DateFormat zDateFormat;
  
  public BubbleXYItemLabelGenerator()
  {
    this("{3}", NumberFormat.getNumberInstance(), NumberFormat.getNumberInstance(), NumberFormat.getNumberInstance());
  }
  














  public BubbleXYItemLabelGenerator(String formatString, NumberFormat xFormat, NumberFormat yFormat, NumberFormat zFormat)
  {
    super(formatString, xFormat, yFormat);
    if (zFormat == null) {
      throw new IllegalArgumentException("Null 'zFormat' argument.");
    }
    this.zFormat = zFormat;
  }
  












  public BubbleXYItemLabelGenerator(String formatString, DateFormat xFormat, DateFormat yFormat, DateFormat zFormat)
  {
    super(formatString, xFormat, yFormat);
    if (zFormat == null) {
      throw new IllegalArgumentException("Null 'zFormat' argument.");
    }
    zDateFormat = zFormat;
  }
  




  public NumberFormat getZFormat()
  {
    return zFormat;
  }
  




  public DateFormat getZDateFormat()
  {
    return zDateFormat;
  }
  








  public String generateLabel(XYDataset dataset, int series, int item)
  {
    return generateLabelString(dataset, series, item);
  }
  








  public String generateLabelString(XYDataset dataset, int series, int item)
  {
    String result = null;
    Object[] items = null;
    if ((dataset instanceof XYZDataset)) {
      items = createItemArray((XYZDataset)dataset, series, item);
    }
    else {
      items = createItemArray(dataset, series, item);
    }
    result = MessageFormat.format(getFormatString(), items);
    return result;
  }
  











  protected Object[] createItemArray(XYZDataset dataset, int series, int item)
  {
    Object[] result = new Object[4];
    result[0] = dataset.getSeriesKey(series).toString();
    
    Number x = dataset.getX(series, item);
    DateFormat xf = getXDateFormat();
    if (xf != null) {
      result[1] = xf.format(x);
    }
    else {
      result[1] = getXFormat().format(x);
    }
    
    Number y = dataset.getY(series, item);
    DateFormat yf = getYDateFormat();
    if (yf != null) {
      result[2] = yf.format(y);
    }
    else {
      result[2] = getYFormat().format(y);
    }
    
    Number z = dataset.getZ(series, item);
    if (zDateFormat != null) {
      result[3] = zDateFormat.format(z);
    }
    else {
      result[3] = zFormat.format(z);
    }
    
    return result;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BubbleXYItemLabelGenerator)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    BubbleXYItemLabelGenerator that = (BubbleXYItemLabelGenerator)obj;
    if (!ObjectUtilities.equal(zFormat, zFormat)) {
      return false;
    }
    if (!ObjectUtilities.equal(zDateFormat, zDateFormat)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int h = super.hashCode();
    h = HashUtilities.hashCode(h, zFormat);
    h = HashUtilities.hashCode(h, zDateFormat);
    return h;
  }
}
