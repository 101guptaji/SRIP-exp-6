package org.jfree.chart;

import java.util.EventListener;

public abstract interface ChartMouseListener
  extends EventListener
{
  public abstract void chartMouseClicked(ChartMouseEvent paramChartMouseEvent);
  
  public abstract void chartMouseMoved(ChartMouseEvent paramChartMouseEvent);
}
