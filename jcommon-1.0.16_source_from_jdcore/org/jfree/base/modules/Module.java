package org.jfree.base.modules;

public abstract interface Module
  extends ModuleInfo
{
  public abstract ModuleInfo[] getRequiredModules();
  
  public abstract ModuleInfo[] getOptionalModules();
  
  public abstract void initialize(SubSystem paramSubSystem)
    throws ModuleInitializeException;
  
  public abstract void configure(SubSystem paramSubSystem);
  
  public abstract String getDescription();
  
  public abstract String getProducer();
  
  public abstract String getName();
  
  public abstract String getSubSystem();
}
