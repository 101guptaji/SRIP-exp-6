package org.jfree.data.xy;

import org.jfree.data.DomainOrder;
import org.jfree.data.general.AbstractSeriesDataset;

















































public abstract class AbstractXYDataset
  extends AbstractSeriesDataset
  implements XYDataset
{
  public AbstractXYDataset() {}
  
  public DomainOrder getDomainOrder()
  {
    return DomainOrder.NONE;
  }
  







  public double getXValue(int series, int item)
  {
    double result = NaN.0D;
    Number x = getX(series, item);
    if (x != null) {
      result = x.doubleValue();
    }
    return result;
  }
  







  public double getYValue(int series, int item)
  {
    double result = NaN.0D;
    Number y = getY(series, item);
    if (y != null) {
      result = y.doubleValue();
    }
    return result;
  }
}
