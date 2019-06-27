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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.layout.LCBLayout;
import org.jfree.ui.FontChooserPanel;
import org.jfree.ui.FontDisplayField;
import org.jfree.ui.PaintSample;
import org.jfree.ui.RectangleInsets;



























































































class DefaultAxisEditor
  extends JPanel
  implements ActionListener
{
  private JTextField label;
  private Font labelFont;
  private PaintSample labelPaintSample;
  private JTextField labelFontField;
  private Font tickLabelFont;
  private JTextField tickLabelFontField;
  private PaintSample tickLabelPaintSample;
  private JPanel slot1;
  private JPanel slot2;
  private JCheckBox showTickLabelsCheckBox;
  private JCheckBox showTickMarksCheckBox;
  private RectangleInsets tickLabelInsets;
  private RectangleInsets labelInsets;
  private JTabbedPane otherTabs;
  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.editor.LocalizationBundle");
  











  public static DefaultAxisEditor getInstance(Axis axis)
  {
    if (axis != null)
    {

      if ((axis instanceof NumberAxis)) {
        return new DefaultNumberAxisEditor((NumberAxis)axis);
      }
      
      return new DefaultAxisEditor(axis);
    }
    

    return null;
  }
  









  public DefaultAxisEditor(Axis axis)
  {
    labelFont = axis.getLabelFont();
    labelPaintSample = new PaintSample(axis.getLabelPaint());
    tickLabelFont = axis.getTickLabelFont();
    tickLabelPaintSample = new PaintSample(axis.getTickLabelPaint());
    

    tickLabelInsets = axis.getTickLabelInsets();
    labelInsets = axis.getLabelInsets();
    
    setLayout(new BorderLayout());
    
    JPanel general = new JPanel(new BorderLayout());
    general.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), localizationResources.getString("General")));
    





    JPanel interior = new JPanel(new LCBLayout(5));
    interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    interior.add(new JLabel(localizationResources.getString("Label")));
    label = new JTextField(axis.getLabel());
    interior.add(label);
    interior.add(new JPanel());
    
    interior.add(new JLabel(localizationResources.getString("Font")));
    labelFontField = new FontDisplayField(labelFont);
    interior.add(labelFontField);
    JButton b = new JButton(localizationResources.getString("Select..."));
    b.setActionCommand("SelectLabelFont");
    b.addActionListener(this);
    interior.add(b);
    
    interior.add(new JLabel(localizationResources.getString("Paint")));
    interior.add(labelPaintSample);
    b = new JButton(localizationResources.getString("Select..."));
    b.setActionCommand("SelectLabelPaint");
    b.addActionListener(this);
    interior.add(b);
    





















    general.add(interior);
    
    add(general, "North");
    
    slot1 = new JPanel(new BorderLayout());
    
    JPanel other = new JPanel(new BorderLayout());
    other.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), localizationResources.getString("Other")));
    


    otherTabs = new JTabbedPane();
    otherTabs.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    
    JPanel ticks = new JPanel(new LCBLayout(3));
    ticks.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    
    showTickLabelsCheckBox = new JCheckBox(localizationResources.getString("Show_tick_labels"), axis.isTickLabelsVisible());
    


    ticks.add(showTickLabelsCheckBox);
    ticks.add(new JPanel());
    ticks.add(new JPanel());
    
    ticks.add(new JLabel(localizationResources.getString("Tick_label_font")));
    

    tickLabelFontField = new FontDisplayField(tickLabelFont);
    ticks.add(tickLabelFontField);
    b = new JButton(localizationResources.getString("Select..."));
    b.setActionCommand("SelectTickLabelFont");
    b.addActionListener(this);
    ticks.add(b);
    
    showTickMarksCheckBox = new JCheckBox(localizationResources.getString("Show_tick_marks"), axis.isTickMarksVisible());
    


    ticks.add(showTickMarksCheckBox);
    ticks.add(new JPanel());
    ticks.add(new JPanel());
    
    otherTabs.add(localizationResources.getString("Ticks"), ticks);
    
    other.add(otherTabs);
    
    slot1.add(other);
    
    slot2 = new JPanel(new BorderLayout());
    slot2.add(slot1, "North");
    add(slot2);
  }
  





  public String getLabel()
  {
    return label.getText();
  }
  




  public Font getLabelFont()
  {
    return labelFont;
  }
  




  public Paint getLabelPaint()
  {
    return labelPaintSample.getPaint();
  }
  




  public boolean isTickLabelsVisible()
  {
    return showTickLabelsCheckBox.isSelected();
  }
  




  public Font getTickLabelFont()
  {
    return tickLabelFont;
  }
  




  public Paint getTickLabelPaint()
  {
    return tickLabelPaintSample.getPaint();
  }
  





  public boolean isTickMarksVisible()
  {
    return showTickMarksCheckBox.isSelected();
  }
  




  public RectangleInsets getTickLabelInsets()
  {
    return tickLabelInsets == null ? new RectangleInsets(0.0D, 0.0D, 0.0D, 0.0D) : tickLabelInsets;
  }
  






  public RectangleInsets getLabelInsets()
  {
    return labelInsets == null ? new RectangleInsets(0.0D, 0.0D, 0.0D, 0.0D) : labelInsets;
  }
  





  public JTabbedPane getOtherTabs()
  {
    return otherTabs;
  }
  





  public void actionPerformed(ActionEvent event)
  {
    String command = event.getActionCommand();
    if (command.equals("SelectLabelFont")) {
      attemptLabelFontSelection();
    }
    else if (command.equals("SelectLabelPaint")) {
      attemptModifyLabelPaint();
    }
    else if (command.equals("SelectTickLabelFont")) {
      attemptTickLabelFontSelection();
    }
  }
  









  private void attemptLabelFontSelection()
  {
    FontChooserPanel panel = new FontChooserPanel(labelFont);
    int result = JOptionPane.showConfirmDialog(this, panel, localizationResources.getString("Font_Selection"), 2, -1);
    


    if (result == 0) {
      labelFont = panel.getSelectedFont();
      labelFontField.setText(labelFont.getFontName() + " " + labelFont.getSize());
    }
  }
  






  private void attemptModifyLabelPaint()
  {
    Color c = JColorChooser.showDialog(this, localizationResources.getString("Label_Color"), Color.blue);
    

    if (c != null) {
      labelPaintSample.setPaint(c);
    }
  }
  



  public void attemptTickLabelFontSelection()
  {
    FontChooserPanel panel = new FontChooserPanel(tickLabelFont);
    int result = JOptionPane.showConfirmDialog(this, panel, localizationResources.getString("Font_Selection"), 2, -1);
    


    if (result == 0) {
      tickLabelFont = panel.getSelectedFont();
      tickLabelFontField.setText(tickLabelFont.getFontName() + " " + tickLabelFont.getSize());
    }
  }
  














































  public void setAxisProperties(Axis axis)
  {
    axis.setLabel(getLabel());
    axis.setLabelFont(getLabelFont());
    axis.setLabelPaint(getLabelPaint());
    axis.setTickMarksVisible(isTickMarksVisible());
    
    axis.setTickLabelsVisible(isTickLabelsVisible());
    axis.setTickLabelFont(getTickLabelFont());
    axis.setTickLabelPaint(getTickLabelPaint());
    axis.setTickLabelInsets(getTickLabelInsets());
    axis.setLabelInsets(getLabelInsets());
  }
}
