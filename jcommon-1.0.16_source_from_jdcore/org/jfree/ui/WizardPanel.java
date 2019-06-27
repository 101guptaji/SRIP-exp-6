package org.jfree.ui;

import java.awt.LayoutManager;
import javax.swing.JPanel;





















































public abstract class WizardPanel
  extends JPanel
{
  private WizardDialog owner;
  
  protected WizardPanel(LayoutManager layout)
  {
    super(layout);
  }
  




  public WizardDialog getOwner()
  {
    return owner;
  }
  





  public void setOwner(WizardDialog owner)
  {
    this.owner = owner;
  }
  




  public Object getResult()
  {
    return null;
  }
  
  public abstract void returnFromLaterStep();
  
  public abstract boolean canRedisplayNextPanel();
  
  public abstract boolean hasNextPanel();
  
  public abstract boolean canFinish();
  
  public abstract WizardPanel getNextPanel();
}
