package org.jfree.data.xy;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jfree.data.general.Series;
import org.jfree.data.general.SeriesException;
import org.jfree.util.ObjectUtilities;




























































































public class XYSeries
  extends Series
  implements Cloneable, Serializable
{
  static final long serialVersionUID = -5908509288197150436L;
  protected List data;
  private int maximumItemCount = Integer.MAX_VALUE;
  


  private boolean autoSort;
  


  private boolean allowDuplicateXValues;
  


  private double minX;
  


  private double maxX;
  


  private double minY;
  


  private double maxY;
  



  public XYSeries(Comparable key)
  {
    this(key, true, true);
  }
  







  public XYSeries(Comparable key, boolean autoSort)
  {
    this(key, autoSort, true);
  }
  










  public XYSeries(Comparable key, boolean autoSort, boolean allowDuplicateXValues)
  {
    super(key);
    data = new ArrayList();
    this.autoSort = autoSort;
    this.allowDuplicateXValues = allowDuplicateXValues;
    minX = NaN.0D;
    maxX = NaN.0D;
    minY = NaN.0D;
    maxY = NaN.0D;
  }
  










  public double getMinX()
  {
    return minX;
  }
  










  public double getMaxX()
  {
    return maxX;
  }
  










  public double getMinY()
  {
    return minY;
  }
  










  public double getMaxY()
  {
    return maxY;
  }
  






  private void updateBoundsForAddedItem(XYDataItem item)
  {
    double x = item.getXValue();
    minX = minIgnoreNaN(minX, x);
    maxX = maxIgnoreNaN(maxX, x);
    if (item.getY() != null) {
      double y = item.getYValue();
      minY = minIgnoreNaN(minY, y);
      maxY = maxIgnoreNaN(maxY, y);
    }
  }
  







  private void updateBoundsForRemovedItem(XYDataItem item)
  {
    boolean itemContributesToXBounds = false;
    boolean itemContributesToYBounds = false;
    double x = item.getXValue();
    if ((!Double.isNaN(x)) && (
      (x <= minX) || (x >= maxX))) {
      itemContributesToXBounds = true;
    }
    
    if (item.getY() != null) {
      double y = item.getYValue();
      if ((!Double.isNaN(y)) && (
        (y <= minY) || (y >= maxY))) {
        itemContributesToYBounds = true;
      }
    }
    
    if (itemContributesToYBounds) {
      findBoundsByIteration();
    }
    else if (itemContributesToXBounds) {
      if (getAutoSort()) {
        minX = getX(0).doubleValue();
        maxX = getX(getItemCount() - 1).doubleValue();
      }
      else {
        findBoundsByIteration();
      }
    }
  }
  





  private void findBoundsByIteration()
  {
    minX = NaN.0D;
    maxX = NaN.0D;
    minY = NaN.0D;
    maxY = NaN.0D;
    Iterator iterator = data.iterator();
    while (iterator.hasNext()) {
      XYDataItem item = (XYDataItem)iterator.next();
      updateBoundsForAddedItem(item);
    }
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
  





  public List getItems()
  {
    return Collections.unmodifiableList(data);
  }
  







  public int getMaximumItemCount()
  {
    return maximumItemCount;
  }
  













  public void setMaximumItemCount(int maximum)
  {
    maximumItemCount = maximum;
    int remove = data.size() - maximum;
    if (remove > 0) {
      data.subList(0, remove).clear();
      findBoundsByIteration();
      fireSeriesChanged();
    }
  }
  






  public void add(XYDataItem item)
  {
    add(item, true);
  }
  






  public void add(double x, double y)
  {
    add(new Double(x), new Double(y), true);
  }
  









  public void add(double x, double y, boolean notify)
  {
    add(new Double(x), new Double(y), notify);
  }
  







  public void add(double x, Number y)
  {
    add(new Double(x), y);
  }
  










  public void add(double x, Number y, boolean notify)
  {
    add(new Double(x), y, notify);
  }
  














  public void add(Number x, Number y)
  {
    add(x, y, true);
  }
  













  public void add(Number x, Number y, boolean notify)
  {
    XYDataItem item = new XYDataItem(x, y);
    add(item, notify);
  }
  








  public void add(XYDataItem item, boolean notify)
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

        int index = indexOf(item.getX());
        if (index >= 0) {
          throw new SeriesException("X-value already exists.");
        }
      }
      data.add(item);
    }
    updateBoundsForAddedItem(item);
    if (getItemCount() > maximumItemCount) {
      XYDataItem removed = (XYDataItem)data.remove(0);
      updateBoundsForRemovedItem(removed);
    }
    if (notify) {
      fireSeriesChanged();
    }
  }
  






  public void delete(int start, int end)
  {
    data.subList(start, end + 1).clear();
    findBoundsByIteration();
    fireSeriesChanged();
  }
  







  public XYDataItem remove(int index)
  {
    XYDataItem removed = (XYDataItem)data.remove(index);
    updateBoundsForRemovedItem(removed);
    fireSeriesChanged();
    return removed;
  }
  









  public XYDataItem remove(Number x)
  {
    return remove(indexOf(x));
  }
  



  public void clear()
  {
    if (data.size() > 0) {
      data.clear();
      minX = NaN.0D;
      maxX = NaN.0D;
      minY = NaN.0D;
      maxY = NaN.0D;
      fireSeriesChanged();
    }
  }
  






  public XYDataItem getDataItem(int index)
  {
    return (XYDataItem)data.get(index);
  }
  






  public Number getX(int index)
  {
    return getDataItem(index).getX();
  }
  






  public Number getY(int index)
  {
    return getDataItem(index).getY();
  }
  






  /**
   * @deprecated
   */
  public void update(int index, Number y)
  {
    XYDataItem item = getDataItem(index);
    

    boolean iterate = false;
    double oldY = item.getYValue();
    if (!Double.isNaN(oldY)) {
      iterate = (oldY <= minY) || (oldY >= maxY);
    }
    item.setY(y);
    
    if (iterate) {
      findBoundsByIteration();
    }
    else if (y != null) {
      double yy = y.doubleValue();
      minY = minIgnoreNaN(minY, yy);
      maxY = maxIgnoreNaN(maxY, yy);
    }
    fireSeriesChanged();
  }
  








  private double minIgnoreNaN(double a, double b)
  {
    if (Double.isNaN(a)) {
      return b;
    }
    
    if (Double.isNaN(b)) {
      return a;
    }
    
    return Math.min(a, b);
  }
  










  private double maxIgnoreNaN(double a, double b)
  {
    if (Double.isNaN(a)) {
      return b;
    }
    
    if (Double.isNaN(b)) {
      return a;
    }
    
    return Math.max(a, b);
  }
  










  public void updateByIndex(int index, Number y)
  {
    update(index, y);
  }
  








  public void update(Number x, Number y)
  {
    int index = indexOf(x);
    if (index < 0) {
      throw new SeriesException("No observation for x = " + x);
    }
    
    updateByIndex(index, y);
  }
  











  public XYDataItem addOrUpdate(double x, double y)
  {
    return addOrUpdate(new Double(x), new Double(y));
  }
  









  public XYDataItem addOrUpdate(Number x, Number y)
  {
    if (x == null) {
      throw new IllegalArgumentException("Null 'x' argument.");
    }
    if (allowDuplicateXValues) {
      add(x, y);
      return null;
    }
    

    XYDataItem overwritten = null;
    int index = indexOf(x);
    if (index >= 0) {
      XYDataItem existing = (XYDataItem)data.get(index);
      try {
        overwritten = (XYDataItem)existing.clone();
      }
      catch (CloneNotSupportedException e) {
        throw new SeriesException("Couldn't clone XYDataItem!");
      }
      
      boolean iterate = false;
      double oldY = existing.getYValue();
      if (!Double.isNaN(oldY)) {
        iterate = (oldY <= minY) || (oldY >= maxY);
      }
      existing.setY(y);
      
      if (iterate) {
        findBoundsByIteration();
      }
      else if (y != null) {
        double yy = y.doubleValue();
        minY = minIgnoreNaN(minY, yy);
        maxY = minIgnoreNaN(maxY, yy);
      }
      

    }
    else
    {

      XYDataItem item = new XYDataItem(x, y);
      if (autoSort) {
        data.add(-index - 1, item);
      }
      else {
        data.add(item);
      }
      updateBoundsForAddedItem(item);
      

      if (getItemCount() > maximumItemCount) {
        XYDataItem removed = (XYDataItem)data.remove(0);
        updateBoundsForRemovedItem(removed);
      }
    }
    fireSeriesChanged();
    return overwritten;
  }
  









  public int indexOf(Number x)
  {
    if (autoSort) {
      return Collections.binarySearch(data, new XYDataItem(x, null));
    }
    
    for (int i = 0; i < data.size(); i++) {
      XYDataItem item = (XYDataItem)data.get(i);
      if (item.getX().equals(x)) {
        return i;
      }
    }
    return -1;
  }
  







  public double[][] toArray()
  {
    int itemCount = getItemCount();
    double[][] result = new double[2][itemCount];
    for (int i = 0; i < itemCount; i++) {
      result[0][i] = getX(i).doubleValue();
      Number y = getY(i);
      if (y != null) {
        result[1][i] = y.doubleValue();
      }
      else {
        result[1][i] = NaN.0D;
      }
    }
    return result;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    XYSeries clone = (XYSeries)super.clone();
    data = ((List)ObjectUtilities.deepClone(data));
    return clone;
  }
  










  public XYSeries createCopy(int start, int end)
    throws CloneNotSupportedException
  {
    XYSeries copy = (XYSeries)super.clone();
    data = new ArrayList();
    if (data.size() > 0) {
      for (int index = start; index <= end; index++) {
        XYDataItem item = (XYDataItem)data.get(index);
        XYDataItem clone = (XYDataItem)item.clone();
        try {
          copy.add(clone);
        }
        catch (SeriesException e) {
          System.err.println("Unable to add cloned data item.");
        }
      }
    }
    return copy;
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYSeries)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    XYSeries that = (XYSeries)obj;
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
      XYDataItem item = getDataItem(0);
      result = 29 * result + item.hashCode();
    }
    if (count > 1) {
      XYDataItem item = getDataItem(count - 1);
      result = 29 * result + item.hashCode();
    }
    if (count > 2) {
      XYDataItem item = getDataItem(count / 2);
      result = 29 * result + item.hashCode();
    }
    result = 29 * result + maximumItemCount;
    result = 29 * result + (autoSort ? 1 : 0);
    result = 29 * result + (allowDuplicateXValues ? 1 : 0);
    return result;
  }
}
