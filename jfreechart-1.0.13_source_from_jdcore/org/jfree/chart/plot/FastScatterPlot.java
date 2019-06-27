package org.jfree.chart.plot;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.axis.ValueTick;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.Range;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ArrayUtilities;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;







































































public class FastScatterPlot
  extends Plot
  implements ValueAxisPlot, Pannable, Zoomable, Cloneable, Serializable
{
  private static final long serialVersionUID = 7871545897358563521L;
  public static final Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0.5F, 0, 2, 0.0F, new float[] { 2.0F, 2.0F }, 0.0F);
  



  public static final Paint DEFAULT_GRIDLINE_PAINT = Color.lightGray;
  


  private float[][] data;
  


  private Range xDataRange;
  


  private Range yDataRange;
  


  private ValueAxis domainAxis;
  


  private ValueAxis rangeAxis;
  


  private transient Paint paint;
  


  private boolean domainGridlinesVisible;
  


  private transient Stroke domainGridlineStroke;
  


  private transient Paint domainGridlinePaint;
  


  private boolean rangeGridlinesVisible;
  

  private transient Stroke rangeGridlineStroke;
  

  private transient Paint rangeGridlinePaint;
  

  private boolean domainPannable;
  

  private boolean rangePannable;
  

  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
  





  public FastScatterPlot()
  {
    this((float[][])null, new NumberAxis("X"), new NumberAxis("Y"));
  }
  











  public FastScatterPlot(float[][] data, ValueAxis domainAxis, ValueAxis rangeAxis)
  {
    if (domainAxis == null) {
      throw new IllegalArgumentException("Null 'domainAxis' argument.");
    }
    if (rangeAxis == null) {
      throw new IllegalArgumentException("Null 'rangeAxis' argument.");
    }
    
    this.data = data;
    xDataRange = calculateXDataRange(data);
    yDataRange = calculateYDataRange(data);
    this.domainAxis = domainAxis;
    this.domainAxis.setPlot(this);
    this.domainAxis.addChangeListener(this);
    this.rangeAxis = rangeAxis;
    this.rangeAxis.setPlot(this);
    this.rangeAxis.addChangeListener(this);
    
    paint = Color.red;
    
    domainGridlinesVisible = true;
    domainGridlinePaint = DEFAULT_GRIDLINE_PAINT;
    domainGridlineStroke = DEFAULT_GRIDLINE_STROKE;
    
    rangeGridlinesVisible = true;
    rangeGridlinePaint = DEFAULT_GRIDLINE_PAINT;
    rangeGridlineStroke = DEFAULT_GRIDLINE_STROKE;
  }
  





  public String getPlotType()
  {
    return localizationResources.getString("Fast_Scatter_Plot");
  }
  






  public float[][] getData()
  {
    return data;
  }
  







  public void setData(float[][] data)
  {
    this.data = data;
    fireChangeEvent();
  }
  




  public PlotOrientation getOrientation()
  {
    return PlotOrientation.VERTICAL;
  }
  






  public ValueAxis getDomainAxis()
  {
    return domainAxis;
  }
  









  public void setDomainAxis(ValueAxis axis)
  {
    if (axis == null) {
      throw new IllegalArgumentException("Null 'axis' argument.");
    }
    domainAxis = axis;
    fireChangeEvent();
  }
  






  public ValueAxis getRangeAxis()
  {
    return rangeAxis;
  }
  









  public void setRangeAxis(ValueAxis axis)
  {
    if (axis == null) {
      throw new IllegalArgumentException("Null 'axis' argument.");
    }
    rangeAxis = axis;
    fireChangeEvent();
  }
  







  public Paint getPaint()
  {
    return paint;
  }
  







  public void setPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    this.paint = paint;
    fireChangeEvent();
  }
  








  public boolean isDomainGridlinesVisible()
  {
    return domainGridlinesVisible;
  }
  








  public void setDomainGridlinesVisible(boolean visible)
  {
    if (domainGridlinesVisible != visible) {
      domainGridlinesVisible = visible;
      fireChangeEvent();
    }
  }
  







  public Stroke getDomainGridlineStroke()
  {
    return domainGridlineStroke;
  }
  







  public void setDomainGridlineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    domainGridlineStroke = stroke;
    fireChangeEvent();
  }
  







  public Paint getDomainGridlinePaint()
  {
    return domainGridlinePaint;
  }
  







  public void setDomainGridlinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    domainGridlinePaint = paint;
    fireChangeEvent();
  }
  







  public boolean isRangeGridlinesVisible()
  {
    return rangeGridlinesVisible;
  }
  








  public void setRangeGridlinesVisible(boolean visible)
  {
    if (rangeGridlinesVisible != visible) {
      rangeGridlinesVisible = visible;
      fireChangeEvent();
    }
  }
  







  public Stroke getRangeGridlineStroke()
  {
    return rangeGridlineStroke;
  }
  







  public void setRangeGridlineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    rangeGridlineStroke = stroke;
    fireChangeEvent();
  }
  







  public Paint getRangeGridlinePaint()
  {
    return rangeGridlinePaint;
  }
  







  public void setRangeGridlinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    rangeGridlinePaint = paint;
    fireChangeEvent();
  }
  















  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info)
  {
    if (info != null) {
      info.setPlotArea(area);
    }
    

    RectangleInsets insets = getInsets();
    insets.trim(area);
    
    AxisSpace space = new AxisSpace();
    space = domainAxis.reserveSpace(g2, this, area, RectangleEdge.BOTTOM, space);
    
    space = rangeAxis.reserveSpace(g2, this, area, RectangleEdge.LEFT, space);
    
    Rectangle2D dataArea = space.shrink(area, null);
    
    if (info != null) {
      info.setDataArea(dataArea);
    }
    

    drawBackground(g2, dataArea);
    
    AxisState domainAxisState = domainAxis.draw(g2, dataArea.getMaxY(), area, dataArea, RectangleEdge.BOTTOM, info);
    
    AxisState rangeAxisState = rangeAxis.draw(g2, dataArea.getMinX(), area, dataArea, RectangleEdge.LEFT, info);
    
    drawDomainGridlines(g2, dataArea, domainAxisState.getTicks());
    drawRangeGridlines(g2, dataArea, rangeAxisState.getTicks());
    
    Shape originalClip = g2.getClip();
    Composite originalComposite = g2.getComposite();
    
    g2.clip(dataArea);
    g2.setComposite(AlphaComposite.getInstance(3, getForegroundAlpha()));
    

    render(g2, dataArea, info, null);
    
    g2.setClip(originalClip);
    g2.setComposite(originalComposite);
    drawOutline(g2, dataArea);
  }
  
















  public void render(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, CrosshairState crosshairState)
  {
    g2.setPaint(paint);
    













    if (data != null) {
      for (int i = 0; i < data[0].length; i++) {
        float x = data[0][i];
        float y = data[1][i];
        


        int transX = (int)domainAxis.valueToJava2D(x, dataArea, RectangleEdge.BOTTOM);
        
        int transY = (int)rangeAxis.valueToJava2D(y, dataArea, RectangleEdge.LEFT);
        
        g2.fillRect(transX, transY, 1, 1);
      }
    }
  }
  













  protected void drawDomainGridlines(Graphics2D g2, Rectangle2D dataArea, List ticks)
  {
    if (isDomainGridlinesVisible()) {
      Iterator iterator = ticks.iterator();
      while (iterator.hasNext()) {
        ValueTick tick = (ValueTick)iterator.next();
        double v = domainAxis.valueToJava2D(tick.getValue(), dataArea, RectangleEdge.BOTTOM);
        
        Line2D line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
        
        g2.setPaint(getDomainGridlinePaint());
        g2.setStroke(getDomainGridlineStroke());
        g2.draw(line);
      }
    }
  }
  









  protected void drawRangeGridlines(Graphics2D g2, Rectangle2D dataArea, List ticks)
  {
    if (isRangeGridlinesVisible()) {
      Iterator iterator = ticks.iterator();
      while (iterator.hasNext()) {
        ValueTick tick = (ValueTick)iterator.next();
        double v = rangeAxis.valueToJava2D(tick.getValue(), dataArea, RectangleEdge.LEFT);
        
        Line2D line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
        
        g2.setPaint(getRangeGridlinePaint());
        g2.setStroke(getRangeGridlineStroke());
        g2.draw(line);
      }
    }
  }
  









  public Range getDataRange(ValueAxis axis)
  {
    Range result = null;
    if (axis == domainAxis) {
      result = xDataRange;
    }
    else if (axis == rangeAxis) {
      result = yDataRange;
    }
    return result;
  }
  







  private Range calculateXDataRange(float[][] data)
  {
    Range result = null;
    
    if (data != null) {
      float lowest = Float.POSITIVE_INFINITY;
      float highest = Float.NEGATIVE_INFINITY;
      for (int i = 0; i < data[0].length; i++) {
        float v = data[0][i];
        if (v < lowest) {
          lowest = v;
        }
        if (v > highest) {
          highest = v;
        }
      }
      if (lowest <= highest) {
        result = new Range(lowest, highest);
      }
    }
    
    return result;
  }
  








  private Range calculateYDataRange(float[][] data)
  {
    Range result = null;
    if (data != null) {
      float lowest = Float.POSITIVE_INFINITY;
      float highest = Float.NEGATIVE_INFINITY;
      for (int i = 0; i < data[0].length; i++) {
        float v = data[1][i];
        if (v < lowest) {
          lowest = v;
        }
        if (v > highest) {
          highest = v;
        }
      }
      if (lowest <= highest) {
        result = new Range(lowest, highest);
      }
    }
    return result;
  }
  








  public void zoomDomainAxes(double factor, PlotRenderingInfo info, Point2D source)
  {
    domainAxis.resizeRange(factor);
  }
  













  public void zoomDomainAxes(double factor, PlotRenderingInfo info, Point2D source, boolean useAnchor)
  {
    if (useAnchor)
    {

      double sourceX = source.getX();
      double anchorX = domainAxis.java2DToValue(sourceX, info.getDataArea(), RectangleEdge.BOTTOM);
      
      domainAxis.resizeRange2(factor, anchorX);
    }
    else {
      domainAxis.resizeRange(factor);
    }
  }
  











  public void zoomDomainAxes(double lowerPercent, double upperPercent, PlotRenderingInfo info, Point2D source)
  {
    domainAxis.zoomRange(lowerPercent, upperPercent);
  }
  







  public void zoomRangeAxes(double factor, PlotRenderingInfo info, Point2D source)
  {
    rangeAxis.resizeRange(factor);
  }
  













  public void zoomRangeAxes(double factor, PlotRenderingInfo info, Point2D source, boolean useAnchor)
  {
    if (useAnchor)
    {

      double sourceY = source.getY();
      double anchorY = rangeAxis.java2DToValue(sourceY, info.getDataArea(), RectangleEdge.LEFT);
      
      rangeAxis.resizeRange2(factor, anchorY);
    }
    else {
      rangeAxis.resizeRange(factor);
    }
  }
  











  public void zoomRangeAxes(double lowerPercent, double upperPercent, PlotRenderingInfo info, Point2D source)
  {
    rangeAxis.zoomRange(lowerPercent, upperPercent);
  }
  




  public boolean isDomainZoomable()
  {
    return true;
  }
  




  public boolean isRangeZoomable()
  {
    return true;
  }
  







  public boolean isDomainPannable()
  {
    return domainPannable;
  }
  







  public void setDomainPannable(boolean pannable)
  {
    domainPannable = pannable;
  }
  







  public boolean isRangePannable()
  {
    return rangePannable;
  }
  







  public void setRangePannable(boolean pannable)
  {
    rangePannable = pannable;
  }
  









  public void panDomainAxes(double percent, PlotRenderingInfo info, Point2D source)
  {
    if ((!isDomainPannable()) || (domainAxis == null)) {
      return;
    }
    double length = domainAxis.getRange().getLength();
    double adj = -percent * length;
    if (domainAxis.isInverted()) {
      adj = -adj;
    }
    domainAxis.setRange(domainAxis.getLowerBound() + adj, domainAxis.getUpperBound() + adj);
  }
  










  public void panRangeAxes(double percent, PlotRenderingInfo info, Point2D source)
  {
    if ((!isRangePannable()) || (rangeAxis == null)) {
      return;
    }
    double length = rangeAxis.getRange().getLength();
    double adj = percent * length;
    if (rangeAxis.isInverted()) {
      adj = -adj;
    }
    rangeAxis.setRange(rangeAxis.getLowerBound() + adj, rangeAxis.getUpperBound() + adj);
  }
  










  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (!(obj instanceof FastScatterPlot)) {
      return false;
    }
    FastScatterPlot that = (FastScatterPlot)obj;
    if (domainPannable != domainPannable) {
      return false;
    }
    if (rangePannable != rangePannable) {
      return false;
    }
    if (!ArrayUtilities.equal(data, data)) {
      return false;
    }
    if (!ObjectUtilities.equal(domainAxis, domainAxis)) {
      return false;
    }
    if (!ObjectUtilities.equal(rangeAxis, rangeAxis)) {
      return false;
    }
    if (!PaintUtilities.equal(paint, paint)) {
      return false;
    }
    if (domainGridlinesVisible != domainGridlinesVisible) {
      return false;
    }
    if (!PaintUtilities.equal(domainGridlinePaint, domainGridlinePaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(domainGridlineStroke, domainGridlineStroke))
    {
      return false;
    }
    if ((!rangeGridlinesVisible) == rangeGridlinesVisible) {
      return false;
    }
    if (!PaintUtilities.equal(rangeGridlinePaint, rangeGridlinePaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(rangeGridlineStroke, rangeGridlineStroke))
    {
      return false;
    }
    return true;
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    FastScatterPlot clone = (FastScatterPlot)super.clone();
    if (data != null) {
      data = ArrayUtilities.clone(data);
    }
    if (domainAxis != null) {
      domainAxis = ((ValueAxis)domainAxis.clone());
      domainAxis.setPlot(clone);
      domainAxis.addChangeListener(clone);
    }
    if (rangeAxis != null) {
      rangeAxis = ((ValueAxis)rangeAxis.clone());
      rangeAxis.setPlot(clone);
      rangeAxis.addChangeListener(clone);
    }
    return clone;
  }
  






  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(paint, stream);
    SerialUtilities.writeStroke(domainGridlineStroke, stream);
    SerialUtilities.writePaint(domainGridlinePaint, stream);
    SerialUtilities.writeStroke(rangeGridlineStroke, stream);
    SerialUtilities.writePaint(rangeGridlinePaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    
    paint = SerialUtilities.readPaint(stream);
    domainGridlineStroke = SerialUtilities.readStroke(stream);
    domainGridlinePaint = SerialUtilities.readPaint(stream);
    
    rangeGridlineStroke = SerialUtilities.readStroke(stream);
    rangeGridlinePaint = SerialUtilities.readPaint(stream);
    
    if (domainAxis != null) {
      domainAxis.addChangeListener(this);
    }
    
    if (rangeAxis != null) {
      rangeAxis.addChangeListener(this);
    }
  }
}
