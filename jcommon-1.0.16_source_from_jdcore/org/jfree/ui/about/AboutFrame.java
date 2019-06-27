package org.jfree.ui.about;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import org.jfree.util.ResourceBundleWrapper;



























































public class AboutFrame
  extends JFrame
{
  public static final Dimension PREFERRED_SIZE = new Dimension(560, 360);
  

  public static final Border STANDARD_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
  


  private ResourceBundle resources;
  


  private String application;
  


  private String version;
  


  private String copyright;
  


  private String info;
  

  private Image logo;
  

  private List contributors;
  

  private String licence;
  


  public AboutFrame(String title, ProjectInfo project)
  {
    this(title, project.getName(), "Version " + project.getVersion(), project.getInfo(), project.getLogo(), project.getCopyright(), project.getLicenceText(), project.getContributors(), project);
  }
  




























  public AboutFrame(String title, String application, String version, String info, Image logo, String copyright, String licence, List contributors, ProjectInfo project)
  {
    super(title);
    
    this.application = application;
    this.version = version;
    this.copyright = copyright;
    this.info = info;
    this.logo = logo;
    this.contributors = contributors;
    this.licence = licence;
    
    String baseName = "org.jfree.ui.about.resources.AboutResources";
    resources = ResourceBundleWrapper.getBundle("org.jfree.ui.about.resources.AboutResources");
    
    JPanel content = new JPanel(new BorderLayout());
    content.setBorder(STANDARD_BORDER);
    
    JTabbedPane tabs = createTabs(project);
    content.add(tabs);
    setContentPane(content);
    
    pack();
  }
  





  public Dimension getPreferredSize()
  {
    return PREFERRED_SIZE;
  }
  







  private JTabbedPane createTabs(ProjectInfo project)
  {
    JTabbedPane tabs = new JTabbedPane();
    
    JPanel aboutPanel = createAboutPanel(project);
    aboutPanel.setBorder(STANDARD_BORDER);
    String aboutTab = resources.getString("about-frame.tab.about");
    
    tabs.add(aboutTab, aboutPanel);
    
    JPanel systemPanel = new SystemPropertiesPanel();
    systemPanel.setBorder(STANDARD_BORDER);
    String systemTab = resources.getString("about-frame.tab.system");
    
    tabs.add(systemTab, systemPanel);
    
    return tabs;
  }
  










  private JPanel createAboutPanel(ProjectInfo project)
  {
    JPanel about = new JPanel(new BorderLayout());
    
    JPanel details = new AboutPanel(application, version, copyright, info, logo);
    

    boolean includetabs = false;
    JTabbedPane tabs = new JTabbedPane();
    
    if (contributors != null) {
      JPanel contributorsPanel = new ContributorsPanel(contributors);
      
      contributorsPanel.setBorder(STANDARD_BORDER);
      String contributorsTab = resources.getString("about-frame.tab.contributors");
      
      tabs.add(contributorsTab, contributorsPanel);
      includetabs = true;
    }
    
    if (licence != null) {
      JPanel licencePanel = createLicencePanel();
      licencePanel.setBorder(STANDARD_BORDER);
      String licenceTab = resources.getString("about-frame.tab.licence");
      
      tabs.add(licenceTab, licencePanel);
      includetabs = true;
    }
    
    if (project != null) {
      JPanel librariesPanel = new LibraryPanel(project);
      librariesPanel.setBorder(STANDARD_BORDER);
      String librariesTab = resources.getString("about-frame.tab.libraries");
      
      tabs.add(librariesTab, librariesPanel);
      includetabs = true;
    }
    
    about.add(details, "North");
    if (includetabs) {
      about.add(tabs);
    }
    
    return about;
  }
  






  private JPanel createLicencePanel()
  {
    JPanel licencePanel = new JPanel(new BorderLayout());
    JTextArea area = new JTextArea(licence);
    area.setLineWrap(true);
    area.setWrapStyleWord(true);
    area.setCaretPosition(0);
    area.setEditable(false);
    licencePanel.add(new JScrollPane(area));
    return licencePanel;
  }
}
