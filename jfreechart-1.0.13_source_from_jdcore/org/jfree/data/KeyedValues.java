package org.jfree.data;

import java.util.List;

public abstract interface KeyedValues
  extends Values
{
  public abstract Comparable getKey(int paramInt);
  
  public abstract int getIndex(Comparable paramComparable);
  
  public abstract List getKeys();
  
  public abstract Number getValue(Comparable paramComparable);
}
