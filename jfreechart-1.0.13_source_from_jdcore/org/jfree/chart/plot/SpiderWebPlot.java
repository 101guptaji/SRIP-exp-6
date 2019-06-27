package org.jfree.chart.plot;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintList;
import org.jfree.util.PaintUtilities;
import org.jfree.util.Rotation;
import org.jfree.util.ShapeUtilities;
import org.jfree.util.StrokeList;
import org.jfree.util.TableOrder;


















































































public class SpiderWebPlot
  extends Plot
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -5376340422031599463L;
  public static final double DEFAULT_HEAD = 0.01D;
  public static final double DEFAULT_AXIS_LABEL_GAP = 0.1D;
  public static final double DEFAULT_INTERIOR_GAP = 0.25D;
  public static final double MAX_INTERIOR_GAP = 0.4D;
  public static final double DEFAULT_START_ANGLE = 90.0D;
  public static final Font DEFAULT_LABEL_FONT = new Font("SansSerif", 0, 10);
  


  public static final Paint DEFAULT_LABEL_PAINT = Color.black;
  

  public static final Paint DEFAULT_LABEL_BACKGROUND_PAINT = new Color(255, 255, 192);
  


  public static final Paint DEFAULT_LABEL_OUTLINE_PAINT = Color.black;
  

  public static final Stroke DEFAULT_LABEL_OUTLINE_STROKE = new BasicStroke(0.5F);
  


  public static final Paint DEFAULT_LABEL_SHADOW_PAINT = Color.lightGray;
  


  public static final double DEFAULT_MAX_VALUE = -1.0D;
  


  protected double headPercent;
  


  private double interiorGap;
  


  private double axisLabelGap;
  


  private transient Paint axisLinePaint;
  


  private transient Stroke axisLineStroke;
  


  private CategoryDataset dataset;
  


  private double maxValue;
  


  private TableOrder dataExtractOrder;
  


  private double startAngle;
  


  private Rotation direction;
  


  private transient Shape legendItemShape;
  


  private transient Paint seriesPaint;
  


  private PaintList seriesPaintList;
  


  private transient Paint baseSeriesPaint;
  


  private transient Paint seriesOutlinePaint;
  

  private PaintList seriesOutlinePaintList;
  

  private transient Paint baseSeriesOutlinePaint;
  

  private transient Stroke seriesOutlineStroke;
  

  private StrokeList seriesOutlineStrokeList;
  

  private transient Stroke baseSeriesOutlineStroke;
  

  private Font labelFont;
  

  private transient Paint labelPaint;
  

  private CategoryItemLabelGenerator labelGenerator;
  

  private boolean webFilled = true;
  

  private CategoryToolTipGenerator toolTipGenerator;
  

  private CategoryURLGenerator urlGenerator;
  


  public SpiderWebPlot()
  {
    this(null);
  }
  





  public SpiderWebPlot(CategoryDataset dataset)
  {
    this(dataset, TableOrder.BY_ROW);
  }
  







  public SpiderWebPlot(CategoryDataset dataset, TableOrder extract)
  {
    if (extract == null) {
      throw new IllegalArgumentException("Null 'extract' argument.");
    }
    this.dataset = dataset;
    if (dataset != null) {
      dataset.addChangeListener(this);
    }
    
    dataExtractOrder = extract;
    headPercent = 0.01D;
    axisLabelGap = 0.1D;
    axisLinePaint = Color.black;
    axisLineStroke = new BasicStroke(1.0F);
    
    interiorGap = 0.25D;
    startAngle = 90.0D;
    direction = Rotation.CLOCKWISE;
    maxValue = -1.0D;
    
    seriesPaint = null;
    seriesPaintList = new PaintList();
    baseSeriesPaint = null;
    
    seriesOutlinePaint = null;
    seriesOutlinePaintList = new PaintList();
    baseSeriesOutlinePaint = DEFAULT_OUTLINE_PAINT;
    
    seriesOutlineStroke = null;
    seriesOutlineStrokeList = new StrokeList();
    baseSeriesOutlineStroke = DEFAULT_OUTLINE_STROKE;
    
    labelFont = DEFAULT_LABEL_FONT;
    labelPaint = DEFAULT_LABEL_PAINT;
    labelGenerator = new StandardCategoryItemLabelGenerator();
    
    legendItemShape = DEFAULT_LEGEND_ITEM_CIRCLE;
  }
  





  public String getPlotType()
  {
    return "Spider Web Plot";
  }
  






  public CategoryDataset getDataset()
  {
    return dataset;
  }
  









  public void setDataset(CategoryDataset dataset)
  {
    if (this.dataset != null) {
      this.dataset.removeChangeListener(this);
    }
    

    this.dataset = dataset;
    if (dataset != null) {
      setDatasetGroup(dataset.getGroup());
      dataset.addChangeListener(this);
    }
    

    datasetChanged(new DatasetChangeEvent(this, dataset));
  }
  






  public boolean isWebFilled()
  {
    return webFilled;
  }
  







  public void setWebFilled(boolean flag)
  {
    webFilled = flag;
    fireChangeEvent();
  }
  






  public TableOrder getDataExtractOrder()
  {
    return dataExtractOrder;
  }
  










  public void setDataExtractOrder(TableOrder order)
  {
    if (order == null) {
      throw new IllegalArgumentException("Null 'order' argument");
    }
    dataExtractOrder = order;
    fireChangeEvent();
  }
  






  public double getHeadPercent()
  {
    return headPercent;
  }
  







  public void setHeadPercent(double percent)
  {
    headPercent = percent;
    fireChangeEvent();
  }
  









  public double getStartAngle()
  {
    return startAngle;
  }
  











  public void setStartAngle(double angle)
  {
    startAngle = angle;
    fireChangeEvent();
  }
  






  public double getMaxValue()
  {
    return maxValue;
  }
  







  public void setMaxValue(double value)
  {
    maxValue = value;
    fireChangeEvent();
  }
  







  public Rotation getDirection()
  {
    return direction;
  }
  







  public void setDirection(Rotation direction)
  {
    if (direction == null) {
      throw new IllegalArgumentException("Null 'direction' argument.");
    }
    this.direction = direction;
    fireChangeEvent();
  }
  







  public double getInteriorGap()
  {
    return interiorGap;
  }
  








  public void setInteriorGap(double percent)
  {
    if ((percent < 0.0D) || (percent > 0.4D)) {
      throw new IllegalArgumentException("Percentage outside valid range.");
    }
    
    if (interiorGap != percent) {
      interiorGap = percent;
      fireChangeEvent();
    }
  }
  






  public double getAxisLabelGap()
  {
    return axisLabelGap;
  }
  







  public void setAxisLabelGap(double gap)
  {
    axisLabelGap = gap;
    fireChangeEvent();
  }
  








  public Paint getAxisLinePaint()
  {
    return axisLinePaint;
  }
  








  public void setAxisLinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    axisLinePaint = paint;
    fireChangeEvent();
  }
  








  public Stroke getAxisLineStroke()
  {
    return axisLineStroke;
  }
  








  public void setAxisLineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    axisLineStroke = stroke;
    fireChangeEvent();
  }
  








  public Paint getSeriesPaint()
  {
    return seriesPaint;
  }
  








  public void setSeriesPaint(Paint paint)
  {
    seriesPaint = paint;
    fireChangeEvent();
  }
  










  public Paint getSeriesPaint(int series)
  {
    if (seriesPaint != null) {
      return seriesPaint;
    }
    

    Paint result = seriesPaintList.getPaint(series);
    if (result == null) {
      DrawingSupplier supplier = getDrawingSupplier();
      if (supplier != null) {
        Paint p = supplier.getNextPaint();
        seriesPaintList.setPaint(series, p);
        result = p;
      }
      else {
        result = baseSeriesPaint;
      }
    }
    return result;
  }
  









  public void setSeriesPaint(int series, Paint paint)
  {
    seriesPaintList.setPaint(series, paint);
    fireChangeEvent();
  }
  







  public Paint getBaseSeriesPaint()
  {
    return baseSeriesPaint;
  }
  






  public void setBaseSeriesPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    baseSeriesPaint = paint;
    fireChangeEvent();
  }
  






  public Paint getSeriesOutlinePaint()
  {
    return seriesOutlinePaint;
  }
  






  public void setSeriesOutlinePaint(Paint paint)
  {
    seriesOutlinePaint = paint;
    fireChangeEvent();
  }
  







  public Paint getSeriesOutlinePaint(int series)
  {
    if (seriesOutlinePaint != null) {
      return seriesOutlinePaint;
    }
    
    Paint result = seriesOutlinePaintList.getPaint(series);
    if (result == null) {
      result = baseSeriesOutlinePaint;
    }
    return result;
  }
  






  public void setSeriesOutlinePaint(int series, Paint paint)
  {
    seriesOutlinePaintList.setPaint(series, paint);
    fireChangeEvent();
  }
  





  public Paint getBaseSeriesOutlinePaint()
  {
    return baseSeriesOutlinePaint;
  }
  




  public void setBaseSeriesOutlinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    baseSeriesOutlinePaint = paint;
    fireChangeEvent();
  }
  






  public Stroke getSeriesOutlineStroke()
  {
    return seriesOutlineStroke;
  }
  






  public void setSeriesOutlineStroke(Stroke stroke)
  {
    seriesOutlineStroke = stroke;
    fireChangeEvent();
  }
  








  public Stroke getSeriesOutlineStroke(int series)
  {
    if (seriesOutlineStroke != null) {
      return seriesOutlineStroke;
    }
    

    Stroke result = seriesOutlineStrokeList.getStroke(series);
    if (result == null) {
      result = baseSeriesOutlineStroke;
    }
    return result;
  }
  







  public void setSeriesOutlineStroke(int series, Stroke stroke)
  {
    seriesOutlineStrokeList.setStroke(series, stroke);
    fireChangeEvent();
  }
  





  public Stroke getBaseSeriesOutlineStroke()
  {
    return baseSeriesOutlineStroke;
  }
  




  public void setBaseSeriesOutlineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    baseSeriesOutlineStroke = stroke;
    fireChangeEvent();
  }
  






  public Shape getLegendItemShape()
  {
    return legendItemShape;
  }
  







  public void setLegendItemShape(Shape shape)
  {
    if (shape == null) {
      throw new IllegalArgumentException("Null 'shape' argument.");
    }
    legendItemShape = shape;
    fireChangeEvent();
  }
  






  public Font getLabelFont()
  {
    return labelFont;
  }
  







  public void setLabelFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    labelFont = font;
    fireChangeEvent();
  }
  






  public Paint getLabelPaint()
  {
    return labelPaint;
  }
  







  public void setLabelPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    labelPaint = paint;
    fireChangeEvent();
  }
  






  public CategoryItemLabelGenerator getLabelGenerator()
  {
    return labelGenerator;
  }
  







  public void setLabelGenerator(CategoryItemLabelGenerator generator)
  {
    if (generator == null) {
      throw new IllegalArgumentException("Null 'generator' argument.");
    }
    labelGenerator = generator;
  }
  








  public CategoryToolTipGenerator getToolTipGenerator()
  {
    return toolTipGenerator;
  }
  









  public void setToolTipGenerator(CategoryToolTipGenerator generator)
  {
    toolTipGenerator = generator;
    fireChangeEvent();
  }
  








  public CategoryURLGenerator getURLGenerator()
  {
    return urlGenerator;
  }
  









  public void setURLGenerator(CategoryURLGenerator generator)
  {
    urlGenerator = generator;
    fireChangeEvent();
  }
  




  public LegendItemCollection getLegendItems()
  {
    LegendItemCollection result = new LegendItemCollection();
    if (getDataset() == null) {
      return result;
    }
    
    List keys = null;
    if (dataExtractOrder == TableOrder.BY_ROW) {
      keys = dataset.getRowKeys();
    }
    else if (dataExtractOrder == TableOrder.BY_COLUMN) {
      keys = dataset.getColumnKeys();
    }
    
    if (keys != null) {
      int series = 0;
      Iterator iterator = keys.iterator();
      Shape shape = getLegendItemShape();
      
      while (iterator.hasNext()) {
        String label = iterator.next().toString();
        String description = label;
        
        Paint paint = getSeriesPaint(series);
        Paint outlinePaint = getSeriesOutlinePaint(series);
        Stroke stroke = getSeriesOutlineStroke(series);
        LegendItem item = new LegendItem(label, description, null, null, shape, paint, stroke, outlinePaint);
        
        item.setDataset(getDataset());
        result.add(item);
        series++;
      }
    }
    
    return result;
  }
  










  protected Point2D getWebPoint(Rectangle2D bounds, double angle, double length)
  {
    double angrad = Math.toRadians(angle);
    double x = Math.cos(angrad) * length * bounds.getWidth() / 2.0D;
    double y = -Math.sin(angrad) * length * bounds.getHeight() / 2.0D;
    
    return new Point2D.Double(bounds.getX() + x + bounds.getWidth() / 2.0D, bounds.getY() + y + bounds.getHeight() / 2.0D);
  }
  













  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info)
  {
    RectangleInsets insets = getInsets();
    insets.trim(area);
    
    if (info != null) {
      info.setPlotArea(area);
      info.setDataArea(area);
    }
    
    drawBackground(g2, area);
    drawOutline(g2, area);
    
    Shape savedClip = g2.getClip();
    
    g2.clip(area);
    Composite originalComposite = g2.getComposite();
    g2.setComposite(AlphaComposite.getInstance(3, getForegroundAlpha()));
    

    if (!DatasetUtilities.isEmptyOrNull(dataset)) {
      int seriesCount = 0;int catCount = 0;
      
      if (dataExtractOrder == TableOrder.BY_ROW) {
        seriesCount = dataset.getRowCount();
        catCount = dataset.getColumnCount();
      }
      else {
        seriesCount = dataset.getColumnCount();
        catCount = dataset.getRowCount();
      }
      

      if (maxValue == -1.0D) {
        calculateMaxValue(seriesCount, catCount);
      }
      



      double gapHorizontal = area.getWidth() * getInteriorGap();
      double gapVertical = area.getHeight() * getInteriorGap();
      
      double X = area.getX() + gapHorizontal / 2.0D;
      double Y = area.getY() + gapVertical / 2.0D;
      double W = area.getWidth() - gapHorizontal;
      double H = area.getHeight() - gapVertical;
      
      double headW = area.getWidth() * headPercent;
      double headH = area.getHeight() * headPercent;
      

      double min = Math.min(W, H) / 2.0D;
      X = (X + X + W) / 2.0D - min;
      Y = (Y + Y + H) / 2.0D - min;
      W = 2.0D * min;
      H = 2.0D * min;
      
      Point2D centre = new Point2D.Double(X + W / 2.0D, Y + H / 2.0D);
      Rectangle2D radarArea = new Rectangle2D.Double(X, Y, W, H);
      

      for (int cat = 0; cat < catCount; cat++) {
        double angle = getStartAngle() + getDirection().getFactor() * cat * 360.0D / catCount;
        

        Point2D endPoint = getWebPoint(radarArea, angle, 1.0D);
        
        Line2D line = new Line2D.Double(centre, endPoint);
        g2.setPaint(axisLinePaint);
        g2.setStroke(axisLineStroke);
        g2.draw(line);
        drawLabel(g2, radarArea, 0.0D, cat, angle, 360.0D / catCount);
      }
      

      for (int series = 0; series < seriesCount; series++) {
        drawRadarPoly(g2, radarArea, centre, info, series, catCount, headH, headW);
      }
    }
    else
    {
      drawNoDataMessage(g2, area);
    }
    g2.setClip(savedClip);
    g2.setComposite(originalComposite);
    drawOutline(g2, area);
  }
  






  private void calculateMaxValue(int seriesCount, int catCount)
  {
    double v = 0.0D;
    Number nV = null;
    
    for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
      for (int catIndex = 0; catIndex < catCount; catIndex++) {
        nV = getPlotValue(seriesIndex, catIndex);
        if (nV != null) {
          v = nV.doubleValue();
          if (v > maxValue) {
            maxValue = v;
          }
        }
      }
    }
  }
  

















  protected void drawRadarPoly(Graphics2D g2, Rectangle2D plotArea, Point2D centre, PlotRenderingInfo info, int series, int catCount, double headH, double headW)
  {
    Polygon polygon = new Polygon();
    
    EntityCollection entities = null;
    if (info != null) {
      entities = info.getOwner().getEntityCollection();
    }
    

    for (int cat = 0; cat < catCount; cat++)
    {
      Number dataValue = getPlotValue(series, cat);
      
      if (dataValue != null) {
        double value = dataValue.doubleValue();
        
        if (value >= 0.0D)
        {


          double angle = getStartAngle() + getDirection().getFactor() * cat * 360.0D / catCount;
          












          Point2D point = getWebPoint(plotArea, angle, value / maxValue);
          
          polygon.addPoint((int)point.getX(), (int)point.getY());
          


          Paint paint = getSeriesPaint(series);
          Paint outlinePaint = getSeriesOutlinePaint(series);
          Stroke outlineStroke = getSeriesOutlineStroke(series);
          
          Ellipse2D head = new Ellipse2D.Double(point.getX() - headW / 2.0D, point.getY() - headH / 2.0D, headW, headH);
          

          g2.setPaint(paint);
          g2.fill(head);
          g2.setStroke(outlineStroke);
          g2.setPaint(outlinePaint);
          g2.draw(head);
          
          if (entities != null) {
            int row = 0;int col = 0;
            if (dataExtractOrder == TableOrder.BY_ROW) {
              row = series;
              col = cat;
            }
            else {
              row = cat;
              col = series;
            }
            String tip = null;
            if (toolTipGenerator != null) {
              tip = toolTipGenerator.generateToolTip(dataset, row, col);
            }
            

            String url = null;
            if (urlGenerator != null) {
              url = urlGenerator.generateURL(dataset, row, col);
            }
            

            Shape area = new Rectangle((int)(point.getX() - headW), (int)(point.getY() - headH), (int)(headW * 2.0D), (int)(headH * 2.0D));
            


            CategoryItemEntity entity = new CategoryItemEntity(area, tip, url, dataset, dataset.getRowKey(row), dataset.getColumnKey(col));
            


            entities.add(entity);
          }
        }
      }
    }
    


    Paint paint = getSeriesPaint(series);
    g2.setPaint(paint);
    g2.setStroke(getSeriesOutlineStroke(series));
    g2.draw(polygon);
    


    if (webFilled) {
      g2.setComposite(AlphaComposite.getInstance(3, 0.1F));
      
      g2.fill(polygon);
      g2.setComposite(AlphaComposite.getInstance(3, getForegroundAlpha()));
    }
  }
  














  protected Number getPlotValue(int series, int cat)
  {
    Number value = null;
    if (dataExtractOrder == TableOrder.BY_ROW) {
      value = dataset.getValue(series, cat);
    }
    else if (dataExtractOrder == TableOrder.BY_COLUMN) {
      value = dataset.getValue(cat, series);
    }
    return value;
  }
  










  protected void drawLabel(Graphics2D g2, Rectangle2D plotArea, double value, int cat, double startAngle, double extent)
  {
    FontRenderContext frc = g2.getFontRenderContext();
    
    String label = null;
    if (dataExtractOrder == TableOrder.BY_ROW)
    {
      label = labelGenerator.generateColumnLabel(dataset, cat);
    }
    else
    {
      label = labelGenerator.generateRowLabel(dataset, cat);
    }
    
    Rectangle2D labelBounds = getLabelFont().getStringBounds(label, frc);
    LineMetrics lm = getLabelFont().getLineMetrics(label, frc);
    double ascent = lm.getAscent();
    
    Point2D labelLocation = calculateLabelLocation(labelBounds, ascent, plotArea, startAngle);
    

    Composite saveComposite = g2.getComposite();
    
    g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
    
    g2.setPaint(getLabelPaint());
    g2.setFont(getLabelFont());
    g2.drawString(label, (float)labelLocation.getX(), (float)labelLocation.getY());
    
    g2.setComposite(saveComposite);
  }
  













  protected Point2D calculateLabelLocation(Rectangle2D labelBounds, double ascent, Rectangle2D plotArea, double startAngle)
  {
    Arc2D arc1 = new Arc2D.Double(plotArea, startAngle, 0.0D, 0);
    Point2D point1 = arc1.getEndPoint();
    
    double deltaX = -(point1.getX() - plotArea.getCenterX()) * axisLabelGap;
    
    double deltaY = -(point1.getY() - plotArea.getCenterY()) * axisLabelGap;
    

    double labelX = point1.getX() - deltaX;
    double labelY = point1.getY() - deltaY;
    
    if (labelX < plotArea.getCenterX()) {
      labelX -= labelBounds.getWidth();
    }
    
    if (labelX == plotArea.getCenterX()) {
      labelX -= labelBounds.getWidth() / 2.0D;
    }
    
    if (labelY > plotArea.getCenterY()) {
      labelY += ascent;
    }
    
    return new Point2D.Double(labelX, labelY);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof SpiderWebPlot)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    SpiderWebPlot that = (SpiderWebPlot)obj;
    if (!dataExtractOrder.equals(dataExtractOrder)) {
      return false;
    }
    if (headPercent != headPercent) {
      return false;
    }
    if (interiorGap != interiorGap) {
      return false;
    }
    if (startAngle != startAngle) {
      return false;
    }
    if (!direction.equals(direction)) {
      return false;
    }
    if (maxValue != maxValue) {
      return false;
    }
    if (webFilled != webFilled) {
      return false;
    }
    if (axisLabelGap != axisLabelGap) {
      return false;
    }
    if (!PaintUtilities.equal(axisLinePaint, axisLinePaint)) {
      return false;
    }
    if (!axisLineStroke.equals(axisLineStroke)) {
      return false;
    }
    if (!ShapeUtilities.equal(legendItemShape, legendItemShape)) {
      return false;
    }
    if (!PaintUtilities.equal(seriesPaint, seriesPaint)) {
      return false;
    }
    if (!seriesPaintList.equals(seriesPaintList)) {
      return false;
    }
    if (!PaintUtilities.equal(baseSeriesPaint, baseSeriesPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(seriesOutlinePaint, seriesOutlinePaint))
    {
      return false;
    }
    if (!seriesOutlinePaintList.equals(seriesOutlinePaintList)) {
      return false;
    }
    if (!PaintUtilities.equal(baseSeriesOutlinePaint, baseSeriesOutlinePaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(seriesOutlineStroke, seriesOutlineStroke))
    {
      return false;
    }
    if (!seriesOutlineStrokeList.equals(seriesOutlineStrokeList))
    {
      return false;
    }
    if (!baseSeriesOutlineStroke.equals(baseSeriesOutlineStroke))
    {
      return false;
    }
    if (!labelFont.equals(labelFont)) {
      return false;
    }
    if (!PaintUtilities.equal(labelPaint, labelPaint)) {
      return false;
    }
    if (!labelGenerator.equals(labelGenerator)) {
      return false;
    }
    if (!ObjectUtilities.equal(toolTipGenerator, toolTipGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(urlGenerator, urlGenerator))
    {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    SpiderWebPlot clone = (SpiderWebPlot)super.clone();
    legendItemShape = ShapeUtilities.clone(legendItemShape);
    seriesPaintList = ((PaintList)seriesPaintList.clone());
    seriesOutlinePaintList = ((PaintList)seriesOutlinePaintList.clone());
    
    seriesOutlineStrokeList = ((StrokeList)seriesOutlineStrokeList.clone());
    
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    
    SerialUtilities.writeShape(legendItemShape, stream);
    SerialUtilities.writePaint(seriesPaint, stream);
    SerialUtilities.writePaint(baseSeriesPaint, stream);
    SerialUtilities.writePaint(seriesOutlinePaint, stream);
    SerialUtilities.writePaint(baseSeriesOutlinePaint, stream);
    SerialUtilities.writeStroke(seriesOutlineStroke, stream);
    SerialUtilities.writeStroke(baseSeriesOutlineStroke, stream);
    SerialUtilities.writePaint(labelPaint, stream);
    SerialUtilities.writePaint(axisLinePaint, stream);
    SerialUtilities.writeStroke(axisLineStroke, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    
    legendItemShape = SerialUtilities.readShape(stream);
    seriesPaint = SerialUtilities.readPaint(stream);
    baseSeriesPaint = SerialUtilities.readPaint(stream);
    seriesOutlinePaint = SerialUtilities.readPaint(stream);
    baseSeriesOutlinePaint = SerialUtilities.readPaint(stream);
    seriesOutlineStroke = SerialUtilities.readStroke(stream);
    baseSeriesOutlineStroke = SerialUtilities.readStroke(stream);
    labelPaint = SerialUtilities.readPaint(stream);
    axisLinePaint = SerialUtilities.readPaint(stream);
    axisLineStroke = SerialUtilities.readStroke(stream);
    if (dataset != null) {
      dataset.addChangeListener(this);
    }
  }
}
