package org.jfree.chart.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import javax.swing.event.EventListenerList;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.AxisEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.event.AxisChangeListener;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
































































































public abstract class Axis
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 7719289504573298271L;
  public static final boolean DEFAULT_AXIS_VISIBLE = true;
  public static final Font DEFAULT_AXIS_LABEL_FONT = new Font("SansSerif", 0, 12);
  


  public static final Paint DEFAULT_AXIS_LABEL_PAINT = Color.black;
  

  public static final RectangleInsets DEFAULT_AXIS_LABEL_INSETS = new RectangleInsets(3.0D, 3.0D, 3.0D, 3.0D);
  


  public static final Paint DEFAULT_AXIS_LINE_PAINT = Color.gray;
  

  public static final Stroke DEFAULT_AXIS_LINE_STROKE = new BasicStroke(1.0F);
  

  public static final boolean DEFAULT_TICK_LABELS_VISIBLE = true;
  

  public static final Font DEFAULT_TICK_LABEL_FONT = new Font("SansSerif", 0, 10);
  


  public static final Paint DEFAULT_TICK_LABEL_PAINT = Color.black;
  

  public static final RectangleInsets DEFAULT_TICK_LABEL_INSETS = new RectangleInsets(2.0D, 4.0D, 2.0D, 4.0D);
  


  public static final boolean DEFAULT_TICK_MARKS_VISIBLE = true;
  

  public static final Stroke DEFAULT_TICK_MARK_STROKE = new BasicStroke(1.0F);
  

  public static final Paint DEFAULT_TICK_MARK_PAINT = Color.gray;
  



  public static final float DEFAULT_TICK_MARK_INSIDE_LENGTH = 0.0F;
  



  public static final float DEFAULT_TICK_MARK_OUTSIDE_LENGTH = 2.0F;
  



  private boolean visible;
  



  private String label;
  


  private Font labelFont;
  


  private transient Paint labelPaint;
  


  private RectangleInsets labelInsets;
  


  private double labelAngle;
  


  private boolean axisLineVisible;
  


  private transient Stroke axisLineStroke;
  


  private transient Paint axisLinePaint;
  


  private boolean tickLabelsVisible;
  


  private Font tickLabelFont;
  


  private transient Paint tickLabelPaint;
  


  private RectangleInsets tickLabelInsets;
  


  private boolean tickMarksVisible;
  


  private float tickMarkInsideLength;
  


  private float tickMarkOutsideLength;
  


  private boolean minorTickMarksVisible;
  


  private float minorTickMarkInsideLength;
  


  private float minorTickMarkOutsideLength;
  


  private transient Stroke tickMarkStroke;
  


  private transient Paint tickMarkPaint;
  


  private double fixedDimension;
  


  private transient Plot plot;
  


  private transient EventListenerList listenerList;
  



  protected Axis(String label)
  {
    this.label = label;
    visible = true;
    labelFont = DEFAULT_AXIS_LABEL_FONT;
    labelPaint = DEFAULT_AXIS_LABEL_PAINT;
    labelInsets = DEFAULT_AXIS_LABEL_INSETS;
    labelAngle = 0.0D;
    
    axisLineVisible = true;
    axisLinePaint = DEFAULT_AXIS_LINE_PAINT;
    axisLineStroke = DEFAULT_AXIS_LINE_STROKE;
    
    tickLabelsVisible = true;
    tickLabelFont = DEFAULT_TICK_LABEL_FONT;
    tickLabelPaint = DEFAULT_TICK_LABEL_PAINT;
    tickLabelInsets = DEFAULT_TICK_LABEL_INSETS;
    
    tickMarksVisible = true;
    tickMarkStroke = DEFAULT_TICK_MARK_STROKE;
    tickMarkPaint = DEFAULT_TICK_MARK_PAINT;
    tickMarkInsideLength = 0.0F;
    tickMarkOutsideLength = 2.0F;
    
    minorTickMarksVisible = false;
    minorTickMarkInsideLength = 0.0F;
    minorTickMarkOutsideLength = 2.0F;
    
    plot = null;
    
    listenerList = new EventListenerList();
  }
  








  public boolean isVisible()
  {
    return visible;
  }
  







  public void setVisible(boolean flag)
  {
    if (flag != visible) {
      visible = flag;
      fireChangeEvent();
    }
  }
  








  public String getLabel()
  {
    return label;
  }
  










  public void setLabel(String label)
  {
    String existing = this.label;
    if (existing != null) {
      if (!existing.equals(label)) {
        this.label = label;
        fireChangeEvent();
      }
      
    }
    else if (label != null) {
      this.label = label;
      fireChangeEvent();
    }
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
    if (!labelFont.equals(font)) {
      labelFont = font;
      fireChangeEvent();
    }
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
  







  public RectangleInsets getLabelInsets()
  {
    return labelInsets;
  }
  







  public void setLabelInsets(RectangleInsets insets)
  {
    setLabelInsets(insets, true);
  }
  








  public void setLabelInsets(RectangleInsets insets, boolean notify)
  {
    if (insets == null) {
      throw new IllegalArgumentException("Null 'insets' argument.");
    }
    if (!insets.equals(labelInsets)) {
      labelInsets = insets;
      if (notify) {
        fireChangeEvent();
      }
    }
  }
  






  public double getLabelAngle()
  {
    return labelAngle;
  }
  







  public void setLabelAngle(double angle)
  {
    labelAngle = angle;
    fireChangeEvent();
  }
  








  public boolean isAxisLineVisible()
  {
    return axisLineVisible;
  }
  









  public void setAxisLineVisible(boolean visible)
  {
    axisLineVisible = visible;
    fireChangeEvent();
  }
  






  public Paint getAxisLinePaint()
  {
    return axisLinePaint;
  }
  







  public void setAxisLinePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    axisLinePaint = paint;
    fireChangeEvent();
  }
  






  public Stroke getAxisLineStroke()
  {
    return axisLineStroke;
  }
  







  public void setAxisLineStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    axisLineStroke = stroke;
    fireChangeEvent();
  }
  








  public boolean isTickLabelsVisible()
  {
    return tickLabelsVisible;
  }
  











  public void setTickLabelsVisible(boolean flag)
  {
    if (flag != tickLabelsVisible) {
      tickLabelsVisible = flag;
      fireChangeEvent();
    }
  }
  











  public boolean isMinorTickMarksVisible()
  {
    return minorTickMarksVisible;
  }
  









  public void setMinorTickMarksVisible(boolean flag)
  {
    if (flag != minorTickMarksVisible) {
      minorTickMarksVisible = flag;
      fireChangeEvent();
    }
  }
  






  public Font getTickLabelFont()
  {
    return tickLabelFont;
  }
  








  public void setTickLabelFont(Font font)
  {
    if (font == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    
    if (!tickLabelFont.equals(font)) {
      tickLabelFont = font;
      fireChangeEvent();
    }
  }
  







  public Paint getTickLabelPaint()
  {
    return tickLabelPaint;
  }
  







  public void setTickLabelPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    tickLabelPaint = paint;
    fireChangeEvent();
  }
  






  public RectangleInsets getTickLabelInsets()
  {
    return tickLabelInsets;
  }
  







  public void setTickLabelInsets(RectangleInsets insets)
  {
    if (insets == null) {
      throw new IllegalArgumentException("Null 'insets' argument.");
    }
    if (!tickLabelInsets.equals(insets)) {
      tickLabelInsets = insets;
      fireChangeEvent();
    }
  }
  








  public boolean isTickMarksVisible()
  {
    return tickMarksVisible;
  }
  







  public void setTickMarksVisible(boolean flag)
  {
    if (flag != tickMarksVisible) {
      tickMarksVisible = flag;
      fireChangeEvent();
    }
  }
  







  public float getTickMarkInsideLength()
  {
    return tickMarkInsideLength;
  }
  







  public void setTickMarkInsideLength(float length)
  {
    tickMarkInsideLength = length;
    fireChangeEvent();
  }
  







  public float getTickMarkOutsideLength()
  {
    return tickMarkOutsideLength;
  }
  







  public void setTickMarkOutsideLength(float length)
  {
    tickMarkOutsideLength = length;
    fireChangeEvent();
  }
  






  public Stroke getTickMarkStroke()
  {
    return tickMarkStroke;
  }
  







  public void setTickMarkStroke(Stroke stroke)
  {
    if (stroke == null) {
      throw new IllegalArgumentException("Null 'stroke' argument.");
    }
    if (!tickMarkStroke.equals(stroke)) {
      tickMarkStroke = stroke;
      fireChangeEvent();
    }
  }
  






  public Paint getTickMarkPaint()
  {
    return tickMarkPaint;
  }
  







  public void setTickMarkPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    tickMarkPaint = paint;
    fireChangeEvent();
  }
  









  public float getMinorTickMarkInsideLength()
  {
    return minorTickMarkInsideLength;
  }
  









  public void setMinorTickMarkInsideLength(float length)
  {
    minorTickMarkInsideLength = length;
    fireChangeEvent();
  }
  









  public float getMinorTickMarkOutsideLength()
  {
    return minorTickMarkOutsideLength;
  }
  









  public void setMinorTickMarkOutsideLength(float length)
  {
    minorTickMarkOutsideLength = length;
    fireChangeEvent();
  }
  








  public Plot getPlot()
  {
    return plot;
  }
  








  public void setPlot(Plot plot)
  {
    this.plot = plot;
    configure();
  }
  






  public double getFixedDimension()
  {
    return fixedDimension;
  }
  












  public void setFixedDimension(double dimension)
  {
    fixedDimension = dimension;
  }
  














  public abstract void configure();
  













  public abstract AxisSpace reserveSpace(Graphics2D paramGraphics2D, Plot paramPlot, Rectangle2D paramRectangle2D, RectangleEdge paramRectangleEdge, AxisSpace paramAxisSpace);
  













  public abstract AxisState draw(Graphics2D paramGraphics2D, double paramDouble, Rectangle2D paramRectangle2D1, Rectangle2D paramRectangle2D2, RectangleEdge paramRectangleEdge, PlotRenderingInfo paramPlotRenderingInfo);
  













  public abstract List refreshTicks(Graphics2D paramGraphics2D, AxisState paramAxisState, Rectangle2D paramRectangle2D, RectangleEdge paramRectangleEdge);
  













  protected void createAndAddEntity(double cursor, AxisState state, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState)
  {
    if ((plotState == null) || (plotState.getOwner() == null)) {
      return;
    }
    Rectangle2D hotspot = null;
    if (edge.equals(RectangleEdge.TOP)) {
      hotspot = new Rectangle2D.Double(dataArea.getX(), state.getCursor(), dataArea.getWidth(), cursor - state.getCursor());


    }
    else if (edge.equals(RectangleEdge.BOTTOM)) {
      hotspot = new Rectangle2D.Double(dataArea.getX(), cursor, dataArea.getWidth(), state.getCursor() - cursor);

    }
    else if (edge.equals(RectangleEdge.LEFT)) {
      hotspot = new Rectangle2D.Double(state.getCursor(), dataArea.getY(), cursor - state.getCursor(), dataArea.getHeight());


    }
    else if (edge.equals(RectangleEdge.RIGHT)) {
      hotspot = new Rectangle2D.Double(cursor, dataArea.getY(), state.getCursor() - cursor, dataArea.getHeight());
    }
    
    EntityCollection e = plotState.getOwner().getEntityCollection();
    if (e != null) {
      e.add(new AxisEntity(hotspot, this));
    }
  }
  






  public void addChangeListener(AxisChangeListener listener)
  {
    listenerList.add(AxisChangeListener.class, listener);
  }
  






  public void removeChangeListener(AxisChangeListener listener)
  {
    listenerList.remove(AxisChangeListener.class, listener);
  }
  








  public boolean hasListener(EventListener listener)
  {
    List list = Arrays.asList(listenerList.getListenerList());
    return list.contains(listener);
  }
  





  protected void notifyListeners(AxisChangeEvent event)
  {
    Object[] listeners = listenerList.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == AxisChangeListener.class) {
        ((AxisChangeListener)listeners[(i + 1)]).axisChanged(event);
      }
    }
  }
  




  protected void fireChangeEvent()
  {
    notifyListeners(new AxisChangeEvent(this));
  }
  









  protected Rectangle2D getLabelEnclosure(Graphics2D g2, RectangleEdge edge)
  {
    Rectangle2D result = new Rectangle2D.Double();
    String axisLabel = getLabel();
    if ((axisLabel != null) && (!axisLabel.equals(""))) {
      FontMetrics fm = g2.getFontMetrics(getLabelFont());
      Rectangle2D bounds = TextUtilities.getTextBounds(axisLabel, g2, fm);
      RectangleInsets insets = getLabelInsets();
      bounds = insets.createOutsetRectangle(bounds);
      double angle = getLabelAngle();
      if ((edge == RectangleEdge.LEFT) || (edge == RectangleEdge.RIGHT)) {
        angle -= 1.5707963267948966D;
      }
      double x = bounds.getCenterX();
      double y = bounds.getCenterY();
      AffineTransform transformer = AffineTransform.getRotateInstance(angle, x, y);
      
      Shape labelBounds = transformer.createTransformedShape(bounds);
      result = labelBounds.getBounds2D();
    }
    
    return result;
  }
  
















  protected AxisState drawLabel(String label, Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, AxisState state)
  {
    if (state == null) {
      throw new IllegalArgumentException("Null 'state' argument.");
    }
    
    if ((label == null) || (label.equals(""))) {
      return state;
    }
    
    Font font = getLabelFont();
    RectangleInsets insets = getLabelInsets();
    g2.setFont(font);
    g2.setPaint(getLabelPaint());
    FontMetrics fm = g2.getFontMetrics();
    Rectangle2D labelBounds = TextUtilities.getTextBounds(label, g2, fm);
    
    if (edge == RectangleEdge.TOP) {
      AffineTransform t = AffineTransform.getRotateInstance(getLabelAngle(), labelBounds.getCenterX(), labelBounds.getCenterY());
      

      Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
      labelBounds = rotatedLabelBounds.getBounds2D();
      double labelx = dataArea.getCenterX();
      double labely = state.getCursor() - insets.getBottom() - labelBounds.getHeight() / 2.0D;
      
      TextUtilities.drawRotatedString(label, g2, (float)labelx, (float)labely, TextAnchor.CENTER, getLabelAngle(), TextAnchor.CENTER);
      

      state.cursorUp(insets.getTop() + labelBounds.getHeight() + insets.getBottom());

    }
    else if (edge == RectangleEdge.BOTTOM) {
      AffineTransform t = AffineTransform.getRotateInstance(getLabelAngle(), labelBounds.getCenterX(), labelBounds.getCenterY());
      

      Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
      labelBounds = rotatedLabelBounds.getBounds2D();
      double labelx = dataArea.getCenterX();
      double labely = state.getCursor() + insets.getTop() + labelBounds.getHeight() / 2.0D;
      
      TextUtilities.drawRotatedString(label, g2, (float)labelx, (float)labely, TextAnchor.CENTER, getLabelAngle(), TextAnchor.CENTER);
      

      state.cursorDown(insets.getTop() + labelBounds.getHeight() + insets.getBottom());

    }
    else if (edge == RectangleEdge.LEFT) {
      AffineTransform t = AffineTransform.getRotateInstance(getLabelAngle() - 1.5707963267948966D, labelBounds.getCenterX(), labelBounds.getCenterY());
      

      Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
      labelBounds = rotatedLabelBounds.getBounds2D();
      double labelx = state.getCursor() - insets.getRight() - labelBounds.getWidth() / 2.0D;
      
      double labely = dataArea.getCenterY();
      TextUtilities.drawRotatedString(label, g2, (float)labelx, (float)labely, TextAnchor.CENTER, getLabelAngle() - 1.5707963267948966D, TextAnchor.CENTER);
      

      state.cursorLeft(insets.getLeft() + labelBounds.getWidth() + insets.getRight());

    }
    else if (edge == RectangleEdge.RIGHT)
    {
      AffineTransform t = AffineTransform.getRotateInstance(getLabelAngle() + 1.5707963267948966D, labelBounds.getCenterX(), labelBounds.getCenterY());
      

      Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
      labelBounds = rotatedLabelBounds.getBounds2D();
      double labelx = state.getCursor() + insets.getLeft() + labelBounds.getWidth() / 2.0D;
      
      double labely = dataArea.getY() + dataArea.getHeight() / 2.0D;
      TextUtilities.drawRotatedString(label, g2, (float)labelx, (float)labely, TextAnchor.CENTER, getLabelAngle() + 1.5707963267948966D, TextAnchor.CENTER);
      

      state.cursorRight(insets.getLeft() + labelBounds.getWidth() + insets.getRight());
    }
    


    return state;
  }
  










  protected void drawAxisLine(Graphics2D g2, double cursor, Rectangle2D dataArea, RectangleEdge edge)
  {
    Line2D axisLine = null;
    if (edge == RectangleEdge.TOP) {
      axisLine = new Line2D.Double(dataArea.getX(), cursor, dataArea.getMaxX(), cursor);

    }
    else if (edge == RectangleEdge.BOTTOM) {
      axisLine = new Line2D.Double(dataArea.getX(), cursor, dataArea.getMaxX(), cursor);

    }
    else if (edge == RectangleEdge.LEFT) {
      axisLine = new Line2D.Double(cursor, dataArea.getY(), cursor, dataArea.getMaxY());

    }
    else if (edge == RectangleEdge.RIGHT) {
      axisLine = new Line2D.Double(cursor, dataArea.getY(), cursor, dataArea.getMaxY());
    }
    
    g2.setPaint(axisLinePaint);
    g2.setStroke(axisLineStroke);
    g2.draw(axisLine);
  }
  







  public Object clone()
    throws CloneNotSupportedException
  {
    Axis clone = (Axis)super.clone();
    
    plot = null;
    listenerList = new EventListenerList();
    return clone;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Axis)) {
      return false;
    }
    Axis that = (Axis)obj;
    if (visible != visible) {
      return false;
    }
    if (!ObjectUtilities.equal(label, label)) {
      return false;
    }
    if (!ObjectUtilities.equal(labelFont, labelFont)) {
      return false;
    }
    if (!PaintUtilities.equal(labelPaint, labelPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(labelInsets, labelInsets)) {
      return false;
    }
    if (labelAngle != labelAngle) {
      return false;
    }
    if (axisLineVisible != axisLineVisible) {
      return false;
    }
    if (!ObjectUtilities.equal(axisLineStroke, axisLineStroke)) {
      return false;
    }
    if (!PaintUtilities.equal(axisLinePaint, axisLinePaint)) {
      return false;
    }
    if (tickLabelsVisible != tickLabelsVisible) {
      return false;
    }
    if (!ObjectUtilities.equal(tickLabelFont, tickLabelFont)) {
      return false;
    }
    if (!PaintUtilities.equal(tickLabelPaint, tickLabelPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(tickLabelInsets, tickLabelInsets))
    {

      return false;
    }
    if (tickMarksVisible != tickMarksVisible) {
      return false;
    }
    if (tickMarkInsideLength != tickMarkInsideLength) {
      return false;
    }
    if (tickMarkOutsideLength != tickMarkOutsideLength) {
      return false;
    }
    if (!PaintUtilities.equal(tickMarkPaint, tickMarkPaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(tickMarkStroke, tickMarkStroke)) {
      return false;
    }
    if (minorTickMarksVisible != minorTickMarksVisible) {
      return false;
    }
    if (minorTickMarkInsideLength != minorTickMarkInsideLength) {
      return false;
    }
    if (minorTickMarkOutsideLength != minorTickMarkOutsideLength) {
      return false;
    }
    if (fixedDimension != fixedDimension) {
      return false;
    }
    return true;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(labelPaint, stream);
    SerialUtilities.writePaint(tickLabelPaint, stream);
    SerialUtilities.writeStroke(axisLineStroke, stream);
    SerialUtilities.writePaint(axisLinePaint, stream);
    SerialUtilities.writeStroke(tickMarkStroke, stream);
    SerialUtilities.writePaint(tickMarkPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    labelPaint = SerialUtilities.readPaint(stream);
    tickLabelPaint = SerialUtilities.readPaint(stream);
    axisLineStroke = SerialUtilities.readStroke(stream);
    axisLinePaint = SerialUtilities.readPaint(stream);
    tickMarkStroke = SerialUtilities.readStroke(stream);
    tickMarkPaint = SerialUtilities.readPaint(stream);
    listenerList = new EventListenerList();
  }
}
