package org.jfree.chart.plot;

import java.awt.geom.Point2D;

public abstract interface Zoomable
{
  public abstract boolean isDomainZoomable();
  
  public abstract boolean isRangeZoomable();
  
  public abstract PlotOrientation getOrientation();
  
  public abstract void zoomDomainAxes(double paramDouble, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D);
  
  public abstract void zoomDomainAxes(double paramDouble, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D, boolean paramBoolean);
  
  public abstract void zoomDomainAxes(double paramDouble1, double paramDouble2, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D);
  
  public abstract void zoomRangeAxes(double paramDouble, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D);
  
  public abstract void zoomRangeAxes(double paramDouble, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D, boolean paramBoolean);
  
  public abstract void zoomRangeAxes(double paramDouble1, double paramDouble2, PlotRenderingInfo paramPlotRenderingInfo, Point2D paramPoint2D);
}
