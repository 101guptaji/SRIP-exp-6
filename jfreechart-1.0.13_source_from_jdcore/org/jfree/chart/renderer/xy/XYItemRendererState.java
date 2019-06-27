package org.jfree.chart.renderer.xy;

import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.renderer.RendererState;
import org.jfree.data.xy.XYDataset;















































































public class XYItemRendererState
  extends RendererState
{
  private int firstItemIndex;
  private int lastItemIndex;
  public Line2D workingLine;
  private boolean processVisibleItemsOnly;
  
  public XYItemRendererState(PlotRenderingInfo info)
  {
    super(info);
    workingLine = new Line2D.Double();
    processVisibleItemsOnly = true;
  }
  










  public boolean getProcessVisibleItemsOnly()
  {
    return processVisibleItemsOnly;
  }
  







  public void setProcessVisibleItemsOnly(boolean flag)
  {
    processVisibleItemsOnly = flag;
  }
  







  public int getFirstItemIndex()
  {
    return firstItemIndex;
  }
  







  public int getLastItemIndex()
  {
    return lastItemIndex;
  }
  

















  public void startSeriesPass(XYDataset dataset, int series, int firstItem, int lastItem, int pass, int passCount)
  {
    firstItemIndex = firstItem;
    lastItemIndex = lastItem;
  }
  
  public void endSeriesPass(XYDataset dataset, int series, int firstItem, int lastItem, int pass, int passCount) {}
}
