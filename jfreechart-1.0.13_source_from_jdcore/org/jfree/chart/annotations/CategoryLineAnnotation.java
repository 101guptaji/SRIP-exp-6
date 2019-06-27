package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;



























































public class CategoryLineAnnotation
  implements CategoryAnnotation, Cloneable, PublicCloneable, Serializable
{
  static final long serialVersionUID = 3477740483341587984L;
  private Comparable category1;
  private double value1;
  private Comparable category2;
  private double value2;
  private transient Paint paint = Color.black;
  

  private transient Stroke stroke = new BasicStroke(1.0F);
  












  public CategoryLineAnnotation(Comparable category1, double value1, Comparable category2, double value2, Paint paint, Stroke stroke)
  {
    if (category1 == null) {
      throw new IllegalArgumentException("Null 'category1' argument.");
    }
    if (category2 == null) {
      throw new IllegalArgumentException("Null 'category2' argument.");
    }
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    this.category1 = category1;
    this.value1 = value1;
    this.category2 = category2;
    this.value2 = value2;
    this.paint = paint;
    this.stroke = stroke;
  }
  






  public Comparable getCategory1()
  {
    return category1;
  }
  






  public void setCategory1(Comparable category)
  {
    if (category == null) {
      throw new IllegalArgumentException("Null 'category' argument.");
    }
    category1 = category;
  }
  






  public double getValue1()
  {
    return value1;
  }
  






  public void setValue1(double value)
  {
    value1 = value;
  }
  






  public Comparable getCategory2()
  {
    return category2;
  }
  






  public void setCategory2(Comparable category)
  {
    if (category == null) {
      throw new IllegalArgumentException("Null 'category' argument.");
    }
    category2 = category;
  }
  






  public double getValue2()
  {
    return value2;
  }
  






  public void setValue2(double value)
  {
    value2 = value;
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
  






  public Stroke getStroke()
  {
    return stroke;
  }
  






  public void setStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    this.stroke = stroke;
  }
  










  public void draw(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea, CategoryAxis domainAxis, ValueAxis rangeAxis)
  {
    CategoryDataset dataset = plot.getDataset();
    int catIndex1 = dataset.getColumnIndex(category1);
    int catIndex2 = dataset.getColumnIndex(category2);
    int catCount = dataset.getColumnCount();
    
    double lineX1 = 0.0D;
    double lineY1 = 0.0D;
    double lineX2 = 0.0D;
    double lineY2 = 0.0D;
    PlotOrientation orientation = plot.getOrientation();
    RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot.getDomainAxisLocation(), orientation);
    
    RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot.getRangeAxisLocation(), orientation);
    

    if (orientation == PlotOrientation.HORIZONTAL) {
      lineY1 = domainAxis.getCategoryJava2DCoordinate(CategoryAnchor.MIDDLE, catIndex1, catCount, dataArea, domainEdge);
      

      lineX1 = rangeAxis.valueToJava2D(value1, dataArea, rangeEdge);
      lineY2 = domainAxis.getCategoryJava2DCoordinate(CategoryAnchor.MIDDLE, catIndex2, catCount, dataArea, domainEdge);
      

      lineX2 = rangeAxis.valueToJava2D(value2, dataArea, rangeEdge);
    }
    else if (orientation == PlotOrientation.VERTICAL) {
      lineX1 = domainAxis.getCategoryJava2DCoordinate(CategoryAnchor.MIDDLE, catIndex1, catCount, dataArea, domainEdge);
      

      lineY1 = rangeAxis.valueToJava2D(value1, dataArea, rangeEdge);
      lineX2 = domainAxis.getCategoryJava2DCoordinate(CategoryAnchor.MIDDLE, catIndex2, catCount, dataArea, domainEdge);
      

      lineY2 = rangeAxis.valueToJava2D(value2, dataArea, rangeEdge);
    }
    g2.setPaint(paint);
    g2.setStroke(stroke);
    g2.drawLine((int)lineX1, (int)lineY1, (int)lineX2, (int)lineY2);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof CategoryLineAnnotation)) {
      return false;
    }
    CategoryLineAnnotation that = (CategoryLineAnnotation)obj;
    if (!category1.equals(that.getCategory1())) {
      return false;
    }
    if (value1 != that.getValue1()) {
      return false;
    }
    if (!category2.equals(that.getCategory2())) {
      return false;
    }
    if (value2 != that.getValue2()) {
      return false;
    }
    if (!PaintUtilities.equal(paint, paint)) {
      return false;
    }
    if (!ObjectUtilities.equal(stroke, stroke)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 193;
    result = 37 * result + category1.hashCode();
    long temp = Double.doubleToLongBits(value1);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    result = 37 * result + category2.hashCode();
    temp = Double.doubleToLongBits(value2);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    result = 37 * result + HashUtilities.hashCodeForPaint(paint);
    result = 37 * result + stroke.hashCode();
    return result;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(paint, stream);
    SerialUtilities.writeStroke(stroke, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    paint = SerialUtilities.readPaint(stream);
    stroke = SerialUtilities.readStroke(stream);
  }
}
