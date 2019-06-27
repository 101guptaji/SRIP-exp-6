package org.jfree.chart.axis;

import java.awt.geom.Rectangle2D;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.data.Range;
import org.jfree.ui.RectangleEdge;
































































public class ModuloAxis
  extends NumberAxis
{
  private Range fixedRange;
  private double displayStart;
  private double displayEnd;
  
  public ModuloAxis(String label, Range fixedRange)
  {
    super(label);
    this.fixedRange = fixedRange;
    displayStart = 270.0D;
    displayEnd = 90.0D;
  }
  




  public double getDisplayStart()
  {
    return displayStart;
  }
  




  public double getDisplayEnd()
  {
    return displayEnd;
  }
  






  public void setDisplayRange(double start, double end)
  {
    displayStart = mapValueToFixedRange(start);
    displayEnd = mapValueToFixedRange(end);
    if (displayStart < displayEnd) {
      setRange(displayStart, displayEnd);
    }
    else {
      setRange(displayStart, fixedRange.getUpperBound() + (displayEnd - fixedRange.getLowerBound()));
    }
    
    notifyListeners(new AxisChangeEvent(this));
  }
  



  protected void autoAdjustRange()
  {
    setRange(fixedRange, false, false);
  }
  









  public double valueToJava2D(double value, Rectangle2D area, RectangleEdge edge)
  {
    double result = 0.0D;
    double v = mapValueToFixedRange(value);
    if (displayStart < displayEnd) {
      result = trans(v, area, edge);
    }
    else {
      double cutoff = (displayStart + displayEnd) / 2.0D;
      double length1 = fixedRange.getUpperBound() - displayStart;
      
      double length2 = displayEnd - fixedRange.getLowerBound();
      if (v > cutoff) {
        result = transStart(v, area, edge, length1, length2);
      }
      else {
        result = transEnd(v, area, edge, length1, length2);
      }
    }
    return result;
  }
  








  private double trans(double value, Rectangle2D area, RectangleEdge edge)
  {
    double min = 0.0D;
    double max = 0.0D;
    if (RectangleEdge.isTopOrBottom(edge)) {
      min = area.getX();
      max = area.getX() + area.getWidth();
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      min = area.getMaxY();
      max = area.getMaxY() - area.getHeight();
    }
    if (isInverted()) {
      return max - (value - displayStart) / (displayEnd - displayStart) * (max - min);
    }
    

    return min + (value - displayStart) / (displayEnd - displayStart) * (max - min);
  }
  
















  private double transStart(double value, Rectangle2D area, RectangleEdge edge, double length1, double length2)
  {
    double min = 0.0D;
    double max = 0.0D;
    if (RectangleEdge.isTopOrBottom(edge)) {
      min = area.getX();
      max = area.getX() + area.getWidth() * length1 / (length1 + length2);
    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      min = area.getMaxY();
      max = area.getMaxY() - area.getHeight() * length1 / (length1 + length2);
    }
    
    if (isInverted()) {
      return max - (value - displayStart) / (fixedRange.getUpperBound() - displayStart) * (max - min);
    }
    


    return min + (value - displayStart) / (fixedRange.getUpperBound() - displayStart) * (max - min);
  }
  
















  private double transEnd(double value, Rectangle2D area, RectangleEdge edge, double length1, double length2)
  {
    double min = 0.0D;
    double max = 0.0D;
    if (RectangleEdge.isTopOrBottom(edge)) {
      max = area.getMaxX();
      min = area.getMaxX() - area.getWidth() * length2 / (length1 + length2);

    }
    else if (RectangleEdge.isLeftOrRight(edge)) {
      max = area.getMinY();
      min = area.getMinY() + area.getHeight() * length2 / (length1 + length2);
    }
    
    if (isInverted()) {
      return max - (value - fixedRange.getLowerBound()) / (displayEnd - fixedRange.getLowerBound()) * (max - min);
    }
    


    return min + (value - fixedRange.getLowerBound()) / (displayEnd - fixedRange.getLowerBound()) * (max - min);
  }
  










  private double mapValueToFixedRange(double value)
  {
    double lower = fixedRange.getLowerBound();
    double length = fixedRange.getLength();
    if (value < lower) {
      return lower + length + (value - lower) % length;
    }
    
    return lower + (value - lower) % length;
  }
  










  public double java2DToValue(double java2DValue, Rectangle2D area, RectangleEdge edge)
  {
    double result = 0.0D;
    if (displayStart < displayEnd) {
      result = super.java2DToValue(java2DValue, area, edge);
    }
    


    return result;
  }
  




  private double getDisplayLength()
  {
    if (displayStart < displayEnd) {
      return displayEnd - displayStart;
    }
    
    return fixedRange.getUpperBound() - displayStart + (displayEnd - fixedRange.getLowerBound());
  }
  






  private double getDisplayCentralValue()
  {
    return mapValueToFixedRange(displayStart + getDisplayLength() / 2.0D);
  }
  











  public void resizeRange(double percent)
  {
    resizeRange(percent, getDisplayCentralValue());
  }
  











  public void resizeRange(double percent, double anchorValue)
  {
    if (percent > 0.0D) {
      double halfLength = getDisplayLength() * percent / 2.0D;
      setDisplayRange(anchorValue - halfLength, anchorValue + halfLength);
    }
    else {
      setAutoRange(true);
    }
  }
  











  public double lengthToJava2D(double length, Rectangle2D area, RectangleEdge edge)
  {
    double axisLength = 0.0D;
    if (displayEnd > displayStart) {
      axisLength = displayEnd - displayStart;
    }
    else {
      axisLength = fixedRange.getUpperBound() - displayStart + (displayEnd - fixedRange.getLowerBound());
    }
    
    double areaLength = 0.0D;
    if (RectangleEdge.isLeftOrRight(edge)) {
      areaLength = area.getHeight();
    }
    else {
      areaLength = area.getWidth();
    }
    return length / axisLength * areaLength;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ModuloAxis)) {
      return false;
    }
    ModuloAxis that = (ModuloAxis)obj;
    if (displayStart != displayStart) {
      return false;
    }
    if (displayEnd != displayEnd) {
      return false;
    }
    if (!fixedRange.equals(fixedRange)) {
      return false;
    }
    return super.equals(obj);
  }
}
