package org.jfree.ui.tabbedui;

import javax.swing.JComponent;
























































public abstract class RootPanel
  extends JComponent
  implements RootEditor
{
  private boolean active;
  
  public RootPanel() {}
  
  public final boolean isActive()
  {
    return active;
  }
  





  protected void panelActivated() {}
  





  protected void panelDeactivated() {}
  




  public final void setActive(boolean active)
  {
    if (this.active == active) {
      return;
    }
    this.active = active;
    if (active) {
      panelActivated();
    }
    else {
      panelDeactivated();
    }
  }
  





  public JComponent getMainPanel()
  {
    return this;
  }
  





  public JComponent getToolbar()
  {
    return null;
  }
}
