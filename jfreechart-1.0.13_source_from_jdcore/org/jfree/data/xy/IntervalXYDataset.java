package org.jfree.data.xy;

public abstract interface IntervalXYDataset
  extends XYDataset
{
  public abstract Number getStartX(int paramInt1, int paramInt2);
  
  public abstract double getStartXValue(int paramInt1, int paramInt2);
  
  public abstract Number getEndX(int paramInt1, int paramInt2);
  
  public abstract double getEndXValue(int paramInt1, int paramInt2);
  
  public abstract Number getStartY(int paramInt1, int paramInt2);
  
  public abstract double getStartYValue(int paramInt1, int paramInt2);
  
  public abstract Number getEndY(int paramInt1, int paramInt2);
  
  public abstract double getEndYValue(int paramInt1, int paramInt2);
}
