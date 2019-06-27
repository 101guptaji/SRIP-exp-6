package org.jfree.chart.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.data.Range;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.PaintUtilities;




















































































public class SymbolAxis
  extends NumberAxis
  implements Serializable
{
  private static final long serialVersionUID = 7216330468770619716L;
  public static final Paint DEFAULT_GRID_BAND_PAINT = new Color(232, 234, 232, 128);
  






  public static final Paint DEFAULT_GRID_BAND_ALTERNATE_PAINT = new Color(0, 0, 0, 0);
  



  private List symbols;
  



  private boolean gridBandsVisible;
  



  private transient Paint gridBandPaint;
  



  private transient Paint gridBandAlternatePaint;
  




  public SymbolAxis(String label, String[] sv)
  {
    super(label);
    symbols = Arrays.asList(sv);
    gridBandsVisible = true;
    gridBandPaint = DEFAULT_GRID_BAND_PAINT;
    gridBandAlternatePaint = DEFAULT_GRID_BAND_ALTERNATE_PAINT;
    setAutoTickUnitSelection(false, false);
    setAutoRangeStickyZero(false);
  }
  





  public String[] getSymbols()
  {
    String[] result = new String[symbols.size()];
    result = (String[])symbols.toArray(result);
    return result;
  }
  








  public boolean isGridBandsVisible()
  {
    return gridBandsVisible;
  }
  







  public void setGridBandsVisible(boolean flag)
  {
    if (gridBandsVisible != flag) {
      gridBandsVisible = flag;
      notifyListeners(new AxisChangeEvent(this));
    }
  }
  







  public Paint getGridBandPaint()
  {
    return gridBandPaint;
  }
  







  public void setGridBandPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    gridBandPaint = paint;
    notifyListeners(new AxisChangeEvent(this));
  }
  









  public Paint getGridBandAlternatePaint()
  {
    return gridBandAlternatePaint;
  }
  










  public void setGridBandAlternatePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    gridBandAlternatePaint = paint;
    notifyListeners(new AxisChangeEvent(this));
  }
  







  protected void selectAutoTickUnit(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    throw new UnsupportedOperationException();
  }
  





















  public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState)
  {
    AxisState info = new AxisState(cursor);
    if (isVisible()) {
      info = super.draw(g2, cursor, plotArea, dataArea, edge, plotState);
    }
    if (gridBandsVisible) {
      drawGridBands(g2, plotArea, dataArea, edge, info.getTicks());
    }
    return info;
  }
  

















  protected void drawGridBands(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, List ticks)
  {
    Shape savedClip = g2.getClip();
    g2.clip(dataArea);
    if (RectangleEdge.isTopOrBottom(edge)) {
      drawGridBandsHorizontal(g2, plotArea, dataArea, true, ticks);
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      drawGridBandsVertical(g2, plotArea, dataArea, true, ticks);
    }
    g2.setClip(savedClip);
  }
  



















  protected void drawGridBandsHorizontal(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, boolean firstGridBandIsDark, List ticks)
  {
    boolean currentGridBandIsDark = firstGridBandIsDark;
    double yy = dataArea.getY();
    
    double outlineStrokeWidth;
    
    double outlineStrokeWidth;
    if (getPlot().getOutlineStroke() != null) {
      outlineStrokeWidth = ((BasicStroke)getPlot().getOutlineStroke()).getLineWidth();
    }
    else
    {
      outlineStrokeWidth = 1.0D;
    }
    
    Iterator iterator = ticks.iterator();
    

    while (iterator.hasNext()) {
      ValueTick tick = (ValueTick)iterator.next();
      double xx1 = valueToJava2D(tick.getValue() - 0.5D, dataArea, RectangleEdge.BOTTOM);
      
      double xx2 = valueToJava2D(tick.getValue() + 0.5D, dataArea, RectangleEdge.BOTTOM);
      
      if (currentGridBandIsDark) {
        g2.setPaint(gridBandPaint);
      }
      else {
        g2.setPaint(gridBandAlternatePaint);
      }
      Rectangle2D band = new Rectangle2D.Double(xx1, yy + outlineStrokeWidth, xx2 - xx1, dataArea.getMaxY() - yy - outlineStrokeWidth);
      
      g2.fill(band);
      currentGridBandIsDark = !currentGridBandIsDark;
    }
    g2.setPaintMode();
  }
  


















  protected void drawGridBandsVertical(Graphics2D g2, Rectangle2D drawArea, Rectangle2D plotArea, boolean firstGridBandIsDark, List ticks)
  {
    boolean currentGridBandIsDark = firstGridBandIsDark;
    double xx = plotArea.getX();
    



    Stroke outlineStroke = getPlot().getOutlineStroke();
    double outlineStrokeWidth; double outlineStrokeWidth; if ((outlineStroke != null) && ((outlineStroke instanceof BasicStroke))) {
      outlineStrokeWidth = ((BasicStroke)outlineStroke).getLineWidth();
    }
    else {
      outlineStrokeWidth = 1.0D;
    }
    
    Iterator iterator = ticks.iterator();
    

    while (iterator.hasNext()) {
      ValueTick tick = (ValueTick)iterator.next();
      double yy1 = valueToJava2D(tick.getValue() + 0.5D, plotArea, RectangleEdge.LEFT);
      
      double yy2 = valueToJava2D(tick.getValue() - 0.5D, plotArea, RectangleEdge.LEFT);
      
      if (currentGridBandIsDark) {
        g2.setPaint(gridBandPaint);
      }
      else {
        g2.setPaint(gridBandAlternatePaint);
      }
      Rectangle2D band = new Rectangle2D.Double(xx + outlineStrokeWidth, yy1, plotArea.getMaxX() - xx - outlineStrokeWidth, yy2 - yy1);
      
      g2.fill(band);
      currentGridBandIsDark = !currentGridBandIsDark;
    }
    g2.setPaintMode();
  }
  



  protected void autoAdjustRange()
  {
    Plot plot = getPlot();
    if (plot == null) {
      return;
    }
    
    if ((plot instanceof ValueAxisPlot))
    {

      double upper = symbols.size() - 1;
      double lower = 0.0D;
      double range = upper - lower;
      

      double minRange = getAutoRangeMinimumSize();
      if (range < minRange) {
        upper = (upper + lower + minRange) / 2.0D;
        lower = (upper + lower - minRange) / 2.0D;
      }
      

      double upperMargin = 0.5D;
      double lowerMargin = 0.5D;
      
      if (getAutoRangeIncludesZero()) {
        if (getAutoRangeStickyZero()) {
          if (upper <= 0.0D) {
            upper = 0.0D;
          }
          else {
            upper += upperMargin;
          }
          if (lower >= 0.0D) {
            lower = 0.0D;
          }
          else {
            lower -= lowerMargin;
          }
        }
        else {
          upper = Math.max(0.0D, upper + upperMargin);
          lower = Math.min(0.0D, lower - lowerMargin);
        }
        
      }
      else if (getAutoRangeStickyZero()) {
        if (upper <= 0.0D) {
          upper = Math.min(0.0D, upper + upperMargin);
        }
        else {
          upper += upperMargin * range;
        }
        if (lower >= 0.0D) {
          lower = Math.max(0.0D, lower - lowerMargin);
        }
        else {
          lower -= lowerMargin;
        }
      }
      else {
        upper += upperMargin;
        lower -= lowerMargin;
      }
      

      setRange(new Range(lower, upper), false, false);
    }
  }
  















  public List refreshTicks(Graphics2D g2, AxisState state, Rectangle2D dataArea, RectangleEdge edge)
  {
    List ticks = null;
    if (RectangleEdge.isTopOrBottom(edge)) {
      ticks = refreshTicksHorizontal(g2, dataArea, edge);
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      ticks = refreshTicksVertical(g2, dataArea, edge);
    }
    return ticks;
  }
  












  protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    List ticks = new ArrayList();
    
    Font tickLabelFont = getTickLabelFont();
    g2.setFont(tickLabelFont);
    
    double size = getTickUnit().getSize();
    int count = calculateVisibleTickCount();
    double lowestTickValue = calculateLowestVisibleTickValue();
    
    double previousDrawnTickLabelPos = 0.0D;
    double previousDrawnTickLabelLength = 0.0D;
    
    if (count <= 500) {
      for (int i = 0; i < count; i++) {
        double currentTickValue = lowestTickValue + i * size;
        double xx = valueToJava2D(currentTickValue, dataArea, edge);
        
        NumberFormat formatter = getNumberFormatOverride();
        String tickLabel; String tickLabel; if (formatter != null) {
          tickLabel = formatter.format(currentTickValue);
        }
        else {
          tickLabel = valueToString(currentTickValue);
        }
        

        Rectangle2D bounds = TextUtilities.getTextBounds(tickLabel, g2, g2.getFontMetrics());
        
        double tickLabelLength = isVerticalTickLabels() ? bounds.getHeight() : bounds.getWidth();
        
        boolean tickLabelsOverlapping = false;
        if (i > 0) {
          double avgTickLabelLength = (previousDrawnTickLabelLength + tickLabelLength) / 2.0D;
          
          if (Math.abs(xx - previousDrawnTickLabelPos) < avgTickLabelLength)
          {
            tickLabelsOverlapping = true;
          }
        }
        if (tickLabelsOverlapping) {
          tickLabel = "";
        }
        else
        {
          previousDrawnTickLabelPos = xx;
          previousDrawnTickLabelLength = tickLabelLength;
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
        
        ticks.add(tick);
      }
    }
    return ticks;
  }
  













  protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge)
  {
    List ticks = new ArrayList();
    
    Font tickLabelFont = getTickLabelFont();
    g2.setFont(tickLabelFont);
    
    double size = getTickUnit().getSize();
    int count = calculateVisibleTickCount();
    double lowestTickValue = calculateLowestVisibleTickValue();
    
    double previousDrawnTickLabelPos = 0.0D;
    double previousDrawnTickLabelLength = 0.0D;
    
    if (count <= 500) {
      for (int i = 0; i < count; i++) {
        double currentTickValue = lowestTickValue + i * size;
        double yy = valueToJava2D(currentTickValue, dataArea, edge);
        
        NumberFormat formatter = getNumberFormatOverride();
        String tickLabel; String tickLabel; if (formatter != null) {
          tickLabel = formatter.format(currentTickValue);
        }
        else {
          tickLabel = valueToString(currentTickValue);
        }
        

        Rectangle2D bounds = TextUtilities.getTextBounds(tickLabel, g2, g2.getFontMetrics());
        
        double tickLabelLength = isVerticalTickLabels() ? bounds.getWidth() : bounds.getHeight();
        
        boolean tickLabelsOverlapping = false;
        if (i > 0) {
          double avgTickLabelLength = (previousDrawnTickLabelLength + tickLabelLength) / 2.0D;
          
          if (Math.abs(yy - previousDrawnTickLabelPos) < avgTickLabelLength)
          {
            tickLabelsOverlapping = true;
          }
        }
        if (tickLabelsOverlapping) {
          tickLabel = "";
        }
        else
        {
          previousDrawnTickLabelPos = yy;
          previousDrawnTickLabelLength = tickLabelLength;
        }
        
        TextAnchor anchor = null;
        TextAnchor rotationAnchor = null;
        double angle = 0.0D;
        if (isVerticalTickLabels()) {
          anchor = TextAnchor.BOTTOM_CENTER;
          rotationAnchor = TextAnchor.BOTTOM_CENTER;
          if (edge == RectangleEdge.LEFT) {
            angle = -1.5707963267948966D;
          }
          else {
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
        
        ticks.add(tick);
      }
    }
    return ticks;
  }
  



  public String valueToString(double value)
  {
    String strToReturn;
    


    try
    {
      strToReturn = (String)symbols.get((int)value);
    }
    catch (IndexOutOfBoundsException ex) {
      strToReturn = "";
    }
    return strToReturn;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof SymbolAxis)) {
      return false;
    }
    SymbolAxis that = (SymbolAxis)obj;
    if (!symbols.equals(symbols)) {
      return false;
    }
    if (gridBandsVisible != gridBandsVisible) {
      return false;
    }
    if (!PaintUtilities.equal(gridBandPaint, gridBandPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(gridBandAlternatePaint, gridBandAlternatePaint))
    {
      return false;
    }
    return super.equals(obj);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(gridBandPaint, stream);
    SerialUtilities.writePaint(gridBandAlternatePaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    gridBandPaint = SerialUtilities.readPaint(stream);
    gridBandAlternatePaint = SerialUtilities.readPaint(stream);
  }
}
