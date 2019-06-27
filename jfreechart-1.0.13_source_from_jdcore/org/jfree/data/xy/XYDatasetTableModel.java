package org.jfree.data.xy;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;


























































public class XYDatasetTableModel
  extends AbstractTableModel
  implements TableModel, DatasetChangeListener
{
  TableXYDataset model = null;
  





  public XYDatasetTableModel() {}
  




  public XYDatasetTableModel(TableXYDataset dataset)
  {
    this();
    
    model.addChangeListener(this);
  }
  




  public void setModel(TableXYDataset dataset)
  {
    model = dataset;
    model.addChangeListener(this);
    fireTableDataChanged();
  }
  




  public int getRowCount()
  {
    if (model == null) {
      return 0;
    }
    return model.getItemCount();
  }
  




  public int getColumnCount()
  {
    if (model == null) {
      return 0;
    }
    return model.getSeriesCount() + 1;
  }
  






  public String getColumnName(int column)
  {
    if (model == null) {
      return super.getColumnName(column);
    }
    if (column < 1) {
      return "X Value";
    }
    
    return model.getSeriesKey(column - 1).toString();
  }
  









  public Object getValueAt(int row, int column)
  {
    if (model == null) {
      return null;
    }
    if (column < 1) {
      return model.getX(0, row);
    }
    
    return model.getY(column - 1, row);
  }
  







  public void datasetChanged(DatasetChangeEvent event)
  {
    fireTableDataChanged();
  }
  







  public boolean isCellEditable(int row, int column)
  {
    return false;
  }
  






  public void setValueAt(Object value, int row, int column)
  {
    if (isCellEditable(row, column)) {}
  }
}
