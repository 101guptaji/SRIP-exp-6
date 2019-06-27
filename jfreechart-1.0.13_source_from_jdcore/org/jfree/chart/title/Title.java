package org.jfree.chart.title;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.event.EventListenerList;
import org.jfree.chart.block.AbstractBlock;
import org.jfree.chart.block.Block;
import org.jfree.chart.event.TitleChangeEvent;
import org.jfree.chart.event.TitleChangeListener;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.VerticalAlignment;
import org.jfree.util.ObjectUtilities;








































































public abstract class Title
  extends AbstractBlock
  implements Block, Cloneable, Serializable
{
  private static final long serialVersionUID = -6675162505277817221L;
  public static final RectangleEdge DEFAULT_POSITION = RectangleEdge.TOP;
  


  public static final HorizontalAlignment DEFAULT_HORIZONTAL_ALIGNMENT = HorizontalAlignment.CENTER;
  


  public static final VerticalAlignment DEFAULT_VERTICAL_ALIGNMENT = VerticalAlignment.CENTER;
  

  public static final RectangleInsets DEFAULT_PADDING = new RectangleInsets(1.0D, 1.0D, 1.0D, 1.0D);
  


  public boolean visible;
  


  private RectangleEdge position;
  


  private HorizontalAlignment horizontalAlignment;
  


  private VerticalAlignment verticalAlignment;
  


  private transient EventListenerList listenerList;
  


  private boolean notify;
  



  protected Title()
  {
    this(DEFAULT_POSITION, DEFAULT_HORIZONTAL_ALIGNMENT, DEFAULT_VERTICAL_ALIGNMENT, DEFAULT_PADDING);
  }
  














  protected Title(RectangleEdge position, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment)
  {
    this(position, horizontalAlignment, verticalAlignment, DEFAULT_PADDING);
  }
  




















  protected Title(RectangleEdge position, HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment, RectangleInsets padding)
  {
    if (position == null) {
      throw new IllegalArgumentException("Null 'position' argument.");
    }
    if (horizontalAlignment == null) {
      throw new IllegalArgumentException("Null 'horizontalAlignment' argument.");
    }
    

    if (verticalAlignment == null) {
      throw new IllegalArgumentException("Null 'verticalAlignment' argument.");
    }
    
    if (padding == null) {
      throw new IllegalArgumentException("Null 'spacer' argument.");
    }
    
    visible = true;
    this.position = position;
    this.horizontalAlignment = horizontalAlignment;
    this.verticalAlignment = verticalAlignment;
    setPadding(padding);
    listenerList = new EventListenerList();
    notify = true;
  }
  










  public boolean isVisible()
  {
    return visible;
  }
  









  public void setVisible(boolean visible)
  {
    this.visible = visible;
    notifyListeners(new TitleChangeEvent(this));
  }
  




  public RectangleEdge getPosition()
  {
    return position;
  }
  





  public void setPosition(RectangleEdge position)
  {
    if (position == null) {
      throw new IllegalArgumentException("Null 'position' argument.");
    }
    if (this.position != position) {
      this.position = position;
      notifyListeners(new TitleChangeEvent(this));
    }
  }
  




  public HorizontalAlignment getHorizontalAlignment()
  {
    return horizontalAlignment;
  }
  






  public void setHorizontalAlignment(HorizontalAlignment alignment)
  {
    if (alignment == null) {
      throw new IllegalArgumentException("Null 'alignment' argument.");
    }
    if (horizontalAlignment != alignment) {
      horizontalAlignment = alignment;
      notifyListeners(new TitleChangeEvent(this));
    }
  }
  




  public VerticalAlignment getVerticalAlignment()
  {
    return verticalAlignment;
  }
  






  public void setVerticalAlignment(VerticalAlignment alignment)
  {
    if (alignment == null) {
      throw new IllegalArgumentException("Null 'alignment' argument.");
    }
    if (verticalAlignment != alignment) {
      verticalAlignment = alignment;
      notifyListeners(new TitleChangeEvent(this));
    }
  }
  





  public boolean getNotify()
  {
    return notify;
  }
  






  public void setNotify(boolean flag)
  {
    notify = flag;
    if (flag) {
      notifyListeners(new TitleChangeEvent(this));
    }
  }
  









  public abstract void draw(Graphics2D paramGraphics2D, Rectangle2D paramRectangle2D);
  









  public Object clone()
    throws CloneNotSupportedException
  {
    Title duplicate = (Title)super.clone();
    listenerList = new EventListenerList();
    
    return duplicate;
  }
  




  public void addChangeListener(TitleChangeListener listener)
  {
    listenerList.add(TitleChangeListener.class, listener);
  }
  




  public void removeChangeListener(TitleChangeListener listener)
  {
    listenerList.remove(TitleChangeListener.class, listener);
  }
  






  protected void notifyListeners(TitleChangeEvent event)
  {
    if (notify) {
      Object[] listeners = listenerList.getListenerList();
      for (int i = listeners.length - 2; i >= 0; i -= 2) {
        if (listeners[i] == TitleChangeListener.class) {
          ((TitleChangeListener)listeners[(i + 1)]).titleChanged(event);
        }
      }
    }
  }
  







  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof Title)) {
      return false;
    }
    Title that = (Title)obj;
    if (visible != visible) {
      return false;
    }
    if (position != position) {
      return false;
    }
    if (horizontalAlignment != horizontalAlignment) {
      return false;
    }
    if (verticalAlignment != verticalAlignment) {
      return false;
    }
    if (notify != notify) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = 193;
    result = 37 * result + ObjectUtilities.hashCode(position);
    result = 37 * result + ObjectUtilities.hashCode(horizontalAlignment);
    
    result = 37 * result + ObjectUtilities.hashCode(verticalAlignment);
    return result;
  }
  





  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    listenerList = new EventListenerList();
  }
}
