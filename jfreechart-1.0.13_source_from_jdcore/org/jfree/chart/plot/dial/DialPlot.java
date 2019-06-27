package org.jfree.chart.plot.dial;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.ValueDataset;
import org.jfree.util.ObjectList;
import org.jfree.util.ObjectUtilities;






































































































public class DialPlot
  extends Plot
  implements DialLayerChangeListener
{
  private DialLayer background;
  private DialLayer cap;
  private DialFrame dialFrame;
  private ObjectList datasets;
  private ObjectList scales;
  private ObjectList datasetToScaleMap;
  private List layers;
  private List pointers;
  private double viewX;
  private double viewY;
  private double viewW;
  private double viewH;
  
  public DialPlot()
  {
    this(null);
  }
  




  public DialPlot(ValueDataset dataset)
  {
    background = null;
    cap = null;
    dialFrame = new ArcDialFrame();
    datasets = new ObjectList();
    if (dataset != null) {
      setDataset(dataset);
    }
    scales = new ObjectList();
    datasetToScaleMap = new ObjectList();
    layers = new ArrayList();
    pointers = new ArrayList();
    viewX = 0.0D;
    viewY = 0.0D;
    viewW = 1.0D;
    viewH = 1.0D;
  }
  






  public DialLayer getBackground()
  {
    return background;
  }
  







  public void setBackground(DialLayer background)
  {
    if (this.background != null) {
      this.background.removeChangeListener(this);
    }
    this.background = background;
    if (background != null) {
      background.addChangeListener(this);
    }
    fireChangeEvent();
  }
  






  public DialLayer getCap()
  {
    return cap;
  }
  







  public void setCap(DialLayer cap)
  {
    if (this.cap != null) {
      this.cap.removeChangeListener(this);
    }
    this.cap = cap;
    if (cap != null) {
      cap.addChangeListener(this);
    }
    fireChangeEvent();
  }
  






  public DialFrame getDialFrame()
  {
    return dialFrame;
  }
  







  public void setDialFrame(DialFrame frame)
  {
    if (frame == null) {
      throw new IllegalArgumentException("Null 'frame' argument.");
    }
    dialFrame.removeChangeListener(this);
    dialFrame = frame;
    frame.addChangeListener(this);
    fireChangeEvent();
  }
  







  public double getViewX()
  {
    return viewX;
  }
  







  public double getViewY()
  {
    return viewY;
  }
  







  public double getViewWidth()
  {
    return viewW;
  }
  







  public double getViewHeight()
  {
    return viewH;
  }
  













  public void setView(double x, double y, double w, double h)
  {
    viewX = x;
    viewY = y;
    viewW = w;
    viewH = h;
    fireChangeEvent();
  }
  





  public void addLayer(DialLayer layer)
  {
    if (layer == null) {
      throw new IllegalArgumentException("Null 'layer' argument.");
    }
    layers.add(layer);
    layer.addChangeListener(this);
    fireChangeEvent();
  }
  






  public int getLayerIndex(DialLayer layer)
  {
    if (layer == null) {
      throw new IllegalArgumentException("Null 'layer' argument.");
    }
    return layers.indexOf(layer);
  }
  





  public void removeLayer(int index)
  {
    DialLayer layer = (DialLayer)layers.get(index);
    if (layer != null) {
      layer.removeChangeListener(this);
    }
    layers.remove(index);
    fireChangeEvent();
  }
  






  public void removeLayer(DialLayer layer)
  {
    removeLayer(getLayerIndex(layer));
  }
  





  public void addPointer(DialPointer pointer)
  {
    if (pointer == null) {
      throw new IllegalArgumentException("Null 'pointer' argument.");
    }
    pointers.add(pointer);
    pointer.addChangeListener(this);
    fireChangeEvent();
  }
  






  public int getPointerIndex(DialPointer pointer)
  {
    if (pointer == null) {
      throw new IllegalArgumentException("Null 'pointer' argument.");
    }
    return pointers.indexOf(pointer);
  }
  





  public void removePointer(int index)
  {
    DialPointer pointer = (DialPointer)pointers.get(index);
    if (pointer != null) {
      pointer.removeChangeListener(this);
    }
    pointers.remove(index);
    fireChangeEvent();
  }
  






  public void removePointer(DialPointer pointer)
  {
    removeLayer(getPointerIndex(pointer));
  }
  







  public DialPointer getPointerForDataset(int datasetIndex)
  {
    DialPointer result = null;
    Iterator iterator = pointers.iterator();
    while (iterator.hasNext()) {
      DialPointer p = (DialPointer)iterator.next();
      if (p.getDatasetIndex() == datasetIndex) {
        return p;
      }
    }
    return result;
  }
  




  public ValueDataset getDataset()
  {
    return getDataset(0);
  }
  






  public ValueDataset getDataset(int index)
  {
    ValueDataset result = null;
    if (datasets.size() > index) {
      result = (ValueDataset)datasets.get(index);
    }
    return result;
  }
  






  public void setDataset(ValueDataset dataset)
  {
    setDataset(0, dataset);
  }
  






  public void setDataset(int index, ValueDataset dataset)
  {
    ValueDataset existing = (ValueDataset)datasets.get(index);
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
  













  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info)
  {
    Shape origClip = g2.getClip();
    g2.setClip(area);
    

    Rectangle2D frame = viewToFrame(area);
    

    if ((background != null) && (background.isVisible())) {
      if (background.isClippedToWindow()) {
        Shape savedClip = g2.getClip();
        g2.clip(dialFrame.getWindow(frame));
        background.draw(g2, this, frame, area);
        g2.setClip(savedClip);
      }
      else {
        background.draw(g2, this, frame, area);
      }
    }
    
    Iterator iterator = layers.iterator();
    while (iterator.hasNext()) {
      DialLayer current = (DialLayer)iterator.next();
      if (current.isVisible()) {
        if (current.isClippedToWindow()) {
          Shape savedClip = g2.getClip();
          g2.clip(dialFrame.getWindow(frame));
          current.draw(g2, this, frame, area);
          g2.setClip(savedClip);
        }
        else {
          current.draw(g2, this, frame, area);
        }
      }
    }
    

    iterator = pointers.iterator();
    while (iterator.hasNext()) {
      DialPointer current = (DialPointer)iterator.next();
      if (current.isVisible()) {
        if (current.isClippedToWindow()) {
          Shape savedClip = g2.getClip();
          g2.clip(dialFrame.getWindow(frame));
          current.draw(g2, this, frame, area);
          g2.setClip(savedClip);
        }
        else {
          current.draw(g2, this, frame, area);
        }
      }
    }
    

    if ((cap != null) && (cap.isVisible())) {
      if (cap.isClippedToWindow()) {
        Shape savedClip = g2.getClip();
        g2.clip(dialFrame.getWindow(frame));
        cap.draw(g2, this, frame, area);
        g2.setClip(savedClip);
      }
      else {
        cap.draw(g2, this, frame, area);
      }
    }
    
    if (dialFrame.isVisible()) {
      dialFrame.draw(g2, this, frame, area);
    }
    
    g2.setClip(origClip);
  }
  







  private Rectangle2D viewToFrame(Rectangle2D view)
  {
    double width = view.getWidth() / viewW;
    double height = view.getHeight() / viewH;
    double x = view.getX() - width * viewX;
    double y = view.getY() - height * viewY;
    return new Rectangle2D.Double(x, y, width, height);
  }
  






  public double getValue(int datasetIndex)
  {
    double result = NaN.0D;
    ValueDataset dataset = getDataset(datasetIndex);
    if (dataset != null) {
      Number n = dataset.getValue();
      if (n != null) {
        result = n.doubleValue();
      }
    }
    return result;
  }
  






  public void addScale(int index, DialScale scale)
  {
    if (scale == null) {
      throw new IllegalArgumentException("Null 'scale' argument.");
    }
    DialScale existing = (DialScale)scales.get(index);
    if (existing != null) {
      removeLayer(existing);
    }
    layers.add(scale);
    scales.set(index, scale);
    scale.addChangeListener(this);
    fireChangeEvent();
  }
  






  public DialScale getScale(int index)
  {
    DialScale result = null;
    if (scales.size() > index) {
      result = (DialScale)scales.get(index);
    }
    return result;
  }
  





  public void mapDatasetToScale(int index, int scaleIndex)
  {
    datasetToScaleMap.set(index, new Integer(scaleIndex));
    fireChangeEvent();
  }
  






  public DialScale getScaleForDataset(int datasetIndex)
  {
    DialScale result = (DialScale)scales.get(0);
    Integer scaleIndex = (Integer)datasetToScaleMap.get(datasetIndex);
    if (scaleIndex != null) {
      result = getScale(scaleIndex.intValue());
    }
    return result;
  }
  









  public static Rectangle2D rectangleByRadius(Rectangle2D rect, double radiusW, double radiusH)
  {
    if (rect == null) {
      throw new IllegalArgumentException("Null 'rect' argument.");
    }
    double x = rect.getCenterX();
    double y = rect.getCenterY();
    double w = rect.getWidth() * radiusW;
    double h = rect.getHeight() * radiusH;
    return new Rectangle2D.Double(x - w / 2.0D, y - h / 2.0D, w, h);
  }
  





  public void dialLayerChanged(DialLayerChangeEvent event)
  {
    fireChangeEvent();
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DialPlot)) {
      return false;
    }
    DialPlot that = (DialPlot)obj;
    if (!ObjectUtilities.equal(background, background)) {
      return false;
    }
    if (!ObjectUtilities.equal(cap, cap)) {
      return false;
    }
    if (!dialFrame.equals(dialFrame)) {
      return false;
    }
    if (viewX != viewX) {
      return false;
    }
    if (viewY != viewY) {
      return false;
    }
    if (viewW != viewW) {
      return false;
    }
    if (viewH != viewH) {
      return false;
    }
    if (!layers.equals(layers)) {
      return false;
    }
    if (!pointers.equals(pointers)) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = 193;
    result = 37 * result + ObjectUtilities.hashCode(background);
    result = 37 * result + ObjectUtilities.hashCode(cap);
    result = 37 * result + dialFrame.hashCode();
    long temp = Double.doubleToLongBits(viewX);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(viewY);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(viewW);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(viewH);
    result = 37 * result + (int)(temp ^ temp >>> 32);
    return result;
  }
  




  public String getPlotType()
  {
    return "DialPlot";
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
  }
}
