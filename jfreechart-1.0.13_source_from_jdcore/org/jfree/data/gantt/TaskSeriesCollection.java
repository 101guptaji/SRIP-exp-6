package org.jfree.data.gantt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.jfree.data.general.AbstractSeriesDataset;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.time.TimePeriod;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
































































public class TaskSeriesCollection
  extends AbstractSeriesDataset
  implements GanttCategoryDataset, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -2065799050738449903L;
  private List keys;
  private List data;
  
  public TaskSeriesCollection()
  {
    keys = new ArrayList();
    data = new ArrayList();
  }
  








  public TaskSeries getSeries(Comparable key)
  {
    if (key == null) {
      throw new NullPointerException("Null 'key' argument.");
    }
    TaskSeries result = null;
    int index = getRowIndex(key);
    if (index >= 0) {
      result = getSeries(index);
    }
    return result;
  }
  








  public TaskSeries getSeries(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("Series index out of bounds");
    }
    return (TaskSeries)data.get(series);
  }
  




  public int getSeriesCount()
  {
    return getRowCount();
  }
  






  public Comparable getSeriesKey(int series)
  {
    TaskSeries ts = (TaskSeries)data.get(series);
    return ts.getKey();
  }
  




  public int getRowCount()
  {
    return data.size();
  }
  




  public List getRowKeys()
  {
    return data;
  }
  




  public int getColumnCount()
  {
    return keys.size();
  }
  




  public List getColumnKeys()
  {
    return keys;
  }
  






  public Comparable getColumnKey(int index)
  {
    return (Comparable)keys.get(index);
  }
  






  public int getColumnIndex(Comparable columnKey)
  {
    if (columnKey == null) {
      throw new IllegalArgumentException("Null 'columnKey' argument.");
    }
    return keys.indexOf(columnKey);
  }
  






  public int getRowIndex(Comparable rowKey)
  {
    int result = -1;
    int count = data.size();
    for (int i = 0; i < count; i++) {
      TaskSeries s = (TaskSeries)data.get(i);
      if (s.getKey().equals(rowKey)) {
        result = i;
        break;
      }
    }
    return result;
  }
  






  public Comparable getRowKey(int index)
  {
    TaskSeries series = (TaskSeries)data.get(index);
    return series.getKey();
  }
  






  public void add(TaskSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    data.add(series);
    series.addChangeListener(this);
    

    Iterator iterator = series.getTasks().iterator();
    while (iterator.hasNext()) {
      Task task = (Task)iterator.next();
      String key = task.getDescription();
      int index = keys.indexOf(key);
      if (index < 0) {
        keys.add(key);
      }
    }
    fireDatasetChanged();
  }
  






  public void remove(TaskSeries series)
  {
    if (series == null) {
      throw new IllegalArgumentException("Null 'series' argument.");
    }
    if (data.contains(series)) {
      series.removeChangeListener(this);
      data.remove(series);
      fireDatasetChanged();
    }
  }
  






  public void remove(int series)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("TaskSeriesCollection.remove(): index outside valid range.");
    }
    


    TaskSeries ts = (TaskSeries)data.get(series);
    ts.removeChangeListener(this);
    data.remove(series);
    fireDatasetChanged();
  }
  








  public void removeAll()
  {
    Iterator iterator = data.iterator();
    while (iterator.hasNext()) {
      TaskSeries series = (TaskSeries)iterator.next();
      series.removeChangeListener(this);
    }
    

    data.clear();
    fireDatasetChanged();
  }
  








  public Number getValue(Comparable rowKey, Comparable columnKey)
  {
    return getStartValue(rowKey, columnKey);
  }
  







  public Number getValue(int row, int column)
  {
    return getStartValue(row, column);
  }
  








  public Number getStartValue(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    int row = getRowIndex(rowKey);
    TaskSeries series = (TaskSeries)data.get(row);
    Task task = series.get(columnKey.toString());
    if (task != null) {
      TimePeriod duration = task.getDuration();
      if (duration != null) {
        result = new Long(duration.getStart().getTime());
      }
    }
    return result;
  }
  







  public Number getStartValue(int row, int column)
  {
    Comparable rowKey = getRowKey(row);
    Comparable columnKey = getColumnKey(column);
    return getStartValue(rowKey, columnKey);
  }
  








  public Number getEndValue(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    int row = getRowIndex(rowKey);
    TaskSeries series = (TaskSeries)data.get(row);
    Task task = series.get(columnKey.toString());
    if (task != null) {
      TimePeriod duration = task.getDuration();
      if (duration != null) {
        result = new Long(duration.getEnd().getTime());
      }
    }
    return result;
  }
  







  public Number getEndValue(int row, int column)
  {
    Comparable rowKey = getRowKey(row);
    Comparable columnKey = getColumnKey(column);
    return getEndValue(rowKey, columnKey);
  }
  







  public Number getPercentComplete(int row, int column)
  {
    Comparable rowKey = getRowKey(row);
    Comparable columnKey = getColumnKey(column);
    return getPercentComplete(rowKey, columnKey);
  }
  







  public Number getPercentComplete(Comparable rowKey, Comparable columnKey)
  {
    Number result = null;
    int row = getRowIndex(rowKey);
    TaskSeries series = (TaskSeries)data.get(row);
    Task task = series.get(columnKey.toString());
    if (task != null) {
      result = task.getPercentComplete();
    }
    return result;
  }
  







  public int getSubIntervalCount(int row, int column)
  {
    Comparable rowKey = getRowKey(row);
    Comparable columnKey = getColumnKey(column);
    return getSubIntervalCount(rowKey, columnKey);
  }
  







  public int getSubIntervalCount(Comparable rowKey, Comparable columnKey)
  {
    int result = 0;
    int row = getRowIndex(rowKey);
    TaskSeries series = (TaskSeries)data.get(row);
    Task task = series.get(columnKey.toString());
    if (task != null) {
      result = task.getSubtaskCount();
    }
    return result;
  }
  








  public Number getStartValue(int row, int column, int subinterval)
  {
    Comparable rowKey = getRowKey(row);
    Comparable columnKey = getColumnKey(column);
    return getStartValue(rowKey, columnKey, subinterval);
  }
  









  public Number getStartValue(Comparable rowKey, Comparable columnKey, int subinterval)
  {
    Number result = null;
    int row = getRowIndex(rowKey);
    TaskSeries series = (TaskSeries)data.get(row);
    Task task = series.get(columnKey.toString());
    if (task != null) {
      Task sub = task.getSubtask(subinterval);
      if (sub != null) {
        TimePeriod duration = sub.getDuration();
        result = new Long(duration.getStart().getTime());
      }
    }
    return result;
  }
  








  public Number getEndValue(int row, int column, int subinterval)
  {
    Comparable rowKey = getRowKey(row);
    Comparable columnKey = getColumnKey(column);
    return getEndValue(rowKey, columnKey, subinterval);
  }
  









  public Number getEndValue(Comparable rowKey, Comparable columnKey, int subinterval)
  {
    Number result = null;
    int row = getRowIndex(rowKey);
    TaskSeries series = (TaskSeries)data.get(row);
    Task task = series.get(columnKey.toString());
    if (task != null) {
      Task sub = task.getSubtask(subinterval);
      if (sub != null) {
        TimePeriod duration = sub.getDuration();
        result = new Long(duration.getEnd().getTime());
      }
    }
    return result;
  }
  








  public Number getPercentComplete(int row, int column, int subinterval)
  {
    Comparable rowKey = getRowKey(row);
    Comparable columnKey = getColumnKey(column);
    return getPercentComplete(rowKey, columnKey, subinterval);
  }
  









  public Number getPercentComplete(Comparable rowKey, Comparable columnKey, int subinterval)
  {
    Number result = null;
    int row = getRowIndex(rowKey);
    TaskSeries series = (TaskSeries)data.get(row);
    Task task = series.get(columnKey.toString());
    if (task != null) {
      Task sub = task.getSubtask(subinterval);
      if (sub != null) {
        result = sub.getPercentComplete();
      }
    }
    return result;
  }
  




  public void seriesChanged(SeriesChangeEvent event)
  {
    refreshKeys();
    fireDatasetChanged();
  }
  



  private void refreshKeys()
  {
    keys.clear();
    for (int i = 0; i < getSeriesCount(); i++) {
      TaskSeries series = (TaskSeries)data.get(i);
      
      Iterator iterator = series.getTasks().iterator();
      while (iterator.hasNext()) {
        Task task = (Task)iterator.next();
        String key = task.getDescription();
        int index = keys.indexOf(key);
        if (index < 0) {
          keys.add(key);
        }
      }
    }
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof TaskSeriesCollection)) {
      return false;
    }
    TaskSeriesCollection that = (TaskSeriesCollection)obj;
    if (!ObjectUtilities.equal(data, data)) {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    TaskSeriesCollection clone = (TaskSeriesCollection)super.clone();
    data = ((List)ObjectUtilities.deepClone(data));
    keys = new ArrayList(keys);
    return clone;
  }
}
