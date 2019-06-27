package org.jfree.chart.block;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.jfree.ui.RectangleInsets;

public abstract interface BlockFrame
{
  public abstract RectangleInsets getInsets();
  
  public abstract void draw(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D);
}
