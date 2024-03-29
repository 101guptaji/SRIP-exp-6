package org.jfree.chart.renderer.category;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.KeyToGroupMap;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.PublicCloneable;
































































public class GroupedStackedBarRenderer
  extends StackedBarRenderer
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -2725921399005922939L;
  private KeyToGroupMap seriesToGroupMap;
  
  public GroupedStackedBarRenderer()
  {
    seriesToGroupMap = new KeyToGroupMap();
  }
  





  public void setSeriesToGroupMap(KeyToGroupMap map)
  {
    if (map == null) {
      throw new IllegalArgumentException("Null 'map' argument.");
    }
    seriesToGroupMap = map;
    fireChangeEvent();
  }
  








  public Range findRangeBounds(CategoryDataset dataset)
  {
    if (dataset == null) {
      return null;
    }
    Range r = DatasetUtilities.findStackedRangeBounds(dataset, seriesToGroupMap);
    
    return r;
  }
  














  protected void calculateBarWidth(CategoryPlot plot, Rectangle2D dataArea, int rendererIndex, CategoryItemRendererState state)
  {
    CategoryAxis xAxis = plot.getDomainAxisForDataset(rendererIndex);
    CategoryDataset data = plot.getDataset(rendererIndex);
    if (data != null) {
      PlotOrientation orientation = plot.getOrientation();
      double space = 0.0D;
      if (orientation == PlotOrientation.HORIZONTAL) {
        space = dataArea.getHeight();
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        space = dataArea.getWidth();
      }
      double maxWidth = space * getMaximumBarWidth();
      int groups = seriesToGroupMap.getGroupCount();
      int categories = data.getColumnCount();
      int columns = groups * categories;
      double categoryMargin = 0.0D;
      double itemMargin = 0.0D;
      if (categories > 1) {
        categoryMargin = xAxis.getCategoryMargin();
      }
      if (groups > 1) {
        itemMargin = getItemMargin();
      }
      
      double used = space * (1.0D - xAxis.getLowerMargin() - xAxis.getUpperMargin() - categoryMargin - itemMargin);
      

      if (columns > 0) {
        state.setBarWidth(Math.min(used / columns, maxWidth));
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
    
    int groupCount = seriesToGroupMap.getGroupCount();
    int groupIndex = seriesToGroupMap.getGroupIndex(seriesToGroupMap.getGroup(plot.getDataset(plot.getIndexOf(this)).getRowKey(row)));
    

    int categoryCount = getColumnCount();
    if (groupCount > 1) {
      double groupGap = space * getItemMargin() / (categoryCount * (groupCount - 1));
      
      double groupW = calculateSeriesWidth(space, domainAxis, categoryCount, groupCount);
      
      barW0 = barW0 + groupIndex * (groupW + groupGap) + groupW / 2.0D - state.getBarWidth() / 2.0D;
    }
    else
    {
      barW0 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge()) - state.getBarWidth() / 2.0D;
    }
    

    return barW0;
  }
  
























  public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass)
  {
    Number dataValue = dataset.getValue(row, column);
    if (dataValue == null) {
      return;
    }
    
    double value = dataValue.doubleValue();
    Comparable group = seriesToGroupMap.getGroup(dataset.getRowKey(row));
    
    PlotOrientation orientation = plot.getOrientation();
    double barW0 = calculateBarW0(plot, orientation, dataArea, domainAxis, state, row, column);
    

    double positiveBase = 0.0D;
    double negativeBase = 0.0D;
    
    for (int i = 0; i < row; i++) {
      if (group.equals(seriesToGroupMap.getGroup(dataset.getRowKey(i))))
      {
        Number v = dataset.getValue(i, column);
        if (v != null) {
          double d = v.doubleValue();
          if (d > 0.0D) {
            positiveBase += d;
          }
          else {
            negativeBase += d;
          }
        }
      }
    }
    


    boolean positive = value > 0.0D;
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
    RectangleEdge location = plot.getRangeAxisEdge();
    double translatedValue; double translatedBase; double translatedValue; if (value > 0.0D) {
      double translatedBase = rangeAxis.valueToJava2D(positiveBase, dataArea, location);
      
      translatedValue = rangeAxis.valueToJava2D(positiveBase + value, dataArea, location);
    }
    else
    {
      translatedBase = rangeAxis.valueToJava2D(negativeBase, dataArea, location);
      
      translatedValue = rangeAxis.valueToJava2D(negativeBase + value, dataArea, location);
    }
    
    double barL0 = Math.min(translatedBase, translatedValue);
    double barLength = Math.max(Math.abs(translatedValue - translatedBase), getMinimumBarLength());
    

    Rectangle2D bar = null;
    if (orientation == PlotOrientation.HORIZONTAL) {
      bar = new Rectangle2D.Double(barL0, barW0, barLength, state.getBarWidth());
    }
    else
    {
      bar = new Rectangle2D.Double(barW0, barL0, state.getBarWidth(), barLength);
    }
    
    getBarPainter().paintBar(g2, this, row, column, bar, barBase);
    
    CategoryItemLabelGenerator generator = getItemLabelGenerator(row, column);
    
    if ((generator != null) && (isItemLabelVisible(row, column))) {
      drawItemLabel(g2, dataset, row, column, plot, generator, bar, value < 0.0D);
    }
    


    if (state.getInfo() != null) {
      EntityCollection entities = state.getEntityCollection();
      if (entities != null) {
        addItemEntity(entities, dataset, row, column, bar);
      }
    }
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof GroupedStackedBarRenderer)) {
      return false;
    }
    GroupedStackedBarRenderer that = (GroupedStackedBarRenderer)obj;
    if (!seriesToGroupMap.equals(seriesToGroupMap)) {
      return false;
    }
    return super.equals(obj);
  }
}
