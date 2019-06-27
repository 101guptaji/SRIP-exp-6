package org.jfree.chart.renderer.xy;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.HighLowItemLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;













































































































public class CandlestickRenderer
  extends AbstractXYItemRenderer
  implements XYItemRenderer, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 50390395841817121L;
  public static final int WIDTHMETHOD_AVERAGE = 0;
  public static final int WIDTHMETHOD_SMALLEST = 1;
  public static final int WIDTHMETHOD_INTERVALDATA = 2;
  private int autoWidthMethod = 0;
  





  private double autoWidthFactor = 0.6428571428571429D;
  

  private double autoWidthGap = 0.0D;
  

  private double candleWidth;
  

  private double maxCandleWidthInMilliseconds = 7.2E7D;
  




  private double maxCandleWidth;
  



  private transient Paint upPaint;
  



  private transient Paint downPaint;
  



  private boolean drawVolume;
  



  private transient Paint volumePaint;
  



  private transient double maxVolume;
  



  private boolean useOutlinePaint;
  




  public CandlestickRenderer()
  {
    this(-1.0D);
  }
  







  public CandlestickRenderer(double candleWidth)
  {
    this(candleWidth, true, new HighLowItemLabelGenerator());
  }
  













  public CandlestickRenderer(double candleWidth, boolean drawVolume, XYToolTipGenerator toolTipGenerator)
  {
    setBaseToolTipGenerator(toolTipGenerator);
    this.candleWidth = candleWidth;
    this.drawVolume = drawVolume;
    volumePaint = Color.gray;
    upPaint = Color.green;
    downPaint = Color.red;
    useOutlinePaint = false;
  }
  







  public double getCandleWidth()
  {
    return candleWidth;
  }
  












  public void setCandleWidth(double width)
  {
    if (width != candleWidth) {
      candleWidth = width;
      fireChangeEvent();
    }
  }
  






  public double getMaxCandleWidthInMilliseconds()
  {
    return maxCandleWidthInMilliseconds;
  }
  











  public void setMaxCandleWidthInMilliseconds(double millis)
  {
    maxCandleWidthInMilliseconds = millis;
    fireChangeEvent();
  }
  






  public int getAutoWidthMethod()
  {
    return autoWidthMethod;
  }
  

























  public void setAutoWidthMethod(int autoWidthMethod)
  {
    if (this.autoWidthMethod != autoWidthMethod) {
      this.autoWidthMethod = autoWidthMethod;
      fireChangeEvent();
    }
  }
  








  public double getAutoWidthFactor()
  {
    return autoWidthFactor;
  }
  











  public void setAutoWidthFactor(double autoWidthFactor)
  {
    if (this.autoWidthFactor != autoWidthFactor) {
      this.autoWidthFactor = autoWidthFactor;
      fireChangeEvent();
    }
  }
  







  public double getAutoWidthGap()
  {
    return autoWidthGap;
  }
  












  public void setAutoWidthGap(double autoWidthGap)
  {
    if (this.autoWidthGap != autoWidthGap) {
      this.autoWidthGap = autoWidthGap;
      fireChangeEvent();
    }
  }
  







  public Paint getUpPaint()
  {
    return upPaint;
  }
  








  public void setUpPaint(Paint paint)
  {
    upPaint = paint;
    fireChangeEvent();
  }
  







  public Paint getDownPaint()
  {
    return downPaint;
  }
  






  public void setDownPaint(Paint paint)
  {
    downPaint = paint;
    fireChangeEvent();
  }
  









  public boolean getDrawVolume()
  {
    return drawVolume;
  }
  








  public void setDrawVolume(boolean flag)
  {
    if (drawVolume != flag) {
      drawVolume = flag;
      fireChangeEvent();
    }
  }
  









  public Paint getVolumePaint()
  {
    return volumePaint;
  }
  










  public void setVolumePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    volumePaint = paint;
    fireChangeEvent();
  }
  










  public boolean getUseOutlinePaint()
  {
    return useOutlinePaint;
  }
  










  public void setUseOutlinePaint(boolean use)
  {
    if (useOutlinePaint != use) {
      useOutlinePaint = use;
      fireChangeEvent();
    }
  }
  








  public Range findRangeBounds(XYDataset dataset)
  {
    return findRangeBounds(dataset, true);
  }
  





















  public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset dataset, PlotRenderingInfo info)
  {
    ValueAxis axis = plot.getDomainAxis();
    double x1 = axis.getLowerBound();
    double x2 = x1 + maxCandleWidthInMilliseconds;
    RectangleEdge edge = plot.getDomainAxisEdge();
    double xx1 = axis.valueToJava2D(x1, dataArea, edge);
    double xx2 = axis.valueToJava2D(x2, dataArea, edge);
    maxCandleWidth = Math.abs(xx2 - xx1);
    



    if (drawVolume) {
      OHLCDataset highLowDataset = (OHLCDataset)dataset;
      maxVolume = 0.0D;
      for (int series = 0; series < highLowDataset.getSeriesCount(); 
          series++) {
        for (int item = 0; item < highLowDataset.getItemCount(series); 
            item++) {
          double volume = highLowDataset.getVolumeValue(series, item);
          if (volume > maxVolume) {
            maxVolume = volume;
          }
        }
      }
    }
    

    return new XYItemRendererState(info);
  }
  






























  public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass)
  {
    PlotOrientation orientation = plot.getOrientation();
    boolean horiz; if (orientation == PlotOrientation.HORIZONTAL) {
      horiz = true;
    } else { boolean horiz;
      if (orientation == PlotOrientation.VERTICAL) {
        horiz = false;
      } else {
        return;
      }
    }
    
    boolean horiz;
    EntityCollection entities = null;
    if (info != null) {
      entities = info.getOwner().getEntityCollection();
    }
    
    OHLCDataset highLowData = (OHLCDataset)dataset;
    
    double x = highLowData.getXValue(series, item);
    double yHigh = highLowData.getHighValue(series, item);
    double yLow = highLowData.getLowValue(series, item);
    double yOpen = highLowData.getOpenValue(series, item);
    double yClose = highLowData.getCloseValue(series, item);
    
    RectangleEdge domainEdge = plot.getDomainAxisEdge();
    double xx = domainAxis.valueToJava2D(x, dataArea, domainEdge);
    
    RectangleEdge edge = plot.getRangeAxisEdge();
    double yyHigh = rangeAxis.valueToJava2D(yHigh, dataArea, edge);
    double yyLow = rangeAxis.valueToJava2D(yLow, dataArea, edge);
    double yyOpen = rangeAxis.valueToJava2D(yOpen, dataArea, edge);
    double yyClose = rangeAxis.valueToJava2D(yClose, dataArea, edge);
    double stickWidth;
    double volumeWidth;
    double stickWidth;
    if (candleWidth > 0.0D)
    {

      double volumeWidth = candleWidth;
      stickWidth = candleWidth;
    }
    else {
      double xxWidth = 0.0D;
      int itemCount;
      switch (autoWidthMethod)
      {
      case 0: 
        itemCount = highLowData.getItemCount(series);
        if (horiz) {
          xxWidth = dataArea.getHeight() / itemCount;
        }
        else {
          xxWidth = dataArea.getWidth() / itemCount;
        }
        break;
      

      case 1: 
        itemCount = highLowData.getItemCount(series);
        double lastPos = -1.0D;
        xxWidth = dataArea.getWidth();
        for (int i = 0; i < itemCount; i++) {
          double pos = domainAxis.valueToJava2D(highLowData.getXValue(series, i), dataArea, domainEdge);
          

          if (lastPos != -1.0D) {
            xxWidth = Math.min(xxWidth, Math.abs(pos - lastPos));
          }
          
          lastPos = pos;
        }
        break;
      
      case 2: 
        IntervalXYDataset intervalXYData = (IntervalXYDataset)dataset;
        
        double startPos = domainAxis.valueToJava2D(intervalXYData.getStartXValue(series, item), dataArea, plot.getDomainAxisEdge());
        

        double endPos = domainAxis.valueToJava2D(intervalXYData.getEndXValue(series, item), dataArea, plot.getDomainAxisEdge());
        

        xxWidth = Math.abs(endPos - startPos);
      }
      
      
      xxWidth -= 2.0D * autoWidthGap;
      xxWidth *= autoWidthFactor;
      xxWidth = Math.min(xxWidth, maxCandleWidth);
      volumeWidth = Math.max(Math.min(1.0D, maxCandleWidth), xxWidth);
      stickWidth = Math.max(Math.min(3.0D, maxCandleWidth), xxWidth);
    }
    
    Paint p = getItemPaint(series, item);
    Paint outlinePaint = null;
    if (useOutlinePaint) {
      outlinePaint = getItemOutlinePaint(series, item);
    }
    Stroke s = getItemStroke(series, item);
    
    g2.setStroke(s);
    
    if (drawVolume) {
      int volume = (int)highLowData.getVolumeValue(series, item);
      double volumeHeight = volume / maxVolume;
      double max;
      double min;
      double max; if (horiz) {
        double min = dataArea.getMinX();
        max = dataArea.getMaxX();
      }
      else {
        min = dataArea.getMinY();
        max = dataArea.getMaxY();
      }
      
      double zzVolume = volumeHeight * (max - min);
      
      g2.setPaint(getVolumePaint());
      Composite originalComposite = g2.getComposite();
      g2.setComposite(AlphaComposite.getInstance(3, 0.3F));
      

      if (horiz) {
        g2.fill(new Rectangle2D.Double(min, xx - volumeWidth / 2.0D, zzVolume, volumeWidth));
      }
      else
      {
        g2.fill(new Rectangle2D.Double(xx - volumeWidth / 2.0D, max - zzVolume, volumeWidth, zzVolume));
      }
      

      g2.setComposite(originalComposite);
    }
    
    if (useOutlinePaint) {
      g2.setPaint(outlinePaint);
    }
    else {
      g2.setPaint(p);
    }
    
    double yyMaxOpenClose = Math.max(yyOpen, yyClose);
    double yyMinOpenClose = Math.min(yyOpen, yyClose);
    double maxOpenClose = Math.max(yOpen, yClose);
    double minOpenClose = Math.min(yOpen, yClose);
    

    if (yHigh > maxOpenClose) {
      if (horiz) {
        g2.draw(new Line2D.Double(yyHigh, xx, yyMaxOpenClose, xx));
      }
      else {
        g2.draw(new Line2D.Double(xx, yyHigh, xx, yyMaxOpenClose));
      }
    }
    

    if (yLow < minOpenClose) {
      if (horiz) {
        g2.draw(new Line2D.Double(yyLow, xx, yyMinOpenClose, xx));
      }
      else {
        g2.draw(new Line2D.Double(xx, yyLow, xx, yyMinOpenClose));
      }
    }
    

    Rectangle2D body = null;
    Rectangle2D hotspot = null;
    double length = Math.abs(yyHigh - yyLow);
    double base = Math.min(yyHigh, yyLow);
    if (horiz) {
      body = new Rectangle2D.Double(yyMinOpenClose, xx - stickWidth / 2.0D, yyMaxOpenClose - yyMinOpenClose, stickWidth);
      
      hotspot = new Rectangle2D.Double(base, xx - stickWidth / 2.0D, length, stickWidth);
    }
    else
    {
      body = new Rectangle2D.Double(xx - stickWidth / 2.0D, yyMinOpenClose, stickWidth, yyMaxOpenClose - yyMinOpenClose);
      
      hotspot = new Rectangle2D.Double(xx - stickWidth / 2.0D, base, stickWidth, length);
    }
    
    if (yClose > yOpen) {
      if (upPaint != null) {
        g2.setPaint(upPaint);
      }
      else {
        g2.setPaint(p);
      }
      g2.fill(body);
    }
    else {
      if (downPaint != null) {
        g2.setPaint(downPaint);
      }
      else {
        g2.setPaint(p);
      }
      g2.fill(body);
    }
    if (useOutlinePaint) {
      g2.setPaint(outlinePaint);
    }
    else {
      g2.setPaint(p);
    }
    g2.draw(body);
    

    if (entities != null) {
      addEntity(entities, hotspot, dataset, series, item, 0.0D, 0.0D);
    }
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof CandlestickRenderer)) {
      return false;
    }
    CandlestickRenderer that = (CandlestickRenderer)obj;
    if (candleWidth != candleWidth) {
      return false;
    }
    if (!PaintUtilities.equal(upPaint, upPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(downPaint, downPaint)) {
      return false;
    }
    if (drawVolume != drawVolume) {
      return false;
    }
    if (maxCandleWidthInMilliseconds != maxCandleWidthInMilliseconds)
    {
      return false;
    }
    if (autoWidthMethod != autoWidthMethod) {
      return false;
    }
    if (autoWidthFactor != autoWidthFactor) {
      return false;
    }
    if (autoWidthGap != autoWidthGap) {
      return false;
    }
    if (useOutlinePaint != useOutlinePaint) {
      return false;
    }
    if (!PaintUtilities.equal(volumePaint, volumePaint)) {
      return false;
    }
    return super.equals(obj);
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(upPaint, stream);
    SerialUtilities.writePaint(downPaint, stream);
    SerialUtilities.writePaint(volumePaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    upPaint = SerialUtilities.readPaint(stream);
    downPaint = SerialUtilities.readPaint(stream);
    volumePaint = SerialUtilities.readPaint(stream);
  }
  







  /**
   * @deprecated
   */
  public boolean drawVolume()
  {
    return drawVolume;
  }
}
