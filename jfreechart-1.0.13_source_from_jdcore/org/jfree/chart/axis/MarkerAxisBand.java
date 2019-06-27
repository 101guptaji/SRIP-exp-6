package org.jfree.chart.axis;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.data.Range;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.ObjectUtilities;











































































public class MarkerAxisBand
  implements Serializable
{
  private static final long serialVersionUID = -1729482413886398919L;
  private NumberAxis axis;
  private double topOuterGap;
  private double topInnerGap;
  private double bottomOuterGap;
  private double bottomInnerGap;
  private Font font;
  private List markers;
  
  public MarkerAxisBand(NumberAxis axis, double topOuterGap, double topInnerGap, double bottomOuterGap, double bottomInnerGap, Font font)
  {
    this.axis = axis;
    this.topOuterGap = topOuterGap;
    this.topInnerGap = topInnerGap;
    this.bottomOuterGap = bottomOuterGap;
    this.bottomInnerGap = bottomInnerGap;
    this.font = font;
    markers = new ArrayList();
  }
  




  public void addMarker(IntervalMarker marker)
  {
    markers.add(marker);
  }
  







  public double getHeight(Graphics2D g2)
  {
    double result = 0.0D;
    if (markers.size() > 0) {
      LineMetrics metrics = font.getLineMetrics("123g", g2.getFontRenderContext());
      

      result = topOuterGap + topInnerGap + metrics.getHeight() + bottomInnerGap + bottomOuterGap;
    }
    
    return result;
  }
  










  private void drawStringInRect(Graphics2D g2, Rectangle2D bounds, Font font, String text)
  {
    g2.setFont(font);
    FontMetrics fm = g2.getFontMetrics(font);
    Rectangle2D r = TextUtilities.getTextBounds(text, g2, fm);
    double x = bounds.getX();
    if (r.getWidth() < bounds.getWidth()) {
      x += (bounds.getWidth() - r.getWidth()) / 2.0D;
    }
    LineMetrics metrics = font.getLineMetrics(text, g2.getFontRenderContext());
    

    g2.drawString(text, (float)x, (float)(bounds.getMaxY() - bottomInnerGap - metrics.getDescent()));
  }
  













  public void draw(Graphics2D g2, Rectangle2D plotArea, Rectangle2D dataArea, double x, double y)
  {
    double h = getHeight(g2);
    Iterator iterator = markers.iterator();
    while (iterator.hasNext()) {
      IntervalMarker marker = (IntervalMarker)iterator.next();
      double start = Math.max(marker.getStartValue(), axis.getRange().getLowerBound());
      

      double end = Math.min(marker.getEndValue(), axis.getRange().getUpperBound());
      

      double s = axis.valueToJava2D(start, dataArea, RectangleEdge.BOTTOM);
      

      double e = axis.valueToJava2D(end, dataArea, RectangleEdge.BOTTOM);
      

      Rectangle2D r = new Rectangle2D.Double(s, y + topOuterGap, e - s, h - topOuterGap - bottomOuterGap);
      



      Composite originalComposite = g2.getComposite();
      g2.setComposite(AlphaComposite.getInstance(3, marker.getAlpha()));
      

      g2.setPaint(marker.getPaint());
      g2.fill(r);
      g2.setPaint(marker.getOutlinePaint());
      g2.draw(r);
      g2.setComposite(originalComposite);
      
      g2.setPaint(Color.black);
      drawStringInRect(g2, r, font, marker.getLabel());
    }
  }
  








  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof MarkerAxisBand)) {
      return false;
    }
    MarkerAxisBand that = (MarkerAxisBand)obj;
    if (topOuterGap != topOuterGap) {
      return false;
    }
    if (topInnerGap != topInnerGap) {
      return false;
    }
    if (bottomInnerGap != bottomInnerGap) {
      return false;
    }
    if (bottomOuterGap != bottomOuterGap) {
      return false;
    }
    if (!ObjectUtilities.equal(font, font)) {
      return false;
    }
    if (!ObjectUtilities.equal(markers, markers)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    int result = 37;
    result = 19 * result + font.hashCode();
    result = 19 * result + markers.hashCode();
    return result;
  }
}
