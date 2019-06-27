package org.jfree.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import javax.swing.JComponent;






















































public class PaintSample
  extends JComponent
{
  private Paint paint;
  private Dimension preferredSize;
  
  public PaintSample(Paint paint)
  {
    this.paint = paint;
    preferredSize = new Dimension(80, 12);
  }
  




  public Paint getPaint()
  {
    return paint;
  }
  




  public void setPaint(Paint paint)
  {
    this.paint = paint;
    repaint();
  }
  




  public Dimension getPreferredSize()
  {
    return preferredSize;
  }
  





  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;
    Dimension size = getSize();
    Insets insets = getInsets();
    double xx = left;
    double yy = top;
    double ww = size.getWidth() - left - right - 1.0D;
    double hh = size.getHeight() - top - bottom - 1.0D;
    Rectangle2D area = new Rectangle2D.Double(xx, yy, ww, hh);
    g2.setPaint(paint);
    g2.fill(area);
    g2.setPaint(Color.black);
    g2.draw(area);
  }
}
