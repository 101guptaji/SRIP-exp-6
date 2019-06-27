package org.jfree.chart;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.EventListenerList;
import org.jfree.chart.editor.ChartEditor;
import org.jfree.chart.editor.ChartEditorManager;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.event.OverlayChangeEvent;
import org.jfree.chart.event.OverlayChangeListener;
import org.jfree.chart.panel.Overlay;
import org.jfree.chart.plot.Pannable;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.Zoomable;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.ExtensionFileFilter;














































































































































































































































































public class ChartPanel
  extends JPanel
  implements ChartChangeListener, ChartProgressListener, ActionListener, MouseListener, MouseMotionListener, OverlayChangeListener, Printable, Serializable
{
  private static final long serialVersionUID = 6046366297214274674L;
  public static final boolean DEFAULT_BUFFER_USED = true;
  public static final int DEFAULT_WIDTH = 680;
  public static final int DEFAULT_HEIGHT = 420;
  public static final int DEFAULT_MINIMUM_DRAW_WIDTH = 300;
  public static final int DEFAULT_MINIMUM_DRAW_HEIGHT = 200;
  public static final int DEFAULT_MAXIMUM_DRAW_WIDTH = 1024;
  public static final int DEFAULT_MAXIMUM_DRAW_HEIGHT = 768;
  public static final int DEFAULT_ZOOM_TRIGGER_DISTANCE = 10;
  public static final String PROPERTIES_COMMAND = "PROPERTIES";
  public static final String COPY_COMMAND = "COPY";
  public static final String SAVE_COMMAND = "SAVE";
  public static final String PRINT_COMMAND = "PRINT";
  public static final String ZOOM_IN_BOTH_COMMAND = "ZOOM_IN_BOTH";
  public static final String ZOOM_IN_DOMAIN_COMMAND = "ZOOM_IN_DOMAIN";
  public static final String ZOOM_IN_RANGE_COMMAND = "ZOOM_IN_RANGE";
  public static final String ZOOM_OUT_BOTH_COMMAND = "ZOOM_OUT_BOTH";
  public static final String ZOOM_OUT_DOMAIN_COMMAND = "ZOOM_DOMAIN_BOTH";
  public static final String ZOOM_OUT_RANGE_COMMAND = "ZOOM_RANGE_BOTH";
  public static final String ZOOM_RESET_BOTH_COMMAND = "ZOOM_RESET_BOTH";
  public static final String ZOOM_RESET_DOMAIN_COMMAND = "ZOOM_RESET_DOMAIN";
  public static final String ZOOM_RESET_RANGE_COMMAND = "ZOOM_RESET_RANGE";
  private JFreeChart chart;
  private transient EventListenerList chartMouseListeners;
  private boolean useBuffer;
  private boolean refreshBuffer;
  private transient Image chartBuffer;
  private int chartBufferHeight;
  private int chartBufferWidth;
  private int minimumDrawWidth;
  private int minimumDrawHeight;
  private int maximumDrawWidth;
  private int maximumDrawHeight;
  private JPopupMenu popup;
  private ChartRenderingInfo info;
  private Point2D anchor;
  private double scaleX;
  private double scaleY;
  private PlotOrientation orientation = PlotOrientation.VERTICAL;
  

  private boolean domainZoomable = false;
  

  private boolean rangeZoomable = false;
  





  private Point2D zoomPoint = null;
  

  private transient Rectangle2D zoomRectangle = null;
  

  private boolean fillZoomRectangle = true;
  

  private int zoomTriggerDistance;
  

  private boolean horizontalAxisTrace = false;
  

  private boolean verticalAxisTrace = false;
  


  private transient Line2D verticalTraceLine;
  


  private transient Line2D horizontalTraceLine;
  


  private JMenuItem zoomInBothMenuItem;
  


  private JMenuItem zoomInDomainMenuItem;
  

  private JMenuItem zoomInRangeMenuItem;
  

  private JMenuItem zoomOutBothMenuItem;
  

  private JMenuItem zoomOutDomainMenuItem;
  

  private JMenuItem zoomOutRangeMenuItem;
  

  private JMenuItem zoomResetBothMenuItem;
  

  private JMenuItem zoomResetDomainMenuItem;
  

  private JMenuItem zoomResetRangeMenuItem;
  

  private File defaultDirectoryForSaveAs;
  

  private boolean enforceFileExtensions;
  

  private boolean ownToolTipDelaysActive;
  

  private int originalToolTipInitialDelay;
  

  private int originalToolTipReshowDelay;
  

  private int originalToolTipDismissDelay;
  

  private int ownToolTipInitialDelay;
  

  private int ownToolTipReshowDelay;
  

  private int ownToolTipDismissDelay;
  

  private double zoomInFactor = 0.5D;
  

  private double zoomOutFactor = 2.0D;
  





  private boolean zoomAroundAnchor;
  




  private transient Paint zoomOutlinePaint;
  




  private transient Paint zoomFillPaint;
  




  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.LocalizationBundle");
  



  private double panW;
  



  private double panH;
  


  private Point panLast;
  


  private int panMask = 2;
  



  private List overlays;
  


  private Object mouseWheelHandler;
  



  public ChartPanel(JFreeChart chart)
  {
    this(chart, 680, 420, 300, 200, 1024, 768, true, true, true, true, true, true);
  }
  































  public ChartPanel(JFreeChart chart, boolean useBuffer)
  {
    this(chart, 680, 420, 300, 200, 1024, 768, useBuffer, true, true, true, true, true);
  }
  





























  public ChartPanel(JFreeChart chart, boolean properties, boolean save, boolean print, boolean zoom, boolean tooltips)
  {
    this(chart, 680, 420, 300, 200, 1024, 768, true, properties, save, print, zoom, tooltips);
  }
  









































  public ChartPanel(JFreeChart chart, int width, int height, int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth, int maximumDrawHeight, boolean useBuffer, boolean properties, boolean save, boolean print, boolean zoom, boolean tooltips)
  {
    this(chart, width, height, minimumDrawWidth, minimumDrawHeight, maximumDrawWidth, maximumDrawHeight, useBuffer, properties, true, save, print, zoom, tooltips);
  }
  


































  public ChartPanel(JFreeChart chart, int width, int height, int minimumDrawWidth, int minimumDrawHeight, int maximumDrawWidth, int maximumDrawHeight, boolean useBuffer, boolean properties, boolean copy, boolean save, boolean print, boolean zoom, boolean tooltips)
  {
    setChart(chart);
    chartMouseListeners = new EventListenerList();
    info = new ChartRenderingInfo();
    setPreferredSize(new Dimension(width, height));
    this.useBuffer = useBuffer;
    refreshBuffer = false;
    this.minimumDrawWidth = minimumDrawWidth;
    this.minimumDrawHeight = minimumDrawHeight;
    this.maximumDrawWidth = maximumDrawWidth;
    this.maximumDrawHeight = maximumDrawHeight;
    zoomTriggerDistance = 10;
    

    popup = null;
    if ((properties) || (copy) || (save) || (print) || (zoom)) {
      popup = createPopupMenu(properties, copy, save, print, zoom);
    }
    
    enableEvents(16L);
    enableEvents(32L);
    setDisplayToolTips(tooltips);
    addMouseListener(this);
    addMouseMotionListener(this);
    
    defaultDirectoryForSaveAs = null;
    enforceFileExtensions = true;
    


    ToolTipManager ttm = ToolTipManager.sharedInstance();
    ownToolTipInitialDelay = ttm.getInitialDelay();
    ownToolTipDismissDelay = ttm.getDismissDelay();
    ownToolTipReshowDelay = ttm.getReshowDelay();
    
    zoomAroundAnchor = false;
    zoomOutlinePaint = Color.blue;
    zoomFillPaint = new Color(0, 0, 255, 63);
    
    panMask = 2;
    

    String osName = System.getProperty("os.name").toLowerCase();
    if (osName.startsWith("mac os x")) {
      panMask = 8;
    }
    
    overlays = new ArrayList();
  }
  




  public JFreeChart getChart()
  {
    return chart;
  }
  






  public void setChart(JFreeChart chart)
  {
    if (this.chart != null) {
      this.chart.removeChangeListener(this);
      this.chart.removeProgressListener(this);
    }
    

    this.chart = chart;
    if (chart != null) {
      this.chart.addChangeListener(this);
      this.chart.addProgressListener(this);
      Plot plot = chart.getPlot();
      domainZoomable = false;
      rangeZoomable = false;
      if ((plot instanceof Zoomable)) {
        Zoomable z = (Zoomable)plot;
        domainZoomable = z.isDomainZoomable();
        rangeZoomable = z.isRangeZoomable();
        orientation = z.getOrientation();
      }
    }
    else {
      domainZoomable = false;
      rangeZoomable = false;
    }
    if (useBuffer) {
      refreshBuffer = true;
    }
    repaint();
  }
  








  public int getMinimumDrawWidth()
  {
    return minimumDrawWidth;
  }
  








  public void setMinimumDrawWidth(int width)
  {
    minimumDrawWidth = width;
  }
  







  public int getMaximumDrawWidth()
  {
    return maximumDrawWidth;
  }
  








  public void setMaximumDrawWidth(int width)
  {
    maximumDrawWidth = width;
  }
  







  public int getMinimumDrawHeight()
  {
    return minimumDrawHeight;
  }
  








  public void setMinimumDrawHeight(int height)
  {
    minimumDrawHeight = height;
  }
  







  public int getMaximumDrawHeight()
  {
    return maximumDrawHeight;
  }
  








  public void setMaximumDrawHeight(int height)
  {
    maximumDrawHeight = height;
  }
  





  public double getScaleX()
  {
    return scaleX;
  }
  





  public double getScaleY()
  {
    return scaleY;
  }
  




  public Point2D getAnchor()
  {
    return anchor;
  }
  





  protected void setAnchor(Point2D anchor)
  {
    this.anchor = anchor;
  }
  




  public JPopupMenu getPopupMenu()
  {
    return popup;
  }
  




  public void setPopupMenu(JPopupMenu popup)
  {
    this.popup = popup;
  }
  




  public ChartRenderingInfo getChartRenderingInfo()
  {
    return info;
  }
  





  public void setMouseZoomable(boolean flag)
  {
    setMouseZoomable(flag, true);
  }
  






  public void setMouseZoomable(boolean flag, boolean fillRectangle)
  {
    setDomainZoomable(flag);
    setRangeZoomable(flag);
    setFillZoomRectangle(fillRectangle);
  }
  





  public boolean isDomainZoomable()
  {
    return domainZoomable;
  }
  






  public void setDomainZoomable(boolean flag)
  {
    if (flag) {
      Plot plot = chart.getPlot();
      if ((plot instanceof Zoomable)) {
        Zoomable z = (Zoomable)plot;
        domainZoomable = ((flag) && (z.isDomainZoomable()));
      }
    }
    else {
      domainZoomable = false;
    }
  }
  





  public boolean isRangeZoomable()
  {
    return rangeZoomable;
  }
  




  public void setRangeZoomable(boolean flag)
  {
    if (flag) {
      Plot plot = chart.getPlot();
      if ((plot instanceof Zoomable)) {
        Zoomable z = (Zoomable)plot;
        rangeZoomable = ((flag) && (z.isRangeZoomable()));
      }
    }
    else {
      rangeZoomable = false;
    }
  }
  





  public boolean getFillZoomRectangle()
  {
    return fillZoomRectangle;
  }
  





  public void setFillZoomRectangle(boolean flag)
  {
    fillZoomRectangle = flag;
  }
  





  public int getZoomTriggerDistance()
  {
    return zoomTriggerDistance;
  }
  





  public void setZoomTriggerDistance(int distance)
  {
    zoomTriggerDistance = distance;
  }
  





  public boolean getHorizontalAxisTrace()
  {
    return horizontalAxisTrace;
  }
  





  public void setHorizontalAxisTrace(boolean flag)
  {
    horizontalAxisTrace = flag;
  }
  




  protected Line2D getHorizontalTraceLine()
  {
    return horizontalTraceLine;
  }
  




  protected void setHorizontalTraceLine(Line2D line)
  {
    horizontalTraceLine = line;
  }
  





  public boolean getVerticalAxisTrace()
  {
    return verticalAxisTrace;
  }
  





  public void setVerticalAxisTrace(boolean flag)
  {
    verticalAxisTrace = flag;
  }
  




  protected Line2D getVerticalTraceLine()
  {
    return verticalTraceLine;
  }
  




  protected void setVerticalTraceLine(Line2D line)
  {
    verticalTraceLine = line;
  }
  






  public File getDefaultDirectoryForSaveAs()
  {
    return defaultDirectoryForSaveAs;
  }
  







  public void setDefaultDirectoryForSaveAs(File directory)
  {
    if ((directory != null) && 
      (!directory.isDirectory())) {
      throw new IllegalArgumentException("The 'directory' argument is not a directory.");
    }
    

    defaultDirectoryForSaveAs = directory;
  }
  







  public boolean isEnforceFileExtensions()
  {
    return enforceFileExtensions;
  }
  






  public void setEnforceFileExtensions(boolean enforce)
  {
    enforceFileExtensions = enforce;
  }
  









  public boolean getZoomAroundAnchor()
  {
    return zoomAroundAnchor;
  }
  









  public void setZoomAroundAnchor(boolean zoomAroundAnchor)
  {
    this.zoomAroundAnchor = zoomAroundAnchor;
  }
  









  public Paint getZoomFillPaint()
  {
    return zoomFillPaint;
  }
  









  public void setZoomFillPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    zoomFillPaint = paint;
  }
  









  public Paint getZoomOutlinePaint()
  {
    return zoomOutlinePaint;
  }
  









  public void setZoomOutlinePaint(Paint paint)
  {
    zoomOutlinePaint = paint;
  }
  














  public boolean isMouseWheelEnabled()
  {
    return mouseWheelHandler != null;
  }
  









  public void setMouseWheelEnabled(boolean flag)
  {
    if ((flag) && (mouseWheelHandler == null))
    {
      try
      {

        Class c = Class.forName("org.jfree.chart.MouseWheelHandler");
        Constructor cc = c.getConstructor(new Class[] { ChartPanel.class });
        
        Object mwh = cc.newInstance(new Object[] { this });
        mouseWheelHandler = mwh;


      }
      catch (ClassNotFoundException e) {}catch (SecurityException e)
      {

        e.printStackTrace();
      }
      catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      catch (IllegalArgumentException e) {
        e.printStackTrace();
      }
      catch (InstantiationException e) {
        e.printStackTrace();
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      catch (InvocationTargetException e) {
        e.printStackTrace();

      }
      
    }
    else if (mouseWheelHandler != null) {
      try
      {
        Class mwl = Class.forName("java.awt.event.MouseWheelListener");
        
        Class c2 = ChartPanel.class;
        Method m = c2.getMethod("removeMouseWheelListener", new Class[] { mwl });
        
        m.invoke(this, new Object[] { mouseWheelHandler });

      }
      catch (ClassNotFoundException e) {}catch (SecurityException e)
      {

        e.printStackTrace();
      }
      catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      catch (IllegalArgumentException e) {
        e.printStackTrace();
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
  







  public void addOverlay(Overlay overlay)
  {
    if (overlay == null) {
      throw new IllegalArgumentException("Null 'overlay' argument.");
    }
    overlays.add(overlay);
    overlay.addChangeListener(this);
    repaint();
  }
  






  public void removeOverlay(Overlay overlay)
  {
    if (overlay == null) {
      throw new IllegalArgumentException("Null 'overlay' argument.");
    }
    boolean removed = overlays.remove(overlay);
    if (removed) {
      overlay.removeChangeListener(this);
      repaint();
    }
  }
  






  public void overlayChanged(OverlayChangeEvent event)
  {
    repaint();
  }
  







  public void setDisplayToolTips(boolean flag)
  {
    if (flag) {
      ToolTipManager.sharedInstance().registerComponent(this);
    }
    else {
      ToolTipManager.sharedInstance().unregisterComponent(this);
    }
  }
  







  public String getToolTipText(MouseEvent e)
  {
    String result = null;
    if (info != null) {
      EntityCollection entities = info.getEntityCollection();
      if (entities != null) {
        Insets insets = getInsets();
        ChartEntity entity = entities.getEntity((int)((e.getX() - left) / scaleX), (int)((e.getY() - top) / scaleY));
        

        if (entity != null) {
          result = entity.getToolTipText();
        }
      }
    }
    return result;
  }
  







  public Point translateJava2DToScreen(Point2D java2DPoint)
  {
    Insets insets = getInsets();
    int x = (int)(java2DPoint.getX() * scaleX + left);
    int y = (int)(java2DPoint.getY() * scaleY + top);
    return new Point(x, y);
  }
  







  public Point2D translateScreenToJava2D(Point screenPoint)
  {
    Insets insets = getInsets();
    double x = (screenPoint.getX() - left) / scaleX;
    double y = (screenPoint.getY() - top) / scaleY;
    return new Point2D.Double(x, y);
  }
  







  public Rectangle2D scale(Rectangle2D rect)
  {
    Insets insets = getInsets();
    double x = rect.getX() * getScaleX() + left;
    double y = rect.getY() * getScaleY() + top;
    double w = rect.getWidth() * getScaleX();
    double h = rect.getHeight() * getScaleY();
    return new Rectangle2D.Double(x, y, w, h);
  }
  











  public ChartEntity getEntityForPoint(int viewX, int viewY)
  {
    ChartEntity result = null;
    if (info != null) {
      Insets insets = getInsets();
      double x = (viewX - left) / scaleX;
      double y = (viewY - top) / scaleY;
      EntityCollection entities = info.getEntityCollection();
      result = entities != null ? entities.getEntity(x, y) : null;
    }
    return result;
  }
  






  public boolean getRefreshBuffer()
  {
    return refreshBuffer;
  }
  






  public void setRefreshBuffer(boolean flag)
  {
    refreshBuffer = flag;
  }
  







  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if (chart == null) {
      return;
    }
    Graphics2D g2 = (Graphics2D)g.create();
    

    Dimension size = getSize();
    Insets insets = getInsets();
    Rectangle2D available = new Rectangle2D.Double(left, top, size.getWidth() - left - right, size.getHeight() - top - bottom);
    



    boolean scale = false;
    double drawWidth = available.getWidth();
    double drawHeight = available.getHeight();
    scaleX = 1.0D;
    scaleY = 1.0D;
    
    if (drawWidth < minimumDrawWidth) {
      scaleX = (drawWidth / minimumDrawWidth);
      drawWidth = minimumDrawWidth;
      scale = true;
    }
    else if (drawWidth > maximumDrawWidth) {
      scaleX = (drawWidth / maximumDrawWidth);
      drawWidth = maximumDrawWidth;
      scale = true;
    }
    
    if (drawHeight < minimumDrawHeight) {
      scaleY = (drawHeight / minimumDrawHeight);
      drawHeight = minimumDrawHeight;
      scale = true;
    }
    else if (drawHeight > maximumDrawHeight) {
      scaleY = (drawHeight / maximumDrawHeight);
      drawHeight = maximumDrawHeight;
      scale = true;
    }
    
    Rectangle2D chartArea = new Rectangle2D.Double(0.0D, 0.0D, drawWidth, drawHeight);
    


    if (useBuffer)
    {

      if ((chartBuffer == null) || (chartBufferWidth != available.getWidth()) || (chartBufferHeight != available.getHeight()))
      {

        chartBufferWidth = ((int)available.getWidth());
        chartBufferHeight = ((int)available.getHeight());
        GraphicsConfiguration gc = g2.getDeviceConfiguration();
        chartBuffer = gc.createCompatibleImage(chartBufferWidth, chartBufferHeight, 3);
        

        refreshBuffer = true;
      }
      

      if (refreshBuffer)
      {
        refreshBuffer = false;
        
        Rectangle2D bufferArea = new Rectangle2D.Double(0.0D, 0.0D, chartBufferWidth, chartBufferHeight);
        

        Graphics2D bufferG2 = (Graphics2D)chartBuffer.getGraphics();
        
        Rectangle r = new Rectangle(0, 0, chartBufferWidth, chartBufferHeight);
        
        bufferG2.setPaint(getBackground());
        bufferG2.fill(r);
        if (scale) {
          AffineTransform saved = bufferG2.getTransform();
          AffineTransform st = AffineTransform.getScaleInstance(scaleX, scaleY);
          
          bufferG2.transform(st);
          chart.draw(bufferG2, chartArea, anchor, info);
          
          bufferG2.setTransform(saved);
        }
        else {
          chart.draw(bufferG2, bufferArea, anchor, info);
        }
      }
      



      g2.drawImage(chartBuffer, left, top, this);


    }
    else
    {

      AffineTransform saved = g2.getTransform();
      g2.translate(left, top);
      if (scale) {
        AffineTransform st = AffineTransform.getScaleInstance(scaleX, scaleY);
        
        g2.transform(st);
      }
      chart.draw(g2, chartArea, anchor, info);
      g2.setTransform(saved);
    }
    

    Iterator iterator = overlays.iterator();
    while (iterator.hasNext()) {
      Overlay overlay = (Overlay)iterator.next();
      overlay.paintOverlay(g2, this);
    }
    



    drawZoomRectangle(g2, !useBuffer);
    
    g2.dispose();
    
    anchor = null;
    verticalTraceLine = null;
    horizontalTraceLine = null;
  }
  





  public void chartChanged(ChartChangeEvent event)
  {
    refreshBuffer = true;
    Plot plot = chart.getPlot();
    if ((plot instanceof Zoomable)) {
      Zoomable z = (Zoomable)plot;
      orientation = z.getOrientation();
    }
    repaint();
  }
  






  public void chartProgress(ChartProgressEvent event) {}
  






  public void actionPerformed(ActionEvent event)
  {
    String command = event.getActionCommand();
    



    double screenX = -1.0D;
    double screenY = -1.0D;
    if (zoomPoint != null) {
      screenX = zoomPoint.getX();
      screenY = zoomPoint.getY();
    }
    
    if (command.equals("PROPERTIES")) {
      doEditChartProperties();
    }
    else if (command.equals("COPY")) {
      doCopy();
    }
    else if (command.equals("SAVE")) {
      try {
        doSaveAs();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      
    } else if (command.equals("PRINT")) {
      createChartPrintJob();
    }
    else if (command.equals("ZOOM_IN_BOTH")) {
      zoomInBoth(screenX, screenY);
    }
    else if (command.equals("ZOOM_IN_DOMAIN")) {
      zoomInDomain(screenX, screenY);
    }
    else if (command.equals("ZOOM_IN_RANGE")) {
      zoomInRange(screenX, screenY);
    }
    else if (command.equals("ZOOM_OUT_BOTH")) {
      zoomOutBoth(screenX, screenY);
    }
    else if (command.equals("ZOOM_DOMAIN_BOTH")) {
      zoomOutDomain(screenX, screenY);
    }
    else if (command.equals("ZOOM_RANGE_BOTH")) {
      zoomOutRange(screenX, screenY);
    }
    else if (command.equals("ZOOM_RESET_BOTH")) {
      restoreAutoBounds();
    }
    else if (command.equals("ZOOM_RESET_DOMAIN")) {
      restoreAutoDomainBounds();
    }
    else if (command.equals("ZOOM_RESET_RANGE")) {
      restoreAutoRangeBounds();
    }
  }
  







  public void mouseEntered(MouseEvent e)
  {
    if (!ownToolTipDelaysActive) {
      ToolTipManager ttm = ToolTipManager.sharedInstance();
      
      originalToolTipInitialDelay = ttm.getInitialDelay();
      ttm.setInitialDelay(ownToolTipInitialDelay);
      
      originalToolTipReshowDelay = ttm.getReshowDelay();
      ttm.setReshowDelay(ownToolTipReshowDelay);
      
      originalToolTipDismissDelay = ttm.getDismissDelay();
      ttm.setDismissDelay(ownToolTipDismissDelay);
      
      ownToolTipDelaysActive = true;
    }
  }
  






  public void mouseExited(MouseEvent e)
  {
    if (ownToolTipDelaysActive)
    {
      ToolTipManager ttm = ToolTipManager.sharedInstance();
      ttm.setInitialDelay(originalToolTipInitialDelay);
      ttm.setReshowDelay(originalToolTipReshowDelay);
      ttm.setDismissDelay(originalToolTipDismissDelay);
      ownToolTipDelaysActive = false;
    }
  }
  







  public void mousePressed(MouseEvent e)
  {
    Plot plot = chart.getPlot();
    int mods = e.getModifiers();
    if ((mods & panMask) == panMask)
    {
      if ((plot instanceof Pannable)) {
        Pannable pannable = (Pannable)plot;
        if ((pannable.isDomainPannable()) || (pannable.isRangePannable())) {
          Rectangle2D screenDataArea = getScreenDataArea(e.getX(), e.getY());
          
          if ((screenDataArea != null) && (screenDataArea.contains(e.getPoint())))
          {
            panW = screenDataArea.getWidth();
            panH = screenDataArea.getHeight();
            panLast = e.getPoint();
            setCursor(Cursor.getPredefinedCursor(13));
          }
          
        }
        
      }
    }
    else if (zoomRectangle == null) {
      Rectangle2D screenDataArea = getScreenDataArea(e.getX(), e.getY());
      if (screenDataArea != null) {
        zoomPoint = getPointInRectangle(e.getX(), e.getY(), screenDataArea);
      }
      else
      {
        zoomPoint = null;
      }
      if ((e.isPopupTrigger()) && 
        (popup != null)) {
        displayPopupMenu(e.getX(), e.getY());
      }
    }
  }
  










  private Point2D getPointInRectangle(int x, int y, Rectangle2D area)
  {
    double xx = Math.max(area.getMinX(), Math.min(x, area.getMaxX()));
    double yy = Math.max(area.getMinY(), Math.min(y, area.getMaxY()));
    return new Point2D.Double(xx, yy);
  }
  






  public void mouseDragged(MouseEvent e)
  {
    if ((popup != null) && (popup.isShowing())) {
      return;
    }
    

    if (panLast != null) {
      double dx = e.getX() - panLast.getX();
      double dy = e.getY() - panLast.getY();
      if ((dx == 0.0D) && (dy == 0.0D)) {
        return;
      }
      double wPercent = -dx / panW;
      double hPercent = dy / panH;
      boolean old = chart.getPlot().isNotify();
      chart.getPlot().setNotify(false);
      Pannable p = (Pannable)chart.getPlot();
      if (p.getOrientation() == PlotOrientation.VERTICAL) {
        p.panDomainAxes(wPercent, info.getPlotInfo(), panLast);
        
        p.panRangeAxes(hPercent, info.getPlotInfo(), panLast);
      }
      else
      {
        p.panDomainAxes(hPercent, info.getPlotInfo(), panLast);
        
        p.panRangeAxes(wPercent, info.getPlotInfo(), panLast);
      }
      
      panLast = e.getPoint();
      chart.getPlot().setNotify(old);
      return;
    }
    

    if (zoomPoint == null) {
      return;
    }
    Graphics2D g2 = (Graphics2D)getGraphics();
    




    if (!useBuffer) {
      drawZoomRectangle(g2, true);
    }
    
    boolean hZoom = false;
    boolean vZoom = false;
    if (orientation == PlotOrientation.HORIZONTAL) {
      hZoom = rangeZoomable;
      vZoom = domainZoomable;
    }
    else {
      hZoom = domainZoomable;
      vZoom = rangeZoomable;
    }
    Rectangle2D scaledDataArea = getScreenDataArea((int)zoomPoint.getX(), (int)zoomPoint.getY());
    
    if ((hZoom) && (vZoom))
    {
      double xmax = Math.min(e.getX(), scaledDataArea.getMaxX());
      double ymax = Math.min(e.getY(), scaledDataArea.getMaxY());
      zoomRectangle = new Rectangle2D.Double(zoomPoint.getX(), zoomPoint.getY(), xmax - zoomPoint.getX(), ymax - zoomPoint.getY());


    }
    else if (hZoom) {
      double xmax = Math.min(e.getX(), scaledDataArea.getMaxX());
      zoomRectangle = new Rectangle2D.Double(zoomPoint.getX(), scaledDataArea.getMinY(), xmax - zoomPoint.getX(), scaledDataArea.getHeight());


    }
    else if (vZoom) {
      double ymax = Math.min(e.getY(), scaledDataArea.getMaxY());
      zoomRectangle = new Rectangle2D.Double(scaledDataArea.getMinX(), zoomPoint.getY(), scaledDataArea.getWidth(), ymax - zoomPoint.getY());
    }
    



    if (useBuffer) {
      repaint();

    }
    else
    {
      drawZoomRectangle(g2, true);
    }
    g2.dispose();
  }
  










  public void mouseReleased(MouseEvent e)
  {
    if (panLast != null) {
      panLast = null;
      setCursor(Cursor.getDefaultCursor());

    }
    else if (zoomRectangle != null) {
      boolean hZoom = false;
      boolean vZoom = false;
      if (orientation == PlotOrientation.HORIZONTAL) {
        hZoom = rangeZoomable;
        vZoom = domainZoomable;
      }
      else {
        hZoom = domainZoomable;
        vZoom = rangeZoomable;
      }
      
      boolean zoomTrigger1 = (hZoom) && (Math.abs(e.getX() - zoomPoint.getX()) >= zoomTriggerDistance);
      
      boolean zoomTrigger2 = (vZoom) && (Math.abs(e.getY() - zoomPoint.getY()) >= zoomTriggerDistance);
      
      if ((zoomTrigger1) || (zoomTrigger2)) {
        if (((hZoom) && (e.getX() < zoomPoint.getX())) || ((vZoom) && (e.getY() < zoomPoint.getY())))
        {
          restoreAutoBounds();
        }
        else
        {
          Rectangle2D screenDataArea = getScreenDataArea((int)zoomPoint.getX(), (int)zoomPoint.getY());
          

          double maxX = screenDataArea.getMaxX();
          double maxY = screenDataArea.getMaxY();
          double h;
          double x;
          double y;
          double w; double h; if (!vZoom) {
            double x = zoomPoint.getX();
            double y = screenDataArea.getMinY();
            double w = Math.min(zoomRectangle.getWidth(), maxX - zoomPoint.getX());
            
            h = screenDataArea.getHeight();
          } else { double h;
            if (!hZoom) {
              double x = screenDataArea.getMinX();
              double y = zoomPoint.getY();
              double w = screenDataArea.getWidth();
              h = Math.min(zoomRectangle.getHeight(), maxY - zoomPoint.getY());
            }
            else
            {
              x = zoomPoint.getX();
              y = zoomPoint.getY();
              w = Math.min(zoomRectangle.getWidth(), maxX - zoomPoint.getX());
              
              h = Math.min(zoomRectangle.getHeight(), maxY - zoomPoint.getY());
            }
          }
          Rectangle2D zoomArea = new Rectangle2D.Double(x, y, w, h);
          zoom(zoomArea);
        }
        zoomPoint = null;
        zoomRectangle = null;
      }
      else
      {
        Graphics2D g2 = (Graphics2D)getGraphics();
        if (useBuffer) {
          repaint();
        }
        else {
          drawZoomRectangle(g2, true);
        }
        g2.dispose();
        zoomPoint = null;
        zoomRectangle = null;
      }
      

    }
    else if ((e.isPopupTrigger()) && 
      (popup != null)) {
      displayPopupMenu(e.getX(), e.getY());
    }
  }
  








  public void mouseClicked(MouseEvent event)
  {
    Insets insets = getInsets();
    int x = (int)((event.getX() - left) / scaleX);
    int y = (int)((event.getY() - top) / scaleY);
    
    anchor = new Point2D.Double(x, y);
    if (chart == null) {
      return;
    }
    chart.setNotify(true);
    
    Object[] listeners = chartMouseListeners.getListeners(ChartMouseListener.class);
    
    if (listeners.length == 0) {
      return;
    }
    
    ChartEntity entity = null;
    if (info != null) {
      EntityCollection entities = info.getEntityCollection();
      if (entities != null) {
        entity = entities.getEntity(x, y);
      }
    }
    ChartMouseEvent chartEvent = new ChartMouseEvent(getChart(), event, entity);
    
    for (int i = listeners.length - 1; i >= 0; i--) {
      ((ChartMouseListener)listeners[i]).chartMouseClicked(chartEvent);
    }
  }
  





  public void mouseMoved(MouseEvent e)
  {
    Graphics2D g2 = (Graphics2D)getGraphics();
    if (horizontalAxisTrace) {
      drawHorizontalAxisTrace(g2, e.getX());
    }
    if (verticalAxisTrace) {
      drawVerticalAxisTrace(g2, e.getY());
    }
    g2.dispose();
    
    Object[] listeners = chartMouseListeners.getListeners(ChartMouseListener.class);
    
    if (listeners.length == 0) {
      return;
    }
    Insets insets = getInsets();
    int x = (int)((e.getX() - left) / scaleX);
    int y = (int)((e.getY() - top) / scaleY);
    
    ChartEntity entity = null;
    if (info != null) {
      EntityCollection entities = info.getEntityCollection();
      if (entities != null) {
        entity = entities.getEntity(x, y);
      }
    }
    


    if (chart != null) {
      ChartMouseEvent event = new ChartMouseEvent(getChart(), e, entity);
      for (int i = listeners.length - 1; i >= 0; i--) {
        ((ChartMouseListener)listeners[i]).chartMouseMoved(event);
      }
    }
  }
  






  public void zoomInBoth(double x, double y)
  {
    Plot plot = chart.getPlot();
    if (plot == null) {
      return;
    }
    


    boolean savedNotify = plot.isNotify();
    plot.setNotify(false);
    zoomInDomain(x, y);
    zoomInRange(x, y);
    plot.setNotify(savedNotify);
  }
  







  public void zoomInDomain(double x, double y)
  {
    Plot plot = chart.getPlot();
    if ((plot instanceof Zoomable))
    {


      boolean savedNotify = plot.isNotify();
      plot.setNotify(false);
      Zoomable z = (Zoomable)plot;
      z.zoomDomainAxes(zoomInFactor, info.getPlotInfo(), translateScreenToJava2D(new Point((int)x, (int)y)), zoomAroundAnchor);
      

      plot.setNotify(savedNotify);
    }
  }
  







  public void zoomInRange(double x, double y)
  {
    Plot plot = chart.getPlot();
    if ((plot instanceof Zoomable))
    {


      boolean savedNotify = plot.isNotify();
      plot.setNotify(false);
      Zoomable z = (Zoomable)plot;
      z.zoomRangeAxes(zoomInFactor, info.getPlotInfo(), translateScreenToJava2D(new Point((int)x, (int)y)), zoomAroundAnchor);
      

      plot.setNotify(savedNotify);
    }
  }
  





  public void zoomOutBoth(double x, double y)
  {
    Plot plot = chart.getPlot();
    if (plot == null) {
      return;
    }
    


    boolean savedNotify = plot.isNotify();
    plot.setNotify(false);
    zoomOutDomain(x, y);
    zoomOutRange(x, y);
    plot.setNotify(savedNotify);
  }
  







  public void zoomOutDomain(double x, double y)
  {
    Plot plot = chart.getPlot();
    if ((plot instanceof Zoomable))
    {


      boolean savedNotify = plot.isNotify();
      plot.setNotify(false);
      Zoomable z = (Zoomable)plot;
      z.zoomDomainAxes(zoomOutFactor, info.getPlotInfo(), translateScreenToJava2D(new Point((int)x, (int)y)), zoomAroundAnchor);
      

      plot.setNotify(savedNotify);
    }
  }
  







  public void zoomOutRange(double x, double y)
  {
    Plot plot = chart.getPlot();
    if ((plot instanceof Zoomable))
    {


      boolean savedNotify = plot.isNotify();
      plot.setNotify(false);
      Zoomable z = (Zoomable)plot;
      z.zoomRangeAxes(zoomOutFactor, info.getPlotInfo(), translateScreenToJava2D(new Point((int)x, (int)y)), zoomAroundAnchor);
      

      plot.setNotify(savedNotify);
    }
  }
  







  public void zoom(Rectangle2D selection)
  {
    Point2D selectOrigin = translateScreenToJava2D(new Point((int)Math.ceil(selection.getX()), (int)Math.ceil(selection.getY())));
    

    PlotRenderingInfo plotInfo = info.getPlotInfo();
    Rectangle2D scaledDataArea = getScreenDataArea((int)selection.getCenterX(), (int)selection.getCenterY());
    
    if ((selection.getHeight() > 0.0D) && (selection.getWidth() > 0.0D))
    {
      double hLower = (selection.getMinX() - scaledDataArea.getMinX()) / scaledDataArea.getWidth();
      
      double hUpper = (selection.getMaxX() - scaledDataArea.getMinX()) / scaledDataArea.getWidth();
      
      double vLower = (scaledDataArea.getMaxY() - selection.getMaxY()) / scaledDataArea.getHeight();
      
      double vUpper = (scaledDataArea.getMaxY() - selection.getMinY()) / scaledDataArea.getHeight();
      

      Plot p = chart.getPlot();
      if ((p instanceof Zoomable))
      {


        boolean savedNotify = p.isNotify();
        p.setNotify(false);
        Zoomable z = (Zoomable)p;
        if (z.getOrientation() == PlotOrientation.HORIZONTAL) {
          z.zoomDomainAxes(vLower, vUpper, plotInfo, selectOrigin);
          z.zoomRangeAxes(hLower, hUpper, plotInfo, selectOrigin);
        }
        else {
          z.zoomDomainAxes(hLower, hUpper, plotInfo, selectOrigin);
          z.zoomRangeAxes(vLower, vUpper, plotInfo, selectOrigin);
        }
        p.setNotify(savedNotify);
      }
    }
  }
  




  public void restoreAutoBounds()
  {
    Plot plot = chart.getPlot();
    if (plot == null) {
      return;
    }
    


    boolean savedNotify = plot.isNotify();
    plot.setNotify(false);
    restoreAutoDomainBounds();
    restoreAutoRangeBounds();
    plot.setNotify(savedNotify);
  }
  


  public void restoreAutoDomainBounds()
  {
    Plot plot = chart.getPlot();
    if ((plot instanceof Zoomable)) {
      Zoomable z = (Zoomable)plot;
      


      boolean savedNotify = plot.isNotify();
      plot.setNotify(false);
      
      Point2D zp = zoomPoint != null ? zoomPoint : new Point();
      
      z.zoomDomainAxes(0.0D, info.getPlotInfo(), zp);
      plot.setNotify(savedNotify);
    }
  }
  


  public void restoreAutoRangeBounds()
  {
    Plot plot = chart.getPlot();
    if ((plot instanceof Zoomable)) {
      Zoomable z = (Zoomable)plot;
      


      boolean savedNotify = plot.isNotify();
      plot.setNotify(false);
      
      Point2D zp = zoomPoint != null ? zoomPoint : new Point();
      
      z.zoomRangeAxes(0.0D, info.getPlotInfo(), zp);
      plot.setNotify(savedNotify);
    }
  }
  





  public Rectangle2D getScreenDataArea()
  {
    Rectangle2D dataArea = info.getPlotInfo().getDataArea();
    Insets insets = getInsets();
    double x = dataArea.getX() * scaleX + left;
    double y = dataArea.getY() * scaleY + top;
    double w = dataArea.getWidth() * scaleX;
    double h = dataArea.getHeight() * scaleY;
    return new Rectangle2D.Double(x, y, w, h);
  }
  








  public Rectangle2D getScreenDataArea(int x, int y)
  {
    PlotRenderingInfo plotInfo = info.getPlotInfo();
    Rectangle2D result;
    Rectangle2D result; if (plotInfo.getSubplotCount() == 0) {
      result = getScreenDataArea();

    }
    else
    {
      Point2D selectOrigin = translateScreenToJava2D(new Point(x, y));
      int subplotIndex = plotInfo.getSubplotIndex(selectOrigin);
      if (subplotIndex == -1) {
        return null;
      }
      result = scale(plotInfo.getSubplotInfo(subplotIndex).getDataArea());
    }
    return result;
  }
  






  public int getInitialDelay()
  {
    return ownToolTipInitialDelay;
  }
  






  public int getReshowDelay()
  {
    return ownToolTipReshowDelay;
  }
  







  public int getDismissDelay()
  {
    return ownToolTipDismissDelay;
  }
  







  public void setInitialDelay(int delay)
  {
    ownToolTipInitialDelay = delay;
  }
  







  public void setReshowDelay(int delay)
  {
    ownToolTipReshowDelay = delay;
  }
  







  public void setDismissDelay(int delay)
  {
    ownToolTipDismissDelay = delay;
  }
  






  public double getZoomInFactor()
  {
    return zoomInFactor;
  }
  






  public void setZoomInFactor(double factor)
  {
    zoomInFactor = factor;
  }
  






  public double getZoomOutFactor()
  {
    return zoomOutFactor;
  }
  






  public void setZoomOutFactor(double factor)
  {
    zoomOutFactor = factor;
  }
  









  private void drawZoomRectangle(Graphics2D g2, boolean xor)
  {
    if (zoomRectangle != null) {
      if (xor)
      {
        g2.setXORMode(Color.gray);
      }
      if (fillZoomRectangle) {
        g2.setPaint(zoomFillPaint);
        g2.fill(zoomRectangle);
      }
      else {
        g2.setPaint(zoomOutlinePaint);
        g2.draw(zoomRectangle);
      }
      if (xor)
      {
        g2.setPaintMode();
      }
    }
  }
  







  private void drawHorizontalAxisTrace(Graphics2D g2, int x)
  {
    Rectangle2D dataArea = getScreenDataArea();
    
    g2.setXORMode(Color.orange);
    if (((int)dataArea.getMinX() < x) && (x < (int)dataArea.getMaxX()))
    {
      if (verticalTraceLine != null) {
        g2.draw(verticalTraceLine);
        verticalTraceLine.setLine(x, (int)dataArea.getMinY(), x, (int)dataArea.getMaxY());
      }
      else
      {
        verticalTraceLine = new Line2D.Float(x, (int)dataArea.getMinY(), x, (int)dataArea.getMaxY());
      }
      
      g2.draw(verticalTraceLine);
    }
    

    g2.setPaintMode();
  }
  







  private void drawVerticalAxisTrace(Graphics2D g2, int y)
  {
    Rectangle2D dataArea = getScreenDataArea();
    
    g2.setXORMode(Color.orange);
    if (((int)dataArea.getMinY() < y) && (y < (int)dataArea.getMaxY()))
    {
      if (horizontalTraceLine != null) {
        g2.draw(horizontalTraceLine);
        horizontalTraceLine.setLine((int)dataArea.getMinX(), y, (int)dataArea.getMaxX(), y);
      }
      else
      {
        horizontalTraceLine = new Line2D.Float((int)dataArea.getMinX(), y, (int)dataArea.getMaxX(), y);
      }
      

      g2.draw(horizontalTraceLine);
    }
    

    g2.setPaintMode();
  }
  






  public void doEditChartProperties()
  {
    ChartEditor editor = ChartEditorManager.getChartEditor(chart);
    int result = JOptionPane.showConfirmDialog(this, editor, localizationResources.getString("Chart_Properties"), 2, -1);
    

    if (result == 0) {
      editor.updateChart(chart);
    }
  }
  





  public void doCopy()
  {
    Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    
    ChartTransferable selection = new ChartTransferable(chart, getWidth(), getHeight());
    
    systemClipboard.setContents(selection, null);
  }
  





  public void doSaveAs()
    throws IOException
  {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(defaultDirectoryForSaveAs);
    ExtensionFileFilter filter = new ExtensionFileFilter(localizationResources.getString("PNG_Image_Files"), ".png");
    
    fileChooser.addChoosableFileFilter(filter);
    
    int option = fileChooser.showSaveDialog(this);
    if (option == 0) {
      String filename = fileChooser.getSelectedFile().getPath();
      if ((isEnforceFileExtensions()) && 
        (!filename.endsWith(".png"))) {
        filename = filename + ".png";
      }
      
      ChartUtilities.saveChartAsPNG(new File(filename), chart, getWidth(), getHeight());
    }
  }
  





  public void createChartPrintJob()
  {
    PrinterJob job = PrinterJob.getPrinterJob();
    PageFormat pf = job.defaultPage();
    PageFormat pf2 = job.pageDialog(pf);
    if (pf2 != pf) {
      job.setPrintable(this, pf2);
      if (job.printDialog()) {
        try {
          job.print();
        }
        catch (PrinterException e) {
          JOptionPane.showMessageDialog(this, e);
        }
      }
    }
  }
  











  public int print(Graphics g, PageFormat pf, int pageIndex)
  {
    if (pageIndex != 0) {
      return 1;
    }
    Graphics2D g2 = (Graphics2D)g;
    double x = pf.getImageableX();
    double y = pf.getImageableY();
    double w = pf.getImageableWidth();
    double h = pf.getImageableHeight();
    chart.draw(g2, new Rectangle2D.Double(x, y, w, h), anchor, null);
    
    return 0;
  }
  





  public void addChartMouseListener(ChartMouseListener listener)
  {
    if (listener == null) {
      throw new IllegalArgumentException("Null 'listener' argument.");
    }
    chartMouseListeners.add(ChartMouseListener.class, listener);
  }
  





  public void removeChartMouseListener(ChartMouseListener listener)
  {
    chartMouseListeners.remove(ChartMouseListener.class, listener);
  }
  







  public EventListener[] getListeners(Class listenerType)
  {
    if (listenerType == ChartMouseListener.class)
    {
      return chartMouseListeners.getListeners(listenerType);
    }
    
    return super.getListeners(listenerType);
  }
  











  protected JPopupMenu createPopupMenu(boolean properties, boolean save, boolean print, boolean zoom)
  {
    return createPopupMenu(properties, false, save, print, zoom);
  }
  














  protected JPopupMenu createPopupMenu(boolean properties, boolean copy, boolean save, boolean print, boolean zoom)
  {
    JPopupMenu result = new JPopupMenu("Chart:");
    boolean separator = false;
    
    if (properties) {
      JMenuItem propertiesItem = new JMenuItem(localizationResources.getString("Properties..."));
      
      propertiesItem.setActionCommand("PROPERTIES");
      propertiesItem.addActionListener(this);
      result.add(propertiesItem);
      separator = true;
    }
    
    if (copy) {
      if (separator) {
        result.addSeparator();
        separator = false;
      }
      JMenuItem copyItem = new JMenuItem(localizationResources.getString("Copy"));
      
      copyItem.setActionCommand("COPY");
      copyItem.addActionListener(this);
      result.add(copyItem);
      separator = !save;
    }
    
    if (save) {
      if (separator) {
        result.addSeparator();
        separator = false;
      }
      JMenuItem saveItem = new JMenuItem(localizationResources.getString("Save_as..."));
      
      saveItem.setActionCommand("SAVE");
      saveItem.addActionListener(this);
      result.add(saveItem);
      separator = true;
    }
    
    if (print) {
      if (separator) {
        result.addSeparator();
        separator = false;
      }
      JMenuItem printItem = new JMenuItem(localizationResources.getString("Print..."));
      
      printItem.setActionCommand("PRINT");
      printItem.addActionListener(this);
      result.add(printItem);
      separator = true;
    }
    
    if (zoom) {
      if (separator) {
        result.addSeparator();
        separator = false;
      }
      
      JMenu zoomInMenu = new JMenu(localizationResources.getString("Zoom_In"));
      

      zoomInBothMenuItem = new JMenuItem(localizationResources.getString("All_Axes"));
      
      zoomInBothMenuItem.setActionCommand("ZOOM_IN_BOTH");
      zoomInBothMenuItem.addActionListener(this);
      zoomInMenu.add(zoomInBothMenuItem);
      
      zoomInMenu.addSeparator();
      
      zoomInDomainMenuItem = new JMenuItem(localizationResources.getString("Domain_Axis"));
      
      zoomInDomainMenuItem.setActionCommand("ZOOM_IN_DOMAIN");
      zoomInDomainMenuItem.addActionListener(this);
      zoomInMenu.add(zoomInDomainMenuItem);
      
      zoomInRangeMenuItem = new JMenuItem(localizationResources.getString("Range_Axis"));
      
      zoomInRangeMenuItem.setActionCommand("ZOOM_IN_RANGE");
      zoomInRangeMenuItem.addActionListener(this);
      zoomInMenu.add(zoomInRangeMenuItem);
      
      result.add(zoomInMenu);
      
      JMenu zoomOutMenu = new JMenu(localizationResources.getString("Zoom_Out"));
      

      zoomOutBothMenuItem = new JMenuItem(localizationResources.getString("All_Axes"));
      
      zoomOutBothMenuItem.setActionCommand("ZOOM_OUT_BOTH");
      zoomOutBothMenuItem.addActionListener(this);
      zoomOutMenu.add(zoomOutBothMenuItem);
      
      zoomOutMenu.addSeparator();
      
      zoomOutDomainMenuItem = new JMenuItem(localizationResources.getString("Domain_Axis"));
      
      zoomOutDomainMenuItem.setActionCommand("ZOOM_DOMAIN_BOTH");
      
      zoomOutDomainMenuItem.addActionListener(this);
      zoomOutMenu.add(zoomOutDomainMenuItem);
      
      zoomOutRangeMenuItem = new JMenuItem(localizationResources.getString("Range_Axis"));
      
      zoomOutRangeMenuItem.setActionCommand("ZOOM_RANGE_BOTH");
      zoomOutRangeMenuItem.addActionListener(this);
      zoomOutMenu.add(zoomOutRangeMenuItem);
      
      result.add(zoomOutMenu);
      
      JMenu autoRangeMenu = new JMenu(localizationResources.getString("Auto_Range"));
      

      zoomResetBothMenuItem = new JMenuItem(localizationResources.getString("All_Axes"));
      
      zoomResetBothMenuItem.setActionCommand("ZOOM_RESET_BOTH");
      
      zoomResetBothMenuItem.addActionListener(this);
      autoRangeMenu.add(zoomResetBothMenuItem);
      
      autoRangeMenu.addSeparator();
      zoomResetDomainMenuItem = new JMenuItem(localizationResources.getString("Domain_Axis"));
      
      zoomResetDomainMenuItem.setActionCommand("ZOOM_RESET_DOMAIN");
      
      zoomResetDomainMenuItem.addActionListener(this);
      autoRangeMenu.add(zoomResetDomainMenuItem);
      
      zoomResetRangeMenuItem = new JMenuItem(localizationResources.getString("Range_Axis"));
      
      zoomResetRangeMenuItem.setActionCommand("ZOOM_RESET_RANGE");
      
      zoomResetRangeMenuItem.addActionListener(this);
      autoRangeMenu.add(zoomResetRangeMenuItem);
      
      result.addSeparator();
      result.add(autoRangeMenu);
    }
    

    return result;
  }
  








  protected void displayPopupMenu(int x, int y)
  {
    if (popup != null)
    {


      Plot plot = chart.getPlot();
      boolean isDomainZoomable = false;
      boolean isRangeZoomable = false;
      if ((plot instanceof Zoomable)) {
        Zoomable z = (Zoomable)plot;
        isDomainZoomable = z.isDomainZoomable();
        isRangeZoomable = z.isRangeZoomable();
      }
      
      if (zoomInDomainMenuItem != null) {
        zoomInDomainMenuItem.setEnabled(isDomainZoomable);
      }
      if (zoomOutDomainMenuItem != null) {
        zoomOutDomainMenuItem.setEnabled(isDomainZoomable);
      }
      if (zoomResetDomainMenuItem != null) {
        zoomResetDomainMenuItem.setEnabled(isDomainZoomable);
      }
      
      if (zoomInRangeMenuItem != null) {
        zoomInRangeMenuItem.setEnabled(isRangeZoomable);
      }
      if (zoomOutRangeMenuItem != null) {
        zoomOutRangeMenuItem.setEnabled(isRangeZoomable);
      }
      
      if (zoomResetRangeMenuItem != null) {
        zoomResetRangeMenuItem.setEnabled(isRangeZoomable);
      }
      
      if (zoomInBothMenuItem != null) {
        zoomInBothMenuItem.setEnabled((isDomainZoomable) && (isRangeZoomable));
      }
      
      if (zoomOutBothMenuItem != null) {
        zoomOutBothMenuItem.setEnabled((isDomainZoomable) && (isRangeZoomable));
      }
      
      if (zoomResetBothMenuItem != null) {
        zoomResetBothMenuItem.setEnabled((isDomainZoomable) && (isRangeZoomable));
      }
      

      popup.show(this, x, y);
    }
  }
  





  public void updateUI()
  {
    if (popup != null) {
      SwingUtilities.updateComponentTreeUI(popup);
    }
    super.updateUI();
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(zoomFillPaint, stream);
    SerialUtilities.writePaint(zoomOutlinePaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    zoomFillPaint = SerialUtilities.readPaint(stream);
    zoomOutlinePaint = SerialUtilities.readPaint(stream);
    

    chartMouseListeners = new EventListenerList();
    

    if (chart != null) {
      chart.addChangeListener(this);
    }
  }
}
