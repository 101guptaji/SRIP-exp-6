package org.jfree.chart.axis;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import java.util.List;
import org.jfree.chart.Effect3D;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.ui.RectangleEdge;











































































public class NumberAxis3D
  extends NumberAxis
  implements Serializable
{
  private static final long serialVersionUID = -1790205852569123512L;
  
  public NumberAxis3D()
  {
    this(null);
  }
  




  public NumberAxis3D(String label)
  {
    super(label);
  }
  


















  public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, PlotRenderingInfo plotState)
  {
    if (!isVisible()) {
      AxisState state = new AxisState(cursor);
      

      List ticks = refreshTicks(g2, state, dataArea, edge);
      state.setTicks(ticks);
      return state;
    }
    

    double xOffset = 0.0D;
    double yOffset = 0.0D;
    Plot plot = getPlot();
    if ((plot instanceof CategoryPlot)) {
      CategoryPlot cp = (CategoryPlot)plot;
      CategoryItemRenderer r = cp.getRenderer();
      if ((r instanceof Effect3D)) {
        Effect3D e3D = (Effect3D)r;
        xOffset = e3D.getXOffset();
        yOffset = e3D.getYOffset();
      }
    }
    
    double adjustedX = dataArea.getMinX();
    double adjustedY = dataArea.getMinY();
    double adjustedW = dataArea.getWidth() - xOffset;
    double adjustedH = dataArea.getHeight() - yOffset;
    
    if ((edge == RectangleEdge.LEFT) || (edge == RectangleEdge.BOTTOM)) {
      adjustedY += yOffset;
    }
    else if ((edge == RectangleEdge.RIGHT) || (edge == RectangleEdge.TOP)) {
      adjustedX += xOffset;
    }
    Rectangle2D adjustedDataArea = new Rectangle2D.Double(adjustedX, adjustedY, adjustedW, adjustedH);
    


    AxisState info = drawTickMarksAndLabels(g2, cursor, plotArea, adjustedDataArea, edge);
    


    info = drawLabel(getLabel(), g2, plotArea, dataArea, edge, info);
    
    return info;
  }
}
