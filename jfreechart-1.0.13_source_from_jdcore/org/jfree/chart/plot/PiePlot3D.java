package org.jfree.chart.plot;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.Rotation;























































































public class PiePlot3D
  extends PiePlot
  implements Serializable
{
  private static final long serialVersionUID = 3408984188945161432L;
  private double depthFactor = 0.12D;
  






  private boolean darkerSides = false;
  



  public PiePlot3D()
  {
    this(null);
  }
  





  public PiePlot3D(PieDataset dataset)
  {
    super(dataset);
    setCircular(false, false);
  }
  






  public double getDepthFactor()
  {
    return depthFactor;
  }
  







  public void setDepthFactor(double factor)
  {
    depthFactor = factor;
    fireChangeEvent();
  }
  










  public boolean getDarkerSides()
  {
    return darkerSides;
  }
  












  public void setDarkerSides(boolean darker)
  {
    darkerSides = darker;
    fireChangeEvent();
  }
  
















  public void draw(Graphics2D g2, Rectangle2D plotArea, Point2D anchor, PlotState parentState, PlotRenderingInfo info)
  {
    RectangleInsets insets = getInsets();
    insets.trim(plotArea);
    
    Rectangle2D originalPlotArea = (Rectangle2D)plotArea.clone();
    if (info != null) {
      info.setPlotArea(plotArea);
      info.setDataArea(plotArea);
    }
    
    drawBackground(g2, plotArea);
    
    Shape savedClip = g2.getClip();
    g2.clip(plotArea);
    

    double gapPercent = getInteriorGap();
    double labelPercent = 0.0D;
    if (getLabelGenerator() != null) {
      labelPercent = getLabelGap() + getMaximumLabelWidth();
    }
    double gapHorizontal = plotArea.getWidth() * (gapPercent + labelPercent) * 2.0D;
    
    double gapVertical = plotArea.getHeight() * gapPercent * 2.0D;
    












    double linkX = plotArea.getX() + gapHorizontal / 2.0D;
    double linkY = plotArea.getY() + gapVertical / 2.0D;
    double linkW = plotArea.getWidth() - gapHorizontal;
    double linkH = plotArea.getHeight() - gapVertical;
    

    if (isCircular()) {
      double min = Math.min(linkW, linkH) / 2.0D;
      linkX = (linkX + linkX + linkW) / 2.0D - min;
      linkY = (linkY + linkY + linkH) / 2.0D - min;
      linkW = 2.0D * min;
      linkH = 2.0D * min;
    }
    
    PiePlotState state = initialise(g2, plotArea, this, null, info);
    


    Rectangle2D linkAreaXX = new Rectangle2D.Double(linkX, linkY, linkW, linkH * (1.0D - depthFactor));
    
    state.setLinkArea(linkAreaXX);
    











    double hh = linkW * getLabelLinkMargin();
    double vv = linkH * getLabelLinkMargin();
    Rectangle2D explodeArea = new Rectangle2D.Double(linkX + hh / 2.0D, linkY + vv / 2.0D, linkW - hh, linkH - vv);
    

    state.setExplodedPieArea(explodeArea);
    



    double maximumExplodePercent = getMaximumExplodePercent();
    double percent = maximumExplodePercent / (1.0D + maximumExplodePercent);
    
    double h1 = explodeArea.getWidth() * percent;
    double v1 = explodeArea.getHeight() * percent;
    Rectangle2D pieArea = new Rectangle2D.Double(explodeArea.getX() + h1 / 2.0D, explodeArea.getY() + v1 / 2.0D, explodeArea.getWidth() - h1, explodeArea.getHeight() - v1);
    




    int depth = (int)(pieArea.getHeight() * depthFactor);
    Rectangle2D linkArea = new Rectangle2D.Double(linkX, linkY, linkW, linkH - depth);
    
    state.setLinkArea(linkArea);
    
    state.setPieArea(pieArea);
    state.setPieCenterX(pieArea.getCenterX());
    state.setPieCenterY(pieArea.getCenterY() - depth / 2.0D);
    state.setPieWRadius(pieArea.getWidth() / 2.0D);
    state.setPieHRadius((pieArea.getHeight() - depth) / 2.0D);
    

    PieDataset dataset = getDataset();
    if (DatasetUtilities.isEmptyOrNull(getDataset())) {
      drawNoDataMessage(g2, plotArea);
      g2.setClip(savedClip);
      drawOutline(g2, plotArea);
      return;
    }
    

    if (dataset.getKeys().size() > plotArea.getWidth()) {
      String text = "Too many elements";
      Font sfont = new Font("dialog", 1, 10);
      g2.setFont(sfont);
      FontMetrics fm = g2.getFontMetrics(sfont);
      int stringWidth = fm.stringWidth(text);
      
      g2.drawString(text, (int)(plotArea.getX() + (plotArea.getWidth() - stringWidth) / 2.0D), (int)(plotArea.getY() + plotArea.getHeight() / 2.0D));
      

      return;
    }
    


    if (isCircular()) {
      double min = Math.min(plotArea.getWidth(), plotArea.getHeight()) / 2.0D;
      
      plotArea = new Rectangle2D.Double(plotArea.getCenterX() - min, plotArea.getCenterY() - min, 2.0D * min, 2.0D * min);
    }
    

    List sectionKeys = dataset.getKeys();
    
    if (sectionKeys.size() == 0) {
      return;
    }
    

    double arcX = pieArea.getX();
    double arcY = pieArea.getY();
    

    Composite originalComposite = g2.getComposite();
    g2.setComposite(AlphaComposite.getInstance(3, getForegroundAlpha()));
    

    double totalValue = DatasetUtilities.calculatePieDatasetTotal(dataset);
    double runningTotal = 0.0D;
    if (depth < 0) {
      return;
    }
    
    ArrayList arcList = new ArrayList();
    




    Iterator iterator = sectionKeys.iterator();
    while (iterator.hasNext())
    {
      Comparable currentKey = (Comparable)iterator.next();
      Number dataValue = dataset.getValue(currentKey);
      if (dataValue == null) {
        arcList.add(null);
      }
      else {
        double value = dataValue.doubleValue();
        if (value <= 0.0D) {
          arcList.add(null);
        }
        else {
          double startAngle = getStartAngle();
          double direction = getDirection().getFactor();
          double angle1 = startAngle + direction * (runningTotal * 360.0D) / totalValue;
          
          double angle2 = startAngle + direction * (runningTotal + value) * 360.0D / totalValue;
          
          if (Math.abs(angle2 - angle1) > getMinimumArcAngleToDraw()) {
            arcList.add(new Arc2D.Double(arcX, arcY + depth, pieArea.getWidth(), pieArea.getHeight() - depth, angle1, angle2 - angle1, 2));

          }
          else
          {
            arcList.add(null);
          }
          runningTotal += value;
        }
      } }
    Shape oldClip = g2.getClip();
    
    Ellipse2D top = new Ellipse2D.Double(pieArea.getX(), pieArea.getY(), pieArea.getWidth(), pieArea.getHeight() - depth);
    

    Ellipse2D bottom = new Ellipse2D.Double(pieArea.getX(), pieArea.getY() + depth, pieArea.getWidth(), pieArea.getHeight() - depth);
    

    Rectangle2D lower = new Rectangle2D.Double(top.getX(), top.getCenterY(), pieArea.getWidth(), bottom.getMaxY() - top.getCenterY());
    


    Rectangle2D upper = new Rectangle2D.Double(pieArea.getX(), top.getY(), pieArea.getWidth(), bottom.getCenterY() - top.getY());
    

    Area a = new Area(top);
    a.add(new Area(lower));
    Area b = new Area(bottom);
    b.add(new Area(upper));
    Area pie = new Area(a);
    pie.intersect(b);
    
    Area front = new Area(pie);
    front.subtract(new Area(top));
    
    Area back = new Area(pie);
    back.subtract(new Area(bottom));
    



    Arc2D.Double arc = new Arc2D.Double(arcX, arcY + depth, pieArea.getWidth(), pieArea.getHeight() - depth, 0.0D, 360.0D, 2);
    

    int categoryCount = arcList.size();
    for (int categoryIndex = 0; categoryIndex < categoryCount; 
        categoryIndex++) {
      arc = (Arc2D.Double)arcList.get(categoryIndex);
      if (arc != null)
      {

        Comparable key = getSectionKey(categoryIndex);
        Paint paint = lookupSectionPaint(key);
        Paint outlinePaint = lookupSectionOutlinePaint(key);
        Stroke outlineStroke = lookupSectionOutlineStroke(key);
        g2.setPaint(paint);
        g2.fill(arc);
        g2.setPaint(outlinePaint);
        g2.setStroke(outlineStroke);
        g2.draw(arc);
        g2.setPaint(paint);
        
        Point2D p1 = arc.getStartPoint();
        

        int[] xs = { (int)arc.getCenterX(), (int)arc.getCenterX(), (int)p1.getX(), (int)p1.getX() };
        
        int[] ys = { (int)arc.getCenterY(), (int)arc.getCenterY() - depth, (int)p1.getY() - depth, (int)p1.getY() };
        
        Polygon polygon = new Polygon(xs, ys, 4);
        g2.setPaint(Color.lightGray);
        g2.fill(polygon);
        g2.setPaint(outlinePaint);
        g2.setStroke(outlineStroke);
        g2.draw(polygon);
        g2.setPaint(paint);
      }
    }
    
    g2.setPaint(Color.gray);
    g2.fill(back);
    g2.fill(front);
    

    int cat = 0;
    iterator = arcList.iterator();
    while (iterator.hasNext()) {
      Arc2D segment = (Arc2D)iterator.next();
      if (segment != null) {
        Comparable key = getSectionKey(cat);
        Paint paint = lookupSectionPaint(key);
        Paint outlinePaint = lookupSectionOutlinePaint(key);
        Stroke outlineStroke = lookupSectionOutlineStroke(key);
        drawSide(g2, pieArea, segment, front, back, paint, outlinePaint, outlineStroke, false, true);
      }
      
      cat++;
    }
    

    cat = 0;
    iterator = arcList.iterator();
    while (iterator.hasNext()) {
      Arc2D segment = (Arc2D)iterator.next();
      if (segment != null) {
        Comparable key = getSectionKey(cat);
        Paint paint = lookupSectionPaint(key);
        Paint outlinePaint = lookupSectionOutlinePaint(key);
        Stroke outlineStroke = lookupSectionOutlineStroke(key);
        drawSide(g2, pieArea, segment, front, back, paint, outlinePaint, outlineStroke, true, false);
      }
      
      cat++;
    }
    
    g2.setClip(oldClip);
    


    for (int sectionIndex = 0; sectionIndex < categoryCount; 
        sectionIndex++) {
      arc = (Arc2D.Double)arcList.get(sectionIndex);
      if (arc != null)
      {

        Arc2D upperArc = new Arc2D.Double(arcX, arcY, pieArea.getWidth(), pieArea.getHeight() - depth, arc.getAngleStart(), arc.getAngleExtent(), 2);
        


        Comparable currentKey = (Comparable)sectionKeys.get(sectionIndex);
        Paint paint = lookupSectionPaint(currentKey, true);
        Paint outlinePaint = lookupSectionOutlinePaint(currentKey);
        Stroke outlineStroke = lookupSectionOutlineStroke(currentKey);
        g2.setPaint(paint);
        g2.fill(upperArc);
        g2.setStroke(outlineStroke);
        g2.setPaint(outlinePaint);
        g2.draw(upperArc);
        

        if (info != null) {
          EntityCollection entities = info.getOwner().getEntityCollection();
          
          if (entities != null) {
            String tip = null;
            PieToolTipGenerator tipster = getToolTipGenerator();
            if (tipster != null)
            {
              tip = tipster.generateToolTip(dataset, currentKey);
            }
            String url = null;
            if (getURLGenerator() != null) {
              url = getURLGenerator().generateURL(dataset, currentKey, getPieIndex());
            }
            
            PieSectionEntity entity = new PieSectionEntity(upperArc, dataset, getPieIndex(), sectionIndex, currentKey, tip, url);
            

            entities.add(entity);
          }
        }
      }
    }
    List keys = dataset.getKeys();
    Rectangle2D adjustedPlotArea = new Rectangle2D.Double(originalPlotArea.getX(), originalPlotArea.getY(), originalPlotArea.getWidth(), originalPlotArea.getHeight() - depth);
    


    if (getSimpleLabels()) {
      drawSimpleLabels(g2, keys, totalValue, adjustedPlotArea, linkArea, state);
    }
    else
    {
      drawLabels(g2, keys, totalValue, adjustedPlotArea, linkArea, state);
    }
    

    g2.setClip(savedClip);
    g2.setComposite(originalComposite);
    drawOutline(g2, originalPlotArea);
  }
  
























  protected void drawSide(Graphics2D g2, Rectangle2D plotArea, Arc2D arc, Area front, Area back, Paint paint, Paint outlinePaint, Stroke outlineStroke, boolean drawFront, boolean drawBack)
  {
    if ((getDarkerSides()) && 
      ((paint instanceof Color))) {
      Color c = (Color)paint;
      c = c.darker();
      paint = c;
    }
    

    double start = arc.getAngleStart();
    double extent = arc.getAngleExtent();
    double end = start + extent;
    
    g2.setStroke(outlineStroke);
    

    if (extent < 0.0D)
    {
      if (isAngleAtFront(start))
      {
        if (!isAngleAtBack(end))
        {
          if (extent > -180.0D)
          {
            if (drawFront) {
              Area side = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), arc.getStartPoint().getX() - arc.getEndPoint().getX(), plotArea.getHeight()));
              



              side.intersect(front);
              g2.setPaint(paint);
              g2.fill(side);
              g2.setPaint(outlinePaint);
              g2.draw(side);
            }
            
          }
          else
          {
            Area side1 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getStartPoint().getX() - plotArea.getX(), plotArea.getHeight()));
            


            side1.intersect(front);
            
            Area side2 = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getEndPoint().getX(), plotArea.getHeight()));
            



            side2.intersect(front);
            g2.setPaint(paint);
            if (drawFront) {
              g2.fill(side1);
              g2.fill(side2);
            }
            
            if (drawBack) {
              g2.fill(back);
            }
            
            g2.setPaint(outlinePaint);
            if (drawFront) {
              g2.draw(side1);
              g2.draw(side2);
            }
            
            if (drawBack) {
              g2.draw(back);
            }
            
          }
          
        }
        else
        {
          if (drawBack) {
            Area side2 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getEndPoint().getX() - plotArea.getX(), plotArea.getHeight()));
            


            side2.intersect(back);
            g2.setPaint(paint);
            g2.fill(side2);
            g2.setPaint(outlinePaint);
            g2.draw(side2);
          }
          
          if (drawFront) {
            Area side1 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getStartPoint().getX() - plotArea.getX(), plotArea.getHeight()));
            


            side1.intersect(front);
            g2.setPaint(paint);
            g2.fill(side1);
            g2.setPaint(outlinePaint);
            g2.draw(side1);
          }
          
        }
        

      }
      else if (!isAngleAtFront(end)) {
        if (extent > -180.0D) {
          if (drawBack) {
            Area side = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), arc.getEndPoint().getX() - arc.getStartPoint().getX(), plotArea.getHeight()));
            



            side.intersect(back);
            g2.setPaint(paint);
            g2.fill(side);
            g2.setPaint(outlinePaint);
            g2.draw(side);
          }
        }
        else
        {
          Area side1 = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getStartPoint().getX(), plotArea.getHeight()));
          


          side1.intersect(back);
          
          Area side2 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getEndPoint().getX() - plotArea.getX(), plotArea.getHeight()));
          



          side2.intersect(back);
          
          g2.setPaint(paint);
          if (drawBack) {
            g2.fill(side1);
            g2.fill(side2);
          }
          
          if (drawFront) {
            g2.fill(front);
          }
          
          g2.setPaint(outlinePaint);
          if (drawBack) {
            g2.draw(side1);
            g2.draw(side2);
          }
          
          if (drawFront) {
            g2.draw(front);
          }
          
        }
      }
      else
      {
        if (drawBack) {
          Area side1 = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getStartPoint().getX(), plotArea.getHeight()));
          


          side1.intersect(back);
          g2.setPaint(paint);
          g2.fill(side1);
          g2.setPaint(outlinePaint);
          g2.draw(side1);
        }
        
        if (drawFront) {
          Area side2 = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getEndPoint().getX(), plotArea.getHeight()));
          


          side2.intersect(front);
          g2.setPaint(paint);
          g2.fill(side2);
          g2.setPaint(outlinePaint);
          g2.draw(side2);
        }
        
      }
      
    }
    else if (extent > 0.0D)
    {
      if (isAngleAtFront(start))
      {
        if (!isAngleAtBack(end))
        {
          if (extent < 180.0D) {
            if (drawFront) {
              Area side = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), arc.getEndPoint().getX() - arc.getStartPoint().getX(), plotArea.getHeight()));
              



              side.intersect(front);
              g2.setPaint(paint);
              g2.fill(side);
              g2.setPaint(outlinePaint);
              g2.draw(side);
            }
          }
          else {
            Area side1 = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getStartPoint().getX(), plotArea.getHeight()));
            


            side1.intersect(front);
            
            Area side2 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getEndPoint().getX() - plotArea.getX(), plotArea.getHeight()));
            


            side2.intersect(front);
            
            g2.setPaint(paint);
            if (drawFront) {
              g2.fill(side1);
              g2.fill(side2);
            }
            
            if (drawBack) {
              g2.fill(back);
            }
            
            g2.setPaint(outlinePaint);
            if (drawFront) {
              g2.draw(side1);
              g2.draw(side2);
            }
            
            if (drawBack) {
              g2.draw(back);
            }
          }
        }
        else
        {
          if (drawBack) {
            Area side2 = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getEndPoint().getX(), plotArea.getHeight()));
            


            side2.intersect(back);
            g2.setPaint(paint);
            g2.fill(side2);
            g2.setPaint(outlinePaint);
            g2.draw(side2);
          }
          
          if (drawFront) {
            Area side1 = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getStartPoint().getX(), plotArea.getHeight()));
            


            side1.intersect(front);
            g2.setPaint(paint);
            g2.fill(side1);
            g2.setPaint(outlinePaint);
            g2.draw(side1);
          }
          
        }
        
      }
      else if (!isAngleAtFront(end)) {
        if (extent < 180.0D) {
          if (drawBack) {
            Area side = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), arc.getStartPoint().getX() - arc.getEndPoint().getX(), plotArea.getHeight()));
            



            side.intersect(back);
            g2.setPaint(paint);
            g2.fill(side);
            g2.setPaint(outlinePaint);
            g2.draw(side);
          }
        }
        else
        {
          Area side1 = new Area(new Rectangle2D.Double(arc.getStartPoint().getX(), plotArea.getY(), plotArea.getX() - arc.getStartPoint().getX(), plotArea.getHeight()));
          


          side1.intersect(back);
          
          Area side2 = new Area(new Rectangle2D.Double(arc.getEndPoint().getX(), plotArea.getY(), plotArea.getMaxX() - arc.getEndPoint().getX(), plotArea.getHeight()));
          


          side2.intersect(back);
          
          g2.setPaint(paint);
          if (drawBack) {
            g2.fill(side1);
            g2.fill(side2);
          }
          
          if (drawFront) {
            g2.fill(front);
          }
          
          g2.setPaint(outlinePaint);
          if (drawBack) {
            g2.draw(side1);
            g2.draw(side2);
          }
          
          if (drawFront) {
            g2.draw(front);
          }
          
        }
      }
      else
      {
        if (drawBack) {
          Area side1 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getStartPoint().getX() - plotArea.getX(), plotArea.getHeight()));
          


          side1.intersect(back);
          g2.setPaint(paint);
          g2.fill(side1);
          g2.setPaint(outlinePaint);
          g2.draw(side1);
        }
        
        if (drawFront) {
          Area side2 = new Area(new Rectangle2D.Double(plotArea.getX(), plotArea.getY(), arc.getEndPoint().getX() - plotArea.getX(), plotArea.getHeight()));
          


          side2.intersect(front);
          g2.setPaint(paint);
          g2.fill(side2);
          g2.setPaint(outlinePaint);
          g2.draw(side2);
        }
      }
    }
  }
  







  public String getPlotType()
  {
    return localizationResources.getString("Pie_3D_Plot");
  }
  








  private boolean isAngleAtFront(double angle)
  {
    return Math.sin(Math.toRadians(angle)) < 0.0D;
  }
  








  private boolean isAngleAtBack(double angle)
  {
    return Math.sin(Math.toRadians(angle)) > 0.0D;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof PiePlot3D)) {
      return false;
    }
    PiePlot3D that = (PiePlot3D)obj;
    if (depthFactor != depthFactor) {
      return false;
    }
    if (darkerSides != darkerSides) {
      return false;
    }
    return super.equals(obj);
  }
}
