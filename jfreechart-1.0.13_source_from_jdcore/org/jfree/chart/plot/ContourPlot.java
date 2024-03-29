package org.jfree.chart.plot;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.RectangularShape;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ClipPath;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.ColorBar;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.ContourEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.labels.ContourToolTipGenerator;
import org.jfree.chart.labels.StandardContourToolTipGenerator;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.Range;
import org.jfree.data.contour.ContourDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ObjectUtilities;










































































/**
 * @deprecated
 */
public class ContourPlot
  extends Plot
  implements ContourValuePlot, ValueAxisPlot, PropertyChangeListener, Serializable, Cloneable
{
  private static final long serialVersionUID = 7861072556590502247L;
  protected static final RectangleInsets DEFAULT_INSETS = new RectangleInsets(2.0D, 2.0D, 100.0D, 10.0D);
  


  private ValueAxis domainAxis;
  

  private ValueAxis rangeAxis;
  

  private ContourDataset dataset;
  

  private ColorBar colorBar = null;
  


  private RectangleEdge colorBarLocation;
  


  private boolean domainCrosshairVisible;
  


  private double domainCrosshairValue;
  

  private transient Stroke domainCrosshairStroke;
  

  private transient Paint domainCrosshairPaint;
  

  private boolean domainCrosshairLockedOnData = true;
  


  private boolean rangeCrosshairVisible;
  


  private double rangeCrosshairValue;
  


  private transient Stroke rangeCrosshairStroke;
  

  private transient Paint rangeCrosshairPaint;
  

  private boolean rangeCrosshairLockedOnData = true;
  









  private double dataAreaRatio = 0.0D;
  


  private List domainMarkers;
  


  private List rangeMarkers;
  


  private List annotations;
  

  private ContourToolTipGenerator toolTipGenerator;
  

  private XYURLGenerator urlGenerator;
  

  private boolean renderAsPoints = false;
  




  private double ptSizePct = 0.05D;
  

  private transient ClipPath clipPath = null;
  

  private transient Paint missingPaint = null;
  

  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
  




  public ContourPlot()
  {
    this(null, null, null, null);
  }
  













  public ContourPlot(ContourDataset dataset, ValueAxis domainAxis, ValueAxis rangeAxis, ColorBar colorBar)
  {
    this.dataset = dataset;
    if (dataset != null) {
      dataset.addChangeListener(this);
    }
    
    this.domainAxis = domainAxis;
    if (domainAxis != null) {
      domainAxis.setPlot(this);
      domainAxis.addChangeListener(this);
    }
    
    this.rangeAxis = rangeAxis;
    if (rangeAxis != null) {
      rangeAxis.setPlot(this);
      rangeAxis.addChangeListener(this);
    }
    
    this.colorBar = colorBar;
    if (colorBar != null) {
      colorBar.getAxis().setPlot(this);
      colorBar.getAxis().addChangeListener(this);
      colorBar.configure(this);
    }
    colorBarLocation = RectangleEdge.LEFT;
    
    toolTipGenerator = new StandardContourToolTipGenerator();
  }
  





  public RectangleEdge getColorBarLocation()
  {
    return colorBarLocation;
  }
  





  public void setColorBarLocation(RectangleEdge edge)
  {
    colorBarLocation = edge;
    fireChangeEvent();
  }
  




  public ContourDataset getDataset()
  {
    return dataset;
  }
  








  public void setDataset(ContourDataset dataset)
  {
    ContourDataset existing = this.dataset;
    if (existing != null) {
      existing.removeChangeListener(this);
    }
    

    this.dataset = dataset;
    if (dataset != null) {
      setDatasetGroup(dataset.getGroup());
      dataset.addChangeListener(this);
    }
    

    DatasetChangeEvent event = new DatasetChangeEvent(this, dataset);
    datasetChanged(event);
  }
  






  public ValueAxis getDomainAxis()
  {
    ValueAxis result = domainAxis;
    
    return result;
  }
  







  public void setDomainAxis(ValueAxis axis)
  {
    if (isCompatibleDomainAxis(axis))
    {
      if (axis != null) {
        axis.setPlot(this);
        axis.addChangeListener(this);
      }
      

      if (domainAxis != null) {
        domainAxis.removeChangeListener(this);
      }
      
      domainAxis = axis;
      fireChangeEvent();
    }
  }
  







  public ValueAxis getRangeAxis()
  {
    ValueAxis result = rangeAxis;
    
    return result;
  }
  









  public void setRangeAxis(ValueAxis axis)
  {
    if (axis != null) {
      axis.setPlot(this);
      axis.addChangeListener(this);
    }
    

    if (rangeAxis != null) {
      rangeAxis.removeChangeListener(this);
    }
    
    rangeAxis = axis;
    fireChangeEvent();
  }
  






  public void setColorBarAxis(ColorBar axis)
  {
    colorBar = axis;
    fireChangeEvent();
  }
  





  public double getDataAreaRatio()
  {
    return dataAreaRatio;
  }
  




  public void setDataAreaRatio(double ratio)
  {
    dataAreaRatio = ratio;
  }
  








  public void addDomainMarker(Marker marker)
  {
    if (domainMarkers == null) {
      domainMarkers = new ArrayList();
    }
    domainMarkers.add(marker);
    fireChangeEvent();
  }
  



  public void clearDomainMarkers()
  {
    if (domainMarkers != null) {
      domainMarkers.clear();
      fireChangeEvent();
    }
  }
  








  public void addRangeMarker(Marker marker)
  {
    if (rangeMarkers == null) {
      rangeMarkers = new ArrayList();
    }
    rangeMarkers.add(marker);
    fireChangeEvent();
  }
  



  public void clearRangeMarkers()
  {
    if (rangeMarkers != null) {
      rangeMarkers.clear();
      fireChangeEvent();
    }
  }
  





  public void addAnnotation(XYAnnotation annotation)
  {
    if (annotations == null) {
      annotations = new ArrayList();
    }
    annotations.add(annotation);
    fireChangeEvent();
  }
  



  public void clearAnnotations()
  {
    if (annotations != null) {
      annotations.clear();
      fireChangeEvent();
    }
  }
  








  public boolean isCompatibleDomainAxis(ValueAxis axis)
  {
    return true;
  }
  




















  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info)
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
    
    AxisSpace space = new AxisSpace();
    
    space = domainAxis.reserveSpace(g2, this, area, RectangleEdge.BOTTOM, space);
    
    space = rangeAxis.reserveSpace(g2, this, area, RectangleEdge.LEFT, space);
    

    Rectangle2D estimatedDataArea = space.shrink(area, null);
    
    AxisSpace space2 = new AxisSpace();
    space2 = colorBar.reserveSpace(g2, this, area, estimatedDataArea, colorBarLocation, space2);
    
    Rectangle2D adjustedPlotArea = space2.shrink(area, null);
    
    Rectangle2D dataArea = space.shrink(adjustedPlotArea, null);
    
    Rectangle2D colorBarArea = space2.reserved(area, colorBarLocation);
    

    if (getDataAreaRatio() != 0.0D) {
      double ratio = getDataAreaRatio();
      Rectangle2D tmpDataArea = (Rectangle2D)dataArea.clone();
      double h = tmpDataArea.getHeight();
      double w = tmpDataArea.getWidth();
      
      if (ratio > 0.0D) {
        if (w * ratio <= h) {
          h = ratio * w;
        }
        else {
          w = h / ratio;
        }
      }
      else {
        ratio *= -1.0D;
        double xLength = getDomainAxis().getRange().getLength();
        double yLength = getRangeAxis().getRange().getLength();
        double unitRatio = yLength / xLength;
        
        ratio = unitRatio * ratio;
        
        if (w * ratio <= h) {
          h = ratio * w;
        }
        else {
          w = h / ratio;
        }
      }
      
      dataArea.setRect(tmpDataArea.getX() + tmpDataArea.getWidth() / 2.0D - w / 2.0D, tmpDataArea.getY(), w, h);
    }
    

    if (info != null) {
      info.setDataArea(dataArea);
    }
    
    CrosshairState crosshairState = new CrosshairState();
    crosshairState.setCrosshairDistance(Double.POSITIVE_INFINITY);
    

    drawBackground(g2, dataArea);
    
    double cursor = dataArea.getMaxY();
    if (domainAxis != null) {
      domainAxis.draw(g2, cursor, adjustedPlotArea, dataArea, RectangleEdge.BOTTOM, info);
    }
    

    if (rangeAxis != null) {
      cursor = dataArea.getMinX();
      rangeAxis.draw(g2, cursor, adjustedPlotArea, dataArea, RectangleEdge.LEFT, info);
    }
    

    if (colorBar != null) {
      cursor = 0.0D;
      cursor = colorBar.draw(g2, cursor, adjustedPlotArea, dataArea, colorBarArea, colorBarLocation);
    }
    
    Shape originalClip = g2.getClip();
    Composite originalComposite = g2.getComposite();
    
    g2.clip(dataArea);
    g2.setComposite(AlphaComposite.getInstance(3, getForegroundAlpha()));
    
    render(g2, dataArea, info, crosshairState);
    
    if (domainMarkers != null) {
      Iterator iterator = domainMarkers.iterator();
      while (iterator.hasNext()) {
        Marker marker = (Marker)iterator.next();
        drawDomainMarker(g2, this, getDomainAxis(), marker, dataArea);
      }
    }
    
    if (rangeMarkers != null) {
      Iterator iterator = rangeMarkers.iterator();
      while (iterator.hasNext()) {
        Marker marker = (Marker)iterator.next();
        drawRangeMarker(g2, this, getRangeAxis(), marker, dataArea);
      }
    }
    

















    g2.setClip(originalClip);
    g2.setComposite(originalComposite);
    drawOutline(g2, dataArea);
  }
  
















  public void render(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, CrosshairState crosshairState)
  {
    ContourDataset data = getDataset();
    if (data != null)
    {
      ColorBar zAxis = getColorBar();
      
      if (clipPath != null) {
        GeneralPath clipper = getClipPath().draw(g2, dataArea, domainAxis, rangeAxis);
        
        if (clipPath.isClip()) {
          g2.clip(clipper);
        }
      }
      
      if (renderAsPoints) {
        pointRenderer(g2, dataArea, info, this, domainAxis, rangeAxis, zAxis, data, crosshairState);
      }
      else
      {
        contourRenderer(g2, dataArea, info, this, domainAxis, rangeAxis, zAxis, data, crosshairState);
      }
      


      setDomainCrosshairValue(crosshairState.getCrosshairX(), false);
      if (isDomainCrosshairVisible()) {
        drawVerticalLine(g2, dataArea, getDomainCrosshairValue(), getDomainCrosshairStroke(), getDomainCrosshairPaint());
      }
      




      setRangeCrosshairValue(crosshairState.getCrosshairY(), false);
      if (isRangeCrosshairVisible()) {
        drawHorizontalLine(g2, dataArea, getRangeCrosshairValue(), getRangeCrosshairStroke(), getRangeCrosshairPaint());

      }
      


    }
    else if (clipPath != null) {
      getClipPath().draw(g2, dataArea, domainAxis, rangeAxis);
    }
  }
  
























  public void contourRenderer(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, ContourPlot plot, ValueAxis horizontalAxis, ValueAxis verticalAxis, ColorBar colorBar, ContourDataset data, CrosshairState crosshairState)
  {
    Rectangle2D.Double entityArea = null;
    EntityCollection entities = null;
    if (info != null) {
      entities = info.getOwner().getEntityCollection();
    }
    
    Rectangle2D.Double rect = null;
    rect = new Rectangle2D.Double();
    

    Object antiAlias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    


    Number[] xNumber = data.getXValues();
    Number[] yNumber = data.getYValues();
    Number[] zNumber = data.getZValues();
    
    double[] x = new double[xNumber.length];
    double[] y = new double[yNumber.length];
    
    for (int i = 0; i < x.length; i++) {
      x[i] = xNumber[i].doubleValue();
      y[i] = yNumber[i].doubleValue();
    }
    
    int[] xIndex = data.indexX();
    int[] indexX = data.getXIndices();
    boolean vertInverted = ((NumberAxis)verticalAxis).isInverted();
    boolean horizInverted = false;
    if ((horizontalAxis instanceof NumberAxis)) {
      horizInverted = ((NumberAxis)horizontalAxis).isInverted();
    }
    double transX = 0.0D;
    double transXm1 = 0.0D;
    double transXp1 = 0.0D;
    double transDXm1 = 0.0D;
    double transDXp1 = 0.0D;
    double transDX = 0.0D;
    double transY = 0.0D;
    double transYm1 = 0.0D;
    double transYp1 = 0.0D;
    double transDYm1 = 0.0D;
    double transDYp1 = 0.0D;
    double transDY = 0.0D;
    int iMax = xIndex[(xIndex.length - 1)];
    for (int k = 0; k < x.length; k++) {
      int i = xIndex[k];
      if (indexX[i] == k) {
        if (i == 0) {
          transX = horizontalAxis.valueToJava2D(x[k], dataArea, RectangleEdge.BOTTOM);
          
          transXm1 = transX;
          transXp1 = horizontalAxis.valueToJava2D(x[indexX[(i + 1)]], dataArea, RectangleEdge.BOTTOM);
          
          transDXm1 = Math.abs(0.5D * (transX - transXm1));
          transDXp1 = Math.abs(0.5D * (transX - transXp1));
        }
        else if (i == iMax) {
          transX = horizontalAxis.valueToJava2D(x[k], dataArea, RectangleEdge.BOTTOM);
          
          transXm1 = horizontalAxis.valueToJava2D(x[indexX[(i - 1)]], dataArea, RectangleEdge.BOTTOM);
          
          transXp1 = transX;
          transDXm1 = Math.abs(0.5D * (transX - transXm1));
          transDXp1 = Math.abs(0.5D * (transX - transXp1));
        }
        else {
          transX = horizontalAxis.valueToJava2D(x[k], dataArea, RectangleEdge.BOTTOM);
          
          transXp1 = horizontalAxis.valueToJava2D(x[indexX[(i + 1)]], dataArea, RectangleEdge.BOTTOM);
          
          transDXm1 = transDXp1;
          transDXp1 = Math.abs(0.5D * (transX - transXp1));
        }
        
        if (horizInverted) {
          transX -= transDXp1;
        }
        else {
          transX -= transDXm1;
        }
        
        transDX = transDXm1 + transDXp1;
        
        transY = verticalAxis.valueToJava2D(y[k], dataArea, RectangleEdge.LEFT);
        
        transYm1 = transY;
        if (k + 1 == y.length) {
          continue;
        }
        transYp1 = verticalAxis.valueToJava2D(y[(k + 1)], dataArea, RectangleEdge.LEFT);
        
        transDYm1 = Math.abs(0.5D * (transY - transYm1));
        transDYp1 = Math.abs(0.5D * (transY - transYp1));
      }
      else if (((i < indexX.length - 1) && (indexX[(i + 1)] - 1 == k)) || (k == x.length - 1))
      {

        transY = verticalAxis.valueToJava2D(y[k], dataArea, RectangleEdge.LEFT);
        
        transYm1 = verticalAxis.valueToJava2D(y[(k - 1)], dataArea, RectangleEdge.LEFT);
        
        transYp1 = transY;
        transDYm1 = Math.abs(0.5D * (transY - transYm1));
        transDYp1 = Math.abs(0.5D * (transY - transYp1));
      }
      else {
        transY = verticalAxis.valueToJava2D(y[k], dataArea, RectangleEdge.LEFT);
        
        transYp1 = verticalAxis.valueToJava2D(y[(k + 1)], dataArea, RectangleEdge.LEFT);
        
        transDYm1 = transDYp1;
        transDYp1 = Math.abs(0.5D * (transY - transYp1));
      }
      if (vertInverted) {
        transY -= transDYm1;
      }
      else {
        transY -= transDYp1;
      }
      
      transDY = transDYm1 + transDYp1;
      
      rect.setRect(transX, transY, transDX, transDY);
      if (zNumber[k] != null) {
        g2.setPaint(colorBar.getPaint(zNumber[k].doubleValue()));
        g2.fill(rect);
      }
      else if (missingPaint != null) {
        g2.setPaint(missingPaint);
        g2.fill(rect);
      }
      
      entityArea = rect;
      

      if (entities != null) {
        String tip = "";
        if (getToolTipGenerator() != null) {
          tip = toolTipGenerator.generateToolTip(data, k);
        }
        

        String url = null;
        




        ContourEntity entity = new ContourEntity((Rectangle2D.Double)entityArea.clone(), tip, url);
        
        entity.setIndex(k);
        entities.add(entity);
      }
      


      if (plot.isDomainCrosshairLockedOnData()) {
        if (plot.isRangeCrosshairLockedOnData())
        {
          crosshairState.updateCrosshairPoint(x[k], y[k], transX, transY, PlotOrientation.VERTICAL);

        }
        else
        {
          crosshairState.updateCrosshairX(transX);
        }
        
      }
      else if (plot.isRangeCrosshairLockedOnData())
      {
        crosshairState.updateCrosshairY(transY);
      }
    }
    

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias);
  }
  


























  public void pointRenderer(Graphics2D g2, Rectangle2D dataArea, PlotRenderingInfo info, ContourPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, ColorBar colorBar, ContourDataset data, CrosshairState crosshairState)
  {
    RectangularShape entityArea = null;
    EntityCollection entities = null;
    if (info != null) {
      entities = info.getOwner().getEntityCollection();
    }
    


    RectangularShape rect = new Ellipse2D.Double();
    


    Object antiAlias = g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    



    Number[] xNumber = data.getXValues();
    Number[] yNumber = data.getYValues();
    Number[] zNumber = data.getZValues();
    
    double[] x = new double[xNumber.length];
    double[] y = new double[yNumber.length];
    
    for (int i = 0; i < x.length; i++) {
      x[i] = xNumber[i].doubleValue();
      y[i] = yNumber[i].doubleValue();
    }
    
    double transX = 0.0D;
    double transDX = 0.0D;
    double transY = 0.0D;
    double transDY = 0.0D;
    double size = dataArea.getWidth() * ptSizePct;
    for (int k = 0; k < x.length; k++)
    {
      transX = domainAxis.valueToJava2D(x[k], dataArea, RectangleEdge.BOTTOM) - 0.5D * size;
      
      transY = rangeAxis.valueToJava2D(y[k], dataArea, RectangleEdge.LEFT) - 0.5D * size;
      
      transDX = size;
      transDY = size;
      
      rect.setFrame(transX, transY, transDX, transDY);
      
      if (zNumber[k] != null) {
        g2.setPaint(colorBar.getPaint(zNumber[k].doubleValue()));
        g2.fill(rect);
      }
      else if (missingPaint != null) {
        g2.setPaint(missingPaint);
        g2.fill(rect);
      }
      

      entityArea = rect;
      

      if (entities != null) {
        String tip = null;
        if (getToolTipGenerator() != null) {
          tip = toolTipGenerator.generateToolTip(data, k);
        }
        String url = null;
        




        ContourEntity entity = new ContourEntity((RectangularShape)entityArea.clone(), tip, url);
        
        entity.setIndex(k);
        entities.add(entity);
      }
      

      if (plot.isDomainCrosshairLockedOnData()) {
        if (plot.isRangeCrosshairLockedOnData())
        {
          crosshairState.updateCrosshairPoint(x[k], y[k], transX, transY, PlotOrientation.VERTICAL);

        }
        else
        {
          crosshairState.updateCrosshairX(transX);
        }
        
      }
      else if (plot.isRangeCrosshairLockedOnData())
      {
        crosshairState.updateCrosshairY(transY);
      }
    }
    


    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias);
  }
  













  protected void drawVerticalLine(Graphics2D g2, Rectangle2D dataArea, double value, Stroke stroke, Paint paint)
  {
    double xx = getDomainAxis().valueToJava2D(value, dataArea, RectangleEdge.BOTTOM);
    
    Line2D line = new Line2D.Double(xx, dataArea.getMinY(), xx, dataArea.getMaxY());
    
    g2.setStroke(stroke);
    g2.setPaint(paint);
    g2.draw(line);
  }
  












  protected void drawHorizontalLine(Graphics2D g2, Rectangle2D dataArea, double value, Stroke stroke, Paint paint)
  {
    double yy = getRangeAxis().valueToJava2D(value, dataArea, RectangleEdge.LEFT);
    
    Line2D line = new Line2D.Double(dataArea.getMinX(), yy, dataArea.getMaxX(), yy);
    
    g2.setStroke(stroke);
    g2.setPaint(paint);
    g2.draw(line);
  }
  


















  public void handleClick(int x, int y, PlotRenderingInfo info) {}
  


















  public void zoom(double percent)
  {
    if (percent <= 0.0D)
    {








      getRangeAxis().setAutoRange(true);
      getDomainAxis().setAutoRange(true);
    }
  }
  





  public String getPlotType()
  {
    return localizationResources.getString("Contour_Plot");
  }
  







  public Range getDataRange(ValueAxis axis)
  {
    if (dataset == null) {
      return null;
    }
    
    Range result = null;
    
    if (axis == getDomainAxis()) {
      result = DatasetUtilities.findDomainBounds(dataset);
    }
    else if (axis == getRangeAxis()) {
      result = DatasetUtilities.findRangeBounds(dataset);
    }
    
    return result;
  }
  






  public Range getContourDataRange()
  {
    Range result = null;
    
    ContourDataset data = getDataset();
    
    if (data != null) {
      Range h = getDomainAxis().getRange();
      Range v = getRangeAxis().getRange();
      result = visibleRange(data, h, v);
    }
    
    return result;
  }
  






  public void propertyChange(PropertyChangeEvent event)
  {
    fireChangeEvent();
  }
  







  public void datasetChanged(DatasetChangeEvent event)
  {
    if (domainAxis != null) {
      domainAxis.configure();
    }
    if (rangeAxis != null) {
      rangeAxis.configure();
    }
    if (colorBar != null) {
      colorBar.configure(this);
    }
    super.datasetChanged(event);
  }
  




  public ColorBar getColorBar()
  {
    return colorBar;
  }
  




  public boolean isDomainCrosshairVisible()
  {
    return domainCrosshairVisible;
  }
  





  public void setDomainCrosshairVisible(boolean flag)
  {
    if (domainCrosshairVisible != flag) {
      domainCrosshairVisible = flag;
      fireChangeEvent();
    }
  }
  






  public boolean isDomainCrosshairLockedOnData()
  {
    return domainCrosshairLockedOnData;
  }
  





  public void setDomainCrosshairLockedOnData(boolean flag)
  {
    if (domainCrosshairLockedOnData != flag) {
      domainCrosshairLockedOnData = flag;
      fireChangeEvent();
    }
  }
  




  public double getDomainCrosshairValue()
  {
    return domainCrosshairValue;
  }
  







  public void setDomainCrosshairValue(double value)
  {
    setDomainCrosshairValue(value, true);
  }
  









  public void setDomainCrosshairValue(double value, boolean notify)
  {
    domainCrosshairValue = value;
    if ((isDomainCrosshairVisible()) && (notify)) {
      fireChangeEvent();
    }
  }
  




  public Stroke getDomainCrosshairStroke()
  {
    return domainCrosshairStroke;
  }
  





  public void setDomainCrosshairStroke(Stroke stroke)
  {
    domainCrosshairStroke = stroke;
    fireChangeEvent();
  }
  




  public Paint getDomainCrosshairPaint()
  {
    return domainCrosshairPaint;
  }
  





  public void setDomainCrosshairPaint(Paint paint)
  {
    domainCrosshairPaint = paint;
    fireChangeEvent();
  }
  




  public boolean isRangeCrosshairVisible()
  {
    return rangeCrosshairVisible;
  }
  




  public void setRangeCrosshairVisible(boolean flag)
  {
    if (rangeCrosshairVisible != flag) {
      rangeCrosshairVisible = flag;
      fireChangeEvent();
    }
  }
  





  public boolean isRangeCrosshairLockedOnData()
  {
    return rangeCrosshairLockedOnData;
  }
  





  public void setRangeCrosshairLockedOnData(boolean flag)
  {
    if (rangeCrosshairLockedOnData != flag) {
      rangeCrosshairLockedOnData = flag;
      fireChangeEvent();
    }
  }
  




  public double getRangeCrosshairValue()
  {
    return rangeCrosshairValue;
  }
  







  public void setRangeCrosshairValue(double value)
  {
    setRangeCrosshairValue(value, true);
  }
  









  public void setRangeCrosshairValue(double value, boolean notify)
  {
    rangeCrosshairValue = value;
    if ((isRangeCrosshairVisible()) && (notify)) {
      fireChangeEvent();
    }
  }
  




  public Stroke getRangeCrosshairStroke()
  {
    return rangeCrosshairStroke;
  }
  





  public void setRangeCrosshairStroke(Stroke stroke)
  {
    rangeCrosshairStroke = stroke;
    fireChangeEvent();
  }
  




  public Paint getRangeCrosshairPaint()
  {
    return rangeCrosshairPaint;
  }
  





  public void setRangeCrosshairPaint(Paint paint)
  {
    rangeCrosshairPaint = paint;
    fireChangeEvent();
  }
  




  public ContourToolTipGenerator getToolTipGenerator()
  {
    return toolTipGenerator;
  }
  





  public void setToolTipGenerator(ContourToolTipGenerator generator)
  {
    toolTipGenerator = generator;
  }
  




  public XYURLGenerator getURLGenerator()
  {
    return urlGenerator;
  }
  





  public void setURLGenerator(XYURLGenerator urlGenerator)
  {
    this.urlGenerator = urlGenerator;
  }
  













  public void drawDomainMarker(Graphics2D g2, ContourPlot plot, ValueAxis domainAxis, Marker marker, Rectangle2D dataArea)
  {
    if ((marker instanceof ValueMarker)) {
      ValueMarker vm = (ValueMarker)marker;
      double value = vm.getValue();
      Range range = domainAxis.getRange();
      if (!range.contains(value)) {
        return;
      }
      
      double x = domainAxis.valueToJava2D(value, dataArea, RectangleEdge.BOTTOM);
      
      Line2D line = new Line2D.Double(x, dataArea.getMinY(), x, dataArea.getMaxY());
      
      Paint paint = marker.getOutlinePaint();
      Stroke stroke = marker.getOutlineStroke();
      g2.setPaint(paint != null ? paint : Plot.DEFAULT_OUTLINE_PAINT);
      g2.setStroke(stroke != null ? stroke : Plot.DEFAULT_OUTLINE_STROKE);
      g2.draw(line);
    }
  }
  














  public void drawRangeMarker(Graphics2D g2, ContourPlot plot, ValueAxis rangeAxis, Marker marker, Rectangle2D dataArea)
  {
    if ((marker instanceof ValueMarker)) {
      ValueMarker vm = (ValueMarker)marker;
      double value = vm.getValue();
      Range range = rangeAxis.getRange();
      if (!range.contains(value)) {
        return;
      }
      
      double y = rangeAxis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
      
      Line2D line = new Line2D.Double(dataArea.getMinX(), y, dataArea.getMaxX(), y);
      
      Paint paint = marker.getOutlinePaint();
      Stroke stroke = marker.getOutlineStroke();
      g2.setPaint(paint != null ? paint : Plot.DEFAULT_OUTLINE_PAINT);
      g2.setStroke(stroke != null ? stroke : Plot.DEFAULT_OUTLINE_STROKE);
      g2.draw(line);
    }
  }
  




  public ClipPath getClipPath()
  {
    return clipPath;
  }
  



  public void setClipPath(ClipPath clipPath)
  {
    this.clipPath = clipPath;
  }
  



  public double getPtSizePct()
  {
    return ptSizePct;
  }
  



  public boolean isRenderAsPoints()
  {
    return renderAsPoints;
  }
  



  public void setPtSizePct(double ptSizePct)
  {
    this.ptSizePct = ptSizePct;
  }
  



  public void setRenderAsPoints(boolean renderAsPoints)
  {
    this.renderAsPoints = renderAsPoints;
  }
  




  public void axisChanged(AxisChangeEvent event)
  {
    Object source = event.getSource();
    if ((source.equals(rangeAxis)) || (source.equals(domainAxis))) {
      ColorBar cba = colorBar;
      if (colorBar.getAxis().isAutoRange()) {
        cba.getAxis().configure();
      }
    }
    
    super.axisChanged(event);
  }
  








  public Range visibleRange(ContourDataset data, Range x, Range y)
  {
    Range range = null;
    range = data.getZValueRange(x, y);
    return range;
  }
  



  public Paint getMissingPaint()
  {
    return missingPaint;
  }
  




  public void setMissingPaint(Paint paint)
  {
    missingPaint = paint;
  }
  









  public void zoomDomainAxes(double x, double y, double factor) {}
  









  public void zoomDomainAxes(double x, double y, double lowerPercent, double upperPercent) {}
  









  public void zoomRangeAxes(double x, double y, double factor) {}
  









  public void zoomRangeAxes(double x, double y, double lowerPercent, double upperPercent) {}
  









  public boolean isDomainZoomable()
  {
    return false;
  }
  




  public boolean isRangeZoomable()
  {
    return false;
  }
  


  public Object clone()
    throws CloneNotSupportedException
  {
    ContourPlot clone = (ContourPlot)super.clone();
    
    if (domainAxis != null) {
      domainAxis = ((ValueAxis)domainAxis.clone());
      domainAxis.setPlot(clone);
      domainAxis.addChangeListener(clone);
    }
    if (rangeAxis != null) {
      rangeAxis = ((ValueAxis)rangeAxis.clone());
      rangeAxis.setPlot(clone);
      rangeAxis.addChangeListener(clone);
    }
    
    if (dataset != null) {
      dataset.addChangeListener(clone);
    }
    
    if (colorBar != null) {
      colorBar = ((ColorBar)colorBar.clone());
    }
    
    domainMarkers = ((List)ObjectUtilities.deepClone(domainMarkers));
    
    rangeMarkers = ((List)ObjectUtilities.deepClone(rangeMarkers));
    
    annotations = ((List)ObjectUtilities.deepClone(annotations));
    
    if (clipPath != null) {
      clipPath = ((ClipPath)clipPath.clone());
    }
    
    return clone;
  }
}
