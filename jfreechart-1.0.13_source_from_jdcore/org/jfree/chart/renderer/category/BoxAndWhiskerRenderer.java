package org.jfree.chart.renderer.category;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategorySeriesLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.renderer.Outlier;
import org.jfree.chart.renderer.OutlierList;
import org.jfree.chart.renderer.OutlierListCollection;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;

















































































































public class BoxAndWhiskerRenderer
  extends AbstractCategoryItemRenderer
  implements Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 632027470694481177L;
  private transient Paint artifactPaint;
  private boolean fillBox;
  private double itemMargin;
  private double maximumBarWidth;
  private boolean medianVisible;
  private boolean meanVisible;
  
  public BoxAndWhiskerRenderer()
  {
    artifactPaint = Color.black;
    fillBox = true;
    itemMargin = 0.2D;
    maximumBarWidth = 1.0D;
    medianVisible = true;
    meanVisible = true;
    setBaseLegendShape(new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D));
  }
  







  public Paint getArtifactPaint()
  {
    return artifactPaint;
  }
  







  public void setArtifactPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    artifactPaint = paint;
    fireChangeEvent();
  }
  






  public boolean getFillBox()
  {
    return fillBox;
  }
  







  public void setFillBox(boolean flag)
  {
    fillBox = flag;
    fireChangeEvent();
  }
  







  public double getItemMargin()
  {
    return itemMargin;
  }
  







  public void setItemMargin(double margin)
  {
    itemMargin = margin;
    fireChangeEvent();
  }
  









  public double getMaximumBarWidth()
  {
    return maximumBarWidth;
  }
  










  public void setMaximumBarWidth(double percent)
  {
    maximumBarWidth = percent;
    fireChangeEvent();
  }
  









  public boolean isMeanVisible()
  {
    return meanVisible;
  }
  










  public void setMeanVisible(boolean visible)
  {
    if (meanVisible == visible) {
      return;
    }
    meanVisible = visible;
    fireChangeEvent();
  }
  









  public boolean isMedianVisible()
  {
    return medianVisible;
  }
  










  public void setMedianVisible(boolean visible)
  {
    medianVisible = visible;
  }
  








  public LegendItem getLegendItem(int datasetIndex, int series)
  {
    CategoryPlot cp = getPlot();
    if (cp == null) {
      return null;
    }
    

    if ((!isSeriesVisible(series)) || (!isSeriesVisibleInLegend(series))) {
      return null;
    }
    
    CategoryDataset dataset = cp.getDataset(datasetIndex);
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
    Paint paint = lookupSeriesPaint(series);
    Paint outlinePaint = lookupSeriesOutlinePaint(series);
    Stroke outlineStroke = lookupSeriesOutlineStroke(series);
    LegendItem result = new LegendItem(label, description, toolTipText, urlText, shape, paint, outlineStroke, outlinePaint);
    
    result.setLabelFont(lookupLegendTextFont(series));
    Paint labelPaint = lookupLegendTextPaint(series);
    if (labelPaint != null) {
      result.setLabelPaint(labelPaint);
    }
    result.setDataset(dataset);
    result.setDatasetIndex(datasetIndex);
    result.setSeriesKey(dataset.getRowKey(series));
    result.setSeriesIndex(series);
    return result;
  }
  








  public Range findRangeBounds(CategoryDataset dataset)
  {
    return super.findRangeBounds(dataset, true);
  }
  
















  public CategoryItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea, CategoryPlot plot, int rendererIndex, PlotRenderingInfo info)
  {
    CategoryItemRendererState state = super.initialise(g2, dataArea, plot, rendererIndex, info);
    

    CategoryAxis domainAxis = getDomainAxis(plot, rendererIndex);
    CategoryDataset dataset = plot.getDataset(rendererIndex);
    if (dataset != null) {
      int columns = dataset.getColumnCount();
      int rows = dataset.getRowCount();
      double space = 0.0D;
      PlotOrientation orientation = plot.getOrientation();
      if (orientation == PlotOrientation.HORIZONTAL) {
        space = dataArea.getHeight();
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        space = dataArea.getWidth();
      }
      double maxWidth = space * getMaximumBarWidth();
      double categoryMargin = 0.0D;
      double currentItemMargin = 0.0D;
      if (columns > 1) {
        categoryMargin = domainAxis.getCategoryMargin();
      }
      if (rows > 1) {
        currentItemMargin = getItemMargin();
      }
      double used = space * (1.0D - domainAxis.getLowerMargin() - domainAxis.getUpperMargin() - categoryMargin - currentItemMargin);
      

      if (rows * columns > 0) {
        state.setBarWidth(Math.min(used / (dataset.getColumnCount() * dataset.getRowCount()), maxWidth));
      }
      else
      {
        state.setBarWidth(Math.min(used, maxWidth));
      }
    }
    return state;
  }
  


























  public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column, int pass)
  {
    if (!getItemVisible(row, column)) {
      return;
    }
    
    if (!(dataset instanceof BoxAndWhiskerCategoryDataset)) {
      throw new IllegalArgumentException("BoxAndWhiskerRenderer.drawItem() : the data should be of type BoxAndWhiskerCategoryDataset only.");
    }
    


    PlotOrientation orientation = plot.getOrientation();
    
    if (orientation == PlotOrientation.HORIZONTAL) {
      drawHorizontalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column);

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      drawVerticalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, dataset, row, column);
    }
  }
  


























  public void drawHorizontalItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column)
  {
    BoxAndWhiskerCategoryDataset bawDataset = (BoxAndWhiskerCategoryDataset)dataset;
    

    double categoryEnd = domainAxis.getCategoryEnd(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
    
    double categoryStart = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
    
    double categoryWidth = Math.abs(categoryEnd - categoryStart);
    
    double yy = categoryStart;
    int seriesCount = getRowCount();
    int categoryCount = getColumnCount();
    
    if (seriesCount > 1) {
      double seriesGap = dataArea.getHeight() * getItemMargin() / (categoryCount * (seriesCount - 1));
      
      double usedWidth = state.getBarWidth() * seriesCount + seriesGap * (seriesCount - 1);
      


      double offset = (categoryWidth - usedWidth) / 2.0D;
      yy = yy + offset + row * (state.getBarWidth() + seriesGap);

    }
    else
    {
      double offset = (categoryWidth - state.getBarWidth()) / 2.0D;
      yy += offset;
    }
    
    g2.setPaint(getItemPaint(row, column));
    Stroke s = getItemStroke(row, column);
    g2.setStroke(s);
    
    RectangleEdge location = plot.getRangeAxisEdge();
    
    Number xQ1 = bawDataset.getQ1Value(row, column);
    Number xQ3 = bawDataset.getQ3Value(row, column);
    Number xMax = bawDataset.getMaxRegularValue(row, column);
    Number xMin = bawDataset.getMinRegularValue(row, column);
    
    Shape box = null;
    if ((xQ1 != null) && (xQ3 != null) && (xMax != null) && (xMin != null))
    {
      double xxQ1 = rangeAxis.valueToJava2D(xQ1.doubleValue(), dataArea, location);
      
      double xxQ3 = rangeAxis.valueToJava2D(xQ3.doubleValue(), dataArea, location);
      
      double xxMax = rangeAxis.valueToJava2D(xMax.doubleValue(), dataArea, location);
      
      double xxMin = rangeAxis.valueToJava2D(xMin.doubleValue(), dataArea, location);
      
      double yymid = yy + state.getBarWidth() / 2.0D;
      

      g2.draw(new Line2D.Double(xxMax, yymid, xxQ3, yymid));
      g2.draw(new Line2D.Double(xxMax, yy, xxMax, yy + state.getBarWidth()));
      


      g2.draw(new Line2D.Double(xxMin, yymid, xxQ1, yymid));
      g2.draw(new Line2D.Double(xxMin, yy, xxMin, yy + state.getBarWidth()));
      


      box = new Rectangle2D.Double(Math.min(xxQ1, xxQ3), yy, Math.abs(xxQ1 - xxQ3), state.getBarWidth());
      
      if (fillBox) {
        g2.fill(box);
      }
      g2.setStroke(getItemOutlineStroke(row, column));
      g2.setPaint(getItemOutlinePaint(row, column));
      g2.draw(box);
    }
    

    g2.setPaint(artifactPaint);
    double aRadius = 0.0D;
    if (meanVisible) {
      Number xMean = bawDataset.getMeanValue(row, column);
      if (xMean != null) {
        double xxMean = rangeAxis.valueToJava2D(xMean.doubleValue(), dataArea, location);
        
        aRadius = state.getBarWidth() / 4.0D;
        

        if ((xxMean > dataArea.getMinX() - aRadius) && (xxMean < dataArea.getMaxX() + aRadius))
        {
          Ellipse2D.Double avgEllipse = new Ellipse2D.Double(xxMean - aRadius, yy + aRadius, aRadius * 2.0D, aRadius * 2.0D);
          
          g2.fill(avgEllipse);
          g2.draw(avgEllipse);
        }
      }
    }
    

    if (medianVisible) {
      Number xMedian = bawDataset.getMedianValue(row, column);
      if (xMedian != null) {
        double xxMedian = rangeAxis.valueToJava2D(xMedian.doubleValue(), dataArea, location);
        
        g2.draw(new Line2D.Double(xxMedian, yy, xxMedian, yy + state.getBarWidth()));
      }
    }
    


    if ((state.getInfo() != null) && (box != null)) {
      EntityCollection entities = state.getEntityCollection();
      if (entities != null) {
        addItemEntity(entities, dataset, row, column, box);
      }
    }
  }
  

























  public void drawVerticalItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row, int column)
  {
    BoxAndWhiskerCategoryDataset bawDataset = (BoxAndWhiskerCategoryDataset)dataset;
    

    double categoryEnd = domainAxis.getCategoryEnd(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
    
    double categoryStart = domainAxis.getCategoryStart(column, getColumnCount(), dataArea, plot.getDomainAxisEdge());
    
    double categoryWidth = categoryEnd - categoryStart;
    
    double xx = categoryStart;
    int seriesCount = getRowCount();
    int categoryCount = getColumnCount();
    
    if (seriesCount > 1) {
      double seriesGap = dataArea.getWidth() * getItemMargin() / (categoryCount * (seriesCount - 1));
      
      double usedWidth = state.getBarWidth() * seriesCount + seriesGap * (seriesCount - 1);
      


      double offset = (categoryWidth - usedWidth) / 2.0D;
      xx = xx + offset + row * (state.getBarWidth() + seriesGap);

    }
    else
    {
      double offset = (categoryWidth - state.getBarWidth()) / 2.0D;
      xx += offset;
    }
    
    double yyAverage = 0.0D;
    

    Paint itemPaint = getItemPaint(row, column);
    g2.setPaint(itemPaint);
    Stroke s = getItemStroke(row, column);
    g2.setStroke(s);
    
    double aRadius = 0.0D;
    
    RectangleEdge location = plot.getRangeAxisEdge();
    
    Number yQ1 = bawDataset.getQ1Value(row, column);
    Number yQ3 = bawDataset.getQ3Value(row, column);
    Number yMax = bawDataset.getMaxRegularValue(row, column);
    Number yMin = bawDataset.getMinRegularValue(row, column);
    Shape box = null;
    if ((yQ1 != null) && (yQ3 != null) && (yMax != null) && (yMin != null))
    {
      double yyQ1 = rangeAxis.valueToJava2D(yQ1.doubleValue(), dataArea, location);
      
      double yyQ3 = rangeAxis.valueToJava2D(yQ3.doubleValue(), dataArea, location);
      
      double yyMax = rangeAxis.valueToJava2D(yMax.doubleValue(), dataArea, location);
      
      double yyMin = rangeAxis.valueToJava2D(yMin.doubleValue(), dataArea, location);
      
      double xxmid = xx + state.getBarWidth() / 2.0D;
      

      g2.draw(new Line2D.Double(xxmid, yyMax, xxmid, yyQ3));
      g2.draw(new Line2D.Double(xx, yyMax, xx + state.getBarWidth(), yyMax));
      


      g2.draw(new Line2D.Double(xxmid, yyMin, xxmid, yyQ1));
      g2.draw(new Line2D.Double(xx, yyMin, xx + state.getBarWidth(), yyMin));
      


      box = new Rectangle2D.Double(xx, Math.min(yyQ1, yyQ3), state.getBarWidth(), Math.abs(yyQ1 - yyQ3));
      
      if (fillBox) {
        g2.fill(box);
      }
      g2.setStroke(getItemOutlineStroke(row, column));
      g2.setPaint(getItemOutlinePaint(row, column));
      g2.draw(box);
    }
    
    g2.setPaint(artifactPaint);
    

    if (meanVisible) {
      Number yMean = bawDataset.getMeanValue(row, column);
      if (yMean != null) {
        yyAverage = rangeAxis.valueToJava2D(yMean.doubleValue(), dataArea, location);
        
        aRadius = state.getBarWidth() / 4.0D;
        

        if ((yyAverage > dataArea.getMinY() - aRadius) && (yyAverage < dataArea.getMaxY() + aRadius))
        {
          Ellipse2D.Double avgEllipse = new Ellipse2D.Double(xx + aRadius, yyAverage - aRadius, aRadius * 2.0D, aRadius * 2.0D);
          

          g2.fill(avgEllipse);
          g2.draw(avgEllipse);
        }
      }
    }
    

    if (medianVisible) {
      Number yMedian = bawDataset.getMedianValue(row, column);
      if (yMedian != null) {
        double yyMedian = rangeAxis.valueToJava2D(yMedian.doubleValue(), dataArea, location);
        
        g2.draw(new Line2D.Double(xx, yyMedian, xx + state.getBarWidth(), yyMedian));
      }
    }
    


    double maxAxisValue = rangeAxis.valueToJava2D(rangeAxis.getUpperBound(), dataArea, location) + aRadius;
    
    double minAxisValue = rangeAxis.valueToJava2D(rangeAxis.getLowerBound(), dataArea, location) - aRadius;
    

    g2.setPaint(itemPaint);
    

    double oRadius = state.getBarWidth() / 3.0D;
    List outliers = new ArrayList();
    OutlierListCollection outlierListCollection = new OutlierListCollection();
    




    List yOutliers = bawDataset.getOutliers(row, column);
    if (yOutliers != null) {
      for (int i = 0; i < yOutliers.size(); i++) {
        double outlier = ((Number)yOutliers.get(i)).doubleValue();
        Number minOutlier = bawDataset.getMinOutlier(row, column);
        Number maxOutlier = bawDataset.getMaxOutlier(row, column);
        Number minRegular = bawDataset.getMinRegularValue(row, column);
        Number maxRegular = bawDataset.getMaxRegularValue(row, column);
        if (outlier > maxOutlier.doubleValue()) {
          outlierListCollection.setHighFarOut(true);
        }
        else if (outlier < minOutlier.doubleValue()) {
          outlierListCollection.setLowFarOut(true);
        }
        else if (outlier > maxRegular.doubleValue()) {
          double yyOutlier = rangeAxis.valueToJava2D(outlier, dataArea, location);
          
          outliers.add(new Outlier(xx + state.getBarWidth() / 2.0D, yyOutlier, oRadius));

        }
        else if (outlier < minRegular.doubleValue()) {
          double yyOutlier = rangeAxis.valueToJava2D(outlier, dataArea, location);
          
          outliers.add(new Outlier(xx + state.getBarWidth() / 2.0D, yyOutlier, oRadius));
        }
        
        Collections.sort(outliers);
      }
      


      for (Iterator iterator = outliers.iterator(); iterator.hasNext();) {
        Outlier outlier = (Outlier)iterator.next();
        outlierListCollection.add(outlier);
      }
      
      Iterator iterator = outlierListCollection.iterator();
      while (iterator.hasNext()) {
        OutlierList list = (OutlierList)iterator.next();
        Outlier outlier = list.getAveragedOutlier();
        Point2D point = outlier.getPoint();
        
        if (list.isMultiple()) {
          drawMultipleEllipse(point, state.getBarWidth(), oRadius, g2);
        }
        else
        {
          drawEllipse(point, oRadius, g2);
        }
      }
      

      if (outlierListCollection.isHighFarOut()) {
        drawHighFarOut(aRadius / 2.0D, g2, xx + state.getBarWidth() / 2.0D, maxAxisValue);
      }
      

      if (outlierListCollection.isLowFarOut()) {
        drawLowFarOut(aRadius / 2.0D, g2, xx + state.getBarWidth() / 2.0D, minAxisValue);
      }
    }
    

    if ((state.getInfo() != null) && (box != null)) {
      EntityCollection entities = state.getEntityCollection();
      if (entities != null) {
        addItemEntity(entities, dataset, row, column, box);
      }
    }
  }
  







  private void drawEllipse(Point2D point, double oRadius, Graphics2D g2)
  {
    Ellipse2D dot = new Ellipse2D.Double(point.getX() + oRadius / 2.0D, point.getY(), oRadius, oRadius);
    
    g2.draw(dot);
  }
  









  private void drawMultipleEllipse(Point2D point, double boxWidth, double oRadius, Graphics2D g2)
  {
    Ellipse2D dot1 = new Ellipse2D.Double(point.getX() - boxWidth / 2.0D + oRadius, point.getY(), oRadius, oRadius);
    
    Ellipse2D dot2 = new Ellipse2D.Double(point.getX() + boxWidth / 2.0D, point.getY(), oRadius, oRadius);
    
    g2.draw(dot1);
    g2.draw(dot2);
  }
  








  private void drawHighFarOut(double aRadius, Graphics2D g2, double xx, double m)
  {
    double side = aRadius * 2.0D;
    g2.draw(new Line2D.Double(xx - side, m + side, xx + side, m + side));
    g2.draw(new Line2D.Double(xx - side, m + side, xx, m));
    g2.draw(new Line2D.Double(xx + side, m + side, xx, m));
  }
  








  private void drawLowFarOut(double aRadius, Graphics2D g2, double xx, double m)
  {
    double side = aRadius * 2.0D;
    g2.draw(new Line2D.Double(xx - side, m - side, xx + side, m - side));
    g2.draw(new Line2D.Double(xx - side, m - side, xx, m));
    g2.draw(new Line2D.Double(xx + side, m - side, xx, m));
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof BoxAndWhiskerRenderer)) {
      return false;
    }
    BoxAndWhiskerRenderer that = (BoxAndWhiskerRenderer)obj;
    if (fillBox != fillBox) {
      return false;
    }
    if (itemMargin != itemMargin) {
      return false;
    }
    if (maximumBarWidth != maximumBarWidth) {
      return false;
    }
    if (meanVisible != meanVisible) {
      return false;
    }
    if (medianVisible != medianVisible) {
      return false;
    }
    if (!PaintUtilities.equal(artifactPaint, artifactPaint)) {
      return false;
    }
    return super.equals(obj);
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(artifactPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    artifactPaint = SerialUtilities.readPaint(stream);
  }
}
