package org.jfree.ui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;



























































public class L1R1ButtonPanel
  extends JPanel
{
  private JButton left;
  private JButton right;
  
  public L1R1ButtonPanel(String leftLabel, String rightLabel)
  {
    setLayout(new BorderLayout());
    left = new JButton(leftLabel);
    right = new JButton(rightLabel);
    add(left, "West");
    add(right, "East");
  }
  





  public JButton getLeftButton()
  {
    return left;
  }
  




  public JButton getRightButton()
  {
    return right;
  }
}
