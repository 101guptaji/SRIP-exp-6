package org.jfree.chart.axis;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.data.Range;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;






















































































































public abstract class ValueAxis
  extends Axis
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 3698345477322391456L;
  public static final Range DEFAULT_RANGE = new Range(0.0D, 1.0D);
  



  public static final boolean DEFAULT_AUTO_RANGE = true;
  



  public static final boolean DEFAULT_INVERTED = false;
  



  public static final double DEFAULT_AUTO_RANGE_MINIMUM_SIZE = 1.0E-8D;
  



  public static final double DEFAULT_LOWER_MARGIN = 0.05D;
  



  public static final double DEFAULT_UPPER_MARGIN = 0.05D;
  



  /**
   * @deprecated
   */
  public static final double DEFAULT_LOWER_BOUND = 0.0D;
  



  /**
   * @deprecated
   */
  public static final double DEFAULT_UPPER_BOUND = 1.0D;
  



  public static final boolean DEFAULT_AUTO_TICK_UNIT_SELECTION = true;
  



  public static final int MAXIMUM_TICK_COUNT = 500;
  



  private boolean positiveArrowVisible;
  



  private boolean negativeArrowVisible;
  



  private transient Shape upArrow;
  



  private transient Shape downArrow;
  


  private transient Shape leftArrow;
  


  private transient Shape rightArrow;
  


  private boolean inverted;
  


  private Range range;
  


  private boolean autoRange;
  


  private double autoRangeMinimumSize;
  


  private Range defaultAutoRange;
  


  private double upperMargin;
  


  private double lowerMargin;
  


  private double fixedAutoRange;
  


  private boolean autoTickUnitSelection;
  


  private TickUnitSource standardTickUnits;
  


  private int autoTickIndex;
  


  private int minorTickCount;
  


  private boolean verticalTickLabels;
  



  protected ValueAxis(String label, TickUnitSource standardTickUnits)
  {
    super(label);
    
    positiveArrowVisible = false;
    negativeArrowVisible = false;
    
    range = DEFAULT_RANGE;
    autoRange = true;
    defaultAutoRange = DEFAULT_RANGE;
    
    inverted = false;
    autoRangeMinimumSize = 1.0E-8D;
    
    lowerMargin = 0.05D;
    upperMargin = 0.05D;
    
    fixedAutoRange = 0.0D;
    
    autoTickUnitSelection = true;
    this.standardTickUnits = standardTickUnits;
    
    Polygon p1 = new Polygon();
    p1.addPoint(0, 0);
    p1.addPoint(-2, 2);
    p1.addPoint(2, 2);
    
    upArrow = p1;
    
    Polygon p2 = new Polygon();
    p2.addPoint(0, 0);
    p2.addPoint(-2, -2);
    p2.addPoint(2, -2);
    
    downArrow = p2;
    
    Polygon p3 = new Polygon();
    p3.addPoint(0, 0);
    p3.addPoint(-2, -2);
    p3.addPoint(-2, 2);
    
    rightArrow = p3;
    
    Polygon p4 = new Polygon();
    p4.addPoint(0, 0);
    p4.addPoint(2, -2);
    p4.addPoint(2, 2);
    
    leftArrow = p4;
    
    verticalTickLabels = false;
    minorTickCount = 0;
  }
  








  public boolean isVerticalTickLabels()
  {
    return verticalTickLabels;
  }
  









  public void setVerticalTickLabels(boolean flag)
  {
    if (verticalTickLabels != flag) {
      verticalTickLabels = flag;
      notifyListeners(new AxisChangeEvent(this));
    }
  }
  







  public boolean isPositiveArrowVisible()
  {
    return positiveArrowVisible;
  }
  








  public void setPositiveArrowVisible(boolean visible)
  {
    positiveArrowVisible = visible;
    notifyListeners(new AxisChangeEvent(this));
  }
  







  public boolean isNegativeArrowVisible()
  {
    return negativeArrowVisible;
  }
  








  public void setNegativeArrowVisible(boolean visible)
  {
    negativeArrowVisible = visible;
    notifyListeners(new AxisChangeEvent(this));
  }
  







  public Shape getUpArrow()
  {
    return upArrow;
  }
  








  public void setUpArrow(Shape arrow)
  {
    if (arrow == null) {
      throw new IllegalArgumentException("Null 'arrow' argument.");
    }
    upArrow = arrow;
    notifyListeners(new AxisChangeEvent(this));
  }
  







  public Shape getDownArrow()
  {
    return downArrow;
  }
  








  public void setDownArrow(Shape arrow)
  {
    if (arrow == null) {
      throw new IllegalArgumentException("Null 'arrow' argument.");
    }
    downArrow = arrow;
    notifyListeners(new AxisChangeEvent(this));
  }
  







  public Shape getLeftArrow()
  {
    return leftArrow;
  }
  








  public void setLeftArrow(Shape arrow)
  {
    if (arrow == null) {
      throw new IllegalArgumentException("Null 'arrow' argument.");
    }
    leftArrow = arrow;
    notifyListeners(new AxisChangeEvent(this));
  }
  







  public Shape getRightArrow()
  {
    return rightArrow;
  }
  








  public void setRightArrow(Shape arrow)
  {
    if (arrow == null) {
      throw new IllegalArgumentException("Null 'arrow' argument.");
    }
    rightArrow = arrow;
    notifyListeners(new AxisChangeEvent(this));
  }
  








  protected void drawAxisLine(Graphics2D g2, double cursor, Rectangle2D dataArea, RectangleEdge edge)
  {
    Line2D axisLine = null;
    if (edge == RectangleEdge.TOP) {
      axisLine = new Line2D.Double(dataArea.getX(), cursor, dataArea.getMaxX(), cursor);

    }
    else if (edge == RectangleEdge.BOTTOM) {
      axisLine = new Line2D.Double(dataArea.getX(), cursor, dataArea.getMaxX(), cursor);

    }
    else if (edge == RectangleEdge.LEFT) {
      axisLine = new Line2D.Double(cursor, dataArea.getY(), cursor, dataArea.getMaxY());

    }
    else if (edge == RectangleEdge.RIGHT) {
      axisLine = new Line2D.Double(cursor, dataArea.getY(), cursor, dataArea.getMaxY());
    }
    
    g2.setPaint(getAxisLinePaint());
    g2.setStroke(getAxisLineStroke());
    g2.draw(axisLine);
    
    boolean drawUpOrRight = false;
    boolean drawDownOrLeft = false;
    if (positiveArrowVisible) {
      if (inverted) {
        drawDownOrLeft = true;
      }
      else {
        drawUpOrRight = true;
      }
    }
    if (negativeArrowVisible) {
      if (inverted) {
        drawUpOrRight = true;
      }
      else {
        drawDownOrLeft = true;
      }
    }
    if (drawUpOrRight) {
      double x = 0.0D;
      double y = 0.0D;
      Shape arrow = null;
      if ((edge == RectangleEdge.TOP) || (edge == RectangleEdge.BOTTOM)) {
        x = dataArea.getMaxX();
        y = cursor;
        arrow = rightArrow;
      }
      else if ((edge == RectangleEdge.LEFT) || (edge == RectangleEdge.RIGHT))
      {
        x = cursor;
        y = dataArea.getMinY();
        arrow = upArrow;
      }
      

      AffineTransform transformer = new AffineTransform();
      transformer.setToTranslation(x, y);
      Shape shape = transformer.createTransformedShape(arrow);
      g2.fill(shape);
      g2.draw(shape);
    }
    
    if (drawDownOrLeft) {
      double x = 0.0D;
      double y = 0.0D;
      Shape arrow = null;
      if ((edge == RectangleEdge.TOP) || (edge == RectangleEdge.BOTTOM)) {
        x = dataArea.getMinX();
        y = cursor;
        arrow = leftArrow;
      }
      else if ((edge == RectangleEdge.LEFT) || (edge == RectangleEdge.RIGHT))
      {
        x = cursor;
        y = dataArea.getMaxY();
        arrow = downArrow;
      }
      

      AffineTransform transformer = new AffineTransform();
      transformer.setToTranslation(x, y);
      Shape shape = transformer.createTransformedShape(arrow);
      g2.fill(shape);
      g2.draw(shape);
    }
  }
  














  protected float[] calculateAnchorPoint(ValueTick tick, double cursor, Rectangle2D dataArea, RectangleEdge edge)
  {
    RectangleInsets insets = getTickLabelInsets();
    float[] result = new float[2];
    if (edge == RectangleEdge.TOP) {
      result[0] = ((float)valueToJava2D(tick.getValue(), dataArea, edge));
      result[1] = ((float)(cursor - insets.getBottom() - 2.0D));
    }
    else if (edge == RectangleEdge.BOTTOM) {
      result[0] = ((float)valueToJava2D(tick.getValue(), dataArea, edge));
      result[1] = ((float)(cursor + insets.getTop() + 2.0D));
    }
    else if (edge == RectangleEdge.LEFT) {
      result[0] = ((float)(cursor - insets.getLeft() - 2.0D));
      result[1] = ((float)valueToJava2D(tick.getValue(), dataArea, edge));
    }
    else if (edge == RectangleEdge.RIGHT) {
      result[0] = ((float)(cursor + insets.getRight() + 2.0D));
      result[1] = ((float)valueToJava2D(tick.getValue(), dataArea, edge));
    }
    return result;
  }
  













  protected AxisState drawTickMarksAndLabels(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge)
  {
    AxisState state = new AxisState(cursor);
    
    if (isAxisLineVisible()) {
      drawAxisLine(g2, cursor, dataArea, edge);
    }
    
    List ticks = refreshTicks(g2, state, dataArea, edge);
    state.setTicks(ticks);
    g2.setFont(getTickLabelFont());
    Iterator iterator = ticks.iterator();
    while (iterator.hasNext()) {
      ValueTick tick = (ValueTick)iterator.next();
      if (isTickLabelsVisible()) {
        g2.setPaint(getTickLabelPaint());
        float[] anchorPoint = calculateAnchorPoint(tick, cursor, dataArea, edge);
        
        TextUtilities.drawRotatedString(tick.getText(), g2, anchorPoint[0], anchorPoint[1], tick.getTextAnchor(), tick.getAngle(), tick.getRotationAnchor());
      }
      


      if (((isTickMarksVisible()) && (tick.getTickType().equals(TickType.MAJOR))) || ((isMinorTickMarksVisible()) && (tick.getTickType().equals(TickType.MINOR))))
      {


        double ol = tick.getTickType().equals(TickType.MINOR) ? getMinorTickMarkOutsideLength() : getTickMarkOutsideLength();
        

        double il = tick.getTickType().equals(TickType.MINOR) ? getMinorTickMarkInsideLength() : getTickMarkInsideLength();
        

        float xx = (float)valueToJava2D(tick.getValue(), dataArea, edge);
        
        Line2D mark = null;
        g2.setStroke(getTickMarkStroke());
        g2.setPaint(getTickMarkPaint());
        if (edge == RectangleEdge.LEFT) {
          mark = new Line2D.Double(cursor - ol, xx, cursor + il, xx);
        }
        else if (edge == RectangleEdge.RIGHT) {
          mark = new Line2D.Double(cursor + ol, xx, cursor - il, xx);
        }
        else if (edge == RectangleEdge.TOP) {
          mark = new Line2D.Double(xx, cursor - ol, xx, cursor + il);
        }
        else if (edge == RectangleEdge.BOTTOM) {
          mark = new Line2D.Double(xx, cursor + ol, xx, cursor - il);
        }
        g2.draw(mark);
      }
    }
    


    double used = 0.0D;
    if (isTickLabelsVisible()) {
      if (edge == RectangleEdge.LEFT) {
        used += findMaximumTickLabelWidth(ticks, g2, plotArea, isVerticalTickLabels());
        
        state.cursorLeft(used);
      }
      else if (edge == RectangleEdge.RIGHT) {
        used = findMaximumTickLabelWidth(ticks, g2, plotArea, isVerticalTickLabels());
        
        state.cursorRight(used);
      }
      else if (edge == RectangleEdge.TOP) {
        used = findMaximumTickLabelHeight(ticks, g2, plotArea, isVerticalTickLabels());
        
        state.cursorUp(used);
      }
      else if (edge == RectangleEdge.BOTTOM) {
        used = findMaximumTickLabelHeight(ticks, g2, plotArea, isVerticalTickLabels());
        
        state.cursorDown(used);
      }
    }
    
    return state;
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
    

    double tickLabelHeight = 0.0D;
    double tickLabelWidth = 0.0D;
    if (isTickLabelsVisible()) {
      g2.setFont(getTickLabelFont());
      List ticks = refreshTicks(g2, new AxisState(), plotArea, edge);
      if (RectangleEdge.isTopOrBottom(edge)) {
        tickLabelHeight = findMaximumTickLabelHeight(ticks, g2, plotArea, isVerticalTickLabels());

      }
      else if (RectangleEdge.isLeftOrRight(edge)) {
        tickLabelWidth = findMaximumTickLabelWidth(ticks, g2, plotArea, isVerticalTickLabels());
      }
    }
    


    Rectangle2D labelEnclosure = getLabelEnclosure(g2, edge);
    double labelHeight = 0.0D;
    double labelWidth = 0.0D;
    if (RectangleEdge.isTopOrBottom(edge)) {
      labelHeight = labelEnclosure.getHeight();
      space.add(labelHeight + tickLabelHeight, edge);
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      labelWidth = labelEnclosure.getWidth();
      space.add(labelWidth + tickLabelWidth, edge);
    }
    
    return space;
  }
  















  protected double findMaximumTickLabelHeight(List ticks, Graphics2D g2, Rectangle2D drawArea, boolean vertical)
  {
    RectangleInsets insets = getTickLabelInsets();
    Font font = getTickLabelFont();
    double maxHeight = 0.0D;
    if (vertical) {
      FontMetrics fm = g2.getFontMetrics(font);
      Iterator iterator = ticks.iterator();
      while (iterator.hasNext()) {
        Tick tick = (Tick)iterator.next();
        Rectangle2D labelBounds = TextUtilities.getTextBounds(tick.getText(), g2, fm);
        
        if (labelBounds.getWidth() + insets.getTop() + insets.getBottom() > maxHeight)
        {
          maxHeight = labelBounds.getWidth() + insets.getTop() + insets.getBottom();
        }
      }
    }
    else
    {
      LineMetrics metrics = font.getLineMetrics("ABCxyz", g2.getFontRenderContext());
      
      maxHeight = metrics.getHeight() + insets.getTop() + insets.getBottom();
    }
    
    return maxHeight;
  }
  















  protected double findMaximumTickLabelWidth(List ticks, Graphics2D g2, Rectangle2D drawArea, boolean vertical)
  {
    RectangleInsets insets = getTickLabelInsets();
    Font font = getTickLabelFont();
    double maxWidth = 0.0D;
    if (!vertical) {
      FontMetrics fm = g2.getFontMetrics(font);
      Iterator iterator = ticks.iterator();
      while (iterator.hasNext()) {
        Tick tick = (Tick)iterator.next();
        Rectangle2D labelBounds = TextUtilities.getTextBounds(tick.getText(), g2, fm);
        
        if (labelBounds.getWidth() + insets.getLeft() + insets.getRight() > maxWidth)
        {
          maxWidth = labelBounds.getWidth() + insets.getLeft() + insets.getRight();
        }
      }
    }
    else
    {
      LineMetrics metrics = font.getLineMetrics("ABCxyz", g2.getFontRenderContext());
      
      maxWidth = metrics.getHeight() + insets.getTop() + insets.getBottom();
    }
    
    return maxWidth;
  }
  











  public boolean isInverted()
  {
    return inverted;
  }
  








  public void setInverted(boolean flag)
  {
    if (inverted != flag) {
      inverted = flag;
      notifyListeners(new AxisChangeEvent(this));
    }
  }
  








  public boolean isAutoRange()
  {
    return autoRange;
  }
  








  public void setAutoRange(boolean auto)
  {
    setAutoRange(auto, true);
  }
  








  protected void setAutoRange(boolean auto, boolean notify)
  {
    if (autoRange != auto) {
      autoRange = auto;
      if (autoRange) {
        autoAdjustRange();
      }
      if (notify) {
        notifyListeners(new AxisChangeEvent(this));
      }
    }
  }
  







  public double getAutoRangeMinimumSize()
  {
    return autoRangeMinimumSize;
  }
  







  public void setAutoRangeMinimumSize(double size)
  {
    setAutoRangeMinimumSize(size, true);
  }
  









  public void setAutoRangeMinimumSize(double size, boolean notify)
  {
    if (size <= 0.0D) {
      throw new IllegalArgumentException("NumberAxis.setAutoRangeMinimumSize(double): must be > 0.0.");
    }
    
    if (autoRangeMinimumSize != size) {
      autoRangeMinimumSize = size;
      if (autoRange) {
        autoAdjustRange();
      }
      if (notify) {
        notifyListeners(new AxisChangeEvent(this));
      }
    }
  }
  









  public Range getDefaultAutoRange()
  {
    return defaultAutoRange;
  }
  









  public void setDefaultAutoRange(Range range)
  {
    if (range == null) {
      throw new IllegalArgumentException("Null 'range' argument.");
    }
    defaultAutoRange = range;
    notifyListeners(new AxisChangeEvent(this));
  }
  









  public double getLowerMargin()
  {
    return lowerMargin;
  }
  










  public void setLowerMargin(double margin)
  {
    lowerMargin = margin;
    if (isAutoRange()) {
      autoAdjustRange();
    }
    notifyListeners(new AxisChangeEvent(this));
  }
  









  public double getUpperMargin()
  {
    return upperMargin;
  }
  










  public void setUpperMargin(double margin)
  {
    upperMargin = margin;
    if (isAutoRange()) {
      autoAdjustRange();
    }
    notifyListeners(new AxisChangeEvent(this));
  }
  






  public double getFixedAutoRange()
  {
    return fixedAutoRange;
  }
  






  public void setFixedAutoRange(double length)
  {
    fixedAutoRange = length;
    if (isAutoRange()) {
      autoAdjustRange();
    }
    notifyListeners(new AxisChangeEvent(this));
  }
  






  public double getLowerBound()
  {
    return range.getLowerBound();
  }
  







  public void setLowerBound(double min)
  {
    if (range.getUpperBound() > min) {
      setRange(new Range(min, range.getUpperBound()));
    }
    else {
      setRange(new Range(min, min + 1.0D));
    }
  }
  






  public double getUpperBound()
  {
    return range.getUpperBound();
  }
  







  public void setUpperBound(double max)
  {
    if (range.getLowerBound() < max) {
      setRange(new Range(range.getLowerBound(), max));
    }
    else {
      setRange(max - 1.0D, max);
    }
  }
  






  public Range getRange()
  {
    return range;
  }
  









  public void setRange(Range range)
  {
    setRange(range, true, true);
  }
  













  public void setRange(Range range, boolean turnOffAutoRange, boolean notify)
  {
    if (range == null) {
      throw new IllegalArgumentException("Null 'range' argument.");
    }
    if (turnOffAutoRange) {
      autoRange = false;
    }
    this.range = range;
    if (notify) {
      notifyListeners(new AxisChangeEvent(this));
    }
  }
  










  public void setRange(double lower, double upper)
  {
    setRange(new Range(lower, upper));
  }
  






  public void setRangeWithMargins(Range range)
  {
    setRangeWithMargins(range, true, true);
  }
  













  public void setRangeWithMargins(Range range, boolean turnOffAutoRange, boolean notify)
  {
    if (range == null) {
      throw new IllegalArgumentException("Null 'range' argument.");
    }
    setRange(Range.expand(range, getLowerMargin(), getUpperMargin()), turnOffAutoRange, notify);
  }
  








  public void setRangeWithMargins(double lower, double upper)
  {
    setRangeWithMargins(new Range(lower, upper));
  }
  






  public void setRangeAboutValue(double value, double length)
  {
    setRange(new Range(value - length / 2.0D, value + length / 2.0D));
  }
  








  public boolean isAutoTickUnitSelection()
  {
    return autoTickUnitSelection;
  }
  








  public void setAutoTickUnitSelection(boolean flag)
  {
    setAutoTickUnitSelection(flag, true);
  }
  









  public void setAutoTickUnitSelection(boolean flag, boolean notify)
  {
    if (autoTickUnitSelection != flag) {
      autoTickUnitSelection = flag;
      if (notify) {
        notifyListeners(new AxisChangeEvent(this));
      }
    }
  }
  






  public TickUnitSource getStandardTickUnits()
  {
    return standardTickUnits;
  }
  











  public void setStandardTickUnits(TickUnitSource source)
  {
    standardTickUnits = source;
    notifyListeners(new AxisChangeEvent(this));
  }
  








  public int getMinorTickCount()
  {
    return minorTickCount;
  }
  









  public void setMinorTickCount(int count)
  {
    minorTickCount = count;
    notifyListeners(new AxisChangeEvent(this));
  }
  













  public abstract double valueToJava2D(double paramDouble, Rectangle2D paramRectangle2D, RectangleEdge paramRectangleEdge);
  












  public double lengthToJava2D(double length, Rectangle2D area, RectangleEdge edge)
  {
    double zero = valueToJava2D(0.0D, area, edge);
    double l = valueToJava2D(length, area, edge);
    return Math.abs(l - zero);
  }
  









  public abstract double java2DToValue(double paramDouble, Rectangle2D paramRectangle2D, RectangleEdge paramRectangleEdge);
  









  protected abstract void autoAdjustRange();
  








  public void centerRange(double value)
  {
    double central = range.getCentralValue();
    Range adjusted = new Range(range.getLowerBound() + value - central, range.getUpperBound() + value - central);
    
    setRange(adjusted);
  }
  












  public void resizeRange(double percent)
  {
    resizeRange(percent, range.getCentralValue());
  }
  












  public void resizeRange(double percent, double anchorValue)
  {
    if (percent > 0.0D) {
      double halfLength = range.getLength() * percent / 2.0D;
      Range adjusted = new Range(anchorValue - halfLength, anchorValue + halfLength);
      
      setRange(adjusted);
    }
    else {
      setAutoRange(true);
    }
  }
  














  public void resizeRange2(double percent, double anchorValue)
  {
    if (percent > 0.0D) {
      double left = anchorValue - getLowerBound();
      double right = getUpperBound() - anchorValue;
      Range adjusted = new Range(anchorValue - left * percent, anchorValue + right * percent);
      
      setRange(adjusted);
    }
    else {
      setAutoRange(true);
    }
  }
  





  public void zoomRange(double lowerPercent, double upperPercent)
  {
    double start = range.getLowerBound();
    double length = range.getLength();
    Range adjusted = null;
    if (isInverted()) {
      adjusted = new Range(start + length * (1.0D - upperPercent), start + length * (1.0D - lowerPercent));
    }
    else
    {
      adjusted = new Range(start + length * lowerPercent, start + length * upperPercent);
    }
    
    setRange(adjusted);
  }
  






  public void pan(double percent)
  {
    Range range = getRange();
    double length = range.getLength();
    double adj = length * percent;
    double lower = range.getLowerBound() + adj;
    double upper = range.getUpperBound() + adj;
    setRange(lower, upper);
  }
  






  protected int getAutoTickIndex()
  {
    return autoTickIndex;
  }
  






  protected void setAutoTickIndex(int index)
  {
    autoTickIndex = index;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ValueAxis)) {
      return false;
    }
    ValueAxis that = (ValueAxis)obj;
    if (positiveArrowVisible != positiveArrowVisible) {
      return false;
    }
    if (negativeArrowVisible != negativeArrowVisible) {
      return false;
    }
    if (inverted != inverted) {
      return false;
    }
    
    if ((!autoRange) && (!ObjectUtilities.equal(range, range))) {
      return false;
    }
    if (autoRange != autoRange) {
      return false;
    }
    if (autoRangeMinimumSize != autoRangeMinimumSize) {
      return false;
    }
    if (!defaultAutoRange.equals(defaultAutoRange)) {
      return false;
    }
    if (upperMargin != upperMargin) {
      return false;
    }
    if (lowerMargin != lowerMargin) {
      return false;
    }
    if (fixedAutoRange != fixedAutoRange) {
      return false;
    }
    if (autoTickUnitSelection != autoTickUnitSelection) {
      return false;
    }
    if (!ObjectUtilities.equal(standardTickUnits, standardTickUnits))
    {
      return false;
    }
    if (verticalTickLabels != verticalTickLabels) {
      return false;
    }
    if (minorTickCount != minorTickCount) {
      return false;
    }
    return super.equals(obj);
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    ValueAxis clone = (ValueAxis)super.clone();
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeShape(upArrow, stream);
    SerialUtilities.writeShape(downArrow, stream);
    SerialUtilities.writeShape(leftArrow, stream);
    SerialUtilities.writeShape(rightArrow, stream);
  }
  








  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    upArrow = SerialUtilities.readShape(stream);
    downArrow = SerialUtilities.readShape(stream);
    leftArrow = SerialUtilities.readShape(stream);
    rightArrow = SerialUtilities.readShape(stream);
  }
}
