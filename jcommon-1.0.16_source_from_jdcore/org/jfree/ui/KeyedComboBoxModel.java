package org.jfree.ui;

import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;































































public class KeyedComboBoxModel
  implements ComboBoxModel
{
  private int selectedItemIndex;
  private Object selectedItemValue;
  private ArrayList data;
  private ArrayList listdatalistener;
  private transient ListDataListener[] tempListeners;
  private boolean allowOtherValue;
  
  private static class ComboBoxItemPair
  {
    private Object key;
    private Object value;
    
    public ComboBoxItemPair(Object key, Object value)
    {
      this.key = key;
      this.value = value;
    }
    





    public Object getKey()
    {
      return key;
    }
    





    public Object getValue()
    {
      return value;
    }
    





    public void setValue(Object value)
    {
      this.value = value;
    }
  }
  






















  public KeyedComboBoxModel()
  {
    data = new ArrayList();
    listdatalistener = new ArrayList();
  }
  







  public KeyedComboBoxModel(Object[] keys, Object[] values)
  {
    this();
    setData(keys, values);
  }
  







  public void setData(Object[] keys, Object[] values)
  {
    if (values.length != keys.length)
    {
      throw new IllegalArgumentException("Values and text must have the same length.");
    }
    
    data.clear();
    data.ensureCapacity(keys.length);
    
    for (int i = 0; i < values.length; i++)
    {
      add(keys[i], values[i]);
    }
    
    selectedItemIndex = -1;
    ListDataEvent evt = new ListDataEvent(this, 0, 0, data.size() - 1);
    
    fireListDataEvent(evt);
  }
  





  protected synchronized void fireListDataEvent(ListDataEvent evt)
  {
    if (tempListeners == null)
    {
      tempListeners = ((ListDataListener[])listdatalistener.toArray(new ListDataListener[listdatalistener.size()]));
    }
    

    ListDataListener[] listeners = tempListeners;
    for (int i = 0; i < listeners.length; i++)
    {
      ListDataListener l = listeners[i];
      l.contentsChanged(evt);
    }
  }
  





  public Object getSelectedItem()
  {
    return selectedItemValue;
  }
  






  public void setSelectedKey(Object anItem)
  {
    if (anItem == null)
    {
      selectedItemIndex = -1;
      selectedItemValue = null;
    }
    else
    {
      int newSelectedItem = findDataElementIndex(anItem);
      if (newSelectedItem == -1)
      {
        selectedItemIndex = -1;
        selectedItemValue = null;
      }
      else
      {
        selectedItemIndex = newSelectedItem;
        selectedItemValue = getElementAt(selectedItemIndex);
      }
    }
    fireListDataEvent(new ListDataEvent(this, 0, -1, -1));
  }
  








  public void setSelectedItem(Object anItem)
  {
    if (anItem == null)
    {
      selectedItemIndex = -1;
      selectedItemValue = null;
    }
    else
    {
      int newSelectedItem = findElementIndex(anItem);
      if (newSelectedItem == -1)
      {
        if (isAllowOtherValue())
        {
          selectedItemIndex = -1;
          selectedItemValue = anItem;
        }
        else
        {
          selectedItemIndex = -1;
          selectedItemValue = null;
        }
      }
      else
      {
        selectedItemIndex = newSelectedItem;
        selectedItemValue = getElementAt(selectedItemIndex);
      }
    }
    fireListDataEvent(new ListDataEvent(this, 0, -1, -1));
  }
  
  private boolean isAllowOtherValue()
  {
    return allowOtherValue;
  }
  



  public void setAllowOtherValue(boolean allowOtherValue)
  {
    this.allowOtherValue = allowOtherValue;
  }
  






  public synchronized void addListDataListener(ListDataListener l)
  {
    if (l == null)
    {
      throw new NullPointerException();
    }
    listdatalistener.add(l);
    tempListeners = null;
  }
  






  public Object getElementAt(int index)
  {
    if (index >= data.size())
    {
      return null;
    }
    
    ComboBoxItemPair datacon = (ComboBoxItemPair)data.get(index);
    if (datacon == null)
    {
      return null;
    }
    return datacon.getValue();
  }
  






  public Object getKeyAt(int index)
  {
    if (index >= data.size())
    {
      return null;
    }
    
    if (index < 0)
    {
      return null;
    }
    
    ComboBoxItemPair datacon = (ComboBoxItemPair)data.get(index);
    if (datacon == null)
    {
      return null;
    }
    return datacon.getKey();
  }
  





  public Object getSelectedKey()
  {
    return getKeyAt(selectedItemIndex);
  }
  





  public int getSize()
  {
    return data.size();
  }
  






  public void removeListDataListener(ListDataListener l)
  {
    listdatalistener.remove(l);
    tempListeners = null;
  }
  







  private int findDataElementIndex(Object anItem)
  {
    if (anItem == null)
    {
      throw new NullPointerException("Item to find must not be null");
    }
    
    for (int i = 0; i < data.size(); i++)
    {
      ComboBoxItemPair datacon = (ComboBoxItemPair)data.get(i);
      if (anItem.equals(datacon.getKey()))
      {
        return i;
      }
    }
    return -1;
  }
  







  public int findElementIndex(Object key)
  {
    if (key == null)
    {
      throw new NullPointerException("Item to find must not be null");
    }
    
    for (int i = 0; i < data.size(); i++)
    {
      ComboBoxItemPair datacon = (ComboBoxItemPair)data.get(i);
      if (key.equals(datacon.getValue()))
      {
        return i;
      }
    }
    return -1;
  }
  





  public void removeDataElement(Object key)
  {
    int idx = findDataElementIndex(key);
    if (idx == -1)
    {
      return;
    }
    
    data.remove(idx);
    ListDataEvent evt = new ListDataEvent(this, 2, idx, idx);
    
    fireListDataEvent(evt);
  }
  






  public void add(Object key, Object cbitem)
  {
    ComboBoxItemPair con = new ComboBoxItemPair(key, cbitem);
    data.add(con);
    ListDataEvent evt = new ListDataEvent(this, 1, data.size() - 2, data.size() - 2);
    
    fireListDataEvent(evt);
  }
  



  public void clear()
  {
    int size = getSize();
    data.clear();
    ListDataEvent evt = new ListDataEvent(this, 2, 0, size - 1);
    fireListDataEvent(evt);
  }
}
