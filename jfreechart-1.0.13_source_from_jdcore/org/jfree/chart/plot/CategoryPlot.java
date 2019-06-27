package org.jfree.chart.plot;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.annotations.CategoryAnnotation;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.AxisCollection;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.TickType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.axis.ValueTick;
import org.jfree.chart.event.ChartChangeEventType;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.event.RendererChangeListener;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ObjectList;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;
import org.jfree.util.ShapeUtilities;
import org.jfree.util.SortOrder;


































































































































































































public class CategoryPlot
  extends Plot
  implements ValueAxisPlot, Pannable, Zoomable, RendererChangeListener, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = -3537691700434728188L;
  public static final boolean DEFAULT_DOMAIN_GRIDLINES_VISIBLE = false;
  public static final boolean DEFAULT_RANGE_GRIDLINES_VISIBLE = true;
  public static final Stroke DEFAULT_GRIDLINE_STROKE = new BasicStroke(0.5F, 0, 2, 0.0F, new float[] { 2.0F, 2.0F }, 0.0F);
  



  public static final Paint DEFAULT_GRIDLINE_PAINT = Color.lightGray;
  

  public static final Font DEFAULT_VALUE_LABEL_FONT = new Font("SansSerif", 0, 10);
  






  public static final boolean DEFAULT_CROSSHAIR_VISIBLE = false;
  





  public static final Stroke DEFAULT_CROSSHAIR_STROKE = DEFAULT_GRIDLINE_STROKE;
  






  public static final Paint DEFAULT_CROSSHAIR_PAINT = Color.blue;
  

  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
  


  private PlotOrientation orientation;
  


  private RectangleInsets axisOffset;
  


  private ObjectList domainAxes;
  


  private ObjectList domainAxisLocations;
  


  private boolean drawSharedDomainAxis;
  

  private ObjectList rangeAxes;
  

  private ObjectList rangeAxisLocations;
  

  private ObjectList datasets;
  

  private TreeMap datasetToDomainAxesMap;
  

  private TreeMap datasetToRangeAxesMap;
  

  private ObjectList renderers;
  

  private DatasetRenderingOrder renderingOrder = DatasetRenderingOrder.REVERSE;
  





  private SortOrder columnRenderingOrder = SortOrder.ASCENDING;
  




  private SortOrder rowRenderingOrder = SortOrder.ASCENDING;
  




  private boolean domainGridlinesVisible;
  




  private CategoryAnchor domainGridlinePosition;
  




  private transient Stroke domainGridlineStroke;
  




  private transient Paint domainGridlinePaint;
  




  private boolean rangeZeroBaselineVisible;
  




  private transient Stroke rangeZeroBaselineStroke;
  




  private transient Paint rangeZeroBaselinePaint;
  




  private boolean rangeGridlinesVisible;
  




  private transient Stroke rangeGridlineStroke;
  




  private transient Paint rangeGridlinePaint;
  



  private boolean rangeMinorGridlinesVisible;
  



  private transient Stroke rangeMinorGridlineStroke;
  



  private transient Paint rangeMinorGridlinePaint;
  



  private double anchorValue;
  



  private int crosshairDatasetIndex;
  



  private boolean domainCrosshairVisible;
  



  private Comparable domainCrosshairRowKey;
  



  private Comparable domainCrosshairColumnKey;
  



  private transient Stroke domainCrosshairStroke;
  



  private transient Paint domainCrosshairPaint;
  



  private boolean rangeCrosshairVisible;
  



  private double rangeCrosshairValue;
  



  private transient Stroke rangeCrosshairStroke;
  



  private transient Paint rangeCrosshairPaint;
  



  private boolean rangeCrosshairLockedOnData = true;
  



  private Map foregroundDomainMarkers;
  



  private Map backgroundDomainMarkers;
  



  private Map foregroundRangeMarkers;
  



  private Map backgroundRangeMarkers;
  


  private List annotations;
  


  private int weight;
  


  private AxisSpace fixedDomainAxisSpace;
  


  private AxisSpace fixedRangeAxisSpace;
  


  private LegendItemCollection fixedLegendItems;
  


  private boolean rangePannable;
  



  public CategoryPlot()
  {
    this(null, null, null, null);
  }
  














  public CategoryPlot(CategoryDataset dataset, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryItemRenderer renderer)
  {
    orientation = PlotOrientation.VERTICAL;
    

    domainAxes = new ObjectList();
    domainAxisLocations = new ObjectList();
    rangeAxes = new ObjectList();
    rangeAxisLocations = new ObjectList();
    
    datasetToDomainAxesMap = new TreeMap();
    datasetToRangeAxesMap = new TreeMap();
    
    renderers = new ObjectList();
    
    datasets = new ObjectList();
    datasets.set(0, dataset);
    if (dataset != null) {
      dataset.addChangeListener(this);
    }
    
    axisOffset = RectangleInsets.ZERO_INSETS;
    
    setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT, false);
    setRangeAxisLocation(AxisLocation.TOP_OR_LEFT, false);
    
    renderers.set(0, renderer);
    if (renderer != null) {
      renderer.setPlot(this);
      renderer.addChangeListener(this);
    }
    
    domainAxes.set(0, domainAxis);
    mapDatasetToDomainAxis(0, 0);
    if (domainAxis != null) {
      domainAxis.setPlot(this);
      domainAxis.addChangeListener(this);
    }
    drawSharedDomainAxis = false;
    
    rangeAxes.set(0, rangeAxis);
    mapDatasetToRangeAxis(0, 0);
    if (rangeAxis != null) {
      rangeAxis.setPlot(this);
      rangeAxis.addChangeListener(this);
    }
    
    configureDomainAxes();
    configureRangeAxes();
    
    domainGridlinesVisible = false;
    domainGridlinePosition = CategoryAnchor.MIDDLE;
    domainGridlineStroke = DEFAULT_GRIDLINE_STROKE;
    domainGridlinePaint = DEFAULT_GRIDLINE_PAINT;
    
    rangeZeroBaselineVisible = false;
    rangeZeroBaselinePaint = Color.black;
    rangeZeroBaselineStroke = new BasicStroke(0.5F);
    
    rangeGridlinesVisible = true;
    rangeGridlineStroke = DEFAULT_GRIDLINE_STROKE;
    rangeGridlinePaint = DEFAULT_GRIDLINE_PAINT;
    
    rangeMinorGridlinesVisible = false;
    rangeMinorGridlineStroke = DEFAULT_GRIDLINE_STROKE;
    rangeMinorGridlinePaint = Color.white;
    
    foregroundDomainMarkers = new HashMap();
    backgroundDomainMarkers = new HashMap();
    foregroundRangeMarkers = new HashMap();
    backgroundRangeMarkers = new HashMap();
    
    anchorValue = 0.0D;
    
    domainCrosshairVisible = false;
    domainCrosshairStroke = DEFAULT_CROSSHAIR_STROKE;
    domainCrosshairPaint = DEFAULT_CROSSHAIR_PAINT;
    
    rangeCrosshairVisible = false;
    rangeCrosshairValue = 0.0D;
    rangeCrosshairStroke = DEFAULT_CROSSHAIR_STROKE;
    rangeCrosshairPaint = DEFAULT_CROSSHAIR_PAINT;
    
    annotations = new ArrayList();
    
    rangePannable = false;
  }
  




  public String getPlotType()
  {
    return localizationResources.getString("Category_Plot");
  }
  






  public PlotOrientation getOrientation()
  {
    return orientation;
  }
  







  public void setOrientation(PlotOrientation orientation)
  {
    if (orientation == null) {
      throw new IllegalArgumentException("Null 'orientation' argument.");
    }
    this.orientation = orientation;
    fireChangeEvent();
  }
  






  public RectangleInsets getAxisOffset()
  {
    return axisOffset;
  }
  







  public void setAxisOffset(RectangleInsets offset)
  {
    if (offset == null) {
      throw new IllegalArgumentException("Null 'offset' argument.");
    }
    axisOffset = offset;
    fireChangeEvent();
  }
  








  public CategoryAxis getDomainAxis()
  {
    return getDomainAxis(0);
  }
  








  public CategoryAxis getDomainAxis(int index)
  {
    CategoryAxis result = null;
    if (index < domainAxes.size()) {
      result = (CategoryAxis)domainAxes.get(index);
    }
    if (result == null) {
      Plot parent = getParent();
      if ((parent instanceof CategoryPlot)) {
        CategoryPlot cp = (CategoryPlot)parent;
        result = cp.getDomainAxis(index);
      }
    }
    return result;
  }
  







  public void setDomainAxis(CategoryAxis axis)
  {
    setDomainAxis(0, axis);
  }
  








  public void setDomainAxis(int index, CategoryAxis axis)
  {
    setDomainAxis(index, axis, true);
  }
  







  public void setDomainAxis(int index, CategoryAxis axis, boolean notify)
  {
    CategoryAxis existing = (CategoryAxis)domainAxes.get(index);
    if (existing != null) {
      existing.removeChangeListener(this);
    }
    if (axis != null) {
      axis.setPlot(this);
    }
    domainAxes.set(index, axis);
    if (axis != null) {
      axis.configure();
      axis.addChangeListener(this);
    }
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public void setDomainAxes(CategoryAxis[] axes)
  {
    for (int i = 0; i < axes.length; i++) {
      setDomainAxis(i, axes[i], false);
    }
    fireChangeEvent();
  }
  












  public int getDomainAxisIndex(CategoryAxis axis)
  {
    if (axis == null) {
      throw new IllegalArgumentException("Null 'axis' argument.");
    }
    return domainAxes.indexOf(axis);
  }
  






  public AxisLocation getDomainAxisLocation()
  {
    return getDomainAxisLocation(0);
  }
  








  public AxisLocation getDomainAxisLocation(int index)
  {
    AxisLocation result = null;
    if (index < domainAxisLocations.size()) {
      result = (AxisLocation)domainAxisLocations.get(index);
    }
    if (result == null) {
      result = AxisLocation.getOpposite(getDomainAxisLocation(0));
    }
    return result;
  }
  









  public void setDomainAxisLocation(AxisLocation location)
  {
    setDomainAxisLocation(0, location, true);
  }
  







  public void setDomainAxisLocation(AxisLocation location, boolean notify)
  {
    setDomainAxisLocation(0, location, notify);
  }
  










  public void setDomainAxisLocation(int index, AxisLocation location)
  {
    setDomainAxisLocation(index, location, true);
  }
  













  public void setDomainAxisLocation(int index, AxisLocation location, boolean notify)
  {
    if ((index == 0) && (location == null)) {
      throw new IllegalArgumentException("Null 'location' for index 0 not permitted.");
    }
    
    domainAxisLocations.set(index, location);
    if (notify) {
      fireChangeEvent();
    }
  }
  





  public RectangleEdge getDomainAxisEdge()
  {
    return getDomainAxisEdge(0);
  }
  






  public RectangleEdge getDomainAxisEdge(int index)
  {
    RectangleEdge result = null;
    AxisLocation location = getDomainAxisLocation(index);
    if (location != null) {
      result = Plot.resolveDomainAxisLocation(location, orientation);
    }
    else {
      result = RectangleEdge.opposite(getDomainAxisEdge(0));
    }
    return result;
  }
  




  public int getDomainAxisCount()
  {
    return domainAxes.size();
  }
  



  public void clearDomainAxes()
  {
    for (int i = 0; i < domainAxes.size(); i++) {
      CategoryAxis axis = (CategoryAxis)domainAxes.get(i);
      if (axis != null) {
        axis.removeChangeListener(this);
      }
    }
    domainAxes.clear();
    fireChangeEvent();
  }
  


  public void configureDomainAxes()
  {
    for (int i = 0; i < domainAxes.size(); i++) {
      CategoryAxis axis = (CategoryAxis)domainAxes.get(i);
      if (axis != null) {
        axis.configure();
      }
    }
  }
  






  public ValueAxis getRangeAxis()
  {
    return getRangeAxis(0);
  }
  






  public ValueAxis getRangeAxis(int index)
  {
    ValueAxis result = null;
    if (index < rangeAxes.size()) {
      result = (ValueAxis)rangeAxes.get(index);
    }
    if (result == null) {
      Plot parent = getParent();
      if ((parent instanceof CategoryPlot)) {
        CategoryPlot cp = (CategoryPlot)parent;
        result = cp.getRangeAxis(index);
      }
    }
    return result;
  }
  





  public void setRangeAxis(ValueAxis axis)
  {
    setRangeAxis(0, axis);
  }
  






  public void setRangeAxis(int index, ValueAxis axis)
  {
    setRangeAxis(index, axis, true);
  }
  







  public void setRangeAxis(int index, ValueAxis axis, boolean notify)
  {
    ValueAxis existing = (ValueAxis)rangeAxes.get(index);
    if (existing != null) {
      existing.removeChangeListener(this);
    }
    if (axis != null) {
      axis.setPlot(this);
    }
    rangeAxes.set(index, axis);
    if (axis != null) {
      axis.configure();
      axis.addChangeListener(this);
    }
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public void setRangeAxes(ValueAxis[] axes)
  {
    for (int i = 0; i < axes.length; i++) {
      setRangeAxis(i, axes[i], false);
    }
    fireChangeEvent();
  }
  












  public int getRangeAxisIndex(ValueAxis axis)
  {
    if (axis == null) {
      throw new IllegalArgumentException("Null 'axis' argument.");
    }
    int result = rangeAxes.indexOf(axis);
    if (result < 0) {
      Plot parent = getParent();
      if ((parent instanceof CategoryPlot)) {
        CategoryPlot p = (CategoryPlot)parent;
        result = p.getRangeAxisIndex(axis);
      }
    }
    return result;
  }
  




  public AxisLocation getRangeAxisLocation()
  {
    return getRangeAxisLocation(0);
  }
  








  public AxisLocation getRangeAxisLocation(int index)
  {
    AxisLocation result = null;
    if (index < rangeAxisLocations.size()) {
      result = (AxisLocation)rangeAxisLocations.get(index);
    }
    if (result == null) {
      result = AxisLocation.getOpposite(getRangeAxisLocation(0));
    }
    return result;
  }
  









  public void setRangeAxisLocation(AxisLocation location)
  {
    setRangeAxisLocation(location, true);
  }
  








  public void setRangeAxisLocation(AxisLocation location, boolean notify)
  {
    setRangeAxisLocation(0, location, notify);
  }
  









  public void setRangeAxisLocation(int index, AxisLocation location)
  {
    setRangeAxisLocation(index, location, true);
  }
  











  public void setRangeAxisLocation(int index, AxisLocation location, boolean notify)
  {
    if ((index == 0) && (location == null)) {
      throw new IllegalArgumentException("Null 'location' for index 0 not permitted.");
    }
    
    rangeAxisLocations.set(index, location);
    if (notify) {
      fireChangeEvent();
    }
  }
  




  public RectangleEdge getRangeAxisEdge()
  {
    return getRangeAxisEdge(0);
  }
  






  public RectangleEdge getRangeAxisEdge(int index)
  {
    AxisLocation location = getRangeAxisLocation(index);
    RectangleEdge result = Plot.resolveRangeAxisLocation(location, orientation);
    
    if (result == null) {
      result = RectangleEdge.opposite(getRangeAxisEdge(0));
    }
    return result;
  }
  




  public int getRangeAxisCount()
  {
    return rangeAxes.size();
  }
  



  public void clearRangeAxes()
  {
    for (int i = 0; i < rangeAxes.size(); i++) {
      ValueAxis axis = (ValueAxis)rangeAxes.get(i);
      if (axis != null) {
        axis.removeChangeListener(this);
      }
    }
    rangeAxes.clear();
    fireChangeEvent();
  }
  


  public void configureRangeAxes()
  {
    for (int i = 0; i < rangeAxes.size(); i++) {
      ValueAxis axis = (ValueAxis)rangeAxes.get(i);
      if (axis != null) {
        axis.configure();
      }
    }
  }
  






  public CategoryDataset getDataset()
  {
    return getDataset(0);
  }
  








  public CategoryDataset getDataset(int index)
  {
    CategoryDataset result = null;
    if (datasets.size() > index) {
      result = (CategoryDataset)datasets.get(index);
    }
    return result;
  }
  










  public void setDataset(CategoryDataset dataset)
  {
    setDataset(0, dataset);
  }
  








  public void setDataset(int index, CategoryDataset dataset)
  {
    CategoryDataset existing = (CategoryDataset)datasets.get(index);
    if (existing != null) {
      existing.removeChangeListener(this);
    }
    datasets.set(index, dataset);
    if (dataset != null) {
      dataset.addChangeListener(this);
    }
    

    DatasetChangeEvent event = new DatasetChangeEvent(this, dataset);
    datasetChanged(event);
  }
  







  public int getDatasetCount()
  {
    return datasets.size();
  }
  









  public int indexOf(CategoryDataset dataset)
  {
    int result = -1;
    for (int i = 0; i < datasets.size(); i++) {
      if (dataset == datasets.get(i)) {
        result = i;
        break;
      }
    }
    return result;
  }
  







  public void mapDatasetToDomainAxis(int index, int axisIndex)
  {
    List axisIndices = new ArrayList(1);
    axisIndices.add(new Integer(axisIndex));
    mapDatasetToDomainAxes(index, axisIndices);
  }
  









  public void mapDatasetToDomainAxes(int index, List axisIndices)
  {
    if (index < 0) {
      throw new IllegalArgumentException("Requires 'index' >= 0.");
    }
    checkAxisIndices(axisIndices);
    Integer key = new Integer(index);
    datasetToDomainAxesMap.put(key, new ArrayList(axisIndices));
    
    datasetChanged(new DatasetChangeEvent(this, getDataset(index)));
  }
  









  private void checkAxisIndices(List indices)
  {
    if (indices == null) {
      return;
    }
    int count = indices.size();
    if (count == 0) {
      throw new IllegalArgumentException("Empty list not permitted.");
    }
    HashSet set = new HashSet();
    for (int i = 0; i < count; i++) {
      Object item = indices.get(i);
      if (!(item instanceof Integer)) {
        throw new IllegalArgumentException("Indices must be Integer instances.");
      }
      
      if (set.contains(item)) {
        throw new IllegalArgumentException("Indices must be unique.");
      }
      set.add(item);
    }
  }
  









  public CategoryAxis getDomainAxisForDataset(int index)
  {
    if (index < 0) {
      throw new IllegalArgumentException("Negative 'index'.");
    }
    CategoryAxis axis = null;
    List axisIndices = (List)datasetToDomainAxesMap.get(new Integer(index));
    
    if (axisIndices != null)
    {
      Integer axisIndex = (Integer)axisIndices.get(0);
      axis = getDomainAxis(axisIndex.intValue());
    }
    else {
      axis = getDomainAxis(0);
    }
    return axis;
  }
  







  public void mapDatasetToRangeAxis(int index, int axisIndex)
  {
    List axisIndices = new ArrayList(1);
    axisIndices.add(new Integer(axisIndex));
    mapDatasetToRangeAxes(index, axisIndices);
  }
  









  public void mapDatasetToRangeAxes(int index, List axisIndices)
  {
    if (index < 0) {
      throw new IllegalArgumentException("Requires 'index' >= 0.");
    }
    checkAxisIndices(axisIndices);
    Integer key = new Integer(index);
    datasetToRangeAxesMap.put(key, new ArrayList(axisIndices));
    
    datasetChanged(new DatasetChangeEvent(this, getDataset(index)));
  }
  









  public ValueAxis getRangeAxisForDataset(int index)
  {
    if (index < 0) {
      throw new IllegalArgumentException("Negative 'index'.");
    }
    ValueAxis axis = null;
    List axisIndices = (List)datasetToRangeAxesMap.get(new Integer(index));
    
    if (axisIndices != null)
    {
      Integer axisIndex = (Integer)axisIndices.get(0);
      axis = getRangeAxis(axisIndex.intValue());
    }
    else {
      axis = getRangeAxis(0);
    }
    return axis;
  }
  






  public int getRendererCount()
  {
    return renderers.size();
  }
  






  public CategoryItemRenderer getRenderer()
  {
    return getRenderer(0);
  }
  








  public CategoryItemRenderer getRenderer(int index)
  {
    CategoryItemRenderer result = null;
    if (renderers.size() > index) {
      result = (CategoryItemRenderer)renderers.get(index);
    }
    return result;
  }
  








  public void setRenderer(CategoryItemRenderer renderer)
  {
    setRenderer(0, renderer, true);
  }
  
















  public void setRenderer(CategoryItemRenderer renderer, boolean notify)
  {
    setRenderer(0, renderer, notify);
  }
  









  public void setRenderer(int index, CategoryItemRenderer renderer)
  {
    setRenderer(index, renderer, true);
  }
  












  public void setRenderer(int index, CategoryItemRenderer renderer, boolean notify)
  {
    CategoryItemRenderer existing = (CategoryItemRenderer)renderers.get(index);
    
    if (existing != null) {
      existing.removeChangeListener(this);
    }
    

    renderers.set(index, renderer);
    if (renderer != null) {
      renderer.setPlot(this);
      renderer.addChangeListener(this);
    }
    
    configureDomainAxes();
    configureRangeAxes();
    
    if (notify) {
      fireChangeEvent();
    }
  }
  





  public void setRenderers(CategoryItemRenderer[] renderers)
  {
    for (int i = 0; i < renderers.length; i++) {
      setRenderer(i, renderers[i], false);
    }
    fireChangeEvent();
  }
  







  public CategoryItemRenderer getRendererForDataset(CategoryDataset dataset)
  {
    CategoryItemRenderer result = null;
    for (int i = 0; i < datasets.size(); i++) {
      if (datasets.get(i) == dataset) {
        result = (CategoryItemRenderer)renderers.get(i);
        break;
      }
    }
    return result;
  }
  







  public int getIndexOf(CategoryItemRenderer renderer)
  {
    return renderers.indexOf(renderer);
  }
  






  public DatasetRenderingOrder getDatasetRenderingOrder()
  {
    return renderingOrder;
  }
  









  public void setDatasetRenderingOrder(DatasetRenderingOrder order)
  {
    if (order == null) {
      throw new IllegalArgumentException("Null 'order' argument.");
    }
    renderingOrder = order;
    fireChangeEvent();
  }
  







  public SortOrder getColumnRenderingOrder()
  {
    return columnRenderingOrder;
  }
  










  public void setColumnRenderingOrder(SortOrder order)
  {
    if (order == null) {
      throw new IllegalArgumentException("Null 'order' argument.");
    }
    columnRenderingOrder = order;
    fireChangeEvent();
  }
  







  public SortOrder getRowRenderingOrder()
  {
    return rowRenderingOrder;
  }
  










  public void setRowRenderingOrder(SortOrder order)
  {
    if (order == null) {
      throw new IllegalArgumentException("Null 'order' argument.");
    }
    rowRenderingOrder = order;
    fireChangeEvent();
  }
  






  public boolean isDomainGridlinesVisible()
  {
    return domainGridlinesVisible;
  }
  










  public void setDomainGridlinesVisible(boolean visible)
  {
    if (domainGridlinesVisible != visible) {
      domainGridlinesVisible = visible;
      fireChangeEvent();
    }
  }
  






  public CategoryAnchor getDomainGridlinePosition()
  {
    return domainGridlinePosition;
  }
  







  public void setDomainGridlinePosition(CategoryAnchor position)
  {
    if (position == null) {
      throw new IllegalArgumentException("Null 'position' argument.");
    }
    domainGridlinePosition = position;
    fireChangeEvent();
  }
  






  public Stroke getDomainGridlineStroke()
  {
    return domainGridlineStroke;
  }
  







  public void setDomainGridlineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' not permitted.");
    }
    domainGridlineStroke = stroke;
    fireChangeEvent();
  }
  






  public Paint getDomainGridlinePaint()
  {
    return domainGridlinePaint;
  }
  







  public void setDomainGridlinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    domainGridlinePaint = paint;
    fireChangeEvent();
  }
  









  public boolean isRangeZeroBaselineVisible()
  {
    return rangeZeroBaselineVisible;
  }
  










  public void setRangeZeroBaselineVisible(boolean visible)
  {
    rangeZeroBaselineVisible = visible;
    fireChangeEvent();
  }
  








  public Stroke getRangeZeroBaselineStroke()
  {
    return rangeZeroBaselineStroke;
  }
  









  public void setRangeZeroBaselineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    rangeZeroBaselineStroke = stroke;
    fireChangeEvent();
  }
  









  public Paint getRangeZeroBaselinePaint()
  {
    return rangeZeroBaselinePaint;
  }
  









  public void setRangeZeroBaselinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    rangeZeroBaselinePaint = paint;
    fireChangeEvent();
  }
  






  public boolean isRangeGridlinesVisible()
  {
    return rangeGridlinesVisible;
  }
  








  public void setRangeGridlinesVisible(boolean visible)
  {
    if (rangeGridlinesVisible != visible) {
      rangeGridlinesVisible = visible;
      fireChangeEvent();
    }
  }
  






  public Stroke getRangeGridlineStroke()
  {
    return rangeGridlineStroke;
  }
  







  public void setRangeGridlineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    rangeGridlineStroke = stroke;
    fireChangeEvent();
  }
  






  public Paint getRangeGridlinePaint()
  {
    return rangeGridlinePaint;
  }
  







  public void setRangeGridlinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    rangeGridlinePaint = paint;
    fireChangeEvent();
  }
  









  public boolean isRangeMinorGridlinesVisible()
  {
    return rangeMinorGridlinesVisible;
  }
  












  public void setRangeMinorGridlinesVisible(boolean visible)
  {
    if (rangeMinorGridlinesVisible != visible) {
      rangeMinorGridlinesVisible = visible;
      fireChangeEvent();
    }
  }
  









  public Stroke getRangeMinorGridlineStroke()
  {
    return rangeMinorGridlineStroke;
  }
  









  public void setRangeMinorGridlineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    rangeMinorGridlineStroke = stroke;
    fireChangeEvent();
  }
  









  public Paint getRangeMinorGridlinePaint()
  {
    return rangeMinorGridlinePaint;
  }
  









  public void setRangeMinorGridlinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    rangeMinorGridlinePaint = paint;
    fireChangeEvent();
  }
  






  public LegendItemCollection getFixedLegendItems()
  {
    return fixedLegendItems;
  }
  








  public void setFixedLegendItems(LegendItemCollection items)
  {
    fixedLegendItems = items;
    fireChangeEvent();
  }
  






  public LegendItemCollection getLegendItems()
  {
    LegendItemCollection result = fixedLegendItems;
    if (result == null) {
      result = new LegendItemCollection();
      
      int count = datasets.size();
      for (int datasetIndex = 0; datasetIndex < count; datasetIndex++) {
        CategoryDataset dataset = getDataset(datasetIndex);
        if (dataset != null) {
          CategoryItemRenderer renderer = getRenderer(datasetIndex);
          if (renderer != null) {
            int seriesCount = dataset.getRowCount();
            for (int i = 0; i < seriesCount; i++) {
              LegendItem item = renderer.getLegendItem(datasetIndex, i);
              
              if (item != null) {
                result.add(item);
              }
            }
          }
        }
      }
    }
    return result;
  }
  








  public void handleClick(int x, int y, PlotRenderingInfo info)
  {
    Rectangle2D dataArea = info.getDataArea();
    if (dataArea.contains(x, y))
    {
      double java2D = 0.0D;
      if (orientation == PlotOrientation.HORIZONTAL) {
        java2D = x;
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        java2D = y;
      }
      RectangleEdge edge = Plot.resolveRangeAxisLocation(getRangeAxisLocation(), orientation);
      
      double value = getRangeAxis().java2DToValue(java2D, info.getDataArea(), edge);
      
      setAnchorValue(value);
      setRangeCrosshairValue(value);
    }
  }
  










  public void zoom(double percent)
  {
    if (percent > 0.0D) {
      double range = getRangeAxis().getRange().getLength();
      double scaledRange = range * percent;
      getRangeAxis().setRange(anchorValue - scaledRange / 2.0D, anchorValue + scaledRange / 2.0D);
    }
    else
    {
      getRangeAxis().setAutoRange(true);
    }
  }
  








  public void datasetChanged(DatasetChangeEvent event)
  {
    int count = rangeAxes.size();
    for (int axisIndex = 0; axisIndex < count; axisIndex++) {
      ValueAxis yAxis = getRangeAxis(axisIndex);
      if (yAxis != null) {
        yAxis.configure();
      }
    }
    if (getParent() != null) {
      getParent().datasetChanged(event);
    }
    else {
      PlotChangeEvent e = new PlotChangeEvent(this);
      e.setType(ChartChangeEventType.DATASET_UPDATED);
      notifyListeners(e);
    }
  }
  





  public void rendererChanged(RendererChangeEvent event)
  {
    Plot parent = getParent();
    if (parent != null) {
      if ((parent instanceof RendererChangeListener)) {
        RendererChangeListener rcl = (RendererChangeListener)parent;
        rcl.rendererChanged(event);

      }
      else
      {
        throw new RuntimeException("The renderer has changed and I don't know what to do!");
      }
    }
    else
    {
      configureRangeAxes();
      PlotChangeEvent e = new PlotChangeEvent(this);
      notifyListeners(e);
    }
  }
  









  public void addDomainMarker(CategoryMarker marker)
  {
    addDomainMarker(marker, Layer.FOREGROUND);
  }
  











  public void addDomainMarker(CategoryMarker marker, Layer layer)
  {
    addDomainMarker(0, marker, layer);
  }
  












  public void addDomainMarker(int index, CategoryMarker marker, Layer layer)
  {
    addDomainMarker(index, marker, layer, true);
  }
  
















  public void addDomainMarker(int index, CategoryMarker marker, Layer layer, boolean notify)
  {
    if (marker == null) {
      throw new IllegalArgumentException("Null 'marker' not permitted.");
    }
    if (layer == null) {
      throw new IllegalArgumentException("Null 'layer' not permitted.");
    }
    
    if (layer == Layer.FOREGROUND) {
      Collection markers = (Collection)foregroundDomainMarkers.get(new Integer(index));
      
      if (markers == null) {
        markers = new ArrayList();
        foregroundDomainMarkers.put(new Integer(index), markers);
      }
      markers.add(marker);
    }
    else if (layer == Layer.BACKGROUND) {
      Collection markers = (Collection)backgroundDomainMarkers.get(new Integer(index));
      
      if (markers == null) {
        markers = new ArrayList();
        backgroundDomainMarkers.put(new Integer(index), markers);
      }
      markers.add(marker);
    }
    marker.addChangeListener(this);
    if (notify) {
      fireChangeEvent();
    }
  }
  





  public void clearDomainMarkers()
  {
    if (backgroundDomainMarkers != null) {
      Set keys = backgroundDomainMarkers.keySet();
      Iterator iterator = keys.iterator();
      while (iterator.hasNext()) {
        Integer key = (Integer)iterator.next();
        clearDomainMarkers(key.intValue());
      }
      backgroundDomainMarkers.clear();
    }
    if (foregroundDomainMarkers != null) {
      Set keys = foregroundDomainMarkers.keySet();
      Iterator iterator = keys.iterator();
      while (iterator.hasNext()) {
        Integer key = (Integer)iterator.next();
        clearDomainMarkers(key.intValue());
      }
      foregroundDomainMarkers.clear();
    }
    fireChangeEvent();
  }
  






  public Collection getDomainMarkers(Layer layer)
  {
    return getDomainMarkers(0, layer);
  }
  








  public Collection getDomainMarkers(int index, Layer layer)
  {
    Collection result = null;
    Integer key = new Integer(index);
    if (layer == Layer.FOREGROUND) {
      result = (Collection)foregroundDomainMarkers.get(key);
    }
    else if (layer == Layer.BACKGROUND) {
      result = (Collection)backgroundDomainMarkers.get(key);
    }
    if (result != null) {
      result = Collections.unmodifiableCollection(result);
    }
    return result;
  }
  






  public void clearDomainMarkers(int index)
  {
    Integer key = new Integer(index);
    if (backgroundDomainMarkers != null) {
      Collection markers = (Collection)backgroundDomainMarkers.get(key);
      
      if (markers != null) {
        Iterator iterator = markers.iterator();
        while (iterator.hasNext()) {
          Marker m = (Marker)iterator.next();
          m.removeChangeListener(this);
        }
        markers.clear();
      }
    }
    if (foregroundDomainMarkers != null) {
      Collection markers = (Collection)foregroundDomainMarkers.get(key);
      
      if (markers != null) {
        Iterator iterator = markers.iterator();
        while (iterator.hasNext()) {
          Marker m = (Marker)iterator.next();
          m.removeChangeListener(this);
        }
        markers.clear();
      }
    }
    fireChangeEvent();
  }
  










  public boolean removeDomainMarker(Marker marker)
  {
    return removeDomainMarker(marker, Layer.FOREGROUND);
  }
  











  public boolean removeDomainMarker(Marker marker, Layer layer)
  {
    return removeDomainMarker(0, marker, layer);
  }
  












  public boolean removeDomainMarker(int index, Marker marker, Layer layer)
  {
    return removeDomainMarker(index, marker, layer, true);
  }
  





  public boolean removeDomainMarker(int index, Marker marker, Layer layer, boolean notify)
  {
    ArrayList markers;
    



    ArrayList markers;
    



    if (layer == Layer.FOREGROUND) {
      markers = (ArrayList)foregroundDomainMarkers.get(new Integer(index));
    }
    else
    {
      markers = (ArrayList)backgroundDomainMarkers.get(new Integer(index));
    }
    
    if (markers == null) {
      return false;
    }
    boolean removed = markers.remove(marker);
    if ((removed) && (notify)) {
      fireChangeEvent();
    }
    return removed;
  }
  









  public void addRangeMarker(Marker marker)
  {
    addRangeMarker(marker, Layer.FOREGROUND);
  }
  











  public void addRangeMarker(Marker marker, Layer layer)
  {
    addRangeMarker(0, marker, layer);
  }
  












  public void addRangeMarker(int index, Marker marker, Layer layer)
  {
    addRangeMarker(index, marker, layer, true);
  }
  

















  public void addRangeMarker(int index, Marker marker, Layer layer, boolean notify)
  {
    if (layer == Layer.FOREGROUND) {
      Collection markers = (Collection)foregroundRangeMarkers.get(new Integer(index));
      
      if (markers == null) {
        markers = new ArrayList();
        foregroundRangeMarkers.put(new Integer(index), markers);
      }
      markers.add(marker);
    }
    else if (layer == Layer.BACKGROUND) {
      Collection markers = (Collection)backgroundRangeMarkers.get(new Integer(index));
      
      if (markers == null) {
        markers = new ArrayList();
        backgroundRangeMarkers.put(new Integer(index), markers);
      }
      markers.add(marker);
    }
    marker.addChangeListener(this);
    if (notify) {
      fireChangeEvent();
    }
  }
  





  public void clearRangeMarkers()
  {
    if (backgroundRangeMarkers != null) {
      Set keys = backgroundRangeMarkers.keySet();
      Iterator iterator = keys.iterator();
      while (iterator.hasNext()) {
        Integer key = (Integer)iterator.next();
        clearRangeMarkers(key.intValue());
      }
      backgroundRangeMarkers.clear();
    }
    if (foregroundRangeMarkers != null) {
      Set keys = foregroundRangeMarkers.keySet();
      Iterator iterator = keys.iterator();
      while (iterator.hasNext()) {
        Integer key = (Integer)iterator.next();
        clearRangeMarkers(key.intValue());
      }
      foregroundRangeMarkers.clear();
    }
    fireChangeEvent();
  }
  








  public Collection getRangeMarkers(Layer layer)
  {
    return getRangeMarkers(0, layer);
  }
  








  public Collection getRangeMarkers(int index, Layer layer)
  {
    Collection result = null;
    Integer key = new Integer(index);
    if (layer == Layer.FOREGROUND) {
      result = (Collection)foregroundRangeMarkers.get(key);
    }
    else if (layer == Layer.BACKGROUND) {
      result = (Collection)backgroundRangeMarkers.get(key);
    }
    if (result != null) {
      result = Collections.unmodifiableCollection(result);
    }
    return result;
  }
  






  public void clearRangeMarkers(int index)
  {
    Integer key = new Integer(index);
    if (backgroundRangeMarkers != null) {
      Collection markers = (Collection)backgroundRangeMarkers.get(key);
      
      if (markers != null) {
        Iterator iterator = markers.iterator();
        while (iterator.hasNext()) {
          Marker m = (Marker)iterator.next();
          m.removeChangeListener(this);
        }
        markers.clear();
      }
    }
    if (foregroundRangeMarkers != null) {
      Collection markers = (Collection)foregroundRangeMarkers.get(key);
      
      if (markers != null) {
        Iterator iterator = markers.iterator();
        while (iterator.hasNext()) {
          Marker m = (Marker)iterator.next();
          m.removeChangeListener(this);
        }
        markers.clear();
      }
    }
    fireChangeEvent();
  }
  












  public boolean removeRangeMarker(Marker marker)
  {
    return removeRangeMarker(marker, Layer.FOREGROUND);
  }
  













  public boolean removeRangeMarker(Marker marker, Layer layer)
  {
    return removeRangeMarker(0, marker, layer);
  }
  














  public boolean removeRangeMarker(int index, Marker marker, Layer layer)
  {
    return removeRangeMarker(index, marker, layer, true);
  }
  
















  public boolean removeRangeMarker(int index, Marker marker, Layer layer, boolean notify)
  {
    if (marker == null)
      throw new IllegalArgumentException("Null 'marker' argument.");
    ArrayList markers;
    ArrayList markers;
    if (layer == Layer.FOREGROUND) {
      markers = (ArrayList)foregroundRangeMarkers.get(new Integer(index));
    }
    else
    {
      markers = (ArrayList)backgroundRangeMarkers.get(new Integer(index));
    }
    
    if (markers == null) {
      return false;
    }
    boolean removed = markers.remove(marker);
    if ((removed) && (notify)) {
      fireChangeEvent();
    }
    return removed;
  }
  









  public boolean isDomainCrosshairVisible()
  {
    return domainCrosshairVisible;
  }
  











  public void setDomainCrosshairVisible(boolean flag)
  {
    if (domainCrosshairVisible != flag) {
      domainCrosshairVisible = flag;
      fireChangeEvent();
    }
  }
  






  public Comparable getDomainCrosshairRowKey()
  {
    return domainCrosshairRowKey;
  }
  







  public void setDomainCrosshairRowKey(Comparable key)
  {
    setDomainCrosshairRowKey(key, true);
  }
  








  public void setDomainCrosshairRowKey(Comparable key, boolean notify)
  {
    domainCrosshairRowKey = key;
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public Comparable getDomainCrosshairColumnKey()
  {
    return domainCrosshairColumnKey;
  }
  







  public void setDomainCrosshairColumnKey(Comparable key)
  {
    setDomainCrosshairColumnKey(key, true);
  }
  








  public void setDomainCrosshairColumnKey(Comparable key, boolean notify)
  {
    domainCrosshairColumnKey = key;
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public int getCrosshairDatasetIndex()
  {
    return crosshairDatasetIndex;
  }
  







  public void setCrosshairDatasetIndex(int index)
  {
    setCrosshairDatasetIndex(index, true);
  }
  








  public void setCrosshairDatasetIndex(int index, boolean notify)
  {
    crosshairDatasetIndex = index;
    if (notify) {
      fireChangeEvent();
    }
  }
  









  public Paint getDomainCrosshairPaint()
  {
    return domainCrosshairPaint;
  }
  








  public void setDomainCrosshairPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    domainCrosshairPaint = paint;
    fireChangeEvent();
  }
  









  public Stroke getDomainCrosshairStroke()
  {
    return domainCrosshairStroke;
  }
  









  public void setDomainCrosshairStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    domainCrosshairStroke = stroke;
  }
  






  public boolean isRangeCrosshairVisible()
  {
    return rangeCrosshairVisible;
  }
  






  public void setRangeCrosshairVisible(boolean flag)
  {
    if (rangeCrosshairVisible != flag) {
      rangeCrosshairVisible = flag;
      fireChangeEvent();
    }
  }
  







  public boolean isRangeCrosshairLockedOnData()
  {
    return rangeCrosshairLockedOnData;
  }
  








  public void setRangeCrosshairLockedOnData(boolean flag)
  {
    if (rangeCrosshairLockedOnData != flag) {
      rangeCrosshairLockedOnData = flag;
      fireChangeEvent();
    }
  }
  






  public double getRangeCrosshairValue()
  {
    return rangeCrosshairValue;
  }
  







  public void setRangeCrosshairValue(double value)
  {
    setRangeCrosshairValue(value, true);
  }
  










  public void setRangeCrosshairValue(double value, boolean notify)
  {
    rangeCrosshairValue = value;
    if ((isRangeCrosshairVisible()) && (notify)) {
      fireChangeEvent();
    }
  }
  









  public Stroke getRangeCrosshairStroke()
  {
    return rangeCrosshairStroke;
  }
  









  public void setRangeCrosshairStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    rangeCrosshairStroke = stroke;
    fireChangeEvent();
  }
  








  public Paint getRangeCrosshairPaint()
  {
    return rangeCrosshairPaint;
  }
  







  public void setRangeCrosshairPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    rangeCrosshairPaint = paint;
    fireChangeEvent();
  }
  







  public List getAnnotations()
  {
    return annotations;
  }
  







  public void addAnnotation(CategoryAnnotation annotation)
  {
    addAnnotation(annotation, true);
  }
  








  public void addAnnotation(CategoryAnnotation annotation, boolean notify)
  {
    if (annotation == null) {
      throw new IllegalArgumentException("Null 'annotation' argument.");
    }
    annotations.add(annotation);
    if (notify) {
      fireChangeEvent();
    }
  }
  









  public boolean removeAnnotation(CategoryAnnotation annotation)
  {
    return removeAnnotation(annotation, true);
  }
  











  public boolean removeAnnotation(CategoryAnnotation annotation, boolean notify)
  {
    if (annotation == null) {
      throw new IllegalArgumentException("Null 'annotation' argument.");
    }
    boolean removed = annotations.remove(annotation);
    if ((removed) && (notify)) {
      fireChangeEvent();
    }
    return removed;
  }
  



  public void clearAnnotations()
  {
    annotations.clear();
    fireChangeEvent();
  }
  











  protected AxisSpace calculateDomainAxisSpace(Graphics2D g2, Rectangle2D plotArea, AxisSpace space)
  {
    if (space == null) {
      space = new AxisSpace();
    }
    

    if (fixedDomainAxisSpace != null) {
      if (orientation == PlotOrientation.HORIZONTAL) {
        space.ensureAtLeast(fixedDomainAxisSpace.getLeft(), RectangleEdge.LEFT);
        
        space.ensureAtLeast(fixedDomainAxisSpace.getRight(), RectangleEdge.RIGHT);

      }
      else if (orientation == PlotOrientation.VERTICAL) {
        space.ensureAtLeast(fixedDomainAxisSpace.getTop(), RectangleEdge.TOP);
        
        space.ensureAtLeast(fixedDomainAxisSpace.getBottom(), RectangleEdge.BOTTOM);
      }
      
    }
    else
    {
      RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(getDomainAxisLocation(), orientation);
      
      if (drawSharedDomainAxis) {
        space = getDomainAxis().reserveSpace(g2, this, plotArea, domainEdge, space);
      }
      


      for (int i = 0; i < domainAxes.size(); i++) {
        Axis xAxis = (Axis)domainAxes.get(i);
        if (xAxis != null) {
          RectangleEdge edge = getDomainAxisEdge(i);
          space = xAxis.reserveSpace(g2, this, plotArea, edge, space);
        }
      }
    }
    
    return space;
  }
  












  protected AxisSpace calculateRangeAxisSpace(Graphics2D g2, Rectangle2D plotArea, AxisSpace space)
  {
    if (space == null) {
      space = new AxisSpace();
    }
    

    if (fixedRangeAxisSpace != null) {
      if (orientation == PlotOrientation.HORIZONTAL) {
        space.ensureAtLeast(fixedRangeAxisSpace.getTop(), RectangleEdge.TOP);
        
        space.ensureAtLeast(fixedRangeAxisSpace.getBottom(), RectangleEdge.BOTTOM);

      }
      else if (orientation == PlotOrientation.VERTICAL) {
        space.ensureAtLeast(fixedRangeAxisSpace.getLeft(), RectangleEdge.LEFT);
        
        space.ensureAtLeast(fixedRangeAxisSpace.getRight(), RectangleEdge.RIGHT);
      }
      

    }
    else {
      for (int i = 0; i < rangeAxes.size(); i++) {
        Axis yAxis = (Axis)rangeAxes.get(i);
        if (yAxis != null) {
          RectangleEdge edge = getRangeAxisEdge(i);
          space = yAxis.reserveSpace(g2, this, plotArea, edge, space);
        }
      }
    }
    return space;
  }
  









  protected AxisSpace calculateAxisSpace(Graphics2D g2, Rectangle2D plotArea)
  {
    AxisSpace space = new AxisSpace();
    space = calculateRangeAxisSpace(g2, plotArea, space);
    space = calculateDomainAxisSpace(g2, plotArea, space);
    return space;
  }
  


















  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo state)
  {
    boolean b1 = area.getWidth() <= 10.0D;
    boolean b2 = area.getHeight() <= 10.0D;
    if ((b1) || (b2)) {
      return;
    }
    

    if (state == null)
    {


      state = new PlotRenderingInfo(null);
    }
    state.setPlotArea(area);
    

    RectangleInsets insets = getInsets();
    insets.trim(area);
    

    AxisSpace space = calculateAxisSpace(g2, area);
    Rectangle2D dataArea = space.shrink(area, null);
    axisOffset.trim(dataArea);
    
    state.setDataArea(dataArea);
    createAndAddEntity((Rectangle2D)dataArea.clone(), state, null, null);
    


    if (getRenderer() != null) {
      getRenderer().drawBackground(g2, this, dataArea);
    }
    else {
      drawBackground(g2, dataArea);
    }
    
    Map axisStateMap = drawAxes(g2, area, dataArea, state);
    


    if ((anchor != null) && (!dataArea.contains(anchor))) {
      anchor = ShapeUtilities.getPointInRectangle(anchor.getX(), anchor.getY(), dataArea);
    }
    
    CategoryCrosshairState crosshairState = new CategoryCrosshairState();
    crosshairState.setCrosshairDistance(Double.POSITIVE_INFINITY);
    crosshairState.setAnchor(anchor);
    



    crosshairState.setAnchorX(NaN.0D);
    crosshairState.setAnchorY(NaN.0D);
    if (anchor != null) {
      ValueAxis rangeAxis = getRangeAxis();
      if (rangeAxis != null) { double y;
        double y;
        if (getOrientation() == PlotOrientation.VERTICAL) {
          y = rangeAxis.java2DToValue(anchor.getY(), dataArea, getRangeAxisEdge());
        }
        else
        {
          y = rangeAxis.java2DToValue(anchor.getX(), dataArea, getRangeAxisEdge());
        }
        
        crosshairState.setAnchorY(y);
      }
    }
    crosshairState.setRowKey(getDomainCrosshairRowKey());
    crosshairState.setColumnKey(getDomainCrosshairColumnKey());
    crosshairState.setCrosshairY(getRangeCrosshairValue());
    

    Shape savedClip = g2.getClip();
    g2.clip(dataArea);
    
    drawDomainGridlines(g2, dataArea);
    
    AxisState rangeAxisState = (AxisState)axisStateMap.get(getRangeAxis());
    if ((rangeAxisState == null) && 
      (parentState != null)) {
      rangeAxisState = (AxisState)parentState.getSharedAxisStates().get(getRangeAxis());
    }
    

    if (rangeAxisState != null) {
      drawRangeGridlines(g2, dataArea, rangeAxisState.getTicks());
      drawZeroRangeBaseline(g2, dataArea);
    }
    

    for (int i = 0; i < renderers.size(); i++) {
      drawDomainMarkers(g2, dataArea, i, Layer.BACKGROUND);
    }
    for (int i = 0; i < renderers.size(); i++) {
      drawRangeMarkers(g2, dataArea, i, Layer.BACKGROUND);
    }
    

    boolean foundData = false;
    

    Composite originalComposite = g2.getComposite();
    g2.setComposite(AlphaComposite.getInstance(3, getForegroundAlpha()));
    

    DatasetRenderingOrder order = getDatasetRenderingOrder();
    if (order == DatasetRenderingOrder.FORWARD) {
      for (int i = 0; i < datasets.size(); i++) {
        foundData = (render(g2, dataArea, i, state, crosshairState)) || (foundData);
      }
      
    }
    else {
      for (int i = datasets.size() - 1; i >= 0; i--) {
        foundData = (render(g2, dataArea, i, state, crosshairState)) || (foundData);
      }
    }
    

    for (int i = 0; i < renderers.size(); i++) {
      drawDomainMarkers(g2, dataArea, i, Layer.FOREGROUND);
    }
    for (int i = 0; i < renderers.size(); i++) {
      drawRangeMarkers(g2, dataArea, i, Layer.FOREGROUND);
    }
    

    drawAnnotations(g2, dataArea);
    
    g2.setClip(savedClip);
    g2.setComposite(originalComposite);
    
    if (!foundData) {
      drawNoDataMessage(g2, dataArea);
    }
    
    int datasetIndex = crosshairState.getDatasetIndex();
    setCrosshairDatasetIndex(datasetIndex, false);
    

    Comparable rowKey = crosshairState.getRowKey();
    Comparable columnKey = crosshairState.getColumnKey();
    setDomainCrosshairRowKey(rowKey, false);
    setDomainCrosshairColumnKey(columnKey, false);
    if ((isDomainCrosshairVisible()) && (columnKey != null)) {
      Paint paint = getDomainCrosshairPaint();
      Stroke stroke = getDomainCrosshairStroke();
      drawDomainCrosshair(g2, dataArea, orientation, datasetIndex, rowKey, columnKey, stroke, paint);
    }
    


    ValueAxis yAxis = getRangeAxisForDataset(datasetIndex);
    RectangleEdge yAxisEdge = getRangeAxisEdge();
    if ((!rangeCrosshairLockedOnData) && (anchor != null)) { double yy;
      double yy;
      if (getOrientation() == PlotOrientation.VERTICAL) {
        yy = yAxis.java2DToValue(anchor.getY(), dataArea, yAxisEdge);
      }
      else {
        yy = yAxis.java2DToValue(anchor.getX(), dataArea, yAxisEdge);
      }
      crosshairState.setCrosshairY(yy);
    }
    setRangeCrosshairValue(crosshairState.getCrosshairY(), false);
    if (isRangeCrosshairVisible()) {
      double y = getRangeCrosshairValue();
      Paint paint = getRangeCrosshairPaint();
      Stroke stroke = getRangeCrosshairStroke();
      drawRangeCrosshair(g2, dataArea, getOrientation(), y, yAxis, stroke, paint);
    }
    


    if (isOutlineVisible()) {
      if (getRenderer() != null) {
        getRenderer().drawOutline(g2, this, dataArea);
      }
      else {
        drawOutline(g2, dataArea);
      }
    }
  }
  










  public void drawBackground(Graphics2D g2, Rectangle2D area)
  {
    fillBackground(g2, area, orientation);
    drawBackgroundImage(g2, area);
  }
  














  protected Map drawAxes(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, PlotRenderingInfo plotState)
  {
    AxisCollection axisCollection = new AxisCollection();
    

    for (int index = 0; index < domainAxes.size(); index++) {
      CategoryAxis xAxis = (CategoryAxis)domainAxes.get(index);
      if (xAxis != null) {
        axisCollection.add(xAxis, getDomainAxisEdge(index));
      }
    }
    

    for (int index = 0; index < rangeAxes.size(); index++) {
      ValueAxis yAxis = (ValueAxis)rangeAxes.get(index);
      if (yAxis != null) {
        axisCollection.add(yAxis, getRangeAxisEdge(index));
      }
    }
    
    Map axisStateMap = new HashMap();
    

    double cursor = dataArea.getMinY() - axisOffset.calculateTopOutset(dataArea.getHeight());
    
    Iterator iterator = axisCollection.getAxesAtTop().iterator();
    while (iterator.hasNext()) {
      Axis axis = (Axis)iterator.next();
      if (axis != null) {
        AxisState axisState = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.TOP, plotState);
        
        cursor = axisState.getCursor();
        axisStateMap.put(axis, axisState);
      }
    }
    

    cursor = dataArea.getMaxY() + axisOffset.calculateBottomOutset(dataArea.getHeight());
    
    iterator = axisCollection.getAxesAtBottom().iterator();
    while (iterator.hasNext()) {
      Axis axis = (Axis)iterator.next();
      if (axis != null) {
        AxisState axisState = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.BOTTOM, plotState);
        
        cursor = axisState.getCursor();
        axisStateMap.put(axis, axisState);
      }
    }
    

    cursor = dataArea.getMinX() - axisOffset.calculateLeftOutset(dataArea.getWidth());
    
    iterator = axisCollection.getAxesAtLeft().iterator();
    while (iterator.hasNext()) {
      Axis axis = (Axis)iterator.next();
      if (axis != null) {
        AxisState axisState = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.LEFT, plotState);
        
        cursor = axisState.getCursor();
        axisStateMap.put(axis, axisState);
      }
    }
    

    cursor = dataArea.getMaxX() + axisOffset.calculateRightOutset(dataArea.getWidth());
    
    iterator = axisCollection.getAxesAtRight().iterator();
    while (iterator.hasNext()) {
      Axis axis = (Axis)iterator.next();
      if (axis != null) {
        AxisState axisState = axis.draw(g2, cursor, plotArea, dataArea, RectangleEdge.RIGHT, plotState);
        
        cursor = axisState.getCursor();
        axisStateMap.put(axis, axisState);
      }
    }
    
    return axisStateMap;
  }
  

















  public boolean render(Graphics2D g2, Rectangle2D dataArea, int index, PlotRenderingInfo info, CategoryCrosshairState crosshairState)
  {
    boolean foundData = false;
    CategoryDataset currentDataset = getDataset(index);
    CategoryItemRenderer renderer = getRenderer(index);
    CategoryAxis domainAxis = getDomainAxisForDataset(index);
    ValueAxis rangeAxis = getRangeAxisForDataset(index);
    boolean hasData = !DatasetUtilities.isEmptyOrNull(currentDataset);
    if ((hasData) && (renderer != null))
    {
      foundData = true;
      CategoryItemRendererState state = renderer.initialise(g2, dataArea, this, index, info);
      
      state.setCrosshairState(crosshairState);
      int columnCount = currentDataset.getColumnCount();
      int rowCount = currentDataset.getRowCount();
      int passCount = renderer.getPassCount();
      for (int pass = 0; pass < passCount; pass++) {
        if (columnRenderingOrder == SortOrder.ASCENDING) {
          for (int column = 0; column < columnCount; column++) {
            if (rowRenderingOrder == SortOrder.ASCENDING) {
              for (int row = 0; row < rowCount; row++) {
                renderer.drawItem(g2, state, dataArea, this, domainAxis, rangeAxis, currentDataset, row, column, pass);
              }
              
            }
            else
            {
              for (int row = rowCount - 1; row >= 0; row--) {
                renderer.drawItem(g2, state, dataArea, this, domainAxis, rangeAxis, currentDataset, row, column, pass);
              }
              
            }
            
          }
          
        } else {
          for (int column = columnCount - 1; column >= 0; column--) {
            if (rowRenderingOrder == SortOrder.ASCENDING) {
              for (int row = 0; row < rowCount; row++) {
                renderer.drawItem(g2, state, dataArea, this, domainAxis, rangeAxis, currentDataset, row, column, pass);
              }
              
            }
            else
            {
              for (int row = rowCount - 1; row >= 0; row--) {
                renderer.drawItem(g2, state, dataArea, this, domainAxis, rangeAxis, currentDataset, row, column, pass);
              }
            }
          }
        }
      }
    }
    

    return foundData;
  }
  









  protected void drawDomainGridlines(Graphics2D g2, Rectangle2D dataArea)
  {
    if (!isDomainGridlinesVisible()) {
      return;
    }
    CategoryAnchor anchor = getDomainGridlinePosition();
    RectangleEdge domainAxisEdge = getDomainAxisEdge();
    CategoryDataset dataset = getDataset();
    if (dataset == null) {
      return;
    }
    CategoryAxis axis = getDomainAxis();
    if (axis != null) {
      int columnCount = dataset.getColumnCount();
      for (int c = 0; c < columnCount; c++) {
        double xx = axis.getCategoryJava2DCoordinate(anchor, c, columnCount, dataArea, domainAxisEdge);
        
        CategoryItemRenderer renderer1 = getRenderer();
        if (renderer1 != null) {
          renderer1.drawDomainGridline(g2, this, dataArea, xx);
        }
      }
    }
  }
  










  protected void drawRangeGridlines(Graphics2D g2, Rectangle2D dataArea, List ticks)
  {
    if ((!isRangeGridlinesVisible()) && (!isRangeMinorGridlinesVisible())) {
      return;
    }
    
    ValueAxis axis = getRangeAxis();
    if (axis == null) {
      return;
    }
    
    CategoryItemRenderer r = getRenderer();
    if (r == null) {
      return;
    }
    
    Stroke gridStroke = null;
    Paint gridPaint = null;
    boolean paintLine = false;
    Iterator iterator = ticks.iterator();
    while (iterator.hasNext()) {
      paintLine = false;
      ValueTick tick = (ValueTick)iterator.next();
      if ((tick.getTickType() == TickType.MINOR) && (isRangeMinorGridlinesVisible()))
      {
        gridStroke = getRangeMinorGridlineStroke();
        gridPaint = getRangeMinorGridlinePaint();
        paintLine = true;
      }
      else if ((tick.getTickType() == TickType.MAJOR) && (isRangeGridlinesVisible()))
      {
        gridStroke = getRangeGridlineStroke();
        gridPaint = getRangeGridlinePaint();
        paintLine = true;
      }
      if (((tick.getValue() != 0.0D) || (!isRangeZeroBaselineVisible())) && (paintLine))
      {


        if ((r instanceof AbstractCategoryItemRenderer)) {
          AbstractCategoryItemRenderer aci = (AbstractCategoryItemRenderer)r;
          
          aci.drawRangeLine(g2, this, axis, dataArea, tick.getValue(), gridPaint, gridStroke);

        }
        else
        {

          r.drawRangeGridline(g2, this, axis, dataArea, tick.getValue());
        }
      }
    }
  }
  










  protected void drawZeroRangeBaseline(Graphics2D g2, Rectangle2D area)
  {
    if (!isRangeZeroBaselineVisible()) {
      return;
    }
    CategoryItemRenderer r = getRenderer();
    if ((r instanceof AbstractCategoryItemRenderer)) {
      AbstractCategoryItemRenderer aci = (AbstractCategoryItemRenderer)r;
      aci.drawRangeLine(g2, this, getRangeAxis(), area, 0.0D, rangeZeroBaselinePaint, rangeZeroBaselineStroke);
    }
    else
    {
      r.drawRangeGridline(g2, this, getRangeAxis(), area, 0.0D);
    }
  }
  






  protected void drawAnnotations(Graphics2D g2, Rectangle2D dataArea)
  {
    if (getAnnotations() != null) {
      Iterator iterator = getAnnotations().iterator();
      while (iterator.hasNext()) {
        CategoryAnnotation annotation = (CategoryAnnotation)iterator.next();
        
        annotation.draw(g2, this, dataArea, getDomainAxis(), getRangeAxis());
      }
    }
  }
  














  protected void drawDomainMarkers(Graphics2D g2, Rectangle2D dataArea, int index, Layer layer)
  {
    CategoryItemRenderer r = getRenderer(index);
    if (r == null) {
      return;
    }
    
    Collection markers = getDomainMarkers(index, layer);
    CategoryAxis axis = getDomainAxisForDataset(index);
    if ((markers != null) && (axis != null)) {
      Iterator iterator = markers.iterator();
      while (iterator.hasNext()) {
        CategoryMarker marker = (CategoryMarker)iterator.next();
        r.drawDomainMarker(g2, this, axis, marker, dataArea);
      }
    }
  }
  













  protected void drawRangeMarkers(Graphics2D g2, Rectangle2D dataArea, int index, Layer layer)
  {
    CategoryItemRenderer r = getRenderer(index);
    if (r == null) {
      return;
    }
    
    Collection markers = getRangeMarkers(index, layer);
    ValueAxis axis = getRangeAxisForDataset(index);
    if ((markers != null) && (axis != null)) {
      Iterator iterator = markers.iterator();
      while (iterator.hasNext()) {
        Marker marker = (Marker)iterator.next();
        r.drawRangeMarker(g2, this, axis, marker, dataArea);
      }
    }
  }
  












  protected void drawRangeLine(Graphics2D g2, Rectangle2D dataArea, double value, Stroke stroke, Paint paint)
  {
    double java2D = getRangeAxis().valueToJava2D(value, dataArea, getRangeAxisEdge());
    
    Line2D line = null;
    if (orientation == PlotOrientation.HORIZONTAL) {
      line = new Line2D.Double(java2D, dataArea.getMinY(), java2D, dataArea.getMaxY());

    }
    else if (orientation == PlotOrientation.VERTICAL) {
      line = new Line2D.Double(dataArea.getMinX(), java2D, dataArea.getMaxX(), java2D);
    }
    
    g2.setStroke(stroke);
    g2.setPaint(paint);
    g2.draw(line);
  }
  





















  protected void drawDomainCrosshair(Graphics2D g2, Rectangle2D dataArea, PlotOrientation orientation, int datasetIndex, Comparable rowKey, Comparable columnKey, Stroke stroke, Paint paint)
  {
    CategoryDataset dataset = getDataset(datasetIndex);
    CategoryAxis axis = getDomainAxisForDataset(datasetIndex);
    CategoryItemRenderer renderer = getRenderer(datasetIndex);
    Line2D line = null;
    if (orientation == PlotOrientation.VERTICAL) {
      double xx = renderer.getItemMiddle(rowKey, columnKey, dataset, axis, dataArea, RectangleEdge.BOTTOM);
      
      line = new Line2D.Double(xx, dataArea.getMinY(), xx, dataArea.getMaxY());
    }
    else
    {
      double yy = renderer.getItemMiddle(rowKey, columnKey, dataset, axis, dataArea, RectangleEdge.LEFT);
      
      line = new Line2D.Double(dataArea.getMinX(), yy, dataArea.getMaxX(), yy);
    }
    
    g2.setStroke(stroke);
    g2.setPaint(paint);
    g2.draw(line);
  }
  



















  protected void drawRangeCrosshair(Graphics2D g2, Rectangle2D dataArea, PlotOrientation orientation, double value, ValueAxis axis, Stroke stroke, Paint paint)
  {
    if (!axis.getRange().contains(value)) {
      return;
    }
    Line2D line = null;
    if (orientation == PlotOrientation.HORIZONTAL) {
      double xx = axis.valueToJava2D(value, dataArea, RectangleEdge.BOTTOM);
      
      line = new Line2D.Double(xx, dataArea.getMinY(), xx, dataArea.getMaxY());
    }
    else
    {
      double yy = axis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
      
      line = new Line2D.Double(dataArea.getMinX(), yy, dataArea.getMaxX(), yy);
    }
    
    g2.setStroke(stroke);
    g2.setPaint(paint);
    g2.draw(line);
  }
  










  public Range getDataRange(ValueAxis axis)
  {
    Range result = null;
    List mappedDatasets = new ArrayList();
    
    int rangeIndex = rangeAxes.indexOf(axis);
    if (rangeIndex >= 0) {
      mappedDatasets.addAll(datasetsMappedToRangeAxis(rangeIndex));
    }
    else if (axis == getRangeAxis()) {
      mappedDatasets.addAll(datasetsMappedToRangeAxis(0));
    }
    


    Iterator iterator = mappedDatasets.iterator();
    while (iterator.hasNext()) {
      CategoryDataset d = (CategoryDataset)iterator.next();
      CategoryItemRenderer r = getRendererForDataset(d);
      if (r != null) {
        result = Range.combine(result, r.findRangeBounds(d));
      }
    }
    return result;
  }
  










  private List datasetsMappedToDomainAxis(int axisIndex)
  {
    Integer key = new Integer(axisIndex);
    List result = new ArrayList();
    for (int i = 0; i < datasets.size(); i++) {
      List mappedAxes = (List)datasetToDomainAxesMap.get(new Integer(i));
      
      CategoryDataset dataset = (CategoryDataset)datasets.get(i);
      if (mappedAxes == null) {
        if ((key.equals(ZERO)) && 
          (dataset != null)) {
          result.add(dataset);
        }
        

      }
      else if ((mappedAxes.contains(key)) && 
        (dataset != null)) {
        result.add(dataset);
      }
    }
    

    return result;
  }
  







  private List datasetsMappedToRangeAxis(int index)
  {
    Integer key = new Integer(index);
    List result = new ArrayList();
    for (int i = 0; i < datasets.size(); i++) {
      List mappedAxes = (List)datasetToRangeAxesMap.get(new Integer(i));
      
      if (mappedAxes == null) {
        if (key.equals(ZERO)) {
          result.add(datasets.get(i));
        }
        
      }
      else if (mappedAxes.contains(key)) {
        result.add(datasets.get(i));
      }
    }
    
    return result;
  }
  







  public int getWeight()
  {
    return weight;
  }
  







  public void setWeight(int weight)
  {
    this.weight = weight;
    fireChangeEvent();
  }
  






  public AxisSpace getFixedDomainAxisSpace()
  {
    return fixedDomainAxisSpace;
  }
  







  public void setFixedDomainAxisSpace(AxisSpace space)
  {
    setFixedDomainAxisSpace(space, true);
  }
  










  public void setFixedDomainAxisSpace(AxisSpace space, boolean notify)
  {
    fixedDomainAxisSpace = space;
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public AxisSpace getFixedRangeAxisSpace()
  {
    return fixedRangeAxisSpace;
  }
  







  public void setFixedRangeAxisSpace(AxisSpace space)
  {
    setFixedRangeAxisSpace(space, true);
  }
  










  public void setFixedRangeAxisSpace(AxisSpace space, boolean notify)
  {
    fixedRangeAxisSpace = space;
    if (notify) {
      fireChangeEvent();
    }
  }
  






  public List getCategories()
  {
    List result = null;
    if (getDataset() != null) {
      result = Collections.unmodifiableList(getDataset().getColumnKeys());
    }
    return result;
  }
  









  public List getCategoriesForAxis(CategoryAxis axis)
  {
    List result = new ArrayList();
    int axisIndex = domainAxes.indexOf(axis);
    List datasets = datasetsMappedToDomainAxis(axisIndex);
    Iterator iterator = datasets.iterator();
    while (iterator.hasNext()) {
      CategoryDataset dataset = (CategoryDataset)iterator.next();
      
      for (int i = 0; i < dataset.getColumnCount(); i++) {
        Comparable category = dataset.getColumnKey(i);
        if (!result.contains(category)) {
          result.add(category);
        }
      }
    }
    return result;
  }
  







  public boolean getDrawSharedDomainAxis()
  {
    return drawSharedDomainAxis;
  }
  







  public void setDrawSharedDomainAxis(boolean draw)
  {
    drawSharedDomainAxis = draw;
    fireChangeEvent();
  }
  







  public boolean isDomainPannable()
  {
    return false;
  }
  







  public boolean isRangePannable()
  {
    return rangePannable;
  }
  







  public void setRangePannable(boolean pannable)
  {
    rangePannable = pannable;
  }
  











  public void panDomainAxes(double percent, PlotRenderingInfo info, Point2D source) {}
  










  public void panRangeAxes(double percent, PlotRenderingInfo info, Point2D source)
  {
    if (!isRangePannable()) {
      return;
    }
    int rangeAxisCount = getRangeAxisCount();
    for (int i = 0; i < rangeAxisCount; i++) {
      ValueAxis axis = getRangeAxis(i);
      if (axis != null)
      {

        double length = axis.getRange().getLength();
        double adj = percent * length;
        if (axis.isInverted()) {
          adj = -adj;
        }
        axis.setRange(axis.getLowerBound() + adj, axis.getUpperBound() + adj);
      }
    }
  }
  







  public boolean isDomainZoomable()
  {
    return false;
  }
  






  public boolean isRangeZoomable()
  {
    return true;
  }
  












  public void zoomDomainAxes(double factor, PlotRenderingInfo state, Point2D source) {}
  












  public void zoomDomainAxes(double lowerPercent, double upperPercent, PlotRenderingInfo state, Point2D source) {}
  












  public void zoomDomainAxes(double factor, PlotRenderingInfo info, Point2D source, boolean useAnchor) {}
  











  public void zoomRangeAxes(double factor, PlotRenderingInfo state, Point2D source)
  {
    zoomRangeAxes(factor, state, source, false);
  }
  















  public void zoomRangeAxes(double factor, PlotRenderingInfo info, Point2D source, boolean useAnchor)
  {
    for (int i = 0; i < rangeAxes.size(); i++) {
      ValueAxis rangeAxis = (ValueAxis)rangeAxes.get(i);
      if (rangeAxis != null) {
        if (useAnchor)
        {

          double sourceY = source.getY();
          if (orientation == PlotOrientation.HORIZONTAL) {
            sourceY = source.getX();
          }
          double anchorY = rangeAxis.java2DToValue(sourceY, info.getDataArea(), getRangeAxisEdge());
          
          rangeAxis.resizeRange2(factor, anchorY);
        }
        else {
          rangeAxis.resizeRange(factor);
        }
      }
    }
  }
  








  public void zoomRangeAxes(double lowerPercent, double upperPercent, PlotRenderingInfo state, Point2D source)
  {
    for (int i = 0; i < rangeAxes.size(); i++) {
      ValueAxis rangeAxis = (ValueAxis)rangeAxes.get(i);
      if (rangeAxis != null) {
        rangeAxis.zoomRange(lowerPercent, upperPercent);
      }
    }
  }
  






  public double getAnchorValue()
  {
    return anchorValue;
  }
  







  public void setAnchorValue(double value)
  {
    setAnchorValue(value, true);
  }
  








  public void setAnchorValue(double value, boolean notify)
  {
    anchorValue = value;
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof CategoryPlot)) {
      return false;
    }
    CategoryPlot that = (CategoryPlot)obj;
    if (orientation != orientation) {
      return false;
    }
    if (!ObjectUtilities.equal(axisOffset, axisOffset)) {
      return false;
    }
    if (!domainAxes.equals(domainAxes)) {
      return false;
    }
    if (!domainAxisLocations.equals(domainAxisLocations)) {
      return false;
    }
    if (drawSharedDomainAxis != drawSharedDomainAxis) {
      return false;
    }
    if (!rangeAxes.equals(rangeAxes)) {
      return false;
    }
    if (!rangeAxisLocations.equals(rangeAxisLocations)) {
      return false;
    }
    if (!ObjectUtilities.equal(datasetToDomainAxesMap, datasetToDomainAxesMap))
    {
      return false;
    }
    if (!ObjectUtilities.equal(datasetToRangeAxesMap, datasetToRangeAxesMap))
    {
      return false;
    }
    if (!ObjectUtilities.equal(renderers, renderers)) {
      return false;
    }
    if (renderingOrder != renderingOrder) {
      return false;
    }
    if (columnRenderingOrder != columnRenderingOrder) {
      return false;
    }
    if (rowRenderingOrder != rowRenderingOrder) {
      return false;
    }
    if (domainGridlinesVisible != domainGridlinesVisible) {
      return false;
    }
    if (domainGridlinePosition != domainGridlinePosition) {
      return false;
    }
    if (!ObjectUtilities.equal(domainGridlineStroke, domainGridlineStroke))
    {
      return false;
    }
    if (!PaintUtilities.equal(domainGridlinePaint, domainGridlinePaint))
    {
      return false;
    }
    if (rangeGridlinesVisible != rangeGridlinesVisible) {
      return false;
    }
    if (!ObjectUtilities.equal(rangeGridlineStroke, rangeGridlineStroke))
    {
      return false;
    }
    if (!PaintUtilities.equal(rangeGridlinePaint, rangeGridlinePaint))
    {
      return false;
    }
    if (anchorValue != anchorValue) {
      return false;
    }
    if (rangeCrosshairVisible != rangeCrosshairVisible) {
      return false;
    }
    if (rangeCrosshairValue != rangeCrosshairValue) {
      return false;
    }
    if (!ObjectUtilities.equal(rangeCrosshairStroke, rangeCrosshairStroke))
    {
      return false;
    }
    if (!PaintUtilities.equal(rangeCrosshairPaint, rangeCrosshairPaint))
    {
      return false;
    }
    if (rangeCrosshairLockedOnData != rangeCrosshairLockedOnData)
    {
      return false;
    }
    if (!ObjectUtilities.equal(foregroundDomainMarkers, foregroundDomainMarkers))
    {
      return false;
    }
    if (!ObjectUtilities.equal(backgroundDomainMarkers, backgroundDomainMarkers))
    {
      return false;
    }
    if (!ObjectUtilities.equal(foregroundRangeMarkers, foregroundRangeMarkers))
    {
      return false;
    }
    if (!ObjectUtilities.equal(backgroundRangeMarkers, backgroundRangeMarkers))
    {
      return false;
    }
    if (!ObjectUtilities.equal(annotations, annotations)) {
      return false;
    }
    if (weight != weight) {
      return false;
    }
    if (!ObjectUtilities.equal(fixedDomainAxisSpace, fixedDomainAxisSpace))
    {
      return false;
    }
    if (!ObjectUtilities.equal(fixedRangeAxisSpace, fixedRangeAxisSpace))
    {
      return false;
    }
    if (!ObjectUtilities.equal(fixedLegendItems, fixedLegendItems))
    {
      return false;
    }
    if (domainCrosshairVisible != domainCrosshairVisible) {
      return false;
    }
    if (crosshairDatasetIndex != crosshairDatasetIndex) {
      return false;
    }
    if (!ObjectUtilities.equal(domainCrosshairColumnKey, domainCrosshairColumnKey))
    {
      return false;
    }
    if (!ObjectUtilities.equal(domainCrosshairRowKey, domainCrosshairRowKey))
    {
      return false;
    }
    if (!PaintUtilities.equal(domainCrosshairPaint, domainCrosshairPaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(domainCrosshairStroke, domainCrosshairStroke))
    {
      return false;
    }
    if (rangeMinorGridlinesVisible != rangeMinorGridlinesVisible)
    {
      return false;
    }
    if (!PaintUtilities.equal(rangeMinorGridlinePaint, rangeMinorGridlinePaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(rangeMinorGridlineStroke, rangeMinorGridlineStroke))
    {
      return false;
    }
    if (rangeZeroBaselineVisible != rangeZeroBaselineVisible) {
      return false;
    }
    if (!PaintUtilities.equal(rangeZeroBaselinePaint, rangeZeroBaselinePaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(rangeZeroBaselineStroke, rangeZeroBaselineStroke))
    {
      return false;
    }
    return super.equals(obj);
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    CategoryPlot clone = (CategoryPlot)super.clone();
    
    domainAxes = new ObjectList();
    for (int i = 0; i < domainAxes.size(); i++) {
      CategoryAxis xAxis = (CategoryAxis)domainAxes.get(i);
      if (xAxis != null) {
        CategoryAxis clonedAxis = (CategoryAxis)xAxis.clone();
        clone.setDomainAxis(i, clonedAxis);
      }
    }
    domainAxisLocations = ((ObjectList)domainAxisLocations.clone());
    

    rangeAxes = new ObjectList();
    for (int i = 0; i < rangeAxes.size(); i++) {
      ValueAxis yAxis = (ValueAxis)rangeAxes.get(i);
      if (yAxis != null) {
        ValueAxis clonedAxis = (ValueAxis)yAxis.clone();
        clone.setRangeAxis(i, clonedAxis);
      }
    }
    rangeAxisLocations = ((ObjectList)rangeAxisLocations.clone());
    
    datasets = ((ObjectList)datasets.clone());
    for (int i = 0; i < datasets.size(); i++) {
      CategoryDataset dataset = clone.getDataset(i);
      if (dataset != null) {
        dataset.addChangeListener(clone);
      }
    }
    datasetToDomainAxesMap = new TreeMap();
    datasetToDomainAxesMap.putAll(datasetToDomainAxesMap);
    datasetToRangeAxesMap = new TreeMap();
    datasetToRangeAxesMap.putAll(datasetToRangeAxesMap);
    
    renderers = ((ObjectList)renderers.clone());
    if (fixedDomainAxisSpace != null) {
      fixedDomainAxisSpace = ((AxisSpace)ObjectUtilities.clone(fixedDomainAxisSpace));
    }
    
    if (fixedRangeAxisSpace != null) {
      fixedRangeAxisSpace = ((AxisSpace)ObjectUtilities.clone(fixedRangeAxisSpace));
    }
    

    annotations = ((List)ObjectUtilities.deepClone(annotations));
    foregroundDomainMarkers = cloneMarkerMap(foregroundDomainMarkers);
    
    backgroundDomainMarkers = cloneMarkerMap(backgroundDomainMarkers);
    
    foregroundRangeMarkers = cloneMarkerMap(foregroundRangeMarkers);
    
    backgroundRangeMarkers = cloneMarkerMap(backgroundRangeMarkers);
    
    if (fixedLegendItems != null) {
      fixedLegendItems = ((LegendItemCollection)fixedLegendItems.clone());
    }
    
    return clone;
  }
  









  private Map cloneMarkerMap(Map map)
    throws CloneNotSupportedException
  {
    Map clone = new HashMap();
    Set keys = map.keySet();
    Iterator iterator = keys.iterator();
    while (iterator.hasNext()) {
      Object key = iterator.next();
      List entry = (List)map.get(key);
      Object toAdd = ObjectUtilities.deepClone(entry);
      clone.put(key, toAdd);
    }
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeStroke(domainGridlineStroke, stream);
    SerialUtilities.writePaint(domainGridlinePaint, stream);
    SerialUtilities.writeStroke(rangeGridlineStroke, stream);
    SerialUtilities.writePaint(rangeGridlinePaint, stream);
    SerialUtilities.writeStroke(rangeCrosshairStroke, stream);
    SerialUtilities.writePaint(rangeCrosshairPaint, stream);
    SerialUtilities.writeStroke(domainCrosshairStroke, stream);
    SerialUtilities.writePaint(domainCrosshairPaint, stream);
    SerialUtilities.writeStroke(rangeMinorGridlineStroke, stream);
    SerialUtilities.writePaint(rangeMinorGridlinePaint, stream);
    SerialUtilities.writeStroke(rangeZeroBaselineStroke, stream);
    SerialUtilities.writePaint(rangeZeroBaselinePaint, stream);
  }
  








  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    domainGridlineStroke = SerialUtilities.readStroke(stream);
    domainGridlinePaint = SerialUtilities.readPaint(stream);
    rangeGridlineStroke = SerialUtilities.readStroke(stream);
    rangeGridlinePaint = SerialUtilities.readPaint(stream);
    rangeCrosshairStroke = SerialUtilities.readStroke(stream);
    rangeCrosshairPaint = SerialUtilities.readPaint(stream);
    domainCrosshairStroke = SerialUtilities.readStroke(stream);
    domainCrosshairPaint = SerialUtilities.readPaint(stream);
    rangeMinorGridlineStroke = SerialUtilities.readStroke(stream);
    rangeMinorGridlinePaint = SerialUtilities.readPaint(stream);
    rangeZeroBaselineStroke = SerialUtilities.readStroke(stream);
    rangeZeroBaselinePaint = SerialUtilities.readPaint(stream);
    
    for (int i = 0; i < domainAxes.size(); i++) {
      CategoryAxis xAxis = (CategoryAxis)domainAxes.get(i);
      if (xAxis != null) {
        xAxis.setPlot(this);
        xAxis.addChangeListener(this);
      }
    }
    for (int i = 0; i < rangeAxes.size(); i++) {
      ValueAxis yAxis = (ValueAxis)rangeAxes.get(i);
      if (yAxis != null) {
        yAxis.setPlot(this);
        yAxis.addChangeListener(this);
      }
    }
    int datasetCount = datasets.size();
    for (int i = 0; i < datasetCount; i++) {
      Dataset dataset = (Dataset)datasets.get(i);
      if (dataset != null) {
        dataset.addChangeListener(this);
      }
    }
    int rendererCount = renderers.size();
    for (int i = 0; i < rendererCount; i++) {
      CategoryItemRenderer renderer = (CategoryItemRenderer)renderers.get(i);
      
      if (renderer != null) {
        renderer.addChangeListener(this);
      }
    }
  }
}
