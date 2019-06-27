package org.jfree.ui;

import javax.swing.table.AbstractTableModel;






















































public abstract class SortableTableModel
  extends AbstractTableModel
{
  private int sortingColumn;
  private boolean ascending;
  
  public SortableTableModel()
  {
    sortingColumn = -1;
    ascending = true;
  }
  





  public int getSortingColumn()
  {
    return sortingColumn;
  }
  






  public boolean isAscending()
  {
    return ascending;
  }
  





  public void setAscending(boolean flag)
  {
    ascending = flag;
  }
  





  public void sortByColumn(int column, boolean ascending)
  {
    if (isSortable(column)) {
      sortingColumn = column;
    }
  }
  






  public boolean isSortable(int column)
  {
    return false;
  }
}
