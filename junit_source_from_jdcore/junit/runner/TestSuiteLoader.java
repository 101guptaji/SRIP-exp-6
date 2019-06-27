package junit.runner;

public abstract interface TestSuiteLoader
{
  public abstract Class load(String paramString)
    throws ClassNotFoundException;
  
  public abstract Class reload(Class paramClass)
    throws ClassNotFoundException;
}
