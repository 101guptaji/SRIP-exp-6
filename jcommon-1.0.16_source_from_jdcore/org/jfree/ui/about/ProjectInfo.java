package org.jfree.ui.about;

import java.awt.Image;
import java.util.Iterator;
import java.util.List;
import org.jfree.base.BootableProjectInfo;
import org.jfree.base.Library;












































































public class ProjectInfo
  extends BootableProjectInfo
{
  private Image logo;
  private String licenceText;
  private List contributors;
  
  public ProjectInfo() {}
  
  public ProjectInfo(String name, String version, String info, Image logo, String copyright, String licenceName, String licenceText)
  {
    super(name, version, info, copyright, licenceName);
    this.logo = logo;
    this.licenceText = licenceText;
  }
  





  public Image getLogo()
  {
    return logo;
  }
  




  public void setLogo(Image logo)
  {
    this.logo = logo;
  }
  




  public String getLicenceText()
  {
    return licenceText;
  }
  




  public void setLicenceText(String licenceText)
  {
    this.licenceText = licenceText;
  }
  




  public List getContributors()
  {
    return contributors;
  }
  




  public void setContributors(List contributors)
  {
    this.contributors = contributors;
  }
  





  public String toString()
  {
    StringBuffer result = new StringBuffer();
    result.append(getName());
    result.append(" version ");
    result.append(getVersion());
    result.append(".\n");
    result.append(getCopyright());
    result.append(".\n");
    result.append("\n");
    result.append("For terms of use, see the licence below.\n");
    result.append("\n");
    result.append("FURTHER INFORMATION:");
    result.append(getInfo());
    result.append("\n");
    result.append("CONTRIBUTORS:");
    if (contributors != null) {
      Iterator iterator = contributors.iterator();
      while (iterator.hasNext()) {
        Contributor contributor = (Contributor)iterator.next();
        result.append(contributor.getName());
        result.append(" (");
        result.append(contributor.getEmail());
        result.append(").");
      }
    }
    else {
      result.append("None");
    }
    
    result.append("\n");
    result.append("OTHER LIBRARIES USED BY ");
    result.append(getName());
    result.append(":");
    Library[] libraries = getLibraries();
    if (libraries.length != 0) {
      for (int i = 0; i < libraries.length; i++) {
        Library lib = libraries[i];
        result.append(lib.getName());
        result.append(" ");
        result.append(lib.getVersion());
        result.append(" (");
        result.append(lib.getInfo());
        result.append(").");
      }
      
    } else {
      result.append("None");
    }
    result.append("\n");
    result.append(getName());
    result.append(" LICENCE TERMS:");
    result.append("\n");
    result.append(getLicenceText());
    
    return result.toString();
  }
}
