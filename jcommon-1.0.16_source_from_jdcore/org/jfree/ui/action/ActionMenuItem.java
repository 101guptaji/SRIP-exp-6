package org.jfree.ui.action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import org.jfree.util.Log;





































































public class ActionMenuItem
  extends JMenuItem
{
  private Action action;
  private ActionEnablePropertyChangeHandler propertyChangeHandler;
  public ActionMenuItem() {}
  
  private class ActionEnablePropertyChangeHandler
    implements PropertyChangeListener
  {
    public ActionEnablePropertyChangeHandler() {}
    
    public void propertyChange(PropertyChangeEvent event)
    {
      try
      {
        if (event.getPropertyName().equals("enabled"))
        {
          setEnabled(getAction().isEnabled());
        }
        else if (event.getPropertyName().equals("SmallIcon"))
        {
          setIcon((Icon)getAction().getValue("SmallIcon"));
        }
        else if (event.getPropertyName().equals("Name"))
        {
          setText((String)getAction().getValue("Name"));
        }
        else if (event.getPropertyName().equals("ShortDescription"))
        {
          setToolTipText((String)getAction().getValue("ShortDescription"));
        }
        

        Action ac = getAction();
        if (event.getPropertyName().equals("AcceleratorKey"))
        {
          setAccelerator((KeyStroke)ac.getValue("AcceleratorKey"));
        }
        else if (event.getPropertyName().equals("MnemonicKey"))
        {
          Object o = ac.getValue("MnemonicKey");
          if (o != null)
          {
            if ((o instanceof Character))
            {
              Character c = (Character)o;
              setMnemonic(c.charValue());
            }
            else if ((o instanceof Integer))
            {
              Integer c = (Integer)o;
              setMnemonic(c.intValue());
            }
            
          }
          else {
            setMnemonic(0);
          }
        }
      }
      catch (Exception e)
      {
        Log.warn("Error on PropertyChange in ActionButton: ", e);
      }
    }
  }
  











  public ActionMenuItem(Icon icon)
  {
    super(icon);
  }
  





  public ActionMenuItem(String text)
  {
    super(text);
  }
  






  public ActionMenuItem(String text, Icon icon)
  {
    super(text, icon);
  }
  






  public ActionMenuItem(String text, int i)
  {
    super(text, i);
  }
  





  public ActionMenuItem(Action action)
  {
    setAction(action);
  }
  





  public Action getAction()
  {
    return action;
  }
  







  private ActionEnablePropertyChangeHandler getPropertyChangeHandler()
  {
    if (propertyChangeHandler == null)
    {
      propertyChangeHandler = new ActionEnablePropertyChangeHandler();
    }
    return propertyChangeHandler;
  }
  






  public void setEnabled(boolean b)
  {
    super.setEnabled(b);
    if (getAction() != null)
    {
      getAction().setEnabled(b);
    }
  }
  











  public void setAction(Action newAction)
  {
    Action oldAction = getAction();
    if (oldAction != null)
    {
      removeActionListener(oldAction);
      oldAction.removePropertyChangeListener(getPropertyChangeHandler());
      setAccelerator(null);
    }
    action = newAction;
    if (action != null)
    {
      addActionListener(newAction);
      newAction.addPropertyChangeListener(getPropertyChangeHandler());
      
      setText((String)newAction.getValue("Name"));
      setToolTipText((String)newAction.getValue("ShortDescription"));
      setIcon((Icon)newAction.getValue("SmallIcon"));
      setEnabled(action.isEnabled());
      
      Object o = newAction.getValue("MnemonicKey");
      if (o != null)
      {
        if ((o instanceof Character))
        {
          Character c = (Character)o;
          setMnemonic(c.charValue());
        }
        else if ((o instanceof Integer))
        {
          Integer c = (Integer)o;
          setMnemonic(c.intValue());
        }
        
      }
      else {
        setMnemonic(0);
      }
      

      o = newAction.getValue("AcceleratorKey");
      if ((o instanceof KeyStroke))
      {
        setAccelerator((KeyStroke)o);
      }
    }
  }
}
