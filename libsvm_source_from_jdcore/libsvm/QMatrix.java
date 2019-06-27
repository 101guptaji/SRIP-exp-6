package libsvm;

abstract class QMatrix
{
  QMatrix() {}
  
  abstract float[] get_Q(int paramInt1, int paramInt2);
  
  abstract double[] get_QD();
  
  abstract void swap_index(int paramInt1, int paramInt2);
}
