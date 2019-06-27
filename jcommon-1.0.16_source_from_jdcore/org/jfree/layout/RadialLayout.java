package org.jfree.layout;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.io.Serializable;


























































public class RadialLayout
  implements LayoutManager, Serializable
{
  private static final long serialVersionUID = -7582156799248315534L;
  private int minWidth = 0;
  

  private int minHeight = 0;
  

  private int maxCompWidth = 0;
  

  private int maxCompHeight = 0;
  

  private int preferredWidth = 0;
  

  private int preferredHeight = 0;
  

  private boolean sizeUnknown = true;
  







  public RadialLayout() {}
  







  public void addLayoutComponent(Component comp) {}
  







  public void removeLayoutComponent(Component comp) {}
  







  public void addLayoutComponent(String name, Component comp) {}
  







  public void removeLayoutComponent(String name, Component comp) {}
  






  private void setSizes(Container parent)
  {
    int nComps = parent.getComponentCount();
    
    preferredWidth = 0;
    preferredHeight = 0;
    minWidth = 0;
    minHeight = 0;
    for (int i = 0; i < nComps; i++) {
      Component c = parent.getComponent(i);
      if (c.isVisible()) {
        Dimension d = c.getPreferredSize();
        if (maxCompWidth < width) {
          maxCompWidth = width;
        }
        if (maxCompHeight < height) {
          maxCompHeight = height;
        }
        preferredWidth += width;
        preferredHeight += height;
      }
    }
    preferredWidth /= 2;
    preferredHeight /= 2;
    minWidth = preferredWidth;
    minHeight = preferredHeight;
  }
  







  public Dimension preferredLayoutSize(Container parent)
  {
    Dimension dim = new Dimension(0, 0);
    setSizes(parent);
    

    Insets insets = parent.getInsets();
    width = (preferredWidth + left + right);
    height = (preferredHeight + top + bottom);
    
    sizeUnknown = false;
    return dim;
  }
  







  public Dimension minimumLayoutSize(Container parent)
  {
    Dimension dim = new Dimension(0, 0);
    

    Insets insets = parent.getInsets();
    width = (minWidth + left + right);
    height = (minHeight + top + bottom);
    
    sizeUnknown = false;
    return dim;
  }
  








  public void layoutContainer(Container parent)
  {
    Insets insets = parent.getInsets();
    int maxWidth = getSizewidth - (left + right);
    
    int maxHeight = getSizeheight - (top + bottom);
    
    int nComps = parent.getComponentCount();
    int x = 0;
    int y = 0;
    


    if (sizeUnknown) {
      setSizes(parent);
    }
    
    if (nComps < 2) {
      Component c = parent.getComponent(0);
      if (c.isVisible()) {
        Dimension d = c.getPreferredSize();
        c.setBounds(x, y, width, height);
      }
    }
    else {
      double radialCurrent = Math.toRadians(90.0D);
      double radialIncrement = 6.283185307179586D / nComps;
      int midX = maxWidth / 2;
      int midY = maxHeight / 2;
      int a = midX - maxCompWidth;
      int b = midY - maxCompHeight;
      for (int i = 0; i < nComps; i++) {
        Component c = parent.getComponent(i);
        if (c.isVisible()) {
          Dimension d = c.getPreferredSize();
          x = (int)(midX - a * Math.cos(radialCurrent) - d.getWidth() / 2.0D + left);
          


          y = (int)(midY - b * Math.sin(radialCurrent) - d.getHeight() / 2.0D + top);
          




          c.setBounds(x, y, width, height);
        }
        radialCurrent += radialIncrement;
      }
    }
  }
  




  public String toString()
  {
    return getClass().getName();
  }
  





  public static void main(String[] args)
    throws Exception
  {
    Frame frame = new Frame();
    Panel panel = new Panel();
    panel.setLayout(new RadialLayout());
    
    panel.add(new Checkbox("One"));
    panel.add(new Checkbox("Two"));
    panel.add(new Checkbox("Three"));
    panel.add(new Checkbox("Four"));
    panel.add(new Checkbox("Five"));
    panel.add(new Checkbox("One"));
    panel.add(new Checkbox("Two"));
    panel.add(new Checkbox("Three"));
    panel.add(new Checkbox("Four"));
    panel.add(new Checkbox("Five"));
    
    frame.add(panel);
    frame.setSize(300, 500);
    frame.setVisible(true);
  }
}
