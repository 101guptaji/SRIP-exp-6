package org.jfree.chart.renderer;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D.Double;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.WaferMapPlot;
import org.jfree.data.general.WaferMapDataset;






























































public class WaferMapRenderer
  extends AbstractRenderer
{
  private Map paintIndex;
  private WaferMapPlot plot;
  private int paintLimit;
  private static final int DEFAULT_PAINT_LIMIT = 35;
  public static final int POSITION_INDEX = 0;
  public static final int VALUE_INDEX = 1;
  private int paintIndexMethod;
  
  public WaferMapRenderer()
  {
    this(null, null);
  }
  





  public WaferMapRenderer(int paintLimit, int paintIndexMethod)
  {
    this(new Integer(paintLimit), new Integer(paintIndexMethod));
  }
  







  public WaferMapRenderer(Integer paintLimit, Integer paintIndexMethod)
  {
    paintIndex = new HashMap();
    
    if (paintLimit == null) {
      this.paintLimit = 35;
    }
    else {
      this.paintLimit = paintLimit.intValue();
    }
    
    this.paintIndexMethod = 1;
    if ((paintIndexMethod != null) && 
      (isMethodValid(paintIndexMethod.intValue()))) {
      this.paintIndexMethod = paintIndexMethod.intValue();
    }
  }
  







  private boolean isMethodValid(int method)
  {
    switch (method) {
    case 0:  return true;
    case 1:  return true; }
    return false;
  }
  





  public DrawingSupplier getDrawingSupplier()
  {
    DrawingSupplier result = null;
    WaferMapPlot p = getPlot();
    if (p != null) {
      result = p.getDrawingSupplier();
    }
    return result;
  }
  




  public WaferMapPlot getPlot()
  {
    return plot;
  }
  




  public void setPlot(WaferMapPlot plot)
  {
    this.plot = plot;
    makePaintIndex();
  }
  






  public Paint getChipColor(Number value)
  {
    return getSeriesPaint(getPaintIndex(value));
  }
  






  private int getPaintIndex(Number value)
  {
    return ((Integer)paintIndex.get(value)).intValue();
  }
  



  private void makePaintIndex()
  {
    if (plot == null) {
      return;
    }
    WaferMapDataset data = plot.getDataset();
    Number dataMin = data.getMinValue();
    Number dataMax = data.getMaxValue();
    Set uniqueValues = data.getUniqueValues();
    int count; Iterator i; if (uniqueValues.size() <= paintLimit) {
      count = 0;
      for (i = uniqueValues.iterator(); i.hasNext();) {
        paintIndex.put(i.next(), new Integer(count++));
      }
      
    }
    else
    {
      switch (paintIndexMethod) {
      case 0: 
        makePositionIndex(uniqueValues);
        break;
      case 1: 
        makeValueIndex(dataMax, dataMin, uniqueValues);
        break;
      }
      
    }
  }
  






  private void makePositionIndex(Set uniqueValues)
  {
    int valuesPerColor = (int)Math.ceil(uniqueValues.size() / paintLimit);
    

    int count = 0;
    int paint = 0;
    for (Iterator i = uniqueValues.iterator(); i.hasNext();) {
      paintIndex.put(i.next(), new Integer(paint));
      count++; if (count % valuesPerColor == 0) {
        paint++;
      }
      if (paint > paintLimit) {
        paint = paintLimit;
      }
    }
  }
  







  private void makeValueIndex(Number max, Number min, Set uniqueValues)
  {
    double valueRange = max.doubleValue() - min.doubleValue();
    double valueStep = valueRange / paintLimit;
    int paint = 0;
    double cutPoint = min.doubleValue() + valueStep;
    for (Iterator i = uniqueValues.iterator(); i.hasNext();) {
      Number value = (Number)i.next();
      while (value.doubleValue() > cutPoint) {
        cutPoint += valueStep;
        paint++;
        if (paint > paintLimit) {
          paint = paintLimit;
        }
      }
      paintIndex.put(value, new Integer(paint));
    }
  }
  





  public LegendItemCollection getLegendCollection()
  {
    LegendItemCollection result = new LegendItemCollection();
    if ((paintIndex != null) && (paintIndex.size() > 0)) {
      if (paintIndex.size() <= paintLimit) {
        Iterator i = paintIndex.entrySet().iterator();
        while (i.hasNext())
        {
          Map.Entry entry = (Map.Entry)i.next();
          String label = entry.getKey().toString();
          String description = label;
          Shape shape = new Rectangle2D.Double(1.0D, 1.0D, 1.0D, 1.0D);
          Paint paint = lookupSeriesPaint(((Integer)entry.getValue()).intValue());
          
          Paint outlinePaint = Color.black;
          Stroke outlineStroke = DEFAULT_STROKE;
          
          result.add(new LegendItem(label, description, null, null, shape, paint, outlineStroke, outlinePaint));
        }
        

      }
      else
      {
        Set unique = new HashSet();
        Iterator i = paintIndex.entrySet().iterator();
        while (i.hasNext()) {
          Map.Entry entry = (Map.Entry)i.next();
          if (unique.add(entry.getValue())) {
            String label = getMinPaintValue((Integer)entry.getValue()).toString() + " - " + getMaxPaintValue((Integer)entry.getValue()).toString();
            


            String description = label;
            Shape shape = new Rectangle2D.Double(1.0D, 1.0D, 1.0D, 1.0D);
            Paint paint = getSeriesPaint(((Integer)entry.getValue()).intValue());
            

            Paint outlinePaint = Color.black;
            Stroke outlineStroke = DEFAULT_STROKE;
            
            result.add(new LegendItem(label, description, null, null, shape, paint, outlineStroke, outlinePaint));
          }
        }
      }
    }
    

    return result;
  }
  







  private Number getMinPaintValue(Integer index)
  {
    double minValue = Double.POSITIVE_INFINITY;
    for (Iterator i = paintIndex.entrySet().iterator(); i.hasNext();) {
      Map.Entry entry = (Map.Entry)i.next();
      if ((((Integer)entry.getValue()).equals(index)) && 
        (((Number)entry.getKey()).doubleValue() < minValue)) {
        minValue = ((Number)entry.getKey()).doubleValue();
      }
    }
    
    return new Double(minValue);
  }
  







  private Number getMaxPaintValue(Integer index)
  {
    double maxValue = Double.NEGATIVE_INFINITY;
    for (Iterator i = paintIndex.entrySet().iterator(); i.hasNext();) {
      Map.Entry entry = (Map.Entry)i.next();
      if ((((Integer)entry.getValue()).equals(index)) && 
        (((Number)entry.getKey()).doubleValue() > maxValue)) {
        maxValue = ((Number)entry.getKey()).doubleValue();
      }
    }
    
    return new Double(maxValue);
  }
}
