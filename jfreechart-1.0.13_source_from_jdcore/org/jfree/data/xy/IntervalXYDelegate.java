package org.jfree.data.xy;

import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.data.DomainInfo;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.util.PublicCloneable;


































































































public class IntervalXYDelegate
  implements DatasetChangeListener, DomainInfo, Serializable, Cloneable, PublicCloneable
{
  private static final long serialVersionUID = -685166711639592857L;
  private XYDataset dataset;
  private boolean autoWidth;
  private double intervalPositionFactor;
  private double fixedIntervalWidth;
  private double autoIntervalWidth;
  
  public IntervalXYDelegate(XYDataset dataset)
  {
    this(dataset, true);
  }
  






  public IntervalXYDelegate(XYDataset dataset, boolean autoWidth)
  {
    if (dataset == null) {
      throw new IllegalArgumentException("Null 'dataset' argument.");
    }
    this.dataset = dataset;
    this.autoWidth = autoWidth;
    intervalPositionFactor = 0.5D;
    autoIntervalWidth = Double.POSITIVE_INFINITY;
    fixedIntervalWidth = 1.0D;
  }
  





  public boolean isAutoWidth()
  {
    return autoWidth;
  }
  










  public void setAutoWidth(boolean b)
  {
    autoWidth = b;
    if (b) {
      autoIntervalWidth = recalculateInterval();
    }
  }
  




  public double getIntervalPositionFactor()
  {
    return intervalPositionFactor;
  }
  















  public void setIntervalPositionFactor(double d)
  {
    if ((d < 0.0D) || (1.0D < d)) {
      throw new IllegalArgumentException("Argument 'd' outside valid range.");
    }
    
    intervalPositionFactor = d;
  }
  




  public double getFixedIntervalWidth()
  {
    return fixedIntervalWidth;
  }
  










  public void setFixedIntervalWidth(double w)
  {
    if (w < 0.0D) {
      throw new IllegalArgumentException("Negative 'w' argument.");
    }
    fixedIntervalWidth = w;
    autoWidth = false;
  }
  






  public double getIntervalWidth()
  {
    if ((isAutoWidth()) && (!Double.isInfinite(autoIntervalWidth)))
    {

      return autoIntervalWidth;
    }
    

    return fixedIntervalWidth;
  }
  










  public Number getStartX(int series, int item)
  {
    Number startX = null;
    Number x = dataset.getX(series, item);
    if (x != null) {
      startX = new Double(x.doubleValue() - getIntervalPositionFactor() * getIntervalWidth());
    }
    
    return startX;
  }
  









  public double getStartXValue(int series, int item)
  {
    return dataset.getXValue(series, item) - getIntervalPositionFactor() * getIntervalWidth();
  }
  










  public Number getEndX(int series, int item)
  {
    Number endX = null;
    Number x = dataset.getX(series, item);
    if (x != null) {
      endX = new Double(x.doubleValue() + (1.0D - getIntervalPositionFactor()) * getIntervalWidth());
    }
    
    return endX;
  }
  









  public double getEndXValue(int series, int item)
  {
    return dataset.getXValue(series, item) + (1.0D - getIntervalPositionFactor()) * getIntervalWidth();
  }
  








  public double getDomainLowerBound(boolean includeInterval)
  {
    double result = NaN.0D;
    Range r = getDomainBounds(includeInterval);
    if (r != null) {
      result = r.getLowerBound();
    }
    return result;
  }
  







  public double getDomainUpperBound(boolean includeInterval)
  {
    double result = NaN.0D;
    Range r = getDomainBounds(includeInterval);
    if (r != null) {
      result = r.getUpperBound();
    }
    return result;
  }
  










  public Range getDomainBounds(boolean includeInterval)
  {
    Range range = DatasetUtilities.findDomainBounds(dataset, false);
    if ((includeInterval) && (range != null)) {
      double lowerAdj = getIntervalWidth() * getIntervalPositionFactor();
      double upperAdj = getIntervalWidth() - lowerAdj;
      range = new Range(range.getLowerBound() - lowerAdj, range.getUpperBound() + upperAdj);
    }
    
    return range;
  }
  










  public void datasetChanged(DatasetChangeEvent e)
  {
    if (autoWidth) {
      autoIntervalWidth = recalculateInterval();
    }
  }
  




  private double recalculateInterval()
  {
    double result = Double.POSITIVE_INFINITY;
    int seriesCount = dataset.getSeriesCount();
    for (int series = 0; series < seriesCount; series++) {
      result = Math.min(result, calculateIntervalForSeries(series));
    }
    return result;
  }
  






  private double calculateIntervalForSeries(int series)
  {
    double result = Double.POSITIVE_INFINITY;
    int itemCount = dataset.getItemCount(series);
    if (itemCount > 1) {
      double prev = dataset.getXValue(series, 0);
      for (int item = 1; item < itemCount; item++) {
        double x = dataset.getXValue(series, item);
        result = Math.min(result, x - prev);
        prev = x;
      }
    }
    return result;
  }
  










  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof IntervalXYDelegate)) {
      return false;
    }
    IntervalXYDelegate that = (IntervalXYDelegate)obj;
    if (autoWidth != autoWidth) {
      return false;
    }
    if (intervalPositionFactor != intervalPositionFactor) {
      return false;
    }
    if (fixedIntervalWidth != fixedIntervalWidth) {
      return false;
    }
    return true;
  }
  



  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  




  public int hashCode()
  {
    int hash = 5;
    hash = HashUtilities.hashCode(hash, autoWidth);
    hash = HashUtilities.hashCode(hash, intervalPositionFactor);
    hash = HashUtilities.hashCode(hash, fixedIntervalWidth);
    return hash;
  }
}
