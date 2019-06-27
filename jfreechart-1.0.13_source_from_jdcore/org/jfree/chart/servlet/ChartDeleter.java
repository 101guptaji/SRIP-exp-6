package org.jfree.chart.servlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
















































public class ChartDeleter
  implements HttpSessionBindingListener, Serializable
{
  private List chartNames = new ArrayList();
  





  public ChartDeleter() {}
  





  public void addChart(String filename)
  {
    chartNames.add(filename);
  }
  







  public boolean isChartAvailable(String filename)
  {
    return chartNames.contains(filename);
  }
  







  public void valueBound(HttpSessionBindingEvent event) {}
  







  public void valueUnbound(HttpSessionBindingEvent event)
  {
    Iterator iter = chartNames.listIterator();
    while (iter.hasNext()) {
      String filename = (String)iter.next();
      File file = new File(System.getProperty("java.io.tmpdir"), filename);
      

      if (file.exists()) {
        file.delete();
      }
    }
  }
}
