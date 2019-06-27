package org.jfree.base.modules;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import org.jfree.base.AbstractBoot;
import org.jfree.base.config.HierarchicalConfiguration;
import org.jfree.base.config.PropertyFileConfiguration;
import org.jfree.base.log.PadMessage;
import org.jfree.util.Configuration;
import org.jfree.util.Log;
import org.jfree.util.Log.SimpleMessage;
import org.jfree.util.ObjectUtilities;





























































public final class PackageManager
{
  private static final int RETURN_MODULE_LOADED = 0;
  private static final int RETURN_MODULE_UNKNOWN = 1;
  private static final int RETURN_MODULE_ERROR = 2;
  private final PackageConfiguration packageConfiguration;
  private final ArrayList modules;
  private final ArrayList initSections;
  private AbstractBoot booter;
  private static HashMap instances;
  
  public static class PackageConfiguration
    extends PropertyFileConfiguration
  {
    public PackageConfiguration() {}
    
    public void insertConfiguration(HierarchicalConfiguration config)
    {
      super.insertConfiguration(config);
    }
  }
  











































  public static PackageManager createInstance(AbstractBoot booter)
  {
    if (instances == null) {
      instances = new HashMap();
      PackageManager manager = new PackageManager(booter);
      instances.put(booter, manager);
      return manager;
    }
    PackageManager manager = (PackageManager)instances.get(booter);
    if (manager == null) {
      manager = new PackageManager(booter);
      instances.put(booter, manager);
    }
    return manager;
  }
  




  private PackageManager(AbstractBoot booter)
  {
    if (booter == null) {
      throw new NullPointerException();
    }
    this.booter = booter;
    packageConfiguration = new PackageConfiguration();
    modules = new ArrayList();
    initSections = new ArrayList();
  }
  






  public boolean isModuleAvailable(ModuleInfo moduleDescription)
  {
    PackageState[] packageStates = (PackageState[])modules.toArray(new PackageState[modules.size()]);
    
    for (int i = 0; i < packageStates.length; i++) {
      PackageState state = packageStates[i];
      if (state.getModule().getModuleClass().equals(moduleDescription.getModuleClass())) {
        return state.getState() == 2;
      }
    }
    return false;
  }
  






  public void load(String modulePrefix)
  {
    if (initSections.contains(modulePrefix)) {
      return;
    }
    initSections.add(modulePrefix);
    
    Configuration config = booter.getGlobalConfig();
    Iterator it = config.findPropertyKeys(modulePrefix);
    int count = 0;
    while (it.hasNext()) {
      String key = (String)it.next();
      if (key.endsWith(".Module")) {
        String moduleClass = config.getConfigProperty(key);
        if ((moduleClass != null) && (moduleClass.length() > 0)) {
          addModule(moduleClass);
          count++;
        }
      }
    }
    Log.debug("Loaded a total of " + count + " modules under prefix: " + modulePrefix);
  }
  




  public synchronized void initializeModules()
  {
    PackageSorter.sort(modules);
    
    for (int i = 0; i < modules.size(); i++) {
      PackageState mod = (PackageState)modules.get(i);
      if (mod.configure(booter)) {
        Log.debug(new Log.SimpleMessage("Conf: ", new PadMessage(mod.getModule().getModuleClass(), 70), " [", mod.getModule().getSubSystem(), "]"));
      }
    }
    


    for (int i = 0; i < modules.size(); i++) {
      PackageState mod = (PackageState)modules.get(i);
      if (mod.initialize(booter)) {
        Log.debug(new Log.SimpleMessage("Init: ", new PadMessage(mod.getModule().getModuleClass(), 70), " [", mod.getModule().getSubSystem(), "]"));
      }
    }
  }
  








  public synchronized void addModule(String modClass)
  {
    ArrayList loadModules = new ArrayList();
    ModuleInfo modInfo = new DefaultModuleInfo(modClass, null, null, null);
    
    if (loadModule(modInfo, new ArrayList(), loadModules, false)) {
      for (int i = 0; i < loadModules.size(); i++) {
        Module mod = (Module)loadModules.get(i);
        modules.add(new PackageState(mod));
      }
    }
  }
  








  private int containsModule(ArrayList tempModules, ModuleInfo module)
  {
    if (tempModules != null) {
      ModuleInfo[] mods = (ModuleInfo[])tempModules.toArray(new ModuleInfo[tempModules.size()]);
      
      for (int i = 0; i < mods.length; i++) {
        if (mods[i].getModuleClass().equals(module.getModuleClass())) {
          return 0;
        }
      }
    }
    
    PackageState[] packageStates = (PackageState[])modules.toArray(new PackageState[modules.size()]);
    
    for (int i = 0; i < packageStates.length; i++) {
      if (packageStates[i].getModule().getModuleClass().equals(module.getModuleClass())) {
        if (packageStates[i].getState() == -2) {
          return 2;
        }
        
        return 0;
      }
    }
    
    return 1;
  }
  






  private void dropFailedModule(PackageState state)
  {
    if (!modules.contains(state)) {
      modules.add(state);
    }
  }
  















  private boolean loadModule(ModuleInfo moduleInfo, ArrayList incompleteModules, ArrayList modules, boolean fatal)
  {
    try
    {
      Class c = ObjectUtilities.getClassLoader(getClass()).loadClass(moduleInfo.getModuleClass());
      Module module = (Module)c.newInstance();
      
      if (!acceptVersion(moduleInfo, module))
      {
        Log.warn("Module " + module.getName() + ": required version: " + moduleInfo + ", but found Version: \n" + module);
        
        PackageState state = new PackageState(module, -2);
        dropFailedModule(state);
        return false;
      }
      
      int moduleContained = containsModule(modules, module);
      if (moduleContained == 2)
      {
        Log.debug("Indicated failure for module: " + module.getModuleClass());
        PackageState state = new PackageState(module, -2);
        dropFailedModule(state);
        return false;
      }
      if (moduleContained == 1) {
        if (incompleteModules.contains(module))
        {
          Log.error(new Log.SimpleMessage("Circular module reference: This module definition is invalid: ", module.getClass()));
          

          PackageState state = new PackageState(module, -2);
          dropFailedModule(state);
          return false;
        }
        incompleteModules.add(module);
        ModuleInfo[] required = module.getRequiredModules();
        for (int i = 0; i < required.length; i++) {
          if (!loadModule(required[i], incompleteModules, modules, true)) {
            Log.debug("Indicated failure for module: " + module.getModuleClass());
            PackageState state = new PackageState(module, -2);
            dropFailedModule(state);
            return false;
          }
        }
        
        ModuleInfo[] optional = module.getOptionalModules();
        for (int i = 0; i < optional.length; i++) {
          if (!loadModule(optional[i], incompleteModules, modules, true)) {
            Log.debug(new Log.SimpleMessage("Optional module: ", optional[i].getModuleClass(), " was not loaded."));
          }
        }
        

        if (containsModule(modules, module) == 1) {
          modules.add(module);
        }
        incompleteModules.remove(module);
      }
      return true;
    }
    catch (ClassNotFoundException cnfe) {
      if (fatal) {
        Log.warn(new Log.SimpleMessage("Unresolved dependency for package: ", moduleInfo.getModuleClass()));
      }
      
      Log.debug(new Log.SimpleMessage("ClassNotFound: ", cnfe.getMessage()));
      return false;
    }
    catch (Exception e) {
      Log.warn(new Log.SimpleMessage("Exception while loading module: ", moduleInfo), e); }
    return false;
  }
  








  private boolean acceptVersion(ModuleInfo moduleRequirement, Module module)
  {
    if (moduleRequirement.getMajorVersion() == null) {
      return true;
    }
    if (module.getMajorVersion() == null) {
      Log.warn("Module " + module.getName() + " does not define a major version.");
    }
    else {
      int compare = acceptVersion(moduleRequirement.getMajorVersion(), module.getMajorVersion());
      
      if (compare > 0) {
        return false;
      }
      if (compare < 0) {
        return true;
      }
    }
    
    if (moduleRequirement.getMinorVersion() == null) {
      return true;
    }
    if (module.getMinorVersion() == null) {
      Log.warn("Module " + module.getName() + " does not define a minor version.");
    }
    else {
      int compare = acceptVersion(moduleRequirement.getMinorVersion(), module.getMinorVersion());
      
      if (compare > 0) {
        return false;
      }
      if (compare < 0) {
        return true;
      }
    }
    
    if (moduleRequirement.getPatchLevel() == null) {
      return true;
    }
    if (module.getPatchLevel() == null) {
      Log.debug("Module " + module.getName() + " does not define a patch level.");

    }
    else if (acceptVersion(moduleRequirement.getPatchLevel(), module.getPatchLevel()) > 0)
    {
      Log.debug("Did not accept patchlevel: " + moduleRequirement.getPatchLevel() + " - " + module.getPatchLevel());
      

      return false;
    }
    
    return true;
  }
  










  private int acceptVersion(String modVer, String depModVer)
  {
    int mLength = Math.max(modVer.length(), depModVer.length());
    char[] depVerArray;
    char[] modVerArray;
    if (modVer.length() > depModVer.length()) {
      char[] modVerArray = modVer.toCharArray();
      char[] depVerArray = new char[mLength];
      int delta = modVer.length() - depModVer.length();
      Arrays.fill(depVerArray, 0, delta, ' ');
      System.arraycopy(depVerArray, delta, depModVer.toCharArray(), 0, depModVer.length());
    }
    else if (modVer.length() < depModVer.length()) {
      char[] depVerArray = depModVer.toCharArray();
      char[] modVerArray = new char[mLength];
      char[] b1 = new char[mLength];
      int delta = depModVer.length() - modVer.length();
      Arrays.fill(b1, 0, delta, ' ');
      System.arraycopy(b1, delta, modVer.toCharArray(), 0, modVer.length());
    }
    else {
      depVerArray = depModVer.toCharArray();
      modVerArray = modVer.toCharArray();
    }
    return new String(modVerArray).compareTo(new String(depVerArray));
  }
  






  public PackageConfiguration getPackageConfiguration()
  {
    return packageConfiguration;
  }
  






  public Module[] getAllModules()
  {
    Module[] mods = new Module[modules.size()];
    for (int i = 0; i < modules.size(); i++) {
      PackageState state = (PackageState)modules.get(i);
      mods[i] = state.getModule();
    }
    return mods;
  }
  





  public Module[] getActiveModules()
  {
    ArrayList mods = new ArrayList();
    for (int i = 0; i < modules.size(); i++) {
      PackageState state = (PackageState)modules.get(i);
      if (state.getState() == 2) {
        mods.add(state.getModule());
      }
    }
    return (Module[])mods.toArray(new Module[mods.size()]);
  }
  




  public void printUsedModules(PrintStream p)
  {
    Module[] allMods = getAllModules();
    ArrayList activeModules = new ArrayList();
    ArrayList failedModules = new ArrayList();
    
    for (int i = 0; i < allMods.length; i++) {
      if (isModuleAvailable(allMods[i])) {
        activeModules.add(allMods[i]);
      }
      else {
        failedModules.add(allMods[i]);
      }
    }
    
    p.print("Active modules: ");
    p.println(activeModules.size());
    p.println("----------------------------------------------------------");
    for (int i = 0; i < activeModules.size(); i++) {
      Module mod = (Module)activeModules.get(i);
      p.print(new PadMessage(mod.getModuleClass(), 70));
      p.print(" [");
      p.print(mod.getSubSystem());
      p.println("]");
      p.print("  Version: ");
      p.print(mod.getMajorVersion());
      p.print("-");
      p.print(mod.getMinorVersion());
      p.print("-");
      p.print(mod.getPatchLevel());
      p.print(" Producer: ");
      p.println(mod.getProducer());
      p.print("  Description: ");
      p.println(mod.getDescription());
    }
  }
}
