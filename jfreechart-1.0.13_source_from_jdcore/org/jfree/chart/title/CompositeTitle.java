package org.jfree.chart.title;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.event.TitleChangeEvent;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.Size2D;
import org.jfree.util.PaintUtilities;





























































public class CompositeTitle
  extends Title
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -6770854036232562290L;
  private transient Paint backgroundPaint;
  private BlockContainer container;
  
  public CompositeTitle()
  {
    this(new BlockContainer(new BorderArrangement()));
  }
  




  public CompositeTitle(BlockContainer container)
  {
    if (container == null) {
      throw new IllegalArgumentException("Null 'container' argument.");
    }
    this.container = container;
    backgroundPaint = null;
  }
  






  public Paint getBackgroundPaint()
  {
    return backgroundPaint;
  }
  








  public void setBackgroundPaint(Paint paint)
  {
    backgroundPaint = paint;
    notifyListeners(new TitleChangeEvent(this));
  }
  




  public BlockContainer getContainer()
  {
    return container;
  }
  




  public void setTitleContainer(BlockContainer container)
  {
    if (container == null) {
      throw new IllegalArgumentException("Null 'container' argument.");
    }
    this.container = container;
  }
  








  public Size2D arrange(Graphics2D g2, RectangleConstraint constraint)
  {
    RectangleConstraint contentConstraint = toContentConstraint(constraint);
    Size2D contentSize = container.arrange(g2, contentConstraint);
    return new Size2D(calculateTotalWidth(contentSize.getWidth()), calculateTotalHeight(contentSize.getHeight()));
  }
  







  public void draw(Graphics2D g2, Rectangle2D area)
  {
    draw(g2, area, null);
  }
  








  public Object draw(Graphics2D g2, Rectangle2D area, Object params)
  {
    area = trimMargin(area);
    drawBorder(g2, area);
    area = trimBorder(area);
    if (backgroundPaint != null) {
      g2.setPaint(backgroundPaint);
      g2.fill(area);
    }
    area = trimPadding(area);
    return container.draw(g2, area, params);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof CompositeTitle)) {
      return false;
    }
    CompositeTitle that = (CompositeTitle)obj;
    if (!container.equals(container)) {
      return false;
    }
    if (!PaintUtilities.equal(backgroundPaint, backgroundPaint)) {
      return false;
    }
    return super.equals(obj);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(backgroundPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    backgroundPaint = SerialUtilities.readPaint(stream);
  }
}
