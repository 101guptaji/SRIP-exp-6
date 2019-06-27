package org.jfree.data.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.general.AbstractDataset;
import org.jfree.util.PublicCloneable;
































































public class SlidingCategoryDataset
  extends AbstractDataset
  implements CategoryDataset
{
  private CategoryDataset underlying;
  private int firstCategoryIndex;
  private int maximumCategoryCount;
  
  public SlidingCategoryDataset(CategoryDataset underlying, int firstColumn, int maxColumns)
  {
    this.underlying = underlying;
    firstCategoryIndex = firstColumn;
    maximumCategoryCount = maxColumns;
  }
  




  public CategoryDataset getUnderlyingDataset()
  {
    return underlying;
  }
  






  public int getFirstCategoryIndex()
  {
    return firstCategoryIndex;
  }
  








  public void setFirstCategoryIndex(int first)
  {
    if ((first < 0) || (first >= underlying.getColumnCount())) {
      throw new IllegalArgumentException("Invalid index.");
    }
    firstCategoryIndex = first;
    fireDatasetChanged();
  }
  






  public int getMaximumCategoryCount()
  {
    return maximumCategoryCount;
  }
  







  public void setMaximumCategoryCount(int max)
  {
    if (max < 0) {
      throw new IllegalArgumentException("Requires 'max' >= 0.");
    }
    maximumCategoryCount = max;
    fireDatasetChanged();
  }
  




  private int lastCategoryIndex()
  {
    if (maximumCategoryCount == 0) {
      return -1;
    }
    return Math.min(firstCategoryIndex + maximumCategoryCount, underlying.getColumnCount()) - 1;
  }
  







  public int getColumnIndex(Comparable key)
  {
    int index = underlying.getColumnIndex(key);
    if ((index >= firstCategoryIndex) && (index <= lastCategoryIndex())) {
      return index - firstCategoryIndex;
    }
    return -1;
  }
  








  public Comparable getColumnKey(int column)
  {
    return underlying.getColumnKey(column + firstCategoryIndex);
  }
  






  public List getColumnKeys()
  {
    List result = new ArrayList();
    int last = lastCategoryIndex();
    for (int i = firstCategoryIndex; i <= last; i++) {
      result.add(underlying.getColumnKey(i));
    }
    return Collections.unmodifiableList(result);
  }
  






  public int getRowIndex(Comparable key)
  {
    return underlying.getRowIndex(key);
  }
  








  public Comparable getRowKey(int row)
  {
    return underlying.getRowKey(row);
  }
  




  public List getRowKeys()
  {
    return underlying.getRowKeys();
  }
  









  public Number getValue(Comparable rowKey, Comparable columnKey)
  {
    int r = getRowIndex(rowKey);
    int c = getColumnIndex(columnKey);
    if (c != -1) {
      return underlying.getValue(r, c + firstCategoryIndex);
    }
    
    throw new UnknownKeyException("Unknown columnKey: " + columnKey);
  }
  





  public int getColumnCount()
  {
    int last = lastCategoryIndex();
    if (last == -1) {
      return 0;
    }
    
    return Math.max(last - firstCategoryIndex + 1, 0);
  }
  





  public int getRowCount()
  {
    return underlying.getRowCount();
  }
  







  public Number getValue(int row, int column)
  {
    return underlying.getValue(row, column + firstCategoryIndex);
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof SlidingCategoryDataset)) {
      return false;
    }
    SlidingCategoryDataset that = (SlidingCategoryDataset)obj;
    if (firstCategoryIndex != firstCategoryIndex) {
      return false;
    }
    if (maximumCategoryCount != maximumCategoryCount) {
      return false;
    }
    if (!underlying.equals(underlying)) {
      return false;
    }
    return true;
  }
  












  public Object clone()
    throws CloneNotSupportedException
  {
    SlidingCategoryDataset clone = (SlidingCategoryDataset)super.clone();
    if ((underlying instanceof PublicCloneable)) {
      PublicCloneable pc = (PublicCloneable)underlying;
      underlying = ((CategoryDataset)pc.clone());
    }
    return clone;
  }
}
