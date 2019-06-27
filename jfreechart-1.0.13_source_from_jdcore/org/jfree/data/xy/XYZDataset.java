package org.jfree.data.xy;

public abstract interface XYZDataset
  extends XYDataset
{
  public abstract Number getZ(int paramInt1, int paramInt2);
  
  public abstract double getZValue(int paramInt1, int paramInt2);
}
