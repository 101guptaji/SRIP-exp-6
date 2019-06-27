package org.jfree.chart.renderer.category;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.MultiValueCategoryDataset;
import org.jfree.util.BooleanList;
import org.jfree.util.BooleanUtilities;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;



























































































public class ScatterRenderer
  extends AbstractCategoryItemRenderer
  implements Cloneable, PublicCloneable, Serializable
{
  private BooleanList seriesShapesFilled;
  private boolean baseShapesFilled;
  private boolean useFillPaint;
  private boolean drawOutlines;
  private boolean useOutlinePaint;
  private boolean useSeriesOffset;
  private double itemMargin;
  
  public ScatterRenderer()
  {
    seriesShapesFilled = new BooleanList();
    baseShapesFilled = true;
    useFillPaint = false;
    drawOutlines = false;
    useOutlinePaint = false;
    useSeriesOffset = true;
    itemMargin = 0.2D;
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
    Boolean flag = seriesShapesFilled.getBoolean(series);
    if (flag != null) {
      return flag.booleanValue();
    }
    
    return baseShapesFilled;
  }
  








  public void setSeriesShapesFilled(int series, Boolean filled)
  {
    seriesShapesFilled.setBoolean(series, filled);
    fireChangeEvent();
  }
  






  public void setSeriesShapesFilled(int series, boolean filled)
  {
    seriesShapesFilled.setBoolean(series, BooleanUtilities.valueOf(filled));
    
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
  






  public boolean getUseFillPaint()
  {
    return useFillPaint;
  }
  






  public void setUseFillPaint(boolean flag)
  {
    useFillPaint = flag;
    fireChangeEvent();
  }
  


















  public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass)
  {
    if (!getItemVisible(row, column)) {
      return;
    }
    int visibleRow = state.getVisibleSeriesIndex(row);
    if (visibleRow < 0) {
      return;
    }
    int visibleRowCount = state.getVisibleSeriesCount();
    
    PlotOrientation orientation = plot.getOrientation();
    
    MultiValueCategoryDataset d = (MultiValueCategoryDataset)dataset;
    List values = d.getValues(row, column);
    if (values == null) {
      return;
    }
    int valueCount = values.size();
    for (int i = 0; i < valueCount; i++) {
      double x1;
      double x1;
      if (useSeriesOffset) {
        x1 = domainAxis.getCategorySeriesMiddle(column, dataset.getColumnCount(), visibleRow, visibleRowCount, itemMargin, dataArea, plot.getDomainAxisEdge());

      }
      else
      {
        x1 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
      }
      
      Number n = (Number)values.get(i);
      double value = n.doubleValue();
      double y1 = rangeAxis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());
      

      Shape shape = getItemShape(row, column);
      if (orientation == PlotOrientation.HORIZONTAL) {
        shape = ShapeUtilities.createTranslatedShape(shape, y1, x1);
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        shape = ShapeUtilities.createTranslatedShape(shape, x1, y1);
      }
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
      LegendItem result = new LegendItem(label, description, toolTipText, urlText, true, shape, getItemShapeFilled(series, 0), fillPaint, shapeOutlineVisible, outlinePaint, outlineStroke, false, new Line2D.Double(-7.0D, 0.0D, 7.0D, 0.0D), getItemStroke(series, 0), getItemPaint(series, 0));
      



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
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ScatterRenderer)) {
      return false;
    }
    ScatterRenderer that = (ScatterRenderer)obj;
    if (!ObjectUtilities.equal(seriesShapesFilled, seriesShapesFilled))
    {
      return false;
    }
    if (baseShapesFilled != baseShapesFilled) {
      return false;
    }
    if (useFillPaint != useFillPaint) {
      return false;
    }
    if (drawOutlines != drawOutlines) {
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
    ScatterRenderer clone = (ScatterRenderer)super.clone();
    seriesShapesFilled = ((BooleanList)seriesShapesFilled.clone());
    
    return clone;
  }
  




  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
  }
}
