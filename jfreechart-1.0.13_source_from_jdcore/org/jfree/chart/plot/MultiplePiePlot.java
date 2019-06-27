package org.jfree.chart.plot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.CategoryToPieDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.PieDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.ShapeUtilities;
import org.jfree.util.TableOrder;





































































public class MultiplePiePlot
  extends Plot
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -355377800470807389L;
  private JFreeChart pieChart;
  private CategoryDataset dataset;
  private TableOrder dataExtractOrder;
  private double limit = 0.0D;
  





  private Comparable aggregatedItemsKey;
  




  private transient Paint aggregatedItemsPaint;
  




  private transient Map sectionPaints;
  




  private transient Shape legendItemShape;
  





  public MultiplePiePlot()
  {
    this(null);
  }
  





  public MultiplePiePlot(CategoryDataset dataset)
  {
    setDataset(dataset);
    PiePlot piePlot = new PiePlot(null);
    piePlot.setIgnoreNullValues(true);
    pieChart = new JFreeChart(piePlot);
    pieChart.removeLegend();
    dataExtractOrder = TableOrder.BY_COLUMN;
    pieChart.setBackgroundPaint(null);
    TextTitle seriesTitle = new TextTitle("Series Title", new Font("SansSerif", 1, 12));
    
    seriesTitle.setPosition(RectangleEdge.BOTTOM);
    pieChart.setTitle(seriesTitle);
    aggregatedItemsKey = "Other";
    aggregatedItemsPaint = Color.lightGray;
    sectionPaints = new HashMap();
    legendItemShape = new Ellipse2D.Double(-4.0D, -4.0D, 8.0D, 8.0D);
  }
  




  public CategoryDataset getDataset()
  {
    return dataset;
  }
  







  public void setDataset(CategoryDataset dataset)
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
  








  public JFreeChart getPieChart()
  {
    return pieChart;
  }
  







  public void setPieChart(JFreeChart pieChart)
  {
    if (pieChart == null) {
      throw new IllegalArgumentException("Null 'pieChart' argument.");
    }
    if (!(pieChart.getPlot() instanceof PiePlot)) {
      throw new IllegalArgumentException("The 'pieChart' argument must be a chart based on a PiePlot.");
    }
    
    this.pieChart = pieChart;
    fireChangeEvent();
  }
  




  public TableOrder getDataExtractOrder()
  {
    return dataExtractOrder;
  }
  





  public void setDataExtractOrder(TableOrder order)
  {
    if (order == null) {
      throw new IllegalArgumentException("Null 'order' argument");
    }
    dataExtractOrder = order;
    fireChangeEvent();
  }
  





  public double getLimit()
  {
    return limit;
  }
  





  public void setLimit(double limit)
  {
    this.limit = limit;
    fireChangeEvent();
  }
  







  public Comparable getAggregatedItemsKey()
  {
    return aggregatedItemsKey;
  }
  







  public void setAggregatedItemsKey(Comparable key)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    aggregatedItemsKey = key;
    fireChangeEvent();
  }
  







  public Paint getAggregatedItemsPaint()
  {
    return aggregatedItemsPaint;
  }
  







  public void setAggregatedItemsPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    aggregatedItemsPaint = paint;
    fireChangeEvent();
  }
  




  public String getPlotType()
  {
    return "Multiple Pie Plot";
  }
  









  public Shape getLegendItemShape()
  {
    return legendItemShape;
  }
  









  public void setLegendItemShape(Shape shape)
  {
    if (shape == null) {
      throw new IllegalArgumentException("Null 'shape' argument.");
    }
    legendItemShape = shape;
    fireChangeEvent();
  }
  
















  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info)
  {
    RectangleInsets insets = getInsets();
    insets.trim(area);
    drawBackground(g2, area);
    drawOutline(g2, area);
    

    if (DatasetUtilities.isEmptyOrNull(dataset)) {
      drawNoDataMessage(g2, area);
      return;
    }
    
    int pieCount = 0;
    if (dataExtractOrder == TableOrder.BY_ROW) {
      pieCount = dataset.getRowCount();
    }
    else {
      pieCount = dataset.getColumnCount();
    }
    

    int displayCols = (int)Math.ceil(Math.sqrt(pieCount));
    int displayRows = (int)Math.ceil(pieCount / displayCols);
    


    if ((displayCols > displayRows) && (area.getWidth() < area.getHeight())) {
      int temp = displayCols;
      displayCols = displayRows;
      displayRows = temp;
    }
    
    prefetchSectionPaints();
    
    int x = (int)area.getX();
    int y = (int)area.getY();
    int width = (int)area.getWidth() / displayCols;
    int height = (int)area.getHeight() / displayRows;
    int row = 0;
    int column = 0;
    int diff = displayRows * displayCols - pieCount;
    int xoffset = 0;
    Rectangle rect = new Rectangle();
    
    for (int pieIndex = 0; pieIndex < pieCount; pieIndex++) {
      rect.setBounds(x + xoffset + width * column, y + height * row, width, height);
      

      String title = null;
      if (dataExtractOrder == TableOrder.BY_ROW) {
        title = dataset.getRowKey(pieIndex).toString();
      }
      else {
        title = dataset.getColumnKey(pieIndex).toString();
      }
      pieChart.setTitle(title);
      
      PieDataset piedataset = null;
      PieDataset dd = new CategoryToPieDataset(dataset, dataExtractOrder, pieIndex);
      
      if (limit > 0.0D) {
        piedataset = DatasetUtilities.createConsolidatedPieDataset(dd, aggregatedItemsKey, limit);
      }
      else
      {
        piedataset = dd;
      }
      PiePlot piePlot = (PiePlot)pieChart.getPlot();
      piePlot.setDataset(piedataset);
      piePlot.setPieIndex(pieIndex);
      

      for (int i = 0; i < piedataset.getItemCount(); i++) {
        Comparable key = piedataset.getKey(i);
        Paint p;
        Paint p; if (key.equals(aggregatedItemsKey)) {
          p = aggregatedItemsPaint;
        }
        else {
          p = (Paint)sectionPaints.get(key);
        }
        piePlot.setSectionPaint(key, p);
      }
      
      ChartRenderingInfo subinfo = null;
      if (info != null) {
        subinfo = new ChartRenderingInfo();
      }
      pieChart.draw(g2, rect, subinfo);
      if (info != null) {
        info.getOwner().getEntityCollection().addAll(subinfo.getEntityCollection());
        
        info.addSubplotInfo(subinfo.getPlotInfo());
      }
      
      column++;
      if (column == displayCols) {
        column = 0;
        row++;
        
        if ((row == displayRows - 1) && (diff != 0)) {
          xoffset = diff * width / 2;
        }
      }
    }
  }
  











  private void prefetchSectionPaints()
  {
    PiePlot piePlot = (PiePlot)getPieChart().getPlot();
    
    if (dataExtractOrder == TableOrder.BY_ROW)
    {
      for (int c = 0; c < dataset.getColumnCount(); c++) {
        Comparable key = dataset.getColumnKey(c);
        Paint p = piePlot.getSectionPaint(key);
        if (p == null) {
          p = (Paint)sectionPaints.get(key);
          if (p == null) {
            p = getDrawingSupplier().getNextPaint();
          }
        }
        sectionPaints.put(key, p);
      }
      
    }
    else {
      for (int r = 0; r < dataset.getRowCount(); r++) {
        Comparable key = dataset.getRowKey(r);
        Paint p = piePlot.getSectionPaint(key);
        if (p == null) {
          p = (Paint)sectionPaints.get(key);
          if (p == null) {
            p = getDrawingSupplier().getNextPaint();
          }
        }
        sectionPaints.put(key, p);
      }
    }
  }
  






  public LegendItemCollection getLegendItems()
  {
    LegendItemCollection result = new LegendItemCollection();
    if (dataset == null) {
      return result;
    }
    
    List keys = null;
    prefetchSectionPaints();
    if (dataExtractOrder == TableOrder.BY_ROW) {
      keys = dataset.getColumnKeys();
    }
    else if (dataExtractOrder == TableOrder.BY_COLUMN) {
      keys = dataset.getRowKeys();
    }
    
    if (keys != null) {
      int section = 0;
      Iterator iterator = keys.iterator();
      while (iterator.hasNext()) {
        Comparable key = (Comparable)iterator.next();
        String label = key.toString();
        String description = label;
        Paint paint = (Paint)sectionPaints.get(key);
        LegendItem item = new LegendItem(label, description, null, null, getLegendItemShape(), paint, Plot.DEFAULT_OUTLINE_STROKE, paint);
        

        item.setDataset(getDataset());
        result.add(item);
        section++;
      }
    }
    if (limit > 0.0D) {
      result.add(new LegendItem(aggregatedItemsKey.toString(), aggregatedItemsKey.toString(), null, null, getLegendItemShape(), aggregatedItemsPaint, Plot.DEFAULT_OUTLINE_STROKE, aggregatedItemsPaint));
    }
    


    return result;
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MultiplePiePlot)) {
      return false;
    }
    MultiplePiePlot that = (MultiplePiePlot)obj;
    if (dataExtractOrder != dataExtractOrder) {
      return false;
    }
    if (limit != limit) {
      return false;
    }
    if (!aggregatedItemsKey.equals(aggregatedItemsKey)) {
      return false;
    }
    if (!PaintUtilities.equal(aggregatedItemsPaint, aggregatedItemsPaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(pieChart, pieChart)) {
      return false;
    }
    if (!ShapeUtilities.equal(legendItemShape, legendItemShape)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    MultiplePiePlot clone = (MultiplePiePlot)super.clone();
    pieChart = ((JFreeChart)pieChart.clone());
    sectionPaints = new HashMap(sectionPaints);
    legendItemShape = ShapeUtilities.clone(legendItemShape);
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(aggregatedItemsPaint, stream);
    SerialUtilities.writeShape(legendItemShape, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    aggregatedItemsPaint = SerialUtilities.readPaint(stream);
    legendItemShape = SerialUtilities.readShape(stream);
    sectionPaints = new HashMap();
  }
}
