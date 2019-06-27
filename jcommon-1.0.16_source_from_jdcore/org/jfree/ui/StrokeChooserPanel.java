package org.jfree.ui;

import java.awt.BorderLayout;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;



























































public class StrokeChooserPanel
  extends JPanel
{
  private JComboBox selector;
  
  public StrokeChooserPanel(StrokeSample current, StrokeSample[] available)
  {
    setLayout(new BorderLayout());
    


    DefaultComboBoxModel model = new DefaultComboBoxModel();
    for (int i = 0; i < available.length; i++) {
      model.addElement(available[i].getStroke());
    }
    selector = new JComboBox(model);
    selector.setSelectedItem(current.getStroke());
    selector.setRenderer(new StrokeSample(null));
    add(selector);
    
    selector.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        getSelector().transferFocus();
      }
    });
  }
  





  protected final JComboBox getSelector()
  {
    return selector;
  }
  




  public Stroke getSelectedStroke()
  {
    return (Stroke)selector.getSelectedItem();
  }
}
