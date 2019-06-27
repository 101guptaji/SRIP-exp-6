package org.jfree.data.xy;

import java.util.List;
import org.jfree.data.ComparableObjectItem;
import org.jfree.data.ComparableObjectSeries;
























































public class VectorSeries
  extends ComparableObjectSeries
{
  public VectorSeries(Comparable key)
  {
    this(key, false, true);
  }
  










  public VectorSeries(Comparable key, boolean autoSort, boolean allowDuplicateXValues)
  {
    super(key, autoSort, allowDuplicateXValues);
  }
  







  public void add(double x, double y, double deltaX, double deltaY)
  {
    super.add(new VectorDataItem(x, y, deltaX, deltaY), true);
  }
  







  public ComparableObjectItem remove(int index)
  {
    VectorDataItem result = (VectorDataItem)data.remove(index);
    fireSeriesChanged();
    return result;
  }
  






  public double getXValue(int index)
  {
    VectorDataItem item = (VectorDataItem)getDataItem(index);
    return item.getXValue();
  }
  






  public double getYValue(int index)
  {
    VectorDataItem item = (VectorDataItem)getDataItem(index);
    return item.getYValue();
  }
  






  public double getVectorXValue(int index)
  {
    VectorDataItem item = (VectorDataItem)getDataItem(index);
    return item.getVectorX();
  }
  






  public double getVectorYValue(int index)
  {
    VectorDataItem item = (VectorDataItem)getDataItem(index);
    return item.getVectorY();
  }
  







  public ComparableObjectItem getDataItem(int index)
  {
    return super.getDataItem(index);
  }
}
