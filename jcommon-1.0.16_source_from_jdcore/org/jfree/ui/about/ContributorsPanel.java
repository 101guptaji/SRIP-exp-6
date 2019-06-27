package org.jfree.ui.about;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;




























































public class ContributorsPanel
  extends JPanel
{
  private JTable table;
  private TableModel model;
  
  public ContributorsPanel(List contributors)
  {
    setLayout(new BorderLayout());
    model = new ContributorsTableModel(contributors);
    table = new JTable(model);
    add(new JScrollPane(table));
  }
}
