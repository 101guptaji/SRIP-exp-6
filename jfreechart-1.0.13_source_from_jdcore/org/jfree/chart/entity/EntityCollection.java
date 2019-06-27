package org.jfree.chart.entity;

import java.util.Collection;
import java.util.Iterator;

public abstract interface EntityCollection
{
  public abstract void clear();
  
  public abstract void add(ChartEntity paramChartEntity);
  
  public abstract void addAll(EntityCollection paramEntityCollection);
  
  public abstract ChartEntity getEntity(double paramDouble1, double paramDouble2);
  
  public abstract ChartEntity getEntity(int paramInt);
  
  public abstract int getEntityCount();
  
  public abstract Collection getEntities();
  
  public abstract Iterator iterator();
}
