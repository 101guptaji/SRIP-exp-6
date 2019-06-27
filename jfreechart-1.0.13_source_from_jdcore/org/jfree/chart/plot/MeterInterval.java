package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.data.Range;
import org.jfree.io.SerialUtilities;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;






























































public class MeterInterval
  implements Serializable
{
  private static final long serialVersionUID = 1530982090622488257L;
  private String label;
  private Range range;
  private transient Paint outlinePaint;
  private transient Stroke outlineStroke;
  private transient Paint backgroundPaint;
  
  public MeterInterval(String label, Range range)
  {
    this(label, range, Color.yellow, new BasicStroke(2.0F), null);
  }
  










  public MeterInterval(String label, Range range, Paint outlinePaint, Stroke outlineStroke, Paint backgroundPaint)
  {
    if (label == null) {
      throw new IllegalArgumentException("Null 'label' argument.");
    }
    if (range == null) {
      throw new IllegalArgumentException("Null 'range' argument.");
    }
    this.label = label;
    this.range = range;
    this.outlinePaint = outlinePaint;
    this.outlineStroke = outlineStroke;
    this.backgroundPaint = backgroundPaint;
  }
  




  public String getLabel()
  {
    return label;
  }
  




  public Range getRange()
  {
    return range;
  }
  





  public Paint getBackgroundPaint()
  {
    return backgroundPaint;
  }
  




  public Paint getOutlinePaint()
  {
    return outlinePaint;
  }
  




  public Stroke getOutlineStroke()
  {
    return outlineStroke;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MeterInterval)) {
      return false;
    }
    MeterInterval that = (MeterInterval)obj;
    if (!label.equals(label)) {
      return false;
    }
    if (!range.equals(range)) {
      return false;
    }
    if (!PaintUtilities.equal(outlinePaint, outlinePaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(outlineStroke, outlineStroke)) {
      return false;
    }
    if (!PaintUtilities.equal(backgroundPaint, backgroundPaint)) {
      return false;
    }
    return true;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(outlinePaint, stream);
    SerialUtilities.writeStroke(outlineStroke, stream);
    SerialUtilities.writePaint(backgroundPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    outlinePaint = SerialUtilities.readPaint(stream);
    outlineStroke = SerialUtilities.readStroke(stream);
    backgroundPaint = SerialUtilities.readPaint(stream);
  }
}
