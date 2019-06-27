package org.jfree.data.statistics;

import java.util.List;
import org.jfree.data.xy.XYDataset;

public abstract interface BoxAndWhiskerXYDataset
  extends XYDataset
{
  public abstract Number getMeanValue(int paramInt1, int paramInt2);
  
  public abstract Number getMedianValue(int paramInt1, int paramInt2);
  
  public abstract Number getQ1Value(int paramInt1, int paramInt2);
  
  public abstract Number getQ3Value(int paramInt1, int paramInt2);
  
  public abstract Number getMinRegularValue(int paramInt1, int paramInt2);
  
  public abstract Number getMaxRegularValue(int paramInt1, int paramInt2);
  
  public abstract Number getMinOutlier(int paramInt1, int paramInt2);
  
  public abstract Number getMaxOutlier(int paramInt1, int paramInt2);
  
  public abstract List getOutliers(int paramInt1, int paramInt2);
  
  public abstract double getOutlierCoefficient();
  
  public abstract double getFaroutCoefficient();
}
