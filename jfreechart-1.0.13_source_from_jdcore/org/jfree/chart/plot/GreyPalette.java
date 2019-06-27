package org.jfree.chart.plot;

import java.io.Serializable;





















































/**
 * @deprecated
 */
public class GreyPalette
  extends ColorPalette
  implements Serializable
{
  private static final long serialVersionUID = -2120941170159987395L;
  
  public GreyPalette()
  {
    initialize();
  }
  



  public void initialize()
  {
    setPaletteName("Grey");
    
    r = new int['Ā'];
    g = new int['Ā'];
    b = new int['Ā'];
    
    r[0] = 255;
    g[0] = 255;
    b[0] = 255;
    r[1] = 0;
    g[1] = 0;
    b[1] = 0;
    
    for (int i = 2; i < 256; i++) {
      r[i] = i;
      g[i] = i;
      b[i] = i;
    }
  }
}
