package org.jfree.chart.entity;

import java.awt.Shape;
import java.io.Serializable;
import org.jfree.data.category.CategoryDataset;
import org.jfree.util.ObjectUtilities;
































































































public class CategoryItemEntity
  extends ChartEntity
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -8657249457902337349L;
  private CategoryDataset dataset;
  /**
   * @deprecated
   */
  private int series;
  /**
   * @deprecated
   */
  private Object category;
  /**
   * @deprecated
   */
  private int categoryIndex;
  private Comparable rowKey;
  private Comparable columnKey;
  
  /**
   * @deprecated
   */
  public CategoryItemEntity(Shape area, String toolTipText, String urlText, CategoryDataset dataset, int series, Object category, int categoryIndex)
  {
    super(area, toolTipText, urlText);
    if (dataset == null) {
      throw new IllegalArgumentException("Null 'dataset' argument.");
    }
    this.dataset = dataset;
    this.series = series;
    this.category = category;
    this.categoryIndex = categoryIndex;
    rowKey = dataset.getRowKey(series);
    columnKey = dataset.getColumnKey(categoryIndex);
  }
  












  public CategoryItemEntity(Shape area, String toolTipText, String urlText, CategoryDataset dataset, Comparable rowKey, Comparable columnKey)
  {
    super(area, toolTipText, urlText);
    if (dataset == null) {
      throw new IllegalArgumentException("Null 'dataset' argument.");
    }
    this.dataset = dataset;
    this.rowKey = rowKey;
    this.columnKey = columnKey;
    

    series = dataset.getRowIndex(rowKey);
    category = columnKey;
    categoryIndex = dataset.getColumnIndex(columnKey);
  }
  








  public CategoryDataset getDataset()
  {
    return dataset;
  }
  






  public void setDataset(CategoryDataset dataset)
  {
    if (dataset == null) {
      throw new IllegalArgumentException("Null 'dataset' argument.");
    }
    this.dataset = dataset;
  }
  








  public Comparable getRowKey()
  {
    return rowKey;
  }
  








  public void setRowKey(Comparable rowKey)
  {
    this.rowKey = rowKey;
    
    series = dataset.getRowIndex(rowKey);
  }
  








  public Comparable getColumnKey()
  {
    return columnKey;
  }
  








  public void setColumnKey(Comparable columnKey)
  {
    this.columnKey = columnKey;
    
    category = columnKey;
    categoryIndex = dataset.getColumnIndex(columnKey);
  }
  






  /**
   * @deprecated
   */
  public int getSeries()
  {
    return series;
  }
  






  /**
   * @deprecated
   */
  public void setSeries(int series)
  {
    this.series = series;
  }
  







  /**
   * @deprecated
   */
  public Object getCategory()
  {
    return category;
  }
  





  /**
   * @deprecated
   */
  public void setCategory(Object category)
  {
    this.category = category;
  }
  






  /**
   * @deprecated
   */
  public int getCategoryIndex()
  {
    return categoryIndex;
  }
  






  /**
   * @deprecated
   */
  public void setCategoryIndex(int index)
  {
    categoryIndex = index;
  }
  





  public String toString()
  {
    return "CategoryItemEntity: rowKey=" + rowKey + ", columnKey=" + columnKey + ", dataset=" + dataset;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof CategoryItemEntity)) {
      return false;
    }
    CategoryItemEntity that = (CategoryItemEntity)obj;
    if (!rowKey.equals(rowKey)) {
      return false;
    }
    if (!columnKey.equals(columnKey)) {
      return false;
    }
    if (!ObjectUtilities.equal(dataset, dataset)) {
      return false;
    }
    

    if (categoryIndex != categoryIndex) {
      return false;
    }
    if (series != series) {
      return false;
    }
    if (!ObjectUtilities.equal(category, category)) {
      return false;
    }
    return super.equals(obj);
  }
}
