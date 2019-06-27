package org.jfree.base;















public class Library
{
  private String name;
  













  private String version;
  













  private String licenceName;
  












  private String info;
  













  public Library(String name, String version, String licence, String info)
  {
    this.name = name;
    this.version = version;
    licenceName = licence;
    this.info = info;
  }
  





  protected Library() {}
  




  public String getName()
  {
    return name;
  }
  




  public String getVersion()
  {
    return version;
  }
  




  public String getLicenceName()
  {
    return licenceName;
  }
  




  public String getInfo()
  {
    return info;
  }
  




  protected void setInfo(String info)
  {
    this.info = info;
  }
  




  protected void setLicenceName(String licenceName)
  {
    this.licenceName = licenceName;
  }
  




  protected void setName(String name)
  {
    this.name = name;
  }
  




  protected void setVersion(String version)
  {
    this.version = version;
  }
  







  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if ((o == null) || (getClass() != o.getClass()))
    {
      return false;
    }
    
    Library library = (Library)o;
    
    if (name != null ? !name.equals(name) : name != null)
    {
      return false;
    }
    
    return true;
  }
  





  public int hashCode()
  {
    return name != null ? name.hashCode() : 0;
  }
}
