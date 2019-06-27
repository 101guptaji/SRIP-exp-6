package org.jfree.data.category;

import java.util.Collections;
import java.util.List;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.PieDataset;
import org.jfree.util.TableOrder;









































































public class CategoryToPieDataset
  extends AbstractDataset
  implements PieDataset, DatasetChangeListener
{
  static final long serialVersionUID = 5516396319762189617L;
  private CategoryDataset source;
  private TableOrder extract;
  private int index;
  
  public CategoryToPieDataset(CategoryDataset source, TableOrder extract, int index)
  {
    if (extract == null) {
      throw new IllegalArgumentException("Null 'extract' argument.");
    }
    this.source = source;
    if (this.source != null) {
      this.source.addChangeListener(this);
    }
    this.extract = extract;
    this.index = index;
  }
  






  public CategoryDataset getUnderlyingDataset()
  {
    return source;
  }
  







  public TableOrder getExtractType()
  {
    return extract;
  }
  






  public int getExtractIndex()
  {
    return index;
  }
  





  public int getItemCount()
  {
    int result = 0;
    if (source != null) {
      if (extract == TableOrder.BY_ROW) {
        result = source.getColumnCount();
      }
      else if (extract == TableOrder.BY_COLUMN) {
        result = source.getRowCount();
      }
    }
    return result;
  }
  









  public Number getValue(int item)
  {
    Number result = null;
    if ((item < 0) || (item >= getItemCount()))
    {
      throw new IndexOutOfBoundsException("The 'item' index is out of bounds.");
    }
    
    if (extract == TableOrder.BY_ROW) {
      result = source.getValue(index, item);
    }
    else if (extract == TableOrder.BY_COLUMN) {
      result = source.getValue(item, index);
    }
    return result;
  }
  










  public Comparable getKey(int index)
  {
    Comparable result = null;
    if ((index < 0) || (index >= getItemCount()))
    {
      throw new IndexOutOfBoundsException("Invalid 'index': " + index);
    }
    if (extract == TableOrder.BY_ROW) {
      result = source.getColumnKey(index);
    }
    else if (extract == TableOrder.BY_COLUMN) {
      result = source.getRowKey(index);
    }
    return result;
  }
  







  public int getIndex(Comparable key)
  {
    int result = -1;
    if (source != null) {
      if (extract == TableOrder.BY_ROW) {
        result = source.getColumnIndex(key);
      }
      else if (extract == TableOrder.BY_COLUMN) {
        result = source.getRowIndex(key);
      }
    }
    return result;
  }
  







  public List getKeys()
  {
    List result = Collections.EMPTY_LIST;
    if (source != null) {
      if (extract == TableOrder.BY_ROW) {
        result = source.getColumnKeys();
      }
      else if (extract == TableOrder.BY_COLUMN) {
        result = source.getRowKeys();
      }
    }
    return result;
  }
  








  public Number getValue(Comparable key)
  {
    Number result = null;
    int keyIndex = getIndex(key);
    if (keyIndex != -1) {
      if (extract == TableOrder.BY_ROW) {
        result = source.getValue(index, keyIndex);
      }
      else if (extract == TableOrder.BY_COLUMN) {
        result = source.getValue(keyIndex, index);
      }
    }
    return result;
  }
  






  public void datasetChanged(DatasetChangeEvent event)
  {
    fireDatasetChanged();
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof PieDataset)) {
      return false;
    }
    PieDataset that = (PieDataset)obj;
    int count = getItemCount();
    if (that.getItemCount() != count) {
      return false;
    }
    for (int i = 0; i < count; i++) {
      Comparable k1 = getKey(i);
      Comparable k2 = that.getKey(i);
      if (!k1.equals(k2)) {
        return false;
      }
      
      Number v1 = getValue(i);
      Number v2 = that.getValue(i);
      if (v1 == null) {
        if (v2 != null) {
          return false;
        }
        
      }
      else if (!v1.equals(v2)) {
        return false;
      }
    }
    
    return true;
  }
}
