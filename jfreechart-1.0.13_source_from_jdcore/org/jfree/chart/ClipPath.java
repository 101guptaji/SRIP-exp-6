package org.jfree.chart;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.ui.RectangleEdge;
























































/**
 * @deprecated
 */
public class ClipPath
  implements Cloneable
{
  private double[] xValue = null;
  

  private double[] yValue = null;
  


  private boolean clip = true;
  

  private boolean drawPath = false;
  

  private boolean fillPath = false;
  

  private Paint fillPaint = null;
  

  private Paint drawPaint = null;
  

  private Stroke drawStroke = null;
  

  private Composite composite = null;
  







  public ClipPath() {}
  







  public ClipPath(double[] xValue, double[] yValue)
  {
    this(xValue, yValue, true, false, true);
  }
  












  public ClipPath(double[] xValue, double[] yValue, boolean clip, boolean fillPath, boolean drawPath)
  {
    this.xValue = xValue;
    this.yValue = yValue;
    
    this.clip = clip;
    this.fillPath = fillPath;
    this.drawPath = drawPath;
    
    fillPaint = Color.gray;
    drawPaint = Color.blue;
    drawStroke = new BasicStroke(1.0F);
    composite = AlphaComposite.Src;
  }
  














  public ClipPath(double[] xValue, double[] yValue, boolean fillPath, boolean drawPath, Paint fillPaint, Paint drawPaint, Stroke drawStroke, Composite composite)
  {
    this.xValue = xValue;
    this.yValue = yValue;
    
    this.fillPath = fillPath;
    this.drawPath = drawPath;
    
    this.fillPaint = fillPaint;
    this.drawPaint = drawPaint;
    this.drawStroke = drawStroke;
    this.composite = composite;
  }
  













  public GeneralPath draw(Graphics2D g2, Rectangle2D dataArea, ValueAxis horizontalAxis, ValueAxis verticalAxis)
  {
    GeneralPath generalPath = generateClipPath(dataArea, horizontalAxis, verticalAxis);
    

    if ((fillPath) || (drawPath)) {
      Composite saveComposite = g2.getComposite();
      Paint savePaint = g2.getPaint();
      Stroke saveStroke = g2.getStroke();
      
      if (fillPaint != null) {
        g2.setPaint(fillPaint);
      }
      if (composite != null) {
        g2.setComposite(composite);
      }
      if (fillPath) {
        g2.fill(generalPath);
      }
      
      if (drawStroke != null) {
        g2.setStroke(drawStroke);
      }
      if (drawPath) {
        g2.draw(generalPath);
      }
      g2.setPaint(savePaint);
      g2.setComposite(saveComposite);
      g2.setStroke(saveStroke);
    }
    return generalPath;
  }
  












  public GeneralPath generateClipPath(Rectangle2D dataArea, ValueAxis horizontalAxis, ValueAxis verticalAxis)
  {
    GeneralPath generalPath = new GeneralPath();
    double transX = horizontalAxis.valueToJava2D(xValue[0], dataArea, RectangleEdge.BOTTOM);
    

    double transY = verticalAxis.valueToJava2D(yValue[0], dataArea, RectangleEdge.LEFT);
    

    generalPath.moveTo((float)transX, (float)transY);
    for (int k = 0; k < yValue.length; k++) {
      transX = horizontalAxis.valueToJava2D(xValue[k], dataArea, RectangleEdge.BOTTOM);
      

      transY = verticalAxis.valueToJava2D(yValue[k], dataArea, RectangleEdge.LEFT);
      

      generalPath.lineTo((float)transX, (float)transY);
    }
    generalPath.closePath();
    
    return generalPath;
  }
  





  public Composite getComposite()
  {
    return composite;
  }
  




  public Paint getDrawPaint()
  {
    return drawPaint;
  }
  




  public boolean isDrawPath()
  {
    return drawPath;
  }
  




  public Stroke getDrawStroke()
  {
    return drawStroke;
  }
  




  public Paint getFillPaint()
  {
    return fillPaint;
  }
  




  public boolean isFillPath()
  {
    return fillPath;
  }
  




  public double[] getXValue()
  {
    return xValue;
  }
  




  public double[] getYValue()
  {
    return yValue;
  }
  




  public void setComposite(Composite composite)
  {
    this.composite = composite;
  }
  




  public void setDrawPaint(Paint drawPaint)
  {
    this.drawPaint = drawPaint;
  }
  




  public void setDrawPath(boolean drawPath)
  {
    this.drawPath = drawPath;
  }
  




  public void setDrawStroke(Stroke drawStroke)
  {
    this.drawStroke = drawStroke;
  }
  




  public void setFillPaint(Paint fillPaint)
  {
    this.fillPaint = fillPaint;
  }
  




  public void setFillPath(boolean fillPath)
  {
    this.fillPath = fillPath;
  }
  




  public void setXValue(double[] xValue)
  {
    this.xValue = xValue;
  }
  




  public void setYValue(double[] yValue)
  {
    this.yValue = yValue;
  }
  




  public boolean isClip()
  {
    return clip;
  }
  




  public void setClip(boolean clip)
  {
    this.clip = clip;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    ClipPath clone = (ClipPath)super.clone();
    xValue = ((double[])xValue.clone());
    yValue = ((double[])yValue.clone());
    return clone;
  }
}
