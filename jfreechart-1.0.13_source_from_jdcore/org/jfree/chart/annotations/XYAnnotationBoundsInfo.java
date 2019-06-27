package org.jfree.chart.annotations;

import org.jfree.data.Range;

public abstract interface XYAnnotationBoundsInfo
{
  public abstract boolean getIncludeInDataBounds();
  
  public abstract Range getXRange();
  
  public abstract Range getYRange();
}
