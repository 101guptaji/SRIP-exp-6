package org.jfree.ui;

import java.awt.Dimension;

public abstract interface ExtendedDrawable
  extends Drawable
{
  public abstract Dimension getPreferredSize();
  
  public abstract boolean isPreserveAspectRatio();
}
