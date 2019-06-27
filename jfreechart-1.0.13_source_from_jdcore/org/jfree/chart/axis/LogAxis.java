package org.jfree.chart.axis;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.chart.util.LogFormat;
import org.jfree.data.Range;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;



































































public class LogAxis
  extends ValueAxis
{
  private double base = 10.0D;
  

  private double baseLog = Math.log(10.0D);
  

  private double smallestValue = 1.0E-100D;
  

  private NumberTickUnit tickUnit;
  

  private NumberFormat numberFormatOverride;
  


  public LogAxis()
  {
    this(null);
  }
  




  public LogAxis(String label)
  {
    super(label, createLogTickUnits(Locale.getDefault()));
    setDefaultAutoRange(new Range(0.01D, 1.0D));
    tickUnit = new NumberTickUnit(1.0D, new DecimalFormat("0.#"), 9);
  }
  






  public double getBase()
  {
    return base;
  }
  







  public void setBase(double base)
  {
    if (base <= 1.0D) {
      throw new IllegalArgumentException("Requires 'base' > 1.0.");
    }
    this.base = base;
    baseLog = Math.log(base);
    notifyListeners(new AxisChangeEvent(this));
  }
  






  public double getSmallestValue()
  {
    return smallestValue;
  }
  







  public void setSmallestValue(double value)
  {
    if (value <= 0.0D) {
      throw new IllegalArgumentException("Requires 'value' > 0.0.");
    }
    smallestValue = value;
    notifyListeners(new AxisChangeEvent(this));
  }
  






  public NumberTickUnit getTickUnit()
  {
    return tickUnit;
  }
  











  public void setTickUnit(NumberTickUnit unit)
  {
    setTickUnit(unit, true, true);
  }
  














  public void setTickUnit(NumberTickUnit unit, boolean notify, boolean turnOffAutoSelect)
  {
    if (unit == null) {
      throw new IllegalArgumentException("Null 'unit' argument.");
    }
    tickUnit = unit;
    if (turnOffAutoSelect) {
      setAutoTickUnitSelection(false, false);
    }
    if (notify) {
      notifyListeners(new AxisChangeEvent(this));
    }
  }
  








  public NumberFormat getNumberFormatOverride()
  {
    return numberFormatOverride;
  }
  







  public void setNumberFormatOverride(NumberFormat formatter)
  {
    numberFormatOverride = formatter;
    notifyListeners(new AxisChangeEvent(this));
  }
  









  public double calculateLog(double value)
  {
    return Math.log(value) / baseLog;
  }
  









  public double calculateValue(double log)
  {
    return Math.pow(base, log);
  }
  











  public double java2DToValue(double java2DValue, Rectangle2D area, RectangleEdge edge)
  {
    Range range = getRange();
    double axisMin = calculateLog(range.getLowerBound());
    double axisMax = calculateLog(range.getUpperBound());
    
    double min = 0.0D;
    double max = 0.0D;
    if (RectangleEdge.isTopOrBottom(edge)) {
      min = area.getX();
      max = area.getMaxX();
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      min = area.getMaxY();
      max = area.getY();
    }
    double log = 0.0D;
    if (isInverted()) {
      log = axisMax - (java2DValue - min) / (max - min) * (axisMax - axisMin);
    }
    else
    {
      log = axisMin + (java2DValue - min) / (max - min) * (axisMax - axisMin);
    }
    
    return calculateValue(log);
  }
  












  public double valueToJava2D(double value, Rectangle2D area, RectangleEdge edge)
  {
    Range range = getRange();
    double axisMin = calculateLog(range.getLowerBound());
    double axisMax = calculateLog(range.getUpperBound());
    value = calculateLog(value);
    
    double min = 0.0D;
    double max = 0.0D;
    if (RectangleEdge.isTopOrBottom(edge)) {
      min = area.getX();
      max = area.getMaxX();
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      max = area.getMinY();
      min = area.getMaxY();
    }
    if (isInverted()) {
      return max - (value - axisMin) / (axisMax - axisMin) * (max - min);
    }
    

    return min + (value - axisMin) / (axisMax - axisMin) * (max - min);
  }
  





  public void configure()
  {
    if (isAutoRange()) {
      autoAdjustRange();
    }
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
      
      double upper = r.getUpperBound();
      double lower = Math.max(r.getLowerBound(), smallestValue);
      double range = upper - lower;
      

      double fixedAutoRange = getFixedAutoRange();
      if (fixedAutoRange > 0.0D) {
        lower = Math.max(upper - fixedAutoRange, smallestValue);
      }
      else
      {
        double minRange = getAutoRangeMinimumSize();
        if (range < minRange) {
          double expand = (minRange - range) / 2.0D;
          upper += expand;
          lower -= expand;
        }
        

        double logUpper = calculateLog(upper);
        double logLower = calculateLog(lower);
        double logRange = logUpper - logLower;
        logUpper += getUpperMargin() * logRange;
        logLower -= getLowerMargin() * logRange;
        upper = calculateValue(logUpper);
        lower = calculateValue(logLower);
      }
      
      setRange(new Range(lower, upper), false, false);
    }
  }
  

















  public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState)
  {
    AxisState state = null;
    
    if (!isVisible()) {
      state = new AxisState(cursor);
      

      List ticks = refreshTicks(g2, state, dataArea, edge);
      state.setTicks(ticks);
      return state;
    }
    state = drawTickMarksAndLabels(g2, cursor, plotArea, dataArea, edge);
    state = drawLabel(getLabel(), g2, plotArea, dataArea, edge, state);
    createAndAddEntity(cursor, state, dataArea, edge, plotState);
    return state;
  }
  













  public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge)
  {
    List result = new ArrayList();
    if (RectangleEdge.isTopOrBottom(edge)) {
      result = refreshTicksHorizontal(g2, dataArea, edge);
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      result = refreshTicksVertical(g2, dataArea, edge);
    }
    return result;
  }
  











  protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    Range range = getRange();
    List ticks = new ArrayList();
    Font tickLabelFont = getTickLabelFont();
    g2.setFont(tickLabelFont);
    TextAnchor textAnchor;
    TextAnchor textAnchor; if (edge == RectangleEdge.TOP) {
      textAnchor = TextAnchor.BOTTOM_CENTER;
    }
    else {
      textAnchor = TextAnchor.TOP_CENTER;
    }
    
    if (isAutoTickUnitSelection()) {
      selectAutoTickUnit(g2, dataArea, edge);
    }
    int minorTickCount = tickUnit.getMinorTickCount();
    double start = Math.floor(calculateLog(getLowerBound()));
    double end = Math.ceil(calculateLog(getUpperBound()));
    double current = start;
    while (current <= end) {
      double v = calculateValue(current);
      if (range.contains(v)) {
        ticks.add(new NumberTick(TickType.MAJOR, v, createTickLabel(v), textAnchor, TextAnchor.CENTER, 0.0D));
      }
      

      double next = Math.pow(base, current + tickUnit.getSize());
      
      for (int i = 1; i < minorTickCount; i++) {
        double minorV = v + i * ((next - v) / minorTickCount);
        if (range.contains(minorV)) {
          ticks.add(new NumberTick(TickType.MINOR, minorV, "", textAnchor, TextAnchor.CENTER, 0.0D));
        }
      }
      
      current += tickUnit.getSize();
    }
    return ticks;
  }
  










  protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    Range range = getRange();
    List ticks = new ArrayList();
    Font tickLabelFont = getTickLabelFont();
    g2.setFont(tickLabelFont);
    TextAnchor textAnchor;
    TextAnchor textAnchor; if (edge == RectangleEdge.RIGHT) {
      textAnchor = TextAnchor.CENTER_LEFT;
    }
    else {
      textAnchor = TextAnchor.CENTER_RIGHT;
    }
    
    if (isAutoTickUnitSelection()) {
      selectAutoTickUnit(g2, dataArea, edge);
    }
    int minorTickCount = tickUnit.getMinorTickCount();
    double start = Math.floor(calculateLog(getLowerBound()));
    double end = Math.ceil(calculateLog(getUpperBound()));
    double current = start;
    while (current <= end) {
      double v = calculateValue(current);
      if (range.contains(v)) {
        ticks.add(new NumberTick(TickType.MAJOR, v, createTickLabel(v), textAnchor, TextAnchor.CENTER, 0.0D));
      }
      

      double next = Math.pow(base, current + tickUnit.getSize());
      
      for (int i = 1; i < minorTickCount; i++) {
        double minorV = v + i * ((next - v) / minorTickCount);
        if (range.contains(minorV)) {
          ticks.add(new NumberTick(TickType.MINOR, minorV, "", textAnchor, TextAnchor.CENTER, 0.0D));
        }
      }
      
      current += tickUnit.getSize();
    }
    return ticks;
  }
  












  protected void selectAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    if (RectangleEdge.isTopOrBottom(edge)) {
      selectHorizontalAutoTickUnit(g2, dataArea, edge);
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      selectVerticalAutoTickUnit(g2, dataArea, edge);
    }
  }
  













  protected void selectHorizontalAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    double tickLabelWidth = estimateMaximumTickLabelWidth(g2, getTickUnit());
    


    TickUnitSource tickUnits = getStandardTickUnits();
    TickUnit unit1 = tickUnits.getCeilingTickUnit(getTickUnit());
    double unit1Width = exponentLengthToJava2D(unit1.getSize(), dataArea, edge);
    


    double guess = tickLabelWidth / unit1Width * unit1.getSize();
    
    NumberTickUnit unit2 = (NumberTickUnit)tickUnits.getCeilingTickUnit(guess);
    
    double unit2Width = exponentLengthToJava2D(unit2.getSize(), dataArea, edge);
    

    tickLabelWidth = estimateMaximumTickLabelWidth(g2, unit2);
    if (tickLabelWidth > unit2Width) {
      unit2 = (NumberTickUnit)tickUnits.getLargerTickUnit(unit2);
    }
    
    setTickUnit(unit2, false, false);
  }
  













  public double exponentLengthToJava2D(double length, Rectangle2D area, RectangleEdge edge)
  {
    double one = valueToJava2D(calculateValue(1.0D), area, edge);
    double l = valueToJava2D(calculateValue(length + 1.0D), area, edge);
    return Math.abs(l - one);
  }
  













  protected void selectVerticalAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    double tickLabelHeight = estimateMaximumTickLabelHeight(g2);
    

    TickUnitSource tickUnits = getStandardTickUnits();
    TickUnit unit1 = tickUnits.getCeilingTickUnit(getTickUnit());
    double unitHeight = exponentLengthToJava2D(unit1.getSize(), dataArea, edge);
    


    double guess = tickLabelHeight / unitHeight * unit1.getSize();
    
    NumberTickUnit unit2 = (NumberTickUnit)tickUnits.getCeilingTickUnit(guess);
    
    double unit2Height = exponentLengthToJava2D(unit2.getSize(), dataArea, edge);
    

    tickLabelHeight = estimateMaximumTickLabelHeight(g2);
    if (tickLabelHeight > unit2Height) {
      unit2 = (NumberTickUnit)tickUnits.getLargerTickUnit(unit2);
    }
    
    setTickUnit(unit2, false, false);
  }
  










  protected double estimateMaximumTickLabelHeight(Graphics2D g2)
  {
    RectangleInsets tickLabelInsets = getTickLabelInsets();
    double result = tickLabelInsets.getTop() + tickLabelInsets.getBottom();
    
    Font tickLabelFont = getTickLabelFont();
    FontRenderContext frc = g2.getFontRenderContext();
    result += tickLabelFont.getLineMetrics("123", frc).getHeight();
    return result;
  }
  

















  protected double estimateMaximumTickLabelWidth(Graphics2D g2, TickUnit unit)
  {
    RectangleInsets tickLabelInsets = getTickLabelInsets();
    double result = tickLabelInsets.getLeft() + tickLabelInsets.getRight();
    
    if (isVerticalTickLabels())
    {

      FontRenderContext frc = g2.getFontRenderContext();
      LineMetrics lm = getTickLabelFont().getLineMetrics("0", frc);
      result += lm.getHeight();
    }
    else
    {
      FontMetrics fm = g2.getFontMetrics(getTickLabelFont());
      Range range = getRange();
      double lower = range.getLowerBound();
      double upper = range.getUpperBound();
      String lowerStr = "";
      String upperStr = "";
      NumberFormat formatter = getNumberFormatOverride();
      if (formatter != null) {
        lowerStr = formatter.format(lower);
        upperStr = formatter.format(upper);
      }
      else {
        lowerStr = unit.valueToString(lower);
        upperStr = unit.valueToString(upper);
      }
      double w1 = fm.stringWidth(lowerStr);
      double w2 = fm.stringWidth(upperStr);
      result += Math.max(w1, w2);
    }
    
    return result;
  }
  






  public void zoomRange(double lowerPercent, double upperPercent)
  {
    Range range = getRange();
    double start = range.getLowerBound();
    double end = range.getUpperBound();
    double log1 = calculateLog(start);
    double log2 = calculateLog(end);
    double length = log2 - log1;
    Range adjusted = null;
    if (isInverted()) {
      double logA = log1 + length * (1.0D - upperPercent);
      double logB = log1 + length * (1.0D - lowerPercent);
      adjusted = new Range(calculateValue(logA), calculateValue(logB));
    }
    else {
      double logA = log1 + length * lowerPercent;
      double logB = log1 + length * upperPercent;
      adjusted = new Range(calculateValue(logA), calculateValue(logB));
    }
    setRange(adjusted);
  }
  






  public void pan(double percent)
  {
    Range range = getRange();
    double lower = range.getLowerBound();
    double upper = range.getUpperBound();
    double log1 = calculateLog(lower);
    double log2 = calculateLog(upper);
    double length = log2 - log1;
    double adj = length * percent;
    log1 += adj;
    log2 += adj;
    setRange(calculateValue(log1), calculateValue(log2));
  }
  









  protected String createTickLabel(double value)
  {
    if (numberFormatOverride != null) {
      return numberFormatOverride.format(value);
    }
    
    return tickUnit.valueToString(value);
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof LogAxis)) {
      return false;
    }
    LogAxis that = (LogAxis)obj;
    if (base != base) {
      return false;
    }
    if (smallestValue != smallestValue) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = 193;
    long temp = Double.doubleToLongBits(base);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(smallestValue);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    if (numberFormatOverride != null) {
      result = 37 * result + numberFormatOverride.hashCode();
    }
    result = 37 * result + tickUnit.hashCode();
    return result;
  }
  









  public static TickUnitSource createLogTickUnits(Locale locale)
  {
    TickUnits units = new TickUnits();
    NumberFormat numberFormat = new LogFormat();
    units.add(new NumberTickUnit(0.05D, numberFormat, 2));
    units.add(new NumberTickUnit(0.1D, numberFormat, 10));
    units.add(new NumberTickUnit(0.2D, numberFormat, 2));
    units.add(new NumberTickUnit(0.5D, numberFormat, 5));
    units.add(new NumberTickUnit(1.0D, numberFormat, 10));
    units.add(new NumberTickUnit(2.0D, numberFormat, 10));
    units.add(new NumberTickUnit(3.0D, numberFormat, 15));
    units.add(new NumberTickUnit(4.0D, numberFormat, 20));
    units.add(new NumberTickUnit(5.0D, numberFormat, 25));
    units.add(new NumberTickUnit(6.0D, numberFormat));
    units.add(new NumberTickUnit(7.0D, numberFormat));
    units.add(new NumberTickUnit(8.0D, numberFormat));
    units.add(new NumberTickUnit(9.0D, numberFormat));
    units.add(new NumberTickUnit(10.0D, numberFormat));
    return units;
  }
}
