package org.jfree.data.xy;

public abstract interface OHLCDataset
  extends XYDataset
{
  public abstract Number getHigh(int paramInt1, int paramInt2);
  
  public abstract double getHighValue(int paramInt1, int paramInt2);
  
  public abstract Number getLow(int paramInt1, int paramInt2);
  
  public abstract double getLowValue(int paramInt1, int paramInt2);
  
  public abstract Number getOpen(int paramInt1, int paramInt2);
  
  public abstract double getOpenValue(int paramInt1, int paramInt2);
  
  public abstract Number getClose(int paramInt1, int paramInt2);
  
  public abstract double getCloseValue(int paramInt1, int paramInt2);
  
  public abstract Number getVolume(int paramInt1, int paramInt2);
  
  public abstract double getVolumeValue(int paramInt1, int paramInt2);
}
