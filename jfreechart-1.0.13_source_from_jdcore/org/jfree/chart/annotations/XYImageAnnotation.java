package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.Rectangle2D.Float;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;







































































public class XYImageAnnotation
  extends AbstractXYAnnotation
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -4364694501921559958L;
  private double x;
  private double y;
  private transient Image image;
  private RectangleAnchor anchor;
  
  public XYImageAnnotation(double x, double y, Image image)
  {
    this(x, y, image, RectangleAnchor.CENTER);
  }
  











  public XYImageAnnotation(double x, double y, Image image, RectangleAnchor anchor)
  {
    if (image == null) {
      throw new IllegalArgumentException("Null 'image' argument.");
    }
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    this.x = x;
    this.y = y;
    this.image = image;
    this.anchor = anchor;
  }
  






  public double getX()
  {
    return x;
  }
  






  public double getY()
  {
    return y;
  }
  






  public Image getImage()
  {
    return image;
  }
  






  public RectangleAnchor getImageAnchor()
  {
    return anchor;
  }
  

















  public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info)
  {
    PlotOrientation orientation = plot.getOrientation();
    AxisLocation domainAxisLocation = plot.getDomainAxisLocation();
    AxisLocation rangeAxisLocation = plot.getRangeAxisLocation();
    RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(domainAxisLocation, orientation);
    
    RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(rangeAxisLocation, orientation);
    
    float j2DX = (float)domainAxis.valueToJava2D(x, dataArea, domainEdge);
    
    float j2DY = (float)rangeAxis.valueToJava2D(y, dataArea, rangeEdge);
    
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
    int w = image.getWidth(null);
    int h = image.getHeight(null);
    
    Rectangle2D imageRect = new Rectangle2D.Double(0.0D, 0.0D, w, h);
    Point2D anchorPoint = RectangleAnchor.coordinates(imageRect, anchor);
    
    xx -= (float)anchorPoint.getX();
    yy -= (float)anchorPoint.getY();
    g2.drawImage(image, (int)xx, (int)yy, null);
    
    String toolTip = getToolTipText();
    String url = getURL();
    if ((toolTip != null) || (url != null)) {
      addEntity(info, new Rectangle2D.Float(xx, yy, w, h), rendererIndex, toolTip, url);
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
    if (!(obj instanceof XYImageAnnotation)) {
      return false;
    }
    XYImageAnnotation that = (XYImageAnnotation)obj;
    if (x != x) {
      return false;
    }
    if (y != y) {
      return false;
    }
    if (!ObjectUtilities.equal(image, image)) {
      return false;
    }
    if (!anchor.equals(anchor)) {
      return false;
    }
    
    return true;
  }
  




  public int hashCode()
  {
    return image.hashCode();
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
  }
  








  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
  }
}
