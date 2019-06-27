package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ResourceBundle;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.needle.ArrowNeedle;
import org.jfree.chart.needle.LineNeedle;
import org.jfree.chart.needle.LongNeedle;
import org.jfree.chart.needle.MeterNeedle;
import org.jfree.chart.needle.MiddlePinNeedle;
import org.jfree.chart.needle.PinNeedle;
import org.jfree.chart.needle.PlumNeedle;
import org.jfree.chart.needle.PointerNeedle;
import org.jfree.chart.needle.ShipNeedle;
import org.jfree.chart.needle.WindNeedle;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;






































































public class CompassPlot
  extends Plot
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 6924382802125527395L;
  public static final Font DEFAULT_LABEL_FONT = new Font("SansSerif", 1, 10);
  


  public static final int NO_LABELS = 0;
  

  public static final int VALUE_LABELS = 1;
  

  private int labelType;
  

  private Font labelFont;
  

  private boolean drawBorder = false;
  

  private transient Paint roseHighlightPaint = Color.black;
  

  private transient Paint rosePaint = Color.yellow;
  

  private transient Paint roseCenterPaint = Color.white;
  

  private Font compassFont = new Font("Arial", 0, 10);
  

  private transient Ellipse2D circle1;
  

  private transient Ellipse2D circle2;
  

  private transient Area a1;
  

  private transient Area a2;
  

  private transient Rectangle2D rect1;
  

  private ValueDataset[] datasets = new ValueDataset[1];
  

  private MeterNeedle[] seriesNeedle = new MeterNeedle[1];
  

  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
  






  protected double revolutionDistance = 360.0D;
  


  public CompassPlot()
  {
    this(new DefaultValueDataset());
  }
  





  public CompassPlot(ValueDataset dataset)
  {
    if (dataset != null) {
      datasets[0] = dataset;
      dataset.addChangeListener(this);
    }
    circle1 = new Ellipse2D.Double();
    circle2 = new Ellipse2D.Double();
    rect1 = new Rectangle2D.Double();
    setSeriesNeedle(0);
  }
  








  public int getLabelType()
  {
    return labelType;
  }
  







  public void setLabelType(int type)
  {
    if ((type != 0) && (type != 1)) {
      throw new IllegalArgumentException("MeterPlot.setLabelType(int): unrecognised type.");
    }
    
    if (labelType != type) {
      labelType = type;
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
      throw new IllegalArgumentException("Null 'font' not allowed.");
    }
    labelFont = font;
    fireChangeEvent();
  }
  






  public Paint getRosePaint()
  {
    return rosePaint;
  }
  







  public void setRosePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    rosePaint = paint;
    fireChangeEvent();
  }
  







  public Paint getRoseCenterPaint()
  {
    return roseCenterPaint;
  }
  







  public void setRoseCenterPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    roseCenterPaint = paint;
    fireChangeEvent();
  }
  







  public Paint getRoseHighlightPaint()
  {
    return roseHighlightPaint;
  }
  







  public void setRoseHighlightPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    roseHighlightPaint = paint;
    fireChangeEvent();
  }
  






  public boolean getDrawBorder()
  {
    return drawBorder;
  }
  






  public void setDrawBorder(boolean status)
  {
    drawBorder = status;
    fireChangeEvent();
  }
  








  public void setSeriesPaint(int series, Paint paint)
  {
    if ((series >= 0) && (series < seriesNeedle.length)) {
      seriesNeedle[series].setFillPaint(paint);
    }
  }
  








  public void setSeriesOutlinePaint(int series, Paint p)
  {
    if ((series >= 0) && (series < seriesNeedle.length)) {
      seriesNeedle[series].setOutlinePaint(p);
    }
  }
  









  public void setSeriesOutlineStroke(int series, Stroke stroke)
  {
    if ((series >= 0) && (series < seriesNeedle.length)) {
      seriesNeedle[series].setOutlineStroke(stroke);
    }
  }
  







  public void setSeriesNeedle(int type)
  {
    setSeriesNeedle(0, type);
  }
  


















  public void setSeriesNeedle(int index, int type)
  {
    switch (type) {
    case 0: 
      setSeriesNeedle(index, new ArrowNeedle(true));
      setSeriesPaint(index, Color.red);
      seriesNeedle[index].setHighlightPaint(Color.white);
      break;
    case 1: 
      setSeriesNeedle(index, new LineNeedle());
      break;
    case 2: 
      MeterNeedle longNeedle = new LongNeedle();
      longNeedle.setRotateY(0.5D);
      setSeriesNeedle(index, longNeedle);
      break;
    case 3: 
      setSeriesNeedle(index, new PinNeedle());
      break;
    case 4: 
      setSeriesNeedle(index, new PlumNeedle());
      break;
    case 5: 
      setSeriesNeedle(index, new PointerNeedle());
      break;
    case 6: 
      setSeriesPaint(index, null);
      setSeriesOutlineStroke(index, new BasicStroke(3.0F));
      setSeriesNeedle(index, new ShipNeedle());
      break;
    case 7: 
      setSeriesPaint(index, Color.blue);
      setSeriesNeedle(index, new WindNeedle());
      break;
    case 8: 
      setSeriesNeedle(index, new ArrowNeedle(true));
      break;
    case 9: 
      setSeriesNeedle(index, new MiddlePinNeedle());
      break;
    
    default: 
      throw new IllegalArgumentException("Unrecognised type.");
    }
    
  }
  






  public void setSeriesNeedle(int index, MeterNeedle needle)
  {
    if ((needle != null) && (index < seriesNeedle.length)) {
      seriesNeedle[index] = needle;
    }
    fireChangeEvent();
  }
  






  public ValueDataset[] getDatasets()
  {
    return datasets;
  }
  






  public void addDataset(ValueDataset dataset)
  {
    addDataset(dataset, null);
  }
  






  public void addDataset(ValueDataset dataset, MeterNeedle needle)
  {
    if (dataset != null) {
      int i = datasets.length + 1;
      ValueDataset[] t = new ValueDataset[i];
      MeterNeedle[] p = new MeterNeedle[i];
      i -= 2;
      for (; i >= 0; i--) {
        t[i] = datasets[i];
        p[i] = seriesNeedle[i];
      }
      i = datasets.length;
      t[i] = dataset;
      p[i] = (needle != null ? needle : p[(i - 1)]);
      
      ValueDataset[] a = datasets;
      MeterNeedle[] b = seriesNeedle;
      datasets = t;
      seriesNeedle = p;
      
      for (i--; i >= 0; i--) {
        a[i] = null;
        b[i] = null;
      }
      dataset.addChangeListener(this);
    }
  }
  












  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info)
  {
    int outerRadius = 0;
    int innerRadius = 0;
    


    if (info != null) {
      info.setPlotArea(area);
    }
    

    RectangleInsets insets = getInsets();
    insets.trim(area);
    

    if (drawBorder) {
      drawBackground(g2, area);
    }
    
    int midX = (int)(area.getWidth() / 2.0D);
    int midY = (int)(area.getHeight() / 2.0D);
    int radius = midX;
    if (midY < midX) {
      radius = midY;
    }
    radius--;
    int diameter = 2 * radius;
    
    midX += (int)area.getMinX();
    midY += (int)area.getMinY();
    
    circle1.setFrame(midX - radius, midY - radius, diameter, diameter);
    circle2.setFrame(midX - radius + 15, midY - radius + 15, diameter - 30, diameter - 30);
    


    g2.setPaint(rosePaint);
    a1 = new Area(circle1);
    a2 = new Area(circle2);
    a1.subtract(a2);
    g2.fill(a1);
    
    g2.setPaint(roseCenterPaint);
    int x1 = diameter - 30;
    g2.fillOval(midX - radius + 15, midY - radius + 15, x1, x1);
    g2.setPaint(roseHighlightPaint);
    g2.drawOval(midX - radius, midY - radius, diameter, diameter);
    x1 = diameter - 20;
    g2.drawOval(midX - radius + 10, midY - radius + 10, x1, x1);
    x1 = diameter - 30;
    g2.drawOval(midX - radius + 15, midY - radius + 15, x1, x1);
    x1 = diameter - 80;
    g2.drawOval(midX - radius + 40, midY - radius + 40, x1, x1);
    
    outerRadius = radius - 20;
    innerRadius = radius - 32;
    for (int w = 0; w < 360; w += 15) {
      double a = Math.toRadians(w);
      x1 = midX - (int)(Math.sin(a) * innerRadius);
      int x2 = midX - (int)(Math.sin(a) * outerRadius);
      int y1 = midY - (int)(Math.cos(a) * innerRadius);
      int y2 = midY - (int)(Math.cos(a) * outerRadius);
      g2.drawLine(x1, y1, x2, y2);
    }
    
    g2.setPaint(roseHighlightPaint);
    innerRadius = radius - 26;
    outerRadius = 7;
    for (int w = 45; w < 360; w += 90) {
      double a = Math.toRadians(w);
      x1 = midX - (int)(Math.sin(a) * innerRadius);
      int y1 = midY - (int)(Math.cos(a) * innerRadius);
      g2.fillOval(x1 - outerRadius, y1 - outerRadius, 2 * outerRadius, 2 * outerRadius);
    }
    


    for (int w = 0; w < 360; w += 90) {
      double a = Math.toRadians(w);
      x1 = midX - (int)(Math.sin(a) * innerRadius);
      int y1 = midY - (int)(Math.cos(a) * innerRadius);
      
      Polygon p = new Polygon();
      p.addPoint(x1 - outerRadius, y1);
      p.addPoint(x1, y1 + outerRadius);
      p.addPoint(x1 + outerRadius, y1);
      p.addPoint(x1, y1 - outerRadius);
      g2.fillPolygon(p);
    }
    

    innerRadius = radius - 42;
    Font f = getCompassFont(radius);
    g2.setFont(f);
    g2.drawString("N", midX - 5, midY - innerRadius + f.getSize());
    g2.drawString("S", midX - 5, midY + innerRadius - 5);
    g2.drawString("W", midX - innerRadius + 5, midY + 5);
    g2.drawString("E", midX + innerRadius - f.getSize(), midY + 5);
    

    int y1 = radius / 2;
    x1 = radius / 6;
    Rectangle2D needleArea = new Rectangle2D.Double(midX - x1, midY - y1, 2 * x1, 2 * y1);
    

    int x = seriesNeedle.length;
    int current = 0;
    double value = 0.0D;
    for (int i = datasets.length - 1; 
        i >= 0; i--) {
      ValueDataset data = datasets[i];
      
      if ((data != null) && (data.getValue() != null)) {
        value = data.getValue().doubleValue() % revolutionDistance;
        
        value = value / revolutionDistance * 360.0D;
        current = i % x;
        seriesNeedle[current].draw(g2, needleArea, value);
      }
    }
    
    if (drawBorder) {
      drawOutline(g2, area);
    }
  }
  





  public String getPlotType()
  {
    return localizationResources.getString("Compass_Plot");
  }
  





  public LegendItemCollection getLegendItems()
  {
    return null;
  }
  







  public void zoom(double percent) {}
  






  protected Font getCompassFont(int radius)
  {
    float fontSize = radius / 10.0F;
    if (fontSize < 8.0F) {
      fontSize = 8.0F;
    }
    Font newFont = compassFont.deriveFont(fontSize);
    return newFont;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof CompassPlot)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    CompassPlot that = (CompassPlot)obj;
    if (labelType != labelType) {
      return false;
    }
    if (!ObjectUtilities.equal(labelFont, labelFont)) {
      return false;
    }
    if (drawBorder != drawBorder) {
      return false;
    }
    if (!PaintUtilities.equal(roseHighlightPaint, roseHighlightPaint))
    {
      return false;
    }
    if (!PaintUtilities.equal(rosePaint, rosePaint)) {
      return false;
    }
    if (!PaintUtilities.equal(roseCenterPaint, roseCenterPaint))
    {
      return false;
    }
    if (!ObjectUtilities.equal(compassFont, compassFont)) {
      return false;
    }
    if (!Arrays.equals(seriesNeedle, seriesNeedle)) {
      return false;
    }
    if (getRevolutionDistance() != that.getRevolutionDistance()) {
      return false;
    }
    return true;
  }
  








  public Object clone()
    throws CloneNotSupportedException
  {
    CompassPlot clone = (CompassPlot)super.clone();
    if (circle1 != null) {
      circle1 = ((Ellipse2D)circle1.clone());
    }
    if (circle2 != null) {
      circle2 = ((Ellipse2D)circle2.clone());
    }
    if (a1 != null) {
      a1 = ((Area)a1.clone());
    }
    if (a2 != null) {
      a2 = ((Area)a2.clone());
    }
    if (rect1 != null) {
      rect1 = ((Rectangle2D)rect1.clone());
    }
    datasets = ((ValueDataset[])datasets.clone());
    seriesNeedle = ((MeterNeedle[])seriesNeedle.clone());
    

    for (int i = 0; i < datasets.length; i++) {
      if (datasets[i] != null) {
        datasets[i].addChangeListener(clone);
      }
    }
    return clone;
  }
  








  public void setRevolutionDistance(double size)
  {
    if (size > 0.0D) {
      revolutionDistance = size;
    }
  }
  






  public double getRevolutionDistance()
  {
    return revolutionDistance;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(rosePaint, stream);
    SerialUtilities.writePaint(roseCenterPaint, stream);
    SerialUtilities.writePaint(roseHighlightPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    rosePaint = SerialUtilities.readPaint(stream);
    roseCenterPaint = SerialUtilities.readPaint(stream);
    roseHighlightPaint = SerialUtilities.readPaint(stream);
  }
}
