package org.jfree.chart.block;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.PaintUtilities;



















































public class BlockBorder
  implements BlockFrame, Serializable
{
  private static final long serialVersionUID = 4961579220410228283L;
  public static final BlockBorder NONE = new BlockBorder(RectangleInsets.ZERO_INSETS, Color.white);
  


  private RectangleInsets insets;
  

  private transient Paint paint;
  


  public BlockBorder()
  {
    this(Color.black);
  }
  




  public BlockBorder(Paint paint)
  {
    this(new RectangleInsets(1.0D, 1.0D, 1.0D, 1.0D), paint);
  }
  







  public BlockBorder(double top, double left, double bottom, double right)
  {
    this(new RectangleInsets(top, left, bottom, right), Color.black);
  }
  









  public BlockBorder(double top, double left, double bottom, double right, Paint paint)
  {
    this(new RectangleInsets(top, left, bottom, right), paint);
  }
  





  public BlockBorder(RectangleInsets insets, Paint paint)
  {
    if (insets == null) {
      throw new IllegalArgumentException("Null 'insets' argument.");
    }
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    this.insets = insets;
    this.paint = paint;
  }
  




  public RectangleInsets getInsets()
  {
    return insets;
  }
  




  public Paint getPaint()
  {
    return paint;
  }
  







  public void draw(Graphics2D g2, Rectangle2D area)
  {
    double t = insets.calculateTopInset(area.getHeight());
    double b = insets.calculateBottomInset(area.getHeight());
    double l = insets.calculateLeftInset(area.getWidth());
    double r = insets.calculateRightInset(area.getWidth());
    double x = area.getX();
    double y = area.getY();
    double w = area.getWidth();
    double h = area.getHeight();
    g2.setPaint(paint);
    Rectangle2D rect = new Rectangle2D.Double();
    if (t > 0.0D) {
      rect.setRect(x, y, w, t);
      g2.fill(rect);
    }
    if (b > 0.0D) {
      rect.setRect(x, y + h - b, w, b);
      g2.fill(rect);
    }
    if (l > 0.0D) {
      rect.setRect(x, y, l, h);
      g2.fill(rect);
    }
    if (r > 0.0D) {
      rect.setRect(x + w - r, y, r, h);
      g2.fill(rect);
    }
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BlockBorder)) {
      return false;
    }
    BlockBorder that = (BlockBorder)obj;
    if (!insets.equals(insets)) {
      return false;
    }
    if (!PaintUtilities.equal(paint, paint)) {
      return false;
    }
    return true;
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
