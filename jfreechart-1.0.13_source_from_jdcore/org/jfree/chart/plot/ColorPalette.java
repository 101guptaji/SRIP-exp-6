package org.jfree.chart.plot;

import java.awt.Color;
import java.awt.Paint;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import org.jfree.chart.axis.ValueTick;




















































/**
 * @deprecated
 */
public abstract class ColorPalette
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -9029901853079622051L;
  protected double minZ = -1.0D;
  

  protected double maxZ = -1.0D;
  

  protected int[] r;
  

  protected int[] g;
  

  protected int[] b;
  

  protected double[] tickValues = null;
  

  protected boolean logscale = false;
  

  protected boolean inverse = false;
  

  protected String paletteName = null;
  

  protected boolean stepped = false;
  

  protected static final double log10 = Math.log(10.0D);
  






  public ColorPalette() {}
  





  public Paint getColor(double value)
  {
    int izV = (int)(253.0D * (value - minZ) / (maxZ - minZ)) + 2;
    
    return new Color(r[izV], g[izV], b[izV]);
  }
  






  public Color getColor(int izV)
  {
    return new Color(r[izV], g[izV], b[izV]);
  }
  






  public Color getColorLinear(double value)
  {
    int izV = 0;
    if (stepped) {
      int index = Arrays.binarySearch(tickValues, value);
      if (index < 0) {
        index = -1 * index - 2;
      }
      
      if (index < 0)
      {
        value = minZ;
      }
      else {
        value = tickValues[index];
      }
    }
    izV = (int)(253.0D * (value - minZ) / (maxZ - minZ)) + 2;
    izV = Math.min(izV, 255);
    izV = Math.max(izV, 2);
    return getColor(izV);
  }
  






  public Color getColorLog(double value)
  {
    int izV = 0;
    double minZtmp = minZ;
    double maxZtmp = maxZ;
    if (minZ <= 0.0D)
    {
      maxZ = (maxZtmp - minZtmp + 1.0D);
      minZ = 1.0D;
      value = value - minZtmp + 1.0D;
    }
    double minZlog = Math.log(minZ) / log10;
    double maxZlog = Math.log(maxZ) / log10;
    value = Math.log(value) / log10;
    
    if (stepped) {
      int numSteps = tickValues.length;
      int steps = 256 / (numSteps - 1);
      izV = steps * (int)(numSteps * (value - minZlog) / (maxZlog - minZlog)) + 2;

    }
    else
    {
      izV = (int)(253.0D * (value - minZlog) / (maxZlog - minZlog)) + 2;
    }
    izV = Math.min(izV, 255);
    izV = Math.max(izV, 2);
    
    minZ = minZtmp;
    maxZ = maxZtmp;
    
    return getColor(izV);
  }
  




  public double getMaxZ()
  {
    return maxZ;
  }
  




  public double getMinZ()
  {
    return minZ;
  }
  







  public Paint getPaint(double value)
  {
    if (isLogscale()) {
      return getColorLog(value);
    }
    
    return getColorLinear(value);
  }
  





  public String getPaletteName()
  {
    return paletteName;
  }
  




  public double[] getTickValues()
  {
    return tickValues;
  }
  



  public abstract void initialize();
  



  public void invertPalette()
  {
    int[] red = new int['Ā'];
    int[] green = new int['Ā'];
    int[] blue = new int['Ā'];
    for (int i = 0; i < 256; i++) {
      red[i] = r[i];
      green[i] = g[i];
      blue[i] = b[i];
    }
    
    for (int i = 2; i < 256; i++) {
      r[i] = red[(257 - i)];
      g[i] = green[(257 - i)];
      b[i] = blue[(257 - i)];
    }
  }
  




  public boolean isInverse()
  {
    return inverse;
  }
  




  public boolean isLogscale()
  {
    return logscale;
  }
  




  public boolean isStepped()
  {
    return stepped;
  }
  




  public void setInverse(boolean inverse)
  {
    this.inverse = inverse;
    initialize();
    if (inverse) {
      invertPalette();
    }
  }
  





  public void setLogscale(boolean logscale)
  {
    this.logscale = logscale;
  }
  




  public void setMaxZ(double newMaxZ)
  {
    maxZ = newMaxZ;
  }
  




  public void setMinZ(double newMinZ)
  {
    minZ = newMinZ;
  }
  





  public void setPaletteName(String paletteName)
  {
    this.paletteName = paletteName;
  }
  





  public void setStepped(boolean stepped)
  {
    this.stepped = stepped;
  }
  





  public void setTickValues(double[] newTickValues)
  {
    tickValues = newTickValues;
  }
  




  public void setTickValues(List ticks)
  {
    tickValues = new double[ticks.size()];
    for (int i = 0; i < tickValues.length; i++) {
      tickValues[i] = ((ValueTick)ticks.get(i)).getValue();
    }
  }
  






  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ColorPalette)) {
      return false;
    }
    
    ColorPalette colorPalette = (ColorPalette)o;
    
    if (inverse != inverse) {
      return false;
    }
    if (logscale != logscale) {
      return false;
    }
    if (maxZ != maxZ) {
      return false;
    }
    if (minZ != minZ) {
      return false;
    }
    if (stepped != stepped) {
      return false;
    }
    if (!Arrays.equals(b, b)) {
      return false;
    }
    if (!Arrays.equals(g, g)) {
      return false;
    }
    if (paletteName != null ? !paletteName.equals(paletteName) : paletteName != null)
    {

      return false;
    }
    if (!Arrays.equals(r, r)) {
      return false;
    }
    if (!Arrays.equals(tickValues, tickValues)) {
      return false;
    }
    
    return true;
  }
  






  public int hashCode()
  {
    long temp = Double.doubleToLongBits(minZ);
    int result = (int)(temp ^ temp >>> 32);
    temp = Double.doubleToLongBits(maxZ);
    result = 29 * result + (int)(temp ^ temp >>> 32);
    result = 29 * result + (logscale ? 1 : 0);
    result = 29 * result + (inverse ? 1 : 0);
    result = 29 * result + (paletteName != null ? paletteName.hashCode() : 0);
    
    result = 29 * result + (stepped ? 1 : 0);
    return result;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    ColorPalette clone = (ColorPalette)super.clone();
    return clone;
  }
}
