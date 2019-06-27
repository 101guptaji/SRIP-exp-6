package org.jfree.chart.title;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.block.Arrangement;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BlockResult;
import org.jfree.chart.block.EntityBlockParams;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.LegendItemEntity;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.general.Dataset;










































































public class LegendItemBlockContainer
  extends BlockContainer
{
  private Dataset dataset;
  private Comparable seriesKey;
  private int datasetIndex;
  private int series;
  private String toolTipText;
  private String urlText;
  
  /**
   * @deprecated
   */
  public LegendItemBlockContainer(Arrangement arrangement, int datasetIndex, int series)
  {
    super(arrangement);
    this.datasetIndex = datasetIndex;
    this.series = series;
  }
  









  public LegendItemBlockContainer(Arrangement arrangement, Dataset dataset, Comparable seriesKey)
  {
    super(arrangement);
    this.dataset = dataset;
    this.seriesKey = seriesKey;
  }
  






  public Dataset getDataset()
  {
    return dataset;
  }
  






  public Comparable getSeriesKey()
  {
    return seriesKey;
  }
  



  /**
   * @deprecated
   */
  public int getDatasetIndex()
  {
    return datasetIndex;
  }
  




  public int getSeriesIndex()
  {
    return series;
  }
  






  public String getToolTipText()
  {
    return toolTipText;
  }
  






  public void setToolTipText(String text)
  {
    toolTipText = text;
  }
  






  public String getURLText()
  {
    return urlText;
  }
  






  public void setURLText(String text)
  {
    urlText = text;
  }
  










  public Object draw(Graphics2D g2, Rectangle2D area, Object params)
  {
    super.draw(g2, area, null);
    EntityBlockParams ebp = null;
    BlockResult r = new BlockResult();
    if ((params instanceof EntityBlockParams)) {
      ebp = (EntityBlockParams)params;
      if (ebp.getGenerateEntities()) {
        EntityCollection ec = new StandardEntityCollection();
        LegendItemEntity entity = new LegendItemEntity((Shape)area.clone());
        
        entity.setSeriesIndex(series);
        entity.setSeriesKey(seriesKey);
        entity.setDataset(dataset);
        entity.setToolTipText(getToolTipText());
        entity.setURLText(getURLText());
        ec.add(entity);
        r.setEntityCollection(ec);
      }
    }
    return r;
  }
}
