package org.jfree.chart.renderer.xy;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleAnchor;
import org.jfree.util.PublicCloneable;





























































public class XYBlockRenderer
  extends AbstractXYItemRenderer
  implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
{
  private double blockWidth = 1.0D;
  



  private double blockHeight = 1.0D;
  




  private RectangleAnchor blockAnchor = RectangleAnchor.CENTER;
  


  private double xOffset;
  

  private double yOffset;
  

  private PaintScale paintScale;
  


  public XYBlockRenderer()
  {
    updateOffsets();
    paintScale = new LookupPaintScale();
  }
  






  public double getBlockWidth()
  {
    return blockWidth;
  }
  







  public void setBlockWidth(double width)
  {
    if (width <= 0.0D) {
      throw new IllegalArgumentException("The 'width' argument must be > 0.0");
    }
    
    blockWidth = width;
    updateOffsets();
    fireChangeEvent();
  }
  






  public double getBlockHeight()
  {
    return blockHeight;
  }
  







  public void setBlockHeight(double height)
  {
    if (height <= 0.0D) {
      throw new IllegalArgumentException("The 'height' argument must be > 0.0");
    }
    
    blockHeight = height;
    updateOffsets();
    fireChangeEvent();
  }
  







  public RectangleAnchor getBlockAnchor()
  {
    return blockAnchor;
  }
  







  public void setBlockAnchor(RectangleAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    if (blockAnchor.equals(anchor)) {
      return;
    }
    blockAnchor = anchor;
    updateOffsets();
    fireChangeEvent();
  }
  







  public PaintScale getPaintScale()
  {
    return paintScale;
  }
  








  public void setPaintScale(PaintScale scale)
  {
    if (scale == null) {
      throw new IllegalArgumentException("Null 'scale' argument.");
    }
    paintScale = scale;
    fireChangeEvent();
  }
  



  private void updateOffsets()
  {
    if (blockAnchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
      xOffset = 0.0D;
      yOffset = 0.0D;
    }
    else if (blockAnchor.equals(RectangleAnchor.BOTTOM)) {
      xOffset = (-blockWidth / 2.0D);
      yOffset = 0.0D;
    }
    else if (blockAnchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
      xOffset = (-blockWidth);
      yOffset = 0.0D;
    }
    else if (blockAnchor.equals(RectangleAnchor.LEFT)) {
      xOffset = 0.0D;
      yOffset = (-blockHeight / 2.0D);
    }
    else if (blockAnchor.equals(RectangleAnchor.CENTER)) {
      xOffset = (-blockWidth / 2.0D);
      yOffset = (-blockHeight / 2.0D);
    }
    else if (blockAnchor.equals(RectangleAnchor.RIGHT)) {
      xOffset = (-blockWidth);
      yOffset = (-blockHeight / 2.0D);
    }
    else if (blockAnchor.equals(RectangleAnchor.TOP_LEFT)) {
      xOffset = 0.0D;
      yOffset = (-blockHeight);
    }
    else if (blockAnchor.equals(RectangleAnchor.TOP)) {
      xOffset = (-blockWidth / 2.0D);
      yOffset = (-blockHeight);
    }
    else if (blockAnchor.equals(RectangleAnchor.TOP_RIGHT)) {
      xOffset = (-blockWidth);
      yOffset = (-blockHeight);
    }
  }
  










  public Range findDomainBounds(XYDataset dataset)
  {
    if (dataset != null) {
      Range r = DatasetUtilities.findDomainBounds(dataset, false);
      if (r == null) {
        return null;
      }
      
      return new Range(r.getLowerBound() + xOffset, r.getUpperBound() + blockWidth + xOffset);
    }
    


    return null;
  }
  











  public Range findRangeBounds(XYDataset dataset)
  {
    if (dataset != null) {
      Range r = DatasetUtilities.findRangeBounds(dataset, false);
      if (r == null) {
        return null;
      }
      
      return new Range(r.getLowerBound() + yOffset, r.getUpperBound() + blockHeight + yOffset);
    }
    


    return null;
  }
  




















  public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass)
  {
    double x = dataset.getXValue(series, item);
    double y = dataset.getYValue(series, item);
    double z = 0.0D;
    if ((dataset instanceof XYZDataset)) {
      z = ((XYZDataset)dataset).getZValue(series, item);
    }
    Paint p = paintScale.getPaint(z);
    double xx0 = domainAxis.valueToJava2D(x + xOffset, dataArea, plot.getDomainAxisEdge());
    
    double yy0 = rangeAxis.valueToJava2D(y + yOffset, dataArea, plot.getRangeAxisEdge());
    
    double xx1 = domainAxis.valueToJava2D(x + blockWidth + xOffset, dataArea, plot.getDomainAxisEdge());
    
    double yy1 = rangeAxis.valueToJava2D(y + blockHeight + yOffset, dataArea, plot.getRangeAxisEdge());
    

    PlotOrientation orientation = plot.getOrientation();
    Rectangle2D block; Rectangle2D block; if (orientation.equals(PlotOrientation.HORIZONTAL)) {
      block = new Rectangle2D.Double(Math.min(yy0, yy1), Math.min(xx0, xx1), Math.abs(yy1 - yy0), Math.abs(xx0 - xx1));

    }
    else
    {
      block = new Rectangle2D.Double(Math.min(xx0, xx1), Math.min(yy0, yy1), Math.abs(xx1 - xx0), Math.abs(yy1 - yy0));
    }
    

    g2.setPaint(p);
    g2.fill(block);
    g2.setStroke(new BasicStroke(1.0F));
    g2.draw(block);
    
    EntityCollection entities = state.getEntityCollection();
    if (entities != null) {
      addEntity(entities, block, dataset, series, item, 0.0D, 0.0D);
    }
  }
  














  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYBlockRenderer)) {
      return false;
    }
    XYBlockRenderer that = (XYBlockRenderer)obj;
    if (blockHeight != blockHeight) {
      return false;
    }
    if (blockWidth != blockWidth) {
      return false;
    }
    if (!blockAnchor.equals(blockAnchor)) {
      return false;
    }
    if (!paintScale.equals(paintScale)) {
      return false;
    }
    return super.equals(obj);
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    XYBlockRenderer clone = (XYBlockRenderer)super.clone();
    if ((paintScale instanceof PublicCloneable)) {
      PublicCloneable pc = (PublicCloneable)paintScale;
      paintScale = ((PaintScale)pc.clone());
    }
    return clone;
  }
}
