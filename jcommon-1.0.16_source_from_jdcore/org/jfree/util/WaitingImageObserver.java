package org.jfree.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.Serializable;






































































public class WaitingImageObserver
  implements ImageObserver, Serializable, Cloneable
{
  static final long serialVersionUID = -807204410581383550L;
  private boolean lock;
  private Image image;
  private boolean error;
  
  public WaitingImageObserver(Image image)
  {
    if (image == null) {
      throw new NullPointerException();
    }
    this.image = image;
    lock = true;
  }
  
























  public synchronized boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
  {
    if ((infoflags & 0x20) == 32) {
      lock = false;
      error = false;
      notifyAll();
      return false;
    }
    if (((infoflags & 0x80) == 128) || ((infoflags & 0x40) == 64))
    {
      lock = false;
      error = true;
      notifyAll();
      return false;
    }
    
    return true;
  }
  




  public synchronized void waitImageLoaded()
  {
    if (!lock)
    {
      return;
    }
    
    BufferedImage img = new BufferedImage(1, 1, 1);
    

    Graphics g = img.getGraphics();
    
    while (lock) {
      if (g.drawImage(image, 0, 0, img.getWidth(this), img.getHeight(this), this))
      {
        return;
      }
      try
      {
        wait(500L);
      }
      catch (InterruptedException e) {
        Log.info("WaitingImageObserver.waitImageLoaded(): InterruptedException thrown", e);
      }
    }
  }
  






  /**
   * @deprecated
   */
  public Object clone()
    throws CloneNotSupportedException
  {
    return (WaitingImageObserver)super.clone();
  }
  





  public boolean isLoadingComplete()
  {
    return !lock;
  }
  




  public boolean isError()
  {
    return error;
  }
}
