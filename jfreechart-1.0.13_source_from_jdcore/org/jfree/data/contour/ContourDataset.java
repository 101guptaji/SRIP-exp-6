package org.jfree.data.contour;

import org.jfree.data.Range;
import org.jfree.data.xy.XYZDataset;

/**
 * @deprecated
 */
public abstract interface ContourDataset
  extends XYZDataset
{
  public abstract double getMinZValue();
  
  public abstract double getMaxZValue();
  
  public abstract Number[] getXValues();
  
  public abstract Number[] getYValues();
  
  public abstract Number[] getZValues();
  
  public abstract int[] indexX();
  
  public abstract int[] getXIndices();
  
  public abstract Range getZValueRange(Range paramRange1, Range paramRange2);
  
  public abstract boolean isDateAxis(int paramInt);
}
