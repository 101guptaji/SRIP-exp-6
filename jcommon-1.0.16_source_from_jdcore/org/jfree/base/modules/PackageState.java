package org.jfree.base.modules;

import org.jfree.util.Log;
import org.jfree.util.Log.SimpleMessage;

































































public class PackageState
{
  public static final int STATE_NEW = 0;
  public static final int STATE_CONFIGURED = 1;
  public static final int STATE_INITIALIZED = 2;
  public static final int STATE_ERROR = -2;
  private final Module module;
  private int state;
  
  public PackageState(Module module)
  {
    this(module, 0);
  }
  







  public PackageState(Module module, int state)
  {
    if (module == null)
    {
      throw new NullPointerException("Module must not be null.");
    }
    if ((state != 1) && (state != -2) && (state != 2) && (state != 0))
    {

      throw new IllegalArgumentException("State is not valid");
    }
    this.module = module;
    this.state = state;
  }
  








  public boolean configure(SubSystem subSystem)
  {
    if (state == 0)
    {
      try
      {
        module.configure(subSystem);
        state = 1;
        return true;
      }
      catch (NoClassDefFoundError noClassDef)
      {
        Log.warn(new Log.SimpleMessage("Unable to load module classes for ", module.getName(), ":", noClassDef.getMessage()));
        
        state = -2;
      }
      catch (Exception e)
      {
        if (Log.isDebugEnabled())
        {

          Log.warn("Unable to configure the module " + module.getName(), e);
        }
        else if (Log.isWarningEnabled())
        {
          Log.warn("Unable to configure the module " + module.getName());
        }
        state = -2;
      }
    }
    return false;
  }
  





  public Module getModule()
  {
    return module;
  }
  






  public int getState()
  {
    return state;
  }
  










  public boolean initialize(SubSystem subSystem)
  {
    if (state == 1)
    {
      try
      {
        module.initialize(subSystem);
        state = 2;
        return true;
      }
      catch (NoClassDefFoundError noClassDef)
      {
        Log.warn(new Log.SimpleMessage("Unable to load module classes for ", module.getName(), ":", noClassDef.getMessage()));
        
        state = -2;
      }
      catch (ModuleInitializeException me)
      {
        if (Log.isDebugEnabled())
        {

          Log.warn("Unable to initialize the module " + module.getName(), me);
        }
        else if (Log.isWarningEnabled())
        {
          Log.warn("Unable to initialize the module " + module.getName());
        }
        state = -2;
      }
      catch (Exception e)
      {
        if (Log.isDebugEnabled())
        {

          Log.warn("Unable to initialize the module " + module.getName(), e);
        }
        else if (Log.isWarningEnabled())
        {
          Log.warn("Unable to initialize the module " + module.getName());
        }
        state = -2;
      }
    }
    return false;
  }
  








  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof PackageState))
    {
      return false;
    }
    
    PackageState packageState = (PackageState)o;
    
    if (!module.getModuleClass().equals(module.getModuleClass()))
    {
      return false;
    }
    
    return true;
  }
  






  public int hashCode()
  {
    return module.hashCode();
  }
}
