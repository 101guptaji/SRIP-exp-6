package org.jfree.data.general;

import java.util.Set;
import java.util.TreeSet;
import org.jfree.data.DefaultKeyedValues2D;





































































public class WaferMapDataset
  extends AbstractDataset
{
  private DefaultKeyedValues2D data;
  private int maxChipX;
  private int maxChipY;
  private double chipSpace;
  private Double maxValue;
  private Double minValue;
  private static final double DEFAULT_CHIP_SPACE = 1.0D;
  
  public WaferMapDataset(int maxChipX, int maxChipY)
  {
    this(maxChipX, maxChipY, null);
  }
  







  public WaferMapDataset(int maxChipX, int maxChipY, Number chipSpace)
  {
    maxValue = new Double(Double.NEGATIVE_INFINITY);
    minValue = new Double(Double.POSITIVE_INFINITY);
    data = new DefaultKeyedValues2D();
    
    this.maxChipX = maxChipX;
    this.maxChipY = maxChipY;
    if (chipSpace == null) {
      this.chipSpace = 1.0D;
    }
    else {
      this.chipSpace = chipSpace.doubleValue();
    }
  }
  







  public void addValue(Number value, Comparable chipx, Comparable chipy)
  {
    setValue(value, chipx, chipy);
  }
  






  public void addValue(int v, int x, int y)
  {
    setValue(new Double(v), new Integer(x), new Integer(y));
  }
  






  public void setValue(Number value, Comparable chipx, Comparable chipy)
  {
    data.setValue(value, chipx, chipy);
    if (isMaxValue(value)) {
      maxValue = ((Double)value);
    }
    if (isMinValue(value)) {
      minValue = ((Double)value);
    }
  }
  




  public int getUniqueValueCount()
  {
    return getUniqueValues().size();
  }
  




  public Set getUniqueValues()
  {
    Set unique = new TreeSet();
    
    for (int r = 0; r < data.getRowCount(); r++) {
      for (int c = 0; c < data.getColumnCount(); c++) {
        Number value = data.getValue(r, c);
        if (value != null) {
          unique.add(value);
        }
      }
    }
    return unique;
  }
  







  public Number getChipValue(int chipx, int chipy)
  {
    return getChipValue(new Integer(chipx), new Integer(chipy));
  }
  







  public Number getChipValue(Comparable chipx, Comparable chipy)
  {
    int rowIndex = data.getRowIndex(chipx);
    if (rowIndex < 0) {
      return null;
    }
    int colIndex = data.getColumnIndex(chipy);
    if (colIndex < 0) {
      return null;
    }
    return data.getValue(rowIndex, colIndex);
  }
  






  public boolean isMaxValue(Number check)
  {
    if (check.doubleValue() > maxValue.doubleValue()) {
      return true;
    }
    return false;
  }
  






  public boolean isMinValue(Number check)
  {
    if (check.doubleValue() < minValue.doubleValue()) {
      return true;
    }
    return false;
  }
  




  public Number getMaxValue()
  {
    return maxValue;
  }
  




  public Number getMinValue()
  {
    return minValue;
  }
  




  public int getMaxChipX()
  {
    return maxChipX;
  }
  




  public void setMaxChipX(int maxChipX)
  {
    this.maxChipX = maxChipX;
  }
  




  public int getMaxChipY()
  {
    return maxChipY;
  }
  




  public void setMaxChipY(int maxChipY)
  {
    this.maxChipY = maxChipY;
  }
  




  public double getChipSpace()
  {
    return chipSpace;
  }
  




  public void setChipSpace(double space)
  {
    chipSpace = space;
  }
}
