package org.jfree.chart.plot;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.CubicCurve2D.Float;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Line2D.Float;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.QuadCurve2D.Float;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.PaintMap;
import org.jfree.chart.StrokeMap;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.PieDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.text.G2TextMeasurer;
import org.jfree.text.TextBlock;
import org.jfree.text.TextBox;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.Rotation;
import org.jfree.util.ShapeUtilities;
import org.jfree.util.UnitType;


































































































































































































public class PiePlot
  extends Plot
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -795612466005590431L;
  public static final double DEFAULT_INTERIOR_GAP = 0.08D;
  public static final double MAX_INTERIOR_GAP = 0.4D;
  public static final double DEFAULT_START_ANGLE = 90.0D;
  public static final Font DEFAULT_LABEL_FONT = new Font("SansSerif", 0, 10);
  


  public static final Paint DEFAULT_LABEL_PAINT = Color.black;
  

  public static final Paint DEFAULT_LABEL_BACKGROUND_PAINT = new Color(255, 255, 192);
  


  public static final Paint DEFAULT_LABEL_OUTLINE_PAINT = Color.black;
  

  public static final Stroke DEFAULT_LABEL_OUTLINE_STROKE = new BasicStroke(0.5F);
  


  public static final Paint DEFAULT_LABEL_SHADOW_PAINT = new Color(151, 151, 151, 128);
  



  public static final double DEFAULT_MINIMUM_ARC_ANGLE_TO_DRAW = 1.0E-5D;
  



  private PieDataset dataset;
  



  private int pieIndex;
  



  private double interiorGap;
  


  private boolean circular;
  


  private double startAngle;
  


  private Rotation direction;
  


  private PaintMap sectionPaintMap;
  


  private transient Paint baseSectionPaint;
  


  private boolean autoPopulateSectionPaint;
  


  private boolean sectionOutlinesVisible;
  


  private PaintMap sectionOutlinePaintMap;
  


  private transient Paint baseSectionOutlinePaint;
  


  private boolean autoPopulateSectionOutlinePaint;
  


  private StrokeMap sectionOutlineStrokeMap;
  


  private transient Stroke baseSectionOutlineStroke;
  


  private boolean autoPopulateSectionOutlineStroke;
  


  private transient Paint shadowPaint = Color.gray;
  

  private double shadowXOffset = 4.0D;
  

  private double shadowYOffset = 4.0D;
  



  private Map explodePercentages;
  



  private PieSectionLabelGenerator labelGenerator;
  



  private Font labelFont;
  



  private transient Paint labelPaint;
  



  private transient Paint labelBackgroundPaint;
  



  private transient Paint labelOutlinePaint;
  



  private transient Stroke labelOutlineStroke;
  


  private transient Paint labelShadowPaint;
  


  private boolean simpleLabels = true;
  




  private RectangleInsets labelPadding;
  




  private RectangleInsets simpleLabelOffset;
  




  private double maximumLabelWidth = 0.14D;
  




  private double labelGap = 0.025D;
  



  private boolean labelLinksVisible;
  



  private PieLabelLinkStyle labelLinkStyle = PieLabelLinkStyle.STANDARD;
  

  private double labelLinkMargin = 0.025D;
  

  private transient Paint labelLinkPaint = Color.black;
  

  private transient Stroke labelLinkStroke = new BasicStroke(0.5F);
  



  private AbstractPieLabelDistributor labelDistributor;
  



  private PieToolTipGenerator toolTipGenerator;
  



  private PieURLGenerator urlGenerator;
  



  private PieSectionLabelGenerator legendLabelGenerator;
  



  private PieSectionLabelGenerator legendLabelToolTipGenerator;
  



  private PieURLGenerator legendLabelURLGenerator;
  



  private boolean ignoreNullValues;
  



  private boolean ignoreZeroValues;
  



  private transient Shape legendItemShape;
  



  private double minimumArcAngleToDraw;
  



  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
  

  static final boolean DEBUG_DRAW_INTERIOR = false;
  

  static final boolean DEBUG_DRAW_LINK_AREA = false;
  
  static final boolean DEBUG_DRAW_PIE_AREA = false;
  
  /**
   * @deprecated
   */
  private transient Paint sectionPaint;
  
  /**
   * @deprecated
   */
  private transient Paint sectionOutlinePaint;
  
  /**
   * @deprecated
   */
  private transient Stroke sectionOutlineStroke;
  

  public PiePlot()
  {
    this(null);
  }
  





  public PiePlot(PieDataset dataset)
  {
    this.dataset = dataset;
    if (dataset != null) {
      dataset.addChangeListener(this);
    }
    pieIndex = 0;
    
    interiorGap = 0.08D;
    circular = true;
    startAngle = 90.0D;
    direction = Rotation.CLOCKWISE;
    minimumArcAngleToDraw = 1.0E-5D;
    
    sectionPaint = null;
    sectionPaintMap = new PaintMap();
    baseSectionPaint = Color.gray;
    autoPopulateSectionPaint = true;
    
    sectionOutlinesVisible = true;
    sectionOutlinePaint = null;
    sectionOutlinePaintMap = new PaintMap();
    baseSectionOutlinePaint = DEFAULT_OUTLINE_PAINT;
    autoPopulateSectionOutlinePaint = false;
    
    sectionOutlineStroke = null;
    sectionOutlineStrokeMap = new StrokeMap();
    baseSectionOutlineStroke = DEFAULT_OUTLINE_STROKE;
    autoPopulateSectionOutlineStroke = false;
    
    explodePercentages = new TreeMap();
    
    labelGenerator = new StandardPieSectionLabelGenerator();
    labelFont = DEFAULT_LABEL_FONT;
    labelPaint = DEFAULT_LABEL_PAINT;
    labelBackgroundPaint = DEFAULT_LABEL_BACKGROUND_PAINT;
    labelOutlinePaint = DEFAULT_LABEL_OUTLINE_PAINT;
    labelOutlineStroke = DEFAULT_LABEL_OUTLINE_STROKE;
    labelShadowPaint = DEFAULT_LABEL_SHADOW_PAINT;
    labelLinksVisible = true;
    labelDistributor = new PieLabelDistributor(0);
    
    simpleLabels = false;
    simpleLabelOffset = new RectangleInsets(UnitType.RELATIVE, 0.18D, 0.18D, 0.18D, 0.18D);
    
    labelPadding = new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D);
    
    toolTipGenerator = null;
    urlGenerator = null;
    legendLabelGenerator = new StandardPieSectionLabelGenerator();
    legendLabelToolTipGenerator = null;
    legendLabelURLGenerator = null;
    legendItemShape = Plot.DEFAULT_LEGEND_ITEM_CIRCLE;
    
    ignoreNullValues = false;
    ignoreZeroValues = false;
  }
  






  public PieDataset getDataset()
  {
    return dataset;
  }
  








  public void setDataset(PieDataset dataset)
  {
    PieDataset existing = this.dataset;
    if (existing != null) {
      existing.removeChangeListener(this);
    }
    

    this.dataset = dataset;
    if (dataset != null) {
      setDatasetGroup(dataset.getGroup());
      dataset.addChangeListener(this);
    }
    

    DatasetChangeEvent event = new DatasetChangeEvent(this, dataset);
    datasetChanged(event);
  }
  







  public int getPieIndex()
  {
    return pieIndex;
  }
  







  public void setPieIndex(int index)
  {
    pieIndex = index;
  }
  







  public double getStartAngle()
  {
    return startAngle;
  }
  









  public void setStartAngle(double angle)
  {
    startAngle = angle;
    fireChangeEvent();
  }
  







  public Rotation getDirection()
  {
    return direction;
  }
  







  public void setDirection(Rotation direction)
  {
    if (direction == null) {
      throw new IllegalArgumentException("Null 'direction' argument.");
    }
    this.direction = direction;
    fireChangeEvent();
  }
  








  public double getInteriorGap()
  {
    return interiorGap;
  }
  










  public void setInteriorGap(double percent)
  {
    if ((percent < 0.0D) || (percent > 0.4D)) {
      throw new IllegalArgumentException("Invalid 'percent' (" + percent + ") argument.");
    }
    

    if (interiorGap != percent) {
      interiorGap = percent;
      fireChangeEvent();
    }
  }
  








  public boolean isCircular()
  {
    return circular;
  }
  







  public void setCircular(boolean flag)
  {
    setCircular(flag, true);
  }
  








  public void setCircular(boolean circular, boolean notify)
  {
    this.circular = circular;
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public boolean getIgnoreNullValues()
  {
    return ignoreNullValues;
  }
  










  public void setIgnoreNullValues(boolean flag)
  {
    ignoreNullValues = flag;
    fireChangeEvent();
  }
  







  public boolean getIgnoreZeroValues()
  {
    return ignoreZeroValues;
  }
  










  public void setIgnoreZeroValues(boolean flag)
  {
    ignoreZeroValues = flag;
    fireChangeEvent();
  }
  













  protected Paint lookupSectionPaint(Comparable key)
  {
    return lookupSectionPaint(key, getAutoPopulateSectionPaint());
  }
  
























  protected Paint lookupSectionPaint(Comparable key, boolean autoPopulate)
  {
    Paint result = getSectionPaint();
    if (result != null) {
      return result;
    }
    

    result = sectionPaintMap.getPaint(key);
    if (result != null) {
      return result;
    }
    

    if (autoPopulate) {
      DrawingSupplier ds = getDrawingSupplier();
      if (ds != null) {
        result = ds.getNextPaint();
        sectionPaintMap.put(key, result);
      }
      else {
        result = baseSectionPaint;
      }
    }
    else {
      result = baseSectionPaint;
    }
    return result;
  }
  






  /**
   * @deprecated
   */
  public Paint getSectionPaint()
  {
    return sectionPaint;
  }
  








  /**
   * @deprecated
   */
  public void setSectionPaint(Paint paint)
  {
    sectionPaint = paint;
    fireChangeEvent();
  }
  













  protected Comparable getSectionKey(int section)
  {
    Comparable key = null;
    if ((dataset != null) && 
      (section >= 0) && (section < dataset.getItemCount())) {
      key = dataset.getKey(section);
    }
    
    if (key == null) {
      key = new Integer(section);
    }
    return key;
  }
  
















  public Paint getSectionPaint(Comparable key)
  {
    return sectionPaintMap.getPaint(key);
  }
  














  public void setSectionPaint(Comparable key, Paint paint)
  {
    sectionPaintMap.put(key, paint);
    fireChangeEvent();
  }
  











  public void clearSectionPaints(boolean notify)
  {
    sectionPaintMap.clear();
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public Paint getBaseSectionPaint()
  {
    return baseSectionPaint;
  }
  







  public void setBaseSectionPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    baseSectionPaint = paint;
    fireChangeEvent();
  }
  







  public boolean getAutoPopulateSectionPaint()
  {
    return autoPopulateSectionPaint;
  }
  








  public void setAutoPopulateSectionPaint(boolean auto)
  {
    autoPopulateSectionPaint = auto;
    fireChangeEvent();
  }
  










  public boolean getSectionOutlinesVisible()
  {
    return sectionOutlinesVisible;
  }
  








  public void setSectionOutlinesVisible(boolean visible)
  {
    sectionOutlinesVisible = visible;
    fireChangeEvent();
  }
  












  protected Paint lookupSectionOutlinePaint(Comparable key)
  {
    return lookupSectionOutlinePaint(key, getAutoPopulateSectionOutlinePaint());
  }
  


























  protected Paint lookupSectionOutlinePaint(Comparable key, boolean autoPopulate)
  {
    Paint result = getSectionOutlinePaint();
    if (result != null) {
      return result;
    }
    

    result = sectionOutlinePaintMap.getPaint(key);
    if (result != null) {
      return result;
    }
    

    if (autoPopulate) {
      DrawingSupplier ds = getDrawingSupplier();
      if (ds != null) {
        result = ds.getNextOutlinePaint();
        sectionOutlinePaintMap.put(key, result);
      }
      else {
        result = baseSectionOutlinePaint;
      }
    }
    else {
      result = baseSectionOutlinePaint;
    }
    return result;
  }
  
















  public Paint getSectionOutlinePaint(Comparable key)
  {
    return sectionOutlinePaintMap.getPaint(key);
  }
  














  public void setSectionOutlinePaint(Comparable key, Paint paint)
  {
    sectionOutlinePaintMap.put(key, paint);
    fireChangeEvent();
  }
  











  public void clearSectionOutlinePaints(boolean notify)
  {
    sectionOutlinePaintMap.clear();
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public Paint getBaseSectionOutlinePaint()
  {
    return baseSectionOutlinePaint;
  }
  






  public void setBaseSectionOutlinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    baseSectionOutlinePaint = paint;
    fireChangeEvent();
  }
  








  public boolean getAutoPopulateSectionOutlinePaint()
  {
    return autoPopulateSectionOutlinePaint;
  }
  








  public void setAutoPopulateSectionOutlinePaint(boolean auto)
  {
    autoPopulateSectionOutlinePaint = auto;
    fireChangeEvent();
  }
  














  protected Stroke lookupSectionOutlineStroke(Comparable key)
  {
    return lookupSectionOutlineStroke(key, getAutoPopulateSectionOutlineStroke());
  }
  


























  protected Stroke lookupSectionOutlineStroke(Comparable key, boolean autoPopulate)
  {
    Stroke result = getSectionOutlineStroke();
    if (result != null) {
      return result;
    }
    

    result = sectionOutlineStrokeMap.getStroke(key);
    if (result != null) {
      return result;
    }
    

    if (autoPopulate) {
      DrawingSupplier ds = getDrawingSupplier();
      if (ds != null) {
        result = ds.getNextOutlineStroke();
        sectionOutlineStrokeMap.put(key, result);
      }
      else {
        result = baseSectionOutlineStroke;
      }
    }
    else {
      result = baseSectionOutlineStroke;
    }
    return result;
  }
  
















  public Stroke getSectionOutlineStroke(Comparable key)
  {
    return sectionOutlineStrokeMap.getStroke(key);
  }
  














  public void setSectionOutlineStroke(Comparable key, Stroke stroke)
  {
    sectionOutlineStrokeMap.put(key, stroke);
    fireChangeEvent();
  }
  











  public void clearSectionOutlineStrokes(boolean notify)
  {
    sectionOutlineStrokeMap.clear();
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public Stroke getBaseSectionOutlineStroke()
  {
    return baseSectionOutlineStroke;
  }
  






  public void setBaseSectionOutlineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    baseSectionOutlineStroke = stroke;
    fireChangeEvent();
  }
  








  public boolean getAutoPopulateSectionOutlineStroke()
  {
    return autoPopulateSectionOutlineStroke;
  }
  








  public void setAutoPopulateSectionOutlineStroke(boolean auto)
  {
    autoPopulateSectionOutlineStroke = auto;
    fireChangeEvent();
  }
  






  public Paint getShadowPaint()
  {
    return shadowPaint;
  }
  







  public void setShadowPaint(Paint paint)
  {
    shadowPaint = paint;
    fireChangeEvent();
  }
  






  public double getShadowXOffset()
  {
    return shadowXOffset;
  }
  







  public void setShadowXOffset(double offset)
  {
    shadowXOffset = offset;
    fireChangeEvent();
  }
  






  public double getShadowYOffset()
  {
    return shadowYOffset;
  }
  







  public void setShadowYOffset(double offset)
  {
    shadowYOffset = offset;
    fireChangeEvent();
  }
  















  public double getExplodePercent(Comparable key)
  {
    double result = 0.0D;
    if (explodePercentages != null) {
      Number percent = (Number)explodePercentages.get(key);
      if (percent != null) {
        result = percent.doubleValue();
      }
    }
    return result;
  }
  










  public void setExplodePercent(Comparable key, double percent)
  {
    if (key == null) {
      throw new IllegalArgumentException("Null 'key' argument.");
    }
    if (explodePercentages == null) {
      explodePercentages = new TreeMap();
    }
    explodePercentages.put(key, new Double(percent));
    fireChangeEvent();
  }
  




  public double getMaximumExplodePercent()
  {
    if (dataset == null) {
      return 0.0D;
    }
    double result = 0.0D;
    Iterator iterator = dataset.getKeys().iterator();
    while (iterator.hasNext()) {
      Comparable key = (Comparable)iterator.next();
      Number explode = (Number)explodePercentages.get(key);
      if (explode != null) {
        result = Math.max(result, explode.doubleValue());
      }
    }
    return result;
  }
  






  public PieSectionLabelGenerator getLabelGenerator()
  {
    return labelGenerator;
  }
  







  public void setLabelGenerator(PieSectionLabelGenerator generator)
  {
    labelGenerator = generator;
    fireChangeEvent();
  }
  







  public double getLabelGap()
  {
    return labelGap;
  }
  








  public void setLabelGap(double gap)
  {
    labelGap = gap;
    fireChangeEvent();
  }
  






  public double getMaximumLabelWidth()
  {
    return maximumLabelWidth;
  }
  







  public void setMaximumLabelWidth(double width)
  {
    maximumLabelWidth = width;
    fireChangeEvent();
  }
  







  public boolean getLabelLinksVisible()
  {
    return labelLinksVisible;
  }
  










  public void setLabelLinksVisible(boolean visible)
  {
    labelLinksVisible = visible;
    fireChangeEvent();
  }
  








  public PieLabelLinkStyle getLabelLinkStyle()
  {
    return labelLinkStyle;
  }
  









  public void setLabelLinkStyle(PieLabelLinkStyle style)
  {
    if (style == null) {
      throw new IllegalArgumentException("Null 'style' argument.");
    }
    labelLinkStyle = style;
    fireChangeEvent();
  }
  







  public double getLabelLinkMargin()
  {
    return labelLinkMargin;
  }
  







  public void setLabelLinkMargin(double margin)
  {
    labelLinkMargin = margin;
    fireChangeEvent();
  }
  







  public Paint getLabelLinkPaint()
  {
    return labelLinkPaint;
  }
  








  public void setLabelLinkPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    labelLinkPaint = paint;
    fireChangeEvent();
  }
  






  public Stroke getLabelLinkStroke()
  {
    return labelLinkStroke;
  }
  







  public void setLabelLinkStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    labelLinkStroke = stroke;
    fireChangeEvent();
  }
  










  protected double getLabelLinkDepth()
  {
    return 0.1D;
  }
  






  public Font getLabelFont()
  {
    return labelFont;
  }
  







  public void setLabelFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    labelFont = font;
    fireChangeEvent();
  }
  






  public Paint getLabelPaint()
  {
    return labelPaint;
  }
  







  public void setLabelPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    labelPaint = paint;
    fireChangeEvent();
  }
  






  public Paint getLabelBackgroundPaint()
  {
    return labelBackgroundPaint;
  }
  







  public void setLabelBackgroundPaint(Paint paint)
  {
    labelBackgroundPaint = paint;
    fireChangeEvent();
  }
  






  public Paint getLabelOutlinePaint()
  {
    return labelOutlinePaint;
  }
  







  public void setLabelOutlinePaint(Paint paint)
  {
    labelOutlinePaint = paint;
    fireChangeEvent();
  }
  






  public Stroke getLabelOutlineStroke()
  {
    return labelOutlineStroke;
  }
  







  public void setLabelOutlineStroke(Stroke stroke)
  {
    labelOutlineStroke = stroke;
    fireChangeEvent();
  }
  






  public Paint getLabelShadowPaint()
  {
    return labelShadowPaint;
  }
  







  public void setLabelShadowPaint(Paint paint)
  {
    labelShadowPaint = paint;
    fireChangeEvent();
  }
  








  public RectangleInsets getLabelPadding()
  {
    return labelPadding;
  }
  









  public void setLabelPadding(RectangleInsets padding)
  {
    if (padding == null) {
      throw new IllegalArgumentException("Null 'padding' argument.");
    }
    labelPadding = padding;
    fireChangeEvent();
  }
  







  public boolean getSimpleLabels()
  {
    return simpleLabels;
  }
  








  public void setSimpleLabels(boolean simple)
  {
    simpleLabels = simple;
    fireChangeEvent();
  }
  








  public RectangleInsets getSimpleLabelOffset()
  {
    return simpleLabelOffset;
  }
  









  public void setSimpleLabelOffset(RectangleInsets offset)
  {
    if (offset == null) {
      throw new IllegalArgumentException("Null 'offset' argument.");
    }
    simpleLabelOffset = offset;
    fireChangeEvent();
  }
  







  public AbstractPieLabelDistributor getLabelDistributor()
  {
    return labelDistributor;
  }
  







  public void setLabelDistributor(AbstractPieLabelDistributor distributor)
  {
    if (distributor == null) {
      throw new IllegalArgumentException("Null 'distributor' argument.");
    }
    labelDistributor = distributor;
    fireChangeEvent();
  }
  








  public PieToolTipGenerator getToolTipGenerator()
  {
    return toolTipGenerator;
  }
  








  public void setToolTipGenerator(PieToolTipGenerator generator)
  {
    toolTipGenerator = generator;
    fireChangeEvent();
  }
  






  public PieURLGenerator getURLGenerator()
  {
    return urlGenerator;
  }
  







  public void setURLGenerator(PieURLGenerator generator)
  {
    urlGenerator = generator;
    fireChangeEvent();
  }
  







  public double getMinimumArcAngleToDraw()
  {
    return minimumArcAngleToDraw;
  }
  

















  public void setMinimumArcAngleToDraw(double angle)
  {
    minimumArcAngleToDraw = angle;
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
  






  public PieSectionLabelGenerator getLegendLabelGenerator()
  {
    return legendLabelGenerator;
  }
  







  public void setLegendLabelGenerator(PieSectionLabelGenerator generator)
  {
    if (generator == null) {
      throw new IllegalArgumentException("Null 'generator' argument.");
    }
    legendLabelGenerator = generator;
    fireChangeEvent();
  }
  






  public PieSectionLabelGenerator getLegendLabelToolTipGenerator()
  {
    return legendLabelToolTipGenerator;
  }
  








  public void setLegendLabelToolTipGenerator(PieSectionLabelGenerator generator)
  {
    legendLabelToolTipGenerator = generator;
    fireChangeEvent();
  }
  








  public PieURLGenerator getLegendLabelURLGenerator()
  {
    return legendLabelURLGenerator;
  }
  









  public void setLegendLabelURLGenerator(PieURLGenerator generator)
  {
    legendLabelURLGenerator = generator;
    fireChangeEvent();
  }
  
















  public PiePlotState initialise(Graphics2D g2, Rectangle2D plotArea, PiePlot plot, Integer index, PlotRenderingInfo info)
  {
    PiePlotState state = new PiePlotState(info);
    state.setPassesRequired(2);
    if (dataset != null) {
      state.setTotal(DatasetUtilities.calculatePieDatasetTotal(plot.getDataset()));
    }
    
    state.setLatestAngle(plot.getStartAngle());
    return state;
  }
  














  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info)
  {
    RectangleInsets insets = getInsets();
    insets.trim(area);
    
    if (info != null) {
      info.setPlotArea(area);
      info.setDataArea(area);
    }
    
    drawBackground(g2, area);
    drawOutline(g2, area);
    
    Shape savedClip = g2.getClip();
    g2.clip(area);
    
    Composite originalComposite = g2.getComposite();
    g2.setComposite(AlphaComposite.getInstance(3, getForegroundAlpha()));
    

    if (!DatasetUtilities.isEmptyOrNull(dataset)) {
      drawPie(g2, area, info);
    }
    else {
      drawNoDataMessage(g2, area);
    }
    
    g2.setClip(savedClip);
    g2.setComposite(originalComposite);
    
    drawOutline(g2, area);
  }
  









  protected void drawPie(Graphics2D g2, Rectangle2D plotArea, PlotRenderingInfo info)
  {
    PiePlotState state = initialise(g2, plotArea, this, null, info);
    

    double labelReserve = 0.0D;
    if ((labelGenerator != null) && (!simpleLabels)) {
      labelReserve = labelGap + maximumLabelWidth;
    }
    double gapHorizontal = plotArea.getWidth() * (interiorGap + labelReserve) * 2.0D;
    
    double gapVertical = plotArea.getHeight() * interiorGap * 2.0D;
    














    double linkX = plotArea.getX() + gapHorizontal / 2.0D;
    double linkY = plotArea.getY() + gapVertical / 2.0D;
    double linkW = plotArea.getWidth() - gapHorizontal;
    double linkH = plotArea.getHeight() - gapVertical;
    

    if (circular) {
      double min = Math.min(linkW, linkH) / 2.0D;
      linkX = (linkX + linkX + linkW) / 2.0D - min;
      linkY = (linkY + linkY + linkH) / 2.0D - min;
      linkW = 2.0D * min;
      linkH = 2.0D * min;
    }
    


    Rectangle2D linkArea = new Rectangle2D.Double(linkX, linkY, linkW, linkH);
    
    state.setLinkArea(linkArea);
    











    double lm = 0.0D;
    if (!simpleLabels) {
      lm = labelLinkMargin;
    }
    double hh = linkArea.getWidth() * lm * 2.0D;
    double vv = linkArea.getHeight() * lm * 2.0D;
    Rectangle2D explodeArea = new Rectangle2D.Double(linkX + hh / 2.0D, linkY + vv / 2.0D, linkW - hh, linkH - vv);
    

    state.setExplodedPieArea(explodeArea);
    



    double maximumExplodePercent = getMaximumExplodePercent();
    double percent = maximumExplodePercent / (1.0D + maximumExplodePercent);
    
    double h1 = explodeArea.getWidth() * percent;
    double v1 = explodeArea.getHeight() * percent;
    Rectangle2D pieArea = new Rectangle2D.Double(explodeArea.getX() + h1 / 2.0D, explodeArea.getY() + v1 / 2.0D, explodeArea.getWidth() - h1, explodeArea.getHeight() - v1);
    






    state.setPieArea(pieArea);
    state.setPieCenterX(pieArea.getCenterX());
    state.setPieCenterY(pieArea.getCenterY());
    state.setPieWRadius(pieArea.getWidth() / 2.0D);
    state.setPieHRadius(pieArea.getHeight() / 2.0D);
    

    if ((dataset != null) && (dataset.getKeys().size() > 0))
    {
      List keys = dataset.getKeys();
      double totalValue = DatasetUtilities.calculatePieDatasetTotal(dataset);
      

      int passesRequired = state.getPassesRequired();
      for (int pass = 0; pass < passesRequired; pass++) {
        double runningTotal = 0.0D;
        for (int section = 0; section < keys.size(); section++) {
          Number n = dataset.getValue(section);
          if (n != null) {
            double value = n.doubleValue();
            if (value > 0.0D) {
              runningTotal += value;
              drawItem(g2, section, explodeArea, state, pass);
            }
          }
        }
      }
      if (simpleLabels) {
        drawSimpleLabels(g2, keys, totalValue, plotArea, linkArea, state);
      }
      else
      {
        drawLabels(g2, keys, totalValue, plotArea, linkArea, state);
      }
    }
    else
    {
      drawNoDataMessage(g2, plotArea);
    }
  }
  










  protected void drawItem(Graphics2D g2, int section, Rectangle2D dataArea, PiePlotState state, int currentPass)
  {
    Number n = dataset.getValue(section);
    if (n == null) {
      return;
    }
    double value = n.doubleValue();
    double angle1 = 0.0D;
    double angle2 = 0.0D;
    
    if (direction == Rotation.CLOCKWISE) {
      angle1 = state.getLatestAngle();
      angle2 = angle1 - value / state.getTotal() * 360.0D;
    }
    else if (direction == Rotation.ANTICLOCKWISE) {
      angle1 = state.getLatestAngle();
      angle2 = angle1 + value / state.getTotal() * 360.0D;
    }
    else {
      throw new IllegalStateException("Rotation type not recognised.");
    }
    
    double angle = angle2 - angle1;
    if (Math.abs(angle) > getMinimumArcAngleToDraw()) {
      double ep = 0.0D;
      double mep = getMaximumExplodePercent();
      if (mep > 0.0D) {
        ep = getExplodePercent(section) / mep;
      }
      Rectangle2D arcBounds = getArcBounds(state.getPieArea(), state.getExplodedPieArea(), angle1, angle, ep);
      
      Arc2D.Double arc = new Arc2D.Double(arcBounds, angle1, angle, 2);
      

      if (currentPass == 0) {
        if (shadowPaint != null) {
          Shape shadowArc = ShapeUtilities.createTranslatedShape(arc, (float)shadowXOffset, (float)shadowYOffset);
          

          g2.setPaint(shadowPaint);
          g2.fill(shadowArc);
        }
      }
      else if (currentPass == 1) {
        Comparable key = getSectionKey(section);
        Paint paint = lookupSectionPaint(key);
        g2.setPaint(paint);
        g2.fill(arc);
        
        Paint outlinePaint = lookupSectionOutlinePaint(key);
        Stroke outlineStroke = lookupSectionOutlineStroke(key);
        if (sectionOutlinesVisible) {
          g2.setPaint(outlinePaint);
          g2.setStroke(outlineStroke);
          g2.draw(arc);
        }
        


        if (state.getInfo() != null) {
          EntityCollection entities = state.getEntityCollection();
          if (entities != null) {
            String tip = null;
            if (toolTipGenerator != null) {
              tip = toolTipGenerator.generateToolTip(dataset, key);
            }
            
            String url = null;
            if (urlGenerator != null) {
              url = urlGenerator.generateURL(dataset, key, pieIndex);
            }
            
            PieSectionEntity entity = new PieSectionEntity(arc, dataset, pieIndex, section, key, tip, url);
            

            entities.add(entity);
          }
        }
      }
    }
    state.setLatestAngle(angle2);
  }
  














  protected void drawSimpleLabels(Graphics2D g2, List keys, double totalValue, Rectangle2D plotArea, Rectangle2D pieArea, PiePlotState state)
  {
    Composite originalComposite = g2.getComposite();
    g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
    

    RectangleInsets labelInsets = new RectangleInsets(UnitType.RELATIVE, 0.18D, 0.18D, 0.18D, 0.18D);
    
    Rectangle2D labelsArea = labelInsets.createInsetRectangle(pieArea);
    double runningTotal = 0.0D;
    Iterator iterator = keys.iterator();
    while (iterator.hasNext()) {
      Comparable key = (Comparable)iterator.next();
      boolean include = true;
      double v = 0.0D;
      Number n = getDataset().getValue(key);
      if (n == null) {
        include = !getIgnoreNullValues();
      }
      else {
        v = n.doubleValue();
        include = v > 0.0D;
      }
      
      if (include) {
        runningTotal += v;
        

        double mid = getStartAngle() + getDirection().getFactor() * ((runningTotal - v / 2.0D) * 360.0D) / totalValue;
        

        Arc2D arc = new Arc2D.Double(labelsArea, getStartAngle(), mid - getStartAngle(), 0);
        
        int x = (int)arc.getEndPoint().getX();
        int y = (int)arc.getEndPoint().getY();
        
        PieSectionLabelGenerator labelGenerator = getLabelGenerator();
        if (labelGenerator != null)
        {

          String label = labelGenerator.generateSectionLabel(dataset, key);
          
          if (label != null)
          {

            g2.setFont(labelFont);
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D bounds = TextUtilities.getTextBounds(label, g2, fm);
            Rectangle2D out = labelPadding.createOutsetRectangle(bounds);
            
            Shape bg = ShapeUtilities.createTranslatedShape(out, x - bounds.getCenterX(), y - bounds.getCenterY());
            
            if (labelShadowPaint != null) {
              Shape shadow = ShapeUtilities.createTranslatedShape(bg, shadowXOffset, shadowYOffset);
              
              g2.setPaint(labelShadowPaint);
              g2.fill(shadow);
            }
            if (labelBackgroundPaint != null) {
              g2.setPaint(labelBackgroundPaint);
              g2.fill(bg);
            }
            if ((labelOutlinePaint != null) && (labelOutlineStroke != null))
            {
              g2.setPaint(labelOutlinePaint);
              g2.setStroke(labelOutlineStroke);
              g2.draw(bg);
            }
            
            g2.setPaint(labelPaint);
            g2.setFont(labelFont);
            TextUtilities.drawAlignedString(getLabelGenerator().generateSectionLabel(getDataset(), key), g2, x, y, TextAnchor.CENTER);
          }
        }
      }
    }
    

    g2.setComposite(originalComposite);
  }
  













  protected void drawLabels(Graphics2D g2, List keys, double totalValue, Rectangle2D plotArea, Rectangle2D linkArea, PiePlotState state)
  {
    Composite originalComposite = g2.getComposite();
    g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
    


    DefaultKeyedValues leftKeys = new DefaultKeyedValues();
    DefaultKeyedValues rightKeys = new DefaultKeyedValues();
    
    double runningTotal = 0.0D;
    Iterator iterator = keys.iterator();
    while (iterator.hasNext()) {
      Comparable key = (Comparable)iterator.next();
      boolean include = true;
      double v = 0.0D;
      Number n = dataset.getValue(key);
      if (n == null) {
        include = !ignoreNullValues;
      }
      else {
        v = n.doubleValue();
        include = v > 0.0D;
      }
      
      if (include) {
        runningTotal += v;
        

        double mid = startAngle + direction.getFactor() * ((runningTotal - v / 2.0D) * 360.0D) / totalValue;
        
        if (Math.cos(Math.toRadians(mid)) < 0.0D) {
          leftKeys.addValue(key, new Double(mid));
        }
        else {
          rightKeys.addValue(key, new Double(mid));
        }
      }
    }
    
    g2.setFont(getLabelFont());
    


    double marginX = plotArea.getX() + interiorGap * plotArea.getWidth();
    
    double gap = plotArea.getWidth() * labelGap;
    double ww = linkArea.getX() - gap - marginX;
    float labelWidth = (float)labelPadding.trimWidth(ww);
    

    if (labelGenerator != null) {
      drawLeftLabels(leftKeys, g2, plotArea, linkArea, labelWidth, state);
      
      drawRightLabels(rightKeys, g2, plotArea, linkArea, labelWidth, state);
    }
    
    g2.setComposite(originalComposite);
  }
  















  protected void drawLeftLabels(KeyedValues leftKeys, Graphics2D g2, Rectangle2D plotArea, Rectangle2D linkArea, float maxLabelWidth, PiePlotState state)
  {
    labelDistributor.clear();
    double lGap = plotArea.getWidth() * labelGap;
    double verticalLinkRadius = state.getLinkArea().getHeight() / 2.0D;
    for (int i = 0; i < leftKeys.getItemCount(); i++) {
      String label = labelGenerator.generateSectionLabel(dataset, leftKeys.getKey(i));
      
      if (label != null) {
        TextBlock block = TextUtilities.createTextBlock(label, labelFont, labelPaint, maxLabelWidth, new G2TextMeasurer(g2));
        

        TextBox labelBox = new TextBox(block);
        labelBox.setBackgroundPaint(labelBackgroundPaint);
        labelBox.setOutlinePaint(labelOutlinePaint);
        labelBox.setOutlineStroke(labelOutlineStroke);
        labelBox.setShadowPaint(labelShadowPaint);
        labelBox.setInteriorGap(labelPadding);
        double theta = Math.toRadians(leftKeys.getValue(i).doubleValue());
        
        double baseY = state.getPieCenterY() - Math.sin(theta) * verticalLinkRadius;
        
        double hh = labelBox.getHeight(g2);
        
        labelDistributor.addPieLabelRecord(new PieLabelRecord(leftKeys.getKey(i), theta, baseY, labelBox, hh, lGap / 2.0D + lGap / 2.0D * -Math.cos(theta), 1.0D - getLabelLinkDepth() + getExplodePercent(leftKeys.getKey(i))));
      }
    }
    



    double hh = plotArea.getHeight();
    double gap = hh * getInteriorGap();
    labelDistributor.distributeLabels(plotArea.getMinY() + gap, hh - 2.0D * gap);
    
    for (int i = 0; i < labelDistributor.getItemCount(); i++) {
      drawLeftLabel(g2, state, labelDistributor.getPieLabelRecord(i));
    }
  }
  














  protected void drawRightLabels(KeyedValues keys, Graphics2D g2, Rectangle2D plotArea, Rectangle2D linkArea, float maxLabelWidth, PiePlotState state)
  {
    labelDistributor.clear();
    double lGap = plotArea.getWidth() * labelGap;
    double verticalLinkRadius = state.getLinkArea().getHeight() / 2.0D;
    
    for (int i = 0; i < keys.getItemCount(); i++) {
      String label = labelGenerator.generateSectionLabel(dataset, keys.getKey(i));
      

      if (label != null) {
        TextBlock block = TextUtilities.createTextBlock(label, labelFont, labelPaint, maxLabelWidth, new G2TextMeasurer(g2));
        

        TextBox labelBox = new TextBox(block);
        labelBox.setBackgroundPaint(labelBackgroundPaint);
        labelBox.setOutlinePaint(labelOutlinePaint);
        labelBox.setOutlineStroke(labelOutlineStroke);
        labelBox.setShadowPaint(labelShadowPaint);
        labelBox.setInteriorGap(labelPadding);
        double theta = Math.toRadians(keys.getValue(i).doubleValue());
        double baseY = state.getPieCenterY() - Math.sin(theta) * verticalLinkRadius;
        
        double hh = labelBox.getHeight(g2);
        labelDistributor.addPieLabelRecord(new PieLabelRecord(keys.getKey(i), theta, baseY, labelBox, hh, lGap / 2.0D + lGap / 2.0D * Math.cos(theta), 1.0D - getLabelLinkDepth() + getExplodePercent(keys.getKey(i))));
      }
    }
    



    double hh = plotArea.getHeight();
    double gap = hh * getInteriorGap();
    labelDistributor.distributeLabels(plotArea.getMinY() + gap, hh - 2.0D * gap);
    
    for (int i = 0; i < labelDistributor.getItemCount(); i++) {
      drawRightLabel(g2, state, labelDistributor.getPieLabelRecord(i));
    }
  }
  







  public LegendItemCollection getLegendItems()
  {
    LegendItemCollection result = new LegendItemCollection();
    if (dataset == null) {
      return result;
    }
    List keys = dataset.getKeys();
    int section = 0;
    Shape shape = getLegendItemShape();
    Iterator iterator = keys.iterator();
    while (iterator.hasNext()) {
      Comparable key = (Comparable)iterator.next();
      Number n = dataset.getValue(key);
      boolean include = true;
      if (n == null) {
        include = !ignoreNullValues;
      }
      else {
        double v = n.doubleValue();
        if (v == 0.0D) {
          include = !ignoreZeroValues;
        }
        else {
          include = v > 0.0D;
        }
      }
      if (include) {
        String label = legendLabelGenerator.generateSectionLabel(dataset, key);
        
        if (label != null) {
          String description = label;
          String toolTipText = null;
          if (legendLabelToolTipGenerator != null) {
            toolTipText = legendLabelToolTipGenerator.generateSectionLabel(dataset, key);
          }
          
          String urlText = null;
          if (legendLabelURLGenerator != null) {
            urlText = legendLabelURLGenerator.generateURL(dataset, key, pieIndex);
          }
          
          Paint paint = lookupSectionPaint(key);
          Paint outlinePaint = lookupSectionOutlinePaint(key);
          Stroke outlineStroke = lookupSectionOutlineStroke(key);
          LegendItem item = new LegendItem(label, description, toolTipText, urlText, true, shape, true, paint, true, outlinePaint, outlineStroke, false, new Line2D.Float(), new BasicStroke(), Color.black);
          



          item.setDataset(getDataset());
          item.setSeriesIndex(dataset.getIndex(key));
          item.setSeriesKey(key);
          result.add(item);
        }
        section++;
      }
      else {
        section++;
      }
    }
    return result;
  }
  




  public String getPlotType()
  {
    return localizationResources.getString("Pie_Plot");
  }
  

















  protected Rectangle2D getArcBounds(Rectangle2D unexploded, Rectangle2D exploded, double angle, double extent, double explodePercent)
  {
    if (explodePercent == 0.0D) {
      return unexploded;
    }
    
    Arc2D arc1 = new Arc2D.Double(unexploded, angle, extent / 2.0D, 0);
    
    Point2D point1 = arc1.getEndPoint();
    Arc2D.Double arc2 = new Arc2D.Double(exploded, angle, extent / 2.0D, 0);
    
    Point2D point2 = arc2.getEndPoint();
    double deltaX = (point1.getX() - point2.getX()) * explodePercent;
    double deltaY = (point1.getY() - point2.getY()) * explodePercent;
    return new Rectangle2D.Double(unexploded.getX() - deltaX, unexploded.getY() - deltaY, unexploded.getWidth(), unexploded.getHeight());
  }
  











  protected void drawLeftLabel(Graphics2D g2, PiePlotState state, PieLabelRecord record)
  {
    double anchorX = state.getLinkArea().getMinX();
    double targetX = anchorX - record.getGap();
    double targetY = record.getAllocatedY();
    
    if (labelLinksVisible) {
      double theta = record.getAngle();
      double linkX = state.getPieCenterX() + Math.cos(theta) * state.getPieWRadius() * record.getLinkPercent();
      
      double linkY = state.getPieCenterY() - Math.sin(theta) * state.getPieHRadius() * record.getLinkPercent();
      
      double elbowX = state.getPieCenterX() + Math.cos(theta) * state.getLinkArea().getWidth() / 2.0D;
      
      double elbowY = state.getPieCenterY() - Math.sin(theta) * state.getLinkArea().getHeight() / 2.0D;
      
      double anchorY = elbowY;
      g2.setPaint(labelLinkPaint);
      g2.setStroke(labelLinkStroke);
      PieLabelLinkStyle style = getLabelLinkStyle();
      if (style.equals(PieLabelLinkStyle.STANDARD)) {
        g2.draw(new Line2D.Double(linkX, linkY, elbowX, elbowY));
        g2.draw(new Line2D.Double(anchorX, anchorY, elbowX, elbowY));
        g2.draw(new Line2D.Double(anchorX, anchorY, targetX, targetY));
      }
      else if (style.equals(PieLabelLinkStyle.QUAD_CURVE)) {
        QuadCurve2D q = new QuadCurve2D.Float();
        q.setCurve(targetX, targetY, anchorX, anchorY, elbowX, elbowY);
        g2.draw(q);
        g2.draw(new Line2D.Double(elbowX, elbowY, linkX, linkY));
      }
      else if (style.equals(PieLabelLinkStyle.CUBIC_CURVE)) {
        CubicCurve2D c = new CubicCurve2D.Float();
        c.setCurve(targetX, targetY, anchorX, anchorY, elbowX, elbowY, linkX, linkY);
        
        g2.draw(c);
      }
    }
    TextBox tb = record.getLabel();
    tb.draw(g2, (float)targetX, (float)targetY, RectangleAnchor.RIGHT);
  }
  









  protected void drawRightLabel(Graphics2D g2, PiePlotState state, PieLabelRecord record)
  {
    double anchorX = state.getLinkArea().getMaxX();
    double targetX = anchorX + record.getGap();
    double targetY = record.getAllocatedY();
    
    if (labelLinksVisible) {
      double theta = record.getAngle();
      double linkX = state.getPieCenterX() + Math.cos(theta) * state.getPieWRadius() * record.getLinkPercent();
      
      double linkY = state.getPieCenterY() - Math.sin(theta) * state.getPieHRadius() * record.getLinkPercent();
      
      double elbowX = state.getPieCenterX() + Math.cos(theta) * state.getLinkArea().getWidth() / 2.0D;
      
      double elbowY = state.getPieCenterY() - Math.sin(theta) * state.getLinkArea().getHeight() / 2.0D;
      
      double anchorY = elbowY;
      g2.setPaint(labelLinkPaint);
      g2.setStroke(labelLinkStroke);
      PieLabelLinkStyle style = getLabelLinkStyle();
      if (style.equals(PieLabelLinkStyle.STANDARD)) {
        g2.draw(new Line2D.Double(linkX, linkY, elbowX, elbowY));
        g2.draw(new Line2D.Double(anchorX, anchorY, elbowX, elbowY));
        g2.draw(new Line2D.Double(anchorX, anchorY, targetX, targetY));
      }
      else if (style.equals(PieLabelLinkStyle.QUAD_CURVE)) {
        QuadCurve2D q = new QuadCurve2D.Float();
        q.setCurve(targetX, targetY, anchorX, anchorY, elbowX, elbowY);
        g2.draw(q);
        g2.draw(new Line2D.Double(elbowX, elbowY, linkX, linkY));
      }
      else if (style.equals(PieLabelLinkStyle.CUBIC_CURVE)) {
        CubicCurve2D c = new CubicCurve2D.Float();
        c.setCurve(targetX, targetY, anchorX, anchorY, elbowX, elbowY, linkX, linkY);
        
        g2.draw(c);
      }
    }
    
    TextBox tb = record.getLabel();
    tb.draw(g2, (float)targetX, (float)targetY, RectangleAnchor.LEFT);
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof PiePlot)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    PiePlot that = (PiePlot)obj;
    if (pieIndex != pieIndex) {
      return false;
    }
    if (interiorGap != interiorGap) {
      return false;
    }
    if (circular != circular) {
      return false;
    }
    if (startAngle != startAngle) {
      return false;
    }
    if (direction != direction) {
      return false;
    }
    if (ignoreZeroValues != ignoreZeroValues) {
      return false;
    }
    if (ignoreNullValues != ignoreNullValues) {
      return false;
    }
    if (!PaintUtilities.equal(sectionPaint, sectionPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(sectionPaintMap, sectionPaintMap))
    {
      return false;
    }
    if (!PaintUtilities.equal(baseSectionPaint, baseSectionPaint))
    {
      return false;
    }
    if (sectionOutlinesVisible != sectionOutlinesVisible) {
      return false;
    }
    if (!PaintUtilities.equal(sectionOutlinePaint, sectionOutlinePaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(sectionOutlinePaintMap, sectionOutlinePaintMap))
    {
      return false;
    }
    if (!PaintUtilities.equal(baseSectionOutlinePaint, baseSectionOutlinePaint))
    {

      return false;
    }
    if (!ObjectUtilities.equal(sectionOutlineStroke, sectionOutlineStroke))
    {
      return false;
    }
    if (!ObjectUtilities.equal(sectionOutlineStrokeMap, sectionOutlineStrokeMap))
    {
      return false;
    }
    if (!ObjectUtilities.equal(baseSectionOutlineStroke, baseSectionOutlineStroke))
    {

      return false;
    }
    if (!PaintUtilities.equal(shadowPaint, shadowPaint)) {
      return false;
    }
    if (shadowXOffset != shadowXOffset) {
      return false;
    }
    if (shadowYOffset != shadowYOffset) {
      return false;
    }
    if (!ObjectUtilities.equal(explodePercentages, explodePercentages))
    {
      return false;
    }
    if (!ObjectUtilities.equal(labelGenerator, labelGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(labelFont, labelFont)) {
      return false;
    }
    if (!PaintUtilities.equal(labelPaint, labelPaint)) {
      return false;
    }
    if (!PaintUtilities.equal(labelBackgroundPaint, labelBackgroundPaint))
    {
      return false;
    }
    if (!PaintUtilities.equal(labelOutlinePaint, labelOutlinePaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(labelOutlineStroke, labelOutlineStroke))
    {
      return false;
    }
    if (!PaintUtilities.equal(labelShadowPaint, labelShadowPaint))
    {
      return false;
    }
    if (simpleLabels != simpleLabels) {
      return false;
    }
    if (!simpleLabelOffset.equals(simpleLabelOffset)) {
      return false;
    }
    if (!labelPadding.equals(labelPadding)) {
      return false;
    }
    if (maximumLabelWidth != maximumLabelWidth) {
      return false;
    }
    if (labelGap != labelGap) {
      return false;
    }
    if (labelLinkMargin != labelLinkMargin) {
      return false;
    }
    if (labelLinksVisible != labelLinksVisible) {
      return false;
    }
    if (!labelLinkStyle.equals(labelLinkStyle)) {
      return false;
    }
    if (!PaintUtilities.equal(labelLinkPaint, labelLinkPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(labelLinkStroke, labelLinkStroke))
    {
      return false;
    }
    if (!ObjectUtilities.equal(toolTipGenerator, toolTipGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(urlGenerator, urlGenerator)) {
      return false;
    }
    if (minimumArcAngleToDraw != minimumArcAngleToDraw) {
      return false;
    }
    if (!ShapeUtilities.equal(legendItemShape, legendItemShape)) {
      return false;
    }
    if (!ObjectUtilities.equal(legendLabelGenerator, legendLabelGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(legendLabelToolTipGenerator, legendLabelToolTipGenerator))
    {
      return false;
    }
    if (!ObjectUtilities.equal(legendLabelURLGenerator, legendLabelURLGenerator))
    {
      return false;
    }
    if (autoPopulateSectionPaint != autoPopulateSectionPaint) {
      return false;
    }
    if (autoPopulateSectionOutlinePaint != autoPopulateSectionOutlinePaint)
    {
      return false;
    }
    if (autoPopulateSectionOutlineStroke != autoPopulateSectionOutlineStroke)
    {
      return false;
    }
    
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    PiePlot clone = (PiePlot)super.clone();
    if (dataset != null) {
      dataset.addChangeListener(clone);
    }
    if ((urlGenerator instanceof PublicCloneable)) {
      urlGenerator = ((PieURLGenerator)ObjectUtilities.clone(urlGenerator));
    }
    
    legendItemShape = ShapeUtilities.clone(legendItemShape);
    if (legendLabelGenerator != null) {
      legendLabelGenerator = ((PieSectionLabelGenerator)ObjectUtilities.clone(legendLabelGenerator));
    }
    
    if (legendLabelToolTipGenerator != null) {
      legendLabelToolTipGenerator = ((PieSectionLabelGenerator)ObjectUtilities.clone(legendLabelToolTipGenerator));
    }
    
    if ((legendLabelURLGenerator instanceof PublicCloneable)) {
      legendLabelURLGenerator = ((PieURLGenerator)ObjectUtilities.clone(legendLabelURLGenerator));
    }
    
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(sectionPaint, stream);
    SerialUtilities.writePaint(baseSectionPaint, stream);
    SerialUtilities.writePaint(sectionOutlinePaint, stream);
    SerialUtilities.writePaint(baseSectionOutlinePaint, stream);
    SerialUtilities.writeStroke(sectionOutlineStroke, stream);
    SerialUtilities.writeStroke(baseSectionOutlineStroke, stream);
    SerialUtilities.writePaint(shadowPaint, stream);
    SerialUtilities.writePaint(labelPaint, stream);
    SerialUtilities.writePaint(labelBackgroundPaint, stream);
    SerialUtilities.writePaint(labelOutlinePaint, stream);
    SerialUtilities.writeStroke(labelOutlineStroke, stream);
    SerialUtilities.writePaint(labelShadowPaint, stream);
    SerialUtilities.writePaint(labelLinkPaint, stream);
    SerialUtilities.writeStroke(labelLinkStroke, stream);
    SerialUtilities.writeShape(legendItemShape, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    sectionPaint = SerialUtilities.readPaint(stream);
    baseSectionPaint = SerialUtilities.readPaint(stream);
    sectionOutlinePaint = SerialUtilities.readPaint(stream);
    baseSectionOutlinePaint = SerialUtilities.readPaint(stream);
    sectionOutlineStroke = SerialUtilities.readStroke(stream);
    baseSectionOutlineStroke = SerialUtilities.readStroke(stream);
    shadowPaint = SerialUtilities.readPaint(stream);
    labelPaint = SerialUtilities.readPaint(stream);
    labelBackgroundPaint = SerialUtilities.readPaint(stream);
    labelOutlinePaint = SerialUtilities.readPaint(stream);
    labelOutlineStroke = SerialUtilities.readStroke(stream);
    labelShadowPaint = SerialUtilities.readPaint(stream);
    labelLinkPaint = SerialUtilities.readPaint(stream);
    labelLinkStroke = SerialUtilities.readStroke(stream);
    legendItemShape = SerialUtilities.readShape(stream);
  }
  


































  /**
   * @deprecated
   */
  public Paint getSectionPaint(int section)
  {
    Comparable key = getSectionKey(section);
    return getSectionPaint(key);
  }
  





  /**
   * @deprecated
   */
  public void setSectionPaint(int section, Paint paint)
  {
    Comparable key = getSectionKey(section);
    setSectionPaint(key, paint);
  }
  







  /**
   * @deprecated
   */
  public Paint getSectionOutlinePaint()
  {
    return sectionOutlinePaint;
  }
  









  /**
   * @deprecated
   */
  public void setSectionOutlinePaint(Paint paint)
  {
    sectionOutlinePaint = paint;
    fireChangeEvent();
  }
  





  /**
   * @deprecated
   */
  public Paint getSectionOutlinePaint(int section)
  {
    Comparable key = getSectionKey(section);
    return getSectionOutlinePaint(key);
  }
  






  /**
   * @deprecated
   */
  public void setSectionOutlinePaint(int section, Paint paint)
  {
    Comparable key = getSectionKey(section);
    setSectionOutlinePaint(key, paint);
  }
  







  /**
   * @deprecated
   */
  public Stroke getSectionOutlineStroke()
  {
    return sectionOutlineStroke;
  }
  









  /**
   * @deprecated
   */
  public void setSectionOutlineStroke(Stroke stroke)
  {
    sectionOutlineStroke = stroke;
    fireChangeEvent();
  }
  





  /**
   * @deprecated
   */
  public Stroke getSectionOutlineStroke(int section)
  {
    Comparable key = getSectionKey(section);
    return getSectionOutlineStroke(key);
  }
  






  /**
   * @deprecated
   */
  public void setSectionOutlineStroke(int section, Stroke stroke)
  {
    Comparable key = getSectionKey(section);
    setSectionOutlineStroke(key, stroke);
  }
  





  /**
   * @deprecated
   */
  public double getExplodePercent(int section)
  {
    Comparable key = getSectionKey(section);
    return getExplodePercent(key);
  }
  





  /**
   * @deprecated
   */
  public void setExplodePercent(int section, double percent)
  {
    Comparable key = getSectionKey(section);
    setExplodePercent(key, percent);
  }
}
