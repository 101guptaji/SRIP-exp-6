package org.jfree.chart.plot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
















































public abstract class AbstractPieLabelDistributor
  implements Serializable
{
  protected List labels;
  
  public AbstractPieLabelDistributor()
  {
    labels = new ArrayList();
  }
  






  public PieLabelRecord getPieLabelRecord(int index)
  {
    return (PieLabelRecord)labels.get(index);
  }
  




  public void addPieLabelRecord(PieLabelRecord record)
  {
    if (record == null) {
      throw new IllegalArgumentException("Null 'record' argument.");
    }
    labels.add(record);
  }
  




  public int getItemCount()
  {
    return labels.size();
  }
  


  public void clear()
  {
    labels.clear();
  }
  
  public abstract void distributeLabels(double paramDouble1, double paramDouble2);
}
