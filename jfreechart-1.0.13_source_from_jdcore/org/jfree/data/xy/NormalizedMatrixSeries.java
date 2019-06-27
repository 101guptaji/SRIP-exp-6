package org.jfree.data.xy;



























public class NormalizedMatrixSeries
  extends MatrixSeries
{
  public static final double DEFAULT_SCALE_FACTOR = 1.0D;
  

























  private double m_scaleFactor = 1.0D;
  



  private double m_totalSum;
  




  public NormalizedMatrixSeries(String name, int rows, int columns)
  {
    super(name, rows, columns);
    






    m_totalSum = Double.MIN_VALUE;
  }
  








  public Number getItem(int itemIndex)
  {
    int i = getItemRow(itemIndex);
    int j = getItemColumn(itemIndex);
    
    double mij = get(i, j) * m_scaleFactor;
    Number n = new Double(mij / m_totalSum);
    
    return n;
  }
  







  public void setScaleFactor(double factor)
  {
    m_scaleFactor = factor;
  }
  







  public double getScaleFactor()
  {
    return m_scaleFactor;
  }
  









  public void update(int i, int j, double mij)
  {
    m_totalSum -= get(i, j);
    m_totalSum += mij;
    
    super.update(i, j, mij);
  }
  


  public void zeroAll()
  {
    m_totalSum = 0.0D;
    super.zeroAll();
  }
}
