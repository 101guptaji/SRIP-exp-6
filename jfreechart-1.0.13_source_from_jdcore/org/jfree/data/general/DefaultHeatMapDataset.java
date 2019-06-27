package org.jfree.data.general;

import java.io.Serializable;
import org.jfree.data.DataUtilities;
import org.jfree.util.PublicCloneable;






































































public class DefaultHeatMapDataset
  extends AbstractDataset
  implements HeatMapDataset, Cloneable, PublicCloneable, Serializable
{
  private int xSamples;
  private int ySamples;
  private double minX;
  private double maxX;
  private double minY;
  private double maxY;
  private double[][] zValues;
  
  public DefaultHeatMapDataset(int xSamples, int ySamples, double minX, double maxX, double minY, double maxY)
  {
    if (xSamples < 1) {
      throw new IllegalArgumentException("Requires 'xSamples' > 0");
    }
    if (ySamples < 1) {
      throw new IllegalArgumentException("Requires 'ySamples' > 0");
    }
    if ((Double.isInfinite(minX)) || (Double.isNaN(minX))) {
      throw new IllegalArgumentException("'minX' cannot be INF or NaN.");
    }
    if ((Double.isInfinite(maxX)) || (Double.isNaN(maxX))) {
      throw new IllegalArgumentException("'maxX' cannot be INF or NaN.");
    }
    if ((Double.isInfinite(minY)) || (Double.isNaN(minY))) {
      throw new IllegalArgumentException("'minY' cannot be INF or NaN.");
    }
    if ((Double.isInfinite(maxY)) || (Double.isNaN(maxY))) {
      throw new IllegalArgumentException("'maxY' cannot be INF or NaN.");
    }
    
    this.xSamples = xSamples;
    this.ySamples = ySamples;
    this.minX = minX;
    this.maxX = maxX;
    this.minY = minY;
    this.maxY = maxY;
    zValues = new double[xSamples][];
    for (int x = 0; x < xSamples; x++) {
      zValues[x] = new double[ySamples];
    }
  }
  






  public int getXSampleCount()
  {
    return xSamples;
  }
  






  public int getYSampleCount()
  {
    return ySamples;
  }
  






  public double getMinimumXValue()
  {
    return minX;
  }
  






  public double getMaximumXValue()
  {
    return maxX;
  }
  






  public double getMinimumYValue()
  {
    return minY;
  }
  






  public double getMaximumYValue()
  {
    return maxY;
  }
  






  public double getXValue(int xIndex)
  {
    double x = minX + (maxX - minX) * (xIndex / xSamples);
    
    return x;
  }
  






  public double getYValue(int yIndex)
  {
    double y = minY + (maxY - minY) * (yIndex / ySamples);
    
    return y;
  }
  








  public double getZValue(int xIndex, int yIndex)
  {
    return zValues[xIndex][yIndex];
  }
  










  public Number getZ(int xIndex, int yIndex)
  {
    return new Double(getZValue(xIndex, yIndex));
  }
  







  public void setZValue(int xIndex, int yIndex, double z)
  {
    setZValue(xIndex, yIndex, z, true);
  }
  








  public void setZValue(int xIndex, int yIndex, double z, boolean notify)
  {
    zValues[xIndex][yIndex] = z;
    if (notify) {
      fireDatasetChanged();
    }
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DefaultHeatMapDataset)) {
      return false;
    }
    DefaultHeatMapDataset that = (DefaultHeatMapDataset)obj;
    if (xSamples != xSamples) {
      return false;
    }
    if (ySamples != ySamples) {
      return false;
    }
    if (minX != minX) {
      return false;
    }
    if (maxX != maxX) {
      return false;
    }
    if (minY != minY) {
      return false;
    }
    if (maxY != maxY) {
      return false;
    }
    if (!DataUtilities.equal(zValues, zValues)) {
      return false;
    }
    
    return true;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultHeatMapDataset clone = (DefaultHeatMapDataset)super.clone();
    zValues = DataUtilities.clone(zValues);
    return clone;
  }
}
