package org.jfree.ui.tabbedui;

import javax.swing.JComponent;
























































public abstract class DetailEditor
  extends JComponent
{
  private Object object;
  private boolean confirmed;
  
  public DetailEditor() {}
  
  public void update()
  {
    if (object == null) {
      throw new IllegalStateException();
    }
    
    updateObject(object);
    
    setConfirmed(false);
  }
  




  public Object getObject()
  {
    return object;
  }
  




  public void setObject(Object object)
  {
    if (object == null) {
      throw new NullPointerException();
    }
    this.object = object;
    setConfirmed(false);
    fillObject();
  }
  







  protected static int parseInt(String text, int def)
  {
    try
    {
      return Integer.parseInt(text);
    }
    catch (NumberFormatException fe) {}
    return def;
  }
  





  public abstract void clear();
  




  protected abstract void fillObject();
  




  protected abstract void updateObject(Object paramObject);
  




  public boolean isConfirmed()
  {
    return confirmed;
  }
  




  protected void setConfirmed(boolean confirmed)
  {
    boolean oldConfirmed = this.confirmed;
    this.confirmed = confirmed;
    firePropertyChange("confirmed", oldConfirmed, confirmed);
  }
}
