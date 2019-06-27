package org.jfree.data.category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.DataUtilities;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.general.AbstractSeriesDataset;






































































public class DefaultIntervalCategoryDataset
  extends AbstractSeriesDataset
  implements IntervalCategoryDataset
{
  private Comparable[] seriesKeys;
  private Comparable[] categoryKeys;
  private Number[][] startData;
  private Number[][] endData;
  
  public DefaultIntervalCategoryDataset(double[][] starts, double[][] ends)
  {
    this(DataUtilities.createNumberArray2D(starts), DataUtilities.createNumberArray2D(ends));
  }
  











  public DefaultIntervalCategoryDataset(Number[][] starts, Number[][] ends)
  {
    this(null, null, starts, ends);
  }
  














  public DefaultIntervalCategoryDataset(String[] seriesNames, Number[][] starts, Number[][] ends)
  {
    this(seriesNames, null, starts, ends);
  }
  
















  public DefaultIntervalCategoryDataset(Comparable[] seriesKeys, Comparable[] categoryKeys, Number[][] starts, Number[][] ends)
  {
    startData = starts;
    endData = ends;
    
    if ((starts != null) && (ends != null))
    {
      String baseName = "org.jfree.data.resources.DataPackageResources";
      ResourceBundle resources = ResourceBundleWrapper.getBundle(baseName);
      

      int seriesCount = starts.length;
      if (seriesCount != ends.length) {
        String errMsg = "DefaultIntervalCategoryDataset: the number of series in the start value dataset does not match the number of series in the end value dataset.";
        


        throw new IllegalArgumentException(errMsg);
      }
      if (seriesCount > 0)
      {

        if (seriesKeys != null)
        {
          if (seriesKeys.length != seriesCount) {
            throw new IllegalArgumentException("The number of series keys does not match the number of series in the data.");
          }
          


          this.seriesKeys = seriesKeys;
        }
        else {
          String prefix = resources.getString("series.default-prefix") + " ";
          
          this.seriesKeys = generateKeys(seriesCount, prefix);
        }
        

        int categoryCount = starts[0].length;
        if (categoryCount != ends[0].length) {
          String errMsg = "DefaultIntervalCategoryDataset: the number of categories in the start value dataset does not match the number of categories in the end value dataset.";
          


          throw new IllegalArgumentException(errMsg);
        }
        if (categoryKeys != null) {
          if (categoryKeys.length != categoryCount) {
            throw new IllegalArgumentException("The number of category keys does not match the number of categories in the data.");
          }
          

          this.categoryKeys = categoryKeys;
        }
        else {
          String prefix = resources.getString("categories.default-prefix") + " ";
          
          this.categoryKeys = generateKeys(categoryCount, prefix);
        }
      }
      else
      {
        this.seriesKeys = new Comparable[0];
        this.categoryKeys = new Comparable[0];
      }
    }
  }
  








  public int getSeriesCount()
  {
    int result = 0;
    if (startData != null) {
      result = startData.length;
    }
    return result;
  }
  









  public int getSeriesIndex(Comparable seriesKey)
  {
    int result = -1;
    for (int i = 0; i < seriesKeys.length; i++) {
      if (seriesKey.equals(seriesKeys[i])) {
        result = i;
        break;
      }
    }
    return result;
  }
  








  public Comparable getSeriesKey(int series)
  {
    if ((series >= getSeriesCount()) || (series < 0)) {
      throw new IllegalArgumentException("No such series : " + series);
    }
    return seriesKeys[series];
  }
  








  public void setSeriesKeys(Comparable[] seriesKeys)
  {
    if (seriesKeys == null) {
      throw new IllegalArgumentException("Null 'seriesKeys' argument.");
    }
    if (seriesKeys.length != getSeriesCount()) {
      throw new IllegalArgumentException("The number of series keys does not match the data.");
    }
    
    this.seriesKeys = seriesKeys;
    fireDatasetChanged();
  }
  






  public int getCategoryCount()
  {
    int result = 0;
    if ((startData != null) && 
      (getSeriesCount() > 0)) {
      result = startData[0].length;
    }
    
    return result;
  }
  









  public List getColumnKeys()
  {
    if (categoryKeys == null) {
      return new ArrayList();
    }
    
    return Collections.unmodifiableList(Arrays.asList(categoryKeys));
  }
  










  public void setCategoryKeys(Comparable[] categoryKeys)
  {
    if (categoryKeys == null) {
      throw new IllegalArgumentException("Null 'categoryKeys' argument.");
    }
    if (categoryKeys.length != getCategoryCount()) {
      throw new IllegalArgumentException("The number of categories does not match the data.");
    }
    
    for (int i = 0; i < categoryKeys.length; i++) {
      if (categoryKeys[i] == null) {
        throw new IllegalArgumentException("DefaultIntervalCategoryDataset.setCategoryKeys(): null category not permitted.");
      }
    }
    

    this.categoryKeys = categoryKeys;
    fireDatasetChanged();
  }
  












  public Number getValue(Comparable series, Comparable category)
  {
    int seriesIndex = getSeriesIndex(series);
    if (seriesIndex < 0) {
      throw new UnknownKeyException("Unknown 'series' key.");
    }
    int itemIndex = getColumnIndex(category);
    if (itemIndex < 0) {
      throw new UnknownKeyException("Unknown 'category' key.");
    }
    return getValue(seriesIndex, itemIndex);
  }
  












  public Number getValue(int series, int category)
  {
    return getEndValue(series, category);
  }
  










  public Number getStartValue(Comparable series, Comparable category)
  {
    int seriesIndex = getSeriesIndex(series);
    if (seriesIndex < 0) {
      throw new UnknownKeyException("Unknown 'series' key.");
    }
    int itemIndex = getColumnIndex(category);
    if (itemIndex < 0) {
      throw new UnknownKeyException("Unknown 'category' key.");
    }
    return getStartValue(seriesIndex, itemIndex);
  }
  












  public Number getStartValue(int series, int category)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("DefaultIntervalCategoryDataset.getValue(): series index out of range.");
    }
    


    if ((category < 0) || (category >= getCategoryCount())) {
      throw new IllegalArgumentException("DefaultIntervalCategoryDataset.getValue(): category index out of range.");
    }
    



    return startData[series][category];
  }
  










  public Number getEndValue(Comparable series, Comparable category)
  {
    int seriesIndex = getSeriesIndex(series);
    if (seriesIndex < 0) {
      throw new UnknownKeyException("Unknown 'series' key.");
    }
    int itemIndex = getColumnIndex(category);
    if (itemIndex < 0) {
      throw new UnknownKeyException("Unknown 'category' key.");
    }
    return getEndValue(seriesIndex, itemIndex);
  }
  









  public Number getEndValue(int series, int category)
  {
    if ((series < 0) || (series >= getSeriesCount())) {
      throw new IllegalArgumentException("DefaultIntervalCategoryDataset.getValue(): series index out of range.");
    }
    


    if ((category < 0) || (category >= getCategoryCount())) {
      throw new IllegalArgumentException("DefaultIntervalCategoryDataset.getValue(): category index out of range.");
    }
    


    return endData[series][category];
  }
  











  public void setStartValue(int series, Comparable category, Number value)
  {
    if ((series < 0) || (series > getSeriesCount() - 1)) {
      throw new IllegalArgumentException("DefaultIntervalCategoryDataset.setValue: series outside valid range.");
    }
    



    int categoryIndex = getCategoryIndex(category);
    if (categoryIndex < 0) {
      throw new IllegalArgumentException("DefaultIntervalCategoryDataset.setValue: unrecognised category.");
    }
    



    startData[series][categoryIndex] = value;
    fireDatasetChanged();
  }
  












  public void setEndValue(int series, Comparable category, Number value)
  {
    if ((series < 0) || (series > getSeriesCount() - 1)) {
      throw new IllegalArgumentException("DefaultIntervalCategoryDataset.setValue: series outside valid range.");
    }
    



    int categoryIndex = getCategoryIndex(category);
    if (categoryIndex < 0) {
      throw new IllegalArgumentException("DefaultIntervalCategoryDataset.setValue: unrecognised category.");
    }
    



    endData[series][categoryIndex] = value;
    fireDatasetChanged();
  }
  









  public int getCategoryIndex(Comparable category)
  {
    int result = -1;
    for (int i = 0; i < categoryKeys.length; i++) {
      if (category.equals(categoryKeys[i])) {
        result = i;
        break;
      }
    }
    return result;
  }
  








  private Comparable[] generateKeys(int count, String prefix)
  {
    Comparable[] result = new Comparable[count];
    
    for (int i = 0; i < count; i++) {
      String name = prefix + (i + 1);
      result[i] = name;
    }
    return result;
  }
  








  public Comparable getColumnKey(int column)
  {
    return categoryKeys[column];
  }
  








  public int getColumnIndex(Comparable columnKey)
  {
    if (columnKey == null) {
      throw new IllegalArgumentException("Null 'columnKey' argument.");
    }
    return getCategoryIndex(columnKey);
  }
  








  public int getRowIndex(Comparable rowKey)
  {
    return getSeriesIndex(rowKey);
  }
  









  public List getRowKeys()
  {
    if (seriesKeys == null) {
      return new ArrayList();
    }
    
    return Collections.unmodifiableList(Arrays.asList(seriesKeys));
  }
  









  public Comparable getRowKey(int row)
  {
    if ((row >= getRowCount()) || (row < 0)) {
      throw new IllegalArgumentException("The 'row' argument is out of bounds.");
    }
    
    return seriesKeys[row];
  }
  








  public int getColumnCount()
  {
    return categoryKeys.length;
  }
  







  public int getRowCount()
  {
    return seriesKeys.length;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DefaultIntervalCategoryDataset)) {
      return false;
    }
    DefaultIntervalCategoryDataset that = (DefaultIntervalCategoryDataset)obj;
    
    if (!Arrays.equals(seriesKeys, seriesKeys)) {
      return false;
    }
    if (!Arrays.equals(categoryKeys, categoryKeys)) {
      return false;
    }
    if (!equal(startData, startData)) {
      return false;
    }
    if (!equal(endData, endData)) {
      return false;
    }
    
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultIntervalCategoryDataset clone = (DefaultIntervalCategoryDataset)super.clone();
    
    categoryKeys = ((Comparable[])categoryKeys.clone());
    seriesKeys = ((Comparable[])seriesKeys.clone());
    startData = clone(startData);
    endData = clone(endData);
    return clone;
  }
  







  private static boolean equal(Number[][] array1, Number[][] array2)
  {
    if (array1 == null) {
      return array2 == null;
    }
    if (array2 == null) {
      return false;
    }
    if (array1.length != array2.length) {
      return false;
    }
    for (int i = 0; i < array1.length; i++) {
      if (!Arrays.equals(array1[i], array2[i])) {
        return false;
      }
    }
    return true;
  }
  






  private static Number[][] clone(Number[][] array)
  {
    if (array == null) {
      throw new IllegalArgumentException("Null 'array' argument.");
    }
    Number[][] result = new Number[array.length][];
    for (int i = 0; i < array.length; i++) {
      Number[] child = array[i];
      Number[] copychild = new Number[child.length];
      System.arraycopy(child, 0, copychild, 0, child.length);
      result[i] = copychild;
    }
    return result;
  }
  



  /**
   * @deprecated
   */
  public List getSeries()
  {
    if (seriesKeys == null) {
      return new ArrayList();
    }
    
    return Collections.unmodifiableList(Arrays.asList(seriesKeys));
  }
  




  /**
   * @deprecated
   */
  public List getCategories()
  {
    return getColumnKeys();
  }
  



  /**
   * @deprecated
   */
  public int getItemCount()
  {
    return categoryKeys.length;
  }
}
