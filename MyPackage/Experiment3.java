package MyPackage;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

public class Experiment3 extends javax.swing.JApplet
{
  private JToggleButton add_class_1;
  private JToggleButton add_class_2;
  private JPanel add_data_panel;
  private javax.swing.JButton calculate_mean_variance;
  private javax.swing.JButton clear_button;
  private JPanel distributionFunction;
  private JPanel feature_graph;
  private JPanel graphs;
  private JPanel input_graph;
  private javax.swing.JComboBox jComboBox1;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;
  private JLabel jLabel5;
  private JPanel jPanel7;
  private JTextField m1_1;
  private JTextField m1_2;
  private JTextField m2_1;
  private JTextField m2_2;
  private javax.swing.JButton mark_all;
  private JPanel mark_points;
  private JTextField s11_1;
  private JTextField s11_2;
  private JTextField s12_1;
  private JTextField s12_2;
  private JTextField s21_1;
  private JTextField s21_2;
  private JTextField s22_1;
  private JTextField s22_2;
  
  public Experiment3() {}
  
  public void init()
  {
    try
    {
      java.awt.EventQueue.invokeAndWait(new Runnable() {
        public void run() {
          Experiment3.this.initComponents();
          initGraph();
        }
      });
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void initGraph() {
    org.jfree.data.xy.XYSeriesCollection x1 = new org.jfree.data.xy.XYSeriesCollection();
    series1 = new XYSeries("Class 1");
    series2 = new XYSeries("Class 2");
    x1.addSeries(series1);
    x1.addSeries(series2);
    chart = new Chart1(x1, "Y-axis", "X-axis");
    plot = ((XYPlot)chart.getPlot());
    plot.getDomainAxis().setRange(-1.1D, 1.1D);
    plot.getRangeAxis().setRange(-1.1D, 1.1D);
    
    inner_graph = new org.jfree.chart.ChartPanel(chart);
    inner_graph.addChartMouseListener(new org.jfree.chart.ChartMouseListener()
    {
      public void chartMouseClicked(org.jfree.chart.ChartMouseEvent e) {
        java.awt.geom.Point2D p = inner_graph.translateScreenToJava2D(e.getTrigger().getPoint());
        java.awt.geom.Rectangle2D plotArea = inner_graph.getScreenDataArea();
        XYPlot plot = (XYPlot)chart.getPlot();
        double chartX = plot.getDomainAxis().java2DToValue(p.getY(), plotArea, plot.getDomainAxisEdge());
        double chartY = plot.getRangeAxis().java2DToValue(p.getX(), plotArea, plot.getRangeAxisEdge());
        
        if (add_class_1.isSelected()) {
          series1.add(chartX, chartY);
        } else if (add_class_2.isSelected()) {
          series2.add(chartX, chartY);
        }
      }
      


      public void chartMouseMoved(org.jfree.chart.ChartMouseEvent event) {}
    });
    input_graph.add(inner_graph);
  }
  
  public static void main(String[] args)
  {
    Experiment3 applet = new Experiment3();
    applet.init();
    javax.swing.JFrame frame = new javax.swing.JFrame("Pattern Recognition : Experiment 6 - MLE: Learning the classifier from data ");
    frame.getContentPane().add(applet);
    
    frame.setDefaultCloseOperation(3);
    frame.setSize(800, 500);
    frame.pack();
    frame.setVisible(true);
  }
  






  private void initComponents()
  {
    graphs = new JPanel();
    input_graph = new JPanel();
    feature_graph = new JPanel();
    jPanel7 = new JPanel();
    mark_points = new JPanel();
    m1_1 = new JTextField();
    jLabel1 = new JLabel();
    m2_1 = new JTextField();
    jLabel2 = new JLabel();
    jLabel3 = new JLabel();
    m1_2 = new JTextField();
    m2_2 = new JTextField();
    jLabel4 = new JLabel();
    s11_1 = new JTextField();
    s12_1 = new JTextField();
    s11_2 = new JTextField();
    s12_2 = new JTextField();
    s21_1 = new JTextField();
    s22_1 = new JTextField();
    s21_2 = new JTextField();
    s22_2 = new JTextField();
    distributionFunction = new JPanel();
    jComboBox1 = new javax.swing.JComboBox();
    jLabel5 = new JLabel();
    add_data_panel = new JPanel();
    add_class_2 = new JToggleButton();
    clear_button = new javax.swing.JButton();
    add_class_1 = new JToggleButton();
    calculate_mean_variance = new javax.swing.JButton();
    mark_all = new javax.swing.JButton();
    
    graphs.setPreferredSize(new java.awt.Dimension(1000, 500));
    graphs.setLayout(new java.awt.GridLayout(1, 0, 10, 10));
    
    input_graph.setMaximumSize(new java.awt.Dimension(400, 400));
    input_graph.setMinimumSize(new java.awt.Dimension(300, 300));
    input_graph.setPreferredSize(new java.awt.Dimension(500, 500));
    input_graph.setLayout(new java.awt.GridLayout(1, 0));
    graphs.add(input_graph);
    
    feature_graph.setPreferredSize(new java.awt.Dimension(500, 500));
    feature_graph.setLayout(new java.awt.GridLayout(1, 0));
    
    mark_points.setBorder(javax.swing.BorderFactory.createTitledBorder("Mark Points"));
    mark_points.setPreferredSize(new java.awt.Dimension(218, 68));
    
    m1_1.setText(" ");
    
    jLabel1.setText("Mean");
    
    m2_1.setText(" ");
    
    jLabel2.setText("Class 1");
    
    jLabel3.setText("Class 2");
    
    m1_2.setText(" ");
    
    m2_2.setText(" ");
    
    jLabel4.setText("Covariance");
    
    s12_1.setText(" ");
    
    s11_2.setText(" ");
    
    s12_2.setText(" ");
    
    s21_1.setText(" ");
    
    s22_1.setText(" ");
    
    s21_2.setText(" ");
    
    s22_2.setText(" ");
    
    GroupLayout mark_pointsLayout = new GroupLayout(mark_points);
    mark_points.setLayout(mark_pointsLayout);
    mark_pointsLayout.setHorizontalGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, mark_pointsLayout.createSequentialGroup().addGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(mark_pointsLayout.createSequentialGroup().addGap(113, 113, 113).addComponent(jLabel2, -2, 77, -2)).addGroup(mark_pointsLayout.createSequentialGroup().addGap(14, 14, 14).addGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(jLabel4).addComponent(jLabel1)).addGap(18, 18, 18).addGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(m1_1, GroupLayout.Alignment.TRAILING).addComponent(s11_1, GroupLayout.Alignment.TRAILING).addComponent(s21_1, -2, 47, -2)).addGap(18, 18, 18).addGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(m2_1).addComponent(s12_1, GroupLayout.Alignment.LEADING).addComponent(s22_1, GroupLayout.Alignment.LEADING, -1, 47, 32767)))).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, 32767).addGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, mark_pointsLayout.createSequentialGroup().addGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(s21_2).addComponent(s11_2, GroupLayout.Alignment.TRAILING).addComponent(m1_2, GroupLayout.Alignment.TRAILING, -1, 57, 32767)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(s12_2, GroupLayout.Alignment.TRAILING).addComponent(m2_2, GroupLayout.Alignment.TRAILING, -1, 60, 32767).addComponent(s22_2)).addGap(35, 35, 35)).addGroup(GroupLayout.Alignment.TRAILING, mark_pointsLayout.createSequentialGroup().addComponent(jLabel3).addGap(83, 83, 83)))));
    





































    mark_pointsLayout.setVerticalGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(mark_pointsLayout.createSequentialGroup().addGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel2, -2, 14, -2).addComponent(jLabel3)).addGap(17, 17, 17).addGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(m1_1, -2, -1, -2).addComponent(m2_1, -2, -1, -2).addComponent(jLabel1).addComponent(m2_2, -2, -1, -2).addComponent(m1_2, -2, -1, -2)).addGap(18, 18, 18).addGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(s11_1, -2, -1, -2).addComponent(s12_1, -2, -1, -2).addComponent(s12_2, -2, -1, -2).addComponent(s11_2, -2, -1, -2)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(mark_pointsLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(s21_1, -2, -1, -2).addComponent(s22_1, -2, -1, -2).addComponent(s22_2, -2, -1, -2).addComponent(s21_2, -2, -1, -2)).addContainerGap(28, 32767)));
    



























    distributionFunction.setBorder(javax.swing.BorderFactory.createTitledBorder("Distibution Function"));
    distributionFunction.setDebugGraphicsOptions(-1);
    distributionFunction.setPreferredSize(new java.awt.Dimension(218, 68));
    
    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gaussian/Normal Distribution", "Uniform Distribution" }));
    jComboBox1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        Experiment3.this.jComboBox1ActionPerformed(evt);
      }
      
    });
    jLabel5.setText("Select Distribution Function");
    
    GroupLayout distributionFunctionLayout = new GroupLayout(distributionFunction);
    distributionFunction.setLayout(distributionFunctionLayout);
    distributionFunctionLayout.setHorizontalGroup(distributionFunctionLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(distributionFunctionLayout.createSequentialGroup().addContainerGap().addComponent(jLabel5).addGap(45, 45, 45).addComponent(jComboBox1, -2, -1, -2).addGap(72, 72, 72)));
    







    distributionFunctionLayout.setVerticalGroup(distributionFunctionLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(distributionFunctionLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jComboBox1, -2, -1, -2).addComponent(jLabel5)));
    





    add_data_panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Add Data"));
    add_data_panel.setPreferredSize(new java.awt.Dimension(329, 68));
    
    add_class_2.setText("Add Class 2");
    add_class_2.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        Experiment3.this.add_class_2ActionPerformed(evt);
      }
      
    });
    clear_button.setText("Clear");
    clear_button.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        Experiment3.this.clear_buttonActionPerformed(evt);
      }
      
    });
    add_class_1.setText("Add Class 1");
    add_class_1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        Experiment3.this.add_class_1ActionPerformed(evt);
      }
      
    });
    calculate_mean_variance.setText("Calculate MLE");
    calculate_mean_variance.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        Experiment3.this.calculate_mean_varianceActionPerformed(evt);
      }
      
    });
    mark_all.setText("Mark All");
    mark_all.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        Experiment3.this.mark_allActionPerformed(evt);
      }
      
    });
    GroupLayout add_data_panelLayout = new GroupLayout(add_data_panel);
    add_data_panel.setLayout(add_data_panelLayout);
    add_data_panelLayout.setHorizontalGroup(add_data_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(add_data_panelLayout.createSequentialGroup().addContainerGap().addGroup(add_data_panelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(add_data_panelLayout.createSequentialGroup().addGap(18, 18, 18).addComponent(calculate_mean_variance, -1, 153, 32767).addGap(61, 61, 61).addComponent(mark_all)).addGroup(add_data_panelLayout.createSequentialGroup().addComponent(add_class_1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(add_class_2).addGap(18, 18, 18).addComponent(clear_button, -2, 95, -2))).addGap(128, 128, 128)));
    
















    add_data_panelLayout.setVerticalGroup(add_data_panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(add_data_panelLayout.createSequentialGroup().addGroup(add_data_panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(add_class_1).addComponent(add_class_2).addComponent(clear_button)).addGap(18, 18, 18).addGroup(add_data_panelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(mark_all).addComponent(calculate_mean_variance)).addContainerGap(23, 32767)));
    












    GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
    jPanel7.setLayout(jPanel7Layout);
    jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(add_data_panel, GroupLayout.Alignment.LEADING, -1, 441, 32767).addGroup(GroupLayout.Alignment.LEADING, jPanel7Layout.createSequentialGroup().addContainerGap().addGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(mark_points, GroupLayout.Alignment.LEADING, -1, 431, 32767).addComponent(distributionFunction, GroupLayout.Alignment.LEADING, -1, 431, 32767)))).addContainerGap(69, 32767)));
    










    jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addComponent(distributionFunction, -2, 80, -2).addGap(18, 18, 18).addComponent(mark_points, -2, 175, -2).addGap(37, 37, 37).addComponent(add_data_panel, -2, 114, -2).addContainerGap(65, 32767)));
    










    distributionFunction.getAccessibleContext().setAccessibleName("Distribution ");
    
    feature_graph.add(jPanel7);
    
    graphs.add(feature_graph);
    
    getContentPane().add(graphs, "North");
  }
  
  private void add_class_1ActionPerformed(ActionEvent evt)
  {
    add_class_2.setSelected(false);
  }
  







  private void add_class_2ActionPerformed(ActionEvent evt)
  {
    add_class_1.setSelected(false);
  }
  







  private void clear_buttonActionPerformed(ActionEvent evt)
  {
    XYPlot p = (XYPlot)chart.getPlot();
    p.clearAnnotations();
    p.getDomainAxis().setRange(-1.1D, 1.1D);
    p.getRangeAxis().setRange(-1.1D, 1.1D);
    
    series1.clear();
    series2.clear();
    





    add_class_1.setSelected(false);
    add_class_2.setSelected(false);
  }
  

  private void calculate_mean_varianceActionPerformed(ActionEvent evt)
  {
    xmin_1 = 100.0D;
    ymin_1 = 100.0D;
    xmin_2 = 100.0D;
    ymin_2 = 100.0D;
    xmax_1 = -100.0D;
    ymax_1 = -100.0D;
    xmax_2 = -100.0D;
    ymax_2 = -100.0D;
    
    XYPlot plot = (XYPlot)chart.getPlot();
    java.awt.BasicStroke bs = new java.awt.BasicStroke(1.0F, 0, 0, 2.0F, new float[] { 5.0F, 5.0F }, 2.0F);
    List<XYDataItem> s1 = series1.getItems();
    
    double sum1 = 0.0D;double sum2 = 0.0D;
    double l1gammax = 0.0D;double l1gammay = 0.0D;
    System.out.print("Hello World\n");
    








    for (int i = 0; i < s1.size(); i++) {
      sum1 += ((XYDataItem)s1.get(i)).getYValue();
      sum2 += ((XYDataItem)s1.get(i)).getXValue();
      if (distribution == 1)
      {
        l1gammax += Math.log(((XYDataItem)s1.get(i)).getXValue());
        l1gammay += Math.log(((XYDataItem)s1.get(i)).getYValue());
      }
      if (((XYDataItem)s1.get(i)).getXValue() < xmin_1)
      {
        xmin_1 = ((XYDataItem)s1.get(i)).getXValue();
      }
      if (((XYDataItem)s1.get(i)).getXValue() > xmax_1)
      {
        xmax_1 = ((XYDataItem)s1.get(i)).getXValue();
      }
      if (((XYDataItem)s1.get(i)).getYValue() < ymin_1)
      {
        ymin_1 = ((XYDataItem)s1.get(i)).getYValue();
      }
      if (((XYDataItem)s1.get(i)).getYValue() > ymax_1)
      {
        ymax_1 = ((XYDataItem)s1.get(i)).getYValue();
      }
    }
    



    if (distribution == 1)
    {
      l1gammax /= s1.size();
      l1gammay /= s1.size();
    }
    m1_val1 = (sum1 / s1.size());
    m2_val1 = (sum2 / s1.size());
    m1_1.setText(df.format(m1_val1));
    m2_1.setText(df.format(m2_val1));
    
    s11_val1 = 0.0D;s12_val1 = 0.0D;s22_val1 = 0.0D;
    for (int i = 0; i < s1.size(); i++) {
      s11_val1 += Math.pow(((XYDataItem)s1.get(i)).getYValue(), 2.0D);
      s22_val1 += Math.pow(((XYDataItem)s1.get(i)).getXValue(), 2.0D);
      s12_val1 += ((XYDataItem)s1.get(i)).getYValue() * ((XYDataItem)s1.get(i)).getXValue();
    }
    s11_val1 = (s11_val1 / s1.size() - m1_val1 * m1_val1);
    s22_val1 = (s22_val1 / s1.size() - m2_val1 * m2_val1);
    s12_val1 = (s12_val1 / s1.size() - m1_val1 * m2_val1);
    if (distribution == 0)
    {
      s11_1.setText(df.format(s11_val1));
      s12_1.setText(df.format(s12_val1));
      s21_1.setText(df.format(s12_val1));
      s22_1.setText(df.format(s22_val1));
    }
    if ((distribution == 1) || (distribution == 2))
    {
      s11_1.setText("");
      s12_1.setText("");
      s21_1.setText("");
      s22_1.setText("");
    }
    s1Size = s1.size();
    









    List<XYDataItem> s2 = series2.getItems();
    



    sum1 = 0.0D;
    sum2 = 0.0D;
    double l2gammax = 0.0D;double l2gammay = 0.0D;
    
    for (int i = 0; i < s2.size(); i++) {
      sum1 += ((XYDataItem)s2.get(i)).getYValue();
      sum2 += ((XYDataItem)s2.get(i)).getXValue();
      if (distribution == 1)
      {
        l2gammax += Math.log(((XYDataItem)s2.get(i)).getXValue());
        l2gammay += Math.log(((XYDataItem)s2.get(i)).getYValue());
      }
      if (((XYDataItem)s2.get(i)).getXValue() < xmin_2)
      {
        xmin_2 = ((XYDataItem)s2.get(i)).getXValue();
      }
      if (((XYDataItem)s2.get(i)).getXValue() > xmax_2)
      {
        xmax_2 = ((XYDataItem)s2.get(i)).getXValue();
      }
      if (((XYDataItem)s2.get(i)).getYValue() < ymin_2)
      {
        ymin_2 = ((XYDataItem)s2.get(i)).getYValue();
      }
      if (((XYDataItem)s2.get(i)).getYValue() > ymax_2)
      {
        ymax_2 = ((XYDataItem)s2.get(i)).getYValue();
      }
    }
    




    if (distribution == 1)
    {
      l2gammax /= s2.size();
      l2gammay /= s2.size();
    }
    m1_val2 = (sum1 / s2.size());
    m2_val2 = (sum2 / s2.size());
    if (distribution == 0)
    {
      m1_2.setText(df.format(m1_val2));
      m2_2.setText(df.format(m2_val2));
    }
    s11_val2 = 0.0D;
    s12_val2 = 0.0D;
    s22_val2 = 0.0D;
    for (int i = 0; i < s2.size(); i++) {
      s11_val2 += Math.pow(((XYDataItem)s2.get(i)).getYValue(), 2.0D);
      s22_val2 += Math.pow(((XYDataItem)s2.get(i)).getXValue(), 2.0D);
      s12_val2 += ((XYDataItem)s2.get(i)).getYValue() * ((XYDataItem)s2.get(i)).getXValue();
    }
    s11_val2 = (s11_val2 / s2.size() - m1_val2 * m1_val2);
    s22_val2 = (s22_val2 / s2.size() - m2_val2 * m2_val2);
    s12_val2 = (s12_val2 / s2.size() - m1_val2 * m2_val2);
    if (distribution == 0)
    {
      s11_2.setText(df.format(s11_val2));
      s12_2.setText(df.format(s12_val2));
      s21_2.setText(df.format(s12_val2));
      s22_2.setText(df.format(s22_val2));
    }
    if (distribution == 1)
    {
      s11_1.setText("");
      s12_1.setText("");
      s21_1.setText("");
      s22_1.setText("");
      s11_2.setText("");
      s12_2.setText("");
      s21_2.setText("");
      s22_2.setText("");
    }
    if (distribution == 1) {}
    


    s2Size = s2.size();
  }
  
















  private void jComboBox1ActionPerformed(ActionEvent evt)
  {
    distribution = jComboBox1.getSelectedIndex();
    
    if (distribution == 0)
    {
      jLabel1.setText("Mean");
      jLabel1.enable();
      m1_1.enable();
      m2_1.enable();
      m1_2.enable();
      m2_2.enable();
      jLabel4.enable();
      s11_1.enable();
      s21_1.enable();
      s12_1.enable();
      s22_1.enable();
      s11_2.enable();
      s21_2.enable();
      s12_2.enable();
      s22_2.enable();
      plot.getDomainAxis().setRange(-1.1D, 1.1D);
      plot.getRangeAxis().setRange(-1.1D, 1.1D);
    }
    if (distribution == 1)
    {


      jLabel1.disable();
      m1_1.disable();
      m2_1.disable();
      m1_2.disable();
      m2_2.disable();
      jLabel4.disable();
      s11_1.disable();
      s21_1.disable();
      s12_1.disable();
      s22_1.disable();
      s11_2.disable();
      s21_2.disable();
      s12_2.disable();
      s22_2.disable();
      System.out.print("Hello World\n");
    }
  }
  


  private void mark_allActionPerformed(ActionEvent evt)
  {
    series1.clear();
    series2.clear();
    XYPlot plot = (XYPlot)chart.getPlot();
    double l = plot.getRangeAxis().getRange().getLowerBound();
    double r = plot.getRangeAxis().getRange().getUpperBound();
    


    if (distribution == 0) {
      for (double i = l; i < r; i += (r - l) / 100.0D)
        for (double j = l; j < r; j += (r - l) / 100.0D)
        {
          double p1 = prob1(j, i);
          double p2 = prob2(j, i);
          if (p1 > p2) {
            series1.add(j, i);
          } else
            series2.add(j, i);
        }
    }
    if (distribution == 1) {
      for (double i = l; i < r; i += (r - l) / 100.0D) {
        for (double j = l; j < r; j += (r - l) / 100.0D)
        {
          double p1 = prob1(j, i);
          double p2 = prob2(j, i);
          if (p1 > p2) {
            series1.add(j, i);
          }
          if (p2 > p1) {
            series2.add(j, i);
          }
          if ((p1 == p2) && (p1 != 0.0D))
          {
            series2.add(j, i);
          }
        }
      }
    }
  }
  



































  private DecimalFormat df = new DecimalFormat("###.##");
  private int distribution = 0;
  private double xmin_1;
  private double ymin_1;
  private double xmin_2;
  private double ymin_2;
  private double xmax_1;
  private double ymax_1;
  private double xmax_2;
  private double ymax_2;
  XYSeries series1;
  XYSeries series2;
  XYSeries out_series1;
  XYSeries out_series2; List<Double> tmp_series1; List<Double> tmp_series2; Chart1 chart; Chart1 out_chart; org.jfree.chart.ChartPanel inner_graph; org.jfree.chart.ChartPanel outer_graph; private int k; private double m1_val1; private double m2_val1; private double s11_val1; private double s12_val1; private double s22_val1; private double m1_val2; private double m2_val2; private double s11_val2; private double s12_val2; private double s22_val2; private double s1Size = 0.0D;
  private double s2Size = 0.0D;
  
  XYPlot plot;
  
  private double prob1(double x, double y)
  {
    double ret = 0.0D;
    if (distribution == 0)
    {
      double sigmax = Math.sqrt(s22_val1);double sigmay = Math.sqrt(s11_val1);
      double rho = s12_val1 / (sigmax * sigmay);
      rho = 0.0D;
      ret = 1.0D / (6.283185307179586D * sigmax * sigmay * Math.sqrt(1.0D - rho * rho)) * Math.exp(-1.0D / (2.0D * (1.0D - rho * rho)) * (Math.pow(y - m1_val1, 2.0D) / s11_val1 + Math.pow(x - m2_val1, 2.0D) / s22_val1 - 2.0D * rho * (y - m1_val1) * (x - m2_val1) / (sigmax * sigmay)));
    }
    if (distribution == 1)
    {
      if ((x <= xmax_1) && (x >= xmin_1) && (y <= ymax_1) && (y >= ymin_1))
      {
        ret = s1Size;
      }
    }
    return ret;
  }
  





  private double prob2(double x, double y)
  {
    double ret = 0.0D;
    if (distribution == 0)
    {
      double sigmax = Math.sqrt(s22_val2);double sigmay = Math.sqrt(s11_val2);
      double rho = s12_val2 / (sigmax * sigmay);
      rho = 0.0D;
      
      ret = 1.0D / (6.283185307179586D * sigmax * sigmay * Math.sqrt(1.0D - rho * rho)) * Math.exp(-1.0D / (2.0D * (1.0D - rho * rho)) * (Math.pow(y - m1_val2, 2.0D) / s11_val2 + Math.pow(x - m2_val2, 2.0D) / s22_val2 - 2.0D * rho * (y - m1_val2) * (x - m2_val2) / (sigmax * sigmay)));
    }
    
    if (distribution == 1)
    {
      if ((x <= xmax_2) && (x >= xmin_2) && (y <= ymax_2) && (y >= ymin_2))
      {
        ret = s2Size;
      }
    }
    return ret;
  }
}
