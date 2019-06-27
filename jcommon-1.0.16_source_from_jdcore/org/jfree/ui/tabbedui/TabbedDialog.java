package org.jfree.ui.tabbedui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JDialog;
import javax.swing.JPanel;

























































public class TabbedDialog
  extends JDialog
{
  private AbstractTabbedUI tabbedUI;
  public TabbedDialog() {}
  
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
  









  public TabbedDialog(Dialog owner)
  {
    super(owner);
  }
  





  public TabbedDialog(Dialog owner, boolean modal)
  {
    super(owner, modal);
  }
  





  public TabbedDialog(Dialog owner, String title)
  {
    super(owner, title);
  }
  






  public TabbedDialog(Dialog owner, String title, boolean modal)
  {
    super(owner, title, modal);
  }
  




  public TabbedDialog(Frame owner)
  {
    super(owner);
  }
  





  public TabbedDialog(Frame owner, boolean modal)
  {
    super(owner, modal);
  }
  





  public TabbedDialog(Frame owner, String title)
  {
    super(owner, title);
  }
  






  public TabbedDialog(Frame owner, String title, boolean modal)
  {
    super(owner, title, modal);
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
