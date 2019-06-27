package org.jfree.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D.Double;
import javax.swing.JPanel;
















































public class DrawablePanel
  extends JPanel
{
  private Drawable drawable;
  
  public DrawablePanel()
  {
    setOpaque(false);
  }
  





  public Drawable getDrawable()
  {
    return drawable;
  }
  





  public void setDrawable(Drawable drawable)
  {
    this.drawable = drawable;
    revalidate();
    repaint();
  }
  










  public Dimension getPreferredSize()
  {
    if ((drawable instanceof ExtendedDrawable))
    {
      ExtendedDrawable ed = (ExtendedDrawable)drawable;
      return ed.getPreferredSize();
    }
    return super.getPreferredSize();
  }
  










  public Dimension getMinimumSize()
  {
    if ((drawable instanceof ExtendedDrawable))
    {
      ExtendedDrawable ed = (ExtendedDrawable)drawable;
      return ed.getPreferredSize();
    }
    return super.getMinimumSize();
  }
  















  public boolean isOpaque()
  {
    if (drawable == null)
    {
      return false;
    }
    return super.isOpaque();
  }
  
























  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if (drawable == null)
    {
      return;
    }
    
    Graphics2D g2 = (Graphics2D)g.create(0, 0, getWidth(), getHeight());
    

    drawable.draw(g2, new Rectangle2D.Double(0.0D, 0.0D, getWidth(), getHeight()));
    g2.dispose();
  }
}
