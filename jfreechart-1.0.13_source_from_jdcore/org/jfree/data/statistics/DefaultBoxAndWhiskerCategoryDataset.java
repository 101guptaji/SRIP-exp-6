package org.jfree.data.statistics;

import java.util.List;
import org.jfree.data.KeyedObjects2D;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.general.AbstractDataset;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;

















































































public class DefaultBoxAndWhiskerCategoryDataset
  extends AbstractDataset
  implements BoxAndWhiskerCategoryDataset, RangeInfo, PublicCloneable
{
  protected KeyedObjects2D data;
  private double minimumRangeValue;
  private int minimumRangeValueRow;
  private int minimumRangeValueColumn;
  private double maximumRangeValue;
  private int maximumRangeValueRow;
  private int maximumRangeValueColumn;
  
  public DefaultBoxAndWhiskerCategoryDataset()
  {
    data = new KeyedObjects2D();
    minimumRangeValue = NaN.0D;
    minimumRangeValueRow = -1;
    minimumRangeValueColumn = -1;
    maximumRangeValue = NaN.0D;
    maximumRangeValueRow = -1;
    maximumRangeValueColumn = -1;
  }
  










  public void add(List list, Comparable rowKey, Comparable columnKey)
  {
    BoxAndWhiskerItem item = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(list);
    
    add(item, rowKey, columnKey);
  }
  











  public void add(BoxAndWhiskerItem item, Comparable rowKey, Comparable columnKey)
  {
    data.addObject(item, rowKey, columnKey);
    

    int r = data.getRowIndex(rowKey);
    int c = data.getColumnIndex(columnKey);
    if (((maximumRangeValueRow == r) && (maximumRangeValueColumn == c)) || ((minimumRangeValueRow == r) && (minimumRangeValueColumn == c)))
    {

      updateBounds();
    }
    else
    {
      double minval = NaN.0D;
      if (item.getMinOutlier() != null) {
        minval = item.getMinOutlier().doubleValue();
      }
      double maxval = NaN.0D;
      if (item.getMaxOutlier() != null) {
        maxval = item.getMaxOutlier().doubleValue();
      }
      
      if (Double.isNaN(maximumRangeValue)) {
        maximumRangeValue = maxval;
        maximumRangeValueRow = r;
        maximumRangeValueColumn = c;
      }
      else if (maxval > maximumRangeValue) {
        maximumRangeValue = maxval;
        maximumRangeValueRow = r;
        maximumRangeValueColumn = c;
      }
      
      if (Double.isNaN(minimumRangeValue)) {
        minimumRangeValue = minval;
        minimumRangeValueRow = r;
        minimumRangeValueColumn = c;
      }
      else if (minval < minimumRangeValue) {
        minimumRangeValue = minval;
        minimumRangeValueRow = r;
        minimumRangeValueColumn = c;
      }
    }
    
    fireDatasetChanged();
  }
  












  public void remove(Comparable rowKey, Comparable columnKey)
  {
    int r = getRowIndex(rowKey);
    int c = getColumnIndex(columnKey);
    data.removeObject(rowKey, columnKey);
    


    if (((maximumRangeValueRow == r) && (maximumRangeValueColumn == c)) || ((minimumRangeValueRow == r) && (minimumRangeValueColumn == c)))
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
  







  public BoxAndWhiskerItem getItem(int row, int column)
  {
    return (BoxAndWhiskerItem)data.getObject(row, column);
  }
  










  public Number getValue(int row, int column)
  {
    return getMedianValue(row, column);
  }
  










  public Number getValue(Comparable rowKey, Comparable columnKey)
  {
    return getMedianValue(rowKey, columnKey);
  }
  










  public Number getMeanValue(int row, int column)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(row, column);
    
    if (item != null) {
      result = item.getMean();
    }
    return result;
  }
  










  public Number getMeanValue(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(rowKey, columnKey);
    
    if (item != null) {
      result = item.getMean();
    }
    return result;
  }
  









  public Number getMedianValue(int row, int column)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(row, column);
    
    if (item != null) {
      result = item.getMedian();
    }
    return result;
  }
  









  public Number getMedianValue(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(rowKey, columnKey);
    
    if (item != null) {
      result = item.getMedian();
    }
    return result;
  }
  









  public Number getQ1Value(int row, int column)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(row, column);
    
    if (item != null) {
      result = item.getQ1();
    }
    return result;
  }
  









  public Number getQ1Value(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(rowKey, columnKey);
    
    if (item != null) {
      result = item.getQ1();
    }
    return result;
  }
  









  public Number getQ3Value(int row, int column)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(row, column);
    
    if (item != null) {
      result = item.getQ3();
    }
    return result;
  }
  









  public Number getQ3Value(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(rowKey, columnKey);
    
    if (item != null) {
      result = item.getQ3();
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
  









  public double getRangeLowerBound(boolean includeInterval)
  {
    return minimumRangeValue;
  }
  









  public double getRangeUpperBound(boolean includeInterval)
  {
    return maximumRangeValue;
  }
  







  public Range getRangeBounds(boolean includeInterval)
  {
    return new Range(minimumRangeValue, maximumRangeValue);
  }
  









  public Number getMinRegularValue(int row, int column)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(row, column);
    
    if (item != null) {
      result = item.getMinRegularValue();
    }
    return result;
  }
  









  public Number getMinRegularValue(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(rowKey, columnKey);
    
    if (item != null) {
      result = item.getMinRegularValue();
    }
    return result;
  }
  









  public Number getMaxRegularValue(int row, int column)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(row, column);
    
    if (item != null) {
      result = item.getMaxRegularValue();
    }
    return result;
  }
  









  public Number getMaxRegularValue(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(rowKey, columnKey);
    
    if (item != null) {
      result = item.getMaxRegularValue();
    }
    return result;
  }
  









  public Number getMinOutlier(int row, int column)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(row, column);
    
    if (item != null) {
      result = item.getMinOutlier();
    }
    return result;
  }
  









  public Number getMinOutlier(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(rowKey, columnKey);
    
    if (item != null) {
      result = item.getMinOutlier();
    }
    return result;
  }
  









  public Number getMaxOutlier(int row, int column)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(row, column);
    
    if (item != null) {
      result = item.getMaxOutlier();
    }
    return result;
  }
  









  public Number getMaxOutlier(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(rowKey, columnKey);
    
    if (item != null) {
      result = item.getMaxOutlier();
    }
    return result;
  }
  









  public List getOutliers(int row, int column)
  {
    List result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(row, column);
    
    if (item != null) {
      result = item.getOutliers();
    }
    return result;
  }
  









  public List getOutliers(Comparable rowKey, Comparable columnKey)
  {
    List result = null;
    BoxAndWhiskerItem item = (BoxAndWhiskerItem)data.getObject(rowKey, columnKey);
    
    if (item != null) {
      result = item.getOutliers();
    }
    return result;
  }
  



  private void updateBounds()
  {
    minimumRangeValue = NaN.0D;
    minimumRangeValueRow = -1;
    minimumRangeValueColumn = -1;
    maximumRangeValue = NaN.0D;
    maximumRangeValueRow = -1;
    maximumRangeValueColumn = -1;
    int rowCount = getRowCount();
    int columnCount = getColumnCount();
    for (int r = 0; r < rowCount; r++) {
      for (int c = 0; c < columnCount; c++) {
        BoxAndWhiskerItem item = getItem(r, c);
        if (item != null) {
          Number min = item.getMinOutlier();
          if (min != null) {
            double minv = min.doubleValue();
            if ((!Double.isNaN(minv)) && (
              (minv < minimumRangeValue) || (Double.isNaN(minimumRangeValue))))
            {
              minimumRangeValue = minv;
              minimumRangeValueRow = r;
              minimumRangeValueColumn = c;
            }
          }
          
          Number max = item.getMaxOutlier();
          if (max != null) {
            double maxv = max.doubleValue();
            if ((!Double.isNaN(maxv)) && (
              (maxv > maximumRangeValue) || (Double.isNaN(maximumRangeValue))))
            {
              maximumRangeValue = maxv;
              maximumRangeValueRow = r;
              maximumRangeValueColumn = c;
            }
          }
        }
      }
    }
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if ((obj instanceof DefaultBoxAndWhiskerCategoryDataset)) {
      DefaultBoxAndWhiskerCategoryDataset dataset = (DefaultBoxAndWhiskerCategoryDataset)obj;
      
      return ObjectUtilities.equal(data, data);
    }
    return false;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultBoxAndWhiskerCategoryDataset clone = (DefaultBoxAndWhiskerCategoryDataset)super.clone();
    
    data = ((KeyedObjects2D)data.clone());
    return clone;
  }
}
