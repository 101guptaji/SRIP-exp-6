package org.jfree.data;

import java.io.Serializable;










































































public class Range
  implements Serializable
{
  private static final long serialVersionUID = -906333695431863380L;
  private double lower;
  private double upper;
  
  public strictfp Range(double lower, double upper)
  {
    if (lower > upper) {
      String msg = "Range(double, double): require lower (" + lower + ") <= upper (" + upper + ").";
      
      throw new IllegalArgumentException(msg);
    }
    this.lower = lower;
    this.upper = upper;
  }
  




  public strictfp double getLowerBound()
  {
    return lower;
  }
  




  public strictfp double getUpperBound()
  {
    return upper;
  }
  




  public strictfp double getLength()
  {
    return upper - lower;
  }
  




  public strictfp double getCentralValue()
  {
    return lower / 2.0D + upper / 2.0D;
  }
  







  public strictfp boolean contains(double value)
  {
    return (value >= lower) && (value <= upper);
  }
  








  public strictfp boolean intersects(double b0, double b1)
  {
    if (b0 <= lower) {
      return b1 > lower;
    }
    
    return (b0 < upper) && (b1 >= b0);
  }
  










  public strictfp boolean intersects(Range range)
  {
    return intersects(range.getLowerBound(), range.getUpperBound());
  }
  







  public strictfp double constrain(double value)
  {
    double result = value;
    if (!contains(value)) {
      if (value > upper) {
        result = upper;
      }
      else if (value < lower) {
        result = lower;
      }
    }
    return result;
  }
  















  public static strictfp Range combine(Range range1, Range range2)
  {
    if (range1 == null) {
      return range2;
    }
    
    if (range2 == null) {
      return range1;
    }
    
    double l = Math.min(range1.getLowerBound(), range2.getLowerBound());
    
    double u = Math.max(range1.getUpperBound(), range2.getUpperBound());
    
    return new Range(l, u);
  }
  












  public static strictfp Range expandToInclude(Range range, double value)
  {
    if (range == null) {
      return new Range(value, value);
    }
    if (value < range.getLowerBound()) {
      return new Range(value, range.getUpperBound());
    }
    if (value > range.getUpperBound()) {
      return new Range(range.getLowerBound(), value);
    }
    
    return range;
  }
  












  public static strictfp Range expand(Range range, double lowerMargin, double upperMargin)
  {
    if (range == null) {
      throw new IllegalArgumentException("Null 'range' argument.");
    }
    double length = range.getLength();
    double lower = range.getLowerBound() - length * lowerMargin;
    double upper = range.getUpperBound() + length * upperMargin;
    if (lower > upper) {
      lower = lower / 2.0D + upper / 2.0D;
      upper = lower;
    }
    return new Range(lower, upper);
  }
  







  public static strictfp Range shift(Range base, double delta)
  {
    return shift(base, delta, false);
  }
  











  public static strictfp Range shift(Range base, double delta, boolean allowZeroCrossing)
  {
    if (base == null) {
      throw new IllegalArgumentException("Null 'base' argument.");
    }
    if (allowZeroCrossing) {
      return new Range(base.getLowerBound() + delta, base.getUpperBound() + delta);
    }
    

    return new Range(shiftWithNoZeroCrossing(base.getLowerBound(), delta), shiftWithNoZeroCrossing(base.getUpperBound(), delta));
  }
  











  private static strictfp double shiftWithNoZeroCrossing(double value, double delta)
  {
    if (value > 0.0D) {
      return Math.max(value + delta, 0.0D);
    }
    if (value < 0.0D) {
      return Math.min(value + delta, 0.0D);
    }
    
    return value + delta;
  }
  










  public static strictfp Range scale(Range base, double factor)
  {
    if (base == null) {
      throw new IllegalArgumentException("Null 'base' argument.");
    }
    if (factor < 0.0D) {
      throw new IllegalArgumentException("Negative 'factor' argument.");
    }
    return new Range(base.getLowerBound() * factor, base.getUpperBound() * factor);
  }
  







  public strictfp boolean equals(Object obj)
  {
    if (!(obj instanceof Range)) {
      return false;
    }
    Range range = (Range)obj;
    if (lower != lower) {
      return false;
    }
    if (upper != upper) {
      return false;
    }
    return true;
  }
  






  public strictfp int hashCode()
  {
    long temp = Double.doubleToLongBits(lower);
    int result = (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(upper);
    result = 29 * result + (int)(temp ^ temp >>> 32);
    return result;
  }
  





  public strictfp String toString()
  {
    return "Range[" + lower + "," + upper + "]";
  }
}
