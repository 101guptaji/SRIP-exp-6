package org.jfree.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.io.Serializable;
















































































































































public class FormatLayout
  implements LayoutManager, Serializable
{
  private static final long serialVersionUID = 2866692886323930722L;
  public static final int C = 1;
  public static final int LC = 2;
  public static final int LCB = 3;
  public static final int LCLC = 4;
  public static final int LCLCB = 5;
  public static final int LCBLC = 6;
  public static final int LCBLCB = 7;
  private int[] rowFormats;
  private int rowGap;
  private int[] columnGaps;
  private int[] rowHeights;
  private int totalHeight;
  private int[] columnWidths;
  private int totalWidth;
  private int columns1and2Width;
  private int columns4and5Width;
  private int columns1to4Width;
  private int columns1to5Width;
  private int columns0to5Width;
  
  public FormatLayout(int rowCount, int[] rowFormats)
  {
    this.rowFormats = rowFormats;
    rowGap = 2;
    columnGaps = new int[5];
    columnGaps[0] = 10;
    columnGaps[1] = 5;
    columnGaps[2] = 5;
    columnGaps[3] = 10;
    columnGaps[4] = 5;
    

    rowHeights = new int[rowCount];
    columnWidths = new int[6];
  }
  









  public Dimension preferredLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      int componentIndex = 0;
      int rowCount = rowHeights.length;
      for (int i = 0; i < columnWidths.length; i++) {
        columnWidths[i] = 0;
      }
      columns1and2Width = 0;
      columns4and5Width = 0;
      columns1to4Width = 0;
      columns1to5Width = 0;
      columns0to5Width = 0;
      
      totalHeight = 0;
      for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
        int format = rowFormats[(rowIndex % rowFormats.length)];
        Component c0;
        Component c1; Component c2; Component c3; Component c4; switch (format) {
        case 1: 
          c0 = parent.getComponent(componentIndex);
          updateC(rowIndex, c0.getPreferredSize());
          componentIndex += 1;
          break;
        case 2: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          updateLC(rowIndex, c0.getPreferredSize(), c1.getPreferredSize());
          
          componentIndex += 2;
          break;
        case 3: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          updateLCB(rowIndex, c0.getPreferredSize(), c1.getPreferredSize(), c2.getPreferredSize());
          


          componentIndex += 3;
          break;
        case 4: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          updateLCLC(rowIndex, c0.getPreferredSize(), c1.getPreferredSize(), c2.getPreferredSize(), c3.getPreferredSize());
          



          componentIndex += 4;
          break;
        case 6: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          c4 = parent.getComponent(componentIndex + 4);
          updateLCBLC(rowIndex, c0.getPreferredSize(), c1.getPreferredSize(), c2.getPreferredSize(), c3.getPreferredSize(), c4.getPreferredSize());
          




          componentIndex += 5;
          break;
        case 5: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          c4 = parent.getComponent(componentIndex + 4);
          updateLCLCB(rowIndex, c0.getPreferredSize(), c1.getPreferredSize(), c2.getPreferredSize(), c3.getPreferredSize(), c4.getPreferredSize());
          




          componentIndex += 5;
          break;
        case 7: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          c4 = parent.getComponent(componentIndex + 4);
          Component c5 = parent.getComponent(componentIndex + 5);
          updateLCBLCB(rowIndex, c0.getPreferredSize(), c1.getPreferredSize(), c2.getPreferredSize(), c3.getPreferredSize(), c4.getPreferredSize(), c5.getPreferredSize());
          





          componentIndex += 6;
        }
        
      }
      complete();
      return new Dimension(totalWidth + left + right, totalHeight + (rowCount - 1) * rowGap + top + bottom);
    }
  }
  











  public Dimension minimumLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      int componentIndex = 0;
      int rowCount = rowHeights.length;
      for (int i = 0; i < columnWidths.length; i++) {
        columnWidths[i] = 0;
      }
      columns1and2Width = 0;
      columns4and5Width = 0;
      columns1to4Width = 0;
      columns1to5Width = 0;
      columns0to5Width = 0;
      int totalHeight = 0;
      for (int rowIndex = 0; rowIndex < rowCount; rowIndex++)
      {
        int format = rowFormats[(rowIndex % rowFormats.length)];
        Component c0;
        Component c1;
        Component c2; Component c3; Component c4; switch (format) {
        case 1: 
          c0 = parent.getComponent(componentIndex);
          columns0to5Width = Math.max(columns0to5Width, getMinimumSizewidth);
          

          componentIndex += 1;
          break;
        case 2: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          updateLC(rowIndex, c0.getMinimumSize(), c1.getMinimumSize());
          

          componentIndex += 2;
          break;
        case 3: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          updateLCB(rowIndex, c0.getMinimumSize(), c1.getMinimumSize(), c2.getMinimumSize());
          


          componentIndex += 3;
          break;
        case 4: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          updateLCLC(rowIndex, c0.getMinimumSize(), c1.getMinimumSize(), c2.getMinimumSize(), c3.getMinimumSize());
          



          componentIndex += 3;
          break;
        case 6: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          c4 = parent.getComponent(componentIndex + 4);
          updateLCBLC(rowIndex, c0.getMinimumSize(), c1.getMinimumSize(), c2.getMinimumSize(), c3.getMinimumSize(), c4.getMinimumSize());
          




          componentIndex += 4;
          break;
        case 5: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          c4 = parent.getComponent(componentIndex + 4);
          updateLCLCB(rowIndex, c0.getMinimumSize(), c1.getMinimumSize(), c2.getMinimumSize(), c3.getMinimumSize(), c4.getMinimumSize());
          




          componentIndex += 4;
          break;
        case 7: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          c4 = parent.getComponent(componentIndex + 4);
          Component c5 = parent.getComponent(componentIndex + 5);
          updateLCBLCB(rowIndex, c0.getMinimumSize(), c1.getMinimumSize(), c2.getMinimumSize(), c3.getMinimumSize(), c4.getMinimumSize(), c5.getMinimumSize());
          





          componentIndex += 5;
        }
        
      }
      complete();
      return new Dimension(totalWidth + left + right, 0 + (rowCount - 1) * rowGap + top + bottom);
    }
  }
  








  public void layoutContainer(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      int componentIndex = 0;
      int rowCount = rowHeights.length;
      for (int i = 0; i < columnWidths.length; i++) {
        columnWidths[i] = 0;
      }
      columns1and2Width = 0;
      columns4and5Width = 0;
      columns1to4Width = 0;
      columns1to5Width = 0;
      columns0to5Width = (getBoundswidth - left - right);
      

      totalHeight = 0;
      Component c0; for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
        int format = rowFormats[(rowIndex % rowFormats.length)];
        Component c1;
        Component c2; Component c3; Component c4; switch (format) {
        case 1: 
          c0 = parent.getComponent(componentIndex);
          updateC(rowIndex, c0.getPreferredSize());
          componentIndex += 1;
          break;
        case 2: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          updateLC(rowIndex, c0.getPreferredSize(), c1.getPreferredSize());
          
          componentIndex += 2;
          break;
        case 3: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          updateLCB(rowIndex, c0.getPreferredSize(), c1.getPreferredSize(), c2.getPreferredSize());
          


          componentIndex += 3;
          break;
        case 4: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          updateLCLC(rowIndex, c0.getPreferredSize(), c1.getPreferredSize(), c2.getPreferredSize(), c3.getPreferredSize());
          



          componentIndex += 4;
          break;
        case 6: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          c4 = parent.getComponent(componentIndex + 4);
          updateLCBLC(rowIndex, c0.getPreferredSize(), c1.getPreferredSize(), c2.getPreferredSize(), c3.getPreferredSize(), c4.getPreferredSize());
          




          componentIndex += 5;
          break;
        case 5: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          c4 = parent.getComponent(componentIndex + 4);
          updateLCLCB(rowIndex, c0.getPreferredSize(), c1.getPreferredSize(), c2.getPreferredSize(), c3.getPreferredSize(), c4.getPreferredSize());
          




          componentIndex += 5;
          break;
        case 7: 
          c0 = parent.getComponent(componentIndex);
          c1 = parent.getComponent(componentIndex + 1);
          c2 = parent.getComponent(componentIndex + 2);
          c3 = parent.getComponent(componentIndex + 3);
          c4 = parent.getComponent(componentIndex + 4);
          Component c5 = parent.getComponent(componentIndex + 5);
          updateLCBLCB(rowIndex, c0.getPreferredSize(), c1.getPreferredSize(), c2.getPreferredSize(), c3.getPreferredSize(), c4.getPreferredSize(), c5.getPreferredSize());
          





          componentIndex += 6;
        }
        
      }
      complete();
      
      componentIndex = 0;
      int rowY = top;
      int[] rowX = new int[6];
      rowX[0] = left;
      rowX[1] = (rowX[0] + columnWidths[0] + columnGaps[0]);
      rowX[2] = (rowX[1] + columnWidths[1] + columnGaps[1]);
      rowX[3] = (rowX[2] + columnWidths[2] + columnGaps[2]);
      rowX[4] = (rowX[3] + columnWidths[3] + columnGaps[3]);
      rowX[5] = (rowX[4] + columnWidths[4] + columnGaps[4]);
      int w1to2 = columnWidths[1] + columnGaps[1] + columnWidths[2];
      
      int w4to5 = columnWidths[4] + columnGaps[4] + columnWidths[5];
      
      int w1to4 = w1to2 + columnGaps[2] + columnWidths[3] + columnGaps[3] + columnWidths[4];
      
      int w1to5 = w1to4 + columnGaps[4] + columnWidths[5];
      int w0to5 = w1to5 + columnWidths[0] + columnGaps[0];
      for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
        int format = rowFormats[(rowIndex % rowFormats.length)];
        Component c1;
        Component c2;
        Component c3; Component c4; switch (format) {
        case 1: 
          c0 = parent.getComponent(componentIndex);
          c0.setBounds(rowX[0], rowY, w0to5, getPreferredSizeheight);
          
          componentIndex += 1;
          break;
        case 2: 
          c0 = parent.getComponent(componentIndex);
          c0.setBounds(rowX[0], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[0], getPreferredSizeheight);
          




          c1 = parent.getComponent(componentIndex + 1);
          c1.setBounds(rowX[1], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, w1to5, getPreferredSizeheight);
          




          componentIndex += 2;
          break;
        case 3: 
          c0 = parent.getComponent(componentIndex);
          c0.setBounds(rowX[0], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[0], getPreferredSizeheight);
          




          c1 = parent.getComponent(componentIndex + 1);
          c1.setBounds(rowX[1], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, w1to4, getPreferredSizeheight);
          




          c2 = parent.getComponent(componentIndex + 2);
          c2.setBounds(rowX[5], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[5], getPreferredSizeheight);
          




          componentIndex += 3;
          break;
        case 4: 
          c0 = parent.getComponent(componentIndex);
          c0.setBounds(rowX[0], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[0], getPreferredSizeheight);
          




          c1 = parent.getComponent(componentIndex + 1);
          c1.setBounds(rowX[1], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, w1to2, getPreferredSizeheight);
          




          c2 = parent.getComponent(componentIndex + 2);
          c2.setBounds(rowX[3], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[3], getPreferredSizeheight);
          




          c3 = parent.getComponent(componentIndex + 3);
          c3.setBounds(rowX[4], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, w4to5, getPreferredSizeheight);
          




          componentIndex += 4;
          break;
        case 6: 
          c0 = parent.getComponent(componentIndex);
          c0.setBounds(rowX[0], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[0], getPreferredSizeheight);
          




          c1 = parent.getComponent(componentIndex + 1);
          c1.setBounds(rowX[1], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[1], getPreferredSizeheight);
          




          c2 = parent.getComponent(componentIndex + 2);
          c2.setBounds(rowX[2], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[2], getPreferredSizeheight);
          




          c3 = parent.getComponent(componentIndex + 3);
          c3.setBounds(rowX[3], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[3], getPreferredSizeheight);
          




          c4 = parent.getComponent(componentIndex + 4);
          c4.setBounds(rowX[4], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, w4to5, getPreferredSizeheight);
          




          componentIndex += 5;
          break;
        case 5: 
          c0 = parent.getComponent(componentIndex);
          c0.setBounds(rowX[0], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[0], getPreferredSizeheight);
          




          c1 = parent.getComponent(componentIndex + 1);
          c1.setBounds(rowX[1], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, w1to2, getPreferredSizeheight);
          




          c2 = parent.getComponent(componentIndex + 2);
          c2.setBounds(rowX[3], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[3], getPreferredSizeheight);
          




          c3 = parent.getComponent(componentIndex + 3);
          c3.setBounds(rowX[4], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[4], getPreferredSizeheight);
          




          c4 = parent.getComponent(componentIndex + 4);
          c4.setBounds(rowX[5], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[5], getPreferredSizeheight);
          




          componentIndex += 5;
          break;
        case 7: 
          c0 = parent.getComponent(componentIndex);
          c0.setBounds(rowX[0], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[0], getPreferredSizeheight);
          




          c1 = parent.getComponent(componentIndex + 1);
          c1.setBounds(rowX[1], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[1], getPreferredSizeheight);
          




          c2 = parent.getComponent(componentIndex + 2);
          c2.setBounds(rowX[2], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[2], getPreferredSizeheight);
          




          c3 = parent.getComponent(componentIndex + 3);
          c3.setBounds(rowX[3], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[3], getPreferredSizeheight);
          




          c4 = parent.getComponent(componentIndex + 4);
          c4.setBounds(rowX[4], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[4], getPreferredSizeheight);
          




          Component c5 = parent.getComponent(componentIndex + 5);
          c5.setBounds(rowX[5], rowY + (rowHeights[rowIndex] - getPreferredSizeheight) / 2, columnWidths[5], getPreferredSizeheight);
          




          componentIndex += 6;
        }
        
        rowY = rowY + rowHeights[rowIndex] + rowGap;
      }
    }
  }
  





  protected void updateC(int rowIndex, Dimension d0)
  {
    rowHeights[rowIndex] = height;
    totalHeight += rowHeights[rowIndex];
    columns0to5Width = Math.max(columns0to5Width, width);
  }
  








  protected void updateLC(int rowIndex, Dimension d0, Dimension d1)
  {
    rowHeights[rowIndex] = Math.max(height, height);
    totalHeight += rowHeights[rowIndex];
    columnWidths[0] = Math.max(columnWidths[0], width);
    columns1to5Width = Math.max(columns1to5Width, width);
  }
  











  protected void updateLCB(int rowIndex, Dimension d0, Dimension d1, Dimension d2)
  {
    rowHeights[rowIndex] = Math.max(height, Math.max(height, height));
    
    totalHeight += rowHeights[rowIndex];
    columnWidths[0] = Math.max(columnWidths[0], width);
    columns1to4Width = Math.max(columns1to4Width, width);
    columnWidths[5] = Math.max(columnWidths[5], width);
  }
  












  protected void updateLCLC(int rowIndex, Dimension d0, Dimension d1, Dimension d2, Dimension d3)
  {
    rowHeights[rowIndex] = Math.max(Math.max(height, height), Math.max(height, height));
    
    totalHeight += rowHeights[rowIndex];
    columnWidths[0] = Math.max(columnWidths[0], width);
    columns1and2Width = Math.max(columns1and2Width, width);
    columnWidths[3] = Math.max(columnWidths[3], width);
    columns4and5Width = Math.max(columns4and5Width, width);
  }
  












  protected void updateLCBLC(int rowIndex, Dimension d0, Dimension d1, Dimension d2, Dimension d3, Dimension d4)
  {
    rowHeights[rowIndex] = Math.max(height, Math.max(Math.max(height, height), Math.max(height, height)));
    



    totalHeight += rowHeights[rowIndex];
    columnWidths[0] = Math.max(columnWidths[0], width);
    columnWidths[1] = Math.max(columnWidths[1], width);
    columnWidths[2] = Math.max(columnWidths[2], width);
    columnWidths[3] = Math.max(columnWidths[3], width);
    columns4and5Width = Math.max(columns4and5Width, width);
  }
  













  protected void updateLCLCB(int rowIndex, Dimension d0, Dimension d1, Dimension d2, Dimension d3, Dimension d4)
  {
    rowHeights[rowIndex] = Math.max(height, Math.max(Math.max(height, height), Math.max(height, height)));
    

    totalHeight += rowHeights[rowIndex];
    columnWidths[0] = Math.max(columnWidths[0], width);
    columns1and2Width = Math.max(columns1and2Width, width);
    columnWidths[3] = Math.max(columnWidths[3], width);
    columnWidths[4] = Math.max(columnWidths[4], width);
    columnWidths[5] = Math.max(columnWidths[5], width);
  }
  
















  protected void updateLCBLCB(int rowIndex, Dimension d0, Dimension d1, Dimension d2, Dimension d3, Dimension d4, Dimension d5)
  {
    rowHeights[rowIndex] = Math.max(Math.max(height, height), Math.max(Math.max(height, height), Math.max(height, height)));
    



    totalHeight += rowHeights[rowIndex];
    columnWidths[0] = Math.max(columnWidths[0], width);
    columnWidths[1] = Math.max(columnWidths[1], width);
    columnWidths[2] = Math.max(columnWidths[2], width);
    columnWidths[3] = Math.max(columnWidths[3], width);
    columnWidths[4] = Math.max(columnWidths[4], width);
    columnWidths[5] = Math.max(columnWidths[5], width);
  }
  




  public void complete()
  {
    columnWidths[1] = Math.max(columnWidths[1], columns1and2Width - columnGaps[1] - columnWidths[2]);
    



    columnWidths[4] = Math.max(columnWidths[4], Math.max(columns4and5Width - columnGaps[4] - columnWidths[5], Math.max(columns1to4Width - columnGaps[1] - columnGaps[2] - columnGaps[3] - columnWidths[1] - columnWidths[2] - columnWidths[3], columns1to5Width - columnGaps[1] - columnGaps[2] - columnGaps[3] - columnWidths[1] - columnWidths[2] - columnWidths[3] - columnGaps[4])));
    
















    int leftWidth = columnWidths[0] + columnGaps[0] + columnWidths[1] + columnGaps[1] + columnWidths[2];
    


    int rightWidth = columnWidths[3] + columnGaps[3] + columnWidths[4] + columnGaps[4] + columnWidths[5];
    


    if (splitLayout()) {
      if (leftWidth > rightWidth) {
        int mismatch = leftWidth - rightWidth;
        columnWidths[4] += mismatch;
        rightWidth += mismatch;
      }
      else {
        int mismatch = rightWidth - leftWidth;
        columnWidths[1] += mismatch;
        leftWidth += mismatch;
      }
    }
    
    totalWidth = (leftWidth + columnGaps[2] + rightWidth);
    
    if (columns0to5Width > totalWidth) {
      int spaceToAdd = columns0to5Width - totalWidth;
      if (splitLayout()) {
        int halfSpaceToAdd = spaceToAdd / 2;
        columnWidths[1] += halfSpaceToAdd;
        columnWidths[4] = (columnWidths[4] + spaceToAdd - halfSpaceToAdd);
        
        totalWidth += spaceToAdd;
      }
      else {
        columnWidths[1] += spaceToAdd;
        totalWidth += spaceToAdd;
      }
    }
  }
  






  private boolean splitLayout()
  {
    for (int i = 0; i < rowFormats.length; i++) {
      if (rowFormats[i] > 3) {
        return true;
      }
    }
    return false;
  }
  
  public void addLayoutComponent(Component comp) {}
  
  public void removeLayoutComponent(Component comp) {}
  
  public void addLayoutComponent(String name, Component comp) {}
  
  public void removeLayoutComponent(String name, Component comp) {}
}
