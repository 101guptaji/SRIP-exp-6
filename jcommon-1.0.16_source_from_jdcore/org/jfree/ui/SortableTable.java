package org.jfree.ui;

import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;





















































public class SortableTable
  extends JTable
{
  private SortableTableHeaderListener headerListener;
  
  public SortableTable(SortableTableModel model)
  {
    super(model);
    
    SortButtonRenderer renderer = new SortButtonRenderer();
    TableColumnModel cm = getColumnModel();
    for (int i = 0; i < cm.getColumnCount(); i++) {
      cm.getColumn(i).setHeaderRenderer(renderer);
    }
    
    JTableHeader header = getTableHeader();
    headerListener = new SortableTableHeaderListener(model, renderer);
    header.addMouseListener(headerListener);
    header.addMouseMotionListener(headerListener);
    
    model.sortByColumn(0, true);
  }
  








  public void setSortableModel(SortableTableModel model)
  {
    super.setModel(model);
    headerListener.setTableModel(model);
    SortButtonRenderer renderer = new SortButtonRenderer();
    TableColumnModel cm = getColumnModel();
    for (int i = 0; i < cm.getColumnCount(); i++) {
      cm.getColumn(i).setHeaderRenderer(renderer);
    }
    model.sortByColumn(0, true);
  }
}
