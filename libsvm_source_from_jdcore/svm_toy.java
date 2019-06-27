import java.awt.Button;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.util.StringTokenizer;
import java.util.Vector;
import libsvm.svm_node;
import libsvm.svm_parameter;

public class svm_toy extends java.applet.Applet
{
  static final String DEFAULT_PARAM = "-t 2 -c 100";
  int XLEN;
  int YLEN;
  java.awt.Image buffer;
  Graphics buffer_gc;
  static final Color[] colors = { new Color(0, 0, 0), new Color(0, 120, 120), new Color(120, 120, 0), new Color(120, 0, 120), new Color(0, 200, 200), new Color(200, 200, 0), new Color(200, 0, 200) };
  
  public svm_toy() {}
  

  class point
  {
    double x;
    
    double y;
    byte value;
    
    point(double paramDouble1, double paramDouble2, byte paramByte)
    {
      x = paramDouble1;
      y = paramDouble2;
      value = paramByte;
    }
  }
  


  Vector<svm_toy.point> point_list = new Vector();
  byte current_value = 1;
  
  public void init()
  {
    setSize(getSize());
    
    final Button localButton1 = new Button("Change");
    Button localButton2 = new Button("Run");
    Button localButton3 = new Button("Clear");
    Button localButton4 = new Button("Save");
    Button localButton5 = new Button("Load");
    final TextField localTextField = new TextField("-t 2 -c 100");
    
    java.awt.BorderLayout localBorderLayout = new java.awt.BorderLayout();
    setLayout(localBorderLayout);
    
    Panel localPanel = new Panel();
    GridBagLayout localGridBagLayout = new GridBagLayout();
    localPanel.setLayout(localGridBagLayout);
    
    java.awt.GridBagConstraints localGridBagConstraints = new java.awt.GridBagConstraints();
    fill = 2;
    weightx = 1.0D;
    gridwidth = 1;
    localGridBagLayout.setConstraints(localButton1, localGridBagConstraints);
    localGridBagLayout.setConstraints(localButton2, localGridBagConstraints);
    localGridBagLayout.setConstraints(localButton3, localGridBagConstraints);
    localGridBagLayout.setConstraints(localButton4, localGridBagConstraints);
    localGridBagLayout.setConstraints(localButton5, localGridBagConstraints);
    weightx = 5.0D;
    gridwidth = 5;
    localGridBagLayout.setConstraints(localTextField, localGridBagConstraints);
    
    localButton1.setBackground(colors[current_value]);
    
    localPanel.add(localButton1);
    localPanel.add(localButton2);
    localPanel.add(localButton3);
    localPanel.add(localButton4);
    localPanel.add(localButton5);
    localPanel.add(localTextField);
    add(localPanel, "South");
    
    localButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
        button_change_clicked();localButton1.setBackground(svm_toy.colors[current_value]);
      } });
    localButton2.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) { button_run_clicked(localTextField.getText()); }
    });
    localButton3.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) { button_clear_clicked(); }
    });
    localButton4.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) { button_save_clicked(localTextField.getText()); }
    });
    localButton5.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) { button_load_clicked(); }
    });
    localTextField.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent) { button_run_clicked(localTextField.getText()); }
    });
    enableEvents(16L);
  }
  
  void draw_point(svm_toy.point paramPoint)
  {
    Color localColor = colors[(value + 3)];
    
    Graphics localGraphics = getGraphics();
    buffer_gc.setColor(localColor);
    buffer_gc.fillRect((int)(x * XLEN), (int)(y * YLEN), 4, 4);
    localGraphics.setColor(localColor);
    localGraphics.fillRect((int)(x * XLEN), (int)(y * YLEN), 4, 4);
  }
  
  void clear_all()
  {
    point_list.removeAllElements();
    if (buffer != null)
    {
      buffer_gc.setColor(colors[0]);
      buffer_gc.fillRect(0, 0, XLEN, YLEN);
    }
    repaint();
  }
  
  void draw_all_points()
  {
    int i = point_list.size();
    for (int j = 0; j < i; j++) {
      draw_point((svm_toy.point)point_list.elementAt(j));
    }
  }
  
  void button_change_clicked() {
    current_value = ((byte)(current_value + 1));
    if (current_value > 3) current_value = 1;
  }
  
  private static double atof(String paramString)
  {
    return Double.valueOf(paramString).doubleValue();
  }
  
  private static int atoi(String paramString)
  {
    return Integer.parseInt(paramString);
  }
  

  void button_run_clicked(String paramString)
  {
    if (point_list.isEmpty()) { return;
    }
    svm_parameter localSvm_parameter = new svm_parameter();
    

    svm_type = 0;
    kernel_type = 2;
    degree = 3;
    gamma = 0.0D;
    coef0 = 0.0D;
    nu = 0.5D;
    cache_size = 40.0D;
    C = 1.0D;
    eps = 0.001D;
    p = 0.1D;
    shrinking = 1;
    probability = 0;
    nr_weight = 0;
    weight_label = new int[0];
    weight = new double[0];
    

    StringTokenizer localStringTokenizer = new StringTokenizer(paramString);
    String[] arrayOfString = new String[localStringTokenizer.countTokens()];
    for (int i = 0; i < arrayOfString.length; i++) {
      arrayOfString[i] = localStringTokenizer.nextToken();
    }
    for (i = 0; i < arrayOfString.length; i++)
    {
      if (arrayOfString[i].charAt(0) != '-') break;
      i++; if (i >= arrayOfString.length)
      {
        System.err.print("unknown option\n");
        break;
      }
      switch (arrayOfString[(i - 1)].charAt(1))
      {
      case 's': 
        svm_type = atoi(arrayOfString[i]);
        break;
      case 't': 
        kernel_type = atoi(arrayOfString[i]);
        break;
      case 'd': 
        degree = atoi(arrayOfString[i]);
        break;
      case 'g': 
        gamma = atof(arrayOfString[i]);
        break;
      case 'r': 
        coef0 = atof(arrayOfString[i]);
        break;
      case 'n': 
        nu = atof(arrayOfString[i]);
        break;
      case 'm': 
        cache_size = atof(arrayOfString[i]);
        break;
      case 'c': 
        C = atof(arrayOfString[i]);
        break;
      case 'e': 
        eps = atof(arrayOfString[i]);
        break;
      case 'p': 
        p = atof(arrayOfString[i]);
        break;
      case 'h': 
        shrinking = atoi(arrayOfString[i]);
        break;
      case 'b': 
        probability = atoi(arrayOfString[i]);
        break;
      case 'w': 
        nr_weight += 1;
        
        Object localObject1 = weight_label;
        weight_label = new int[nr_weight];
        System.arraycopy(localObject1, 0, weight_label, 0, nr_weight - 1);
        


        localObject1 = weight;
        weight = new double[nr_weight];
        System.arraycopy(localObject1, 0, weight, 0, nr_weight - 1);
        

        weight_label[(nr_weight - 1)] = atoi(arrayOfString[(i - 1)].substring(2));
        weight[(nr_weight - 1)] = atof(arrayOfString[i]);
        break;
      case 'f': case 'i': case 'j': case 'k': case 'l': case 'o': case 'q': case 'u': case 'v': default: 
        System.err.print("unknown option\n");
      }
      
    }
    
    libsvm.svm_problem localSvm_problem = new libsvm.svm_problem();
    l = point_list.size();
    y = new double[l];
    
    if (kernel_type != 4) { Object localObject2;
      Object localObject3;
      int n;
      if ((svm_type == 3) || (svm_type == 4))
      {

        if (gamma == 0.0D) gamma = 1.0D;
        x = new svm_node[l][1];
        for (int j = 0; j < l; j++)
        {
          localObject2 = (svm_toy.point)point_list.elementAt(j);
          x[j][0] = new svm_node();
          x[j][0].index = 1;
          x[j][0].value = x;
          y[j] = y;
        }
        

        libsvm.svm_model localSvm_model1 = libsvm.svm.svm_train(localSvm_problem, localSvm_parameter);
        localObject2 = new svm_node[1];
        localObject2[0] = new svm_node();
        0index = 1;
        localObject3 = new int[XLEN];
        
        Graphics localGraphics = getGraphics();
        for (n = 0; n < XLEN; n++)
        {
          0value = (n / XLEN);
          localObject3[n] = ((int)(YLEN * libsvm.svm.svm_predict(localSvm_model1, (svm_node[])localObject2)));
        }
        
        buffer_gc.setColor(colors[0]);
        buffer_gc.drawLine(0, 0, 0, YLEN - 1);
        localGraphics.setColor(colors[0]);
        localGraphics.drawLine(0, 0, 0, YLEN - 1);
        
        n = (int)(p * YLEN);
        for (int i1 = 1; i1 < XLEN; i1++)
        {
          buffer_gc.setColor(colors[0]);
          buffer_gc.drawLine(i1, 0, i1, YLEN - 1);
          localGraphics.setColor(colors[0]);
          localGraphics.drawLine(i1, 0, i1, YLEN - 1);
          
          buffer_gc.setColor(colors[5]);
          localGraphics.setColor(colors[5]);
          buffer_gc.drawLine(i1 - 1, localObject3[(i1 - 1)], i1, localObject3[i1]);
          localGraphics.drawLine(i1 - 1, localObject3[(i1 - 1)], i1, localObject3[i1]);
          
          if (svm_type == 3)
          {
            buffer_gc.setColor(colors[2]);
            localGraphics.setColor(colors[2]);
            buffer_gc.drawLine(i1 - 1, localObject3[(i1 - 1)] + n, i1, localObject3[i1] + n);
            localGraphics.drawLine(i1 - 1, localObject3[(i1 - 1)] + n, i1, localObject3[i1] + n);
            
            buffer_gc.setColor(colors[2]);
            localGraphics.setColor(colors[2]);
            buffer_gc.drawLine(i1 - 1, localObject3[(i1 - 1)] - n, i1, localObject3[i1] - n);
            localGraphics.drawLine(i1 - 1, localObject3[(i1 - 1)] - n, i1, localObject3[i1] - n);
          }
        }
      }
      else
      {
        if (gamma == 0.0D) gamma = 0.5D;
        x = new svm_node[l][2];
        for (int k = 0; k < l; k++)
        {
          localObject2 = (svm_toy.point)point_list.elementAt(k);
          x[k][0] = new svm_node();
          x[k][0].index = 1;
          x[k][0].value = x;
          x[k][1] = new svm_node();
          x[k][1].index = 2;
          x[k][1].value = y;
          y[k] = value;
        }
        

        libsvm.svm_model localSvm_model2 = libsvm.svm.svm_train(localSvm_problem, localSvm_parameter);
        localObject2 = new svm_node[2];
        localObject2[0] = new svm_node();
        localObject2[1] = new svm_node();
        0index = 1;
        1index = 2;
        
        localObject3 = getGraphics();
        for (int m = 0; m < XLEN; m++)
          for (n = 0; n < YLEN; n++) {
            0value = (m / XLEN);
            1value = (n / YLEN);
            double d = libsvm.svm.svm_predict(localSvm_model2, (svm_node[])localObject2);
            if ((svm_type == 2) && (d < 0.0D)) d = 2.0D;
            buffer_gc.setColor(colors[((int)d)]);
            ((Graphics)localObject3).setColor(colors[((int)d)]);
            buffer_gc.drawLine(m, n, m, n);
            ((Graphics)localObject3).drawLine(m, n, m, n);
          }
      }
    }
    draw_all_points();
  }
  
  void button_clear_clicked()
  {
    clear_all();
  }
  
  void button_save_clicked(String paramString)
  {
    FileDialog localFileDialog = new FileDialog(new java.awt.Frame(), "Save", 1);
    localFileDialog.setVisible(true);
    String str = localFileDialog.getDirectory() + localFileDialog.getFile();
    if (str == null) return;
    try {
      java.io.DataOutputStream localDataOutputStream = new java.io.DataOutputStream(new java.io.BufferedOutputStream(new java.io.FileOutputStream(str)));
      
      int i = 0;
      int j = paramString.indexOf("-s ");
      if (j != -1)
      {
        StringTokenizer localStringTokenizer = new StringTokenizer(paramString.substring(j + 2).trim());
        i = atoi(localStringTokenizer.nextToken());
      }
      
      int k = point_list.size();
      int m; svm_toy.point localPoint; if ((i == 3) || (i == 4))
      {
        for (m = 0; m < k; m++)
        {
          localPoint = (svm_toy.point)point_list.elementAt(m);
          localDataOutputStream.writeBytes(y + " 1:" + x + "\n");
        }
        
      }
      else {
        for (m = 0; m < k; m++)
        {
          localPoint = (svm_toy.point)point_list.elementAt(m);
          localDataOutputStream.writeBytes(value + " 1:" + x + " 2:" + y + "\n");
        }
      }
      localDataOutputStream.close();
    } catch (java.io.IOException localIOException) { System.err.print(localIOException);
    }
  }
  
  void button_load_clicked() {
    FileDialog localFileDialog = new FileDialog(new java.awt.Frame(), "Load", 0);
    localFileDialog.setVisible(true);
    String str1 = localFileDialog.getDirectory() + localFileDialog.getFile();
    if (str1 == null) return;
    clear_all();
    try {
      java.io.BufferedReader localBufferedReader = new java.io.BufferedReader(new java.io.FileReader(str1));
      String str2;
      while ((str2 = localBufferedReader.readLine()) != null)
      {
        StringTokenizer localStringTokenizer = new StringTokenizer(str2, " \t\n\r\f:");
        if (localStringTokenizer.countTokens() == 5)
        {
          byte b = (byte)atoi(localStringTokenizer.nextToken());
          localStringTokenizer.nextToken();
          double d2 = atof(localStringTokenizer.nextToken());
          localStringTokenizer.nextToken();
          double d4 = atof(localStringTokenizer.nextToken());
          point_list.addElement(new svm_toy.point(d2, d4, b));
        } else {
          if (localStringTokenizer.countTokens() != 3)
            break;
          double d1 = atof(localStringTokenizer.nextToken());
          localStringTokenizer.nextToken();
          double d3 = atof(localStringTokenizer.nextToken());
          point_list.addElement(new svm_toy.point(d3, d1, current_value));
        }
      }
      
      localBufferedReader.close();
    } catch (java.io.IOException localIOException) { System.err.print(localIOException); }
    draw_all_points();
  }
  
  protected void processMouseEvent(java.awt.event.MouseEvent paramMouseEvent)
  {
    if (paramMouseEvent.getID() == 501)
    {
      if ((paramMouseEvent.getX() >= XLEN) || (paramMouseEvent.getY() >= YLEN)) return;
      svm_toy.point localPoint = new svm_toy.point(paramMouseEvent.getX() / XLEN, paramMouseEvent.getY() / YLEN, current_value);
      

      point_list.addElement(localPoint);
      draw_point(localPoint);
    }
  }
  

  public void paint(Graphics paramGraphics)
  {
    if (buffer == null) {
      buffer = createImage(XLEN, YLEN);
      buffer_gc = buffer.getGraphics();
      buffer_gc.setColor(colors[0]);
      buffer_gc.fillRect(0, 0, XLEN, YLEN);
    }
    paramGraphics.drawImage(buffer, 0, 0, this);
  }
  
  public java.awt.Dimension getPreferredSize() { return new java.awt.Dimension(XLEN, YLEN + 50); }
  
  public void setSize(java.awt.Dimension paramDimension) { setSize(width, height); }
  
  public void setSize(int paramInt1, int paramInt2) { super.setSize(paramInt1, paramInt2);
    XLEN = paramInt1;
    YLEN = (paramInt2 - 50);
    clear_all();
  }
  
  public static void main(String[] paramArrayOfString)
  {
    new AppletFrame("svm_toy", new svm_toy(), 500, 550);
  }
}
