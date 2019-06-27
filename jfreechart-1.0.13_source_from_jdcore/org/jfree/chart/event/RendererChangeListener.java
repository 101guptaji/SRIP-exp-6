package org.jfree.chart.event;

import java.util.EventListener;

public abstract interface RendererChangeListener
  extends EventListener
{
  public abstract void rendererChanged(RendererChangeEvent paramRendererChangeEvent);
}
