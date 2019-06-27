package org.jfree.chart.plot;

import java.awt.geom.Point2D;





























































public class CrosshairState
{
  private boolean calculateDistanceInDataSpace = false;
  



  private double anchorX;
  



  private double anchorY;
  



  private Point2D anchor;
  



  private double crosshairX;
  



  private double crosshairY;
  



  private int datasetIndex;
  



  private int domainAxisIndex;
  



  private int rangeAxisIndex;
  



  private double distance;
  



  public CrosshairState()
  {
    this(false);
  }
  






  public CrosshairState(boolean calculateDistanceInDataSpace)
  {
    this.calculateDistanceInDataSpace = calculateDistanceInDataSpace;
  }
  








  public double getCrosshairDistance()
  {
    return distance;
  }
  









  public void setCrosshairDistance(double distance)
  {
    this.distance = distance;
  }
  

















  /**
   * @deprecated
   */
  public void updateCrosshairPoint(double x, double y, double transX, double transY, PlotOrientation orientation)
  {
    updateCrosshairPoint(x, y, 0, 0, transX, transY, orientation);
  }
  






















  public void updateCrosshairPoint(double x, double y, int domainAxisIndex, int rangeAxisIndex, double transX, double transY, PlotOrientation orientation)
  {
    if (anchor != null) {
      double d = 0.0D;
      if (calculateDistanceInDataSpace) {
        d = (x - anchorX) * (x - anchorX) + (y - anchorY) * (y - anchorY);
      }
      else
      {
        double xx = anchor.getX();
        double yy = anchor.getY();
        if (orientation == PlotOrientation.HORIZONTAL) {
          double temp = yy;
          yy = xx;
          xx = temp;
        }
        d = (transX - xx) * (transX - xx) + (transY - yy) * (transY - yy);
      }
      

      if (d < distance) {
        crosshairX = x;
        crosshairY = y;
        this.domainAxisIndex = domainAxisIndex;
        this.rangeAxisIndex = rangeAxisIndex;
        distance = d;
      }
    }
  }
  









  /**
   * @deprecated
   */
  public void updateCrosshairX(double candidateX)
  {
    updateCrosshairX(candidateX, 0);
  }
  












  public void updateCrosshairX(double candidateX, int domainAxisIndex)
  {
    double d = Math.abs(candidateX - anchorX);
    if (d < distance) {
      crosshairX = candidateX;
      this.domainAxisIndex = domainAxisIndex;
      distance = d;
    }
  }
  









  /**
   * @deprecated
   */
  public void updateCrosshairY(double candidateY)
  {
    updateCrosshairY(candidateY, 0);
  }
  











  public void updateCrosshairY(double candidateY, int rangeAxisIndex)
  {
    double d = Math.abs(candidateY - anchorY);
    if (d < distance) {
      crosshairY = candidateY;
      this.rangeAxisIndex = rangeAxisIndex;
      distance = d;
    }
  }
  









  public Point2D getAnchor()
  {
    return anchor;
  }
  












  public void setAnchor(Point2D anchor)
  {
    this.anchor = anchor;
  }
  






  public double getAnchorX()
  {
    return anchorX;
  }
  








  public void setAnchorX(double x)
  {
    anchorX = x;
  }
  






  public double getAnchorY()
  {
    return anchorY;
  }
  








  public void setAnchorY(double y)
  {
    anchorY = y;
  }
  






  public double getCrosshairX()
  {
    return crosshairX;
  }
  










  public void setCrosshairX(double x)
  {
    crosshairX = x;
  }
  







  public double getCrosshairY()
  {
    return crosshairY;
  }
  









  public void setCrosshairY(double y)
  {
    crosshairY = y;
  }
  










  public int getDatasetIndex()
  {
    return datasetIndex;
  }
  








  public void setDatasetIndex(int index)
  {
    datasetIndex = index;
  }
  






  /**
   * @deprecated
   */
  public int getDomainAxisIndex()
  {
    return domainAxisIndex;
  }
  






  /**
   * @deprecated
   */
  public int getRangeAxisIndex()
  {
    return rangeAxisIndex;
  }
}
