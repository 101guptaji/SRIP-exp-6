package org.jfree.data.gantt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jfree.data.general.Series;
import org.jfree.util.ObjectUtilities;
























































public class TaskSeries
  extends Series
{
  private List tasks;
  
  public TaskSeries(String name)
  {
    super(name);
    tasks = new ArrayList();
  }
  






  public void add(Task task)
  {
    if (task == null) {
      throw new IllegalArgumentException("Null 'task' argument.");
    }
    tasks.add(task);
    fireSeriesChanged();
  }
  






  public void remove(Task task)
  {
    tasks.remove(task);
    fireSeriesChanged();
  }
  




  public void removeAll()
  {
    tasks.clear();
    fireSeriesChanged();
  }
  




  public int getItemCount()
  {
    return tasks.size();
  }
  






  public Task get(int index)
  {
    return (Task)tasks.get(index);
  }
  






  public Task get(String description)
  {
    Task result = null;
    int count = tasks.size();
    for (int i = 0; i < count; i++) {
      Task t = (Task)tasks.get(i);
      if (t.getDescription().equals(description)) {
        result = t;
        break;
      }
    }
    return result;
  }
  




  public List getTasks()
  {
    return Collections.unmodifiableList(tasks);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof TaskSeries)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    TaskSeries that = (TaskSeries)obj;
    if (!tasks.equals(tasks)) {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    TaskSeries clone = (TaskSeries)super.clone();
    tasks = ((List)ObjectUtilities.deepClone(tasks));
    return clone;
  }
}
