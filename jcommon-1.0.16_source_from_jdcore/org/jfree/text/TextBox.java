package org.jfree.text;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.Size2D;
import org.jfree.util.ObjectUtilities;

































































public class TextBox
  implements Serializable
{
  private static final long serialVersionUID = 3360220213180203706L;
  private transient Paint outlinePaint;
  private transient Stroke outlineStroke;
  private RectangleInsets interiorGap;
  private transient Paint backgroundPaint;
  private transient Paint shadowPaint;
  private double shadowXOffset = 2.0D;
  

  private double shadowYOffset = 2.0D;
  

  private TextBlock textBlock;
  


  public TextBox()
  {
    this((TextBlock)null);
  }
  




  public TextBox(String text)
  {
    this((TextBlock)null);
    if (text != null) {
      textBlock = new TextBlock();
      textBlock.addLine(text, new Font("SansSerif", 0, 10), Color.black);
    }
  }
  







  public TextBox(TextBlock block)
  {
    outlinePaint = Color.black;
    outlineStroke = new BasicStroke(1.0F);
    interiorGap = new RectangleInsets(1.0D, 3.0D, 1.0D, 3.0D);
    backgroundPaint = new Color(255, 255, 192);
    shadowPaint = Color.gray;
    shadowXOffset = 2.0D;
    shadowYOffset = 2.0D;
    textBlock = block;
  }
  




  public Paint getOutlinePaint()
  {
    return outlinePaint;
  }
  




  public void setOutlinePaint(Paint paint)
  {
    outlinePaint = paint;
  }
  




  public Stroke getOutlineStroke()
  {
    return outlineStroke;
  }
  




  public void setOutlineStroke(Stroke stroke)
  {
    outlineStroke = stroke;
  }
  




  public RectangleInsets getInteriorGap()
  {
    return interiorGap;
  }
  




  public void setInteriorGap(RectangleInsets gap)
  {
    interiorGap = gap;
  }
  




  public Paint getBackgroundPaint()
  {
    return backgroundPaint;
  }
  




  public void setBackgroundPaint(Paint paint)
  {
    backgroundPaint = paint;
  }
  




  public Paint getShadowPaint()
  {
    return shadowPaint;
  }
  




  public void setShadowPaint(Paint paint)
  {
    shadowPaint = paint;
  }
  




  public double getShadowXOffset()
  {
    return shadowXOffset;
  }
  




  public void setShadowXOffset(double offset)
  {
    shadowXOffset = offset;
  }
  




  public double getShadowYOffset()
  {
    return shadowYOffset;
  }
  




  public void setShadowYOffset(double offset)
  {
    shadowYOffset = offset;
  }
  




  public TextBlock getTextBlock()
  {
    return textBlock;
  }
  




  public void setTextBlock(TextBlock block)
  {
    textBlock = block;
  }
  









  public void draw(Graphics2D g2, float x, float y, RectangleAnchor anchor)
  {
    Size2D d1 = textBlock.calculateDimensions(g2);
    double w = interiorGap.extendWidth(d1.getWidth());
    double h = interiorGap.extendHeight(d1.getHeight());
    Size2D d2 = new Size2D(w, h);
    Rectangle2D bounds = RectangleAnchor.createRectangle(d2, x, y, anchor);
    
    double xx = bounds.getX();
    double yy = bounds.getY();
    
    if (shadowPaint != null) {
      Rectangle2D shadow = new Rectangle2D.Double(xx + shadowXOffset, yy + shadowYOffset, bounds.getWidth(), bounds.getHeight());
      

      g2.setPaint(shadowPaint);
      g2.fill(shadow);
    }
    if (backgroundPaint != null) {
      g2.setPaint(backgroundPaint);
      g2.fill(bounds);
    }
    
    if ((outlinePaint != null) && (outlineStroke != null)) {
      g2.setPaint(outlinePaint);
      g2.setStroke(outlineStroke);
      g2.draw(bounds);
    }
    
    textBlock.draw(g2, (float)(xx + interiorGap.calculateLeftInset(w)), (float)(yy + interiorGap.calculateTopInset(h)), TextBlockAnchor.TOP_LEFT);
  }
  










  public double getHeight(Graphics2D g2)
  {
    Size2D d = textBlock.calculateDimensions(g2);
    return interiorGap.extendHeight(d.getHeight());
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof TextBox)) {
      return false;
    }
    TextBox that = (TextBox)obj;
    if (!ObjectUtilities.equal(outlinePaint, outlinePaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(outlineStroke, outlineStroke)) {
      return false;
    }
    if (!ObjectUtilities.equal(interiorGap, interiorGap)) {
      return false;
    }
    if (!ObjectUtilities.equal(backgroundPaint, backgroundPaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(shadowPaint, shadowPaint)) {
      return false;
    }
    if (shadowXOffset != shadowXOffset) {
      return false;
    }
    if (shadowYOffset != shadowYOffset) {
      return false;
    }
    if (!ObjectUtilities.equal(textBlock, textBlock)) {
      return false;
    }
    
    return true;
  }
  






  public int hashCode()
  {
    int result = outlinePaint != null ? outlinePaint.hashCode() : 0;
    result = 29 * result + (outlineStroke != null ? outlineStroke.hashCode() : 0);
    
    result = 29 * result + (interiorGap != null ? interiorGap.hashCode() : 0);
    
    result = 29 * result + (backgroundPaint != null ? backgroundPaint.hashCode() : 0);
    
    result = 29 * result + (shadowPaint != null ? shadowPaint.hashCode() : 0);
    
    long temp = shadowXOffset != 0.0D ? Double.doubleToLongBits(shadowXOffset) : 0L;
    
    result = 29 * result + (int)(temp ^ temp >>> 32);
    temp = shadowYOffset != 0.0D ? Double.doubleToLongBits(shadowYOffset) : 0L;
    
    result = 29 * result + (int)(temp ^ temp >>> 32);
    result = 29 * result + (textBlock != null ? textBlock.hashCode() : 0);
    
    return result;
  }
  






  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(outlinePaint, stream);
    SerialUtilities.writeStroke(outlineStroke, stream);
    SerialUtilities.writePaint(backgroundPaint, stream);
    SerialUtilities.writePaint(shadowPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    outlinePaint = SerialUtilities.readPaint(stream);
    outlineStroke = SerialUtilities.readStroke(stream);
    backgroundPaint = SerialUtilities.readPaint(stream);
    shadowPaint = SerialUtilities.readPaint(stream);
  }
}
