package org.jfree.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;





























































public class CenterLayout
  implements LayoutManager, Serializable
{
  private static final long serialVersionUID = 469319532333015042L;
  
  public CenterLayout() {}
  
  public Dimension preferredLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      if (parent.getComponentCount() > 0) {
        Component component = parent.getComponent(0);
        Dimension d = component.getPreferredSize();
        return new Dimension((int)d.getWidth() + left + right, (int)d.getHeight() + top + bottom);
      }
      



      return new Dimension(left + right, top + bottom);
    }
  }
  











  public Dimension minimumLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      if (parent.getComponentCount() > 0) {
        Component component = parent.getComponent(0);
        Dimension d = component.getMinimumSize();
        return new Dimension(width + left + right, height + top + bottom);
      }
      

      return new Dimension(left + right, top + bottom);
    }
  }
  








  public void layoutContainer(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      if (parent.getComponentCount() > 0) {
        Insets insets = parent.getInsets();
        Dimension parentSize = parent.getSize();
        Component component = parent.getComponent(0);
        Dimension componentSize = component.getPreferredSize();
        int xx = left + Math.max((width - left - right - width) / 2, 0);
        


        int yy = top + Math.max((height - top - bottom - height) / 2, 0);
        

        component.setBounds(xx, yy, width, height);
      }
    }
  }
  
  public void addLayoutComponent(Component comp) {}
  
  public void removeLayoutComponent(Component comp) {}
  
  public void addLayoutComponent(String name, Component comp) {}
  
  public void removeLayoutComponent(String name, Component comp) {}
}
