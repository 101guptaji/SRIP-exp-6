package org.jfree.chart.event;

import java.util.EventListener;

public abstract interface OverlayChangeListener
  extends EventListener
{
  public abstract void overlayChanged(OverlayChangeEvent paramOverlayChangeEvent);
}
