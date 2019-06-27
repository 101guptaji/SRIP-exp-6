package org.jfree.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;




















































public class StrokeSample
  extends JComponent
  implements ListCellRenderer
{
  private Stroke stroke;
  private Dimension preferredSize;
  
  public StrokeSample(Stroke stroke)
  {
    this.stroke = stroke;
    preferredSize = new Dimension(80, 18);
    setPreferredSize(preferredSize);
  }
  




  public Stroke getStroke()
  {
    return stroke;
  }
  




  public void setStroke(Stroke stroke)
  {
    this.stroke = stroke;
    repaint();
  }
  




  public Dimension getPreferredSize()
  {
    return preferredSize;
  }
  





  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
    Dimension size = getSize();
    Insets insets = getInsets();
    double xx = left;
    double yy = top;
    double ww = size.getWidth() - left - right;
    double hh = size.getHeight() - top - bottom;
    

    Point2D one = new Point2D.Double(xx + 6.0D, yy + hh / 2.0D);
    
    Point2D two = new Point2D.Double(xx + ww - 6.0D, yy + hh / 2.0D);
    
    Ellipse2D circle1 = new Ellipse2D.Double(one.getX() - 5.0D, one.getY() - 5.0D, 10.0D, 10.0D);
    
    Ellipse2D circle2 = new Ellipse2D.Double(two.getX() - 6.0D, two.getY() - 5.0D, 10.0D, 10.0D);
    


    g2.draw(circle1);
    g2.fill(circle1);
    g2.draw(circle2);
    g2.fill(circle2);
    

    Line2D line = new Line2D.Double(one, two);
    if (stroke != null) {
      g2.setStroke(stroke);
      g2.draw(line);
    }
  }
  













  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
  {
    if ((value instanceof Stroke)) {
      setStroke((Stroke)value);
    }
    else {
      setStroke(null);
    }
    return this;
  }
}
