package org.jfree.ui.about;

import java.util.List;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;
import org.jfree.util.ResourceBundleWrapper;


































































public class ContributorsTableModel
  extends AbstractTableModel
{
  private List contributors;
  private String nameColumnLabel;
  private String contactColumnLabel;
  
  public ContributorsTableModel(List contributors)
  {
    this.contributors = contributors;
    
    String baseName = "org.jfree.ui.about.resources.AboutResources";
    ResourceBundle resources = ResourceBundleWrapper.getBundle("org.jfree.ui.about.resources.AboutResources");
    
    nameColumnLabel = resources.getString("contributors-table.column.name");
    
    contactColumnLabel = resources.getString("contributors-table.column.contact");
  }
  






  public int getRowCount()
  {
    return contributors.size();
  }
  





  public int getColumnCount()
  {
    return 2;
  }
  







  public String getColumnName(int column)
  {
    String result = null;
    
    switch (column) {
    case 0: 
      result = nameColumnLabel;
      break;
    case 1: 
      result = contactColumnLabel;
    }
    
    

    return result;
  }
  









  public Object getValueAt(int row, int column)
  {
    Object result = null;
    Contributor contributor = (Contributor)contributors.get(row);
    

    if (column == 0) {
      result = contributor.getName();
    }
    else if (column == 1) {
      result = contributor.getEmail();
    }
    return result;
  }
}
