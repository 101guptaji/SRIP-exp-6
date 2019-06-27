package org.jfree.ui.action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import org.jfree.util.Log;




































































public class ActionButton
  extends JButton
{
  private Action action;
  private ActionEnablePropertyChangeHandler propertyChangeHandler;
  public ActionButton() {}
  
  private class ActionEnablePropertyChangeHandler
    implements PropertyChangeListener
  {
    public ActionEnablePropertyChangeHandler() {}
    
    public void propertyChange(PropertyChangeEvent event)
    {
      try
      {
        if (event.getPropertyName().equals("enabled")) {
          setEnabled(getAction().isEnabled());
        }
        else if (event.getPropertyName().equals("SmallIcon")) {
          setIcon((Icon)getAction().getValue("SmallIcon"));
        }
        else if (event.getPropertyName().equals("Name")) {
          setText((String)getAction().getValue("Name"));

        }
        else if (event.getPropertyName().equals("ShortDescription")) {
          setToolTipText((String)getAction().getValue("ShortDescription"));
        }
        

        Action ac = getAction();
        if (event.getPropertyName().equals("AcceleratorKey")) {
          KeyStroke oldVal = (KeyStroke)event.getOldValue();
          if (oldVal != null) {
            unregisterKeyboardAction(oldVal);
          }
          Object o = ac.getValue("AcceleratorKey");
          if ((o instanceof KeyStroke)) {
            KeyStroke k = (KeyStroke)o;
            registerKeyboardAction(ac, k, 2);
          }
        }
        else if (event.getPropertyName().equals("MnemonicKey")) {
          Object o = ac.getValue("MnemonicKey");
          if (o != null) {
            if ((o instanceof Character)) {
              Character c = (Character)o;
              setMnemonic(c.charValue());
            }
            else if ((o instanceof Integer)) {
              Integer c = (Integer)o;
              setMnemonic(c.intValue());
            }
          }
        }
      }
      catch (Exception e) {
        Log.warn("Error on PropertyChange in ActionButton: ", e);
      }
    }
  }
  











  public ActionButton(String text)
  {
    super(text);
  }
  





  public ActionButton(String text, Icon icon)
  {
    super(text, icon);
  }
  





  public ActionButton(Icon icon)
  {
    super(icon);
  }
  




  public ActionButton(Action action)
  {
    setAction(action);
  }
  




  public Action getAction()
  {
    return action;
  }
  






  private ActionEnablePropertyChangeHandler getPropertyChangeHandler()
  {
    if (propertyChangeHandler == null) {
      propertyChangeHandler = new ActionEnablePropertyChangeHandler();
    }
    return propertyChangeHandler;
  }
  





  public void setEnabled(boolean b)
  {
    super.setEnabled(b);
    if (getAction() != null) {
      getAction().setEnabled(b);
    }
  }
  












  public void setAction(Action newAction)
  {
    Action oldAction = getAction();
    if (oldAction != null) {
      removeActionListener(oldAction);
      oldAction.removePropertyChangeListener(getPropertyChangeHandler());
      
      Object o = oldAction.getValue("AcceleratorKey");
      if ((o instanceof KeyStroke)) {
        KeyStroke k = (KeyStroke)o;
        unregisterKeyboardAction(k);
      }
    }
    action = newAction;
    if (action != null) {
      addActionListener(newAction);
      newAction.addPropertyChangeListener(getPropertyChangeHandler());
      
      setText((String)newAction.getValue("Name"));
      setToolTipText((String)newAction.getValue("ShortDescription"));
      setIcon((Icon)newAction.getValue("SmallIcon"));
      setEnabled(action.isEnabled());
      
      Object o = newAction.getValue("MnemonicKey");
      if (o != null) {
        if ((o instanceof Character)) {
          Character c = (Character)o;
          setMnemonic(c.charValue());
        }
        else if ((o instanceof Integer)) {
          Integer c = (Integer)o;
          setMnemonic(c.intValue());
        }
      }
      o = newAction.getValue("AcceleratorKey");
      if ((o instanceof KeyStroke)) {
        KeyStroke k = (KeyStroke)o;
        registerKeyboardAction(newAction, k, 2);
      }
    }
  }
}
