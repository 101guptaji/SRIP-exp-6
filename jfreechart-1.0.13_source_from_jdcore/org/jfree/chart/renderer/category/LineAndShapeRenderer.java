package org.jfree.chart.renderer.category;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.util.BooleanList;
import org.jfree.util.BooleanUtilities;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;





































































































































































public class LineAndShapeRenderer
  extends AbstractCategoryItemRenderer
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -197749519869226398L;
  /**
   * @deprecated
   */
  private Boolean linesVisible;
  private BooleanList seriesLinesVisible;
  private boolean baseLinesVisible;
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
  private boolean useFillPaint;
  private boolean drawOutlines;
  private boolean useOutlinePaint;
  private boolean useSeriesOffset;
  private double itemMargin;
  
  public LineAndShapeRenderer()
  {
    this(true, true);
  }
  






  public LineAndShapeRenderer(boolean lines, boolean shapes)
  {
    linesVisible = null;
    seriesLinesVisible = new BooleanList();
    baseLinesVisible = lines;
    shapesVisible = null;
    seriesShapesVisible = new BooleanList();
    baseShapesVisible = shapes;
    shapesFilled = null;
    seriesShapesFilled = new BooleanList();
    baseShapesFilled = true;
    useFillPaint = false;
    drawOutlines = true;
    useOutlinePaint = false;
    useSeriesOffset = false;
    itemMargin = 0.0D;
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
  







  public boolean getDrawOutlines()
  {
    return drawOutlines;
  }
  











  public void setDrawOutlines(boolean flag)
  {
    drawOutlines = flag;
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
  












  public boolean getItemShapeFilled(int series, int item)
  {
    return getSeriesShapesFilled(series);
  }
  









  public boolean getSeriesShapesFilled(int series)
  {
    if (shapesFilled != null) {
      return shapesFilled.booleanValue();
    }
    

    Boolean flag = seriesShapesFilled.getBoolean(series);
    if (flag != null) {
      return flag.booleanValue();
    }
    
    return baseShapesFilled;
  }
  









  /**
   * @deprecated
   */
  public Boolean getShapesFilled()
  {
    return shapesFilled;
  }
  







  /**
   * @deprecated
   */
  public void setShapesFilled(boolean filled)
  {
    if (filled) {
      setShapesFilled(Boolean.TRUE);
    }
    else {
      setShapesFilled(Boolean.FALSE);
    }
  }
  







  /**
   * @deprecated
   */
  public void setShapesFilled(Boolean filled)
  {
    shapesFilled = filled;
    fireChangeEvent();
  }
  








  public void setSeriesShapesFilled(int series, Boolean filled)
  {
    seriesShapesFilled.setBoolean(series, filled);
    fireChangeEvent();
  }
  









  public void setSeriesShapesFilled(int series, boolean filled)
  {
    setSeriesShapesFilled(series, BooleanUtilities.valueOf(filled));
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
  








  public boolean getUseFillPaint()
  {
    return useFillPaint;
  }
  








  public void setUseFillPaint(boolean flag)
  {
    useFillPaint = flag;
    fireChangeEvent();
  }
  









  public boolean getUseSeriesOffset()
  {
    return useSeriesOffset;
  }
  










  public void setUseSeriesOffset(boolean offset)
  {
    useSeriesOffset = offset;
    fireChangeEvent();
  }
  












  public double getItemMargin()
  {
    return itemMargin;
  }
  











  public void setItemMargin(double margin)
  {
    if ((margin < 0.0D) || (margin >= 1.0D)) {
      throw new IllegalArgumentException("Requires 0.0 <= margin < 1.0.");
    }
    itemMargin = margin;
    fireChangeEvent();
  }
  








  public LegendItem getLegendItem(int datasetIndex, int series)
  {
    CategoryPlot cp = getPlot();
    if (cp == null) {
      return null;
    }
    
    if ((isSeriesVisible(series)) && (isSeriesVisibleInLegend(series))) {
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
      Paint fillPaint = useFillPaint ? getItemFillPaint(series, 0) : paint;
      
      boolean shapeOutlineVisible = drawOutlines;
      Paint outlinePaint = useOutlinePaint ? getItemOutlinePaint(series, 0) : paint;
      
      Stroke outlineStroke = lookupSeriesOutlineStroke(series);
      boolean lineVisible = getItemLineVisible(series, 0);
      boolean shapeVisible = getItemShapeVisible(series, 0);
      LegendItem result = new LegendItem(label, description, toolTipText, urlText, shapeVisible, shape, getItemShapeFilled(series, 0), fillPaint, shapeOutlineVisible, outlinePaint, outlineStroke, lineVisible, new Line2D.Double(-7.0D, 0.0D, 7.0D, 0.0D), getItemStroke(series, 0), getItemPaint(series, 0));
      



      result.setLabelFont(lookupLegendTextFont(series));
      Paint labelPaint = lookupLegendTextPaint(series);
      if (labelPaint != null) {
        result.setLabelPaint(labelPaint);
      }
      result.setDataset(dataset);
      result.setDatasetIndex(datasetIndex);
      result.setSeriesKey(dataset.getRowKey(series));
      result.setSeriesIndex(series);
      return result;
    }
    return null;
  }
  





  public int getPassCount()
  {
    return 2;
  }
  


















  public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass)
  {
    if (!getItemVisible(row, column)) {
      return;
    }
    

    if ((!getItemLineVisible(row, column)) && (!getItemShapeVisible(row, column)))
    {
      return;
    }
    

    Number v = dataset.getValue(row, column);
    if (v == null) {
      return;
    }
    
    int visibleRow = state.getVisibleSeriesIndex(row);
    if (visibleRow < 0) {
      return;
    }
    int visibleRowCount = state.getVisibleSeriesCount();
    
    PlotOrientation orientation = plot.getOrientation();
    
    double x1;
    double x1;
    if (useSeriesOffset) {
      x1 = domainAxis.getCategorySeriesMiddle(column, dataset.getColumnCount(), visibleRow, visibleRowCount, itemMargin, dataArea, plot.getDomainAxisEdge());

    }
    else
    {
      x1 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
    }
    
    double value = v.doubleValue();
    double y1 = rangeAxis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
    

    if ((pass == 0) && (getItemLineVisible(row, column)) && 
      (column != 0)) {
      Number previousValue = dataset.getValue(row, column - 1);
      if (previousValue != null)
      {
        double previous = previousValue.doubleValue();
        double x0;
        double x0; if (useSeriesOffset) {
          x0 = domainAxis.getCategorySeriesMiddle(column - 1, dataset.getColumnCount(), visibleRow, visibleRowCount, itemMargin, dataArea, plot.getDomainAxisEdge());


        }
        else
        {

          x0 = domainAxis.getCategoryMiddle(column - 1, getColumnCount(), dataArea, plot.getDomainAxisEdge());
        }
        

        double y0 = rangeAxis.valueToJava2D(previous, dataArea, plot.getRangeAxisEdge());
        

        Line2D line = null;
        if (orientation == PlotOrientation.HORIZONTAL) {
          line = new Line2D.Double(y0, x0, y1, x1);
        }
        else if (orientation == PlotOrientation.VERTICAL) {
          line = new Line2D.Double(x0, y0, x1, y1);
        }
        g2.setPaint(getItemPaint(row, column));
        g2.setStroke(getItemStroke(row, column));
        g2.draw(line);
      }
    }
    

    if (pass == 1) {
      Shape shape = getItemShape(row, column);
      if (orientation == PlotOrientation.HORIZONTAL) {
        shape = ShapeUtilities.createTranslatedShape(shape, y1, x1);
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        shape = ShapeUtilities.createTranslatedShape(shape, x1, y1);
      }
      
      if (getItemShapeVisible(row, column)) {
        if (getItemShapeFilled(row, column)) {
          if (useFillPaint) {
            g2.setPaint(getItemFillPaint(row, column));
          }
          else {
            g2.setPaint(getItemPaint(row, column));
          }
          g2.fill(shape);
        }
        if (drawOutlines) {
          if (useOutlinePaint) {
            g2.setPaint(getItemOutlinePaint(row, column));
          }
          else {
            g2.setPaint(getItemPaint(row, column));
          }
          g2.setStroke(getItemOutlineStroke(row, column));
          g2.draw(shape);
        }
      }
      

      if (isItemLabelVisible(row, column)) {
        if (orientation == PlotOrientation.HORIZONTAL) {
          drawItemLabel(g2, orientation, dataset, row, column, y1, x1, value < 0.0D);

        }
        else if (orientation == PlotOrientation.VERTICAL) {
          drawItemLabel(g2, orientation, dataset, row, column, x1, y1, value < 0.0D);
        }
      }
      


      int datasetIndex = plot.indexOf(dataset);
      updateCrosshairValues(state.getCrosshairState(), dataset.getRowKey(row), dataset.getColumnKey(column), value, datasetIndex, x1, y1, orientation);
      



      EntityCollection entities = state.getEntityCollection();
      if (entities != null) {
        addItemEntity(entities, dataset, row, column, shape);
      }
    }
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof LineAndShapeRenderer)) {
      return false;
    }
    
    LineAndShapeRenderer that = (LineAndShapeRenderer)obj;
    if (baseLinesVisible != baseLinesVisible) {
      return false;
    }
    if (!ObjectUtilities.equal(seriesLinesVisible, seriesLinesVisible))
    {
      return false;
    }
    if (!ObjectUtilities.equal(linesVisible, linesVisible)) {
      return false;
    }
    if (baseShapesVisible != baseShapesVisible) {
      return false;
    }
    if (!ObjectUtilities.equal(seriesShapesVisible, seriesShapesVisible))
    {
      return false;
    }
    if (!ObjectUtilities.equal(shapesVisible, shapesVisible)) {
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
    if (useOutlinePaint != useOutlinePaint) {
      return false;
    }
    if (useSeriesOffset != useSeriesOffset) {
      return false;
    }
    if (itemMargin != itemMargin) {
      return false;
    }
    return super.equals(obj);
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    LineAndShapeRenderer clone = (LineAndShapeRenderer)super.clone();
    seriesLinesVisible = ((BooleanList)seriesLinesVisible.clone());
    
    seriesShapesVisible = ((BooleanList)seriesShapesVisible.clone());
    
    seriesShapesFilled = ((BooleanList)seriesShapesFilled.clone());
    
    return clone;
  }
}
