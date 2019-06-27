package org.jfree.chart.axis;

import java.util.ArrayList;
import java.util.List;
import org.jfree.ui.RectangleEdge;
























































public class AxisState
{
  private double cursor;
  private List ticks;
  private double max;
  
  public AxisState()
  {
    this(0.0D);
  }
  




  public AxisState(double cursor)
  {
    this.cursor = cursor;
    ticks = new ArrayList();
  }
  




  public double getCursor()
  {
    return cursor;
  }
  




  public void setCursor(double cursor)
  {
    this.cursor = cursor;
  }
  





  public void moveCursor(double units, RectangleEdge edge)
  {
    if (edge == RectangleEdge.TOP) {
      cursorUp(units);
    }
    else if (edge == RectangleEdge.BOTTOM) {
      cursorDown(units);
    }
    else if (edge == RectangleEdge.LEFT) {
      cursorLeft(units);
    }
    else if (edge == RectangleEdge.RIGHT) {
      cursorRight(units);
    }
  }
  




  public void cursorUp(double units)
  {
    cursor -= units;
  }
  




  public void cursorDown(double units)
  {
    cursor += units;
  }
  




  public void cursorLeft(double units)
  {
    cursor -= units;
  }
  




  public void cursorRight(double units)
  {
    cursor += units;
  }
  




  public List getTicks()
  {
    return ticks;
  }
  




  public void setTicks(List ticks)
  {
    this.ticks = ticks;
  }
  




  public double getMax()
  {
    return max;
  }
  




  public void setMax(double max)
  {
    this.max = max;
  }
}
