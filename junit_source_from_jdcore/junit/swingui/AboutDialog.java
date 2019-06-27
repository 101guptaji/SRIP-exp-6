package junit.swingui;

import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JLabel;

class AboutDialog extends JDialog
{
  public AboutDialog(javax.swing.JFrame parent)
  {
    super(parent, true);
    
    setResizable(false);
    getContentPane().setLayout(new java.awt.GridBagLayout());
    setSize(330, 138);
    setTitle("About");
    try
    {
      setLocationRelativeTo(parent);
    } catch (NoSuchMethodError e) {
      TestSelector.centerWindow(this);
    }
    
    javax.swing.JButton close = new javax.swing.JButton("Close");
    close.addActionListener(
      new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          dispose();
        }
        
      });
    getRootPane().setDefaultButton(close);
    JLabel label1 = new JLabel("JUnit");
    label1.setFont(new java.awt.Font("dialog", 0, 36));
    
    JLabel label2 = new JLabel("JUnit " + junit.runner.Version.id() + " by Kent Beck and Erich Gamma");
    label2.setFont(new java.awt.Font("dialog", 0, 14));
    
    JLabel logo = createLogo();
    
    GridBagConstraints constraintsLabel1 = new GridBagConstraints();
    gridx = 3;gridy = 0;
    gridwidth = 1;gridheight = 1;
    anchor = 10;
    getContentPane().add(label1, constraintsLabel1);
    
    GridBagConstraints constraintsLabel2 = new GridBagConstraints();
    gridx = 2;gridy = 1;
    gridwidth = 2;gridheight = 1;
    anchor = 10;
    getContentPane().add(label2, constraintsLabel2);
    
    GridBagConstraints constraintsButton1 = new GridBagConstraints();
    gridx = 2;gridy = 2;
    gridwidth = 2;gridheight = 1;
    anchor = 10;
    insets = new java.awt.Insets(8, 0, 8, 0);
    getContentPane().add(close, constraintsButton1);
    
    GridBagConstraints constraintsLogo1 = new GridBagConstraints();
    gridx = 2;gridy = 0;
    gridwidth = 1;gridheight = 1;
    anchor = 10;
    getContentPane().add(logo, constraintsLogo1);
    
    addWindowListener(
      new java.awt.event.WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          dispose();
        }
      });
  }
  
  protected JLabel createLogo() {
    javax.swing.Icon icon = TestRunner.getIconResource(junit.runner.BaseTestRunner.class, "logo.gif");
    return new JLabel(icon);
  }
}
