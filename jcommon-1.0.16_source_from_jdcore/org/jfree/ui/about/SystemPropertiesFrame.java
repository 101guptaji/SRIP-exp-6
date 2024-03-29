package org.jfree.ui.about;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import org.jfree.util.ResourceBundleWrapper;










































































public class SystemPropertiesFrame
  extends JFrame
  implements ActionListener
{
  private static final String COPY_COMMAND = "COPY";
  private static final String CLOSE_COMMAND = "CLOSE";
  private SystemPropertiesPanel panel;
  
  public SystemPropertiesFrame(boolean menu)
  {
    String baseName = "org.jfree.ui.about.resources.AboutResources";
    ResourceBundle resources = ResourceBundleWrapper.getBundle("org.jfree.ui.about.resources.AboutResources");
    

    String title = resources.getString("system-frame.title");
    setTitle(title);
    
    setDefaultCloseOperation(2);
    
    if (menu) {
      setJMenuBar(createMenuBar(resources));
    }
    
    JPanel content = new JPanel(new BorderLayout());
    panel = new SystemPropertiesPanel();
    panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    content.add(panel, "Center");
    
    JPanel buttonPanel = new JPanel(new BorderLayout());
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
    
    String label = resources.getString("system-frame.button.close");
    Character mnemonic = (Character)resources.getObject("system-frame.button.close.mnemonic");
    
    JButton closeButton = new JButton(label);
    closeButton.setMnemonic(mnemonic.charValue());
    
    closeButton.setActionCommand("CLOSE");
    closeButton.addActionListener(this);
    
    buttonPanel.add(closeButton, "East");
    content.add(buttonPanel, "South");
    
    setContentPane(content);
  }
  






  public void actionPerformed(ActionEvent e)
  {
    String command = e.getActionCommand();
    if (command.equals("CLOSE")) {
      dispose();
    }
    else if (command.equals("COPY")) {
      panel.copySystemPropertiesToClipboard();
    }
  }
  









  private JMenuBar createMenuBar(ResourceBundle resources)
  {
    JMenuBar menuBar = new JMenuBar();
    
    String label = resources.getString("system-frame.menu.file");
    Character mnemonic = (Character)resources.getObject("system-frame.menu.file.mnemonic");
    
    JMenu fileMenu = new JMenu(label, true);
    fileMenu.setMnemonic(mnemonic.charValue());
    
    label = resources.getString("system-frame.menu.file.close");
    mnemonic = (Character)resources.getObject("system-frame.menu.file.close.mnemonic");
    
    JMenuItem closeItem = new JMenuItem(label, mnemonic.charValue());
    closeItem.setActionCommand("CLOSE");
    
    closeItem.addActionListener(this);
    fileMenu.add(closeItem);
    
    label = resources.getString("system-frame.menu.edit");
    mnemonic = (Character)resources.getObject("system-frame.menu.edit.mnemonic");
    
    JMenu editMenu = new JMenu(label);
    editMenu.setMnemonic(mnemonic.charValue());
    
    label = resources.getString("system-frame.menu.edit.copy");
    mnemonic = (Character)resources.getObject("system-frame.menu.edit.copy.mnemonic");
    
    JMenuItem copyItem = new JMenuItem(label, mnemonic.charValue());
    copyItem.setActionCommand("COPY");
    copyItem.addActionListener(this);
    editMenu.add(copyItem);
    
    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    return menuBar;
  }
}
