package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.Rectangle2D.Float;
import java.io.Serializable;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.HashUtilities;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockParams;
import org.jfree.chart.block.EntityBlockResult;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.Title;
import org.jfree.chart.util.XYCoordinateType;
import org.jfree.data.Range;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.Size2D;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;







































































public class XYTitleAnnotation
  extends AbstractXYAnnotation
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -4364694501921559958L;
  private XYCoordinateType coordinateType;
  private double x;
  private double y;
  private double maxWidth;
  private double maxHeight;
  private Title title;
  private RectangleAnchor anchor;
  
  public XYTitleAnnotation(double x, double y, Title title)
  {
    this(x, y, title, RectangleAnchor.CENTER);
  }
  









  public XYTitleAnnotation(double x, double y, Title title, RectangleAnchor anchor)
  {
    if (title == null) {
      throw new IllegalArgumentException("Null 'title' argument.");
    }
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    coordinateType = XYCoordinateType.RELATIVE;
    this.x = x;
    this.y = y;
    maxWidth = 0.0D;
    maxHeight = 0.0D;
    this.title = title;
    this.anchor = anchor;
  }
  




  public XYCoordinateType getCoordinateType()
  {
    return coordinateType;
  }
  




  public double getX()
  {
    return x;
  }
  




  public double getY()
  {
    return y;
  }
  




  public Title getTitle()
  {
    return title;
  }
  




  public RectangleAnchor getTitleAnchor()
  {
    return anchor;
  }
  




  public double getMaxWidth()
  {
    return maxWidth;
  }
  




  public void setMaxWidth(double max)
  {
    maxWidth = max;
  }
  




  public double getMaxHeight()
  {
    return maxHeight;
  }
  




  public void setMaxHeight(double max)
  {
    maxHeight = max;
  }
  

















  public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info)
  {
    PlotOrientation orientation = plot.getOrientation();
    AxisLocation domainAxisLocation = plot.getDomainAxisLocation();
    AxisLocation rangeAxisLocation = plot.getRangeAxisLocation();
    RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(domainAxisLocation, orientation);
    
    RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(rangeAxisLocation, orientation);
    
    Range xRange = domainAxis.getRange();
    Range yRange = rangeAxis.getRange();
    double anchorX = 0.0D;
    double anchorY = 0.0D;
    if (coordinateType == XYCoordinateType.RELATIVE) {
      anchorX = xRange.getLowerBound() + x * xRange.getLength();
      anchorY = yRange.getLowerBound() + y * yRange.getLength();
    }
    else {
      anchorX = domainAxis.valueToJava2D(x, dataArea, domainEdge);
      anchorY = rangeAxis.valueToJava2D(y, dataArea, rangeEdge);
    }
    
    float j2DX = (float)domainAxis.valueToJava2D(anchorX, dataArea, domainEdge);
    
    float j2DY = (float)rangeAxis.valueToJava2D(anchorY, dataArea, rangeEdge);
    
    float xx = 0.0F;
    float yy = 0.0F;
    if (orientation == PlotOrientation.HORIZONTAL) {
      xx = j2DY;
      yy = j2DX;
    }
    else if (orientation == PlotOrientation.VERTICAL) {
      xx = j2DX;
      yy = j2DY;
    }
    
    double maxW = dataArea.getWidth();
    double maxH = dataArea.getHeight();
    if (coordinateType == XYCoordinateType.RELATIVE) {
      if (maxWidth > 0.0D) {
        maxW *= maxWidth;
      }
      if (maxHeight > 0.0D) {
        maxH *= maxHeight;
      }
    }
    if (coordinateType == XYCoordinateType.DATA) {
      maxW = maxWidth;
      maxH = maxHeight;
    }
    RectangleConstraint rc = new RectangleConstraint(new Range(0.0D, maxW), new Range(0.0D, maxH));
    

    Size2D size = title.arrange(g2, rc);
    Rectangle2D titleRect = new Rectangle2D.Double(0.0D, 0.0D, width, height);
    
    Point2D anchorPoint = RectangleAnchor.coordinates(titleRect, anchor);
    
    xx -= (float)anchorPoint.getX();
    yy -= (float)anchorPoint.getY();
    titleRect.setRect(xx, yy, titleRect.getWidth(), titleRect.getHeight());
    BlockParams p = new BlockParams();
    if ((info != null) && 
      (info.getOwner().getEntityCollection() != null)) {
      p.setGenerateEntities(true);
    }
    
    Object result = title.draw(g2, titleRect, p);
    if (info != null) {
      if ((result instanceof EntityBlockResult)) {
        EntityBlockResult ebr = (EntityBlockResult)result;
        info.getOwner().getEntityCollection().addAll(ebr.getEntityCollection());
      }
      
      String toolTip = getToolTipText();
      String url = getURL();
      if ((toolTip != null) || (url != null)) {
        addEntity(info, new Rectangle2D.Float(xx, yy, (float)width, (float)height), rendererIndex, toolTip, url);
      }
    }
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYTitleAnnotation)) {
      return false;
    }
    XYTitleAnnotation that = (XYTitleAnnotation)obj;
    if (coordinateType != coordinateType) {
      return false;
    }
    if (x != x) {
      return false;
    }
    if (y != y) {
      return false;
    }
    if (maxWidth != maxWidth) {
      return false;
    }
    if (maxHeight != maxHeight) {
      return false;
    }
    if (!ObjectUtilities.equal(title, title)) {
      return false;
    }
    if (!anchor.equals(anchor)) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = 193;
    result = HashUtilities.hashCode(result, anchor);
    result = HashUtilities.hashCode(result, coordinateType);
    result = HashUtilities.hashCode(result, x);
    result = HashUtilities.hashCode(result, y);
    result = HashUtilities.hashCode(result, maxWidth);
    result = HashUtilities.hashCode(result, maxHeight);
    result = HashUtilities.hashCode(result, title);
    return result;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
