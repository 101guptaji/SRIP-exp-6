package org.jfree.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import org.jfree.util.ResourceBundleWrapper;
























































public class FontChooserPanel
  extends JPanel
{
  public static final String[] SIZES = { "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "28", "36", "48", "72" };
  


  private JList fontlist;
  

  private JList sizelist;
  

  private JCheckBox bold;
  

  private JCheckBox italic;
  

  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.ui.LocalizationBundle");
  







  public FontChooserPanel(Font font)
  {
    GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
    
    String[] fonts = g.getAvailableFontFamilyNames();
    
    setLayout(new BorderLayout());
    JPanel right = new JPanel(new BorderLayout());
    
    JPanel fontPanel = new JPanel(new BorderLayout());
    fontPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), localizationResources.getString("Font")));
    

    fontlist = new JList(fonts);
    JScrollPane fontpane = new JScrollPane(fontlist);
    fontpane.setBorder(BorderFactory.createEtchedBorder());
    fontPanel.add(fontpane);
    add(fontPanel);
    
    JPanel sizePanel = new JPanel(new BorderLayout());
    sizePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), localizationResources.getString("Size")));
    

    sizelist = new JList(SIZES);
    JScrollPane sizepane = new JScrollPane(sizelist);
    sizepane.setBorder(BorderFactory.createEtchedBorder());
    sizePanel.add(sizepane);
    
    JPanel attributes = new JPanel(new GridLayout(1, 2));
    bold = new JCheckBox(localizationResources.getString("Bold"));
    italic = new JCheckBox(localizationResources.getString("Italic"));
    attributes.add(bold);
    attributes.add(italic);
    attributes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), localizationResources.getString("Attributes")));
    


    right.add(sizePanel, "Center");
    right.add(attributes, "South");
    
    add(right, "East");
    
    setSelectedFont(font);
  }
  




  public Font getSelectedFont()
  {
    return new Font(getSelectedName(), getSelectedStyle(), getSelectedSize());
  }
  





  public String getSelectedName()
  {
    return (String)fontlist.getSelectedValue();
  }
  




  public int getSelectedStyle()
  {
    if ((bold.isSelected()) && (italic.isSelected())) {
      return 3;
    }
    if (bold.isSelected()) {
      return 1;
    }
    if (italic.isSelected()) {
      return 2;
    }
    
    return 0;
  }
  





  public int getSelectedSize()
  {
    String selected = (String)sizelist.getSelectedValue();
    if (selected != null) {
      return Integer.parseInt(selected);
    }
    
    return 10;
  }
  






  public void setSelectedFont(Font font)
  {
    if (font == null) {
      throw new NullPointerException();
    }
    bold.setSelected(font.isBold());
    italic.setSelected(font.isItalic());
    
    String fontName = font.getName();
    ListModel model = fontlist.getModel();
    fontlist.clearSelection();
    for (int i = 0; i < model.getSize(); i++) {
      if (fontName.equals(model.getElementAt(i))) {
        fontlist.setSelectedIndex(i);
        break;
      }
    }
    
    String fontSize = String.valueOf(font.getSize());
    model = sizelist.getModel();
    sizelist.clearSelection();
    for (int i = 0; i < model.getSize(); i++) {
      if (fontSize.equals(model.getElementAt(i))) {
        sizelist.setSelectedIndex(i);
        break;
      }
    }
  }
}
