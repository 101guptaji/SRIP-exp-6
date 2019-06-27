package org.jfree.chart.block;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import org.jfree.data.Range;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.Size2D;
import org.jfree.util.ObjectUtilities;









































































public class BorderArrangement
  implements Arrangement, Serializable
{
  private static final long serialVersionUID = 506071142274883745L;
  private Block centerBlock;
  private Block topBlock;
  private Block bottomBlock;
  private Block leftBlock;
  private Block rightBlock;
  
  public BorderArrangement() {}
  
  public void add(Block block, Object key)
  {
    if (key == null) {
      centerBlock = block;
    }
    else {
      RectangleEdge edge = (RectangleEdge)key;
      if (edge == RectangleEdge.TOP) {
        topBlock = block;
      }
      else if (edge == RectangleEdge.BOTTOM) {
        bottomBlock = block;
      }
      else if (edge == RectangleEdge.LEFT) {
        leftBlock = block;
      }
      else if (edge == RectangleEdge.RIGHT) {
        rightBlock = block;
      }
    }
  }
  











  public Size2D arrange(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    RectangleConstraint contentConstraint = container.toContentConstraint(constraint);
    
    Size2D contentSize = null;
    LengthConstraintType w = contentConstraint.getWidthConstraintType();
    LengthConstraintType h = contentConstraint.getHeightConstraintType();
    if (w == LengthConstraintType.NONE) {
      if (h == LengthConstraintType.NONE) {
        contentSize = arrangeNN(container, g2);
      } else {
        if (h == LengthConstraintType.FIXED) {
          throw new RuntimeException("Not implemented.");
        }
        if (h == LengthConstraintType.RANGE) {
          throw new RuntimeException("Not implemented.");
        }
      }
    } else if (w == LengthConstraintType.FIXED) {
      if (h == LengthConstraintType.NONE) {
        contentSize = arrangeFN(container, g2, constraint.getWidth());
      }
      else if (h == LengthConstraintType.FIXED) {
        contentSize = arrangeFF(container, g2, constraint);
      }
      else if (h == LengthConstraintType.RANGE) {
        contentSize = arrangeFR(container, g2, constraint);
      }
    }
    else if (w == LengthConstraintType.RANGE) {
      if (h == LengthConstraintType.NONE) {
        throw new RuntimeException("Not implemented.");
      }
      if (h == LengthConstraintType.FIXED) {
        throw new RuntimeException("Not implemented.");
      }
      if (h == LengthConstraintType.RANGE) {
        contentSize = arrangeRR(container, constraint.getWidthRange(), constraint.getHeightRange(), g2);
      }
    }
    
    return new Size2D(container.calculateTotalWidth(contentSize.getWidth()), container.calculateTotalHeight(contentSize.getHeight()));
  }
  








  protected Size2D arrangeNN(BlockContainer container, Graphics2D g2)
  {
    double[] w = new double[5];
    double[] h = new double[5];
    if (topBlock != null) {
      Size2D size = topBlock.arrange(g2, RectangleConstraint.NONE);
      w[0] = width;
      h[0] = height;
    }
    if (bottomBlock != null) {
      Size2D size = bottomBlock.arrange(g2, RectangleConstraint.NONE);
      
      w[1] = width;
      h[1] = height;
    }
    if (leftBlock != null) {
      Size2D size = leftBlock.arrange(g2, RectangleConstraint.NONE);
      w[2] = width;
      h[2] = height;
    }
    if (rightBlock != null) {
      Size2D size = rightBlock.arrange(g2, RectangleConstraint.NONE);
      w[3] = width;
      h[3] = height;
    }
    
    h[2] = Math.max(h[2], h[3]);
    h[3] = h[2];
    
    if (centerBlock != null) {
      Size2D size = centerBlock.arrange(g2, RectangleConstraint.NONE);
      
      w[4] = width;
      h[4] = height;
    }
    double width = Math.max(w[0], Math.max(w[1], w[2] + w[4] + w[3]));
    double centerHeight = Math.max(h[2], Math.max(h[3], h[4]));
    double height = h[0] + h[1] + centerHeight;
    if (topBlock != null) {
      topBlock.setBounds(new Rectangle2D.Double(0.0D, 0.0D, width, h[0]));
    }
    
    if (bottomBlock != null) {
      bottomBlock.setBounds(new Rectangle2D.Double(0.0D, height - h[1], width, h[1]));
    }
    
    if (leftBlock != null) {
      leftBlock.setBounds(new Rectangle2D.Double(0.0D, h[0], w[2], centerHeight));
    }
    
    if (rightBlock != null) {
      rightBlock.setBounds(new Rectangle2D.Double(width - w[3], h[0], w[3], centerHeight));
    }
    

    if (centerBlock != null) {
      centerBlock.setBounds(new Rectangle2D.Double(w[2], h[0], width - w[2] - w[3], centerHeight));
    }
    
    return new Size2D(width, height);
  }
  









  protected Size2D arrangeFR(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    Size2D size1 = arrangeFN(container, g2, constraint.getWidth());
    if (constraint.getHeightRange().contains(size1.getHeight())) {
      return size1;
    }
    
    double h = constraint.getHeightRange().constrain(size1.getHeight());
    RectangleConstraint c2 = constraint.toFixedHeight(h);
    return arrange(container, g2, c2);
  }
  











  protected Size2D arrangeFN(BlockContainer container, Graphics2D g2, double width)
  {
    double[] w = new double[5];
    double[] h = new double[5];
    RectangleConstraint c1 = new RectangleConstraint(width, null, LengthConstraintType.FIXED, 0.0D, null, LengthConstraintType.NONE);
    

    if (topBlock != null) {
      Size2D size = topBlock.arrange(g2, c1);
      w[0] = width;
      h[0] = height;
    }
    if (bottomBlock != null) {
      Size2D size = bottomBlock.arrange(g2, c1);
      w[1] = width;
      h[1] = height;
    }
    RectangleConstraint c2 = new RectangleConstraint(0.0D, new Range(0.0D, width), LengthConstraintType.RANGE, 0.0D, null, LengthConstraintType.NONE);
    

    if (leftBlock != null) {
      Size2D size = leftBlock.arrange(g2, c2);
      w[2] = width;
      h[2] = height;
    }
    if (rightBlock != null) {
      double maxW = Math.max(width - w[2], 0.0D);
      RectangleConstraint c3 = new RectangleConstraint(0.0D, new Range(Math.min(w[2], maxW), maxW), LengthConstraintType.RANGE, 0.0D, null, LengthConstraintType.NONE);
      


      Size2D size = rightBlock.arrange(g2, c3);
      w[3] = width;
      h[3] = height;
    }
    
    h[2] = Math.max(h[2], h[3]);
    h[3] = h[2];
    
    if (centerBlock != null) {
      RectangleConstraint c4 = new RectangleConstraint(width - w[2] - w[3], null, LengthConstraintType.FIXED, 0.0D, null, LengthConstraintType.NONE);
      

      Size2D size = centerBlock.arrange(g2, c4);
      w[4] = width;
      h[4] = height;
    }
    double height = h[0] + h[1] + Math.max(h[2], Math.max(h[3], h[4]));
    return arrange(container, g2, new RectangleConstraint(width, height));
  }
  












  protected Size2D arrangeRR(BlockContainer container, Range widthRange, Range heightRange, Graphics2D g2)
  {
    double[] w = new double[5];
    double[] h = new double[5];
    if (topBlock != null) {
      RectangleConstraint c1 = new RectangleConstraint(widthRange, heightRange);
      
      Size2D size = topBlock.arrange(g2, c1);
      w[0] = width;
      h[0] = height;
    }
    if (bottomBlock != null) {
      Range heightRange2 = Range.shift(heightRange, -h[0], false);
      RectangleConstraint c2 = new RectangleConstraint(widthRange, heightRange2);
      
      Size2D size = bottomBlock.arrange(g2, c2);
      w[1] = width;
      h[1] = height;
    }
    Range heightRange3 = Range.shift(heightRange, -(h[0] + h[1]));
    if (leftBlock != null) {
      RectangleConstraint c3 = new RectangleConstraint(widthRange, heightRange3);
      
      Size2D size = leftBlock.arrange(g2, c3);
      w[2] = width;
      h[2] = height;
    }
    Range widthRange2 = Range.shift(widthRange, -w[2], false);
    if (rightBlock != null) {
      RectangleConstraint c4 = new RectangleConstraint(widthRange2, heightRange3);
      
      Size2D size = rightBlock.arrange(g2, c4);
      w[3] = width;
      h[3] = height;
    }
    
    h[2] = Math.max(h[2], h[3]);
    h[3] = h[2];
    Range widthRange3 = Range.shift(widthRange, -(w[2] + w[3]), false);
    if (centerBlock != null) {
      RectangleConstraint c5 = new RectangleConstraint(widthRange3, heightRange3);
      
      Size2D size = centerBlock.arrange(g2, c5);
      w[4] = width;
      h[4] = height;
    }
    double width = Math.max(w[0], Math.max(w[1], w[2] + w[4] + w[3]));
    double height = h[0] + h[1] + Math.max(h[2], Math.max(h[3], h[4]));
    if (topBlock != null) {
      topBlock.setBounds(new Rectangle2D.Double(0.0D, 0.0D, width, h[0]));
    }
    
    if (bottomBlock != null) {
      bottomBlock.setBounds(new Rectangle2D.Double(0.0D, height - h[1], width, h[1]));
    }
    
    if (leftBlock != null) {
      leftBlock.setBounds(new Rectangle2D.Double(0.0D, h[0], w[2], h[2]));
    }
    
    if (rightBlock != null) {
      rightBlock.setBounds(new Rectangle2D.Double(width - w[3], h[0], w[3], h[3]));
    }
    

    if (centerBlock != null) {
      centerBlock.setBounds(new Rectangle2D.Double(w[2], h[0], width - w[2] - w[3], height - h[0] - h[1]));
    }
    
    return new Size2D(width, height);
  }
  









  protected Size2D arrangeFF(BlockContainer container, Graphics2D g2, RectangleConstraint constraint)
  {
    double[] w = new double[5];
    double[] h = new double[5];
    w[0] = constraint.getWidth();
    if (topBlock != null) {
      RectangleConstraint c1 = new RectangleConstraint(w[0], null, LengthConstraintType.FIXED, 0.0D, new Range(0.0D, constraint.getHeight()), LengthConstraintType.RANGE);
      


      Size2D size = topBlock.arrange(g2, c1);
      h[0] = height;
    }
    w[1] = w[0];
    if (bottomBlock != null) {
      RectangleConstraint c2 = new RectangleConstraint(w[0], null, LengthConstraintType.FIXED, 0.0D, new Range(0.0D, constraint.getHeight() - h[0]), LengthConstraintType.RANGE);
      

      Size2D size = bottomBlock.arrange(g2, c2);
      h[1] = height;
    }
    h[2] = (constraint.getHeight() - h[1] - h[0]);
    if (leftBlock != null) {
      RectangleConstraint c3 = new RectangleConstraint(0.0D, new Range(0.0D, constraint.getWidth()), LengthConstraintType.RANGE, h[2], null, LengthConstraintType.FIXED);
      


      Size2D size = leftBlock.arrange(g2, c3);
      w[2] = width;
    }
    h[3] = h[2];
    if (rightBlock != null) {
      RectangleConstraint c4 = new RectangleConstraint(0.0D, new Range(0.0D, Math.max(constraint.getWidth() - w[2], 0.0D)), LengthConstraintType.RANGE, h[2], null, LengthConstraintType.FIXED);
      


      Size2D size = rightBlock.arrange(g2, c4);
      w[3] = width;
    }
    h[4] = h[2];
    w[4] = (constraint.getWidth() - w[3] - w[2]);
    RectangleConstraint c5 = new RectangleConstraint(w[4], h[4]);
    if (centerBlock != null) {
      centerBlock.arrange(g2, c5);
    }
    
    if (topBlock != null) {
      topBlock.setBounds(new Rectangle2D.Double(0.0D, 0.0D, w[0], h[0]));
    }
    
    if (bottomBlock != null) {
      bottomBlock.setBounds(new Rectangle2D.Double(0.0D, h[0] + h[2], w[1], h[1]));
    }
    
    if (leftBlock != null) {
      leftBlock.setBounds(new Rectangle2D.Double(0.0D, h[0], w[2], h[2]));
    }
    
    if (rightBlock != null) {
      rightBlock.setBounds(new Rectangle2D.Double(w[2] + w[4], h[0], w[3], h[3]));
    }
    
    if (centerBlock != null) {
      centerBlock.setBounds(new Rectangle2D.Double(w[2], h[0], w[4], h[4]));
    }
    
    return new Size2D(constraint.getWidth(), constraint.getHeight());
  }
  


  public void clear()
  {
    centerBlock = null;
    topBlock = null;
    bottomBlock = null;
    leftBlock = null;
    rightBlock = null;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BorderArrangement)) {
      return false;
    }
    BorderArrangement that = (BorderArrangement)obj;
    if (!ObjectUtilities.equal(topBlock, topBlock)) {
      return false;
    }
    if (!ObjectUtilities.equal(bottomBlock, bottomBlock)) {
      return false;
    }
    if (!ObjectUtilities.equal(leftBlock, leftBlock)) {
      return false;
    }
    if (!ObjectUtilities.equal(rightBlock, rightBlock)) {
      return false;
    }
    if (!ObjectUtilities.equal(centerBlock, centerBlock)) {
      return false;
    }
    return true;
  }
}
