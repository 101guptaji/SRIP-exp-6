package org.jfree.data.contour;

import org.jfree.data.Range;





































































/**
 * @deprecated
 */
public class NonGridContourDataset
  extends DefaultContourDataset
{
  static final int DEFAULT_NUM_X = 50;
  static final int DEFAULT_NUM_Y = 50;
  static final int DEFAULT_POWER = 4;
  
  public NonGridContourDataset() {}
  
  public NonGridContourDataset(String seriesName, Object[] xData, Object[] yData, Object[] zData)
  {
    super(seriesName, xData, yData, zData);
    buildGrid(50, 50, 4);
  }
  













  public NonGridContourDataset(String seriesName, Object[] xData, Object[] yData, Object[] zData, int numX, int numY, int power)
  {
    super(seriesName, xData, yData, zData);
    buildGrid(numX, numY, power);
  }
  











  protected void buildGrid(int numX, int numY, int power)
  {
    int numValues = numX * numY;
    double[] xGrid = new double[numValues];
    double[] yGrid = new double[numValues];
    double[] zGrid = new double[numValues];
    

    double xMin = 1.0E20D;
    for (int k = 0; k < xValues.length; k++) {
      xMin = Math.min(xMin, xValues[k].doubleValue());
    }
    
    double xMax = -1.0E20D;
    for (int k = 0; k < xValues.length; k++) {
      xMax = Math.max(xMax, xValues[k].doubleValue());
    }
    
    double yMin = 1.0E20D;
    for (int k = 0; k < yValues.length; k++) {
      yMin = Math.min(yMin, yValues[k].doubleValue());
    }
    
    double yMax = -1.0E20D;
    for (int k = 0; k < yValues.length; k++) {
      yMax = Math.max(yMax, yValues[k].doubleValue());
    }
    
    Range xRange = new Range(xMin, xMax);
    Range yRange = new Range(yMin, yMax);
    
    xRange.getLength();
    yRange.getLength();
    

    double dxGrid = xRange.getLength() / (numX - 1);
    double dyGrid = yRange.getLength() / (numY - 1);
    

    double x = 0.0D;
    for (int i = 0; i < numX; i++) {
      if (i == 0) {
        x = xMin;
      }
      else {
        x += dxGrid;
      }
      double y = 0.0D;
      for (int j = 0; j < numY; j++) {
        int k = numY * i + j;
        xGrid[k] = x;
        if (j == 0) {
          y = yMin;
        }
        else {
          y += dyGrid;
        }
        yGrid[k] = y;
      }
    }
    

    for (int kGrid = 0; kGrid < xGrid.length; kGrid++) {
      double dTotal = 0.0D;
      zGrid[kGrid] = 0.0D;
      for (int k = 0; k < xValues.length; k++) {
        double xPt = xValues[k].doubleValue();
        double yPt = yValues[k].doubleValue();
        double d = distance(xPt, yPt, xGrid[kGrid], yGrid[kGrid]);
        if (power != 1) {
          d = Math.pow(d, power);
        }
        d = Math.sqrt(d);
        if (d > 0.0D) {
          d = 1.0D / d;
        }
        else
        {
          d = 1.0E20D;
        }
        if (zValues[k] != null)
        {
          zGrid[kGrid] += zValues[k].doubleValue() * d;
        }
        dTotal += d;
      }
      zGrid[kGrid] /= dTotal;
    }
    

    initialize(formObjectArray(xGrid), formObjectArray(yGrid), formObjectArray(zGrid));
  }
  
















  protected double distance(double xDataPt, double yDataPt, double xGrdPt, double yGrdPt)
  {
    double dx = xDataPt - xGrdPt;
    double dy = yDataPt - yGrdPt;
    return Math.sqrt(dx * dx + dy * dy);
  }
}
