package org.jfree.data;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;

























































public class KeyToGroupMap
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -2228169345475318082L;
  private Comparable defaultGroup;
  private List groups;
  private Map keyToGroupMap;
  
  public KeyToGroupMap()
  {
    this("Default Group");
  }
  




  public KeyToGroupMap(Comparable defaultGroup)
  {
    if (defaultGroup == null) {
      throw new IllegalArgumentException("Null 'defaultGroup' argument.");
    }
    this.defaultGroup = defaultGroup;
    groups = new ArrayList();
    keyToGroupMap = new HashMap();
  }
  




  public int getGroupCount()
  {
    return groups.size() + 1;
  }
  






  public List getGroups()
  {
    List result = new ArrayList();
    result.add(defaultGroup);
    Iterator iterator = groups.iterator();
    while (iterator.hasNext()) {
      Comparable group = (Comparable)iterator.next();
      if (!result.contains(group)) {
        result.add(group);
      }
    }
    return result;
  }
  







  public int getGroupIndex(Comparable group)
  {
    int result = groups.indexOf(group);
    if (result < 0) {
      if (defaultGroup.equals(group)) {
        result = 0;
      }
    }
    else {
      result += 1;
    }
    return result;
  }
  







  public Comparable getGroup(Comparable key)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    Comparable result = defaultGroup;
    Comparable group = (Comparable)keyToGroupMap.get(key);
    if (group != null) {
      result = group;
    }
    return result;
  }
  






  public void mapKeyToGroup(Comparable key, Comparable group)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    Comparable currentGroup = getGroup(key);
    if ((!currentGroup.equals(defaultGroup)) && 
      (!currentGroup.equals(group))) {
      int count = getKeyCount(currentGroup);
      if (count == 1) {
        groups.remove(currentGroup);
      }
    }
    
    if (group == null) {
      keyToGroupMap.remove(key);
    }
    else {
      if ((!groups.contains(group)) && 
        (!defaultGroup.equals(group))) {
        groups.add(group);
      }
      
      keyToGroupMap.put(key, group);
    }
  }
  








  public int getKeyCount(Comparable group)
  {
    if (group == null) {
      throw new IllegalArgumentException("Null 'group' argument.");
    }
    int result = 0;
    Iterator iterator = keyToGroupMap.values().iterator();
    while (iterator.hasNext()) {
      Comparable g = (Comparable)iterator.next();
      if (group.equals(g)) {
        result++;
      }
    }
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof KeyToGroupMap)) {
      return false;
    }
    KeyToGroupMap that = (KeyToGroupMap)obj;
    if (!ObjectUtilities.equal(defaultGroup, defaultGroup)) {
      return false;
    }
    if (!keyToGroupMap.equals(keyToGroupMap)) {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    KeyToGroupMap result = (KeyToGroupMap)super.clone();
    defaultGroup = ((Comparable)clone(defaultGroup));
    
    groups = ((List)clone(groups));
    keyToGroupMap = ((Map)clone(keyToGroupMap));
    return result;
  }
  






  private static Object clone(Object object)
  {
    if (object == null) {
      return null;
    }
    Class c = object.getClass();
    Object result = null;
    try {
      Method m = c.getMethod("clone", (Class[])null);
      if (Modifier.isPublic(m.getModifiers())) {
        try {
          result = m.invoke(object, (Object[])null);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    catch (NoSuchMethodException e) {
      result = object;
    }
    return result;
  }
  








  private static Collection clone(Collection list)
    throws CloneNotSupportedException
  {
    Collection result = null;
    if (list != null) {
      try {
        List clone = (List)list.getClass().newInstance();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
          clone.add(clone(iterator.next()));
        }
        result = clone;
      }
      catch (Exception e) {
        throw new CloneNotSupportedException("Exception.");
      }
    }
    return result;
  }
}
