package org.jfree.base.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.jfree.util.ObjectUtilities;




























































































public abstract class AbstractModule
  extends DefaultModuleInfo
  implements Module
{
  private ModuleInfo[] requiredModules;
  private ModuleInfo[] optionalModules;
  private String name;
  private String description;
  private String producer;
  private String subsystem;
  
  private static class ReaderHelper
  {
    private String buffer;
    private final BufferedReader reader;
    
    protected ReaderHelper(BufferedReader reader)
    {
      this.reader = reader;
    }
    






    public boolean hasNext()
      throws IOException
    {
      if (buffer == null)
      {
        buffer = readLine();
      }
      return buffer != null;
    }
    





    public String next()
    {
      String line = buffer;
      buffer = null;
      return line;
    }
    






    public void pushBack(String line)
    {
      buffer = line;
    }
    





    protected String readLine()
      throws IOException
    {
      String line = reader.readLine();
      while ((line != null) && ((line.length() == 0) || (line.startsWith("#"))))
      {

        line = reader.readLine();
      }
      return line;
    }
    




    public void close()
      throws IOException
    {
      reader.close();
    }
  }
  

















  public AbstractModule()
  {
    setModuleClass(getClass().getName());
  }
  





  protected void loadModuleInfo()
    throws ModuleInitializeException
  {
    InputStream in = ObjectUtilities.getResourceRelativeAsStream("module.properties", getClass());
    
    if (in == null)
    {
      throw new ModuleInitializeException("File 'module.properties' not found in module package.");
    }
    

    loadModuleInfo(in);
  }
  







  protected void loadModuleInfo(InputStream in)
    throws ModuleInitializeException
  {
    if (in == null)
    {
      throw new NullPointerException("Given InputStream is null.");
    }
    

    try
    {
      ArrayList optionalModules = new ArrayList();
      ArrayList dependendModules = new ArrayList();
      ReaderHelper rh = new ReaderHelper(new BufferedReader(new InputStreamReader(in, "ISO-8859-1")));
      
      try
      {
        while (rh.hasNext())
        {
          String lastLineRead = rh.next();
          if (lastLineRead.startsWith("module-info:"))
          {
            readModuleInfo(rh);
          }
          else if (lastLineRead.startsWith("depends:"))
          {
            dependendModules.add(readExternalModule(rh));
          }
          else if (lastLineRead.startsWith("optional:"))
          {
            optionalModules.add(readExternalModule(rh));

          }
          
        }
        

      }
      finally
      {

        rh.close();
      }
      
      this.optionalModules = ((ModuleInfo[])optionalModules.toArray(new ModuleInfo[optionalModules.size()]));
      

      requiredModules = ((ModuleInfo[])dependendModules.toArray(new ModuleInfo[dependendModules.size()]));

    }
    catch (IOException ioe)
    {
      throw new ModuleInitializeException("Failed to load properties", ioe);
    }
  }
  








  private String readValue(ReaderHelper reader, String firstLine)
    throws IOException
  {
    StringBuffer b = new StringBuffer(firstLine.trim());
    boolean newLine = true;
    while (isNextLineValueLine(reader))
    {
      firstLine = reader.next();
      String trimedLine = firstLine.trim();
      if ((trimedLine.length() == 0) && (!newLine))
      {
        b.append("\n");
        newLine = true;
      }
      else
      {
        if (!newLine)
        {
          b.append(" ");
        }
        b.append(parseValue(trimedLine));
        newLine = false;
      }
    }
    return b.toString();
  }
  






  private boolean isNextLineValueLine(ReaderHelper reader)
    throws IOException
  {
    if (!reader.hasNext())
    {
      return false;
    }
    String firstLine = reader.next();
    if (firstLine == null)
    {
      return false;
    }
    if (parseKey(firstLine) != null)
    {
      reader.pushBack(firstLine);
      return false;
    }
    reader.pushBack(firstLine);
    return true;
  }
  






  private void readModuleInfo(ReaderHelper reader)
    throws IOException
  {
    while (reader.hasNext())
    {
      String lastLineRead = reader.next();
      
      if (!Character.isWhitespace(lastLineRead.charAt(0)))
      {

        reader.pushBack(lastLineRead);
        return;
      }
      
      String line = lastLineRead.trim();
      String key = parseKey(line);
      if (key != null)
      {

        String b = readValue(reader, parseValue(line.trim()));
        
        if ("name".equals(key))
        {
          setName(b);
        }
        else if ("producer".equals(key))
        {
          setProducer(b);
        }
        else if ("description".equals(key))
        {
          setDescription(b);
        }
        else if ("subsystem".equals(key))
        {
          setSubSystem(b);
        }
        else if ("version.major".equals(key))
        {
          setMajorVersion(b);
        }
        else if ("version.minor".equals(key))
        {
          setMinorVersion(b);
        }
        else if ("version.patchlevel".equals(key))
        {
          setPatchLevel(b);
        }
      }
    }
  }
  







  private String parseKey(String line)
  {
    int idx = line.indexOf(':');
    if (idx == -1)
    {
      return null;
    }
    return line.substring(0, idx);
  }
  






  private String parseValue(String line)
  {
    int idx = line.indexOf(':');
    if (idx == -1)
    {
      return line;
    }
    if (idx + 1 == line.length())
    {
      return "";
    }
    return line.substring(idx + 1);
  }
  








  private DefaultModuleInfo readExternalModule(ReaderHelper reader)
    throws IOException
  {
    DefaultModuleInfo mi = new DefaultModuleInfo();
    
    while (reader.hasNext())
    {
      String lastLineRead = reader.next();
      
      if (!Character.isWhitespace(lastLineRead.charAt(0)))
      {

        reader.pushBack(lastLineRead);
        return mi;
      }
      
      String line = lastLineRead.trim();
      String key = parseKey(line);
      if (key != null)
      {
        String b = readValue(reader, parseValue(line));
        if ("module".equals(key))
        {
          mi.setModuleClass(b);
        }
        else if ("version.major".equals(key))
        {
          mi.setMajorVersion(b);
        }
        else if ("version.minor".equals(key))
        {
          mi.setMinorVersion(b);
        }
        else if ("version.patchlevel".equals(key))
        {
          mi.setPatchLevel(b);
        }
      }
    }
    return mi;
  }
  







  public String getName()
  {
    return name;
  }
  





  protected void setName(String name)
  {
    this.name = name;
  }
  






  public String getDescription()
  {
    return description;
  }
  





  protected void setDescription(String description)
  {
    this.description = description;
  }
  







  public String getProducer()
  {
    return producer;
  }
  





  protected void setProducer(String producer)
  {
    this.producer = producer;
  }
  







  public ModuleInfo[] getRequiredModules()
  {
    ModuleInfo[] retval = new ModuleInfo[requiredModules.length];
    System.arraycopy(requiredModules, 0, retval, 0, requiredModules.length);
    return retval;
  }
  







  public ModuleInfo[] getOptionalModules()
  {
    ModuleInfo[] retval = new ModuleInfo[optionalModules.length];
    System.arraycopy(optionalModules, 0, retval, 0, optionalModules.length);
    return retval;
  }
  





  protected void setRequiredModules(ModuleInfo[] requiredModules)
  {
    this.requiredModules = new ModuleInfo[requiredModules.length];
    System.arraycopy(requiredModules, 0, this.requiredModules, 0, requiredModules.length);
  }
  





  public void setOptionalModules(ModuleInfo[] optionalModules)
  {
    this.optionalModules = new ModuleInfo[optionalModules.length];
    System.arraycopy(optionalModules, 0, this.optionalModules, 0, optionalModules.length);
  }
  






  public String toString()
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append("Module : ");
    buffer.append(getName());
    buffer.append("\n");
    buffer.append("ModuleClass : ");
    buffer.append(getModuleClass());
    buffer.append("\n");
    buffer.append("Version: ");
    buffer.append(getMajorVersion());
    buffer.append(".");
    buffer.append(getMinorVersion());
    buffer.append(".");
    buffer.append(getPatchLevel());
    buffer.append("\n");
    buffer.append("Producer: ");
    buffer.append(getProducer());
    buffer.append("\n");
    buffer.append("Description: ");
    buffer.append(getDescription());
    buffer.append("\n");
    return buffer.toString();
  }
  





  /**
   * @deprecated
   */
  protected static boolean isClassLoadable(String name)
  {
    try
    {
      ClassLoader loader = ObjectUtilities.getClassLoader(AbstractModule.class);
      if (loader == null)
      {

        return false;
      }
      loader.loadClass(name);
      return true;
    }
    catch (Exception e) {}
    
    return false;
  }
  









  protected static boolean isClassLoadable(String name, Class context)
  {
    try
    {
      ObjectUtilities.getClassLoader(context).loadClass(name);
      return true;
    }
    catch (Exception e) {}
    
    return false;
  }
  







  public void configure(SubSystem subSystem)
  {
    InputStream in = ObjectUtilities.getResourceRelativeAsStream("configuration.properties", getClass());
    
    if (in == null)
    {
      return;
    }
    try
    {
      subSystem.getPackageManager().getPackageConfiguration().load(in); return;
    }
    finally
    {
      try
      {
        in.close();
      }
      catch (IOException e) {}
    }
  }
  








  /**
   * @deprecated
   */
  protected void performExternalInitialize(String classname)
    throws ModuleInitializeException
  {
    try
    {
      ModuleInitializer mi = (ModuleInitializer)ObjectUtilities.loadAndInstantiate(classname, AbstractModule.class, ModuleInitializer.class);
      
      if (mi == null)
      {
        throw new ModuleInitializeException("Failed to load specified initializer class.");
      }
      mi.performInit();
    }
    catch (ModuleInitializeException mie)
    {
      throw mie;
    }
    catch (Exception e)
    {
      throw new ModuleInitializeException("Failed to load specified initializer class.", e);
    }
  }
  







  protected void performExternalInitialize(String classname, Class context)
    throws ModuleInitializeException
  {
    try
    {
      ModuleInitializer mi = (ModuleInitializer)ObjectUtilities.loadAndInstantiate(classname, context, ModuleInitializer.class);
      
      if (mi == null)
      {
        throw new ModuleInitializeException("Failed to load specified initializer class.");
      }
      mi.performInit();
    }
    catch (ModuleInitializeException mie)
    {
      throw mie;
    }
    catch (Exception e)
    {
      throw new ModuleInitializeException("Failed to load specified initializer class.", e);
    }
  }
  






  public String getSubSystem()
  {
    if (subsystem == null)
    {
      return getName();
    }
    return subsystem;
  }
  





  protected void setSubSystem(String name)
  {
    subsystem = name;
  }
}
