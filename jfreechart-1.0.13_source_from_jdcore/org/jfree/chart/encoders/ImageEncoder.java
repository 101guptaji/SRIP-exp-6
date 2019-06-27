package org.jfree.chart.encoders;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public abstract interface ImageEncoder
{
  public abstract byte[] encode(BufferedImage paramBufferedImage)
    throws IOException;
  
  public abstract void encode(BufferedImage paramBufferedImage, OutputStream paramOutputStream)
    throws IOException;
  
  public abstract float getQuality();
  
  public abstract void setQuality(float paramFloat);
  
  public abstract boolean isEncodingAlpha();
  
  public abstract void setEncodingAlpha(boolean paramBoolean);
}
