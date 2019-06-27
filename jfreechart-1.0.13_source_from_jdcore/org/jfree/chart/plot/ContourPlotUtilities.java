package org.jfree.chart.plot;

import org.jfree.data.Range;
import org.jfree.data.contour.ContourDataset;
import org.jfree.data.contour.DefaultContourDataset;
























































/**
 * @deprecated
 */
public abstract class ContourPlotUtilities
{
  public ContourPlotUtilities() {}
  
  public static Range visibleRange(ContourDataset data, Range x, Range y)
  {
    Range range = null;
    range = ((DefaultContourDataset)data).getZValueRange(x, y);
    return range;
  }
}
