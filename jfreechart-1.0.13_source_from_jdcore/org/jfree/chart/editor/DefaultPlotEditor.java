package org.jfree.chart.editor;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.ColorBar;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.ContourPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.layout.LCBLayout;
import org.jfree.ui.PaintSample;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.StrokeChooserPanel;
import org.jfree.ui.StrokeSample;
import org.jfree.util.BooleanUtilities;


















































class DefaultPlotEditor
  extends JPanel
  implements ActionListener
{
  private static final String[] orientationNames = { "Vertical", "Horizontal" };
  


  private static final int ORIENTATION_VERTICAL = 0;
  


  private static final int ORIENTATION_HORIZONTAL = 1;
  


  private PaintSample backgroundPaintSample;
  


  private StrokeSample outlineStrokeSample;
  


  private PaintSample outlinePaintSample;
  


  private DefaultAxisEditor domainAxisPropertyPanel;
  


  private DefaultAxisEditor rangeAxisPropertyPanel;
  


  private DefaultColorBarEditor colorBarAxisPropertyPanel;
  


  private StrokeSample[] availableStrokeSamples;
  


  private RectangleInsets plotInsets;
  


  private PlotOrientation plotOrientation;
  


  private JComboBox orientationCombo;
  


  private Boolean drawLines;
  


  private JCheckBox drawLinesCheckBox;
  


  private Boolean drawShapes;
  


  private JCheckBox drawShapesCheckBox;
  


  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.editor.LocalizationBundle");
  












  public DefaultPlotEditor(Plot plot)
  {
    plotInsets = plot.getInsets();
    backgroundPaintSample = new PaintSample(plot.getBackgroundPaint());
    outlineStrokeSample = new StrokeSample(plot.getOutlineStroke());
    outlinePaintSample = new PaintSample(plot.getOutlinePaint());
    if ((plot instanceof CategoryPlot)) {
      plotOrientation = ((CategoryPlot)plot).getOrientation();
    }
    else if ((plot instanceof XYPlot)) {
      plotOrientation = ((XYPlot)plot).getOrientation();
    }
    if ((plot instanceof CategoryPlot)) {
      CategoryItemRenderer renderer = ((CategoryPlot)plot).getRenderer();
      if ((renderer instanceof LineAndShapeRenderer)) {
        LineAndShapeRenderer r = (LineAndShapeRenderer)renderer;
        drawLines = BooleanUtilities.valueOf(r.getBaseLinesVisible());
        
        drawShapes = BooleanUtilities.valueOf(r.getBaseShapesVisible());
      }
      
    }
    else if ((plot instanceof XYPlot)) {
      XYItemRenderer renderer = ((XYPlot)plot).getRenderer();
      if ((renderer instanceof StandardXYItemRenderer)) {
        StandardXYItemRenderer r = (StandardXYItemRenderer)renderer;
        drawLines = BooleanUtilities.valueOf(r.getPlotLines());
        drawShapes = BooleanUtilities.valueOf(r.getBaseShapesVisible());
      }
    }
    

    setLayout(new BorderLayout());
    
    availableStrokeSamples = new StrokeSample[4];
    availableStrokeSamples[0] = new StrokeSample(null);
    availableStrokeSamples[1] = new StrokeSample(new BasicStroke(1.0F));
    
    availableStrokeSamples[2] = new StrokeSample(new BasicStroke(2.0F));
    
    availableStrokeSamples[3] = new StrokeSample(new BasicStroke(3.0F));
    


    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), plot.getPlotType() + localizationResources.getString(":")));
    


    JPanel general = new JPanel(new BorderLayout());
    general.setBorder(BorderFactory.createTitledBorder(localizationResources.getString("General")));
    

    JPanel interior = new JPanel(new LCBLayout(7));
    interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    












    interior.add(new JLabel(localizationResources.getString("Outline_stroke")));
    
    JButton button = new JButton(localizationResources.getString("Select..."));
    
    button.setActionCommand("OutlineStroke");
    button.addActionListener(this);
    interior.add(outlineStrokeSample);
    interior.add(button);
    
    interior.add(new JLabel(localizationResources.getString("Outline_Paint")));
    
    button = new JButton(localizationResources.getString("Select..."));
    button.setActionCommand("OutlinePaint");
    button.addActionListener(this);
    interior.add(outlinePaintSample);
    interior.add(button);
    
    interior.add(new JLabel(localizationResources.getString("Background_paint")));
    
    button = new JButton(localizationResources.getString("Select..."));
    button.setActionCommand("BackgroundPaint");
    button.addActionListener(this);
    interior.add(backgroundPaintSample);
    interior.add(button);
    
    if (plotOrientation != null) {
      boolean isVertical = plotOrientation.equals(PlotOrientation.VERTICAL);
      
      int index = isVertical ? 0 : 1;
      
      interior.add(new JLabel(localizationResources.getString("Orientation")));
      
      orientationCombo = new JComboBox(orientationNames);
      orientationCombo.setSelectedIndex(index);
      orientationCombo.setActionCommand("Orientation");
      orientationCombo.addActionListener(this);
      interior.add(new JPanel());
      interior.add(orientationCombo);
    }
    
    if (drawLines != null) {
      interior.add(new JLabel(localizationResources.getString("Draw_lines")));
      
      drawLinesCheckBox = new JCheckBox();
      drawLinesCheckBox.setSelected(drawLines.booleanValue());
      drawLinesCheckBox.setActionCommand("DrawLines");
      drawLinesCheckBox.addActionListener(this);
      interior.add(new JPanel());
      interior.add(drawLinesCheckBox);
    }
    
    if (drawShapes != null) {
      interior.add(new JLabel(localizationResources.getString("Draw_shapes")));
      
      drawShapesCheckBox = new JCheckBox();
      drawShapesCheckBox.setSelected(drawShapes.booleanValue());
      drawShapesCheckBox.setActionCommand("DrawShapes");
      drawShapesCheckBox.addActionListener(this);
      interior.add(new JPanel());
      interior.add(drawShapesCheckBox);
    }
    
    general.add(interior, "North");
    
    JPanel appearance = new JPanel(new BorderLayout());
    appearance.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    appearance.add(general, "North");
    
    JTabbedPane tabs = new JTabbedPane();
    tabs.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    
    Axis domainAxis = null;
    if ((plot instanceof CategoryPlot)) {
      domainAxis = ((CategoryPlot)plot).getDomainAxis();
    }
    else if ((plot instanceof XYPlot)) {
      domainAxis = ((XYPlot)plot).getDomainAxis();
    }
    domainAxisPropertyPanel = DefaultAxisEditor.getInstance(domainAxis);
    
    if (domainAxisPropertyPanel != null) {
      domainAxisPropertyPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      
      tabs.add(localizationResources.getString("Domain_Axis"), domainAxisPropertyPanel);
    }
    

    Axis rangeAxis = null;
    if ((plot instanceof CategoryPlot)) {
      rangeAxis = ((CategoryPlot)plot).getRangeAxis();
    }
    else if ((plot instanceof XYPlot)) {
      rangeAxis = ((XYPlot)plot).getRangeAxis();
    }
    
    rangeAxisPropertyPanel = DefaultAxisEditor.getInstance(rangeAxis);
    if (rangeAxisPropertyPanel != null) {
      rangeAxisPropertyPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      
      tabs.add(localizationResources.getString("Range_Axis"), rangeAxisPropertyPanel);
    }
    


    ColorBar colorBar = null;
    if ((plot instanceof ContourPlot)) {
      colorBar = ((ContourPlot)plot).getColorBar();
    }
    
    colorBarAxisPropertyPanel = DefaultColorBarEditor.getInstance(colorBar);
    
    if (colorBarAxisPropertyPanel != null) {
      colorBarAxisPropertyPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      
      tabs.add(localizationResources.getString("Color_Bar"), colorBarAxisPropertyPanel);
    }
    


    tabs.add(localizationResources.getString("Appearance"), appearance);
    panel.add(tabs);
    
    add(panel);
  }
  




  public RectangleInsets getPlotInsets()
  {
    if (plotInsets == null) {
      plotInsets = new RectangleInsets(0.0D, 0.0D, 0.0D, 0.0D);
    }
    return plotInsets;
  }
  




  public Paint getBackgroundPaint()
  {
    return backgroundPaintSample.getPaint();
  }
  




  public Stroke getOutlineStroke()
  {
    return outlineStrokeSample.getStroke();
  }
  




  public Paint getOutlinePaint()
  {
    return outlinePaintSample.getPaint();
  }
  





  public DefaultAxisEditor getDomainAxisPropertyEditPanel()
  {
    return domainAxisPropertyPanel;
  }
  





  public DefaultAxisEditor getRangeAxisPropertyEditPanel()
  {
    return rangeAxisPropertyPanel;
  }
  



  public void actionPerformed(ActionEvent event)
  {
    String command = event.getActionCommand();
    if (command.equals("BackgroundPaint")) {
      attemptBackgroundPaintSelection();
    }
    else if (command.equals("OutlineStroke")) {
      attemptOutlineStrokeSelection();
    }
    else if (command.equals("OutlinePaint")) {
      attemptOutlinePaintSelection();



    }
    else if (command.equals("Orientation")) {
      attemptOrientationSelection();
    }
    else if (command.equals("DrawLines")) {
      attemptDrawLinesSelection();
    }
    else if (command.equals("DrawShapes")) {
      attemptDrawShapesSelection();
    }
  }
  



  private void attemptBackgroundPaintSelection()
  {
    Color c = JColorChooser.showDialog(this, localizationResources.getString("Background_Color"), Color.blue);
    
    if (c != null) {
      backgroundPaintSample.setPaint(c);
    }
  }
  


  private void attemptOutlineStrokeSelection()
  {
    StrokeChooserPanel panel = new StrokeChooserPanel(outlineStrokeSample, availableStrokeSamples);
    
    int result = JOptionPane.showConfirmDialog(this, panel, localizationResources.getString("Stroke_Selection"), 2, -1);
    


    if (result == 0) {
      outlineStrokeSample.setStroke(panel.getSelectedStroke());
    }
  }
  




  private void attemptOutlinePaintSelection()
  {
    Color c = JColorChooser.showDialog(this, localizationResources.getString("Outline_Color"), Color.blue);
    
    if (c != null) {
      outlinePaintSample.setPaint(c);
    }
  }
  





















  private void attemptOrientationSelection()
  {
    int index = orientationCombo.getSelectedIndex();
    
    if (index == 0) {
      plotOrientation = PlotOrientation.VERTICAL;
    }
    else {
      plotOrientation = PlotOrientation.HORIZONTAL;
    }
  }
  




  private void attemptDrawLinesSelection()
  {
    drawLines = BooleanUtilities.valueOf(drawLinesCheckBox.isSelected());
  }
  




  private void attemptDrawShapesSelection()
  {
    drawShapes = BooleanUtilities.valueOf(drawShapesCheckBox.isSelected());
  }
  







  public void updatePlotProperties(Plot plot)
  {
    plot.setOutlinePaint(getOutlinePaint());
    plot.setOutlineStroke(getOutlineStroke());
    plot.setBackgroundPaint(getBackgroundPaint());
    plot.setInsets(getPlotInsets());
    

    if (domainAxisPropertyPanel != null) {
      Axis domainAxis = null;
      if ((plot instanceof CategoryPlot)) {
        CategoryPlot p = (CategoryPlot)plot;
        domainAxis = p.getDomainAxis();
      }
      else if ((plot instanceof XYPlot)) {
        XYPlot p = (XYPlot)plot;
        domainAxis = p.getDomainAxis();
      }
      if (domainAxis != null) {
        domainAxisPropertyPanel.setAxisProperties(domainAxis);
      }
    }
    
    if (rangeAxisPropertyPanel != null) {
      Axis rangeAxis = null;
      if ((plot instanceof CategoryPlot)) {
        CategoryPlot p = (CategoryPlot)plot;
        rangeAxis = p.getRangeAxis();
      }
      else if ((plot instanceof XYPlot)) {
        XYPlot p = (XYPlot)plot;
        rangeAxis = p.getRangeAxis();
      }
      if (rangeAxis != null) {
        rangeAxisPropertyPanel.setAxisProperties(rangeAxis);
      }
    }
    
    if (plotOrientation != null) {
      if ((plot instanceof CategoryPlot)) {
        CategoryPlot p = (CategoryPlot)plot;
        p.setOrientation(plotOrientation);
      }
      else if ((plot instanceof XYPlot)) {
        XYPlot p = (XYPlot)plot;
        p.setOrientation(plotOrientation);
      }
    }
    
    if (drawLines != null) {
      if ((plot instanceof CategoryPlot)) {
        CategoryPlot p = (CategoryPlot)plot;
        CategoryItemRenderer r = p.getRenderer();
        if ((r instanceof LineAndShapeRenderer)) {
          ((LineAndShapeRenderer)r).setLinesVisible(drawLines.booleanValue());
        }
        
      }
      else if ((plot instanceof XYPlot)) {
        XYPlot p = (XYPlot)plot;
        XYItemRenderer r = p.getRenderer();
        if ((r instanceof StandardXYItemRenderer)) {
          ((StandardXYItemRenderer)r).setPlotLines(drawLines.booleanValue());
        }
      }
    }
    

    if (drawShapes != null) {
      if ((plot instanceof CategoryPlot)) {
        CategoryPlot p = (CategoryPlot)plot;
        CategoryItemRenderer r = p.getRenderer();
        if ((r instanceof LineAndShapeRenderer)) {
          ((LineAndShapeRenderer)r).setShapesVisible(drawShapes.booleanValue());
        }
        
      }
      else if ((plot instanceof XYPlot)) {
        XYPlot p = (XYPlot)plot;
        XYItemRenderer r = p.getRenderer();
        if ((r instanceof StandardXYItemRenderer)) {
          ((StandardXYItemRenderer)r).setBaseShapesVisible(drawShapes.booleanValue());
        }
      }
    }
    


    if (colorBarAxisPropertyPanel != null) {
      ColorBar colorBar = null;
      if ((plot instanceof ContourPlot)) {
        ContourPlot p = (ContourPlot)plot;
        colorBar = p.getColorBar();
      }
      if (colorBar != null) {
        colorBarAxisPropertyPanel.setAxisProperties(colorBar);
      }
    }
  }
}
