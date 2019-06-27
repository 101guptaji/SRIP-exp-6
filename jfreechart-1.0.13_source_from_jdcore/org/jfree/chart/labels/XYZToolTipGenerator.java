package org.jfree.chart.labels;

import org.jfree.data.xy.XYZDataset;

public abstract interface XYZToolTipGenerator
  extends XYToolTipGenerator
{
  public abstract String generateToolTip(XYZDataset paramXYZDataset, int paramInt1, int paramInt2);
}
