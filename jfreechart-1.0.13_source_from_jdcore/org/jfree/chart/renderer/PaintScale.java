package org.jfree.chart.renderer;

import java.awt.Paint;

public abstract interface PaintScale
{
  public abstract double getLowerBound();
  
  public abstract double getUpperBound();
  
  public abstract Paint getPaint(double paramDouble);
}
