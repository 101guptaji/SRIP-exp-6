package org.jfree.chart.renderer;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;





























































public class Outlier
  implements Comparable
{
  private Point2D point;
  private double radius;
  
  public Outlier(double xCoord, double yCoord, double radius)
  {
    point = new Point2D.Double(xCoord - radius, yCoord - radius);
    this.radius = radius;
  }
  





  public Point2D getPoint()
  {
    return point;
  }
  





  public void setPoint(Point2D point)
  {
    this.point = point;
  }
  





  public double getX()
  {
    return getPoint().getX();
  }
  





  public double getY()
  {
    return getPoint().getY();
  }
  




  public double getRadius()
  {
    return radius;
  }
  




  public void setRadius(double radius)
  {
    this.radius = radius;
  }
  








  public int compareTo(Object o)
  {
    Outlier outlier = (Outlier)o;
    Point2D p1 = getPoint();
    Point2D p2 = outlier.getPoint();
    if (p1.equals(p2)) {
      return 0;
    }
    if ((p1.getX() < p2.getX()) || (p1.getY() < p2.getY())) {
      return -1;
    }
    
    return 1;
  }
  










  public boolean overlaps(Outlier other)
  {
    return (other.getX() >= getX() - radius * 1.1D) && (other.getX() <= getX() + radius * 1.1D) && (other.getY() >= getY() - radius * 1.1D) && (other.getY() <= getY() + radius * 1.1D);
  }
  









  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Outlier)) {
      return false;
    }
    Outlier that = (Outlier)obj;
    if (!point.equals(point)) {
      return false;
    }
    if (radius != radius) {
      return false;
    }
    return true;
  }
  




  public String toString()
  {
    return "{" + getX() + "," + getY() + "}";
  }
}
