package org.jfree.chart.labels;

import org.jfree.data.general.PieDataset;

public abstract interface PieToolTipGenerator
{
  public abstract String generateToolTip(PieDataset paramPieDataset, Comparable paramComparable);
}
