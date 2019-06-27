package org.jfree.chart.renderer.category;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.GradientPaintTransformer;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.util.PaintUtilities;
























































































public class WaterfallBarRenderer
  extends BarRenderer
{
  private static final long serialVersionUID = -2482910643727230911L;
  private transient Paint firstBarPaint;
  private transient Paint lastBarPaint;
  private transient Paint positiveBarPaint;
  private transient Paint negativeBarPaint;
  
  public WaterfallBarRenderer()
  {
    this(new GradientPaint(0.0F, 0.0F, new Color(34, 34, 255), 0.0F, 0.0F, new Color(102, 102, 255)), new GradientPaint(0.0F, 0.0F, new Color(34, 255, 34), 0.0F, 0.0F, new Color(102, 255, 102)), new GradientPaint(0.0F, 0.0F, new Color(255, 34, 34), 0.0F, 0.0F, new Color(255, 102, 102)), new GradientPaint(0.0F, 0.0F, new Color(255, 255, 34), 0.0F, 0.0F, new Color(255, 255, 102)));
  }
  






















  public WaterfallBarRenderer(Paint firstBarPaint, Paint positiveBarPaint, Paint negativeBarPaint, Paint lastBarPaint)
  {
    if (firstBarPaint == null) {
      throw new IllegalArgumentException("Null 'firstBarPaint' argument");
    }
    if (positiveBarPaint == null) {
      throw new IllegalArgumentException("Null 'positiveBarPaint' argument");
    }
    
    if (negativeBarPaint == null) {
      throw new IllegalArgumentException("Null 'negativeBarPaint' argument");
    }
    
    if (lastBarPaint == null) {
      throw new IllegalArgumentException("Null 'lastBarPaint' argument");
    }
    this.firstBarPaint = firstBarPaint;
    this.lastBarPaint = lastBarPaint;
    this.positiveBarPaint = positiveBarPaint;
    this.negativeBarPaint = negativeBarPaint;
    setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_VERTICAL));
    
    setMinimumBarLength(1.0D);
  }
  




  public Paint getFirstBarPaint()
  {
    return firstBarPaint;
  }
  





  public void setFirstBarPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument");
    }
    firstBarPaint = paint;
    fireChangeEvent();
  }
  




  public Paint getLastBarPaint()
  {
    return lastBarPaint;
  }
  





  public void setLastBarPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument");
    }
    lastBarPaint = paint;
    fireChangeEvent();
  }
  




  public Paint getPositiveBarPaint()
  {
    return positiveBarPaint;
  }
  




  public void setPositiveBarPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument");
    }
    positiveBarPaint = paint;
    fireChangeEvent();
  }
  




  public Paint getNegativeBarPaint()
  {
    return negativeBarPaint;
  }
  





  public void setNegativeBarPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument");
    }
    negativeBarPaint = paint;
    fireChangeEvent();
  }
  







  public Range findRangeBounds(CategoryDataset dataset)
  {
    if (dataset == null) {
      return null;
    }
    boolean allItemsNull = true;
    
    double minimum = 0.0D;
    double maximum = 0.0D;
    int columnCount = dataset.getColumnCount();
    for (int row = 0; row < dataset.getRowCount(); row++) {
      double runningTotal = 0.0D;
      for (int column = 0; column <= columnCount - 1; column++) {
        Number n = dataset.getValue(row, column);
        if (n != null) {
          allItemsNull = false;
          double value = n.doubleValue();
          if (column == columnCount - 1)
          {
            runningTotal = value;
          }
          else {
            runningTotal += value;
          }
          minimum = Math.min(minimum, runningTotal);
          maximum = Math.max(maximum, runningTotal);
        }
      }
    }
    
    if (!allItemsNull) {
      return new Range(minimum, maximum);
    }
    
    return null;
  }
  

























  public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass)
  {
    double previous = state.getSeriesRunningTotal();
    if (column == dataset.getColumnCount() - 1) {
      previous = 0.0D;
    }
    double current = 0.0D;
    Number n = dataset.getValue(row, column);
    if (n != null) {
      current = previous + n.doubleValue();
    }
    state.setSeriesRunningTotal(current);
    
    int categoryCount = getColumnCount();
    PlotOrientation orientation = plot.getOrientation();
    
    double rectX = 0.0D;
    double rectY = 0.0D;
    
    RectangleEdge rangeAxisLocation = plot.getRangeAxisEdge();
    

    double j2dy0 = rangeAxis.valueToJava2D(previous, dataArea, rangeAxisLocation);
    


    double j2dy1 = rangeAxis.valueToJava2D(current, dataArea, rangeAxisLocation);
    

    double valDiff = current - previous;
    if (j2dy1 < j2dy0) {
      double temp = j2dy1;
      j2dy1 = j2dy0;
      j2dy0 = temp;
    }
    

    double rectWidth = state.getBarWidth();
    

    double rectHeight = Math.max(getMinimumBarLength(), Math.abs(j2dy1 - j2dy0));
    

    Comparable seriesKey = dataset.getRowKey(row);
    Comparable categoryKey = dataset.getColumnKey(column);
    if (orientation == PlotOrientation.HORIZONTAL) {
      rectY = domainAxis.getCategorySeriesMiddle(categoryKey, seriesKey, dataset, getItemMargin(), dataArea, RectangleEdge.LEFT);
      

      rectX = j2dy0;
      rectHeight = state.getBarWidth();
      rectY -= rectHeight / 2.0D;
      rectWidth = Math.max(getMinimumBarLength(), Math.abs(j2dy1 - j2dy0));


    }
    else if (orientation == PlotOrientation.VERTICAL) {
      rectX = domainAxis.getCategorySeriesMiddle(categoryKey, seriesKey, dataset, getItemMargin(), dataArea, RectangleEdge.TOP);
      
      rectX -= rectWidth / 2.0D;
      rectY = j2dy0;
    }
    Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
    
    Paint seriesPaint = getFirstBarPaint();
    if (column == 0) {
      seriesPaint = getFirstBarPaint();
    }
    else if (column == categoryCount - 1) {
      seriesPaint = getLastBarPaint();

    }
    else if (valDiff < 0.0D) {
      seriesPaint = getNegativeBarPaint();
    }
    else if (valDiff > 0.0D) {
      seriesPaint = getPositiveBarPaint();
    }
    else {
      seriesPaint = getLastBarPaint();
    }
    
    if ((getGradientPaintTransformer() != null) && ((seriesPaint instanceof GradientPaint)))
    {
      GradientPaint gp = (GradientPaint)seriesPaint;
      seriesPaint = getGradientPaintTransformer().transform(gp, bar);
    }
    g2.setPaint(seriesPaint);
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
    
    CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
    
    if ((generator != null) && (isItemLabelVisible(row, column))) {
      drawItemLabel(g2, dataset, row, column, plot, generator, bar, valDiff < 0.0D);
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
    if (!super.equals(obj)) {
      return false;
    }
    if (!(obj instanceof WaterfallBarRenderer)) {
      return false;
    }
    WaterfallBarRenderer that = (WaterfallBarRenderer)obj;
    if (!PaintUtilities.equal(firstBarPaint, firstBarPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(lastBarPaint, lastBarPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(positiveBarPaint, positiveBarPaint))
    {
      return false;
    }
    if (!PaintUtilities.equal(negativeBarPaint, negativeBarPaint))
    {
      return false;
    }
    return true;
  }
  






  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(firstBarPaint, stream);
    SerialUtilities.writePaint(lastBarPaint, stream);
    SerialUtilities.writePaint(positiveBarPaint, stream);
    SerialUtilities.writePaint(negativeBarPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    firstBarPaint = SerialUtilities.readPaint(stream);
    lastBarPaint = SerialUtilities.readPaint(stream);
    positiveBarPaint = SerialUtilities.readPaint(stream);
    negativeBarPaint = SerialUtilities.readPaint(stream);
  }
}
