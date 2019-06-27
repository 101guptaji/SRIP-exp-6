package org.jfree.chart.labels;

import org.jfree.data.category.CategoryDataset;

public abstract interface CategorySeriesLabelGenerator
{
  public abstract String generateLabel(CategoryDataset paramCategoryDataset, int paramInt);
}
