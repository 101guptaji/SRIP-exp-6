package org.jfree.chart.plot;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.event.EventListenerList;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.PlotEntity;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.event.AxisChangeListener;
import org.jfree.chart.event.ChartChangeEventType;
import org.jfree.chart.event.MarkerChangeEvent;
import org.jfree.chart.event.MarkerChangeListener;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.io.SerialUtilities;
import org.jfree.text.G2TextMeasurer;
import org.jfree.text.TextBlock;
import org.jfree.text.TextBlockAnchor;
import org.jfree.text.TextUtilities;
import org.jfree.ui.Align;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;













































































































































public abstract class Plot
  implements AxisChangeListener, DatasetChangeListener, MarkerChangeListener, LegendItemSource, PublicCloneable, Cloneable, Serializable
{
  private static final long serialVersionUID = -8831571430103671324L;
  public static final Number ZERO = new Integer(0);
  

  public static final RectangleInsets DEFAULT_INSETS = new RectangleInsets(4.0D, 8.0D, 4.0D, 8.0D);
  


  public static final Stroke DEFAULT_OUTLINE_STROKE = new BasicStroke(0.5F);
  

  public static final Paint DEFAULT_OUTLINE_PAINT = Color.gray;
  

  public static final float DEFAULT_FOREGROUND_ALPHA = 1.0F;
  

  public static final float DEFAULT_BACKGROUND_ALPHA = 1.0F;
  

  public static final Paint DEFAULT_BACKGROUND_PAINT = Color.white;
  

  public static final int MINIMUM_WIDTH_TO_DRAW = 10;
  

  public static final int MINIMUM_HEIGHT_TO_DRAW = 10;
  

  public static final Shape DEFAULT_LEGEND_ITEM_BOX = new Rectangle2D.Double(-4.0D, -4.0D, 8.0D, 8.0D);
  


  public static final Shape DEFAULT_LEGEND_ITEM_CIRCLE = new Ellipse2D.Double(-4.0D, -4.0D, 8.0D, 8.0D);
  


  private Plot parent;
  


  private DatasetGroup datasetGroup;
  


  private String noDataMessage;
  


  private Font noDataMessageFont;
  


  private transient Paint noDataMessagePaint;
  

  private RectangleInsets insets;
  

  private boolean outlineVisible;
  

  private transient Stroke outlineStroke;
  

  private transient Paint outlinePaint;
  

  private transient Paint backgroundPaint;
  

  private transient Image backgroundImage;
  

  private int backgroundImageAlignment = 15;
  

  private float backgroundImageAlpha = 0.5F;
  



  private float foregroundAlpha;
  


  private float backgroundAlpha;
  


  private DrawingSupplier drawingSupplier;
  


  private transient EventListenerList listenerList;
  


  private boolean notify;
  



  protected Plot()
  {
    parent = null;
    insets = DEFAULT_INSETS;
    backgroundPaint = DEFAULT_BACKGROUND_PAINT;
    backgroundAlpha = 1.0F;
    backgroundImage = null;
    outlineVisible = true;
    outlineStroke = DEFAULT_OUTLINE_STROKE;
    outlinePaint = DEFAULT_OUTLINE_PAINT;
    foregroundAlpha = 1.0F;
    
    noDataMessage = null;
    noDataMessageFont = new Font("SansSerif", 0, 12);
    noDataMessagePaint = Color.black;
    
    drawingSupplier = new DefaultDrawingSupplier();
    
    notify = true;
    listenerList = new EventListenerList();
  }
  







  public DatasetGroup getDatasetGroup()
  {
    return datasetGroup;
  }
  






  protected void setDatasetGroup(DatasetGroup group)
  {
    datasetGroup = group;
  }
  









  public String getNoDataMessage()
  {
    return noDataMessage;
  }
  








  public void setNoDataMessage(String message)
  {
    noDataMessage = message;
    fireChangeEvent();
  }
  







  public Font getNoDataMessageFont()
  {
    return noDataMessageFont;
  }
  







  public void setNoDataMessageFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    noDataMessageFont = font;
    fireChangeEvent();
  }
  







  public Paint getNoDataMessagePaint()
  {
    return noDataMessagePaint;
  }
  







  public void setNoDataMessagePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    noDataMessagePaint = paint;
    fireChangeEvent();
  }
  









  public abstract String getPlotType();
  








  public Plot getParent()
  {
    return parent;
  }
  







  public void setParent(Plot parent)
  {
    this.parent = parent;
  }
  







  public Plot getRootPlot()
  {
    Plot p = getParent();
    if (p == null) {
      return this;
    }
    
    return p.getRootPlot();
  }
  











  public boolean isSubplot()
  {
    return getParent() != null;
  }
  






  public RectangleInsets getInsets()
  {
    return insets;
  }
  








  public void setInsets(RectangleInsets insets)
  {
    setInsets(insets, true);
  }
  










  public void setInsets(RectangleInsets insets, boolean notify)
  {
    if (insets == null) {
      throw new IllegalArgumentException("Null 'insets' argument.");
    }
    if (!this.insets.equals(insets)) {
      this.insets = insets;
      if (notify) {
        fireChangeEvent();
      }
    }
  }
  







  public Paint getBackgroundPaint()
  {
    return backgroundPaint;
  }
  








  public void setBackgroundPaint(Paint paint)
  {
    if (paint == null) {
      if (backgroundPaint != null) {
        backgroundPaint = null;
        fireChangeEvent();
      }
    }
    else {
      if ((backgroundPaint != null) && 
        (backgroundPaint.equals(paint))) {
        return;
      }
      
      backgroundPaint = paint;
      fireChangeEvent();
    }
  }
  







  public float getBackgroundAlpha()
  {
    return backgroundAlpha;
  }
  







  public void setBackgroundAlpha(float alpha)
  {
    if (backgroundAlpha != alpha) {
      backgroundAlpha = alpha;
      fireChangeEvent();
    }
  }
  






  public DrawingSupplier getDrawingSupplier()
  {
    DrawingSupplier result = null;
    Plot p = getParent();
    if (p != null) {
      result = p.getDrawingSupplier();
    }
    else {
      result = drawingSupplier;
    }
    return result;
  }
  











  public void setDrawingSupplier(DrawingSupplier supplier)
  {
    drawingSupplier = supplier;
    fireChangeEvent();
  }
  














  public void setDrawingSupplier(DrawingSupplier supplier, boolean notify)
  {
    drawingSupplier = supplier;
    if (notify) {
      fireChangeEvent();
    }
  }
  







  public Image getBackgroundImage()
  {
    return backgroundImage;
  }
  







  public void setBackgroundImage(Image image)
  {
    backgroundImage = image;
    fireChangeEvent();
  }
  








  public int getBackgroundImageAlignment()
  {
    return backgroundImageAlignment;
  }
  









  public void setBackgroundImageAlignment(int alignment)
  {
    if (backgroundImageAlignment != alignment) {
      backgroundImageAlignment = alignment;
      fireChangeEvent();
    }
  }
  








  public float getBackgroundImageAlpha()
  {
    return backgroundImageAlpha;
  }
  










  public void setBackgroundImageAlpha(float alpha)
  {
    if ((alpha < 0.0F) || (alpha > 1.0F)) {
      throw new IllegalArgumentException("The 'alpha' value must be in the range 0.0f to 1.0f.");
    }
    if (backgroundImageAlpha != alpha) {
      backgroundImageAlpha = alpha;
      fireChangeEvent();
    }
  }
  












  public boolean isOutlineVisible()
  {
    return outlineVisible;
  }
  









  public void setOutlineVisible(boolean visible)
  {
    outlineVisible = visible;
    fireChangeEvent();
  }
  






  public Stroke getOutlineStroke()
  {
    return outlineStroke;
  }
  








  public void setOutlineStroke(Stroke stroke)
  {
    if (stroke == null) {
      if (outlineStroke != null) {
        outlineStroke = null;
        fireChangeEvent();
      }
    }
    else {
      if ((outlineStroke != null) && 
        (outlineStroke.equals(stroke))) {
        return;
      }
      
      outlineStroke = stroke;
      fireChangeEvent();
    }
  }
  






  public Paint getOutlinePaint()
  {
    return outlinePaint;
  }
  








  public void setOutlinePaint(Paint paint)
  {
    if (paint == null) {
      if (outlinePaint != null) {
        outlinePaint = null;
        fireChangeEvent();
      }
    }
    else {
      if ((outlinePaint != null) && 
        (outlinePaint.equals(paint))) {
        return;
      }
      
      outlinePaint = paint;
      fireChangeEvent();
    }
  }
  






  public float getForegroundAlpha()
  {
    return foregroundAlpha;
  }
  







  public void setForegroundAlpha(float alpha)
  {
    if (foregroundAlpha != alpha) {
      foregroundAlpha = alpha;
      fireChangeEvent();
    }
  }
  






  public LegendItemCollection getLegendItems()
  {
    return null;
  }
  









  public boolean isNotify()
  {
    return notify;
  }
  









  public void setNotify(boolean notify)
  {
    this.notify = notify;
    
    if (notify) {
      notifyListeners(new PlotChangeEvent(this));
    }
  }
  






  public void addChangeListener(PlotChangeListener listener)
  {
    listenerList.add(PlotChangeListener.class, listener);
  }
  






  public void removeChangeListener(PlotChangeListener listener)
  {
    listenerList.remove(PlotChangeListener.class, listener);
  }
  






  public void notifyListeners(PlotChangeEvent event)
  {
    if (!notify) {
      return;
    }
    Object[] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == PlotChangeListener.class) {
        ((PlotChangeListener)listeners[(i + 1)]).plotChanged(event);
      }
    }
  }
  




  protected void fireChangeEvent()
  {
    notifyListeners(new PlotChangeEvent(this));
  }
  















  public abstract void draw(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D, Point2D paramPoint2D, PlotState paramPlotState, PlotRenderingInfo paramPlotRenderingInfo);
  














  public void drawBackground(Graphics2D g2, Rectangle2D area)
  {
    fillBackground(g2, area);
    drawBackgroundImage(g2, area);
  }
  









  protected void fillBackground(Graphics2D g2, Rectangle2D area)
  {
    fillBackground(g2, area, PlotOrientation.VERTICAL);
  }
  












  protected void fillBackground(Graphics2D g2, Rectangle2D area, PlotOrientation orientation)
  {
    if (orientation == null) {
      throw new IllegalArgumentException("Null 'orientation' argument.");
    }
    if (backgroundPaint == null) {
      return;
    }
    Paint p = backgroundPaint;
    if ((p instanceof GradientPaint)) {
      GradientPaint gp = (GradientPaint)p;
      if (orientation == PlotOrientation.VERTICAL) {
        p = new GradientPaint((float)area.getCenterX(), (float)area.getMaxY(), gp.getColor1(), (float)area.getCenterX(), (float)area.getMinY(), gp.getColor2());



      }
      else if (orientation == PlotOrientation.HORIZONTAL) {
        p = new GradientPaint((float)area.getMinX(), (float)area.getCenterY(), gp.getColor1(), (float)area.getMaxX(), (float)area.getCenterY(), gp.getColor2());
      }
    }
    


    Composite originalComposite = g2.getComposite();
    g2.setComposite(AlphaComposite.getInstance(3, backgroundAlpha));
    
    g2.setPaint(p);
    g2.fill(area);
    g2.setComposite(originalComposite);
  }
  










  public void drawBackgroundImage(Graphics2D g2, Rectangle2D area)
  {
    if (backgroundImage != null) {
      Composite originalComposite = g2.getComposite();
      g2.setComposite(AlphaComposite.getInstance(3, backgroundImageAlpha));
      
      Rectangle2D dest = new Rectangle2D.Double(0.0D, 0.0D, backgroundImage.getWidth(null), backgroundImage.getHeight(null));
      

      Align.align(dest, area, backgroundImageAlignment);
      g2.drawImage(backgroundImage, (int)dest.getX(), (int)dest.getY(), (int)dest.getWidth() + 1, (int)dest.getHeight() + 1, null);
      

      g2.setComposite(originalComposite);
    }
  }
  








  public void drawOutline(Graphics2D g2, Rectangle2D area)
  {
    if (!outlineVisible) {
      return;
    }
    if ((outlineStroke != null) && (outlinePaint != null)) {
      g2.setStroke(outlineStroke);
      g2.setPaint(outlinePaint);
      g2.draw(area);
    }
  }
  





  protected void drawNoDataMessage(Graphics2D g2, Rectangle2D area)
  {
    Shape savedClip = g2.getClip();
    g2.clip(area);
    String message = noDataMessage;
    if (message != null) {
      g2.setFont(noDataMessageFont);
      g2.setPaint(noDataMessagePaint);
      TextBlock block = TextUtilities.createTextBlock(noDataMessage, noDataMessageFont, noDataMessagePaint, 0.9F * (float)area.getWidth(), new G2TextMeasurer(g2));
      


      block.draw(g2, (float)area.getCenterX(), (float)area.getCenterY(), TextBlockAnchor.CENTER);
    }
    
    g2.setClip(savedClip);
  }
  














  protected void createAndAddEntity(Rectangle2D dataArea, PlotRenderingInfo plotState, String toolTip, String urlText)
  {
    if ((plotState != null) && (plotState.getOwner() != null)) {
      EntityCollection e = plotState.getOwner().getEntityCollection();
      if (e != null) {
        e.add(new PlotEntity(dataArea, this, toolTip, urlText));
      }
    }
  }
  








  public void handleClick(int x, int y, PlotRenderingInfo info) {}
  








  public void zoom(double percent) {}
  








  public void axisChanged(AxisChangeEvent event)
  {
    fireChangeEvent();
  }
  







  public void datasetChanged(DatasetChangeEvent event)
  {
    PlotChangeEvent newEvent = new PlotChangeEvent(this);
    newEvent.setType(ChartChangeEventType.DATASET_UPDATED);
    notifyListeners(newEvent);
  }
  







  public void markerChanged(MarkerChangeEvent event)
  {
    fireChangeEvent();
  }
  











  protected double getRectX(double x, double w1, double w2, RectangleEdge edge)
  {
    double result = x;
    if (edge == RectangleEdge.LEFT) {
      result += w1;
    }
    else if (edge == RectangleEdge.RIGHT) {
      result += w2;
    }
    return result;
  }
  












  protected double getRectY(double y, double h1, double h2, RectangleEdge edge)
  {
    double result = y;
    if (edge == RectangleEdge.TOP) {
      result += h1;
    }
    else if (edge == RectangleEdge.BOTTOM) {
      result += h2;
    }
    return result;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Plot)) {
      return false;
    }
    Plot that = (Plot)obj;
    if (!ObjectUtilities.equal(noDataMessage, noDataMessage)) {
      return false;
    }
    if (!ObjectUtilities.equal(noDataMessageFont, noDataMessageFont))
    {

      return false;
    }
    if (!PaintUtilities.equal(noDataMessagePaint, noDataMessagePaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(insets, insets)) {
      return false;
    }
    if (outlineVisible != outlineVisible) {
      return false;
    }
    if (!ObjectUtilities.equal(outlineStroke, outlineStroke)) {
      return false;
    }
    if (!PaintUtilities.equal(outlinePaint, outlinePaint)) {
      return false;
    }
    if (!PaintUtilities.equal(backgroundPaint, backgroundPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(backgroundImage, backgroundImage))
    {
      return false;
    }
    if (backgroundImageAlignment != backgroundImageAlignment) {
      return false;
    }
    if (backgroundImageAlpha != backgroundImageAlpha) {
      return false;
    }
    if (foregroundAlpha != foregroundAlpha) {
      return false;
    }
    if (backgroundAlpha != backgroundAlpha) {
      return false;
    }
    if (!drawingSupplier.equals(drawingSupplier)) {
      return false;
    }
    if (notify != notify) {
      return false;
    }
    return true;
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    Plot clone = (Plot)super.clone();
    

    if (datasetGroup != null) {
      datasetGroup = ((DatasetGroup)ObjectUtilities.clone(datasetGroup));
    }
    
    drawingSupplier = ((DrawingSupplier)ObjectUtilities.clone(drawingSupplier));
    
    listenerList = new EventListenerList();
    return clone;
  }
  






  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(noDataMessagePaint, stream);
    SerialUtilities.writeStroke(outlineStroke, stream);
    SerialUtilities.writePaint(outlinePaint, stream);
    
    SerialUtilities.writePaint(backgroundPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    noDataMessagePaint = SerialUtilities.readPaint(stream);
    outlineStroke = SerialUtilities.readStroke(stream);
    outlinePaint = SerialUtilities.readPaint(stream);
    
    backgroundPaint = SerialUtilities.readPaint(stream);
    
    listenerList = new EventListenerList();
  }
  










  public static RectangleEdge resolveDomainAxisLocation(AxisLocation location, PlotOrientation orientation)
  {
    if (location == null) {
      throw new IllegalArgumentException("Null 'location' argument.");
    }
    if (orientation == null) {
      throw new IllegalArgumentException("Null 'orientation' argument.");
    }
    
    RectangleEdge result = null;
    
    if (location == AxisLocation.TOP_OR_RIGHT) {
      if (orientation == PlotOrientation.HORIZONTAL) {
        result = RectangleEdge.RIGHT;
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        result = RectangleEdge.TOP;
      }
    }
    else if (location == AxisLocation.TOP_OR_LEFT) {
      if (orientation == PlotOrientation.HORIZONTAL) {
        result = RectangleEdge.LEFT;
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        result = RectangleEdge.TOP;
      }
    }
    else if (location == AxisLocation.BOTTOM_OR_RIGHT) {
      if (orientation == PlotOrientation.HORIZONTAL) {
        result = RectangleEdge.RIGHT;
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        result = RectangleEdge.BOTTOM;
      }
    }
    else if (location == AxisLocation.BOTTOM_OR_LEFT) {
      if (orientation == PlotOrientation.HORIZONTAL) {
        result = RectangleEdge.LEFT;
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        result = RectangleEdge.BOTTOM;
      }
    }
    
    if (result == null) {
      throw new IllegalStateException("resolveDomainAxisLocation()");
    }
    return result;
  }
  










  public static RectangleEdge resolveRangeAxisLocation(AxisLocation location, PlotOrientation orientation)
  {
    if (location == null) {
      throw new IllegalArgumentException("Null 'location' argument.");
    }
    if (orientation == null) {
      throw new IllegalArgumentException("Null 'orientation' argument.");
    }
    
    RectangleEdge result = null;
    
    if (location == AxisLocation.TOP_OR_RIGHT) {
      if (orientation == PlotOrientation.HORIZONTAL) {
        result = RectangleEdge.TOP;
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        result = RectangleEdge.RIGHT;
      }
    }
    else if (location == AxisLocation.TOP_OR_LEFT) {
      if (orientation == PlotOrientation.HORIZONTAL) {
        result = RectangleEdge.TOP;
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        result = RectangleEdge.LEFT;
      }
    }
    else if (location == AxisLocation.BOTTOM_OR_RIGHT) {
      if (orientation == PlotOrientation.HORIZONTAL) {
        result = RectangleEdge.BOTTOM;
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        result = RectangleEdge.RIGHT;
      }
    }
    else if (location == AxisLocation.BOTTOM_OR_LEFT) {
      if (orientation == PlotOrientation.HORIZONTAL) {
        result = RectangleEdge.BOTTOM;
      }
      else if (orientation == PlotOrientation.VERTICAL) {
        result = RectangleEdge.LEFT;
      }
    }
    

    if (result == null) {
      throw new IllegalStateException("resolveRangeAxisLocation()");
    }
    return result;
  }
}
