package org.jfree.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jfree.data.general.Series;
import org.jfree.data.general.SeriesException;
import org.jfree.util.ObjectUtilities;



















































public class ComparableObjectSeries
  extends Series
  implements Cloneable, Serializable
{
  protected List data;
  private int maximumItemCount = Integer.MAX_VALUE;
  



  private boolean autoSort;
  


  private boolean allowDuplicateXValues;
  



  public ComparableObjectSeries(Comparable key)
  {
    this(key, true, true);
  }
  










  public ComparableObjectSeries(Comparable key, boolean autoSort, boolean allowDuplicateXValues)
  {
    super(key);
    data = new ArrayList();
    this.autoSort = autoSort;
    this.allowDuplicateXValues = allowDuplicateXValues;
  }
  






  public boolean getAutoSort()
  {
    return autoSort;
  }
  





  public boolean getAllowDuplicateXValues()
  {
    return allowDuplicateXValues;
  }
  




  public int getItemCount()
  {
    return data.size();
  }
  






  public int getMaximumItemCount()
  {
    return maximumItemCount;
  }
  













  public void setMaximumItemCount(int maximum)
  {
    maximumItemCount = maximum;
    boolean dataRemoved = false;
    while (data.size() > maximum) {
      data.remove(0);
      dataRemoved = true;
    }
    if (dataRemoved) {
      fireSeriesChanged();
    }
  }
  










  protected void add(Comparable x, Object y)
  {
    add(x, y, true);
  }
  













  protected void add(Comparable x, Object y, boolean notify)
  {
    ComparableObjectItem item = new ComparableObjectItem(x, y);
    add(item, notify);
  }
  









  protected void add(ComparableObjectItem item, boolean notify)
  {
    if (item == null) {
      throw new IllegalArgumentException("Null 'item' argument.");
    }
    
    if (autoSort) {
      int index = Collections.binarySearch(data, item);
      if (index < 0) {
        data.add(-index - 1, item);

      }
      else if (allowDuplicateXValues)
      {
        int size = data.size();
        
        while ((index < size) && (item.compareTo(data.get(index)) == 0)) {
          index++;
        }
        if (index < data.size()) {
          data.add(index, item);
        }
        else {
          data.add(item);
        }
      }
      else {
        throw new SeriesException("X-value already exists.");
      }
    }
    else
    {
      if (!allowDuplicateXValues)
      {

        int index = indexOf(item.getComparable());
        if (index >= 0) {
          throw new SeriesException("X-value already exists.");
        }
      }
      data.add(item);
    }
    if (getItemCount() > maximumItemCount) {
      data.remove(0);
    }
    if (notify) {
      fireSeriesChanged();
    }
  }
  









  public int indexOf(Comparable x)
  {
    if (autoSort) {
      return Collections.binarySearch(data, new ComparableObjectItem(x, null));
    }
    

    for (int i = 0; i < data.size(); i++) {
      ComparableObjectItem item = (ComparableObjectItem)data.get(i);
      
      if (item.getComparable().equals(x)) {
        return i;
      }
    }
    return -1;
  }
  









  protected void update(Comparable x, Object y)
  {
    int index = indexOf(x);
    if (index < 0) {
      throw new SeriesException("No observation for x = " + x);
    }
    
    ComparableObjectItem item = getDataItem(index);
    item.setObject(y);
    fireSeriesChanged();
  }
  







  protected void updateByIndex(int index, Object y)
  {
    ComparableObjectItem item = getDataItem(index);
    item.setObject(y);
    fireSeriesChanged();
  }
  






  protected ComparableObjectItem getDataItem(int index)
  {
    return (ComparableObjectItem)data.get(index);
  }
  






  protected void delete(int start, int end)
  {
    for (int i = start; i <= end; i++) {
      data.remove(start);
    }
    fireSeriesChanged();
  }
  




  public void clear()
  {
    if (data.size() > 0) {
      data.clear();
      fireSeriesChanged();
    }
  }
  







  protected ComparableObjectItem remove(int index)
  {
    ComparableObjectItem result = (ComparableObjectItem)data.remove(index);
    
    fireSeriesChanged();
    return result;
  }
  







  public ComparableObjectItem remove(Comparable x)
  {
    return remove(indexOf(x));
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ComparableObjectSeries)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    ComparableObjectSeries that = (ComparableObjectSeries)obj;
    if (maximumItemCount != maximumItemCount) {
      return false;
    }
    if (autoSort != autoSort) {
      return false;
    }
    if (allowDuplicateXValues != allowDuplicateXValues) {
      return false;
    }
    if (!ObjectUtilities.equal(data, data)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = super.hashCode();
    

    int count = getItemCount();
    if (count > 0) {
      ComparableObjectItem item = getDataItem(0);
      result = 29 * result + item.hashCode();
    }
    if (count > 1) {
      ComparableObjectItem item = getDataItem(count - 1);
      result = 29 * result + item.hashCode();
    }
    if (count > 2) {
      ComparableObjectItem item = getDataItem(count / 2);
      result = 29 * result + item.hashCode();
    }
    result = 29 * result + maximumItemCount;
    result = 29 * result + (autoSort ? 1 : 0);
    result = 29 * result + (allowDuplicateXValues ? 1 : 0);
    return result;
  }
}
