package org.jfree.chart.renderer.category;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.StatisticalCategoryDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.GradientPaintTransformer;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;

















































































public class StatisticalBarRenderer
  extends BarRenderer
  implements CategoryItemRenderer, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -4986038395414039117L;
  private transient Paint errorIndicatorPaint;
  private transient Stroke errorIndicatorStroke;
  
  public StatisticalBarRenderer()
  {
    errorIndicatorPaint = Color.gray;
    errorIndicatorStroke = new BasicStroke(1.0F);
  }
  







  public Paint getErrorIndicatorPaint()
  {
    return errorIndicatorPaint;
  }
  








  public void setErrorIndicatorPaint(Paint paint)
  {
    errorIndicatorPaint = paint;
    fireChangeEvent();
  }
  









  public Stroke getErrorIndicatorStroke()
  {
    return errorIndicatorStroke;
  }
  











  public void setErrorIndicatorStroke(Stroke stroke)
  {
    errorIndicatorStroke = stroke;
    fireChangeEvent();
  }
  
























  public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset data, int row, int column, int pass)
  {
    int visibleRow = state.getVisibleSeriesIndex(row);
    if (visibleRow < 0) {
      return;
    }
    
    if (!(data instanceof StatisticalCategoryDataset)) {
      throw new IllegalArgumentException("Requires StatisticalCategoryDataset.");
    }
    
    StatisticalCategoryDataset statData = (StatisticalCategoryDataset)data;
    
    PlotOrientation orientation = plot.getOrientation();
    if (orientation == PlotOrientation.HORIZONTAL) {
      drawHorizontalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, statData, visibleRow, row, column);

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      drawVerticalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, statData, visibleRow, row, column);
    }
  }
  
























  protected void drawHorizontalItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, StatisticalCategoryDataset dataset, int visibleRow, int row, int column)
  {
    RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
    

    double rectY = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, xAxisLocation);
    

    int seriesCount = state.getVisibleSeriesCount() >= 0 ? state.getVisibleSeriesCount() : getRowCount();
    
    int categoryCount = getColumnCount();
    if (seriesCount > 1) {
      double seriesGap = dataArea.getHeight() * getItemMargin() / (categoryCount * (seriesCount - 1));
      
      rectY += visibleRow * (state.getBarWidth() + seriesGap);
    }
    else {
      rectY += visibleRow * state.getBarWidth();
    }
    

    Number meanValue = dataset.getMeanValue(row, column);
    if (meanValue == null) {
      return;
    }
    double value = meanValue.doubleValue();
    double base = 0.0D;
    double lclip = getLowerClip();
    double uclip = getUpperClip();
    
    if (uclip <= 0.0D) {
      if (value >= uclip) {
        return;
      }
      base = uclip;
      if (value <= lclip) {
        value = lclip;
      }
    }
    else if (lclip <= 0.0D) {
      if (value >= uclip) {
        value = uclip;

      }
      else if (value <= lclip) {
        value = lclip;
      }
    }
    else
    {
      if (value <= lclip) {
        return;
      }
      base = getLowerClip();
      if (value >= uclip) {
        value = uclip;
      }
    }
    
    RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
    double transY1 = rangeAxis.valueToJava2D(base, dataArea, yAxisLocation);
    double transY2 = rangeAxis.valueToJava2D(value, dataArea, yAxisLocation);
    
    double rectX = Math.min(transY2, transY1);
    
    double rectHeight = state.getBarWidth();
    double rectWidth = Math.abs(transY2 - transY1);
    
    Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
    
    Paint itemPaint = getItemPaint(row, column);
    GradientPaintTransformer t = getGradientPaintTransformer();
    if ((t != null) && ((itemPaint instanceof GradientPaint))) {
      itemPaint = t.transform((GradientPaint)itemPaint, bar);
    }
    g2.setPaint(itemPaint);
    g2.fill(bar);
    

    if ((isDrawBarOutline()) && (state.getBarWidth() > 3.0D))
    {
      Stroke stroke = getItemOutlineStroke(row, column);
      Paint paint = getItemOutlinePaint(row, column);
      if ((stroke != null) && (paint != null)) {
        g2.setStroke(stroke);
        g2.setPaint(paint);
        g2.draw(bar);
      }
    }
    

    Number n = dataset.getStdDevValue(row, column);
    if (n != null) {
      double valueDelta = n.doubleValue();
      double highVal = rangeAxis.valueToJava2D(meanValue.doubleValue() + valueDelta, dataArea, yAxisLocation);
      
      double lowVal = rangeAxis.valueToJava2D(meanValue.doubleValue() - valueDelta, dataArea, yAxisLocation);
      

      if (errorIndicatorPaint != null) {
        g2.setPaint(errorIndicatorPaint);
      }
      else {
        g2.setPaint(getItemOutlinePaint(row, column));
      }
      if (errorIndicatorStroke != null) {
        g2.setStroke(errorIndicatorStroke);
      }
      else {
        g2.setStroke(getItemOutlineStroke(row, column));
      }
      Line2D line = null;
      line = new Line2D.Double(lowVal, rectY + rectHeight / 2.0D, highVal, rectY + rectHeight / 2.0D);
      
      g2.draw(line);
      line = new Line2D.Double(highVal, rectY + rectHeight * 0.25D, highVal, rectY + rectHeight * 0.75D);
      
      g2.draw(line);
      line = new Line2D.Double(lowVal, rectY + rectHeight * 0.25D, lowVal, rectY + rectHeight * 0.75D);
      
      g2.draw(line);
    }
    
    CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
    
    if ((generator != null) && (isItemLabelVisible(row, column))) {
      drawItemLabel(g2, dataset, row, column, plot, generator, bar, value < 0.0D);
    }
    


    EntityCollection entities = state.getEntityCollection();
    if (entities != null) {
      addItemEntity(entities, dataset, row, column, bar);
    }
  }
  
























  protected void drawVerticalItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, StatisticalCategoryDataset dataset, int visibleRow, int row, int column)
  {
    RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
    

    double rectX = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, xAxisLocation);
    

    int seriesCount = state.getVisibleSeriesCount() >= 0 ? state.getVisibleSeriesCount() : getRowCount();
    
    int categoryCount = getColumnCount();
    if (seriesCount > 1) {
      double seriesGap = dataArea.getWidth() * getItemMargin() / (categoryCount * (seriesCount - 1));
      
      rectX += visibleRow * (state.getBarWidth() + seriesGap);
    }
    else {
      rectX += visibleRow * state.getBarWidth();
    }
    

    Number meanValue = dataset.getMeanValue(row, column);
    if (meanValue == null) {
      return;
    }
    
    double value = meanValue.doubleValue();
    double base = 0.0D;
    double lclip = getLowerClip();
    double uclip = getUpperClip();
    
    if (uclip <= 0.0D) {
      if (value >= uclip) {
        return;
      }
      base = uclip;
      if (value <= lclip) {
        value = lclip;
      }
    }
    else if (lclip <= 0.0D) {
      if (value >= uclip) {
        value = uclip;

      }
      else if (value <= lclip) {
        value = lclip;
      }
    }
    else
    {
      if (value <= lclip) {
        return;
      }
      base = getLowerClip();
      if (value >= uclip) {
        value = uclip;
      }
    }
    
    RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
    double transY1 = rangeAxis.valueToJava2D(base, dataArea, yAxisLocation);
    double transY2 = rangeAxis.valueToJava2D(value, dataArea, yAxisLocation);
    
    double rectY = Math.min(transY2, transY1);
    
    double rectWidth = state.getBarWidth();
    double rectHeight = Math.abs(transY2 - transY1);
    
    Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
    
    Paint itemPaint = getItemPaint(row, column);
    GradientPaintTransformer t = getGradientPaintTransformer();
    if ((t != null) && ((itemPaint instanceof GradientPaint))) {
      itemPaint = t.transform((GradientPaint)itemPaint, bar);
    }
    g2.setPaint(itemPaint);
    g2.fill(bar);
    
    if ((isDrawBarOutline()) && (state.getBarWidth() > 3.0D))
    {
      Stroke stroke = getItemOutlineStroke(row, column);
      Paint paint = getItemOutlinePaint(row, column);
      if ((stroke != null) && (paint != null)) {
        g2.setStroke(stroke);
        g2.setPaint(paint);
        g2.draw(bar);
      }
    }
    

    Number n = dataset.getStdDevValue(row, column);
    if (n != null) {
      double valueDelta = n.doubleValue();
      double highVal = rangeAxis.valueToJava2D(meanValue.doubleValue() + valueDelta, dataArea, yAxisLocation);
      
      double lowVal = rangeAxis.valueToJava2D(meanValue.doubleValue() - valueDelta, dataArea, yAxisLocation);
      

      if (errorIndicatorPaint != null) {
        g2.setPaint(errorIndicatorPaint);
      }
      else {
        g2.setPaint(getItemOutlinePaint(row, column));
      }
      if (errorIndicatorStroke != null) {
        g2.setStroke(errorIndicatorStroke);
      }
      else {
        g2.setStroke(getItemOutlineStroke(row, column));
      }
      
      Line2D line = null;
      line = new Line2D.Double(rectX + rectWidth / 2.0D, lowVal, rectX + rectWidth / 2.0D, highVal);
      
      g2.draw(line);
      line = new Line2D.Double(rectX + rectWidth / 2.0D - 5.0D, highVal, rectX + rectWidth / 2.0D + 5.0D, highVal);
      
      g2.draw(line);
      line = new Line2D.Double(rectX + rectWidth / 2.0D - 5.0D, lowVal, rectX + rectWidth / 2.0D + 5.0D, lowVal);
      
      g2.draw(line);
    }
    
    CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
    
    if ((generator != null) && (isItemLabelVisible(row, column))) {
      drawItemLabel(g2, dataset, row, column, plot, generator, bar, value < 0.0D);
    }
    


    EntityCollection entities = state.getEntityCollection();
    if (entities != null) {
      addItemEntity(entities, dataset, row, column, bar);
    }
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StatisticalBarRenderer)) {
      return false;
    }
    StatisticalBarRenderer that = (StatisticalBarRenderer)obj;
    if (!PaintUtilities.equal(errorIndicatorPaint, errorIndicatorPaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(errorIndicatorStroke, errorIndicatorStroke))
    {
      return false;
    }
    return super.equals(obj);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(errorIndicatorPaint, stream);
    SerialUtilities.writeStroke(errorIndicatorStroke, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    errorIndicatorPaint = SerialUtilities.readPaint(stream);
    errorIndicatorStroke = SerialUtilities.readStroke(stream);
  }
}
