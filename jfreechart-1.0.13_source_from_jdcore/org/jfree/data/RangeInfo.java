package org.jfree.data;

public abstract interface RangeInfo
{
  public abstract double getRangeLowerBound(boolean paramBoolean);
  
  public abstract double getRangeUpperBound(boolean paramBoolean);
  
  public abstract Range getRangeBounds(boolean paramBoolean);
}
