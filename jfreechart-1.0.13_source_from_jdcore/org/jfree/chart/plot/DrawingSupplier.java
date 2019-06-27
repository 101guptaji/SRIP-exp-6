package org.jfree.chart.plot;

import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;

public abstract interface DrawingSupplier
{
  public abstract Paint getNextPaint();
  
  public abstract Paint getNextOutlinePaint();
  
  public abstract Paint getNextFillPaint();
  
  public abstract Stroke getNextStroke();
  
  public abstract Stroke getNextOutlineStroke();
  
  public abstract Shape getNextShape();
}
