package org.jfree.chart.axis;

import java.io.Serializable;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;





































































public class CategoryLabelPosition
  implements Serializable
{
  private static final long serialVersionUID = 5168681143844183864L;
  private RectangleAnchor categoryAnchor;
  private TextBlockAnchor labelAnchor;
  private TextAnchor rotationAnchor;
  private double angle;
  private CategoryLabelWidthType widthType;
  private float widthRatio;
  
  public CategoryLabelPosition()
  {
    this(RectangleAnchor.CENTER, TextBlockAnchor.BOTTOM_CENTER, TextAnchor.CENTER, 0.0D, CategoryLabelWidthType.CATEGORY, 0.95F);
  }
  









  public CategoryLabelPosition(RectangleAnchor categoryAnchor, TextBlockAnchor labelAnchor)
  {
    this(categoryAnchor, labelAnchor, TextAnchor.CENTER, 0.0D, CategoryLabelWidthType.CATEGORY, 0.95F);
  }
  














  public CategoryLabelPosition(RectangleAnchor categoryAnchor, TextBlockAnchor labelAnchor, CategoryLabelWidthType widthType, float widthRatio)
  {
    this(categoryAnchor, labelAnchor, TextAnchor.CENTER, 0.0D, widthType, widthRatio);
  }
  






















  public CategoryLabelPosition(RectangleAnchor categoryAnchor, TextBlockAnchor labelAnchor, TextAnchor rotationAnchor, double angle, CategoryLabelWidthType widthType, float widthRatio)
  {
    if (categoryAnchor == null) {
      throw new IllegalArgumentException("Null 'categoryAnchor' argument.");
    }
    
    if (labelAnchor == null) {
      throw new IllegalArgumentException("Null 'labelAnchor' argument.");
    }
    
    if (rotationAnchor == null) {
      throw new IllegalArgumentException("Null 'rotationAnchor' argument.");
    }
    
    if (widthType == null) {
      throw new IllegalArgumentException("Null 'widthType' argument.");
    }
    
    this.categoryAnchor = categoryAnchor;
    this.labelAnchor = labelAnchor;
    this.rotationAnchor = rotationAnchor;
    this.angle = angle;
    this.widthType = widthType;
    this.widthRatio = widthRatio;
  }
  





  public RectangleAnchor getCategoryAnchor()
  {
    return categoryAnchor;
  }
  




  public TextBlockAnchor getLabelAnchor()
  {
    return labelAnchor;
  }
  




  public TextAnchor getRotationAnchor()
  {
    return rotationAnchor;
  }
  




  public double getAngle()
  {
    return angle;
  }
  




  public CategoryLabelWidthType getWidthType()
  {
    return widthType;
  }
  




  public float getWidthRatio()
  {
    return widthRatio;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof CategoryLabelPosition)) {
      return false;
    }
    CategoryLabelPosition that = (CategoryLabelPosition)obj;
    if (!categoryAnchor.equals(categoryAnchor)) {
      return false;
    }
    if (!labelAnchor.equals(labelAnchor)) {
      return false;
    }
    if (!rotationAnchor.equals(rotationAnchor)) {
      return false;
    }
    if (angle != angle) {
      return false;
    }
    if (widthType != widthType) {
      return false;
    }
    if (widthRatio != widthRatio) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 19;
    result = 37 * result + categoryAnchor.hashCode();
    result = 37 * result + labelAnchor.hashCode();
    result = 37 * result + rotationAnchor.hashCode();
    return result;
  }
}
