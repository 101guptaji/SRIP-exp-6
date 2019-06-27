package org.jfree.chart.plot;

import java.util.HashMap;
import java.util.Map;















































public class PlotState
{
  private Map sharedAxisStates;
  
  public PlotState()
  {
    sharedAxisStates = new HashMap();
  }
  




  public Map getSharedAxisStates()
  {
    return sharedAxisStates;
  }
}
