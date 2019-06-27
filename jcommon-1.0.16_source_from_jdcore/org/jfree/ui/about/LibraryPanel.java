package org.jfree.ui.about;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.jfree.base.Library;

























































public class LibraryPanel
  extends JPanel
{
  private JTable table;
  private LibraryTableModel model;
  
  public LibraryPanel(List libraries)
  {
    setLayout(new BorderLayout());
    model = new LibraryTableModel(libraries);
    table = new JTable(model);
    add(new JScrollPane(table));
  }
  





  public LibraryPanel(ProjectInfo projectInfo)
  {
    this(getLibraries(projectInfo));
  }
  
  private static List getLibraries(ProjectInfo info) {
    if (info == null) {
      return new ArrayList();
    }
    ArrayList libs = new ArrayList();
    collectLibraries(info, libs);
    return libs;
  }
  
  private static void collectLibraries(ProjectInfo info, List list)
  {
    Library[] libs = info.getLibraries();
    for (int i = 0; i < libs.length; i++) {
      Library lib = libs[i];
      if (!list.contains(lib))
      {
        list.add(lib);
        if ((lib instanceof ProjectInfo)) {
          collectLibraries((ProjectInfo)lib, list);
        }
      }
    }
    
    libs = info.getOptionalLibraries();
    for (int i = 0; i < libs.length; i++) {
      Library lib = libs[i];
      if (!list.contains(lib))
      {
        list.add(lib);
        if ((lib instanceof ProjectInfo)) {
          collectLibraries((ProjectInfo)lib, list);
        }
      }
    }
  }
  




  public LibraryTableModel getModel()
  {
    return model;
  }
  




  protected JTable getTable()
  {
    return table;
  }
}
