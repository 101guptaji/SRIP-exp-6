package org.jfree.data;















































public final class KeyedValueComparatorType
{
  public static final KeyedValueComparatorType BY_KEY = new KeyedValueComparatorType("KeyedValueComparatorType.BY_KEY");
  


  public static final KeyedValueComparatorType BY_VALUE = new KeyedValueComparatorType("KeyedValueComparatorType.BY_VALUE");
  



  private String name;
  



  private KeyedValueComparatorType(String name)
  {
    this.name = name;
  }
  




  public String toString()
  {
    return name;
  }
  







  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (!(o instanceof KeyedValueComparatorType)) {
      return false;
    }
    
    KeyedValueComparatorType type = (KeyedValueComparatorType)o;
    if (!name.equals(name)) {
      return false;
    }
    
    return true;
  }
  




  public int hashCode()
  {
    return name.hashCode();
  }
}
