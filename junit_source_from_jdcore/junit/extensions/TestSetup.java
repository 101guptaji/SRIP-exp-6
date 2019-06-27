package junit.extensions;

import junit.framework.Protectable;
import junit.framework.Test;
import junit.framework.TestResult;




public class TestSetup
  extends TestDecorator
{
  public TestSetup(Test test) { super(test); }
  
  public void run(TestResult result) {
    Protectable p = new Protectable() { private final TestResult val$result;
      
      public void protect() throws Exception { setUp();
        basicRun(val$result);
        tearDown();
      }
    };
    result.runProtected(this, p);
  }
  
  protected void setUp()
    throws Exception
  {}
  
  protected void tearDown()
    throws Exception
  {}
}
