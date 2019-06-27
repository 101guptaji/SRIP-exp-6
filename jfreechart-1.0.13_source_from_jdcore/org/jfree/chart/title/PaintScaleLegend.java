package org.jfree.chart.title;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockFrame;
import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.event.AxisChangeListener;
import org.jfree.chart.event.TitleChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.data.Range;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.Size2D;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;



















































































public class PaintScaleLegend
  extends Title
  implements AxisChangeListener, PublicCloneable
{
  static final long serialVersionUID = -1365146490993227503L;
  private PaintScale scale;
  private ValueAxis axis;
  private AxisLocation axisLocation;
  private double axisOffset;
  private double stripWidth;
  private boolean stripOutlineVisible;
  private transient Paint stripOutlinePaint;
  private transient Stroke stripOutlineStroke;
  private transient Paint backgroundPaint;
  private int subdivisions;
  
  public PaintScaleLegend(PaintScale scale, ValueAxis axis)
  {
    if (axis == null) {
      throw new IllegalArgumentException("Null 'axis' argument.");
    }
    this.scale = scale;
    this.axis = axis;
    this.axis.addChangeListener(this);
    axisLocation = AxisLocation.BOTTOM_OR_LEFT;
    axisOffset = 0.0D;
    this.axis.setRange(scale.getLowerBound(), scale.getUpperBound());
    stripWidth = 15.0D;
    stripOutlineVisible = true;
    stripOutlinePaint = Color.gray;
    stripOutlineStroke = new BasicStroke(0.5F);
    backgroundPaint = Color.white;
    subdivisions = 100;
  }
  






  public PaintScale getScale()
  {
    return scale;
  }
  







  public void setScale(PaintScale scale)
  {
    if (scale == null) {
      throw new IllegalArgumentException("Null 'scale' argument.");
    }
    this.scale = scale;
    notifyListeners(new TitleChangeEvent(this));
  }
  






  public ValueAxis getAxis()
  {
    return axis;
  }
  







  public void setAxis(ValueAxis axis)
  {
    if (axis == null) {
      throw new IllegalArgumentException("Null 'axis' argument.");
    }
    this.axis.removeChangeListener(this);
    this.axis = axis;
    this.axis.addChangeListener(this);
    notifyListeners(new TitleChangeEvent(this));
  }
  






  public AxisLocation getAxisLocation()
  {
    return axisLocation;
  }
  







  public void setAxisLocation(AxisLocation location)
  {
    if (location == null) {
      throw new IllegalArgumentException("Null 'location' argument.");
    }
    axisLocation = location;
    notifyListeners(new TitleChangeEvent(this));
  }
  






  public double getAxisOffset()
  {
    return axisOffset;
  }
  





  public void setAxisOffset(double offset)
  {
    axisOffset = offset;
    notifyListeners(new TitleChangeEvent(this));
  }
  






  public double getStripWidth()
  {
    return stripWidth;
  }
  







  public void setStripWidth(double width)
  {
    stripWidth = width;
    notifyListeners(new TitleChangeEvent(this));
  }
  







  public boolean isStripOutlineVisible()
  {
    return stripOutlineVisible;
  }
  








  public void setStripOutlineVisible(boolean visible)
  {
    stripOutlineVisible = visible;
    notifyListeners(new TitleChangeEvent(this));
  }
  






  public Paint getStripOutlinePaint()
  {
    return stripOutlinePaint;
  }
  







  public void setStripOutlinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    stripOutlinePaint = paint;
    notifyListeners(new TitleChangeEvent(this));
  }
  






  public Stroke getStripOutlineStroke()
  {
    return stripOutlineStroke;
  }
  







  public void setStripOutlineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    stripOutlineStroke = stroke;
    notifyListeners(new TitleChangeEvent(this));
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
  






  public int getSubdivisionCount()
  {
    return subdivisions;
  }
  







  public void setSubdivisionCount(int count)
  {
    if (count <= 0) {
      throw new IllegalArgumentException("Requires 'count' > 0.");
    }
    subdivisions = count;
    notifyListeners(new TitleChangeEvent(this));
  }
  







  public void axisChanged(AxisChangeEvent event)
  {
    if (axis == event.getAxis()) {
      notifyListeners(new TitleChangeEvent(this));
    }
  }
  








  public Size2D arrange(Graphics2D g2, RectangleConstraint constraint)
  {
    RectangleConstraint cc = toContentConstraint(constraint);
    LengthConstraintType w = cc.getWidthConstraintType();
    LengthConstraintType h = cc.getHeightConstraintType();
    Size2D contentSize = null;
    if (w == LengthConstraintType.NONE) {
      if (h == LengthConstraintType.NONE) {
        contentSize = new Size2D(getWidth(), getHeight());
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
        contentSize = arrangeRR(g2, cc.getWidthRange(), cc.getHeightRange());

      }
      else if (h == LengthConstraintType.FIXED) {
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
        throw new RuntimeException("Not yet implemented.");
      }
    }
    return new Size2D(calculateTotalWidth(contentSize.getWidth()), calculateTotalHeight(contentSize.getHeight()));
  }
  













  protected Size2D arrangeRR(Graphics2D g2, Range widthRange, Range heightRange)
  {
    RectangleEdge position = getPosition();
    if ((position == RectangleEdge.TOP) || (position == RectangleEdge.BOTTOM))
    {

      float maxWidth = (float)widthRange.getUpperBound();
      

      AxisSpace space = axis.reserveSpace(g2, null, new Rectangle2D.Double(0.0D, 0.0D, maxWidth, 100.0D), RectangleEdge.BOTTOM, null);
      


      return new Size2D(maxWidth, stripWidth + axisOffset + space.getTop() + space.getBottom());
    }
    
    if ((position == RectangleEdge.LEFT) || (position == RectangleEdge.RIGHT))
    {
      float maxHeight = (float)heightRange.getUpperBound();
      AxisSpace space = axis.reserveSpace(g2, null, new Rectangle2D.Double(0.0D, 0.0D, 100.0D, maxHeight), RectangleEdge.RIGHT, null);
      

      return new Size2D(stripWidth + axisOffset + space.getLeft() + space.getRight(), maxHeight);
    }
    

    throw new RuntimeException("Unrecognised position.");
  }
  






  public void draw(Graphics2D g2, Rectangle2D area)
  {
    draw(g2, area, null);
  }
  









  public Object draw(Graphics2D g2, Rectangle2D area, Object params)
  {
    Rectangle2D target = (Rectangle2D)area.clone();
    target = trimMargin(target);
    if (backgroundPaint != null) {
      g2.setPaint(backgroundPaint);
      g2.fill(target);
    }
    getFrame().draw(g2, target);
    getFrame().getInsets().trim(target);
    target = trimPadding(target);
    double base = axis.getLowerBound();
    double increment = axis.getRange().getLength() / subdivisions;
    Rectangle2D r = new Rectangle2D.Double();
    
    if (RectangleEdge.isTopOrBottom(getPosition())) {
      RectangleEdge axisEdge = Plot.resolveRangeAxisLocation(axisLocation, PlotOrientation.HORIZONTAL);
      
      if (axisEdge == RectangleEdge.TOP) {
        for (int i = 0; i < subdivisions; i++) {
          double v = base + i * increment;
          Paint p = scale.getPaint(v);
          double vv0 = axis.valueToJava2D(v, target, RectangleEdge.TOP);
          
          double vv1 = axis.valueToJava2D(v + increment, target, RectangleEdge.TOP);
          
          double ww = Math.abs(vv1 - vv0) + 1.0D;
          r.setRect(Math.min(vv0, vv1), target.getMaxY() - stripWidth, ww, stripWidth);
          
          g2.setPaint(p);
          g2.fill(r);
        }
        if (isStripOutlineVisible()) {
          g2.setPaint(stripOutlinePaint);
          g2.setStroke(stripOutlineStroke);
          g2.draw(new Rectangle2D.Double(target.getMinX(), target.getMaxY() - stripWidth, target.getWidth(), stripWidth));
        }
        

        axis.draw(g2, target.getMaxY() - stripWidth - axisOffset, target, target, RectangleEdge.TOP, null);


      }
      else if (axisEdge == RectangleEdge.BOTTOM) {
        for (int i = 0; i < subdivisions; i++) {
          double v = base + i * increment;
          Paint p = scale.getPaint(v);
          double vv0 = axis.valueToJava2D(v, target, RectangleEdge.BOTTOM);
          
          double vv1 = axis.valueToJava2D(v + increment, target, RectangleEdge.BOTTOM);
          
          double ww = Math.abs(vv1 - vv0) + 1.0D;
          r.setRect(Math.min(vv0, vv1), target.getMinY(), ww, stripWidth);
          
          g2.setPaint(p);
          g2.fill(r);
        }
        if (isStripOutlineVisible()) {
          g2.setPaint(stripOutlinePaint);
          g2.setStroke(stripOutlineStroke);
          g2.draw(new Rectangle2D.Double(target.getMinX(), target.getMinY(), target.getWidth(), stripWidth));
        }
        

        axis.draw(g2, target.getMinY() + stripWidth + axisOffset, target, target, RectangleEdge.BOTTOM, null);
      }
      
    }
    else
    {
      RectangleEdge axisEdge = Plot.resolveRangeAxisLocation(axisLocation, PlotOrientation.VERTICAL);
      
      if (axisEdge == RectangleEdge.LEFT) {
        for (int i = 0; i < subdivisions; i++) {
          double v = base + i * increment;
          Paint p = scale.getPaint(v);
          double vv0 = axis.valueToJava2D(v, target, RectangleEdge.LEFT);
          
          double vv1 = axis.valueToJava2D(v + increment, target, RectangleEdge.LEFT);
          
          double hh = Math.abs(vv1 - vv0) + 1.0D;
          r.setRect(target.getMaxX() - stripWidth, Math.min(vv0, vv1), stripWidth, hh);
          
          g2.setPaint(p);
          g2.fill(r);
        }
        if (isStripOutlineVisible()) {
          g2.setPaint(stripOutlinePaint);
          g2.setStroke(stripOutlineStroke);
          g2.draw(new Rectangle2D.Double(target.getMaxX() - stripWidth, target.getMinY(), stripWidth, target.getHeight()));
        }
        

        axis.draw(g2, target.getMaxX() - stripWidth - axisOffset, target, target, RectangleEdge.LEFT, null);


      }
      else if (axisEdge == RectangleEdge.RIGHT) {
        for (int i = 0; i < subdivisions; i++) {
          double v = base + i * increment;
          Paint p = scale.getPaint(v);
          double vv0 = axis.valueToJava2D(v, target, RectangleEdge.LEFT);
          
          double vv1 = axis.valueToJava2D(v + increment, target, RectangleEdge.LEFT);
          
          double hh = Math.abs(vv1 - vv0) + 1.0D;
          r.setRect(target.getMinX(), Math.min(vv0, vv1), stripWidth, hh);
          
          g2.setPaint(p);
          g2.fill(r);
        }
        if (isStripOutlineVisible()) {
          g2.setPaint(stripOutlinePaint);
          g2.setStroke(stripOutlineStroke);
          g2.draw(new Rectangle2D.Double(target.getMinX(), target.getMinY(), stripWidth, target.getHeight()));
        }
        

        axis.draw(g2, target.getMinX() + stripWidth + axisOffset, target, target, RectangleEdge.RIGHT, null);
      }
    }
    

    return null;
  }
  






  public boolean equals(Object obj)
  {
    if (!(obj instanceof PaintScaleLegend)) {
      return false;
    }
    PaintScaleLegend that = (PaintScaleLegend)obj;
    if (!scale.equals(scale)) {
      return false;
    }
    if (!axis.equals(axis)) {
      return false;
    }
    if (!axisLocation.equals(axisLocation)) {
      return false;
    }
    if (axisOffset != axisOffset) {
      return false;
    }
    if (stripWidth != stripWidth) {
      return false;
    }
    if (stripOutlineVisible != stripOutlineVisible) {
      return false;
    }
    if (!PaintUtilities.equal(stripOutlinePaint, stripOutlinePaint))
    {
      return false;
    }
    if (!stripOutlineStroke.equals(stripOutlineStroke)) {
      return false;
    }
    if (!PaintUtilities.equal(backgroundPaint, backgroundPaint)) {
      return false;
    }
    if (subdivisions != subdivisions) {
      return false;
    }
    return super.equals(obj);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(backgroundPaint, stream);
    SerialUtilities.writePaint(stripOutlinePaint, stream);
    SerialUtilities.writeStroke(stripOutlineStroke, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    backgroundPaint = SerialUtilities.readPaint(stream);
    stripOutlinePaint = SerialUtilities.readPaint(stream);
    stripOutlineStroke = SerialUtilities.readStroke(stream);
  }
}
