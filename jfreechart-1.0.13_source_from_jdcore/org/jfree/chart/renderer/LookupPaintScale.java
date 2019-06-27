package org.jfree.chart.renderer;

import java.awt.Color;
import java.awt.Paint;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;

























































public class LookupPaintScale
  implements PaintScale, PublicCloneable, Serializable
{
  static final long serialVersionUID = -5239384246251042006L;
  private double lowerBound;
  private double upperBound;
  private transient Paint defaultPaint;
  private List lookupTable;
  
  static class PaintItem
    implements Comparable, Serializable
  {
    static final long serialVersionUID = 698920578512361570L;
    double value;
    transient Paint paint;
    
    public PaintItem(double value, Paint paint)
    {
      this.value = value;
      this.paint = paint;
    }
    


    public int compareTo(Object obj)
    {
      PaintItem that = (PaintItem)obj;
      double d1 = value;
      double d2 = value;
      if (d1 > d2) {
        return 1;
      }
      if (d1 < d2) {
        return -1;
      }
      return 0;
    }
    






    public boolean equals(Object obj)
    {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof PaintItem)) {
        return false;
      }
      PaintItem that = (PaintItem)obj;
      if (value != value) {
        return false;
      }
      if (!PaintUtilities.equal(paint, paint)) {
        return false;
      }
      return true;
    }
    





    private void writeObject(ObjectOutputStream stream)
      throws IOException
    {
      stream.defaultWriteObject();
      SerialUtilities.writePaint(paint, stream);
    }
    







    private void readObject(ObjectInputStream stream)
      throws IOException, ClassNotFoundException
    {
      stream.defaultReadObject();
      paint = SerialUtilities.readPaint(stream);
    }
  }
  


















  public LookupPaintScale()
  {
    this(0.0D, 1.0D, Color.lightGray);
  }
  








  public LookupPaintScale(double lowerBound, double upperBound, Paint defaultPaint)
  {
    if (lowerBound >= upperBound) {
      throw new IllegalArgumentException("Requires lowerBound < upperBound.");
    }
    
    if (defaultPaint == null) {
      throw new IllegalArgumentException("Null 'paint' argument.");
    }
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
    this.defaultPaint = defaultPaint;
    lookupTable = new ArrayList();
  }
  




  public Paint getDefaultPaint()
  {
    return defaultPaint;
  }
  






  public double getLowerBound()
  {
    return lowerBound;
  }
  






  public double getUpperBound()
  {
    return upperBound;
  }
  






  /**
   * @deprecated
   */
  public void add(Number value, Paint paint)
  {
    add(value.doubleValue(), paint);
  }
  









  public void add(double value, Paint paint)
  {
    PaintItem item = new PaintItem(value, paint);
    int index = Collections.binarySearch(lookupTable, item);
    if (index >= 0) {
      lookupTable.set(index, item);
    }
    else {
      lookupTable.add(-(index + 1), item);
    }
  }
  










  public Paint getPaint(double value)
  {
    if (value < lowerBound) {
      return defaultPaint;
    }
    if (value > upperBound) {
      return defaultPaint;
    }
    
    int count = lookupTable.size();
    if (count == 0) {
      return defaultPaint;
    }
    

    PaintItem item = (PaintItem)lookupTable.get(0);
    if (value < value) {
      return defaultPaint;
    }
    

    int low = 0;
    int high = lookupTable.size() - 1;
    while (high - low > 1) {
      int current = (low + high) / 2;
      item = (PaintItem)lookupTable.get(current);
      if (value >= value) {
        low = current;
      }
      else {
        high = current;
      }
    }
    if (high > low) {
      item = (PaintItem)lookupTable.get(high);
      if (value < value) {
        item = (PaintItem)lookupTable.get(low);
      }
    }
    return item != null ? paint : defaultPaint;
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof LookupPaintScale)) {
      return false;
    }
    LookupPaintScale that = (LookupPaintScale)obj;
    if (lowerBound != lowerBound) {
      return false;
    }
    if (upperBound != upperBound) {
      return false;
    }
    if (!PaintUtilities.equal(defaultPaint, defaultPaint)) {
      return false;
    }
    if (!lookupTable.equals(lookupTable)) {
      return false;
    }
    return true;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    LookupPaintScale clone = (LookupPaintScale)super.clone();
    lookupTable = new ArrayList(lookupTable);
    return clone;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    SerialUtilities.writePaint(defaultPaint, stream);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    defaultPaint = SerialUtilities.readPaint(stream);
  }
}
