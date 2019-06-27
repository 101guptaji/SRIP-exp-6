package org.jfree.base.modules;

public abstract interface ModuleInitializer
{
  public abstract void performInit()
    throws ModuleInitializeException;
}
