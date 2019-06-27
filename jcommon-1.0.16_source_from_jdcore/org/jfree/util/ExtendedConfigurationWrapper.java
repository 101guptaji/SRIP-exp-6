package org.jfree.util;

import java.util.Enumeration;
import java.util.Iterator;






















































public class ExtendedConfigurationWrapper
  implements ExtendedConfiguration
{
  private Configuration parent;
  
  public ExtendedConfigurationWrapper(Configuration parent)
  {
    if (parent == null)
    {
      throw new NullPointerException("Parent given must not be null");
    }
    this.parent = parent;
  }
  







  public boolean getBoolProperty(String name)
  {
    return getBoolProperty(name, false);
  }
  










  public boolean getBoolProperty(String name, boolean defaultValue)
  {
    return "true".equals(parent.getConfigProperty(name, String.valueOf(defaultValue)));
  }
  







  public int getIntProperty(String name)
  {
    return getIntProperty(name, 0);
  }
  









  public int getIntProperty(String name, int defaultValue)
  {
    String retval = parent.getConfigProperty(name);
    if (retval == null)
    {
      return defaultValue;
    }
    try
    {
      return Integer.parseInt(retval);
    }
    catch (Exception e) {}
    
    return defaultValue;
  }
  







  public boolean isPropertySet(String name)
  {
    return parent.getConfigProperty(name) != null;
  }
  






  public Iterator findPropertyKeys(String prefix)
  {
    return parent.findPropertyKeys(prefix);
  }
  






  public String getConfigProperty(String key)
  {
    return parent.getConfigProperty(key);
  }
  











  public String getConfigProperty(String key, String defaultValue)
  {
    return parent.getConfigProperty(key, defaultValue);
  }
  





  public Enumeration getConfigProperties()
  {
    return parent.getConfigProperties();
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    ExtendedConfigurationWrapper wrapper = (ExtendedConfigurationWrapper)super.clone();
    parent = ((Configuration)parent.clone());
    return parent;
  }
}
