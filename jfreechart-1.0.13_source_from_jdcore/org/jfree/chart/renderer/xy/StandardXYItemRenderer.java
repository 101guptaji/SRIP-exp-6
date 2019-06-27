package org.jfree.chart.renderer.xy;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.xy.XYDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.BooleanList;
import org.jfree.util.BooleanUtilities;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;
import org.jfree.util.UnitType;










































































































































public class StandardXYItemRenderer
  extends AbstractXYItemRenderer
  implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -3271351259436865995L;
  public static final int SHAPES = 1;
  public static final int LINES = 2;
  public static final int SHAPES_AND_LINES = 3;
  public static final int IMAGES = 4;
  public static final int DISCONTINUOUS = 8;
  public static final int DISCONTINUOUS_LINES = 10;
  private boolean baseShapesVisible;
  private boolean plotLines;
  private boolean plotImages;
  private boolean plotDiscontinuous;
  private UnitType gapThresholdType = UnitType.RELATIVE;
  

  private double gapThreshold = 1.0D;
  



  /**
   * @deprecated
   */
  private Boolean shapesFilled;
  



  private BooleanList seriesShapesFilled;
  



  private boolean baseShapesFilled;
  



  private boolean drawSeriesLineAsPath;
  


  private transient Shape legendLine;
  



  public StandardXYItemRenderer()
  {
    this(2, null);
  }
  






  public StandardXYItemRenderer(int type)
  {
    this(type, null);
  }
  









  public StandardXYItemRenderer(int type, XYToolTipGenerator toolTipGenerator)
  {
    this(type, toolTipGenerator, null);
  }
  













  public StandardXYItemRenderer(int type, XYToolTipGenerator toolTipGenerator, XYURLGenerator urlGenerator)
  {
    setBaseToolTipGenerator(toolTipGenerator);
    setURLGenerator(urlGenerator);
    if ((type & 0x1) != 0) {
      baseShapesVisible = true;
    }
    if ((type & 0x2) != 0) {
      plotLines = true;
    }
    if ((type & 0x4) != 0) {
      plotImages = true;
    }
    if ((type & 0x8) != 0) {
      plotDiscontinuous = true;
    }
    
    shapesFilled = null;
    seriesShapesFilled = new BooleanList();
    baseShapesFilled = true;
    legendLine = new Line2D.Double(-7.0D, 0.0D, 7.0D, 0.0D);
    drawSeriesLineAsPath = false;
  }
  






  public boolean getBaseShapesVisible()
  {
    return baseShapesVisible;
  }
  







  public void setBaseShapesVisible(boolean flag)
  {
    if (baseShapesVisible != flag) {
      baseShapesVisible = flag;
      fireChangeEvent();
    }
  }
  

















  public boolean getItemShapeFilled(int series, int item)
  {
    if (shapesFilled != null) {
      return shapesFilled.booleanValue();
    }
    

    Boolean flag = seriesShapesFilled.getBoolean(series);
    if (flag != null) {
      return flag.booleanValue();
    }
    
    return baseShapesFilled;
  }
  









  /**
   * @deprecated
   */
  public Boolean getShapesFilled()
  {
    return shapesFilled;
  }
  











  /**
   * @deprecated
   */
  public void setShapesFilled(boolean filled)
  {
    setShapesFilled(BooleanUtilities.valueOf(filled));
  }
  










  /**
   * @deprecated
   */
  public void setShapesFilled(Boolean filled)
  {
    shapesFilled = filled;
    fireChangeEvent();
  }
  







  public Boolean getSeriesShapesFilled(int series)
  {
    return seriesShapesFilled.getBoolean(series);
  }
  








  public void setSeriesShapesFilled(int series, Boolean flag)
  {
    seriesShapesFilled.setBoolean(series, flag);
    fireChangeEvent();
  }
  






  public boolean getBaseShapesFilled()
  {
    return baseShapesFilled;
  }
  







  public void setBaseShapesFilled(boolean flag)
  {
    baseShapesFilled = flag;
  }
  






  public boolean getPlotLines()
  {
    return plotLines;
  }
  








  public void setPlotLines(boolean flag)
  {
    if (plotLines != flag) {
      plotLines = flag;
      fireChangeEvent();
    }
  }
  






  public UnitType getGapThresholdType()
  {
    return gapThresholdType;
  }
  







  public void setGapThresholdType(UnitType thresholdType)
  {
    if (thresholdType == null) {
      throw new IllegalArgumentException("Null 'thresholdType' argument.");
    }
    
    gapThresholdType = thresholdType;
    fireChangeEvent();
  }
  






  public double getGapThreshold()
  {
    return gapThreshold;
  }
  







  public void setGapThreshold(double t)
  {
    gapThreshold = t;
    fireChangeEvent();
  }
  






  public boolean getPlotImages()
  {
    return plotImages;
  }
  








  public void setPlotImages(boolean flag)
  {
    if (plotImages != flag) {
      plotImages = flag;
      fireChangeEvent();
    }
  }
  





  public boolean getPlotDiscontinuous()
  {
    return plotDiscontinuous;
  }
  








  public void setPlotDiscontinuous(boolean flag)
  {
    if (plotDiscontinuous != flag) {
      plotDiscontinuous = flag;
      fireChangeEvent();
    }
  }
  







  public boolean getDrawSeriesLineAsPath()
  {
    return drawSeriesLineAsPath;
  }
  







  public void setDrawSeriesLineAsPath(boolean flag)
  {
    drawSeriesLineAsPath = flag;
  }
  






  public Shape getLegendLine()
  {
    return legendLine;
  }
  







  public void setLegendLine(Shape line)
  {
    if (line == null) {
      throw new IllegalArgumentException("Null 'line' argument.");
    }
    legendLine = line;
    fireChangeEvent();
  }
  







  public LegendItem getLegendItem(int datasetIndex, int series)
  {
    XYPlot plot = getPlot();
    if (plot == null) {
      return null;
    }
    LegendItem result = null;
    XYDataset dataset = plot.getDataset(datasetIndex);
    if ((dataset != null) && 
      (getItemVisible(series, 0))) {
      String label = getLegendItemLabelGenerator().generateLabel(dataset, series);
      
      String description = label;
      String toolTipText = null;
      if (getLegendItemToolTipGenerator() != null) {
        toolTipText = getLegendItemToolTipGenerator().generateLabel(dataset, series);
      }
      
      String urlText = null;
      if (getLegendItemURLGenerator() != null) {
        urlText = getLegendItemURLGenerator().generateLabel(dataset, series);
      }
      
      Shape shape = lookupLegendShape(series);
      boolean shapeFilled = getItemShapeFilled(series, 0);
      Paint paint = lookupSeriesPaint(series);
      Paint linePaint = paint;
      Stroke lineStroke = lookupSeriesStroke(series);
      result = new LegendItem(label, description, toolTipText, urlText, baseShapesVisible, shape, shapeFilled, paint, !shapeFilled, paint, lineStroke, plotLines, legendLine, lineStroke, linePaint);
      


      result.setLabelFont(lookupLegendTextFont(series));
      Paint labelPaint = lookupLegendTextPaint(series);
      if (labelPaint != null) {
        result.setLabelPaint(labelPaint);
      }
      result.setDataset(dataset);
      result.setDatasetIndex(datasetIndex);
      result.setSeriesKey(dataset.getSeriesKey(series));
      result.setSeriesIndex(series);
    }
    
    return result;
  }
  




  public static class State
    extends XYItemRendererState
  {
    public GeneralPath seriesPath;
    



    private int seriesIndex;
    



    private boolean lastPointGood;
    




    public State(PlotRenderingInfo info)
    {
      super();
    }
    





    public boolean isLastPointGood()
    {
      return lastPointGood;
    }
    





    public void setLastPointGood(boolean good)
    {
      lastPointGood = good;
    }
    




    public int getSeriesIndex()
    {
      return seriesIndex;
    }
    




    public void setSeriesIndex(int index)
    {
      seriesIndex = index;
    }
  }
  




















  public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info)
  {
    State state = new State(info);
    seriesPath = new GeneralPath();
    seriesIndex = -1;
    return state;
  }
  






























  public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass)
  {
    boolean itemVisible = getItemVisible(series, item);
    

    Shape entityArea = null;
    EntityCollection entities = null;
    if (info != null) {
      entities = info.getOwner().getEntityCollection();
    }
    
    PlotOrientation orientation = plot.getOrientation();
    Paint paint = getItemPaint(series, item);
    Stroke seriesStroke = getItemStroke(series, item);
    g2.setPaint(paint);
    g2.setStroke(seriesStroke);
    

    double x1 = dataset.getXValue(series, item);
    double y1 = dataset.getYValue(series, item);
    if ((Double.isNaN(x1)) || (Double.isNaN(y1))) {
      itemVisible = false;
    }
    
    RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
    RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
    double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
    double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);
    
    if (getPlotLines()) {
      if (drawSeriesLineAsPath) {
        State s = (State)state;
        if (s.getSeriesIndex() != series)
        {
          seriesPath.reset();
          lastPointGood = false;
          s.setSeriesIndex(series);
        }
        

        if ((itemVisible) && (!Double.isNaN(transX1)) && (!Double.isNaN(transY1)))
        {
          float x = (float)transX1;
          float y = (float)transY1;
          if (orientation == PlotOrientation.HORIZONTAL) {
            x = (float)transY1;
            y = (float)transX1;
          }
          if (s.isLastPointGood())
          {
            seriesPath.lineTo(x, y);
          }
          else {
            seriesPath.moveTo(x, y);
          }
          s.setLastPointGood(true);
        }
        else {
          s.setLastPointGood(false);
        }
        if ((item == dataset.getItemCount(series) - 1) && 
          (seriesIndex == series))
        {
          g2.setStroke(lookupSeriesStroke(series));
          g2.setPaint(lookupSeriesPaint(series));
          g2.draw(seriesPath);
        }
        

      }
      else if ((item != 0) && (itemVisible))
      {
        double x0 = dataset.getXValue(series, item - 1);
        double y0 = dataset.getYValue(series, item - 1);
        if ((!Double.isNaN(x0)) && (!Double.isNaN(y0))) {
          boolean drawLine = true;
          if (getPlotDiscontinuous())
          {

            int numX = dataset.getItemCount(series);
            double minX = dataset.getXValue(series, 0);
            double maxX = dataset.getXValue(series, numX - 1);
            if (gapThresholdType == UnitType.ABSOLUTE) {
              drawLine = Math.abs(x1 - x0) <= gapThreshold;
            }
            else {
              drawLine = Math.abs(x1 - x0) <= (maxX - minX) / numX * getGapThreshold();
            }
          }
          
          if (drawLine) {
            double transX0 = domainAxis.valueToJava2D(x0, dataArea, xAxisLocation);
            
            double transY0 = rangeAxis.valueToJava2D(y0, dataArea, yAxisLocation);
            


            if ((Double.isNaN(transX0)) || (Double.isNaN(transY0)) || (Double.isNaN(transX1)) || (Double.isNaN(transY1)))
            {
              return;
            }
            
            if (orientation == PlotOrientation.HORIZONTAL) {
              workingLine.setLine(transY0, transX0, transY1, transX1);

            }
            else if (orientation == PlotOrientation.VERTICAL) {
              workingLine.setLine(transX0, transY0, transX1, transY1);
            }
            

            if (workingLine.intersects(dataArea)) {
              g2.draw(workingLine);
            }
          }
        }
      }
    }
    



    if (!itemVisible) {
      return;
    }
    
    if (getBaseShapesVisible())
    {
      Shape shape = getItemShape(series, item);
      if (orientation == PlotOrientation.HORIZONTAL) {
        shape = ShapeUtilities.createTranslatedShape(shape, transY1, transX1);

      }
      else if (orientation == PlotOrientation.VERTICAL) {
        shape = ShapeUtilities.createTranslatedShape(shape, transX1, transY1);
      }
      
      if (shape.intersects(dataArea)) {
        if (getItemShapeFilled(series, item)) {
          g2.fill(shape);
        }
        else {
          g2.draw(shape);
        }
      }
      entityArea = shape;
    }
    

    if (getPlotImages()) {
      Image image = getImage(plot, series, item, transX1, transY1);
      if (image != null) {
        Point hotspot = getImageHotspot(plot, series, item, transX1, transY1, image);
        
        g2.drawImage(image, (int)(transX1 - hotspot.getX()), (int)(transY1 - hotspot.getY()), null);
        
        entityArea = new Rectangle2D.Double(transX1 - hotspot.getX(), transY1 - hotspot.getY(), image.getWidth(null), image.getHeight(null));
      }
    }
    



    double xx = transX1;
    double yy = transY1;
    if (orientation == PlotOrientation.HORIZONTAL) {
      xx = transY1;
      yy = transX1;
    }
    

    if (isItemLabelVisible(series, item)) {
      drawItemLabel(g2, orientation, dataset, series, item, xx, yy, y1 < 0.0D);
    }
    

    int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
    int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
    updateCrosshairValues(crosshairState, x1, y1, domainAxisIndex, rangeAxisIndex, transX1, transY1, orientation);
    


    if ((entities != null) && (isPointInRect(dataArea, xx, yy))) {
      addEntity(entities, entityArea, dataset, series, item, xx, yy);
    }
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StandardXYItemRenderer)) {
      return false;
    }
    StandardXYItemRenderer that = (StandardXYItemRenderer)obj;
    if (baseShapesVisible != baseShapesVisible) {
      return false;
    }
    if (plotLines != plotLines) {
      return false;
    }
    if (plotImages != plotImages) {
      return false;
    }
    if (plotDiscontinuous != plotDiscontinuous) {
      return false;
    }
    if (gapThresholdType != gapThresholdType) {
      return false;
    }
    if (gapThreshold != gapThreshold) {
      return false;
    }
    if (!ObjectUtilities.equal(shapesFilled, shapesFilled)) {
      return false;
    }
    if (!seriesShapesFilled.equals(seriesShapesFilled)) {
      return false;
    }
    if (baseShapesFilled != baseShapesFilled) {
      return false;
    }
    if (drawSeriesLineAsPath != drawSeriesLineAsPath) {
      return false;
    }
    if (!ShapeUtilities.equal(legendLine, legendLine)) {
      return false;
    }
    return super.equals(obj);
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    StandardXYItemRenderer clone = (StandardXYItemRenderer)super.clone();
    seriesShapesFilled = ((BooleanList)seriesShapesFilled.clone());
    
    legendLine = ShapeUtilities.clone(legendLine);
    return clone;
  }
  





















  protected Image getImage(Plot plot, int series, int item, double x, double y)
  {
    return null;
  }
  


















  protected Point getImageHotspot(Plot plot, int series, int item, double x, double y, Image image)
  {
    int height = image.getHeight(null);
    int width = image.getWidth(null);
    return new Point(width / 2, height / 2);
  }
  








  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    legendLine = SerialUtilities.readShape(stream);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeShape(legendLine, stream);
  }
}
