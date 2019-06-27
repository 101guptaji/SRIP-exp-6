package org.jfree.chart.plot;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.data.Range;

public abstract interface ValueAxisPlot
{
  public abstract Range getDataRange(ValueAxis paramValueAxis);
}
