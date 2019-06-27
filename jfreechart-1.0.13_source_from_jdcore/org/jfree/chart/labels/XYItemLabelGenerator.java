package org.jfree.chart.labels;

import org.jfree.data.xy.XYDataset;

public abstract interface XYItemLabelGenerator
{
  public abstract String generateLabel(XYDataset paramXYDataset, int paramInt1, int paramInt2);
}
