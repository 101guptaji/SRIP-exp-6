package org.jfree.chart.annotations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;



























































public class TextAnnotation
  implements Serializable
{
  private static final long serialVersionUID = 7008912287533127432L;
  public static final Font DEFAULT_FONT = new Font("SansSerif", 0, 10);
  


  public static final Paint DEFAULT_PAINT = Color.black;
  

  public static final TextAnchor DEFAULT_TEXT_ANCHOR = TextAnchor.CENTER;
  

  public static final TextAnchor DEFAULT_ROTATION_ANCHOR = TextAnchor.CENTER;
  


  public static final double DEFAULT_ROTATION_ANGLE = 0.0D;
  


  private String text;
  

  private Font font;
  

  private transient Paint paint;
  

  private TextAnchor textAnchor;
  

  private TextAnchor rotationAnchor;
  

  private double rotationAngle;
  


  protected TextAnnotation(String text)
  {
    if (text == null) {
      throw new IllegalArgumentException("Null 'text' argument.");
    }
    this.text = text;
    font = DEFAULT_FONT;
    paint = DEFAULT_PAINT;
    textAnchor = DEFAULT_TEXT_ANCHOR;
    rotationAnchor = DEFAULT_ROTATION_ANCHOR;
    rotationAngle = 0.0D;
  }
  






  public String getText()
  {
    return text;
  }
  






  public void setText(String text)
  {
    if (text == null) {
      throw new IllegalArgumentException("Null 'text' argument.");
    }
    this.text = text;
  }
  






  public Font getFont()
  {
    return font;
  }
  






  public void setFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    this.font = font;
  }
  






  public Paint getPaint()
  {
    return paint;
  }
  






  public void setPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    this.paint = paint;
  }
  






  public TextAnchor getTextAnchor()
  {
    return textAnchor;
  }
  







  public void setTextAnchor(TextAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    textAnchor = anchor;
  }
  






  public TextAnchor getRotationAnchor()
  {
    return rotationAnchor;
  }
  






  public void setRotationAnchor(TextAnchor anchor)
  {
    rotationAnchor = anchor;
  }
  






  public double getRotationAngle()
  {
    return rotationAngle;
  }
  






  public void setRotationAngle(double angle)
  {
    rotationAngle = angle;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    
    if (!(obj instanceof TextAnnotation)) {
      return false;
    }
    TextAnnotation that = (TextAnnotation)obj;
    if (!ObjectUtilities.equal(text, that.getText())) {
      return false;
    }
    if (!ObjectUtilities.equal(font, that.getFont())) {
      return false;
    }
    if (!PaintUtilities.equal(paint, that.getPaint())) {
      return false;
    }
    if (!ObjectUtilities.equal(textAnchor, that.getTextAnchor())) {
      return false;
    }
    if (!ObjectUtilities.equal(rotationAnchor, that.getRotationAnchor()))
    {
      return false;
    }
    if (rotationAngle != that.getRotationAngle()) {
      return false;
    }
    

    return true;
  }
  





  public int hashCode()
  {
    int result = 193;
    result = 37 * result + font.hashCode();
    result = 37 * result + HashUtilities.hashCodeForPaint(paint);
    result = 37 * result + rotationAnchor.hashCode();
    long temp = Double.doubleToLongBits(rotationAngle);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    result = 37 * result + text.hashCode();
    result = 37 * result + textAnchor.hashCode();
    return result;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(paint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    paint = SerialUtilities.readPaint(stream);
  }
}
