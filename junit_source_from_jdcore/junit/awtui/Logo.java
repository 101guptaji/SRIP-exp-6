package junit.awtui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;

public class Logo extends java.awt.Canvas
{
  private Image fImage;
  private int fWidth;
  private int fHeight;
  
  public Logo()
  {
    fImage = loadImage("logo.gif");
    MediaTracker tracker = new MediaTracker(this);
    tracker.addImage(fImage, 0);
    try {
      tracker.waitForAll();
    }
    catch (Exception localException) {}
    
    if (fImage != null) {
      fWidth = fImage.getWidth(this);
      fHeight = fImage.getHeight(this);
    } else {
      fWidth = 20;
      fHeight = 20;
    }
    setSize(fWidth, fHeight);
  }
  
  public Image loadImage(String name) {
    java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
    try {
      java.net.URL url = junit.runner.BaseTestRunner.class.getResource(name);
      return toolkit.createImage((java.awt.image.ImageProducer)url.getContent());
    }
    catch (Exception localException) {}
    return null;
  }
  
  public void paint(Graphics g) {
    paintBackground(g);
    if (fImage != null)
      g.drawImage(fImage, 0, 0, fWidth, fHeight, this);
  }
  
  public void paintBackground(Graphics g) {
    g.setColor(java.awt.SystemColor.control);
    g.fillRect(0, 0, getBoundswidth, getBoundsheight);
  }
}
