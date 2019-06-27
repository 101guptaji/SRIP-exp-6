package org.jfree.chart.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Line2D.Float;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.data.Range;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Year;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.PublicCloneable;











































































































public class PeriodAxis
  extends ValueAxis
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 8353295532075872069L;
  private RegularTimePeriod first;
  private RegularTimePeriod last;
  private TimeZone timeZone;
  private Locale locale;
  private Calendar calendar;
  private Class autoRangeTimePeriodClass;
  private Class majorTickTimePeriodClass;
  private boolean minorTickMarksVisible;
  private Class minorTickTimePeriodClass;
  private float minorTickMarkInsideLength = 0.0F;
  

  private float minorTickMarkOutsideLength = 2.0F;
  

  private transient Stroke minorTickMarkStroke = new BasicStroke(0.5F);
  

  private transient Paint minorTickMarkPaint = Color.black;
  


  private PeriodAxisLabelInfo[] labelInfo;
  



  public PeriodAxis(String label)
  {
    this(label, new Day(), new Day());
  }
  









  public PeriodAxis(String label, RegularTimePeriod first, RegularTimePeriod last)
  {
    this(label, first, last, TimeZone.getDefault(), Locale.getDefault());
  }
  











  /**
   * @deprecated
   */
  public PeriodAxis(String label, RegularTimePeriod first, RegularTimePeriod last, TimeZone timeZone)
  {
    this(label, first, last, timeZone, Locale.getDefault());
  }
  













  public PeriodAxis(String label, RegularTimePeriod first, RegularTimePeriod last, TimeZone timeZone, Locale locale)
  {
    super(label, null);
    if (timeZone == null) {
      throw new IllegalArgumentException("Null 'timeZone' argument.");
    }
    if (locale == null) {
      throw new IllegalArgumentException("Null 'locale' argument.");
    }
    this.first = first;
    this.last = last;
    this.timeZone = timeZone;
    this.locale = locale;
    calendar = Calendar.getInstance(timeZone, locale);
    this.first.peg(calendar);
    this.last.peg(calendar);
    autoRangeTimePeriodClass = first.getClass();
    majorTickTimePeriodClass = first.getClass();
    minorTickMarksVisible = false;
    minorTickTimePeriodClass = RegularTimePeriod.downsize(majorTickTimePeriodClass);
    
    setAutoRange(true);
    labelInfo = new PeriodAxisLabelInfo[2];
    labelInfo[0] = new PeriodAxisLabelInfo(Month.class, new SimpleDateFormat("MMM", locale));
    
    labelInfo[1] = new PeriodAxisLabelInfo(Year.class, new SimpleDateFormat("yyyy", locale));
  }
  





  public RegularTimePeriod getFirst()
  {
    return first;
  }
  





  public void setFirst(RegularTimePeriod first)
  {
    if (first == null) {
      throw new IllegalArgumentException("Null 'first' argument.");
    }
    this.first = first;
    this.first.peg(calendar);
    notifyListeners(new AxisChangeEvent(this));
  }
  




  public RegularTimePeriod getLast()
  {
    return last;
  }
  





  public void setLast(RegularTimePeriod last)
  {
    if (last == null) {
      throw new IllegalArgumentException("Null 'last' argument.");
    }
    this.last = last;
    this.last.peg(calendar);
    notifyListeners(new AxisChangeEvent(this));
  }
  





  public TimeZone getTimeZone()
  {
    return timeZone;
  }
  





  public void setTimeZone(TimeZone zone)
  {
    if (zone == null) {
      throw new IllegalArgumentException("Null 'zone' argument.");
    }
    timeZone = zone;
    calendar = Calendar.getInstance(zone, locale);
    first.peg(calendar);
    last.peg(calendar);
    notifyListeners(new AxisChangeEvent(this));
  }
  






  public Locale getLocale()
  {
    return locale;
  }
  





  public Class getAutoRangeTimePeriodClass()
  {
    return autoRangeTimePeriodClass;
  }
  






  public void setAutoRangeTimePeriodClass(Class c)
  {
    if (c == null) {
      throw new IllegalArgumentException("Null 'c' argument.");
    }
    autoRangeTimePeriodClass = c;
    notifyListeners(new AxisChangeEvent(this));
  }
  




  public Class getMajorTickTimePeriodClass()
  {
    return majorTickTimePeriodClass;
  }
  






  public void setMajorTickTimePeriodClass(Class c)
  {
    if (c == null) {
      throw new IllegalArgumentException("Null 'c' argument.");
    }
    majorTickTimePeriodClass = c;
    notifyListeners(new AxisChangeEvent(this));
  }
  





  public boolean isMinorTickMarksVisible()
  {
    return minorTickMarksVisible;
  }
  






  public void setMinorTickMarksVisible(boolean visible)
  {
    minorTickMarksVisible = visible;
    notifyListeners(new AxisChangeEvent(this));
  }
  




  public Class getMinorTickTimePeriodClass()
  {
    return minorTickTimePeriodClass;
  }
  






  public void setMinorTickTimePeriodClass(Class c)
  {
    if (c == null) {
      throw new IllegalArgumentException("Null 'c' argument.");
    }
    minorTickTimePeriodClass = c;
    notifyListeners(new AxisChangeEvent(this));
  }
  





  public Stroke getMinorTickMarkStroke()
  {
    return minorTickMarkStroke;
  }
  






  public void setMinorTickMarkStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    minorTickMarkStroke = stroke;
    notifyListeners(new AxisChangeEvent(this));
  }
  





  public Paint getMinorTickMarkPaint()
  {
    return minorTickMarkPaint;
  }
  






  public void setMinorTickMarkPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    minorTickMarkPaint = paint;
    notifyListeners(new AxisChangeEvent(this));
  }
  




  public float getMinorTickMarkInsideLength()
  {
    return minorTickMarkInsideLength;
  }
  





  public void setMinorTickMarkInsideLength(float length)
  {
    minorTickMarkInsideLength = length;
    notifyListeners(new AxisChangeEvent(this));
  }
  




  public float getMinorTickMarkOutsideLength()
  {
    return minorTickMarkOutsideLength;
  }
  





  public void setMinorTickMarkOutsideLength(float length)
  {
    minorTickMarkOutsideLength = length;
    notifyListeners(new AxisChangeEvent(this));
  }
  




  public PeriodAxisLabelInfo[] getLabelInfo()
  {
    return labelInfo;
  }
  





  public void setLabelInfo(PeriodAxisLabelInfo[] info)
  {
    labelInfo = info;
    notifyListeners(new AxisChangeEvent(this));
  }
  











  public void setRange(Range range, boolean turnOffAutoRange, boolean notify)
  {
    long upper = Math.round(range.getUpperBound());
    long lower = Math.round(range.getLowerBound());
    first = createInstance(autoRangeTimePeriodClass, new Date(lower), timeZone, locale);
    
    last = createInstance(autoRangeTimePeriodClass, new Date(upper), timeZone, locale);
    
    super.setRange(new Range(first.getFirstMillisecond(), last.getLastMillisecond() + 1.0D), turnOffAutoRange, notify);
  }
  





  public void configure()
  {
    if (isAutoRange()) {
      autoAdjustRange();
    }
  }
  















  public AxisSpace reserveSpace(Graphics2D g2, Plot plot, Rectangle2D plotArea, RectangleEdge edge, AxisSpace space)
  {
    if (space == null) {
      space = new AxisSpace();
    }
    

    if (!isVisible()) {
      return space;
    }
    

    double dimension = getFixedDimension();
    if (dimension > 0.0D) {
      space.ensureAtLeast(dimension, edge);
    }
    

    Rectangle2D labelEnclosure = getLabelEnclosure(g2, edge);
    double labelHeight = 0.0D;
    double labelWidth = 0.0D;
    double tickLabelBandsDimension = 0.0D;
    
    for (int i = 0; i < labelInfo.length; i++) {
      PeriodAxisLabelInfo info = labelInfo[i];
      FontMetrics fm = g2.getFontMetrics(info.getLabelFont());
      tickLabelBandsDimension += info.getPadding().extendHeight(fm.getHeight());
    }
    

    if (RectangleEdge.isTopOrBottom(edge)) {
      labelHeight = labelEnclosure.getHeight();
      space.add(labelHeight + tickLabelBandsDimension, edge);
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      labelWidth = labelEnclosure.getWidth();
      space.add(labelWidth + tickLabelBandsDimension, edge);
    }
    

    double tickMarkSpace = 0.0D;
    if (isTickMarksVisible()) {
      tickMarkSpace = getTickMarkOutsideLength();
    }
    if (minorTickMarksVisible) {
      tickMarkSpace = Math.max(tickMarkSpace, minorTickMarkOutsideLength);
    }
    
    space.add(tickMarkSpace, edge);
    return space;
  }
  
















  public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState)
  {
    AxisState axisState = new AxisState(cursor);
    if (isAxisLineVisible()) {
      drawAxisLine(g2, cursor, dataArea, edge);
    }
    if (isTickMarksVisible()) {
      drawTickMarks(g2, axisState, dataArea, edge);
    }
    if (isTickLabelsVisible()) {
      for (int band = 0; band < labelInfo.length; band++) {
        axisState = drawTickLabels(band, g2, axisState, dataArea, edge);
      }
    }
    


    axisState = drawLabel(getLabel(), g2, plotArea, dataArea, edge, axisState);
    
    return axisState;
  }
  










  protected void drawTickMarks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge)
  {
    if (RectangleEdge.isTopOrBottom(edge)) {
      drawTickMarksHorizontal(g2, state, dataArea, edge);
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      drawTickMarksVertical(g2, state, dataArea, edge);
    }
  }
  










  protected void drawTickMarksHorizontal(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge)
  {
    List ticks = new ArrayList();
    double x0 = dataArea.getX();
    double y0 = state.getCursor();
    double insideLength = getTickMarkInsideLength();
    double outsideLength = getTickMarkOutsideLength();
    RegularTimePeriod t = createInstance(majorTickTimePeriodClass, first.getStart(), getTimeZone(), locale);
    
    long t0 = t.getFirstMillisecond();
    Line2D inside = null;
    Line2D outside = null;
    long firstOnAxis = getFirst().getFirstMillisecond();
    long lastOnAxis = getLast().getLastMillisecond() + 1L;
    while (t0 <= lastOnAxis) {
      ticks.add(new NumberTick(new Double(t0), "", TextAnchor.CENTER, TextAnchor.CENTER, 0.0D));
      
      x0 = valueToJava2D(t0, dataArea, edge);
      if (edge == RectangleEdge.TOP) {
        inside = new Line2D.Double(x0, y0, x0, y0 + insideLength);
        outside = new Line2D.Double(x0, y0, x0, y0 - outsideLength);
      }
      else if (edge == RectangleEdge.BOTTOM) {
        inside = new Line2D.Double(x0, y0, x0, y0 - insideLength);
        outside = new Line2D.Double(x0, y0, x0, y0 + outsideLength);
      }
      if (t0 >= firstOnAxis) {
        g2.setPaint(getTickMarkPaint());
        g2.setStroke(getTickMarkStroke());
        g2.draw(inside);
        g2.draw(outside);
      }
      
      if (minorTickMarksVisible) {
        RegularTimePeriod tminor = createInstance(minorTickTimePeriodClass, new Date(t0), getTimeZone(), locale);
        

        long tt0 = tminor.getFirstMillisecond();
        
        while ((tt0 < t.getLastMillisecond()) && (tt0 < lastOnAxis)) {
          double xx0 = valueToJava2D(tt0, dataArea, edge);
          if (edge == RectangleEdge.TOP) {
            inside = new Line2D.Double(xx0, y0, xx0, y0 + minorTickMarkInsideLength);
            
            outside = new Line2D.Double(xx0, y0, xx0, y0 - minorTickMarkOutsideLength);

          }
          else if (edge == RectangleEdge.BOTTOM) {
            inside = new Line2D.Double(xx0, y0, xx0, y0 - minorTickMarkInsideLength);
            
            outside = new Line2D.Double(xx0, y0, xx0, y0 + minorTickMarkOutsideLength);
          }
          
          if (tt0 >= firstOnAxis) {
            g2.setPaint(minorTickMarkPaint);
            g2.setStroke(minorTickMarkStroke);
            g2.draw(inside);
            g2.draw(outside);
          }
          tminor = tminor.next();
          tminor.peg(calendar);
          tt0 = tminor.getFirstMillisecond();
        }
      }
      t = t.next();
      t.peg(calendar);
      t0 = t.getFirstMillisecond();
    }
    if (edge == RectangleEdge.TOP) {
      state.cursorUp(Math.max(outsideLength, minorTickMarkOutsideLength));

    }
    else if (edge == RectangleEdge.BOTTOM) {
      state.cursorDown(Math.max(outsideLength, minorTickMarkOutsideLength));
    }
    
    state.setTicks(ticks);
  }
  













  protected void drawTickMarksVertical(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge) {}
  













  protected AxisState drawTickLabels(int band, Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge)
  {
    double delta1 = 0.0D;
    FontMetrics fm = g2.getFontMetrics(labelInfo[band].getLabelFont());
    if (edge == RectangleEdge.BOTTOM) {
      delta1 = labelInfo[band].getPadding().calculateTopOutset(fm.getHeight());

    }
    else if (edge == RectangleEdge.TOP) {
      delta1 = labelInfo[band].getPadding().calculateBottomOutset(fm.getHeight());
    }
    
    state.moveCursor(delta1, edge);
    long axisMin = this.first.getFirstMillisecond();
    long axisMax = this.last.getLastMillisecond();
    g2.setFont(labelInfo[band].getLabelFont());
    g2.setPaint(labelInfo[band].getLabelPaint());
    

    RegularTimePeriod p1 = labelInfo[band].createInstance(new Date(axisMin), timeZone, locale);
    
    RegularTimePeriod p2 = labelInfo[band].createInstance(new Date(axisMax), timeZone, locale);
    
    String label1 = labelInfo[band].getDateFormat().format(new Date(p1.getMiddleMillisecond()));
    
    String label2 = labelInfo[band].getDateFormat().format(new Date(p2.getMiddleMillisecond()));
    
    Rectangle2D b1 = TextUtilities.getTextBounds(label1, g2, g2.getFontMetrics());
    
    Rectangle2D b2 = TextUtilities.getTextBounds(label2, g2, g2.getFontMetrics());
    
    double w = Math.max(b1.getWidth(), b2.getWidth());
    long ww = Math.round(java2DToValue(dataArea.getX() + w + 5.0D, dataArea, edge));
    
    if (isInverted()) {
      ww = axisMax - ww;
    }
    else {
      ww -= axisMin;
    }
    long length = p1.getLastMillisecond() - p1.getFirstMillisecond();
    
    int periods = (int)(ww / length) + 1;
    
    RegularTimePeriod p = labelInfo[band].createInstance(new Date(axisMin), timeZone, locale);
    
    Rectangle2D b = null;
    long lastXX = 0L;
    float y = (float)state.getCursor();
    TextAnchor anchor = TextAnchor.TOP_CENTER;
    float yDelta = (float)b1.getHeight();
    if (edge == RectangleEdge.TOP) {
      anchor = TextAnchor.BOTTOM_CENTER;
      yDelta = -yDelta;
    }
    while (p.getFirstMillisecond() <= axisMax) {
      float x = (float)valueToJava2D(p.getMiddleMillisecond(), dataArea, edge);
      
      DateFormat df = labelInfo[band].getDateFormat();
      String label = df.format(new Date(p.getMiddleMillisecond()));
      long first = p.getFirstMillisecond();
      long last = p.getLastMillisecond();
      if (last > axisMax)
      {

        Rectangle2D bb = TextUtilities.getTextBounds(label, g2, g2.getFontMetrics());
        
        if (x + bb.getWidth() / 2.0D > dataArea.getMaxX()) {
          float xstart = (float)valueToJava2D(Math.max(first, axisMin), dataArea, edge);
          
          if (bb.getWidth() < dataArea.getMaxX() - xstart) {
            x = ((float)dataArea.getMaxX() + xstart) / 2.0F;
          }
          else {
            label = null;
          }
        }
      }
      if (first < axisMin)
      {

        Rectangle2D bb = TextUtilities.getTextBounds(label, g2, g2.getFontMetrics());
        
        if (x - bb.getWidth() / 2.0D < dataArea.getX()) {
          float xlast = (float)valueToJava2D(Math.min(last, axisMax), dataArea, edge);
          
          if (bb.getWidth() < xlast - dataArea.getX()) {
            x = (xlast + (float)dataArea.getX()) / 2.0F;
          }
          else {
            label = null;
          }
        }
      }
      
      if (label != null) {
        g2.setPaint(labelInfo[band].getLabelPaint());
        b = TextUtilities.drawAlignedString(label, g2, x, y, anchor);
      }
      if ((lastXX > 0L) && 
        (labelInfo[band].getDrawDividers())) {
        long nextXX = p.getFirstMillisecond();
        long mid = (lastXX + nextXX) / 2L;
        float mid2d = (float)valueToJava2D(mid, dataArea, edge);
        g2.setStroke(labelInfo[band].getDividerStroke());
        g2.setPaint(labelInfo[band].getDividerPaint());
        g2.draw(new Line2D.Float(mid2d, y, mid2d, y + yDelta));
      }
      
      lastXX = last;
      for (int i = 0; i < periods; i++) {
        p = p.next();
      }
      p.peg(calendar);
    }
    double used = 0.0D;
    if (b != null) {
      used = b.getHeight();
      
      if (edge == RectangleEdge.BOTTOM) {
        used += labelInfo[band].getPadding().calculateBottomOutset(fm.getHeight());

      }
      else if (edge == RectangleEdge.TOP) {
        used += labelInfo[band].getPadding().calculateTopOutset(fm.getHeight());
      }
    }
    
    state.moveCursor(used, edge);
    return state;
  }
  











  public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge)
  {
    return Collections.EMPTY_LIST;
  }
  













  public double valueToJava2D(double value, Rectangle2D area, RectangleEdge edge)
  {
    double result = NaN.0D;
    double axisMin = first.getFirstMillisecond();
    double axisMax = last.getLastMillisecond();
    if (RectangleEdge.isTopOrBottom(edge)) {
      double minX = area.getX();
      double maxX = area.getMaxX();
      if (isInverted()) {
        result = maxX + (value - axisMin) / (axisMax - axisMin) * (minX - maxX);
      }
      else
      {
        result = minX + (value - axisMin) / (axisMax - axisMin) * (maxX - minX);
      }
      
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      double minY = area.getMinY();
      double maxY = area.getMaxY();
      if (isInverted()) {
        result = minY + (value - axisMin) / (axisMax - axisMin) * (maxY - minY);
      }
      else
      {
        result = maxY - (value - axisMin) / (axisMax - axisMin) * (maxY - minY);
      }
    }
    
    return result;
  }
  












  public double java2DToValue(double java2DValue, Rectangle2D area, RectangleEdge edge)
  {
    double result = NaN.0D;
    double min = 0.0D;
    double max = 0.0D;
    double axisMin = first.getFirstMillisecond();
    double axisMax = last.getLastMillisecond();
    if (RectangleEdge.isTopOrBottom(edge)) {
      min = area.getX();
      max = area.getMaxX();
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      min = area.getMaxY();
      max = area.getY();
    }
    if (isInverted()) {
      result = axisMax - (java2DValue - min) / (max - min) * (axisMax - axisMin);
    }
    else
    {
      result = axisMin + (java2DValue - min) / (max - min) * (axisMax - axisMin);
    }
    
    return result;
  }
  



  protected void autoAdjustRange()
  {
    Plot plot = getPlot();
    if (plot == null) {
      return;
    }
    
    if ((plot instanceof ValueAxisPlot)) {
      ValueAxisPlot vap = (ValueAxisPlot)plot;
      
      Range r = vap.getDataRange(this);
      if (r == null) {
        r = getDefaultAutoRange();
      }
      
      long upper = Math.round(r.getUpperBound());
      long lower = Math.round(r.getLowerBound());
      first = createInstance(autoRangeTimePeriodClass, new Date(lower), timeZone, locale);
      
      last = createInstance(autoRangeTimePeriodClass, new Date(upper), timeZone, locale);
      
      setRange(r, false, false);
    }
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof PeriodAxis)) {
      return false;
    }
    PeriodAxis that = (PeriodAxis)obj;
    if (!first.equals(first)) {
      return false;
    }
    if (!last.equals(last)) {
      return false;
    }
    if (!timeZone.equals(timeZone)) {
      return false;
    }
    if (!locale.equals(locale)) {
      return false;
    }
    if (!autoRangeTimePeriodClass.equals(autoRangeTimePeriodClass))
    {
      return false;
    }
    if (isMinorTickMarksVisible() != that.isMinorTickMarksVisible()) {
      return false;
    }
    if (!majorTickTimePeriodClass.equals(majorTickTimePeriodClass))
    {
      return false;
    }
    if (!minorTickTimePeriodClass.equals(minorTickTimePeriodClass))
    {
      return false;
    }
    if (!minorTickMarkPaint.equals(minorTickMarkPaint)) {
      return false;
    }
    if (!minorTickMarkStroke.equals(minorTickMarkStroke)) {
      return false;
    }
    if (!Arrays.equals(labelInfo, labelInfo)) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    if (getLabel() != null) {
      return getLabel().hashCode();
    }
    
    return 0;
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    PeriodAxis clone = (PeriodAxis)super.clone();
    timeZone = ((TimeZone)timeZone.clone());
    labelInfo = new PeriodAxisLabelInfo[labelInfo.length];
    for (int i = 0; i < labelInfo.length; i++) {
      labelInfo[i] = labelInfo[i];
    }
    
    return clone;
  }
  












  private RegularTimePeriod createInstance(Class periodClass, Date millisecond, TimeZone zone, Locale locale)
  {
    RegularTimePeriod result = null;
    try {
      Constructor c = periodClass.getDeclaredConstructor(new Class[] { Date.class, TimeZone.class, Locale.class });
      
      result = (RegularTimePeriod)c.newInstance(new Object[] { millisecond, zone, locale });
    }
    catch (Exception e)
    {
      try {
        Constructor c = periodClass.getDeclaredConstructor(new Class[] { Date.class });
        
        result = (RegularTimePeriod)c.newInstance(new Object[] { millisecond });
      }
      catch (Exception e2) {}
    }
    


    return result;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeStroke(minorTickMarkStroke, stream);
    SerialUtilities.writePaint(minorTickMarkPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    minorTickMarkStroke = SerialUtilities.readStroke(stream);
    minorTickMarkPaint = SerialUtilities.readPaint(stream);
  }
}
