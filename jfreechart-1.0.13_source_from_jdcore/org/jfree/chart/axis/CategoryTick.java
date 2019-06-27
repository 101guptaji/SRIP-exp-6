package org.jfree.chart.axis;

import org.jfree.text.TextBlock;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;






























































public class CategoryTick
  extends Tick
{
  private Comparable category;
  private TextBlock label;
  private TextBlockAnchor labelAnchor;
  
  public CategoryTick(Comparable category, TextBlock label, TextBlockAnchor labelAnchor, TextAnchor rotationAnchor, double angle)
  {
    super("", TextAnchor.CENTER, rotationAnchor, angle);
    this.category = category;
    this.label = label;
    this.labelAnchor = labelAnchor;
  }
  





  public Comparable getCategory()
  {
    return category;
  }
  




  public TextBlock getLabel()
  {
    return label;
  }
  




  public TextBlockAnchor getLabelAnchor()
  {
    return labelAnchor;
  }
  






  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (((obj instanceof CategoryTick)) && (super.equals(obj))) {
      CategoryTick that = (CategoryTick)obj;
      if (!ObjectUtilities.equal(category, category)) {
        return false;
      }
      if (!ObjectUtilities.equal(label, label)) {
        return false;
      }
      if (!ObjectUtilities.equal(labelAnchor, labelAnchor)) {
        return false;
      }
      return true;
    }
    return false;
  }
  




  public int hashCode()
  {
    int result = 41;
    result = 37 * result + category.hashCode();
    result = 37 * result + label.hashCode();
    result = 37 * result + labelAnchor.hashCode();
    return result;
  }
}
