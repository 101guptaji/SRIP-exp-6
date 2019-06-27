package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.Drawable;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;




































































public class XYDrawableAnnotation
  extends AbstractXYAnnotation
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -6540812859722691020L;
  private double drawScaleFactor;
  private double x;
  private double y;
  private double displayWidth;
  private double displayHeight;
  private Drawable drawable;
  
  public XYDrawableAnnotation(double x, double y, double width, double height, Drawable drawable)
  {
    this(x, y, width, height, 1.0D, drawable);
  }
  
















  public XYDrawableAnnotation(double x, double y, double displayWidth, double displayHeight, double drawScaleFactor, Drawable drawable)
  {
    if (drawable == null) {
      throw new IllegalArgumentException("Null 'drawable' argument.");
    }
    this.x = x;
    this.y = y;
    this.displayWidth = displayWidth;
    this.displayHeight = displayHeight;
    this.drawScaleFactor = drawScaleFactor;
    this.drawable = drawable;
  }
  
















  public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info)
  {
    PlotOrientation orientation = plot.getOrientation();
    RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot.getDomainAxisLocation(), orientation);
    
    RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot.getRangeAxisLocation(), orientation);
    
    float j2DX = (float)domainAxis.valueToJava2D(x, dataArea, domainEdge);
    
    float j2DY = (float)rangeAxis.valueToJava2D(y, dataArea, rangeEdge);
    
    Rectangle2D displayArea = new Rectangle2D.Double(j2DX - displayWidth / 2.0D, j2DY - displayHeight / 2.0D, displayWidth, displayHeight);
    






    AffineTransform savedTransform = g2.getTransform();
    Rectangle2D drawArea = new Rectangle2D.Double(0.0D, 0.0D, displayWidth * drawScaleFactor, displayHeight * drawScaleFactor);
    


    g2.scale(1.0D / drawScaleFactor, 1.0D / drawScaleFactor);
    g2.translate((j2DX - displayWidth / 2.0D) * drawScaleFactor, (j2DY - displayHeight / 2.0D) * drawScaleFactor);
    
    drawable.draw(g2, drawArea);
    g2.setTransform(savedTransform);
    String toolTip = getToolTipText();
    String url = getURL();
    if ((toolTip != null) || (url != null)) {
      addEntity(info, displayArea, rendererIndex, toolTip, url);
    }
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    
    if (!super.equals(obj)) {
      return false;
    }
    if (!(obj instanceof XYDrawableAnnotation)) {
      return false;
    }
    XYDrawableAnnotation that = (XYDrawableAnnotation)obj;
    if (x != x) {
      return false;
    }
    if (y != y) {
      return false;
    }
    if (displayWidth != displayWidth) {
      return false;
    }
    if (displayHeight != displayHeight) {
      return false;
    }
    if (drawScaleFactor != drawScaleFactor) {
      return false;
    }
    if (!ObjectUtilities.equal(drawable, drawable)) {
      return false;
    }
    
    return true;
  }
  







  public int hashCode()
  {
    long temp = Double.doubleToLongBits(x);
    int result = (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(y);
    result = 29 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(displayWidth);
    result = 29 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(displayHeight);
    result = 29 * result + (int)(temp ^ temp >>> 32);
    return result;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
