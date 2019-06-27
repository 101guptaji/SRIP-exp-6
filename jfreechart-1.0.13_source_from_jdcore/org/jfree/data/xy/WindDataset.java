package org.jfree.data.xy;

public abstract interface WindDataset
  extends XYDataset
{
  public abstract Number getWindDirection(int paramInt1, int paramInt2);
  
  public abstract Number getWindForce(int paramInt1, int paramInt2);
}
