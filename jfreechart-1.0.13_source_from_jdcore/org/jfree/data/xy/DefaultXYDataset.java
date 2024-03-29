package org.jfree.data.xy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.util.PublicCloneable;































































public class DefaultXYDataset
  extends AbstractXYDataset
  implements XYDataset, PublicCloneable
{
  private List seriesKeys;
  private List seriesList;
  
  public DefaultXYDataset()
  {
    seriesKeys = new ArrayList();
    seriesList = new ArrayList();
  }
  




  public int getSeriesCount()
  {
    return seriesList.size();
  }
  










  public Comparable getSeriesKey(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds");
    }
    return (Comparable)seriesKeys.get(series);
  }
  







  public int indexOf(Comparable seriesKey)
  {
    return seriesKeys.indexOf(seriesKey);
  }
  






  public DomainOrder getDomainOrder()
  {
    return DomainOrder.NONE;
  }
  










  public int getItemCount(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds");
    }
    double[][] seriesArray = (double[][])seriesList.get(series);
    return seriesArray[0].length;
  }
  
















  public double getXValue(int series, int item)
  {
    double[][] seriesData = (double[][])seriesList.get(series);
    return seriesData[0][item];
  }
  
















  public Number getX(int series, int item)
  {
    return new Double(getXValue(series, item));
  }
  
















  public double getYValue(int series, int item)
  {
    double[][] seriesData = (double[][])seriesList.get(series);
    return seriesData[1][item];
  }
  
















  public Number getY(int series, int item)
  {
    return new Double(getYValue(series, item));
  }
  









  public void addSeries(Comparable seriesKey, double[][] data)
  {
    if (seriesKey == null) {
      throw new IllegalArgumentException("The 'seriesKey' cannot be null.");
    }
    
    if (data == null) {
      throw new IllegalArgumentException("The 'data' is null.");
    }
    if (data.length != 2) {
      throw new IllegalArgumentException("The 'data' array must have length == 2.");
    }
    
    if (data[0].length != data[1].length) {
      throw new IllegalArgumentException("The 'data' array must contain two arrays with equal length.");
    }
    
    int seriesIndex = indexOf(seriesKey);
    if (seriesIndex == -1) {
      seriesKeys.add(seriesKey);
      seriesList.add(data);
    }
    else {
      seriesList.remove(seriesIndex);
      seriesList.add(seriesIndex, data);
    }
    notifyListeners(new DatasetChangeEvent(this, this));
  }
  






  public void removeSeries(Comparable seriesKey)
  {
    int seriesIndex = indexOf(seriesKey);
    if (seriesIndex >= 0) {
      seriesKeys.remove(seriesIndex);
      seriesList.remove(seriesIndex);
      notifyListeners(new DatasetChangeEvent(this, this));
    }
  }
  














  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DefaultXYDataset)) {
      return false;
    }
    DefaultXYDataset that = (DefaultXYDataset)obj;
    if (!seriesKeys.equals(seriesKeys)) {
      return false;
    }
    for (int i = 0; i < seriesList.size(); i++) {
      double[][] d1 = (double[][])seriesList.get(i);
      double[][] d2 = (double[][])seriesList.get(i);
      double[] d1x = d1[0];
      double[] d2x = d2[0];
      if (!Arrays.equals(d1x, d2x)) {
        return false;
      }
      double[] d1y = d1[1];
      double[] d2y = d2[1];
      if (!Arrays.equals(d1y, d2y)) {
        return false;
      }
    }
    return true;
  }
  





  public int hashCode()
  {
    int result = seriesKeys.hashCode();
    result = 29 * result + seriesList.hashCode();
    return result;
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultXYDataset clone = (DefaultXYDataset)super.clone();
    seriesKeys = new ArrayList(seriesKeys);
    seriesList = new ArrayList(seriesList.size());
    for (int i = 0; i < seriesList.size(); i++) {
      double[][] data = (double[][])seriesList.get(i);
      double[] x = data[0];
      double[] y = data[1];
      double[] xx = new double[x.length];
      double[] yy = new double[y.length];
      System.arraycopy(x, 0, xx, 0, x.length);
      System.arraycopy(y, 0, yy, 0, y.length);
      seriesList.add(i, new double[][] { xx, yy });
    }
    return clone;
  }
}
