package org.jfree.data.statistics;

import java.util.List;
import org.jfree.data.category.CategoryDataset;

public abstract interface MultiValueCategoryDataset
  extends CategoryDataset
{
  public abstract List getValues(int paramInt1, int paramInt2);
  
  public abstract List getValues(Comparable paramComparable1, Comparable paramComparable2);
}
