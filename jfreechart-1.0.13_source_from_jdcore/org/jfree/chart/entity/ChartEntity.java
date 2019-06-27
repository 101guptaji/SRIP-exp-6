package org.jfree.chart.entity;

import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.chart.imagemap.ToolTipTagFragmentGenerator;
import org.jfree.chart.imagemap.URLTagFragmentGenerator;
import org.jfree.io.SerialUtilities;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
public class ChartEntity
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -4445994133561919083L;
  private transient Shape area;
  private String toolTipText;
  private String urlText;
  
  public ChartEntity(Shape area)
  {
    this(area, null);
  }
 public ChartEntity(Shape area, String toolTipText)
  {
    this(area, toolTipText, null);
  }
 public ChartEntity(Shape area, String toolTipText, String urlText)
  {
    if (area == null) {
      throw new IllegalArgumentException("Null 'area' argument.");
    }
    this.area = area;
    this.toolTipText = toolTipText;
    this.urlText = urlText;
  }
  public Shape getArea()
  {
    return area;
  }
 public void setArea(Shape area)
  {
    if (area == null) {
      throw new IllegalArgumentException("Null 'area' argument.");
    }
    this.area = area;
  }
 public String getToolTipText()
  {
    return toolTipText;
  }
  public void setToolTipText(String text)
  {
    toolTipText = text;
  }
 public String getURLText()
  {
    return urlText;
  }
public String getShapeType()
  {
    if ((area instanceof Rectangle2D)) {
      return "rect";
    }
    
    return "poly";
  }
 public String getShapeCoords()
  {
    if ((area instanceof Rectangle2D)) {
      return getRectCoords((Rectangle2D)area);
    }
    
    return getPolyCoords(area);
  }
 private String getRectCoords(Rectangle2D rectangle)
  {
    if (rectangle == null) {
      throw new IllegalArgumentException("Null 'rectangle' argument.");
    }
    int x1 = (int)rectangle.getX();
    int y1 = (int)rectangle.getY();
    int x2 = x1 + (int)rectangle.getWidth();
    int y2 = y1 + (int)rectangle.getHeight();
    
    if (x2 == x1) {
      x2++;
    }
    if (y2 == y1) {
      y2++;
    }
    
    return x1 + "," + y1 + "," + x2 + "," + y2;
  }
 private String getPolyCoords(Shape shape)
  {
    if (shape == null) {
      throw new IllegalArgumentException("Null 'shape' argument.");
    }
    StringBuffer result = new StringBuffer();
    boolean first = true;
    float[] coords = new float[6];
    PathIterator pi = shape.getPathIterator(null, 1.0D);
    while (!pi.isDone()) {
      pi.currentSegment(coords);
      if (first) {
        first = false;
        result.append((int)coords[0]);
        result.append(",").append((int)coords[1]);
      }
      else {
        result.append(",");
        result.append((int)coords[0]);
        result.append(",");
        result.append((int)coords[1]);
      }
      pi.next();
    }
    return result.toString();
  }
  public String getImageMapAreaTag(ToolTipTagFragmentGenerator toolTipTagFragmentGenerator, URLTagFragmentGenerator urlTagFragmentGenerator)
  {
    StringBuffer tag = new StringBuffer();
    boolean hasURL = urlText != null;
    
    boolean hasToolTip = toolTipText != null;
    
    if ((hasURL) || (hasToolTip)) {
      tag.append("<area shape=\"" + getShapeType() + "\"" + " coords=\"" + getShapeCoords() + "\"");
      
      if (hasToolTip) {
        tag.append(toolTipTagFragmentGenerator.generateToolTipFragment(toolTipText));
      }
      
      if (hasURL) {
        tag.append(urlTagFragmentGenerator.generateURLFragment(urlText));
      }
      else
      {
        tag.append(" nohref=\"nohref\"");
      }
      

      if (!hasToolTip) {
        tag.append(" alt=\"\"");
      }
      tag.append("/>");
    }
    return tag.toString();
  }
public String toString()
  {
    StringBuffer buf = new StringBuffer("ChartEntity: ");
    buf.append("tooltip = ");
    buf.append(toolTipText);
    return buf.toString();
  }
 public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ChartEntity)) {
      return false;
    }
    ChartEntity that = (ChartEntity)obj;
    if (!area.equals(area)) {
      return false;
    }
    if (!ObjectUtilities.equal(toolTipText, toolTipText)) {
      return false;
    }
    if (!ObjectUtilities.equal(urlText, urlText)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 37;
    result = HashUtilities.hashCode(result, toolTipText);
    result = HashUtilities.hashCode(result, urlText);
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
    SerialUtilities.writeShape(area, stream);
  }
 private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    area = SerialUtilities.readShape(stream);
  }
}
