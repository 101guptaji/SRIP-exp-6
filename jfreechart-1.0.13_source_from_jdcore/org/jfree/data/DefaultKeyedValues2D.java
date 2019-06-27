package org.jfree.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;










































































public class DefaultKeyedValues2D
  implements KeyedValues2D, PublicCloneable, Cloneable, Serializable
{
  private static final long serialVersionUID = -5514169970951994748L;
  private List rowKeys;
  private List columnKeys;
  private List rows;
  private boolean sortRowKeys;
  
  public DefaultKeyedValues2D()
  {
    this(false);
  }
  




  public DefaultKeyedValues2D(boolean sortRowKeys)
  {
    rowKeys = new ArrayList();
    columnKeys = new ArrayList();
    rows = new ArrayList();
    this.sortRowKeys = sortRowKeys;
  }
  






  public int getRowCount()
  {
    return rowKeys.size();
  }
  






  public int getColumnCount()
  {
    return columnKeys.size();
  }
  









  public Number getValue(int row, int column)
  {
    Number result = null;
    DefaultKeyedValues rowData = (DefaultKeyedValues)rows.get(row);
    if (rowData != null) {
      Comparable columnKey = (Comparable)columnKeys.get(column);
      

      int index = rowData.getIndex(columnKey);
      if (index >= 0) {
        result = rowData.getValue(index);
      }
    }
    return result;
  }
  









  public Comparable getRowKey(int row)
  {
    return (Comparable)rowKeys.get(row);
  }
  









  public int getRowIndex(Comparable key)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    if (sortRowKeys) {
      return Collections.binarySearch(rowKeys, key);
    }
    
    return rowKeys.indexOf(key);
  }
  







  public List getRowKeys()
  {
    return Collections.unmodifiableList(rowKeys);
  }
  










  public Comparable getColumnKey(int column)
  {
    return (Comparable)columnKeys.get(column);
  }
  









  public int getColumnIndex(Comparable key)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    return columnKeys.indexOf(key);
  }
  






  public List getColumnKeys()
  {
    return Collections.unmodifiableList(columnKeys);
  }
  












  public Number getValue(Comparable rowKey, Comparable columnKey)
  {
    if (rowKey == null) {
      throw new IllegalArgumentException("Null 'rowKey' argument.");
    }
    if (columnKey == null) {
      throw new IllegalArgumentException("Null 'columnKey' argument.");
    }
    

    if (!columnKeys.contains(columnKey)) {
      throw new UnknownKeyException("Unrecognised columnKey: " + columnKey);
    }
    




    int row = getRowIndex(rowKey);
    if (row >= 0) {
      DefaultKeyedValues rowData = (DefaultKeyedValues)rows.get(row);
      
      int col = rowData.getIndex(columnKey);
      return col >= 0 ? rowData.getValue(col) : null;
    }
    
    throw new UnknownKeyException("Unrecognised rowKey: " + rowKey);
  }
  













  public void addValue(Number value, Comparable rowKey, Comparable columnKey)
  {
    setValue(value, rowKey, columnKey);
  }
  












  public void setValue(Number value, Comparable rowKey, Comparable columnKey)
  {
    int rowIndex = getRowIndex(rowKey);
    DefaultKeyedValues row;
    DefaultKeyedValues row; if (rowIndex >= 0) {
      row = (DefaultKeyedValues)rows.get(rowIndex);
    }
    else {
      row = new DefaultKeyedValues();
      if (sortRowKeys) {
        rowIndex = -rowIndex - 1;
        rowKeys.add(rowIndex, rowKey);
        rows.add(rowIndex, row);
      }
      else {
        rowKeys.add(rowKey);
        rows.add(row);
      }
    }
    row.setValue(columnKey, value);
    
    int columnIndex = columnKeys.indexOf(columnKey);
    if (columnIndex < 0) {
      columnKeys.add(columnKey);
    }
  }
  









  public void removeValue(Comparable rowKey, Comparable columnKey)
  {
    setValue(null, rowKey, columnKey);
    

    boolean allNull = true;
    int rowIndex = getRowIndex(rowKey);
    DefaultKeyedValues row = (DefaultKeyedValues)rows.get(rowIndex);
    
    int item = 0; for (int itemCount = row.getItemCount(); item < itemCount; 
        item++) {
      if (row.getValue(item) != null) {
        allNull = false;
        break;
      }
    }
    
    if (allNull) {
      rowKeys.remove(rowIndex);
      rows.remove(rowIndex);
    }
    

    allNull = true;
    

    int item = 0; for (int itemCount = rows.size(); item < itemCount; 
        item++) {
      row = (DefaultKeyedValues)rows.get(item);
      int columnIndex = row.getIndex(columnKey);
      if ((columnIndex >= 0) && (row.getValue(columnIndex) != null)) {
        allNull = false;
        break;
      }
    }
    
    if (allNull) {
      int item = 0; for (int itemCount = rows.size(); item < itemCount; 
          item++) {
        row = (DefaultKeyedValues)rows.get(item);
        int columnIndex = row.getIndex(columnKey);
        if (columnIndex >= 0) {
          row.removeValue(columnIndex);
        }
      }
      columnKeys.remove(columnKey);
    }
  }
  







  public void removeRow(int rowIndex)
  {
    rowKeys.remove(rowIndex);
    rows.remove(rowIndex);
  }
  










  public void removeRow(Comparable rowKey)
  {
    if (rowKey == null) {
      throw new IllegalArgumentException("Null 'rowKey' argument.");
    }
    int index = getRowIndex(rowKey);
    if (index >= 0) {
      removeRow(index);
    }
    else {
      throw new UnknownKeyException("Unknown key: " + rowKey);
    }
  }
  







  public void removeColumn(int columnIndex)
  {
    Comparable columnKey = getColumnKey(columnIndex);
    removeColumn(columnKey);
  }
  












  public void removeColumn(Comparable columnKey)
  {
    if (columnKey == null) {
      throw new IllegalArgumentException("Null 'columnKey' argument.");
    }
    if (!columnKeys.contains(columnKey)) {
      throw new UnknownKeyException("Unknown key: " + columnKey);
    }
    Iterator iterator = rows.iterator();
    while (iterator.hasNext()) {
      DefaultKeyedValues rowData = (DefaultKeyedValues)iterator.next();
      int index = rowData.getIndex(columnKey);
      if (index >= 0) {
        rowData.removeValue(columnKey);
      }
    }
    columnKeys.remove(columnKey);
  }
  


  public void clear()
  {
    rowKeys.clear();
    columnKeys.clear();
    rows.clear();
  }
  







  public boolean equals(Object o)
  {
    if (o == null) {
      return false;
    }
    if (o == this) {
      return true;
    }
    
    if (!(o instanceof KeyedValues2D)) {
      return false;
    }
    KeyedValues2D kv2D = (KeyedValues2D)o;
    if (!getRowKeys().equals(kv2D.getRowKeys())) {
      return false;
    }
    if (!getColumnKeys().equals(kv2D.getColumnKeys())) {
      return false;
    }
    int rowCount = getRowCount();
    if (rowCount != kv2D.getRowCount()) {
      return false;
    }
    
    int colCount = getColumnCount();
    if (colCount != kv2D.getColumnCount()) {
      return false;
    }
    
    for (int r = 0; r < rowCount; r++) {
      for (int c = 0; c < colCount; c++) {
        Number v1 = getValue(r, c);
        Number v2 = kv2D.getValue(r, c);
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
    int result = rowKeys.hashCode();
    result = 29 * result + columnKeys.hashCode();
    result = 29 * result + rows.hashCode();
    return result;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultKeyedValues2D clone = (DefaultKeyedValues2D)super.clone();
    

    columnKeys = new ArrayList(columnKeys);
    rowKeys = new ArrayList(rowKeys);
    

    rows = ((List)ObjectUtilities.deepClone(rows));
    return clone;
  }
}
