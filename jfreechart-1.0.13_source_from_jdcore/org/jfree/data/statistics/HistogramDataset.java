package org.jfree.data.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.xy.AbstractIntervalXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;









































































public class HistogramDataset
  extends AbstractIntervalXYDataset
  implements IntervalXYDataset, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -6341668077370231153L;
  private List list;
  private HistogramType type;
  
  public HistogramDataset()
  {
    list = new ArrayList();
    type = HistogramType.FREQUENCY;
  }
  




  public HistogramType getType()
  {
    return type;
  }
  





  public void setType(HistogramType type)
  {
    if (type == null) {
      throw new IllegalArgumentException("Null 'type' argument");
    }
    this.type = type;
    notifyListeners(new DatasetChangeEvent(this, this));
  }
  







  public void addSeries(Comparable key, double[] values, int bins)
  {
    double minimum = getMinimum(values);
    double maximum = getMaximum(values);
    addSeries(key, values, bins, minimum, maximum);
  }
  
















  public void addSeries(Comparable key, double[] values, int bins, double minimum, double maximum)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    if (values == null) {
      throw new IllegalArgumentException("Null 'values' argument.");
    }
    if (bins < 1) {
      throw new IllegalArgumentException("The 'bins' value must be at least 1.");
    }
    
    double binWidth = (maximum - minimum) / bins;
    
    double lower = minimum;
    
    List binList = new ArrayList(bins);
    for (int i = 0; i < bins; i++)
    {
      HistogramBin bin;
      
      HistogramBin bin;
      if (i == bins - 1) {
        bin = new HistogramBin(lower, maximum);
      }
      else {
        double upper = minimum + (i + 1) * binWidth;
        bin = new HistogramBin(lower, upper);
        lower = upper;
      }
      binList.add(bin);
    }
    
    for (int i = 0; i < values.length; i++) {
      int binIndex = bins - 1;
      if (values[i] < maximum) {
        double fraction = (values[i] - minimum) / (maximum - minimum);
        if (fraction < 0.0D) {
          fraction = 0.0D;
        }
        binIndex = (int)(fraction * bins);
        


        if (binIndex >= bins) {
          binIndex = bins - 1;
        }
      }
      HistogramBin bin = (HistogramBin)binList.get(binIndex);
      bin.incrementCount();
    }
    
    Map map = new HashMap();
    map.put("key", key);
    map.put("bins", binList);
    map.put("values.length", new Integer(values.length));
    map.put("bin width", new Double(binWidth));
    list.add(map);
  }
  







  private double getMinimum(double[] values)
  {
    if ((values == null) || (values.length < 1)) {
      throw new IllegalArgumentException("Null or zero length 'values' argument.");
    }
    
    double min = Double.MAX_VALUE;
    for (int i = 0; i < values.length; i++) {
      if (values[i] < min) {
        min = values[i];
      }
    }
    return min;
  }
  







  private double getMaximum(double[] values)
  {
    if ((values == null) || (values.length < 1)) {
      throw new IllegalArgumentException("Null or zero length 'values' argument.");
    }
    
    double max = -1.7976931348623157E308D;
    for (int i = 0; i < values.length; i++) {
      if (values[i] > max) {
        max = values[i];
      }
    }
    return max;
  }
  










  List getBins(int series)
  {
    Map map = (Map)list.get(series);
    return (List)map.get("bins");
  }
  






  private int getTotal(int series)
  {
    Map map = (Map)list.get(series);
    return ((Integer)map.get("values.length")).intValue();
  }
  






  private double getBinWidth(int series)
  {
    Map map = (Map)list.get(series);
    return ((Double)map.get("bin width")).doubleValue();
  }
  




  public int getSeriesCount()
  {
    return list.size();
  }
  










  public Comparable getSeriesKey(int series)
  {
    Map map = (Map)list.get(series);
    return (Comparable)map.get("key");
  }
  










  public int getItemCount(int series)
  {
    return getBins(series).size();
  }
  














  public Number getX(int series, int item)
  {
    List bins = getBins(series);
    HistogramBin bin = (HistogramBin)bins.get(item);
    double x = (bin.getStartBoundary() + bin.getEndBoundary()) / 2.0D;
    return new Double(x);
  }
  












  public Number getY(int series, int item)
  {
    List bins = getBins(series);
    HistogramBin bin = (HistogramBin)bins.get(item);
    double total = getTotal(series);
    double binWidth = getBinWidth(series);
    
    if (type == HistogramType.FREQUENCY) {
      return new Double(bin.getCount());
    }
    if (type == HistogramType.RELATIVE_FREQUENCY) {
      return new Double(bin.getCount() / total);
    }
    if (type == HistogramType.SCALE_AREA_TO_1) {
      return new Double(bin.getCount() / (binWidth * total));
    }
    
    throw new IllegalStateException();
  }
  












  public Number getStartX(int series, int item)
  {
    List bins = getBins(series);
    HistogramBin bin = (HistogramBin)bins.get(item);
    return new Double(bin.getStartBoundary());
  }
  











  public Number getEndX(int series, int item)
  {
    List bins = getBins(series);
    HistogramBin bin = (HistogramBin)bins.get(item);
    return new Double(bin.getEndBoundary());
  }
  













  public Number getStartY(int series, int item)
  {
    return getY(series, item);
  }
  













  public Number getEndY(int series, int item)
  {
    return getY(series, item);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof HistogramDataset)) {
      return false;
    }
    HistogramDataset that = (HistogramDataset)obj;
    if (!ObjectUtilities.equal(type, type)) {
      return false;
    }
    if (!ObjectUtilities.equal(list, list)) {
      return false;
    }
    return true;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    HistogramDataset clone = (HistogramDataset)super.clone();
    int seriesCount = getSeriesCount();
    list = new ArrayList(seriesCount);
    for (int i = 0; i < seriesCount; i++) {
      list.add(new HashMap((Map)list.get(i)));
    }
    return clone;
  }
}
