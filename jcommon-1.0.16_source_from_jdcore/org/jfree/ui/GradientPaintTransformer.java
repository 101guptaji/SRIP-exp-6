package org.jfree.ui;

import java.awt.GradientPaint;
import java.awt.Shape;

public abstract interface GradientPaintTransformer
{
  public abstract GradientPaint transform(GradientPaint paramGradientPaint, Shape paramShape);
}
