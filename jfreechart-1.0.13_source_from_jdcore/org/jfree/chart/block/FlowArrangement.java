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
























































public class FlowArrangement
  implements Arrangement, Serializable
{
  private static final long serialVersionUID = 4543632485478613800L;
  private HorizontalAlignment horizontalAlignment;
  private VerticalAlignment verticalAlignment;
  private double horizontalGap;
  private double verticalGap;
  
  public FlowArrangement()
  {
    this(HorizontalAlignment.CENTER, VerticalAlignment.CENTER, 2.0D, 2.0D);
  }
  








  public FlowArrangement(HorizontalAlignment hAlign, VerticalAlignment vAlign, double hGap, double vGap)
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
        return arrangeNF(container, g2, constraint);
      }
      if (h == LengthConstraintType.RANGE) {
        throw new RuntimeException("Not implemented.");
      }
    }
    else if (w == LengthConstraintType.FIXED) {
      if (h == LengthConstraintType.NONE) {
        return arrangeFN(container, g2, constraint);
      }
      if (h == LengthConstraintType.FIXED) {
        return arrangeFF(container, g2, constraint);
      }
      if (h == LengthConstraintType.RANGE) {
        return arrangeFR(container, g2, constraint);
      }
    }
    else if (w == LengthConstraintType.RANGE) {
      if (h == LengthConstraintType.NONE) {
        return arrangeRN(container, g2, constraint);
      }
      if (h == LengthConstraintType.FIXED) {
        return arrangeRF(container, g2, constraint);
      }
      if (h == LengthConstraintType.RANGE) {
        return arrangeRR(container, g2, constraint);
      }
    }
    throw new RuntimeException("Unrecognised constraint type.");
  }
  












  protected Size2D arrangeFN(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    List blocks = container.getBlocks();
    double width = constraint.getWidth();
    
    double x = 0.0D;
    double y = 0.0D;
    double maxHeight = 0.0D;
    List itemsInRow = new ArrayList();
    for (int i = 0; i < blocks.size(); i++) {
      Block block = (Block)blocks.get(i);
      Size2D size = block.arrange(g2, RectangleConstraint.NONE);
      if (x + width <= width) {
        itemsInRow.add(block);
        block.setBounds(new Rectangle2D.Double(x, y, width, height));
        

        x = x + width + horizontalGap;
        maxHeight = Math.max(maxHeight, height);

      }
      else if (itemsInRow.isEmpty())
      {
        block.setBounds(new Rectangle2D.Double(x, y, Math.min(width, width - x), height));
        



        x = 0.0D;
        y = y + height + verticalGap;
      }
      else
      {
        itemsInRow.clear();
        x = 0.0D;
        y = y + maxHeight + verticalGap;
        maxHeight = height;
        block.setBounds(new Rectangle2D.Double(x, y, Math.min(width, width), height));
        



        x = width + horizontalGap;
        itemsInRow.add(block);
      }
    }
    
    return new Size2D(constraint.getWidth(), y + maxHeight);
  }
  











  protected Size2D arrangeFR(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    Size2D s = arrangeFN(container, g2, constraint);
    if (constraint.getHeightRange().contains(height)) {
      return s;
    }
    
    RectangleConstraint c = constraint.toFixedHeight(constraint.getHeightRange().constrain(s.getHeight()));
    

    return arrangeFF(container, g2, c);
  }
  













  protected Size2D arrangeFF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    return arrangeFN(container, g2, constraint);
  }
  













  protected Size2D arrangeRR(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    Size2D s1 = arrangeNN(container, g2);
    if (constraint.getWidthRange().contains(width)) {
      return s1;
    }
    
    RectangleConstraint c = constraint.toFixedWidth(constraint.getWidthRange().getUpperBound());
    

    return arrangeFR(container, g2, c);
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
  













  protected Size2D arrangeRN(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    Size2D s1 = arrangeNN(container, g2);
    if (constraint.getWidthRange().contains(width)) {
      return s1;
    }
    
    RectangleConstraint c = constraint.toFixedWidth(constraint.getWidthRange().getUpperBound());
    

    return arrangeFN(container, g2, c);
  }
  









  protected Size2D arrangeNN(BlockContainer container, Graphics2D g2)
  {
    double x = 0.0D;
    double width = 0.0D;
    double maxHeight = 0.0D;
    List blocks = container.getBlocks();
    int blockCount = blocks.size();
    if (blockCount > 0) {
      Size2D[] sizes = new Size2D[blocks.size()];
      for (int i = 0; i < blocks.size(); i++) {
        Block block = (Block)blocks.get(i);
        sizes[i] = block.arrange(g2, RectangleConstraint.NONE);
        width += sizes[i].getWidth();
        maxHeight = Math.max(height, maxHeight);
        block.setBounds(new Rectangle2D.Double(x, 0.0D, width, height));
        



        x = x + width + horizontalGap;
      }
      if (blockCount > 1) {
        width += horizontalGap * (blockCount - 1);
      }
      if (verticalAlignment != VerticalAlignment.TOP) {
        for (int i = 0; i < blocks.size(); i++)
        {
          if (verticalAlignment != VerticalAlignment.CENTER)
          {

            if (verticalAlignment != VerticalAlignment.BOTTOM) {}
          }
        }
      }
    }
    

    return new Size2D(width, maxHeight);
  }
  











  protected Size2D arrangeNF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    return arrangeNN(container, g2);
  }
  






  public void clear() {}
  





  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof FlowArrangement)) {
      return false;
    }
    FlowArrangement that = (FlowArrangement)obj;
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
