package org.jfree.chart.urls;

import org.jfree.data.xy.XYDataset;

public abstract interface XYURLGenerator
{
  public abstract String generateURL(XYDataset paramXYDataset, int paramInt1, int paramInt2);
}
