package org.jfree.chart.axis;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.data.Range;
import org.jfree.data.RangeType;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;






















































































































public class NumberAxis
  extends ValueAxis
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 2805933088476185789L;
  public static final boolean DEFAULT_AUTO_RANGE_INCLUDES_ZERO = true;
  public static final boolean DEFAULT_AUTO_RANGE_STICKY_ZERO = true;
  public static final NumberTickUnit DEFAULT_TICK_UNIT = new NumberTickUnit(1.0D, new DecimalFormat("0"));
  



  public static final boolean DEFAULT_VERTICAL_TICK_LABELS = false;
  



  private RangeType rangeType;
  



  private boolean autoRangeIncludesZero;
  



  private boolean autoRangeStickyZero;
  


  private NumberTickUnit tickUnit;
  


  private NumberFormat numberFormatOverride;
  


  private MarkerAxisBand markerBand;
  



  public NumberAxis()
  {
    this(null);
  }
  




  public NumberAxis(String label)
  {
    super(label, createStandardTickUnits());
    rangeType = RangeType.FULL;
    autoRangeIncludesZero = true;
    autoRangeStickyZero = true;
    tickUnit = DEFAULT_TICK_UNIT;
    numberFormatOverride = null;
    markerBand = null;
  }
  






  public RangeType getRangeType()
  {
    return rangeType;
  }
  






  public void setRangeType(RangeType rangeType)
  {
    if (rangeType == null) {
      throw new IllegalArgumentException("Null 'rangeType' argument.");
    }
    this.rangeType = rangeType;
    notifyListeners(new AxisChangeEvent(this));
  }
  





  public boolean getAutoRangeIncludesZero()
  {
    return autoRangeIncludesZero;
  }
  












  public void setAutoRangeIncludesZero(boolean flag)
  {
    if (autoRangeIncludesZero != flag) {
      autoRangeIncludesZero = flag;
      if (isAutoRange()) {
        autoAdjustRange();
      }
      notifyListeners(new AxisChangeEvent(this));
    }
  }
  







  public boolean getAutoRangeStickyZero()
  {
    return autoRangeStickyZero;
  }
  







  public void setAutoRangeStickyZero(boolean flag)
  {
    if (autoRangeStickyZero != flag) {
      autoRangeStickyZero = flag;
      if (isAutoRange()) {
        autoAdjustRange();
      }
      notifyListeners(new AxisChangeEvent(this));
    }
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
  






  public MarkerAxisBand getMarkerBand()
  {
    return markerBand;
  }
  









  public void setMarkerBand(MarkerAxisBand band)
  {
    markerBand = band;
    notifyListeners(new AxisChangeEvent(this));
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
      double lower = r.getLowerBound();
      if (rangeType == RangeType.POSITIVE) {
        lower = Math.max(0.0D, lower);
        upper = Math.max(0.0D, upper);
      }
      else if (rangeType == RangeType.NEGATIVE) {
        lower = Math.min(0.0D, lower);
        upper = Math.min(0.0D, upper);
      }
      
      if (getAutoRangeIncludesZero()) {
        lower = Math.min(lower, 0.0D);
        upper = Math.max(upper, 0.0D);
      }
      double range = upper - lower;
      

      double fixedAutoRange = getFixedAutoRange();
      if (fixedAutoRange > 0.0D) {
        lower = upper - fixedAutoRange;
      }
      else
      {
        double minRange = getAutoRangeMinimumSize();
        if (range < minRange) {
          double expand = (minRange - range) / 2.0D;
          upper += expand;
          lower -= expand;
          if (lower == upper) {
            double adjust = Math.abs(lower) / 10.0D;
            lower -= adjust;
            upper += adjust;
          }
          if (rangeType == RangeType.POSITIVE) {
            if (lower < 0.0D) {
              upper -= lower;
              lower = 0.0D;
            }
          }
          else if ((rangeType == RangeType.NEGATIVE) && 
            (upper > 0.0D)) {
            lower -= upper;
            upper = 0.0D;
          }
        }
        

        if (getAutoRangeStickyZero()) {
          if (upper <= 0.0D) {
            upper = Math.min(0.0D, upper + getUpperMargin() * range);
          }
          else {
            upper += getUpperMargin() * range;
          }
          if (lower >= 0.0D) {
            lower = Math.max(0.0D, lower - getLowerMargin() * range);
          }
          else {
            lower -= getLowerMargin() * range;
          }
        }
        else {
          upper += getUpperMargin() * range;
          lower -= getLowerMargin() * range;
        }
      }
      
      setRange(new Range(lower, upper), false, false);
    }
  }
  
















  public double valueToJava2D(double value, Rectangle2D area, RectangleEdge edge)
  {
    Range range = getRange();
    double axisMin = range.getLowerBound();
    double axisMax = range.getUpperBound();
    
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
  
















  public double java2DToValue(double java2DValue, Rectangle2D area, RectangleEdge edge)
  {
    Range range = getRange();
    double axisMin = range.getLowerBound();
    double axisMax = range.getUpperBound();
    
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
    if (isInverted()) {
      return axisMax - (java2DValue - min) / (max - min) * (axisMax - axisMin);
    }
    

    return axisMin + (java2DValue - min) / (max - min) * (axisMax - axisMin);
  }
  










  protected double calculateLowestVisibleTickValue()
  {
    double unit = getTickUnit().getSize();
    double index = Math.ceil(getRange().getLowerBound() / unit);
    return index * unit;
  }
  








  protected double calculateHighestVisibleTickValue()
  {
    double unit = getTickUnit().getSize();
    double index = Math.floor(getRange().getUpperBound() / unit);
    return index * unit;
  }
  






  protected int calculateVisibleTickCount()
  {
    double unit = getTickUnit().getSize();
    Range range = getRange();
    return (int)(Math.floor(range.getUpperBound() / unit) - Math.ceil(range.getLowerBound() / unit) + 1.0D);
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
  













  public static TickUnitSource createStandardTickUnits()
  {
    TickUnits units = new TickUnits();
    DecimalFormat df0 = new DecimalFormat("0.00000000");
    DecimalFormat df1 = new DecimalFormat("0.0000000");
    DecimalFormat df2 = new DecimalFormat("0.000000");
    DecimalFormat df3 = new DecimalFormat("0.00000");
    DecimalFormat df4 = new DecimalFormat("0.0000");
    DecimalFormat df5 = new DecimalFormat("0.000");
    DecimalFormat df6 = new DecimalFormat("0.00");
    DecimalFormat df7 = new DecimalFormat("0.0");
    DecimalFormat df8 = new DecimalFormat("#,##0");
    DecimalFormat df9 = new DecimalFormat("#,###,##0");
    DecimalFormat df10 = new DecimalFormat("#,###,###,##0");
    


    units.add(new NumberTickUnit(1.0E-7D, df1, 2));
    units.add(new NumberTickUnit(1.0E-6D, df2, 2));
    units.add(new NumberTickUnit(1.0E-5D, df3, 2));
    units.add(new NumberTickUnit(1.0E-4D, df4, 2));
    units.add(new NumberTickUnit(0.001D, df5, 2));
    units.add(new NumberTickUnit(0.01D, df6, 2));
    units.add(new NumberTickUnit(0.1D, df7, 2));
    units.add(new NumberTickUnit(1.0D, df8, 2));
    units.add(new NumberTickUnit(10.0D, df8, 2));
    units.add(new NumberTickUnit(100.0D, df8, 2));
    units.add(new NumberTickUnit(1000.0D, df8, 2));
    units.add(new NumberTickUnit(10000.0D, df8, 2));
    units.add(new NumberTickUnit(100000.0D, df8, 2));
    units.add(new NumberTickUnit(1000000.0D, df9, 2));
    units.add(new NumberTickUnit(1.0E7D, df9, 2));
    units.add(new NumberTickUnit(1.0E8D, df9, 2));
    units.add(new NumberTickUnit(1.0E9D, df10, 2));
    units.add(new NumberTickUnit(1.0E10D, df10, 2));
    units.add(new NumberTickUnit(1.0E11D, df10, 2));
    
    units.add(new NumberTickUnit(2.5E-7D, df0, 5));
    units.add(new NumberTickUnit(2.5E-6D, df1, 5));
    units.add(new NumberTickUnit(2.5E-5D, df2, 5));
    units.add(new NumberTickUnit(2.5E-4D, df3, 5));
    units.add(new NumberTickUnit(0.0025D, df4, 5));
    units.add(new NumberTickUnit(0.025D, df5, 5));
    units.add(new NumberTickUnit(0.25D, df6, 5));
    units.add(new NumberTickUnit(2.5D, df7, 5));
    units.add(new NumberTickUnit(25.0D, df8, 5));
    units.add(new NumberTickUnit(250.0D, df8, 5));
    units.add(new NumberTickUnit(2500.0D, df8, 5));
    units.add(new NumberTickUnit(25000.0D, df8, 5));
    units.add(new NumberTickUnit(250000.0D, df8, 5));
    units.add(new NumberTickUnit(2500000.0D, df9, 5));
    units.add(new NumberTickUnit(2.5E7D, df9, 5));
    units.add(new NumberTickUnit(2.5E8D, df9, 5));
    units.add(new NumberTickUnit(2.5E9D, df10, 5));
    units.add(new NumberTickUnit(2.5E10D, df10, 5));
    units.add(new NumberTickUnit(2.5E11D, df10, 5));
    
    units.add(new NumberTickUnit(5.0E-7D, df1, 5));
    units.add(new NumberTickUnit(5.0E-6D, df2, 5));
    units.add(new NumberTickUnit(5.0E-5D, df3, 5));
    units.add(new NumberTickUnit(5.0E-4D, df4, 5));
    units.add(new NumberTickUnit(0.005D, df5, 5));
    units.add(new NumberTickUnit(0.05D, df6, 5));
    units.add(new NumberTickUnit(0.5D, df7, 5));
    units.add(new NumberTickUnit(5.0D, df8, 5));
    units.add(new NumberTickUnit(50.0D, df8, 5));
    units.add(new NumberTickUnit(500.0D, df8, 5));
    units.add(new NumberTickUnit(5000.0D, df8, 5));
    units.add(new NumberTickUnit(50000.0D, df8, 5));
    units.add(new NumberTickUnit(500000.0D, df8, 5));
    units.add(new NumberTickUnit(5000000.0D, df9, 5));
    units.add(new NumberTickUnit(5.0E7D, df9, 5));
    units.add(new NumberTickUnit(5.0E8D, df9, 5));
    units.add(new NumberTickUnit(5.0E9D, df10, 5));
    units.add(new NumberTickUnit(5.0E10D, df10, 5));
    units.add(new NumberTickUnit(5.0E11D, df10, 5));
    
    return units;
  }
  








  public static TickUnitSource createIntegerTickUnits()
  {
    TickUnits units = new TickUnits();
    DecimalFormat df0 = new DecimalFormat("0");
    DecimalFormat df1 = new DecimalFormat("#,##0");
    units.add(new NumberTickUnit(1.0D, df0, 2));
    units.add(new NumberTickUnit(2.0D, df0, 2));
    units.add(new NumberTickUnit(5.0D, df0, 5));
    units.add(new NumberTickUnit(10.0D, df0, 2));
    units.add(new NumberTickUnit(20.0D, df0, 2));
    units.add(new NumberTickUnit(50.0D, df0, 5));
    units.add(new NumberTickUnit(100.0D, df0, 2));
    units.add(new NumberTickUnit(200.0D, df0, 2));
    units.add(new NumberTickUnit(500.0D, df0, 5));
    units.add(new NumberTickUnit(1000.0D, df1, 2));
    units.add(new NumberTickUnit(2000.0D, df1, 2));
    units.add(new NumberTickUnit(5000.0D, df1, 5));
    units.add(new NumberTickUnit(10000.0D, df1, 2));
    units.add(new NumberTickUnit(20000.0D, df1, 2));
    units.add(new NumberTickUnit(50000.0D, df1, 5));
    units.add(new NumberTickUnit(100000.0D, df1, 2));
    units.add(new NumberTickUnit(200000.0D, df1, 2));
    units.add(new NumberTickUnit(500000.0D, df1, 5));
    units.add(new NumberTickUnit(1000000.0D, df1, 2));
    units.add(new NumberTickUnit(2000000.0D, df1, 2));
    units.add(new NumberTickUnit(5000000.0D, df1, 5));
    units.add(new NumberTickUnit(1.0E7D, df1, 2));
    units.add(new NumberTickUnit(2.0E7D, df1, 2));
    units.add(new NumberTickUnit(5.0E7D, df1, 5));
    units.add(new NumberTickUnit(1.0E8D, df1, 2));
    units.add(new NumberTickUnit(2.0E8D, df1, 2));
    units.add(new NumberTickUnit(5.0E8D, df1, 5));
    units.add(new NumberTickUnit(1.0E9D, df1, 2));
    units.add(new NumberTickUnit(2.0E9D, df1, 2));
    units.add(new NumberTickUnit(5.0E9D, df1, 5));
    units.add(new NumberTickUnit(1.0E10D, df1, 2));
    return units;
  }
  















  public static TickUnitSource createStandardTickUnits(Locale locale)
  {
    TickUnits units = new TickUnits();
    NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
    

    units.add(new NumberTickUnit(1.0E-7D, numberFormat, 2));
    units.add(new NumberTickUnit(1.0E-6D, numberFormat, 2));
    units.add(new NumberTickUnit(1.0E-5D, numberFormat, 2));
    units.add(new NumberTickUnit(1.0E-4D, numberFormat, 2));
    units.add(new NumberTickUnit(0.001D, numberFormat, 2));
    units.add(new NumberTickUnit(0.01D, numberFormat, 2));
    units.add(new NumberTickUnit(0.1D, numberFormat, 2));
    units.add(new NumberTickUnit(1.0D, numberFormat, 2));
    units.add(new NumberTickUnit(10.0D, numberFormat, 2));
    units.add(new NumberTickUnit(100.0D, numberFormat, 2));
    units.add(new NumberTickUnit(1000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(10000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(100000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(1000000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(1.0E7D, numberFormat, 2));
    units.add(new NumberTickUnit(1.0E8D, numberFormat, 2));
    units.add(new NumberTickUnit(1.0E9D, numberFormat, 2));
    units.add(new NumberTickUnit(1.0E10D, numberFormat, 2));
    
    units.add(new NumberTickUnit(2.5E-7D, numberFormat, 5));
    units.add(new NumberTickUnit(2.5E-6D, numberFormat, 5));
    units.add(new NumberTickUnit(2.5E-5D, numberFormat, 5));
    units.add(new NumberTickUnit(2.5E-4D, numberFormat, 5));
    units.add(new NumberTickUnit(0.0025D, numberFormat, 5));
    units.add(new NumberTickUnit(0.025D, numberFormat, 5));
    units.add(new NumberTickUnit(0.25D, numberFormat, 5));
    units.add(new NumberTickUnit(2.5D, numberFormat, 5));
    units.add(new NumberTickUnit(25.0D, numberFormat, 5));
    units.add(new NumberTickUnit(250.0D, numberFormat, 5));
    units.add(new NumberTickUnit(2500.0D, numberFormat, 5));
    units.add(new NumberTickUnit(25000.0D, numberFormat, 5));
    units.add(new NumberTickUnit(250000.0D, numberFormat, 5));
    units.add(new NumberTickUnit(2500000.0D, numberFormat, 5));
    units.add(new NumberTickUnit(2.5E7D, numberFormat, 5));
    units.add(new NumberTickUnit(2.5E8D, numberFormat, 5));
    units.add(new NumberTickUnit(2.5E9D, numberFormat, 5));
    units.add(new NumberTickUnit(2.5E10D, numberFormat, 5));
    
    units.add(new NumberTickUnit(5.0E-7D, numberFormat, 5));
    units.add(new NumberTickUnit(5.0E-6D, numberFormat, 5));
    units.add(new NumberTickUnit(5.0E-5D, numberFormat, 5));
    units.add(new NumberTickUnit(5.0E-4D, numberFormat, 5));
    units.add(new NumberTickUnit(0.005D, numberFormat, 5));
    units.add(new NumberTickUnit(0.05D, numberFormat, 5));
    units.add(new NumberTickUnit(0.5D, numberFormat, 5));
    units.add(new NumberTickUnit(5.0D, numberFormat, 5));
    units.add(new NumberTickUnit(50.0D, numberFormat, 5));
    units.add(new NumberTickUnit(500.0D, numberFormat, 5));
    units.add(new NumberTickUnit(5000.0D, numberFormat, 5));
    units.add(new NumberTickUnit(50000.0D, numberFormat, 5));
    units.add(new NumberTickUnit(500000.0D, numberFormat, 5));
    units.add(new NumberTickUnit(5000000.0D, numberFormat, 5));
    units.add(new NumberTickUnit(5.0E7D, numberFormat, 5));
    units.add(new NumberTickUnit(5.0E8D, numberFormat, 5));
    units.add(new NumberTickUnit(5.0E9D, numberFormat, 5));
    units.add(new NumberTickUnit(5.0E10D, numberFormat, 5));
    
    return units;
  }
  










  public static TickUnitSource createIntegerTickUnits(Locale locale)
  {
    TickUnits units = new TickUnits();
    NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
    units.add(new NumberTickUnit(1.0D, numberFormat, 2));
    units.add(new NumberTickUnit(2.0D, numberFormat, 2));
    units.add(new NumberTickUnit(5.0D, numberFormat, 5));
    units.add(new NumberTickUnit(10.0D, numberFormat, 2));
    units.add(new NumberTickUnit(20.0D, numberFormat, 2));
    units.add(new NumberTickUnit(50.0D, numberFormat, 5));
    units.add(new NumberTickUnit(100.0D, numberFormat, 2));
    units.add(new NumberTickUnit(200.0D, numberFormat, 2));
    units.add(new NumberTickUnit(500.0D, numberFormat, 5));
    units.add(new NumberTickUnit(1000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(2000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(5000.0D, numberFormat, 5));
    units.add(new NumberTickUnit(10000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(20000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(50000.0D, numberFormat, 5));
    units.add(new NumberTickUnit(100000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(200000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(500000.0D, numberFormat, 5));
    units.add(new NumberTickUnit(1000000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(2000000.0D, numberFormat, 2));
    units.add(new NumberTickUnit(5000000.0D, numberFormat, 5));
    units.add(new NumberTickUnit(1.0E7D, numberFormat, 2));
    units.add(new NumberTickUnit(2.0E7D, numberFormat, 2));
    units.add(new NumberTickUnit(5.0E7D, numberFormat, 5));
    units.add(new NumberTickUnit(1.0E8D, numberFormat, 2));
    units.add(new NumberTickUnit(2.0E8D, numberFormat, 2));
    units.add(new NumberTickUnit(5.0E8D, numberFormat, 5));
    units.add(new NumberTickUnit(1.0E9D, numberFormat, 2));
    units.add(new NumberTickUnit(2.0E9D, numberFormat, 2));
    units.add(new NumberTickUnit(5.0E9D, numberFormat, 5));
    units.add(new NumberTickUnit(1.0E10D, numberFormat, 2));
    return units;
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
    double unit1Width = lengthToJava2D(unit1.getSize(), dataArea, edge);
    

    double guess = tickLabelWidth / unit1Width * unit1.getSize();
    
    NumberTickUnit unit2 = (NumberTickUnit)tickUnits.getCeilingTickUnit(guess);
    
    double unit2Width = lengthToJava2D(unit2.getSize(), dataArea, edge);
    
    tickLabelWidth = estimateMaximumTickLabelWidth(g2, unit2);
    if (tickLabelWidth > unit2Width) {
      unit2 = (NumberTickUnit)tickUnits.getLargerTickUnit(unit2);
    }
    
    setTickUnit(unit2, false, false);
  }
  












  protected void selectVerticalAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    double tickLabelHeight = estimateMaximumTickLabelHeight(g2);
    

    TickUnitSource tickUnits = getStandardTickUnits();
    TickUnit unit1 = tickUnits.getCeilingTickUnit(getTickUnit());
    double unitHeight = lengthToJava2D(unit1.getSize(), dataArea, edge);
    

    double guess = tickLabelHeight / unitHeight * unit1.getSize();
    
    NumberTickUnit unit2 = (NumberTickUnit)tickUnits.getCeilingTickUnit(guess);
    
    double unit2Height = lengthToJava2D(unit2.getSize(), dataArea, edge);
    
    tickLabelHeight = estimateMaximumTickLabelHeight(g2);
    if (tickLabelHeight > unit2Height) {
      unit2 = (NumberTickUnit)tickUnits.getLargerTickUnit(unit2);
    }
    
    setTickUnit(unit2, false, false);
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
    List result = new ArrayList();
    
    Font tickLabelFont = getTickLabelFont();
    g2.setFont(tickLabelFont);
    
    if (isAutoTickUnitSelection()) {
      selectAutoTickUnit(g2, dataArea, edge);
    }
    
    TickUnit tu = getTickUnit();
    double size = tu.getSize();
    int count = calculateVisibleTickCount();
    double lowestTickValue = calculateLowestVisibleTickValue();
    
    if (count <= 500) {
      int minorTickSpaces = getMinorTickCount();
      if (minorTickSpaces <= 0) {
        minorTickSpaces = tu.getMinorTickCount();
      }
      for (int minorTick = 1; minorTick < minorTickSpaces; minorTick++) {
        double minorTickValue = lowestTickValue - size * minorTick / minorTickSpaces;
        
        if (getRange().contains(minorTickValue)) {
          result.add(new NumberTick(TickType.MINOR, minorTickValue, "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
        }
      }
      

      for (int i = 0; i < count; i++) {
        double currentTickValue = lowestTickValue + i * size;
        
        NumberFormat formatter = getNumberFormatOverride();
        String tickLabel; String tickLabel; if (formatter != null) {
          tickLabel = formatter.format(currentTickValue);
        }
        else {
          tickLabel = getTickUnit().valueToString(currentTickValue);
        }
        TextAnchor anchor = null;
        TextAnchor rotationAnchor = null;
        double angle = 0.0D;
        if (isVerticalTickLabels()) {
          anchor = TextAnchor.CENTER_RIGHT;
          rotationAnchor = TextAnchor.CENTER_RIGHT;
          if (edge == RectangleEdge.TOP) {
            angle = 1.5707963267948966D;
          }
          else {
            angle = -1.5707963267948966D;
          }
          
        }
        else if (edge == RectangleEdge.TOP) {
          anchor = TextAnchor.BOTTOM_CENTER;
          rotationAnchor = TextAnchor.BOTTOM_CENTER;
        }
        else {
          anchor = TextAnchor.TOP_CENTER;
          rotationAnchor = TextAnchor.TOP_CENTER;
        }
        

        Tick tick = new NumberTick(new Double(currentTickValue), tickLabel, anchor, rotationAnchor, angle);
        
        result.add(tick);
        double nextTickValue = lowestTickValue + (i + 1) * size;
        for (int minorTick = 1; minorTick < minorTickSpaces; 
            minorTick++) {
          double minorTickValue = currentTickValue + (nextTickValue - currentTickValue) * minorTick / minorTickSpaces;
          

          if (getRange().contains(minorTickValue)) {
            result.add(new NumberTick(TickType.MINOR, minorTickValue, "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
          }
        }
      }
    }
    

    return result;
  }
  












  protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    List result = new ArrayList();
    result.clear();
    
    Font tickLabelFont = getTickLabelFont();
    g2.setFont(tickLabelFont);
    if (isAutoTickUnitSelection()) {
      selectAutoTickUnit(g2, dataArea, edge);
    }
    
    TickUnit tu = getTickUnit();
    double size = tu.getSize();
    int count = calculateVisibleTickCount();
    double lowestTickValue = calculateLowestVisibleTickValue();
    
    if (count <= 500) {
      int minorTickSpaces = getMinorTickCount();
      if (minorTickSpaces <= 0) {
        minorTickSpaces = tu.getMinorTickCount();
      }
      for (int minorTick = 1; minorTick < minorTickSpaces; minorTick++) {
        double minorTickValue = lowestTickValue - size * minorTick / minorTickSpaces;
        
        if (getRange().contains(minorTickValue)) {
          result.add(new NumberTick(TickType.MINOR, minorTickValue, "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
        }
      }
      


      for (int i = 0; i < count; i++) {
        double currentTickValue = lowestTickValue + i * size;
        
        NumberFormat formatter = getNumberFormatOverride();
        String tickLabel; String tickLabel; if (formatter != null) {
          tickLabel = formatter.format(currentTickValue);
        }
        else {
          tickLabel = getTickUnit().valueToString(currentTickValue);
        }
        
        TextAnchor anchor = null;
        TextAnchor rotationAnchor = null;
        double angle = 0.0D;
        if (isVerticalTickLabels()) {
          if (edge == RectangleEdge.LEFT) {
            anchor = TextAnchor.BOTTOM_CENTER;
            rotationAnchor = TextAnchor.BOTTOM_CENTER;
            angle = -1.5707963267948966D;
          }
          else {
            anchor = TextAnchor.BOTTOM_CENTER;
            rotationAnchor = TextAnchor.BOTTOM_CENTER;
            angle = 1.5707963267948966D;
          }
          
        }
        else if (edge == RectangleEdge.LEFT) {
          anchor = TextAnchor.CENTER_RIGHT;
          rotationAnchor = TextAnchor.CENTER_RIGHT;
        }
        else {
          anchor = TextAnchor.CENTER_LEFT;
          rotationAnchor = TextAnchor.CENTER_LEFT;
        }
        

        Tick tick = new NumberTick(new Double(currentTickValue), tickLabel, anchor, rotationAnchor, angle);
        
        result.add(tick);
        
        double nextTickValue = lowestTickValue + (i + 1) * size;
        for (int minorTick = 1; minorTick < minorTickSpaces; 
            minorTick++) {
          double minorTickValue = currentTickValue + (nextTickValue - currentTickValue) * minorTick / minorTickSpaces;
          

          if (getRange().contains(minorTickValue)) {
            result.add(new NumberTick(TickType.MINOR, minorTickValue, "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0D));
          }
        }
      }
    }
    

    return result;
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    NumberAxis clone = (NumberAxis)super.clone();
    if (numberFormatOverride != null) {
      numberFormatOverride = ((NumberFormat)numberFormatOverride.clone());
    }
    
    return clone;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof NumberAxis)) {
      return false;
    }
    NumberAxis that = (NumberAxis)obj;
    if (autoRangeIncludesZero != autoRangeIncludesZero) {
      return false;
    }
    if (autoRangeStickyZero != autoRangeStickyZero) {
      return false;
    }
    if (!ObjectUtilities.equal(tickUnit, tickUnit)) {
      return false;
    }
    if (!ObjectUtilities.equal(numberFormatOverride, numberFormatOverride))
    {
      return false;
    }
    if (!rangeType.equals(rangeType)) {
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
}
