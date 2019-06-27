package org.jfree.ui.action;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Action;






























































public class DowngradeActionMap
{
  private final HashMap actionMap;
  private final ArrayList actionList;
  private DowngradeActionMap parent;
  
  public DowngradeActionMap()
  {
    actionMap = new HashMap();
    actionList = new ArrayList();
  }
  




  public void setParent(DowngradeActionMap map)
  {
    parent = map;
  }
  





  public DowngradeActionMap getParent()
  {
    return parent;
  }
  









  public void put(Object key, Action action)
  {
    if (action == null) {
      remove(key);
    }
    else {
      if (actionMap.containsKey(key)) {
        remove(key);
      }
      actionMap.put(key, action);
      actionList.add(key);
    }
  }
  






  public Action get(Object key)
  {
    Action retval = (Action)actionMap.get(key);
    if (retval != null) {
      return retval;
    }
    if (parent != null) {
      return parent.get(key);
    }
    return null;
  }
  




  public void remove(Object key)
  {
    actionMap.remove(key);
    actionList.remove(key);
  }
  


  public void clear()
  {
    actionMap.clear();
    actionList.clear();
  }
  




  public Object[] keys()
  {
    return actionList.toArray();
  }
  




  public int size()
  {
    return actionMap.size();
  }
  






  public Object[] allKeys()
  {
    if (parent == null) {
      return keys();
    }
    Object[] parentKeys = parent.allKeys();
    Object[] key = keys();
    Object[] retval = new Object[parentKeys.length + key.length];
    System.arraycopy(key, 0, retval, 0, key.length);
    System.arraycopy(retval, 0, retval, key.length, retval.length);
    return retval;
  }
}
