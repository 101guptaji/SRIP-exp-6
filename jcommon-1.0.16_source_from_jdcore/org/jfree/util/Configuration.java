package org.jfree.util;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;

public abstract interface Configuration
  extends Serializable, Cloneable
{
  public abstract String getConfigProperty(String paramString);
  
  public abstract String getConfigProperty(String paramString1, String paramString2);
  
  public abstract Iterator findPropertyKeys(String paramString);
  
  public abstract Enumeration getConfigProperties();
  
  public abstract Object clone()
    throws CloneNotSupportedException;
}
