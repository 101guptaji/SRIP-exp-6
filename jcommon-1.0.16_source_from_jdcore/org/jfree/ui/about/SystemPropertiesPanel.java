package org.jfree.ui.about;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;
import javax.accessibility.AccessibleContext;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import org.jfree.util.ResourceBundleWrapper;


































































public class SystemPropertiesPanel
  extends JPanel
{
  private JTable table;
  private JPopupMenu copyPopupMenu;
  private JMenuItem copyMenuItem;
  private PopupListener copyPopupListener;
  
  public SystemPropertiesPanel()
  {
    String baseName = "org.jfree.ui.about.resources.AboutResources";
    ResourceBundle resources = ResourceBundleWrapper.getBundle("org.jfree.ui.about.resources.AboutResources");
    

    setLayout(new BorderLayout());
    table = SystemProperties.createSystemPropertiesTable();
    add(new JScrollPane(table));
    

    copyPopupMenu = new JPopupMenu();
    
    String label = resources.getString("system-properties-panel.popup-menu.copy");
    
    KeyStroke accelerator = (KeyStroke)resources.getObject("system-properties-panel.popup-menu.copy.accelerator");
    
    copyMenuItem = new JMenuItem(label);
    copyMenuItem.setAccelerator(accelerator);
    copyMenuItem.getAccessibleContext().setAccessibleDescription(label);
    
    copyMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        copySystemPropertiesToClipboard();
      }
    });
    copyPopupMenu.add(copyMenuItem);
    

    copyPopupListener = new PopupListener();
    table.addMouseListener(copyPopupListener);
  }
  





  public void copySystemPropertiesToClipboard()
  {
    StringBuffer buffer = new StringBuffer();
    ListSelectionModel selection = table.getSelectionModel();
    int firstRow = selection.getMinSelectionIndex();
    int lastRow = selection.getMaxSelectionIndex();
    if ((firstRow != -1) && (lastRow != -1)) {
      for (int r = firstRow; r <= lastRow; r++) {
        for (int c = 0; c < table.getColumnCount(); c++) {
          buffer.append(table.getValueAt(r, c));
          if (c != 2) {
            buffer.append("\t");
          }
        }
        buffer.append("\n");
      }
    }
    StringSelection ss = new StringSelection(buffer.toString());
    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
    cb.setContents(ss, ss);
  }
  







  protected final JPopupMenu getCopyPopupMenu()
  {
    return copyPopupMenu;
  }
  




  protected final JTable getTable()
  {
    return table;
  }
  





  private class PopupListener
    extends MouseAdapter
  {
    public PopupListener() {}
    





    public void mousePressed(MouseEvent e)
    {
      maybeShowPopup(e);
    }
    




    public void mouseReleased(MouseEvent e)
    {
      maybeShowPopup(e);
    }
    




    private void maybeShowPopup(MouseEvent e)
    {
      if (e.isPopupTrigger()) {
        getCopyPopupMenu().show(getTable(), e.getX(), e.getY());
      }
    }
  }
}
