package org.jfree.chart.plot.dial;

public abstract interface DialScale
  extends DialLayer
{
  public abstract double valueToAngle(double paramDouble);
  
  public abstract double angleToValue(double paramDouble);
}
