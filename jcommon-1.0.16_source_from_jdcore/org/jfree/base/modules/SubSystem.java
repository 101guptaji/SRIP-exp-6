package org.jfree.base.modules;

import org.jfree.util.Configuration;
import org.jfree.util.ExtendedConfiguration;

public abstract interface SubSystem
{
  public abstract Configuration getGlobalConfig();
  
  public abstract ExtendedConfiguration getExtendedConfig();
  
  public abstract PackageManager getPackageManager();
}
