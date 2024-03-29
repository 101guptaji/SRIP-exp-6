package Jama.util;

public class Maths
{
  public Maths() {}
  
  public static double hypot(double paramDouble1, double paramDouble2) {
    double d;
    if (Math.abs(paramDouble1) > Math.abs(paramDouble2)) {
      d = paramDouble2 / paramDouble1;
      d = Math.abs(paramDouble1) * Math.sqrt(1.0D + d * d);
    } else if (paramDouble2 != 0.0D) {
      d = paramDouble1 / paramDouble2;
      d = Math.abs(paramDouble2) * Math.sqrt(1.0D + d * d);
    } else {
      d = 0.0D;
    }
    return d;
  }
}
