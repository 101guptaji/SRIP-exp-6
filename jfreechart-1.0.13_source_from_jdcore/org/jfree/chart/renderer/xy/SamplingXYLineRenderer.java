package org.jfree.chart.renderer.xy;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D.Double;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;
















































public class SamplingXYLineRenderer
  extends AbstractXYItemRenderer
  implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
{
  private transient Shape legendLine;
  
  public SamplingXYLineRenderer()
  {
    legendLine = new Line2D.Double(-7.0D, 0.0D, 7.0D, 0.0D);
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
  






  public int getPassCount()
  {
    return 1;
  }
  





  public static class State
    extends XYItemRendererState
  {
    GeneralPath seriesPath;
    




    GeneralPath intervalPath;
    




    double dX = 1.0D;
    

    double lastX;
    

    double openY = 0.0D;
    

    double highY = 0.0D;
    

    double lowY = 0.0D;
    

    double closeY = 0.0D;
    




    boolean lastPointGood;
    




    public State(PlotRenderingInfo info)
    {
      super();
    }
    











    public void startSeriesPass(XYDataset dataset, int series, int firstItem, int lastItem, int pass, int passCount)
    {
      seriesPath.reset();
      lastPointGood = false;
      super.startSeriesPass(dataset, series, firstItem, lastItem, pass, passCount);
    }
  }
  




















  public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset data, PlotRenderingInfo info)
  {
    double dpi = 72.0D;
    



    State state = new State(info);
    seriesPath = new GeneralPath();
    intervalPath = new GeneralPath();
    dX = (72.0D / dpi);
    return state;
  }
  






























  public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass)
  {
    if (!getItemVisible(series, item)) {
      return;
    }
    RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
    RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
    

    double x1 = dataset.getXValue(series, item);
    double y1 = dataset.getYValue(series, item);
    double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
    double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);
    
    State s = (State)state;
    
    if ((!Double.isNaN(transX1)) && (!Double.isNaN(transY1))) {
      float x = (float)transX1;
      float y = (float)transY1;
      PlotOrientation orientation = plot.getOrientation();
      if (orientation == PlotOrientation.HORIZONTAL) {
        x = (float)transY1;
        y = (float)transX1;
      }
      if (lastPointGood) {
        if (Math.abs(x - lastX) > dX) {
          seriesPath.lineTo(x, y);
          if (lowY < highY) {
            intervalPath.moveTo((float)lastX, (float)lowY);
            intervalPath.lineTo((float)lastX, (float)highY);
          }
          lastX = x;
          openY = y;
          highY = y;
          lowY = y;
          closeY = y;
        }
        else {
          highY = Math.max(highY, y);
          lowY = Math.min(lowY, y);
          closeY = y;
        }
      }
      else {
        seriesPath.moveTo(x, y);
        lastX = x;
        openY = y;
        highY = y;
        lowY = y;
        closeY = y;
      }
      lastPointGood = true;
    }
    else {
      lastPointGood = false;
    }
    
    if (item == s.getLastItemIndex())
    {
      PathIterator pi = seriesPath.getPathIterator(null);
      int count = 0;
      while (!pi.isDone()) {
        count++;
        pi.next();
      }
      g2.setStroke(getItemStroke(series, item));
      g2.setPaint(getItemPaint(series, item));
      g2.draw(seriesPath);
      g2.draw(intervalPath);
    }
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
      
      result = new LegendItem(label);
      result.setLabelFont(lookupLegendTextFont(series));
      Paint labelPaint = lookupLegendTextPaint(series);
      if (labelPaint != null) {
        result.setLabelPaint(labelPaint);
      }
      result.setSeriesKey(dataset.getSeriesKey(series));
      result.setSeriesIndex(series);
      result.setDataset(dataset);
      result.setDatasetIndex(datasetIndex);
    }
    
    return result;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    SamplingXYLineRenderer clone = (SamplingXYLineRenderer)super.clone();
    if (legendLine != null) {
      legendLine = ShapeUtilities.clone(legendLine);
    }
    return clone;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof SamplingXYLineRenderer)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    SamplingXYLineRenderer that = (SamplingXYLineRenderer)obj;
    if (!ShapeUtilities.equal(legendLine, legendLine)) {
      return false;
    }
    return true;
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
