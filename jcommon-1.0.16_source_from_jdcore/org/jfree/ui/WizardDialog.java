package org.jfree.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;


















































































public class WizardDialog
  extends JDialog
  implements ActionListener
{
  private Object result;
  private int step;
  private WizardPanel currentPanel;
  private List panels;
  private JButton previousButton;
  private JButton nextButton;
  private JButton finishButton;
  private JButton helpButton;
  
  public WizardDialog(JDialog owner, boolean modal, String title, WizardPanel firstPanel)
  {
    super(owner, title + " : step 1", modal);
    result = null;
    currentPanel = firstPanel;
    step = 0;
    panels = new ArrayList();
    panels.add(firstPanel);
    setContentPane(createContent());
  }
  










  public WizardDialog(JFrame owner, boolean modal, String title, WizardPanel firstPanel)
  {
    super(owner, title + " : step 1", modal);
    result = null;
    currentPanel = firstPanel;
    step = 0;
    panels = new ArrayList();
    panels.add(firstPanel);
    setContentPane(createContent());
  }
  




  public Object getResult()
  {
    return result;
  }
  






  public int getStepCount()
  {
    return 0;
  }
  




  public boolean canDoPreviousPanel()
  {
    return step > 0;
  }
  




  public boolean canDoNextPanel()
  {
    return currentPanel.hasNextPanel();
  }
  





  public boolean canFinish()
  {
    return currentPanel.canFinish();
  }
  






  public WizardPanel getWizardPanel(int step)
  {
    if (step < panels.size()) {
      return (WizardPanel)panels.get(step);
    }
    
    return null;
  }
  





  public void actionPerformed(ActionEvent event)
  {
    String command = event.getActionCommand();
    if (command.equals("nextButton")) {
      next();
    }
    else if (command.equals("previousButton")) {
      previous();
    }
    else if (command.equals("finishButton")) {
      finish();
    }
  }
  


  public void previous()
  {
    if (step > 0) {
      WizardPanel previousPanel = getWizardPanel(step - 1);
      
      previousPanel.returnFromLaterStep();
      Container content = getContentPane();
      content.remove(currentPanel);
      content.add(previousPanel);
      step -= 1;
      currentPanel = previousPanel;
      setTitle("Step " + (step + 1));
      enableButtons();
      pack();
    }
  }
  



  public void next()
  {
    WizardPanel nextPanel = getWizardPanel(step + 1);
    if (nextPanel != null) {
      if (!currentPanel.canRedisplayNextPanel()) {
        nextPanel = currentPanel.getNextPanel();
      }
    }
    else {
      nextPanel = currentPanel.getNextPanel();
    }
    
    step += 1;
    if (step < panels.size()) {
      panels.set(step, nextPanel);
    }
    else {
      panels.add(nextPanel);
    }
    
    Container content = getContentPane();
    content.remove(currentPanel);
    content.add(nextPanel);
    
    currentPanel = nextPanel;
    setTitle("Step " + (step + 1));
    enableButtons();
    pack();
  }
  



  public void finish()
  {
    result = currentPanel.getResult();
    setVisible(false);
  }
  



  private void enableButtons()
  {
    previousButton.setEnabled(step > 0);
    nextButton.setEnabled(canDoNextPanel());
    finishButton.setEnabled(canFinish());
    helpButton.setEnabled(false);
  }
  




  public boolean isCancelled()
  {
    return false;
  }
  





  public JPanel createContent()
  {
    JPanel content = new JPanel(new BorderLayout());
    content.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    content.add((JPanel)panels.get(0));
    L1R3ButtonPanel buttons = new L1R3ButtonPanel("Help", "Previous", "Next", "Finish");
    
    helpButton = buttons.getLeftButton();
    helpButton.setEnabled(false);
    
    previousButton = buttons.getRightButton1();
    previousButton.setActionCommand("previousButton");
    previousButton.addActionListener(this);
    previousButton.setEnabled(false);
    
    nextButton = buttons.getRightButton2();
    nextButton.setActionCommand("nextButton");
    nextButton.addActionListener(this);
    nextButton.setEnabled(true);
    
    finishButton = buttons.getRightButton3();
    finishButton.setActionCommand("finishButton");
    finishButton.addActionListener(this);
    finishButton.setEnabled(false);
    
    buttons.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
    content.add(buttons, "South");
    
    return content;
  }
}
