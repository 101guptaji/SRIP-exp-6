package org.jfree.chart.renderer;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.PlotRenderingInfo;



















































public class RendererState
{
  private PlotRenderingInfo info;
  
  public RendererState(PlotRenderingInfo info)
  {
    this.info = info;
  }
  




  public PlotRenderingInfo getInfo()
  {
    return info;
  }
  






  public EntityCollection getEntityCollection()
  {
    EntityCollection result = null;
    if (info != null) {
      ChartRenderingInfo owner = info.getOwner();
      if (owner != null) {
        result = owner.getEntityCollection();
      }
    }
    return result;
  }
}
