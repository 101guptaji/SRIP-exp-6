package junit.swingui;

import java.util.Vector;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreePath;
import junit.framework.Test;

class TestSuitePanel extends javax.swing.JPanel implements junit.framework.TestListener
{
  private JTree fTree;
  private javax.swing.JScrollPane fScrollTree;
  private TestTreeModel fModel;
  
  static class TestTreeCellRenderer extends javax.swing.tree.DefaultTreeCellRenderer
  {
    private Icon fErrorIcon;
    private Icon fOkIcon;
    private Icon fFailureIcon;
    
    TestTreeCellRenderer()
    {
      loadIcons();
    }
    
    void loadIcons() {
      fErrorIcon = TestRunner.getIconResource(getClass(), "icons/error.gif");
      fOkIcon = TestRunner.getIconResource(getClass(), "icons/ok.gif");
      fFailureIcon = TestRunner.getIconResource(getClass(), "icons/failure.gif");
    }
    
    String stripParenthesis(Object o) {
      String text = o.toString();
      int pos = text.indexOf('(');
      if (pos < 1)
        return text;
      return text.substring(0, pos);
    }
    

    public java.awt.Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
      java.awt.Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
      javax.swing.tree.TreeModel model = tree.getModel();
      if ((model instanceof TestTreeModel)) {
        TestTreeModel testModel = (TestTreeModel)model;
        Test t = (Test)value;
        String s = "";
        if (testModel.isFailure(t)) {
          if (fFailureIcon != null)
            setIcon(fFailureIcon);
          s = " - Failed";
        }
        else if (testModel.isError(t)) {
          if (fErrorIcon != null)
            setIcon(fErrorIcon);
          s = " - Error";
        }
        else if (testModel.wasRun(t)) {
          if (fOkIcon != null)
            setIcon(fOkIcon);
          s = " - Passed";
        }
        if ((c instanceof JComponent))
          ((JComponent)c).setToolTipText(getText() + s);
      }
      setText(stripParenthesis(value));
      return c;
    }
  }
  
  public TestSuitePanel() {
    super(new java.awt.BorderLayout());
    setPreferredSize(new java.awt.Dimension(300, 100));
    fTree = new JTree();
    fTree.setModel(null);
    fTree.setRowHeight(20);
    javax.swing.ToolTipManager.sharedInstance().registerComponent(fTree);
    fTree.putClientProperty("JTree.lineStyle", "Angled");
    fScrollTree = new javax.swing.JScrollPane(fTree);
    add(fScrollTree, "Center");
  }
  
  public void addError(Test test, Throwable t) {
    fModel.addError(test);
    fireTestChanged(test, true);
  }
  
  public void addFailure(Test test, junit.framework.AssertionFailedError t) {
    fModel.addFailure(test);
    fireTestChanged(test, true);
  }
  


  public void endTest(Test test)
  {
    fModel.addRunTest(test);
    fireTestChanged(test, false);
  }
  



  public void startTest(Test test) {}
  



  public Test getSelectedTest()
  {
    TreePath[] paths = fTree.getSelectionPaths();
    if ((paths != null) && (paths.length == 1))
      return (Test)paths[0].getLastPathComponent();
    return null;
  }
  


  public JTree getTree()
  {
    return fTree;
  }
  


  public void showTestTree(Test root)
  {
    fModel = new TestTreeModel(root);
    fTree.setModel(fModel);
    fTree.setCellRenderer(new TestTreeCellRenderer());
  }
  
  private void fireTestChanged(Test test, boolean expand) {
    javax.swing.SwingUtilities.invokeLater(
      new Runnable() { private final Test val$test;
        
        public void run() { Vector vpath = new Vector();
          int index = fModel.findTest(val$test, (Test)fModel.getRoot(), vpath);
          if (index >= 0) {
            Object[] path = new Object[vpath.size()];
            vpath.copyInto(path);
            TreePath treePath = new TreePath(path);
            fModel.fireNodeChanged(treePath, index);
            if (val$expand) {
              Object[] fullPath = new Object[vpath.size() + 1];
              vpath.copyInto(fullPath);
              fullPath[vpath.size()] = fModel.getChild(treePath.getLastPathComponent(), index);
              TreePath fullTreePath = new TreePath(fullPath);
              fTree.scrollPathToVisible(fullTreePath);
            }
          }
        }
      });
  }
  
  private final boolean val$expand;
}
