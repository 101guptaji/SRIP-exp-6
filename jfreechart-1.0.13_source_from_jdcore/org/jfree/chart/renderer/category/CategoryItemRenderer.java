package org.jfree.chart.renderer.category;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleEdge;

public abstract interface CategoryItemRenderer
  extends LegendItemSource
{
  public abstract int getPassCount();
  
  public abstract CategoryPlot getPlot();
  
  public abstract void setPlot(CategoryPlot paramCategoryPlot);
  
  public abstract void addChangeListener(RendererChangeListener paramRendererChangeListener);
  
  public abstract void removeChangeListener(RendererChangeListener paramRendererChangeListener);
  
  public abstract Range findRangeBounds(CategoryDataset paramCategoryDataset);
  
  public abstract CategoryItemRendererState initialise(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D, CategoryPlot paramCategoryPlot, int paramInt, PlotRenderingInfo paramPlotRenderingInfo);
  
  public abstract boolean getItemVisible(int paramInt1, int paramInt2);
  
  public abstract boolean isSeriesVisible(int paramInt);
  
  /**
   * @deprecated
   */
  public abstract Boolean getSeriesVisible();
  
  /**
   * @deprecated
   */
  public abstract void setSeriesVisible(Boolean paramBoolean);
  
  /**
   * @deprecated
   */
  public abstract void setSeriesVisible(Boolean paramBoolean, boolean paramBoolean1);
  
  public abstract Boolean getSeriesVisible(int paramInt);
  
  public abstract void setSeriesVisible(int paramInt, Boolean paramBoolean);
  
  public abstract void setSeriesVisible(int paramInt, Boolean paramBoolean, boolean paramBoolean1);
  
  public abstract boolean getBaseSeriesVisible();
  
  public abstract void setBaseSeriesVisible(boolean paramBoolean);
  
  public abstract void setBaseSeriesVisible(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract boolean isSeriesVisibleInLegend(int paramInt);
  
  /**
   * @deprecated
   */
  public abstract Boolean getSeriesVisibleInLegend();
  
  /**
   * @deprecated
   */
  public abstract void setSeriesVisibleInLegend(Boolean paramBoolean);
  
  /**
   * @deprecated
   */
  public abstract void setSeriesVisibleInLegend(Boolean paramBoolean, boolean paramBoolean1);
  
  public abstract Boolean getSeriesVisibleInLegend(int paramInt);
  
  public abstract void setSeriesVisibleInLegend(int paramInt, Boolean paramBoolean);
  
  public abstract void setSeriesVisibleInLegend(int paramInt, Boolean paramBoolean, boolean paramBoolean1);
  
  public abstract boolean getBaseSeriesVisibleInLegend();
  
  public abstract void setBaseSeriesVisibleInLegend(boolean paramBoolean);
  
  public abstract void setBaseSeriesVisibleInLegend(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract Paint getItemPaint(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract void setPaint(Paint paramPaint);
  
  public abstract Paint getSeriesPaint(int paramInt);
  
  public abstract void setSeriesPaint(int paramInt, Paint paramPaint);
  
  public abstract Paint getBasePaint();
  
  public abstract void setBasePaint(Paint paramPaint);
  
  public abstract Paint getItemOutlinePaint(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract void setOutlinePaint(Paint paramPaint);
  
  public abstract Paint getSeriesOutlinePaint(int paramInt);
  
  public abstract void setSeriesOutlinePaint(int paramInt, Paint paramPaint);
  
  public abstract Paint getBaseOutlinePaint();
  
  public abstract void setBaseOutlinePaint(Paint paramPaint);
  
  public abstract Stroke getItemStroke(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract void setStroke(Stroke paramStroke);
  
  public abstract Stroke getSeriesStroke(int paramInt);
  
  public abstract void setSeriesStroke(int paramInt, Stroke paramStroke);
  
  public abstract Stroke getBaseStroke();
  
  public abstract void setBaseStroke(Stroke paramStroke);
  
  public abstract Stroke getItemOutlineStroke(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract void setOutlineStroke(Stroke paramStroke);
  
  public abstract Stroke getSeriesOutlineStroke(int paramInt);
  
  public abstract void setSeriesOutlineStroke(int paramInt, Stroke paramStroke);
  
  public abstract Stroke getBaseOutlineStroke();
  
  public abstract void setBaseOutlineStroke(Stroke paramStroke);
  
  public abstract Shape getItemShape(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract void setShape(Shape paramShape);
  
  public abstract Shape getSeriesShape(int paramInt);
  
  public abstract void setSeriesShape(int paramInt, Shape paramShape);
  
  public abstract Shape getBaseShape();
  
  public abstract void setBaseShape(Shape paramShape);
  
  public abstract boolean isItemLabelVisible(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract void setItemLabelsVisible(boolean paramBoolean);
  
  /**
   * @deprecated
   */
  public abstract void setItemLabelsVisible(Boolean paramBoolean);
  
  /**
   * @deprecated
   */
  public abstract void setItemLabelsVisible(Boolean paramBoolean, boolean paramBoolean1);
  
  public abstract boolean isSeriesItemLabelsVisible(int paramInt);
  
  public abstract void setSeriesItemLabelsVisible(int paramInt, boolean paramBoolean);
  
  public abstract void setSeriesItemLabelsVisible(int paramInt, Boolean paramBoolean);
  
  public abstract void setSeriesItemLabelsVisible(int paramInt, Boolean paramBoolean, boolean paramBoolean1);
  
  public abstract Boolean getBaseItemLabelsVisible();
  
  public abstract void setBaseItemLabelsVisible(boolean paramBoolean);
  
  public abstract void setBaseItemLabelsVisible(Boolean paramBoolean);
  
  public abstract void setBaseItemLabelsVisible(Boolean paramBoolean, boolean paramBoolean1);
  
  public abstract CategoryItemLabelGenerator getItemLabelGenerator(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract void setItemLabelGenerator(CategoryItemLabelGenerator paramCategoryItemLabelGenerator);
  
  public abstract CategoryItemLabelGenerator getSeriesItemLabelGenerator(int paramInt);
  
  public abstract void setSeriesItemLabelGenerator(int paramInt, CategoryItemLabelGenerator paramCategoryItemLabelGenerator);
  
  public abstract CategoryItemLabelGenerator getBaseItemLabelGenerator();
  
  public abstract void setBaseItemLabelGenerator(CategoryItemLabelGenerator paramCategoryItemLabelGenerator);
  
  public abstract CategoryToolTipGenerator getToolTipGenerator(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract CategoryToolTipGenerator getToolTipGenerator();
  
  /**
   * @deprecated
   */
  public abstract void setToolTipGenerator(CategoryToolTipGenerator paramCategoryToolTipGenerator);
  
  public abstract CategoryToolTipGenerator getSeriesToolTipGenerator(int paramInt);
  
  public abstract void setSeriesToolTipGenerator(int paramInt, CategoryToolTipGenerator paramCategoryToolTipGenerator);
  
  public abstract CategoryToolTipGenerator getBaseToolTipGenerator();
  
  public abstract void setBaseToolTipGenerator(CategoryToolTipGenerator paramCategoryToolTipGenerator);
  
  public abstract Font getItemLabelFont(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract Font getItemLabelFont();
  
  /**
   * @deprecated
   */
  public abstract void setItemLabelFont(Font paramFont);
  
  public abstract Font getSeriesItemLabelFont(int paramInt);
  
  public abstract void setSeriesItemLabelFont(int paramInt, Font paramFont);
  
  public abstract Font getBaseItemLabelFont();
  
  public abstract void setBaseItemLabelFont(Font paramFont);
  
  public abstract Paint getItemLabelPaint(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract Paint getItemLabelPaint();
  
  /**
   * @deprecated
   */
  public abstract void setItemLabelPaint(Paint paramPaint);
  
  public abstract Paint getSeriesItemLabelPaint(int paramInt);
  
  public abstract void setSeriesItemLabelPaint(int paramInt, Paint paramPaint);
  
  public abstract Paint getBaseItemLabelPaint();
  
  public abstract void setBaseItemLabelPaint(Paint paramPaint);
  
  public abstract ItemLabelPosition getPositiveItemLabelPosition(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract ItemLabelPosition getPositiveItemLabelPosition();
  
  /**
   * @deprecated
   */
  public abstract void setPositiveItemLabelPosition(ItemLabelPosition paramItemLabelPosition);
  
  /**
   * @deprecated
   */
  public abstract void setPositiveItemLabelPosition(ItemLabelPosition paramItemLabelPosition, boolean paramBoolean);
  
  public abstract ItemLabelPosition getSeriesPositiveItemLabelPosition(int paramInt);
  
  public abstract void setSeriesPositiveItemLabelPosition(int paramInt, ItemLabelPosition paramItemLabelPosition);
  
  public abstract void setSeriesPositiveItemLabelPosition(int paramInt, ItemLabelPosition paramItemLabelPosition, boolean paramBoolean);
  
  public abstract ItemLabelPosition getBasePositiveItemLabelPosition();
  
  public abstract void setBasePositiveItemLabelPosition(ItemLabelPosition paramItemLabelPosition);
  
  public abstract void setBasePositiveItemLabelPosition(ItemLabelPosition paramItemLabelPosition, boolean paramBoolean);
  
  public abstract ItemLabelPosition getNegativeItemLabelPosition(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract ItemLabelPosition getNegativeItemLabelPosition();
  
  /**
   * @deprecated
   */
  public abstract void setNegativeItemLabelPosition(ItemLabelPosition paramItemLabelPosition);
  
  /**
   * @deprecated
   */
  public abstract void setNegativeItemLabelPosition(ItemLabelPosition paramItemLabelPosition, boolean paramBoolean);
  
  public abstract ItemLabelPosition getSeriesNegativeItemLabelPosition(int paramInt);
  
  public abstract void setSeriesNegativeItemLabelPosition(int paramInt, ItemLabelPosition paramItemLabelPosition);
  
  public abstract void setSeriesNegativeItemLabelPosition(int paramInt, ItemLabelPosition paramItemLabelPosition, boolean paramBoolean);
  
  public abstract ItemLabelPosition getBaseNegativeItemLabelPosition();
  
  public abstract void setBaseNegativeItemLabelPosition(ItemLabelPosition paramItemLabelPosition);
  
  public abstract void setBaseNegativeItemLabelPosition(ItemLabelPosition paramItemLabelPosition, boolean paramBoolean);
  
  public abstract CategoryURLGenerator getItemURLGenerator(int paramInt1, int paramInt2);
  
  /**
   * @deprecated
   */
  public abstract void setItemURLGenerator(CategoryURLGenerator paramCategoryURLGenerator);
  
  public abstract CategoryURLGenerator getSeriesItemURLGenerator(int paramInt);
  
  public abstract void setSeriesItemURLGenerator(int paramInt, CategoryURLGenerator paramCategoryURLGenerator);
  
  public abstract CategoryURLGenerator getBaseItemURLGenerator();
  
  public abstract void setBaseItemURLGenerator(CategoryURLGenerator paramCategoryURLGenerator);
  
  public abstract LegendItem getLegendItem(int paramInt1, int paramInt2);
  
  public abstract void drawBackground(Graphics2D paramGraphics2D, CategoryPlot paramCategoryPlot, Rectangle2D paramRectangle2D);
  
  public abstract void drawOutline(Graphics2D paramGraphics2D, CategoryPlot paramCategoryPlot, Rectangle2D paramRectangle2D);
  
  public abstract void drawItem(Graphics2D paramGraphics2D, CategoryItemRendererState paramCategoryItemRendererState, Rectangle2D paramRectangle2D, CategoryPlot paramCategoryPlot, CategoryAxis paramCategoryAxis, ValueAxis paramValueAxis, CategoryDataset paramCategoryDataset, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void drawDomainGridline(Graphics2D paramGraphics2D, CategoryPlot paramCategoryPlot, Rectangle2D paramRectangle2D, double paramDouble);
  
  public abstract void drawRangeGridline(Graphics2D paramGraphics2D, CategoryPlot paramCategoryPlot, ValueAxis paramValueAxis, Rectangle2D paramRectangle2D, double paramDouble);
  
  public abstract void drawDomainMarker(Graphics2D paramGraphics2D, CategoryPlot paramCategoryPlot, CategoryAxis paramCategoryAxis, CategoryMarker paramCategoryMarker, Rectangle2D paramRectangle2D);
  
  public abstract void drawRangeMarker(Graphics2D paramGraphics2D, CategoryPlot paramCategoryPlot, ValueAxis paramValueAxis, Marker paramMarker, Rectangle2D paramRectangle2D);
  
  public abstract double getItemMiddle(Comparable paramComparable1, Comparable paramComparable2, CategoryDataset paramCategoryDataset, CategoryAxis paramCategoryAxis, Rectangle2D paramRectangle2D, RectangleEdge paramRectangleEdge);
}
