package org.jfree.date;

























public abstract class AnnualDateRule
  implements Cloneable
{
  protected AnnualDateRule() {}
  























  public abstract SerialDate getDate(int paramInt);
  























  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
