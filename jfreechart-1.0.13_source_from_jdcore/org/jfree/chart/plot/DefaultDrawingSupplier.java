package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import org.jfree.chart.ChartColor;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;


























































public class DefaultDrawingSupplier
  implements DrawingSupplier, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -7339847061039422538L;
  public static final Paint[] DEFAULT_PAINT_SEQUENCE = ;
  


  public static final Paint[] DEFAULT_OUTLINE_PAINT_SEQUENCE = { Color.lightGray };
  


  public static final Paint[] DEFAULT_FILL_PAINT_SEQUENCE = { Color.white };
  


  public static final Stroke[] DEFAULT_STROKE_SEQUENCE = { new BasicStroke(1.0F, 2, 2) };
  



  public static final Stroke[] DEFAULT_OUTLINE_STROKE_SEQUENCE = { new BasicStroke(1.0F, 2, 2) };
  



  public static final Shape[] DEFAULT_SHAPE_SEQUENCE = createStandardSeriesShapes();
  


  private transient Paint[] paintSequence;
  


  private int paintIndex;
  


  private transient Paint[] outlinePaintSequence;
  

  private int outlinePaintIndex;
  

  private transient Paint[] fillPaintSequence;
  

  private int fillPaintIndex;
  

  private transient Stroke[] strokeSequence;
  

  private int strokeIndex;
  

  private transient Stroke[] outlineStrokeSequence;
  

  private int outlineStrokeIndex;
  

  private transient Shape[] shapeSequence;
  

  private int shapeIndex;
  


  public DefaultDrawingSupplier()
  {
    this(DEFAULT_PAINT_SEQUENCE, DEFAULT_FILL_PAINT_SEQUENCE, DEFAULT_OUTLINE_PAINT_SEQUENCE, DEFAULT_STROKE_SEQUENCE, DEFAULT_OUTLINE_STROKE_SEQUENCE, DEFAULT_SHAPE_SEQUENCE);
  }
  


















  public DefaultDrawingSupplier(Paint[] paintSequence, Paint[] outlinePaintSequence, Stroke[] strokeSequence, Stroke[] outlineStrokeSequence, Shape[] shapeSequence)
  {
    this.paintSequence = paintSequence;
    fillPaintSequence = DEFAULT_FILL_PAINT_SEQUENCE;
    this.outlinePaintSequence = outlinePaintSequence;
    this.strokeSequence = strokeSequence;
    this.outlineStrokeSequence = outlineStrokeSequence;
    this.shapeSequence = shapeSequence;
  }
  
















  public DefaultDrawingSupplier(Paint[] paintSequence, Paint[] fillPaintSequence, Paint[] outlinePaintSequence, Stroke[] strokeSequence, Stroke[] outlineStrokeSequence, Shape[] shapeSequence)
  {
    this.paintSequence = paintSequence;
    this.fillPaintSequence = fillPaintSequence;
    this.outlinePaintSequence = outlinePaintSequence;
    this.strokeSequence = strokeSequence;
    this.outlineStrokeSequence = outlineStrokeSequence;
    this.shapeSequence = shapeSequence;
  }
  




  public Paint getNextPaint()
  {
    Paint result = paintSequence[(paintIndex % paintSequence.length)];
    
    paintIndex += 1;
    return result;
  }
  




  public Paint getNextOutlinePaint()
  {
    Paint result = outlinePaintSequence[(outlinePaintIndex % outlinePaintSequence.length)];
    
    outlinePaintIndex += 1;
    return result;
  }
  






  public Paint getNextFillPaint()
  {
    Paint result = fillPaintSequence[(fillPaintIndex % fillPaintSequence.length)];
    
    fillPaintIndex += 1;
    return result;
  }
  




  public Stroke getNextStroke()
  {
    Stroke result = strokeSequence[(strokeIndex % strokeSequence.length)];
    
    strokeIndex += 1;
    return result;
  }
  




  public Stroke getNextOutlineStroke()
  {
    Stroke result = outlineStrokeSequence[(outlineStrokeIndex % outlineStrokeSequence.length)];
    
    outlineStrokeIndex += 1;
    return result;
  }
  




  public Shape getNextShape()
  {
    Shape result = shapeSequence[(shapeIndex % shapeSequence.length)];
    
    shapeIndex += 1;
    return result;
  }
  






  public static Shape[] createStandardSeriesShapes()
  {
    Shape[] result = new Shape[10];
    
    double size = 6.0D;
    double delta = size / 2.0D;
    int[] xpoints = null;
    int[] ypoints = null;
    

    result[0] = new Rectangle2D.Double(-delta, -delta, size, size);
    
    result[1] = new Ellipse2D.Double(-delta, -delta, size, size);
    

    xpoints = intArray(0.0D, delta, -delta);
    ypoints = intArray(-delta, delta, delta);
    result[2] = new Polygon(xpoints, ypoints, 3);
    

    xpoints = intArray(0.0D, delta, 0.0D, -delta);
    ypoints = intArray(-delta, 0.0D, delta, 0.0D);
    result[3] = new Polygon(xpoints, ypoints, 4);
    

    result[4] = new Rectangle2D.Double(-delta, -delta / 2.0D, size, size / 2.0D);
    

    xpoints = intArray(-delta, delta, 0.0D);
    ypoints = intArray(-delta, -delta, delta);
    result[5] = new Polygon(xpoints, ypoints, 3);
    

    result[6] = new Ellipse2D.Double(-delta, -delta / 2.0D, size, size / 2.0D);
    

    xpoints = intArray(-delta, delta, -delta);
    ypoints = intArray(-delta, 0.0D, delta);
    result[7] = new Polygon(xpoints, ypoints, 3);
    

    result[8] = new Rectangle2D.Double(-delta / 2.0D, -delta, size / 2.0D, size);
    

    xpoints = intArray(-delta, delta, delta);
    ypoints = intArray(0.0D, -delta, delta);
    result[9] = new Polygon(xpoints, ypoints, 3);
    
    return result;
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    
    if (!(obj instanceof DefaultDrawingSupplier)) {
      return false;
    }
    
    DefaultDrawingSupplier that = (DefaultDrawingSupplier)obj;
    
    if (!Arrays.equals(paintSequence, paintSequence)) {
      return false;
    }
    if (paintIndex != paintIndex) {
      return false;
    }
    if (!Arrays.equals(outlinePaintSequence, outlinePaintSequence))
    {
      return false;
    }
    if (outlinePaintIndex != outlinePaintIndex) {
      return false;
    }
    if (!Arrays.equals(strokeSequence, strokeSequence)) {
      return false;
    }
    if (strokeIndex != strokeIndex) {
      return false;
    }
    if (!Arrays.equals(outlineStrokeSequence, outlineStrokeSequence))
    {
      return false;
    }
    if (outlineStrokeIndex != outlineStrokeIndex) {
      return false;
    }
    if (!equalShapes(shapeSequence, shapeSequence)) {
      return false;
    }
    if (shapeIndex != shapeIndex) {
      return false;
    }
    return true;
  }
  








  private boolean equalShapes(Shape[] s1, Shape[] s2)
  {
    if (s1 == null) {
      return s2 == null;
    }
    if (s2 == null) {
      return false;
    }
    if (s1.length != s2.length) {
      return false;
    }
    for (int i = 0; i < s1.length; i++) {
      if (!ShapeUtilities.equal(s1[i], s2[i])) {
        return false;
      }
    }
    return true;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    
    int paintCount = paintSequence.length;
    stream.writeInt(paintCount);
    for (int i = 0; i < paintCount; i++) {
      SerialUtilities.writePaint(paintSequence[i], stream);
    }
    
    int outlinePaintCount = outlinePaintSequence.length;
    stream.writeInt(outlinePaintCount);
    for (int i = 0; i < outlinePaintCount; i++) {
      SerialUtilities.writePaint(outlinePaintSequence[i], stream);
    }
    
    int strokeCount = strokeSequence.length;
    stream.writeInt(strokeCount);
    for (int i = 0; i < strokeCount; i++) {
      SerialUtilities.writeStroke(strokeSequence[i], stream);
    }
    
    int outlineStrokeCount = outlineStrokeSequence.length;
    stream.writeInt(outlineStrokeCount);
    for (int i = 0; i < outlineStrokeCount; i++) {
      SerialUtilities.writeStroke(outlineStrokeSequence[i], stream);
    }
    
    int shapeCount = shapeSequence.length;
    stream.writeInt(shapeCount);
    for (int i = 0; i < shapeCount; i++) {
      SerialUtilities.writeShape(shapeSequence[i], stream);
    }
  }
  








  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    
    int paintCount = stream.readInt();
    paintSequence = new Paint[paintCount];
    for (int i = 0; i < paintCount; i++) {
      paintSequence[i] = SerialUtilities.readPaint(stream);
    }
    
    int outlinePaintCount = stream.readInt();
    outlinePaintSequence = new Paint[outlinePaintCount];
    for (int i = 0; i < outlinePaintCount; i++) {
      outlinePaintSequence[i] = SerialUtilities.readPaint(stream);
    }
    
    int strokeCount = stream.readInt();
    strokeSequence = new Stroke[strokeCount];
    for (int i = 0; i < strokeCount; i++) {
      strokeSequence[i] = SerialUtilities.readStroke(stream);
    }
    
    int outlineStrokeCount = stream.readInt();
    outlineStrokeSequence = new Stroke[outlineStrokeCount];
    for (int i = 0; i < outlineStrokeCount; i++) {
      outlineStrokeSequence[i] = SerialUtilities.readStroke(stream);
    }
    
    int shapeCount = stream.readInt();
    shapeSequence = new Shape[shapeCount];
    for (int i = 0; i < shapeCount; i++) {
      shapeSequence[i] = SerialUtilities.readShape(stream);
    }
  }
  










  private static int[] intArray(double a, double b, double c)
  {
    return new int[] { (int)a, (int)b, (int)c };
  }
  










  private static int[] intArray(double a, double b, double c, double d)
  {
    return new int[] { (int)a, (int)b, (int)c, (int)d };
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    DefaultDrawingSupplier clone = (DefaultDrawingSupplier)super.clone();
    return clone;
  }
}
