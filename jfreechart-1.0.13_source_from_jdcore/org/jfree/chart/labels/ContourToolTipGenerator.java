package org.jfree.chart.labels;

import org.jfree.data.contour.ContourDataset;

/**
 * @deprecated
 */
public abstract interface ContourToolTipGenerator
{
  public abstract String generateToolTip(ContourDataset paramContourDataset, int paramInt);
}
