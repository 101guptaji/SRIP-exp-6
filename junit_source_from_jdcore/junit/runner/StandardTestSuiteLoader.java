package junit.runner;

public class StandardTestSuiteLoader
  implements TestSuiteLoader
{
  public StandardTestSuiteLoader() {}
  
  public Class load(String suiteClassName)
    throws ClassNotFoundException
  {
    return Class.forName(suiteClassName);
  }
  
  public Class reload(Class aClass)
    throws ClassNotFoundException
  {
    return aClass;
  }
}
