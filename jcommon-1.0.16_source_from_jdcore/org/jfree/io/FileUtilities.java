package org.jfree.io;

import java.io.File;
import java.util.StringTokenizer;































































public class FileUtilities
{
  private FileUtilities() {}
  
  public static File findFileOnClassPath(String name)
  {
    String classpath = System.getProperty("java.class.path");
    String pathSeparator = System.getProperty("path.separator");
    
    StringTokenizer tokenizer = new StringTokenizer(classpath, pathSeparator);
    
    while (tokenizer.hasMoreTokens()) {
      String pathElement = tokenizer.nextToken();
      
      File directoryOrJar = new File(pathElement);
      File absoluteDirectoryOrJar = directoryOrJar.getAbsoluteFile();
      
      if (absoluteDirectoryOrJar.isFile()) {
        File target = new File(absoluteDirectoryOrJar.getParent(), name);
        if (target.exists()) {
          return target;
        }
      }
      else {
        File target = new File(directoryOrJar, name);
        if (target.exists()) {
          return target;
        }
      }
    }
    
    return null;
  }
}
