package junit.extensions;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;



public class ActiveTestSuite
  extends TestSuite
{
  private volatile int fActiveTestDeathCount;
  
  public ActiveTestSuite() {}
  
  public ActiveTestSuite(Class theClass)
  {
    super(theClass);
  }
  
  public ActiveTestSuite(String name) {
    super(name);
  }
  
  public ActiveTestSuite(Class theClass, String name) {
    super(theClass, name);
  }
  
  public void run(TestResult result) {
    fActiveTestDeathCount = 0;
    super.run(result);
    waitUntilFinished();
  }
  
  public void runTest(Test test, TestResult result) {
    Thread t = new Thread()
    {
      private final Test val$test;
      private final TestResult val$result;
      
      /* Error */
      public void run()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 21	junit/extensions/ActiveTestSuite$1:val$test	Ljunit/framework/Test;
        //   4: aload_0
        //   5: getfield 23	junit/extensions/ActiveTestSuite$1:val$result	Ljunit/framework/TestResult;
        //   8: invokeinterface 33 2 0
        //   13: goto +9 -> 22
        //   16: astore_2
        //   17: jsr +11 -> 28
        //   20: aload_2
        //   21: athrow
        //   22: jsr +6 -> 28
        //   25: goto +17 -> 42
        //   28: astore_1
        //   29: aload_0
        //   30: getfield 19	junit/extensions/ActiveTestSuite$1:this$0	Ljunit/extensions/ActiveTestSuite;
        //   33: aload_0
        //   34: getfield 21	junit/extensions/ActiveTestSuite$1:val$test	Ljunit/framework/Test;
        //   37: invokevirtual 39	junit/extensions/ActiveTestSuite:runFinished	(Ljunit/framework/Test;)V
        //   40: ret 1
        //   42: return
        // Line number table:
        //   Java source line #41	-> byte code offset #0
        //   Java source line #42	-> byte code offset #16
        //   Java source line #43	-> byte code offset #29
        //   Java source line #38	-> byte code offset #40
        //   Java source line #45	-> byte code offset #42
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	43	0	this	1
        //   28	1	1	localObject1	Object
        //   16	5	2	localObject2	Object
        // Exception table:
        //   from	to	target	type
        //   0	16	16	finally
      }
    };
    t.start();
  }
  
  synchronized void waitUntilFinished() {
    while (fActiveTestDeathCount < testCount()) {
      try {
        wait();
      } catch (InterruptedException e) {
        return;
      }
    }
  }
  
  public synchronized void runFinished(Test test) {
    fActiveTestDeathCount += 1;
    notifyAll();
  }
}
