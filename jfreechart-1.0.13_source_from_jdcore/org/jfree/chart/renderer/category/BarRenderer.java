package org.jfree.chart.renderer.category;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D.Float;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategorySeriesLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.GradientPaintTransformer;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;



















































































































public class BarRenderer
  extends AbstractCategoryItemRenderer
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 6000649414965887481L;
  public static final double DEFAULT_ITEM_MARGIN = 0.2D;
  public static final double BAR_OUTLINE_WIDTH_THRESHOLD = 3.0D;
  private static BarPainter defaultBarPainter = new GradientBarPainter();
  






  public static BarPainter getDefaultBarPainter()
  {
    return defaultBarPainter;
  }
  






  public static void setDefaultBarPainter(BarPainter painter)
  {
    if (painter == null) {
      throw new IllegalArgumentException("Null 'painter' argument.");
    }
    defaultBarPainter = painter;
  }
  



  private static boolean defaultShadowsVisible = true;
  private double itemMargin;
  private boolean drawBarOutline;
  private double maximumBarWidth;
  private double minimumBarLength;
  private GradientPaintTransformer gradientPaintTransformer;
  private ItemLabelPosition positiveItemLabelPositionFallback;
  private ItemLabelPosition negativeItemLabelPositionFallback;
  private double upperClip;
  
  public static boolean getDefaultShadowsVisible()
  {
    return defaultShadowsVisible;
  }
  








  public static void setDefaultShadowsVisible(boolean visible)
  {
    defaultShadowsVisible = visible;
  }
  









  private double lowerClip;
  








  private double base;
  








  private boolean includeBaseInRange;
  








  private BarPainter barPainter;
  








  private boolean shadowsVisible;
  







  private transient Paint shadowPaint;
  







  private double shadowXOffset;
  







  private double shadowYOffset;
  







  public BarRenderer()
  {
    base = 0.0D;
    includeBaseInRange = true;
    itemMargin = 0.2D;
    drawBarOutline = false;
    maximumBarWidth = 1.0D;
    
    positiveItemLabelPositionFallback = null;
    negativeItemLabelPositionFallback = null;
    gradientPaintTransformer = new StandardGradientPaintTransformer();
    minimumBarLength = 0.0D;
    setBaseLegendShape(new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D));
    barPainter = getDefaultBarPainter();
    shadowsVisible = getDefaultShadowsVisible();
    shadowPaint = Color.gray;
    shadowXOffset = 4.0D;
    shadowYOffset = 4.0D;
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
  







  public double getItemMargin()
  {
    return itemMargin;
  }
  









  public void setItemMargin(double percent)
  {
    itemMargin = percent;
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
  







  public double getMaximumBarWidth()
  {
    return maximumBarWidth;
  }
  








  public void setMaximumBarWidth(double percent)
  {
    maximumBarWidth = percent;
    fireChangeEvent();
  }
  







  public double getMinimumBarLength()
  {
    return minimumBarLength;
  }
  













  public void setMinimumBarLength(double min)
  {
    if (min < 0.0D) {
      throw new IllegalArgumentException("Requires 'min' >= 0.0");
    }
    minimumBarLength = min;
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
  











  public boolean getIncludeBaseInRange()
  {
    return includeBaseInRange;
  }
  











  public void setIncludeBaseInRange(boolean include)
  {
    if (includeBaseInRange != include) {
      includeBaseInRange = include;
      fireChangeEvent();
    }
  }
  








  public BarPainter getBarPainter()
  {
    return barPainter;
  }
  









  public void setBarPainter(BarPainter painter)
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
  








  public Paint getShadowPaint()
  {
    return shadowPaint;
  }
  









  public void setShadowPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    shadowPaint = paint;
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
  






  public double getLowerClip()
  {
    return lowerClip;
  }
  






  public double getUpperClip()
  {
    return upperClip;
  }
  

















  public CategoryItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, CategoryPlot plot, int rendererIndex, PlotRenderingInfo info)
  {
    CategoryItemRendererState state = super.initialise(g2, dataArea, plot, rendererIndex, info);
    


    ValueAxis rangeAxis = plot.getRangeAxisForDataset(rendererIndex);
    lowerClip = rangeAxis.getRange().getLowerBound();
    upperClip = rangeAxis.getRange().getUpperBound();
    

    calculateBarWidth(plot, dataArea, rendererIndex, state);
    
    return state;
  }
  












  protected void calculateBarWidth(CategoryPlot plot, Rectangle2D dataArea, int rendererIndex, CategoryItemRendererState state)
  {
    CategoryAxis domainAxis = getDomainAxis(plot, rendererIndex);
    CategoryDataset dataset = plot.getDataset(rendererIndex);
    if (dataset != null) {
      int columns = dataset.getColumnCount();
      int rows = state.getVisibleSeriesCount() >= 0 ? state.getVisibleSeriesCount() : dataset.getRowCount();
      
      double space = 0.0D;
      PlotOrientation orientation = plot.getOrientation();
      if (orientation == PlotOrientation.HORIZONTAL) {
        space = dataArea.getHeight();
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        space = dataArea.getWidth();
      }
      double maxWidth = space * getMaximumBarWidth();
      double categoryMargin = 0.0D;
      double currentItemMargin = 0.0D;
      if (columns > 1) {
        categoryMargin = domainAxis.getCategoryMargin();
      }
      if (rows > 1) {
        currentItemMargin = getItemMargin();
      }
      double used = space * (1.0D - domainAxis.getLowerMargin() - domainAxis.getUpperMargin() - categoryMargin - currentItemMargin);
      

      if (rows * columns > 0) {
        state.setBarWidth(Math.min(used / (rows * columns), maxWidth));
      }
      else {
        state.setBarWidth(Math.min(used, maxWidth));
      }
    }
  }
  





















  protected double calculateBarW0(CategoryPlot plot, PlotOrientation orientation, Rectangle2D dataArea, CategoryAxis domainAxis, CategoryItemRendererState state, int row, int column)
  {
    double space = 0.0D;
    if (orientation == PlotOrientation.HORIZONTAL) {
      space = dataArea.getHeight();
    }
    else {
      space = dataArea.getWidth();
    }
    double barW0 = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
    
    int seriesCount = state.getVisibleSeriesCount() >= 0 ? state.getVisibleSeriesCount() : getRowCount();
    
    int categoryCount = getColumnCount();
    if (seriesCount > 1) {
      double seriesGap = space * getItemMargin() / (categoryCount * (seriesCount - 1));
      
      double seriesW = calculateSeriesWidth(space, domainAxis, categoryCount, seriesCount);
      
      barW0 = barW0 + row * (seriesW + seriesGap) + seriesW / 2.0D - state.getBarWidth() / 2.0D;
    }
    else
    {
      barW0 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge()) - state.getBarWidth() / 2.0D;
    }
    

    return barW0;
  }
  







  protected double[] calculateBarL0L1(double value)
  {
    double lclip = getLowerClip();
    double uclip = getUpperClip();
    double barLow = Math.min(base, value);
    double barHigh = Math.max(base, value);
    if (barHigh < lclip) {
      return null;
    }
    if (barLow > uclip) {
      return null;
    }
    barLow = Math.max(barLow, lclip);
    barHigh = Math.min(barHigh, uclip);
    return new double[] { barLow, barHigh };
  }
  










  public Range findRangeBounds(CategoryDataset dataset)
  {
    if (dataset == null) {
      return null;
    }
    Range result = DatasetUtilities.findRangeBounds(dataset);
    if ((result != null) && 
      (includeBaseInRange)) {
      result = Range.expandToInclude(result, base);
    }
    
    return result;
  }
  








  public LegendItem getLegendItem(int datasetIndex, int series)
  {
    CategoryPlot cp = getPlot();
    if (cp == null) {
      return null;
    }
    

    if ((!isSeriesVisible(series)) || (!isSeriesVisibleInLegend(series))) {
      return null;
    }
    
    CategoryDataset dataset = cp.getDataset(datasetIndex);
    String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
    
    String description = label;
    String toolTipText = null;
    if (getLegendItemToolTipGenerator() != null) {
      toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
    }
    
    String urlText = null;
    if (getLegendItemURLGenerator() != null) {
      urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
    }
    
    Shape shape = lookupLegendShape(series);
    Paint paint = lookupSeriesPaint(series);
    Paint outlinePaint = lookupSeriesOutlinePaint(series);
    Stroke outlineStroke = lookupSeriesOutlineStroke(series);
    
    LegendItem result = new LegendItem(label, description, toolTipText, urlText, true, shape, true, paint, isDrawBarOutline(), outlinePaint, outlineStroke, false, new Line2D.Float(), new BasicStroke(1.0F), Color.black);
    


    result.setLabelFont(lookupLegendTextFont(series));
    Paint labelPaint = lookupLegendTextPaint(series);
    if (labelPaint != null) {
      result.setLabelPaint(labelPaint);
    }
    result.setDataset(dataset);
    result.setDatasetIndex(datasetIndex);
    result.setSeriesKey(dataset.getRowKey(series));
    result.setSeriesIndex(series);
    if (gradientPaintTransformer != null) {
      result.setFillPaintTransformer(gradientPaintTransformer);
    }
    return result;
  }
  

























  public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass)
  {
    int visibleRow = state.getVisibleSeriesIndex(row);
    if (visibleRow < 0) {
      return;
    }
    
    Number dataValue = dataset.getValue(row, column);
    if (dataValue == null) {
      return;
    }
    
    double value = dataValue.doubleValue();
    PlotOrientation orientation = plot.getOrientation();
    double barW0 = calculateBarW0(plot, orientation, dataArea, domainAxis, state, visibleRow, column);
    
    double[] barL0L1 = calculateBarL0L1(value);
    if (barL0L1 == null) {
      return;
    }
    
    RectangleEdge edge = plot.getRangeAxisEdge();
    double transL0 = rangeAxis.valueToJava2D(barL0L1[0], dataArea, edge);
    double transL1 = rangeAxis.valueToJava2D(barL0L1[1], dataArea, edge);
    







    boolean positive = value >= base;
    boolean inverted = rangeAxis.isInverted();
    double barL0 = Math.min(transL0, transL1);
    double barLength = Math.abs(transL1 - transL0);
    double barLengthAdj = 0.0D;
    if ((barLength > 0.0D) && (barLength < getMinimumBarLength())) {
      barLengthAdj = getMinimumBarLength() - barLength;
    }
    double barL0Adj = 0.0D;
    RectangleEdge barBase;
    RectangleEdge barBase; if (orientation == PlotOrientation.HORIZONTAL) { RectangleEdge barBase;
      if (((positive) && (inverted)) || ((!positive) && (!inverted))) {
        barL0Adj = barLengthAdj;
        barBase = RectangleEdge.RIGHT;
      }
      else {
        barBase = RectangleEdge.LEFT;
      }
    } else {
      RectangleEdge barBase;
      if (((positive) && (!inverted)) || ((!positive) && (inverted))) {
        barL0Adj = barLengthAdj;
        barBase = RectangleEdge.BOTTOM;
      }
      else {
        barBase = RectangleEdge.TOP;
      }
    }
    

    Rectangle2D bar = null;
    if (orientation == PlotOrientation.HORIZONTAL) {
      bar = new Rectangle2D.Double(barL0 - barL0Adj, barW0, barLength + barLengthAdj, state.getBarWidth());
    }
    else
    {
      bar = new Rectangle2D.Double(barW0, barL0 - barL0Adj, state.getBarWidth(), barLength + barLengthAdj);
    }
    
    if (getShadowsVisible()) {
      barPainter.paintBarShadow(g2, this, row, column, bar, barBase, true);
    }
    
    barPainter.paintBar(g2, this, row, column, bar, barBase);
    
    CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
    
    if ((generator != null) && (isItemLabelVisible(row, column))) {
      drawItemLabel(g2, dataset, row, column, plot, generator, bar, value < 0.0D);
    }
    


    int datasetIndex = plot.indexOf(dataset);
    updateCrosshairValues(state.getCrosshairState(), dataset.getRowKey(row), dataset.getColumnKey(column), value, datasetIndex, barW0, barL0, orientation);
    



    EntityCollection entities = state.getEntityCollection();
    if (entities != null) {
      addItemEntity(entities, dataset, row, column, bar);
    }
  }
  











  protected double calculateSeriesWidth(double space, CategoryAxis axis, int categories, int series)
  {
    double factor = 1.0D - getItemMargin() - axis.getLowerMargin() - axis.getUpperMargin();
    
    if (categories > 1) {
      factor -= axis.getCategoryMargin();
    }
    return space * factor / (categories * series);
  }
  




















  protected void drawItemLabel(Graphics2D g2, CategoryDataset data, int row, int column, CategoryPlot plot, CategoryItemLabelGenerator generator, Rectangle2D bar, boolean negative)
  {
    String label = generator.generateLabel(data, row, column);
    if (label == null) {
      return;
    }
    
    Font labelFont = getItemLabelFont(row, column);
    g2.setFont(labelFont);
    Paint paint = getItemLabelPaint(row, column);
    g2.setPaint(paint);
    

    ItemLabelPosition position = null;
    if (!negative) {
      position = getPositiveItemLabelPosition(row, column);
    }
    else {
      position = getNegativeItemLabelPosition(row, column);
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
  


















  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BarRenderer)) {
      return false;
    }
    BarRenderer that = (BarRenderer)obj;
    if (base != base) {
      return false;
    }
    if (itemMargin != itemMargin) {
      return false;
    }
    if (drawBarOutline != drawBarOutline) {
      return false;
    }
    if (maximumBarWidth != maximumBarWidth) {
      return false;
    }
    if (minimumBarLength != minimumBarLength) {
      return false;
    }
    if (!ObjectUtilities.equal(gradientPaintTransformer, gradientPaintTransformer))
    {
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
    if (!PaintUtilities.equal(shadowPaint, shadowPaint)) {
      return false;
    }
    if (shadowXOffset != shadowXOffset) {
      return false;
    }
    if (shadowYOffset != shadowYOffset) {
      return false;
    }
    return super.equals(obj);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(shadowPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    shadowPaint = SerialUtilities.readPaint(stream);
  }
}
