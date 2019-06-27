package org.jfree.ui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

































































public class L1R3ButtonPanel
  extends JPanel
{
  private JButton left;
  private JButton right1;
  private JButton right2;
  private JButton right3;
  
  public L1R3ButtonPanel(String label1, String label2, String label3, String label4)
  {
    setLayout(new BorderLayout());
    

    JPanel panel = new JPanel(new BorderLayout());
    JPanel panel2 = new JPanel(new BorderLayout());
    left = new JButton(label1);
    right1 = new JButton(label2);
    right2 = new JButton(label3);
    right3 = new JButton(label4);
    

    panel.add(left, "West");
    panel2.add(right1, "East");
    panel.add(panel2, "Center");
    panel.add(right2, "East");
    add(panel, "Center");
    add(right3, "East");
  }
  





  public JButton getLeftButton()
  {
    return left;
  }
  




  public JButton getRightButton1()
  {
    return right1;
  }
  




  public JButton getRightButton2()
  {
    return right2;
  }
  




  public JButton getRightButton3()
  {
    return right3;
  }
}
