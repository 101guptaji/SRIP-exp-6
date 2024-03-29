package org.jfree.chart.renderer.xy;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;






































































public class DeviationRenderer
  extends XYLineAndShapeRenderer
{
  private float alpha;
  
  public static class State
    extends XYLineAndShapeRenderer.State
  {
    public List upperCoordinates;
    public List lowerCoordinates;
    
    public State(PlotRenderingInfo info)
    {
      super();
      lowerCoordinates = new ArrayList();
      upperCoordinates = new ArrayList();
    }
  }
  







  public DeviationRenderer()
  {
    this(true, true);
  }
  





  public DeviationRenderer(boolean lines, boolean shapes)
  {
    super(lines, shapes);
    super.setDrawSeriesLineAsPath(true);
    alpha = 0.5F;
  }
  






  public float getAlpha()
  {
    return alpha;
  }
  







  public void setAlpha(float alpha)
  {
    if ((alpha < 0.0F) || (alpha > 1.0F)) {
      throw new IllegalArgumentException("Requires 'alpha' in the range 0.0 to 1.0.");
    }
    
    this.alpha = alpha;
    fireChangeEvent();
  }
  








  public void setDrawSeriesLineAsPath(boolean flag) {}
  








  public Range findRangeBounds(XYDataset dataset)
  {
    return findRangeBounds(dataset, true);
  }
  












  public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, XYPlot plot, XYDataset dataset, PlotRenderingInfo info)
  {
    State state = new State(info);
    seriesPath = new GeneralPath();
    state.setProcessVisibleItemsOnly(false);
    return state;
  }
  





  public int getPassCount()
  {
    return 3;
  }
  









  protected boolean isItemPass(int pass)
  {
    return pass == 2;
  }
  









  protected boolean isLinePass(int pass)
  {
    return pass == 1;
  }
  






























  public void drawItem(Graphics2D g2, XYItemRendererState state, Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot, ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset, int series, int item, CrosshairState crosshairState, int pass)
  {
    if (!getItemVisible(series, item)) {
      return;
    }
    

    if (pass == 0) {
      IntervalXYDataset intervalDataset = (IntervalXYDataset)dataset;
      State drState = (State)state;
      
      double x = intervalDataset.getXValue(series, item);
      double yLow = intervalDataset.getStartYValue(series, item);
      double yHigh = intervalDataset.getEndYValue(series, item);
      
      RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
      RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
      
      double xx = domainAxis.valueToJava2D(x, dataArea, xAxisLocation);
      double yyLow = rangeAxis.valueToJava2D(yLow, dataArea, yAxisLocation);
      
      double yyHigh = rangeAxis.valueToJava2D(yHigh, dataArea, yAxisLocation);
      

      PlotOrientation orientation = plot.getOrientation();
      if (orientation == PlotOrientation.HORIZONTAL) {
        lowerCoordinates.add(new double[] { yyLow, xx });
        upperCoordinates.add(new double[] { yyHigh, xx });
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        lowerCoordinates.add(new double[] { xx, yyLow });
        upperCoordinates.add(new double[] { xx, yyHigh });
      }
      
      if (item == dataset.getItemCount(series) - 1)
      {

        Composite originalComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(3, alpha));
        
        g2.setPaint(getItemFillPaint(series, item));
        GeneralPath area = new GeneralPath();
        double[] coords = (double[])lowerCoordinates.get(0);
        area.moveTo((float)coords[0], (float)coords[1]);
        for (int i = 1; i < lowerCoordinates.size(); i++) {
          coords = (double[])lowerCoordinates.get(i);
          area.lineTo((float)coords[0], (float)coords[1]);
        }
        int count = upperCoordinates.size();
        coords = (double[])upperCoordinates.get(count - 1);
        area.lineTo((float)coords[0], (float)coords[1]);
        for (int i = count - 2; i >= 0; i--) {
          coords = (double[])upperCoordinates.get(i);
          area.lineTo((float)coords[0], (float)coords[1]);
        }
        area.closePath();
        g2.fill(area);
        g2.setComposite(originalComposite);
        
        lowerCoordinates.clear();
        upperCoordinates.clear();
      }
    }
    if (isLinePass(pass))
    {


      if (item == 0) {
        State s = (State)state;
        seriesPath.reset();
        s.setLastPointGood(false);
      }
      
      if (getItemLineVisible(series, item)) {
        drawPrimaryLineAsPath(state, g2, plot, dataset, pass, series, item, domainAxis, rangeAxis, dataArea);

      }
      

    }
    else if (isItemPass(pass))
    {

      EntityCollection entities = null;
      if (info != null) {
        entities = info.getOwner().getEntityCollection();
      }
      
      drawSecondaryPass(g2, plot, dataset, pass, series, item, domainAxis, dataArea, rangeAxis, crosshairState, entities);
    }
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DeviationRenderer)) {
      return false;
    }
    DeviationRenderer that = (DeviationRenderer)obj;
    if (alpha != alpha) {
      return false;
    }
    return super.equals(obj);
  }
}
