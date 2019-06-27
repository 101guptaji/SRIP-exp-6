package org.jfree.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;
































































public class LCBLayout
  implements LayoutManager, Serializable
{
  private static final long serialVersionUID = -2531780832406163833L;
  private static final int COLUMNS = 3;
  private int[] colWidth;
  private int[] rowHeight;
  private int labelGap;
  private int buttonGap;
  private int vGap;
  
  public LCBLayout(int maxrows)
  {
    labelGap = 10;
    buttonGap = 6;
    vGap = 2;
    colWidth = new int[3];
    rowHeight = new int[maxrows];
  }
  







  public Dimension preferredLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      int ncomponents = parent.getComponentCount();
      int nrows = ncomponents / 3;
      for (int c = 0; c < 3; c++) {
        for (int r = 0; r < nrows; r++) {
          Component component = parent.getComponent(r * 3 + c);
          
          Dimension d = component.getPreferredSize();
          if (colWidth[c] < width) {
            colWidth[c] = width;
          }
          if (rowHeight[r] < height) {
            rowHeight[r] = height;
          }
        }
      }
      int totalHeight = vGap * (nrows - 1);
      for (int r = 0; r < nrows; r++) {
        totalHeight += rowHeight[r];
      }
      int totalWidth = colWidth[0] + labelGap + colWidth[1] + buttonGap + colWidth[2];
      
      return new Dimension(left + right + totalWidth + labelGap + buttonGap, top + bottom + totalHeight + vGap);
    }
  }
  












  public Dimension minimumLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      int ncomponents = parent.getComponentCount();
      int nrows = ncomponents / 3;
      for (int c = 0; c < 3; c++) {
        for (int r = 0; r < nrows; r++) {
          Component component = parent.getComponent(r * 3 + c);
          
          Dimension d = component.getMinimumSize();
          if (colWidth[c] < width) {
            colWidth[c] = width;
          }
          if (rowHeight[r] < height) {
            rowHeight[r] = height;
          }
        }
      }
      int totalHeight = vGap * (nrows - 1);
      for (int r = 0; r < nrows; r++) {
        totalHeight += rowHeight[r];
      }
      int totalWidth = colWidth[0] + labelGap + colWidth[1] + buttonGap + colWidth[2];
      
      return new Dimension(left + right + totalWidth + labelGap + buttonGap, top + bottom + totalHeight + vGap);
    }
  }
  










  public void layoutContainer(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      int ncomponents = parent.getComponentCount();
      int nrows = ncomponents / 3;
      for (int c = 0; c < 3; c++) {
        for (int r = 0; r < nrows; r++) {
          Component component = parent.getComponent(r * 3 + c);
          
          Dimension d = component.getPreferredSize();
          if (colWidth[c] < width) {
            colWidth[c] = width;
          }
          if (rowHeight[r] < height) {
            rowHeight[r] = height;
          }
        }
      }
      int totalHeight = vGap * (nrows - 1);
      for (int r = 0; r < nrows; r++) {
        totalHeight += rowHeight[r];
      }
      int totalWidth = colWidth[0] + colWidth[1] + colWidth[2];
      


      int available = parent.getWidth() - left - right - labelGap - buttonGap;
      
      colWidth[1] += available - totalWidth;
      

      int x = left;
      for (int c = 0; c < 3; c++) {
        int y = top;
        for (int r = 0; r < nrows; r++) {
          int i = r * 3 + c;
          if (i < ncomponents) {
            Component component = parent.getComponent(i);
            Dimension d = component.getPreferredSize();
            int h = height;
            int adjust = (rowHeight[r] - h) / 2;
            parent.getComponent(i).setBounds(x, y + adjust, colWidth[c], h);
          }
          
          y = y + rowHeight[r] + vGap;
        }
        x += colWidth[c];
        if (c == 0) {
          x += labelGap;
        }
        if (c == 1) {
          x += buttonGap;
        }
      }
    }
  }
  
  public void addLayoutComponent(Component comp) {}
  
  public void removeLayoutComponent(Component comp) {}
  
  public void addLayoutComponent(String name, Component comp) {}
  
  public void removeLayoutComponent(String name, Component comp) {}
}
