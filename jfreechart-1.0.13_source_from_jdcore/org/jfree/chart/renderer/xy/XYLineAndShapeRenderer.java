package org.jfree.chart.renderer.xy;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.BooleanList;
import org.jfree.util.BooleanUtilities;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;



































































































































public class XYLineAndShapeRenderer
  extends AbstractXYItemRenderer
  implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -7435246895986425885L;
  /**
   * @deprecated
   */
  private Boolean linesVisible;
  private BooleanList seriesLinesVisible;
  private boolean baseLinesVisible;
  private transient Shape legendLine;
  /**
   * @deprecated
   */
  private Boolean shapesVisible;
  private BooleanList seriesShapesVisible;
  private boolean baseShapesVisible;
  /**
   * @deprecated
   */
  private Boolean shapesFilled;
  private BooleanList seriesShapesFilled;
  private boolean baseShapesFilled;
  private boolean drawOutlines;
  private boolean useFillPaint;
  private boolean useOutlinePaint;
  private boolean drawSeriesLineAsPath;
  
  public XYLineAndShapeRenderer()
  {
    this(true, true);
  }
  





  public XYLineAndShapeRenderer(boolean lines, boolean shapes)
  {
    linesVisible = null;
    seriesLinesVisible = new BooleanList();
    baseLinesVisible = lines;
    legendLine = new Line2D.Double(-7.0D, 0.0D, 7.0D, 0.0D);
    
    shapesVisible = null;
    seriesShapesVisible = new BooleanList();
    baseShapesVisible = shapes;
    
    shapesFilled = null;
    useFillPaint = false;
    seriesShapesFilled = new BooleanList();
    baseShapesFilled = true;
    
    drawOutlines = true;
    useOutlinePaint = false;
    

    drawSeriesLineAsPath = false;
  }
  







  public boolean getDrawSeriesLineAsPath()
  {
    return drawSeriesLineAsPath;
  }
  








  public void setDrawSeriesLineAsPath(boolean flag)
  {
    if (drawSeriesLineAsPath != flag) {
      drawSeriesLineAsPath = flag;
      fireChangeEvent();
    }
  }
  






  public int getPassCount()
  {
    return 2;
  }
  










  public boolean getItemLineVisible(int series, int item)
  {
    Boolean flag = linesVisible;
    if (flag == null) {
      flag = getSeriesLinesVisible(series);
    }
    if (flag != null) {
      return flag.booleanValue();
    }
    
    return baseLinesVisible;
  }
  








  /**
   * @deprecated
   */
  public Boolean getLinesVisible()
  {
    return linesVisible;
  }
  








  /**
   * @deprecated
   */
  public void setLinesVisible(Boolean visible)
  {
    linesVisible = visible;
    fireChangeEvent();
  }
  








  /**
   * @deprecated
   */
  public void setLinesVisible(boolean visible)
  {
    setLinesVisible(BooleanUtilities.valueOf(visible));
  }
  









  public Boolean getSeriesLinesVisible(int series)
  {
    return seriesLinesVisible.getBoolean(series);
  }
  








  public void setSeriesLinesVisible(int series, Boolean flag)
  {
    seriesLinesVisible.setBoolean(series, flag);
    fireChangeEvent();
  }
  








  public void setSeriesLinesVisible(int series, boolean visible)
  {
    setSeriesLinesVisible(series, BooleanUtilities.valueOf(visible));
  }
  






  public boolean getBaseLinesVisible()
  {
    return baseLinesVisible;
  }
  







  public void setBaseLinesVisible(boolean flag)
  {
    baseLinesVisible = flag;
    fireChangeEvent();
  }
  






  public Shape getLegendLine()
  {
    return legendLine;
  }
  







  public void setLegendLine(Shape line)
  {
    if (line == null) {
      throw new IllegalArgumentException("Null 'line' argument.");
    }
    legendLine = line;
    fireChangeEvent();
  }
  














  public boolean getItemShapeVisible(int series, int item)
  {
    Boolean flag = shapesVisible;
    if (flag == null) {
      flag = getSeriesShapesVisible(series);
    }
    if (flag != null) {
      return flag.booleanValue();
    }
    
    return baseShapesVisible;
  }
  







  /**
   * @deprecated
   */
  public Boolean getShapesVisible()
  {
    return shapesVisible;
  }
  






  /**
   * @deprecated
   */
  public void setShapesVisible(Boolean visible)
  {
    shapesVisible = visible;
    fireChangeEvent();
  }
  






  /**
   * @deprecated
   */
  public void setShapesVisible(boolean visible)
  {
    setShapesVisible(BooleanUtilities.valueOf(visible));
  }
  









  public Boolean getSeriesShapesVisible(int series)
  {
    return seriesShapesVisible.getBoolean(series);
  }
  








  public void setSeriesShapesVisible(int series, boolean visible)
  {
    setSeriesShapesVisible(series, BooleanUtilities.valueOf(visible));
  }
  








  public void setSeriesShapesVisible(int series, Boolean flag)
  {
    seriesShapesVisible.setBoolean(series, flag);
    fireChangeEvent();
  }
  






  public boolean getBaseShapesVisible()
  {
    return baseShapesVisible;
  }
  







  public void setBaseShapesVisible(boolean flag)
  {
    baseShapesVisible = flag;
    fireChangeEvent();
  }
  














  public boolean getItemShapeFilled(int series, int item)
  {
    Boolean flag = shapesFilled;
    if (flag == null) {
      flag = getSeriesShapesFilled(series);
    }
    if (flag != null) {
      return flag.booleanValue();
    }
    
    return baseShapesFilled;
  }
  





  /**
   * @deprecated
   */
  public void setShapesFilled(boolean filled)
  {
    setShapesFilled(BooleanUtilities.valueOf(filled));
  }
  




  /**
   * @deprecated
   */
  public void setShapesFilled(Boolean filled)
  {
    shapesFilled = filled;
    fireChangeEvent();
  }
  









  public Boolean getSeriesShapesFilled(int series)
  {
    return seriesShapesFilled.getBoolean(series);
  }
  








  public void setSeriesShapesFilled(int series, boolean flag)
  {
    setSeriesShapesFilled(series, BooleanUtilities.valueOf(flag));
  }
  








  public void setSeriesShapesFilled(int series, Boolean flag)
  {
    seriesShapesFilled.setBoolean(series, flag);
    fireChangeEvent();
  }
  






  public boolean getBaseShapesFilled()
  {
    return baseShapesFilled;
  }
  







  public void setBaseShapesFilled(boolean flag)
  {
    baseShapesFilled = flag;
    fireChangeEvent();
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
  











  public void setUseOutlinePaint(boolean flag)
  {
    useOutlinePaint = flag;
    fireChangeEvent();
  }
  





  public static class State
    extends XYItemRendererState
  {
    public GeneralPath seriesPath;
    




    private boolean lastPointGood;
    




    public State(PlotRenderingInfo info)
    {
      super();
    }
    





    public boolean isLastPointGood()
    {
      return lastPointGood;
    }
    





    public void setLastPointGood(boolean good)
    {
      lastPointGood = good;
    }
    











    public void startSeriesPass(XYDataset dataset, int series, int firstItem, int lastItem, int pass, int passCount)
    {
      seriesPath.reset();
      lastPointGood = false;
      super.startSeriesPass(dataset, series, firstItem, lastItem, pass, passCount);
    }
  }
  






















  public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info)
  {
    State state = new State(info);
    seriesPath = new GeneralPath();
    return state;
  }
  































  public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass)
  {
    if (!getItemVisible(series, item)) {
      return;
    }
    

    if (isLinePass(pass)) {
      if (getItemLineVisible(series, item)) {
        if (drawSeriesLineAsPath) {
          drawPrimaryLineAsPath(state, g2, plot, dataset, pass, series, item, domainAxis, rangeAxis, dataArea);
        }
        else
        {
          drawPrimaryLine(state, g2, plot, dataset, pass, series, item, domainAxis, rangeAxis, dataArea);
        }
        
      }
      
    }
    else if (isItemPass(pass))
    {

      EntityCollection entities = null;
      if (info != null) {
        entities = info.getOwner().getEntityCollection();
      }
      
      drawSecondaryPass(g2, plot, dataset, pass, series, item, domainAxis, dataArea, rangeAxis, crosshairState, entities);
    }
  }
  








  protected boolean isLinePass(int pass)
  {
    return pass == 0;
  }
  







  protected boolean isItemPass(int pass)
  {
    return pass == 1;
  }
  
























  protected void drawPrimaryLine(XYItemRendererState state, Graphics2D g2, XYPlot plot, XYDataset dataset, int pass, int series, int item, ValueAxis domainAxis, ValueAxis rangeAxis, Rectangle2D dataArea)
  {
    if (item == 0) {
      return;
    }
    

    double x1 = dataset.getXValue(series, item);
    double y1 = dataset.getYValue(series, item);
    if ((Double.isNaN(y1)) || (Double.isNaN(x1))) {
      return;
    }
    
    double x0 = dataset.getXValue(series, item - 1);
    double y0 = dataset.getYValue(series, item - 1);
    if ((Double.isNaN(y0)) || (Double.isNaN(x0))) {
      return;
    }
    
    RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
    RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
    
    double transX0 = domainAxis.valueToJava2D(x0, dataArea, xAxisLocation);
    double transY0 = rangeAxis.valueToJava2D(y0, dataArea, yAxisLocation);
    
    double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
    double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);
    

    if ((Double.isNaN(transX0)) || (Double.isNaN(transY0)) || (Double.isNaN(transX1)) || (Double.isNaN(transY1)))
    {
      return;
    }
    
    PlotOrientation orientation = plot.getOrientation();
    if (orientation == PlotOrientation.HORIZONTAL) {
      workingLine.setLine(transY0, transX0, transY1, transX1);
    }
    else if (orientation == PlotOrientation.VERTICAL) {
      workingLine.setLine(transX0, transY0, transX1, transY1);
    }
    
    if (workingLine.intersects(dataArea)) {
      drawFirstPassShape(g2, pass, series, item, workingLine);
    }
  }
  









  protected void drawFirstPassShape(Graphics2D g2, int pass, int series, int item, Shape shape)
  {
    g2.setStroke(getItemStroke(series, item));
    g2.setPaint(getItemPaint(series, item));
    g2.draw(shape);
  }
  




























  protected void drawPrimaryLineAsPath(XYItemRendererState state, Graphics2D g2, XYPlot plot, XYDataset dataset, int pass, int series, int item, ValueAxis domainAxis, ValueAxis rangeAxis, Rectangle2D dataArea)
  {
    RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
    RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
    

    double x1 = dataset.getXValue(series, item);
    double y1 = dataset.getYValue(series, item);
    double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
    double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);
    
    State s = (State)state;
    
    if ((!Double.isNaN(transX1)) && (!Double.isNaN(transY1))) {
      float x = (float)transX1;
      float y = (float)transY1;
      PlotOrientation orientation = plot.getOrientation();
      if (orientation == PlotOrientation.HORIZONTAL) {
        x = (float)transY1;
        y = (float)transX1;
      }
      if (s.isLastPointGood()) {
        seriesPath.lineTo(x, y);
      }
      else {
        seriesPath.moveTo(x, y);
      }
      s.setLastPointGood(true);
    }
    else {
      s.setLastPointGood(false);
    }
    
    if (item == s.getLastItemIndex())
    {
      drawFirstPassShape(g2, pass, series, item, seriesPath);
    }
  }
  


























  protected void drawSecondaryPass(Graphics2D g2, XYPlot plot, XYDataset dataset, int pass, int series, int item, ValueAxis domainAxis, Rectangle2D dataArea, ValueAxis rangeAxis, CrosshairState crosshairState, EntityCollection entities)
  {
    Shape entityArea = null;
    

    double x1 = dataset.getXValue(series, item);
    double y1 = dataset.getYValue(series, item);
    if ((Double.isNaN(y1)) || (Double.isNaN(x1))) {
      return;
    }
    
    PlotOrientation orientation = plot.getOrientation();
    RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
    RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
    double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
    double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);
    
    if (getItemShapeVisible(series, item)) {
      Shape shape = getItemShape(series, item);
      if (orientation == PlotOrientation.HORIZONTAL) {
        shape = ShapeUtilities.createTranslatedShape(shape, transY1, transX1);

      }
      else if (orientation == PlotOrientation.VERTICAL) {
        shape = ShapeUtilities.createTranslatedShape(shape, transX1, transY1);
      }
      
      entityArea = shape;
      if (shape.intersects(dataArea)) {
        if (getItemShapeFilled(series, item)) {
          if (useFillPaint) {
            g2.setPaint(getItemFillPaint(series, item));
          }
          else {
            g2.setPaint(getItemPaint(series, item));
          }
          g2.fill(shape);
        }
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
    }
    
    double xx = transX1;
    double yy = transY1;
    if (orientation == PlotOrientation.HORIZONTAL) {
      xx = transY1;
      yy = transX1;
    }
    

    if (isItemLabelVisible(series, item)) {
      drawItemLabel(g2, orientation, dataset, series, item, xx, yy, y1 < 0.0D);
    }
    

    int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
    int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
    updateCrosshairValues(crosshairState, x1, y1, domainAxisIndex, rangeAxisIndex, transX1, transY1, orientation);
    



    if ((entities != null) && (isPointInRect(dataArea, xx, yy))) {
      addEntity(entities, entityArea, dataset, series, item, xx, yy);
    }
  }
  









  public LegendItem getLegendItem(int datasetIndex, int series)
  {
    XYPlot plot = getPlot();
    if (plot == null) {
      return null;
    }
    
    LegendItem result = null;
    XYDataset dataset = plot.getDataset(datasetIndex);
    if ((dataset != null) && 
      (getItemVisible(series, 0))) {
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
      
      boolean shapeIsVisible = getItemShapeVisible(series, 0);
      Shape shape = lookupLegendShape(series);
      boolean shapeIsFilled = getItemShapeFilled(series, 0);
      Paint fillPaint = useFillPaint ? lookupSeriesFillPaint(series) : lookupSeriesPaint(series);
      

      boolean shapeOutlineVisible = drawOutlines;
      Paint outlinePaint = useOutlinePaint ? lookupSeriesOutlinePaint(series) : lookupSeriesPaint(series);
      

      Stroke outlineStroke = lookupSeriesOutlineStroke(series);
      boolean lineVisible = getItemLineVisible(series, 0);
      Stroke lineStroke = lookupSeriesStroke(series);
      Paint linePaint = lookupSeriesPaint(series);
      result = new LegendItem(label, description, toolTipText, urlText, shapeIsVisible, shape, shapeIsFilled, fillPaint, shapeOutlineVisible, outlinePaint, outlineStroke, lineVisible, legendLine, lineStroke, linePaint);
      



      result.setLabelFont(lookupLegendTextFont(series));
      Paint labelPaint = lookupLegendTextPaint(series);
      if (labelPaint != null) {
        result.setLabelPaint(labelPaint);
      }
      result.setSeriesKey(dataset.getSeriesKey(series));
      result.setSeriesIndex(series);
      result.setDataset(dataset);
      result.setDatasetIndex(datasetIndex);
    }
    

    return result;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    XYLineAndShapeRenderer clone = (XYLineAndShapeRenderer)super.clone();
    seriesLinesVisible = ((BooleanList)seriesLinesVisible.clone());
    
    if (legendLine != null) {
      legendLine = ShapeUtilities.clone(legendLine);
    }
    seriesShapesVisible = ((BooleanList)seriesShapesVisible.clone());
    
    seriesShapesFilled = ((BooleanList)seriesShapesFilled.clone());
    
    return clone;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYLineAndShapeRenderer)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    XYLineAndShapeRenderer that = (XYLineAndShapeRenderer)obj;
    if (!ObjectUtilities.equal(linesVisible, linesVisible)) {
      return false;
    }
    if (!ObjectUtilities.equal(seriesLinesVisible, seriesLinesVisible))
    {

      return false;
    }
    if (baseLinesVisible != baseLinesVisible) {
      return false;
    }
    if (!ShapeUtilities.equal(legendLine, legendLine)) {
      return false;
    }
    if (!ObjectUtilities.equal(shapesVisible, shapesVisible)) {
      return false;
    }
    if (!ObjectUtilities.equal(seriesShapesVisible, seriesShapesVisible))
    {

      return false;
    }
    if (baseShapesVisible != baseShapesVisible) {
      return false;
    }
    if (!ObjectUtilities.equal(shapesFilled, shapesFilled)) {
      return false;
    }
    if (!ObjectUtilities.equal(seriesShapesFilled, seriesShapesFilled))
    {

      return false;
    }
    if (baseShapesFilled != baseShapesFilled) {
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
    if (drawSeriesLineAsPath != drawSeriesLineAsPath) {
      return false;
    }
    return true;
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    legendLine = SerialUtilities.readShape(stream);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeShape(legendLine, stream);
  }
}
