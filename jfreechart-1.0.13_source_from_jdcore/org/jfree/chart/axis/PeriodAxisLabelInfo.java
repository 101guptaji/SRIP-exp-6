package org.jfree.chart.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleInsets;


























































public class PeriodAxisLabelInfo
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 5710451740920277357L;
  public static final RectangleInsets DEFAULT_INSETS = new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D);
  


  public static final Font DEFAULT_FONT = new Font("SansSerif", 0, 10);
  


  public static final Paint DEFAULT_LABEL_PAINT = Color.black;
  

  public static final Stroke DEFAULT_DIVIDER_STROKE = new BasicStroke(0.5F);
  

  public static final Paint DEFAULT_DIVIDER_PAINT = Color.gray;
  


  private Class periodClass;
  


  private RectangleInsets padding;
  


  private DateFormat dateFormat;
  


  private Font labelFont;
  

  private transient Paint labelPaint;
  

  private boolean drawDividers;
  

  private transient Stroke dividerStroke;
  

  private transient Paint dividerPaint;
  


  public PeriodAxisLabelInfo(Class periodClass, DateFormat dateFormat)
  {
    this(periodClass, dateFormat, DEFAULT_INSETS, DEFAULT_FONT, DEFAULT_LABEL_PAINT, true, DEFAULT_DIVIDER_STROKE, DEFAULT_DIVIDER_PAINT);
  }
  























  public PeriodAxisLabelInfo(Class periodClass, DateFormat dateFormat, RectangleInsets padding, Font labelFont, Paint labelPaint, boolean drawDividers, Stroke dividerStroke, Paint dividerPaint)
  {
    if (periodClass == null) {
      throw new IllegalArgumentException("Null 'periodClass' argument.");
    }
    if (dateFormat == null) {
      throw new IllegalArgumentException("Null 'dateFormat' argument.");
    }
    if (padding == null) {
      throw new IllegalArgumentException("Null 'padding' argument.");
    }
    if (labelFont == null) {
      throw new IllegalArgumentException("Null 'labelFont' argument.");
    }
    if (labelPaint == null) {
      throw new IllegalArgumentException("Null 'labelPaint' argument.");
    }
    if (dividerStroke == null) {
      throw new IllegalArgumentException("Null 'dividerStroke' argument.");
    }
    
    if (dividerPaint == null) {
      throw new IllegalArgumentException("Null 'dividerPaint' argument.");
    }
    this.periodClass = periodClass;
    this.dateFormat = dateFormat;
    this.padding = padding;
    this.labelFont = labelFont;
    this.labelPaint = labelPaint;
    this.drawDividers = drawDividers;
    this.dividerStroke = dividerStroke;
    this.dividerPaint = dividerPaint;
  }
  





  public Class getPeriodClass()
  {
    return periodClass;
  }
  




  public DateFormat getDateFormat()
  {
    return dateFormat;
  }
  




  public RectangleInsets getPadding()
  {
    return padding;
  }
  




  public Font getLabelFont()
  {
    return labelFont;
  }
  




  public Paint getLabelPaint()
  {
    return labelPaint;
  }
  




  public boolean getDrawDividers()
  {
    return drawDividers;
  }
  




  public Stroke getDividerStroke()
  {
    return dividerStroke;
  }
  




  public Paint getDividerPaint()
  {
    return dividerPaint;
  }
  







  /**
   * @deprecated
   */
  public RegularTimePeriod createInstance(Date millisecond, TimeZone zone)
  {
    return createInstance(millisecond, zone, Locale.getDefault());
  }
  












  public RegularTimePeriod createInstance(Date millisecond, TimeZone zone, Locale locale)
  {
    RegularTimePeriod result = null;
    try {
      Constructor c = periodClass.getDeclaredConstructor(new Class[] { Date.class, TimeZone.class, Locale.class });
      
      result = (RegularTimePeriod)c.newInstance(new Object[] { millisecond, zone, locale });
    }
    catch (Exception e) {}
    


    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if ((obj instanceof PeriodAxisLabelInfo)) {
      PeriodAxisLabelInfo info = (PeriodAxisLabelInfo)obj;
      if (!periodClass.equals(periodClass)) {
        return false;
      }
      if (!dateFormat.equals(dateFormat)) {
        return false;
      }
      if (!padding.equals(padding)) {
        return false;
      }
      if (!labelFont.equals(labelFont)) {
        return false;
      }
      if (!labelPaint.equals(labelPaint)) {
        return false;
      }
      if (drawDividers != drawDividers) {
        return false;
      }
      if (!dividerStroke.equals(dividerStroke)) {
        return false;
      }
      if (!dividerPaint.equals(dividerPaint)) {
        return false;
      }
      return true;
    }
    return false;
  }
  




  public int hashCode()
  {
    int result = 41;
    result = 37 * periodClass.hashCode();
    result = 37 * dateFormat.hashCode();
    return result;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    PeriodAxisLabelInfo clone = (PeriodAxisLabelInfo)super.clone();
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(labelPaint, stream);
    SerialUtilities.writeStroke(dividerStroke, stream);
    SerialUtilities.writePaint(dividerPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    labelPaint = SerialUtilities.readPaint(stream);
    dividerStroke = SerialUtilities.readStroke(stream);
    dividerPaint = SerialUtilities.readPaint(stream);
  }
}
