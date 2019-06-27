package org.jfree.chart.urls;

import org.jfree.data.xy.XYZDataset;

public abstract interface XYZURLGenerator
  extends XYURLGenerator
{
  public abstract String generateURL(XYZDataset paramXYZDataset, int paramInt1, int paramInt2);
}
