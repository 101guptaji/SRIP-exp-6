package org.jfree.chart.renderer.category;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.Icon;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PaintUtilities;









































































public class MinMaxCategoryRenderer
  extends AbstractCategoryItemRenderer
{
  private static final long serialVersionUID = 2935615937671064911L;
  private boolean plotLines = false;
  



  private transient Paint groupPaint = Color.black;
  



  private transient Stroke groupStroke = new BasicStroke(1.0F);
  

  private transient Icon minIcon = getIcon(new Arc2D.Double(-4.0D, -4.0D, 8.0D, 8.0D, 0.0D, 360.0D, 0), null, Color.black);
  


  private transient Icon maxIcon = getIcon(new Arc2D.Double(-4.0D, -4.0D, 8.0D, 8.0D, 0.0D, 360.0D, 0), null, Color.black);
  


  private transient Icon objectIcon = getIcon(new Line2D.Double(-4.0D, 0.0D, 4.0D, 0.0D), false, true);
  


  private int lastCategory = -1;
  



  private double min;
  



  private double max;
  




  public MinMaxCategoryRenderer() {}
  




  public boolean isDrawLines()
  {
    return plotLines;
  }
  








  public void setDrawLines(boolean draw)
  {
    if (plotLines != draw) {
      plotLines = draw;
      fireChangeEvent();
    }
  }
  








  public Paint getGroupPaint()
  {
    return groupPaint;
  }
  








  public void setGroupPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    groupPaint = paint;
    fireChangeEvent();
  }
  







  public Stroke getGroupStroke()
  {
    return groupStroke;
  }
  






  public void setGroupStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    groupStroke = stroke;
    fireChangeEvent();
  }
  






  public Icon getObjectIcon()
  {
    return objectIcon;
  }
  







  public void setObjectIcon(Icon icon)
  {
    if (icon == null) {
      throw new IllegalArgumentException("Null 'icon' argument.");
    }
    objectIcon = icon;
    fireChangeEvent();
  }
  







  public Icon getMaxIcon()
  {
    return maxIcon;
  }
  








  public void setMaxIcon(Icon icon)
  {
    if (icon == null) {
      throw new IllegalArgumentException("Null 'icon' argument.");
    }
    maxIcon = icon;
    fireChangeEvent();
  }
  







  public Icon getMinIcon()
  {
    return minIcon;
  }
  








  public void setMinIcon(Icon icon)
  {
    if (icon == null) {
      throw new IllegalArgumentException("Null 'icon' argument.");
    }
    minIcon = icon;
    fireChangeEvent();
  }
  


















  public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass)
  {
    Number value = dataset.getValue(row, column);
    if (value != null)
    {
      double x1 = domainAxis.getCategoryMiddle(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
      
      double y1 = rangeAxis.valueToJava2D(value.doubleValue(), dataArea, plot.getRangeAxisEdge());
      
      g2.setPaint(getItemPaint(row, column));
      g2.setStroke(getItemStroke(row, column));
      Shape shape = null;
      shape = new Rectangle2D.Double(x1 - 4.0D, y1 - 4.0D, 8.0D, 8.0D);
      
      PlotOrientation orient = plot.getOrientation();
      if (orient == PlotOrientation.VERTICAL) {
        objectIcon.paintIcon(null, g2, (int)x1, (int)y1);
      }
      else {
        objectIcon.paintIcon(null, g2, (int)y1, (int)x1);
      }
      
      if (lastCategory == column) {
        if (min > value.doubleValue()) {
          min = value.doubleValue();
        }
        if (max < value.doubleValue()) {
          max = value.doubleValue();
        }
        

        if (dataset.getRowCount() - 1 == row) {
          g2.setPaint(groupPaint);
          g2.setStroke(groupStroke);
          double minY = rangeAxis.valueToJava2D(min, dataArea, plot.getRangeAxisEdge());
          
          double maxY = rangeAxis.valueToJava2D(max, dataArea, plot.getRangeAxisEdge());
          

          if (orient == PlotOrientation.VERTICAL) {
            g2.draw(new Line2D.Double(x1, minY, x1, maxY));
            minIcon.paintIcon(null, g2, (int)x1, (int)minY);
            maxIcon.paintIcon(null, g2, (int)x1, (int)maxY);
          }
          else {
            g2.draw(new Line2D.Double(minY, x1, maxY, x1));
            minIcon.paintIcon(null, g2, (int)minY, (int)x1);
            maxIcon.paintIcon(null, g2, (int)maxY, (int)x1);
          }
        }
      }
      else {
        lastCategory = column;
        min = value.doubleValue();
        max = value.doubleValue();
      }
      

      if ((plotLines) && 
        (column != 0)) {
        Number previousValue = dataset.getValue(row, column - 1);
        if (previousValue != null)
        {
          double previous = previousValue.doubleValue();
          double x0 = domainAxis.getCategoryMiddle(column - 1, getColumnCount(), dataArea, plot.getDomainAxisEdge());
          

          double y0 = rangeAxis.valueToJava2D(previous, dataArea, plot.getRangeAxisEdge());
          
          g2.setPaint(getItemPaint(row, column));
          g2.setStroke(getItemStroke(row, column));
          Line2D line;
          Line2D line; if (orient == PlotOrientation.VERTICAL) {
            line = new Line2D.Double(x0, y0, x1, y1);
          }
          else {
            line = new Line2D.Double(y0, x0, y1, x1);
          }
          g2.draw(line);
        }
      }
      


      EntityCollection entities = state.getEntityCollection();
      if ((entities != null) && (shape != null)) {
        addItemEntity(entities, dataset, row, column, shape);
      }
    }
  }
  










  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MinMaxCategoryRenderer)) {
      return false;
    }
    MinMaxCategoryRenderer that = (MinMaxCategoryRenderer)obj;
    if (plotLines != plotLines) {
      return false;
    }
    if (!PaintUtilities.equal(groupPaint, groupPaint)) {
      return false;
    }
    if (!groupStroke.equals(groupStroke)) {
      return false;
    }
    return super.equals(obj);
  }
  










  private Icon getIcon(Shape shape, Paint fillPaint, Paint outlinePaint)
  {
    int width = getBoundswidth;
    int height = getBoundsheight;
    GeneralPath path = new GeneralPath(shape);
    new Icon() { private final GeneralPath val$path;
      
      public void paintIcon(Component c, Graphics g, int x, int y) { Graphics2D g2 = (Graphics2D)g;
        val$path.transform(AffineTransform.getTranslateInstance(x, y));
        if (val$fillPaint != null) {
          g2.setPaint(val$fillPaint);
          g2.fill(val$path);
        }
        if (val$outlinePaint != null) {
          g2.setPaint(val$outlinePaint);
          g2.draw(val$path);
        }
        val$path.transform(AffineTransform.getTranslateInstance(-x, -y));
      }
      
      public int getIconWidth() {
        return val$width;
      }
      
      public int getIconHeight() {
        return val$height;
      }
    };
  }
  


  private final Paint val$fillPaint;
  
  private final Paint val$outlinePaint;
  
  private final int val$width;
  
  private final int val$height;
  
  private Icon getIcon(Shape shape, boolean fill, boolean outline)
  {
    int width = getBoundswidth;
    int height = getBoundsheight;
    GeneralPath path = new GeneralPath(shape);
    new Icon() { private final GeneralPath val$path;
      
      public void paintIcon(Component c, Graphics g, int x, int y) { Graphics2D g2 = (Graphics2D)g;
        val$path.transform(AffineTransform.getTranslateInstance(x, y));
        if (val$fill) {
          g2.fill(val$path);
        }
        if (val$outline) {
          g2.draw(val$path);
        }
        val$path.transform(AffineTransform.getTranslateInstance(-x, -y));
      }
      
      public int getIconWidth() {
        return val$width;
      }
      
      public int getIconHeight() {
        return val$height;
      }
    };
  }
  


  private final boolean val$fill;
  

  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeStroke(groupStroke, stream);
    SerialUtilities.writePaint(groupPaint, stream);
  }
  

  private final boolean val$outline;
  
  private final int val$width;
  
  private final int val$height;
  
  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    groupStroke = SerialUtilities.readStroke(stream);
    groupPaint = SerialUtilities.readPaint(stream);
    
    minIcon = getIcon(new Arc2D.Double(-4.0D, -4.0D, 8.0D, 8.0D, 0.0D, 360.0D, 0), null, Color.black);
    
    maxIcon = getIcon(new Arc2D.Double(-4.0D, -4.0D, 8.0D, 8.0D, 0.0D, 360.0D, 0), null, Color.black);
    
    objectIcon = getIcon(new Line2D.Double(-4.0D, 0.0D, 4.0D, 0.0D), false, true);
  }
}
