package org.jfree.data.statistics;

import java.util.List;
import org.jfree.data.KeyedObjects2D;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.general.AbstractDataset;
import org.jfree.util.PublicCloneable;




































































































public class DefaultStatisticalCategoryDataset
  extends AbstractDataset
  implements StatisticalCategoryDataset, RangeInfo, PublicCloneable
{
  private KeyedObjects2D data;
  private double minimumRangeValue;
  private int minimumRangeValueRow;
  private int minimumRangeValueColumn;
  private double minimumRangeValueIncStdDev;
  private int minimumRangeValueIncStdDevRow;
  private int minimumRangeValueIncStdDevColumn;
  private double maximumRangeValue;
  private int maximumRangeValueRow;
  private int maximumRangeValueColumn;
  private double maximumRangeValueIncStdDev;
  private int maximumRangeValueIncStdDevRow;
  private int maximumRangeValueIncStdDevColumn;
  
  public DefaultStatisticalCategoryDataset()
  {
    data = new KeyedObjects2D();
    minimumRangeValue = NaN.0D;
    minimumRangeValueRow = -1;
    minimumRangeValueColumn = -1;
    maximumRangeValue = NaN.0D;
    maximumRangeValueRow = -1;
    maximumRangeValueColumn = -1;
    minimumRangeValueIncStdDev = NaN.0D;
    minimumRangeValueIncStdDevRow = -1;
    minimumRangeValueIncStdDevColumn = -1;
    maximumRangeValueIncStdDev = NaN.0D;
    maximumRangeValueIncStdDevRow = -1;
    maximumRangeValueIncStdDevColumn = -1;
  }
  







  public Number getMeanValue(int row, int column)
  {
    Number result = null;
    MeanAndStandardDeviation masd = (MeanAndStandardDeviation)data.getObject(row, column);
    
    if (masd != null) {
      result = masd.getMean();
    }
    return result;
  }
  








  public Number getValue(int row, int column)
  {
    return getMeanValue(row, column);
  }
  








  public Number getValue(Comparable rowKey, Comparable columnKey)
  {
    return getMeanValue(rowKey, columnKey);
  }
  







  public Number getMeanValue(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    MeanAndStandardDeviation masd = (MeanAndStandardDeviation)data.getObject(rowKey, columnKey);
    
    if (masd != null) {
      result = masd.getMean();
    }
    return result;
  }
  







  public Number getStdDevValue(int row, int column)
  {
    Number result = null;
    MeanAndStandardDeviation masd = (MeanAndStandardDeviation)data.getObject(row, column);
    
    if (masd != null) {
      result = masd.getStandardDeviation();
    }
    return result;
  }
  







  public Number getStdDevValue(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    MeanAndStandardDeviation masd = (MeanAndStandardDeviation)data.getObject(rowKey, columnKey);
    
    if (masd != null) {
      result = masd.getStandardDeviation();
    }
    return result;
  }
  







  public int getColumnIndex(Comparable key)
  {
    return data.getColumnIndex(key);
  }
  






  public Comparable getColumnKey(int column)
  {
    return data.getColumnKey(column);
  }
  




  public List getColumnKeys()
  {
    return data.getColumnKeys();
  }
  







  public int getRowIndex(Comparable key)
  {
    return data.getRowIndex(key);
  }
  






  public Comparable getRowKey(int row)
  {
    return data.getRowKey(row);
  }
  




  public List getRowKeys()
  {
    return data.getRowKeys();
  }
  






  public int getRowCount()
  {
    return data.getRowCount();
  }
  






  public int getColumnCount()
  {
    return data.getColumnCount();
  }
  








  public void add(double mean, double standardDeviation, Comparable rowKey, Comparable columnKey)
  {
    add(new Double(mean), new Double(standardDeviation), rowKey, columnKey);
  }
  








  public void add(Number mean, Number standardDeviation, Comparable rowKey, Comparable columnKey)
  {
    MeanAndStandardDeviation item = new MeanAndStandardDeviation(mean, standardDeviation);
    
    data.addObject(item, rowKey, columnKey);
    
    double m = NaN.0D;
    double sd = NaN.0D;
    if (mean != null) {
      m = mean.doubleValue();
    }
    if (standardDeviation != null) {
      sd = standardDeviation.doubleValue();
    }
    

    int r = data.getColumnIndex(columnKey);
    int c = data.getRowIndex(rowKey);
    if (((r == maximumRangeValueRow) && (c == maximumRangeValueColumn)) || ((r == maximumRangeValueIncStdDevRow) && (c == maximumRangeValueIncStdDevColumn)) || ((r == minimumRangeValueRow) && (c == minimumRangeValueColumn)) || ((r == minimumRangeValueIncStdDevRow) && (c == minimumRangeValueIncStdDevColumn)))
    {








      updateBounds();
    }
    else {
      if ((!Double.isNaN(m)) && (
        (Double.isNaN(maximumRangeValue)) || (m > maximumRangeValue)))
      {
        maximumRangeValue = m;
        maximumRangeValueRow = r;
        maximumRangeValueColumn = c;
      }
      

      if ((!Double.isNaN(m + sd)) && (
        (Double.isNaN(maximumRangeValueIncStdDev)) || (m + sd > maximumRangeValueIncStdDev)))
      {
        maximumRangeValueIncStdDev = (m + sd);
        maximumRangeValueIncStdDevRow = r;
        maximumRangeValueIncStdDevColumn = c;
      }
      

      if ((!Double.isNaN(m)) && (
        (Double.isNaN(minimumRangeValue)) || (m < minimumRangeValue)))
      {
        minimumRangeValue = m;
        minimumRangeValueRow = r;
        minimumRangeValueColumn = c;
      }
      

      if ((!Double.isNaN(m - sd)) && (
        (Double.isNaN(minimumRangeValueIncStdDev)) || (m - sd < minimumRangeValueIncStdDev)))
      {
        minimumRangeValueIncStdDev = (m - sd);
        minimumRangeValueIncStdDevRow = r;
        minimumRangeValueIncStdDevColumn = c;
      }
    }
    
    fireDatasetChanged();
  }
  











  public void remove(Comparable rowKey, Comparable columnKey)
  {
    int r = getRowIndex(rowKey);
    int c = getColumnIndex(columnKey);
    data.removeObject(rowKey, columnKey);
    


    if (((r == maximumRangeValueRow) && (c == maximumRangeValueColumn)) || ((r == maximumRangeValueIncStdDevRow) && (c == maximumRangeValueIncStdDevColumn)) || ((r == minimumRangeValueRow) && (c == minimumRangeValueColumn)) || ((r == minimumRangeValueIncStdDevRow) && (c == minimumRangeValueIncStdDevColumn)))
    {








      updateBounds();
    }
    
    fireDatasetChanged();
  }
  










  public void removeRow(int rowIndex)
  {
    data.removeRow(rowIndex);
    updateBounds();
    fireDatasetChanged();
  }
  









  public void removeRow(Comparable rowKey)
  {
    data.removeRow(rowKey);
    updateBounds();
    fireDatasetChanged();
  }
  









  public void removeColumn(int columnIndex)
  {
    data.removeColumn(columnIndex);
    updateBounds();
    fireDatasetChanged();
  }
  









  public void removeColumn(Comparable columnKey)
  {
    data.removeColumn(columnKey);
    updateBounds();
    fireDatasetChanged();
  }
  





  public void clear()
  {
    data.clear();
    updateBounds();
    fireDatasetChanged();
  }
  


  private void updateBounds()
  {
    maximumRangeValue = NaN.0D;
    maximumRangeValueRow = -1;
    maximumRangeValueColumn = -1;
    minimumRangeValue = NaN.0D;
    minimumRangeValueRow = -1;
    minimumRangeValueColumn = -1;
    maximumRangeValueIncStdDev = NaN.0D;
    maximumRangeValueIncStdDevRow = -1;
    maximumRangeValueIncStdDevColumn = -1;
    minimumRangeValueIncStdDev = NaN.0D;
    minimumRangeValueIncStdDevRow = -1;
    minimumRangeValueIncStdDevColumn = -1;
    
    int rowCount = data.getRowCount();
    int columnCount = data.getColumnCount();
    for (int r = 0; r < rowCount; r++) {
      for (int c = 0; c < columnCount; c++) {
        double m = NaN.0D;
        double sd = NaN.0D;
        MeanAndStandardDeviation masd = (MeanAndStandardDeviation)data.getObject(r, c);
        
        if (masd != null)
        {

          m = masd.getMeanValue();
          sd = masd.getStandardDeviationValue();
          
          if (!Double.isNaN(m))
          {

            if (Double.isNaN(maximumRangeValue)) {
              maximumRangeValue = m;
              maximumRangeValueRow = r;
              maximumRangeValueColumn = c;

            }
            else if (m > maximumRangeValue) {
              maximumRangeValue = m;
              maximumRangeValueRow = r;
              maximumRangeValueColumn = c;
            }
            


            if (Double.isNaN(minimumRangeValue)) {
              minimumRangeValue = m;
              minimumRangeValueRow = r;
              minimumRangeValueColumn = c;

            }
            else if (m < minimumRangeValue) {
              minimumRangeValue = m;
              minimumRangeValueRow = r;
              minimumRangeValueColumn = c;
            }
            

            if (!Double.isNaN(sd))
            {
              if (Double.isNaN(maximumRangeValueIncStdDev)) {
                maximumRangeValueIncStdDev = (m + sd);
                maximumRangeValueIncStdDevRow = r;
                maximumRangeValueIncStdDevColumn = c;

              }
              else if (m + sd > maximumRangeValueIncStdDev) {
                maximumRangeValueIncStdDev = (m + sd);
                maximumRangeValueIncStdDevRow = r;
                maximumRangeValueIncStdDevColumn = c;
              }
              


              if (Double.isNaN(minimumRangeValueIncStdDev)) {
                minimumRangeValueIncStdDev = (m - sd);
                minimumRangeValueIncStdDevRow = r;
                minimumRangeValueIncStdDevColumn = c;

              }
              else if (m - sd < minimumRangeValueIncStdDev) {
                minimumRangeValueIncStdDev = (m - sd);
                minimumRangeValueIncStdDevRow = r;
                minimumRangeValueIncStdDevColumn = c;
              }
            }
          }
        }
      }
    }
  }
  









  public double getRangeLowerBound(boolean includeInterval)
  {
    if (includeInterval) {
      return minimumRangeValueIncStdDev;
    }
    
    return minimumRangeValue;
  }
  










  public double getRangeUpperBound(boolean includeInterval)
  {
    if (includeInterval) {
      return maximumRangeValueIncStdDev;
    }
    
    return maximumRangeValue;
  }
  








  public Range getRangeBounds(boolean includeInterval)
  {
    Range result = null;
    if (includeInterval) {
      if ((!Double.isNaN(minimumRangeValueIncStdDev)) && (!Double.isNaN(maximumRangeValueIncStdDev)))
      {
        result = new Range(minimumRangeValueIncStdDev, maximumRangeValueIncStdDev);
      }
      

    }
    else if ((!Double.isNaN(minimumRangeValue)) && (!Double.isNaN(maximumRangeValue)))
    {
      result = new Range(minimumRangeValue, maximumRangeValue);
    }
    

    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DefaultStatisticalCategoryDataset)) {
      return false;
    }
    DefaultStatisticalCategoryDataset that = (DefaultStatisticalCategoryDataset)obj;
    
    if (!data.equals(data)) {
      return false;
    }
    return true;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultStatisticalCategoryDataset clone = (DefaultStatisticalCategoryDataset)super.clone();
    
    data = ((KeyedObjects2D)data.clone());
    return clone;
  }
}
