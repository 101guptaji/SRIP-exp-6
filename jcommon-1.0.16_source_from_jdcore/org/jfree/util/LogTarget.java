package org.jfree.util;















public abstract interface LogTarget
{
  public static final int ERROR = 0;
  












  public static final int WARN = 1;
  












  public static final int INFO = 2;
  












  public static final int DEBUG = 3;
  












  public static final String[] LEVELS = { "ERROR: ", "WARN:  ", "INFO:  ", "DEBUG: " };
  
  public abstract void log(int paramInt, Object paramObject);
  
  public abstract void log(int paramInt, Object paramObject, Exception paramException);
}
