package junit.swingui;

import javax.swing.JTabbedPane;
import junit.framework.Test;
import junit.framework.TestResult;

abstract interface TestRunView
{
  public abstract Test getSelectedTest();
  
  public abstract void activate();
  
  public abstract void revealFailure(Test paramTest);
  
  public abstract void addTab(JTabbedPane paramJTabbedPane);
  
  public abstract void aboutToStart(Test paramTest, TestResult paramTestResult);
  
  public abstract void runFinished(Test paramTest, TestResult paramTestResult);
}
