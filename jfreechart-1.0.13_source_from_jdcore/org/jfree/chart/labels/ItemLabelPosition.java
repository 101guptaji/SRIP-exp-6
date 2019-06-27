package org.jfree.chart.labels;

import java.io.Serializable;
import org.jfree.ui.TextAnchor;



























































public class ItemLabelPosition
  implements Serializable
{
  private static final long serialVersionUID = 5845390630157034499L;
  private ItemLabelAnchor itemLabelAnchor;
  private TextAnchor textAnchor;
  private TextAnchor rotationAnchor;
  private double angle;
  
  public ItemLabelPosition()
  {
    this(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER, TextAnchor.CENTER, 0.0D);
  }
  








  public ItemLabelPosition(ItemLabelAnchor itemLabelAnchor, TextAnchor textAnchor)
  {
    this(itemLabelAnchor, textAnchor, TextAnchor.CENTER, 0.0D);
  }
  
















  public ItemLabelPosition(ItemLabelAnchor itemLabelAnchor, TextAnchor textAnchor, TextAnchor rotationAnchor, double angle)
  {
    if (itemLabelAnchor == null) {
      throw new IllegalArgumentException("Null 'itemLabelAnchor' argument.");
    }
    
    if (textAnchor == null) {
      throw new IllegalArgumentException("Null 'textAnchor' argument.");
    }
    if (rotationAnchor == null) {
      throw new IllegalArgumentException("Null 'rotationAnchor' argument.");
    }
    

    this.itemLabelAnchor = itemLabelAnchor;
    this.textAnchor = textAnchor;
    this.rotationAnchor = rotationAnchor;
    this.angle = angle;
  }
  





  public ItemLabelAnchor getItemLabelAnchor()
  {
    return itemLabelAnchor;
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
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ItemLabelPosition)) {
      return false;
    }
    ItemLabelPosition that = (ItemLabelPosition)obj;
    if (!itemLabelAnchor.equals(itemLabelAnchor)) {
      return false;
    }
    if (!textAnchor.equals(textAnchor)) {
      return false;
    }
    if (!rotationAnchor.equals(rotationAnchor)) {
      return false;
    }
    if (angle != angle) {
      return false;
    }
    return true;
  }
}
