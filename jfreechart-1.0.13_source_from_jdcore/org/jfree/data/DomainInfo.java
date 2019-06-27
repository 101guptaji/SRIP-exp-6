package org.jfree.data;

public abstract interface DomainInfo
{
  public abstract double getDomainLowerBound(boolean paramBoolean);
  
  public abstract double getDomainUpperBound(boolean paramBoolean);
  
  public abstract Range getDomainBounds(boolean paramBoolean);
}
