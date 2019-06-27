package org.jfree.chart.renderer.xy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.Effect3D;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PaintUtilities;






















































public class XYLine3DRenderer
  extends XYLineAndShapeRenderer
  implements Effect3D, Serializable
{
  private static final long serialVersionUID = 588933208243446087L;
  public static final double DEFAULT_X_OFFSET = 12.0D;
  public static final double DEFAULT_Y_OFFSET = 8.0D;
  public static final Paint DEFAULT_WALL_PAINT = new Color(221, 221, 221);
  

  private double xOffset;
  

  private double yOffset;
  

  private transient Paint wallPaint;
  


  public XYLine3DRenderer()
  {
    wallPaint = DEFAULT_WALL_PAINT;
    xOffset = 12.0D;
    yOffset = 8.0D;
  }
  




  public double getXOffset()
  {
    return xOffset;
  }
  




  public double getYOffset()
  {
    return yOffset;
  }
  





  public void setXOffset(double xOffset)
  {
    this.xOffset = xOffset;
    fireChangeEvent();
  }
  





  public void setYOffset(double yOffset)
  {
    this.yOffset = yOffset;
    fireChangeEvent();
  }
  





  public Paint getWallPaint()
  {
    return wallPaint;
  }
  






  public void setWallPaint(Paint paint)
  {
    wallPaint = paint;
    fireChangeEvent();
  }
  






  public int getPassCount()
  {
    return 3;
  }
  






  protected boolean isLinePass(int pass)
  {
    return (pass == 0) || (pass == 1);
  }
  






  protected boolean isItemPass(int pass)
  {
    return pass == 2;
  }
  






  protected boolean isShadowPass(int pass)
  {
    return pass == 0;
  }
  












  protected void drawFirstPassShape(Graphics2D g2, int pass, int series, int item, Shape shape)
  {
    if (isShadowPass(pass)) {
      if (getWallPaint() != null) {
        g2.setStroke(getItemStroke(series, item));
        g2.setPaint(getWallPaint());
        g2.translate(getXOffset(), getYOffset());
        g2.draw(shape);
        g2.translate(-getXOffset(), -getYOffset());
      }
      
    }
    else {
      super.drawFirstPassShape(g2, pass, series, item, shape);
    }
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYLine3DRenderer)) {
      return false;
    }
    XYLine3DRenderer that = (XYLine3DRenderer)obj;
    if (xOffset != xOffset) {
      return false;
    }
    if (yOffset != yOffset) {
      return false;
    }
    if (!PaintUtilities.equal(wallPaint, wallPaint)) {
      return false;
    }
    return super.equals(obj);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    wallPaint = SerialUtilities.readPaint(stream);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(wallPaint, stream);
  }
}
