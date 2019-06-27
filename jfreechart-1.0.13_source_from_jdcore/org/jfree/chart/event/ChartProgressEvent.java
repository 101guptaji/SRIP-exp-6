package org.jfree.chart.event;

import java.util.EventObject;
import org.jfree.chart.JFreeChart;




























































public class ChartProgressEvent
  extends EventObject
{
  public static final int DRAWING_STARTED = 1;
  public static final int DRAWING_FINISHED = 2;
  private int type;
  private int percent;
  private JFreeChart chart;
  
  public ChartProgressEvent(Object source, JFreeChart chart, int type, int percent)
  {
    super(source);
    this.chart = chart;
    this.type = type;
  }
  




  public JFreeChart getChart()
  {
    return chart;
  }
  




  public void setChart(JFreeChart chart)
  {
    this.chart = chart;
  }
  




  public int getType()
  {
    return type;
  }
  




  public void setType(int type)
  {
    this.type = type;
  }
  




  public int getPercent()
  {
    return percent;
  }
  




  public void setPercent(int percent)
  {
    this.percent = percent;
  }
}
