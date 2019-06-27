package junit.runner;

import java.awt.Component;
import junit.framework.TestFailure;

public abstract interface FailureDetailView
{
  public abstract Component getComponent();
  
  public abstract void showFailure(TestFailure paramTestFailure);
  
  public abstract void clear();
}
