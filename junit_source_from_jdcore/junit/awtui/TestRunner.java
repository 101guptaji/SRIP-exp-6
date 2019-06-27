package junit.awtui;

import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.Menu;
import java.awt.Panel;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.runner.BaseTestRunner;

public class TestRunner extends BaseTestRunner
{
  protected Frame fFrame;
  protected Vector fExceptions;
  protected Vector fFailedTests;
  protected Thread fRunner;
  protected TestResult fTestResult;
  protected java.awt.TextArea fTraceArea;
  protected TextField fSuiteField;
  protected Button fRun;
  protected ProgressBar fProgressIndicator;
  protected List fFailureList;
  protected Logo fLogo;
  protected Label fNumberOfErrors;
  protected Label fNumberOfFailures;
  protected Label fNumberOfRuns;
  protected Button fQuitButton;
  protected Button fRerunButton;
  protected TextField fStatusLine;
  protected java.awt.Checkbox fUseLoadingRunner;
  protected static final java.awt.Font PLAIN_FONT = new java.awt.Font("dialog", 0, 12);
  private static final int GAP = 4;
  
  public TestRunner() {}
  
  private void about()
  {
    AboutDialog about = new AboutDialog(fFrame);
    about.setModal(true);
    about.setLocation(300, 300);
    about.setVisible(true);
  }
  
  public void testStarted(String testName) {
    showInfo("Running: " + testName);
  }
  
  public void testEnded(String testName) {
    setLabelValue(fNumberOfRuns, fTestResult.runCount());
    synchronized (this) {
      fProgressIndicator.step(fTestResult.wasSuccessful());
    }
  }
  
  public void testFailed(int status, Test test, Throwable t) {
    switch (status) {
    case 1: 
      fNumberOfErrors.setText(Integer.toString(fTestResult.errorCount()));
      appendFailure("Error", test, t);
      break;
    case 2: 
      fNumberOfFailures.setText(Integer.toString(fTestResult.failureCount()));
      appendFailure("Failure", test, t);
    }
  }
  
  protected void addGrid(Panel p, Component co, int x, int y, int w, int fill, double wx, int anchor)
  {
    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
    gridx = x;gridy = y;
    gridwidth = w;
    anchor = anchor;
    weightx = wx;
    fill = fill;
    if ((fill == 1) || (fill == 3))
      weighty = 1.0D;
    insets = new Insets(y == 0 ? 4 : 0, x == 0 ? 4 : 0, 4, 4);
    p.add(co, c);
  }
  
  private void appendFailure(String kind, Test test, Throwable t) {
    kind = kind + ": " + test;
    String msg = t.getMessage();
    if (msg != null) {
      kind = kind + ":" + BaseTestRunner.truncate(msg);
    }
    fFailureList.add(kind);
    fExceptions.addElement(t);
    fFailedTests.addElement(test);
    if (fFailureList.getItemCount() == 1) {
      fFailureList.select(0);
      failureSelected();
    }
  }
  


  protected Menu createJUnitMenu()
  {
    Menu menu = new Menu("JUnit");
    java.awt.MenuItem mi = new java.awt.MenuItem("About...");
    mi.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          TestRunner.this.about();
        }
        
      });
    menu.add(mi);
    
    menu.addSeparator();
    mi = new java.awt.MenuItem("Exit");
    mi.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          System.exit(0);
        }
        
      });
    menu.add(mi);
    return menu;
  }
  
  protected void createMenus(java.awt.MenuBar mb) {
    mb.add(createJUnitMenu());
  }
  
  protected TestResult createTestResult() { return new TestResult(); }
  
  protected Frame createUI(String suiteName)
  {
    Frame frame = new Frame("JUnit");
    java.awt.Image icon = loadFrameIcon();
    if (icon != null) {
      frame.setIconImage(icon);
    }
    frame.setLayout(new java.awt.BorderLayout(0, 0));
    frame.setBackground(java.awt.SystemColor.control);
    Frame finalFrame = frame;
    
    frame.addWindowListener(
      new java.awt.event.WindowAdapter() {
        public void windowClosing(java.awt.event.WindowEvent e) {
          dispose();
          System.exit(0);
        }
        

      });
    java.awt.MenuBar mb = new java.awt.MenuBar();
    createMenus(mb);
    frame.setMenuBar(mb);
    

    Label suiteLabel = new Label("Test class name:");
    
    fSuiteField = new TextField(suiteName != null ? suiteName : "");
    fSuiteField.selectAll();
    fSuiteField.requestFocus();
    fSuiteField.setFont(PLAIN_FONT);
    fSuiteField.setColumns(40);
    fSuiteField.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          runSuite();
        }
        
      });
    fSuiteField.addTextListener(
      new java.awt.event.TextListener() {
        public void textValueChanged(java.awt.event.TextEvent e) {
          fRun.setEnabled(fSuiteField.getText().length() > 0);
          fStatusLine.setText("");
        }
        
      });
    fRun = new Button("Run");
    fRun.setEnabled(false);
    fRun.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          runSuite();
        }
        
      });
    boolean useLoader = useReloadingTestSuiteLoader();
    fUseLoadingRunner = new java.awt.Checkbox("Reload classes every run", useLoader);
    if (BaseTestRunner.inVAJava()) {
      fUseLoadingRunner.setVisible(false);
    }
    
    fProgressIndicator = new ProgressBar();
    

    fNumberOfErrors = new Label("0000", 2);
    fNumberOfErrors.setText("0");
    fNumberOfErrors.setFont(PLAIN_FONT);
    
    fNumberOfFailures = new Label("0000", 2);
    fNumberOfFailures.setText("0");
    fNumberOfFailures.setFont(PLAIN_FONT);
    
    fNumberOfRuns = new Label("0000", 2);
    fNumberOfRuns.setText("0");
    fNumberOfRuns.setFont(PLAIN_FONT);
    
    Panel numbersPanel = createCounterPanel();
    

    Label failureLabel = new Label("Errors and Failures:");
    
    fFailureList = new List(5);
    fFailureList.addItemListener(
      new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent e) {
          failureSelected();
        }
        
      });
    fRerunButton = new Button("Run");
    fRerunButton.setEnabled(false);
    fRerunButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          rerun();
        }
        

      });
    Panel failedPanel = new Panel(new java.awt.GridLayout(0, 1, 0, 2));
    failedPanel.add(fRerunButton);
    
    fTraceArea = new java.awt.TextArea();
    fTraceArea.setRows(5);
    fTraceArea.setColumns(60);
    

    fStatusLine = new TextField();
    fStatusLine.setFont(PLAIN_FONT);
    fStatusLine.setEditable(false);
    fStatusLine.setForeground(java.awt.Color.red);
    
    fQuitButton = new Button("Exit");
    fQuitButton.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.exit(0);

        }
        

      });
    fLogo = new Logo();
    

    Panel panel = new Panel(new java.awt.GridBagLayout());
    
    addGrid(panel, suiteLabel, 0, 0, 2, 2, 1.0D, 17);
    
    addGrid(panel, fSuiteField, 0, 1, 2, 2, 1.0D, 17);
    addGrid(panel, fRun, 2, 1, 1, 2, 0.0D, 10);
    addGrid(panel, fUseLoadingRunner, 0, 2, 2, 0, 1.0D, 17);
    addGrid(panel, fProgressIndicator, 0, 3, 2, 2, 1.0D, 17);
    addGrid(panel, fLogo, 2, 3, 1, 0, 0.0D, 11);
    
    addGrid(panel, numbersPanel, 0, 4, 2, 0, 0.0D, 17);
    
    addGrid(panel, failureLabel, 0, 5, 2, 2, 1.0D, 17);
    addGrid(panel, fFailureList, 0, 6, 2, 1, 1.0D, 17);
    addGrid(panel, failedPanel, 2, 6, 1, 2, 0.0D, 10);
    addGrid(panel, fTraceArea, 0, 7, 2, 1, 1.0D, 17);
    
    addGrid(panel, fStatusLine, 0, 8, 2, 2, 1.0D, 10);
    addGrid(panel, fQuitButton, 2, 8, 1, 2, 0.0D, 10);
    
    frame.add(panel, "Center");
    frame.pack();
    return frame;
  }
  
  protected Panel createCounterPanel() {
    Panel numbersPanel = new Panel(new java.awt.GridBagLayout());
    addToCounterPanel(
      numbersPanel, 
      new Label("Runs:"), 
      0, 0, 1, 1, 0.0D, 0.0D, 
      10, 0, 
      new Insets(0, 0, 0, 0));
    
    addToCounterPanel(
      numbersPanel, 
      fNumberOfRuns, 
      1, 0, 1, 1, 0.33D, 0.0D, 
      10, 2, 
      new Insets(0, 8, 0, 40));
    
    addToCounterPanel(
      numbersPanel, 
      new Label("Errors:"), 
      2, 0, 1, 1, 0.0D, 0.0D, 
      10, 0, 
      new Insets(0, 8, 0, 0));
    
    addToCounterPanel(
      numbersPanel, 
      fNumberOfErrors, 
      3, 0, 1, 1, 0.33D, 0.0D, 
      10, 2, 
      new Insets(0, 8, 0, 40));
    
    addToCounterPanel(
      numbersPanel, 
      new Label("Failures:"), 
      4, 0, 1, 1, 0.0D, 0.0D, 
      10, 0, 
      new Insets(0, 8, 0, 0));
    
    addToCounterPanel(
      numbersPanel, 
      fNumberOfFailures, 
      5, 0, 1, 1, 0.33D, 0.0D, 
      10, 2, 
      new Insets(0, 8, 0, 0));
    
    return numbersPanel;
  }
  




  private void addToCounterPanel(Panel counter, Component comp, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill, Insets insets)
  {
    java.awt.GridBagConstraints constraints = new java.awt.GridBagConstraints();
    gridx = gridx;
    gridy = gridy;
    gridwidth = gridwidth;
    gridheight = gridheight;
    weightx = weightx;
    weighty = weighty;
    anchor = anchor;
    fill = fill;
    insets = insets;
    counter.add(comp, constraints);
  }
  
  public void failureSelected()
  {
    fRerunButton.setEnabled(isErrorSelected());
    showErrorTrace();
  }
  
  private boolean isErrorSelected() {
    return fFailureList.getSelectedIndex() != -1;
  }
  
  private java.awt.Image loadFrameIcon() {
    java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
    try {
      java.net.URL url = BaseTestRunner.class.getResource("smalllogo.gif");
      return toolkit.createImage((java.awt.image.ImageProducer)url.getContent());
    }
    catch (Exception localException) {}
    return null;
  }
  
  public Thread getRunner() {
    return fRunner;
  }
  
  public static void main(String[] args) {
    new TestRunner().start(args);
  }
  
  public static void run(Class test) {
    String[] args = { test.getName() };
    main(args);
  }
  
  public void rerun() {
    int index = fFailureList.getSelectedIndex();
    if (index == -1) {
      return;
    }
    Test test = (Test)fFailedTests.elementAt(index);
    rerunTest(test);
  }
  
  private void rerunTest(Test test) {
    if (!(test instanceof junit.framework.TestCase)) {
      showInfo("Could not reload " + test.toString());
      return;
    }
    Test reloadedTest = null;
    junit.framework.TestCase rerunTest = (junit.framework.TestCase)test;
    try {
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
    setLabelValue(fNumberOfErrors, 0);
    setLabelValue(fNumberOfFailures, 0);
    setLabelValue(fNumberOfRuns, 0);
    fProgressIndicator.reset();
    fRerunButton.setEnabled(false);
    fFailureList.removeAll();
    fExceptions = new Vector(10);
    fFailedTests = new Vector(10);
    fTraceArea.setText("");
  }
  
  protected void runFailed(String message)
  {
    showStatus(message);
    fRun.setLabel("Run");
    fRunner = null;
  }
  
  public synchronized void runSuite() {
    if ((fRunner != null) && (fTestResult != null)) {
      fTestResult.stop();
    } else {
      setLoading(shouldReload());
      fRun.setLabel("Stop");
      showInfo("Initializing...");
      reset();
      
      showInfo("Load Test Case...");
      
      Test testSuite = getTest(fSuiteField.getText());
      if (testSuite != null) {
        fRunner = new Thread() { private final Test val$testSuite;
          
          public void run() { fTestResult = createTestResult();
            fTestResult.addListener(TestRunner.this);
            fProgressIndicator.start(val$testSuite.countTestCases());
            TestRunner.this.showInfo("Running...");
            
            long startTime = System.currentTimeMillis();
            val$testSuite.run(fTestResult);
            
            if (fTestResult.shouldStop()) {
              TestRunner.this.showStatus("Stopped");
            } else {
              long endTime = System.currentTimeMillis();
              long runTime = endTime - startTime;
              TestRunner.this.showInfo("Finished: " + elapsedTimeAsString(runTime) + " seconds");
            }
            fTestResult = null;
            fRun.setLabel("Run");
            fRunner = null;
            System.gc();
          }
        };
        fRunner.start();
      }
    }
  }
  
  private boolean shouldReload() {
    return (!BaseTestRunner.inVAJava()) && (fUseLoadingRunner.getState());
  }
  
  private void setLabelValue(Label label, int value) {
    label.setText(Integer.toString(value));
    label.invalidate();
    label.getParent().validate();
  }
  
  public void setSuiteName(String suite)
  {
    fSuiteField.setText(suite);
  }
  
  private void showErrorTrace() {
    int index = fFailureList.getSelectedIndex();
    if (index == -1) {
      return;
    }
    Throwable t = (Throwable)fExceptions.elementAt(index);
    fTraceArea.setText(BaseTestRunner.getFilteredTrace(t));
  }
  
  private void showInfo(String message)
  {
    fStatusLine.setFont(PLAIN_FONT);
    fStatusLine.setForeground(java.awt.Color.black);
    fStatusLine.setText(message);
  }
  
  protected void clearStatus() {
    showStatus("");
  }
  
  private void showStatus(String status) {
    fStatusLine.setFont(PLAIN_FONT);
    fStatusLine.setForeground(java.awt.Color.red);
    fStatusLine.setText(status);
  }
  

  public void start(String[] args)
  {
    String suiteName = processArguments(args);
    fFrame = createUI(suiteName);
    fFrame.setLocation(200, 200);
    fFrame.setVisible(true);
    
    if (suiteName != null) {
      setSuiteName(suiteName);
      runSuite();
    }
  }
}
