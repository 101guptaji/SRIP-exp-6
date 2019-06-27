package junit.swingui;

import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class CounterPanel extends javax.swing.JPanel
{
  private JTextField fNumberOfErrors;
  private JTextField fNumberOfFailures;
  private JTextField fNumberOfRuns;
  private javax.swing.Icon fFailureIcon = TestRunner.getIconResource(getClass(), "icons/failure.gif");
  private javax.swing.Icon fErrorIcon = TestRunner.getIconResource(getClass(), "icons/error.gif");
  private int fTotal;
  
  public CounterPanel()
  {
    super(new java.awt.GridBagLayout());
    fNumberOfErrors = createOutputField(5);
    fNumberOfFailures = createOutputField(5);
    fNumberOfRuns = createOutputField(9);
    
    addToGrid(new JLabel("Runs:", 0), 
      0, 0, 1, 1, 0.0D, 0.0D, 
      10, 0, 
      new Insets(0, 0, 0, 0));
    addToGrid(fNumberOfRuns, 
      1, 0, 1, 1, 0.33D, 0.0D, 
      10, 2, 
      new Insets(0, 8, 0, 0));
    
    addToGrid(new JLabel("Errors:", fErrorIcon, 2), 
      2, 0, 1, 1, 0.0D, 0.0D, 
      10, 0, 
      new Insets(0, 8, 0, 0));
    addToGrid(fNumberOfErrors, 
      3, 0, 1, 1, 0.33D, 0.0D, 
      10, 2, 
      new Insets(0, 8, 0, 0));
    
    addToGrid(new JLabel("Failures:", fFailureIcon, 2), 
      4, 0, 1, 1, 0.0D, 0.0D, 
      10, 0, 
      new Insets(0, 8, 0, 0));
    addToGrid(fNumberOfFailures, 
      5, 0, 1, 1, 0.33D, 0.0D, 
      10, 2, 
      new Insets(0, 8, 0, 0));
  }
  
  private JTextField createOutputField(int width) {
    JTextField field = new JTextField("0", width);
    
    field.setMinimumSize(field.getPreferredSize());
    field.setMaximumSize(field.getPreferredSize());
    field.setHorizontalAlignment(2);
    field.setFont(StatusLine.BOLD_FONT);
    field.setEditable(false);
    field.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    return field;
  }
  




  public void addToGrid(java.awt.Component comp, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill, Insets insets)
  {
    java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
    gridx = gridx;
    gridy = gridy;
    gridwidth = gridwidth;
    gridheight = gridheight;
    weightx = weightx;
    weighty = weighty;
    anchor = anchor;
    fill = fill;
    insets = insets;
    add(comp, constraints);
  }
  
  public void reset() {
    setLabelValue(fNumberOfErrors, 0);
    setLabelValue(fNumberOfFailures, 0);
    setLabelValue(fNumberOfRuns, 0);
    fTotal = 0;
  }
  
  public void setTotal(int value) {
    fTotal = value;
  }
  
  public void setRunValue(int value) {
    fNumberOfRuns.setText(Integer.toString(value) + "/" + fTotal);
  }
  
  public void setErrorValue(int value) {
    setLabelValue(fNumberOfErrors, value);
  }
  
  public void setFailureValue(int value) {
    setLabelValue(fNumberOfFailures, value);
  }
  
  private void setLabelValue(JTextField label, int value) {
    label.setText(Integer.toString(value));
  }
}
