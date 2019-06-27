package org.jfree.chart.axis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextBlock;
import org.jfree.text.TextFragment;
import org.jfree.text.TextLine;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.PaintUtilities;




























































public class ExtendedCategoryAxis
  extends CategoryAxis
{
  static final long serialVersionUID = -3004429093959826567L;
  private Map sublabels;
  private Font sublabelFont;
  private transient Paint sublabelPaint;
  
  public ExtendedCategoryAxis(String label)
  {
    super(label);
    sublabels = new HashMap();
    sublabelFont = new Font("SansSerif", 0, 10);
    sublabelPaint = Color.black;
  }
  






  public Font getSubLabelFont()
  {
    return sublabelFont;
  }
  







  public void setSubLabelFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    sublabelFont = font;
    notifyListeners(new AxisChangeEvent(this));
  }
  






  public Paint getSubLabelPaint()
  {
    return sublabelPaint;
  }
  







  public void setSubLabelPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    sublabelPaint = paint;
    notifyListeners(new AxisChangeEvent(this));
  }
  





  public void addSubLabel(Comparable category, String label)
  {
    sublabels.put(category, label);
  }
  











  protected TextBlock createLabel(Comparable category, float width, RectangleEdge edge, Graphics2D g2)
  {
    TextBlock label = super.createLabel(category, width, edge, g2);
    String s = (String)sublabels.get(category);
    if (s != null) {
      if ((edge == RectangleEdge.TOP) || (edge == RectangleEdge.BOTTOM)) {
        TextLine line = new TextLine(s, sublabelFont, sublabelPaint);
        
        label.addLine(line);
      }
      else if ((edge == RectangleEdge.LEFT) || (edge == RectangleEdge.RIGHT))
      {
        TextLine line = label.getLastLine();
        if (line != null) {
          line.addFragment(new TextFragment("  " + s, sublabelFont, sublabelPaint));
        }
      }
    }
    
    return label;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ExtendedCategoryAxis)) {
      return false;
    }
    ExtendedCategoryAxis that = (ExtendedCategoryAxis)obj;
    if (!sublabelFont.equals(sublabelFont)) {
      return false;
    }
    if (!PaintUtilities.equal(sublabelPaint, sublabelPaint)) {
      return false;
    }
    if (!sublabels.equals(sublabels)) {
      return false;
    }
    return super.equals(obj);
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    ExtendedCategoryAxis clone = (ExtendedCategoryAxis)super.clone();
    sublabels = new HashMap(sublabels);
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(sublabelPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    sublabelPaint = SerialUtilities.readPaint(stream);
  }
}
