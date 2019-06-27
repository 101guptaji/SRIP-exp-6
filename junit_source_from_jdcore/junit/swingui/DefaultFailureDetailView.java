package junit.swingui;

import java.awt.Component;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import junit.framework.TestFailure;

public class DefaultFailureDetailView implements junit.runner.FailureDetailView
{
  JList fList;
  public DefaultFailureDetailView() {}
  
  static class StackTraceListModel extends AbstractListModel
  {
    StackTraceListModel() {}
    
    private Vector fLines = new Vector(20);
    
    public Object getElementAt(int index) {
      return fLines.elementAt(index);
    }
    
    public int getSize() {
      return fLines.size();
    }
    
    public void setTrace(String trace) {
      scan(trace);
      fireContentsChanged(this, 0, fLines.size());
    }
    
    public void clear() {
      fLines.removeAllElements();
      fireContentsChanged(this, 0, fLines.size());
    }
    
    private void scan(String trace) {
      fLines.removeAllElements();
      StringTokenizer st = new StringTokenizer(trace, "\n\r", false);
      while (st.hasMoreTokens()) {
        fLines.add(st.nextToken());
      }
    }
  }
  
  static class StackEntryRenderer
    extends javax.swing.DefaultListCellRenderer
  {
    StackEntryRenderer() {}
    
    public Component getListCellRendererComponent(JList list, Object value, int modelIndex, boolean isSelected, boolean cellHasFocus)
    {
      String text = ((String)value).replace('\t', ' ');
      Component c = super.getListCellRendererComponent(list, text, modelIndex, isSelected, cellHasFocus);
      setText(text);
      setToolTipText(text);
      return c;
    }
  }
  


  public Component getComponent()
  {
    if (fList == null) {
      fList = new JList(new StackTraceListModel());
      fList.setFont(new java.awt.Font("Dialog", 0, 12));
      fList.setSelectionMode(0);
      fList.setVisibleRowCount(5);
      fList.setCellRenderer(new StackEntryRenderer());
    }
    return fList;
  }
  


  public void showFailure(TestFailure failure)
  {
    getModel().setTrace(junit.runner.BaseTestRunner.getFilteredTrace(failure.trace()));
  }
  

  public void clear()
  {
    getModel().clear();
  }
  
  private StackTraceListModel getModel() {
    return (StackTraceListModel)fList.getModel();
  }
}
