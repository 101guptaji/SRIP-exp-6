package org.jfree.ui.tabbedui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JApplet;
import javax.swing.JPanel;






















































public class TabbedApplet
  extends JApplet
{
  private AbstractTabbedUI tabbedUI;
  public TabbedApplet() {}
  
  private class MenuBarChangeListener
    implements PropertyChangeListener
  {
    public MenuBarChangeListener() {}
    
    public void propertyChange(PropertyChangeEvent evt)
    {
      if (evt.getPropertyName().equals("jMenuBar")) {
        setJMenuBar(getTabbedUI().getJMenuBar());
      }
    }
  }
  















  protected final AbstractTabbedUI getTabbedUI()
  {
    return tabbedUI;
  }
  




  public void init(AbstractTabbedUI tabbedUI)
  {
    this.tabbedUI = tabbedUI;
    this.tabbedUI.addPropertyChangeListener("jMenuBar", new MenuBarChangeListener());
    

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(tabbedUI, "Center");
    setContentPane(panel);
    setJMenuBar(tabbedUI.getJMenuBar());
  }
}
