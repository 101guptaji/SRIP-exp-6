package org.jfree.chart.event;

import java.util.EventListener;

public abstract interface MarkerChangeListener
  extends EventListener
{
  public abstract void markerChanged(MarkerChangeEvent paramMarkerChangeEvent);
}
