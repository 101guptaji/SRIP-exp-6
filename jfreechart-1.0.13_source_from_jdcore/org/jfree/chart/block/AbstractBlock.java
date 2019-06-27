package org.jfree.chart.block;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.data.Range;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.Size2D;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;













































































public class AbstractBlock
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 7689852412141274563L;
  private String id;
  private RectangleInsets margin;
  private BlockFrame frame;
  private RectangleInsets padding;
  private double width;
  private double height;
  private transient Rectangle2D bounds;
  
  protected AbstractBlock()
  {
    id = null;
    width = 0.0D;
    height = 0.0D;
    bounds = new Rectangle2D.Float();
    margin = RectangleInsets.ZERO_INSETS;
    frame = BlockBorder.NONE;
    padding = RectangleInsets.ZERO_INSETS;
  }
  






  public String getID()
  {
    return id;
  }
  






  public void setID(String id)
  {
    this.id = id;
  }
  








  public double getWidth()
  {
    return width;
  }
  






  public void setWidth(double width)
  {
    this.width = width;
  }
  








  public double getHeight()
  {
    return height;
  }
  






  public void setHeight(double height)
  {
    this.height = height;
  }
  






  public RectangleInsets getMargin()
  {
    return margin;
  }
  







  public void setMargin(RectangleInsets margin)
  {
    if (margin == null) {
      throw new IllegalArgumentException("Null 'margin' argument.");
    }
    this.margin = margin;
  }
  










  public void setMargin(double top, double left, double bottom, double right)
  {
    setMargin(new RectangleInsets(top, left, bottom, right));
  }
  



  /**
   * @deprecated
   */
  public BlockBorder getBorder()
  {
    if ((frame instanceof BlockBorder)) {
      return (BlockBorder)frame;
    }
    
    return null;
  }
  







  /**
   * @deprecated
   */
  public void setBorder(BlockBorder border)
  {
    setFrame(border);
  }
  








  public void setBorder(double top, double left, double bottom, double right)
  {
    setFrame(new BlockBorder(top, left, bottom, right));
  }
  







  public BlockFrame getFrame()
  {
    return frame;
  }
  







  public void setFrame(BlockFrame frame)
  {
    if (frame == null) {
      throw new IllegalArgumentException("Null 'frame' argument.");
    }
    this.frame = frame;
  }
  






  public RectangleInsets getPadding()
  {
    return padding;
  }
  







  public void setPadding(RectangleInsets padding)
  {
    if (padding == null) {
      throw new IllegalArgumentException("Null 'padding' argument.");
    }
    this.padding = padding;
  }
  








  public void setPadding(double top, double left, double bottom, double right)
  {
    setPadding(new RectangleInsets(top, left, bottom, right));
  }
  






  public double getContentXOffset()
  {
    return margin.getLeft() + frame.getInsets().getLeft() + padding.getLeft();
  }
  







  public double getContentYOffset()
  {
    return margin.getTop() + frame.getInsets().getTop() + padding.getTop();
  }
  








  public Size2D arrange(Graphics2D g2)
  {
    return arrange(g2, RectangleConstraint.NONE);
  }
  








  public Size2D arrange(Graphics2D g2, RectangleConstraint constraint)
  {
    Size2D base = new Size2D(getWidth(), getHeight());
    return constraint.calculateConstrainedSize(base);
  }
  






  public Rectangle2D getBounds()
  {
    return bounds;
  }
  






  public void setBounds(Rectangle2D bounds)
  {
    if (bounds == null) {
      throw new IllegalArgumentException("Null 'bounds' argument.");
    }
    this.bounds = bounds;
  }
  










  protected double trimToContentWidth(double fixedWidth)
  {
    double result = margin.trimWidth(fixedWidth);
    result = frame.getInsets().trimWidth(result);
    result = padding.trimWidth(result);
    return Math.max(result, 0.0D);
  }
  










  protected double trimToContentHeight(double fixedHeight)
  {
    double result = margin.trimHeight(fixedHeight);
    result = frame.getInsets().trimHeight(result);
    result = padding.trimHeight(result);
    return Math.max(result, 0.0D);
  }
  







  protected RectangleConstraint toContentConstraint(RectangleConstraint c)
  {
    if (c == null) {
      throw new IllegalArgumentException("Null 'c' argument.");
    }
    if (c.equals(RectangleConstraint.NONE)) {
      return c;
    }
    double w = c.getWidth();
    Range wr = c.getWidthRange();
    double h = c.getHeight();
    Range hr = c.getHeightRange();
    double ww = trimToContentWidth(w);
    double hh = trimToContentHeight(h);
    Range wwr = trimToContentWidth(wr);
    Range hhr = trimToContentHeight(hr);
    return new RectangleConstraint(ww, wwr, c.getWidthConstraintType(), hh, hhr, c.getHeightConstraintType());
  }
  


  private Range trimToContentWidth(Range r)
  {
    if (r == null) {
      return null;
    }
    double lowerBound = 0.0D;
    double upperBound = Double.POSITIVE_INFINITY;
    if (r.getLowerBound() > 0.0D) {
      lowerBound = trimToContentWidth(r.getLowerBound());
    }
    if (r.getUpperBound() < Double.POSITIVE_INFINITY) {
      upperBound = trimToContentWidth(r.getUpperBound());
    }
    return new Range(lowerBound, upperBound);
  }
  
  private Range trimToContentHeight(Range r) {
    if (r == null) {
      return null;
    }
    double lowerBound = 0.0D;
    double upperBound = Double.POSITIVE_INFINITY;
    if (r.getLowerBound() > 0.0D) {
      lowerBound = trimToContentHeight(r.getLowerBound());
    }
    if (r.getUpperBound() < Double.POSITIVE_INFINITY) {
      upperBound = trimToContentHeight(r.getUpperBound());
    }
    return new Range(lowerBound, upperBound);
  }
  






  protected double calculateTotalWidth(double contentWidth)
  {
    double result = contentWidth;
    result = padding.extendWidth(result);
    result = frame.getInsets().extendWidth(result);
    result = margin.extendWidth(result);
    return result;
  }
  






  protected double calculateTotalHeight(double contentHeight)
  {
    double result = contentHeight;
    result = padding.extendHeight(result);
    result = frame.getInsets().extendHeight(result);
    result = margin.extendHeight(result);
    return result;
  }
  








  protected Rectangle2D trimMargin(Rectangle2D area)
  {
    margin.trim(area);
    return area;
  }
  








  protected Rectangle2D trimBorder(Rectangle2D area)
  {
    frame.getInsets().trim(area);
    return area;
  }
  








  protected Rectangle2D trimPadding(Rectangle2D area)
  {
    padding.trim(area);
    return area;
  }
  





  protected void drawBorder(Graphics2D g2, Rectangle2D area)
  {
    frame.draw(g2, area);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AbstractBlock)) {
      return false;
    }
    AbstractBlock that = (AbstractBlock)obj;
    if (!ObjectUtilities.equal(id, id)) {
      return false;
    }
    if (!frame.equals(frame)) {
      return false;
    }
    if (!bounds.equals(bounds)) {
      return false;
    }
    if (!margin.equals(margin)) {
      return false;
    }
    if (!padding.equals(padding)) {
      return false;
    }
    if (height != height) {
      return false;
    }
    if (width != width) {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    AbstractBlock clone = (AbstractBlock)super.clone();
    bounds = ((Rectangle2D)ShapeUtilities.clone(bounds));
    if ((frame instanceof PublicCloneable)) {
      PublicCloneable pc = (PublicCloneable)frame;
      frame = ((BlockFrame)pc.clone());
    }
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeShape(bounds, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    bounds = ((Rectangle2D)SerialUtilities.readShape(stream));
  }
}
