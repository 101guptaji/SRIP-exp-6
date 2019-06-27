package junit.runner;

import java.util.Enumeration;

public abstract interface TestCollector
{
  public abstract Enumeration collectTests();
}
