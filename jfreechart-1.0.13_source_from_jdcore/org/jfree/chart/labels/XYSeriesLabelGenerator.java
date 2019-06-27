package org.jfree.chart.labels;

import org.jfree.data.xy.XYDataset;

public abstract interface XYSeriesLabelGenerator
{
  public abstract String generateLabel(XYDataset paramXYDataset, int paramInt);
}
