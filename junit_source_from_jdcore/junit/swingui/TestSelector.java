package junit.swingui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import junit.runner.Sorter;
import junit.runner.Sorter.Swapper;
import junit.runner.TestCollector;

class TestSelector
  extends JDialog
{
  private JButton fCancel;
  private JButton fOk;
  private JList fList;
  private JScrollPane fScrolledList;
  private JLabel fDescription;
  private String fSelectedItem;
  /* Error */
  public TestSelector(java.awt.Frame parent, TestCollector testCollector)
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: iconst_1
    //   3: invokespecial 21	javax/swing/JDialog:<init>	(Ljava/awt/Frame;Z)V
    //   6: aload_0
    //   7: sipush 350
    //   10: sipush 300
    //   13: invokevirtual 27	java/awt/Component:setSize	(II)V
    //   16: aload_0
    //   17: iconst_0
    //   18: invokevirtual 33	java/awt/Dialog:setResizable	(Z)V
    //   21: aload_0
    //   22: aload_1
    //   23: invokevirtual 39	java/awt/Window:setLocationRelativeTo	(Ljava/awt/Component;)V
    //   26: goto +8 -> 34
    //   29: astore_3
    //   30: aload_0
    //   31: invokestatic 42	junit/swingui/TestSelector:centerWindow	(Ljava/awt/Component;)V
    //   34: aload_0
    //   35: ldc 44
    //   37: invokevirtual 48	java/awt/Dialog:setTitle	(Ljava/lang/String;)V
    //   40: aconst_null
    //   41: astore_3
    //   42: aload_1
    //   43: iconst_3
    //   44: invokestatic 54	java/awt/Cursor:getPredefinedCursor	(I)Ljava/awt/Cursor;
    //   47: invokevirtual 58	java/awt/Window:setCursor	(Ljava/awt/Cursor;)V
    //   50: aload_0
    //   51: aload_2
    //   52: invokespecial 62	junit/swingui/TestSelector:createTestList	(Ljunit/runner/TestCollector;)Ljava/util/Vector;
    //   55: astore_3
    //   56: goto +11 -> 67
    //   59: astore 5
    //   61: jsr +12 -> 73
    //   64: aload 5
    //   66: athrow
    //   67: jsr +6 -> 73
    //   70: goto +14 -> 84
    //   73: astore 4
    //   75: aload_1
    //   76: invokestatic 66	java/awt/Cursor:getDefaultCursor	()Ljava/awt/Cursor;
    //   79: invokevirtual 58	java/awt/Window:setCursor	(Ljava/awt/Cursor;)V
    //   82: ret 4
    //   84: aload_0
    //   85: new 68	javax/swing/JList
    //   88: dup
    //   89: aload_3
    //   90: invokespecial 71	javax/swing/JList:<init>	(Ljava/util/Vector;)V
    //   93: putfield 73	junit/swingui/TestSelector:fList	Ljavax/swing/JList;
    //   96: aload_0
    //   97: getfield 73	junit/swingui/TestSelector:fList	Ljavax/swing/JList;
    //   100: iconst_0
    //   101: invokevirtual 77	javax/swing/JList:setSelectionMode	(I)V
    //   104: aload_0
    //   105: getfield 73	junit/swingui/TestSelector:fList	Ljavax/swing/JList;
    //   108: new 79	junit/swingui/TestSelector$TestCellRenderer
    //   111: dup
    //   112: invokespecial 82	junit/swingui/TestSelector$TestCellRenderer:<init>	()V
    //   115: invokevirtual 86	javax/swing/JList:setCellRenderer	(Ljavax/swing/ListCellRenderer;)V
    //   118: aload_0
    //   119: new 88	javax/swing/JScrollPane
    //   122: dup
    //   123: aload_0
    //   124: getfield 73	junit/swingui/TestSelector:fList	Ljavax/swing/JList;
    //   127: invokespecial 90	javax/swing/JScrollPane:<init>	(Ljava/awt/Component;)V
    //   130: putfield 92	junit/swingui/TestSelector:fScrolledList	Ljavax/swing/JScrollPane;
    //   133: aload_0
    //   134: new 94	javax/swing/JButton
    //   137: dup
    //   138: ldc 96
    //   140: invokespecial 98	javax/swing/JButton:<init>	(Ljava/lang/String;)V
    //   143: putfield 100	junit/swingui/TestSelector:fCancel	Ljavax/swing/JButton;
    //   146: aload_0
    //   147: new 102	javax/swing/JLabel
    //   150: dup
    //   151: ldc 104
    //   153: invokespecial 105	javax/swing/JLabel:<init>	(Ljava/lang/String;)V
    //   156: putfield 107	junit/swingui/TestSelector:fDescription	Ljavax/swing/JLabel;
    //   159: aload_0
    //   160: new 94	javax/swing/JButton
    //   163: dup
    //   164: ldc 109
    //   166: invokespecial 98	javax/swing/JButton:<init>	(Ljava/lang/String;)V
    //   169: putfield 111	junit/swingui/TestSelector:fOk	Ljavax/swing/JButton;
    //   172: aload_0
    //   173: getfield 111	junit/swingui/TestSelector:fOk	Ljavax/swing/JButton;
    //   176: iconst_0
    //   177: invokevirtual 116	javax/swing/AbstractButton:setEnabled	(Z)V
    //   180: aload_0
    //   181: invokevirtual 120	javax/swing/JDialog:getRootPane	()Ljavax/swing/JRootPane;
    //   184: aload_0
    //   185: getfield 111	junit/swingui/TestSelector:fOk	Ljavax/swing/JButton;
    //   188: invokevirtual 126	javax/swing/JRootPane:setDefaultButton	(Ljavax/swing/JButton;)V
    //   191: aload_0
    //   192: invokespecial 129	junit/swingui/TestSelector:defineLayout	()V
    //   195: aload_0
    //   196: invokespecial 132	junit/swingui/TestSelector:addListeners	()V
    //   199: return
    // Line number table:
    //   Java source line #84	-> byte code offset #0
    //   Java source line #85	-> byte code offset #6
    //   Java source line #86	-> byte code offset #16
    //   Java source line #89	-> byte code offset #21
    //   Java source line #90	-> byte code offset #29
    //   Java source line #91	-> byte code offset #30
    //   Java source line #93	-> byte code offset #34
    //   Java source line #95	-> byte code offset #40
    //   Java source line #97	-> byte code offset #42
    //   Java source line #98	-> byte code offset #50
    //   Java source line #99	-> byte code offset #59
    //   Java source line #100	-> byte code offset #75
    //   Java source line #96	-> byte code offset #82
    //   Java source line #102	-> byte code offset #84
    //   Java source line #103	-> byte code offset #96
    //   Java source line #104	-> byte code offset #104
    //   Java source line #105	-> byte code offset #118
    //   Java source line #107	-> byte code offset #133
    //   Java source line #108	-> byte code offset #146
    //   Java source line #109	-> byte code offset #159
    //   Java source line #110	-> byte code offset #172
    //   Java source line #111	-> byte code offset #180
    //   Java source line #113	-> byte code offset #191
    //   Java source line #114	-> byte code offset #195
    //   Java source line #115	-> byte code offset #199
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	200	0	this	TestSelector
    //   0	200	1	parent	java.awt.Frame
    //   0	200	2	testCollector	TestCollector
    //   29	2	3	e	NoSuchMethodError
    //   41	49	3	list	Vector
    //   73	1	4	localObject1	Object
    //   59	6	5	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   21	29	29	java/lang/NoSuchMethodError
    //   42	59	59	finally
  }
  
  static class TestCellRenderer
    extends DefaultListCellRenderer
  {
    Icon fLeafIcon;
    Icon fSuiteIcon;
    
    public TestCellRenderer()
    {
      fLeafIcon = UIManager.getIcon("Tree.leafIcon");
      fSuiteIcon = UIManager.getIcon("Tree.closedIcon");
    }
    

    public Component getListCellRendererComponent(JList list, Object value, int modelIndex, boolean isSelected, boolean cellHasFocus)
    {
      Component c = super.getListCellRendererComponent(list, value, modelIndex, isSelected, cellHasFocus);
      String displayString = displayString((String)value);
      
      if (displayString.startsWith("AllTests")) {
        setIcon(fSuiteIcon);
      } else {
        setIcon(fLeafIcon);
      }
      setText(displayString);
      return c;
    }
    
    public static String displayString(String className) {
      int typeIndex = className.lastIndexOf('.');
      if (typeIndex < 0)
        return className;
      return className.substring(typeIndex + 1) + " - " + className.substring(0, typeIndex);
    }
    
    public static boolean matchesKey(String s, char ch) {
      return ch == Character.toUpperCase(s.charAt(typeIndex(s)));
    }
    
    private static int typeIndex(String s) {
      int typeIndex = s.lastIndexOf('.');
      int i = 0;
      if (typeIndex > 0)
        i = typeIndex + 1;
      return i;
    }
  }
  
  protected class DoubleClickListener extends MouseAdapter { protected DoubleClickListener() {}
    
    public void mouseClicked(MouseEvent e) { if (e.getClickCount() == 2)
        okSelected();
    }
  }
  
  protected class KeySelectListener extends KeyAdapter {
    protected KeySelectListener() {}
    
    public void keyTyped(KeyEvent e) { keySelectTestClass(e.getKeyChar()); }
  }
  


































  public static void centerWindow(Component c)
  {
    Dimension paneSize = c.getSize();
    Dimension screenSize = c.getToolkit().getScreenSize();
    c.setLocation((width - width) / 2, (height - height) / 2);
  }
  
  private void addListeners() {
    fCancel.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          dispose();
        }
        

      });
    fOk.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          okSelected();
        }
        

      });
    fList.addMouseListener(new DoubleClickListener());
    fList.addKeyListener(new KeySelectListener());
    fList.addListSelectionListener(
      new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
          checkEnableOK(e);
        }
        

      });
    addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          dispose();
        }
      });
  }
  
  private void defineLayout()
  {
    getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints labelConstraints = new GridBagConstraints();
    gridx = 0;gridy = 0;
    gridwidth = 1;gridheight = 1;
    fill = 1;
    anchor = 17;
    weightx = 1.0D;
    weighty = 0.0D;
    insets = new Insets(8, 8, 0, 8);
    getContentPane().add(fDescription, labelConstraints);
    
    GridBagConstraints listConstraints = new GridBagConstraints();
    gridx = 0;gridy = 1;
    gridwidth = 4;gridheight = 1;
    fill = 1;
    anchor = 10;
    weightx = 1.0D;
    weighty = 1.0D;
    insets = new Insets(8, 8, 8, 8);
    getContentPane().add(fScrolledList, listConstraints);
    
    GridBagConstraints okConstraints = new GridBagConstraints();
    gridx = 2;gridy = 2;
    gridwidth = 1;gridheight = 1;
    anchor = 13;
    insets = new Insets(0, 8, 8, 8);
    getContentPane().add(fOk, okConstraints);
    

    GridBagConstraints cancelConstraints = new GridBagConstraints();
    gridx = 3;gridy = 2;
    gridwidth = 1;gridheight = 1;
    anchor = 13;
    insets = new Insets(0, 8, 8, 8);
    getContentPane().add(fCancel, cancelConstraints);
  }
  
  public void checkEnableOK(ListSelectionEvent e) {
    fOk.setEnabled(fList.getSelectedIndex() != -1);
  }
  
  public void okSelected() {
    fSelectedItem = ((String)fList.getSelectedValue());
    dispose();
  }
  
  public boolean isEmpty() {
    return fList.getModel().getSize() == 0;
  }
  
  public void keySelectTestClass(char ch) {
    ListModel model = fList.getModel();
    if (!Character.isJavaIdentifierStart(ch))
      return;
    for (int i = 0; i < model.getSize(); i++) {
      String s = (String)model.getElementAt(i);
      if (TestCellRenderer.matchesKey(s, Character.toUpperCase(ch))) {
        fList.setSelectedIndex(i);
        fList.ensureIndexIsVisible(i);
        return;
      }
    }
    Toolkit.getDefaultToolkit().beep();
  }
  
  public String getSelectedItem() {
    return fSelectedItem;
  }
  
  private Vector createTestList(TestCollector collector) {
    Enumeration each = collector.collectTests();
    Vector v = new Vector(200);
    Vector displayVector = new Vector(v.size());
    while (each.hasMoreElements()) {
      String s = (String)each.nextElement();
      v.addElement(s);
      displayVector.addElement(TestCellRenderer.displayString(s));
    }
    if (v.size() > 0)
      Sorter.sortStrings(displayVector, 0, displayVector.size() - 1, new ParallelSwapper(v));
    return v;
  }
  
  private class ParallelSwapper
    implements Sorter.Swapper {
    Vector fOther;
    
    ParallelSwapper(Vector other) { fOther = other; }
    
    public void swap(Vector values, int left, int right) {
      Object tmp = values.elementAt(left);
      values.setElementAt(values.elementAt(right), left);
      values.setElementAt(tmp, right);
      Object tmp2 = fOther.elementAt(left);
      fOther.setElementAt(fOther.elementAt(right), left);
      fOther.setElementAt(tmp2, right);
    }
  }
}
