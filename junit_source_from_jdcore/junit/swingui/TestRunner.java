package junit.swingui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.runner.BaseTestRunner;
import junit.runner.FailureDetailView;

public class TestRunner extends BaseTestRunner implements TestRunContext
{
  private static final int GAP = 4;
  private static final int HISTORY_LENGTH = 5;
  protected JFrame fFrame;
  private Thread fRunner;
  private TestResult fTestResult;
  private JComboBox fSuiteCombo;
  private ProgressBar fProgressIndicator;
  private DefaultListModel fFailures;
  private JLabel fLogo;
  private CounterPanel fCounterPanel;
  private JButton fRun;
  private JButton fQuitButton;
  private JButton fRerunButton;
  private StatusLine fStatusLine;
  private FailureDetailView fFailureView;
  private JTabbedPane fTestViewTab;
  private javax.swing.JCheckBox fUseLoadingRunner;
  private Vector fTestRunViews = new Vector();
  
  private static final String TESTCOLLECTOR_KEY = "TestCollectorClass";
  private static final String FAILUREDETAILVIEW_KEY = "FailureViewClass";
  
  public TestRunner() {}
  
  public static void main(String[] args)
  {
    new TestRunner().start(args);
  }
  
  public static void run(Class test) {
    String[] args = { test.getName() };
    main(args);
  }
  
  public void testFailed(int status, Test test, Throwable t) {
    SwingUtilities.invokeLater(
      new Runnable() { private final int val$status;
        
        public void run() { switch (val$status) {
          case 1: 
            fCounterPanel.setErrorValue(fTestResult.errorCount());
            TestRunner.this.appendFailure(val$test, val$t);
            break;
          case 2: 
            fCounterPanel.setFailureValue(fTestResult.failureCount());
            TestRunner.this.appendFailure(val$test, val$t);
          }
          
        }
      });
  }
  
  public void testStarted(String testName)
  {
    postInfo("Running: " + testName);
  }
  
  public void testEnded(String stringName) {
    synchUI();
    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          if (fTestResult != null) {
            fCounterPanel.setRunValue(fTestResult.runCount());
            fProgressIndicator.step(fTestResult.runCount(), fTestResult.wasSuccessful());
          }
        }
      });
  }
  
  public void setSuite(String suiteName)
  {
    fSuiteCombo.getEditor().setItem(suiteName);
  }
  
  private void addToHistory(String suite) {
    for (int i = 0; i < fSuiteCombo.getItemCount(); i++) {
      if (suite.equals(fSuiteCombo.getItemAt(i))) {
        fSuiteCombo.removeItemAt(i);
        fSuiteCombo.insertItemAt(suite, 0);
        fSuiteCombo.setSelectedIndex(0);
        return;
      }
    }
    fSuiteCombo.insertItemAt(suite, 0);
    fSuiteCombo.setSelectedIndex(0);
    pruneHistory();
  }
  
  private void pruneHistory() {
    int historyLength = BaseTestRunner.getPreference("maxhistory", 5);
    if (historyLength < 1)
      historyLength = 1;
    for (int i = fSuiteCombo.getItemCount() - 1; i > historyLength - 1; i--)
      fSuiteCombo.removeItemAt(i);
  }
  
  private void appendFailure(Test test, Throwable t) {
    fFailures.addElement(new junit.framework.TestFailure(test, t));
    if (fFailures.size() == 1)
      revealFailure(test);
  }
  
  private void revealFailure(Test test) {
    for (Enumeration e = fTestRunViews.elements(); e.hasMoreElements();) {
      TestRunView v = (TestRunView)e.nextElement();
      v.revealFailure(test);
    }
  }
  
  protected void aboutToStart(Test testSuite) {
    for (Enumeration e = fTestRunViews.elements(); e.hasMoreElements();) {
      TestRunView v = (TestRunView)e.nextElement();
      v.aboutToStart(testSuite, fTestResult);
    }
  }
  
  protected void runFinished(Test testSuite) {
    SwingUtilities.invokeLater(
      new Runnable() { private final Test val$testSuite;
        
        public void run() { for (Enumeration e = fTestRunViews.elements(); e.hasMoreElements();) {
            TestRunView v = (TestRunView)e.nextElement();
            v.runFinished(val$testSuite, fTestResult);
          }
        }
      });
  }
  
  protected CounterPanel createCounterPanel()
  {
    return new CounterPanel();
  }
  
  protected JPanel createFailedPanel() {
    JPanel failedPanel = new JPanel(new java.awt.GridLayout(0, 1, 0, 2));
    fRerunButton = new JButton("Run");
    fRerunButton.setEnabled(false);
    fRerunButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          TestRunner.this.rerun();
        }
        
      });
    failedPanel.add(fRerunButton);
    return failedPanel;
  }
  
  protected FailureDetailView createFailureDetailView() {
    String className = BaseTestRunner.getPreference("FailureViewClass");
    if (className != null) {
      Class viewClass = null;
      try {
        viewClass = Class.forName(className);
        return (FailureDetailView)viewClass.newInstance();
      } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(fFrame, "Could not create Failure DetailView - using default view");
      }
    }
    return new DefaultFailureDetailView();
  }
  

  private final Test val$test;
  private final Throwable val$t;
  protected JMenu createJUnitMenu()
  {
    JMenu menu = new JMenu("JUnit");
    menu.setMnemonic('J');
    javax.swing.JMenuItem mi1 = new javax.swing.JMenuItem("About...");
    mi1.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          TestRunner.this.about();
        }
        
      });
    mi1.setMnemonic('A');
    menu.add(mi1);
    
    menu.addSeparator();
    javax.swing.JMenuItem mi2 = new javax.swing.JMenuItem(" Exit ");
    mi2.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          terminate();
        }
        
      });
    mi2.setMnemonic('x');
    menu.add(mi2);
    
    return menu;
  }
  
  protected JFrame createFrame() {
    JFrame frame = new JFrame("JUnit");
    java.awt.Image icon = loadFrameIcon();
    if (icon != null)
      frame.setIconImage(icon);
    frame.getContentPane().setLayout(new java.awt.BorderLayout(0, 0));
    
    frame.addWindowListener(
      new java.awt.event.WindowAdapter() {
        public void windowClosing(java.awt.event.WindowEvent e) {
          terminate();
        }
        
      });
    return frame;
  }
  
  protected JLabel createLogo()
  {
    javax.swing.Icon icon = getIconResource(BaseTestRunner.class, "logo.gif");
    JLabel label; JLabel label; if (icon != null) {
      label = new JLabel(icon);
    } else
      label = new JLabel("JV");
    label.setToolTipText("JUnit Version " + junit.runner.Version.id());
    return label;
  }
  
  protected void createMenus(javax.swing.JMenuBar mb) {
    mb.add(createJUnitMenu());
  }
  
  protected javax.swing.JCheckBox createUseLoaderCheckBox() {
    boolean useLoader = useReloadingTestSuiteLoader();
    javax.swing.JCheckBox box = new javax.swing.JCheckBox("Reload classes every run", useLoader);
    box.setToolTipText("Use a custom class loader to reload the classes for every run");
    if (BaseTestRunner.inVAJava())
      box.setVisible(false);
    return box;
  }
  

  protected JButton createQuitButton()
  {
    JButton quit = new JButton(" Exit ");
    quit.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          terminate();
        }
        
      });
    return quit;
  }
  
  protected JButton createRunButton() {
    JButton run = new JButton("Run");
    run.setEnabled(true);
    run.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          runSuite();
        }
        
      });
    return run;
  }
  
  protected Component createBrowseButton() {
    JButton browse = new JButton("...");
    browse.setToolTipText("Select a Test class");
    browse.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          browseTestClasses();
        }
        
      });
    return browse;
  }
  
  protected StatusLine createStatusLine() {
    return new StatusLine(380);
  }
  
  protected JComboBox createSuiteCombo() {
    JComboBox combo = new JComboBox();
    combo.setEditable(true);
    combo.setLightWeightPopupEnabled(false);
    
    combo.getEditor().getEditorComponent().addKeyListener(
      new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent e) {
          textChanged();
          if (e.getKeyChar() == '\n') {
            runSuite();
          }
        }
      });
    try {
      loadHistory(combo);
    }
    catch (java.io.IOException localIOException) {}
    
    combo.addItemListener(
      new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent event) {
          if (event.getStateChange() == 1) {
            textChanged();
          }
          
        }
      });
    return combo;
  }
  
  protected JTabbedPane createTestRunViews() {
    JTabbedPane pane = new JTabbedPane(3);
    
    FailureRunView lv = new FailureRunView(this);
    fTestRunViews.addElement(lv);
    lv.addTab(pane);
    
    TestHierarchyRunView tv = new TestHierarchyRunView(this);
    fTestRunViews.addElement(tv);
    tv.addTab(pane);
    
    pane.addChangeListener(
      new javax.swing.event.ChangeListener() {
        public void stateChanged(javax.swing.event.ChangeEvent e) {
          testViewChanged();
        }
        
      });
    return pane;
  }
  
  public void testViewChanged() {
    TestRunView view = (TestRunView)fTestRunViews.elementAt(fTestViewTab.getSelectedIndex());
    view.activate();
  }
  
  protected TestResult createTestResult() {
    return new TestResult();
  }
  
  protected JFrame createUI(String suiteName) {
    JFrame frame = createFrame();
    javax.swing.JMenuBar mb = new javax.swing.JMenuBar();
    createMenus(mb);
    frame.setJMenuBar(mb);
    
    JLabel suiteLabel = new JLabel("Test class name:");
    fSuiteCombo = createSuiteCombo();
    fRun = createRunButton();
    frame.getRootPane().setDefaultButton(fRun);
    Component browseButton = createBrowseButton();
    
    fUseLoadingRunner = createUseLoaderCheckBox();
    fProgressIndicator = new ProgressBar();
    fCounterPanel = createCounterPanel();
    
    fFailures = new DefaultListModel();
    
    fTestViewTab = createTestRunViews();
    JPanel failedPanel = createFailedPanel();
    
    fFailureView = createFailureDetailView();
    javax.swing.JScrollPane tracePane = new javax.swing.JScrollPane(fFailureView.getComponent(), 22, 32);
    
    fStatusLine = createStatusLine();
    fQuitButton = createQuitButton();
    fLogo = createLogo();
    
    JPanel panel = new JPanel(new java.awt.GridBagLayout());
    
    addGrid(panel, suiteLabel, 0, 0, 2, 2, 1.0D, 17);
    addGrid(panel, fSuiteCombo, 0, 1, 1, 2, 1.0D, 17);
    addGrid(panel, browseButton, 1, 1, 1, 0, 0.0D, 17);
    addGrid(panel, fRun, 2, 1, 1, 2, 0.0D, 10);
    
    addGrid(panel, fUseLoadingRunner, 0, 2, 3, 0, 1.0D, 17);
    


    addGrid(panel, fProgressIndicator, 0, 3, 2, 2, 1.0D, 17);
    addGrid(panel, fLogo, 2, 3, 1, 0, 0.0D, 11);
    
    addGrid(panel, fCounterPanel, 0, 4, 2, 0, 0.0D, 17);
    addGrid(panel, new javax.swing.JSeparator(), 0, 5, 2, 2, 1.0D, 17);
    addGrid(panel, new JLabel("Results:"), 0, 6, 2, 2, 1.0D, 17);
    
    javax.swing.JSplitPane splitter = new javax.swing.JSplitPane(0, fTestViewTab, tracePane);
    addGrid(panel, splitter, 0, 7, 2, 1, 1.0D, 17);
    
    addGrid(panel, failedPanel, 2, 7, 1, 2, 0.0D, 11);
    
    addGrid(panel, fStatusLine, 0, 9, 2, 2, 1.0D, 10);
    addGrid(panel, fQuitButton, 2, 9, 1, 2, 0.0D, 10);
    
    frame.setContentPane(panel);
    frame.pack();
    frame.setLocation(200, 200);
    return frame;
  }
  
  private void addGrid(JPanel p, Component co, int x, int y, int w, int fill, double wx, int anchor) {
    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
    gridx = x;gridy = y;
    gridwidth = w;
    anchor = anchor;
    weightx = wx;
    fill = fill;
    if ((fill == 1) || (fill == 3))
      weighty = 1.0D;
    insets = new java.awt.Insets(y == 0 ? 10 : 0, x == 0 ? 10 : 4, 4, 4);
    p.add(co, c);
  }
  
  protected String getSuiteText() {
    if (fSuiteCombo == null)
      return "";
    return (String)fSuiteCombo.getEditor().getItem();
  }
  
  public javax.swing.ListModel getFailures() {
    return fFailures;
  }
  
  public void insertUpdate(javax.swing.event.DocumentEvent event) {
    textChanged();
  }
  
  public void browseTestClasses() {
    junit.runner.TestCollector collector = createTestCollector();
    TestSelector selector = new TestSelector(fFrame, collector);
    if (selector.isEmpty()) {
      javax.swing.JOptionPane.showMessageDialog(fFrame, "No Test Cases found.\nCheck that the configured 'TestCollector' is supported on this platform.");
      return;
    }
    selector.show();
    String className = selector.getSelectedItem();
    if (className != null)
      setSuite(className);
  }
  
  junit.runner.TestCollector createTestCollector() {
    String className = BaseTestRunner.getPreference("TestCollectorClass");
    if (className != null) {
      Class collectorClass = null;
      try {
        collectorClass = Class.forName(className);
        return (junit.runner.TestCollector)collectorClass.newInstance();
      } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(fFrame, "Could not create TestCollector - using default collector");
      }
    }
    return new junit.runner.SimpleTestCollector();
  }
  
  private java.awt.Image loadFrameIcon() {
    javax.swing.ImageIcon icon = (javax.swing.ImageIcon)getIconResource(BaseTestRunner.class, "smalllogo.gif");
    if (icon != null)
      return icon.getImage();
    return null;
  }
  
  /* Error */
  private void loadHistory(JComboBox combo)
    throws java.io.IOException
  {
    // Byte code:
    //   0: new 790	java/io/BufferedReader
    //   3: dup
    //   4: new 792	java/io/FileReader
    //   7: dup
    //   8: aload_0
    //   9: invokespecial 796	junit/swingui/TestRunner:getSettingsFile	()Ljava/io/File;
    //   12: invokespecial 799	java/io/FileReader:<init>	(Ljava/io/File;)V
    //   15: invokespecial 802	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   18: astore_2
    //   19: iconst_0
    //   20: istore_3
    //   21: goto +12 -> 33
    //   24: aload_1
    //   25: aload 4
    //   27: invokevirtual 805	javax/swing/JComboBox:addItem	(Ljava/lang/Object;)V
    //   30: iinc 3 1
    //   33: aload_2
    //   34: invokevirtual 808	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   37: dup
    //   38: astore 4
    //   40: ifnonnull -16 -> 24
    //   43: iload_3
    //   44: ifle +19 -> 63
    //   47: aload_1
    //   48: iconst_0
    //   49: invokevirtual 169	javax/swing/JComboBox:setSelectedIndex	(I)V
    //   52: goto +11 -> 63
    //   55: astore 6
    //   57: jsr +12 -> 69
    //   60: aload 6
    //   62: athrow
    //   63: jsr +6 -> 69
    //   66: goto +11 -> 77
    //   69: astore 5
    //   71: aload_2
    //   72: invokevirtual 811	java/io/BufferedReader:close	()V
    //   75: ret 5
    //   77: return
    // Line number table:
    //   Java source line #490	-> byte code offset #0
    //   Java source line #491	-> byte code offset #19
    //   Java source line #494	-> byte code offset #21
    //   Java source line #495	-> byte code offset #24
    //   Java source line #496	-> byte code offset #30
    //   Java source line #494	-> byte code offset #33
    //   Java source line #498	-> byte code offset #43
    //   Java source line #499	-> byte code offset #47
    //   Java source line #501	-> byte code offset #55
    //   Java source line #502	-> byte code offset #71
    //   Java source line #492	-> byte code offset #75
    //   Java source line #504	-> byte code offset #77
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	78	0	this	TestRunner
    //   0	78	1	combo	JComboBox
    //   18	54	2	br	java.io.BufferedReader
    //   20	24	3	itemCount	int
    //   24	2	4	line	String
    //   38	3	4	line	String
    //   69	1	5	localObject1	Object
    //   55	6	6	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   21	55	55	finally
  }
  
  private java.io.File getSettingsFile()
  {
    String home = System.getProperty("user.home");
    return new java.io.File(home, ".junitsession");
  }
  
  private void postInfo(String message) {
    SwingUtilities.invokeLater(
      new Runnable() { private final String val$message;
        
        public void run() { TestRunner.this.showInfo(val$message); }
      });
  }
  

  private void postStatus(String status)
  {
    SwingUtilities.invokeLater(
      new Runnable() { private final String val$status;
        
        public void run() { TestRunner.this.showStatus(val$status); }
      });
  }
  

  public void removeUpdate(javax.swing.event.DocumentEvent event)
  {
    textChanged();
  }
  
  private void rerun() {
    TestRunView view = (TestRunView)fTestRunViews.elementAt(fTestViewTab.getSelectedIndex());
    Test rerunTest = view.getSelectedTest();
    if (rerunTest != null)
      rerunTest(rerunTest);
  }
  
  private void rerunTest(Test test) {
    if (!(test instanceof TestCase)) {
      showInfo("Could not reload " + test.toString());
      return;
    }
    Test reloadedTest = null;
    TestCase rerunTest = (TestCase)test;
    try
    {
      Class reloadedTestClass = getLoader().reload(test.getClass());
      reloadedTest = junit.framework.TestSuite.createTest(reloadedTestClass, rerunTest.getName());
    } catch (Exception e) {
      showInfo("Could not reload " + test.toString());
      return;
    }
    TestResult result = new TestResult();
    reloadedTest.run(result);
    
    String message = reloadedTest.toString();
    if (result.wasSuccessful()) {
      showInfo(message + " was successful");
    } else if (result.errorCount() == 1) {
      showStatus(message + " had an error");
    } else
      showStatus(message + " had a failure");
  }
  
  protected void reset() {
    fCounterPanel.reset();
    fProgressIndicator.reset();
    fRerunButton.setEnabled(false);
    fFailureView.clear();
    fFailures.clear();
  }
  
  protected void runFailed(String message) {
    showStatus(message);
    fRun.setText("Run");
    fRunner = null;
  }
  
  public synchronized void runSuite() {
    if (fRunner != null) {
      fTestResult.stop();
    } else {
      setLoading(shouldReload());
      reset();
      showInfo("Load Test Case...");
      String suiteName = getSuiteText();
      Test testSuite = getTest(suiteName);
      if (testSuite != null) {
        addToHistory(suiteName);
        doRunTest(testSuite);
      }
    }
  }
  
  private boolean shouldReload() {
    return (!BaseTestRunner.inVAJava()) && (fUseLoadingRunner.isSelected());
  }
  
  protected synchronized void runTest(Test testSuite)
  {
    if (fRunner != null) {
      fTestResult.stop();
    } else {
      reset();
      if (testSuite != null) {
        doRunTest(testSuite);
      }
    }
  }
  
  private void doRunTest(Test testSuite) {
    setButtonLabel(fRun, "Stop");
    fRunner = new Thread(testSuite) { private final Test val$testSuite;
      
      public void run() { TestRunner.this.start(val$testSuite);
        TestRunner.this.postInfo("Running...");
        
        long startTime = System.currentTimeMillis();
        val$testSuite.run(fTestResult);
        
        if (fTestResult.shouldStop()) {
          TestRunner.this.postStatus("Stopped");
        } else {
          long endTime = System.currentTimeMillis();
          long runTime = endTime - startTime;
          TestRunner.this.postInfo("Finished: " + elapsedTimeAsString(runTime) + " seconds");
        }
        runFinished(val$testSuite);
        TestRunner.this.setButtonLabel(fRun, "Run");
        fRunner = null;
        System.gc();
      }
      

    };
    fTestResult = createTestResult();
    fTestResult.addListener(this);
    aboutToStart(testSuite);
    
    fRunner.start();
  }
  
  /* Error */
  private void saveHistory()
    throws java.io.IOException
  {
    // Byte code:
    //   0: new 978	java/io/BufferedWriter
    //   3: dup
    //   4: new 980	java/io/FileWriter
    //   7: dup
    //   8: aload_0
    //   9: invokespecial 796	junit/swingui/TestRunner:getSettingsFile	()Ljava/io/File;
    //   12: invokespecial 981	java/io/FileWriter:<init>	(Ljava/io/File;)V
    //   15: invokespecial 984	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
    //   18: astore_1
    //   19: iconst_0
    //   20: istore_2
    //   21: goto +32 -> 53
    //   24: aload_0
    //   25: getfield 136	junit/swingui/TestRunner:fSuiteCombo	Ljavax/swing/JComboBox;
    //   28: iload_2
    //   29: invokevirtual 154	javax/swing/JComboBox:getItemAt	(I)Ljava/lang/Object;
    //   32: invokevirtual 856	java/lang/Object:toString	()Ljava/lang/String;
    //   35: astore_3
    //   36: aload_1
    //   37: aload_3
    //   38: iconst_0
    //   39: aload_3
    //   40: invokevirtual 987	java/lang/String:length	()I
    //   43: invokevirtual 991	java/io/BufferedWriter:write	(Ljava/lang/String;II)V
    //   46: aload_1
    //   47: invokevirtual 994	java/io/BufferedWriter:newLine	()V
    //   50: iinc 2 1
    //   53: iload_2
    //   54: aload_0
    //   55: getfield 136	junit/swingui/TestRunner:fSuiteCombo	Ljavax/swing/JComboBox;
    //   58: invokevirtual 173	javax/swing/JComboBox:getItemCount	()I
    //   61: if_icmplt -37 -> 24
    //   64: goto +11 -> 75
    //   67: astore 5
    //   69: jsr +12 -> 81
    //   72: aload 5
    //   74: athrow
    //   75: jsr +6 -> 81
    //   78: goto +11 -> 89
    //   81: astore 4
    //   83: aload_1
    //   84: invokevirtual 995	java/io/BufferedWriter:close	()V
    //   87: ret 4
    //   89: return
    // Line number table:
    //   Java source line #648	-> byte code offset #0
    //   Java source line #650	-> byte code offset #19
    //   Java source line #651	-> byte code offset #24
    //   Java source line #652	-> byte code offset #36
    //   Java source line #653	-> byte code offset #46
    //   Java source line #650	-> byte code offset #50
    //   Java source line #655	-> byte code offset #67
    //   Java source line #656	-> byte code offset #83
    //   Java source line #649	-> byte code offset #87
    //   Java source line #658	-> byte code offset #89
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	90	0	this	TestRunner
    //   18	66	1	bw	java.io.BufferedWriter
    //   20	34	2	i	int
    //   35	5	3	testsuite	String
    //   81	1	4	localObject1	Object
    //   67	6	5	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   19	67	67	finally
  }
  
  private void setButtonLabel(JButton button, String label)
  {
    SwingUtilities.invokeLater(
      new Runnable() { private final String val$label;
        
        public void run() { setText(val$label); }
      });
  }
  

  public void handleTestSelected(Test test)
  {
    fRerunButton.setEnabled((test != null) && ((test instanceof TestCase)));
    showFailureDetail(test);
  }
  
  private void showFailureDetail(Test test) {
    if (test != null) {
      javax.swing.ListModel failures = getFailures();
      for (int i = 0; i < failures.getSize(); i++) {
        junit.framework.TestFailure failure = (junit.framework.TestFailure)failures.getElementAt(i);
        if (failure.failedTest() == test) {
          fFailureView.showFailure(failure);
          return;
        }
      }
    }
    fFailureView.clear();
  }
  
  private void showInfo(String message) {
    fStatusLine.showInfo(message);
  }
  
  private void showStatus(String status) {
    fStatusLine.showError(status);
  }
  


  public void start(String[] args)
  {
    String suiteName = processArguments(args);
    fFrame = createUI(suiteName);
    fFrame.pack();
    fFrame.setVisible(true);
    
    if (suiteName != null) {
      setSuite(suiteName);
      runSuite();
    }
  }
  
  private void start(Test test) {
    SwingUtilities.invokeLater(
      new Runnable() { private final Test val$test;
        
        public void run() { int total = val$test.countTestCases();
          fProgressIndicator.start(total);
          fCounterPanel.setTotal(total);
        }
      });
  }
  


  private void synchUI()
  {
    try
    {
      SwingUtilities.invokeAndWait(
        new Runnable()
        {
          public void run() {}
        });
    }
    catch (Exception localException) {}
  }
  



  public void terminate()
  {
    fFrame.dispose();
    try {
      saveHistory();
    } catch (java.io.IOException e) {
      System.out.println("Couldn't save test run history");
    }
    System.exit(0);
  }
  
  public void textChanged() {
    fRun.setEnabled(getSuiteText().length() > 0);
    clearStatus();
  }
  
  protected void clearStatus() {
    fStatusLine.clear();
  }
  
  public static javax.swing.Icon getIconResource(Class clazz, String name) {
    java.net.URL url = clazz.getResource(name);
    if (url == null) {
      System.err.println("Warning: could not load \"" + name + "\" icon");
      return null;
    }
    return new javax.swing.ImageIcon(url);
  }
  
  private void about() {
    AboutDialog about = new AboutDialog(fFrame);
    about.show();
  }
}
