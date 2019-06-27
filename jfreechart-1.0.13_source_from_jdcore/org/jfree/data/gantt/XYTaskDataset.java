package org.jfree.data.gantt;

import java.util.Date;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.IntervalXYDataset;






























































public class XYTaskDataset
  extends AbstractXYDataset
  implements IntervalXYDataset, DatasetChangeListener
{
  private TaskSeriesCollection underlying;
  private double seriesWidth;
  private boolean transposed;
  
  public XYTaskDataset(TaskSeriesCollection tasks)
  {
    if (tasks == null) {
      throw new IllegalArgumentException("Null 'tasks' argument.");
    }
    underlying = tasks;
    seriesWidth = 0.8D;
    underlying.addChangeListener(this);
  }
  





  public TaskSeriesCollection getTasks()
  {
    return underlying;
  }
  






  public double getSeriesWidth()
  {
    return seriesWidth;
  }
  







  public void setSeriesWidth(double w)
  {
    if (w <= 0.0D) {
      throw new IllegalArgumentException("Requires 'w' > 0.0.");
    }
    seriesWidth = w;
    fireDatasetChanged();
  }
  










  public boolean isTransposed()
  {
    return transposed;
  }
  







  public void setTransposed(boolean transposed)
  {
    this.transposed = transposed;
    fireDatasetChanged();
  }
  




  public int getSeriesCount()
  {
    return underlying.getSeriesCount();
  }
  






  public Comparable getSeriesKey(int series)
  {
    return underlying.getSeriesKey(series);
  }
  






  public int getItemCount(int series)
  {
    return underlying.getSeries(series).getItemCount();
  }
  







  public double getXValue(int series, int item)
  {
    if (!transposed) {
      return getSeriesValue(series);
    }
    
    return getItemValue(series, item);
  }
  










  public double getStartXValue(int series, int item)
  {
    if (!transposed) {
      return getSeriesStartValue(series);
    }
    
    return getItemStartValue(series, item);
  }
  










  public double getEndXValue(int series, int item)
  {
    if (!transposed) {
      return getSeriesEndValue(series);
    }
    
    return getItemEndValue(series, item);
  }
  








  public Number getX(int series, int item)
  {
    return new Double(getXValue(series, item));
  }
  









  public Number getStartX(int series, int item)
  {
    return new Double(getStartXValue(series, item));
  }
  









  public Number getEndX(int series, int item)
  {
    return new Double(getEndXValue(series, item));
  }
  







  public double getYValue(int series, int item)
  {
    if (!transposed) {
      return getItemValue(series, item);
    }
    
    return getSeriesValue(series);
  }
  









  public double getStartYValue(int series, int item)
  {
    if (!transposed) {
      return getItemStartValue(series, item);
    }
    
    return getSeriesStartValue(series);
  }
  









  public double getEndYValue(int series, int item)
  {
    if (!transposed) {
      return getItemEndValue(series, item);
    }
    
    return getSeriesEndValue(series);
  }
  










  public Number getY(int series, int item)
  {
    return new Double(getYValue(series, item));
  }
  








  public Number getStartY(int series, int item)
  {
    return new Double(getStartYValue(series, item));
  }
  








  public Number getEndY(int series, int item)
  {
    return new Double(getEndYValue(series, item));
  }
  
  private double getSeriesValue(int series) {
    return series;
  }
  
  private double getSeriesStartValue(int series) {
    return series - seriesWidth / 2.0D;
  }
  
  private double getSeriesEndValue(int series) {
    return series + seriesWidth / 2.0D;
  }
  
  private double getItemValue(int series, int item) {
    TaskSeries s = underlying.getSeries(series);
    Task t = s.get(item);
    TimePeriod duration = t.getDuration();
    Date start = duration.getStart();
    Date end = duration.getEnd();
    return (start.getTime() + end.getTime()) / 2.0D;
  }
  
  private double getItemStartValue(int series, int item) {
    TaskSeries s = underlying.getSeries(series);
    Task t = s.get(item);
    TimePeriod duration = t.getDuration();
    Date start = duration.getStart();
    return start.getTime();
  }
  
  private double getItemEndValue(int series, int item) {
    TaskSeries s = underlying.getSeries(series);
    Task t = s.get(item);
    TimePeriod duration = t.getDuration();
    Date end = duration.getEnd();
    return end.getTime();
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
    if (!(obj instanceof XYTaskDataset)) {
      return false;
    }
    XYTaskDataset that = (XYTaskDataset)obj;
    if (seriesWidth != seriesWidth) {
      return false;
    }
    if (transposed != transposed) {
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
    XYTaskDataset clone = (XYTaskDataset)super.clone();
    underlying = ((TaskSeriesCollection)underlying.clone());
    return clone;
  }
}
