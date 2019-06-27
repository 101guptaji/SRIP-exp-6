package org.jfree.base.config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import org.jfree.util.Configuration;
import org.jfree.util.PublicCloneable;





























































public class HierarchicalConfiguration
  implements ModifiableConfiguration, PublicCloneable
{
  private Properties configuration;
  private transient Configuration parentConfiguration;
  
  public HierarchicalConfiguration()
  {
    configuration = new Properties();
  }
  





  public HierarchicalConfiguration(Configuration parentConfiguration)
  {
    this();
    this.parentConfiguration = parentConfiguration;
  }
  






  public String getConfigProperty(String key)
  {
    return getConfigProperty(key, null);
  }
  











  public String getConfigProperty(String key, String defaultValue)
  {
    String value = configuration.getProperty(key);
    if (value == null)
    {
      if (isRootConfig())
      {
        value = defaultValue;
      }
      else
      {
        value = parentConfiguration.getConfigProperty(key, defaultValue);
      }
    }
    return value;
  }
  






  public void setConfigProperty(String key, String value)
  {
    if (key == null)
    {
      throw new NullPointerException();
    }
    
    if (value == null)
    {
      configuration.remove(key);
    }
    else
    {
      configuration.setProperty(key, value);
    }
  }
  





  private boolean isRootConfig()
  {
    return parentConfiguration == null;
  }
  







  public boolean isLocallyDefined(String key)
  {
    return configuration.containsKey(key);
  }
  





  protected Properties getConfiguration()
  {
    return configuration;
  }
  







  public void insertConfiguration(HierarchicalConfiguration config)
  {
    config.setParentConfig(getParentConfig());
    setParentConfig(config);
  }
  







  protected void setParentConfig(Configuration config)
  {
    if (parentConfiguration == this)
    {
      throw new IllegalArgumentException("Cannot add myself as parent configuration.");
    }
    parentConfiguration = config;
  }
  







  protected Configuration getParentConfig()
  {
    return parentConfiguration;
  }
  







  public Enumeration getConfigProperties()
  {
    return configuration.keys();
  }
  






  public Iterator findPropertyKeys(String prefix)
  {
    TreeSet keys = new TreeSet();
    collectPropertyKeys(prefix, this, keys);
    return Collections.unmodifiableSet(keys).iterator();
  }
  










  private void collectPropertyKeys(String prefix, Configuration config, TreeSet collector)
  {
    Enumeration enum1 = config.getConfigProperties();
    while (enum1.hasMoreElements())
    {
      String key = (String)enum1.nextElement();
      if (key.startsWith(prefix))
      {
        if (!collector.contains(key))
        {
          collector.add(key);
        }
      }
    }
    
    if ((config instanceof HierarchicalConfiguration))
    {
      HierarchicalConfiguration hconfig = (HierarchicalConfiguration)config;
      if (parentConfiguration != null)
      {
        collectPropertyKeys(prefix, parentConfiguration, collector);
      }
    }
  }
  






  protected boolean isParentSaved()
  {
    return true;
  }
  






  protected void configurationLoaded() {}
  






  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.defaultWriteObject();
    if (!isParentSaved())
    {
      out.writeBoolean(false);
    }
    else
    {
      out.writeBoolean(true);
      out.writeObject(parentConfiguration);
    }
  }
  








  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();
    boolean readParent = in.readBoolean();
    if (readParent)
    {
      parentConfiguration = ((ModifiableConfiguration)in.readObject());
    }
    else
    {
      parentConfiguration = null;
    }
    configurationLoaded();
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    HierarchicalConfiguration config = (HierarchicalConfiguration)super.clone();
    configuration = ((Properties)configuration.clone());
    return config;
  }
}
