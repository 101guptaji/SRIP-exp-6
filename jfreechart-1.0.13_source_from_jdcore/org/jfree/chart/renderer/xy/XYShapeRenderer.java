package org.jfree.chart.renderer.xy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.RendererChangeEvent;
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
import org.jfree.io.SerialUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;





















































































public class XYShapeRenderer
  extends AbstractXYItemRenderer
  implements XYItemRenderer, Cloneable, Serializable
{
  private static final long serialVersionUID = 8320552104211173221L;
  private PaintScale paintScale;
  private boolean drawOutlines;
  private boolean useOutlinePaint;
  private boolean useFillPaint;
  private boolean guideLinesVisible;
  private transient Paint guideLinePaint;
  private transient Stroke guideLineStroke;
  
  public XYShapeRenderer()
  {
    paintScale = new LookupPaintScale();
    useFillPaint = false;
    drawOutlines = false;
    useOutlinePaint = true;
    guideLinesVisible = false;
    guideLinePaint = Color.darkGray;
    guideLineStroke = new BasicStroke();
    setBaseShape(new Ellipse2D.Double(-5.0D, -5.0D, 10.0D, 10.0D));
    setAutoPopulateSeriesShape(false);
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
    notifyListeners(new RendererChangeEvent(this));
  }
  







  public boolean getDrawOutlines()
  {
    return drawOutlines;
  }
  











  public void setDrawOutlines(boolean flag)
  {
    drawOutlines = flag;
    fireChangeEvent();
  }
  












  public boolean getUseFillPaint()
  {
    return useFillPaint;
  }
  








  public void setUseFillPaint(boolean flag)
  {
    useFillPaint = flag;
    fireChangeEvent();
  }
  







  public boolean getUseOutlinePaint()
  {
    return useOutlinePaint;
  }
  








  public void setUseOutlinePaint(boolean use)
  {
    useOutlinePaint = use;
    fireChangeEvent();
  }
  








  public boolean isGuideLinesVisible()
  {
    return guideLinesVisible;
  }
  








  public void setGuideLinesVisible(boolean visible)
  {
    guideLinesVisible = visible;
    fireChangeEvent();
  }
  






  public Paint getGuideLinePaint()
  {
    return guideLinePaint;
  }
  







  public void setGuideLinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    guideLinePaint = paint;
    fireChangeEvent();
  }
  






  public Stroke getGuideLineStroke()
  {
    return guideLineStroke;
  }
  







  public void setGuideLineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    guideLineStroke = stroke;
    fireChangeEvent();
  }
  








  public Range findDomainBounds(XYDataset dataset)
  {
    if (dataset != null) {
      Range r = DatasetUtilities.findDomainBounds(dataset, false);
      double offset = 0.0D;
      return new Range(r.getLowerBound() + offset, r.getUpperBound() + offset);
    }
    

    return null;
  }
  









  public Range findRangeBounds(XYDataset dataset)
  {
    if (dataset != null) {
      Range r = DatasetUtilities.findRangeBounds(dataset, false);
      double offset = 0.0D;
      return new Range(r.getLowerBound() + offset, r.getUpperBound() + offset);
    }
    

    return null;
  }
  





  public int getPassCount()
  {
    return 2;
  }
  



















  public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass)
  {
    Shape hotspot = null;
    EntityCollection entities = null;
    if (info != null) {
      entities = info.getOwner().getEntityCollection();
    }
    
    double x = dataset.getXValue(series, item);
    double y = dataset.getYValue(series, item);
    if ((Double.isNaN(x)) || (Double.isNaN(y)))
    {
      return;
    }
    
    double transX = domainAxis.valueToJava2D(x, dataArea, plot.getDomainAxisEdge());
    
    double transY = rangeAxis.valueToJava2D(y, dataArea, plot.getRangeAxisEdge());
    

    PlotOrientation orientation = plot.getOrientation();
    

    if ((pass == 0) && (guideLinesVisible)) {
      g2.setStroke(guideLineStroke);
      g2.setPaint(guideLinePaint);
      if (orientation == PlotOrientation.HORIZONTAL) {
        g2.draw(new Line2D.Double(transY, dataArea.getMinY(), transY, dataArea.getMaxY()));
        
        g2.draw(new Line2D.Double(dataArea.getMinX(), transX, dataArea.getMaxX(), transX));
      }
      else
      {
        g2.draw(new Line2D.Double(transX, dataArea.getMinY(), transX, dataArea.getMaxY()));
        
        g2.draw(new Line2D.Double(dataArea.getMinX(), transY, dataArea.getMaxX(), transY));
      }
      
    }
    else if (pass == 1) {
      Shape shape = getItemShape(series, item);
      if (orientation == PlotOrientation.HORIZONTAL) {
        shape = ShapeUtilities.createTranslatedShape(shape, transY, transX);

      }
      else if (orientation == PlotOrientation.VERTICAL) {
        shape = ShapeUtilities.createTranslatedShape(shape, transX, transY);
      }
      
      hotspot = shape;
      if (shape.intersects(dataArea))
      {
        g2.setPaint(getPaint(dataset, series, item));
        g2.fill(shape);
        
        if (drawOutlines) {
          if (getUseOutlinePaint()) {
            g2.setPaint(getItemOutlinePaint(series, item));
          }
          else {
            g2.setPaint(getItemPaint(series, item));
          }
          g2.setStroke(getItemOutlineStroke(series, item));
          g2.draw(shape);
        }
      }
      

      if (entities != null) {
        addEntity(entities, hotspot, dataset, series, item, transX, transY);
      }
    }
  }
  









  protected Paint getPaint(XYDataset dataset, int series, int item)
  {
    Paint p = null;
    if ((dataset instanceof XYZDataset)) {
      double z = ((XYZDataset)dataset).getZValue(series, item);
      p = paintScale.getPaint(z);

    }
    else if (useFillPaint) {
      p = getItemFillPaint(series, item);
    }
    else {
      p = getItemPaint(series, item);
    }
    
    return p;
  }
  













  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYShapeRenderer)) {
      return false;
    }
    XYShapeRenderer that = (XYShapeRenderer)obj;
    if (((paintScale == null) && (paintScale != null)) || (!paintScale.equals(paintScale)))
    {
      return false;
    }
    if (drawOutlines != drawOutlines) {
      return false;
    }
    if (useOutlinePaint != useOutlinePaint) {
      return false;
    }
    if (useFillPaint != useFillPaint) {
      return false;
    }
    if (guideLinesVisible != guideLinesVisible) {
      return false;
    }
    if (((guideLinePaint == null) && (guideLinePaint != null)) || (!guideLinePaint.equals(guideLinePaint)))
    {
      return false; }
    if (((guideLineStroke == null) && (guideLineStroke != null)) || (!guideLineStroke.equals(guideLineStroke)))
    {
      return false;
    }
    return super.equals(obj);
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    XYShapeRenderer clone = (XYShapeRenderer)super.clone();
    if ((paintScale instanceof PublicCloneable)) {
      PublicCloneable pc = (PublicCloneable)paintScale;
      paintScale = ((PaintScale)pc.clone());
    }
    return clone;
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    guideLinePaint = SerialUtilities.readPaint(stream);
    guideLineStroke = SerialUtilities.readStroke(stream);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(guideLinePaint, stream);
    SerialUtilities.writeStroke(guideLineStroke, stream);
  }
}
