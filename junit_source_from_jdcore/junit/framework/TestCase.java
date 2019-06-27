package junit.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;



































































public abstract class TestCase
  extends Assert
  implements Test
{
  private String fName;
  
  public TestCase()
  {
    fName = null;
  }
  

  public TestCase(String name)
  {
    fName = name;
  }
  

  public int countTestCases()
  {
    return 1;
  }
  



  protected TestResult createResult()
  {
    return new TestResult();
  }
  




  public TestResult run()
  {
    TestResult result = createResult();
    run(result);
    return result;
  }
  

  public void run(TestResult result)
  {
    result.run(this);
  }
  
  /* Error */
  public void runBare()
    throws Throwable
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 46	junit/framework/TestCase:setUp	()V
    //   4: aload_0
    //   5: invokevirtual 49	junit/framework/TestCase:runTest	()V
    //   8: goto +9 -> 17
    //   11: astore_2
    //   12: jsr +11 -> 23
    //   15: aload_2
    //   16: athrow
    //   17: jsr +6 -> 23
    //   20: goto +10 -> 30
    //   23: astore_1
    //   24: aload_0
    //   25: invokevirtual 52	junit/framework/TestCase:tearDown	()V
    //   28: ret 1
    //   30: return
    // Line number table:
    //   Java source line #125	-> byte code offset #0
    //   Java source line #127	-> byte code offset #4
    //   Java source line #129	-> byte code offset #11
    //   Java source line #130	-> byte code offset #24
    //   Java source line #126	-> byte code offset #28
    //   Java source line #132	-> byte code offset #30
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	31	0	this	TestCase
    //   23	1	1	localObject1	Object
    //   11	5	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   4	11	11	finally
  }
  
  protected void runTest()
    throws Throwable
  {
    Assert.assertNotNull(fName);
    Method runMethod = null;
    


    try
    {
      runMethod = getClass().getMethod(fName, null);
    } catch (NoSuchMethodException e) {
      Assert.fail("Method \"" + fName + "\" not found");
    }
    if (!Modifier.isPublic(runMethod.getModifiers())) {
      Assert.fail("Method \"" + fName + "\" should be public");
    }
    try
    {
      runMethod.invoke(this, new Class[0]);
    }
    catch (InvocationTargetException e) {
      e.fillInStackTrace();
      throw e.getTargetException();
    }
    catch (IllegalAccessException e) {
      e.fillInStackTrace();
      throw e;
    }
  }
  


  protected void setUp()
    throws Exception
  {}
  


  protected void tearDown()
    throws Exception
  {}
  

  public String toString()
  {
    return getName() + "(" + getClass().getName() + ")";
  }
  


  public String getName()
  {
    return fName;
  }
  


  public void setName(String name)
  {
    fName = name;
  }
}
