package org.jfree.chart.renderer.xy;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.Layer;

public abstract interface XYItemRenderer
  extends LegendItemSource
{
  public abstract XYPlot getPlot();
  
  public abstract void setPlot(XYPlot paramXYPlot);
  
  public abstract int getPassCount();
  
  public abstract Range findDomainBounds(XYDataset paramXYDataset);
  
  public abstract Range findRangeBounds(XYDataset paramXYDataset);
  
  public abstract void addChangeListener(RendererChangeListener paramRendererChangeListener);
  
  public abstract void removeChangeListener(RendererChangeListener paramRendererChangeListener);
  
  public abstract boolean getItemVisible(int paramInt1, int paramInt2);
  
  public abstract boolean isSeriesVisible(int paramInt);
  
  public abstract Boolean getSeriesVisible(int paramInt);
  
  public abstract void setSeriesVisible(int paramInt, Boolean paramBoolean);
  
  public abstract void setSeriesVisible(int paramInt, Boolean paramBoolean, boolean paramBoolean1);
  
  public abstract boolean getBaseSeriesVisible();
  
  public abstract void setBaseSeriesVisible(boolean paramBoolean);
  
  public abstract void setBaseSeriesVisible(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract boolean isSeriesVisibleInLegend(int paramInt);
  
  public abstract Boolean getSeriesVisibleInLegend(int paramInt);
  
  public abstract void setSeriesVisibleInLegend(int paramInt, Boolean paramBoolean);
  
  public abstract void setSeriesVisibleInLegend(int paramInt, Boolean paramBoolean, boolean paramBoolean1);
  
  public abstract boolean getBaseSeriesVisibleInLegend();
  
  public abstract void setBaseSeriesVisibleInLegend(boolean paramBoolean);
  
  public abstract void setBaseSeriesVisibleInLegend(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract Paint getItemPaint(int paramInt1, int paramInt2);
  
  public abstract Paint getSeriesPaint(int paramInt);
  
  public abstract void setSeriesPaint(int paramInt, Paint paramPaint);
  
  public abstract Paint getBasePaint();
  
  public abstract void setBasePaint(Paint paramPaint);
  
  public abstract Paint getItemOutlinePaint(int paramInt1, int paramInt2);
  
  public abstract Paint getSeriesOutlinePaint(int paramInt);
  
  public abstract void setSeriesOutlinePaint(int paramInt, Paint paramPaint);
  
  public abstract Paint getBaseOutlinePaint();
  
  public abstract void setBaseOutlinePaint(Paint paramPaint);
  
  public abstract Stroke getItemStroke(int paramInt1, int paramInt2);
  
  public abstract Stroke getSeriesStroke(int paramInt);
  
  public abstract void setSeriesStroke(int paramInt, Stroke paramStroke);
  
  public abstract Stroke getBaseStroke();
  
  public abstract void setBaseStroke(Stroke paramStroke);
  
  public abstract Stroke getItemOutlineStroke(int paramInt1, int paramInt2);
  
  public abstract Stroke getSeriesOutlineStroke(int paramInt);
  
  public abstract void setSeriesOutlineStroke(int paramInt, Stroke paramStroke);
  
  public abstract Stroke getBaseOutlineStroke();
  
  public abstract void setBaseOutlineStroke(Stroke paramStroke);
  
  public abstract Shape getItemShape(int paramInt1, int paramInt2);
  
  public abstract Shape getSeriesShape(int paramInt);
  
  public abstract void setSeriesShape(int paramInt, Shape paramShape);
  
  public abstract Shape getBaseShape();
  
  public abstract void setBaseShape(Shape paramShape);
  
  public abstract LegendItem getLegendItem(int paramInt1, int paramInt2);
  
  public abstract XYSeriesLabelGenerator getLegendItemLabelGenerator();
  
  public abstract void setLegendItemLabelGenerator(XYSeriesLabelGenerator paramXYSeriesLabelGenerator);
  
  public abstract XYToolTipGenerator getToolTipGenerator(int paramInt1, int paramInt2);
  
  public abstract XYToolTipGenerator getSeriesToolTipGenerator(int paramInt);
  
  public abstract void setSeriesToolTipGenerator(int paramInt, XYToolTipGenerator paramXYToolTipGenerator);
  
  public abstract XYToolTipGenerator getBaseToolTipGenerator();
  
  public abstract void setBaseToolTipGenerator(XYToolTipGenerator paramXYToolTipGenerator);
  
  public abstract XYURLGenerator getURLGenerator();
  
  public abstract void setURLGenerator(XYURLGenerator paramXYURLGenerator);
  
  public abstract boolean isItemLabelVisible(int paramInt1, int paramInt2);
  
  public abstract boolean isSeriesItemLabelsVisible(int paramInt);
  
  public abstract void setSeriesItemLabelsVisible(int paramInt, boolean paramBoolean);
  
  public abstract void setSeriesItemLabelsVisible(int paramInt, Boolean paramBoolean);
  
  public abstract void setSeriesItemLabelsVisible(int paramInt, Boolean paramBoolean, boolean paramBoolean1);
  
  public abstract Boolean getBaseItemLabelsVisible();
  
  public abstract void setBaseItemLabelsVisible(boolean paramBoolean);
  
  public abstract void setBaseItemLabelsVisible(Boolean paramBoolean);
  
  public abstract void setBaseItemLabelsVisible(Boolean paramBoolean, boolean paramBoolean1);
  
  public abstract XYItemLabelGenerator getItemLabelGenerator(int paramInt1, int paramInt2);
  
  public abstract XYItemLabelGenerator getSeriesItemLabelGenerator(int paramInt);
  
  public abstract void setSeriesItemLabelGenerator(int paramInt, XYItemLabelGenerator paramXYItemLabelGenerator);
  
  public abstract XYItemLabelGenerator getBaseItemLabelGenerator();
  
  public abstract void setBaseItemLabelGenerator(XYItemLabelGenerator paramXYItemLabelGenerator);
  
  public abstract Font getItemLabelFont(int paramInt1, int paramInt2);
  
  public abstract Font getSeriesItemLabelFont(int paramInt);
  
  public abstract void setSeriesItemLabelFont(int paramInt, Font paramFont);
  
  public abstract Font getBaseItemLabelFont();
  
  public abstract void setBaseItemLabelFont(Font paramFont);
  
  public abstract Paint getItemLabelPaint(int paramInt1, int paramInt2);
  
  public abstract Paint getSeriesItemLabelPaint(int paramInt);
  
  public abstract void setSeriesItemLabelPaint(int paramInt, Paint paramPaint);
  
  public abstract Paint getBaseItemLabelPaint();
  
  public abstract void setBaseItemLabelPaint(Paint paramPaint);
  
  public abstract ItemLabelPosition getPositiveItemLabelPosition(int paramInt1, int paramInt2);
  
  public abstract ItemLabelPosition getSeriesPositiveItemLabelPosition(int paramInt);
  
  public abstract void setSeriesPositiveItemLabelPosition(int paramInt, ItemLabelPosition paramItemLabelPosition);
  
  public abstract void setSeriesPositiveItemLabelPosition(int paramInt, ItemLabelPosition paramItemLabelPosition, boolean paramBoolean);
  
  public abstract ItemLabelPosition getBasePositiveItemLabelPosition();
  
  public abstract void setBasePositiveItemLabelPosition(ItemLabelPosition paramItemLabelPosition);
  
  public abstract void setBasePositiveItemLabelPosition(ItemLabelPosition paramItemLabelPosition, boolean paramBoolean);
  
  public abstract ItemLabelPosition getNegativeItemLabelPosition(int paramInt1, int paramInt2);
  
  public abstract ItemLabelPosition getSeriesNegativeItemLabelPosition(int paramInt);
  
  public abstract void setSeriesNegativeItemLabelPosition(int paramInt, ItemLabelPosition paramItemLabelPosition);
  
  public abstract void setSeriesNegativeItemLabelPosition(int paramInt, ItemLabelPosition paramItemLabelPosition, boolean paramBoolean);
  
  public abstract ItemLabelPosition getBaseNegativeItemLabelPosition();
  
  public abstract void setBaseNegativeItemLabelPosition(ItemLabelPosition paramItemLabelPosition);
  
  public abstract void setBaseNegativeItemLabelPosition(ItemLabelPosition paramItemLabelPosition, boolean paramBoolean);
  
  public abstract void addAnnotation(XYAnnotation paramXYAnnotation);
  
  public abstract void addAnnotation(XYAnnotation paramXYAnnotation, Layer paramLayer);
  
  public abstract boolean removeAnnotation(XYAnnotation paramXYAnnotation);
  
  public abstract void removeAnnotations();
  
  public abstract void drawAnnotations(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D, ValueAxis paramValueAxis1, ValueAxis paramValueAxis2, Layer paramLayer, PlotRenderingInfo paramPlotRenderingInfo);
  
  public abstract XYItemRendererState initialise(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D, XYPlot paramXYPlot, XYDataset paramXYDataset, PlotRenderingInfo paramPlotRenderingInfo);
  
  public abstract void drawItem(Graphics2D paramGraphics2D, XYItemRendererState paramXYItemRendererState, Rectangle2D paramRectangle2D, PlotRenderingInfo paramPlotRenderingInfo, XYPlot paramXYPlot, ValueAxis paramValueAxis1, ValueAxis paramValueAxis2, XYDataset paramXYDataset, int paramInt1, int paramInt2, CrosshairState paramCrosshairState, int paramInt3);
  
  public abstract void fillDomainGridBand(Graphics2D paramGraphics2D, XYPlot paramXYPlot, ValueAxis paramValueAxis, Rectangle2D paramRectangle2D, double paramDouble1, double paramDouble2);
  
  public abstract void fillRangeGridBand(Graphics2D paramGraphics2D, XYPlot paramXYPlot, ValueAxis paramValueAxis, Rectangle2D paramRectangle2D, double paramDouble1, double paramDouble2);
  
  public abstract void drawDomainGridLine(Graphics2D paramGraphics2D, XYPlot paramXYPlot, ValueAxis paramValueAxis, Rectangle2D paramRectangle2D, double paramDouble);
  
  public abstract void drawRangeLine(Graphics2D paramGraphics2D, XYPlot paramXYPlot, ValueAxis paramValueAxis, Rectangle2D paramRectangle2D, double paramDouble, Paint paramPaint, Stroke paramStroke);
  
  public abstract void drawDomainMarker(Graphics2D paramGraphics2D, XYPlot paramXYPlot, ValueAxis paramValueAxis, Marker paramMarker, Rectangle2D paramRectangle2D);
  
  public abstract void drawRangeMarker(Graphics2D paramGraphics2D, XYPlot paramXYPlot, ValueAxis paramValueAxis, Marker paramMarker, Rectangle2D paramRectangle2D);
  
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
  
  /**
   * @deprecated
   */
  public abstract void setPaint(Paint paramPaint);
  
  /**
   * @deprecated
   */
  public abstract void setOutlinePaint(Paint paramPaint);
  
  /**
   * @deprecated
   */
  public abstract void setStroke(Stroke paramStroke);
  
  /**
   * @deprecated
   */
  public abstract void setOutlineStroke(Stroke paramStroke);
  
  /**
   * @deprecated
   */
  public abstract void setShape(Shape paramShape);
  
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
  
  /**
   * @deprecated
   */
  public abstract void setItemLabelGenerator(XYItemLabelGenerator paramXYItemLabelGenerator);
  
  /**
   * @deprecated
   */
  public abstract void setToolTipGenerator(XYToolTipGenerator paramXYToolTipGenerator);
  
  /**
   * @deprecated
   */
  public abstract Font getItemLabelFont();
  
  /**
   * @deprecated
   */
  public abstract void setItemLabelFont(Font paramFont);
  
  /**
   * @deprecated
   */
  public abstract Paint getItemLabelPaint();
  
  /**
   * @deprecated
   */
  public abstract void setItemLabelPaint(Paint paramPaint);
  
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
}
