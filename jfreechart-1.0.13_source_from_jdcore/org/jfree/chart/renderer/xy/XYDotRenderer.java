package org.jfree.chart.renderer.xy;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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












































































public class XYDotRenderer
  extends AbstractXYItemRenderer
  implements XYItemRenderer, PublicCloneable
{
  private static final long serialVersionUID = -2764344339073566425L;
  private int dotWidth;
  private int dotHeight;
  private transient Shape legendShape;
  
  public XYDotRenderer()
  {
    dotWidth = 1;
    dotHeight = 1;
    legendShape = new Rectangle2D.Double(-3.0D, -3.0D, 6.0D, 6.0D);
  }
  







  public int getDotWidth()
  {
    return dotWidth;
  }
  










  public void setDotWidth(int w)
  {
    if (w < 1) {
      throw new IllegalArgumentException("Requires w > 0.");
    }
    dotWidth = w;
    fireChangeEvent();
  }
  







  public int getDotHeight()
  {
    return dotHeight;
  }
  










  public void setDotHeight(int h)
  {
    if (h < 1) {
      throw new IllegalArgumentException("Requires h > 0.");
    }
    dotHeight = h;
    fireChangeEvent();
  }
  








  public Shape getLegendShape()
  {
    return legendShape;
  }
  









  public void setLegendShape(Shape shape)
  {
    if (shape == null) {
      throw new IllegalArgumentException("Null 'shape' argument.");
    }
    legendShape = shape;
    fireChangeEvent();
  }
  






























  public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass)
  {
    if (!getItemVisible(series, item)) {
      return;
    }
    

    double x = dataset.getXValue(series, item);
    double y = dataset.getYValue(series, item);
    double adjx = (dotWidth - 1) / 2.0D;
    double adjy = (dotHeight - 1) / 2.0D;
    if (!Double.isNaN(y)) {
      RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
      RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
      double transX = domainAxis.valueToJava2D(x, dataArea, xAxisLocation) - adjx;
      
      double transY = rangeAxis.valueToJava2D(y, dataArea, yAxisLocation) - adjy;
      

      g2.setPaint(getItemPaint(series, item));
      PlotOrientation orientation = plot.getOrientation();
      if (orientation == PlotOrientation.HORIZONTAL) {
        g2.fillRect((int)transY, (int)transX, dotHeight, dotWidth);

      }
      else if (orientation == PlotOrientation.VERTICAL) {
        g2.fillRect((int)transX, (int)transY, dotWidth, dotHeight);
      }
      

      int domainAxisIndex = plot.getDomainAxisIndex(domainAxis);
      int rangeAxisIndex = plot.getRangeAxisIndex(rangeAxis);
      updateCrosshairValues(crosshairState, x, y, domainAxisIndex, rangeAxisIndex, transX, transY, orientation);
    }
  }
  












  public LegendItem getLegendItem(int datasetIndex, int series)
  {
    XYPlot plot = getPlot();
    if (plot == null) {
      return null;
    }
    
    XYDataset dataset = plot.getDataset(datasetIndex);
    if (dataset == null) {
      return null;
    }
    
    LegendItem result = null;
    if (getItemVisible(series, 0)) {
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
      
      Paint fillPaint = lookupSeriesPaint(series);
      result = new LegendItem(label, description, toolTipText, urlText, getLegendShape(), fillPaint);
      
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
  














  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof XYDotRenderer)) {
      return false;
    }
    XYDotRenderer that = (XYDotRenderer)obj;
    if (dotWidth != dotWidth) {
      return false;
    }
    if (dotHeight != dotHeight) {
      return false;
    }
    if (!ShapeUtilities.equal(legendShape, legendShape)) {
      return false;
    }
    return super.equals(obj);
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    legendShape = SerialUtilities.readShape(stream);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeShape(legendShape, stream);
  }
}
