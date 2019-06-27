package org.jfree.base;

import java.util.ArrayList;





















































public class BootableProjectInfo
  extends BasicProjectInfo
{
  private String bootClass;
  private boolean autoBoot;
  
  public BootableProjectInfo()
  {
    autoBoot = true;
  }
  








  public BootableProjectInfo(String name, String version, String licence, String info)
  {
    this();
    setName(name);
    setVersion(version);
    setLicenceName(licence);
    setInfo(info);
  }
  









  public BootableProjectInfo(String name, String version, String info, String copyright, String licenceName)
  {
    this();
    setName(name);
    setVersion(version);
    setLicenceName(licenceName);
    setInfo(info);
    setCopyright(copyright);
  }
  




  public BootableProjectInfo[] getDependencies()
  {
    ArrayList dependencies = new ArrayList();
    Library[] libraries = getLibraries();
    for (int i = 0; i < libraries.length; i++) {
      Library lib = libraries[i];
      if ((lib instanceof BootableProjectInfo)) {
        dependencies.add(lib);
      }
    }
    
    Library[] optionalLibraries = getOptionalLibraries();
    for (int i = 0; i < optionalLibraries.length; i++) {
      Library lib = optionalLibraries[i];
      if ((lib instanceof BootableProjectInfo)) {
        dependencies.add(lib);
      }
    }
    return (BootableProjectInfo[])dependencies.toArray(new BootableProjectInfo[dependencies.size()]);
  }
  



  /**
   * @deprecated
   */
  public void addDependency(BootableProjectInfo projectInfo)
  {
    if (projectInfo == null) {
      throw new NullPointerException();
    }
    addLibrary(projectInfo);
  }
  




  public String getBootClass()
  {
    return bootClass;
  }
  




  public void setBootClass(String bootClass)
  {
    this.bootClass = bootClass;
  }
  




  public boolean isAutoBoot()
  {
    return autoBoot;
  }
  




  public void setAutoBoot(boolean autoBoot)
  {
    this.autoBoot = autoBoot;
  }
}
