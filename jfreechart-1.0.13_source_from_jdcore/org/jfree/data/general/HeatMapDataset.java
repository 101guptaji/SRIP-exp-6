package org.jfree.data.general;

public abstract interface HeatMapDataset
{
  public abstract int getXSampleCount();
  
  public abstract int getYSampleCount();
  
  public abstract double getMinimumXValue();
  
  public abstract double getMaximumXValue();
  
  public abstract double getMinimumYValue();
  
  public abstract double getMaximumYValue();
  
  public abstract double getXValue(int paramInt);
  
  public abstract double getYValue(int paramInt);
  
  public abstract double getZValue(int paramInt1, int paramInt2);
  
  public abstract Number getZ(int paramInt1, int paramInt2);
}
