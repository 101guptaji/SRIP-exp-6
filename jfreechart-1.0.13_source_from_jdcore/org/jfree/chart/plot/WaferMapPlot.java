package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import java.util.ResourceBundle;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.renderer.WaferMapRenderer;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.WaferMapDataset;
import org.jfree.ui.RectangleInsets;




















































public class WaferMapPlot
  extends Plot
  implements RendererChangeListener, Cloneable, Serializable
{
  private static final long serialVersionUID = 4668320403707308155L;
  public static final Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0.5F, 0, 2, 0.0F, new float[] { 2.0F, 2.0F }, 0.0F);
  






  public static final Paint DEFAULT_GRIDLINE_PAINT = Color.lightGray;
  

  public static final boolean DEFAULT_CROSSHAIR_VISIBLE = false;
  

  public static final Stroke DEFAULT_CROSSHAIR_STROKE = DEFAULT_GRIDLINE_STROKE;
  


  public static final Paint DEFAULT_CROSSHAIR_PAINT = Color.blue;
  

  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
  



  private PlotOrientation orientation;
  



  private WaferMapDataset dataset;
  



  private WaferMapRenderer renderer;
  




  public WaferMapPlot()
  {
    this(null);
  }
  




  public WaferMapPlot(WaferMapDataset dataset)
  {
    this(dataset, null);
  }
  








  public WaferMapPlot(WaferMapDataset dataset, WaferMapRenderer renderer)
  {
    orientation = PlotOrientation.VERTICAL;
    
    this.dataset = dataset;
    if (dataset != null) {
      dataset.addChangeListener(this);
    }
    
    this.renderer = renderer;
    if (renderer != null) {
      renderer.setPlot(this);
      renderer.addChangeListener(this);
    }
  }
  





  public String getPlotType()
  {
    return "WMAP_Plot";
  }
  




  public WaferMapDataset getDataset()
  {
    return dataset;
  }
  







  public void setDataset(WaferMapDataset dataset)
  {
    if (this.dataset != null) {
      this.dataset.removeChangeListener(this);
    }
    

    this.dataset = dataset;
    if (dataset != null) {
      setDatasetGroup(dataset.getGroup());
      dataset.addChangeListener(this);
    }
    

    datasetChanged(new DatasetChangeEvent(this, dataset));
  }
  






  public void setRenderer(WaferMapRenderer renderer)
  {
    if (this.renderer != null) {
      this.renderer.removeChangeListener(this);
    }
    this.renderer = renderer;
    if (renderer != null) {
      renderer.setPlot(this);
    }
    fireChangeEvent();
  }
  












  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState state, PlotRenderingInfo info)
  {
    boolean b1 = area.getWidth() <= 10.0D;
    boolean b2 = area.getHeight() <= 10.0D;
    if ((b1) || (b2)) {
      return;
    }
    

    if (info != null) {
      info.setPlotArea(area);
    }
    

    RectangleInsets insets = getInsets();
    insets.trim(area);
    
    drawChipGrid(g2, area);
    drawWaferEdge(g2, area);
  }
  







  protected void drawChipGrid(Graphics2D g2, Rectangle2D plotArea)
  {
    Shape savedClip = g2.getClip();
    g2.setClip(getWaferEdge(plotArea));
    Rectangle2D chip = new Rectangle2D.Double();
    int xchips = 35;
    int ychips = 20;
    double space = 1.0D;
    if (dataset != null) {
      xchips = dataset.getMaxChipX() + 2;
      ychips = dataset.getMaxChipY() + 2;
      space = dataset.getChipSpace();
    }
    double startX = plotArea.getX();
    double startY = plotArea.getY();
    double chipWidth = 1.0D;
    double chipHeight = 1.0D;
    if (plotArea.getWidth() != plotArea.getHeight()) {
      double major = 0.0D;
      double minor = 0.0D;
      if (plotArea.getWidth() > plotArea.getHeight()) {
        major = plotArea.getWidth();
        minor = plotArea.getHeight();
      }
      else {
        major = plotArea.getHeight();
        minor = plotArea.getWidth();
      }
      
      if (plotArea.getWidth() == minor) {
        startY += (major - minor) / 2.0D;
        chipWidth = (plotArea.getWidth() - (space * xchips - 1.0D)) / xchips;
        
        chipHeight = (plotArea.getWidth() - (space * ychips - 1.0D)) / ychips;
      }
      else
      {
        startX += (major - minor) / 2.0D;
        chipWidth = (plotArea.getHeight() - (space * xchips - 1.0D)) / xchips;
        
        chipHeight = (plotArea.getHeight() - (space * ychips - 1.0D)) / ychips;
      }
    }
    

    for (int x = 1; x <= xchips; x++) {
      double upperLeftX = startX - chipWidth + chipWidth * x + space * (x - 1);
      
      for (int y = 1; y <= ychips; y++) {
        double upperLeftY = startY - chipHeight + chipHeight * y + space * (y - 1);
        
        chip.setFrame(upperLeftX, upperLeftY, chipWidth, chipHeight);
        g2.setColor(Color.white);
        if (dataset.getChipValue(x - 1, ychips - y - 1) != null) {
          g2.setPaint(renderer.getChipColor(dataset.getChipValue(x - 1, ychips - y - 1)));
        }
        



        g2.fill(chip);
        g2.setColor(Color.lightGray);
        g2.draw(chip);
      }
    }
    g2.setClip(savedClip);
  }
  






  protected Ellipse2D getWaferEdge(Rectangle2D plotArea)
  {
    Ellipse2D edge = new Ellipse2D.Double();
    double diameter = plotArea.getWidth();
    double upperLeftX = plotArea.getX();
    double upperLeftY = plotArea.getY();
    
    if (plotArea.getWidth() != plotArea.getHeight()) {
      double major = 0.0D;
      double minor = 0.0D;
      if (plotArea.getWidth() > plotArea.getHeight()) {
        major = plotArea.getWidth();
        minor = plotArea.getHeight();
      }
      else {
        major = plotArea.getHeight();
        minor = plotArea.getWidth();
      }
      
      diameter = minor;
      
      if (plotArea.getWidth() == minor) {
        upperLeftY = plotArea.getY() + (major - minor) / 2.0D;
      }
      else {
        upperLeftX = plotArea.getX() + (major - minor) / 2.0D;
      }
    }
    edge.setFrame(upperLeftX, upperLeftY, diameter, diameter);
    return edge;
  }
  






  protected void drawWaferEdge(Graphics2D g2, Rectangle2D plotArea)
  {
    Ellipse2D waferEdge = getWaferEdge(plotArea);
    g2.setColor(Color.black);
    g2.draw(waferEdge);
    


    Arc2D notch = null;
    Rectangle2D waferFrame = waferEdge.getFrame();
    double notchDiameter = waferFrame.getWidth() * 0.04D;
    if (orientation == PlotOrientation.HORIZONTAL) {
      Rectangle2D notchFrame = new Rectangle2D.Double(waferFrame.getX() + waferFrame.getWidth() - notchDiameter / 2.0D, waferFrame.getY() + waferFrame.getHeight() / 2.0D - notchDiameter / 2.0D, notchDiameter, notchDiameter);
      





      notch = new Arc2D.Double(notchFrame, 90.0D, 180.0D, 0);
    }
    else {
      Rectangle2D notchFrame = new Rectangle2D.Double(waferFrame.getX() + waferFrame.getWidth() / 2.0D - notchDiameter / 2.0D, waferFrame.getY() + waferFrame.getHeight() - notchDiameter / 2.0D, notchDiameter, notchDiameter);
      





      notch = new Arc2D.Double(notchFrame, 0.0D, 180.0D, 0);
    }
    g2.setColor(Color.white);
    g2.fill(notch);
    g2.setColor(Color.black);
    g2.draw(notch);
  }
  





  public LegendItemCollection getLegendItems()
  {
    return renderer.getLegendCollection();
  }
  




  public void rendererChanged(RendererChangeEvent event)
  {
    fireChangeEvent();
  }
}
