package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.XYAnnotationEntity;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.util.ObjectUtilities;



















































public abstract class AbstractXYAnnotation
  implements XYAnnotation
{
  private String toolTipText;
  private String url;
  
  protected AbstractXYAnnotation()
  {
    toolTipText = null;
    url = null;
  }
  








  public String getToolTipText()
  {
    return toolTipText;
  }
  






  public void setToolTipText(String text)
  {
    toolTipText = text;
  }
  







  public String getURL()
  {
    return url;
  }
  






  public void setURL(String url)
  {
    this.url = url;
  }
  













  public abstract void draw(Graphics2D paramGraphics2D, XYPlot paramXYPlot, Rectangle2D paramRectangle2D, ValueAxis paramValueAxis1, ValueAxis paramValueAxis2, int paramInt, PlotRenderingInfo paramPlotRenderingInfo);
  













  protected void addEntity(PlotRenderingInfo info, Shape hotspot, int rendererIndex, String toolTipText, String urlText)
  {
    if (info == null) {
      return;
    }
    EntityCollection entities = info.getOwner().getEntityCollection();
    if (entities == null) {
      return;
    }
    XYAnnotationEntity entity = new XYAnnotationEntity(hotspot, rendererIndex, toolTipText, urlText);
    
    entities.add(entity);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AbstractXYAnnotation)) {
      return false;
    }
    AbstractXYAnnotation that = (AbstractXYAnnotation)obj;
    if (!ObjectUtilities.equal(toolTipText, toolTipText)) {
      return false;
    }
    if (!ObjectUtilities.equal(url, url)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 193;
    if (toolTipText != null) {
      result = 37 * result + toolTipText.hashCode();
    }
    if (url != null) {
      result = 37 * result + url.hashCode();
    }
    return result;
  }
}
