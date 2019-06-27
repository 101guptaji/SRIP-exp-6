package org.jfree.data.xy;

import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.util.PublicCloneable;






























































public class XYBarDataset
  extends AbstractIntervalXYDataset
  implements IntervalXYDataset, DatasetChangeListener, PublicCloneable
{
  private XYDataset underlying;
  private double barWidth;
  
  public XYBarDataset(XYDataset underlying, double barWidth)
  {
    this.underlying = underlying;
    this.underlying.addChangeListener(this);
    this.barWidth = barWidth;
  }
  






  public XYDataset getUnderlyingDataset()
  {
    return underlying;
  }
  







  public double getBarWidth()
  {
    return barWidth;
  }
  








  public void setBarWidth(double barWidth)
  {
    this.barWidth = barWidth;
    notifyListeners(new DatasetChangeEvent(this, this));
  }
  




  public int getSeriesCount()
  {
    return underlying.getSeriesCount();
  }
  







  public Comparable getSeriesKey(int series)
  {
    return underlying.getSeriesKey(series);
  }
  






  public int getItemCount(int series)
  {
    return underlying.getItemCount(series);
  }
  









  public Number getX(int series, int item)
  {
    return underlying.getX(series, item);
  }
  









  public double getXValue(int series, int item)
  {
    return underlying.getXValue(series, item);
  }
  









  public Number getY(int series, int item)
  {
    return underlying.getY(series, item);
  }
  









  public double getYValue(int series, int item)
  {
    return underlying.getYValue(series, item);
  }
  







  public Number getStartX(int series, int item)
  {
    Number result = null;
    Number xnum = underlying.getX(series, item);
    if (xnum != null) {
      result = new Double(xnum.doubleValue() - barWidth / 2.0D);
    }
    return result;
  }
  










  public double getStartXValue(int series, int item)
  {
    return getXValue(series, item) - barWidth / 2.0D;
  }
  







  public Number getEndX(int series, int item)
  {
    Number result = null;
    Number xnum = underlying.getX(series, item);
    if (xnum != null) {
      result = new Double(xnum.doubleValue() + barWidth / 2.0D);
    }
    return result;
  }
  










  public double getEndXValue(int series, int item)
  {
    return getXValue(series, item) + barWidth / 2.0D;
  }
  







  public Number getStartY(int series, int item)
  {
    return underlying.getY(series, item);
  }
  










  public double getStartYValue(int series, int item)
  {
    return getYValue(series, item);
  }
  







  public Number getEndY(int series, int item)
  {
    return underlying.getY(series, item);
  }
  










  public double getEndYValue(int series, int item)
  {
    return getYValue(series, item);
  }
  




  public void datasetChanged(DatasetChangeEvent event)
  {
    notifyListeners(event);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYBarDataset)) {
      return false;
    }
    XYBarDataset that = (XYBarDataset)obj;
    if (!underlying.equals(underlying)) {
      return false;
    }
    if (barWidth != barWidth) {
      return false;
    }
    return true;
  }
  












  public Object clone()
    throws CloneNotSupportedException
  {
    XYBarDataset clone = (XYBarDataset)super.clone();
    if ((underlying instanceof PublicCloneable)) {
      PublicCloneable pc = (PublicCloneable)underlying;
      underlying = ((XYDataset)pc.clone());
    }
    return clone;
  }
}
