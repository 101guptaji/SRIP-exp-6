package org.jfree.data.statistics;

import java.util.List;
import org.jfree.data.category.CategoryDataset;

public abstract interface BoxAndWhiskerCategoryDataset
  extends CategoryDataset
{
  public abstract Number getMeanValue(int paramInt1, int paramInt2);
  
  public abstract Number getMeanValue(Comparable paramComparable1, Comparable paramComparable2);
  
  public abstract Number getMedianValue(int paramInt1, int paramInt2);
  
  public abstract Number getMedianValue(Comparable paramComparable1, Comparable paramComparable2);
  
  public abstract Number getQ1Value(int paramInt1, int paramInt2);
  
  public abstract Number getQ1Value(Comparable paramComparable1, Comparable paramComparable2);
  
  public abstract Number getQ3Value(int paramInt1, int paramInt2);
  
  public abstract Number getQ3Value(Comparable paramComparable1, Comparable paramComparable2);
  
  public abstract Number getMinRegularValue(int paramInt1, int paramInt2);
  
  public abstract Number getMinRegularValue(Comparable paramComparable1, Comparable paramComparable2);
  
  public abstract Number getMaxRegularValue(int paramInt1, int paramInt2);
  
  public abstract Number getMaxRegularValue(Comparable paramComparable1, Comparable paramComparable2);
  
  public abstract Number getMinOutlier(int paramInt1, int paramInt2);
  
  public abstract Number getMinOutlier(Comparable paramComparable1, Comparable paramComparable2);
  
  public abstract Number getMaxOutlier(int paramInt1, int paramInt2);
  
  public abstract Number getMaxOutlier(Comparable paramComparable1, Comparable paramComparable2);
  
  public abstract List getOutliers(int paramInt1, int paramInt2);
  
  public abstract List getOutliers(Comparable paramComparable1, Comparable paramComparable2);
}
