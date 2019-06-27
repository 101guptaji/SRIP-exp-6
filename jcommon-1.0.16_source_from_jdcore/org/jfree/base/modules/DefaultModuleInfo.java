package org.jfree.base.modules;













public class DefaultModuleInfo
  implements ModuleInfo
{
  private String moduleClass;
  










  private String majorVersion;
  










  private String minorVersion;
  










  private String patchLevel;
  











  public DefaultModuleInfo() {}
  











  public DefaultModuleInfo(String moduleClass, String majorVersion, String minorVersion, String patchLevel)
  {
    if (moduleClass == null)
    {
      throw new NullPointerException("Module class must not be null.");
    }
    this.moduleClass = moduleClass;
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
    this.patchLevel = patchLevel;
  }
  







  public String getModuleClass()
  {
    return moduleClass;
  }
  





  public void setModuleClass(String moduleClass)
  {
    if (moduleClass == null)
    {
      throw new NullPointerException();
    }
    this.moduleClass = moduleClass;
  }
  







  public String getMajorVersion()
  {
    return majorVersion;
  }
  







  public void setMajorVersion(String majorVersion)
  {
    this.majorVersion = majorVersion;
  }
  







  public String getMinorVersion()
  {
    return minorVersion;
  }
  







  public void setMinorVersion(String minorVersion)
  {
    this.minorVersion = minorVersion;
  }
  







  public String getPatchLevel()
  {
    return patchLevel;
  }
  







  public void setPatchLevel(String patchLevel)
  {
    this.patchLevel = patchLevel;
  }
  






  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof DefaultModuleInfo))
    {
      return false;
    }
    
    ModuleInfo defaultModuleInfo = (ModuleInfo)o;
    
    if (!moduleClass.equals(defaultModuleInfo.getModuleClass()))
    {
      return false;
    }
    return true;
  }
  







  public int hashCode()
  {
    int result = moduleClass.hashCode();
    return result;
  }
  







  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append(getClass().getName());
    buffer.append("={ModuleClass=");
    buffer.append(getModuleClass());
    if (getMajorVersion() != null)
    {
      buffer.append("; Version=");
      buffer.append(getMajorVersion());
      if (getMinorVersion() != null)
      {
        buffer.append("-");
        buffer.append(getMinorVersion());
        if (getPatchLevel() != null)
        {
          buffer.append("_");
          buffer.append(getPatchLevel());
        }
      }
    }
    buffer.append("}");
    return buffer.toString();
  }
}
