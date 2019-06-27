package org.jfree.chart.urls;

import org.jfree.data.general.PieDataset;

public abstract interface PieURLGenerator
{
  public abstract String generateURL(PieDataset paramPieDataset, Comparable paramComparable, int paramInt);
}
