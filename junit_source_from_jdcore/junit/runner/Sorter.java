package junit.runner;

import java.util.Vector;








public class Sorter
{
  public Sorter() {}
  
  public static void sortStrings(Vector values, int left, int right, Swapper swapper)
  {
    int oleft = left;
    int oright = right;
    String mid = (String)values.elementAt((left + right) / 2);
    do {
      while (((String)values.elementAt(left)).compareTo(mid) < 0)
        left++;
      while (mid.compareTo((String)values.elementAt(right)) < 0)
        right--;
      if (left <= right) {
        swapper.swap(values, left, right);
        left++;
        right--;
      }
    } while (left <= right);
    
    if (oleft < right)
      sortStrings(values, oleft, right, swapper);
    if (left < oright) {
      sortStrings(values, left, oright, swapper);
    }
  }
  
  public static abstract interface Swapper
  {
    public abstract void swap(Vector paramVector, int paramInt1, int paramInt2);
  }
}
