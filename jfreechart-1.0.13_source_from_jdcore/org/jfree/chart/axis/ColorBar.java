package org.jfree.chart.axis;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import org.jfree.chart.plot.ColorPalette;
import org.jfree.chart.plot.ContourPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.RainbowPalette;
import org.jfree.data.Range;
import org.jfree.data.contour.ContourDataset;
import org.jfree.ui.RectangleEdge;


































































/**
 * @deprecated
 */
public class ColorBar
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -2101776212647268103L;
  public static final int DEFAULT_COLORBAR_THICKNESS = 0;
  public static final double DEFAULT_COLORBAR_THICKNESS_PERCENT = 0.1D;
  public static final int DEFAULT_OUTERGAP = 2;
  private ValueAxis axis;
  private int colorBarThickness = 0;
  



  private double colorBarThicknessPercent = 0.1D;
  


  private ColorPalette colorPalette = null;
  

  private int colorBarLength = 0;
  



  private int outerGap;
  




  public ColorBar(String label)
  {
    NumberAxis a = new NumberAxis(label);
    a.setAutoRangeIncludesZero(false);
    axis = a;
    axis.setLowerMargin(0.0D);
    axis.setUpperMargin(0.0D);
    
    colorPalette = new RainbowPalette();
    colorBarThickness = 0;
    colorBarThicknessPercent = 0.1D;
    outerGap = 2;
    colorPalette.setMinZ(axis.getRange().getLowerBound());
    colorPalette.setMaxZ(axis.getRange().getUpperBound());
  }
  





  public void configure(ContourPlot plot)
  {
    double minZ = plot.getDataset().getMinZValue();
    double maxZ = plot.getDataset().getMaxZValue();
    setMinimumValue(minZ);
    setMaximumValue(maxZ);
  }
  




  public ValueAxis getAxis()
  {
    return axis;
  }
  




  public void setAxis(ValueAxis axis)
  {
    this.axis = axis;
  }
  


  public void autoAdjustRange()
  {
    axis.autoAdjustRange();
    colorPalette.setMinZ(axis.getLowerBound());
    colorPalette.setMaxZ(axis.getUpperBound());
  }
  

















  public double draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, Rectangle2D reservedArea, RectangleEdge edge)
  {
    Rectangle2D colorBarArea = null;
    
    double thickness = calculateBarThickness(dataArea, edge);
    if (colorBarThickness > 0) {
      thickness = colorBarThickness;
    }
    
    double length = 0.0D;
    if (RectangleEdge.isLeftOrRight(edge)) {
      length = dataArea.getHeight();
    }
    else {
      length = dataArea.getWidth();
    }
    
    if (colorBarLength > 0) {
      length = colorBarLength;
    }
    
    if (edge == RectangleEdge.BOTTOM) {
      colorBarArea = new Rectangle2D.Double(dataArea.getX(), plotArea.getMaxY() + outerGap, length, thickness);

    }
    else if (edge == RectangleEdge.TOP) {
      colorBarArea = new Rectangle2D.Double(dataArea.getX(), reservedArea.getMinY() + outerGap, length, thickness);

    }
    else if (edge == RectangleEdge.LEFT) {
      colorBarArea = new Rectangle2D.Double(plotArea.getX() - thickness - outerGap, dataArea.getMinY(), thickness, length);

    }
    else if (edge == RectangleEdge.RIGHT) {
      colorBarArea = new Rectangle2D.Double(plotArea.getMaxX() + outerGap, dataArea.getMinY(), thickness, length);
    }
    


    axis.refreshTicks(g2, new AxisState(), colorBarArea, edge);
    
    drawColorBar(g2, colorBarArea, edge);
    
    AxisState state = null;
    if (edge == RectangleEdge.TOP) {
      cursor = colorBarArea.getMinY();
      state = axis.draw(g2, cursor, reservedArea, colorBarArea, RectangleEdge.TOP, null);

    }
    else if (edge == RectangleEdge.BOTTOM) {
      cursor = colorBarArea.getMaxY();
      state = axis.draw(g2, cursor, reservedArea, colorBarArea, RectangleEdge.BOTTOM, null);

    }
    else if (edge == RectangleEdge.LEFT) {
      cursor = colorBarArea.getMinX();
      state = axis.draw(g2, cursor, reservedArea, colorBarArea, RectangleEdge.LEFT, null);

    }
    else if (edge == RectangleEdge.RIGHT) {
      cursor = colorBarArea.getMaxX();
      state = axis.draw(g2, cursor, reservedArea, colorBarArea, RectangleEdge.RIGHT, null);
    }
    
    return state.getCursor();
  }
  










  public void drawColorBar(Graphics2D g2, Rectangle2D colorBarArea, RectangleEdge edge)
  {
    Object antiAlias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    




    Stroke strokeSaved = g2.getStroke();
    g2.setStroke(new BasicStroke(1.0F));
    
    if (RectangleEdge.isTopOrBottom(edge)) {
      double y1 = colorBarArea.getY();
      double y2 = colorBarArea.getMaxY();
      double xx = colorBarArea.getX();
      Line2D line = new Line2D.Double();
      while (xx <= colorBarArea.getMaxX()) {
        double value = axis.java2DToValue(xx, colorBarArea, edge);
        line.setLine(xx, y1, xx, y2);
        g2.setPaint(getPaint(value));
        g2.draw(line);
        xx += 1.0D;
      }
    }
    else {
      double y1 = colorBarArea.getX();
      double y2 = colorBarArea.getMaxX();
      double xx = colorBarArea.getY();
      Line2D line = new Line2D.Double();
      while (xx <= colorBarArea.getMaxY()) {
        double value = axis.java2DToValue(xx, colorBarArea, edge);
        line.setLine(y1, xx, y2, xx);
        g2.setPaint(getPaint(value));
        g2.draw(line);
        xx += 1.0D;
      }
    }
    
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias);
    g2.setStroke(strokeSaved);
  }
  





  public ColorPalette getColorPalette()
  {
    return colorPalette;
  }
  






  public Paint getPaint(double value)
  {
    return colorPalette.getPaint(value);
  }
  




  public void setColorPalette(ColorPalette palette)
  {
    colorPalette = palette;
  }
  




  public void setMaximumValue(double value)
  {
    colorPalette.setMaxZ(value);
    axis.setUpperBound(value);
  }
  




  public void setMinimumValue(double value)
  {
    colorPalette.setMinZ(value);
    axis.setLowerBound(value);
  }
  















  public AxisSpace reserveSpace(Graphics2D g2, Plot plot, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, AxisSpace space)
  {
    AxisSpace result = axis.reserveSpace(g2, plot, plotArea, edge, space);
    
    double thickness = calculateBarThickness(dataArea, edge);
    result.add(thickness + 2 * outerGap, edge);
    return result;
  }
  









  private double calculateBarThickness(Rectangle2D plotArea, RectangleEdge edge)
  {
    double result = 0.0D;
    if (RectangleEdge.isLeftOrRight(edge)) {
      result = plotArea.getWidth() * colorBarThicknessPercent;
    }
    else {
      result = plotArea.getHeight() * colorBarThicknessPercent;
    }
    return result;
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    ColorBar clone = (ColorBar)super.clone();
    axis = ((ValueAxis)axis.clone());
    return clone;
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ColorBar)) {
      return false;
    }
    ColorBar that = (ColorBar)obj;
    if (!axis.equals(axis)) {
      return false;
    }
    if (colorBarThickness != colorBarThickness) {
      return false;
    }
    if (colorBarThicknessPercent != colorBarThicknessPercent) {
      return false;
    }
    if (!colorPalette.equals(colorPalette)) {
      return false;
    }
    if (colorBarLength != colorBarLength) {
      return false;
    }
    if (outerGap != outerGap) {
      return false;
    }
    return true;
  }
  





  public int hashCode()
  {
    return axis.hashCode();
  }
}
