package org.jfree.chart.plot;

import java.awt.geom.Point2D;




























































public class CategoryCrosshairState
  extends CrosshairState
{
  private Comparable rowKey;
  private Comparable columnKey;
  
  public CategoryCrosshairState()
  {
    rowKey = null;
    columnKey = null;
  }
  




  public Comparable getRowKey()
  {
    return rowKey;
  }
  




  public void setRowKey(Comparable key)
  {
    rowKey = key;
  }
  




  public Comparable getColumnKey()
  {
    return columnKey;
  }
  




  public void setColumnKey(Comparable key)
  {
    columnKey = key;
  }
  














  public void updateCrosshairPoint(Comparable rowKey, Comparable columnKey, double value, int datasetIndex, double transX, double transY, PlotOrientation orientation)
  {
    Point2D anchor = getAnchor();
    if (anchor != null) {
      double xx = anchor.getX();
      double yy = anchor.getY();
      if (orientation == PlotOrientation.HORIZONTAL) {
        double temp = yy;
        yy = xx;
        xx = temp;
      }
      double d = (transX - xx) * (transX - xx) + (transY - yy) * (transY - yy);
      

      if (d < getCrosshairDistance()) {
        this.rowKey = rowKey;
        this.columnKey = columnKey;
        setCrosshairY(value);
        setDatasetIndex(datasetIndex);
        setCrosshairDistance(d);
      }
    }
  }
  












  public void updateCrosshairX(Comparable rowKey, Comparable columnKey, int datasetIndex, double transX, PlotOrientation orientation)
  {
    Point2D anchor = getAnchor();
    if (anchor != null) {
      double anchorX = anchor.getX();
      if (orientation == PlotOrientation.HORIZONTAL) {
        anchorX = anchor.getY();
      }
      double d = Math.abs(transX - anchorX);
      if (d < getCrosshairDistance()) {
        this.rowKey = rowKey;
        this.columnKey = columnKey;
        setDatasetIndex(datasetIndex);
        setCrosshairDistance(d);
      }
    }
  }
}
