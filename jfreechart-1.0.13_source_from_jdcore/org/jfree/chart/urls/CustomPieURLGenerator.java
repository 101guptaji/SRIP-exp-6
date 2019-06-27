package org.jfree.chart.urls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jfree.data.general.PieDataset;
import org.jfree.util.PublicCloneable;























































public class CustomPieURLGenerator
  implements PieURLGenerator, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 7100607670144900503L;
  private ArrayList urls;
  
  public CustomPieURLGenerator()
  {
    urls = new ArrayList();
  }
  











  public String generateURL(PieDataset dataset, Comparable key, int pieIndex)
  {
    return getURL(key, pieIndex);
  }
  






  public int getListCount()
  {
    return urls.size();
  }
  









  public int getURLCount(int list)
  {
    int result = 0;
    Map urlMap = (Map)urls.get(list);
    if (urlMap != null) {
      result = urlMap.size();
    }
    return result;
  }
  







  public String getURL(Comparable key, int mapIndex)
  {
    String result = null;
    if (mapIndex < getListCount()) {
      Map urlMap = (Map)urls.get(mapIndex);
      if (urlMap != null) {
        result = (String)urlMap.get(key);
      }
    }
    return result;
  }
  










  public void addURLs(Map urlMap)
  {
    urls.add(urlMap);
  }
  







  public boolean equals(Object o)
  {
    if (o == this) {
      return true;
    }
    
    if ((o instanceof CustomPieURLGenerator)) {
      CustomPieURLGenerator generator = (CustomPieURLGenerator)o;
      if (getListCount() != generator.getListCount()) {
        return false;
      }
      Iterator i;
      for (int pieItem = 0; pieItem < getListCount(); pieItem++) {
        if (getURLCount(pieItem) != generator.getURLCount(pieItem)) {
          return false;
        }
        Set keySet = ((HashMap)urls.get(pieItem)).keySet();
        
        for (i = keySet.iterator(); i.hasNext();) {
          String key = (String)i.next();
          if (!getURL(key, pieItem).equals(generator.getURL(key, pieItem)))
          {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    CustomPieURLGenerator urlGen = new CustomPieURLGenerator();
    



    for (Iterator i = urls.iterator(); i.hasNext();) {
      Map map = (Map)i.next();
      
      Map newMap = new HashMap();
      for (Iterator j = map.keySet().iterator(); j.hasNext();) {
        String key = (String)j.next();
        newMap.put(key, map.get(key));
      }
      
      urlGen.addURLs(newMap);
      newMap = null;
    }
    
    return urlGen;
  }
}
