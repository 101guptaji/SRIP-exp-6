package org.jfree.data;

public abstract interface Values2D
{
  public abstract int getRowCount();
  
  public abstract int getColumnCount();
  
  public abstract Number getValue(int paramInt1, int paramInt2);
}
