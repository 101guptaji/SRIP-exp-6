package org.jfree.chart.renderer.xy;

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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYSeriesLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.text.TextUtilities;
import org.jfree.ui.GradientPaintTransformer;
import org.jfree.ui.Layer;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ObjectList;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;















































































































































public abstract class AbstractXYItemRenderer
  extends AbstractRenderer
  implements XYItemRenderer, Cloneable, Serializable
{
  private static final long serialVersionUID = 8019124836026607990L;
  private XYPlot plot;
  private ObjectList itemLabelGeneratorList;
  private XYItemLabelGenerator baseItemLabelGenerator;
  private ObjectList toolTipGeneratorList;
  private XYToolTipGenerator baseToolTipGenerator;
  private XYURLGenerator urlGenerator;
  private List backgroundAnnotations;
  private List foregroundAnnotations;
  private XYSeriesLabelGenerator legendItemLabelGenerator;
  private XYSeriesLabelGenerator legendItemToolTipGenerator;
  private XYSeriesLabelGenerator legendItemURLGenerator;
  /**
   * @deprecated
   */
  private XYItemLabelGenerator itemLabelGenerator;
  /**
   * @deprecated
   */
  private XYToolTipGenerator toolTipGenerator;
  
  protected AbstractXYItemRenderer()
  {
    itemLabelGenerator = null;
    itemLabelGeneratorList = new ObjectList();
    toolTipGenerator = null;
    toolTipGeneratorList = new ObjectList();
    urlGenerator = null;
    backgroundAnnotations = new ArrayList();
    foregroundAnnotations = new ArrayList();
    legendItemLabelGenerator = new StandardXYSeriesLabelGenerator("{0}");
  }
  







  public int getPassCount()
  {
    return 1;
  }
  




  public XYPlot getPlot()
  {
    return plot;
  }
  




  public void setPlot(XYPlot plot)
  {
    this.plot = plot;
  }
  





















  public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info)
  {
    XYItemRendererState state = new XYItemRendererState(info);
    return state;
  }
  














  public XYItemLabelGenerator getItemLabelGenerator(int series, int item)
  {
    if (itemLabelGenerator != null) {
      return itemLabelGenerator;
    }
    

    XYItemLabelGenerator generator = (XYItemLabelGenerator)itemLabelGeneratorList.get(series);
    
    if (generator == null) {
      generator = baseItemLabelGenerator;
    }
    return generator;
  }
  






  public XYItemLabelGenerator getSeriesItemLabelGenerator(int series)
  {
    return (XYItemLabelGenerator)itemLabelGeneratorList.get(series);
  }
  







  public void setSeriesItemLabelGenerator(int series, XYItemLabelGenerator generator)
  {
    itemLabelGeneratorList.set(series, generator);
    fireChangeEvent();
  }
  




  public XYItemLabelGenerator getBaseItemLabelGenerator()
  {
    return baseItemLabelGenerator;
  }
  





  public void setBaseItemLabelGenerator(XYItemLabelGenerator generator)
  {
    baseItemLabelGenerator = generator;
    fireChangeEvent();
  }
  












  public XYToolTipGenerator getToolTipGenerator(int series, int item)
  {
    if (toolTipGenerator != null) {
      return toolTipGenerator;
    }
    

    XYToolTipGenerator generator = (XYToolTipGenerator)toolTipGeneratorList.get(series);
    
    if (generator == null) {
      generator = baseToolTipGenerator;
    }
    return generator;
  }
  






  public XYToolTipGenerator getSeriesToolTipGenerator(int series)
  {
    return (XYToolTipGenerator)toolTipGeneratorList.get(series);
  }
  







  public void setSeriesToolTipGenerator(int series, XYToolTipGenerator generator)
  {
    toolTipGeneratorList.set(series, generator);
    fireChangeEvent();
  }
  






  public XYToolTipGenerator getBaseToolTipGenerator()
  {
    return baseToolTipGenerator;
  }
  







  public void setBaseToolTipGenerator(XYToolTipGenerator generator)
  {
    baseToolTipGenerator = generator;
    fireChangeEvent();
  }
  






  public XYURLGenerator getURLGenerator()
  {
    return urlGenerator;
  }
  





  public void setURLGenerator(XYURLGenerator urlGenerator)
  {
    this.urlGenerator = urlGenerator;
    fireChangeEvent();
  }
  







  public void addAnnotation(XYAnnotation annotation)
  {
    addAnnotation(annotation, Layer.FOREGROUND);
  }
  






  public void addAnnotation(XYAnnotation annotation, Layer layer)
  {
    if (annotation == null) {
      throw new IllegalArgumentException("Null 'annotation' argument.");
    }
    if (layer.equals(Layer.FOREGROUND)) {
      foregroundAnnotations.add(annotation);
      fireChangeEvent();
    }
    else if (layer.equals(Layer.BACKGROUND)) {
      backgroundAnnotations.add(annotation);
      fireChangeEvent();
    }
    else
    {
      throw new RuntimeException("Unknown layer.");
    }
  }
  








  public boolean removeAnnotation(XYAnnotation annotation)
  {
    boolean removed = foregroundAnnotations.remove(annotation);
    removed &= backgroundAnnotations.remove(annotation);
    fireChangeEvent();
    return removed;
  }
  



  public void removeAnnotations()
  {
    foregroundAnnotations.clear();
    backgroundAnnotations.clear();
    fireChangeEvent();
  }
  








  public Collection getAnnotations()
  {
    List result = new ArrayList(foregroundAnnotations);
    result.addAll(backgroundAnnotations);
    return result;
  }
  






  public XYSeriesLabelGenerator getLegendItemLabelGenerator()
  {
    return legendItemLabelGenerator;
  }
  







  public void setLegendItemLabelGenerator(XYSeriesLabelGenerator generator)
  {
    if (generator == null) {
      throw new IllegalArgumentException("Null 'generator' argument.");
    }
    legendItemLabelGenerator = generator;
    fireChangeEvent();
  }
  






  public XYSeriesLabelGenerator getLegendItemToolTipGenerator()
  {
    return legendItemToolTipGenerator;
  }
  








  public void setLegendItemToolTipGenerator(XYSeriesLabelGenerator generator)
  {
    legendItemToolTipGenerator = generator;
    fireChangeEvent();
  }
  






  public XYSeriesLabelGenerator getLegendItemURLGenerator()
  {
    return legendItemURLGenerator;
  }
  







  public void setLegendItemURLGenerator(XYSeriesLabelGenerator generator)
  {
    legendItemURLGenerator = generator;
    fireChangeEvent();
  }
  










  public Range findDomainBounds(XYDataset dataset)
  {
    return findDomainBounds(dataset, false);
  }
  











  protected Range findDomainBounds(XYDataset dataset, boolean includeInterval)
  {
    if (dataset == null) {
      return null;
    }
    if (getDataBoundsIncludesVisibleSeriesOnly()) {
      List visibleSeriesKeys = new ArrayList();
      int seriesCount = dataset.getSeriesCount();
      for (int s = 0; s < seriesCount; s++) {
        if (isSeriesVisible(s)) {
          visibleSeriesKeys.add(dataset.getSeriesKey(s));
        }
      }
      return DatasetUtilities.findDomainBounds(dataset, visibleSeriesKeys, includeInterval);
    }
    

    return DatasetUtilities.findDomainBounds(dataset, includeInterval);
  }
  











  public Range findRangeBounds(XYDataset dataset)
  {
    return findRangeBounds(dataset, false);
  }
  











  protected Range findRangeBounds(XYDataset dataset, boolean includeInterval)
  {
    if (dataset == null) {
      return null;
    }
    if (getDataBoundsIncludesVisibleSeriesOnly()) {
      List visibleSeriesKeys = new ArrayList();
      int seriesCount = dataset.getSeriesCount();
      for (int s = 0; s < seriesCount; s++) {
        if (isSeriesVisible(s)) {
          visibleSeriesKeys.add(dataset.getSeriesKey(s));
        }
      }
      

      Range xRange = null;
      XYPlot p = getPlot();
      if (p != null) {
        ValueAxis xAxis = null;
        int index = p.getIndexOf(this);
        if (index >= 0) {
          xAxis = plot.getDomainAxisForDataset(index);
        }
        if (xAxis != null) {
          xRange = xAxis.getRange();
        }
      }
      if (xRange == null) {
        xRange = new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
      }
      
      return DatasetUtilities.findRangeBounds(dataset, visibleSeriesKeys, xRange, includeInterval);
    }
    

    return DatasetUtilities.findRangeBounds(dataset, includeInterval);
  }
  






  public LegendItemCollection getLegendItems()
  {
    if (plot == null) {
      return new LegendItemCollection();
    }
    LegendItemCollection result = new LegendItemCollection();
    int index = plot.getIndexOf(this);
    XYDataset dataset = plot.getDataset(index);
    if (dataset != null) {
      int seriesCount = dataset.getSeriesCount();
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
  








  public LegendItem getLegendItem(int datasetIndex, int series)
  {
    LegendItem result = null;
    XYPlot xyplot = getPlot();
    if (xyplot != null) {
      XYDataset dataset = xyplot.getDataset(datasetIndex);
      if (dataset != null) {
        String label = legendItemLabelGenerator.generateLabel(dataset, series);
        
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
        result = new LegendItem(label, description, toolTipText, urlText, shape, paint, outlineStroke, outlinePaint);
        
        Paint labelPaint = lookupLegendTextPaint(series);
        result.setLabelFont(lookupLegendTextFont(series));
        if (labelPaint != null) {
          result.setLabelPaint(labelPaint);
        }
        result.setSeriesKey(dataset.getSeriesKey(series));
        result.setSeriesIndex(series);
        result.setDataset(dataset);
        result.setDatasetIndex(datasetIndex);
      }
    }
    return result;
  }
  












  public void fillDomainGridBand(Graphics2D g2, XYPlot plot, ValueAxis axis, Rectangle2D dataArea, double start, double end)
  {
    double x1 = axis.valueToJava2D(start, dataArea, plot.getDomainAxisEdge());
    
    double x2 = axis.valueToJava2D(end, dataArea, plot.getDomainAxisEdge());
    Rectangle2D band;
    Rectangle2D band;
    if (plot.getOrientation() == PlotOrientation.VERTICAL) {
      band = new Rectangle2D.Double(Math.min(x1, x2), dataArea.getMinY(), Math.abs(x2 - x1), dataArea.getWidth());
    }
    else
    {
      band = new Rectangle2D.Double(dataArea.getMinX(), Math.min(x1, x2), dataArea.getWidth(), Math.abs(x2 - x1));
    }
    
    Paint paint = plot.getDomainTickBandPaint();
    
    if (paint != null) {
      g2.setPaint(paint);
      g2.fill(band);
    }
  }
  













  public void fillRangeGridBand(Graphics2D g2, XYPlot plot, ValueAxis axis, Rectangle2D dataArea, double start, double end)
  {
    double y1 = axis.valueToJava2D(start, dataArea, plot.getRangeAxisEdge());
    
    double y2 = axis.valueToJava2D(end, dataArea, plot.getRangeAxisEdge());
    Rectangle2D band;
    Rectangle2D band; if (plot.getOrientation() == PlotOrientation.VERTICAL) {
      band = new Rectangle2D.Double(dataArea.getMinX(), Math.min(y1, y2), dataArea.getWidth(), Math.abs(y2 - y1));
    }
    else
    {
      band = new Rectangle2D.Double(Math.min(y1, y2), dataArea.getMinY(), Math.abs(y2 - y1), dataArea.getHeight());
    }
    
    Paint paint = plot.getRangeTickBandPaint();
    
    if (paint != null) {
      g2.setPaint(paint);
      g2.fill(band);
    }
  }
  















  public void drawDomainGridLine(Graphics2D g2, XYPlot plot, ValueAxis axis, Rectangle2D dataArea, double value)
  {
    Range range = axis.getRange();
    if (!range.contains(value)) {
      return;
    }
    
    PlotOrientation orientation = plot.getOrientation();
    double v = axis.valueToJava2D(value, dataArea, plot.getDomainAxisEdge());
    
    Line2D line = null;
    if (orientation == PlotOrientation.HORIZONTAL) {
      line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
    }
    

    Paint paint = plot.getDomainGridlinePaint();
    Stroke stroke = plot.getDomainGridlineStroke();
    g2.setPaint(paint != null ? paint : Plot.DEFAULT_OUTLINE_PAINT);
    g2.setStroke(stroke != null ? stroke : Plot.DEFAULT_OUTLINE_STROKE);
    g2.draw(line);
  }
  
















  public void drawDomainLine(Graphics2D g2, XYPlot plot, ValueAxis axis, Rectangle2D dataArea, double value, Paint paint, Stroke stroke)
  {
    Range range = axis.getRange();
    if (!range.contains(value)) {
      return;
    }
    
    PlotOrientation orientation = plot.getOrientation();
    Line2D line = null;
    double v = axis.valueToJava2D(value, dataArea, plot.getDomainAxisEdge());
    
    if (orientation == PlotOrientation.HORIZONTAL) {
      line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
    }
    

    g2.setPaint(paint);
    g2.setStroke(stroke);
    g2.draw(line);
  }
  



















  public void drawRangeLine(Graphics2D g2, XYPlot plot, ValueAxis axis, Rectangle2D dataArea, double value, Paint paint, Stroke stroke)
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
  














  public void drawDomainMarker(Graphics2D g2, XYPlot plot, ValueAxis domainAxis, Marker marker, Rectangle2D dataArea)
  {
    if ((marker instanceof ValueMarker)) {
      ValueMarker vm = (ValueMarker)marker;
      double value = vm.getValue();
      Range range = domainAxis.getRange();
      if (!range.contains(value)) {
        return;
      }
      
      double v = domainAxis.valueToJava2D(value, dataArea, plot.getDomainAxisEdge());
      

      PlotOrientation orientation = plot.getOrientation();
      Line2D line = null;
      if (orientation == PlotOrientation.HORIZONTAL) {
        line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);

      }
      else if (orientation == PlotOrientation.VERTICAL) {
        line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());
      }
      

      Composite originalComposite = g2.getComposite();
      g2.setComposite(AlphaComposite.getInstance(3, marker.getAlpha()));
      
      g2.setPaint(marker.getPaint());
      g2.setStroke(marker.getStroke());
      g2.draw(line);
      
      String label = marker.getLabel();
      RectangleAnchor anchor = marker.getLabelAnchor();
      if (label != null) {
        Font labelFont = marker.getLabelFont();
        g2.setFont(labelFont);
        g2.setPaint(marker.getLabelPaint());
        Point2D coordinates = calculateDomainMarkerTextAnchorPoint(g2, orientation, dataArea, line.getBounds2D(), marker.getLabelOffset(), LengthAdjustmentType.EXPAND, anchor);
        


        TextUtilities.drawAlignedString(label, g2, (float)coordinates.getX(), (float)coordinates.getY(), marker.getLabelTextAnchor());
      }
      

      g2.setComposite(originalComposite);
    }
    else if ((marker instanceof IntervalMarker)) {
      IntervalMarker im = (IntervalMarker)marker;
      double start = im.getStartValue();
      double end = im.getEndValue();
      Range range = domainAxis.getRange();
      if (!range.intersects(start, end)) {
        return;
      }
      
      double start2d = domainAxis.valueToJava2D(start, dataArea, plot.getDomainAxisEdge());
      
      double end2d = domainAxis.valueToJava2D(end, dataArea, plot.getDomainAxisEdge());
      
      double low = Math.min(start2d, end2d);
      double high = Math.max(start2d, end2d);
      
      PlotOrientation orientation = plot.getOrientation();
      Rectangle2D rect = null;
      if (orientation == PlotOrientation.HORIZONTAL)
      {
        low = Math.max(low, dataArea.getMinY());
        high = Math.min(high, dataArea.getMaxY());
        rect = new Rectangle2D.Double(dataArea.getMinX(), low, dataArea.getWidth(), high - low);


      }
      else if (orientation == PlotOrientation.VERTICAL)
      {
        low = Math.max(low, dataArea.getMinX());
        high = Math.min(high, dataArea.getMaxX());
        rect = new Rectangle2D.Double(low, dataArea.getMinY(), high - low, dataArea.getHeight());
      }
      


      Composite originalComposite = g2.getComposite();
      g2.setComposite(AlphaComposite.getInstance(3, marker.getAlpha()));
      
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
        else {
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
      }
      
      String label = marker.getLabel();
      RectangleAnchor anchor = marker.getLabelAnchor();
      if (label != null) {
        Font labelFont = marker.getLabelFont();
        g2.setFont(labelFont);
        g2.setPaint(marker.getLabelPaint());
        Point2D coordinates = calculateDomainMarkerTextAnchorPoint(g2, orientation, dataArea, rect, marker.getLabelOffset(), marker.getLabelOffsetType(), anchor);
        


        TextUtilities.drawAlignedString(label, g2, (float)coordinates.getX(), (float)coordinates.getY(), marker.getLabelTextAnchor());
      }
      

      g2.setComposite(originalComposite);
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
  














  public void drawRangeMarker(Graphics2D g2, XYPlot plot, ValueAxis rangeAxis, Marker marker, Rectangle2D dataArea)
  {
    if ((marker instanceof ValueMarker)) {
      ValueMarker vm = (ValueMarker)marker;
      double value = vm.getValue();
      Range range = rangeAxis.getRange();
      if (!range.contains(value)) {
        return;
      }
      
      double v = rangeAxis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
      
      PlotOrientation orientation = plot.getOrientation();
      Line2D line = null;
      if (orientation == PlotOrientation.HORIZONTAL) {
        line = new Line2D.Double(v, dataArea.getMinY(), v, dataArea.getMaxY());

      }
      else if (orientation == PlotOrientation.VERTICAL) {
        line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(), v);
      }
      

      Composite originalComposite = g2.getComposite();
      g2.setComposite(AlphaComposite.getInstance(3, marker.getAlpha()));
      
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
      

      g2.setComposite(originalComposite);
    }
    else if ((marker instanceof IntervalMarker)) {
      IntervalMarker im = (IntervalMarker)marker;
      double start = im.getStartValue();
      double end = im.getEndValue();
      Range range = rangeAxis.getRange();
      if (!range.intersects(start, end)) {
        return;
      }
      
      double start2d = rangeAxis.valueToJava2D(start, dataArea, plot.getRangeAxisEdge());
      
      double end2d = rangeAxis.valueToJava2D(end, dataArea, plot.getRangeAxisEdge());
      
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
      


      Composite originalComposite = g2.getComposite();
      g2.setComposite(AlphaComposite.getInstance(3, marker.getAlpha()));
      
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
      

      g2.setComposite(originalComposite);
    }
  }
  



















  private Point2D calculateRangeMarkerTextAnchorPoint(Graphics2D g2, PlotOrientation orientation, Rectangle2D dataArea, Rectangle2D markerArea, RectangleInsets markerOffset, LengthAdjustmentType labelOffsetForRange, RectangleAnchor anchor)
  {
    Rectangle2D anchorRect = null;
    if (orientation == PlotOrientation.HORIZONTAL) {
      anchorRect = markerOffset.createAdjustedRectangle(markerArea, labelOffsetForRange, LengthAdjustmentType.CONTRACT);

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      anchorRect = markerOffset.createAdjustedRectangle(markerArea, LengthAdjustmentType.CONTRACT, labelOffsetForRange);
    }
    
    return RectangleAnchor.coordinates(anchorRect, anchor);
  }
  







  protected Object clone()
    throws CloneNotSupportedException
  {
    AbstractXYItemRenderer clone = (AbstractXYItemRenderer)super.clone();
    

    if ((itemLabelGenerator != null) && ((itemLabelGenerator instanceof PublicCloneable)))
    {
      PublicCloneable pc = (PublicCloneable)itemLabelGenerator;
      itemLabelGenerator = ((XYItemLabelGenerator)pc.clone());
    }
    itemLabelGeneratorList = ((ObjectList)itemLabelGeneratorList.clone());
    
    if ((baseItemLabelGenerator != null) && ((baseItemLabelGenerator instanceof PublicCloneable)))
    {
      PublicCloneable pc = (PublicCloneable)baseItemLabelGenerator;
      baseItemLabelGenerator = ((XYItemLabelGenerator)pc.clone());
    }
    
    if ((toolTipGenerator != null) && ((toolTipGenerator instanceof PublicCloneable)))
    {
      PublicCloneable pc = (PublicCloneable)toolTipGenerator;
      toolTipGenerator = ((XYToolTipGenerator)pc.clone());
    }
    toolTipGeneratorList = ((ObjectList)toolTipGeneratorList.clone());
    
    if ((baseToolTipGenerator != null) && ((baseToolTipGenerator instanceof PublicCloneable)))
    {
      PublicCloneable pc = (PublicCloneable)baseToolTipGenerator;
      baseToolTipGenerator = ((XYToolTipGenerator)pc.clone());
    }
    
    if ((legendItemLabelGenerator instanceof PublicCloneable)) {
      legendItemLabelGenerator = ((XYSeriesLabelGenerator)ObjectUtilities.clone(legendItemLabelGenerator));
    }
    
    if ((legendItemToolTipGenerator instanceof PublicCloneable)) {
      legendItemToolTipGenerator = ((XYSeriesLabelGenerator)ObjectUtilities.clone(legendItemToolTipGenerator));
    }
    
    if ((legendItemURLGenerator instanceof PublicCloneable)) {
      legendItemURLGenerator = ((XYSeriesLabelGenerator)ObjectUtilities.clone(legendItemURLGenerator));
    }
    

    foregroundAnnotations = ((List)ObjectUtilities.deepClone(foregroundAnnotations));
    
    backgroundAnnotations = ((List)ObjectUtilities.deepClone(backgroundAnnotations));
    

    if ((legendItemLabelGenerator instanceof PublicCloneable)) {
      legendItemLabelGenerator = ((XYSeriesLabelGenerator)ObjectUtilities.clone(legendItemLabelGenerator));
    }
    
    if ((legendItemToolTipGenerator instanceof PublicCloneable)) {
      legendItemToolTipGenerator = ((XYSeriesLabelGenerator)ObjectUtilities.clone(legendItemToolTipGenerator));
    }
    
    if ((legendItemURLGenerator instanceof PublicCloneable)) {
      legendItemURLGenerator = ((XYSeriesLabelGenerator)ObjectUtilities.clone(legendItemURLGenerator));
    }
    

    return clone;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AbstractXYItemRenderer)) {
      return false;
    }
    AbstractXYItemRenderer that = (AbstractXYItemRenderer)obj;
    if (!ObjectUtilities.equal(itemLabelGenerator, itemLabelGenerator))
    {
      return false;
    }
    if (!itemLabelGeneratorList.equals(itemLabelGeneratorList)) {
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
    if (!toolTipGeneratorList.equals(toolTipGeneratorList)) {
      return false;
    }
    if (!ObjectUtilities.equal(baseToolTipGenerator, baseToolTipGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(urlGenerator, urlGenerator)) {
      return false;
    }
    if (!foregroundAnnotations.equals(foregroundAnnotations)) {
      return false;
    }
    if (!backgroundAnnotations.equals(backgroundAnnotations)) {
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
  




  public DrawingSupplier getDrawingSupplier()
  {
    DrawingSupplier result = null;
    XYPlot p = getPlot();
    if (p != null) {
      result = p.getDrawingSupplier();
    }
    return result;
  }
  




















  protected void updateCrosshairValues(CrosshairState crosshairState, double x, double y, int domainAxisIndex, int rangeAxisIndex, double transX, double transY, PlotOrientation orientation)
  {
    if (orientation == null) {
      throw new IllegalArgumentException("Null 'orientation' argument.");
    }
    
    if (crosshairState != null)
    {
      if (plot.isDomainCrosshairLockedOnData()) {
        if (plot.isRangeCrosshairLockedOnData())
        {
          crosshairState.updateCrosshairPoint(x, y, domainAxisIndex, rangeAxisIndex, transX, transY, orientation);

        }
        else
        {
          crosshairState.updateCrosshairX(x, domainAxisIndex);
        }
        
      }
      else if (plot.isRangeCrosshairLockedOnData())
      {
        crosshairState.updateCrosshairY(y, rangeAxisIndex);
      }
    }
  }
  

















  protected void drawItemLabel(Graphics2D g2, PlotOrientation orientation, XYDataset dataset, int series, int item, double x, double y, boolean negative)
  {
    XYItemLabelGenerator generator = getItemLabelGenerator(series, item);
    if (generator != null) {
      Font labelFont = getItemLabelFont(series, item);
      Paint paint = getItemLabelPaint(series, item);
      g2.setFont(labelFont);
      g2.setPaint(paint);
      String label = generator.generateLabel(dataset, series, item);
      

      ItemLabelPosition position = null;
      if (!negative) {
        position = getPositiveItemLabelPosition(series, item);
      }
      else {
        position = getNegativeItemLabelPosition(series, item);
      }
      

      Point2D anchorPoint = calculateLabelAnchorPoint(position.getItemLabelAnchor(), x, y, orientation);
      
      TextUtilities.drawRotatedString(label, g2, (float)anchorPoint.getX(), (float)anchorPoint.getY(), position.getTextAnchor(), position.getAngle(), position.getRotationAnchor());
    }
  }
  



















  public void drawAnnotations(Graphics2D g2, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, Layer layer, PlotRenderingInfo info)
  {
    Iterator iterator = null;
    if (layer.equals(Layer.FOREGROUND)) {
      iterator = foregroundAnnotations.iterator();
    }
    else if (layer.equals(Layer.BACKGROUND)) {
      iterator = backgroundAnnotations.iterator();
    }
    else
    {
      throw new RuntimeException("Unknown layer.");
    }
    while (iterator.hasNext()) {
      XYAnnotation annotation = (XYAnnotation)iterator.next();
      annotation.draw(g2, plot, dataArea, domainAxis, rangeAxis, 0, info);
    }
  }
  

















  protected void addEntity(EntityCollection entities, Shape area, XYDataset dataset, int series, int item, double entityX, double entityY)
  {
    if (!getItemCreateEntity(series, item)) {
      return;
    }
    Shape hotspot = area;
    if (hotspot == null) {
      double r = getDefaultEntityRadius();
      double w = r * 2.0D;
      if (getPlot().getOrientation() == PlotOrientation.VERTICAL) {
        hotspot = new Ellipse2D.Double(entityX - r, entityY - r, w, w);
      }
      else {
        hotspot = new Ellipse2D.Double(entityY - r, entityX - r, w, w);
      }
    }
    String tip = null;
    XYToolTipGenerator generator = getToolTipGenerator(series, item);
    if (generator != null) {
      tip = generator.generateToolTip(dataset, series, item);
    }
    String url = null;
    if (getURLGenerator() != null) {
      url = getURLGenerator().generateURL(dataset, series, item);
    }
    XYItemEntity entity = new XYItemEntity(hotspot, dataset, series, item, tip, url);
    
    entities.add(entity);
  }
  













  public static boolean isPointInRect(Rectangle2D rect, double x, double y)
  {
    return (x >= rect.getMinX()) && (x <= rect.getMaxX()) && (y >= rect.getMinY()) && (y <= rect.getMaxY());
  }
  




























  /**
   * @deprecated
   */
  public XYItemLabelGenerator getItemLabelGenerator()
  {
    return itemLabelGenerator;
  }
  








  /**
   * @deprecated
   */
  public void setItemLabelGenerator(XYItemLabelGenerator generator)
  {
    itemLabelGenerator = generator;
    fireChangeEvent();
  }
  









  /**
   * @deprecated
   */
  public XYToolTipGenerator getToolTipGenerator()
  {
    return toolTipGenerator;
  }
  








  /**
   * @deprecated
   */
  public void setToolTipGenerator(XYToolTipGenerator generator)
  {
    toolTipGenerator = generator;
    fireChangeEvent();
  }
  
















  /**
   * @deprecated
   */
  protected void updateCrosshairValues(CrosshairState crosshairState, double x, double y, double transX, double transY, PlotOrientation orientation)
  {
    updateCrosshairValues(crosshairState, x, y, 0, 0, transX, transY, orientation);
  }
}
