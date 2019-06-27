package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.Vector;


















public class TestSuite
  implements Test
{
  private Vector fTests = new Vector(10);
  


  private String fName;
  


  public TestSuite() {}
  


  public TestSuite(Class theClass, String name)
  {
    this(theClass);
    setName(name);
  }
  





  public TestSuite(Class theClass)
  {
    fName = theClass.getName();
    try {
      getTestConstructor(theClass);
    } catch (NoSuchMethodException e) {
      addTest(warning("Class " + theClass.getName() + " has no public constructor TestCase(String name) or TestCase()"));
      return;
    }
    
    if (!Modifier.isPublic(theClass.getModifiers())) {
      addTest(warning("Class " + theClass.getName() + " is not public"));
      return;
    }
    
    Class superClass = theClass;
    Vector names = new Vector();
    while (Test.class.isAssignableFrom(superClass)) {
      Method[] methods = superClass.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++) {
        addTestMethod(methods[i], names, theClass);
      }
      superClass = superClass.getSuperclass();
    }
    if (fTests.size() == 0) {
      addTest(warning("No tests found in " + theClass.getName()));
    }
  }
  

  public TestSuite(String name)
  {
    setName(name);
  }
  


  public void addTest(Test test)
  {
    fTests.addElement(test);
  }
  


  public void addTestSuite(Class testClass)
  {
    addTest(new TestSuite(testClass));
  }
  
  private void addTestMethod(Method m, Vector names, Class theClass) {
    String name = m.getName();
    if (names.contains(name))
      return;
    if (!isPublicTestMethod(m)) {
      if (isTestMethod(m))
        addTest(warning("Test method isn't public: " + m.getName()));
      return;
    }
    names.addElement(name);
    addTest(createTest(theClass, name));
  }
  



  public static Test createTest(Class theClass, String name)
  {
    try
    {
      constructor = getTestConstructor(theClass);
    } catch (NoSuchMethodException e) { Constructor constructor;
      return warning("Class " + theClass.getName() + " has no public constructor TestCase(String name) or TestCase()");
    }
    try {
      Constructor constructor;
      if (constructor.getParameterTypes().length == 0) {
        Object test = constructor.newInstance(new Object[0]);
        if ((test instanceof TestCase))
          ((TestCase)test).setName(name);
      } else {
        test = constructor.newInstance(new Object[] { name });
      }
    } catch (InstantiationException e) { Object test;
      return warning("Cannot instantiate test case: " + name + " (" + exceptionToString(e) + ")");
    } catch (InvocationTargetException e) {
      return warning("Exception in constructor: " + name + " (" + exceptionToString(e.getTargetException()) + ")");
    } catch (IllegalAccessException e) {
      return warning("Cannot access test case: " + name + " (" + exceptionToString(e) + ")"); }
    Object test;
    return (Test)test;
  }
  


  private static String exceptionToString(Throwable t)
  {
    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    t.printStackTrace(writer);
    return stringWriter.toString();
  }
  



  public int countTestCases()
  {
    int count = 0;
    for (Enumeration e = tests(); e.hasMoreElements();) {
      Test test = (Test)e.nextElement();
      count += test.countTestCases();
    }
    return count;
  }
  


  public static Constructor getTestConstructor(Class theClass)
    throws NoSuchMethodException
  {
    Class[] args = { String.class };
    try {
      return theClass.getConstructor(args);
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}
    
    return theClass.getConstructor(new Class[0]);
  }
  
  private boolean isPublicTestMethod(Method m) {
    return (isTestMethod(m)) && (Modifier.isPublic(m.getModifiers()));
  }
  
  private boolean isTestMethod(Method m) {
    String name = m.getName();
    Class[] parameters = m.getParameterTypes();
    Class returnType = m.getReturnType();
    return (parameters.length == 0) && (name.startsWith("test")) && (returnType.equals(Void.TYPE));
  }
  


  public void run(TestResult result)
  {
    for (Enumeration e = tests(); e.hasMoreElements();) {
      if (result.shouldStop())
        break;
      Test test = (Test)e.nextElement();
      runTest(test, result);
    }
  }
  
  public void runTest(Test test, TestResult result) {
    test.run(result);
  }
  


  public Test testAt(int index)
  {
    return (Test)fTests.elementAt(index);
  }
  


  public int testCount()
  {
    return fTests.size();
  }
  


  public Enumeration tests()
  {
    return fTests.elements();
  }
  

  public String toString()
  {
    if (getName() != null)
      return getName();
    return super.toString();
  }
  



  public void setName(String name)
  {
    fName = name;
  }
  




  public String getName()
  {
    return fName;
  }
  


  private static Test warning(String message)
  {
    new TestCase(message) {
      protected void runTest() {
        Assert.fail(TestSuite.this);
      }
    };
  }
}
