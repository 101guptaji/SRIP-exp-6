package org.jfree.base.modules;

public abstract interface ModuleInfo
{
  public abstract String getModuleClass();
  
  public abstract String getMajorVersion();
  
  public abstract String getMinorVersion();
  
  public abstract String getPatchLevel();
}
