package junit.textui;

import java.io.InputStream;
import java.io.PrintStream;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;
import junit.runner.BaseTestRunner;
import junit.runner.StandardTestSuiteLoader;
import junit.runner.TestSuiteLoader;
import junit.runner.Version;














public class TestRunner
  extends BaseTestRunner
{
  private ResultPrinter fPrinter;
  public static final int SUCCESS_EXIT = 0;
  public static final int FAILURE_EXIT = 1;
  public static final int EXCEPTION_EXIT = 2;
  
  public TestRunner()
  {
    this(System.out);
  }
  


  public TestRunner(PrintStream writer)
  {
    this(new ResultPrinter(writer));
  }
  


  public TestRunner(ResultPrinter printer)
  {
    fPrinter = printer;
  }
  


  public static void run(Class testClass)
  {
    run(new TestSuite(testClass));
  }
  









  public static TestResult run(Test test)
  {
    TestRunner runner = new TestRunner();
    return runner.doRun(test);
  }
  



  public static void runAndWait(Test suite)
  {
    TestRunner aTestRunner = new TestRunner();
    aTestRunner.doRun(suite, true);
  }
  



  public TestSuiteLoader getLoader()
  {
    return new StandardTestSuiteLoader();
  }
  


  public void testFailed(int status, Test test, Throwable t) {}
  

  public void testStarted(String testName) {}
  

  public void testEnded(String testName) {}
  

  protected TestResult createTestResult()
  {
    return new TestResult();
  }
  
  public TestResult doRun(Test test) {
    return doRun(test, false);
  }
  
  public TestResult doRun(Test suite, boolean wait) {
    TestResult result = createTestResult();
    result.addListener(fPrinter);
    long startTime = System.currentTimeMillis();
    suite.run(result);
    long endTime = System.currentTimeMillis();
    long runTime = endTime - startTime;
    fPrinter.print(result, runTime);
    
    pause(wait);
    return result;
  }
  
  protected void pause(boolean wait) {
    if (!wait) return;
    fPrinter.printWaitPrompt();
    try {
      System.in.read();
    }
    catch (Exception localException) {}
  }
  
  public static void main(String[] args)
  {
    TestRunner aTestRunner = new TestRunner();
    try {
      TestResult r = aTestRunner.start(args);
      if (!r.wasSuccessful())
        System.exit(1);
      System.exit(0);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.exit(2);
    }
  }
  


  protected TestResult start(String[] args)
    throws Exception
  {
    String testCase = "";
    boolean wait = false;
    
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-wait")) {
        wait = true;
      } else if (args[i].equals("-c")) {
        testCase = extractClassName(args[(++i)]);
      } else if (args[i].equals("-v")) {
        System.err.println("JUnit " + Version.id() + " by Kent Beck and Erich Gamma");
      } else {
        testCase = args[i];
      }
    }
    if (testCase.equals("")) {
      throw new Exception("Usage: TestRunner [-wait] testCaseName, where name is the name of the TestCase class");
    }
    try {
      Test suite = getTest(testCase);
      return doRun(suite, wait);
    }
    catch (Exception e) {
      throw new Exception("Could not create and run test suite: " + e);
    }
  }
  
  protected void runFailed(String message) {
    System.err.println(message);
    System.exit(1);
  }
  
  public void setPrinter(ResultPrinter printer) {
    fPrinter = printer;
  }
}
