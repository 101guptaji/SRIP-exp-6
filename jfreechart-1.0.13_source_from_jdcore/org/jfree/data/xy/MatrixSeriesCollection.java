package org.jfree.data.xy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
























































public class MatrixSeriesCollection
  extends AbstractXYZDataset
  implements XYZDataset, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -3197705779242543945L;
  private List seriesList;
  
  public MatrixSeriesCollection()
  {
    this(null);
  }
  





  public MatrixSeriesCollection(MatrixSeries series)
  {
    seriesList = new ArrayList();
    
    if (series != null) {
      seriesList.add(series);
      series.addChangeListener(this);
    }
  }
  






  public int getItemCount(int seriesIndex)
  {
    return getSeries(seriesIndex).getItemCount();
  }
  









  public MatrixSeries getSeries(int seriesIndex)
  {
    if ((seriesIndex < 0) || (seriesIndex > getSeriesCount())) {
      throw new IllegalArgumentException("Index outside valid range.");
    }
    
    MatrixSeries series = (MatrixSeries)seriesList.get(seriesIndex);
    
    return series;
  }
  





  public int getSeriesCount()
  {
    return seriesList.size();
  }
  







  public Comparable getSeriesKey(int seriesIndex)
  {
    return getSeries(seriesIndex).getKey();
  }
  











  public Number getX(int seriesIndex, int itemIndex)
  {
    MatrixSeries series = (MatrixSeries)seriesList.get(seriesIndex);
    int x = series.getItemColumn(itemIndex);
    
    return new Integer(x);
  }
  











  public Number getY(int seriesIndex, int itemIndex)
  {
    MatrixSeries series = (MatrixSeries)seriesList.get(seriesIndex);
    int y = series.getItemRow(itemIndex);
    
    return new Integer(y);
  }
  











  public Number getZ(int seriesIndex, int itemIndex)
  {
    MatrixSeries series = (MatrixSeries)seriesList.get(seriesIndex);
    Number z = series.getItem(itemIndex);
    return z;
  }
  











  public void addSeries(MatrixSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Cannot add null series.");
    }
    


    seriesList.add(series);
    series.addChangeListener(this);
    fireDatasetChanged();
  }
  







  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    
    if (obj == this) {
      return true;
    }
    
    if ((obj instanceof MatrixSeriesCollection)) {
      MatrixSeriesCollection c = (MatrixSeriesCollection)obj;
      
      return ObjectUtilities.equal(seriesList, seriesList);
    }
    
    return false;
  }
  




  public int hashCode()
  {
    return seriesList != null ? seriesList.hashCode() : 0;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    MatrixSeriesCollection clone = (MatrixSeriesCollection)super.clone();
    seriesList = ((List)ObjectUtilities.deepClone(seriesList));
    return clone;
  }
  







  public void removeAllSeries()
  {
    for (int i = 0; i < seriesList.size(); i++) {
      MatrixSeries series = (MatrixSeries)seriesList.get(i);
      series.removeChangeListener(this);
    }
    

    seriesList.clear();
    fireDatasetChanged();
  }
  











  public void removeSeries(MatrixSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Cannot remove null series.");
    }
    

    if (seriesList.contains(series)) {
      series.removeChangeListener(this);
      seriesList.remove(series);
      fireDatasetChanged();
    }
  }
  










  public void removeSeries(int seriesIndex)
  {
    if ((seriesIndex < 0) || (seriesIndex > getSeriesCount())) {
      throw new IllegalArgumentException("Index outside valid range.");
    }
    

    MatrixSeries series = (MatrixSeries)seriesList.get(seriesIndex);
    series.removeChangeListener(this);
    seriesList.remove(seriesIndex);
    fireDatasetChanged();
  }
}
