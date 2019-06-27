package org.jfree.chart.renderer;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.data.xy.XYDataset;

public abstract interface PolarItemRenderer
{
  public abstract void drawSeries(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D, PlotRenderingInfo paramPlotRenderingInfo, PolarPlot paramPolarPlot, XYDataset paramXYDataset, int paramInt);
  
  public abstract void drawAngularGridLines(Graphics2D paramGraphics2D, PolarPlot paramPolarPlot, List paramList, Rectangle2D paramRectangle2D);
  
  public abstract void drawRadialGridLines(Graphics2D paramGraphics2D, PolarPlot paramPolarPlot, ValueAxis paramValueAxis, List paramList, Rectangle2D paramRectangle2D);
  
  public abstract LegendItem getLegendItem(int paramInt);
  
  public abstract PolarPlot getPlot();
  
  public abstract void setPlot(PolarPlot paramPolarPlot);
  
  public abstract void addChangeListener(RendererChangeListener paramRendererChangeListener);
  
  public abstract void removeChangeListener(RendererChangeListener paramRendererChangeListener);
}
