package org.jfree.chart.renderer.category;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategorySeriesLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryCrosshairState;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.GradientPaintTransformer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ObjectList;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;





































































































































public abstract class AbstractCategoryItemRenderer
  extends AbstractRenderer
  implements CategoryItemRenderer, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 1247553218442497391L;
  private CategoryPlot plot;
  private ObjectList itemLabelGeneratorList;
  private CategoryItemLabelGenerator baseItemLabelGenerator;
  private ObjectList toolTipGeneratorList;
  private CategoryToolTipGenerator baseToolTipGenerator;
  private ObjectList itemURLGeneratorList;
  private CategoryURLGenerator baseItemURLGenerator;
  private CategorySeriesLabelGenerator legendItemLabelGenerator;
  private CategorySeriesLabelGenerator legendItemToolTipGenerator;
  private CategorySeriesLabelGenerator legendItemURLGenerator;
  private transient int rowCount;
  private transient int columnCount;
  /**
   * @deprecated
   */
  private CategoryItemLabelGenerator itemLabelGenerator;
  /**
   * @deprecated
   */
  private CategoryToolTipGenerator toolTipGenerator;
  /**
   * @deprecated
   */
  private CategoryURLGenerator itemURLGenerator;
  
  protected AbstractCategoryItemRenderer()
  {
    itemLabelGenerator = null;
    itemLabelGeneratorList = new ObjectList();
    toolTipGenerator = null;
    toolTipGeneratorList = new ObjectList();
    itemURLGenerator = null;
    itemURLGeneratorList = new ObjectList();
    legendItemLabelGenerator = new StandardCategorySeriesLabelGenerator();
  }
  







  public int getPassCount()
  {
    return 1;
  }
  








  public CategoryPlot getPlot()
  {
    return plot;
  }
  








  public void setPlot(CategoryPlot plot)
  {
    if (plot == null) {
      throw new IllegalArgumentException("Null 'plot' argument.");
    }
    this.plot = plot;
  }
  













  public CategoryItemLabelGenerator getItemLabelGenerator(int row, int column)
  {
    return getSeriesItemLabelGenerator(row);
  }
  










  public CategoryItemLabelGenerator getSeriesItemLabelGenerator(int series)
  {
    if (itemLabelGenerator != null) {
      return itemLabelGenerator;
    }
    

    CategoryItemLabelGenerator generator = (CategoryItemLabelGenerator)itemLabelGeneratorList.get(series);
    
    if (generator == null) {
      generator = baseItemLabelGenerator;
    }
    return generator;
  }
  










  public void setSeriesItemLabelGenerator(int series, CategoryItemLabelGenerator generator)
  {
    itemLabelGeneratorList.set(series, generator);
    fireChangeEvent();
  }
  






  public CategoryItemLabelGenerator getBaseItemLabelGenerator()
  {
    return baseItemLabelGenerator;
  }
  








  public void setBaseItemLabelGenerator(CategoryItemLabelGenerator generator)
  {
    baseItemLabelGenerator = generator;
    fireChangeEvent();
  }
  














  public CategoryToolTipGenerator getToolTipGenerator(int row, int column)
  {
    CategoryToolTipGenerator result = null;
    if (toolTipGenerator != null) {
      result = toolTipGenerator;
    }
    else {
      result = getSeriesToolTipGenerator(row);
      if (result == null) {
        result = baseToolTipGenerator;
      }
    }
    return result;
  }
  









  public CategoryToolTipGenerator getSeriesToolTipGenerator(int series)
  {
    return (CategoryToolTipGenerator)toolTipGeneratorList.get(series);
  }
  









  public void setSeriesToolTipGenerator(int series, CategoryToolTipGenerator generator)
  {
    toolTipGeneratorList.set(series, generator);
    fireChangeEvent();
  }
  






  public CategoryToolTipGenerator getBaseToolTipGenerator()
  {
    return baseToolTipGenerator;
  }
  







  public void setBaseToolTipGenerator(CategoryToolTipGenerator generator)
  {
    baseToolTipGenerator = generator;
    fireChangeEvent();
  }
  











  public CategoryURLGenerator getItemURLGenerator(int row, int column)
  {
    return getSeriesItemURLGenerator(row);
  }
  










  public CategoryURLGenerator getSeriesItemURLGenerator(int series)
  {
    if (itemURLGenerator != null) {
      return itemURLGenerator;
    }
    

    CategoryURLGenerator generator = (CategoryURLGenerator)itemURLGeneratorList.get(series);
    
    if (generator == null) {
      generator = baseItemURLGenerator;
    }
    return generator;
  }
  










  public void setSeriesItemURLGenerator(int series, CategoryURLGenerator generator)
  {
    itemURLGeneratorList.set(series, generator);
    fireChangeEvent();
  }
  






  public CategoryURLGenerator getBaseItemURLGenerator()
  {
    return baseItemURLGenerator;
  }
  







  public void setBaseItemURLGenerator(CategoryURLGenerator generator)
  {
    baseItemURLGenerator = generator;
    fireChangeEvent();
  }
  





  public int getRowCount()
  {
    return rowCount;
  }
  





  public int getColumnCount()
  {
    return columnCount;
  }
  











  protected CategoryItemRendererState createState(PlotRenderingInfo info)
  {
    return new CategoryItemRendererState(info);
  }
  



















  public CategoryItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, CategoryPlot plot, int rendererIndex, PlotRenderingInfo info)
  {
    setPlot(plot);
    CategoryDataset data = plot.getDataset(rendererIndex);
    if (data != null) {
      rowCount = data.getRowCount();
      columnCount = data.getColumnCount();
    }
    else {
      rowCount = 0;
      columnCount = 0;
    }
    CategoryItemRendererState state = createState(info);
    int[] visibleSeriesTemp = new int[rowCount];
    int visibleSeriesCount = 0;
    for (int row = 0; row < rowCount; row++) {
      if (isSeriesVisible(row)) {
        visibleSeriesTemp[visibleSeriesCount] = row;
        visibleSeriesCount++;
      }
    }
    int[] visibleSeries = new int[visibleSeriesCount];
    System.arraycopy(visibleSeriesTemp, 0, visibleSeries, 0, visibleSeriesCount);
    
    state.setVisibleSeriesArray(visibleSeries);
    return state;
  }
  








  public Range findRangeBounds(CategoryDataset dataset)
  {
    return findRangeBounds(dataset, false);
  }
  












  protected Range findRangeBounds(CategoryDataset dataset, boolean includeInterval)
  {
    if (dataset == null) {
      return null;
    }
    if (getDataBoundsIncludesVisibleSeriesOnly()) {
      List visibleSeriesKeys = new ArrayList();
      int seriesCount = dataset.getRowCount();
      for (int s = 0; s < seriesCount; s++) {
        if (isSeriesVisible(s)) {
          visibleSeriesKeys.add(dataset.getRowKey(s));
        }
      }
      return DatasetUtilities.findRangeBounds(dataset, visibleSeriesKeys, includeInterval);
    }
    

    return DatasetUtilities.findRangeBounds(dataset, includeInterval);
  }
  
















  public double getItemMiddle(Comparable rowKey, Comparable columnKey, CategoryDataset dataset, CategoryAxis axis, Rectangle2D area, RectangleEdge edge)
  {
    return axis.getCategoryMiddle(columnKey, dataset.getColumnKeys(), area, edge);
  }
  












  public void drawBackground(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea)
  {
    plot.drawBackground(g2, dataArea);
  }
  












  public void drawOutline(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea)
  {
    plot.drawOutline(g2, dataArea);
  }
  




















  public void drawDomainGridline(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea, double value)
  {
    Line2D line = null;
    PlotOrientation orientation = plot.getOrientation();
    
    if (orientation == PlotOrientation.HORIZONTAL) {
      line = new Line2D.Double(dataArea.getMinX(), value, dataArea.getMaxX(), value);

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      line = new Line2D.Double(value, dataArea.getMinY(), value, dataArea.getMaxY());
    }
    

    Paint paint = plot.getDomainGridlinePaint();
    if (paint == null) {
      paint = CategoryPlot.DEFAULT_GRIDLINE_PAINT;
    }
    g2.setPaint(paint);
    
    Stroke stroke = plot.getDomainGridlineStroke();
    if (stroke == null) {
      stroke = CategoryPlot.DEFAULT_GRIDLINE_STROKE;
    }
    g2.setStroke(stroke);
    
    g2.draw(line);
  }
  

















  public void drawRangeGridline(Graphics2D g2, CategoryPlot plot, ValueAxis axis, Rectangle2D dataArea, double value)
  {
    Range range = axis.getRange();
    if (!range.contains(value)) {
      return;
    }
    
    PlotOrientation orientation = plot.getOrientation();
    double v = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
    Line2D line = null;
    if (orientation == PlotOrientation.HORIZONTAL) {
      line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
    }
    

    Paint paint = plot.getRangeGridlinePaint();
    if (paint == null) {
      paint = CategoryPlot.DEFAULT_GRIDLINE_PAINT;
    }
    g2.setPaint(paint);
    
    Stroke stroke = plot.getRangeGridlineStroke();
    if (stroke == null) {
      stroke = CategoryPlot.DEFAULT_GRIDLINE_STROKE;
    }
    g2.setStroke(stroke);
    
    g2.draw(line);
  }
  




















  public void drawRangeLine(Graphics2D g2, CategoryPlot plot, ValueAxis axis, Rectangle2D dataArea, double value, Paint paint, Stroke stroke)
  {
    Range range = axis.getRange();
    if (!range.contains(value)) {
      return;
    }
    
    PlotOrientation orientation = plot.getOrientation();
    Line2D line = null;
    double v = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
    if (orientation == PlotOrientation.HORIZONTAL) {
      line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
    }
    

    g2.setPaint(paint);
    g2.setStroke(stroke);
    g2.draw(line);
  }
  

















  public void drawDomainMarker(Graphics2D g2, CategoryPlot plot, CategoryAxis axis, CategoryMarker marker, Rectangle2D dataArea)
  {
    Comparable category = marker.getKey();
    CategoryDataset dataset = plot.getDataset(plot.getIndexOf(this));
    int columnIndex = dataset.getColumnIndex(category);
    if (columnIndex < 0) {
      return;
    }
    
    Composite savedComposite = g2.getComposite();
    g2.setComposite(AlphaComposite.getInstance(3, marker.getAlpha()));
    

    PlotOrientation orientation = plot.getOrientation();
    Rectangle2D bounds = null;
    if (marker.getDrawAsLine()) {
      double v = axis.getCategoryMiddle(columnIndex, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
      

      Line2D line = null;
      if (orientation == PlotOrientation.HORIZONTAL) {
        line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);

      }
      else if (orientation == PlotOrientation.VERTICAL) {
        line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
      }
      
      g2.setPaint(marker.getPaint());
      g2.setStroke(marker.getStroke());
      g2.draw(line);
      bounds = line.getBounds2D();
    }
    else {
      double v0 = axis.getCategoryStart(columnIndex, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
      

      double v1 = axis.getCategoryEnd(columnIndex, dataset.getColumnCount(), dataArea, plot.getDomainAxisEdge());
      

      Rectangle2D area = null;
      if (orientation == PlotOrientation.HORIZONTAL) {
        area = new Rectangle2D.Double(dataArea.getMinX(), v0, dataArea.getWidth(), v1 - v0);

      }
      else if (orientation == PlotOrientation.VERTICAL) {
        area = new Rectangle2D.Double(v0, dataArea.getMinY(), v1 - v0, dataArea.getHeight());
      }
      
      g2.setPaint(marker.getPaint());
      g2.fill(area);
      bounds = area;
    }
    
    String label = marker.getLabel();
    RectangleAnchor anchor = marker.getLabelAnchor();
    if (label != null) {
      Font labelFont = marker.getLabelFont();
      g2.setFont(labelFont);
      g2.setPaint(marker.getLabelPaint());
      Point2D coordinates = calculateDomainMarkerTextAnchorPoint(g2, orientation, dataArea, bounds, marker.getLabelOffset(), marker.getLabelOffsetType(), anchor);
      

      TextUtilities.drawAlignedString(label, g2, (float)coordinates.getX(), (float)coordinates.getY(), marker.getLabelTextAnchor());
    }
    

    g2.setComposite(savedComposite);
  }
  
















  public void drawRangeMarker(Graphics2D g2, CategoryPlot plot, ValueAxis axis, Marker marker, Rectangle2D dataArea)
  {
    if ((marker instanceof ValueMarker)) {
      ValueMarker vm = (ValueMarker)marker;
      double value = vm.getValue();
      Range range = axis.getRange();
      
      if (!range.contains(value)) {
        return;
      }
      
      Composite savedComposite = g2.getComposite();
      g2.setComposite(AlphaComposite.getInstance(3, marker.getAlpha()));
      

      PlotOrientation orientation = plot.getOrientation();
      double v = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
      
      Line2D line = null;
      if (orientation == PlotOrientation.HORIZONTAL) {
        line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());

      }
      else if (orientation == PlotOrientation.VERTICAL) {
        line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
      }
      

      g2.setPaint(marker.getPaint());
      g2.setStroke(marker.getStroke());
      g2.draw(line);
      
      String label = marker.getLabel();
      RectangleAnchor anchor = marker.getLabelAnchor();
      if (label != null) {
        Font labelFont = marker.getLabelFont();
        g2.setFont(labelFont);
        g2.setPaint(marker.getLabelPaint());
        Point2D coordinates = calculateRangeMarkerTextAnchorPoint(g2, orientation, dataArea, line.getBounds2D(), marker.getLabelOffset(), LengthAdjustmentType.EXPAND, anchor);
        


        TextUtilities.drawAlignedString(label, g2, (float)coordinates.getX(), (float)coordinates.getY(), marker.getLabelTextAnchor());
      }
      

      g2.setComposite(savedComposite);
    }
    else if ((marker instanceof IntervalMarker)) {
      IntervalMarker im = (IntervalMarker)marker;
      double start = im.getStartValue();
      double end = im.getEndValue();
      Range range = axis.getRange();
      if (!range.intersects(start, end)) {
        return;
      }
      
      Composite savedComposite = g2.getComposite();
      g2.setComposite(AlphaComposite.getInstance(3, marker.getAlpha()));
      

      double start2d = axis.valueToJava2D(start, dataArea, plot.getRangeAxisEdge());
      
      double end2d = axis.valueToJava2D(end, dataArea, plot.getRangeAxisEdge());
      
      double low = Math.min(start2d, end2d);
      double high = Math.max(start2d, end2d);
      
      PlotOrientation orientation = plot.getOrientation();
      Rectangle2D rect = null;
      if (orientation == PlotOrientation.HORIZONTAL)
      {
        low = Math.max(low, dataArea.getMinX());
        high = Math.min(high, dataArea.getMaxX());
        rect = new Rectangle2D.Double(low, dataArea.getMinY(), high - low, dataArea.getHeight());


      }
      else if (orientation == PlotOrientation.VERTICAL)
      {
        low = Math.max(low, dataArea.getMinY());
        high = Math.min(high, dataArea.getMaxY());
        rect = new Rectangle2D.Double(dataArea.getMinX(), low, dataArea.getWidth(), high - low);
      }
      

      Paint p = marker.getPaint();
      if ((p instanceof GradientPaint)) {
        GradientPaint gp = (GradientPaint)p;
        GradientPaintTransformer t = im.getGradientPaintTransformer();
        if (t != null) {
          gp = t.transform(gp, rect);
        }
        g2.setPaint(gp);
      }
      else {
        g2.setPaint(p);
      }
      g2.fill(rect);
      

      if ((im.getOutlinePaint() != null) && (im.getOutlineStroke() != null)) {
        if (orientation == PlotOrientation.VERTICAL) {
          Line2D line = new Line2D.Double();
          double x0 = dataArea.getMinX();
          double x1 = dataArea.getMaxX();
          g2.setPaint(im.getOutlinePaint());
          g2.setStroke(im.getOutlineStroke());
          if (range.contains(start)) {
            line.setLine(x0, start2d, x1, start2d);
            g2.draw(line);
          }
          if (range.contains(end)) {
            line.setLine(x0, end2d, x1, end2d);
            g2.draw(line);
          }
        }
        else {
          Line2D line = new Line2D.Double();
          double y0 = dataArea.getMinY();
          double y1 = dataArea.getMaxY();
          g2.setPaint(im.getOutlinePaint());
          g2.setStroke(im.getOutlineStroke());
          if (range.contains(start)) {
            line.setLine(start2d, y0, start2d, y1);
            g2.draw(line);
          }
          if (range.contains(end)) {
            line.setLine(end2d, y0, end2d, y1);
            g2.draw(line);
          }
        }
      }
      
      String label = marker.getLabel();
      RectangleAnchor anchor = marker.getLabelAnchor();
      if (label != null) {
        Font labelFont = marker.getLabelFont();
        g2.setFont(labelFont);
        g2.setPaint(marker.getLabelPaint());
        Point2D coordinates = calculateRangeMarkerTextAnchorPoint(g2, orientation, dataArea, rect, marker.getLabelOffset(), marker.getLabelOffsetType(), anchor);
        


        TextUtilities.drawAlignedString(label, g2, (float)coordinates.getX(), (float)coordinates.getY(), marker.getLabelTextAnchor());
      }
      

      g2.setComposite(savedComposite);
    }
  }
  




















  protected Point2D calculateDomainMarkerTextAnchorPoint(Graphics2D g2, PlotOrientation orientation, Rectangle2D dataArea, Rectangle2D markerArea, RectangleInsets markerOffset, LengthAdjustmentType labelOffsetType, RectangleAnchor anchor)
  {
    Rectangle2D anchorRect = null;
    if (orientation == PlotOrientation.HORIZONTAL) {
      anchorRect = markerOffset.createAdjustedRectangle(markerArea, LengthAdjustmentType.CONTRACT, labelOffsetType);

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      anchorRect = markerOffset.createAdjustedRectangle(markerArea, labelOffsetType, LengthAdjustmentType.CONTRACT);
    }
    
    return RectangleAnchor.coordinates(anchorRect, anchor);
  }
  




















  protected Point2D calculateRangeMarkerTextAnchorPoint(Graphics2D g2, PlotOrientation orientation, Rectangle2D dataArea, Rectangle2D markerArea, RectangleInsets markerOffset, LengthAdjustmentType labelOffsetType, RectangleAnchor anchor)
  {
    Rectangle2D anchorRect = null;
    if (orientation == PlotOrientation.HORIZONTAL) {
      anchorRect = markerOffset.createAdjustedRectangle(markerArea, labelOffsetType, LengthAdjustmentType.CONTRACT);

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      anchorRect = markerOffset.createAdjustedRectangle(markerArea, LengthAdjustmentType.CONTRACT, labelOffsetType);
    }
    
    return RectangleAnchor.coordinates(anchorRect, anchor);
  }
  













  public LegendItem getLegendItem(int datasetIndex, int series)
  {
    CategoryPlot p = getPlot();
    if (p == null) {
      return null;
    }
    

    if ((!isSeriesVisible(series)) || (!isSeriesVisibleInLegend(series))) {
      return null;
    }
    
    CategoryDataset dataset = p.getDataset(datasetIndex);
    String label = legendItemLabelGenerator.generateLabel(dataset, series);
    
    String description = label;
    String toolTipText = null;
    if (legendItemToolTipGenerator != null) {
      toolTipText = legendItemToolTipGenerator.generateLabel(dataset, series);
    }
    
    String urlText = null;
    if (legendItemURLGenerator != null) {
      urlText = legendItemURLGenerator.generateLabel(dataset, series);
    }
    
    Shape shape = lookupLegendShape(series);
    Paint paint = lookupSeriesPaint(series);
    Paint outlinePaint = lookupSeriesOutlinePaint(series);
    Stroke outlineStroke = lookupSeriesOutlineStroke(series);
    
    LegendItem item = new LegendItem(label, description, toolTipText, urlText, shape, paint, outlineStroke, outlinePaint);
    
    item.setLabelFont(lookupLegendTextFont(series));
    Paint labelPaint = lookupLegendTextPaint(series);
    if (labelPaint != null) {
      item.setLabelPaint(labelPaint);
    }
    item.setSeriesKey(dataset.getRowKey(series));
    item.setSeriesIndex(series);
    item.setDataset(dataset);
    item.setDatasetIndex(datasetIndex);
    return item;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AbstractCategoryItemRenderer)) {
      return false;
    }
    AbstractCategoryItemRenderer that = (AbstractCategoryItemRenderer)obj;
    
    if (!ObjectUtilities.equal(itemLabelGenerator, itemLabelGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(itemLabelGeneratorList, itemLabelGeneratorList))
    {
      return false;
    }
    if (!ObjectUtilities.equal(baseItemLabelGenerator, baseItemLabelGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(toolTipGenerator, toolTipGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(toolTipGeneratorList, toolTipGeneratorList))
    {
      return false;
    }
    if (!ObjectUtilities.equal(baseToolTipGenerator, baseToolTipGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(itemURLGenerator, itemURLGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(itemURLGeneratorList, itemURLGeneratorList))
    {
      return false;
    }
    if (!ObjectUtilities.equal(baseItemURLGenerator, baseItemURLGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(legendItemLabelGenerator, legendItemLabelGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(legendItemToolTipGenerator, legendItemToolTipGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(legendItemURLGenerator, legendItemURLGenerator))
    {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = super.hashCode();
    return result;
  }
  




  public DrawingSupplier getDrawingSupplier()
  {
    DrawingSupplier result = null;
    CategoryPlot cp = getPlot();
    if (cp != null) {
      result = cp.getDrawingSupplier();
    }
    return result;
  }
  





















  protected void updateCrosshairValues(CategoryCrosshairState crosshairState, Comparable rowKey, Comparable columnKey, double value, int datasetIndex, double transX, double transY, PlotOrientation orientation)
  {
    if (orientation == null) {
      throw new IllegalArgumentException("Null 'orientation' argument.");
    }
    
    if (crosshairState != null) {
      if (plot.isRangeCrosshairLockedOnData())
      {
        crosshairState.updateCrosshairPoint(rowKey, columnKey, value, datasetIndex, transX, transY, orientation);
      }
      else
      {
        crosshairState.updateCrosshairX(rowKey, columnKey, datasetIndex, transX, orientation);
      }
    }
  }
  
















  protected void drawItemLabel(Graphics2D g2, PlotOrientation orientation, CategoryDataset dataset, int row, int column, double x, double y, boolean negative)
  {
    CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
    
    if (generator != null) {
      Font labelFont = getItemLabelFont(row, column);
      Paint paint = getItemLabelPaint(row, column);
      g2.setFont(labelFont);
      g2.setPaint(paint);
      String label = generator.generateLabel(dataset, row, column);
      ItemLabelPosition position = null;
      if (!negative) {
        position = getPositiveItemLabelPosition(row, column);
      }
      else {
        position = getNegativeItemLabelPosition(row, column);
      }
      Point2D anchorPoint = calculateLabelAnchorPoint(position.getItemLabelAnchor(), x, y, orientation);
      
      TextUtilities.drawRotatedString(label, g2, (float)anchorPoint.getX(), (float)anchorPoint.getY(), position.getTextAnchor(), position.getAngle(), position.getRotationAnchor());
    }
  }
  













  public Object clone()
    throws CloneNotSupportedException
  {
    AbstractCategoryItemRenderer clone = (AbstractCategoryItemRenderer)super.clone();
    

    if (itemLabelGenerator != null) {
      if ((itemLabelGenerator instanceof PublicCloneable)) {
        PublicCloneable pc = (PublicCloneable)itemLabelGenerator;
        itemLabelGenerator = ((CategoryItemLabelGenerator)pc.clone());
      }
      else
      {
        throw new CloneNotSupportedException("ItemLabelGenerator not cloneable.");
      }
    }
    

    if (itemLabelGeneratorList != null) {
      itemLabelGeneratorList = ((ObjectList)itemLabelGeneratorList.clone());
    }
    

    if (baseItemLabelGenerator != null) {
      if ((baseItemLabelGenerator instanceof PublicCloneable)) {
        PublicCloneable pc = (PublicCloneable)baseItemLabelGenerator;
        
        baseItemLabelGenerator = ((CategoryItemLabelGenerator)pc.clone());
      }
      else
      {
        throw new CloneNotSupportedException("ItemLabelGenerator not cloneable.");
      }
    }
    

    if (toolTipGenerator != null) {
      if ((toolTipGenerator instanceof PublicCloneable)) {
        PublicCloneable pc = (PublicCloneable)toolTipGenerator;
        toolTipGenerator = ((CategoryToolTipGenerator)pc.clone());
      }
      else {
        throw new CloneNotSupportedException("Tool tip generator not cloneable.");
      }
    }
    

    if (toolTipGeneratorList != null) {
      toolTipGeneratorList = ((ObjectList)toolTipGeneratorList.clone());
    }
    

    if (baseToolTipGenerator != null) {
      if ((baseToolTipGenerator instanceof PublicCloneable)) {
        PublicCloneable pc = (PublicCloneable)baseToolTipGenerator;
        
        baseToolTipGenerator = ((CategoryToolTipGenerator)pc.clone());
      }
      else
      {
        throw new CloneNotSupportedException("Base tool tip generator not cloneable.");
      }
    }
    

    if (itemURLGenerator != null) {
      if ((itemURLGenerator instanceof PublicCloneable)) {
        PublicCloneable pc = (PublicCloneable)itemURLGenerator;
        itemURLGenerator = ((CategoryURLGenerator)pc.clone());
      }
      else {
        throw new CloneNotSupportedException("Item URL generator not cloneable.");
      }
    }
    

    if (itemURLGeneratorList != null) {
      itemURLGeneratorList = ((ObjectList)itemURLGeneratorList.clone());
    }
    

    if (baseItemURLGenerator != null) {
      if ((baseItemURLGenerator instanceof PublicCloneable)) {
        PublicCloneable pc = (PublicCloneable)baseItemURLGenerator;
        
        baseItemURLGenerator = ((CategoryURLGenerator)pc.clone());
      }
      else {
        throw new CloneNotSupportedException("Base item URL generator not cloneable.");
      }
    }
    

    if ((legendItemLabelGenerator instanceof PublicCloneable)) {
      legendItemLabelGenerator = ((CategorySeriesLabelGenerator)ObjectUtilities.clone(legendItemLabelGenerator));
    }
    
    if ((legendItemToolTipGenerator instanceof PublicCloneable)) {
      legendItemToolTipGenerator = ((CategorySeriesLabelGenerator)ObjectUtilities.clone(legendItemToolTipGenerator));
    }
    
    if ((legendItemURLGenerator instanceof PublicCloneable)) {
      legendItemURLGenerator = ((CategorySeriesLabelGenerator)ObjectUtilities.clone(legendItemURLGenerator));
    }
    
    return clone;
  }
  







  protected CategoryAxis getDomainAxis(CategoryPlot plot, int index)
  {
    CategoryAxis result = plot.getDomainAxis(index);
    if (result == null) {
      result = plot.getDomainAxis();
    }
    return result;
  }
  







  protected ValueAxis getRangeAxis(CategoryPlot plot, int index)
  {
    ValueAxis result = plot.getRangeAxis(index);
    if (result == null) {
      result = plot.getRangeAxis();
    }
    return result;
  }
  







  public LegendItemCollection getLegendItems()
  {
    if (plot == null) {
      return new LegendItemCollection();
    }
    LegendItemCollection result = new LegendItemCollection();
    int index = plot.getIndexOf(this);
    CategoryDataset dataset = plot.getDataset(index);
    if (dataset != null) {
      int seriesCount = dataset.getRowCount();
      for (int i = 0; i < seriesCount; i++) {
        if (isSeriesVisibleInLegend(i)) {
          LegendItem item = getLegendItem(index, i);
          if (item != null) {
            result.add(item);
          }
        }
      }
    }
    
    return result;
  }
  






  public CategorySeriesLabelGenerator getLegendItemLabelGenerator()
  {
    return legendItemLabelGenerator;
  }
  








  public void setLegendItemLabelGenerator(CategorySeriesLabelGenerator generator)
  {
    if (generator == null) {
      throw new IllegalArgumentException("Null 'generator' argument.");
    }
    legendItemLabelGenerator = generator;
    fireChangeEvent();
  }
  






  public CategorySeriesLabelGenerator getLegendItemToolTipGenerator()
  {
    return legendItemToolTipGenerator;
  }
  








  public void setLegendItemToolTipGenerator(CategorySeriesLabelGenerator generator)
  {
    legendItemToolTipGenerator = generator;
    fireChangeEvent();
  }
  






  public CategorySeriesLabelGenerator getLegendItemURLGenerator()
  {
    return legendItemURLGenerator;
  }
  








  public void setLegendItemURLGenerator(CategorySeriesLabelGenerator generator)
  {
    legendItemURLGenerator = generator;
    fireChangeEvent();
  }
  










  protected void addItemEntity(EntityCollection entities, CategoryDataset dataset, int row, int column, Shape hotspot)
  {
    if (hotspot == null) {
      throw new IllegalArgumentException("Null 'hotspot' argument.");
    }
    if (!getItemCreateEntity(row, column)) {
      return;
    }
    String tip = null;
    CategoryToolTipGenerator tipster = getToolTipGenerator(row, column);
    if (tipster != null) {
      tip = tipster.generateToolTip(dataset, row, column);
    }
    String url = null;
    CategoryURLGenerator urlster = getItemURLGenerator(row, column);
    if (urlster != null) {
      url = urlster.generateURL(dataset, row, column);
    }
    CategoryItemEntity entity = new CategoryItemEntity(hotspot, tip, url, dataset, dataset.getRowKey(row), dataset.getColumnKey(column));
    
    entities.add(entity);
  }
  

















  protected void addEntity(EntityCollection entities, Shape hotspot, CategoryDataset dataset, int row, int column, double entityX, double entityY)
  {
    if (!getItemCreateEntity(row, column)) {
      return;
    }
    Shape s = hotspot;
    if (hotspot == null) {
      double r = getDefaultEntityRadius();
      double w = r * 2.0D;
      if (getPlot().getOrientation() == PlotOrientation.VERTICAL) {
        s = new Ellipse2D.Double(entityX - r, entityY - r, w, w);
      }
      else {
        s = new Ellipse2D.Double(entityY - r, entityX - r, w, w);
      }
    }
    String tip = null;
    CategoryToolTipGenerator generator = getToolTipGenerator(row, column);
    if (generator != null) {
      tip = generator.generateToolTip(dataset, row, column);
    }
    String url = null;
    CategoryURLGenerator urlster = getItemURLGenerator(row, column);
    if (urlster != null) {
      url = urlster.generateURL(dataset, row, column);
    }
    CategoryItemEntity entity = new CategoryItemEntity(s, tip, url, dataset, dataset.getRowKey(row), dataset.getColumnKey(column));
    
    entities.add(entity);
  }
  






























  /**
   * @deprecated
   */
  public void setItemLabelGenerator(CategoryItemLabelGenerator generator)
  {
    itemLabelGenerator = generator;
    fireChangeEvent();
  }
  








  /**
   * @deprecated
   */
  public CategoryToolTipGenerator getToolTipGenerator()
  {
    return toolTipGenerator;
  }
  










  /**
   * @deprecated
   */
  public void setToolTipGenerator(CategoryToolTipGenerator generator)
  {
    toolTipGenerator = generator;
    fireChangeEvent();
  }
  







  /**
   * @deprecated
   */
  public void setItemURLGenerator(CategoryURLGenerator generator)
  {
    itemURLGenerator = generator;
    fireChangeEvent();
  }
}
