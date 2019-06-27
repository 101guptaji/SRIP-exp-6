package org.jfree.chart.renderer.xy;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;



















































































public class XYStepAreaRenderer
  extends AbstractXYItemRenderer
  implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -7311560779702649635L;
  public static final int SHAPES = 1;
  public static final int AREA = 2;
  public static final int AREA_AND_SHAPES = 3;
  private boolean shapesVisible;
  private boolean shapesFilled;
  private boolean plotArea;
  private boolean showOutline;
  protected transient Polygon pArea = null;
  



  private double rangeBase;
  



  public XYStepAreaRenderer()
  {
    this(2);
  }
  




  public XYStepAreaRenderer(int type)
  {
    this(type, null, null);
  }
  














  public XYStepAreaRenderer(int type, XYToolTipGenerator toolTipGenerator, XYURLGenerator urlGenerator)
  {
    setBaseToolTipGenerator(toolTipGenerator);
    setURLGenerator(urlGenerator);
    
    if (type == 2) {
      plotArea = true;
    }
    else if (type == 1) {
      shapesVisible = true;
    }
    else if (type == 3) {
      plotArea = true;
      shapesVisible = true;
    }
    showOutline = false;
  }
  







  public boolean isOutline()
  {
    return showOutline;
  }
  








  public void setOutline(boolean show)
  {
    showOutline = show;
    fireChangeEvent();
  }
  






  public boolean getShapesVisible()
  {
    return shapesVisible;
  }
  








  public void setShapesVisible(boolean flag)
  {
    shapesVisible = flag;
    fireChangeEvent();
  }
  






  public boolean isShapesFilled()
  {
    return shapesFilled;
  }
  







  public void setShapesFilled(boolean filled)
  {
    shapesFilled = filled;
    fireChangeEvent();
  }
  






  public boolean getPlotArea()
  {
    return plotArea;
  }
  








  public void setPlotArea(boolean flag)
  {
    plotArea = flag;
    fireChangeEvent();
  }
  








  public double getRangeBase()
  {
    return rangeBase;
  }
  










  public void setRangeBase(double val)
  {
    rangeBase = val;
    fireChangeEvent();
  }
  


















  public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info)
  {
    XYItemRendererState state = super.initialise(g2, dataArea, plot, data, info);
    


    state.setProcessVisibleItemsOnly(false);
    return state;
  }
  































  public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass)
  {
    PlotOrientation orientation = plot.getOrientation();
    


    int itemCount = dataset.getItemCount(series);
    
    Paint paint = getItemPaint(series, item);
    Stroke seriesStroke = getItemStroke(series, item);
    g2.setPaint(paint);
    g2.setStroke(seriesStroke);
    

    double x1 = dataset.getXValue(series, item);
    double y1 = dataset.getYValue(series, item);
    double x = x1;
    double y = Double.isNaN(y1) ? getRangeBase() : y1;
    double transX1 = domainAxis.valueToJava2D(x, dataArea, plot.getDomainAxisEdge());
    
    double transY1 = rangeAxis.valueToJava2D(y, dataArea, plot.getRangeAxisEdge());
    


    transY1 = restrictValueToDataArea(transY1, plot, dataArea);
    
    if ((pArea == null) && (!Double.isNaN(y1)))
    {

      pArea = new Polygon();
      

      double transY2 = rangeAxis.valueToJava2D(getRangeBase(), dataArea, plot.getRangeAxisEdge());
      


      transY2 = restrictValueToDataArea(transY2, plot, dataArea);
      

      if (orientation == PlotOrientation.VERTICAL) {
        pArea.addPoint((int)transX1, (int)transY2);
      }
      else if (orientation == PlotOrientation.HORIZONTAL) {
        pArea.addPoint((int)transY2, (int)transX1);
      }
    }
    
    double transX0 = 0.0D;
    double transY0 = restrictValueToDataArea(getRangeBase(), plot, dataArea);
    



    if (item > 0)
    {
      double x0 = dataset.getXValue(series, item - 1);
      double y0 = Double.isNaN(y1) ? y1 : dataset.getYValue(series, item - 1);
      
      x = x0;
      y = Double.isNaN(y0) ? getRangeBase() : y0;
      transX0 = domainAxis.valueToJava2D(x, dataArea, plot.getDomainAxisEdge());
      
      transY0 = rangeAxis.valueToJava2D(y, dataArea, plot.getRangeAxisEdge());
      


      transY0 = restrictValueToDataArea(transY0, plot, dataArea);
      
      if (Double.isNaN(y1))
      {

        transX1 = transX0;
        transY0 = transY1;
      }
      if (transY0 != transY1)
      {
        if (orientation == PlotOrientation.VERTICAL) {
          pArea.addPoint((int)transX1, (int)transY0);
        }
        else if (orientation == PlotOrientation.HORIZONTAL) {
          pArea.addPoint((int)transY0, (int)transX1);
        }
      }
    }
    
    Shape shape = null;
    if (!Double.isNaN(y1))
    {
      if (orientation == PlotOrientation.VERTICAL) {
        pArea.addPoint((int)transX1, (int)transY1);
      }
      else if (orientation == PlotOrientation.HORIZONTAL) {
        pArea.addPoint((int)transY1, (int)transX1);
      }
      
      if (getShapesVisible()) {
        shape = getItemShape(series, item);
        if (orientation == PlotOrientation.VERTICAL) {
          shape = ShapeUtilities.createTranslatedShape(shape, transX1, transY1);

        }
        else if (orientation == PlotOrientation.HORIZONTAL) {
          shape = ShapeUtilities.createTranslatedShape(shape, transY1, transX1);
        }
        
        if (isShapesFilled()) {
          g2.fill(shape);
        }
        else {
          g2.draw(shape);
        }
        
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        shape = new Rectangle2D.Double(transX1 - 2.0D, transY1 - 2.0D, 4.0D, 4.0D);

      }
      else if (orientation == PlotOrientation.HORIZONTAL) {
        shape = new Rectangle2D.Double(transY1 - 2.0D, transX1 - 2.0D, 4.0D, 4.0D);
      }
    }
    





    if ((getPlotArea()) && (item > 0) && (pArea != null) && ((item == itemCount - 1) || (Double.isNaN(y1))))
    {

      double transY2 = rangeAxis.valueToJava2D(getRangeBase(), dataArea, plot.getRangeAxisEdge());
      


      transY2 = restrictValueToDataArea(transY2, plot, dataArea);
      
      if (orientation == PlotOrientation.VERTICAL)
      {
        pArea.addPoint((int)transX1, (int)transY2);
      }
      else if (orientation == PlotOrientation.HORIZONTAL)
      {
        pArea.addPoint((int)transY2, (int)transX1);
      }
      

      g2.fill(pArea);
      

      if (isOutline()) {
        g2.setStroke(plot.getOutlineStroke());
        g2.setPaint(plot.getOutlinePaint());
        g2.draw(pArea);
      }
      

      pArea = null;
    }
    

    if (!Double.isNaN(y1)) {
      int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
      int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
      updateCrosshairValues(crosshairState, x1, y1, domainAxisIndex, rangeAxisIndex, transX1, transY1, orientation);
    }
    


    EntityCollection entities = state.getEntityCollection();
    if (entities != null) {
      addEntity(entities, shape, dataset, series, item, transX1, transY1);
    }
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYStepAreaRenderer)) {
      return false;
    }
    XYStepAreaRenderer that = (XYStepAreaRenderer)obj;
    if (showOutline != showOutline) {
      return false;
    }
    if (shapesVisible != shapesVisible) {
      return false;
    }
    if (shapesFilled != shapesFilled) {
      return false;
    }
    if (plotArea != plotArea) {
      return false;
    }
    if (rangeBase != rangeBase) {
      return false;
    }
    return super.equals(obj);
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  
















  protected static double restrictValueToDataArea(double value, XYPlot plot, Rectangle2D dataArea)
  {
    double min = 0.0D;
    double max = 0.0D;
    if (plot.getOrientation() == PlotOrientation.VERTICAL) {
      min = dataArea.getMinY();
      max = dataArea.getMaxY();
    }
    else if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
      min = dataArea.getMinX();
      max = dataArea.getMaxX();
    }
    if (value < min) {
      value = min;
    }
    else if (value > max) {
      value = max;
    }
    return value;
  }
}
