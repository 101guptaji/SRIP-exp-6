package org.jfree.chart.renderer;

import java.awt.Color;
import java.awt.Paint;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.util.PublicCloneable;





























































public class GrayPaintScale
  implements PaintScale, PublicCloneable, Serializable
{
  private double lowerBound;
  private double upperBound;
  private int alpha;
  
  public GrayPaintScale()
  {
    this(0.0D, 1.0D);
  }
  








  public GrayPaintScale(double lowerBound, double upperBound)
  {
    this(lowerBound, upperBound, 255);
  }
  












  public GrayPaintScale(double lowerBound, double upperBound, int alpha)
  {
    if (lowerBound >= upperBound) {
      throw new IllegalArgumentException("Requires lowerBound < upperBound.");
    }
    
    if ((alpha < 0) || (alpha > 255)) {
      throw new IllegalArgumentException("Requires alpha in the range 0 to 255.");
    }
    

    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.alpha = alpha;
  }
  






  public double getLowerBound()
  {
    return lowerBound;
  }
  






  public double getUpperBound()
  {
    return upperBound;
  }
  






  public int getAlpha()
  {
    return alpha;
  }
  







  public Paint getPaint(double value)
  {
    double v = Math.max(value, lowerBound);
    v = Math.min(v, upperBound);
    int g = (int)((v - lowerBound) / (upperBound - lowerBound) * 255.0D);
    


    return new Color(g, g, g, alpha);
  }
  












  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof GrayPaintScale)) {
      return false;
    }
    GrayPaintScale that = (GrayPaintScale)obj;
    if (lowerBound != lowerBound) {
      return false;
    }
    if (upperBound != upperBound) {
      return false;
    }
    if (alpha != alpha) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int hash = 7;
    hash = HashUtilities.hashCode(hash, lowerBound);
    hash = HashUtilities.hashCode(hash, upperBound);
    hash = 43 * hash + alpha;
    return hash;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
