package org.jfree.base.log;

import org.jfree.base.AbstractBoot;
import org.jfree.base.BaseBoot;
import org.jfree.base.config.ModifiableConfiguration;
import org.jfree.util.Configuration;
import org.jfree.util.PrintStreamLogTarget;






















































public class LogConfiguration
{
  public static final String DISABLE_LOGGING_DEFAULT = "false";
  public static final String LOGLEVEL = "org.jfree.base.LogLevel";
  public static final String LOGLEVEL_DEFAULT = "Info";
  public static final String LOGTARGET = "org.jfree.base.LogTarget";
  public static final String LOGTARGET_DEFAULT = PrintStreamLogTarget.class.getName();
  




  public static final String DISABLE_LOGGING = "org.jfree.base.NoDefaultDebug";
  




  private LogConfiguration() {}
  




  public static String getLogTarget()
  {
    return BaseBoot.getInstance().getGlobalConfig().getConfigProperty("org.jfree.base.LogTarget", LOGTARGET_DEFAULT);
  }
  






  public static void setLogTarget(String logTarget)
  {
    BaseBoot.getConfiguration().setConfigProperty("org.jfree.base.LogTarget", logTarget);
  }
  





  public static String getLogLevel()
  {
    return BaseBoot.getInstance().getGlobalConfig().getConfigProperty("org.jfree.base.LogLevel", "Info");
  }
  
























  public static void setLogLevel(String level)
  {
    BaseBoot.getConfiguration().setConfigProperty("org.jfree.base.LogLevel", level);
  }
  





  public static boolean isDisableLogging()
  {
    return BaseBoot.getInstance().getGlobalConfig().getConfigProperty("org.jfree.base.NoDefaultDebug", "false").equalsIgnoreCase("true");
  }
  










  public static void setDisableLogging(boolean disableLogging)
  {
    BaseBoot.getConfiguration().setConfigProperty("org.jfree.base.NoDefaultDebug", String.valueOf(disableLogging));
  }
}
