package org.jfree.data.gantt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriod;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;






























































public class Task
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 1094303785346988894L;
  private String description;
  private TimePeriod duration;
  private Double percentComplete;
  private List subtasks;
  
  public Task(String description, TimePeriod duration)
  {
    if (description == null) {
      throw new IllegalArgumentException("Null 'description' argument.");
    }
    this.description = description;
    this.duration = duration;
    percentComplete = null;
    subtasks = new ArrayList();
  }
  







  public Task(String description, Date start, Date end)
  {
    this(description, new SimpleTimePeriod(start, end));
  }
  




  public String getDescription()
  {
    return description;
  }
  




  public void setDescription(String description)
  {
    if (description == null) {
      throw new IllegalArgumentException("Null 'description' argument.");
    }
    this.description = description;
  }
  




  public TimePeriod getDuration()
  {
    return duration;
  }
  




  public void setDuration(TimePeriod duration)
  {
    this.duration = duration;
  }
  




  public Double getPercentComplete()
  {
    return percentComplete;
  }
  




  public void setPercentComplete(Double percent)
  {
    percentComplete = percent;
  }
  




  public void setPercentComplete(double percent)
  {
    setPercentComplete(new Double(percent));
  }
  




  public void addSubtask(Task subtask)
  {
    if (subtask == null) {
      throw new IllegalArgumentException("Null 'subtask' argument.");
    }
    subtasks.add(subtask);
  }
  




  public void removeSubtask(Task subtask)
  {
    subtasks.remove(subtask);
  }
  




  public int getSubtaskCount()
  {
    return subtasks.size();
  }
  






  public Task getSubtask(int index)
  {
    return (Task)subtasks.get(index);
  }
  






  public boolean equals(Object object)
  {
    if (object == this) {
      return true;
    }
    if (!(object instanceof Task)) {
      return false;
    }
    Task that = (Task)object;
    if (!ObjectUtilities.equal(description, description)) {
      return false;
    }
    if (!ObjectUtilities.equal(duration, duration)) {
      return false;
    }
    if (!ObjectUtilities.equal(percentComplete, percentComplete))
    {
      return false;
    }
    if (!ObjectUtilities.equal(subtasks, subtasks)) {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    Task clone = (Task)super.clone();
    return clone;
  }
}
