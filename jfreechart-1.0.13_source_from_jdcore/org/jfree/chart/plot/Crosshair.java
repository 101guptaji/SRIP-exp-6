package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.chart.labels.CrosshairLabelGenerator;
import org.jfree.chart.labels.StandardCrosshairLabelGenerator;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleAnchor;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;





























































































public class Crosshair
  implements Cloneable, PublicCloneable, Serializable
{
  private boolean visible;
  private double value;
  private transient Paint paint;
  private transient Stroke stroke;
  private boolean labelVisible;
  private RectangleAnchor labelAnchor;
  private CrosshairLabelGenerator labelGenerator;
  private double labelXOffset;
  private double labelYOffset;
  private Font labelFont;
  private transient Paint labelPaint;
  private transient Paint labelBackgroundPaint;
  private boolean labelOutlineVisible;
  private transient Stroke labelOutlineStroke;
  private transient Paint labelOutlinePaint;
  private transient PropertyChangeSupport pcs;
  
  public Crosshair()
  {
    this(0.0D);
  }
  




  public Crosshair(double value)
  {
    this(value, Color.black, new BasicStroke(1.0F));
  }
  






  public Crosshair(double value, Paint paint, Stroke stroke)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    visible = true;
    this.value = value;
    this.paint = paint;
    this.stroke = stroke;
    labelVisible = false;
    labelGenerator = new StandardCrosshairLabelGenerator();
    labelAnchor = RectangleAnchor.BOTTOM_LEFT;
    labelXOffset = 3.0D;
    labelYOffset = 3.0D;
    labelFont = new Font("Tahoma", 0, 12);
    labelPaint = Color.black;
    labelBackgroundPaint = new Color(0, 0, 255, 63);
    labelOutlineVisible = true;
    labelOutlinePaint = Color.black;
    labelOutlineStroke = new BasicStroke(0.5F);
    pcs = new PropertyChangeSupport(this);
  }
  





  public boolean isVisible()
  {
    return visible;
  }
  






  public void setVisible(boolean visible)
  {
    boolean old = this.visible;
    this.visible = visible;
    pcs.firePropertyChange("visible", old, visible);
  }
  




  public double getValue()
  {
    return value;
  }
  





  public void setValue(double value)
  {
    Double oldValue = new Double(this.value);
    this.value = value;
    pcs.firePropertyChange("value", oldValue, new Double(value));
  }
  




  public Paint getPaint()
  {
    return paint;
  }
  





  public void setPaint(Paint paint)
  {
    Paint old = this.paint;
    this.paint = paint;
    pcs.firePropertyChange("paint", old, paint);
  }
  




  public Stroke getStroke()
  {
    return stroke;
  }
  





  public void setStroke(Stroke stroke)
  {
    Stroke old = this.stroke;
    this.stroke = stroke;
    pcs.firePropertyChange("stroke", old, stroke);
  }
  





  public boolean isLabelVisible()
  {
    return labelVisible;
  }
  






  public void setLabelVisible(boolean visible)
  {
    boolean old = labelVisible;
    labelVisible = visible;
    pcs.firePropertyChange("labelVisible", old, visible);
  }
  




  public CrosshairLabelGenerator getLabelGenerator()
  {
    return labelGenerator;
  }
  





  public void setLabelGenerator(CrosshairLabelGenerator generator)
  {
    if (generator == null) {
      throw new IllegalArgumentException("Null 'generator' argument.");
    }
    CrosshairLabelGenerator old = labelGenerator;
    labelGenerator = generator;
    pcs.firePropertyChange("labelGenerator", old, generator);
  }
  




  public RectangleAnchor getLabelAnchor()
  {
    return labelAnchor;
  }
  





  public void setLabelAnchor(RectangleAnchor anchor)
  {
    RectangleAnchor old = labelAnchor;
    labelAnchor = anchor;
    pcs.firePropertyChange("labelAnchor", old, anchor);
  }
  




  public double getLabelXOffset()
  {
    return labelXOffset;
  }
  





  public void setLabelXOffset(double offset)
  {
    Double old = new Double(labelXOffset);
    labelXOffset = offset;
    pcs.firePropertyChange("labelXOffset", old, new Double(offset));
  }
  




  public double getLabelYOffset()
  {
    return labelYOffset;
  }
  





  public void setLabelYOffset(double offset)
  {
    Double old = new Double(labelYOffset);
    labelYOffset = offset;
    pcs.firePropertyChange("labelYOffset", old, new Double(offset));
  }
  




  public Font getLabelFont()
  {
    return labelFont;
  }
  





  public void setLabelFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    Font old = labelFont;
    labelFont = font;
    pcs.firePropertyChange("labelFont", old, font);
  }
  




  public Paint getLabelPaint()
  {
    return labelPaint;
  }
  





  public void setLabelPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    Paint old = labelPaint;
    labelPaint = paint;
    pcs.firePropertyChange("labelPaint", old, paint);
  }
  




  public Paint getLabelBackgroundPaint()
  {
    return labelBackgroundPaint;
  }
  





  public void setLabelBackgroundPaint(Paint paint)
  {
    Paint old = labelBackgroundPaint;
    labelBackgroundPaint = paint;
    pcs.firePropertyChange("labelBackgroundPaint", old, paint);
  }
  




  public boolean isLabelOutlineVisible()
  {
    return labelOutlineVisible;
  }
  






  public void setLabelOutlineVisible(boolean visible)
  {
    boolean old = labelOutlineVisible;
    labelOutlineVisible = visible;
    pcs.firePropertyChange("labelOutlineVisible", old, visible);
  }
  




  public Paint getLabelOutlinePaint()
  {
    return labelOutlinePaint;
  }
  





  public void setLabelOutlinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    Paint old = labelOutlinePaint;
    labelOutlinePaint = paint;
    pcs.firePropertyChange("labelOutlinePaint", old, paint);
  }
  




  public Stroke getLabelOutlineStroke()
  {
    return labelOutlineStroke;
  }
  





  public void setLabelOutlineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    Stroke old = labelOutlineStroke;
    labelOutlineStroke = stroke;
    pcs.firePropertyChange("labelOutlineStroke", old, stroke);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Crosshair)) {
      return false;
    }
    Crosshair that = (Crosshair)obj;
    if (visible != visible) {
      return false;
    }
    if (value != value) {
      return false;
    }
    if (!PaintUtilities.equal(paint, paint)) {
      return false;
    }
    if (!stroke.equals(stroke)) {
      return false;
    }
    if (labelVisible != labelVisible) {
      return false;
    }
    if (!labelGenerator.equals(labelGenerator)) {
      return false;
    }
    if (!labelAnchor.equals(labelAnchor)) {
      return false;
    }
    if (labelXOffset != labelXOffset) {
      return false;
    }
    if (labelYOffset != labelYOffset) {
      return false;
    }
    if (!labelFont.equals(labelFont)) {
      return false;
    }
    if (!PaintUtilities.equal(labelPaint, labelPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(labelBackgroundPaint, labelBackgroundPaint))
    {
      return false;
    }
    if (labelOutlineVisible != labelOutlineVisible) {
      return false;
    }
    if (!PaintUtilities.equal(labelOutlinePaint, labelOutlinePaint))
    {
      return false;
    }
    if (!labelOutlineStroke.equals(labelOutlineStroke)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int hash = 7;
    hash = HashUtilities.hashCode(hash, visible);
    hash = HashUtilities.hashCode(hash, value);
    hash = HashUtilities.hashCode(hash, paint);
    hash = HashUtilities.hashCode(hash, stroke);
    hash = HashUtilities.hashCode(hash, labelVisible);
    hash = HashUtilities.hashCode(hash, labelAnchor);
    hash = HashUtilities.hashCode(hash, labelGenerator);
    hash = HashUtilities.hashCode(hash, labelXOffset);
    hash = HashUtilities.hashCode(hash, labelYOffset);
    hash = HashUtilities.hashCode(hash, labelFont);
    hash = HashUtilities.hashCode(hash, labelPaint);
    hash = HashUtilities.hashCode(hash, labelBackgroundPaint);
    hash = HashUtilities.hashCode(hash, labelOutlineVisible);
    hash = HashUtilities.hashCode(hash, labelOutlineStroke);
    hash = HashUtilities.hashCode(hash, labelOutlinePaint);
    return hash;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  




  public void addPropertyChangeListener(PropertyChangeListener l)
  {
    pcs.addPropertyChangeListener(l);
  }
  




  public void removePropertyChangeListener(PropertyChangeListener l)
  {
    pcs.removePropertyChangeListener(l);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(paint, stream);
    SerialUtilities.writeStroke(stroke, stream);
    SerialUtilities.writePaint(labelPaint, stream);
    SerialUtilities.writePaint(labelBackgroundPaint, stream);
    SerialUtilities.writeStroke(labelOutlineStroke, stream);
    SerialUtilities.writePaint(labelOutlinePaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    paint = SerialUtilities.readPaint(stream);
    stroke = SerialUtilities.readStroke(stream);
    labelPaint = SerialUtilities.readPaint(stream);
    labelBackgroundPaint = SerialUtilities.readPaint(stream);
    labelOutlineStroke = SerialUtilities.readStroke(stream);
    labelOutlinePaint = SerialUtilities.readPaint(stream);
    pcs = new PropertyChangeSupport(this);
  }
}
