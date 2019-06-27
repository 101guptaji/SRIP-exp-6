package org.jfree.chart.block;

















public class BlockParams
  implements EntityBlockParams
{
  private boolean generateEntities;
  














  private double translateX;
  














  private double translateY;
  















  public BlockParams()
  {
    translateX = 0.0D;
    translateY = 0.0D;
    generateEntities = false;
  }
  





  public boolean getGenerateEntities()
  {
    return generateEntities;
  }
  




  public void setGenerateEntities(boolean generate)
  {
    generateEntities = generate;
  }
  





  public double getTranslateX()
  {
    return translateX;
  }
  





  public void setTranslateX(double x)
  {
    translateX = x;
  }
  





  public double getTranslateY()
  {
    return translateY;
  }
  





  public void setTranslateY(double y)
  {
    translateY = y;
  }
}
