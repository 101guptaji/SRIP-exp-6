package org.jfree.chart.event;

import java.util.EventListener;

public abstract interface ChartProgressListener
  extends EventListener
{
  public abstract void chartProgress(ChartProgressEvent paramChartProgressEvent);
}
