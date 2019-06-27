package org.jfree.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;


























































public final class OverlayLayout
  implements LayoutManager
{
  private boolean ignoreInvisible;
  
  public OverlayLayout(boolean ignoreInvisible)
  {
    this.ignoreInvisible = ignoreInvisible;
  }
  







  public OverlayLayout() {}
  






  public void addLayoutComponent(String name, Component comp) {}
  






  public void removeLayoutComponent(Component comp) {}
  






  public void layoutContainer(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets ins = parent.getInsets();
      
      Rectangle bounds = parent.getBounds();
      int width = width - left - right;
      int height = height - top - bottom;
      
      Component[] comps = parent.getComponents();
      
      for (int i = 0; i < comps.length; i++) {
        Component c = comps[i];
        if ((comps[i].isVisible()) || (!ignoreInvisible))
        {

          c.setBounds(left, top, width, height);
        }
      }
    }
  }
  






  public Dimension minimumLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets ins = parent.getInsets();
      Component[] comps = parent.getComponents();
      int height = 0;
      int width = 0;
      for (int i = 0; i < comps.length; i++) {
        if ((comps[i].isVisible()) || (!ignoreInvisible))
        {


          Dimension pref = comps[i].getMinimumSize();
          if (height > height) {
            height = height;
          }
          if (width > width)
            width = width;
        }
      }
      return new Dimension(width + left + right, height + top + bottom);
    }
  }
  








  public Dimension preferredLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets ins = parent.getInsets();
      Component[] comps = parent.getComponents();
      int height = 0;
      int width = 0;
      for (int i = 0; i < comps.length; i++) {
        if ((comps[i].isVisible()) || (!ignoreInvisible))
        {


          Dimension pref = comps[i].getPreferredSize();
          if (height > height) {
            height = height;
          }
          if (width > width)
            width = width;
        }
      }
      return new Dimension(width + left + right, height + top + bottom);
    }
  }
}
