package org.jfree.util;

import java.io.ObjectStreamException;
import java.io.Serializable;



















































public final class Rotation
  implements Serializable
{
  private static final long serialVersionUID = -4662815260201591676L;
  public static final Rotation CLOCKWISE = new Rotation("Rotation.CLOCKWISE", -1.0D);
  


  public static final Rotation ANTICLOCKWISE = new Rotation("Rotation.ANTICLOCKWISE", 1.0D);
  




  private String name;
  



  private double factor;
  




  private Rotation(String name, double factor)
  {
    this.name = name;
    this.factor = factor;
  }
  




  public String toString()
  {
    return name;
  }
  





  public double getFactor()
  {
    return factor;
  }
  







  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Rotation)) {
      return false;
    }
    
    Rotation rotation = (Rotation)o;
    
    if (factor != factor) {
      return false;
    }
    
    return true;
  }
  




  public int hashCode()
  {
    long temp = Double.doubleToLongBits(factor);
    return (int)(temp ^ temp >>> 32);
  }
  





  private Object readResolve()
    throws ObjectStreamException
  {
    if (equals(CLOCKWISE)) {
      return CLOCKWISE;
    }
    if (equals(ANTICLOCKWISE)) {
      return ANTICLOCKWISE;
    }
    return null;
  }
}
