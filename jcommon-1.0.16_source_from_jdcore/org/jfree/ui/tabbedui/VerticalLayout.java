package org.jfree.ui.tabbedui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;






















































public class VerticalLayout
  implements LayoutManager
{
  private final boolean useSizeFromParent;
  
  public VerticalLayout()
  {
    this(true);
  }
  






  public VerticalLayout(boolean useParent)
  {
    useSizeFromParent = useParent;
  }
  








  public void addLayoutComponent(String name, Component comp) {}
  








  public void removeLayoutComponent(Component comp) {}
  







  public Dimension preferredLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets ins = parent.getInsets();
      Component[] comps = parent.getComponents();
      int height = top + bottom;
      int width = left + right;
      for (int i = 0; i < comps.length; i++) {
        if (comps[i].isVisible())
        {

          Dimension pref = comps[i].getPreferredSize();
          height += height;
          if (width > width) {
            width = width;
          }
        }
      }
      return new Dimension(width + left + right, height + top + bottom);
    }
  }
  








  public Dimension minimumLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets ins = parent.getInsets();
      Component[] comps = parent.getComponents();
      int height = top + bottom;
      int width = left + right;
      for (int i = 0; i < comps.length; i++) {
        if (comps[i].isVisible())
        {

          Dimension min = comps[i].getMinimumSize();
          height += height;
          if (width > width)
            width = width;
        }
      }
      return new Dimension(width + left + right, height + top + bottom);
    }
  }
  






  public boolean isUseSizeFromParent()
  {
    return useSizeFromParent;
  }
  




  public void layoutContainer(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets ins = parent.getInsets();
      int insHorizontal = left + right;
      int width;
      int width;
      if (isUseSizeFromParent()) {
        Rectangle bounds = parent.getBounds();
        width = width - insHorizontal;
      }
      else {
        width = preferredLayoutSizewidth - insHorizontal;
      }
      Component[] comps = parent.getComponents();
      
      int y = top;
      for (int i = 0; i < comps.length; i++) {
        Component c = comps[i];
        if (c.isVisible())
        {

          Dimension dim = c.getPreferredSize();
          c.setBounds(left, y, width, height);
          y += height;
        }
      }
    }
  }
}
