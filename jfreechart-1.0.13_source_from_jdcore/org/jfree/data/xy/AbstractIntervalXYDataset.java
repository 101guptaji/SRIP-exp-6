package org.jfree.data.xy;




























public abstract class AbstractIntervalXYDataset
  extends AbstractXYDataset
  implements IntervalXYDataset
{
  public AbstractIntervalXYDataset() {}
  


























  public double getStartXValue(int series, int item)
  {
    double result = NaN.0D;
    Number x = getStartX(series, item);
    if (x != null) {
      result = x.doubleValue();
    }
    return result;
  }
  








  public double getEndXValue(int series, int item)
  {
    double result = NaN.0D;
    Number x = getEndX(series, item);
    if (x != null) {
      result = x.doubleValue();
    }
    return result;
  }
  








  public double getStartYValue(int series, int item)
  {
    double result = NaN.0D;
    Number y = getStartY(series, item);
    if (y != null) {
      result = y.doubleValue();
    }
    return result;
  }
  








  public double getEndYValue(int series, int item)
  {
    double result = NaN.0D;
    Number y = getEndY(series, item);
    if (y != null) {
      result = y.doubleValue();
    }
    return result;
  }
}
