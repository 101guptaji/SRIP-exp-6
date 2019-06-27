package org.jfree.ui.tabbedui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.util.Log;

















































public abstract class AbstractTabbedUI
  extends JComponent
{
  public static final String JMENUBAR_PROPERTY = "jMenuBar";
  public static final String GLOBAL_MENU_PROPERTY = "globalMenu";
  private ArrayList rootEditors;
  private JTabbedPane tabbedPane;
  private int selectedRootEditor;
  private JComponent currentToolbar;
  private JPanel toolbarContainer;
  private Action closeAction;
  private JMenuBar jMenuBar;
  private boolean globalMenu;
  
  protected class ExitAction
    extends AbstractAction
  {
    public ExitAction()
    {
      putValue("Name", "Exit");
    }
    




    public void actionPerformed(ActionEvent e)
    {
      attempExit();
    }
  }
  




  private class TabChangeHandler
    implements ChangeListener
  {
    private final JTabbedPane pane;
    




    public TabChangeHandler(JTabbedPane pane)
    {
      this.pane = pane;
    }
    




    public void stateChanged(ChangeEvent e)
    {
      setSelectedEditor(pane.getSelectedIndex());
    }
  }
  






  private class TabEnableChangeListener
    implements PropertyChangeListener
  {
    public TabEnableChangeListener() {}
    





    public void propertyChange(PropertyChangeEvent evt)
    {
      if (!evt.getPropertyName().equals("enabled")) {
        Log.debug("PropertyName");
        return;
      }
      if (!(evt.getSource() instanceof RootEditor)) {
        Log.debug("Source");
        return;
      }
      RootEditor editor = (RootEditor)evt.getSource();
      updateRootEditorEnabled(editor);
    }
  }
  



















  public AbstractTabbedUI()
  {
    selectedRootEditor = -1;
    
    toolbarContainer = new JPanel();
    toolbarContainer.setLayout(new BorderLayout());
    
    tabbedPane = new JTabbedPane(3);
    tabbedPane.addChangeListener(new TabChangeHandler(tabbedPane));
    
    rootEditors = new ArrayList();
    
    setLayout(new BorderLayout());
    add(toolbarContainer, "North");
    add(tabbedPane, "Center");
    
    closeAction = createCloseAction();
  }
  




  protected JTabbedPane getTabbedPane()
  {
    return tabbedPane;
  }
  









  public boolean isGlobalMenu()
  {
    return globalMenu;
  }
  




  public void setGlobalMenu(boolean globalMenu)
  {
    this.globalMenu = globalMenu;
    if (isGlobalMenu()) {
      setJMenuBar(updateGlobalMenubar());

    }
    else if (getRootEditorCount() > 0) {
      setJMenuBar(createEditorMenubar(getRootEditor(getSelectedEditor())));
    }
  }
  





  public JMenuBar getJMenuBar()
  {
    return jMenuBar;
  }
  




  protected void setJMenuBar(JMenuBar menuBar)
  {
    JMenuBar oldMenuBar = jMenuBar;
    jMenuBar = menuBar;
    firePropertyChange("jMenuBar", oldMenuBar, menuBar);
  }
  




  protected Action createCloseAction()
  {
    return new ExitAction();
  }
  




  public Action getCloseAction()
  {
    return closeAction;
  }
  





  protected abstract JMenu[] getPrefixMenus();
  





  protected abstract JMenu[] getPostfixMenus();
  





  private void addMenus(JMenuBar menuBar, JMenu[] customMenus)
  {
    for (int i = 0; i < customMenus.length; i++) {
      menuBar.add(customMenus[i]);
    }
  }
  



  private JMenuBar updateGlobalMenubar()
  {
    JMenuBar menuBar = getJMenuBar();
    if (menuBar == null) {
      menuBar = new JMenuBar();
    }
    else {
      menuBar.removeAll();
    }
    
    addMenus(menuBar, getPrefixMenus());
    for (int i = 0; i < rootEditors.size(); i++)
    {
      RootEditor editor = (RootEditor)rootEditors.get(i);
      addMenus(menuBar, editor.getMenus());
    }
    addMenus(menuBar, getPostfixMenus());
    return menuBar;
  }
  






  private JMenuBar createEditorMenubar(RootEditor root)
  {
    JMenuBar menuBar = getJMenuBar();
    if (menuBar == null) {
      menuBar = new JMenuBar();
    }
    else {
      menuBar.removeAll();
    }
    
    addMenus(menuBar, getPrefixMenus());
    if (isGlobalMenu())
    {
      for (int i = 0; i < rootEditors.size(); i++)
      {
        RootEditor editor = (RootEditor)rootEditors.get(i);
        addMenus(menuBar, editor.getMenus());
      }
      
    }
    else {
      addMenus(menuBar, root.getMenus());
    }
    addMenus(menuBar, getPostfixMenus());
    return menuBar;
  }
  




  public void addRootEditor(RootEditor rootPanel)
  {
    rootEditors.add(rootPanel);
    tabbedPane.add(rootPanel.getEditorName(), rootPanel.getMainPanel());
    rootPanel.addPropertyChangeListener("enabled", new TabEnableChangeListener());
    updateRootEditorEnabled(rootPanel);
    if (getRootEditorCount() == 1) {
      setSelectedEditor(0);
    }
    else if (isGlobalMenu()) {
      setJMenuBar(updateGlobalMenubar());
    }
  }
  




  public int getRootEditorCount()
  {
    return rootEditors.size();
  }
  






  public RootEditor getRootEditor(int pos)
  {
    return (RootEditor)rootEditors.get(pos);
  }
  




  public int getSelectedEditor()
  {
    return selectedRootEditor;
  }
  




  public void setSelectedEditor(int selectedEditor)
  {
    int oldEditor = selectedRootEditor;
    if (oldEditor == selectedEditor)
    {
      return;
    }
    selectedRootEditor = selectedEditor;
    



    for (int i = 0; i < rootEditors.size(); i++) {
      boolean shouldBeActive = i == selectedEditor;
      RootEditor container = (RootEditor)rootEditors.get(i);
      
      if ((container.isActive()) && (!shouldBeActive)) {
        container.setActive(false);
      }
    }
    
    if (currentToolbar != null) {
      closeToolbar();
      toolbarContainer.removeAll();
      currentToolbar = null;
    }
    
    for (int i = 0; i < rootEditors.size(); i++) {
      boolean shouldBeActive = i == selectedEditor;
      RootEditor container = (RootEditor)rootEditors.get(i);
      
      if ((!container.isActive()) && (shouldBeActive == true)) {
        container.setActive(true);
        setJMenuBar(createEditorMenubar(container));
        currentToolbar = container.getToolbar();
        if (currentToolbar != null) {
          toolbarContainer.add(currentToolbar, "Center");
          
          toolbarContainer.setVisible(true);
          currentToolbar.setVisible(true);
        }
        else {
          toolbarContainer.setVisible(false);
        }
        
        getJMenuBar().repaint();
      }
    }
  }
  


  private void closeToolbar()
  {
    if (currentToolbar != null) {
      if (currentToolbar.getParent() != toolbarContainer)
      {

        Window w = SwingUtilities.windowForComponent(currentToolbar);
        if (w != null) {
          w.setVisible(false);
          w.dispose();
        }
      }
      currentToolbar.setVisible(false);
    }
  }
  




  protected abstract void attempExit();
  




  protected void updateRootEditorEnabled(RootEditor editor)
  {
    boolean enabled = editor.isEnabled();
    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
      Component tab = tabbedPane.getComponentAt(i);
      if (tab == editor.getMainPanel()) {
        tabbedPane.setEnabledAt(i, enabled);
        return;
      }
    }
  }
}
