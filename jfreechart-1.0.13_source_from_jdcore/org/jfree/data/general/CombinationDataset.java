package org.jfree.data.general;

/**
 * @deprecated
 */
public abstract interface CombinationDataset
{
  public abstract SeriesDataset getParent();
  
  public abstract int[] getMap();
}
