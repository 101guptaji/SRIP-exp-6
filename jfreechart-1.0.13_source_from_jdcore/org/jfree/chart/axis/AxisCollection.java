package org.jfree.chart.axis;

import java.util.ArrayList;
import java.util.List;
import org.jfree.ui.RectangleEdge;























































public class AxisCollection
{
  private List axesAtTop;
  private List axesAtBottom;
  private List axesAtLeft;
  private List axesAtRight;
  
  public AxisCollection()
  {
    axesAtTop = new ArrayList();
    axesAtBottom = new ArrayList();
    axesAtLeft = new ArrayList();
    axesAtRight = new ArrayList();
  }
  





  public List getAxesAtTop()
  {
    return axesAtTop;
  }
  





  public List getAxesAtBottom()
  {
    return axesAtBottom;
  }
  





  public List getAxesAtLeft()
  {
    return axesAtLeft;
  }
  





  public List getAxesAtRight()
  {
    return axesAtRight;
  }
  






  public void add(Axis axis, RectangleEdge edge)
  {
    if (axis == null) {
      throw new IllegalArgumentException("Null 'axis' argument.");
    }
    if (edge == null) {
      throw new IllegalArgumentException("Null 'edge' argument.");
    }
    if (edge == RectangleEdge.TOP) {
      axesAtTop.add(axis);
    }
    else if (edge == RectangleEdge.BOTTOM) {
      axesAtBottom.add(axis);
    }
    else if (edge == RectangleEdge.LEFT) {
      axesAtLeft.add(axis);
    }
    else if (edge == RectangleEdge.RIGHT) {
      axesAtRight.add(axis);
    }
  }
}
