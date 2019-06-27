package junit.swingui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import junit.framework.Test;
import junit.framework.TestFailure;
import junit.framework.TestResult;

public class FailureRunView implements TestRunView
{
  JList fFailureList;
  TestRunContext fRunContext;
  
  static class FailureListCellRenderer extends DefaultListCellRenderer
  {
    private Icon fFailureIcon;
    private Icon fErrorIcon;
    
    FailureListCellRenderer()
    {
      loadIcons();
    }
    
    void loadIcons() {
      fFailureIcon = TestRunner.getIconResource(getClass(), "icons/failure.gif");
      fErrorIcon = TestRunner.getIconResource(getClass(), "icons/error.gif");
    }
    


    public Component getListCellRendererComponent(JList list, Object value, int modelIndex, boolean isSelected, boolean cellHasFocus)
    {
      Component c = super.getListCellRendererComponent(list, value, modelIndex, isSelected, cellHasFocus);
      TestFailure failure = (TestFailure)value;
      String text = failure.failedTest().toString();
      String msg = failure.exceptionMessage();
      if (msg != null) {
        text = text + ":" + junit.runner.BaseTestRunner.truncate(msg);
      }
      if (failure.isFailure()) {
        if (fFailureIcon != null) {
          setIcon(fFailureIcon);
        }
      } else if (fErrorIcon != null) {
        setIcon(fErrorIcon);
      }
      setText(text);
      setToolTipText(text);
      return c;
    }
  }
  
  public FailureRunView(TestRunContext context) {
    fRunContext = context;
    fFailureList = new JList(fRunContext.getFailures());
    fFailureList.setFont(new java.awt.Font("Dialog", 0, 12));
    
    fFailureList.setSelectionMode(0);
    fFailureList.setCellRenderer(new FailureListCellRenderer());
    fFailureList.setVisibleRowCount(5);
    
    fFailureList.addListSelectionListener(
      new javax.swing.event.ListSelectionListener() {
        public void valueChanged(javax.swing.event.ListSelectionEvent e) {
          testSelected();
        }
      });
  }
  
  public Test getSelectedTest()
  {
    int index = fFailureList.getSelectedIndex();
    if (index == -1) {
      return null;
    }
    ListModel model = fFailureList.getModel();
    TestFailure failure = (TestFailure)model.getElementAt(index);
    return failure.failedTest();
  }
  
  public void activate() {
    testSelected();
  }
  
  public void addTab(JTabbedPane pane) {
    javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(fFailureList, 22, 32);
    Icon errorIcon = TestRunner.getIconResource(getClass(), "icons/error.gif");
    pane.addTab("Failures", errorIcon, scrollPane, "The list of failed tests");
  }
  
  public void revealFailure(Test failure) {
    fFailureList.setSelectedIndex(0);
  }
  

  public void aboutToStart(Test suite, TestResult result) {}
  
  public void runFinished(Test suite, TestResult result) {}
  
  protected void testSelected()
  {
    fRunContext.handleTestSelected(getSelectedTest());
  }
}
