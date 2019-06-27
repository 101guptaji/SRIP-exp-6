package org.jfree.chart.editor;

import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.jfree.chart.plot.ColorPalette;
import org.jfree.chart.plot.RainbowPalette;
























































/**
 * @deprecated
 */
class PaletteChooserPanel
  extends JPanel
{
  private JComboBox selector;
  
  public PaletteChooserPanel(PaletteSample current, PaletteSample[] available)
  {
    setLayout(new BorderLayout());
    selector = new JComboBox(available);
    selector.setSelectedItem(current);
    selector.setRenderer(new PaletteSample(new RainbowPalette()));
    add(selector);
  }
  




  public ColorPalette getSelectedPalette()
  {
    PaletteSample sample = (PaletteSample)selector.getSelectedItem();
    return sample.getPalette();
  }
}
