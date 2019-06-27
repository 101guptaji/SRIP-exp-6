package org.jfree.chart.labels;

import org.jfree.data.xy.XYDataset;

public abstract interface XYToolTipGenerator
{
  public abstract String generateToolTip(XYDataset paramXYDataset, int paramInt1, int paramInt2);
}
