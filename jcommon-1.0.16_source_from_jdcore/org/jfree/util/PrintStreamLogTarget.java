package org.jfree.util;

import java.io.PrintStream;
import java.io.Serializable;

























































public class PrintStreamLogTarget
  implements LogTarget, Serializable
{
  private static final long serialVersionUID = 6510564403264504688L;
  private PrintStream printStream;
  
  public PrintStreamLogTarget()
  {
    this(System.out);
  }
  




  public PrintStreamLogTarget(PrintStream printStream)
  {
    if (printStream == null) {
      throw new NullPointerException();
    }
    this.printStream = printStream;
  }
  







  public void log(int level, Object message)
  {
    if (level > 3) {
      level = 3;
    }
    printStream.print(LogTarget.LEVELS[level]);
    printStream.println(message);
    if (level < 3) {
      System.out.flush();
    }
  }
  










  public void log(int level, Object message, Exception e)
  {
    if (level > 3) {
      level = 3;
    }
    printStream.print(LogTarget.LEVELS[level]);
    printStream.println(message);
    e.printStackTrace(printStream);
    if (level < 3) {
      System.out.flush();
    }
  }
}
