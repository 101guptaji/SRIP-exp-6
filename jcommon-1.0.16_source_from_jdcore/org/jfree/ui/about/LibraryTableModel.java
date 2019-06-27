package org.jfree.ui.about;

import java.util.List;
import java.util.ResourceBundle;
import javax.swing.table.AbstractTableModel;
import org.jfree.base.Library;
import org.jfree.util.ResourceBundleWrapper;






































































public class LibraryTableModel
  extends AbstractTableModel
{
  private Library[] libraries;
  private String nameColumnLabel;
  private String versionColumnLabel;
  private String licenceColumnLabel;
  private String infoColumnLabel;
  
  public LibraryTableModel(List libraries)
  {
    this.libraries = ((Library[])libraries.toArray(new Library[libraries.size()]));
    

    String baseName = "org.jfree.ui.about.resources.AboutResources";
    ResourceBundle resources = ResourceBundleWrapper.getBundle("org.jfree.ui.about.resources.AboutResources");
    

    nameColumnLabel = resources.getString("libraries-table.column.name");
    
    versionColumnLabel = resources.getString("libraries-table.column.version");
    
    licenceColumnLabel = resources.getString("libraries-table.column.licence");
    
    infoColumnLabel = resources.getString("libraries-table.column.info");
  }
  






  public int getRowCount()
  {
    return libraries.length;
  }
  





  public int getColumnCount()
  {
    return 4;
  }
  







  public String getColumnName(int column)
  {
    String result = null;
    
    switch (column) {
    case 0: 
      result = nameColumnLabel;
      break;
    case 1: 
      result = versionColumnLabel;
      break;
    case 2: 
      result = licenceColumnLabel;
      break;
    case 3: 
      result = infoColumnLabel;
    }
    
    

    return result;
  }
  









  public Object getValueAt(int row, int column)
  {
    Object result = null;
    Library library = libraries[row];
    
    if (column == 0) {
      result = library.getName();
    }
    else if (column == 1) {
      result = library.getVersion();
    }
    else if (column == 2) {
      result = library.getLicenceName();
    }
    else if (column == 3) {
      result = library.getInfo();
    }
    return result;
  }
  





  public Library[] getLibraries()
  {
    return (Library[])libraries.clone();
  }
}
