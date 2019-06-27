package org.jfree.chart.title;

import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.jfree.chart.block.AbstractBlock;
import org.jfree.chart.block.Block;
import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.GradientPaintTransformer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.Size2D;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;










































































































public class LegendGraphic
  extends AbstractBlock
  implements Block, PublicCloneable
{
  static final long serialVersionUID = -1338791523854985009L;
  private boolean shapeVisible;
  private transient Shape shape;
  private RectangleAnchor shapeLocation;
  private RectangleAnchor shapeAnchor;
  private boolean shapeFilled;
  private transient Paint fillPaint;
  private GradientPaintTransformer fillPaintTransformer;
  private boolean shapeOutlineVisible;
  private transient Paint outlinePaint;
  private transient Stroke outlineStroke;
  private boolean lineVisible;
  private transient Shape line;
  private transient Stroke lineStroke;
  private transient Paint linePaint;
  
  public LegendGraphic(Shape shape, Paint fillPaint)
  {
    if (shape == null) {
      throw new IllegalArgumentException("Null 'shape' argument.");
    }
    if (fillPaint == null) {
      throw new IllegalArgumentException("Null 'fillPaint' argument.");
    }
    shapeVisible = true;
    this.shape = shape;
    shapeAnchor = RectangleAnchor.CENTER;
    shapeLocation = RectangleAnchor.CENTER;
    shapeFilled = true;
    this.fillPaint = fillPaint;
    fillPaintTransformer = new StandardGradientPaintTransformer();
    setPadding(2.0D, 2.0D, 2.0D, 2.0D);
  }
  







  public boolean isShapeVisible()
  {
    return shapeVisible;
  }
  







  public void setShapeVisible(boolean visible)
  {
    shapeVisible = visible;
  }
  






  public Shape getShape()
  {
    return shape;
  }
  






  public void setShape(Shape shape)
  {
    this.shape = shape;
  }
  







  public boolean isShapeFilled()
  {
    return shapeFilled;
  }
  







  public void setShapeFilled(boolean filled)
  {
    shapeFilled = filled;
  }
  






  public Paint getFillPaint()
  {
    return fillPaint;
  }
  






  public void setFillPaint(Paint paint)
  {
    fillPaint = paint;
  }
  









  public GradientPaintTransformer getFillPaintTransformer()
  {
    return fillPaintTransformer;
  }
  









  public void setFillPaintTransformer(GradientPaintTransformer transformer)
  {
    if (transformer == null) {
      throw new IllegalArgumentException("Null 'transformer' argument.");
    }
    fillPaintTransformer = transformer;
  }
  






  public boolean isShapeOutlineVisible()
  {
    return shapeOutlineVisible;
  }
  







  public void setShapeOutlineVisible(boolean visible)
  {
    shapeOutlineVisible = visible;
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
  






  public RectangleAnchor getShapeAnchor()
  {
    return shapeAnchor;
  }
  







  public void setShapeAnchor(RectangleAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    shapeAnchor = anchor;
  }
  






  public RectangleAnchor getShapeLocation()
  {
    return shapeLocation;
  }
  







  public void setShapeLocation(RectangleAnchor location)
  {
    if (location == null) {
      throw new IllegalArgumentException("Null 'location' argument.");
    }
    shapeLocation = location;
  }
  






  public boolean isLineVisible()
  {
    return lineVisible;
  }
  






  public void setLineVisible(boolean visible)
  {
    lineVisible = visible;
  }
  






  public Shape getLine()
  {
    return line;
  }
  







  public void setLine(Shape line)
  {
    this.line = line;
  }
  






  public Paint getLinePaint()
  {
    return linePaint;
  }
  






  public void setLinePaint(Paint paint)
  {
    linePaint = paint;
  }
  






  public Stroke getLineStroke()
  {
    return lineStroke;
  }
  






  public void setLineStroke(Stroke stroke)
  {
    lineStroke = stroke;
  }
  








  public Size2D arrange(Graphics2D g2, RectangleConstraint constraint)
  {
    RectangleConstraint contentConstraint = toContentConstraint(constraint);
    LengthConstraintType w = contentConstraint.getWidthConstraintType();
    LengthConstraintType h = contentConstraint.getHeightConstraintType();
    Size2D contentSize = null;
    if (w == LengthConstraintType.NONE) {
      if (h == LengthConstraintType.NONE) {
        contentSize = arrangeNN(g2);
      } else {
        if (h == LengthConstraintType.RANGE) {
          throw new RuntimeException("Not yet implemented.");
        }
        if (h == LengthConstraintType.FIXED) {
          throw new RuntimeException("Not yet implemented.");
        }
      }
    } else if (w == LengthConstraintType.RANGE) {
      if (h == LengthConstraintType.NONE) {
        throw new RuntimeException("Not yet implemented.");
      }
      if (h == LengthConstraintType.RANGE) {
        throw new RuntimeException("Not yet implemented.");
      }
      if (h == LengthConstraintType.FIXED) {
        throw new RuntimeException("Not yet implemented.");
      }
    }
    else if (w == LengthConstraintType.FIXED) {
      if (h == LengthConstraintType.NONE) {
        throw new RuntimeException("Not yet implemented.");
      }
      if (h == LengthConstraintType.RANGE) {
        throw new RuntimeException("Not yet implemented.");
      }
      if (h == LengthConstraintType.FIXED) {
        contentSize = new Size2D(contentConstraint.getWidth(), contentConstraint.getHeight());
      }
    }
    


    return new Size2D(calculateTotalWidth(contentSize.getWidth()), calculateTotalHeight(contentSize.getHeight()));
  }
  











  protected Size2D arrangeNN(Graphics2D g2)
  {
    Rectangle2D contentSize = new Rectangle2D.Double();
    if (line != null) {
      contentSize.setRect(line.getBounds2D());
    }
    if (shape != null) {
      contentSize = contentSize.createUnion(shape.getBounds2D());
    }
    return new Size2D(contentSize.getWidth(), contentSize.getHeight());
  }
  






  public void draw(Graphics2D g2, Rectangle2D area)
  {
    area = trimMargin(area);
    drawBorder(g2, area);
    area = trimBorder(area);
    area = trimPadding(area);
    
    if (lineVisible) {
      Point2D location = RectangleAnchor.coordinates(area, shapeLocation);
      
      Shape aLine = ShapeUtilities.createTranslatedShape(getLine(), shapeAnchor, location.getX(), location.getY());
      
      g2.setPaint(linePaint);
      g2.setStroke(lineStroke);
      g2.draw(aLine);
    }
    
    if (shapeVisible) {
      Point2D location = RectangleAnchor.coordinates(area, shapeLocation);
      

      Shape s = ShapeUtilities.createTranslatedShape(shape, shapeAnchor, location.getX(), location.getY());
      
      if (shapeFilled) {
        Paint p = fillPaint;
        if ((p instanceof GradientPaint)) {
          GradientPaint gp = (GradientPaint)fillPaint;
          p = fillPaintTransformer.transform(gp, s);
        }
        g2.setPaint(p);
        g2.fill(s);
      }
      if (shapeOutlineVisible) {
        g2.setPaint(outlinePaint);
        g2.setStroke(outlineStroke);
        g2.draw(s);
      }
    }
  }
  









  public Object draw(Graphics2D g2, Rectangle2D area, Object params)
  {
    draw(g2, area);
    return null;
  }
  







  public boolean equals(Object obj)
  {
    if (!(obj instanceof LegendGraphic)) {
      return false;
    }
    LegendGraphic that = (LegendGraphic)obj;
    if (shapeVisible != shapeVisible) {
      return false;
    }
    if (!ShapeUtilities.equal(shape, shape)) {
      return false;
    }
    if (shapeFilled != shapeFilled) {
      return false;
    }
    if (!PaintUtilities.equal(fillPaint, fillPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(fillPaintTransformer, fillPaintTransformer))
    {
      return false;
    }
    if (shapeOutlineVisible != shapeOutlineVisible) {
      return false;
    }
    if (!PaintUtilities.equal(outlinePaint, outlinePaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(outlineStroke, outlineStroke)) {
      return false;
    }
    if (shapeAnchor != shapeAnchor) {
      return false;
    }
    if (shapeLocation != shapeLocation) {
      return false;
    }
    if (lineVisible != lineVisible) {
      return false;
    }
    if (!ShapeUtilities.equal(line, line)) {
      return false;
    }
    if (!PaintUtilities.equal(linePaint, linePaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(lineStroke, lineStroke)) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = 193;
    result = 37 * result + ObjectUtilities.hashCode(fillPaint);
    
    return result;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    LegendGraphic clone = (LegendGraphic)super.clone();
    shape = ShapeUtilities.clone(shape);
    line = ShapeUtilities.clone(line);
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeShape(shape, stream);
    SerialUtilities.writePaint(fillPaint, stream);
    SerialUtilities.writePaint(outlinePaint, stream);
    SerialUtilities.writeStroke(outlineStroke, stream);
    SerialUtilities.writeShape(line, stream);
    SerialUtilities.writePaint(linePaint, stream);
    SerialUtilities.writeStroke(lineStroke, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    shape = SerialUtilities.readShape(stream);
    fillPaint = SerialUtilities.readPaint(stream);
    outlinePaint = SerialUtilities.readPaint(stream);
    outlineStroke = SerialUtilities.readStroke(stream);
    line = SerialUtilities.readShape(stream);
    linePaint = SerialUtilities.readPaint(stream);
    lineStroke = SerialUtilities.readStroke(stream);
  }
}
