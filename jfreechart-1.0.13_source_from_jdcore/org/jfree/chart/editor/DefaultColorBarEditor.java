package org.jfree.chart.editor;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.jfree.chart.axis.ColorBar;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.ColorPalette;
import org.jfree.chart.plot.GreyPalette;
import org.jfree.chart.plot.RainbowPalette;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.layout.LCBLayout;



























































class DefaultColorBarEditor
  extends DefaultNumberAxisEditor
{
  private JCheckBox invertPaletteCheckBox;
  private boolean invertPalette = false;
  

  private JCheckBox stepPaletteCheckBox;
  

  private boolean stepPalette = false;
  

  private PaletteSample currentPalette;
  

  private PaletteSample[] availablePaletteSamples;
  

  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.editor.LocalizationBundle");
  






  public DefaultColorBarEditor(ColorBar colorBar)
  {
    super((NumberAxis)colorBar.getAxis());
    invertPalette = colorBar.getColorPalette().isInverse();
    stepPalette = colorBar.getColorPalette().isStepped();
    currentPalette = new PaletteSample(colorBar.getColorPalette());
    availablePaletteSamples = new PaletteSample[2];
    availablePaletteSamples[0] = new PaletteSample(new RainbowPalette());
    
    availablePaletteSamples[1] = new PaletteSample(new GreyPalette());
    

    JTabbedPane other = getOtherTabs();
    
    JPanel palettePanel = new JPanel(new LCBLayout(4));
    palettePanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    
    palettePanel.add(new JPanel());
    invertPaletteCheckBox = new JCheckBox(localizationResources.getString("Invert_Palette"), invertPalette);
    


    invertPaletteCheckBox.setActionCommand("invertPalette");
    invertPaletteCheckBox.addActionListener(this);
    palettePanel.add(invertPaletteCheckBox);
    palettePanel.add(new JPanel());
    
    palettePanel.add(new JPanel());
    stepPaletteCheckBox = new JCheckBox(localizationResources.getString("Step_Palette"), stepPalette);
    


    stepPaletteCheckBox.setActionCommand("stepPalette");
    stepPaletteCheckBox.addActionListener(this);
    palettePanel.add(stepPaletteCheckBox);
    palettePanel.add(new JPanel());
    
    palettePanel.add(new JLabel(localizationResources.getString("Palette")));
    

    JButton button = new JButton(localizationResources.getString("Set_palette..."));
    
    button.setActionCommand("PaletteChoice");
    button.addActionListener(this);
    palettePanel.add(currentPalette);
    palettePanel.add(button);
    
    other.add(localizationResources.getString("Palette"), palettePanel);
  }
  





  public void actionPerformed(ActionEvent event)
  {
    String command = event.getActionCommand();
    if (command.equals("PaletteChoice")) {
      attemptPaletteSelection();
    }
    else if (command.equals("invertPalette")) {
      invertPalette = invertPaletteCheckBox.isSelected();
    }
    else if (command.equals("stepPalette")) {
      stepPalette = stepPaletteCheckBox.isSelected();
    }
    else {
      super.actionPerformed(event);
    }
  }
  


  private void attemptPaletteSelection()
  {
    PaletteChooserPanel panel = new PaletteChooserPanel(null, availablePaletteSamples);
    
    int result = JOptionPane.showConfirmDialog(this, panel, localizationResources.getString("Palette_Selection"), 2, -1);
    



    if (result == 0) {
      double zmin = currentPalette.getPalette().getMinZ();
      double zmax = currentPalette.getPalette().getMaxZ();
      currentPalette.setPalette(panel.getSelectedPalette());
      currentPalette.getPalette().setMinZ(zmin);
      currentPalette.getPalette().setMaxZ(zmax);
    }
  }
  





  public void setAxisProperties(ColorBar colorBar)
  {
    super.setAxisProperties(colorBar.getAxis());
    colorBar.setColorPalette(currentPalette.getPalette());
    colorBar.getColorPalette().setInverse(invertPalette);
    colorBar.getColorPalette().setStepped(stepPalette);
  }
  








  public static DefaultColorBarEditor getInstance(ColorBar colorBar)
  {
    if (colorBar != null) {
      return new DefaultColorBarEditor(colorBar);
    }
    
    return null;
  }
}
