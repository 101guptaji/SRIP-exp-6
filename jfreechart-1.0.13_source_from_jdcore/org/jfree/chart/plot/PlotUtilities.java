package org.jfree.chart.plot;

import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;






















































public class PlotUtilities
{
  public PlotUtilities() {}
  
  public static boolean isEmptyOrNull(XYPlot plot)
  {
    if (plot != null) {
      int i = 0; for (int n = plot.getDatasetCount(); i < n; i++) {
        XYDataset dataset = plot.getDataset(i);
        if (!DatasetUtilities.isEmptyOrNull(dataset)) {
          return false;
        }
      }
    }
    return true;
  }
}
