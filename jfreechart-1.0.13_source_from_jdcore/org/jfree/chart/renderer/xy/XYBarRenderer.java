package org.jfree.chart.renderer.xy;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.GradientPaintTransformer;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;





















































































































public class XYBarRenderer
  extends AbstractXYItemRenderer
  implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 770559577251370036L;
  private static XYBarPainter defaultBarPainter = new GradientXYBarPainter();
  






  public static XYBarPainter getDefaultBarPainter()
  {
    return defaultBarPainter;
  }
  






  public static void setDefaultBarPainter(XYBarPainter painter)
  {
    if (painter == null) {
      throw new IllegalArgumentException("Null 'painter' argument.");
    }
    defaultBarPainter = painter;
  }
  



  private static boolean defaultShadowsVisible = true;
  
  private double base;
  
  private boolean useYInterval;
  private double margin;
  private boolean drawBarOutline;
  private GradientPaintTransformer gradientPaintTransformer;
  private transient Shape legendBar;
  
  public static boolean getDefaultShadowsVisible()
  {
    return defaultShadowsVisible;
  }
  








  public static void setDefaultShadowsVisible(boolean visible)
  {
    defaultShadowsVisible = visible;
  }
  




  protected class XYBarRendererState
    extends XYItemRendererState
  {
    private double g2Base;
    



    public XYBarRendererState(PlotRenderingInfo info)
    {
      super();
    }
    




    public double getG2Base()
    {
      return g2Base;
    }
    




    public void setG2Base(double value)
    {
      g2Base = value;
    }
  }
  









  private ItemLabelPosition positiveItemLabelPositionFallback;
  








  private ItemLabelPosition negativeItemLabelPositionFallback;
  








  private XYBarPainter barPainter;
  








  private boolean shadowsVisible;
  








  private double shadowXOffset;
  







  private double shadowYOffset;
  







  private double barAlignmentFactor;
  







  public XYBarRenderer()
  {
    this(0.0D);
  }
  





  public XYBarRenderer(double margin)
  {
    this.margin = margin;
    base = 0.0D;
    useYInterval = false;
    gradientPaintTransformer = new StandardGradientPaintTransformer();
    drawBarOutline = false;
    legendBar = new Rectangle2D.Double(-3.0D, -5.0D, 6.0D, 10.0D);
    barPainter = getDefaultBarPainter();
    shadowsVisible = getDefaultShadowsVisible();
    shadowXOffset = 4.0D;
    shadowYOffset = 4.0D;
    barAlignmentFactor = -1.0D;
  }
  






  public double getBase()
  {
    return base;
  }
  









  public void setBase(double base)
  {
    this.base = base;
    fireChangeEvent();
  }
  







  public boolean getUseYInterval()
  {
    return useYInterval;
  }
  








  public void setUseYInterval(boolean use)
  {
    if (useYInterval != use) {
      useYInterval = use;
      fireChangeEvent();
    }
  }
  







  public double getMargin()
  {
    return margin;
  }
  







  public void setMargin(double margin)
  {
    this.margin = margin;
    fireChangeEvent();
  }
  






  public boolean isDrawBarOutline()
  {
    return drawBarOutline;
  }
  







  public void setDrawBarOutline(boolean draw)
  {
    drawBarOutline = draw;
    fireChangeEvent();
  }
  







  public GradientPaintTransformer getGradientPaintTransformer()
  {
    return gradientPaintTransformer;
  }
  








  public void setGradientPaintTransformer(GradientPaintTransformer transformer)
  {
    gradientPaintTransformer = transformer;
    fireChangeEvent();
  }
  







  public Shape getLegendBar()
  {
    return legendBar;
  }
  







  public void setLegendBar(Shape bar)
  {
    if (bar == null) {
      throw new IllegalArgumentException("Null 'bar' argument.");
    }
    legendBar = bar;
    fireChangeEvent();
  }
  








  public ItemLabelPosition getPositiveItemLabelPositionFallback()
  {
    return positiveItemLabelPositionFallback;
  }
  










  public void setPositiveItemLabelPositionFallback(ItemLabelPosition position)
  {
    positiveItemLabelPositionFallback = position;
    fireChangeEvent();
  }
  








  public ItemLabelPosition getNegativeItemLabelPositionFallback()
  {
    return negativeItemLabelPositionFallback;
  }
  










  public void setNegativeItemLabelPositionFallback(ItemLabelPosition position)
  {
    negativeItemLabelPositionFallback = position;
    fireChangeEvent();
  }
  






  public XYBarPainter getBarPainter()
  {
    return barPainter;
  }
  







  public void setBarPainter(XYBarPainter painter)
  {
    if (painter == null) {
      throw new IllegalArgumentException("Null 'painter' argument.");
    }
    barPainter = painter;
    fireChangeEvent();
  }
  







  public boolean getShadowsVisible()
  {
    return shadowsVisible;
  }
  








  public void setShadowVisible(boolean visible)
  {
    shadowsVisible = visible;
    fireChangeEvent();
  }
  






  public double getShadowXOffset()
  {
    return shadowXOffset;
  }
  







  public void setShadowXOffset(double offset)
  {
    shadowXOffset = offset;
    fireChangeEvent();
  }
  






  public double getShadowYOffset()
  {
    return shadowYOffset;
  }
  







  public void setShadowYOffset(double offset)
  {
    shadowYOffset = offset;
    fireChangeEvent();
  }
  






  public double getBarAlignmentFactor()
  {
    return barAlignmentFactor;
  }
  








  public void setBarAlignmentFactor(double factor)
  {
    barAlignmentFactor = factor;
    fireChangeEvent();
  }
  
















  public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset dataset, PlotRenderingInfo info)
  {
    XYBarRendererState state = new XYBarRendererState(info);
    ValueAxis rangeAxis = plot.getRangeAxisForDataset(plot.indexOf(dataset));
    
    state.setG2Base(rangeAxis.valueToJava2D(base, dataArea, plot.getRangeAxisEdge()));
    
    return state;
  }
  









  public LegendItem getLegendItem(int datasetIndex, int series)
  {
    LegendItem result = null;
    XYPlot xyplot = getPlot();
    if (xyplot != null) {
      XYDataset dataset = xyplot.getDataset(datasetIndex);
      if (dataset != null) {
        XYSeriesLabelGenerator lg = getLegendItemLabelGenerator();
        String label = lg.generateLabel(dataset, series);
        String description = label;
        String toolTipText = null;
        if (getLegendItemToolTipGenerator() != null) {
          toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
        }
        
        String urlText = null;
        if (getLegendItemURLGenerator() != null) {
          urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
        }
        
        Shape shape = legendBar;
        Paint paint = lookupSeriesPaint(series);
        Paint outlinePaint = lookupSeriesOutlinePaint(series);
        Stroke outlineStroke = lookupSeriesOutlineStroke(series);
        if (drawBarOutline) {
          result = new LegendItem(label, description, toolTipText, urlText, shape, paint, outlineStroke, outlinePaint);
        }
        else
        {
          result = new LegendItem(label, description, toolTipText, urlText, shape, paint);
        }
        
        result.setLabelFont(lookupLegendTextFont(series));
        Paint labelPaint = lookupLegendTextPaint(series);
        if (labelPaint != null) {
          result.setLabelPaint(labelPaint);
        }
        result.setDataset(dataset);
        result.setDatasetIndex(datasetIndex);
        result.setSeriesKey(dataset.getSeriesKey(series));
        result.setSeriesIndex(series);
        if (getGradientPaintTransformer() != null) {
          result.setFillPaintTransformer(getGradientPaintTransformer());
        }
      }
    }
    
    return result;
  }
  





























  public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass)
  {
    if (!getItemVisible(series, item)) {
      return;
    }
    IntervalXYDataset intervalDataset = (IntervalXYDataset)dataset;
    double value1;
    double value0;
    double value1;
    if (useYInterval) {
      double value0 = intervalDataset.getStartYValue(series, item);
      value1 = intervalDataset.getEndYValue(series, item);
    }
    else {
      value0 = base;
      value1 = intervalDataset.getYValue(series, item);
    }
    if ((Double.isNaN(value0)) || (Double.isNaN(value1))) {
      return;
    }
    if (value0 <= value1) {
      if (rangeAxis.getRange().intersects(value0, value1)) {}



    }
    else if (!rangeAxis.getRange().intersects(value1, value0)) {
      return;
    }
    

    double translatedValue0 = rangeAxis.valueToJava2D(value0, dataArea, plot.getRangeAxisEdge());
    
    double translatedValue1 = rangeAxis.valueToJava2D(value1, dataArea, plot.getRangeAxisEdge());
    
    double bottom = Math.min(translatedValue0, translatedValue1);
    double top = Math.max(translatedValue0, translatedValue1);
    
    double startX = intervalDataset.getStartXValue(series, item);
    if (Double.isNaN(startX)) {
      return;
    }
    double endX = intervalDataset.getEndXValue(series, item);
    if (Double.isNaN(endX)) {
      return;
    }
    if (startX <= endX) {
      if (domainAxis.getRange().intersects(startX, endX)) {}



    }
    else if (!domainAxis.getRange().intersects(endX, startX)) {
      return;
    }
    


    if ((barAlignmentFactor >= 0.0D) && (barAlignmentFactor <= 1.0D)) {
      double x = intervalDataset.getXValue(series, item);
      double interval = endX - startX;
      startX = x - interval * barAlignmentFactor;
      endX = startX + interval;
    }
    
    RectangleEdge location = plot.getDomainAxisEdge();
    double translatedStartX = domainAxis.valueToJava2D(startX, dataArea, location);
    
    double translatedEndX = domainAxis.valueToJava2D(endX, dataArea, location);
    

    double translatedWidth = Math.max(1.0D, Math.abs(translatedEndX - translatedStartX));
    

    double left = Math.min(translatedStartX, translatedEndX);
    if (getMargin() > 0.0D) {
      double cut = translatedWidth * getMargin();
      translatedWidth -= cut;
      left += cut / 2.0D;
    }
    
    Rectangle2D bar = null;
    PlotOrientation orientation = plot.getOrientation();
    if (orientation == PlotOrientation.HORIZONTAL)
    {
      bottom = Math.max(bottom, dataArea.getMinX());
      top = Math.min(top, dataArea.getMaxX());
      bar = new Rectangle2D.Double(bottom, left, top - bottom, translatedWidth);

    }
    else if (orientation == PlotOrientation.VERTICAL)
    {
      bottom = Math.max(bottom, dataArea.getMinY());
      top = Math.min(top, dataArea.getMaxY());
      bar = new Rectangle2D.Double(left, bottom, translatedWidth, top - bottom);
    }
    

    boolean positive = value1 > 0.0D;
    boolean inverted = rangeAxis.isInverted();
    RectangleEdge barBase;
    RectangleEdge barBase; if (orientation == PlotOrientation.HORIZONTAL) { RectangleEdge barBase;
      if (((positive) && (inverted)) || ((!positive) && (!inverted))) {
        barBase = RectangleEdge.RIGHT;
      }
      else {
        barBase = RectangleEdge.LEFT;
      }
    } else {
      RectangleEdge barBase;
      if (((positive) && (!inverted)) || ((!positive) && (inverted))) {
        barBase = RectangleEdge.BOTTOM;
      }
      else {
        barBase = RectangleEdge.TOP;
      }
    }
    if (getShadowsVisible()) {
      barPainter.paintBarShadow(g2, this, series, item, bar, barBase, !useYInterval);
    }
    
    barPainter.paintBar(g2, this, series, item, bar, barBase);
    
    if (isItemLabelVisible(series, item)) {
      XYItemLabelGenerator generator = getItemLabelGenerator(series, item);
      
      drawItemLabel(g2, dataset, series, item, plot, generator, bar, value1 < 0.0D);
    }
    


    double x1 = (startX + endX) / 2.0D;
    double y1 = dataset.getYValue(series, item);
    double transX1 = domainAxis.valueToJava2D(x1, dataArea, location);
    double transY1 = rangeAxis.valueToJava2D(y1, dataArea, plot.getRangeAxisEdge());
    
    int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
    int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
    updateCrosshairValues(crosshairState, x1, y1, domainAxisIndex, rangeAxisIndex, transX1, transY1, plot.getOrientation());
    

    EntityCollection entities = state.getEntityCollection();
    if (entities != null) {
      addEntity(entities, bar, dataset, series, item, 0.0D, 0.0D);
    }
  }
  



















  protected void drawItemLabel(Graphics2D g2, XYDataset dataset, int series, int item, XYPlot plot, XYItemLabelGenerator generator, Rectangle2D bar, boolean negative)
  {
    if (generator == null) {
      return;
    }
    String label = generator.generateLabel(dataset, series, item);
    if (label == null) {
      return;
    }
    
    Font labelFont = getItemLabelFont(series, item);
    g2.setFont(labelFont);
    Paint paint = getItemLabelPaint(series, item);
    g2.setPaint(paint);
    

    ItemLabelPosition position = null;
    if (!negative) {
      position = getPositiveItemLabelPosition(series, item);
    }
    else {
      position = getNegativeItemLabelPosition(series, item);
    }
    

    Point2D anchorPoint = calculateLabelAnchorPoint(position.getItemLabelAnchor(), bar, plot.getOrientation());
    

    if (isInternalAnchor(position.getItemLabelAnchor())) {
      Shape bounds = TextUtilities.calculateRotatedStringBounds(label, g2, (float)anchorPoint.getX(), (float)anchorPoint.getY(), position.getTextAnchor(), position.getAngle(), position.getRotationAnchor());
      



      if ((bounds != null) && 
        (!bar.contains(bounds.getBounds2D()))) {
        if (!negative) {
          position = getPositiveItemLabelPositionFallback();
        }
        else {
          position = getNegativeItemLabelPositionFallback();
        }
        if (position != null) {
          anchorPoint = calculateLabelAnchorPoint(position.getItemLabelAnchor(), bar, plot.getOrientation());
        }
      }
    }
    




    if (position != null) {
      TextUtilities.drawRotatedString(label, g2, (float)anchorPoint.getX(), (float)anchorPoint.getY(), position.getTextAnchor(), position.getAngle(), position.getRotationAnchor());
    }
  }
  













  private Point2D calculateLabelAnchorPoint(ItemLabelAnchor anchor, Rectangle2D bar, PlotOrientation orientation)
  {
    Point2D result = null;
    double offset = getItemLabelAnchorOffset();
    double x0 = bar.getX() - offset;
    double x1 = bar.getX();
    double x2 = bar.getX() + offset;
    double x3 = bar.getCenterX();
    double x4 = bar.getMaxX() - offset;
    double x5 = bar.getMaxX();
    double x6 = bar.getMaxX() + offset;
    
    double y0 = bar.getMaxY() + offset;
    double y1 = bar.getMaxY();
    double y2 = bar.getMaxY() - offset;
    double y3 = bar.getCenterY();
    double y4 = bar.getMinY() + offset;
    double y5 = bar.getMinY();
    double y6 = bar.getMinY() - offset;
    
    if (anchor == ItemLabelAnchor.CENTER) {
      result = new Point2D.Double(x3, y3);
    }
    else if (anchor == ItemLabelAnchor.INSIDE1) {
      result = new Point2D.Double(x4, y4);
    }
    else if (anchor == ItemLabelAnchor.INSIDE2) {
      result = new Point2D.Double(x4, y4);
    }
    else if (anchor == ItemLabelAnchor.INSIDE3) {
      result = new Point2D.Double(x4, y3);
    }
    else if (anchor == ItemLabelAnchor.INSIDE4) {
      result = new Point2D.Double(x4, y2);
    }
    else if (anchor == ItemLabelAnchor.INSIDE5) {
      result = new Point2D.Double(x4, y2);
    }
    else if (anchor == ItemLabelAnchor.INSIDE6) {
      result = new Point2D.Double(x3, y2);
    }
    else if (anchor == ItemLabelAnchor.INSIDE7) {
      result = new Point2D.Double(x2, y2);
    }
    else if (anchor == ItemLabelAnchor.INSIDE8) {
      result = new Point2D.Double(x2, y2);
    }
    else if (anchor == ItemLabelAnchor.INSIDE9) {
      result = new Point2D.Double(x2, y3);
    }
    else if (anchor == ItemLabelAnchor.INSIDE10) {
      result = new Point2D.Double(x2, y4);
    }
    else if (anchor == ItemLabelAnchor.INSIDE11) {
      result = new Point2D.Double(x2, y4);
    }
    else if (anchor == ItemLabelAnchor.INSIDE12) {
      result = new Point2D.Double(x3, y4);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE1) {
      result = new Point2D.Double(x5, y6);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE2) {
      result = new Point2D.Double(x6, y5);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE3) {
      result = new Point2D.Double(x6, y3);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE4) {
      result = new Point2D.Double(x6, y1);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE5) {
      result = new Point2D.Double(x5, y0);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE6) {
      result = new Point2D.Double(x3, y0);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE7) {
      result = new Point2D.Double(x1, y0);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE8) {
      result = new Point2D.Double(x0, y1);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE9) {
      result = new Point2D.Double(x0, y3);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE10) {
      result = new Point2D.Double(x0, y5);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE11) {
      result = new Point2D.Double(x1, y6);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE12) {
      result = new Point2D.Double(x3, y6);
    }
    
    return result;
  }
  







  private boolean isInternalAnchor(ItemLabelAnchor anchor)
  {
    return (anchor == ItemLabelAnchor.CENTER) || (anchor == ItemLabelAnchor.INSIDE1) || (anchor == ItemLabelAnchor.INSIDE2) || (anchor == ItemLabelAnchor.INSIDE3) || (anchor == ItemLabelAnchor.INSIDE4) || (anchor == ItemLabelAnchor.INSIDE5) || (anchor == ItemLabelAnchor.INSIDE6) || (anchor == ItemLabelAnchor.INSIDE7) || (anchor == ItemLabelAnchor.INSIDE8) || (anchor == ItemLabelAnchor.INSIDE9) || (anchor == ItemLabelAnchor.INSIDE10) || (anchor == ItemLabelAnchor.INSIDE11) || (anchor == ItemLabelAnchor.INSIDE12);
  }
  





















  public Range findDomainBounds(XYDataset dataset)
  {
    if (dataset != null) {
      return DatasetUtilities.findDomainBounds(dataset, true);
    }
    
    return null;
  }
  










  public Range findRangeBounds(XYDataset dataset)
  {
    if (dataset != null) {
      return DatasetUtilities.findRangeBounds(dataset, useYInterval);
    }
    

    return null;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    XYBarRenderer result = (XYBarRenderer)super.clone();
    if (gradientPaintTransformer != null) {
      gradientPaintTransformer = ((GradientPaintTransformer)ObjectUtilities.clone(gradientPaintTransformer));
    }
    
    legendBar = ShapeUtilities.clone(legendBar);
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYBarRenderer)) {
      return false;
    }
    XYBarRenderer that = (XYBarRenderer)obj;
    if (base != base) {
      return false;
    }
    if (drawBarOutline != drawBarOutline) {
      return false;
    }
    if (margin != margin) {
      return false;
    }
    if (useYInterval != useYInterval) {
      return false;
    }
    if (!ObjectUtilities.equal(gradientPaintTransformer, gradientPaintTransformer))
    {

      return false;
    }
    if (!ShapeUtilities.equal(legendBar, legendBar)) {
      return false;
    }
    if (!ObjectUtilities.equal(positiveItemLabelPositionFallback, positiveItemLabelPositionFallback))
    {
      return false;
    }
    if (!ObjectUtilities.equal(negativeItemLabelPositionFallback, negativeItemLabelPositionFallback))
    {
      return false;
    }
    if (!barPainter.equals(barPainter)) {
      return false;
    }
    if (shadowsVisible != shadowsVisible) {
      return false;
    }
    if (shadowXOffset != shadowXOffset) {
      return false;
    }
    if (shadowYOffset != shadowYOffset) {
      return false;
    }
    if (barAlignmentFactor != barAlignmentFactor) {
      return false;
    }
    return super.equals(obj);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    legendBar = SerialUtilities.readShape(stream);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeShape(legendBar, stream);
  }
}
