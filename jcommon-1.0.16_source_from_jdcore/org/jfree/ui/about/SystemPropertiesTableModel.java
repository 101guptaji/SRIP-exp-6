package org.jfree.ui.about;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import org.jfree.ui.SortableTableModel;
import org.jfree.util.ResourceBundleWrapper;




























































public class SystemPropertiesTableModel
  extends SortableTableModel
{
  private List properties;
  private String nameColumnLabel;
  private String valueColumnLabel;
  
  protected static class SystemProperty
  {
    private String name;
    private String value;
    
    public SystemProperty(String name, String value)
    {
      this.name = name;
      this.value = value;
    }
    




    public String getName()
    {
      return name;
    }
    




    public String getValue()
    {
      return value;
    }
  }
  





  protected static class SystemPropertyComparator
    implements Comparator
  {
    private boolean ascending;
    





    public SystemPropertyComparator(boolean ascending)
    {
      this.ascending = ascending;
    }
    








    public int compare(Object o1, Object o2)
    {
      if (((o1 instanceof SystemPropertiesTableModel.SystemProperty)) && ((o2 instanceof SystemPropertiesTableModel.SystemProperty)))
      {
        SystemPropertiesTableModel.SystemProperty sp1 = (SystemPropertiesTableModel.SystemProperty)o1;
        SystemPropertiesTableModel.SystemProperty sp2 = (SystemPropertiesTableModel.SystemProperty)o2;
        if (ascending) {
          return sp1.getName().compareTo(sp2.getName());
        }
        
        return sp2.getName().compareTo(sp1.getName());
      }
      

      return 0;
    }
    









    public boolean equals(Object o)
    {
      if (this == o) {
        return true;
      }
      if (!(o instanceof SystemPropertyComparator)) {
        return false;
      }
      
      SystemPropertyComparator systemPropertyComparator = (SystemPropertyComparator)o;
      

      if (ascending != ascending) {
        return false;
      }
      
      return true;
    }
    




    public int hashCode()
    {
      return ascending ? 1 : 0;
    }
  }
  













  public SystemPropertiesTableModel()
  {
    properties = new ArrayList();
    try {
      Properties p = System.getProperties();
      Iterator iterator = p.keySet().iterator();
      while (iterator.hasNext()) {
        String name = (String)iterator.next();
        String value = System.getProperty(name);
        SystemProperty sp = new SystemProperty(name, value);
        properties.add(sp);
      }
    }
    catch (SecurityException se) {}
    


    Collections.sort(properties, new SystemPropertyComparator(true));
    
    String baseName = "org.jfree.ui.about.resources.AboutResources";
    ResourceBundle resources = ResourceBundleWrapper.getBundle("org.jfree.ui.about.resources.AboutResources");
    

    nameColumnLabel = resources.getString("system-properties-table.column.name");
    
    valueColumnLabel = resources.getString("system-properties-table.column.value");
  }
  










  public boolean isSortable(int column)
  {
    if (column == 0) {
      return true;
    }
    
    return false;
  }
  







  public int getRowCount()
  {
    return properties.size();
  }
  






  public int getColumnCount()
  {
    return 2;
  }
  







  public String getColumnName(int column)
  {
    if (column == 0) {
      return nameColumnLabel;
    }
    
    return valueColumnLabel;
  }
  











  public Object getValueAt(int row, int column)
  {
    SystemProperty sp = (SystemProperty)properties.get(row);
    if (column == 0) {
      return sp.getName();
    }
    
    if (column == 1) {
      return sp.getValue();
    }
    
    return null;
  }
  










  public void sortByColumn(int column, boolean ascending)
  {
    if (isSortable(column)) {
      super.sortByColumn(column, ascending);
      Collections.sort(properties, new SystemPropertyComparator(ascending));
    }
  }
}
