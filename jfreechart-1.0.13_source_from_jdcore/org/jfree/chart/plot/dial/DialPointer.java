package org.jfree.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.HashUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;
























































public abstract class DialPointer
  extends AbstractDialLayer
  implements DialLayer, Cloneable, PublicCloneable, Serializable
{
  double radius;
  int datasetIndex;
  
  protected DialPointer()
  {
    this(0);
  }
  




  protected DialPointer(int datasetIndex)
  {
    radius = 0.9D;
    this.datasetIndex = datasetIndex;
  }
  






  public int getDatasetIndex()
  {
    return datasetIndex;
  }
  







  public void setDatasetIndex(int index)
  {
    datasetIndex = index;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  







  public double getRadius()
  {
    return radius;
  }
  







  public void setRadius(double radius)
  {
    this.radius = radius;
    notifyListeners(new DialLayerChangeEvent(this));
  }
  





  public boolean isClippedToWindow()
  {
    return true;
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DialPointer)) {
      return false;
    }
    DialPointer that = (DialPointer)obj;
    if (datasetIndex != datasetIndex) {
      return false;
    }
    if (radius != radius) {
      return false;
    }
    return super.equals(obj);
  }
  




  public int hashCode()
  {
    int result = 23;
    result = HashUtilities.hashCode(result, radius);
    return result;
  }
  






  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  



  public static class Pin
    extends DialPointer
  {
    static final long serialVersionUID = -8445860485367689750L;
    

    private transient Paint paint;
    

    private transient Stroke stroke;
    


    public Pin()
    {
      this(0);
    }
    




    public Pin(int datasetIndex)
    {
      super();
      paint = Color.red;
      stroke = new BasicStroke(3.0F, 1, 2);
    }
    







    public Paint getPaint()
    {
      return paint;
    }
    







    public void setPaint(Paint paint)
    {
      if (paint == null) {
        throw new IllegalArgumentException("Null 'paint' argument.");
      }
      this.paint = paint;
      notifyListeners(new DialLayerChangeEvent(this));
    }
    






    public Stroke getStroke()
    {
      return stroke;
    }
    







    public void setStroke(Stroke stroke)
    {
      if (stroke == null) {
        throw new IllegalArgumentException("Null 'stroke' argument.");
      }
      this.stroke = stroke;
      notifyListeners(new DialLayerChangeEvent(this));
    }
    









    public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view)
    {
      g2.setPaint(paint);
      g2.setStroke(stroke);
      Rectangle2D arcRect = DialPlot.rectangleByRadius(frame, radius, radius);
      

      double value = plot.getValue(datasetIndex);
      DialScale scale = plot.getScaleForDataset(datasetIndex);
      double angle = scale.valueToAngle(value);
      
      Arc2D arc = new Arc2D.Double(arcRect, angle, 0.0D, 0);
      Point2D pt = arc.getEndPoint();
      
      Line2D line = new Line2D.Double(frame.getCenterX(), frame.getCenterY(), pt.getX(), pt.getY());
      
      g2.draw(line);
    }
    






    public boolean equals(Object obj)
    {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof Pin)) {
        return false;
      }
      Pin that = (Pin)obj;
      if (!PaintUtilities.equal(paint, paint)) {
        return false;
      }
      if (!stroke.equals(stroke)) {
        return false;
      }
      return super.equals(obj);
    }
    




    public int hashCode()
    {
      int result = super.hashCode();
      result = HashUtilities.hashCode(result, paint);
      result = HashUtilities.hashCode(result, stroke);
      return result;
    }
    





    private void writeObject(ObjectOutputStream stream)
      throws IOException
    {
      stream.defaultWriteObject();
      SerialUtilities.writePaint(paint, stream);
      SerialUtilities.writeStroke(stroke, stream);
    }
    







    private void readObject(ObjectInputStream stream)
      throws IOException, ClassNotFoundException
    {
      stream.defaultReadObject();
      paint = SerialUtilities.readPaint(stream);
      stroke = SerialUtilities.readStroke(stream);
    }
  }
  





  public static class Pointer
    extends DialPointer
  {
    static final long serialVersionUID = -4180500011963176960L;
    




    private double widthRadius;
    



    private transient Paint fillPaint;
    



    private transient Paint outlinePaint;
    




    public Pointer()
    {
      this(0);
    }
    




    public Pointer(int datasetIndex)
    {
      super();
      widthRadius = 0.05D;
      fillPaint = Color.gray;
      outlinePaint = Color.black;
    }
    






    public double getWidthRadius()
    {
      return widthRadius;
    }
    







    public void setWidthRadius(double radius)
    {
      widthRadius = radius;
      notifyListeners(new DialLayerChangeEvent(this));
    }
    








    public Paint getFillPaint()
    {
      return fillPaint;
    }
    









    public void setFillPaint(Paint paint)
    {
      if (paint == null) {
        throw new IllegalArgumentException("Null 'paint' argument.");
      }
      fillPaint = paint;
      notifyListeners(new DialLayerChangeEvent(this));
    }
    








    public Paint getOutlinePaint()
    {
      return outlinePaint;
    }
    









    public void setOutlinePaint(Paint paint)
    {
      if (paint == null) {
        throw new IllegalArgumentException("Null 'paint' argument.");
      }
      outlinePaint = paint;
      notifyListeners(new DialLayerChangeEvent(this));
    }
    









    public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, Rectangle2D view)
    {
      g2.setPaint(Color.blue);
      g2.setStroke(new BasicStroke(1.0F));
      Rectangle2D lengthRect = DialPlot.rectangleByRadius(frame, radius, radius);
      
      Rectangle2D widthRect = DialPlot.rectangleByRadius(frame, widthRadius, widthRadius);
      
      double value = plot.getValue(datasetIndex);
      DialScale scale = plot.getScaleForDataset(datasetIndex);
      double angle = scale.valueToAngle(value);
      
      Arc2D arc1 = new Arc2D.Double(lengthRect, angle, 0.0D, 0);
      Point2D pt1 = arc1.getEndPoint();
      Arc2D arc2 = new Arc2D.Double(widthRect, angle - 90.0D, 180.0D, 0);
      
      Point2D pt2 = arc2.getStartPoint();
      Point2D pt3 = arc2.getEndPoint();
      Arc2D arc3 = new Arc2D.Double(widthRect, angle - 180.0D, 0.0D, 0);
      
      Point2D pt4 = arc3.getStartPoint();
      
      GeneralPath gp = new GeneralPath();
      gp.moveTo((float)pt1.getX(), (float)pt1.getY());
      gp.lineTo((float)pt2.getX(), (float)pt2.getY());
      gp.lineTo((float)pt4.getX(), (float)pt4.getY());
      gp.lineTo((float)pt3.getX(), (float)pt3.getY());
      gp.closePath();
      g2.setPaint(fillPaint);
      g2.fill(gp);
      
      g2.setPaint(outlinePaint);
      Line2D line = new Line2D.Double(frame.getCenterX(), frame.getCenterY(), pt1.getX(), pt1.getY());
      
      g2.draw(line);
      
      line.setLine(pt2, pt3);
      g2.draw(line);
      
      line.setLine(pt3, pt1);
      g2.draw(line);
      
      line.setLine(pt2, pt1);
      g2.draw(line);
      
      line.setLine(pt2, pt4);
      g2.draw(line);
      
      line.setLine(pt3, pt4);
      g2.draw(line);
    }
    






    public boolean equals(Object obj)
    {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof Pointer)) {
        return false;
      }
      Pointer that = (Pointer)obj;
      
      if (widthRadius != widthRadius) {
        return false;
      }
      if (!PaintUtilities.equal(fillPaint, fillPaint)) {
        return false;
      }
      if (!PaintUtilities.equal(outlinePaint, outlinePaint)) {
        return false;
      }
      return super.equals(obj);
    }
    




    public int hashCode()
    {
      int result = super.hashCode();
      result = HashUtilities.hashCode(result, widthRadius);
      result = HashUtilities.hashCode(result, fillPaint);
      result = HashUtilities.hashCode(result, outlinePaint);
      return result;
    }
    





    private void writeObject(ObjectOutputStream stream)
      throws IOException
    {
      stream.defaultWriteObject();
      SerialUtilities.writePaint(fillPaint, stream);
      SerialUtilities.writePaint(outlinePaint, stream);
    }
    







    private void readObject(ObjectInputStream stream)
      throws IOException, ClassNotFoundException
    {
      stream.defaultReadObject();
      fillPaint = SerialUtilities.readPaint(stream);
      outlinePaint = SerialUtilities.readPaint(stream);
    }
  }
}
