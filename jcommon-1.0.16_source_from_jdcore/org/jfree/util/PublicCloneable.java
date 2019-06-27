package org.jfree.util;

public abstract interface PublicCloneable
  extends Cloneable
{
  public abstract Object clone()
    throws CloneNotSupportedException;
}
