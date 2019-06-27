package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Double;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.ResourceBundle;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.general.ValueDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.UnitType;

























































































































































public class ThermometerPlot
  extends Plot
  implements ValueAxisPlot, Zoomable, Cloneable, Serializable
{
  private static final long serialVersionUID = 4087093313147984390L;
  public static final int UNITS_NONE = 0;
  public static final int UNITS_FAHRENHEIT = 1;
  public static final int UNITS_CELCIUS = 2;
  public static final int UNITS_KELVIN = 3;
  public static final int NONE = 0;
  public static final int RIGHT = 1;
  public static final int LEFT = 2;
  public static final int BULB = 3;
  public static final int NORMAL = 0;
  public static final int WARNING = 1;
  public static final int CRITICAL = 2;
  /**
   * @deprecated
   */
  protected static final int BULB_RADIUS = 40;
  /**
   * @deprecated
   */
  protected static final int BULB_DIAMETER = 80;
  /**
   * @deprecated
   */
  protected static final int COLUMN_RADIUS = 20;
  /**
   * @deprecated
   */
  protected static final int COLUMN_DIAMETER = 40;
  /**
   * @deprecated
   */
  protected static final int GAP_RADIUS = 5;
  /**
   * @deprecated
   */
  protected static final int GAP_DIAMETER = 10;
  protected static final int AXIS_GAP = 10;
  protected static final String[] UNITS = { "", "°F", "°C", "°K" };
  



  protected static final int RANGE_LOW = 0;
  


  protected static final int RANGE_HIGH = 1;
  


  protected static final int DISPLAY_LOW = 2;
  


  protected static final int DISPLAY_HIGH = 3;
  


  protected static final double DEFAULT_LOWER_BOUND = 0.0D;
  


  protected static final double DEFAULT_UPPER_BOUND = 100.0D;
  


  protected static final int DEFAULT_BULB_RADIUS = 40;
  


  protected static final int DEFAULT_COLUMN_RADIUS = 20;
  


  protected static final int DEFAULT_GAP = 5;
  


  private ValueDataset dataset;
  


  private ValueAxis rangeAxis;
  


  private double lowerBound = 0.0D;
  

  private double upperBound = 100.0D;
  





  private int bulbRadius = 40;
  





  private int columnRadius = 20;
  





  private int gap = 5;
  


  private RectangleInsets padding;
  


  private transient Stroke thermometerStroke = new BasicStroke(1.0F);
  

  private transient Paint thermometerPaint = Color.black;
  

  private int units = 2;
  

  private int valueLocation = 3;
  

  private int axisLocation = 2;
  

  private Font valueFont = new Font("SansSerif", 1, 16);
  

  private transient Paint valuePaint = Color.white;
  

  private NumberFormat valueFormat = new DecimalFormat();
  

  private transient Paint mercuryPaint = Color.lightGray;
  

  private boolean showValueLines = false;
  

  private int subrange = -1;
  

  private double[][] subrangeInfo = { { 0.0D, 50.0D, 0.0D, 50.0D }, { 50.0D, 75.0D, 50.0D, 75.0D }, { 75.0D, 100.0D, 75.0D, 100.0D } };
  








  private boolean followDataInSubranges = false;
  




  private boolean useSubrangePaint = true;
  

  private transient Paint[] subrangePaint = { Color.green, Color.orange, Color.red };
  


  private boolean subrangeIndicatorsVisible = true;
  

  private transient Stroke subrangeIndicatorStroke = new BasicStroke(2.0F);
  

  private transient Stroke rangeIndicatorStroke = new BasicStroke(3.0F);
  

  protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.plot.LocalizationBundle");
  




  public ThermometerPlot()
  {
    this(new DefaultValueDataset());
  }
  







  public ThermometerPlot(ValueDataset dataset)
  {
    padding = new RectangleInsets(UnitType.RELATIVE, 0.05D, 0.05D, 0.05D, 0.05D);
    
    this.dataset = dataset;
    if (dataset != null) {
      dataset.addChangeListener(this);
    }
    NumberAxis axis = new NumberAxis(null);
    axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    axis.setAxisLineVisible(false);
    axis.setPlot(this);
    axis.addChangeListener(this);
    rangeAxis = axis;
    setAxisRange();
  }
  






  public ValueDataset getDataset()
  {
    return dataset;
  }
  










  public void setDataset(ValueDataset dataset)
  {
    ValueDataset existing = this.dataset;
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
  







  public ValueAxis getRangeAxis()
  {
    return rangeAxis;
  }
  







  public void setRangeAxis(ValueAxis axis)
  {
    if (axis == null) {
      throw new IllegalArgumentException("Null 'axis' argument.");
    }
    
    rangeAxis.removeChangeListener(this);
    
    axis.setPlot(this);
    axis.addChangeListener(this);
    rangeAxis = axis;
    fireChangeEvent();
  }
  







  public double getLowerBound()
  {
    return lowerBound;
  }
  






  public void setLowerBound(double lower)
  {
    lowerBound = lower;
    setAxisRange();
  }
  







  public double getUpperBound()
  {
    return upperBound;
  }
  






  public void setUpperBound(double upper)
  {
    upperBound = upper;
    setAxisRange();
  }
  





  public void setRange(double lower, double upper)
  {
    lowerBound = lower;
    upperBound = upper;
    setAxisRange();
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
    fireChangeEvent();
  }
  







  public Stroke getThermometerStroke()
  {
    return thermometerStroke;
  }
  







  public void setThermometerStroke(Stroke s)
  {
    if (s != null) {
      thermometerStroke = s;
      fireChangeEvent();
    }
  }
  







  public Paint getThermometerPaint()
  {
    return thermometerPaint;
  }
  







  public void setThermometerPaint(Paint paint)
  {
    if (paint != null) {
      thermometerPaint = paint;
      fireChangeEvent();
    }
  }
  








  public int getUnits()
  {
    return units;
  }
  














  public void setUnits(int u)
  {
    if ((u >= 0) && (u < UNITS.length) && 
      (units != u)) {
      units = u;
      fireChangeEvent();
    }
  }
  





  /**
   * @deprecated
   */
  public void setUnits(String u)
  {
    if (u == null) {
      return;
    }
    
    u = u.toUpperCase().trim();
    for (int i = 0; i < UNITS.length; i++) {
      if (u.equals(UNITS[i].toUpperCase().trim())) {
        setUnits(i);
        i = UNITS.length;
      }
    }
  }
  






  public int getValueLocation()
  {
    return valueLocation;
  }
  











  public void setValueLocation(int location)
  {
    if ((location >= 0) && (location < 4)) {
      valueLocation = location;
      fireChangeEvent();
    }
    else {
      throw new IllegalArgumentException("Location not recognised.");
    }
  }
  







  public int getAxisLocation()
  {
    return axisLocation;
  }
  









  public void setAxisLocation(int location)
  {
    if ((location >= 0) && (location < 3)) {
      axisLocation = location;
      fireChangeEvent();
    }
    else {
      throw new IllegalArgumentException("Location not recognised.");
    }
  }
  






  public Font getValueFont()
  {
    return valueFont;
  }
  






  public void setValueFont(Font f)
  {
    if (f == null) {
      throw new IllegalArgumentException("Null 'font' argument.");
    }
    if (!valueFont.equals(f)) {
      valueFont = f;
      fireChangeEvent();
    }
  }
  






  public Paint getValuePaint()
  {
    return valuePaint;
  }
  







  public void setValuePaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    if (!valuePaint.equals(paint)) {
      valuePaint = paint;
      fireChangeEvent();
    }
  }
  







  public void setValueFormat(NumberFormat formatter)
  {
    if (formatter == null) {
      throw new IllegalArgumentException("Null 'formatter' argument.");
    }
    valueFormat = formatter;
    fireChangeEvent();
  }
  






  public Paint getMercuryPaint()
  {
    return mercuryPaint;
  }
  







  public void setMercuryPaint(Paint paint)
  {
    if (paint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    mercuryPaint = paint;
    fireChangeEvent();
  }
  






  /**
   * @deprecated
   */
  public boolean getShowValueLines()
  {
    return showValueLines;
  }
  






  /**
   * @deprecated
   */
  public void setShowValueLines(boolean b)
  {
    showValueLines = b;
    fireChangeEvent();
  }
  






  public void setSubrangeInfo(int range, double low, double hi)
  {
    setSubrangeInfo(range, low, hi, low, hi);
  }
  











  public void setSubrangeInfo(int range, double rangeLow, double rangeHigh, double displayLow, double displayHigh)
  {
    if ((range >= 0) && (range < 3)) {
      setSubrange(range, rangeLow, rangeHigh);
      setDisplayRange(range, displayLow, displayHigh);
      setAxisRange();
      fireChangeEvent();
    }
  }
  







  public void setSubrange(int range, double low, double high)
  {
    if ((range >= 0) && (range < 3)) {
      subrangeInfo[range][1] = high;
      subrangeInfo[range][0] = low;
    }
  }
  







  public void setDisplayRange(int range, double low, double high)
  {
    if ((range >= 0) && (range < subrangeInfo.length) && (isValidNumber(high)) && (isValidNumber(low)))
    {

      if (high > low) {
        subrangeInfo[range][3] = high;
        subrangeInfo[range][2] = low;
      }
      else {
        subrangeInfo[range][3] = low;
        subrangeInfo[range][2] = high;
      }
    }
  }
  










  public Paint getSubrangePaint(int range)
  {
    if ((range >= 0) && (range < subrangePaint.length)) {
      return subrangePaint[range];
    }
    
    return mercuryPaint;
  }
  









  public void setSubrangePaint(int range, Paint paint)
  {
    if ((range >= 0) && (range < subrangePaint.length) && (paint != null))
    {
      subrangePaint[range] = paint;
      fireChangeEvent();
    }
  }
  





  public boolean getFollowDataInSubranges()
  {
    return followDataInSubranges;
  }
  





  public void setFollowDataInSubranges(boolean flag)
  {
    followDataInSubranges = flag;
    fireChangeEvent();
  }
  







  public boolean getUseSubrangePaint()
  {
    return useSubrangePaint;
  }
  






  public void setUseSubrangePaint(boolean flag)
  {
    useSubrangePaint = flag;
    fireChangeEvent();
  }
  






  public int getBulbRadius()
  {
    return bulbRadius;
  }
  









  public void setBulbRadius(int r)
  {
    bulbRadius = r;
    fireChangeEvent();
  }
  







  public int getBulbDiameter()
  {
    return getBulbRadius() * 2;
  }
  








  public int getColumnRadius()
  {
    return columnRadius;
  }
  









  public void setColumnRadius(int r)
  {
    columnRadius = r;
    fireChangeEvent();
  }
  







  public int getColumnDiameter()
  {
    return getColumnRadius() * 2;
  }
  









  public int getGap()
  {
    return gap;
  }
  










  public void setGap(int gap)
  {
    this.gap = gap;
    fireChangeEvent();
  }
  












  public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info)
  {
    RoundRectangle2D outerStem = new RoundRectangle2D.Double();
    RoundRectangle2D innerStem = new RoundRectangle2D.Double();
    RoundRectangle2D mercuryStem = new RoundRectangle2D.Double();
    Ellipse2D outerBulb = new Ellipse2D.Double();
    Ellipse2D innerBulb = new Ellipse2D.Double();
    String temp = null;
    FontMetrics metrics = null;
    if (info != null) {
      info.setPlotArea(area);
    }
    

    RectangleInsets insets = getInsets();
    insets.trim(area);
    drawBackground(g2, area);
    

    Rectangle2D interior = (Rectangle2D)area.clone();
    padding.trim(interior);
    int midX = (int)(interior.getX() + interior.getWidth() / 2.0D);
    int midY = (int)(interior.getY() + interior.getHeight() / 2.0D);
    int stemTop = (int)(interior.getMinY() + getBulbRadius());
    int stemBottom = (int)(interior.getMaxY() - getBulbDiameter());
    Rectangle2D dataArea = new Rectangle2D.Double(midX - getColumnRadius(), stemTop, getColumnRadius(), stemBottom - stemTop);
    

    outerBulb.setFrame(midX - getBulbRadius(), stemBottom, getBulbDiameter(), getBulbDiameter());
    

    outerStem.setRoundRect(midX - getColumnRadius(), interior.getMinY(), getColumnDiameter(), stemBottom + getBulbDiameter() - stemTop, getColumnDiameter(), getColumnDiameter());
    


    Area outerThermometer = new Area(outerBulb);
    Area tempArea = new Area(outerStem);
    outerThermometer.add(tempArea);
    
    innerBulb.setFrame(midX - getBulbRadius() + getGap(), stemBottom + getGap(), getBulbDiameter() - getGap() * 2, getBulbDiameter() - getGap() * 2);
    


    innerStem.setRoundRect(midX - getColumnRadius() + getGap(), interior.getMinY() + getGap(), getColumnDiameter() - getGap() * 2, stemBottom + getBulbDiameter() - getGap() * 2 - stemTop, getColumnDiameter() - getGap() * 2, getColumnDiameter() - getGap() * 2);
    




    Area innerThermometer = new Area(innerBulb);
    tempArea = new Area(innerStem);
    innerThermometer.add(tempArea);
    
    if ((dataset != null) && (dataset.getValue() != null)) {
      double current = dataset.getValue().doubleValue();
      double ds = rangeAxis.valueToJava2D(current, dataArea, RectangleEdge.LEFT);
      

      int i = getColumnDiameter() - getGap() * 2;
      int j = getColumnRadius() - getGap();
      int l = i / 2;
      int k = (int)Math.round(ds);
      if (k < getGap() + interior.getMinY()) {
        k = (int)(getGap() + interior.getMinY());
        l = getBulbRadius();
      }
      
      Area mercury = new Area(innerBulb);
      
      if (k < stemBottom + getBulbRadius()) {
        mercuryStem.setRoundRect(midX - j, k, i, stemBottom + getBulbRadius() - k, l, l);
        
        tempArea = new Area(mercuryStem);
        mercury.add(tempArea);
      }
      
      g2.setPaint(getCurrentPaint());
      g2.fill(mercury);
      

      if (subrangeIndicatorsVisible) {
        g2.setStroke(subrangeIndicatorStroke);
        Range range = rangeAxis.getRange();
        

        double value = subrangeInfo[0][0];
        if (range.contains(value)) {
          double x = midX + getColumnRadius() + 2;
          double y = rangeAxis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
          
          Line2D line = new Line2D.Double(x, y, x + 10.0D, y);
          g2.setPaint(subrangePaint[0]);
          g2.draw(line);
        }
        

        value = subrangeInfo[1][0];
        if (range.contains(value)) {
          double x = midX + getColumnRadius() + 2;
          double y = rangeAxis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
          
          Line2D line = new Line2D.Double(x, y, x + 10.0D, y);
          g2.setPaint(subrangePaint[1]);
          g2.draw(line);
        }
        

        value = subrangeInfo[2][0];
        if (range.contains(value)) {
          double x = midX + getColumnRadius() + 2;
          double y = rangeAxis.valueToJava2D(value, dataArea, RectangleEdge.LEFT);
          
          Line2D line = new Line2D.Double(x, y, x + 10.0D, y);
          g2.setPaint(subrangePaint[2]);
          g2.draw(line);
        }
      }
      

      if ((rangeAxis != null) && (axisLocation != 0)) {
        int drawWidth = 10;
        if (showValueLines) {
          drawWidth += getColumnDiameter();
        }
        
        double cursor = 0.0D;
        Rectangle2D drawArea;
        switch (axisLocation) {
        case 1: 
          cursor = midX + getColumnRadius();
          drawArea = new Rectangle2D.Double(cursor, stemTop, drawWidth, stemBottom - stemTop + 1);
          
          rangeAxis.draw(g2, cursor, area, drawArea, RectangleEdge.RIGHT, null);
          
          break;
        

        case 2: 
        default: 
          cursor = midX - getColumnRadius();
          drawArea = new Rectangle2D.Double(cursor, stemTop, drawWidth, stemBottom - stemTop + 1);
          
          rangeAxis.draw(g2, cursor, area, drawArea, RectangleEdge.LEFT, null);
        }
        
      }
      



      g2.setFont(valueFont);
      g2.setPaint(valuePaint);
      metrics = g2.getFontMetrics();
      switch (valueLocation) {
      case 1: 
        g2.drawString(valueFormat.format(current), midX + getColumnRadius() + getGap(), midY);
        
        break;
      case 2: 
        String valueString = valueFormat.format(current);
        int stringWidth = metrics.stringWidth(valueString);
        g2.drawString(valueString, midX - getColumnRadius() - getGap() - stringWidth, midY);
        
        break;
      case 3: 
        temp = valueFormat.format(current);
        i = metrics.stringWidth(temp) / 2;
        g2.drawString(temp, midX - i, stemBottom + getBulbRadius() + getGap());
        
        break;
      }
      
    }
    

    g2.setPaint(thermometerPaint);
    g2.setFont(valueFont);
    

    metrics = g2.getFontMetrics();
    int tickX1 = midX - getColumnRadius() - getGap() * 2 - metrics.stringWidth(UNITS[units]);
    
    if (tickX1 > area.getMinX()) {
      g2.drawString(UNITS[units], tickX1, (int)(area.getMinY() + 20.0D));
    }
    


    g2.setStroke(thermometerStroke);
    g2.draw(outerThermometer);
    g2.draw(innerThermometer);
    
    drawOutline(g2, area);
  }
  







  public void zoom(double percent) {}
  






  public String getPlotType()
  {
    return localizationResources.getString("Thermometer_Plot");
  }
  




  public void datasetChanged(DatasetChangeEvent event)
  {
    if (dataset != null) {
      Number vn = dataset.getValue();
      if (vn != null) {
        double value = vn.doubleValue();
        if (inSubrange(0, value)) {
          subrange = 0;
        }
        else if (inSubrange(1, value)) {
          subrange = 1;
        }
        else if (inSubrange(2, value)) {
          subrange = 2;
        }
        else {
          subrange = -1;
        }
        setAxisRange();
      }
    }
    super.datasetChanged(event);
  }
  






  /**
   * @deprecated
   */
  public Number getMinimumVerticalDataValue()
  {
    return new Double(lowerBound);
  }
  






  /**
   * @deprecated
   */
  public Number getMaximumVerticalDataValue()
  {
    return new Double(upperBound);
  }
  






  public Range getDataRange(ValueAxis axis)
  {
    return new Range(lowerBound, upperBound);
  }
  


  protected void setAxisRange()
  {
    if ((subrange >= 0) && (followDataInSubranges)) {
      rangeAxis.setRange(new Range(subrangeInfo[subrange][2], subrangeInfo[subrange][3]));

    }
    else
    {
      rangeAxis.setRange(lowerBound, upperBound);
    }
  }
  




  public LegendItemCollection getLegendItems()
  {
    return null;
  }
  




  public PlotOrientation getOrientation()
  {
    return PlotOrientation.VERTICAL;
  }
  







  protected static boolean isValidNumber(double d)
  {
    return (!Double.isNaN(d)) && (!Double.isInfinite(d));
  }
  







  private boolean inSubrange(int subrange, double value)
  {
    return (value > subrangeInfo[subrange][0]) && (value <= subrangeInfo[subrange][1]);
  }
  







  private Paint getCurrentPaint()
  {
    Paint result = mercuryPaint;
    if (useSubrangePaint) {
      double value = dataset.getValue().doubleValue();
      if (inSubrange(0, value)) {
        result = subrangePaint[0];
      }
      else if (inSubrange(1, value)) {
        result = subrangePaint[1];
      }
      else if (inSubrange(2, value)) {
        result = subrangePaint[2];
      }
    }
    return result;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ThermometerPlot)) {
      return false;
    }
    ThermometerPlot that = (ThermometerPlot)obj;
    if (!super.equals(obj)) {
      return false;
    }
    if (!ObjectUtilities.equal(rangeAxis, rangeAxis)) {
      return false;
    }
    if (axisLocation != axisLocation) {
      return false;
    }
    if (lowerBound != lowerBound) {
      return false;
    }
    if (upperBound != upperBound) {
      return false;
    }
    if (!ObjectUtilities.equal(padding, padding)) {
      return false;
    }
    if (!ObjectUtilities.equal(thermometerStroke, thermometerStroke))
    {
      return false;
    }
    if (!PaintUtilities.equal(thermometerPaint, thermometerPaint))
    {
      return false;
    }
    if (units != units) {
      return false;
    }
    if (valueLocation != valueLocation) {
      return false;
    }
    if (!ObjectUtilities.equal(valueFont, valueFont)) {
      return false;
    }
    if (!PaintUtilities.equal(valuePaint, valuePaint)) {
      return false;
    }
    if (!ObjectUtilities.equal(valueFormat, valueFormat)) {
      return false;
    }
    if (!PaintUtilities.equal(mercuryPaint, mercuryPaint)) {
      return false;
    }
    if (showValueLines != showValueLines) {
      return false;
    }
    if (subrange != subrange) {
      return false;
    }
    if (followDataInSubranges != followDataInSubranges) {
      return false;
    }
    if (!equal(subrangeInfo, subrangeInfo)) {
      return false;
    }
    if (useSubrangePaint != useSubrangePaint) {
      return false;
    }
    if (bulbRadius != bulbRadius) {
      return false;
    }
    if (columnRadius != columnRadius) {
      return false;
    }
    if (gap != gap) {
      return false;
    }
    for (int i = 0; i < subrangePaint.length; i++) {
      if (!PaintUtilities.equal(subrangePaint[i], subrangePaint[i]))
      {
        return false;
      }
    }
    return true;
  }
  







  private static boolean equal(double[][] array1, double[][] array2)
  {
    if (array1 == null) {
      return array2 == null;
    }
    if (array2 == null) {
      return false;
    }
    if (array1.length != array2.length) {
      return false;
    }
    for (int i = 0; i < array1.length; i++) {
      if (!Arrays.equals(array1[i], array2[i])) {
        return false;
      }
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    ThermometerPlot clone = (ThermometerPlot)super.clone();
    
    if (dataset != null) {
      dataset.addChangeListener(clone);
    }
    rangeAxis = ((ValueAxis)ObjectUtilities.clone(rangeAxis));
    if (rangeAxis != null) {
      rangeAxis.setPlot(clone);
      rangeAxis.addChangeListener(clone);
    }
    valueFormat = ((NumberFormat)valueFormat.clone());
    subrangePaint = ((Paint[])subrangePaint.clone());
    
    return clone;
  }
  






  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writeStroke(thermometerStroke, stream);
    SerialUtilities.writePaint(thermometerPaint, stream);
    SerialUtilities.writePaint(valuePaint, stream);
    SerialUtilities.writePaint(mercuryPaint, stream);
    SerialUtilities.writeStroke(subrangeIndicatorStroke, stream);
    SerialUtilities.writeStroke(rangeIndicatorStroke, stream);
    for (int i = 0; i < 3; i++) {
      SerialUtilities.writePaint(subrangePaint[i], stream);
    }
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    thermometerStroke = SerialUtilities.readStroke(stream);
    thermometerPaint = SerialUtilities.readPaint(stream);
    valuePaint = SerialUtilities.readPaint(stream);
    mercuryPaint = SerialUtilities.readPaint(stream);
    subrangeIndicatorStroke = SerialUtilities.readStroke(stream);
    rangeIndicatorStroke = SerialUtilities.readStroke(stream);
    subrangePaint = new Paint[3];
    for (int i = 0; i < 3; i++) {
      subrangePaint[i] = SerialUtilities.readPaint(stream);
    }
    if (rangeAxis != null) {
      rangeAxis.addChangeListener(this);
    }
  }
  











  public void zoomDomainAxes(double factor, PlotRenderingInfo state, Point2D source) {}
  










  public void zoomDomainAxes(double factor, PlotRenderingInfo state, Point2D source, boolean useAnchor) {}
  










  public void zoomRangeAxes(double factor, PlotRenderingInfo state, Point2D source)
  {
    rangeAxis.resizeRange(factor);
  }
  











  public void zoomRangeAxes(double factor, PlotRenderingInfo state, Point2D source, boolean useAnchor)
  {
    double anchorY = getRangeAxis().java2DToValue(source.getY(), state.getDataArea(), RectangleEdge.LEFT);
    
    rangeAxis.resizeRange(factor, anchorY);
  }
  










  public void zoomDomainAxes(double lowerPercent, double upperPercent, PlotRenderingInfo state, Point2D source) {}
  









  public void zoomRangeAxes(double lowerPercent, double upperPercent, PlotRenderingInfo state, Point2D source)
  {
    rangeAxis.zoomRange(lowerPercent, upperPercent);
  }
  




  public boolean isDomainZoomable()
  {
    return false;
  }
  




  public boolean isRangeZoomable()
  {
    return true;
  }
}
