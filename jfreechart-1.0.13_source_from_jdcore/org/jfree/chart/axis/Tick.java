package org.jfree.chart.axis;

import java.io.Serializable;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;



































































public abstract class Tick
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = 6668230383875149773L;
  private String text;
  private TextAnchor textAnchor;
  private TextAnchor rotationAnchor;
  private double angle;
  
  public Tick(String text, TextAnchor textAnchor, TextAnchor rotationAnchor, double angle)
  {
    if (textAnchor == null) {
      throw new IllegalArgumentException("Null 'textAnchor' argument.");
    }
    if (rotationAnchor == null) {
      throw new IllegalArgumentException("Null 'rotationAnchor' argument.");
    }
    

    this.text = text;
    this.textAnchor = textAnchor;
    this.rotationAnchor = rotationAnchor;
    this.angle = angle;
  }
  




  public String getText()
  {
    return text;
  }
  




  public TextAnchor getTextAnchor()
  {
    return textAnchor;
  }
  





  public TextAnchor getRotationAnchor()
  {
    return rotationAnchor;
  }
  




  public double getAngle()
  {
    return angle;
  }
  






  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if ((obj instanceof Tick)) {
      Tick t = (Tick)obj;
      if (!ObjectUtilities.equal(text, text)) {
        return false;
      }
      if (!ObjectUtilities.equal(textAnchor, textAnchor)) {
        return false;
      }
      if (!ObjectUtilities.equal(rotationAnchor, rotationAnchor)) {
        return false;
      }
      if (angle != angle) {
        return false;
      }
      return true;
    }
    return false;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    Tick clone = (Tick)super.clone();
    return clone;
  }
  




  public String toString()
  {
    return text;
  }
}
