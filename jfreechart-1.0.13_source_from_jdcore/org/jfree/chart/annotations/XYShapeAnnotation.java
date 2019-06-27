package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;









































































public class XYShapeAnnotation
  extends AbstractXYAnnotation
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -8553218317600684041L;
  private transient Shape shape;
  private transient Stroke stroke;
  private transient Paint outlinePaint;
  private transient Paint fillPaint;
  
  public XYShapeAnnotation(Shape shape)
  {
    this(shape, new BasicStroke(1.0F), Color.black);
  }
  







  public XYShapeAnnotation(Shape shape, Stroke stroke, Paint outlinePaint)
  {
    this(shape, stroke, outlinePaint, null);
  }
  









  public XYShapeAnnotation(Shape shape, Stroke stroke, Paint outlinePaint, Paint fillPaint)
  {
    if (shape == null) {
      throw new IllegalArgumentException("Null 'shape' argument.");
    }
    this.shape = shape;
    this.stroke = stroke;
    this.outlinePaint = outlinePaint;
    this.fillPaint = fillPaint;
  }
  















  public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info)
  {
    PlotOrientation orientation = plot.getOrientation();
    RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot.getDomainAxisLocation(), orientation);
    
    RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot.getRangeAxisLocation(), orientation);
    



    Rectangle2D bounds = shape.getBounds2D();
    double x0 = bounds.getMinX();
    double x1 = bounds.getMaxX();
    double xx0 = domainAxis.valueToJava2D(x0, dataArea, domainEdge);
    double xx1 = domainAxis.valueToJava2D(x1, dataArea, domainEdge);
    double m00 = (xx1 - xx0) / (x1 - x0);
    double m02 = xx0 - x0 * m00;
    
    double y0 = bounds.getMaxY();
    double y1 = bounds.getMinY();
    double yy0 = rangeAxis.valueToJava2D(y0, dataArea, rangeEdge);
    double yy1 = rangeAxis.valueToJava2D(y1, dataArea, rangeEdge);
    double m11 = (yy1 - yy0) / (y1 - y0);
    double m12 = yy0 - m11 * y0;
    

    Shape s = null;
    if (orientation == PlotOrientation.HORIZONTAL) {
      AffineTransform t1 = new AffineTransform(0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
      
      AffineTransform t2 = new AffineTransform(m11, 0.0D, 0.0D, m00, m12, m02);
      
      s = t1.createTransformedShape(shape);
      s = t2.createTransformedShape(s);
    }
    else if (orientation == PlotOrientation.VERTICAL) {
      AffineTransform t = new AffineTransform(m00, 0.0D, 0.0D, m11, m02, m12);
      s = t.createTransformedShape(shape);
    }
    
    if (fillPaint != null) {
      g2.setPaint(fillPaint);
      g2.fill(s);
    }
    
    if ((stroke != null) && (outlinePaint != null)) {
      g2.setPaint(outlinePaint);
      g2.setStroke(stroke);
      g2.draw(s);
    }
    addEntity(info, s, rendererIndex, getToolTipText(), getURL());
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    
    if (!super.equals(obj)) {
      return false;
    }
    if (!(obj instanceof XYShapeAnnotation)) {
      return false;
    }
    XYShapeAnnotation that = (XYShapeAnnotation)obj;
    if (!shape.equals(shape)) {
      return false;
    }
    if (!ObjectUtilities.equal(stroke, stroke)) {
      return false;
    }
    if (!PaintUtilities.equal(outlinePaint, outlinePaint)) {
      return false;
    }
    if (!PaintUtilities.equal(fillPaint, fillPaint)) {
      return false;
    }
    
    return true;
  }
  




  public int hashCode()
  {
    int result = 193;
    result = 37 * result + shape.hashCode();
    if (stroke != null) {
      result = 37 * result + stroke.hashCode();
    }
    result = 37 * result + HashUtilities.hashCodeForPaint(outlinePaint);
    
    result = 37 * result + HashUtilities.hashCodeForPaint(fillPaint);
    return result;
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
    SerialUtilities.writeShape(shape, stream);
    SerialUtilities.writeStroke(stroke, stream);
    SerialUtilities.writePaint(outlinePaint, stream);
    SerialUtilities.writePaint(fillPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    shape = SerialUtilities.readShape(stream);
    stroke = SerialUtilities.readStroke(stream);
    outlinePaint = SerialUtilities.readPaint(stream);
    fillPaint = SerialUtilities.readPaint(stream);
  }
}
