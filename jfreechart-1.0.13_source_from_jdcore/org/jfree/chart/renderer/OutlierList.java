package org.jfree.chart.renderer;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


































































public class OutlierList
{
  private List outliers;
  private Outlier averagedOutlier;
  private boolean multiple = false;
  




  public OutlierList(Outlier outlier)
  {
    outliers = new ArrayList();
    setAveragedOutlier(outlier);
  }
  






  public boolean add(Outlier outlier)
  {
    return outliers.add(outlier);
  }
  




  public int getItemCount()
  {
    return outliers.size();
  }
  




  public Outlier getAveragedOutlier()
  {
    return averagedOutlier;
  }
  




  public void setAveragedOutlier(Outlier averagedOutlier)
  {
    this.averagedOutlier = averagedOutlier;
  }
  





  public boolean isMultiple()
  {
    return multiple;
  }
  





  public void setMultiple(boolean multiple)
  {
    this.multiple = multiple;
  }
  








  public boolean isOverlapped(Outlier other)
  {
    if (other == null) {
      return false;
    }
    
    boolean result = other.overlaps(getAveragedOutlier());
    return result;
  }
  




  public void updateAveragedOutlier()
  {
    double totalXCoords = 0.0D;
    double totalYCoords = 0.0D;
    int size = getItemCount();
    Iterator iterator = outliers.iterator();
    while (iterator.hasNext()) {
      Outlier o = (Outlier)iterator.next();
      totalXCoords += o.getX();
      totalYCoords += o.getY();
    }
    getAveragedOutlier().getPoint().setLocation(new Point2D.Double(totalXCoords / size, totalYCoords / size));
  }
}
