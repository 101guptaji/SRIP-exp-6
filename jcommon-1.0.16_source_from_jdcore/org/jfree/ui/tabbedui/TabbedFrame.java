package org.jfree.ui.tabbedui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;

























































public class TabbedFrame
  extends JFrame
{
  private AbstractTabbedUI tabbedUI;
  public TabbedFrame() {}
  
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
  










  public TabbedFrame(String title)
  {
    super(title);
  }
  





  protected final AbstractTabbedUI getTabbedUI()
  {
    return tabbedUI;
  }
  





  public void init(AbstractTabbedUI tabbedUI)
  {
    this.tabbedUI = tabbedUI;
    this.tabbedUI.addPropertyChangeListener("jMenuBar", new MenuBarChangeListener());
    


    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        getTabbedUI().getCloseAction().actionPerformed(new ActionEvent(this, 1001, null, 0));
      }
      

    });
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.add(tabbedUI, "Center");
    setContentPane(panel);
    setJMenuBar(tabbedUI.getJMenuBar());
  }
}
