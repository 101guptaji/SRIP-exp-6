package org.jfree.chart.block;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextBlock;
import org.jfree.text.TextBlockAnchor;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.Size2D;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;






































































public class LabelBlock
  extends AbstractBlock
  implements Block, PublicCloneable
{
  static final long serialVersionUID = 249626098864178017L;
  private String text;
  private TextBlock label;
  private Font font;
  private String toolTipText;
  private String urlText;
  public static final Paint DEFAULT_PAINT = Color.black;
  




  private transient Paint paint;
  




  private TextBlockAnchor contentAlignmentPoint;
  



  private RectangleAnchor textAnchor;
  




  public LabelBlock(String label)
  {
    this(label, new Font("SansSerif", 0, 10), DEFAULT_PAINT);
  }
  





  public LabelBlock(String text, Font font)
  {
    this(text, font, DEFAULT_PAINT);
  }
  






  public LabelBlock(String text, Font font, Paint paint)
  {
    this.text = text;
    this.paint = paint;
    label = TextUtilities.createTextBlock(text, font, this.paint);
    this.font = font;
    toolTipText = null;
    urlText = null;
    contentAlignmentPoint = TextBlockAnchor.CENTER;
    textAnchor = RectangleAnchor.CENTER;
  }
  






  public Font getFont()
  {
    return font;
  }
  






  public void setFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    this.font = font;
    label = TextUtilities.createTextBlock(text, font, paint);
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
    label = TextUtilities.createTextBlock(text, font, this.paint);
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
  






  public void setURLText(String text)
  {
    urlText = text;
  }
  






  public TextBlockAnchor getContentAlignmentPoint()
  {
    return contentAlignmentPoint;
  }
  







  public void setContentAlignmentPoint(TextBlockAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    contentAlignmentPoint = anchor;
  }
  






  public RectangleAnchor getTextAnchor()
  {
    return textAnchor;
  }
  






  public void setTextAnchor(RectangleAnchor anchor)
  {
    textAnchor = anchor;
  }
  








  public Size2D arrange(Graphics2D g2, RectangleConstraint constraint)
  {
    g2.setFont(font);
    Size2D s = label.calculateDimensions(g2);
    return new Size2D(calculateTotalWidth(s.getWidth()), calculateTotalHeight(s.getHeight()));
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
    area = trimPadding(area);
    

    EntityBlockParams ebp = null;
    StandardEntityCollection sec = null;
    Shape entityArea = null;
    if ((params instanceof EntityBlockParams)) {
      ebp = (EntityBlockParams)params;
      if (ebp.getGenerateEntities()) {
        sec = new StandardEntityCollection();
        entityArea = (Shape)area.clone();
      }
    }
    g2.setPaint(paint);
    g2.setFont(font);
    Point2D pt = RectangleAnchor.coordinates(area, textAnchor);
    label.draw(g2, (float)pt.getX(), (float)pt.getY(), contentAlignmentPoint);
    
    BlockResult result = null;
    if ((ebp != null) && (sec != null) && (
      (toolTipText != null) || (urlText != null))) {
      ChartEntity entity = new ChartEntity(entityArea, toolTipText, urlText);
      
      sec.add(entity);
      result = new BlockResult();
      result.setEntityCollection(sec);
    }
    
    return result;
  }
  







  public boolean equals(Object obj)
  {
    if (!(obj instanceof LabelBlock)) {
      return false;
    }
    LabelBlock that = (LabelBlock)obj;
    if (!text.equals(text)) {
      return false;
    }
    if (!font.equals(font)) {
      return false;
    }
    if (!PaintUtilities.equal(paint, paint)) {
      return false;
    }
    if (!ObjectUtilities.equal(toolTipText, toolTipText)) {
      return false;
    }
    if (!ObjectUtilities.equal(urlText, urlText)) {
      return false;
    }
    if (!contentAlignmentPoint.equals(contentAlignmentPoint)) {
      return false;
    }
    if (!textAnchor.equals(textAnchor)) {
      return false;
    }
    return super.equals(obj);
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
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    paint = SerialUtilities.readPaint(stream);
  }
}
