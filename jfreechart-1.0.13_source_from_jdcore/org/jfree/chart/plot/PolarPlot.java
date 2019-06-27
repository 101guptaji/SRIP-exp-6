package org.jfree.chart.plot;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.renderer.PolarItemRenderer;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;







































































public class PolarPlot
  extends Plot
  implements ValueAxisPlot, Zoomable, RendererChangeListener, Cloneable, Serializable
{
  private static final long serialVersionUID = 3794383185924179525L;
  private static final int MARGIN = 20;
  private static final double ANNOTATION_MARGIN = 7.0D;
  public static final double DEFAULT_ANGLE_TICK_UNIT_SIZE = 45.0D;
  public static final Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0.5F, 0, 2, 0.0F, new float[] { 2.0F, 2.0F }, 0.0F);
  



  public static final Paint DEFAULT_GRIDLINE_PAINT = Color.gray;
  

  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
  



  private List angleTicks;
  



  private ValueAxis axis;
  



  private XYDataset dataset;
  


  private PolarItemRenderer renderer;
  


  private TickUnit angleTickUnit;
  


  private boolean angleLabelsVisible = true;
  

  private Font angleLabelFont = new Font("SansSerif", 0, 12);
  

  private transient Paint angleLabelPaint = Color.black;
  

  private boolean angleGridlinesVisible;
  

  private transient Stroke angleGridlineStroke;
  

  private transient Paint angleGridlinePaint;
  

  private boolean radiusGridlinesVisible;
  

  private transient Stroke radiusGridlineStroke;
  

  private transient Paint radiusGridlinePaint;
  

  private List cornerTextItems = new ArrayList();
  


  public PolarPlot()
  {
    this(null, null, null);
  }
  











  public PolarPlot(XYDataset dataset, ValueAxis radiusAxis, PolarItemRenderer renderer)
  {
    this.dataset = dataset;
    if (this.dataset != null) {
      this.dataset.addChangeListener(this);
    }
    angleTickUnit = new NumberTickUnit(45.0D);
    
    axis = radiusAxis;
    if (axis != null) {
      axis.setPlot(this);
      axis.addChangeListener(this);
    }
    
    this.renderer = renderer;
    if (this.renderer != null) {
      this.renderer.setPlot(this);
      this.renderer.addChangeListener(this);
    }
    
    angleGridlinesVisible = true;
    angleGridlineStroke = DEFAULT_GRIDLINE_STROKE;
    angleGridlinePaint = DEFAULT_GRIDLINE_PAINT;
    
    radiusGridlinesVisible = true;
    radiusGridlineStroke = DEFAULT_GRIDLINE_STROKE;
    radiusGridlinePaint = DEFAULT_GRIDLINE_PAINT;
  }
  







  public void addCornerTextItem(String text)
  {
    if (text == null) {
      throw new IllegalArgumentException("Null 'text' argument.");
    }
    cornerTextItems.add(text);
    fireChangeEvent();
  }
  







  public void removeCornerTextItem(String text)
  {
    boolean removed = cornerTextItems.remove(text);
    if (removed) {
      fireChangeEvent();
    }
  }
  






  public void clearCornerTextItems()
  {
    if (cornerTextItems.size() > 0) {
      cornerTextItems.clear();
      fireChangeEvent();
    }
  }
  




  public String getPlotType()
  {
    return localizationResources.getString("Polar_Plot");
  }
  






  public ValueAxis getAxis()
  {
    return axis;
  }
  





  public void setAxis(ValueAxis axis)
  {
    if (axis != null) {
      axis.setPlot(this);
    }
    

    if (this.axis != null) {
      this.axis.removeChangeListener(this);
    }
    
    this.axis = axis;
    if (this.axis != null) {
      this.axis.configure();
      this.axis.addChangeListener(this);
    }
    fireChangeEvent();
  }
  






  public XYDataset getDataset()
  {
    return dataset;
  }
  









  public void setDataset(XYDataset dataset)
  {
    XYDataset existing = this.dataset;
    if (existing != null) {
      existing.removeChangeListener(this);
    }
    

    this.dataset = dataset;
    if (this.dataset != null) {
      setDatasetGroup(this.dataset.getGroup());
      this.dataset.addChangeListener(this);
    }
    

    DatasetChangeEvent event = new DatasetChangeEvent(this, this.dataset);
    datasetChanged(event);
  }
  






  public PolarItemRenderer getRenderer()
  {
    return renderer;
  }
  









  public void setRenderer(PolarItemRenderer renderer)
  {
    if (this.renderer != null) {
      this.renderer.removeChangeListener(this);
    }
    
    this.renderer = renderer;
    if (this.renderer != null) {
      this.renderer.setPlot(this);
    }
    fireChangeEvent();
  }
  







  public TickUnit getAngleTickUnit()
  {
    return angleTickUnit;
  }
  







  public void setAngleTickUnit(TickUnit unit)
  {
    if (unit == null) {
      throw new IllegalArgumentException("Null 'unit' argument.");
    }
    angleTickUnit = unit;
    fireChangeEvent();
  }
  






  public boolean isAngleLabelsVisible()
  {
    return angleLabelsVisible;
  }
  







  public void setAngleLabelsVisible(boolean visible)
  {
    if (angleLabelsVisible != visible) {
      angleLabelsVisible = visible;
      fireChangeEvent();
    }
  }
  






  public Font getAngleLabelFont()
  {
    return angleLabelFont;
  }
  







  public void setAngleLabelFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    angleLabelFont = font;
    fireChangeEvent();
  }
  






  public Paint getAngleLabelPaint()
  {
    return angleLabelPaint;
  }
  





  public void setAngleLabelPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    angleLabelPaint = paint;
    fireChangeEvent();
  }
  







  public boolean isAngleGridlinesVisible()
  {
    return angleGridlinesVisible;
  }
  










  public void setAngleGridlinesVisible(boolean visible)
  {
    if (angleGridlinesVisible != visible) {
      angleGridlinesVisible = visible;
      fireChangeEvent();
    }
  }
  







  public Stroke getAngleGridlineStroke()
  {
    return angleGridlineStroke;
  }
  









  public void setAngleGridlineStroke(Stroke stroke)
  {
    angleGridlineStroke = stroke;
    fireChangeEvent();
  }
  







  public Paint getAngleGridlinePaint()
  {
    return angleGridlinePaint;
  }
  








  public void setAngleGridlinePaint(Paint paint)
  {
    angleGridlinePaint = paint;
    fireChangeEvent();
  }
  







  public boolean isRadiusGridlinesVisible()
  {
    return radiusGridlinesVisible;
  }
  










  public void setRadiusGridlinesVisible(boolean visible)
  {
    if (radiusGridlinesVisible != visible) {
      radiusGridlinesVisible = visible;
      fireChangeEvent();
    }
  }
  







  public Stroke getRadiusGridlineStroke()
  {
    return radiusGridlineStroke;
  }
  









  public void setRadiusGridlineStroke(Stroke stroke)
  {
    radiusGridlineStroke = stroke;
    fireChangeEvent();
  }
  







  public Paint getRadiusGridlinePaint()
  {
    return radiusGridlinePaint;
  }
  









  public void setRadiusGridlinePaint(Paint paint)
  {
    radiusGridlinePaint = paint;
    fireChangeEvent();
  }
  






  protected List refreshAngleTicks()
  {
    List ticks = new ArrayList();
    for (double currentTickVal = 0.0D; currentTickVal < 360.0D; 
        currentTickVal += angleTickUnit.getSize()) {
      NumberTick tick = new NumberTick(new Double(currentTickVal), angleTickUnit.valueToString(currentTickVal), TextAnchor.CENTER, TextAnchor.CENTER, 0.0D);
      

      ticks.add(tick);
    }
    return ticks;
  }
  

























  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info)
  {
    boolean b1 = area.getWidth() <= 10.0D;
    boolean b2 = area.getHeight() <= 10.0D;
    if ((b1) || (b2)) {
      return;
    }
    

    if (info != null) {
      info.setPlotArea(area);
    }
    

    RectangleInsets insets = getInsets();
    insets.trim(area);
    
    Rectangle2D dataArea = area;
    if (info != null) {
      info.setDataArea(dataArea);
    }
    

    drawBackground(g2, dataArea);
    double h = Math.min(dataArea.getWidth() / 2.0D, dataArea.getHeight() / 2.0D) - 20.0D;
    
    Rectangle2D quadrant = new Rectangle2D.Double(dataArea.getCenterX(), dataArea.getCenterY(), h, h);
    
    AxisState state = drawAxis(g2, area, quadrant);
    if (renderer != null) {
      Shape originalClip = g2.getClip();
      Composite originalComposite = g2.getComposite();
      
      g2.clip(dataArea);
      g2.setComposite(AlphaComposite.getInstance(3, getForegroundAlpha()));
      

      angleTicks = refreshAngleTicks();
      drawGridlines(g2, dataArea, angleTicks, state.getTicks());
      

      render(g2, dataArea, info);
      
      g2.setClip(originalClip);
      g2.setComposite(originalComposite);
    }
    drawOutline(g2, dataArea);
    drawCornerTextItems(g2, dataArea);
  }
  





  protected void drawCornerTextItems(Graphics2D g2, Rectangle2D area)
  {
    if (cornerTextItems.isEmpty()) {
      return;
    }
    
    g2.setColor(Color.black);
    double width = 0.0D;
    double height = 0.0D;
    for (Iterator it = cornerTextItems.iterator(); it.hasNext();) {
      String msg = (String)it.next();
      FontMetrics fm = g2.getFontMetrics();
      Rectangle2D bounds = TextUtilities.getTextBounds(msg, g2, fm);
      width = Math.max(width, bounds.getWidth());
      height += bounds.getHeight();
    }
    
    double xadj = 14.0D;
    double yadj = 7.0D;
    width += xadj;
    height += yadj;
    
    double x = area.getMaxX() - width;
    double y = area.getMaxY() - height;
    g2.drawRect((int)x, (int)y, (int)width, (int)height);
    x += 7.0D;
    for (Iterator it = cornerTextItems.iterator(); it.hasNext();) {
      String msg = (String)it.next();
      Rectangle2D bounds = TextUtilities.getTextBounds(msg, g2, g2.getFontMetrics());
      
      y += bounds.getHeight();
      g2.drawString(msg, (int)x, (int)y);
    }
  }
  









  protected AxisState drawAxis(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea)
  {
    return axis.draw(g2, dataArea.getMinY(), plotArea, dataArea, RectangleEdge.TOP, null);
  }
  














  protected void render(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info)
  {
    if (!DatasetUtilities.isEmptyOrNull(dataset)) {
      int seriesCount = dataset.getSeriesCount();
      for (int series = 0; series < seriesCount; series++) {
        renderer.drawSeries(g2, dataArea, info, this, dataset, series);
      }
    }
    else
    {
      drawNoDataMessage(g2, dataArea);
    }
  }
  










  protected void drawGridlines(Graphics2D g2, Rectangle2D dataArea, List angularTicks, List radialTicks)
  {
    if (renderer == null) {
      return;
    }
    

    if (isAngleGridlinesVisible()) {
      Stroke gridStroke = getAngleGridlineStroke();
      Paint gridPaint = getAngleGridlinePaint();
      if ((gridStroke != null) && (gridPaint != null)) {
        renderer.drawAngularGridLines(g2, this, angularTicks, dataArea);
      }
    }
    


    if (isRadiusGridlinesVisible()) {
      Stroke gridStroke = getRadiusGridlineStroke();
      Paint gridPaint = getRadiusGridlinePaint();
      if ((gridStroke != null) && (gridPaint != null)) {
        renderer.drawRadialGridLines(g2, this, axis, radialTicks, dataArea);
      }
    }
  }
  





  public void zoom(double percent)
  {
    if (percent > 0.0D) {
      double radius = getMaxRadius();
      double scaledRadius = radius * percent;
      axis.setUpperBound(scaledRadius);
      getAxis().setAutoRange(false);
    }
    else {
      getAxis().setAutoRange(true);
    }
  }
  






  public Range getDataRange(ValueAxis axis)
  {
    Range result = null;
    if (dataset != null) {
      result = Range.combine(result, DatasetUtilities.findRangeBounds(dataset));
    }
    
    return result;
  }
  







  public void datasetChanged(DatasetChangeEvent event)
  {
    if (axis != null) {
      axis.configure();
    }
    
    if (getParent() != null) {
      getParent().datasetChanged(event);
    }
    else {
      super.datasetChanged(event);
    }
  }
  






  public void rendererChanged(RendererChangeEvent event)
  {
    fireChangeEvent();
  }
  





  public int getSeriesCount()
  {
    int result = 0;
    
    if (dataset != null) {
      result = dataset.getSeriesCount();
    }
    return result;
  }
  






  public LegendItemCollection getLegendItems()
  {
    LegendItemCollection result = new LegendItemCollection();
    

    if ((dataset != null) && 
      (renderer != null)) {
      int seriesCount = dataset.getSeriesCount();
      for (int i = 0; i < seriesCount; i++) {
        LegendItem item = renderer.getLegendItem(i);
        result.add(item);
      }
    }
    
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof PolarPlot)) {
      return false;
    }
    PolarPlot that = (PolarPlot)obj;
    if (!ObjectUtilities.equal(axis, axis)) {
      return false;
    }
    if (!ObjectUtilities.equal(renderer, renderer)) {
      return false;
    }
    if (!angleTickUnit.equals(angleTickUnit)) {
      return false;
    }
    if (angleGridlinesVisible != angleGridlinesVisible) {
      return false;
    }
    if (angleLabelsVisible != angleLabelsVisible) {
      return false;
    }
    if (!angleLabelFont.equals(angleLabelFont)) {
      return false;
    }
    if (!PaintUtilities.equal(angleLabelPaint, angleLabelPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(angleGridlineStroke, angleGridlineStroke))
    {
      return false;
    }
    if (!PaintUtilities.equal(angleGridlinePaint, angleGridlinePaint))
    {

      return false;
    }
    if (radiusGridlinesVisible != radiusGridlinesVisible) {
      return false;
    }
    if (!ObjectUtilities.equal(radiusGridlineStroke, radiusGridlineStroke))
    {
      return false;
    }
    if (!PaintUtilities.equal(radiusGridlinePaint, radiusGridlinePaint))
    {
      return false;
    }
    if (!cornerTextItems.equals(cornerTextItems)) {
      return false;
    }
    return super.equals(obj);
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    PolarPlot clone = (PolarPlot)super.clone();
    if (axis != null) {
      axis = ((ValueAxis)ObjectUtilities.clone(axis));
      axis.setPlot(clone);
      axis.addChangeListener(clone);
    }
    
    if (dataset != null) {
      dataset.addChangeListener(clone);
    }
    
    if (renderer != null) {
      renderer = ((PolarItemRenderer)ObjectUtilities.clone(renderer));
    }
    

    cornerTextItems = new ArrayList(cornerTextItems);
    
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeStroke(angleGridlineStroke, stream);
    SerialUtilities.writePaint(angleGridlinePaint, stream);
    SerialUtilities.writeStroke(radiusGridlineStroke, stream);
    SerialUtilities.writePaint(radiusGridlinePaint, stream);
    SerialUtilities.writePaint(angleLabelPaint, stream);
  }
  








  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    angleGridlineStroke = SerialUtilities.readStroke(stream);
    angleGridlinePaint = SerialUtilities.readPaint(stream);
    radiusGridlineStroke = SerialUtilities.readStroke(stream);
    radiusGridlinePaint = SerialUtilities.readPaint(stream);
    angleLabelPaint = SerialUtilities.readPaint(stream);
    
    if (axis != null) {
      axis.setPlot(this);
      axis.addChangeListener(this);
    }
    
    if (dataset != null) {
      dataset.addChangeListener(this);
    }
  }
  











  public void zoomDomainAxes(double factor, PlotRenderingInfo state, Point2D source) {}
  











  public void zoomDomainAxes(double factor, PlotRenderingInfo state, Point2D source, boolean useAnchor) {}
  











  public void zoomDomainAxes(double lowerPercent, double upperPercent, PlotRenderingInfo state, Point2D source) {}
  











  public void zoomRangeAxes(double factor, PlotRenderingInfo state, Point2D source)
  {
    zoom(factor);
  }
  













  public void zoomRangeAxes(double factor, PlotRenderingInfo info, Point2D source, boolean useAnchor)
  {
    if (useAnchor)
    {

      double sourceX = source.getX();
      double anchorX = axis.java2DToValue(sourceX, info.getDataArea(), RectangleEdge.BOTTOM);
      
      axis.resizeRange(factor, anchorX);
    }
    else {
      axis.resizeRange(factor);
    }
  }
  









  public void zoomRangeAxes(double lowerPercent, double upperPercent, PlotRenderingInfo state, Point2D source)
  {
    zoom((upperPercent + lowerPercent) / 2.0D);
  }
  




  public boolean isDomainZoomable()
  {
    return false;
  }
  




  public boolean isRangeZoomable()
  {
    return true;
  }
  




  public PlotOrientation getOrientation()
  {
    return PlotOrientation.HORIZONTAL;
  }
  




  public double getMaxRadius()
  {
    return axis.getUpperBound();
  }
  













  public Point translateValueThetaRadiusToJava2D(double angleDegrees, double radius, Rectangle2D dataArea)
  {
    double radians = Math.toRadians(angleDegrees - 90.0D);
    
    double minx = dataArea.getMinX() + 20.0D;
    double maxx = dataArea.getMaxX() - 20.0D;
    double miny = dataArea.getMinY() + 20.0D;
    double maxy = dataArea.getMaxY() - 20.0D;
    
    double lengthX = maxx - minx;
    double lengthY = maxy - miny;
    double length = Math.min(lengthX, lengthY);
    
    double midX = minx + lengthX / 2.0D;
    double midY = miny + lengthY / 2.0D;
    
    double axisMin = axis.getLowerBound();
    double axisMax = getMaxRadius();
    double adjustedRadius = Math.max(radius, axisMin);
    
    double xv = length / 2.0D * Math.cos(radians);
    double yv = length / 2.0D * Math.sin(radians);
    
    float x = (float)(midX + xv * (adjustedRadius - axisMin) / (axisMax - axisMin));
    
    float y = (float)(midY + yv * (adjustedRadius - axisMin) / (axisMax - axisMin));
    

    int ix = Math.round(x);
    int iy = Math.round(y);
    
    Point p = new Point(ix, iy);
    return p;
  }
}
