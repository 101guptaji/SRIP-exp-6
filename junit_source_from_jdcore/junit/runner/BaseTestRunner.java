package junit.runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.Properties;
import junit.framework.Test;

public abstract class BaseTestRunner implements junit.framework.TestListener
{
  static boolean fgFilterStack = true;
  boolean fLoading = true;
  
  public BaseTestRunner() {}
  
  public synchronized void startTest(Test test)
  {
    testStarted(test.toString());
  }
  
  protected static void setPreferences(Properties preferences) {
    fPreferences = preferences;
  }
  
  protected static Properties getPreferences() {
    if (fPreferences == null) {
      fPreferences = new Properties();
      fPreferences.put("loading", "true");
      fPreferences.put("filterstack", "true");
      readPreferences();
    }
    return fPreferences;
  }
  
  /* Error */
  public static void savePreferences()
    throws IOException
  {
    // Byte code:
    //   0: new 83	java/io/FileOutputStream
    //   3: dup
    //   4: invokestatic 87	junit/runner/BaseTestRunner:getPreferencesFile	()Ljava/io/File;
    //   7: invokespecial 90	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   10: astore_0
    //   11: invokestatic 92	junit/runner/BaseTestRunner:getPreferences	()Ljava/util/Properties;
    //   14: aload_0
    //   15: ldc 94
    //   17: invokevirtual 98	java/util/Properties:store	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   20: goto +9 -> 29
    //   23: astore_2
    //   24: jsr +11 -> 35
    //   27: aload_2
    //   28: athrow
    //   29: jsr +6 -> 35
    //   32: goto +10 -> 42
    //   35: astore_1
    //   36: aload_0
    //   37: invokevirtual 101	java/io/FileOutputStream:close	()V
    //   40: ret 1
    //   42: return
    // Line number table:
    //   Java source line #43	-> byte code offset #0
    //   Java source line #45	-> byte code offset #11
    //   Java source line #46	-> byte code offset #23
    //   Java source line #47	-> byte code offset #36
    //   Java source line #44	-> byte code offset #40
    //   Java source line #49	-> byte code offset #42
    // Local variable table:
    //   start	length	slot	name	signature
    //   10	27	0	fos	java.io.FileOutputStream
    //   35	1	1	localObject1	Object
    //   23	5	2	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   11	23	23	finally
  }
  
  public void setPreference(String key, String value)
  {
    getPreferences().setProperty(key, value);
  }
  
  public synchronized void endTest(Test test) {
    testEnded(test.toString());
  }
  
  public synchronized void addError(Test test, Throwable t) {
    testFailed(1, test, t);
  }
  
  public synchronized void addFailure(Test test, junit.framework.AssertionFailedError t) {
    testFailed(2, test, t);
  }
  


  public abstract void testStarted(String paramString);
  

  public abstract void testEnded(String paramString);
  

  public abstract void testFailed(int paramInt, Test paramTest, Throwable paramThrowable);
  

  public Test getTest(String suiteClassName)
  {
    if (suiteClassName.length() <= 0) {
      clearStatus();
      return null;
    }
    Class testClass = null;
    try {
      testClass = loadSuiteClass(suiteClassName);
    } catch (ClassNotFoundException e) {
      String clazz = e.getMessage();
      if (clazz == null)
        clazz = suiteClassName;
      runFailed("Class not found \"" + clazz + "\"");
      return null;
    } catch (Exception e) {
      runFailed("Error: " + e.toString());
      return null;
    }
    Method suiteMethod = null;
    try {
      suiteMethod = testClass.getMethod("suite", new Class[0]);
    }
    catch (Exception e) {
      clearStatus();
      return new junit.framework.TestSuite(testClass);
    }
    if (!java.lang.reflect.Modifier.isStatic(suiteMethod.getModifiers())) {
      runFailed("Suite() method must be static");
      return null;
    }
    Test test = null;
    try {
      test = (Test)suiteMethod.invoke(null, new Class[0]);
      if (test == null) {
        return test;
      }
    } catch (InvocationTargetException e) {
      runFailed("Failed to invoke suite():" + e.getTargetException().toString());
      return null;
    }
    catch (IllegalAccessException e) {
      runFailed("Failed to invoke suite():" + e.toString());
      return null;
    }
    
    clearStatus();
    return test;
  }
  


  public String elapsedTimeAsString(long runTime)
  {
    return NumberFormat.getInstance().format(runTime / 1000.0D);
  }
  



  protected String processArguments(String[] args)
  {
    String suiteName = null;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-noloading")) {
        setLoading(false);
      } else if (args[i].equals("-nofilterstack")) {
        fgFilterStack = false;
      } else if (args[i].equals("-c")) {
        if (args.length > i + 1) {
          suiteName = extractClassName(args[(i + 1)]);
        } else
          System.out.println("Missing Test class name");
        i++;
      } else {
        suiteName = args[i];
      }
    }
    return suiteName;
  }
  


  public void setLoading(boolean enable)
  {
    fLoading = enable;
  }
  

  public String extractClassName(String className)
  {
    if (className.startsWith("Default package for"))
      return className.substring(className.lastIndexOf(".") + 1);
    return className;
  }
  


  public static String truncate(String s)
  {
    if ((fgMaxMessageLength != -1) && (s.length() > fgMaxMessageLength))
      s = s.substring(0, fgMaxMessageLength) + "...";
    return s;
  }
  



  protected abstract void runFailed(String paramString);
  


  protected Class loadSuiteClass(String suiteClassName)
    throws ClassNotFoundException
  {
    return getLoader().load(suiteClassName);
  }
  



  protected void clearStatus() {}
  



  public TestSuiteLoader getLoader()
  {
    if (useReloadingTestSuiteLoader())
      return new ReloadingTestSuiteLoader();
    return new StandardTestSuiteLoader();
  }
  
  protected boolean useReloadingTestSuiteLoader() {
    return (getPreference("loading").equals("true")) && (!inVAJava()) && (fLoading);
  }
  
  private static File getPreferencesFile() {
    String home = System.getProperty("user.home");
    return new File(home, "junit.properties");
  }
  
  private static void readPreferences() {
    InputStream is = null;
    try {
      is = new java.io.FileInputStream(getPreferencesFile());
      setPreferences(new Properties(getPreferences()));
      getPreferences().load(is);
    } catch (IOException e) {
      try {
        if (is != null) {
          is.close();
        }
      } catch (IOException localIOException1) {}
    }
  }
  
  public static String getPreference(String key) {
    return getPreferences().getProperty(key);
  }
  
  public static int getPreference(String key, int dflt) {
    String value = getPreference(key);
    int intValue = dflt;
    if (value == null)
      return intValue;
    try {
      intValue = Integer.parseInt(value);
    }
    catch (NumberFormatException localNumberFormatException) {}
    return intValue;
  }
  
  public static boolean inVAJava() {
    try {
      Class.forName("com.ibm.uvm.tools.DebugSupport");
    }
    catch (Exception e) {
      return false;
    }
    return true;
  }
  


  public static String getFilteredTrace(Throwable t)
  {
    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);
    t.printStackTrace(writer);
    StringBuffer buffer = stringWriter.getBuffer();
    String trace = buffer.toString();
    return getFilteredTrace(trace);
  }
  


  public static String getFilteredTrace(String stack)
  {
    if (showStackRaw()) {
      return stack;
    }
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    StringReader sr = new StringReader(stack);
    BufferedReader br = new BufferedReader(sr);
    try
    {
      String line;
      while ((line = br.readLine()) != null) { String line;
        if (!filterLine(line))
          pw.println(line);
      }
    } catch (Exception IOException) {
      return stack; }
    String line;
    return sw.toString();
  }
  
  protected static boolean showStackRaw() {
    return (!getPreference("filterstack").equals("true")) || (!fgFilterStack);
  }
  
  static boolean filterLine(String line) {
    String[] patterns = {
      "junit.framework.TestCase", 
      "junit.framework.TestResult", 
      "junit.framework.TestSuite", 
      "junit.framework.Assert.", 
      "junit.swingui.TestRunner", 
      "junit.awtui.TestRunner", 
      "junit.textui.TestRunner", 
      "java.lang.reflect.Method.invoke(" };
    
    for (int i = 0; i < patterns.length; i++) {
      if (line.indexOf(patterns[i]) > 0)
        return true;
    }
    return false;
  }
  

  static int fgMaxMessageLength = getPreference("maxmessage", fgMaxMessageLength);
  public static final String SUITE_METHODNAME = "suite";
  private static Properties fPreferences;
}
