package org.jfree.data.general;

public abstract interface SeriesDataset
  extends Dataset
{
  public abstract int getSeriesCount();
  
  public abstract Comparable getSeriesKey(int paramInt);
  
  public abstract int indexOf(Comparable paramComparable);
}
