package org.jfree.ui.action;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import org.jfree.ui.ExtensionFileFilter;
import org.jfree.util.StringUtils;


























































public abstract class AbstractFileSelectionAction
  extends AbstractActionDowngrade
{
  private JFileChooser fileChooser;
  private Component parent;
  
  public AbstractFileSelectionAction(Component parent)
  {
    this.parent = parent;
  }
  





  protected abstract String getFileExtension();
  





  protected abstract String getFileDescription();
  





  protected File getCurrentDirectory()
  {
    return new File(".");
  }
  












  protected File performSelectFile(File selectedFile, int dialogType, boolean appendExtension)
  {
    if (fileChooser == null) {
      fileChooser = createFileChooser();
    }
    
    fileChooser.setSelectedFile(selectedFile);
    fileChooser.setDialogType(dialogType);
    int option = fileChooser.showDialog(parent, null);
    if (option == 0) {
      File selFile = fileChooser.getSelectedFile();
      String selFileName = selFile.getAbsolutePath();
      if (!StringUtils.endsWithIgnoreCase(selFileName, getFileExtension())) {
        selFileName = selFileName + getFileExtension();
      }
      return new File(selFileName);
    }
    return null;
  }
  




  protected JFileChooser createFileChooser()
  {
    JFileChooser fc = new JFileChooser();
    fc.addChoosableFileFilter(new ExtensionFileFilter(getFileDescription(), getFileExtension()));
    

    fc.setMultiSelectionEnabled(false);
    fc.setCurrentDirectory(getCurrentDirectory());
    return fc;
  }
}
