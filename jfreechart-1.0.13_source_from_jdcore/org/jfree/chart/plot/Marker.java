package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.EventListener;
import javax.swing.event.EventListenerList;
import org.jfree.chart.event.MarkerChangeEvent;
import org.jfree.chart.event.MarkerChangeListener;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;


















































































public abstract class Marker
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -734389651405327166L;
  private transient Paint paint;
  private transient Stroke stroke;
  private transient Paint outlinePaint;
  private transient Stroke outlineStroke;
  private float alpha;
  private String label = null;
  


  private Font labelFont;
  


  private transient Paint labelPaint;
  

  private RectangleAnchor labelAnchor;
  

  private TextAnchor labelTextAnchor;
  

  private RectangleInsets labelOffset;
  

  private LengthAdjustmentType labelOffsetType;
  

  private transient EventListenerList listenerList;
  


  protected Marker()
  {
    this(Color.gray);
  }
  




  protected Marker(Paint paint)
  {
    this(paint, new BasicStroke(0.5F), Color.gray, new BasicStroke(0.5F), 0.8F);
  }
  

















  protected Marker(Paint paint, Stroke stroke, Paint outlinePaint, Stroke outlineStroke, float alpha)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    if ((alpha < 0.0F) || (alpha > 1.0F)) {
      throw new IllegalArgumentException("The 'alpha' value must be in the range 0.0f to 1.0f");
    }
    
    this.paint = paint;
    this.stroke = stroke;
    this.outlinePaint = outlinePaint;
    this.outlineStroke = outlineStroke;
    this.alpha = alpha;
    
    labelFont = new Font("SansSerif", 0, 9);
    labelPaint = Color.black;
    labelAnchor = RectangleAnchor.TOP_LEFT;
    labelOffset = new RectangleInsets(3.0D, 3.0D, 3.0D, 3.0D);
    labelOffsetType = LengthAdjustmentType.CONTRACT;
    labelTextAnchor = TextAnchor.CENTER;
    
    listenerList = new EventListenerList();
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
    notifyListeners(new MarkerChangeEvent(this));
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
    notifyListeners(new MarkerChangeEvent(this));
  }
  






  public Paint getOutlinePaint()
  {
    return outlinePaint;
  }
  







  public void setOutlinePaint(Paint paint)
  {
    outlinePaint = paint;
    notifyListeners(new MarkerChangeEvent(this));
  }
  






  public Stroke getOutlineStroke()
  {
    return outlineStroke;
  }
  







  public void setOutlineStroke(Stroke stroke)
  {
    outlineStroke = stroke;
    notifyListeners(new MarkerChangeEvent(this));
  }
  






  public float getAlpha()
  {
    return alpha;
  }
  













  public void setAlpha(float alpha)
  {
    if ((alpha < 0.0F) || (alpha > 1.0F)) {
      throw new IllegalArgumentException("The 'alpha' value must be in the range 0.0f to 1.0f");
    }
    this.alpha = alpha;
    notifyListeners(new MarkerChangeEvent(this));
  }
  






  public String getLabel()
  {
    return label;
  }
  







  public void setLabel(String label)
  {
    this.label = label;
    notifyListeners(new MarkerChangeEvent(this));
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
    labelFont = font;
    notifyListeners(new MarkerChangeEvent(this));
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
    labelPaint = paint;
    notifyListeners(new MarkerChangeEvent(this));
  }
  







  public RectangleAnchor getLabelAnchor()
  {
    return labelAnchor;
  }
  








  public void setLabelAnchor(RectangleAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    labelAnchor = anchor;
    notifyListeners(new MarkerChangeEvent(this));
  }
  






  public RectangleInsets getLabelOffset()
  {
    return labelOffset;
  }
  







  public void setLabelOffset(RectangleInsets offset)
  {
    if (offset == null) {
      throw new IllegalArgumentException("Null 'offset' argument.");
    }
    labelOffset = offset;
    notifyListeners(new MarkerChangeEvent(this));
  }
  






  public LengthAdjustmentType getLabelOffsetType()
  {
    return labelOffsetType;
  }
  







  public void setLabelOffsetType(LengthAdjustmentType adj)
  {
    if (adj == null) {
      throw new IllegalArgumentException("Null 'adj' argument.");
    }
    labelOffsetType = adj;
    notifyListeners(new MarkerChangeEvent(this));
  }
  






  public TextAnchor getLabelTextAnchor()
  {
    return labelTextAnchor;
  }
  







  public void setLabelTextAnchor(TextAnchor anchor)
  {
    if (anchor == null) {
      throw new IllegalArgumentException("Null 'anchor' argument.");
    }
    labelTextAnchor = anchor;
    notifyListeners(new MarkerChangeEvent(this));
  }
  








  public void addChangeListener(MarkerChangeListener listener)
  {
    listenerList.add(MarkerChangeListener.class, listener);
  }
  








  public void removeChangeListener(MarkerChangeListener listener)
  {
    listenerList.remove(MarkerChangeListener.class, listener);
  }
  







  public void notifyListeners(MarkerChangeEvent event)
  {
    Object[] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == MarkerChangeListener.class) {
        ((MarkerChangeListener)listeners[(i + 1)]).markerChanged(event);
      }
    }
  }
  









  public EventListener[] getListeners(Class listenerType)
  {
    return listenerList.getListeners(listenerType);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Marker)) {
      return false;
    }
    Marker that = (Marker)obj;
    if (!PaintUtilities.equal(paint, paint)) {
      return false;
    }
    if (!ObjectUtilities.equal(stroke, stroke)) {
      return false;
    }
    if (!PaintUtilities.equal(outlinePaint, outlinePaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(outlineStroke, outlineStroke)) {
      return false;
    }
    if (alpha != alpha) {
      return false;
    }
    if (!ObjectUtilities.equal(label, label)) {
      return false;
    }
    if (!ObjectUtilities.equal(labelFont, labelFont)) {
      return false;
    }
    if (!PaintUtilities.equal(labelPaint, labelPaint)) {
      return false;
    }
    if (labelAnchor != labelAnchor) {
      return false;
    }
    if (labelTextAnchor != labelTextAnchor) {
      return false;
    }
    if (!ObjectUtilities.equal(labelOffset, labelOffset)) {
      return false;
    }
    if (!labelOffsetType.equals(labelOffsetType)) {
      return false;
    }
    return true;
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
    SerialUtilities.writePaint(outlinePaint, stream);
    SerialUtilities.writeStroke(outlineStroke, stream);
    SerialUtilities.writePaint(labelPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    paint = SerialUtilities.readPaint(stream);
    stroke = SerialUtilities.readStroke(stream);
    outlinePaint = SerialUtilities.readPaint(stream);
    outlineStroke = SerialUtilities.readStroke(stream);
    labelPaint = SerialUtilities.readPaint(stream);
    listenerList = new EventListenerList();
  }
}
