package org.jfree.chart.block;

import org.jfree.data.Range;
import org.jfree.ui.Size2D;




















































public class RectangleConstraint
{
  public static final RectangleConstraint NONE = new RectangleConstraint(0.0D, null, LengthConstraintType.NONE, 0.0D, null, LengthConstraintType.NONE);
  


  private double width;
  


  private Range widthRange;
  


  private LengthConstraintType widthConstraintType;
  


  private double height;
  

  private Range heightRange;
  

  private LengthConstraintType heightConstraintType;
  


  public RectangleConstraint(double w, double h)
  {
    this(w, null, LengthConstraintType.FIXED, h, null, LengthConstraintType.FIXED);
  }
  






  public RectangleConstraint(Range w, Range h)
  {
    this(0.0D, w, LengthConstraintType.RANGE, 0.0D, h, LengthConstraintType.RANGE);
  }
  







  public RectangleConstraint(Range w, double h)
  {
    this(0.0D, w, LengthConstraintType.RANGE, h, null, LengthConstraintType.FIXED);
  }
  







  public RectangleConstraint(double w, Range h)
  {
    this(w, null, LengthConstraintType.FIXED, 0.0D, h, LengthConstraintType.RANGE);
  }
  













  public RectangleConstraint(double w, Range widthRange, LengthConstraintType widthConstraintType, double h, Range heightRange, LengthConstraintType heightConstraintType)
  {
    if (widthConstraintType == null) {
      throw new IllegalArgumentException("Null 'widthType' argument.");
    }
    if (heightConstraintType == null) {
      throw new IllegalArgumentException("Null 'heightType' argument.");
    }
    width = w;
    this.widthRange = widthRange;
    this.widthConstraintType = widthConstraintType;
    height = h;
    this.heightRange = heightRange;
    this.heightConstraintType = heightConstraintType;
  }
  




  public double getWidth()
  {
    return width;
  }
  




  public Range getWidthRange()
  {
    return widthRange;
  }
  




  public LengthConstraintType getWidthConstraintType()
  {
    return widthConstraintType;
  }
  




  public double getHeight()
  {
    return height;
  }
  




  public Range getHeightRange()
  {
    return heightRange;
  }
  




  public LengthConstraintType getHeightConstraintType()
  {
    return heightConstraintType;
  }
  





  public RectangleConstraint toUnconstrainedWidth()
  {
    if (widthConstraintType == LengthConstraintType.NONE) {
      return this;
    }
    
    return new RectangleConstraint(width, widthRange, LengthConstraintType.NONE, height, heightRange, heightConstraintType);
  }
  








  public RectangleConstraint toUnconstrainedHeight()
  {
    if (heightConstraintType == LengthConstraintType.NONE) {
      return this;
    }
    
    return new RectangleConstraint(width, widthRange, widthConstraintType, 0.0D, heightRange, LengthConstraintType.NONE);
  }
  










  public RectangleConstraint toFixedWidth(double width)
  {
    return new RectangleConstraint(width, widthRange, LengthConstraintType.FIXED, height, heightRange, heightConstraintType);
  }
  









  public RectangleConstraint toFixedHeight(double height)
  {
    return new RectangleConstraint(width, widthRange, widthConstraintType, height, heightRange, LengthConstraintType.FIXED);
  }
  









  public RectangleConstraint toRangeWidth(Range range)
  {
    if (range == null) {
      throw new IllegalArgumentException("Null 'range' argument.");
    }
    return new RectangleConstraint(range.getUpperBound(), range, LengthConstraintType.RANGE, height, heightRange, heightConstraintType);
  }
  









  public RectangleConstraint toRangeHeight(Range range)
  {
    if (range == null) {
      throw new IllegalArgumentException("Null 'range' argument.");
    }
    return new RectangleConstraint(width, widthRange, widthConstraintType, range.getUpperBound(), range, LengthConstraintType.RANGE);
  }
  







  public String toString()
  {
    return "RectangleConstraint[" + widthConstraintType.toString() + ": width=" + width + ", height=" + height + "]";
  }
  









  public Size2D calculateConstrainedSize(Size2D base)
  {
    Size2D result = new Size2D();
    if (widthConstraintType == LengthConstraintType.NONE) {
      width = width;
      if (heightConstraintType == LengthConstraintType.NONE) {
        height = height;
      }
      else if (heightConstraintType == LengthConstraintType.RANGE) {
        height = heightRange.constrain(height);
      }
      else if (heightConstraintType == LengthConstraintType.FIXED) {
        height = height;
      }
    }
    else if (widthConstraintType == LengthConstraintType.RANGE) {
      width = widthRange.constrain(width);
      if (heightConstraintType == LengthConstraintType.NONE) {
        height = height;
      }
      else if (heightConstraintType == LengthConstraintType.RANGE) {
        height = heightRange.constrain(height);
      }
      else if (heightConstraintType == LengthConstraintType.FIXED) {
        height = height;
      }
    }
    else if (widthConstraintType == LengthConstraintType.FIXED) {
      width = width;
      if (heightConstraintType == LengthConstraintType.NONE) {
        height = height;
      }
      else if (heightConstraintType == LengthConstraintType.RANGE) {
        height = heightRange.constrain(height);
      }
      else if (heightConstraintType == LengthConstraintType.FIXED) {
        height = height;
      }
    }
    return result;
  }
}
