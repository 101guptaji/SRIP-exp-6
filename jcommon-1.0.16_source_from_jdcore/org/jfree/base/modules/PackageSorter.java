package org.jfree.base.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jfree.util.Log;
import org.jfree.util.Log.SimpleMessage;





































































public final class PackageSorter
{
  private PackageSorter() {}
  
  private static class SortModule
    implements Comparable
  {
    private int position;
    private final PackageState state;
    private ArrayList dependSubsystems;
    
    public SortModule(PackageState state)
    {
      position = -1;
      this.state = state;
    }
    






    public ArrayList getDependSubsystems()
    {
      return dependSubsystems;
    }
    






    public void setDependSubsystems(ArrayList dependSubsystems)
    {
      this.dependSubsystems = dependSubsystems;
    }
    







    public int getPosition()
    {
      return position;
    }
    






    public void setPosition(int position)
    {
      this.position = position;
    }
    





    public PackageState getState()
    {
      return state;
    }
    







    public String toString()
    {
      StringBuffer buffer = new StringBuffer();
      buffer.append("SortModule: ");
      buffer.append(position);
      buffer.append(" ");
      buffer.append(state.getModule().getName());
      buffer.append(" ");
      buffer.append(state.getModule().getModuleClass());
      return buffer.toString();
    }
    











    public int compareTo(Object o)
    {
      SortModule otherModule = (SortModule)o;
      if (position > position)
      {
        return 1;
      }
      if (position < position)
      {
        return -1;
      }
      return 0;
    }
  }
  















  public static void sort(List modules)
  {
    HashMap moduleMap = new HashMap();
    ArrayList errorModules = new ArrayList();
    ArrayList weightModules = new ArrayList();
    
    for (int i = 0; i < modules.size(); i++)
    {
      PackageState state = (PackageState)modules.get(i);
      if (state.getState() == -2)
      {
        errorModules.add(state);
      }
      else
      {
        SortModule mod = new SortModule(state);
        weightModules.add(mod);
        moduleMap.put(state.getModule().getModuleClass(), mod);
      }
    }
    
    SortModule[] weigths = (SortModule[])weightModules.toArray(new SortModule[weightModules.size()]);
    

    for (int i = 0; i < weigths.length; i++)
    {
      SortModule sortMod = weigths[i];
      sortMod.setDependSubsystems(collectSubsystemModules(sortMod.getState().getModule(), moduleMap));
    }
    








    boolean doneWork = true;
    while (doneWork)
    {
      doneWork = false;
      for (int i = 0; i < weigths.length; i++)
      {
        SortModule mod = weigths[i];
        int position = searchModulePosition(mod, moduleMap);
        if (position != mod.getPosition())
        {
          mod.setPosition(position);
          doneWork = true;
        }
      }
    }
    
    Arrays.sort(weigths);
    modules.clear();
    for (int i = 0; i < weigths.length; i++)
    {
      modules.add(weigths[i].getState());
    }
    for (int i = 0; i < errorModules.size(); i++)
    {
      modules.add(errorModules.get(i));
    }
  }
  










  private static int searchModulePosition(SortModule smodule, HashMap moduleMap)
  {
    Module module = smodule.getState().getModule();
    int position = 0;
    



    ModuleInfo[] modInfo = module.getOptionalModules();
    for (int modPos = 0; modPos < modInfo.length; modPos++)
    {
      String moduleName = modInfo[modPos].getModuleClass();
      SortModule reqMod = (SortModule)moduleMap.get(moduleName);
      if (reqMod != null)
      {


        if (reqMod.getPosition() >= position)
        {
          position = reqMod.getPosition() + 1;
        }
      }
    }
    



    modInfo = module.getRequiredModules();
    for (int modPos = 0; modPos < modInfo.length; modPos++)
    {
      String moduleName = modInfo[modPos].getModuleClass();
      SortModule reqMod = (SortModule)moduleMap.get(moduleName);
      if (reqMod == null)
      {
        Log.warn("Invalid state: Required dependency of '" + moduleName + "' had an error.");

      }
      else if (reqMod.getPosition() >= position)
      {
        position = reqMod.getPosition() + 1;
      }
    }
    



    String subSystem = module.getSubSystem();
    Iterator it = moduleMap.values().iterator();
    while (it.hasNext())
    {
      SortModule mod = (SortModule)it.next();
      
      if (mod.getState().getModule() != module)
      {



        Module subSysMod = mod.getState().getModule();
        


        if (!subSystem.equals(subSysMod.getSubSystem()))
        {








          if (smodule.getDependSubsystems().contains(subSysMod.getSubSystem()))
          {



            if (!isBaseModule(subSysMod, module))
            {
              if (mod.getPosition() >= position)
              {
                position = mod.getPosition() + 1; } }
          }
        }
      }
    }
    return position;
  }
  








  private static boolean isBaseModule(Module mod, ModuleInfo mi)
  {
    ModuleInfo[] info = mod.getRequiredModules();
    for (int i = 0; i < info.length; i++)
    {
      if (info[i].getModuleClass().equals(mi.getModuleClass()))
      {
        return true;
      }
    }
    info = mod.getOptionalModules();
    for (int i = 0; i < info.length; i++)
    {
      if (info[i].getModuleClass().equals(mi.getModuleClass()))
      {
        return true;
      }
    }
    return false;
  }
  









  private static ArrayList collectSubsystemModules(Module childMod, HashMap moduleMap)
  {
    ArrayList collector = new ArrayList();
    ModuleInfo[] info = childMod.getRequiredModules();
    for (int i = 0; i < info.length; i++)
    {
      SortModule dependentModule = (SortModule)moduleMap.get(info[i].getModuleClass());
      
      if (dependentModule == null)
      {
        Log.warn(new Log.SimpleMessage("A dependent module was not found in the list of known modules.", info[i].getModuleClass()));


      }
      else
      {

        collector.add(dependentModule.getState().getModule().getSubSystem());
      }
    }
    info = childMod.getOptionalModules();
    for (int i = 0; i < info.length; i++)
    {
      Module dependentModule = (Module)moduleMap.get(info[i].getModuleClass());
      
      if (dependentModule == null)
      {
        Log.warn("A dependent module was not found in the list of known modules.");
      }
      else
        collector.add(dependentModule.getSubSystem());
    }
    return collector;
  }
}
