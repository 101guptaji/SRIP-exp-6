package org.jfree.chart.axis;

import org.jfree.ui.TextAnchor;































































public abstract class ValueTick
  extends Tick
{
  private double value;
  private TickType tickType;
  
  public ValueTick(double value, String label, TextAnchor textAnchor, TextAnchor rotationAnchor, double angle)
  {
    this(TickType.MAJOR, value, label, textAnchor, rotationAnchor, angle);
    this.value = value;
  }
  
















  public ValueTick(TickType tickType, double value, String label, TextAnchor textAnchor, TextAnchor rotationAnchor, double angle)
  {
    super(label, textAnchor, rotationAnchor, angle);
    this.value = value;
    this.tickType = tickType;
  }
  




  public double getValue()
  {
    return value;
  }
  






  public TickType getTickType()
  {
    return tickType;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ValueTick)) {
      return false;
    }
    ValueTick that = (ValueTick)obj;
    if (value != value) {
      return false;
    }
    if (!tickType.equals(tickType)) {
      return false;
    }
    return super.equals(obj);
  }
}
