package org.jfree.chart.entity;

import java.awt.Shape;
import java.io.Serializable;
import org.jfree.data.general.Dataset;
import org.jfree.util.ObjectUtilities;



































































public class LegendItemEntity
  extends ChartEntity
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -7435683933545666702L;
  private Dataset dataset;
  private Comparable seriesKey;
  private int seriesIndex;
  
  public LegendItemEntity(Shape area)
  {
    super(area);
  }
  









  public Dataset getDataset()
  {
    return dataset;
  }
  






  public void setDataset(Dataset dataset)
  {
    this.dataset = dataset;
  }
  








  public Comparable getSeriesKey()
  {
    return seriesKey;
  }
  








  public void setSeriesKey(Comparable key)
  {
    seriesKey = key;
  }
  





  /**
   * @deprecated
   */
  public int getSeriesIndex()
  {
    return seriesIndex;
  }
  






  /**
   * @deprecated
   */
  public void setSeriesIndex(int index)
  {
    seriesIndex = index;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof LegendItemEntity)) {
      return false;
    }
    LegendItemEntity that = (LegendItemEntity)obj;
    if (!ObjectUtilities.equal(seriesKey, seriesKey)) {
      return false;
    }
    if (seriesIndex != seriesIndex) {
      return false;
    }
    if (!ObjectUtilities.equal(dataset, dataset)) {
      return false;
    }
    return super.equals(obj);
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  





  public String toString()
  {
    return "LegendItemEntity: seriesKey=" + seriesKey + ", dataset=" + dataset;
  }
}
