package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
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






























































public class XYPolygonAnnotation
  extends AbstractXYAnnotation
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -6984203651995900036L;
  private double[] polygon;
  private transient Stroke stroke;
  private transient Paint outlinePaint;
  private transient Paint fillPaint;
  
  public XYPolygonAnnotation(double[] polygon)
  {
    this(polygon, new BasicStroke(1.0F), Color.black);
  }
  












  public XYPolygonAnnotation(double[] polygon, Stroke stroke, Paint outlinePaint)
  {
    this(polygon, stroke, outlinePaint, null);
  }
  














  public XYPolygonAnnotation(double[] polygon, Stroke stroke, Paint outlinePaint, Paint fillPaint)
  {
    if (polygon == null) {
      throw new IllegalArgumentException("Null 'polygon' argument.");
    }
    if (polygon.length % 2 != 0) {
      throw new IllegalArgumentException("The 'polygon' array must contain an even number of items.");
    }
    
    this.polygon = ((double[])polygon.clone());
    this.stroke = stroke;
    this.outlinePaint = outlinePaint;
    this.fillPaint = fillPaint;
  }
  








  public double[] getPolygonCoordinates()
  {
    return (double[])polygon.clone();
  }
  






  public Paint getFillPaint()
  {
    return fillPaint;
  }
  






  public Stroke getOutlineStroke()
  {
    return stroke;
  }
  






  public Paint getOutlinePaint()
  {
    return outlinePaint;
  }
  















  public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info)
  {
    if (polygon.length < 4) {
      return;
    }
    PlotOrientation orientation = plot.getOrientation();
    RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot.getDomainAxisLocation(), orientation);
    
    RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot.getRangeAxisLocation(), orientation);
    

    GeneralPath area = new GeneralPath();
    double x = domainAxis.valueToJava2D(polygon[0], dataArea, domainEdge);
    
    double y = rangeAxis.valueToJava2D(polygon[1], dataArea, rangeEdge);
    
    if (orientation == PlotOrientation.HORIZONTAL) {
      area.moveTo((float)y, (float)x);
      for (int i = 2; i < polygon.length; i += 2) {
        x = domainAxis.valueToJava2D(polygon[i], dataArea, domainEdge);
        
        y = rangeAxis.valueToJava2D(polygon[(i + 1)], dataArea, rangeEdge);
        
        area.lineTo((float)y, (float)x);
      }
      area.closePath();
    }
    else if (orientation == PlotOrientation.VERTICAL) {
      area.moveTo((float)x, (float)y);
      for (int i = 2; i < polygon.length; i += 2) {
        x = domainAxis.valueToJava2D(polygon[i], dataArea, domainEdge);
        
        y = rangeAxis.valueToJava2D(polygon[(i + 1)], dataArea, rangeEdge);
        
        area.lineTo((float)x, (float)y);
      }
      area.closePath();
    }
    

    if (fillPaint != null) {
      g2.setPaint(fillPaint);
      g2.fill(area);
    }
    
    if ((stroke != null) && (outlinePaint != null)) {
      g2.setPaint(outlinePaint);
      g2.setStroke(stroke);
      g2.draw(area);
    }
    addEntity(info, area, rendererIndex, getToolTipText(), getURL());
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    
    if (!super.equals(obj)) {
      return false;
    }
    if (!(obj instanceof XYPolygonAnnotation)) {
      return false;
    }
    XYPolygonAnnotation that = (XYPolygonAnnotation)obj;
    if (!Arrays.equals(polygon, polygon)) {
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
    result = 37 * result + HashUtilities.hashCodeForDoubleArray(polygon);
    
    result = 37 * result + HashUtilities.hashCodeForPaint(fillPaint);
    result = 37 * result + HashUtilities.hashCodeForPaint(outlinePaint);
    
    if (stroke != null) {
      result = 37 * result + stroke.hashCode();
    }
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
    SerialUtilities.writeStroke(stroke, stream);
    SerialUtilities.writePaint(outlinePaint, stream);
    SerialUtilities.writePaint(fillPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    stroke = SerialUtilities.readStroke(stream);
    outlinePaint = SerialUtilities.readPaint(stream);
    fillPaint = SerialUtilities.readPaint(stream);
  }
}
