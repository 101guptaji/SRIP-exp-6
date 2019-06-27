package org.jfree.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D.Float;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.AttributedString;
import java.text.CharacterIterator;
import org.jfree.data.general.Dataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.GradientPaintTransformer;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.util.AttributedStringUtilities;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;













































































































































public class LegendItem
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -797214582948827144L;
  private Dataset dataset;
  private Comparable seriesKey;
  private int datasetIndex;
  private int series;
  private String label;
  private Font labelFont;
  private transient Paint labelPaint;
  private transient AttributedString attributedLabel;
  private String description;
  private String toolTipText;
  private String urlText;
  private boolean shapeVisible;
  private transient Shape shape;
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
  private static final Shape UNUSED_SHAPE = new Line2D.Float();
  




  private static final Stroke UNUSED_STROKE = new BasicStroke(0.0F);
  







  public LegendItem(String label)
  {
    this(label, Color.black);
  }
  








  public LegendItem(String label, Paint paint)
  {
    this(label, null, null, null, new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D), paint);
  }
  















  public LegendItem(String label, String description, String toolTipText, String urlText, Shape shape, Paint fillPaint)
  {
    this(label, description, toolTipText, urlText, true, shape, true, fillPaint, false, Color.black, UNUSED_STROKE, false, UNUSED_SHAPE, UNUSED_STROKE, Color.black);
  }
  
























  public LegendItem(String label, String description, String toolTipText, String urlText, Shape shape, Paint fillPaint, Stroke outlineStroke, Paint outlinePaint)
  {
    this(label, description, toolTipText, urlText, true, shape, true, fillPaint, true, outlinePaint, outlineStroke, false, UNUSED_SHAPE, UNUSED_STROKE, Color.black);
  }
  



















  public LegendItem(String label, String description, String toolTipText, String urlText, Shape line, Stroke lineStroke, Paint linePaint)
  {
    this(label, description, toolTipText, urlText, false, UNUSED_SHAPE, false, Color.black, false, Color.black, UNUSED_STROKE, true, line, lineStroke, linePaint);
  }
  




































  public LegendItem(String label, String description, String toolTipText, String urlText, boolean shapeVisible, Shape shape, boolean shapeFilled, Paint fillPaint, boolean shapeOutlineVisible, Paint outlinePaint, Stroke outlineStroke, boolean lineVisible, Shape line, Stroke lineStroke, Paint linePaint)
  {
    if (label == null) {
      throw new IllegalArgumentException("Null 'label' argument.");
    }
    if (fillPaint == null) {
      throw new IllegalArgumentException("Null 'fillPaint' argument.");
    }
    if (lineStroke == null) {
      throw new IllegalArgumentException("Null 'lineStroke' argument.");
    }
    if (outlinePaint == null) {
      throw new IllegalArgumentException("Null 'outlinePaint' argument.");
    }
    if (outlineStroke == null) {
      throw new IllegalArgumentException("Null 'outlineStroke' argument.");
    }
    
    this.label = label;
    labelPaint = null;
    attributedLabel = null;
    this.description = description;
    this.shapeVisible = shapeVisible;
    this.shape = shape;
    this.shapeFilled = shapeFilled;
    this.fillPaint = fillPaint;
    fillPaintTransformer = new StandardGradientPaintTransformer();
    this.shapeOutlineVisible = shapeOutlineVisible;
    this.outlinePaint = outlinePaint;
    this.outlineStroke = outlineStroke;
    this.lineVisible = lineVisible;
    this.line = line;
    this.lineStroke = lineStroke;
    this.linePaint = linePaint;
    this.toolTipText = toolTipText;
    this.urlText = urlText;
  }
  














  public LegendItem(AttributedString label, String description, String toolTipText, String urlText, Shape shape, Paint fillPaint)
  {
    this(label, description, toolTipText, urlText, true, shape, true, fillPaint, false, Color.black, UNUSED_STROKE, false, UNUSED_SHAPE, UNUSED_STROKE, Color.black);
  }
  
























  public LegendItem(AttributedString label, String description, String toolTipText, String urlText, Shape shape, Paint fillPaint, Stroke outlineStroke, Paint outlinePaint)
  {
    this(label, description, toolTipText, urlText, true, shape, true, fillPaint, true, outlinePaint, outlineStroke, false, UNUSED_SHAPE, UNUSED_STROKE, Color.black);
  }
  


















  public LegendItem(AttributedString label, String description, String toolTipText, String urlText, Shape line, Stroke lineStroke, Paint linePaint)
  {
    this(label, description, toolTipText, urlText, false, UNUSED_SHAPE, false, Color.black, false, Color.black, UNUSED_STROKE, true, line, lineStroke, linePaint);
  }
  




































  public LegendItem(AttributedString label, String description, String toolTipText, String urlText, boolean shapeVisible, Shape shape, boolean shapeFilled, Paint fillPaint, boolean shapeOutlineVisible, Paint outlinePaint, Stroke outlineStroke, boolean lineVisible, Shape line, Stroke lineStroke, Paint linePaint)
  {
    if (label == null) {
      throw new IllegalArgumentException("Null 'label' argument.");
    }
    if (fillPaint == null) {
      throw new IllegalArgumentException("Null 'fillPaint' argument.");
    }
    if (lineStroke == null) {
      throw new IllegalArgumentException("Null 'lineStroke' argument.");
    }
    if (line == null) {
      throw new IllegalArgumentException("Null 'line' argument.");
    }
    if (linePaint == null) {
      throw new IllegalArgumentException("Null 'linePaint' argument.");
    }
    if (outlinePaint == null) {
      throw new IllegalArgumentException("Null 'outlinePaint' argument.");
    }
    if (outlineStroke == null) {
      throw new IllegalArgumentException("Null 'outlineStroke' argument.");
    }
    
    this.label = characterIteratorToString(label.getIterator());
    attributedLabel = label;
    this.description = description;
    this.shapeVisible = shapeVisible;
    this.shape = shape;
    this.shapeFilled = shapeFilled;
    this.fillPaint = fillPaint;
    fillPaintTransformer = new StandardGradientPaintTransformer();
    this.shapeOutlineVisible = shapeOutlineVisible;
    this.outlinePaint = outlinePaint;
    this.outlineStroke = outlineStroke;
    this.lineVisible = lineVisible;
    this.line = line;
    this.lineStroke = lineStroke;
    this.linePaint = linePaint;
    this.toolTipText = toolTipText;
    this.urlText = urlText;
  }
  






  private String characterIteratorToString(CharacterIterator iterator)
  {
    int endIndex = iterator.getEndIndex();
    int beginIndex = iterator.getBeginIndex();
    int count = endIndex - beginIndex;
    if (count <= 0) {
      return "";
    }
    char[] chars = new char[count];
    int i = 0;
    char c = iterator.first();
    while (c != 65535) {
      chars[i] = c;
      i++;
      c = iterator.next();
    }
    return new String(chars);
  }
  








  public Dataset getDataset()
  {
    return dataset;
  }
  






  public void setDataset(Dataset dataset)
  {
    this.dataset = dataset;
  }
  









  public int getDatasetIndex()
  {
    return datasetIndex;
  }
  








  public void setDatasetIndex(int index)
  {
    datasetIndex = index;
  }
  








  public Comparable getSeriesKey()
  {
    return seriesKey;
  }
  






  public void setSeriesKey(Comparable key)
  {
    seriesKey = key;
  }
  






  public int getSeriesIndex()
  {
    return series;
  }
  






  public void setSeriesIndex(int index)
  {
    series = index;
  }
  




  public String getLabel()
  {
    return label;
  }
  






  public Font getLabelFont()
  {
    return labelFont;
  }
  






  public void setLabelFont(Font font)
  {
    labelFont = font;
  }
  






  public Paint getLabelPaint()
  {
    return labelPaint;
  }
  






  public void setLabelPaint(Paint paint)
  {
    labelPaint = paint;
  }
  




  public AttributedString getAttributedLabel()
  {
    return attributedLabel;
  }
  




  public String getDescription()
  {
    return description;
  }
  




  public String getToolTipText()
  {
    return toolTipText;
  }
  




  public String getURLText()
  {
    return urlText;
  }
  




  public boolean isShapeVisible()
  {
    return shapeVisible;
  }
  





  public Shape getShape()
  {
    return shape;
  }
  




  public boolean isShapeFilled()
  {
    return shapeFilled;
  }
  




  public Paint getFillPaint()
  {
    return fillPaint;
  }
  






  public void setFillPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    fillPaint = paint;
  }
  





  public boolean isShapeOutlineVisible()
  {
    return shapeOutlineVisible;
  }
  




  public Stroke getLineStroke()
  {
    return lineStroke;
  }
  




  public Paint getLinePaint()
  {
    return linePaint;
  }
  






  public void setLinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    linePaint = paint;
  }
  




  public Paint getOutlinePaint()
  {
    return outlinePaint;
  }
  






  public void setOutlinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    outlinePaint = paint;
  }
  




  public Stroke getOutlineStroke()
  {
    return outlineStroke;
  }
  




  public boolean isLineVisible()
  {
    return lineVisible;
  }
  




  public Shape getLine()
  {
    return line;
  }
  









  public GradientPaintTransformer getFillPaintTransformer()
  {
    return fillPaintTransformer;
  }
  









  public void setFillPaintTransformer(GradientPaintTransformer transformer)
  {
    if (transformer == null) {
      throw new IllegalArgumentException("Null 'transformer' attribute.");
    }
    fillPaintTransformer = transformer;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof LegendItem)) {
      return false;
    }
    LegendItem that = (LegendItem)obj;
    if (datasetIndex != datasetIndex) {
      return false;
    }
    if (series != series) {
      return false;
    }
    if (!label.equals(label)) {
      return false;
    }
    if (!AttributedStringUtilities.equal(attributedLabel, attributedLabel))
    {
      return false;
    }
    if (!ObjectUtilities.equal(description, description)) {
      return false;
    }
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
    if (!outlineStroke.equals(outlineStroke)) {
      return false;
    }
    if (!PaintUtilities.equal(outlinePaint, outlinePaint)) {
      return false;
    }
    if ((!lineVisible) == lineVisible) {
      return false;
    }
    if (!ShapeUtilities.equal(line, line)) {
      return false;
    }
    if (!lineStroke.equals(lineStroke)) {
      return false;
    }
    if (!PaintUtilities.equal(linePaint, linePaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(labelFont, labelFont)) {
      return false;
    }
    if (!PaintUtilities.equal(labelPaint, labelPaint)) {
      return false;
    }
    return true;
  }
  









  public Object clone()
    throws CloneNotSupportedException
  {
    LegendItem clone = (LegendItem)super.clone();
    if ((seriesKey instanceof PublicCloneable)) {
      PublicCloneable pc = (PublicCloneable)seriesKey;
      seriesKey = ((Comparable)pc.clone());
    }
    
    shape = ShapeUtilities.clone(shape);
    if ((fillPaintTransformer instanceof PublicCloneable)) {
      PublicCloneable pc = (PublicCloneable)fillPaintTransformer;
      fillPaintTransformer = ((GradientPaintTransformer)pc.clone());
    }
    
    line = ShapeUtilities.clone(line);
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeAttributedString(attributedLabel, stream);
    SerialUtilities.writeShape(shape, stream);
    SerialUtilities.writePaint(fillPaint, stream);
    SerialUtilities.writeStroke(outlineStroke, stream);
    SerialUtilities.writePaint(outlinePaint, stream);
    SerialUtilities.writeShape(line, stream);
    SerialUtilities.writeStroke(lineStroke, stream);
    SerialUtilities.writePaint(linePaint, stream);
    SerialUtilities.writePaint(labelPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    attributedLabel = SerialUtilities.readAttributedString(stream);
    shape = SerialUtilities.readShape(stream);
    fillPaint = SerialUtilities.readPaint(stream);
    outlineStroke = SerialUtilities.readStroke(stream);
    outlinePaint = SerialUtilities.readPaint(stream);
    line = SerialUtilities.readShape(stream);
    lineStroke = SerialUtilities.readStroke(stream);
    linePaint = SerialUtilities.readPaint(stream);
    labelPaint = SerialUtilities.readPaint(stream);
  }
}
