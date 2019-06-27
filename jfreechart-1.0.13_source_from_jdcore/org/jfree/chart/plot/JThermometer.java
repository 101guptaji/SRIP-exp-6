package org.jfree.chart.plot;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.Serializable;
import java.text.DecimalFormat;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.RectangleInsets;




























































public class JThermometer
  extends JPanel
  implements Serializable
{
  private static final long serialVersionUID = 1079905665515589820L;
  private DefaultValueDataset data;
  private JFreeChart chart;
  private ChartPanel panel;
  private ThermometerPlot plot = new ThermometerPlot();
  


  public JThermometer()
  {
    super(new CardLayout());
    plot.setInsets(new RectangleInsets(5.0D, 5.0D, 5.0D, 5.0D));
    data = new DefaultValueDataset();
    plot.setDataset(data);
    chart = new JFreeChart(null, JFreeChart.DEFAULT_TITLE_FONT, plot, false);
    
    panel = new ChartPanel(chart);
    add(panel, "Panel");
    setBackground(getBackground());
  }
  




  public void addSubtitle(Title subtitle)
  {
    chart.addSubtitle(subtitle);
  }
  




  public void addSubtitle(String subtitle)
  {
    chart.addSubtitle(new TextTitle(subtitle));
  }
  





  public void addSubtitle(String subtitle, Font font)
  {
    chart.addSubtitle(new TextTitle(subtitle, font));
  }
  




  public void setValueFormat(DecimalFormat df)
  {
    plot.setValueFormat(df);
  }
  





  public void setRange(double lower, double upper)
  {
    plot.setRange(lower, upper);
  }
  







  public void setSubrangeInfo(int range, double displayLow, double displayHigh)
  {
    plot.setSubrangeInfo(range, displayLow, displayHigh);
  }
  











  public void setSubrangeInfo(int range, double rangeLow, double rangeHigh, double displayLow, double displayHigh)
  {
    plot.setSubrangeInfo(range, rangeLow, rangeHigh, displayLow, displayHigh);
  }
  






  public void setValueLocation(int loc)
  {
    plot.setValueLocation(loc);
    panel.repaint();
  }
  




  public void setValuePaint(Paint paint)
  {
    plot.setValuePaint(paint);
  }
  




  public Number getValue()
  {
    if (data != null) {
      return data.getValue();
    }
    
    return null;
  }
  





  public void setValue(double value)
  {
    setValue(new Double(value));
  }
  




  public void setValue(Number value)
  {
    if (data != null) {
      data.setValue(value);
    }
  }
  




  public void setUnits(int i)
  {
    if (plot != null) {
      plot.setUnits(i);
    }
  }
  




  public void setOutlinePaint(Paint p)
  {
    if (plot != null) {
      plot.setOutlinePaint(p);
    }
  }
  




  public void setForeground(Color fg)
  {
    super.setForeground(fg);
    if (plot != null) {
      plot.setThermometerPaint(fg);
    }
  }
  




  public void setBackground(Color bg)
  {
    super.setBackground(bg);
    if (plot != null) {
      plot.setBackgroundPaint(bg);
    }
    if (chart != null) {
      chart.setBackgroundPaint(bg);
    }
    if (panel != null) {
      panel.setBackground(bg);
    }
  }
  




  public void setValueFont(Font f)
  {
    if (plot != null) {
      plot.setValueFont(f);
    }
  }
  




  public Font getTickLabelFont()
  {
    ValueAxis axis = plot.getRangeAxis();
    return axis.getTickLabelFont();
  }
  




  public void setTickLabelFont(Font font)
  {
    ValueAxis axis = plot.getRangeAxis();
    axis.setTickLabelFont(font);
  }
  




  public void changeTickFontSize(int delta)
  {
    Font f = getTickLabelFont();
    String fName = f.getFontName();
    Font newFont = new Font(fName, f.getStyle(), f.getSize() + delta);
    setTickLabelFont(newFont);
  }
  




  public void setTickFontStyle(int style)
  {
    Font f = getTickLabelFont();
    String fName = f.getFontName();
    Font newFont = new Font(fName, style, f.getSize());
    setTickLabelFont(newFont);
  }
  





  public void setFollowDataInSubranges(boolean flag)
  {
    plot.setFollowDataInSubranges(flag);
  }
  




  public void setShowValueLines(boolean b)
  {
    plot.setShowValueLines(b);
  }
  




  public void setShowAxisLocation(int location)
  {
    plot.setAxisLocation(location);
  }
  




  public int getShowAxisLocation()
  {
    return plot.getAxisLocation();
  }
}
