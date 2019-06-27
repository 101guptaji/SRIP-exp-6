package org.jfree.ui.tabbedui;

import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import javax.swing.JMenu;

public abstract interface RootEditor
{
  public abstract void setActive(boolean paramBoolean);
  
  public abstract boolean isActive();
  
  public abstract String getEditorName();
  
  public abstract JMenu[] getMenus();
  
  public abstract JComponent getToolbar();
  
  public abstract JComponent getMainPanel();
  
  public abstract boolean isEnabled();
  
  public abstract void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener);
  
  public abstract void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener);
  
  public abstract void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
  
  public abstract void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener);
}
