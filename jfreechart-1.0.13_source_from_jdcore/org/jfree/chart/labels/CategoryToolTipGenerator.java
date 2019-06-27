package org.jfree.chart.labels;

import org.jfree.data.category.CategoryDataset;

public abstract interface CategoryToolTipGenerator
{
  public abstract String generateToolTip(CategoryDataset paramCategoryDataset, int paramInt1, int paramInt2);
}
