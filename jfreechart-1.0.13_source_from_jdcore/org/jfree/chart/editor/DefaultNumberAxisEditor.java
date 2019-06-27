package org.jfree.chart.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
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
import org.jfree.ui.PaintSample;
import org.jfree.ui.StrokeChooserPanel;
import org.jfree.ui.StrokeSample;









































































class DefaultNumberAxisEditor
  extends DefaultAxisEditor
  implements FocusListener
{
  private boolean autoRange;
  private double minimumValue;
  private double maximumValue;
  private JCheckBox autoRangeCheckBox;
  private JTextField minimumRangeValue;
  private JTextField maximumRangeValue;
  private PaintSample gridPaintSample;
  private StrokeSample gridStrokeSample;
  private StrokeSample[] availableStrokeSamples;
  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.editor.LocalizationBundle");
  







  public DefaultNumberAxisEditor(NumberAxis axis)
  {
    super(axis);
    
    autoRange = axis.isAutoRange();
    minimumValue = axis.getLowerBound();
    maximumValue = axis.getUpperBound();
    
    gridPaintSample = new PaintSample(Color.blue);
    gridStrokeSample = new StrokeSample(new BasicStroke(1.0F));
    
    availableStrokeSamples = new StrokeSample[3];
    availableStrokeSamples[0] = new StrokeSample(new BasicStroke(1.0F));
    
    availableStrokeSamples[1] = new StrokeSample(new BasicStroke(2.0F));
    
    availableStrokeSamples[2] = new StrokeSample(new BasicStroke(3.0F));
    

    JTabbedPane other = getOtherTabs();
    
    JPanel range = new JPanel(new LCBLayout(3));
    range.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    
    range.add(new JPanel());
    autoRangeCheckBox = new JCheckBox(localizationResources.getString("Auto-adjust_range"), autoRange);
    
    autoRangeCheckBox.setActionCommand("AutoRangeOnOff");
    autoRangeCheckBox.addActionListener(this);
    range.add(autoRangeCheckBox);
    range.add(new JPanel());
    
    range.add(new JLabel(localizationResources.getString("Minimum_range_value")));
    
    minimumRangeValue = new JTextField(Double.toString(minimumValue));
    
    minimumRangeValue.setEnabled(!autoRange);
    minimumRangeValue.setActionCommand("MinimumRange");
    minimumRangeValue.addActionListener(this);
    minimumRangeValue.addFocusListener(this);
    range.add(minimumRangeValue);
    range.add(new JPanel());
    
    range.add(new JLabel(localizationResources.getString("Maximum_range_value")));
    
    maximumRangeValue = new JTextField(Double.toString(maximumValue));
    
    maximumRangeValue.setEnabled(!autoRange);
    maximumRangeValue.setActionCommand("MaximumRange");
    maximumRangeValue.addActionListener(this);
    maximumRangeValue.addFocusListener(this);
    range.add(maximumRangeValue);
    range.add(new JPanel());
    
    other.add(localizationResources.getString("Range"), range);
  }
  





  public boolean isAutoRange()
  {
    return autoRange;
  }
  




  public double getMinimumValue()
  {
    return minimumValue;
  }
  




  public double getMaximumValue()
  {
    return maximumValue;
  }
  



  public void actionPerformed(ActionEvent event)
  {
    String command = event.getActionCommand();
    if (command.equals("GridStroke")) {
      attemptGridStrokeSelection();
    }
    else if (command.equals("GridPaint")) {
      attemptGridPaintSelection();
    }
    else if (command.equals("AutoRangeOnOff")) {
      toggleAutoRange();
    }
    else if (command.equals("MinimumRange")) {
      validateMinimum();
    }
    else if (command.equals("MaximumRange")) {
      validateMaximum();
    }
    else
    {
      super.actionPerformed(event);
    }
  }
  


  private void attemptGridStrokeSelection()
  {
    StrokeChooserPanel panel = new StrokeChooserPanel(gridStrokeSample, availableStrokeSamples);
    
    int result = JOptionPane.showConfirmDialog(this, panel, localizationResources.getString("Stroke_Selection"), 2, -1);
    


    if (result == 0) {
      gridStrokeSample.setStroke(panel.getSelectedStroke());
    }
  }
  



  private void attemptGridPaintSelection()
  {
    Color c = JColorChooser.showDialog(this, localizationResources.getString("Grid_Color"), Color.blue);
    
    if (c != null) {
      gridPaintSample.setPaint(c);
    }
  }
  






  public void focusGained(FocusEvent event) {}
  





  public void focusLost(FocusEvent event)
  {
    if (event.getSource() == minimumRangeValue) {
      validateMinimum();
    }
    else if (event.getSource() == maximumRangeValue) {
      validateMaximum();
    }
  }
  


  public void toggleAutoRange()
  {
    autoRange = autoRangeCheckBox.isSelected();
    if (autoRange) {
      minimumRangeValue.setText(Double.toString(minimumValue));
      minimumRangeValue.setEnabled(false);
      maximumRangeValue.setText(Double.toString(maximumValue));
      maximumRangeValue.setEnabled(false);
    }
    else {
      minimumRangeValue.setEnabled(true);
      maximumRangeValue.setEnabled(true);
    }
  }
  

  public void validateMinimum()
  {
    double newMin;
    try
    {
      newMin = Double.parseDouble(minimumRangeValue.getText());
      if (newMin >= maximumValue) {
        newMin = minimumValue;
      }
    }
    catch (NumberFormatException e) {
      newMin = minimumValue;
    }
    
    minimumValue = newMin;
    minimumRangeValue.setText(Double.toString(minimumValue));
  }
  

  public void validateMaximum()
  {
    double newMax;
    try
    {
      newMax = Double.parseDouble(maximumRangeValue.getText());
      if (newMax <= minimumValue) {
        newMax = maximumValue;
      }
    }
    catch (NumberFormatException e) {
      newMax = maximumValue;
    }
    
    maximumValue = newMax;
    maximumRangeValue.setText(Double.toString(maximumValue));
  }
  





  public void setAxisProperties(Axis axis)
  {
    super.setAxisProperties(axis);
    NumberAxis numberAxis = (NumberAxis)axis;
    numberAxis.setAutoRange(autoRange);
    if (!autoRange) {
      numberAxis.setRange(minimumValue, maximumValue);
    }
  }
}
