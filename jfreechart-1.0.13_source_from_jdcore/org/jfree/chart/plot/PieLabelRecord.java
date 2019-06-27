package org.jfree.chart.plot;

import java.io.Serializable;
import org.jfree.text.TextBox;










































































public class PieLabelRecord
  implements Comparable, Serializable
{
  private Comparable key;
  private double angle;
  private double baseY;
  private double allocatedY;
  private TextBox label;
  private double labelHeight;
  private double gap;
  private double linkPercent;
  
  public PieLabelRecord(Comparable key, double angle, double baseY, TextBox label, double labelHeight, double gap, double linkPercent)
  {
    this.key = key;
    this.angle = angle;
    this.baseY = baseY;
    allocatedY = baseY;
    this.label = label;
    this.labelHeight = labelHeight;
    this.gap = gap;
    this.linkPercent = linkPercent;
  }
  





  public double getBaseY()
  {
    return baseY;
  }
  




  public void setBaseY(double base)
  {
    baseY = base;
  }
  




  public double getLowerY()
  {
    return allocatedY - labelHeight / 2.0D;
  }
  




  public double getUpperY()
  {
    return allocatedY + labelHeight / 2.0D;
  }
  




  public double getAngle()
  {
    return angle;
  }
  




  public Comparable getKey()
  {
    return key;
  }
  




  public TextBox getLabel()
  {
    return label;
  }
  





  public double getLabelHeight()
  {
    return labelHeight;
  }
  




  public double getAllocatedY()
  {
    return allocatedY;
  }
  




  public void setAllocatedY(double y)
  {
    allocatedY = y;
  }
  




  public double getGap()
  {
    return gap;
  }
  




  public double getLinkPercent()
  {
    return linkPercent;
  }
  






  public int compareTo(Object obj)
  {
    int result = 0;
    if ((obj instanceof PieLabelRecord)) {
      PieLabelRecord plr = (PieLabelRecord)obj;
      if (baseY < baseY) {
        result = -1;
      }
      else if (baseY > baseY) {
        result = 1;
      }
    }
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof PieLabelRecord)) {
      return false;
    }
    PieLabelRecord that = (PieLabelRecord)obj;
    if (!key.equals(key)) {
      return false;
    }
    if (angle != angle) {
      return false;
    }
    if (gap != gap) {
      return false;
    }
    if (allocatedY != allocatedY) {
      return false;
    }
    if (baseY != baseY) {
      return false;
    }
    if (labelHeight != labelHeight) {
      return false;
    }
    if (linkPercent != linkPercent) {
      return false;
    }
    if (!label.equals(label)) {
      return false;
    }
    return true;
  }
  




  public String toString()
  {
    return baseY + ", " + key.toString();
  }
}
