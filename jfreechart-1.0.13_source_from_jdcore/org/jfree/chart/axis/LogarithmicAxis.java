package org.jfree.chart.axis;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.data.Range;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;




































































































public class LogarithmicAxis
  extends NumberAxis
{
  private static final long serialVersionUID = 2502918599004103054L;
  public static final double LOG10_VALUE = Math.log(10.0D);
  

  public static final double SMALL_LOG_VALUE = 1.0E-100D;
  

  protected boolean allowNegativesFlag = false;
  




  protected boolean strictValuesFlag = true;
  

  protected final NumberFormat numberFormatterObj = NumberFormat.getInstance();
  


  protected boolean expTickLabelsFlag = false;
  

  protected boolean log10TickLabelsFlag = false;
  

  protected boolean autoRangeNextLogFlag = false;
  

  protected boolean smallLogFlag = false;
  




  public LogarithmicAxis(String label)
  {
    super(label);
    setupNumberFmtObj();
  }
  






  public void setAllowNegativesFlag(boolean flgVal)
  {
    allowNegativesFlag = flgVal;
  }
  






  public boolean getAllowNegativesFlag()
  {
    return allowNegativesFlag;
  }
  







  public void setStrictValuesFlag(boolean flgVal)
  {
    strictValuesFlag = flgVal;
  }
  







  public boolean getStrictValuesFlag()
  {
    return strictValuesFlag;
  }
  







  public void setExpTickLabelsFlag(boolean flgVal)
  {
    expTickLabelsFlag = flgVal;
    setupNumberFmtObj();
  }
  





  public boolean getExpTickLabelsFlag()
  {
    return expTickLabelsFlag;
  }
  





  public void setLog10TickLabelsFlag(boolean flag)
  {
    log10TickLabelsFlag = flag;
  }
  






  public boolean getLog10TickLabelsFlag()
  {
    return log10TickLabelsFlag;
  }
  








  public void setAutoRangeNextLogFlag(boolean flag)
  {
    autoRangeNextLogFlag = flag;
  }
  





  public boolean getAutoRangeNextLogFlag()
  {
    return autoRangeNextLogFlag;
  }
  





  public void setRange(Range range)
  {
    super.setRange(range);
    setupSmallLogFlag();
  }
  





  protected void setupSmallLogFlag()
  {
    double lowerVal = getRange().getLowerBound();
    smallLogFlag = ((!allowNegativesFlag) && (lowerVal < 10.0D) && (lowerVal > 0.0D));
  }
  




  protected void setupNumberFmtObj()
  {
    if ((numberFormatterObj instanceof DecimalFormat))
    {

      ((DecimalFormat)numberFormatterObj).applyPattern(expTickLabelsFlag ? "0E0" : "0.###");
    }
  }
  













  protected double switchedLog10(double val)
  {
    return smallLogFlag ? Math.log(val) / LOG10_VALUE : adjustedLog10(val);
  }
  














  public double switchedPow10(double val)
  {
    return smallLogFlag ? Math.pow(10.0D, val) : adjustedPow10(val);
  }
  













  public double adjustedLog10(double val)
  {
    boolean negFlag = val < 0.0D;
    if (negFlag) {
      val = -val;
    }
    if (val < 10.0D) {
      val += (10.0D - val) / 10.0D;
    }
    
    double res = Math.log(val) / LOG10_VALUE;
    return negFlag ? -res : res;
  }
  













  public double adjustedPow10(double val)
  {
    boolean negFlag = val < 0.0D;
    if (negFlag)
      val = -val;
    double res;
    double res;
    if (val < 1.0D) {
      res = (Math.pow(10.0D, val + 1.0D) - 10.0D) / 9.0D;
    }
    else {
      res = Math.pow(10.0D, val);
    }
    return negFlag ? -res : res;
  }
  



  protected double computeLogFloor(double lower)
  {
    double logFloor;
    


    double logFloor;
    


    if (allowNegativesFlag)
    {
      if (lower > 10.0D)
      {
        double logFloor = Math.log(lower) / LOG10_VALUE;
        logFloor = Math.floor(logFloor);
        logFloor = Math.pow(10.0D, logFloor);
      }
      else if (lower < -10.0D)
      {
        double logFloor = Math.log(-lower) / LOG10_VALUE;
        
        logFloor = Math.floor(-logFloor);
        
        logFloor = -Math.pow(10.0D, -logFloor);
      }
      else
      {
        logFloor = Math.floor(lower);
      }
      

    }
    else if (lower > 0.0D)
    {
      double logFloor = Math.log(lower) / LOG10_VALUE;
      logFloor = Math.floor(logFloor);
      logFloor = Math.pow(10.0D, logFloor);
    }
    else
    {
      logFloor = Math.floor(lower);
    }
    
    return logFloor;
  }
  



  protected double computeLogCeil(double upper)
  {
    double logCeil;
    


    double logCeil;
    


    if (allowNegativesFlag)
    {
      if (upper > 10.0D)
      {

        double logCeil = Math.log(upper) / LOG10_VALUE;
        logCeil = Math.ceil(logCeil);
        logCeil = Math.pow(10.0D, logCeil);
      }
      else if (upper < -10.0D)
      {

        double logCeil = Math.log(-upper) / LOG10_VALUE;
        
        logCeil = Math.ceil(-logCeil);
        
        logCeil = -Math.pow(10.0D, -logCeil);
      }
      else
      {
        logCeil = Math.ceil(upper);
      }
      

    }
    else if (upper > 0.0D)
    {

      double logCeil = Math.log(upper) / LOG10_VALUE;
      logCeil = Math.ceil(logCeil);
      logCeil = Math.pow(10.0D, logCeil);
    }
    else
    {
      logCeil = Math.ceil(upper);
    }
    
    return logCeil;
  }
  



  public void autoAdjustRange()
  {
    Plot plot = getPlot();
    if (plot == null) {
      return;
    }
    
    if ((plot instanceof ValueAxisPlot)) {
      ValueAxisPlot vap = (ValueAxisPlot)plot;
      

      Range r = vap.getDataRange(this);
      double lower; double lower; if (r == null)
      {
        r = getDefaultAutoRange();
        lower = r.getLowerBound();
      }
      else
      {
        lower = r.getLowerBound();
        if ((strictValuesFlag) && (!allowNegativesFlag) && (lower <= 0.0D))
        {

          throw new RuntimeException("Values less than or equal to zero not allowed with logarithmic axis");
        }
      }
      

      double lowerMargin;
      
      if ((lower > 0.0D) && ((lowerMargin = getLowerMargin()) > 0.0D))
      {
        double logLower = Math.log(lower) / LOG10_VALUE;
        double logAbs;
        if ((logAbs = Math.abs(logLower)) < 1.0D) {
          logAbs = 1.0D;
        }
        lower = Math.pow(10.0D, logLower - logAbs * lowerMargin);
      }
      


      if (autoRangeNextLogFlag) {
        lower = computeLogFloor(lower);
      }
      
      if ((!allowNegativesFlag) && (lower >= 0.0D) && (lower < 1.0E-100D))
      {

        lower = r.getLowerBound();
      }
      
      double upper = r.getUpperBound();
      
      double upperMargin;
      
      if ((upper > 0.0D) && ((upperMargin = getUpperMargin()) > 0.0D))
      {
        double logUpper = Math.log(upper) / LOG10_VALUE;
        double logAbs;
        if ((logAbs = Math.abs(logUpper)) < 1.0D) {
          logAbs = 1.0D;
        }
        upper = Math.pow(10.0D, logUpper + logAbs * upperMargin);
      }
      
      if ((!allowNegativesFlag) && (upper < 1.0D) && (upper > 0.0D) && (lower > 0.0D))
      {



        double expVal = Math.log(upper) / LOG10_VALUE;
        expVal = Math.ceil(-expVal + 0.001D);
        expVal = Math.pow(10.0D, expVal);
        
        upper = expVal > 0.0D ? Math.ceil(upper * expVal) / expVal : Math.ceil(upper);


      }
      else
      {

        upper = autoRangeNextLogFlag ? computeLogCeil(upper) : Math.ceil(upper);
      }
      

      double minRange = getAutoRangeMinimumSize();
      if (upper - lower < minRange) {
        upper = (upper + lower + minRange) / 2.0D;
        lower = (upper + lower - minRange) / 2.0D;
        

        if (upper - lower < minRange) {
          double absUpper = Math.abs(upper);
          
          double adjVal = absUpper > 1.0E-100D ? absUpper / 100.0D : 0.01D;
          
          upper = (upper + lower + adjVal) / 2.0D;
          lower = (upper + lower - adjVal) / 2.0D;
        }
      }
      
      setRange(new Range(lower, upper), false, false);
      setupSmallLogFlag();
    }
  }
  













  public double valueToJava2D(double value, Rectangle2D plotArea, RectangleEdge edge)
  {
    Range range = getRange();
    double axisMin = switchedLog10(range.getLowerBound());
    double axisMax = switchedLog10(range.getUpperBound());
    
    double min = 0.0D;
    double max = 0.0D;
    if (RectangleEdge.isTopOrBottom(edge)) {
      min = plotArea.getMinX();
      max = plotArea.getMaxX();
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      min = plotArea.getMaxY();
      max = plotArea.getMinY();
    }
    
    value = switchedLog10(value);
    
    if (isInverted()) {
      return max - (value - axisMin) / (axisMax - axisMin) * (max - min);
    }
    

    return min + (value - axisMin) / (axisMax - axisMin) * (max - min);
  }
  















  public double java2DToValue(double java2DValue, Rectangle2D plotArea, RectangleEdge edge)
  {
    Range range = getRange();
    double axisMin = switchedLog10(range.getLowerBound());
    double axisMax = switchedLog10(range.getUpperBound());
    
    double plotMin = 0.0D;
    double plotMax = 0.0D;
    if (RectangleEdge.isTopOrBottom(edge)) {
      plotMin = plotArea.getX();
      plotMax = plotArea.getMaxX();
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      plotMin = plotArea.getMaxY();
      plotMax = plotArea.getMinY();
    }
    
    if (isInverted()) {
      return switchedPow10(axisMax - (java2DValue - plotMin) / (plotMax - plotMin) * (axisMax - axisMin));
    }
    

    return switchedPow10(axisMin + (java2DValue - plotMin) / (plotMax - plotMin) * (axisMax - axisMin));
  }
  







  public void zoomRange(double lowerPercent, double upperPercent)
  {
    double startLog = switchedLog10(getRange().getLowerBound());
    double lengthLog = switchedLog10(getRange().getUpperBound()) - startLog;
    Range adjusted;
    Range adjusted;
    if (isInverted()) {
      adjusted = new Range(switchedPow10(startLog + lengthLog * (1.0D - upperPercent)), switchedPow10(startLog + lengthLog * (1.0D - lowerPercent)));


    }
    else
    {

      adjusted = new Range(switchedPow10(startLog + lengthLog * lowerPercent), switchedPow10(startLog + lengthLog * upperPercent));
    }
    


    setRange(adjusted);
  }
  












  protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    List ticks = new ArrayList();
    Range range = getRange();
    

    double lowerBoundVal = range.getLowerBound();
    

    if ((smallLogFlag) && (lowerBoundVal < 1.0E-100D)) {
      lowerBoundVal = 1.0E-100D;
    }
    

    double upperBoundVal = range.getUpperBound();
    

    int iBegCount = (int)Math.rint(switchedLog10(lowerBoundVal));
    
    int iEndCount = (int)Math.rint(switchedLog10(upperBoundVal));
    
    if ((iBegCount == iEndCount) && (iBegCount > 0) && (Math.pow(10.0D, iBegCount) > lowerBoundVal))
    {


      iBegCount--;
    }
    


    boolean zeroTickFlag = false;
    for (int i = iBegCount; i <= iEndCount; i++)
    {
      for (int j = 0; j < 10; j++) { String tickLabel;
        double currentTickValue;
        String tickLabel; if (smallLogFlag)
        {
          double currentTickValue = Math.pow(10.0D, i) + Math.pow(10.0D, i) * j;
          String tickLabel; if ((expTickLabelsFlag) || ((i < 0) && (currentTickValue > 0.0D) && (currentTickValue < 1.0D)))
          {
            String tickLabel;
            

            if ((j == 0) || ((i > -4) && (j < 2)) || (currentTickValue >= upperBoundVal))
            {




              numberFormatterObj.setMaximumFractionDigits(-i);
              

              tickLabel = makeTickLabel(currentTickValue, true);
            }
            else {
              tickLabel = "";
            }
            

          }
          else
          {
            tickLabel = (j < 1) || ((i < 1) && (j < 5)) || (j < 4 - i) || (currentTickValue >= upperBoundVal) ? makeTickLabel(currentTickValue) : "";
          }
          
        }
        else
        {
          if (zeroTickFlag) {
            j--;
          }
          currentTickValue = i >= 0 ? Math.pow(10.0D, i) + Math.pow(10.0D, i) * j : -(Math.pow(10.0D, -i) - Math.pow(10.0D, -i - 1) * j);
          

          if (!zeroTickFlag) {
            if ((Math.abs(currentTickValue - 1.0D) < 1.0E-4D) && (lowerBoundVal <= 0.0D) && (upperBoundVal >= 0.0D))
            {

              currentTickValue = 0.0D;
              zeroTickFlag = true;
            }
          }
          else {
            zeroTickFlag = false;
          }
          



          tickLabel = ((expTickLabelsFlag) && (j < 2)) || (j < 1) || ((i < 1) && (j < 5)) || (j < 4 - i) || (currentTickValue >= upperBoundVal) ? makeTickLabel(currentTickValue) : "";
        }
        




        if (currentTickValue > upperBoundVal) {
          return ticks;
        }
        

        if (currentTickValue >= lowerBoundVal - 1.0E-100D)
        {
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
          
          ticks.add(tick);
        }
      }
    }
    return ticks;
  }
  













  protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    List ticks = new ArrayList();
    

    double lowerBoundVal = getRange().getLowerBound();
    

    if ((smallLogFlag) && (lowerBoundVal < 1.0E-100D)) {
      lowerBoundVal = 1.0E-100D;
    }
    
    double upperBoundVal = getRange().getUpperBound();
    

    int iBegCount = (int)Math.rint(switchedLog10(lowerBoundVal));
    
    int iEndCount = (int)Math.rint(switchedLog10(upperBoundVal));
    
    if ((iBegCount == iEndCount) && (iBegCount > 0) && (Math.pow(10.0D, iBegCount) > lowerBoundVal))
    {


      iBegCount--;
    }
    


    boolean zeroTickFlag = false;
    for (int i = iBegCount; i <= iEndCount; i++)
    {
      int jEndCount = 10;
      if (i == iEndCount) {
        jEndCount = 1;
      }
      
      for (int j = 0; j < jEndCount; j++) { String tickLabel;
        double tickVal;
        String tickLabel; if (smallLogFlag)
        {
          double tickVal = Math.pow(10.0D, i) + Math.pow(10.0D, i) * j;
          String tickLabel; if (j == 0) {
            String tickLabel;
            if (log10TickLabelsFlag)
            {
              tickLabel = "10^" + i;
            } else {
              String tickLabel;
              if (expTickLabelsFlag)
              {
                tickLabel = "1e" + i;
              } else {
                String tickLabel;
                if (i >= 0)
                {
                  NumberFormat format = getNumberFormatOverride();
                  String tickLabel;
                  if (format != null) {
                    tickLabel = format.format(tickVal);
                  }
                  else {
                    tickLabel = Long.toString(Math.rint(tickVal));
                  }
                  

                }
                else
                {

                  numberFormatterObj.setMaximumFractionDigits(-i);
                  

                  tickLabel = numberFormatterObj.format(tickVal);
                }
              }
            }
          }
          else
          {
            tickLabel = "";
          }
        }
        else {
          if (zeroTickFlag) {
            j--;
          }
          tickVal = i >= 0 ? Math.pow(10.0D, i) + Math.pow(10.0D, i) * j : -(Math.pow(10.0D, -i) - Math.pow(10.0D, -i - 1) * j);
          
          if (j == 0) { String tickLabel;
            if (!zeroTickFlag) {
              String tickLabel;
              if ((i > iBegCount) && (i < iEndCount) && (Math.abs(tickVal - 1.0D) < 1.0E-4D))
              {


                tickVal = 0.0D;
                zeroTickFlag = true;
                tickLabel = "0";
              }
              else
              {
                String tickLabel;
                if (log10TickLabelsFlag)
                {
                  tickLabel = (i < 0 ? "-" : "") + "10^" + Math.abs(i);
                }
                else {
                  String tickLabel;
                  if (expTickLabelsFlag)
                  {
                    tickLabel = (i < 0 ? "-" : "") + "1e" + Math.abs(i);
                  }
                  else
                  {
                    NumberFormat format = getNumberFormatOverride();
                    String tickLabel;
                    if (format != null) {
                      tickLabel = format.format(tickVal);
                    }
                    else {
                      tickLabel = Long.toString(Math.rint(tickVal));
                    }
                  }
                }
              }
            }
            else
            {
              String tickLabel = "";
              zeroTickFlag = false;
            }
          }
          else {
            tickLabel = "";
            zeroTickFlag = false;
          }
        }
        
        if (tickVal > upperBoundVal) {
          return ticks;
        }
        
        if (tickVal >= lowerBoundVal - 1.0E-100D)
        {
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
          

          ticks.add(new NumberTick(new Double(tickVal), tickLabel, anchor, rotationAnchor, angle));
        }
      }
    }
    
    return ticks;
  }
  








  protected String makeTickLabel(double val, boolean forceFmtFlag)
  {
    if ((expTickLabelsFlag) || (forceFmtFlag))
    {

      return numberFormatterObj.format(val).toLowerCase();
    }
    return getTickUnit().valueToString(val);
  }
  





  protected String makeTickLabel(double val)
  {
    return makeTickLabel(val, false);
  }
}
