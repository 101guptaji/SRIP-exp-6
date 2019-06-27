package org.jfree.chart.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.layout.LCBLayout;
import org.jfree.ui.FontChooserPanel;
import org.jfree.ui.FontDisplayField;
import org.jfree.ui.PaintSample;
































































class DefaultTitleEditor
  extends JPanel
  implements ActionListener
{
  private boolean showTitle;
  private JCheckBox showTitleCheckBox;
  private JTextField titleField;
  private Font titleFont;
  private JTextField fontfield;
  private JButton selectFontButton;
  private PaintSample titlePaint;
  private JButton selectPaintButton;
  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.editor.LocalizationBundle");
  








  public DefaultTitleEditor(Title title)
  {
    TextTitle t = title != null ? (TextTitle)title : new TextTitle(localizationResources.getString("Title"));
    
    showTitle = (title != null);
    titleFont = t.getFont();
    titleField = new JTextField(t.getText());
    titlePaint = new PaintSample(t.getPaint());
    
    setLayout(new BorderLayout());
    
    JPanel general = new JPanel(new BorderLayout());
    general.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), localizationResources.getString("General")));
    





    JPanel interior = new JPanel(new LCBLayout(4));
    interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    
    interior.add(new JLabel(localizationResources.getString("Show_Title")));
    showTitleCheckBox = new JCheckBox();
    showTitleCheckBox.setSelected(showTitle);
    showTitleCheckBox.setActionCommand("ShowTitle");
    showTitleCheckBox.addActionListener(this);
    interior.add(new JPanel());
    interior.add(showTitleCheckBox);
    
    JLabel titleLabel = new JLabel(localizationResources.getString("Text"));
    interior.add(titleLabel);
    interior.add(titleField);
    interior.add(new JPanel());
    
    JLabel fontLabel = new JLabel(localizationResources.getString("Font"));
    fontfield = new FontDisplayField(titleFont);
    selectFontButton = new JButton(localizationResources.getString("Select..."));
    

    selectFontButton.setActionCommand("SelectFont");
    selectFontButton.addActionListener(this);
    interior.add(fontLabel);
    interior.add(fontfield);
    interior.add(selectFontButton);
    
    JLabel colorLabel = new JLabel(localizationResources.getString("Color"));
    

    selectPaintButton = new JButton(localizationResources.getString("Select..."));
    

    selectPaintButton.setActionCommand("SelectPaint");
    selectPaintButton.addActionListener(this);
    interior.add(colorLabel);
    interior.add(titlePaint);
    interior.add(selectPaintButton);
    
    enableOrDisableControls();
    
    general.add(interior);
    add(general, "North");
  }
  




  public String getTitleText()
  {
    return titleField.getText();
  }
  




  public Font getTitleFont()
  {
    return titleFont;
  }
  




  public Paint getTitlePaint()
  {
    return titlePaint.getPaint();
  }
  






  public void actionPerformed(ActionEvent event)
  {
    String command = event.getActionCommand();
    
    if (command.equals("SelectFont")) {
      attemptFontSelection();
    }
    else if (command.equals("SelectPaint")) {
      attemptPaintSelection();
    }
    else if (command.equals("ShowTitle")) {
      attemptModifyShowTitle();
    }
  }
  



  public void attemptFontSelection()
  {
    FontChooserPanel panel = new FontChooserPanel(titleFont);
    int result = JOptionPane.showConfirmDialog(this, panel, localizationResources.getString("Font_Selection"), 2, -1);
    




    if (result == 0) {
      titleFont = panel.getSelectedFont();
      fontfield.setText(titleFont.getFontName() + " " + titleFont.getSize());
    }
  }
  







  public void attemptPaintSelection()
  {
    Paint p = titlePaint.getPaint();
    Color defaultColor = (p instanceof Color) ? (Color)p : Color.blue;
    Color c = JColorChooser.showDialog(this, localizationResources.getString("Title_Color"), defaultColor);
    

    if (c != null) {
      titlePaint.setPaint(c);
    }
  }
  



  private void attemptModifyShowTitle()
  {
    showTitle = showTitleCheckBox.isSelected();
    enableOrDisableControls();
  }
  



  private void enableOrDisableControls()
  {
    boolean enabled = showTitle == true;
    titleField.setEnabled(enabled);
    selectFontButton.setEnabled(enabled);
    selectPaintButton.setEnabled(enabled);
  }
  





  public void setTitleProperties(JFreeChart chart)
  {
    if (showTitle) {
      TextTitle title = chart.getTitle();
      if (title == null) {
        title = new TextTitle();
        chart.setTitle(title);
      }
      title.setText(getTitleText());
      title.setFont(getTitleFont());
      title.setPaint(getTitlePaint());
    }
    else {
      chart.setTitle((TextTitle)null);
    }
  }
}
