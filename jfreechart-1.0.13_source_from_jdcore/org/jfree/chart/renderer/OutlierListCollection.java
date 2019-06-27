package org.jfree.chart.renderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;





























































public class OutlierListCollection
{
  private List outlierLists;
  private boolean highFarOut = false;
  




  private boolean lowFarOut = false;
  


  public OutlierListCollection()
  {
    outlierLists = new ArrayList();
  }
  





  public boolean isHighFarOut()
  {
    return highFarOut;
  }
  





  public void setHighFarOut(boolean farOut)
  {
    highFarOut = farOut;
  }
  





  public boolean isLowFarOut()
  {
    return lowFarOut;
  }
  





  public void setLowFarOut(boolean farOut)
  {
    lowFarOut = farOut;
  }
  










  public boolean add(Outlier outlier)
  {
    if (outlierLists.isEmpty()) {
      return outlierLists.add(new OutlierList(outlier));
    }
    
    boolean updated = false;
    Iterator iterator = outlierLists.iterator();
    while (iterator.hasNext()) {
      OutlierList list = (OutlierList)iterator.next();
      if (list.isOverlapped(outlier)) {
        updated = updateOutlierList(list, outlier);
      }
    }
    if (!updated)
    {
      updated = outlierLists.add(new OutlierList(outlier));
    }
    return updated;
  }
  






  public Iterator iterator()
  {
    return outlierLists.iterator();
  }
  










  private boolean updateOutlierList(OutlierList list, Outlier outlier)
  {
    boolean result = false;
    result = list.add(outlier);
    list.updateAveragedOutlier();
    list.setMultiple(true);
    return result;
  }
}
