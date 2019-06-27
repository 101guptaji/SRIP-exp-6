package org.jfree.chart.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import javax.swing.event.EventListenerList;
import org.jfree.chart.HashUtilities;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.TextAnchor;
import org.jfree.util.BooleanList;
import org.jfree.util.BooleanUtilities;
import org.jfree.util.ObjectList;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintList;
import org.jfree.util.PaintUtilities;
import org.jfree.util.ShapeList;
import org.jfree.util.ShapeUtilities;
import org.jfree.util.StrokeList;






































































































public abstract class AbstractRenderer
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -828267569428206075L;
  public static final Double ZERO = new Double(0.0D);
  

  public static final Paint DEFAULT_PAINT = Color.blue;
  

  public static final Paint DEFAULT_OUTLINE_PAINT = Color.gray;
  

  public static final Stroke DEFAULT_STROKE = new BasicStroke(1.0F);
  

  public static final Stroke DEFAULT_OUTLINE_STROKE = new BasicStroke(1.0F);
  

  public static final Shape DEFAULT_SHAPE = new Rectangle2D.Double(-3.0D, -3.0D, 6.0D, 6.0D);
  


  public static final Font DEFAULT_VALUE_LABEL_FONT = new Font("SansSerif", 0, 10);
  


  public static final Paint DEFAULT_VALUE_LABEL_PAINT = Color.black;
  


  private BooleanList seriesVisibleList;
  


  private boolean baseSeriesVisible;
  


  private BooleanList seriesVisibleInLegendList;
  


  private boolean baseSeriesVisibleInLegend;
  


  private PaintList paintList;
  


  private boolean autoPopulateSeriesPaint;
  


  private transient Paint basePaint;
  


  private PaintList fillPaintList;
  


  private boolean autoPopulateSeriesFillPaint;
  


  private transient Paint baseFillPaint;
  


  private PaintList outlinePaintList;
  


  private boolean autoPopulateSeriesOutlinePaint;
  


  private transient Paint baseOutlinePaint;
  


  private StrokeList strokeList;
  


  private boolean autoPopulateSeriesStroke;
  


  private transient Stroke baseStroke;
  


  private StrokeList outlineStrokeList;
  


  private transient Stroke baseOutlineStroke;
  


  private boolean autoPopulateSeriesOutlineStroke;
  


  private ShapeList shapeList;
  


  private boolean autoPopulateSeriesShape;
  


  private transient Shape baseShape;
  


  private BooleanList itemLabelsVisibleList;
  


  private Boolean baseItemLabelsVisible;
  


  private ObjectList itemLabelFontList;
  


  private Font baseItemLabelFont;
  


  private PaintList itemLabelPaintList;
  


  private transient Paint baseItemLabelPaint;
  


  private ObjectList positiveItemLabelPositionList;
  


  private ItemLabelPosition basePositiveItemLabelPosition;
  


  private ObjectList negativeItemLabelPositionList;
  


  private ItemLabelPosition baseNegativeItemLabelPosition;
  


  private double itemLabelAnchorOffset = 2.0D;
  






  private BooleanList createEntitiesList;
  






  private boolean baseCreateEntities;
  





  private ShapeList legendShape;
  





  private transient Shape baseLegendShape;
  





  private ObjectList legendTextFont;
  





  private Font baseLegendTextFont;
  





  private PaintList legendTextPaint;
  





  private transient Paint baseLegendTextPaint;
  





  private boolean dataBoundsIncludesVisibleSeriesOnly = true;
  


  private int defaultEntityRadius;
  

  private transient EventListenerList listenerList;
  

  private transient RendererChangeEvent event;
  


  public AbstractRenderer()
  {
    seriesVisible = null;
    seriesVisibleList = new BooleanList();
    baseSeriesVisible = true;
    
    seriesVisibleInLegend = null;
    seriesVisibleInLegendList = new BooleanList();
    baseSeriesVisibleInLegend = true;
    
    paint = null;
    paintList = new PaintList();
    basePaint = DEFAULT_PAINT;
    autoPopulateSeriesPaint = true;
    
    fillPaint = null;
    fillPaintList = new PaintList();
    baseFillPaint = Color.white;
    autoPopulateSeriesFillPaint = false;
    
    outlinePaint = null;
    outlinePaintList = new PaintList();
    baseOutlinePaint = DEFAULT_OUTLINE_PAINT;
    autoPopulateSeriesOutlinePaint = false;
    
    stroke = null;
    strokeList = new StrokeList();
    baseStroke = DEFAULT_STROKE;
    autoPopulateSeriesStroke = true;
    
    outlineStroke = null;
    outlineStrokeList = new StrokeList();
    baseOutlineStroke = DEFAULT_OUTLINE_STROKE;
    autoPopulateSeriesOutlineStroke = false;
    
    shape = null;
    shapeList = new ShapeList();
    baseShape = DEFAULT_SHAPE;
    autoPopulateSeriesShape = true;
    
    itemLabelsVisible = null;
    itemLabelsVisibleList = new BooleanList();
    baseItemLabelsVisible = Boolean.FALSE;
    
    itemLabelFont = null;
    itemLabelFontList = new ObjectList();
    baseItemLabelFont = new Font("SansSerif", 0, 10);
    
    itemLabelPaint = null;
    itemLabelPaintList = new PaintList();
    baseItemLabelPaint = Color.black;
    
    positiveItemLabelPosition = null;
    positiveItemLabelPositionList = new ObjectList();
    basePositiveItemLabelPosition = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER);
    

    negativeItemLabelPosition = null;
    negativeItemLabelPositionList = new ObjectList();
    baseNegativeItemLabelPosition = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER);
    

    createEntities = null;
    createEntitiesList = new BooleanList();
    baseCreateEntities = true;
    
    defaultEntityRadius = 3;
    
    legendShape = new ShapeList();
    baseLegendShape = null;
    
    legendTextFont = new ObjectList();
    baseLegendTextFont = null;
    
    legendTextPaint = new PaintList();
    baseLegendTextPaint = null;
    
    listenerList = new EventListenerList();
  }
  








  public abstract DrawingSupplier getDrawingSupplier();
  








  public boolean getItemVisible(int series, int item)
  {
    return isSeriesVisible(series);
  }
  







  public boolean isSeriesVisible(int series)
  {
    boolean result = baseSeriesVisible;
    if (seriesVisible != null) {
      result = seriesVisible.booleanValue();
    }
    else {
      Boolean b = seriesVisibleList.getBoolean(series);
      if (b != null) {
        result = b.booleanValue();
      }
    }
    return result;
  }
  








  public Boolean getSeriesVisible(int series)
  {
    return seriesVisibleList.getBoolean(series);
  }
  








  public void setSeriesVisible(int series, Boolean visible)
  {
    setSeriesVisible(series, visible, true);
  }
  










  public void setSeriesVisible(int series, Boolean visible, boolean notify)
  {
    seriesVisibleList.setBoolean(series, visible);
    if (notify)
    {



      RendererChangeEvent e = new RendererChangeEvent(this, true);
      notifyListeners(e);
    }
  }
  






  public boolean getBaseSeriesVisible()
  {
    return baseSeriesVisible;
  }
  








  public void setBaseSeriesVisible(boolean visible)
  {
    setBaseSeriesVisible(visible, true);
  }
  








  public void setBaseSeriesVisible(boolean visible, boolean notify)
  {
    baseSeriesVisible = visible;
    if (notify)
    {



      RendererChangeEvent e = new RendererChangeEvent(this, true);
      notifyListeners(e);
    }
  }
  









  public boolean isSeriesVisibleInLegend(int series)
  {
    boolean result = baseSeriesVisibleInLegend;
    if (seriesVisibleInLegend != null) {
      result = seriesVisibleInLegend.booleanValue();
    }
    else {
      Boolean b = seriesVisibleInLegendList.getBoolean(series);
      if (b != null) {
        result = b.booleanValue();
      }
    }
    return result;
  }
  











  public Boolean getSeriesVisibleInLegend(int series)
  {
    return seriesVisibleInLegendList.getBoolean(series);
  }
  








  public void setSeriesVisibleInLegend(int series, Boolean visible)
  {
    setSeriesVisibleInLegend(series, visible, true);
  }
  











  public void setSeriesVisibleInLegend(int series, Boolean visible, boolean notify)
  {
    seriesVisibleInLegendList.setBoolean(series, visible);
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public boolean getBaseSeriesVisibleInLegend()
  {
    return baseSeriesVisibleInLegend;
  }
  








  public void setBaseSeriesVisibleInLegend(boolean visible)
  {
    setBaseSeriesVisibleInLegend(visible, true);
  }
  








  public void setBaseSeriesVisibleInLegend(boolean visible, boolean notify)
  {
    baseSeriesVisibleInLegend = visible;
    if (notify) {
      fireChangeEvent();
    }
  }
  













  public Paint getItemPaint(int row, int column)
  {
    return lookupSeriesPaint(row);
  }
  










  public Paint lookupSeriesPaint(int series)
  {
    if (paint != null) {
      return paint;
    }
    

    Paint seriesPaint = getSeriesPaint(series);
    if ((seriesPaint == null) && (autoPopulateSeriesPaint)) {
      DrawingSupplier supplier = getDrawingSupplier();
      if (supplier != null) {
        seriesPaint = supplier.getNextPaint();
        setSeriesPaint(series, seriesPaint, false);
      }
    }
    if (seriesPaint == null) {
      seriesPaint = basePaint;
    }
    return seriesPaint;
  }
  









  public Paint getSeriesPaint(int series)
  {
    return paintList.getPaint(series);
  }
  








  public void setSeriesPaint(int series, Paint paint)
  {
    setSeriesPaint(series, paint, true);
  }
  









  public void setSeriesPaint(int series, Paint paint, boolean notify)
  {
    paintList.setPaint(series, paint);
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public void clearSeriesPaints(boolean notify)
  {
    paintList.clear();
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public Paint getBasePaint()
  {
    return basePaint;
  }
  








  public void setBasePaint(Paint paint)
  {
    setBasePaint(paint, true);
  }
  








  public void setBasePaint(Paint paint, boolean notify)
  {
    basePaint = paint;
    if (notify) {
      fireChangeEvent();
    }
  }
  









  public boolean getAutoPopulateSeriesPaint()
  {
    return autoPopulateSeriesPaint;
  }
  









  public void setAutoPopulateSeriesPaint(boolean auto)
  {
    autoPopulateSeriesPaint = auto;
  }
  












  public Paint getItemFillPaint(int row, int column)
  {
    return lookupSeriesFillPaint(row);
  }
  










  public Paint lookupSeriesFillPaint(int series)
  {
    if (fillPaint != null) {
      return fillPaint;
    }
    

    Paint seriesFillPaint = getSeriesFillPaint(series);
    if ((seriesFillPaint == null) && (autoPopulateSeriesFillPaint)) {
      DrawingSupplier supplier = getDrawingSupplier();
      if (supplier != null) {
        seriesFillPaint = supplier.getNextFillPaint();
        setSeriesFillPaint(series, seriesFillPaint, false);
      }
    }
    if (seriesFillPaint == null) {
      seriesFillPaint = baseFillPaint;
    }
    return seriesFillPaint;
  }
  









  public Paint getSeriesFillPaint(int series)
  {
    return fillPaintList.getPaint(series);
  }
  








  public void setSeriesFillPaint(int series, Paint paint)
  {
    setSeriesFillPaint(series, paint, true);
  }
  









  public void setSeriesFillPaint(int series, Paint paint, boolean notify)
  {
    fillPaintList.setPaint(series, paint);
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public Paint getBaseFillPaint()
  {
    return baseFillPaint;
  }
  








  public void setBaseFillPaint(Paint paint)
  {
    setBaseFillPaint(paint, true);
  }
  








  public void setBaseFillPaint(Paint paint, boolean notify)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    baseFillPaint = paint;
    if (notify) {
      fireChangeEvent();
    }
  }
  










  public boolean getAutoPopulateSeriesFillPaint()
  {
    return autoPopulateSeriesFillPaint;
  }
  










  public void setAutoPopulateSeriesFillPaint(boolean auto)
  {
    autoPopulateSeriesFillPaint = auto;
  }
  













  public Paint getItemOutlinePaint(int row, int column)
  {
    return lookupSeriesOutlinePaint(row);
  }
  










  public Paint lookupSeriesOutlinePaint(int series)
  {
    if (outlinePaint != null) {
      return outlinePaint;
    }
    

    Paint seriesOutlinePaint = getSeriesOutlinePaint(series);
    if ((seriesOutlinePaint == null) && (autoPopulateSeriesOutlinePaint)) {
      DrawingSupplier supplier = getDrawingSupplier();
      if (supplier != null) {
        seriesOutlinePaint = supplier.getNextOutlinePaint();
        setSeriesOutlinePaint(series, seriesOutlinePaint, false);
      }
    }
    if (seriesOutlinePaint == null) {
      seriesOutlinePaint = baseOutlinePaint;
    }
    return seriesOutlinePaint;
  }
  









  public Paint getSeriesOutlinePaint(int series)
  {
    return outlinePaintList.getPaint(series);
  }
  








  public void setSeriesOutlinePaint(int series, Paint paint)
  {
    setSeriesOutlinePaint(series, paint, true);
  }
  









  public void setSeriesOutlinePaint(int series, Paint paint, boolean notify)
  {
    outlinePaintList.setPaint(series, paint);
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public Paint getBaseOutlinePaint()
  {
    return baseOutlinePaint;
  }
  








  public void setBaseOutlinePaint(Paint paint)
  {
    setBaseOutlinePaint(paint, true);
  }
  








  public void setBaseOutlinePaint(Paint paint, boolean notify)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    baseOutlinePaint = paint;
    if (notify) {
      fireChangeEvent();
    }
  }
  










  public boolean getAutoPopulateSeriesOutlinePaint()
  {
    return autoPopulateSeriesOutlinePaint;
  }
  










  public void setAutoPopulateSeriesOutlinePaint(boolean auto)
  {
    autoPopulateSeriesOutlinePaint = auto;
  }
  












  public Stroke getItemStroke(int row, int column)
  {
    return lookupSeriesStroke(row);
  }
  










  public Stroke lookupSeriesStroke(int series)
  {
    if (stroke != null) {
      return stroke;
    }
    

    Stroke result = getSeriesStroke(series);
    if ((result == null) && (autoPopulateSeriesStroke)) {
      DrawingSupplier supplier = getDrawingSupplier();
      if (supplier != null) {
        result = supplier.getNextStroke();
        setSeriesStroke(series, result, false);
      }
    }
    if (result == null) {
      result = baseStroke;
    }
    return result;
  }
  









  public Stroke getSeriesStroke(int series)
  {
    return strokeList.getStroke(series);
  }
  








  public void setSeriesStroke(int series, Stroke stroke)
  {
    setSeriesStroke(series, stroke, true);
  }
  









  public void setSeriesStroke(int series, Stroke stroke, boolean notify)
  {
    strokeList.setStroke(series, stroke);
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public void clearSeriesStrokes(boolean notify)
  {
    strokeList.clear();
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public Stroke getBaseStroke()
  {
    return baseStroke;
  }
  








  public void setBaseStroke(Stroke stroke)
  {
    setBaseStroke(stroke, true);
  }
  








  public void setBaseStroke(Stroke stroke, boolean notify)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    baseStroke = stroke;
    if (notify) {
      fireChangeEvent();
    }
  }
  









  public boolean getAutoPopulateSeriesStroke()
  {
    return autoPopulateSeriesStroke;
  }
  









  public void setAutoPopulateSeriesStroke(boolean auto)
  {
    autoPopulateSeriesStroke = auto;
  }
  












  public Stroke getItemOutlineStroke(int row, int column)
  {
    return lookupSeriesOutlineStroke(row);
  }
  










  public Stroke lookupSeriesOutlineStroke(int series)
  {
    if (outlineStroke != null) {
      return outlineStroke;
    }
    

    Stroke result = getSeriesOutlineStroke(series);
    if ((result == null) && (autoPopulateSeriesOutlineStroke)) {
      DrawingSupplier supplier = getDrawingSupplier();
      if (supplier != null) {
        result = supplier.getNextOutlineStroke();
        setSeriesOutlineStroke(series, result, false);
      }
    }
    if (result == null) {
      result = baseOutlineStroke;
    }
    return result;
  }
  









  public Stroke getSeriesOutlineStroke(int series)
  {
    return outlineStrokeList.getStroke(series);
  }
  








  public void setSeriesOutlineStroke(int series, Stroke stroke)
  {
    setSeriesOutlineStroke(series, stroke, true);
  }
  










  public void setSeriesOutlineStroke(int series, Stroke stroke, boolean notify)
  {
    outlineStrokeList.setStroke(series, stroke);
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public Stroke getBaseOutlineStroke()
  {
    return baseOutlineStroke;
  }
  







  public void setBaseOutlineStroke(Stroke stroke)
  {
    setBaseOutlineStroke(stroke, true);
  }
  









  public void setBaseOutlineStroke(Stroke stroke, boolean notify)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    baseOutlineStroke = stroke;
    if (notify) {
      fireChangeEvent();
    }
  }
  










  public boolean getAutoPopulateSeriesOutlineStroke()
  {
    return autoPopulateSeriesOutlineStroke;
  }
  










  public void setAutoPopulateSeriesOutlineStroke(boolean auto)
  {
    autoPopulateSeriesOutlineStroke = auto;
  }
  












  public Shape getItemShape(int row, int column)
  {
    return lookupSeriesShape(row);
  }
  










  public Shape lookupSeriesShape(int series)
  {
    if (shape != null) {
      return shape;
    }
    

    Shape result = getSeriesShape(series);
    if ((result == null) && (autoPopulateSeriesShape)) {
      DrawingSupplier supplier = getDrawingSupplier();
      if (supplier != null) {
        result = supplier.getNextShape();
        setSeriesShape(series, result, false);
      }
    }
    if (result == null) {
      result = baseShape;
    }
    return result;
  }
  









  public Shape getSeriesShape(int series)
  {
    return shapeList.getShape(series);
  }
  








  public void setSeriesShape(int series, Shape shape)
  {
    setSeriesShape(series, shape, true);
  }
  









  public void setSeriesShape(int series, Shape shape, boolean notify)
  {
    shapeList.setShape(series, shape);
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public Shape getBaseShape()
  {
    return baseShape;
  }
  








  public void setBaseShape(Shape shape)
  {
    setBaseShape(shape, true);
  }
  








  public void setBaseShape(Shape shape, boolean notify)
  {
    if (shape == null) {
      throw new IllegalArgumentException("Null 'shape' argument.");
    }
    baseShape = shape;
    if (notify) {
      fireChangeEvent();
    }
  }
  









  public boolean getAutoPopulateSeriesShape()
  {
    return autoPopulateSeriesShape;
  }
  









  public void setAutoPopulateSeriesShape(boolean auto)
  {
    autoPopulateSeriesShape = auto;
  }
  










  public boolean isItemLabelVisible(int row, int column)
  {
    return isSeriesItemLabelsVisible(row);
  }
  









  public boolean isSeriesItemLabelsVisible(int series)
  {
    if (itemLabelsVisible != null) {
      return itemLabelsVisible.booleanValue();
    }
    

    Boolean b = itemLabelsVisibleList.getBoolean(series);
    if (b == null) {
      b = baseItemLabelsVisible;
    }
    if (b == null) {
      b = Boolean.FALSE;
    }
    return b.booleanValue();
  }
  







  public void setSeriesItemLabelsVisible(int series, boolean visible)
  {
    setSeriesItemLabelsVisible(series, BooleanUtilities.valueOf(visible));
  }
  






  public void setSeriesItemLabelsVisible(int series, Boolean visible)
  {
    setSeriesItemLabelsVisible(series, visible, true);
  }
  









  public void setSeriesItemLabelsVisible(int series, Boolean visible, boolean notify)
  {
    itemLabelsVisibleList.setBoolean(series, visible);
    if (notify) {
      fireChangeEvent();
    }
  }
  










  public Boolean getBaseItemLabelsVisible()
  {
    return baseItemLabelsVisible;
  }
  







  public void setBaseItemLabelsVisible(boolean visible)
  {
    setBaseItemLabelsVisible(BooleanUtilities.valueOf(visible));
  }
  






  public void setBaseItemLabelsVisible(Boolean visible)
  {
    setBaseItemLabelsVisible(visible, true);
  }
  










  public void setBaseItemLabelsVisible(Boolean visible, boolean notify)
  {
    baseItemLabelsVisible = visible;
    if (notify) {
      fireChangeEvent();
    }
  }
  









  public Font getItemLabelFont(int row, int column)
  {
    Font result = itemLabelFont;
    if (result == null) {
      result = getSeriesItemLabelFont(row);
      if (result == null) {
        result = baseItemLabelFont;
      }
    }
    return result;
  }
  








  public Font getSeriesItemLabelFont(int series)
  {
    return (Font)itemLabelFontList.get(series);
  }
  








  public void setSeriesItemLabelFont(int series, Font font)
  {
    setSeriesItemLabelFont(series, font, true);
  }
  










  public void setSeriesItemLabelFont(int series, Font font, boolean notify)
  {
    itemLabelFontList.set(series, font);
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public Font getBaseItemLabelFont()
  {
    return baseItemLabelFont;
  }
  







  public void setBaseItemLabelFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    setBaseItemLabelFont(font, true);
  }
  









  public void setBaseItemLabelFont(Font font, boolean notify)
  {
    baseItemLabelFont = font;
    if (notify) {
      fireChangeEvent();
    }
  }
  









  public Paint getItemLabelPaint(int row, int column)
  {
    Paint result = itemLabelPaint;
    if (result == null) {
      result = getSeriesItemLabelPaint(row);
      if (result == null) {
        result = baseItemLabelPaint;
      }
    }
    return result;
  }
  








  public Paint getSeriesItemLabelPaint(int series)
  {
    return itemLabelPaintList.getPaint(series);
  }
  








  public void setSeriesItemLabelPaint(int series, Paint paint)
  {
    setSeriesItemLabelPaint(series, paint, true);
  }
  











  public void setSeriesItemLabelPaint(int series, Paint paint, boolean notify)
  {
    itemLabelPaintList.setPaint(series, paint);
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public Paint getBaseItemLabelPaint()
  {
    return baseItemLabelPaint;
  }
  








  public void setBaseItemLabelPaint(Paint paint)
  {
    setBaseItemLabelPaint(paint, true);
  }
  









  public void setBaseItemLabelPaint(Paint paint, boolean notify)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    baseItemLabelPaint = paint;
    if (notify) {
      fireChangeEvent();
    }
  }
  











  public ItemLabelPosition getPositiveItemLabelPosition(int row, int column)
  {
    return getSeriesPositiveItemLabelPosition(row);
  }
  










  public ItemLabelPosition getSeriesPositiveItemLabelPosition(int series)
  {
    if (positiveItemLabelPosition != null) {
      return positiveItemLabelPosition;
    }
    

    ItemLabelPosition position = (ItemLabelPosition)positiveItemLabelPositionList.get(series);
    
    if (position == null) {
      position = basePositiveItemLabelPosition;
    }
    return position;
  }
  










  public void setSeriesPositiveItemLabelPosition(int series, ItemLabelPosition position)
  {
    setSeriesPositiveItemLabelPosition(series, position, true);
  }
  












  public void setSeriesPositiveItemLabelPosition(int series, ItemLabelPosition position, boolean notify)
  {
    positiveItemLabelPositionList.set(series, position);
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public ItemLabelPosition getBasePositiveItemLabelPosition()
  {
    return basePositiveItemLabelPosition;
  }
  







  public void setBasePositiveItemLabelPosition(ItemLabelPosition position)
  {
    setBasePositiveItemLabelPosition(position, true);
  }
  









  public void setBasePositiveItemLabelPosition(ItemLabelPosition position, boolean notify)
  {
    if (position == null) {
      throw new IllegalArgumentException("Null 'position' argument.");
    }
    basePositiveItemLabelPosition = position;
    if (notify) {
      fireChangeEvent();
    }
  }
  













  public ItemLabelPosition getNegativeItemLabelPosition(int row, int column)
  {
    return getSeriesNegativeItemLabelPosition(row);
  }
  










  public ItemLabelPosition getSeriesNegativeItemLabelPosition(int series)
  {
    if (negativeItemLabelPosition != null) {
      return negativeItemLabelPosition;
    }
    

    ItemLabelPosition position = (ItemLabelPosition)negativeItemLabelPositionList.get(series);
    
    if (position == null) {
      position = baseNegativeItemLabelPosition;
    }
    return position;
  }
  










  public void setSeriesNegativeItemLabelPosition(int series, ItemLabelPosition position)
  {
    setSeriesNegativeItemLabelPosition(series, position, true);
  }
  












  public void setSeriesNegativeItemLabelPosition(int series, ItemLabelPosition position, boolean notify)
  {
    negativeItemLabelPositionList.set(series, position);
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public ItemLabelPosition getBaseNegativeItemLabelPosition()
  {
    return baseNegativeItemLabelPosition;
  }
  







  public void setBaseNegativeItemLabelPosition(ItemLabelPosition position)
  {
    setBaseNegativeItemLabelPosition(position, true);
  }
  









  public void setBaseNegativeItemLabelPosition(ItemLabelPosition position, boolean notify)
  {
    if (position == null) {
      throw new IllegalArgumentException("Null 'position' argument.");
    }
    baseNegativeItemLabelPosition = position;
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public double getItemLabelAnchorOffset()
  {
    return itemLabelAnchorOffset;
  }
  






  public void setItemLabelAnchorOffset(double offset)
  {
    itemLabelAnchorOffset = offset;
    fireChangeEvent();
  }
  








  public boolean getItemCreateEntity(int series, int item)
  {
    if (createEntities != null) {
      return createEntities.booleanValue();
    }
    
    Boolean b = getSeriesCreateEntities(series);
    if (b != null) {
      return b.booleanValue();
    }
    
    return baseCreateEntities;
  }
  











  public Boolean getSeriesCreateEntities(int series)
  {
    return createEntitiesList.getBoolean(series);
  }
  








  public void setSeriesCreateEntities(int series, Boolean create)
  {
    setSeriesCreateEntities(series, create, true);
  }
  











  public void setSeriesCreateEntities(int series, Boolean create, boolean notify)
  {
    createEntitiesList.setBoolean(series, create);
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public boolean getBaseCreateEntities()
  {
    return baseCreateEntities;
  }
  









  public void setBaseCreateEntities(boolean create)
  {
    setBaseCreateEntities(create, true);
  }
  









  public void setBaseCreateEntities(boolean create, boolean notify)
  {
    baseCreateEntities = create;
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public int getDefaultEntityRadius()
  {
    return defaultEntityRadius;
  }
  







  public void setDefaultEntityRadius(int radius)
  {
    defaultEntityRadius = radius;
  }
  








  public Shape lookupLegendShape(int series)
  {
    Shape result = getLegendShape(series);
    if (result == null) {
      result = baseLegendShape;
    }
    if (result == null) {
      result = lookupSeriesShape(series);
    }
    return result;
  }
  











  public Shape getLegendShape(int series)
  {
    return legendShape.getShape(series);
  }
  








  public void setLegendShape(int series, Shape shape)
  {
    legendShape.setShape(series, shape);
    fireChangeEvent();
  }
  






  public Shape getBaseLegendShape()
  {
    return baseLegendShape;
  }
  







  public void setBaseLegendShape(Shape shape)
  {
    baseLegendShape = shape;
    fireChangeEvent();
  }
  








  public Font lookupLegendTextFont(int series)
  {
    Font result = getLegendTextFont(series);
    if (result == null) {
      result = baseLegendTextFont;
    }
    return result;
  }
  











  public Font getLegendTextFont(int series)
  {
    return (Font)legendTextFont.get(series);
  }
  








  public void setLegendTextFont(int series, Font font)
  {
    legendTextFont.set(series, font);
    fireChangeEvent();
  }
  






  public Font getBaseLegendTextFont()
  {
    return baseLegendTextFont;
  }
  







  public void setBaseLegendTextFont(Font font)
  {
    baseLegendTextFont = font;
    fireChangeEvent();
  }
  








  public Paint lookupLegendTextPaint(int series)
  {
    Paint result = getLegendTextPaint(series);
    if (result == null) {
      result = baseLegendTextPaint;
    }
    return result;
  }
  











  public Paint getLegendTextPaint(int series)
  {
    return legendTextPaint.getPaint(series);
  }
  








  public void setLegendTextPaint(int series, Paint paint)
  {
    legendTextPaint.setPaint(series, paint);
    fireChangeEvent();
  }
  






  public Paint getBaseLegendTextPaint()
  {
    return baseLegendTextPaint;
  }
  







  public void setBaseLegendTextPaint(Paint paint)
  {
    baseLegendTextPaint = paint;
    fireChangeEvent();
  }
  







  public boolean getDataBoundsIncludesVisibleSeriesOnly()
  {
    return dataBoundsIncludesVisibleSeriesOnly;
  }
  








  public void setDataBoundsIncludesVisibleSeriesOnly(boolean visibleOnly)
  {
    dataBoundsIncludesVisibleSeriesOnly = visibleOnly;
    notifyListeners(new RendererChangeEvent(this, true));
  }
  

  private static final double ADJ = Math.cos(0.5235987755982988D);
  

  private static final double OPP = Math.sin(0.5235987755982988D);
  /**
   * @deprecated
   */
  private Boolean seriesVisible;
  /**
   * @deprecated
   */
  private Boolean seriesVisibleInLegend;
  /**
   * @deprecated
   */
  private transient Paint paint;
  
  protected Point2D calculateLabelAnchorPoint(ItemLabelAnchor anchor, double x, double y, PlotOrientation orientation) { Point2D result = null;
    if (anchor == ItemLabelAnchor.CENTER) {
      result = new Point2D.Double(x, y);
    }
    else if (anchor == ItemLabelAnchor.INSIDE1) {
      result = new Point2D.Double(x + OPP * itemLabelAnchorOffset, y - ADJ * itemLabelAnchorOffset);

    }
    else if (anchor == ItemLabelAnchor.INSIDE2) {
      result = new Point2D.Double(x + ADJ * itemLabelAnchorOffset, y - OPP * itemLabelAnchorOffset);

    }
    else if (anchor == ItemLabelAnchor.INSIDE3) {
      result = new Point2D.Double(x + itemLabelAnchorOffset, y);
    }
    else if (anchor == ItemLabelAnchor.INSIDE4) {
      result = new Point2D.Double(x + ADJ * itemLabelAnchorOffset, y + OPP * itemLabelAnchorOffset);

    }
    else if (anchor == ItemLabelAnchor.INSIDE5) {
      result = new Point2D.Double(x + OPP * itemLabelAnchorOffset, y + ADJ * itemLabelAnchorOffset);

    }
    else if (anchor == ItemLabelAnchor.INSIDE6) {
      result = new Point2D.Double(x, y + itemLabelAnchorOffset);
    }
    else if (anchor == ItemLabelAnchor.INSIDE7) {
      result = new Point2D.Double(x - OPP * itemLabelAnchorOffset, y + ADJ * itemLabelAnchorOffset);

    }
    else if (anchor == ItemLabelAnchor.INSIDE8) {
      result = new Point2D.Double(x - ADJ * itemLabelAnchorOffset, y + OPP * itemLabelAnchorOffset);

    }
    else if (anchor == ItemLabelAnchor.INSIDE9) {
      result = new Point2D.Double(x - itemLabelAnchorOffset, y);
    }
    else if (anchor == ItemLabelAnchor.INSIDE10) {
      result = new Point2D.Double(x - ADJ * itemLabelAnchorOffset, y - OPP * itemLabelAnchorOffset);

    }
    else if (anchor == ItemLabelAnchor.INSIDE11) {
      result = new Point2D.Double(x - OPP * itemLabelAnchorOffset, y - ADJ * itemLabelAnchorOffset);

    }
    else if (anchor == ItemLabelAnchor.INSIDE12) {
      result = new Point2D.Double(x, y - itemLabelAnchorOffset);
    }
    else if (anchor == ItemLabelAnchor.OUTSIDE1) {
      result = new Point2D.Double(x + 2.0D * OPP * itemLabelAnchorOffset, y - 2.0D * ADJ * itemLabelAnchorOffset);


    }
    else if (anchor == ItemLabelAnchor.OUTSIDE2) {
      result = new Point2D.Double(x + 2.0D * ADJ * itemLabelAnchorOffset, y - 2.0D * OPP * itemLabelAnchorOffset);


    }
    else if (anchor == ItemLabelAnchor.OUTSIDE3) {
      result = new Point2D.Double(x + 2.0D * itemLabelAnchorOffset, y);

    }
    else if (anchor == ItemLabelAnchor.OUTSIDE4) {
      result = new Point2D.Double(x + 2.0D * ADJ * itemLabelAnchorOffset, y + 2.0D * OPP * itemLabelAnchorOffset);


    }
    else if (anchor == ItemLabelAnchor.OUTSIDE5) {
      result = new Point2D.Double(x + 2.0D * OPP * itemLabelAnchorOffset, y + 2.0D * ADJ * itemLabelAnchorOffset);


    }
    else if (anchor == ItemLabelAnchor.OUTSIDE6) {
      result = new Point2D.Double(x, y + 2.0D * itemLabelAnchorOffset);

    }
    else if (anchor == ItemLabelAnchor.OUTSIDE7) {
      result = new Point2D.Double(x - 2.0D * OPP * itemLabelAnchorOffset, y + 2.0D * ADJ * itemLabelAnchorOffset);


    }
    else if (anchor == ItemLabelAnchor.OUTSIDE8) {
      result = new Point2D.Double(x - 2.0D * ADJ * itemLabelAnchorOffset, y + 2.0D * OPP * itemLabelAnchorOffset);


    }
    else if (anchor == ItemLabelAnchor.OUTSIDE9) {
      result = new Point2D.Double(x - 2.0D * itemLabelAnchorOffset, y);

    }
    else if (anchor == ItemLabelAnchor.OUTSIDE10) {
      result = new Point2D.Double(x - 2.0D * ADJ * itemLabelAnchorOffset, y - 2.0D * OPP * itemLabelAnchorOffset);


    }
    else if (anchor == ItemLabelAnchor.OUTSIDE11) {
      result = new Point2D.Double(x - 2.0D * OPP * itemLabelAnchorOffset, y - 2.0D * ADJ * itemLabelAnchorOffset);


    }
    else if (anchor == ItemLabelAnchor.OUTSIDE12) {
      result = new Point2D.Double(x, y - 2.0D * itemLabelAnchorOffset);
    }
    
    return result;
  }
  






  public void addChangeListener(RendererChangeListener listener)
  {
    if (listener == null) {
      throw new IllegalArgumentException("Null 'listener' argument.");
    }
    listenerList.add(RendererChangeListener.class, listener);
  }
  







  public void removeChangeListener(RendererChangeListener listener)
  {
    if (listener == null) {
      throw new IllegalArgumentException("Null 'listener' argument.");
    }
    listenerList.remove(RendererChangeListener.class, listener);
  }
  








  public boolean hasListener(EventListener listener)
  {
    List list = Arrays.asList(listenerList.getListenerList());
    return list.contains(listener);
  }
  














  protected void fireChangeEvent()
  {
    notifyListeners(new RendererChangeEvent(this));
  }
  




  public void notifyListeners(RendererChangeEvent event)
  {
    Object[] ls = listenerList.getListenerList();
    for (int i = ls.length - 2; i >= 0; i -= 2) {
      if (ls[i] == RendererChangeListener.class) {
        ((RendererChangeListener)ls[(i + 1)]).rendererChanged(event);
      }
    }
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AbstractRenderer)) {
      return false;
    }
    AbstractRenderer that = (AbstractRenderer)obj;
    if (dataBoundsIncludesVisibleSeriesOnly != dataBoundsIncludesVisibleSeriesOnly)
    {
      return false;
    }
    if (defaultEntityRadius != defaultEntityRadius) {
      return false;
    }
    if (!ObjectUtilities.equal(seriesVisible, seriesVisible)) {
      return false;
    }
    if (!seriesVisibleList.equals(seriesVisibleList)) {
      return false;
    }
    if (baseSeriesVisible != baseSeriesVisible) {
      return false;
    }
    if (!ObjectUtilities.equal(seriesVisibleInLegend, seriesVisibleInLegend))
    {
      return false;
    }
    if (!seriesVisibleInLegendList.equals(seriesVisibleInLegendList))
    {
      return false;
    }
    if (baseSeriesVisibleInLegend != baseSeriesVisibleInLegend) {
      return false;
    }
    if (!PaintUtilities.equal(paint, paint)) {
      return false;
    }
    if (!ObjectUtilities.equal(paintList, paintList)) {
      return false;
    }
    if (!PaintUtilities.equal(basePaint, basePaint)) {
      return false;
    }
    if (!PaintUtilities.equal(fillPaint, fillPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(fillPaintList, fillPaintList)) {
      return false;
    }
    if (!PaintUtilities.equal(baseFillPaint, baseFillPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(outlinePaint, outlinePaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(outlinePaintList, outlinePaintList))
    {
      return false;
    }
    if (!PaintUtilities.equal(baseOutlinePaint, baseOutlinePaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(stroke, stroke)) {
      return false;
    }
    if (!ObjectUtilities.equal(strokeList, strokeList)) {
      return false;
    }
    if (!ObjectUtilities.equal(baseStroke, baseStroke)) {
      return false;
    }
    if (!ObjectUtilities.equal(outlineStroke, outlineStroke)) {
      return false;
    }
    if (!ObjectUtilities.equal(outlineStrokeList, outlineStrokeList))
    {
      return false;
    }
    if (!ObjectUtilities.equal(baseOutlineStroke, baseOutlineStroke))
    {

      return false;
    }
    if (!ShapeUtilities.equal(shape, shape)) {
      return false;
    }
    if (!ObjectUtilities.equal(shapeList, shapeList)) {
      return false;
    }
    if (!ShapeUtilities.equal(baseShape, baseShape)) {
      return false;
    }
    if (!ObjectUtilities.equal(itemLabelsVisible, itemLabelsVisible))
    {
      return false;
    }
    if (!ObjectUtilities.equal(itemLabelsVisibleList, itemLabelsVisibleList))
    {
      return false;
    }
    if (!ObjectUtilities.equal(baseItemLabelsVisible, baseItemLabelsVisible))
    {
      return false;
    }
    if (!ObjectUtilities.equal(itemLabelFont, itemLabelFont)) {
      return false;
    }
    if (!ObjectUtilities.equal(itemLabelFontList, itemLabelFontList))
    {
      return false;
    }
    if (!ObjectUtilities.equal(baseItemLabelFont, baseItemLabelFont))
    {
      return false;
    }
    
    if (!PaintUtilities.equal(itemLabelPaint, itemLabelPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(itemLabelPaintList, itemLabelPaintList))
    {
      return false;
    }
    if (!PaintUtilities.equal(baseItemLabelPaint, baseItemLabelPaint))
    {
      return false;
    }
    
    if (!ObjectUtilities.equal(positiveItemLabelPosition, positiveItemLabelPosition))
    {
      return false;
    }
    if (!ObjectUtilities.equal(positiveItemLabelPositionList, positiveItemLabelPositionList))
    {
      return false;
    }
    if (!ObjectUtilities.equal(basePositiveItemLabelPosition, basePositiveItemLabelPosition))
    {
      return false;
    }
    
    if (!ObjectUtilities.equal(negativeItemLabelPosition, negativeItemLabelPosition))
    {
      return false;
    }
    if (!ObjectUtilities.equal(negativeItemLabelPositionList, negativeItemLabelPositionList))
    {
      return false;
    }
    if (!ObjectUtilities.equal(baseNegativeItemLabelPosition, baseNegativeItemLabelPosition))
    {
      return false;
    }
    if (itemLabelAnchorOffset != itemLabelAnchorOffset) {
      return false;
    }
    if (!ObjectUtilities.equal(createEntities, createEntities)) {
      return false;
    }
    if (!ObjectUtilities.equal(createEntitiesList, createEntitiesList))
    {
      return false;
    }
    if (baseCreateEntities != baseCreateEntities) {
      return false;
    }
    if (!ObjectUtilities.equal(legendShape, legendShape)) {
      return false;
    }
    if (!ShapeUtilities.equal(baseLegendShape, baseLegendShape))
    {
      return false;
    }
    if (!ObjectUtilities.equal(legendTextFont, legendTextFont)) {
      return false;
    }
    if (!ObjectUtilities.equal(baseLegendTextFont, baseLegendTextFont))
    {
      return false;
    }
    if (!ObjectUtilities.equal(legendTextPaint, legendTextPaint))
    {
      return false;
    }
    if (!PaintUtilities.equal(baseLegendTextPaint, baseLegendTextPaint))
    {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 193;
    result = HashUtilities.hashCode(result, seriesVisibleList);
    result = HashUtilities.hashCode(result, baseSeriesVisible);
    result = HashUtilities.hashCode(result, seriesVisibleInLegendList);
    result = HashUtilities.hashCode(result, baseSeriesVisibleInLegend);
    result = HashUtilities.hashCode(result, paintList);
    result = HashUtilities.hashCode(result, basePaint);
    result = HashUtilities.hashCode(result, fillPaintList);
    result = HashUtilities.hashCode(result, baseFillPaint);
    result = HashUtilities.hashCode(result, outlinePaintList);
    result = HashUtilities.hashCode(result, baseOutlinePaint);
    result = HashUtilities.hashCode(result, strokeList);
    result = HashUtilities.hashCode(result, baseStroke);
    result = HashUtilities.hashCode(result, outlineStrokeList);
    result = HashUtilities.hashCode(result, baseOutlineStroke);
    

    result = HashUtilities.hashCode(result, itemLabelsVisibleList);
    result = HashUtilities.hashCode(result, baseItemLabelsVisible);
    










    return result;
  }
  






  protected Object clone()
    throws CloneNotSupportedException
  {
    AbstractRenderer clone = (AbstractRenderer)super.clone();
    
    if (seriesVisibleList != null) {
      seriesVisibleList = ((BooleanList)seriesVisibleList.clone());
    }
    

    if (seriesVisibleInLegendList != null) {
      seriesVisibleInLegendList = ((BooleanList)seriesVisibleInLegendList.clone());
    }
    


    if (paintList != null) {
      paintList = ((PaintList)paintList.clone());
    }
    

    if (fillPaintList != null) {
      fillPaintList = ((PaintList)fillPaintList.clone());
    }
    
    if (outlinePaintList != null) {
      outlinePaintList = ((PaintList)outlinePaintList.clone());
    }
    


    if (strokeList != null) {
      strokeList = ((StrokeList)strokeList.clone());
    }
    


    if (outlineStrokeList != null) {
      outlineStrokeList = ((StrokeList)outlineStrokeList.clone());
    }
    


    if (shape != null) {
      shape = ShapeUtilities.clone(shape);
    }
    if (shapeList != null) {
      shapeList = ((ShapeList)shapeList.clone());
    }
    if (baseShape != null) {
      baseShape = ShapeUtilities.clone(baseShape);
    }
    

    if (itemLabelsVisibleList != null) {
      itemLabelsVisibleList = ((BooleanList)itemLabelsVisibleList.clone());
    }
    



    if (itemLabelFontList != null) {
      itemLabelFontList = ((ObjectList)itemLabelFontList.clone());
    }
    



    if (itemLabelPaintList != null) {
      itemLabelPaintList = ((PaintList)itemLabelPaintList.clone());
    }
    



    if (positiveItemLabelPositionList != null) {
      positiveItemLabelPositionList = ((ObjectList)positiveItemLabelPositionList.clone());
    }
    



    if (negativeItemLabelPositionList != null) {
      negativeItemLabelPositionList = ((ObjectList)negativeItemLabelPositionList.clone());
    }
    


    if (createEntitiesList != null) {
      createEntitiesList = ((BooleanList)createEntitiesList.clone());
    }
    

    if (legendShape != null) {
      legendShape = ((ShapeList)legendShape.clone());
    }
    if (legendTextFont != null) {
      legendTextFont = ((ObjectList)legendTextFont.clone());
    }
    if (legendTextPaint != null) {
      legendTextPaint = ((PaintList)legendTextPaint.clone());
    }
    listenerList = new EventListenerList();
    event = null;
    return clone;
  }
  






  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(paint, stream);
    SerialUtilities.writePaint(basePaint, stream);
    SerialUtilities.writePaint(fillPaint, stream);
    SerialUtilities.writePaint(baseFillPaint, stream);
    SerialUtilities.writePaint(outlinePaint, stream);
    SerialUtilities.writePaint(baseOutlinePaint, stream);
    SerialUtilities.writeStroke(stroke, stream);
    SerialUtilities.writeStroke(baseStroke, stream);
    SerialUtilities.writeStroke(outlineStroke, stream);
    SerialUtilities.writeStroke(baseOutlineStroke, stream);
    SerialUtilities.writeShape(shape, stream);
    SerialUtilities.writeShape(baseShape, stream);
    SerialUtilities.writePaint(itemLabelPaint, stream);
    SerialUtilities.writePaint(baseItemLabelPaint, stream);
    SerialUtilities.writeShape(baseLegendShape, stream);
    SerialUtilities.writePaint(baseLegendTextPaint, stream);
  }
  









  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    paint = SerialUtilities.readPaint(stream);
    basePaint = SerialUtilities.readPaint(stream);
    fillPaint = SerialUtilities.readPaint(stream);
    baseFillPaint = SerialUtilities.readPaint(stream);
    outlinePaint = SerialUtilities.readPaint(stream);
    baseOutlinePaint = SerialUtilities.readPaint(stream);
    stroke = SerialUtilities.readStroke(stream);
    baseStroke = SerialUtilities.readStroke(stream);
    outlineStroke = SerialUtilities.readStroke(stream);
    baseOutlineStroke = SerialUtilities.readStroke(stream);
    shape = SerialUtilities.readShape(stream);
    baseShape = SerialUtilities.readShape(stream);
    itemLabelPaint = SerialUtilities.readPaint(stream);
    baseItemLabelPaint = SerialUtilities.readPaint(stream);
    baseLegendShape = SerialUtilities.readShape(stream);
    baseLegendTextPaint = SerialUtilities.readPaint(stream);
    


    listenerList = new EventListenerList();
  }
  








  /**
   * @deprecated
   */
  private transient Paint fillPaint;
  







  /**
   * @deprecated
   */
  private transient Paint outlinePaint;
  






  /**
   * @deprecated
   */
  private transient Stroke stroke;
  






  /**
   * @deprecated
   */
  private transient Stroke outlineStroke;
  






  /**
   * @deprecated
   */
  private transient Shape shape;
  






  /**
   * @deprecated
   */
  private Boolean itemLabelsVisible;
  






  /**
   * @deprecated
   */
  private Font itemLabelFont;
  






  /**
   * @deprecated
   */
  private transient Paint itemLabelPaint;
  






  /**
   * @deprecated
   */
  private ItemLabelPosition positiveItemLabelPosition;
  






  /**
   * @deprecated
   */
  private ItemLabelPosition negativeItemLabelPosition;
  






  /**
   * @deprecated
   */
  private Boolean createEntities;
  






  /**
   * @deprecated
   */
  public Boolean getSeriesVisible()
  {
    return seriesVisible;
  }
  










  /**
   * @deprecated
   */
  public void setSeriesVisible(Boolean visible)
  {
    setSeriesVisible(visible, true);
  }
  











  /**
   * @deprecated
   */
  public void setSeriesVisible(Boolean visible, boolean notify)
  {
    seriesVisible = visible;
    if (notify)
    {



      RendererChangeEvent e = new RendererChangeEvent(this, true);
      notifyListeners(e);
    }
  }
  










  /**
   * @deprecated
   */
  public Boolean getSeriesVisibleInLegend()
  {
    return seriesVisibleInLegend;
  }
  










  /**
   * @deprecated
   */
  public void setSeriesVisibleInLegend(Boolean visible)
  {
    setSeriesVisibleInLegend(visible, true);
  }
  












  /**
   * @deprecated
   */
  public void setSeriesVisibleInLegend(Boolean visible, boolean notify)
  {
    seriesVisibleInLegend = visible;
    if (notify) {
      fireChangeEvent();
    }
  }
  







  /**
   * @deprecated
   */
  public void setPaint(Paint paint)
  {
    setPaint(paint, true);
  }
  







  /**
   * @deprecated
   */
  public void setPaint(Paint paint, boolean notify)
  {
    this.paint = paint;
    if (notify) {
      fireChangeEvent();
    }
  }
  





  /**
   * @deprecated
   */
  public void setFillPaint(Paint paint)
  {
    setFillPaint(paint, true);
  }
  







  /**
   * @deprecated
   */
  public void setFillPaint(Paint paint, boolean notify)
  {
    fillPaint = paint;
    if (notify) {
      fireChangeEvent();
    }
  }
  






  /**
   * @deprecated
   */
  public void setOutlinePaint(Paint paint)
  {
    setOutlinePaint(paint, true);
  }
  







  /**
   * @deprecated
   */
  public void setOutlinePaint(Paint paint, boolean notify)
  {
    outlinePaint = paint;
    if (notify) {
      fireChangeEvent();
    }
  }
  






  /**
   * @deprecated
   */
  public void setStroke(Stroke stroke)
  {
    setStroke(stroke, true);
  }
  







  /**
   * @deprecated
   */
  public void setStroke(Stroke stroke, boolean notify)
  {
    this.stroke = stroke;
    if (notify) {
      fireChangeEvent();
    }
  }
  






  /**
   * @deprecated
   */
  public void setOutlineStroke(Stroke stroke)
  {
    setOutlineStroke(stroke, true);
  }
  







  /**
   * @deprecated
   */
  public void setOutlineStroke(Stroke stroke, boolean notify)
  {
    outlineStroke = stroke;
    if (notify) {
      fireChangeEvent();
    }
  }
  






  /**
   * @deprecated
   */
  public void setShape(Shape shape)
  {
    setShape(shape, true);
  }
  







  /**
   * @deprecated
   */
  public void setShape(Shape shape, boolean notify)
  {
    this.shape = shape;
    if (notify) {
      fireChangeEvent();
    }
  }
  





  /**
   * @deprecated
   */
  public void setItemLabelsVisible(boolean visible)
  {
    setItemLabelsVisible(BooleanUtilities.valueOf(visible));
  }
  








  /**
   * @deprecated
   */
  public void setItemLabelsVisible(Boolean visible)
  {
    setItemLabelsVisible(visible, true);
  }
  










  /**
   * @deprecated
   */
  public void setItemLabelsVisible(Boolean visible, boolean notify)
  {
    itemLabelsVisible = visible;
    if (notify) {
      fireChangeEvent();
    }
  }
  






  /**
   * @deprecated
   */
  public Font getItemLabelFont()
  {
    return itemLabelFont;
  }
  








  /**
   * @deprecated
   */
  public void setItemLabelFont(Font font)
  {
    setItemLabelFont(font, true);
  }
  








  /**
   * @deprecated
   */
  public void setItemLabelFont(Font font, boolean notify)
  {
    itemLabelFont = font;
    if (notify) {
      fireChangeEvent();
    }
  }
  







  /**
   * @deprecated
   */
  public Paint getItemLabelPaint()
  {
    return itemLabelPaint;
  }
  






  /**
   * @deprecated
   */
  public void setItemLabelPaint(Paint paint)
  {
    setItemLabelPaint(paint, true);
  }
  








  /**
   * @deprecated
   */
  public void setItemLabelPaint(Paint paint, boolean notify)
  {
    itemLabelPaint = paint;
    if (notify) {
      fireChangeEvent();
    }
  }
  








  /**
   * @deprecated
   */
  public ItemLabelPosition getPositiveItemLabelPosition()
  {
    return positiveItemLabelPosition;
  }
  











  /**
   * @deprecated
   */
  public void setPositiveItemLabelPosition(ItemLabelPosition position)
  {
    setPositiveItemLabelPosition(position, true);
  }
  












  /**
   * @deprecated
   */
  public void setPositiveItemLabelPosition(ItemLabelPosition position, boolean notify)
  {
    positiveItemLabelPosition = position;
    if (notify) {
      fireChangeEvent();
    }
  }
  








  /**
   * @deprecated
   */
  public ItemLabelPosition getNegativeItemLabelPosition()
  {
    return negativeItemLabelPosition;
  }
  











  /**
   * @deprecated
   */
  public void setNegativeItemLabelPosition(ItemLabelPosition position)
  {
    setNegativeItemLabelPosition(position, true);
  }
  













  /**
   * @deprecated
   */
  public void setNegativeItemLabelPosition(ItemLabelPosition position, boolean notify)
  {
    negativeItemLabelPosition = position;
    if (notify) {
      fireChangeEvent();
    }
  }
  








  /**
   * @deprecated
   */
  public Boolean getCreateEntities()
  {
    return createEntities;
  }
  









  /**
   * @deprecated
   */
  public void setCreateEntities(Boolean create)
  {
    setCreateEntities(create, true);
  }
  










  /**
   * @deprecated
   */
  public void setCreateEntities(Boolean create, boolean notify)
  {
    createEntities = create;
    if (notify) {
      fireChangeEvent();
    }
  }
}
