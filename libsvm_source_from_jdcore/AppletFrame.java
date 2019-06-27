import java.applet.Applet;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class AppletFrame
  extends Frame
{
  AppletFrame(String paramString, Applet paramApplet, int paramInt1, int paramInt2)
  {
    super(paramString);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent paramAnonymousWindowEvent) {
        System.exit(0);
      }
    });
    paramApplet.init();
    paramApplet.setSize(paramInt1, paramInt2);
    paramApplet.start();
    add(paramApplet);
    pack();
    setVisible(true);
  }
}
