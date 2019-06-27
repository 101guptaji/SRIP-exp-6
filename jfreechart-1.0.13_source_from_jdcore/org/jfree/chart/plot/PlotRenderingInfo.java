package org.jfree.chart.plot;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.io.SerialUtilities;
import org.jfree.util.ObjectUtilities;































































public class PlotRenderingInfo
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 8446720134379617220L;
  private ChartRenderingInfo owner;
  private transient Rectangle2D plotArea;
  private transient Rectangle2D dataArea;
  private List subplotInfo;
  
  public PlotRenderingInfo(ChartRenderingInfo owner)
  {
    this.owner = owner;
    dataArea = new Rectangle2D.Double();
    subplotInfo = new ArrayList();
  }
  




  public ChartRenderingInfo getOwner()
  {
    return owner;
  }
  






  public Rectangle2D getPlotArea()
  {
    return plotArea;
  }
  







  public void setPlotArea(Rectangle2D area)
  {
    plotArea = area;
  }
  






  public Rectangle2D getDataArea()
  {
    return dataArea;
  }
  







  public void setDataArea(Rectangle2D area)
  {
    dataArea = area;
  }
  




  public int getSubplotCount()
  {
    return subplotInfo.size();
  }
  






  public void addSubplotInfo(PlotRenderingInfo info)
  {
    subplotInfo.add(info);
  }
  








  public PlotRenderingInfo getSubplotInfo(int index)
  {
    return (PlotRenderingInfo)subplotInfo.get(index);
  }
  












  public int getSubplotIndex(Point2D source)
  {
    if (source == null) {
      throw new IllegalArgumentException("Null 'source' argument.");
    }
    int subplotCount = getSubplotCount();
    for (int i = 0; i < subplotCount; i++) {
      PlotRenderingInfo info = getSubplotInfo(i);
      Rectangle2D area = info.getDataArea();
      if (area.contains(source)) {
        return i;
      }
    }
    return -1;
  }
  






  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof PlotRenderingInfo)) {
      return false;
    }
    PlotRenderingInfo that = (PlotRenderingInfo)obj;
    if (!ObjectUtilities.equal(dataArea, dataArea)) {
      return false;
    }
    if (!ObjectUtilities.equal(plotArea, plotArea)) {
      return false;
    }
    if (!ObjectUtilities.equal(subplotInfo, subplotInfo)) {
      return false;
    }
    return true;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    PlotRenderingInfo clone = (PlotRenderingInfo)super.clone();
    if (plotArea != null) {
      plotArea = ((Rectangle2D)plotArea.clone());
    }
    if (dataArea != null) {
      dataArea = ((Rectangle2D)dataArea.clone());
    }
    subplotInfo = new ArrayList(subplotInfo.size());
    for (int i = 0; i < subplotInfo.size(); i++) {
      PlotRenderingInfo info = (PlotRenderingInfo)subplotInfo.get(i);
      
      subplotInfo.add(info.clone());
    }
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeShape(dataArea, stream);
    SerialUtilities.writeShape(plotArea, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    dataArea = ((Rectangle2D)SerialUtilities.readShape(stream));
    plotArea = ((Rectangle2D)SerialUtilities.readShape(stream));
  }
}
