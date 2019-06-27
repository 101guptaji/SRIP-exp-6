package org.jfree.chart.demo;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


















































public class PieChartDemo1
  extends ApplicationFrame
{
  public PieChartDemo1(String title)
  {
    super(title);
    setContentPane(createDemoPanel());
  }
  




  private static PieDataset createDataset()
  {
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("One", new Double(43.2D));
    dataset.setValue("Two", new Double(10.0D));
    dataset.setValue("Three", new Double(27.5D));
    dataset.setValue("Four", new Double(17.5D));
    dataset.setValue("Five", new Double(11.0D));
    dataset.setValue("Six", new Double(19.4D));
    return dataset;
  }
  







  private static JFreeChart createChart(PieDataset dataset)
  {
    JFreeChart chart = ChartFactory.createPieChart("Pie Chart Demo 1", dataset, true, true, false);
    






    PiePlot plot = (PiePlot)chart.getPlot();
    plot.setSectionOutlinesVisible(false);
    plot.setNoDataMessage("No data available");
    
    return chart;
  }
  





  public static JPanel createDemoPanel()
  {
    JFreeChart chart = createChart(createDataset());
    return new ChartPanel(chart);
  }
  













  public static void main(String[] args)
  {
    PieChartDemo1 demo = new PieChartDemo1("Pie Chart Demo 1");
    demo.pack();
    RefineryUtilities.centerFrameOnScreen(demo);
    demo.setVisible(true);
  }
}
