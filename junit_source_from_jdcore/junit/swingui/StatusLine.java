package junit.swingui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class StatusLine extends JTextField
{
  public static final Font PLAIN_FONT = new Font("dialog", 0, 12);
  public static final Font BOLD_FONT = new Font("dialog", 1, 12);
  
  public StatusLine(int preferredWidth)
  {
    setFont(BOLD_FONT);
    setEditable(false);
    setBorder(javax.swing.BorderFactory.createBevelBorder(1));
    Dimension d = getPreferredSize();
    width = preferredWidth;
    setPreferredSize(d);
  }
  
  public void showInfo(String message) {
    setFont(PLAIN_FONT);
    setForeground(Color.black);
    setText(message);
  }
  
  public void showError(String status) {
    setFont(BOLD_FONT);
    setForeground(Color.red);
    setText(status);
    setToolTipText(status);
  }
  
  public void clear() {
    setText("");
    setToolTipText(null);
  }
}
