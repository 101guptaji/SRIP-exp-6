package junit.awtui;

import java.awt.Dialog;
import java.awt.GridBagConstraints;

class AboutDialog extends Dialog
{
  public AboutDialog(java.awt.Frame parent)
  {
    super(parent);
    
    setResizable(false);
    setLayout(new java.awt.GridBagLayout());
    setSize(330, 138);
    setTitle("About");
    
    java.awt.Button button = new java.awt.Button("Close");
    button.addActionListener(
      new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent e) {
          dispose();
        }
        

      });
    java.awt.Label label1 = new java.awt.Label("JUnit");
    label1.setFont(new java.awt.Font("dialog", 0, 36));
    
    java.awt.Label label2 = new java.awt.Label("JUnit " + junit.runner.Version.id() + " by Kent Beck and Erich Gamma");
    label2.setFont(new java.awt.Font("dialog", 0, 14));
    
    Logo logo = new Logo();
    
    GridBagConstraints constraintsLabel1 = new GridBagConstraints();
    gridx = 3;gridy = 0;
    gridwidth = 1;gridheight = 1;
    anchor = 10;
    add(label1, constraintsLabel1);
    
    GridBagConstraints constraintsLabel2 = new GridBagConstraints();
    gridx = 2;gridy = 1;
    gridwidth = 2;gridheight = 1;
    anchor = 10;
    add(label2, constraintsLabel2);
    
    GridBagConstraints constraintsButton1 = new GridBagConstraints();
    gridx = 2;gridy = 2;
    gridwidth = 2;gridheight = 1;
    anchor = 10;
    insets = new java.awt.Insets(8, 0, 8, 0);
    add(button, constraintsButton1);
    
    GridBagConstraints constraintsLogo1 = new GridBagConstraints();
    gridx = 2;gridy = 0;
    gridwidth = 1;gridheight = 1;
    anchor = 10;
    add(logo, constraintsLogo1);
    
    addWindowListener(
      new java.awt.event.WindowAdapter() {
        public void windowClosing(java.awt.event.WindowEvent e) {
          dispose();
        }
      });
  }
}
