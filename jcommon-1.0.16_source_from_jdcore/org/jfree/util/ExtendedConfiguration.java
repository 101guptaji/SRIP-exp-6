package org.jfree.util;

public abstract interface ExtendedConfiguration
  extends Configuration
{
  public abstract boolean isPropertySet(String paramString);
  
  public abstract int getIntProperty(String paramString);
  
  public abstract int getIntProperty(String paramString, int paramInt);
  
  public abstract boolean getBoolProperty(String paramString);
  
  public abstract boolean getBoolProperty(String paramString, boolean paramBoolean);
}
