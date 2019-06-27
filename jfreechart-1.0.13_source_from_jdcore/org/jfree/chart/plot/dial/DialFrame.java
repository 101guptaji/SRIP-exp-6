package org.jfree.chart.plot.dial;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public abstract interface DialFrame
  extends DialLayer
{
  public abstract Shape getWindow(Rectangle2D paramRectangle2D);
}
