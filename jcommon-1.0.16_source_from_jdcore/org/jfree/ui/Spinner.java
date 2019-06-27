package org.jfree.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JTextField;




























































public class Spinner
  extends JPanel
  implements MouseListener
{
  private int value;
  private JTextField textField;
  private JPanel buttonPanel;
  private ArrowPanel upButton;
  private ArrowPanel downButton;
  
  public Spinner(int value)
  {
    super(new BorderLayout());
    this.value = value;
    textField = new JTextField(Integer.toString(this.value));
    textField.setHorizontalAlignment(4);
    add(textField);
    buttonPanel = new JPanel(new GridLayout(2, 1, 0, 1));
    upButton = new ArrowPanel(0);
    upButton.addMouseListener(this);
    downButton = new ArrowPanel(1);
    downButton.addMouseListener(this);
    buttonPanel.add(upButton);
    buttonPanel.add(downButton);
    add(buttonPanel, "East");
  }
  




  public int getValue()
  {
    return value;
  }
  




  public void mouseClicked(MouseEvent e)
  {
    if (e.getSource() == upButton) {
      value += 1;
      textField.setText(Integer.toString(value));
      firePropertyChange("value", value - 1, value);
    }
    else if (e.getSource() == downButton) {
      value -= 1;
      textField.setText(Integer.toString(value));
      firePropertyChange("value", value + 1, value);
    }
  }
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e) {}
  
  public void mouseReleased(MouseEvent e) {}
}
