package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.PublicCloneable;




































































public class CategoryTextAnnotation
  extends TextAnnotation
  implements CategoryAnnotation, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 3333360090781320147L;
  private Comparable category;
  private CategoryAnchor categoryAnchor;
  private double value;
  
  public CategoryTextAnnotation(String text, Comparable category, double value)
  {
    super(text);
    if (category == null) {
      throw new IllegalArgumentException("Null 'category' argument.");
    }
    this.category = category;
    this.value = value;
    categoryAnchor = CategoryAnchor.MIDDLE;
  }
  






  public Comparable getCategory()
  {
    return category;
  }
  






  public void setCategory(Comparable category)
  {
    if (category == null) {
      throw new IllegalArgumentException("Null 'category' argument.");
    }
    this.category = category;
  }
  






  public CategoryAnchor getCategoryAnchor()
  {
    return categoryAnchor;
  }
  






  public void setCategoryAnchor(CategoryAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    categoryAnchor = anchor;
  }
  






  public double getValue()
  {
    return value;
  }
  






  public void setValue(double value)
  {
    this.value = value;
  }
  










  public void draw(Graphics2D g2, CategoryPlot plot, Rectangle2D dataArea, CategoryAxis domainAxis, ValueAxis rangeAxis)
  {
    CategoryDataset dataset = plot.getDataset();
    int catIndex = dataset.getColumnIndex(category);
    int catCount = dataset.getColumnCount();
    
    float anchorX = 0.0F;
    float anchorY = 0.0F;
    PlotOrientation orientation = plot.getOrientation();
    RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot.getDomainAxisLocation(), orientation);
    
    RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot.getRangeAxisLocation(), orientation);
    

    if (orientation == PlotOrientation.HORIZONTAL) {
      anchorY = (float)domainAxis.getCategoryJava2DCoordinate(categoryAnchor, catIndex, catCount, dataArea, domainEdge);
      

      anchorX = (float)rangeAxis.valueToJava2D(value, dataArea, rangeEdge);

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      anchorX = (float)domainAxis.getCategoryJava2DCoordinate(categoryAnchor, catIndex, catCount, dataArea, domainEdge);
      

      anchorY = (float)rangeAxis.valueToJava2D(value, dataArea, rangeEdge);
    }
    
    g2.setFont(getFont());
    g2.setPaint(getPaint());
    TextUtilities.drawRotatedString(getText(), g2, anchorX, anchorY, getTextAnchor(), getRotationAngle(), getRotationAnchor());
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof CategoryTextAnnotation)) {
      return false;
    }
    CategoryTextAnnotation that = (CategoryTextAnnotation)obj;
    if (!super.equals(obj)) {
      return false;
    }
    if (!category.equals(that.getCategory())) {
      return false;
    }
    if (!categoryAnchor.equals(that.getCategoryAnchor())) {
      return false;
    }
    if (value != that.getValue()) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = super.hashCode();
    result = 37 * result + category.hashCode();
    result = 37 * result + categoryAnchor.hashCode();
    long temp = Double.doubleToLongBits(value);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    return result;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
