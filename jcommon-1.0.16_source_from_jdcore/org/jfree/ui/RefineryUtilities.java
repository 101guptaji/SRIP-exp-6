package org.jfree.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.lang.reflect.Method;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
















































































public class RefineryUtilities
{
  private RefineryUtilities() {}
  
  public static Point getCenterPoint()
  {
    GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    try
    {
      Method method = GraphicsEnvironment.class.getMethod("getCenterPoint", (Class[])null);
      return (Point)method.invoke(localGraphicsEnvironment, (Object[])null);


    }
    catch (Exception e)
    {

      Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
      return new Point(width / 2, height / 2);
    }
  }
  





  public static Rectangle getMaximumWindowBounds()
  {
    GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    try
    {
      Method method = GraphicsEnvironment.class.getMethod("getMaximumWindowBounds", (Class[])null);
      return (Rectangle)method.invoke(localGraphicsEnvironment, (Object[])null);


    }
    catch (Exception e)
    {

      Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
      return new Rectangle(0, 0, width, height);
    }
  }
  



  public static void centerFrameOnScreen(Window frame)
  {
    positionFrameOnScreen(frame, 0.5D, 0.5D);
  }
  












  public static void positionFrameOnScreen(Window frame, double horizontalPercent, double verticalPercent)
  {
    Rectangle s = getMaximumWindowBounds();
    Dimension f = frame.getSize();
    int w = Math.max(width - width, 0);
    int h = Math.max(height - height, 0);
    int x = (int)(horizontalPercent * w) + x;
    int y = (int)(verticalPercent * h) + y;
    frame.setBounds(x, y, width, height);
  }
  






  public static void positionFrameRandomly(Window frame)
  {
    positionFrameOnScreen(frame, Math.random(), Math.random());
  }
  




  public static void centerDialogInParent(Dialog dialog)
  {
    positionDialogRelativeToParent(dialog, 0.5D, 0.5D);
  }
  








  public static void positionDialogRelativeToParent(Dialog dialog, double horizontalPercent, double verticalPercent)
  {
    Dimension d = dialog.getSize();
    Container parent = dialog.getParent();
    Dimension p = parent.getSize();
    
    int baseX = parent.getX() - width;
    int baseY = parent.getY() - height;
    int w = width + width;
    int h = height + height;
    int x = baseX + (int)(horizontalPercent * w);
    int y = baseY + (int)(verticalPercent * h);
    

    Rectangle s = getMaximumWindowBounds();
    x = Math.min(x, width - width);
    x = Math.max(x, 0);
    y = Math.min(y, height - height);
    y = Math.max(y, 0);
    
    dialog.setBounds(x + x, y + y, width, height);
  }
  








  public static JPanel createTablePanel(TableModel model)
  {
    JPanel panel = new JPanel(new BorderLayout());
    JTable table = new JTable(model);
    for (int columnIndex = 0; columnIndex < model.getColumnCount(); columnIndex++) {
      TableColumn column = table.getColumnModel().getColumn(columnIndex);
      Class c = model.getColumnClass(columnIndex);
      if (c.equals(Number.class)) {
        column.setCellRenderer(new NumberCellRenderer());
      }
    }
    panel.add(new JScrollPane(table));
    return panel;
  }
  









  public static JLabel createJLabel(String text, Font font)
  {
    JLabel result = new JLabel(text);
    result.setFont(font);
    return result;
  }
  










  public static JLabel createJLabel(String text, Font font, Color color)
  {
    JLabel result = new JLabel(text);
    result.setFont(font);
    result.setForeground(color);
    return result;
  }
  









  public static JButton createJButton(String label, Font font)
  {
    JButton result = new JButton(label);
    result.setFont(font);
    return result;
  }
}
