package org.jfree.chart.block;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jfree.data.Range;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.Size2D;
import org.jfree.ui.VerticalAlignment;


































































public class ColumnArrangement
  implements Arrangement, Serializable
{
  private static final long serialVersionUID = -5315388482898581555L;
  private HorizontalAlignment horizontalAlignment;
  private VerticalAlignment verticalAlignment;
  private double horizontalGap;
  private double verticalGap;
  
  public ColumnArrangement() {}
  
  public ColumnArrangement(HorizontalAlignment hAlign, VerticalAlignment vAlign, double hGap, double vGap)
  {
    horizontalAlignment = hAlign;
    verticalAlignment = vAlign;
    horizontalGap = hGap;
    verticalGap = vGap;
  }
  












  public void add(Block block, Object key) {}
  












  public Size2D arrange(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    LengthConstraintType w = constraint.getWidthConstraintType();
    LengthConstraintType h = constraint.getHeightConstraintType();
    if (w == LengthConstraintType.NONE) {
      if (h == LengthConstraintType.NONE) {
        return arrangeNN(container, g2);
      }
      if (h == LengthConstraintType.FIXED) {
        throw new RuntimeException("Not implemented.");
      }
      if (h == LengthConstraintType.RANGE) {
        throw new RuntimeException("Not implemented.");
      }
    }
    else if (w == LengthConstraintType.FIXED) {
      if (h == LengthConstraintType.NONE) {
        throw new RuntimeException("Not implemented.");
      }
      if (h == LengthConstraintType.FIXED) {
        return arrangeFF(container, g2, constraint);
      }
      if (h == LengthConstraintType.RANGE) {
        throw new RuntimeException("Not implemented.");
      }
    }
    else if (w == LengthConstraintType.RANGE) {
      if (h == LengthConstraintType.NONE) {
        throw new RuntimeException("Not implemented.");
      }
      if (h == LengthConstraintType.FIXED) {
        return arrangeRF(container, g2, constraint);
      }
      if (h == LengthConstraintType.RANGE) {
        return arrangeRR(container, g2, constraint);
      }
    }
    return new Size2D();
  }
  














  protected Size2D arrangeFF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    return arrangeNF(container, g2, constraint);
  }
  













  protected Size2D arrangeNF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    List blocks = container.getBlocks();
    
    double height = constraint.getHeight();
    if (height <= 0.0D) {
      height = Double.POSITIVE_INFINITY;
    }
    
    double x = 0.0D;
    double y = 0.0D;
    double maxWidth = 0.0D;
    List itemsInColumn = new ArrayList();
    for (int i = 0; i < blocks.size(); i++) {
      Block block = (Block)blocks.get(i);
      Size2D size = block.arrange(g2, RectangleConstraint.NONE);
      if (y + height <= height) {
        itemsInColumn.add(block);
        block.setBounds(new Rectangle2D.Double(x, y, width, height));
        

        y = y + height + verticalGap;
        maxWidth = Math.max(maxWidth, width);

      }
      else if (itemsInColumn.isEmpty())
      {
        block.setBounds(new Rectangle2D.Double(x, y, width, Math.min(height, height - y)));
        



        y = 0.0D;
        x = x + width + horizontalGap;
      }
      else
      {
        itemsInColumn.clear();
        x = x + maxWidth + horizontalGap;
        y = 0.0D;
        maxWidth = width;
        block.setBounds(new Rectangle2D.Double(x, y, width, Math.min(height, height)));
        



        y = height + verticalGap;
        itemsInColumn.add(block);
      }
    }
    
    return new Size2D(x + maxWidth, constraint.getHeight());
  }
  













  protected Size2D arrangeRR(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    Size2D s1 = arrangeNN(container, g2);
    if (constraint.getHeightRange().contains(height)) {
      return s1;
    }
    
    RectangleConstraint c = constraint.toFixedHeight(constraint.getHeightRange().getUpperBound());
    

    return arrangeRF(container, g2, c);
  }
  












  protected Size2D arrangeRF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    Size2D s = arrangeNF(container, g2, constraint);
    if (constraint.getWidthRange().contains(width)) {
      return s;
    }
    
    RectangleConstraint c = constraint.toFixedWidth(constraint.getWidthRange().constrain(s.getWidth()));
    

    return arrangeFF(container, g2, c);
  }
  









  protected Size2D arrangeNN(BlockContainer container, Graphics2D g2)
  {
    double y = 0.0D;
    double height = 0.0D;
    double maxWidth = 0.0D;
    List blocks = container.getBlocks();
    int blockCount = blocks.size();
    if (blockCount > 0) {
      Size2D[] sizes = new Size2D[blocks.size()];
      for (int i = 0; i < blocks.size(); i++) {
        Block block = (Block)blocks.get(i);
        sizes[i] = block.arrange(g2, RectangleConstraint.NONE);
        height += sizes[i].getHeight();
        maxWidth = Math.max(width, maxWidth);
        block.setBounds(new Rectangle2D.Double(0.0D, y, width, height));
        



        y = y + height + verticalGap;
      }
      if (blockCount > 1) {
        height += verticalGap * (blockCount - 1);
      }
      if (horizontalAlignment != HorizontalAlignment.LEFT) {
        for (int i = 0; i < blocks.size(); i++)
        {
          if (horizontalAlignment != HorizontalAlignment.CENTER)
          {


            if (horizontalAlignment != HorizontalAlignment.RIGHT) {}
          }
        }
      }
    }
    

    return new Size2D(maxWidth, height);
  }
  






  public void clear() {}
  





  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ColumnArrangement)) {
      return false;
    }
    ColumnArrangement that = (ColumnArrangement)obj;
    if (horizontalAlignment != horizontalAlignment) {
      return false;
    }
    if (verticalAlignment != verticalAlignment) {
      return false;
    }
    if (horizontalGap != horizontalGap) {
      return false;
    }
    if (verticalGap != verticalGap) {
      return false;
    }
    return true;
  }
}
