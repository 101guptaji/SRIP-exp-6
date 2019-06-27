package org.jfree.chart.plot.dial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import javax.swing.event.EventListenerList;
import org.jfree.chart.HashUtilities;























































public abstract class AbstractDialLayer
  implements DialLayer
{
  private boolean visible;
  private transient EventListenerList listenerList;
  
  protected AbstractDialLayer()
  {
    visible = true;
    listenerList = new EventListenerList();
  }
  







  public boolean isVisible()
  {
    return visible;
  }
  








  public void setVisible(boolean visible)
  {
    this.visible = visible;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AbstractDialLayer)) {
      return false;
    }
    AbstractDialLayer that = (AbstractDialLayer)obj;
    return visible == visible;
  }
  




  public int hashCode()
  {
    int result = 23;
    result = HashUtilities.hashCode(result, visible);
    return result;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    AbstractDialLayer clone = (AbstractDialLayer)super.clone();
    
    listenerList = new EventListenerList();
    return clone;
  }
  






  public void addChangeListener(DialLayerChangeListener listener)
  {
    listenerList.add(DialLayerChangeListener.class, listener);
  }
  






  public void removeChangeListener(DialLayerChangeListener listener)
  {
    listenerList.remove(DialLayerChangeListener.class, listener);
  }
  








  public boolean hasListener(EventListener listener)
  {
    List list = Arrays.asList(listenerList.getListenerList());
    return list.contains(listener);
  }
  





  protected void notifyListeners(DialLayerChangeEvent event)
  {
    Object[] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == DialLayerChangeListener.class) {
        ((DialLayerChangeListener)listeners[(i + 1)]).dialLayerChanged(event);
      }
    }
  }
  








  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    listenerList = new EventListenerList();
  }
}
