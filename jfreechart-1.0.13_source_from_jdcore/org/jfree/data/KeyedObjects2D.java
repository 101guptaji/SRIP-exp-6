package org.jfree.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
























































public class KeyedObjects2D
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -1015873563138522374L;
  private List rowKeys;
  private List columnKeys;
  private List rows;
  
  public KeyedObjects2D()
  {
    rowKeys = new ArrayList();
    columnKeys = new ArrayList();
    rows = new ArrayList();
  }
  






  public int getRowCount()
  {
    return rowKeys.size();
  }
  






  public int getColumnCount()
  {
    return columnKeys.size();
  }
  









  public Object getObject(int row, int column)
  {
    Object result = null;
    KeyedObjects rowData = (KeyedObjects)rows.get(row);
    if (rowData != null) {
      Comparable columnKey = (Comparable)columnKeys.get(column);
      if (columnKey != null) {
        int index = rowData.getIndex(columnKey);
        if (index >= 0) {
          result = rowData.getObject(columnKey);
        }
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
  












  public Object getObject(Comparable rowKey, Comparable columnKey)
  {
    if (rowKey == null) {
      throw new IllegalArgumentException("Null 'rowKey' argument.");
    }
    if (columnKey == null) {
      throw new IllegalArgumentException("Null 'columnKey' argument.");
    }
    int row = rowKeys.indexOf(rowKey);
    if (row < 0) {
      throw new UnknownKeyException("Row key (" + rowKey + ") not recognised.");
    }
    
    int column = columnKeys.indexOf(columnKey);
    if (column < 0) {
      throw new UnknownKeyException("Column key (" + columnKey + ") not recognised.");
    }
    
    KeyedObjects rowData = (KeyedObjects)rows.get(row);
    int index = rowData.getIndex(columnKey);
    if (index >= 0) {
      return rowData.getObject(index);
    }
    
    return null;
  }
  








  public void addObject(Object object, Comparable rowKey, Comparable columnKey)
  {
    setObject(object, rowKey, columnKey);
  }
  








  public void setObject(Object object, Comparable rowKey, Comparable columnKey)
  {
    if (rowKey == null) {
      throw new IllegalArgumentException("Null 'rowKey' argument.");
    }
    if (columnKey == null) {
      throw new IllegalArgumentException("Null 'columnKey' argument.");
    }
    
    int rowIndex = rowKeys.indexOf(rowKey);
    KeyedObjects row; KeyedObjects row; if (rowIndex >= 0) {
      row = (KeyedObjects)rows.get(rowIndex);
    }
    else {
      rowKeys.add(rowKey);
      row = new KeyedObjects();
      rows.add(row);
    }
    row.setObject(columnKey, object);
    int columnIndex = columnKeys.indexOf(columnKey);
    if (columnIndex < 0) {
      columnKeys.add(columnKey);
    }
  }
  










  public void removeObject(Comparable rowKey, Comparable columnKey)
  {
    int rowIndex = getRowIndex(rowKey);
    if (rowIndex < 0) {
      throw new UnknownKeyException("Row key (" + rowKey + ") not recognised.");
    }
    
    int columnIndex = getColumnIndex(columnKey);
    if (columnIndex < 0) {
      throw new UnknownKeyException("Column key (" + columnKey + ") not recognised.");
    }
    
    setObject(null, rowKey, columnKey);
    

    boolean allNull = true;
    KeyedObjects row = (KeyedObjects)rows.get(rowIndex);
    
    int item = 0; for (int itemCount = row.getItemCount(); item < itemCount; 
        item++) {
      if (row.getObject(item) != null) {
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
      row = (KeyedObjects)rows.get(item);
      int colIndex = row.getIndex(columnKey);
      if ((colIndex >= 0) && (row.getObject(colIndex) != null)) {
        allNull = false;
        break;
      }
    }
    
    if (allNull) {
      int item = 0; for (int itemCount = rows.size(); item < itemCount; 
          item++) {
        row = (KeyedObjects)rows.get(item);
        int colIndex = row.getIndex(columnKey);
        if (colIndex >= 0) {
          row.removeValue(colIndex);
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
    int index = getRowIndex(rowKey);
    if (index < 0) {
      throw new UnknownKeyException("Row key (" + rowKey + ") not recognised.");
    }
    
    removeRow(index);
  }
  






  public void removeColumn(int columnIndex)
  {
    Comparable columnKey = getColumnKey(columnIndex);
    removeColumn(columnKey);
  }
  








  public void removeColumn(Comparable columnKey)
  {
    int index = getColumnIndex(columnKey);
    if (index < 0) {
      throw new UnknownKeyException("Column key (" + columnKey + ") not recognised.");
    }
    
    Iterator iterator = rows.iterator();
    while (iterator.hasNext()) {
      KeyedObjects rowData = (KeyedObjects)iterator.next();
      int i = rowData.getIndex(columnKey);
      if (i >= 0) {
        rowData.removeValue(i);
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
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof KeyedObjects2D)) {
      return false;
    }
    
    KeyedObjects2D that = (KeyedObjects2D)obj;
    if (!getRowKeys().equals(that.getRowKeys())) {
      return false;
    }
    if (!getColumnKeys().equals(that.getColumnKeys())) {
      return false;
    }
    int rowCount = getRowCount();
    if (rowCount != that.getRowCount()) {
      return false;
    }
    int colCount = getColumnCount();
    if (colCount != that.getColumnCount()) {
      return false;
    }
    for (int r = 0; r < rowCount; r++) {
      for (int c = 0; c < colCount; c++) {
        Object v1 = getObject(r, c);
        Object v2 = that.getObject(r, c);
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
    KeyedObjects2D clone = (KeyedObjects2D)super.clone();
    columnKeys = new ArrayList(columnKeys);
    rowKeys = new ArrayList(rowKeys);
    rows = new ArrayList(rows.size());
    Iterator iterator = rows.iterator();
    while (iterator.hasNext()) {
      KeyedObjects row = (KeyedObjects)iterator.next();
      rows.add(row.clone());
    }
    return clone;
  }
}
