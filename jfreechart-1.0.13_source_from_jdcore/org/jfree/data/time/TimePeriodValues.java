package org.jfree.data.time;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jfree.data.general.Series;
import org.jfree.data.general.SeriesException;
import org.jfree.util.ObjectUtilities;



































































public class TimePeriodValues
  extends Series
  implements Serializable
{
  static final long serialVersionUID = -2210593619794989709L;
  protected static final String DEFAULT_DOMAIN_DESCRIPTION = "Time";
  protected static final String DEFAULT_RANGE_DESCRIPTION = "Value";
  private String domain;
  private String range;
  private List data;
  private int minStartIndex = -1;
  

  private int maxStartIndex = -1;
  

  private int minMiddleIndex = -1;
  

  private int maxMiddleIndex = -1;
  

  private int minEndIndex = -1;
  

  private int maxEndIndex = -1;
  




  public TimePeriodValues(String name)
  {
    this(name, "Time", "Value");
  }
  










  public TimePeriodValues(String name, String domain, String range)
  {
    super(name);
    this.domain = domain;
    this.range = range;
    data = new ArrayList();
  }
  







  public String getDomainDescription()
  {
    return domain;
  }
  







  public void setDomainDescription(String description)
  {
    String old = domain;
    domain = description;
    firePropertyChange("Domain", old, description);
  }
  







  public String getRangeDescription()
  {
    return range;
  }
  







  public void setRangeDescription(String description)
  {
    String old = range;
    range = description;
    firePropertyChange("Range", old, description);
  }
  




  public int getItemCount()
  {
    return data.size();
  }
  







  public TimePeriodValue getDataItem(int index)
  {
    return (TimePeriodValue)data.get(index);
  }
  









  public TimePeriod getTimePeriod(int index)
  {
    return getDataItem(index).getPeriod();
  }
  









  public Number getValue(int index)
  {
    return getDataItem(index).getValue();
  }
  





  public void add(TimePeriodValue item)
  {
    if (item == null) {
      throw new IllegalArgumentException("Null item not allowed.");
    }
    data.add(item);
    updateBounds(item.getPeriod(), data.size() - 1);
    fireSeriesChanged();
  }
  






  private void updateBounds(TimePeriod period, int index)
  {
    long start = period.getStart().getTime();
    long end = period.getEnd().getTime();
    long middle = start + (end - start) / 2L;
    
    if (minStartIndex >= 0) {
      long minStart = getDataItem(minStartIndex).getPeriod().getStart().getTime();
      
      if (start < minStart) {
        minStartIndex = index;
      }
    }
    else {
      minStartIndex = index;
    }
    
    if (maxStartIndex >= 0) {
      long maxStart = getDataItem(maxStartIndex).getPeriod().getStart().getTime();
      
      if (start > maxStart) {
        maxStartIndex = index;
      }
    }
    else {
      maxStartIndex = index;
    }
    
    if (minMiddleIndex >= 0) {
      long s = getDataItem(minMiddleIndex).getPeriod().getStart().getTime();
      
      long e = getDataItem(minMiddleIndex).getPeriod().getEnd().getTime();
      
      long minMiddle = s + (e - s) / 2L;
      if (middle < minMiddle) {
        minMiddleIndex = index;
      }
    }
    else {
      minMiddleIndex = index;
    }
    
    if (maxMiddleIndex >= 0) {
      long s = getDataItem(maxMiddleIndex).getPeriod().getStart().getTime();
      
      long e = getDataItem(maxMiddleIndex).getPeriod().getEnd().getTime();
      
      long maxMiddle = s + (e - s) / 2L;
      if (middle > maxMiddle) {
        maxMiddleIndex = index;
      }
    }
    else {
      maxMiddleIndex = index;
    }
    
    if (minEndIndex >= 0) {
      long minEnd = getDataItem(minEndIndex).getPeriod().getEnd().getTime();
      
      if (end < minEnd) {
        minEndIndex = index;
      }
    }
    else {
      minEndIndex = index;
    }
    
    if (maxEndIndex >= 0) {
      long maxEnd = getDataItem(maxEndIndex).getPeriod().getEnd().getTime();
      
      if (end > maxEnd) {
        maxEndIndex = index;
      }
    }
    else {
      maxEndIndex = index;
    }
  }
  



  private void recalculateBounds()
  {
    minStartIndex = -1;
    minMiddleIndex = -1;
    minEndIndex = -1;
    maxStartIndex = -1;
    maxMiddleIndex = -1;
    maxEndIndex = -1;
    for (int i = 0; i < data.size(); i++) {
      TimePeriodValue tpv = (TimePeriodValue)data.get(i);
      updateBounds(tpv.getPeriod(), i);
    }
  }
  








  public void add(TimePeriod period, double value)
  {
    TimePeriodValue item = new TimePeriodValue(period, value);
    add(item);
  }
  






  public void add(TimePeriod period, Number value)
  {
    TimePeriodValue item = new TimePeriodValue(period, value);
    add(item);
  }
  






  public void update(int index, Number value)
  {
    TimePeriodValue item = getDataItem(index);
    item.setValue(value);
    fireSeriesChanged();
  }
  






  public void delete(int start, int end)
  {
    for (int i = 0; i <= end - start; i++) {
      data.remove(start);
    }
    recalculateBounds();
    fireSeriesChanged();
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof TimePeriodValues)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    TimePeriodValues that = (TimePeriodValues)obj;
    if (!ObjectUtilities.equal(getDomainDescription(), that.getDomainDescription()))
    {
      return false;
    }
    if (!ObjectUtilities.equal(getRangeDescription(), that.getRangeDescription()))
    {
      return false;
    }
    int count = getItemCount();
    if (count != that.getItemCount()) {
      return false;
    }
    for (int i = 0; i < count; i++) {
      if (!getDataItem(i).equals(that.getDataItem(i))) {
        return false;
      }
    }
    return true;
  }
  





  public int hashCode()
  {
    int result = domain != null ? domain.hashCode() : 0;
    result = 29 * result + (range != null ? range.hashCode() : 0);
    result = 29 * result + data.hashCode();
    result = 29 * result + minStartIndex;
    result = 29 * result + maxStartIndex;
    result = 29 * result + minMiddleIndex;
    result = 29 * result + maxMiddleIndex;
    result = 29 * result + minEndIndex;
    result = 29 * result + maxEndIndex;
    return result;
  }
  













  public Object clone()
    throws CloneNotSupportedException
  {
    Object clone = createCopy(0, getItemCount() - 1);
    return clone;
  }
  











  public TimePeriodValues createCopy(int start, int end)
    throws CloneNotSupportedException
  {
    TimePeriodValues copy = (TimePeriodValues)super.clone();
    
    data = new ArrayList();
    if (data.size() > 0) {
      for (int index = start; index <= end; index++) {
        TimePeriodValue item = (TimePeriodValue)data.get(index);
        TimePeriodValue clone = (TimePeriodValue)item.clone();
        try {
          copy.add(clone);
        }
        catch (SeriesException e) {
          System.err.println("Failed to add cloned item.");
        }
      }
    }
    return copy;
  }
  





  public int getMinStartIndex()
  {
    return minStartIndex;
  }
  




  public int getMaxStartIndex()
  {
    return maxStartIndex;
  }
  





  public int getMinMiddleIndex()
  {
    return minMiddleIndex;
  }
  





  public int getMaxMiddleIndex()
  {
    return maxMiddleIndex;
  }
  




  public int getMinEndIndex()
  {
    return minEndIndex;
  }
  




  public int getMaxEndIndex()
  {
    return maxEndIndex;
  }
}
