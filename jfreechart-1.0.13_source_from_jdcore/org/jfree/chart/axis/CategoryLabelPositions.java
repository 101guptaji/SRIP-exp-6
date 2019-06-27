package org.jfree.chart.axis;

import java.io.Serializable;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;



















































public class CategoryLabelPositions
  implements Serializable
{
  private static final long serialVersionUID = -8999557901920364580L;
  public static final CategoryLabelPositions STANDARD = new CategoryLabelPositions(new CategoryLabelPosition(RectangleAnchor.BOTTOM, TextBlockAnchor.BOTTOM_CENTER), new CategoryLabelPosition(RectangleAnchor.TOP, TextBlockAnchor.TOP_CENTER), new CategoryLabelPosition(RectangleAnchor.RIGHT, TextBlockAnchor.CENTER_RIGHT, CategoryLabelWidthType.RANGE, 0.3F), new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT, CategoryLabelWidthType.RANGE, 0.3F));
  

















  public static final CategoryLabelPositions UP_90 = new CategoryLabelPositions(new CategoryLabelPosition(RectangleAnchor.BOTTOM, TextBlockAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, -1.5707963267948966D, CategoryLabelWidthType.RANGE, 0.3F), new CategoryLabelPosition(RectangleAnchor.TOP, TextBlockAnchor.CENTER_RIGHT, TextAnchor.CENTER_RIGHT, -1.5707963267948966D, CategoryLabelWidthType.RANGE, 0.3F), new CategoryLabelPosition(RectangleAnchor.RIGHT, TextBlockAnchor.BOTTOM_CENTER, TextAnchor.BOTTOM_CENTER, -1.5707963267948966D, CategoryLabelWidthType.CATEGORY, 0.9F), new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.TOP_CENTER, TextAnchor.TOP_CENTER, -1.5707963267948966D, CategoryLabelWidthType.CATEGORY, 0.9F));
  























  public static final CategoryLabelPositions DOWN_90 = new CategoryLabelPositions(new CategoryLabelPosition(RectangleAnchor.BOTTOM, TextBlockAnchor.CENTER_RIGHT, TextAnchor.CENTER_RIGHT, 1.5707963267948966D, CategoryLabelWidthType.RANGE, 0.3F), new CategoryLabelPosition(RectangleAnchor.TOP, TextBlockAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, 1.5707963267948966D, CategoryLabelWidthType.RANGE, 0.3F), new CategoryLabelPosition(RectangleAnchor.RIGHT, TextBlockAnchor.TOP_CENTER, TextAnchor.TOP_CENTER, 1.5707963267948966D, CategoryLabelWidthType.CATEGORY, 0.9F), new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.BOTTOM_CENTER, TextAnchor.BOTTOM_CENTER, 1.5707963267948966D, CategoryLabelWidthType.CATEGORY, 0.9F));
  






















  public static final CategoryLabelPositions UP_45 = createUpRotationLabelPositions(0.7853981633974483D);
  


  public static final CategoryLabelPositions DOWN_45 = createDownRotationLabelPositions(0.7853981633974483D);
  
  private CategoryLabelPosition positionForAxisAtTop;
  
  private CategoryLabelPosition positionForAxisAtBottom;
  
  private CategoryLabelPosition positionForAxisAtLeft;
  
  private CategoryLabelPosition positionForAxisAtRight;
  

  public static CategoryLabelPositions createUpRotationLabelPositions(double angle)
  {
    return new CategoryLabelPositions(new CategoryLabelPosition(RectangleAnchor.BOTTOM, TextBlockAnchor.BOTTOM_LEFT, TextAnchor.BOTTOM_LEFT, -angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.TOP, TextBlockAnchor.TOP_RIGHT, TextAnchor.TOP_RIGHT, -angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.RIGHT, TextBlockAnchor.BOTTOM_RIGHT, TextAnchor.BOTTOM_RIGHT, -angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, -angle, CategoryLabelWidthType.RANGE, 0.5F));
  }
  





























  public static CategoryLabelPositions createDownRotationLabelPositions(double angle)
  {
    return new CategoryLabelPositions(new CategoryLabelPosition(RectangleAnchor.BOTTOM, TextBlockAnchor.BOTTOM_RIGHT, TextAnchor.BOTTOM_RIGHT, angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.TOP, TextBlockAnchor.TOP_LEFT, TextAnchor.TOP_LEFT, angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.RIGHT, TextBlockAnchor.TOP_RIGHT, TextAnchor.TOP_RIGHT, angle, CategoryLabelWidthType.RANGE, 0.5F), new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.BOTTOM_LEFT, TextAnchor.BOTTOM_LEFT, angle, CategoryLabelWidthType.RANGE, 0.5F));
  }
  















































  public CategoryLabelPositions()
  {
    positionForAxisAtTop = new CategoryLabelPosition();
    positionForAxisAtBottom = new CategoryLabelPosition();
    positionForAxisAtLeft = new CategoryLabelPosition();
    positionForAxisAtRight = new CategoryLabelPosition();
  }
  















  public CategoryLabelPositions(CategoryLabelPosition top, CategoryLabelPosition bottom, CategoryLabelPosition left, CategoryLabelPosition right)
  {
    if (top == null) {
      throw new IllegalArgumentException("Null 'top' argument.");
    }
    if (bottom == null) {
      throw new IllegalArgumentException("Null 'bottom' argument.");
    }
    if (left == null) {
      throw new IllegalArgumentException("Null 'left' argument.");
    }
    if (right == null) {
      throw new IllegalArgumentException("Null 'right' argument.");
    }
    
    positionForAxisAtTop = top;
    positionForAxisAtBottom = bottom;
    positionForAxisAtLeft = left;
    positionForAxisAtRight = right;
  }
  








  public CategoryLabelPosition getLabelPosition(RectangleEdge edge)
  {
    CategoryLabelPosition result = null;
    if (edge == RectangleEdge.TOP) {
      result = positionForAxisAtTop;
    }
    else if (edge == RectangleEdge.BOTTOM) {
      result = positionForAxisAtBottom;
    }
    else if (edge == RectangleEdge.LEFT) {
      result = positionForAxisAtLeft;
    }
    else if (edge == RectangleEdge.RIGHT) {
      result = positionForAxisAtRight;
    }
    return result;
  }
  










  public static CategoryLabelPositions replaceTopPosition(CategoryLabelPositions base, CategoryLabelPosition top)
  {
    if (base == null) {
      throw new IllegalArgumentException("Null 'base' argument.");
    }
    if (top == null) {
      throw new IllegalArgumentException("Null 'top' argument.");
    }
    
    return new CategoryLabelPositions(top, base.getLabelPosition(RectangleEdge.BOTTOM), base.getLabelPosition(RectangleEdge.LEFT), base.getLabelPosition(RectangleEdge.RIGHT));
  }
  















  public static CategoryLabelPositions replaceBottomPosition(CategoryLabelPositions base, CategoryLabelPosition bottom)
  {
    if (base == null) {
      throw new IllegalArgumentException("Null 'base' argument.");
    }
    if (bottom == null) {
      throw new IllegalArgumentException("Null 'bottom' argument.");
    }
    
    return new CategoryLabelPositions(base.getLabelPosition(RectangleEdge.TOP), bottom, base.getLabelPosition(RectangleEdge.LEFT), base.getLabelPosition(RectangleEdge.RIGHT));
  }
  















  public static CategoryLabelPositions replaceLeftPosition(CategoryLabelPositions base, CategoryLabelPosition left)
  {
    if (base == null) {
      throw new IllegalArgumentException("Null 'base' argument.");
    }
    if (left == null) {
      throw new IllegalArgumentException("Null 'left' argument.");
    }
    
    return new CategoryLabelPositions(base.getLabelPosition(RectangleEdge.TOP), base.getLabelPosition(RectangleEdge.BOTTOM), left, base.getLabelPosition(RectangleEdge.RIGHT));
  }
  















  public static CategoryLabelPositions replaceRightPosition(CategoryLabelPositions base, CategoryLabelPosition right)
  {
    if (base == null) {
      throw new IllegalArgumentException("Null 'base' argument.");
    }
    if (right == null) {
      throw new IllegalArgumentException("Null 'right' argument.");
    }
    
    return new CategoryLabelPositions(base.getLabelPosition(RectangleEdge.TOP), base.getLabelPosition(RectangleEdge.BOTTOM), base.getLabelPosition(RectangleEdge.LEFT), right);
  }
  













  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof CategoryLabelPositions)) {
      return false;
    }
    
    CategoryLabelPositions that = (CategoryLabelPositions)obj;
    if (!positionForAxisAtTop.equals(positionForAxisAtTop)) {
      return false;
    }
    if (!positionForAxisAtBottom.equals(positionForAxisAtBottom))
    {
      return false;
    }
    if (!positionForAxisAtLeft.equals(positionForAxisAtLeft)) {
      return false;
    }
    if (!positionForAxisAtRight.equals(positionForAxisAtRight)) {
      return false;
    }
    
    return true;
  }
  





  public int hashCode()
  {
    int result = 19;
    result = 37 * result + positionForAxisAtTop.hashCode();
    result = 37 * result + positionForAxisAtBottom.hashCode();
    result = 37 * result + positionForAxisAtLeft.hashCode();
    result = 37 * result + positionForAxisAtRight.hashCode();
    return result;
  }
}
