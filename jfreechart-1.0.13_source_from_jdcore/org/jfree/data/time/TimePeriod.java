package org.jfree.data.time;

import java.util.Date;

public abstract interface TimePeriod
  extends Comparable
{
  public abstract Date getStart();
  
  public abstract Date getEnd();
}
