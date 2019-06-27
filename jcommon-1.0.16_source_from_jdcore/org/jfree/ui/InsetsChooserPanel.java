package org.jfree.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ResourceBundle;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import org.jfree.util.ResourceBundleWrapper;



































































public class InsetsChooserPanel
  extends JPanel
{
  private JTextField topValueEditor;
  private JTextField leftValueEditor;
  private JTextField bottomValueEditor;
  private JTextField rightValueEditor;
  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.ui.LocalizationBundle");
  





  public InsetsChooserPanel()
  {
    this(new Insets(0, 0, 0, 0));
  }
  





  public InsetsChooserPanel(Insets current)
  {
    current = current == null ? new Insets(0, 0, 0, 0) : current;
    
    topValueEditor = new JTextField(new IntegerDocument(), "" + top, 0);
    
    leftValueEditor = new JTextField(new IntegerDocument(), "" + left, 0);
    
    bottomValueEditor = new JTextField(new IntegerDocument(), "" + bottom, 0);
    
    rightValueEditor = new JTextField(new IntegerDocument(), "" + right, 0);
    

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(new TitledBorder(localizationResources.getString("Insets")));
    


    panel.add(new JLabel(localizationResources.getString("Top")), new GridBagConstraints(1, 0, 3, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
    




    panel.add(new JLabel(" "), new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 12, 0, 12), 8, 0));
    

    panel.add(topValueEditor, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
    

    panel.add(new JLabel(" "), new GridBagConstraints(3, 1, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 12, 0, 11), 8, 0));
    



    panel.add(new JLabel(localizationResources.getString("Left")), new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 4, 0, 4), 0, 0));
    


    panel.add(leftValueEditor, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
    

    panel.add(new JLabel(" "), new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 12, 0, 12), 8, 0));
    

    panel.add(rightValueEditor, new GridBagConstraints(3, 2, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
    

    panel.add(new JLabel(localizationResources.getString("Right")), new GridBagConstraints(4, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 4, 0, 4), 0, 0));
    




    panel.add(bottomValueEditor, new GridBagConstraints(2, 3, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
    



    panel.add(new JLabel(localizationResources.getString("Bottom")), new GridBagConstraints(1, 4, 3, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
    


    setLayout(new BorderLayout());
    add(panel, "Center");
  }
  






  public Insets getInsetsValue()
  {
    return new Insets(Math.abs(stringToInt(topValueEditor.getText())), Math.abs(stringToInt(leftValueEditor.getText())), Math.abs(stringToInt(bottomValueEditor.getText())), Math.abs(stringToInt(rightValueEditor.getText())));
  }
  












  protected int stringToInt(String value)
  {
    value = value.trim();
    if (value.length() == 0) {
      return 0;
    }
    try
    {
      return Integer.parseInt(value);
    }
    catch (NumberFormatException e) {}
    return 0;
  }
  




  public void removeNotify()
  {
    super.removeNotify();
    removeAll();
  }
}
