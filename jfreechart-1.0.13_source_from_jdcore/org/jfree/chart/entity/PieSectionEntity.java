package org.jfree.chart.entity;

import java.awt.Shape;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.data.general.PieDataset;
import org.jfree.util.ObjectUtilities;


















































































public class PieSectionEntity
  extends ChartEntity
  implements Serializable
{
  private static final long serialVersionUID = 9199892576531984162L;
  private PieDataset dataset;
  private int pieIndex;
  private int sectionIndex;
  private Comparable sectionKey;
  
  public PieSectionEntity(Shape area, PieDataset dataset, int pieIndex, int sectionIndex, Comparable sectionKey, String toolTipText, String urlText)
  {
    super(area, toolTipText, urlText);
    this.dataset = dataset;
    this.pieIndex = pieIndex;
    this.sectionIndex = sectionIndex;
    this.sectionKey = sectionKey;
  }
  







  public PieDataset getDataset()
  {
    return dataset;
  }
  






  public void setDataset(PieDataset dataset)
  {
    this.dataset = dataset;
  }
  








  public int getPieIndex()
  {
    return pieIndex;
  }
  






  public void setPieIndex(int index)
  {
    pieIndex = index;
  }
  






  public int getSectionIndex()
  {
    return sectionIndex;
  }
  






  public void setSectionIndex(int index)
  {
    sectionIndex = index;
  }
  






  public Comparable getSectionKey()
  {
    return sectionKey;
  }
  






  public void setSectionKey(Comparable key)
  {
    sectionKey = key;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof PieSectionEntity)) {
      return false;
    }
    PieSectionEntity that = (PieSectionEntity)obj;
    if (!ObjectUtilities.equal(dataset, dataset)) {
      return false;
    }
    if (pieIndex != pieIndex) {
      return false;
    }
    if (sectionIndex != sectionIndex) {
      return false;
    }
    if (!ObjectUtilities.equal(sectionKey, sectionKey)) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = super.hashCode();
    result = HashUtilities.hashCode(result, pieIndex);
    result = HashUtilities.hashCode(result, sectionIndex);
    return result;
  }
  




  public String toString()
  {
    return "PieSection: " + pieIndex + ", " + sectionIndex + "(" + sectionKey.toString() + ")";
  }
}
