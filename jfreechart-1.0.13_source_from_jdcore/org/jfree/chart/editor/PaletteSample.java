package org.jfree.chart.editor;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.jfree.chart.plot.ColorPalette;
























































/**
 * @deprecated
 */
public class PaletteSample
  extends JComponent
  implements ListCellRenderer
{
  private ColorPalette palette;
  private Dimension preferredSize;
  
  public PaletteSample(ColorPalette palette)
  {
    this.palette = palette;
    preferredSize = new Dimension(80, 18);
  }
  
















  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
  {
    if ((value instanceof PaletteSample)) {
      PaletteSample in = (PaletteSample)value;
      setPalette(in.getPalette());
    }
    return this;
  }
  




  public ColorPalette getPalette()
  {
    return palette;
  }
  




  public Dimension getPreferredSize()
  {
    return preferredSize;
  }
  





  public void paintComponent(Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    

    Dimension size = getSize();
    Insets insets = getInsets();
    double ww = size.getWidth() - left - right;
    double hh = size.getHeight() - top - bottom;
    
    g2.setStroke(new BasicStroke(1.0F));
    
    double y1 = top;
    double y2 = y1 + hh;
    double xx = left;
    Line2D line = new Line2D.Double();
    int count = 0;
    while (xx <= left + ww) {
      count++;
      line.setLine(xx, y1, xx, y2);
      g2.setPaint(palette.getColor(count));
      g2.draw(line);
      xx += 1.0D;
    }
  }
  




  public void setPalette(ColorPalette palette)
  {
    this.palette = palette;
    repaint();
  }
}
