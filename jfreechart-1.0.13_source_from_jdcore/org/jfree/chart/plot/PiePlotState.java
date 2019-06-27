package org.jfree.chart.plot;

import java.awt.geom.Rectangle2D;
import org.jfree.chart.renderer.RendererState;




































































public class PiePlotState
  extends RendererState
{
  private int passesRequired;
  private double total;
  private double latestAngle;
  private Rectangle2D explodedPieArea;
  private Rectangle2D pieArea;
  private double pieCenterX;
  private double pieCenterY;
  private double pieHRadius;
  private double pieWRadius;
  private Rectangle2D linkArea;
  
  public PiePlotState(PlotRenderingInfo info)
  {
    super(info);
    passesRequired = 1;
    total = 0.0D;
  }
  




  public int getPassesRequired()
  {
    return passesRequired;
  }
  




  public void setPassesRequired(int passes)
  {
    passesRequired = passes;
  }
  




  public double getTotal()
  {
    return total;
  }
  




  public void setTotal(double total)
  {
    this.total = total;
  }
  




  public double getLatestAngle()
  {
    return latestAngle;
  }
  




  public void setLatestAngle(double angle)
  {
    latestAngle = angle;
  }
  




  public Rectangle2D getPieArea()
  {
    return pieArea;
  }
  




  public void setPieArea(Rectangle2D area)
  {
    pieArea = area;
  }
  




  public Rectangle2D getExplodedPieArea()
  {
    return explodedPieArea;
  }
  




  public void setExplodedPieArea(Rectangle2D area)
  {
    explodedPieArea = area;
  }
  




  public double getPieCenterX()
  {
    return pieCenterX;
  }
  




  public void setPieCenterX(double x)
  {
    pieCenterX = x;
  }
  






  public double getPieCenterY()
  {
    return pieCenterY;
  }
  





  public void setPieCenterY(double y)
  {
    pieCenterY = y;
  }
  





  public Rectangle2D getLinkArea()
  {
    return linkArea;
  }
  





  public void setLinkArea(Rectangle2D area)
  {
    linkArea = area;
  }
  




  public double getPieHRadius()
  {
    return pieHRadius;
  }
  




  public void setPieHRadius(double radius)
  {
    pieHRadius = radius;
  }
  




  public double getPieWRadius()
  {
    return pieWRadius;
  }
  




  public void setPieWRadius(double radius)
  {
    pieWRadius = radius;
  }
}
