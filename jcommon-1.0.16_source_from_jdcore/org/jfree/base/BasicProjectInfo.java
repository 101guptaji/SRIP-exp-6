package org.jfree.base;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.jfree.util.ObjectUtilities;













































public class BasicProjectInfo
  extends Library
{
  private String copyright;
  private List libraries;
  private List optionalLibraries;
  
  private static class OptionalLibraryHolder
  {
    private String libraryClass;
    private transient Library library;
    
    public OptionalLibraryHolder(String libraryClass)
    {
      if (libraryClass == null) {
        throw new NullPointerException("LibraryClass must not be null.");
      }
      this.libraryClass = libraryClass;
    }
    
    public OptionalLibraryHolder(Library library) {
      if (library == null) {
        throw new NullPointerException("Library must not be null.");
      }
      this.library = library;
      libraryClass = library.getClass().getName();
    }
    
    public String getLibraryClass() {
      return libraryClass;
    }
    
    public Library getLibrary() {
      if (library == null) {
        library = loadLibrary(libraryClass);
      }
      return library;
    }
    
    protected Library loadLibrary(String classname) {
      if (classname == null) {
        return null;
      }
      try {
        Class c = ObjectUtilities.getClassLoader(getClass()).loadClass(classname);
        try
        {
          Method m = c.getMethod("getInstance", (Class[])null);
          return (Library)m.invoke(null, (Object[])null);

        }
        catch (Exception e)
        {
          return (Library)c.newInstance();
        }
        


        return null;
      }
      catch (Exception e) {}
    }
  }
  










  public BasicProjectInfo()
  {
    libraries = new ArrayList();
    optionalLibraries = new ArrayList();
  }
  








  public BasicProjectInfo(String name, String version, String licence, String info)
  {
    this();
    setName(name);
    setVersion(version);
    setLicenceName(licence);
    setInfo(info);
  }
  










  public BasicProjectInfo(String name, String version, String info, String copyright, String licenceName)
  {
    this(name, version, licenceName, info);
    setCopyright(copyright);
  }
  




  public String getCopyright()
  {
    return copyright;
  }
  




  public void setCopyright(String copyright)
  {
    this.copyright = copyright;
  }
  




  public void setInfo(String info)
  {
    super.setInfo(info);
  }
  




  public void setLicenceName(String licence)
  {
    super.setLicenceName(licence);
  }
  




  public void setName(String name)
  {
    super.setName(name);
  }
  




  public void setVersion(String version)
  {
    super.setVersion(version);
  }
  




  public Library[] getLibraries()
  {
    return (Library[])libraries.toArray(new Library[libraries.size()]);
  }
  





  public void addLibrary(Library library)
  {
    if (library == null) {
      throw new NullPointerException();
    }
    libraries.add(library);
  }
  




  public Library[] getOptionalLibraries()
  {
    ArrayList libraries = new ArrayList();
    for (int i = 0; i < optionalLibraries.size(); i++) {
      OptionalLibraryHolder holder = (OptionalLibraryHolder)optionalLibraries.get(i);
      
      Library l = holder.getLibrary();
      if (l != null) {
        libraries.add(l);
      }
    }
    return (Library[])libraries.toArray(new Library[libraries.size()]);
  }
  






  public void addOptionalLibrary(String libraryClass)
  {
    if (libraryClass == null) {
      throw new NullPointerException("Library classname must be given.");
    }
    optionalLibraries.add(new OptionalLibraryHolder(libraryClass));
  }
  








  public void addOptionalLibrary(Library library)
  {
    if (library == null) {
      throw new NullPointerException("Library must be given.");
    }
    optionalLibraries.add(new OptionalLibraryHolder(library));
  }
}
