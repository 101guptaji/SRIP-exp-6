package org.jfree.base.config;

import java.util.Enumeration;
import java.util.Iterator;
import org.jfree.util.Configuration;

public abstract interface ModifiableConfiguration
  extends Configuration
{
  public abstract void setConfigProperty(String paramString1, String paramString2);
  
  public abstract Enumeration getConfigProperties();
  
  public abstract Iterator findPropertyKeys(String paramString);
}
