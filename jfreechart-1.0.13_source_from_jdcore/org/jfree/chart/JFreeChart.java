package org.jfree.chart;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.event.EventListenerList;
import org.jfree.chart.block.BlockParams;
import org.jfree.chart.block.EntityBlockResult;
import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.LineBorder;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.JFreeChartEntity;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.event.TitleChangeEvent;
import org.jfree.chart.event.TitleChangeListener;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.Range;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.Align;
import org.jfree.ui.Drawable;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.Size2D;
import org.jfree.ui.VerticalAlignment;
import org.jfree.ui.about.ProjectInfo;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;



























































































































































































public class JFreeChart
  implements Drawable, TitleChangeListener, PlotChangeListener, Serializable, Cloneable
{
  private static final long serialVersionUID = -3470703747817429120L;
  public static final ProjectInfo INFO = new JFreeChartInfo();
  

  public static final Font DEFAULT_TITLE_FONT = new Font("SansSerif", 1, 18);
  


  public static final Paint DEFAULT_BACKGROUND_PAINT = UIManager.getColor("Panel.background");
  


  public static final Image DEFAULT_BACKGROUND_IMAGE = null;
  


  public static final int DEFAULT_BACKGROUND_IMAGE_ALIGNMENT = 15;
  


  public static final float DEFAULT_BACKGROUND_IMAGE_ALPHA = 0.5F;
  


  private transient RenderingHints renderingHints;
  


  private boolean borderVisible;
  


  private transient Stroke borderStroke;
  


  private transient Paint borderPaint;
  

  private RectangleInsets padding;
  

  private TextTitle title;
  

  private List subtitles;
  

  private Plot plot;
  

  private transient Paint backgroundPaint;
  

  private transient Image backgroundImage;
  

  private int backgroundImageAlignment = 15;
  

  private float backgroundImageAlpha = 0.5F;
  




  private transient EventListenerList changeListeners;
  




  private transient EventListenerList progressListeners;
  




  private boolean notify;
  




  public JFreeChart(Plot plot)
  {
    this(null, null, plot, true);
  }
  











  public JFreeChart(String title, Plot plot)
  {
    this(title, DEFAULT_TITLE_FONT, plot, true);
  }
  


















  public JFreeChart(String title, Font titleFont, Plot plot, boolean createLegend)
  {
    if (plot == null) {
      throw new NullPointerException("Null 'plot' argument.");
    }
    

    progressListeners = new EventListenerList();
    changeListeners = new EventListenerList();
    notify = true;
    

    renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    


    borderVisible = false;
    borderStroke = new BasicStroke(1.0F);
    borderPaint = Color.black;
    
    padding = RectangleInsets.ZERO_INSETS;
    
    this.plot = plot;
    plot.addChangeListener(this);
    
    subtitles = new ArrayList();
    

    if (createLegend) {
      LegendTitle legend = new LegendTitle(this.plot);
      legend.setMargin(new RectangleInsets(1.0D, 1.0D, 1.0D, 1.0D));
      legend.setFrame(new LineBorder());
      legend.setBackgroundPaint(Color.white);
      legend.setPosition(RectangleEdge.BOTTOM);
      subtitles.add(legend);
      legend.addChangeListener(this);
    }
    

    if (title != null) {
      if (titleFont == null) {
        titleFont = DEFAULT_TITLE_FONT;
      }
      this.title = new TextTitle(title, titleFont);
      this.title.addChangeListener(this);
    }
    
    backgroundPaint = DEFAULT_BACKGROUND_PAINT;
    
    backgroundImage = DEFAULT_BACKGROUND_IMAGE;
    backgroundImageAlignment = 15;
    backgroundImageAlpha = 0.5F;
  }
  







  public RenderingHints getRenderingHints()
  {
    return renderingHints;
  }
  









  public void setRenderingHints(RenderingHints renderingHints)
  {
    if (renderingHints == null) {
      throw new NullPointerException("RenderingHints given are null");
    }
    this.renderingHints = renderingHints;
    fireChartChanged();
  }
  







  public boolean isBorderVisible()
  {
    return borderVisible;
  }
  







  public void setBorderVisible(boolean visible)
  {
    borderVisible = visible;
    fireChartChanged();
  }
  






  public Stroke getBorderStroke()
  {
    return borderStroke;
  }
  






  public void setBorderStroke(Stroke stroke)
  {
    borderStroke = stroke;
    fireChartChanged();
  }
  






  public Paint getBorderPaint()
  {
    return borderPaint;
  }
  






  public void setBorderPaint(Paint paint)
  {
    borderPaint = paint;
    fireChartChanged();
  }
  






  public RectangleInsets getPadding()
  {
    return padding;
  }
  







  public void setPadding(RectangleInsets padding)
  {
    if (padding == null) {
      throw new IllegalArgumentException("Null 'padding' argument.");
    }
    this.padding = padding;
    notifyListeners(new ChartChangeEvent(this));
  }
  









  public TextTitle getTitle()
  {
    return title;
  }
  









  public void setTitle(TextTitle title)
  {
    if (this.title != null) {
      this.title.removeChangeListener(this);
    }
    this.title = title;
    if (title != null) {
      title.addChangeListener(this);
    }
    fireChartChanged();
  }
  











  public void setTitle(String text)
  {
    if (text != null) {
      if (title == null) {
        setTitle(new TextTitle(text, DEFAULT_TITLE_FONT));
      }
      else {
        title.setText(text);
      }
    }
    else {
      setTitle((TextTitle)null);
    }
  }
  







  public void addLegend(LegendTitle legend)
  {
    addSubtitle(legend);
  }
  







  public LegendTitle getLegend()
  {
    return getLegend(0);
  }
  








  public LegendTitle getLegend(int index)
  {
    int seen = 0;
    Iterator iterator = subtitles.iterator();
    while (iterator.hasNext()) {
      Title subtitle = (Title)iterator.next();
      if ((subtitle instanceof LegendTitle)) {
        if (seen == index) {
          return (LegendTitle)subtitle;
        }
        
        seen++;
      }
    }
    
    return null;
  }
  





  public void removeLegend()
  {
    removeSubtitle(getLegend());
  }
  






  public List getSubtitles()
  {
    return new ArrayList(subtitles);
  }
  









  public void setSubtitles(List subtitles)
  {
    if (subtitles == null) {
      throw new NullPointerException("Null 'subtitles' argument.");
    }
    setNotify(false);
    clearSubtitles();
    Iterator iterator = subtitles.iterator();
    while (iterator.hasNext()) {
      Title t = (Title)iterator.next();
      if (t != null) {
        addSubtitle(t);
      }
    }
    setNotify(true);
  }
  






  public int getSubtitleCount()
  {
    return subtitles.size();
  }
  








  public Title getSubtitle(int index)
  {
    if ((index < 0) || (index >= getSubtitleCount())) {
      throw new IllegalArgumentException("Index out of range.");
    }
    return (Title)subtitles.get(index);
  }
  







  public void addSubtitle(Title subtitle)
  {
    if (subtitle == null) {
      throw new IllegalArgumentException("Null 'subtitle' argument.");
    }
    subtitles.add(subtitle);
    subtitle.addChangeListener(this);
    fireChartChanged();
  }
  








  public void addSubtitle(int index, Title subtitle)
  {
    if ((index < 0) || (index > getSubtitleCount())) {
      throw new IllegalArgumentException("The 'index' argument is out of range.");
    }
    
    if (subtitle == null) {
      throw new IllegalArgumentException("Null 'subtitle' argument.");
    }
    subtitles.add(index, subtitle);
    subtitle.addChangeListener(this);
    fireChartChanged();
  }
  





  public void clearSubtitles()
  {
    Iterator iterator = subtitles.iterator();
    while (iterator.hasNext()) {
      Title t = (Title)iterator.next();
      t.removeChangeListener(this);
    }
    subtitles.clear();
    fireChartChanged();
  }
  







  public void removeSubtitle(Title title)
  {
    subtitles.remove(title);
    fireChartChanged();
  }
  






  public Plot getPlot()
  {
    return plot;
  }
  









  public CategoryPlot getCategoryPlot()
  {
    return (CategoryPlot)plot;
  }
  









  public XYPlot getXYPlot()
  {
    return (XYPlot)plot;
  }
  







  public boolean getAntiAlias()
  {
    Object val = renderingHints.get(RenderingHints.KEY_ANTIALIASING);
    return RenderingHints.VALUE_ANTIALIAS_ON.equals(val);
  }
  










  public void setAntiAlias(boolean flag)
  {
    Object val = renderingHints.get(RenderingHints.KEY_ANTIALIASING);
    if (val == null) {
      val = RenderingHints.VALUE_ANTIALIAS_DEFAULT;
    }
    if (((!flag) && (RenderingHints.VALUE_ANTIALIAS_OFF.equals(val))) || ((flag) && (RenderingHints.VALUE_ANTIALIAS_ON.equals(val))))
    {

      return;
    }
    if (flag) {
      renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    else
    {
      renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    fireChartChanged();
  }
  










  public Object getTextAntiAlias()
  {
    return renderingHints.get(RenderingHints.KEY_TEXT_ANTIALIASING);
  }
  













  public void setTextAntiAlias(boolean flag)
  {
    if (flag) {
      setTextAntiAlias(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
    else {
      setTextAntiAlias(RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
    }
  }
  











  public void setTextAntiAlias(Object val)
  {
    renderingHints.put(RenderingHints.KEY_TEXT_ANTIALIASING, val);
    notifyListeners(new ChartChangeEvent(this));
  }
  






  public Paint getBackgroundPaint()
  {
    return backgroundPaint;
  }
  








  public void setBackgroundPaint(Paint paint)
  {
    if (backgroundPaint != null) {
      if (!backgroundPaint.equals(paint)) {
        backgroundPaint = paint;
        fireChartChanged();
      }
      
    }
    else if (paint != null) {
      backgroundPaint = paint;
      fireChartChanged();
    }
  }
  









  public Image getBackgroundImage()
  {
    return backgroundImage;
  }
  








  public void setBackgroundImage(Image image)
  {
    if (backgroundImage != null) {
      if (!backgroundImage.equals(image)) {
        backgroundImage = image;
        fireChartChanged();
      }
      
    }
    else if (image != null) {
      backgroundImage = image;
      fireChartChanged();
    }
  }
  










  public int getBackgroundImageAlignment()
  {
    return backgroundImageAlignment;
  }
  







  public void setBackgroundImageAlignment(int alignment)
  {
    if (backgroundImageAlignment != alignment) {
      backgroundImageAlignment = alignment;
      fireChartChanged();
    }
  }
  






  public float getBackgroundImageAlpha()
  {
    return backgroundImageAlpha;
  }
  








  public void setBackgroundImageAlpha(float alpha)
  {
    if (backgroundImageAlpha != alpha) {
      backgroundImageAlpha = alpha;
      fireChartChanged();
    }
  }
  








  public boolean isNotify()
  {
    return notify;
  }
  







  public void setNotify(boolean notify)
  {
    this.notify = notify;
    
    if (notify) {
      notifyListeners(new ChartChangeEvent(this));
    }
  }
  








  public void draw(Graphics2D g2, Rectangle2D area)
  {
    draw(g2, area, null, null);
  }
  







  public void draw(Graphics2D g2, Rectangle2D area, ChartRenderingInfo info)
  {
    draw(g2, area, null, info);
  }
  














  public void draw(Graphics2D g2, Rectangle2D chartArea, Point2D anchor, ChartRenderingInfo info)
  {
    notifyListeners(new ChartProgressEvent(this, this, 1, 0));
    

    EntityCollection entities = null;
    
    if (info != null) {
      info.clear();
      info.setChartArea(chartArea);
      entities = info.getEntityCollection();
    }
    if (entities != null) {
      entities.add(new JFreeChartEntity((Rectangle2D)chartArea.clone(), this));
    }
    


    Shape savedClip = g2.getClip();
    g2.clip(chartArea);
    
    g2.addRenderingHints(renderingHints);
    

    if (backgroundPaint != null) {
      g2.setPaint(backgroundPaint);
      g2.fill(chartArea);
    }
    
    if (backgroundImage != null) {
      Composite originalComposite = g2.getComposite();
      g2.setComposite(AlphaComposite.getInstance(3, backgroundImageAlpha));
      
      Rectangle2D dest = new Rectangle2D.Double(0.0D, 0.0D, backgroundImage.getWidth(null), backgroundImage.getHeight(null));
      

      Align.align(dest, chartArea, backgroundImageAlignment);
      g2.drawImage(backgroundImage, (int)dest.getX(), (int)dest.getY(), (int)dest.getWidth(), (int)dest.getHeight(), null);
      

      g2.setComposite(originalComposite);
    }
    
    if (isBorderVisible()) {
      Paint paint = getBorderPaint();
      Stroke stroke = getBorderStroke();
      if ((paint != null) && (stroke != null)) {
        Rectangle2D borderArea = new Rectangle2D.Double(chartArea.getX(), chartArea.getY(), chartArea.getWidth() - 1.0D, chartArea.getHeight() - 1.0D);
        


        g2.setPaint(paint);
        g2.setStroke(stroke);
        g2.draw(borderArea);
      }
    }
    

    Rectangle2D nonTitleArea = new Rectangle2D.Double();
    nonTitleArea.setRect(chartArea);
    padding.trim(nonTitleArea);
    
    if (title != null) {
      EntityCollection e = drawTitle(title, g2, nonTitleArea, entities != null);
      
      if (e != null) {
        entities.addAll(e);
      }
    }
    
    Iterator iterator = subtitles.iterator();
    while (iterator.hasNext()) {
      Title currentTitle = (Title)iterator.next();
      if (currentTitle.isVisible()) {
        EntityCollection e = drawTitle(currentTitle, g2, nonTitleArea, entities != null);
        
        if (e != null) {
          entities.addAll(e);
        }
      }
    }
    
    Rectangle2D plotArea = nonTitleArea;
    

    PlotRenderingInfo plotInfo = null;
    if (info != null) {
      plotInfo = info.getPlotInfo();
    }
    plot.draw(g2, plotArea, anchor, null, plotInfo);
    
    g2.setClip(savedClip);
    
    notifyListeners(new ChartProgressEvent(this, this, 2, 100));
  }
  












  private Rectangle2D createAlignedRectangle2D(Size2D dimensions, Rectangle2D frame, HorizontalAlignment hAlign, VerticalAlignment vAlign)
  {
    double x = NaN.0D;
    double y = NaN.0D;
    if (hAlign == HorizontalAlignment.LEFT) {
      x = frame.getX();
    }
    else if (hAlign == HorizontalAlignment.CENTER) {
      x = frame.getCenterX() - width / 2.0D;
    }
    else if (hAlign == HorizontalAlignment.RIGHT) {
      x = frame.getMaxX() - width;
    }
    if (vAlign == VerticalAlignment.TOP) {
      y = frame.getY();
    }
    else if (vAlign == VerticalAlignment.CENTER) {
      y = frame.getCenterY() - height / 2.0D;
    }
    else if (vAlign == VerticalAlignment.BOTTOM) {
      y = frame.getMaxY() - height;
    }
    
    return new Rectangle2D.Double(x, y, width, height);
  }
  
















  protected EntityCollection drawTitle(Title t, Graphics2D g2, Rectangle2D area, boolean entities)
  {
    if (t == null) {
      throw new IllegalArgumentException("Null 't' argument.");
    }
    if (area == null) {
      throw new IllegalArgumentException("Null 'area' argument.");
    }
    Rectangle2D titleArea = new Rectangle2D.Double();
    RectangleEdge position = t.getPosition();
    double ww = area.getWidth();
    if (ww <= 0.0D) {
      return null;
    }
    double hh = area.getHeight();
    if (hh <= 0.0D) {
      return null;
    }
    RectangleConstraint constraint = new RectangleConstraint(ww, new Range(0.0D, ww), LengthConstraintType.RANGE, hh, new Range(0.0D, hh), LengthConstraintType.RANGE);
    

    Object retValue = null;
    BlockParams p = new BlockParams();
    p.setGenerateEntities(entities);
    if (position == RectangleEdge.TOP) {
      Size2D size = t.arrange(g2, constraint);
      titleArea = createAlignedRectangle2D(size, area, t.getHorizontalAlignment(), VerticalAlignment.TOP);
      
      retValue = t.draw(g2, titleArea, p);
      area.setRect(area.getX(), Math.min(area.getY() + height, area.getMaxY()), area.getWidth(), Math.max(area.getHeight() - height, 0.0D));


    }
    else if (position == RectangleEdge.BOTTOM) {
      Size2D size = t.arrange(g2, constraint);
      titleArea = createAlignedRectangle2D(size, area, t.getHorizontalAlignment(), VerticalAlignment.BOTTOM);
      
      retValue = t.draw(g2, titleArea, p);
      area.setRect(area.getX(), area.getY(), area.getWidth(), area.getHeight() - height);

    }
    else if (position == RectangleEdge.RIGHT) {
      Size2D size = t.arrange(g2, constraint);
      titleArea = createAlignedRectangle2D(size, area, HorizontalAlignment.RIGHT, t.getVerticalAlignment());
      
      retValue = t.draw(g2, titleArea, p);
      area.setRect(area.getX(), area.getY(), area.getWidth() - width, area.getHeight());


    }
    else if (position == RectangleEdge.LEFT) {
      Size2D size = t.arrange(g2, constraint);
      titleArea = createAlignedRectangle2D(size, area, HorizontalAlignment.LEFT, t.getVerticalAlignment());
      
      retValue = t.draw(g2, titleArea, p);
      area.setRect(area.getX() + width, area.getY(), area.getWidth() - width, area.getHeight());
    }
    else
    {
      throw new RuntimeException("Unrecognised title position.");
    }
    EntityCollection result = null;
    if ((retValue instanceof EntityBlockResult)) {
      EntityBlockResult ebr = (EntityBlockResult)retValue;
      result = ebr.getEntityCollection();
    }
    return result;
  }
  







  public BufferedImage createBufferedImage(int width, int height)
  {
    return createBufferedImage(width, height, null);
  }
  










  public BufferedImage createBufferedImage(int width, int height, ChartRenderingInfo info)
  {
    return createBufferedImage(width, height, 2, info);
  }
  













  public BufferedImage createBufferedImage(int width, int height, int imageType, ChartRenderingInfo info)
  {
    BufferedImage image = new BufferedImage(width, height, imageType);
    Graphics2D g2 = image.createGraphics();
    draw(g2, new Rectangle2D.Double(0.0D, 0.0D, width, height), null, info);
    g2.dispose();
    return image;
  }
  


















  public BufferedImage createBufferedImage(int imageWidth, int imageHeight, double drawWidth, double drawHeight, ChartRenderingInfo info)
  {
    BufferedImage image = new BufferedImage(imageWidth, imageHeight, 2);
    
    Graphics2D g2 = image.createGraphics();
    double scaleX = imageWidth / drawWidth;
    double scaleY = imageHeight / drawHeight;
    AffineTransform st = AffineTransform.getScaleInstance(scaleX, scaleY);
    g2.transform(st);
    draw(g2, new Rectangle2D.Double(0.0D, 0.0D, drawWidth, drawHeight), null, info);
    
    g2.dispose();
    return image;
  }
  















  public void handleClick(int x, int y, ChartRenderingInfo info)
  {
    plot.handleClick(x, y, info.getPlotInfo());
  }
  







  public void addChangeListener(ChartChangeListener listener)
  {
    if (listener == null) {
      throw new IllegalArgumentException("Null 'listener' argument.");
    }
    changeListeners.add(ChartChangeListener.class, listener);
  }
  






  public void removeChangeListener(ChartChangeListener listener)
  {
    if (listener == null) {
      throw new IllegalArgumentException("Null 'listener' argument.");
    }
    changeListeners.remove(ChartChangeListener.class, listener);
  }
  




  public void fireChartChanged()
  {
    ChartChangeEvent event = new ChartChangeEvent(this);
    notifyListeners(event);
  }
  





  protected void notifyListeners(ChartChangeEvent event)
  {
    if (notify) {
      Object[] listeners = changeListeners.getListenerList();
      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == ChartChangeListener.class) {
          ((ChartChangeListener)listeners[(i + 1)]).chartChanged(event);
        }
      }
    }
  }
  








  public void addProgressListener(ChartProgressListener listener)
  {
    progressListeners.add(ChartProgressListener.class, listener);
  }
  






  public void removeProgressListener(ChartProgressListener listener)
  {
    progressListeners.remove(ChartProgressListener.class, listener);
  }
  






  protected void notifyListeners(ChartProgressEvent event)
  {
    Object[] listeners = progressListeners.getListenerList();
    for (int i = listeners.length - 2; i >= 0; i -= 2) {
      if (listeners[i] == ChartProgressListener.class) {
        ((ChartProgressListener)listeners[(i + 1)]).chartProgress(event);
      }
    }
  }
  






  public void titleChanged(TitleChangeEvent event)
  {
    event.setChart(this);
    notifyListeners(event);
  }
  





  public void plotChanged(PlotChangeEvent event)
  {
    event.setChart(this);
    notifyListeners(event);
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof JFreeChart)) {
      return false;
    }
    JFreeChart that = (JFreeChart)obj;
    if (!renderingHints.equals(renderingHints)) {
      return false;
    }
    if (borderVisible != borderVisible) {
      return false;
    }
    if (!ObjectUtilities.equal(borderStroke, borderStroke)) {
      return false;
    }
    if (!PaintUtilities.equal(borderPaint, borderPaint)) {
      return false;
    }
    if (!padding.equals(padding)) {
      return false;
    }
    if (!ObjectUtilities.equal(title, title)) {
      return false;
    }
    if (!ObjectUtilities.equal(subtitles, subtitles)) {
      return false;
    }
    if (!ObjectUtilities.equal(plot, plot)) {
      return false;
    }
    if (!PaintUtilities.equal(backgroundPaint, backgroundPaint))
    {

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
    if (notify != notify) {
      return false;
    }
    return true;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeStroke(borderStroke, stream);
    SerialUtilities.writePaint(borderPaint, stream);
    SerialUtilities.writePaint(backgroundPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    borderStroke = SerialUtilities.readStroke(stream);
    borderPaint = SerialUtilities.readPaint(stream);
    backgroundPaint = SerialUtilities.readPaint(stream);
    progressListeners = new EventListenerList();
    changeListeners = new EventListenerList();
    renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    



    if (title != null) {
      title.addChangeListener(this);
    }
    
    for (int i = 0; i < getSubtitleCount(); i++) {
      getSubtitle(i).addChangeListener(this);
    }
    plot.addChangeListener(this);
  }
  




  public static void main(String[] args)
  {
    System.out.println(INFO.toString());
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    JFreeChart chart = (JFreeChart)super.clone();
    
    renderingHints = ((RenderingHints)renderingHints.clone());
    



    if (title != null) {
      title = ((TextTitle)title.clone());
      title.addChangeListener(chart);
    }
    
    subtitles = new ArrayList();
    for (int i = 0; i < getSubtitleCount(); i++) {
      Title subtitle = (Title)getSubtitle(i).clone();
      subtitles.add(subtitle);
      subtitle.addChangeListener(chart);
    }
    
    if (plot != null) {
      plot = ((Plot)plot.clone());
      plot.addChangeListener(chart);
    }
    
    progressListeners = new EventListenerList();
    changeListeners = new EventListenerList();
    return chart;
  }
}
