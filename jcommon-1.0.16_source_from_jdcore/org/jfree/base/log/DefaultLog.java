package org.jfree.base.log;

import org.jfree.util.Log;
import org.jfree.util.LogTarget;
import org.jfree.util.PrintStreamLogTarget;

















































public class DefaultLog
  extends Log
{
  private static final PrintStreamLogTarget DEFAULT_LOG_TARGET = new PrintStreamLogTarget();
  












  private static final DefaultLog defaultLogInstance = new DefaultLog();
  static { defaultLogInstance.addTarget(DEFAULT_LOG_TARGET);
    
    try
    {
      String property = System.getProperty("org.jfree.DebugDefault", "false");
      if (Boolean.valueOf(property).booleanValue()) {
        defaultLogInstance.setDebuglevel(3);
      }
      else {
        defaultLogInstance.setDebuglevel(1);
      }
    }
    catch (SecurityException se) {
      defaultLogInstance.setDebuglevel(1);
    }
  }
  



  public void init()
  {
    removeTarget(DEFAULT_LOG_TARGET);
    String logLevel = LogConfiguration.getLogLevel();
    if (logLevel.equalsIgnoreCase("error")) {
      setDebuglevel(0);
    }
    else if (logLevel.equalsIgnoreCase("warn")) {
      setDebuglevel(1);
    }
    else if (logLevel.equalsIgnoreCase("info")) {
      setDebuglevel(2);
    }
    else if (logLevel.equalsIgnoreCase("debug")) {
      setDebuglevel(3);
    }
  }
  






  public synchronized void addTarget(LogTarget target)
  {
    super.addTarget(target);
    


    if (target != DEFAULT_LOG_TARGET) {
      removeTarget(DEFAULT_LOG_TARGET);
    }
  }
  




  public static DefaultLog getDefaultLog()
  {
    return defaultLogInstance;
  }
  


  public static void installDefaultLog()
  {
    Log.defineLog(defaultLogInstance);
  }
  
  protected DefaultLog() {}
}
