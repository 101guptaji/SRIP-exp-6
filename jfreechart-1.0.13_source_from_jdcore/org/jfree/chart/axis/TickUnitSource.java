package org.jfree.chart.axis;

public abstract interface TickUnitSource
{
  public abstract TickUnit getLargerTickUnit(TickUnit paramTickUnit);
  
  public abstract TickUnit getCeilingTickUnit(TickUnit paramTickUnit);
  
  public abstract TickUnit getCeilingTickUnit(double paramDouble);
}
