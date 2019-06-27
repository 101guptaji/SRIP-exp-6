package org.jfree.chart.plot;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.ValueDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;




























































































public class MeterPlot
  extends Plot
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = 2987472457734470962L;
  static final Paint DEFAULT_DIAL_BACKGROUND_PAINT = Color.black;
  

  static final Paint DEFAULT_NEEDLE_PAINT = Color.green;
  

  static final Font DEFAULT_VALUE_FONT = new Font("SansSerif", 1, 12);
  

  static final Paint DEFAULT_VALUE_PAINT = Color.yellow;
  

  public static final int DEFAULT_METER_ANGLE = 270;
  

  public static final float DEFAULT_BORDER_SIZE = 3.0F;
  

  public static final float DEFAULT_CIRCLE_SIZE = 10.0F;
  

  public static final Font DEFAULT_LABEL_FONT = new Font("SansSerif", 1, 10);
  


  private ValueDataset dataset;
  

  private DialShape shape;
  

  private int meterAngle;
  

  private Range range;
  

  private double tickSize;
  

  private transient Paint tickPaint;
  

  private String units;
  

  private Font valueFont;
  

  private transient Paint valuePaint;
  

  private boolean drawBorder;
  

  private transient Paint dialOutlinePaint;
  

  private transient Paint dialBackgroundPaint;
  

  private transient Paint needlePaint;
  

  private boolean tickLabelsVisible;
  

  private Font tickLabelFont;
  

  private transient Paint tickLabelPaint;
  

  private NumberFormat tickLabelFormat;
  

  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
  




  private List intervals;
  





  public MeterPlot()
  {
    this(null);
  }
  





  public MeterPlot(ValueDataset dataset)
  {
    shape = DialShape.CIRCLE;
    meterAngle = 270;
    range = new Range(0.0D, 100.0D);
    tickSize = 10.0D;
    tickPaint = Color.white;
    units = "Units";
    needlePaint = DEFAULT_NEEDLE_PAINT;
    tickLabelsVisible = true;
    tickLabelFont = DEFAULT_LABEL_FONT;
    tickLabelPaint = Color.black;
    tickLabelFormat = NumberFormat.getInstance();
    valueFont = DEFAULT_VALUE_FONT;
    valuePaint = DEFAULT_VALUE_PAINT;
    dialBackgroundPaint = DEFAULT_DIAL_BACKGROUND_PAINT;
    intervals = new ArrayList();
    setDataset(dataset);
  }
  






  public DialShape getDialShape()
  {
    return shape;
  }
  







  public void setDialShape(DialShape shape)
  {
    if (shape == null) {
      throw new IllegalArgumentException("Null 'shape' argument.");
    }
    this.shape = shape;
    fireChangeEvent();
  }
  







  public int getMeterAngle()
  {
    return meterAngle;
  }
  







  public void setMeterAngle(int angle)
  {
    if ((angle < 1) || (angle > 360)) {
      throw new IllegalArgumentException("Invalid 'angle' (" + angle + ")");
    }
    
    meterAngle = angle;
    fireChangeEvent();
  }
  






  public Range getRange()
  {
    return range;
  }
  








  public void setRange(Range range)
  {
    if (range == null) {
      throw new IllegalArgumentException("Null 'range' argument.");
    }
    if (range.getLength() <= 0.0D) {
      throw new IllegalArgumentException("Range length must be positive.");
    }
    
    this.range = range;
    fireChangeEvent();
  }
  






  public double getTickSize()
  {
    return tickSize;
  }
  







  public void setTickSize(double size)
  {
    if (size <= 0.0D) {
      throw new IllegalArgumentException("Requires 'size' > 0.");
    }
    tickSize = size;
    fireChangeEvent();
  }
  







  public Paint getTickPaint()
  {
    return tickPaint;
  }
  







  public void setTickPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    tickPaint = paint;
    fireChangeEvent();
  }
  






  public String getUnits()
  {
    return units;
  }
  







  public void setUnits(String units)
  {
    this.units = units;
    fireChangeEvent();
  }
  






  public Paint getNeedlePaint()
  {
    return needlePaint;
  }
  







  public void setNeedlePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    needlePaint = paint;
    fireChangeEvent();
  }
  






  public boolean getTickLabelsVisible()
  {
    return tickLabelsVisible;
  }
  







  public void setTickLabelsVisible(boolean visible)
  {
    if (tickLabelsVisible != visible) {
      tickLabelsVisible = visible;
      fireChangeEvent();
    }
  }
  






  public Font getTickLabelFont()
  {
    return tickLabelFont;
  }
  







  public void setTickLabelFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    if (!tickLabelFont.equals(font)) {
      tickLabelFont = font;
      fireChangeEvent();
    }
  }
  






  public Paint getTickLabelPaint()
  {
    return tickLabelPaint;
  }
  







  public void setTickLabelPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    if (!tickLabelPaint.equals(paint)) {
      tickLabelPaint = paint;
      fireChangeEvent();
    }
  }
  






  public NumberFormat getTickLabelFormat()
  {
    return tickLabelFormat;
  }
  







  public void setTickLabelFormat(NumberFormat format)
  {
    if (format == null) {
      throw new IllegalArgumentException("Null 'format' argument.");
    }
    tickLabelFormat = format;
    fireChangeEvent();
  }
  






  public Font getValueFont()
  {
    return valueFont;
  }
  







  public void setValueFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    valueFont = font;
    fireChangeEvent();
  }
  






  public Paint getValuePaint()
  {
    return valuePaint;
  }
  







  public void setValuePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    valuePaint = paint;
    fireChangeEvent();
  }
  






  public Paint getDialBackgroundPaint()
  {
    return dialBackgroundPaint;
  }
  







  public void setDialBackgroundPaint(Paint paint)
  {
    dialBackgroundPaint = paint;
    fireChangeEvent();
  }
  







  public boolean getDrawBorder()
  {
    return drawBorder;
  }
  









  public void setDrawBorder(boolean draw)
  {
    drawBorder = draw;
    fireChangeEvent();
  }
  






  public Paint getDialOutlinePaint()
  {
    return dialOutlinePaint;
  }
  







  public void setDialOutlinePaint(Paint paint)
  {
    dialOutlinePaint = paint;
    fireChangeEvent();
  }
  






  public ValueDataset getDataset()
  {
    return dataset;
  }
  










  public void setDataset(ValueDataset dataset)
  {
    ValueDataset existing = this.dataset;
    if (existing != null) {
      existing.removeChangeListener(this);
    }
    

    this.dataset = dataset;
    if (dataset != null) {
      setDatasetGroup(dataset.getGroup());
      dataset.addChangeListener(this);
    }
    

    DatasetChangeEvent event = new DatasetChangeEvent(this, dataset);
    datasetChanged(event);
  }
  







  public List getIntervals()
  {
    return Collections.unmodifiableList(intervals);
  }
  








  public void addInterval(MeterInterval interval)
  {
    if (interval == null) {
      throw new IllegalArgumentException("Null 'interval' argument.");
    }
    intervals.add(interval);
    fireChangeEvent();
  }
  





  public void clearIntervals()
  {
    intervals.clear();
    fireChangeEvent();
  }
  




  public LegendItemCollection getLegendItems()
  {
    LegendItemCollection result = new LegendItemCollection();
    Iterator iterator = intervals.iterator();
    while (iterator.hasNext()) {
      MeterInterval mi = (MeterInterval)iterator.next();
      Paint color = mi.getBackgroundPaint();
      if (color == null) {
        color = mi.getOutlinePaint();
      }
      LegendItem item = new LegendItem(mi.getLabel(), mi.getLabel(), null, null, new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D), color);
      

      item.setDataset(getDataset());
      result.add(item);
    }
    return result;
  }
  












  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info)
  {
    if (info != null) {
      info.setPlotArea(area);
    }
    

    RectangleInsets insets = getInsets();
    insets.trim(area);
    
    area.setRect(area.getX() + 4.0D, area.getY() + 4.0D, area.getWidth() - 8.0D, area.getHeight() - 8.0D);
    


    if (drawBorder) {
      drawBackground(g2, area);
    }
    

    double gapHorizontal = 6.0D;
    double gapVertical = 6.0D;
    double meterX = area.getX() + gapHorizontal / 2.0D;
    double meterY = area.getY() + gapVertical / 2.0D;
    double meterW = area.getWidth() - gapHorizontal;
    double meterH = area.getHeight() - gapVertical + ((meterAngle <= 180) && (shape != DialShape.CIRCLE) ? area.getHeight() / 1.25D : 0.0D);
    


    double min = Math.min(meterW, meterH) / 2.0D;
    meterX = (meterX + meterX + meterW) / 2.0D - min;
    meterY = (meterY + meterY + meterH) / 2.0D - min;
    meterW = 2.0D * min;
    meterH = 2.0D * min;
    
    Rectangle2D meterArea = new Rectangle2D.Double(meterX, meterY, meterW, meterH);
    

    Rectangle2D.Double originalArea = new Rectangle2D.Double(meterArea.getX() - 4.0D, meterArea.getY() - 4.0D, meterArea.getWidth() + 8.0D, meterArea.getHeight() + 8.0D);
    


    double meterMiddleX = meterArea.getCenterX();
    double meterMiddleY = meterArea.getCenterY();
    

    ValueDataset data = getDataset();
    if (data != null) {
      double dataMin = range.getLowerBound();
      double dataMax = range.getUpperBound();
      
      Shape savedClip = g2.getClip();
      g2.clip(originalArea);
      Composite originalComposite = g2.getComposite();
      g2.setComposite(AlphaComposite.getInstance(3, getForegroundAlpha()));
      

      if (dialBackgroundPaint != null) {
        fillArc(g2, originalArea, dataMin, dataMax, dialBackgroundPaint, true);
      }
      
      drawTicks(g2, meterArea, dataMin, dataMax);
      drawArcForInterval(g2, meterArea, new MeterInterval("", range, dialOutlinePaint, new BasicStroke(1.0F), null));
      

      Iterator iterator = intervals.iterator();
      while (iterator.hasNext()) {
        MeterInterval interval = (MeterInterval)iterator.next();
        drawArcForInterval(g2, meterArea, interval);
      }
      
      Number n = data.getValue();
      if (n != null) {
        double value = n.doubleValue();
        drawValueLabel(g2, meterArea);
        
        if (range.contains(value)) {
          g2.setPaint(needlePaint);
          g2.setStroke(new BasicStroke(2.0F));
          
          double radius = meterArea.getWidth() / 2.0D + 3.0D + 15.0D;
          
          double valueAngle = valueToAngle(value);
          double valueP1 = meterMiddleX + radius * Math.cos(3.141592653589793D * (valueAngle / 180.0D));
          
          double valueP2 = meterMiddleY - radius * Math.sin(3.141592653589793D * (valueAngle / 180.0D));
          

          Polygon arrow = new Polygon();
          if (((valueAngle > 135.0D) && (valueAngle < 225.0D)) || ((valueAngle < 45.0D) && (valueAngle > -45.0D)))
          {

            double valueP3 = meterMiddleY - 2.5D;
            
            double valueP4 = meterMiddleY + 2.5D;
            
            arrow.addPoint((int)meterMiddleX, (int)valueP3);
            arrow.addPoint((int)meterMiddleX, (int)valueP4);
          }
          else
          {
            arrow.addPoint((int)(meterMiddleX - 2.5D), (int)meterMiddleY);
            
            arrow.addPoint((int)(meterMiddleX + 2.5D), (int)meterMiddleY);
          }
          
          arrow.addPoint((int)valueP1, (int)valueP2);
          g2.fill(arrow);
          
          Ellipse2D circle = new Ellipse2D.Double(meterMiddleX - 5.0D, meterMiddleY - 5.0D, 10.0D, 10.0D);
          


          g2.fill(circle);
        }
      }
      
      g2.setClip(savedClip);
      g2.setComposite(originalComposite);
    }
    
    if (drawBorder) {
      drawOutline(g2, area);
    }
  }
  









  protected void drawArcForInterval(Graphics2D g2, Rectangle2D meterArea, MeterInterval interval)
  {
    double minValue = interval.getRange().getLowerBound();
    double maxValue = interval.getRange().getUpperBound();
    Paint outlinePaint = interval.getOutlinePaint();
    Stroke outlineStroke = interval.getOutlineStroke();
    Paint backgroundPaint = interval.getBackgroundPaint();
    
    if (backgroundPaint != null) {
      fillArc(g2, meterArea, minValue, maxValue, backgroundPaint, false);
    }
    if (outlinePaint != null) {
      if (outlineStroke != null) {
        drawArc(g2, meterArea, minValue, maxValue, outlinePaint, outlineStroke);
      }
      
      drawTick(g2, meterArea, minValue, true);
      drawTick(g2, meterArea, maxValue, true);
    }
  }
  











  protected void drawArc(Graphics2D g2, Rectangle2D area, double minValue, double maxValue, Paint paint, Stroke stroke)
  {
    double startAngle = valueToAngle(maxValue);
    double endAngle = valueToAngle(minValue);
    double extent = endAngle - startAngle;
    
    double x = area.getX();
    double y = area.getY();
    double w = area.getWidth();
    double h = area.getHeight();
    g2.setPaint(paint);
    g2.setStroke(stroke);
    
    if ((paint != null) && (stroke != null)) {
      Arc2D.Double arc = new Arc2D.Double(x, y, w, h, startAngle, extent, 0);
      
      g2.setPaint(paint);
      g2.setStroke(stroke);
      g2.draw(arc);
    }
  }
  













  protected void fillArc(Graphics2D g2, Rectangle2D area, double minValue, double maxValue, Paint paint, boolean dial)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument");
    }
    double startAngle = valueToAngle(maxValue);
    double endAngle = valueToAngle(minValue);
    double extent = endAngle - startAngle;
    
    double x = area.getX();
    double y = area.getY();
    double w = area.getWidth();
    double h = area.getHeight();
    int joinType = 0;
    if (shape == DialShape.PIE) {
      joinType = 2;
    }
    else if (shape == DialShape.CHORD) {
      if ((dial) && (meterAngle > 180)) {
        joinType = 1;
      }
      else {
        joinType = 2;
      }
    }
    else if (shape == DialShape.CIRCLE) {
      joinType = 2;
      if (dial) {
        extent = 360.0D;
      }
    }
    else {
      throw new IllegalStateException("DialShape not recognised.");
    }
    
    g2.setPaint(paint);
    Arc2D.Double arc = new Arc2D.Double(x, y, w, h, startAngle, extent, joinType);
    
    g2.fill(arc);
  }
  






  public double valueToAngle(double value)
  {
    value -= range.getLowerBound();
    double baseAngle = 180 + (meterAngle - 180) / 2;
    return baseAngle - value / range.getLength() * meterAngle;
  }
  








  protected void drawTicks(Graphics2D g2, Rectangle2D meterArea, double minValue, double maxValue)
  {
    for (double v = minValue; v <= maxValue; v += tickSize) {
      drawTick(g2, meterArea, v);
    }
  }
  







  protected void drawTick(Graphics2D g2, Rectangle2D meterArea, double value)
  {
    drawTick(g2, meterArea, value, false);
  }
  









  protected void drawTick(Graphics2D g2, Rectangle2D meterArea, double value, boolean label)
  {
    double valueAngle = valueToAngle(value);
    
    double meterMiddleX = meterArea.getCenterX();
    double meterMiddleY = meterArea.getCenterY();
    
    g2.setPaint(tickPaint);
    g2.setStroke(new BasicStroke(2.0F));
    
    double valueP2X = 0.0D;
    double valueP2Y = 0.0D;
    
    double radius = meterArea.getWidth() / 2.0D + 3.0D;
    double radius1 = radius - 15.0D;
    
    double valueP1X = meterMiddleX + radius * Math.cos(3.141592653589793D * (valueAngle / 180.0D));
    
    double valueP1Y = meterMiddleY - radius * Math.sin(3.141592653589793D * (valueAngle / 180.0D));
    

    valueP2X = meterMiddleX + radius1 * Math.cos(3.141592653589793D * (valueAngle / 180.0D));
    
    valueP2Y = meterMiddleY - radius1 * Math.sin(3.141592653589793D * (valueAngle / 180.0D));
    

    Line2D.Double line = new Line2D.Double(valueP1X, valueP1Y, valueP2X, valueP2Y);
    
    g2.draw(line);
    
    if ((tickLabelsVisible) && (label))
    {
      String tickLabel = tickLabelFormat.format(value);
      g2.setFont(tickLabelFont);
      g2.setPaint(tickLabelPaint);
      
      FontMetrics fm = g2.getFontMetrics();
      Rectangle2D tickLabelBounds = TextUtilities.getTextBounds(tickLabel, g2, fm);
      

      double x = valueP2X;
      double y = valueP2Y;
      if ((valueAngle == 90.0D) || (valueAngle == 270.0D)) {
        x -= tickLabelBounds.getWidth() / 2.0D;
      }
      else if ((valueAngle < 90.0D) || (valueAngle > 270.0D)) {
        x -= tickLabelBounds.getWidth();
      }
      if (((valueAngle > 135.0D) && (valueAngle < 225.0D)) || (valueAngle > 315.0D) || (valueAngle < 45.0D))
      {
        y -= tickLabelBounds.getHeight() / 2.0D;
      }
      else {
        y += tickLabelBounds.getHeight() / 2.0D;
      }
      g2.drawString(tickLabel, (float)x, (float)y);
    }
  }
  





  protected void drawValueLabel(Graphics2D g2, Rectangle2D area)
  {
    g2.setFont(valueFont);
    g2.setPaint(valuePaint);
    String valueStr = "No value";
    if (dataset != null) {
      Number n = dataset.getValue();
      if (n != null) {
        valueStr = tickLabelFormat.format(n.doubleValue()) + " " + units;
      }
    }
    
    float x = (float)area.getCenterX();
    float y = (float)area.getCenterY() + 10.0F;
    TextUtilities.drawAlignedString(valueStr, g2, x, y, TextAnchor.TOP_CENTER);
  }
  





  public String getPlotType()
  {
    return localizationResources.getString("Meter_Plot");
  }
  








  public void zoom(double percent) {}
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MeterPlot)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    MeterPlot that = (MeterPlot)obj;
    if (!ObjectUtilities.equal(units, units)) {
      return false;
    }
    if (!ObjectUtilities.equal(range, range)) {
      return false;
    }
    if (!ObjectUtilities.equal(intervals, intervals)) {
      return false;
    }
    if (!PaintUtilities.equal(dialOutlinePaint, dialOutlinePaint))
    {
      return false;
    }
    if (shape != shape) {
      return false;
    }
    if (!PaintUtilities.equal(dialBackgroundPaint, dialBackgroundPaint))
    {
      return false;
    }
    if (!PaintUtilities.equal(needlePaint, needlePaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(valueFont, valueFont)) {
      return false;
    }
    if (!PaintUtilities.equal(valuePaint, valuePaint)) {
      return false;
    }
    if (!PaintUtilities.equal(tickPaint, tickPaint)) {
      return false;
    }
    if (tickSize != tickSize) {
      return false;
    }
    if (tickLabelsVisible != tickLabelsVisible) {
      return false;
    }
    if (!ObjectUtilities.equal(tickLabelFont, tickLabelFont)) {
      return false;
    }
    if (!PaintUtilities.equal(tickLabelPaint, tickLabelPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(tickLabelFormat, tickLabelFormat))
    {
      return false;
    }
    if (drawBorder != drawBorder) {
      return false;
    }
    if (meterAngle != meterAngle) {
      return false;
    }
    return true;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(dialBackgroundPaint, stream);
    SerialUtilities.writePaint(dialOutlinePaint, stream);
    SerialUtilities.writePaint(needlePaint, stream);
    SerialUtilities.writePaint(valuePaint, stream);
    SerialUtilities.writePaint(tickPaint, stream);
    SerialUtilities.writePaint(tickLabelPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    dialBackgroundPaint = SerialUtilities.readPaint(stream);
    dialOutlinePaint = SerialUtilities.readPaint(stream);
    needlePaint = SerialUtilities.readPaint(stream);
    valuePaint = SerialUtilities.readPaint(stream);
    tickPaint = SerialUtilities.readPaint(stream);
    tickLabelPaint = SerialUtilities.readPaint(stream);
    if (dataset != null) {
      dataset.addChangeListener(this);
    }
  }
  








  public Object clone()
    throws CloneNotSupportedException
  {
    MeterPlot clone = (MeterPlot)super.clone();
    tickLabelFormat = ((NumberFormat)tickLabelFormat.clone());
    
    intervals = new ArrayList(intervals);
    if (dataset != null) {
      dataset.addChangeListener(clone);
    }
    return clone;
  }
}
