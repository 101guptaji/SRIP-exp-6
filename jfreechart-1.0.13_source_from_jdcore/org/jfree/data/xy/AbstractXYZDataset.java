package org.jfree.data.xy;


























public abstract class AbstractXYZDataset
  extends AbstractXYDataset
  implements XYZDataset
{
  public AbstractXYZDataset() {}
  
























  public double getZValue(int series, int item)
  {
    double result = NaN.0D;
    Number z = getZ(series, item);
    if (z != null) {
      result = z.doubleValue();
    }
    return result;
  }
}
