package org.jfree.chart;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.io.SerialUtilities;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;













































































public class ChartRenderingInfo
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 2751952018173406822L;
  private transient Rectangle2D chartArea;
  private PlotRenderingInfo plotInfo;
  private EntityCollection entities;
  
  public ChartRenderingInfo()
  {
    this(new StandardEntityCollection());
  }
  







  public ChartRenderingInfo(EntityCollection entities)
  {
    chartArea = new Rectangle2D.Double();
    plotInfo = new PlotRenderingInfo(this);
    this.entities = entities;
  }
  






  public Rectangle2D getChartArea()
  {
    return chartArea;
  }
  






  public void setChartArea(Rectangle2D area)
  {
    chartArea.setRect(area);
  }
  






  public EntityCollection getEntityCollection()
  {
    return entities;
  }
  






  public void setEntityCollection(EntityCollection entities)
  {
    this.entities = entities;
  }
  


  public void clear()
  {
    chartArea.setRect(0.0D, 0.0D, 0.0D, 0.0D);
    plotInfo = new PlotRenderingInfo(this);
    if (entities != null) {
      entities.clear();
    }
  }
  




  public PlotRenderingInfo getPlotInfo()
  {
    return plotInfo;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ChartRenderingInfo)) {
      return false;
    }
    ChartRenderingInfo that = (ChartRenderingInfo)obj;
    if (!ObjectUtilities.equal(chartArea, chartArea)) {
      return false;
    }
    if (!ObjectUtilities.equal(plotInfo, plotInfo)) {
      return false;
    }
    if (!ObjectUtilities.equal(entities, entities)) {
      return false;
    }
    return true;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    ChartRenderingInfo clone = (ChartRenderingInfo)super.clone();
    if (chartArea != null) {
      chartArea = ((Rectangle2D)chartArea.clone());
    }
    if ((entities instanceof PublicCloneable)) {
      PublicCloneable pc = (PublicCloneable)entities;
      entities = ((EntityCollection)pc.clone());
    }
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeShape(chartArea, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    chartArea = ((Rectangle2D)SerialUtilities.readShape(stream));
  }
}
