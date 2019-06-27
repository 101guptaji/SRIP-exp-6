package org.jfree.data.category;

import java.io.Serializable;
import java.util.List;
import org.jfree.data.DefaultKeyedValues2D;
import org.jfree.data.general.AbstractDataset;
import org.jfree.util.PublicCloneable;



























































public class DefaultCategoryDataset
  extends AbstractDataset
  implements CategoryDataset, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -8168173757291644622L;
  private DefaultKeyedValues2D data;
  
  public DefaultCategoryDataset()
  {
    data = new DefaultKeyedValues2D();
  }
  






  public int getRowCount()
  {
    return data.getRowCount();
  }
  






  public int getColumnCount()
  {
    return data.getColumnCount();
  }
  










  public Number getValue(int row, int column)
  {
    return data.getValue(row, column);
  }
  










  public Comparable getRowKey(int row)
  {
    return data.getRowKey(row);
  }
  









  public int getRowIndex(Comparable key)
  {
    return data.getRowIndex(key);
  }
  






  public List getRowKeys()
  {
    return data.getRowKeys();
  }
  








  public Comparable getColumnKey(int column)
  {
    return data.getColumnKey(column);
  }
  









  public int getColumnIndex(Comparable key)
  {
    return data.getColumnIndex(key);
  }
  






  public List getColumnKeys()
  {
    return data.getColumnKeys();
  }
  











  public Number getValue(Comparable rowKey, Comparable columnKey)
  {
    return data.getValue(rowKey, columnKey);
  }
  










  public void addValue(Number value, Comparable rowKey, Comparable columnKey)
  {
    data.addValue(value, rowKey, columnKey);
    fireDatasetChanged();
  }
  









  public void addValue(double value, Comparable rowKey, Comparable columnKey)
  {
    addValue(new Double(value), rowKey, columnKey);
  }
  










  public void setValue(Number value, Comparable rowKey, Comparable columnKey)
  {
    data.setValue(value, rowKey, columnKey);
    fireDatasetChanged();
  }
  










  public void setValue(double value, Comparable rowKey, Comparable columnKey)
  {
    setValue(new Double(value), rowKey, columnKey);
  }
  











  public void incrementValue(double value, Comparable rowKey, Comparable columnKey)
  {
    double existing = 0.0D;
    Number n = getValue(rowKey, columnKey);
    if (n != null) {
      existing = n.doubleValue();
    }
    setValue(existing + value, rowKey, columnKey);
  }
  








  public void removeValue(Comparable rowKey, Comparable columnKey)
  {
    data.removeValue(rowKey, columnKey);
    fireDatasetChanged();
  }
  







  public void removeRow(int rowIndex)
  {
    data.removeRow(rowIndex);
    fireDatasetChanged();
  }
  







  public void removeRow(Comparable rowKey)
  {
    data.removeRow(rowKey);
    fireDatasetChanged();
  }
  







  public void removeColumn(int columnIndex)
  {
    data.removeColumn(columnIndex);
    fireDatasetChanged();
  }
  










  public void removeColumn(Comparable columnKey)
  {
    data.removeColumn(columnKey);
    fireDatasetChanged();
  }
  



  public void clear()
  {
    data.clear();
    fireDatasetChanged();
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof CategoryDataset)) {
      return false;
    }
    CategoryDataset that = (CategoryDataset)obj;
    if (!getRowKeys().equals(that.getRowKeys())) {
      return false;
    }
    if (!getColumnKeys().equals(that.getColumnKeys())) {
      return false;
    }
    int rowCount = getRowCount();
    int colCount = getColumnCount();
    for (int r = 0; r < rowCount; r++) {
      for (int c = 0; c < colCount; c++) {
        Number v1 = getValue(r, c);
        Number v2 = that.getValue(r, c);
        if (v1 == null) {
          if (v2 != null) {
            return false;
          }
        }
        else if (!v1.equals(v2)) {
          return false;
        }
      }
    }
    return true;
  }
  




  public int hashCode()
  {
    return data.hashCode();
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultCategoryDataset clone = (DefaultCategoryDataset)super.clone();
    data = ((DefaultKeyedValues2D)data.clone());
    return clone;
  }
}
