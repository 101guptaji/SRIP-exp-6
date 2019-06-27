package org.jfree.chart.renderer.category;

import org.jfree.chart.plot.CategoryCrosshairState;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.renderer.RendererState;


































































public class CategoryItemRendererState
  extends RendererState
{
  private double barWidth;
  private double seriesRunningTotal;
  private int[] visibleSeries;
  private CategoryCrosshairState crosshairState;
  
  public CategoryItemRendererState(PlotRenderingInfo info)
  {
    super(info);
    barWidth = 0.0D;
    seriesRunningTotal = 0.0D;
  }
  






  public double getBarWidth()
  {
    return barWidth;
  }
  







  public void setBarWidth(double width)
  {
    barWidth = width;
  }
  






  public double getSeriesRunningTotal()
  {
    return seriesRunningTotal;
  }
  







  void setSeriesRunningTotal(double total)
  {
    seriesRunningTotal = total;
  }
  








  public CategoryCrosshairState getCrosshairState()
  {
    return crosshairState;
  }
  








  public void setCrosshairState(CategoryCrosshairState state)
  {
    crosshairState = state;
  }
  











  public int getVisibleSeriesIndex(int rowIndex)
  {
    if (visibleSeries == null) {
      return rowIndex;
    }
    int index = -1;
    for (int vRow = 0; vRow < visibleSeries.length; vRow++) {
      if (visibleSeries[vRow] == rowIndex) {
        index = vRow;
        break;
      }
    }
    return index;
  }
  







  public int getVisibleSeriesCount()
  {
    if (visibleSeries == null) {
      return -1;
    }
    return visibleSeries.length;
  }
  






  public int[] getVisibleSeriesArray()
  {
    if (visibleSeries == null) {
      return null;
    }
    int[] result = new int[visibleSeries.length];
    System.arraycopy(visibleSeries, 0, result, 0, visibleSeries.length);
    
    return result;
  }
  






  public void setVisibleSeriesArray(int[] visibleSeries)
  {
    this.visibleSeries = visibleSeries;
  }
}
