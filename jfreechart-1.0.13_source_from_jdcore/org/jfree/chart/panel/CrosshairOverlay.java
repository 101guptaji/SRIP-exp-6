package org.jfree.chart.panel;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.CrosshairLabelGenerator;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;
















































public class CrosshairOverlay
  extends AbstractOverlay
  implements Overlay, PropertyChangeListener, PublicCloneable, Cloneable, Serializable
{
  private List xCrosshairs;
  private List yCrosshairs;
  
  public CrosshairOverlay()
  {
    xCrosshairs = new ArrayList();
    yCrosshairs = new ArrayList();
  }
  




  public void addDomainCrosshair(Crosshair crosshair)
  {
    if (crosshair == null) {
      throw new IllegalArgumentException("Null 'crosshair' argument.");
    }
    xCrosshairs.add(crosshair);
    crosshair.addPropertyChangeListener(this);
  }
  
  public void removeDomainCrosshair(Crosshair crosshair) {
    if (crosshair == null) {
      throw new IllegalArgumentException("Null 'crosshair' argument.");
    }
    if (xCrosshairs.remove(crosshair)) {
      crosshair.removePropertyChangeListener(this);
      fireOverlayChanged();
    }
  }
  
  public void clearDomainCrosshairs() {
    if (xCrosshairs.isEmpty()) {
      return;
    }
    List crosshairs = getDomainCrosshairs();
    for (int i = 0; i < crosshairs.size(); i++) {
      Crosshair c = (Crosshair)crosshairs.get(i);
      xCrosshairs.remove(c);
      c.removePropertyChangeListener(this);
    }
    fireOverlayChanged();
  }
  
  public List getDomainCrosshairs() {
    return new ArrayList(xCrosshairs);
  }
  




  public void addRangeCrosshair(Crosshair crosshair)
  {
    if (crosshair == null) {
      throw new IllegalArgumentException("Null 'crosshair' argument.");
    }
    yCrosshairs.add(crosshair);
    crosshair.addPropertyChangeListener(this);
  }
  
  public void removeRangeCrosshair(Crosshair crosshair) {
    if (crosshair == null) {
      throw new IllegalArgumentException("Null 'crosshair' argument.");
    }
    if (yCrosshairs.remove(crosshair)) {
      crosshair.removePropertyChangeListener(this);
      fireOverlayChanged();
    }
  }
  
  public void clearRangeCrosshairs() {
    if (yCrosshairs.isEmpty()) {
      return;
    }
    List crosshairs = getRangeCrosshairs();
    for (int i = 0; i < crosshairs.size(); i++) {
      Crosshair c = (Crosshair)crosshairs.get(i);
      yCrosshairs.remove(c);
      c.removePropertyChangeListener(this);
    }
    fireOverlayChanged();
  }
  
  public List getRangeCrosshairs() {
    return new ArrayList(yCrosshairs);
  }
  





  public void propertyChange(PropertyChangeEvent e)
  {
    fireOverlayChanged();
  }
  





  public void paintOverlay(Graphics2D g2, ChartPanel chartPanel)
  {
    Shape savedClip = g2.getClip();
    Rectangle2D dataArea = chartPanel.getScreenDataArea();
    g2.clip(dataArea);
    JFreeChart chart = chartPanel.getChart();
    XYPlot plot = (XYPlot)chart.getPlot();
    ValueAxis xAxis = plot.getDomainAxis();
    RectangleEdge xAxisEdge = plot.getDomainAxisEdge();
    Iterator iterator = xCrosshairs.iterator();
    while (iterator.hasNext()) {
      Crosshair ch = (Crosshair)iterator.next();
      if (ch.isVisible()) {
        double x = ch.getValue();
        double xx = xAxis.valueToJava2D(x, dataArea, xAxisEdge);
        if (plot.getOrientation() == PlotOrientation.VERTICAL) {
          drawVerticalCrosshair(g2, dataArea, xx, ch);
        }
        else {
          drawHorizontalCrosshair(g2, dataArea, xx, ch);
        }
      }
    }
    ValueAxis yAxis = plot.getRangeAxis();
    RectangleEdge yAxisEdge = plot.getRangeAxisEdge();
    iterator = yCrosshairs.iterator();
    while (iterator.hasNext()) {
      Crosshair ch = (Crosshair)iterator.next();
      if (ch.isVisible()) {
        double y = ch.getValue();
        double yy = yAxis.valueToJava2D(y, dataArea, yAxisEdge);
        if (plot.getOrientation() == PlotOrientation.VERTICAL) {
          drawHorizontalCrosshair(g2, dataArea, yy, ch);
        }
        else {
          drawVerticalCrosshair(g2, dataArea, yy, ch);
        }
      }
    }
    g2.setClip(savedClip);
  }
  









  protected void drawHorizontalCrosshair(Graphics2D g2, Rectangle2D dataArea, double y, Crosshair crosshair)
  {
    if ((y >= dataArea.getMinY()) && (y <= dataArea.getMaxY())) {
      Line2D line = new Line2D.Double(dataArea.getMinX(), y, dataArea.getMaxX(), y);
      
      Paint savedPaint = g2.getPaint();
      Stroke savedStroke = g2.getStroke();
      g2.setPaint(crosshair.getPaint());
      g2.setStroke(crosshair.getStroke());
      g2.draw(line);
      if (crosshair.isLabelVisible()) {
        String label = crosshair.getLabelGenerator().generateLabel(crosshair);
        
        RectangleAnchor anchor = crosshair.getLabelAnchor();
        Point2D pt = calculateLabelPoint(line, anchor, 5.0D, 5.0D);
        float xx = (float)pt.getX();
        float yy = (float)pt.getY();
        TextAnchor alignPt = textAlignPtForLabelAnchorH(anchor);
        Shape hotspot = TextUtilities.calculateRotatedStringBounds(label, g2, xx, yy, alignPt, 0.0D, TextAnchor.CENTER);
        
        if (!dataArea.contains(hotspot.getBounds2D())) {
          anchor = flipAnchorV(anchor);
          pt = calculateLabelPoint(line, anchor, 5.0D, 5.0D);
          xx = (float)pt.getX();
          yy = (float)pt.getY();
          alignPt = textAlignPtForLabelAnchorH(anchor);
          hotspot = TextUtilities.calculateRotatedStringBounds(label, g2, xx, yy, alignPt, 0.0D, TextAnchor.CENTER);
        }
        

        g2.setPaint(crosshair.getLabelBackgroundPaint());
        g2.fill(hotspot);
        g2.setPaint(crosshair.getLabelOutlinePaint());
        g2.draw(hotspot);
        TextUtilities.drawAlignedString(label, g2, xx, yy, alignPt);
      }
      g2.setPaint(savedPaint);
      g2.setStroke(savedStroke);
    }
  }
  









  protected void drawVerticalCrosshair(Graphics2D g2, Rectangle2D dataArea, double x, Crosshair crosshair)
  {
    if ((x >= dataArea.getMinX()) && (x <= dataArea.getMaxX())) {
      Line2D line = new Line2D.Double(x, dataArea.getMinY(), x, dataArea.getMaxY());
      
      Paint savedPaint = g2.getPaint();
      Stroke savedStroke = g2.getStroke();
      g2.setPaint(crosshair.getPaint());
      g2.setStroke(crosshair.getStroke());
      g2.draw(line);
      if (crosshair.isLabelVisible()) {
        String label = crosshair.getLabelGenerator().generateLabel(crosshair);
        
        RectangleAnchor anchor = crosshair.getLabelAnchor();
        Point2D pt = calculateLabelPoint(line, anchor, 5.0D, 5.0D);
        float xx = (float)pt.getX();
        float yy = (float)pt.getY();
        TextAnchor alignPt = textAlignPtForLabelAnchorV(anchor);
        Shape hotspot = TextUtilities.calculateRotatedStringBounds(label, g2, xx, yy, alignPt, 0.0D, TextAnchor.CENTER);
        
        if (!dataArea.contains(hotspot.getBounds2D())) {
          anchor = flipAnchorH(anchor);
          pt = calculateLabelPoint(line, anchor, 5.0D, 5.0D);
          xx = (float)pt.getX();
          yy = (float)pt.getY();
          alignPt = textAlignPtForLabelAnchorV(anchor);
          hotspot = TextUtilities.calculateRotatedStringBounds(label, g2, xx, yy, alignPt, 0.0D, TextAnchor.CENTER);
        }
        
        g2.setPaint(crosshair.getLabelBackgroundPaint());
        g2.fill(hotspot);
        g2.setPaint(crosshair.getLabelOutlinePaint());
        g2.draw(hotspot);
        TextUtilities.drawAlignedString(label, g2, xx, yy, alignPt);
      }
      g2.setPaint(savedPaint);
      g2.setStroke(savedStroke);
    }
  }
  










  private Point2D calculateLabelPoint(Line2D line, RectangleAnchor anchor, double deltaX, double deltaY)
  {
    double x = 0.0D;
    double y = 0.0D;
    boolean left = (anchor == RectangleAnchor.BOTTOM_LEFT) || (anchor == RectangleAnchor.LEFT) || (anchor == RectangleAnchor.TOP_LEFT);
    

    boolean right = (anchor == RectangleAnchor.BOTTOM_RIGHT) || (anchor == RectangleAnchor.RIGHT) || (anchor == RectangleAnchor.TOP_RIGHT);
    

    boolean top = (anchor == RectangleAnchor.TOP_LEFT) || (anchor == RectangleAnchor.TOP) || (anchor == RectangleAnchor.TOP_RIGHT);
    

    boolean bottom = (anchor == RectangleAnchor.BOTTOM_LEFT) || (anchor == RectangleAnchor.BOTTOM) || (anchor == RectangleAnchor.BOTTOM_RIGHT);
    

    Rectangle rect = line.getBounds();
    Point2D pt = RectangleAnchor.coordinates(rect, anchor);
    
    if (line.getX1() == line.getX2()) {
      x = line.getX1();
      y = (line.getY1() + line.getY2()) / 2.0D;
      if (left) {
        x -= deltaX;
      }
      if (right) {
        x += deltaX;
      }
      if (top) {
        y = Math.min(line.getY1(), line.getY2()) + deltaY;
      }
      if (bottom) {
        y = Math.max(line.getY1(), line.getY2()) - deltaY;
      }
    }
    else {
      x = (line.getX1() + line.getX2()) / 2.0D;
      y = line.getY1();
      if (left) {
        x = Math.min(line.getX1(), line.getX2()) + deltaX;
      }
      if (right) {
        x = Math.max(line.getX1(), line.getX2()) - deltaX;
      }
      if (top) {
        y -= deltaY;
      }
      if (bottom) {
        y += deltaY;
      }
    }
    return new Point2D.Double(x, y);
  }
  







  private TextAnchor textAlignPtForLabelAnchorV(RectangleAnchor anchor)
  {
    TextAnchor result = TextAnchor.CENTER;
    if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
      result = TextAnchor.TOP_RIGHT;
    }
    else if (anchor.equals(RectangleAnchor.TOP)) {
      result = TextAnchor.TOP_CENTER;
    }
    else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
      result = TextAnchor.TOP_LEFT;
    }
    else if (anchor.equals(RectangleAnchor.LEFT)) {
      result = TextAnchor.HALF_ASCENT_RIGHT;
    }
    else if (anchor.equals(RectangleAnchor.RIGHT)) {
      result = TextAnchor.HALF_ASCENT_LEFT;
    }
    else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
      result = TextAnchor.BOTTOM_RIGHT;
    }
    else if (anchor.equals(RectangleAnchor.BOTTOM)) {
      result = TextAnchor.BOTTOM_CENTER;
    }
    else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
      result = TextAnchor.BOTTOM_LEFT;
    }
    return result;
  }
  







  private TextAnchor textAlignPtForLabelAnchorH(RectangleAnchor anchor)
  {
    TextAnchor result = TextAnchor.CENTER;
    if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
      result = TextAnchor.BOTTOM_LEFT;
    }
    else if (anchor.equals(RectangleAnchor.TOP)) {
      result = TextAnchor.BOTTOM_CENTER;
    }
    else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
      result = TextAnchor.BOTTOM_RIGHT;
    }
    else if (anchor.equals(RectangleAnchor.LEFT)) {
      result = TextAnchor.HALF_ASCENT_LEFT;
    }
    else if (anchor.equals(RectangleAnchor.RIGHT)) {
      result = TextAnchor.HALF_ASCENT_RIGHT;
    }
    else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
      result = TextAnchor.TOP_LEFT;
    }
    else if (anchor.equals(RectangleAnchor.BOTTOM)) {
      result = TextAnchor.TOP_CENTER;
    }
    else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
      result = TextAnchor.TOP_RIGHT;
    }
    return result;
  }
  
  private RectangleAnchor flipAnchorH(RectangleAnchor anchor) {
    RectangleAnchor result = anchor;
    if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
      result = RectangleAnchor.TOP_RIGHT;
    }
    else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
      result = RectangleAnchor.TOP_LEFT;
    }
    else if (anchor.equals(RectangleAnchor.LEFT)) {
      result = RectangleAnchor.RIGHT;
    }
    else if (anchor.equals(RectangleAnchor.RIGHT)) {
      result = RectangleAnchor.LEFT;
    }
    else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
      result = RectangleAnchor.BOTTOM_RIGHT;
    }
    else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
      result = RectangleAnchor.BOTTOM_LEFT;
    }
    return result;
  }
  
  private RectangleAnchor flipAnchorV(RectangleAnchor anchor) {
    RectangleAnchor result = anchor;
    if (anchor.equals(RectangleAnchor.TOP_LEFT)) {
      result = RectangleAnchor.BOTTOM_LEFT;
    }
    else if (anchor.equals(RectangleAnchor.TOP_RIGHT)) {
      result = RectangleAnchor.BOTTOM_RIGHT;
    }
    else if (anchor.equals(RectangleAnchor.TOP)) {
      result = RectangleAnchor.BOTTOM;
    }
    else if (anchor.equals(RectangleAnchor.BOTTOM)) {
      result = RectangleAnchor.TOP;
    }
    else if (anchor.equals(RectangleAnchor.BOTTOM_LEFT)) {
      result = RectangleAnchor.TOP_LEFT;
    }
    else if (anchor.equals(RectangleAnchor.BOTTOM_RIGHT)) {
      result = RectangleAnchor.TOP_RIGHT;
    }
    return result;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof CrosshairOverlay)) {
      return false;
    }
    CrosshairOverlay that = (CrosshairOverlay)obj;
    if (!xCrosshairs.equals(xCrosshairs)) {
      return false;
    }
    if (!yCrosshairs.equals(yCrosshairs)) {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    CrosshairOverlay clone = (CrosshairOverlay)super.clone();
    xCrosshairs = ((List)ObjectUtilities.deepClone(xCrosshairs));
    yCrosshairs = ((List)ObjectUtilities.deepClone(yCrosshairs));
    return clone;
  }
}
