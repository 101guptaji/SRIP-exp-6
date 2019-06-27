package org.jfree.data.gantt;

import org.jfree.data.category.IntervalCategoryDataset;

public abstract interface GanttCategoryDataset
  extends IntervalCategoryDataset
{
  public abstract Number getPercentComplete(int paramInt1, int paramInt2);
  
  public abstract Number getPercentComplete(Comparable paramComparable1, Comparable paramComparable2);
  
  public abstract int getSubIntervalCount(int paramInt1, int paramInt2);
  
  public abstract int getSubIntervalCount(Comparable paramComparable1, Comparable paramComparable2);
  
  public abstract Number getStartValue(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Number getStartValue(Comparable paramComparable1, Comparable paramComparable2, int paramInt);
  
  public abstract Number getEndValue(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Number getEndValue(Comparable paramComparable1, Comparable paramComparable2, int paramInt);
  
  public abstract Number getPercentComplete(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract Number getPercentComplete(Comparable paramComparable1, Comparable paramComparable2, int paramInt);
}
