package org.jfree.chart.block;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.jfree.ui.Drawable;
import org.jfree.ui.Size2D;

public abstract interface Block
  extends Drawable
{
  public abstract String getID();
  
  public abstract void setID(String paramString);
  
  public abstract Size2D arrange(Graphics2D paramGraphics2D);
  
  public abstract Size2D arrange(Graphics2D paramGraphics2D, RectangleConstraint paramRectangleConstraint);
  
  public abstract Rectangle2D getBounds();
  
  public abstract void setBounds(Rectangle2D paramRectangle2D);
  
  public abstract Object draw(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D, Object paramObject);
}
