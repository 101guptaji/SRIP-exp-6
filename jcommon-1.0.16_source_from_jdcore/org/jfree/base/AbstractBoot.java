package org.jfree.base;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import org.jfree.base.config.HierarchicalConfiguration;
import org.jfree.base.config.PropertyFileConfiguration;
import org.jfree.base.config.SystemPropertyConfiguration;
import org.jfree.base.modules.PackageManager;
import org.jfree.base.modules.SubSystem;
import org.jfree.util.Configuration;
import org.jfree.util.ExtendedConfiguration;
import org.jfree.util.ExtendedConfigurationWrapper;
import org.jfree.util.Log;
import org.jfree.util.ObjectUtilities;












































































public abstract class AbstractBoot
  implements SubSystem
{
  private ExtendedConfigurationWrapper extWrapper;
  private PackageManager packageManager;
  private Configuration globalConfig;
  private boolean bootInProgress;
  private boolean bootDone;
  
  protected AbstractBoot() {}
  
  public synchronized PackageManager getPackageManager()
  {
    if (packageManager == null) {
      packageManager = PackageManager.createInstance(this);
    }
    return packageManager;
  }
  




  public synchronized Configuration getGlobalConfig()
  {
    if (globalConfig == null) {
      globalConfig = loadConfiguration();
    }
    return globalConfig;
  }
  




  public final synchronized boolean isBootInProgress()
  {
    return bootInProgress;
  }
  




  public final synchronized boolean isBootDone()
  {
    return bootDone;
  }
  




  protected abstract Configuration loadConfiguration();
  




  public final void start()
  {
    synchronized (this) {
      if (isBootDone()) {
        return;
      }
      while (isBootInProgress()) {
        try {
          wait();
        }
        catch (InterruptedException e) {}
      }
      

      if (isBootDone()) {
        return;
      }
      bootInProgress = true;
    }
    

    BootableProjectInfo info = getProjectInfo();
    if (info != null) {
      BootableProjectInfo[] childs = info.getDependencies();
      for (int i = 0; i < childs.length; i++) {
        AbstractBoot boot = loadBooter(childs[i].getBootClass());
        if (boot != null)
        {
          synchronized (boot) {
            boot.start();
            while (!boot.isBootDone()) {
              try {
                boot.wait();
              }
              catch (InterruptedException e) {}
            }
          }
        }
      }
    }
    


    performBoot();
    if (info != null)
    {
      Log.info(info.getName() + " " + info.getVersion() + " started.");
    }
    else
    {
      Log.info(getClass() + " started.");
    }
    
    synchronized (this) {
      bootInProgress = false;
      bootDone = true;
      notifyAll();
    }
  }
  





  protected abstract void performBoot();
  





  protected abstract BootableProjectInfo getProjectInfo();
  




  protected AbstractBoot loadBooter(String classname)
  {
    if (classname == null) {
      return null;
    }
    try {
      Class c = ObjectUtilities.getClassLoader(getClass()).loadClass(classname);
      
      Method m = c.getMethod("getInstance", (Class[])null);
      return (AbstractBoot)m.invoke(null, (Object[])null);
    }
    catch (Exception e) {
      Log.info("Unable to boot dependent class: " + classname); }
    return null;
  }
  



















  protected Configuration createDefaultHierarchicalConfiguration(String staticConfig, String userConfig, boolean addSysProps)
  {
    return createDefaultHierarchicalConfiguration(staticConfig, userConfig, addSysProps, PropertyFileConfiguration.class);
  }
  













  protected Configuration createDefaultHierarchicalConfiguration(String staticConfig, String userConfig, boolean addSysProps, Class source)
  {
    HierarchicalConfiguration globalConfig = new HierarchicalConfiguration();
    

    if (staticConfig != null) {
      PropertyFileConfiguration rootProperty = new PropertyFileConfiguration();
      
      rootProperty.load(staticConfig, getClass());
      globalConfig.insertConfiguration(rootProperty);
      globalConfig.insertConfiguration(getPackageManager().getPackageConfiguration());
    }
    
    if (userConfig != null) { String userConfigStripped;
      String userConfigStripped;
      if (userConfig.startsWith("/")) {
        userConfigStripped = userConfig.substring(1);
      }
      else {
        userConfigStripped = userConfig;
      }
      try {
        Enumeration userConfigs = ObjectUtilities.getClassLoader(getClass()).getResources(userConfigStripped);
        
        ArrayList configs = new ArrayList();
        while (userConfigs.hasMoreElements()) {
          URL url = (URL)userConfigs.nextElement();
          try {
            PropertyFileConfiguration baseProperty = new PropertyFileConfiguration();
            
            InputStream in = url.openStream();
            baseProperty.load(in);
            in.close();
            configs.add(baseProperty);
          }
          catch (IOException ioe) {
            Log.warn("Failed to load the user configuration at " + url, ioe);
          }
        }
        
        for (int i = configs.size() - 1; i >= 0; i--) {
          PropertyFileConfiguration baseProperty = (PropertyFileConfiguration)configs.get(i);
          
          globalConfig.insertConfiguration(baseProperty);
        }
      }
      catch (IOException e) {
        Log.warn("Failed to lookup the user configurations.", e);
      }
    }
    if (addSysProps) {
      SystemPropertyConfiguration systemConfig = new SystemPropertyConfiguration();
      
      globalConfig.insertConfiguration(systemConfig);
    }
    return globalConfig;
  }
  





  public synchronized ExtendedConfiguration getExtendedConfig()
  {
    if (extWrapper == null) {
      extWrapper = new ExtendedConfigurationWrapper(getGlobalConfig());
    }
    return extWrapper;
  }
}
