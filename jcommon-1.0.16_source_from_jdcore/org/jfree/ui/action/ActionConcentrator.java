package org.jfree.ui.action;

import java.util.ArrayList;
import javax.swing.Action;




















































public class ActionConcentrator
{
  private final ArrayList actions;
  
  public ActionConcentrator()
  {
    actions = new ArrayList();
  }
  




  public void addAction(Action a)
  {
    if (a == null) {
      throw new NullPointerException();
    }
    actions.add(a);
  }
  




  public void removeAction(Action a)
  {
    if (a == null) {
      throw new NullPointerException();
    }
    actions.remove(a);
  }
  




  public void setEnabled(boolean b)
  {
    for (int i = 0; i < actions.size(); i++) {
      Action a = (Action)actions.get(i);
      a.setEnabled(b);
    }
  }
  







  public boolean isEnabled()
  {
    for (int i = 0; i < actions.size(); i++) {
      Action a = (Action)actions.get(i);
      if (a.isEnabled()) {
        return true;
      }
    }
    return false;
  }
}
