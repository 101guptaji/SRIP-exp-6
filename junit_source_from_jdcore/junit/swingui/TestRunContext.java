package junit.swingui;

import javax.swing.ListModel;
import junit.framework.Test;

public abstract interface TestRunContext
{
  public abstract void handleTestSelected(Test paramTest);
  
  public abstract ListModel getFailures();
}
