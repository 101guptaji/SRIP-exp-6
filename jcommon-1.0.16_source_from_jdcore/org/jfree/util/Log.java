package org.jfree.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;




























































public class Log
{
  private int debuglevel;
  private LogTarget[] logTargets;
  private HashMap logContexts;
  private static Log singleton;
  
  public static class SimpleMessage
  {
    private String message;
    private Object[] param;
    
    public SimpleMessage(String message, Object param1)
    {
      this.message = message;
      param = new Object[] { param1 };
    }
    







    public SimpleMessage(String message, Object param1, Object param2)
    {
      this.message = message;
      param = new Object[] { param1, param2 };
    }
    








    public SimpleMessage(String message, Object param1, Object param2, Object param3)
    {
      this.message = message;
      param = new Object[] { param1, param2, param3 };
    }
    










    public SimpleMessage(String message, Object param1, Object param2, Object param3, Object param4)
    {
      this.message = message;
      param = new Object[] { param1, param2, param3, param4 };
    }
    





    public SimpleMessage(String message, Object[] param)
    {
      this.message = message;
      this.param = param;
    }
    




    public String toString()
    {
      StringBuffer b = new StringBuffer();
      b.append(message);
      if (param != null) {
        for (int i = 0; i < param.length; i++) {
          b.append(param[i]);
        }
      }
      return b.toString();
    }
  }
  





















  protected Log()
  {
    logContexts = new HashMap();
    logTargets = new LogTarget[0];
    debuglevel = 100;
  }
  




  public static synchronized Log getInstance()
  {
    if (singleton == null) {
      singleton = new Log();
    }
    return singleton;
  }
  




  protected static synchronized void defineLog(Log log)
  {
    singleton = log;
  }
  





  public int getDebuglevel()
  {
    return debuglevel;
  }
  





  protected void setDebuglevel(int debuglevel)
  {
    this.debuglevel = debuglevel;
  }
  





  public synchronized void addTarget(LogTarget target)
  {
    if (target == null) {
      throw new NullPointerException();
    }
    LogTarget[] data = new LogTarget[logTargets.length + 1];
    System.arraycopy(logTargets, 0, data, 0, logTargets.length);
    data[logTargets.length] = target;
    logTargets = data;
  }
  




  public synchronized void removeTarget(LogTarget target)
  {
    if (target == null) {
      throw new NullPointerException();
    }
    ArrayList l = new ArrayList();
    l.addAll(Arrays.asList(logTargets));
    l.remove(target);
    
    LogTarget[] targets = new LogTarget[l.size()];
    logTargets = ((LogTarget[])l.toArray(targets));
  }
  




  public LogTarget[] getTargets()
  {
    return (LogTarget[])logTargets.clone();
  }
  




  public synchronized void replaceTargets(LogTarget target)
  {
    if (target == null) {
      throw new NullPointerException();
    }
    logTargets = new LogTarget[] { target };
  }
  




  public static void debug(Object message)
  {
    log(3, message);
  }
  





  public static void debug(Object message, Exception e)
  {
    log(3, message, e);
  }
  




  public static void info(Object message)
  {
    log(2, message);
  }
  





  public static void info(Object message, Exception e)
  {
    log(2, message, e);
  }
  




  public static void warn(Object message)
  {
    log(1, message);
  }
  





  public static void warn(Object message, Exception e)
  {
    log(1, message, e);
  }
  




  public static void error(Object message)
  {
    log(0, message);
  }
  





  public static void error(Object message, Exception e)
  {
    log(0, message, e);
  }
  







  protected void doLog(int level, Object message)
  {
    if (level > 3) {
      level = 3;
    }
    if (level <= debuglevel) {
      for (int i = 0; i < logTargets.length; i++) {
        LogTarget t = logTargets[i];
        t.log(level, message);
      }
    }
  }
  







  public static void log(int level, Object message)
  {
    getInstance().doLog(level, message);
  }
  










  public static void log(int level, Object message, Exception e)
  {
    getInstance().doLog(level, message, e);
  }
  










  protected void doLog(int level, Object message, Exception e)
  {
    if (level > 3) {
      level = 3;
    }
    
    if (level <= debuglevel) {
      for (int i = 0; i < logTargets.length; i++) {
        LogTarget t = logTargets[i];
        t.log(level, message, e);
      }
    }
  }
  






  public void init() {}
  





  public static boolean isDebugEnabled()
  {
    return getInstance().getDebuglevel() >= 3;
  }
  





  public static boolean isInfoEnabled()
  {
    return getInstance().getDebuglevel() >= 2;
  }
  





  public static boolean isWarningEnabled()
  {
    return getInstance().getDebuglevel() >= 1;
  }
  





  public static boolean isErrorEnabled()
  {
    return getInstance().getDebuglevel() >= 0;
  }
  






  public static LogContext createContext(Class context)
  {
    return createContext(context.getName());
  }
  






  public static LogContext createContext(String context)
  {
    return getInstance().internalCreateContext(context);
  }
  






  protected LogContext internalCreateContext(String context)
  {
    synchronized (this) {
      LogContext ctx = (LogContext)logContexts.get(context);
      if (ctx == null) {
        ctx = new LogContext(context);
        logContexts.put(context, ctx);
      }
      return ctx;
    }
  }
}
