package org.jfree.resources;

import java.util.ListResourceBundle;

















































public class JCommonResources
  extends ListResourceBundle
{
  public JCommonResources() {}
  
  public Object[][] getContents()
  {
    return CONTENTS;
  }
  

  private static final Object[][] CONTENTS = { { "project.name", "JCommon" }, { "project.version", "1.0.15" }, { "project.info", "http://www.jfree.org/jcommon/" }, { "project.copyright", "(C)opyright 2000-2008, by Object Refinery Limited and Contributors" } };
}
