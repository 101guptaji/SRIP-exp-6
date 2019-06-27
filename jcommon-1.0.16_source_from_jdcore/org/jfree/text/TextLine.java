package org.jfree.text;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jfree.ui.Size2D;
import org.jfree.ui.TextAnchor;




























































public class TextLine
  implements Serializable
{
  private static final long serialVersionUID = 7100085690160465444L;
  private List fragments;
  
  public TextLine()
  {
    fragments = new ArrayList();
  }
  




  public TextLine(String text)
  {
    this(text, TextFragment.DEFAULT_FONT);
  }
  





  public TextLine(String text, Font font)
  {
    fragments = new ArrayList();
    TextFragment fragment = new TextFragment(text, font);
    fragments.add(fragment);
  }
  






  public TextLine(String text, Font font, Paint paint)
  {
    if (text == null) {
      throw new IllegalArgumentException("Null 'text' argument.");
    }
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    fragments = new ArrayList();
    TextFragment fragment = new TextFragment(text, font, paint);
    fragments.add(fragment);
  }
  




  public void addFragment(TextFragment fragment)
  {
    fragments.add(fragment);
  }
  




  public void removeFragment(TextFragment fragment)
  {
    fragments.remove(fragment);
  }
  
















  public void draw(Graphics2D g2, float anchorX, float anchorY, TextAnchor anchor, float rotateX, float rotateY, double angle)
  {
    float x = anchorX;
    float yOffset = calculateBaselineOffset(g2, anchor);
    Iterator iterator = fragments.iterator();
    while (iterator.hasNext()) {
      TextFragment fragment = (TextFragment)iterator.next();
      Size2D d = fragment.calculateDimensions(g2);
      fragment.draw(g2, x, anchorY + yOffset, TextAnchor.BASELINE_LEFT, rotateX, rotateY, angle);
      


      x += (float)d.getWidth();
    }
  }
  







  public Size2D calculateDimensions(Graphics2D g2)
  {
    double width = 0.0D;
    double height = 0.0D;
    Iterator iterator = fragments.iterator();
    while (iterator.hasNext()) {
      TextFragment fragment = (TextFragment)iterator.next();
      Size2D dimension = fragment.calculateDimensions(g2);
      width += dimension.getWidth();
      height = Math.max(height, dimension.getHeight());
    }
    return new Size2D(width, height);
  }
  




  public TextFragment getFirstTextFragment()
  {
    TextFragment result = null;
    if (fragments.size() > 0) {
      result = (TextFragment)fragments.get(0);
    }
    return result;
  }
  




  public TextFragment getLastTextFragment()
  {
    TextFragment result = null;
    if (fragments.size() > 0) {
      result = (TextFragment)fragments.get(fragments.size() - 1);
    }
    
    return result;
  }
  









  private float calculateBaselineOffset(Graphics2D g2, TextAnchor anchor)
  {
    float result = 0.0F;
    Iterator iterator = fragments.iterator();
    while (iterator.hasNext()) {
      TextFragment fragment = (TextFragment)iterator.next();
      result = Math.max(result, fragment.calculateBaselineOffset(g2, anchor));
    }
    
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if ((obj instanceof TextLine)) {
      TextLine line = (TextLine)obj;
      return fragments.equals(fragments);
    }
    return false;
  }
  




  public int hashCode()
  {
    return fragments != null ? fragments.hashCode() : 0;
  }
}
