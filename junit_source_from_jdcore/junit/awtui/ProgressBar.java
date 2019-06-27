package junit.awtui;

import java.awt.Rectangle;

public class ProgressBar extends java.awt.Canvas {
  public boolean fError = false;
  public int fTotal = 0;
  public int fProgress = 0;
  public int fProgressX = 0;
  
  public ProgressBar()
  {
    setSize(20, 30);
  }
  
  private java.awt.Color getStatusColor() {
    if (fError)
      return java.awt.Color.red;
    return java.awt.Color.green;
  }
  
  public void paint(java.awt.Graphics g) {
    paintBackground(g);
    paintStatus(g);
  }
  
  public void paintBackground(java.awt.Graphics g) {
    g.setColor(java.awt.SystemColor.control);
    Rectangle r = getBounds();
    g.fillRect(0, 0, width, height);
    g.setColor(java.awt.Color.darkGray);
    g.drawLine(0, 0, width - 1, 0);
    g.drawLine(0, 0, 0, height - 1);
    g.setColor(java.awt.Color.white);
    g.drawLine(width - 1, 0, width - 1, height - 1);
    g.drawLine(0, height - 1, width - 1, height - 1);
  }
  
  public void paintStatus(java.awt.Graphics g) {
    g.setColor(getStatusColor());
    Rectangle r = new Rectangle(0, 0, fProgressX, getBoundsheight);
    g.fillRect(1, 1, width - 1, height - 2);
  }
  
  private void paintStep(int startX, int endX) {
    repaint(startX, 1, endX - startX, getBoundsheight - 2);
  }
  
  public void reset() {
    fProgressX = 1;
    fProgress = 0;
    fError = false;
    paint(getGraphics());
  }
  
  public int scale(int value) {
    if (fTotal > 0)
      return Math.max(1, value * (getBoundswidth - 1) / fTotal);
    return value;
  }
  
  public void setBounds(int x, int y, int w, int h) {
    super.setBounds(x, y, w, h);
    fProgressX = scale(fProgress);
  }
  
  public void start(int total) {
    fTotal = total;
    reset();
  }
  
  public void step(boolean successful) {
    fProgress += 1;
    int x = fProgressX;
    
    fProgressX = scale(fProgress);
    
    if ((!fError) && (!successful)) {
      fError = true;
      x = 1;
    }
    paintStep(x, fProgressX);
  }
}
