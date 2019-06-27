package org.jfree.chart.urls;

import org.jfree.data.category.CategoryDataset;

public abstract interface CategoryURLGenerator
{
  public abstract String generateURL(CategoryDataset paramCategoryDataset, int paramInt1, int paramInt2);
}
