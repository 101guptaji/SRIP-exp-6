package org.jfree.ui;

import java.awt.Insets;
import java.util.ResourceBundle;
import javax.swing.JTextField;
import org.jfree.util.ResourceBundleWrapper;






















































public class InsetsTextField
  extends JTextField
{
  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.ui.LocalizationBundle");
  








  public InsetsTextField(Insets insets)
  {
    setInsets(insets);
    setEnabled(false);
  }
  






  public String formatInsetsString(Insets insets)
  {
    insets = insets == null ? new Insets(0, 0, 0, 0) : insets;
    return localizationResources.getString("T") + top + ", " + localizationResources.getString("L") + left + ", " + localizationResources.getString("B") + bottom + ", " + localizationResources.getString("R") + right;
  }
  










  public void setInsets(Insets insets)
  {
    setText(formatInsetsString(insets));
  }
}
