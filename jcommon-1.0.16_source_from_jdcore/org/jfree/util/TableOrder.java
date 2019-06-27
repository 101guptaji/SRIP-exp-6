package org.jfree.util;

import java.io.ObjectStreamException;
import java.io.Serializable;


















































public final class TableOrder
  implements Serializable
{
  private static final long serialVersionUID = 525193294068177057L;
  public static final TableOrder BY_ROW = new TableOrder("TableOrder.BY_ROW");
  

  public static final TableOrder BY_COLUMN = new TableOrder("TableOrder.BY_COLUMN");
  



  private String name;
  



  private TableOrder(String name)
  {
    this.name = name;
  }
  




  public String toString()
  {
    return name;
  }
  







  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof TableOrder)) {
      return false;
    }
    TableOrder that = (TableOrder)obj;
    if (!name.equals(name)) {
      return false;
    }
    return true;
  }
  




  public int hashCode()
  {
    return name.hashCode();
  }
  





  private Object readResolve()
    throws ObjectStreamException
  {
    if (equals(BY_ROW)) {
      return BY_ROW;
    }
    if (equals(BY_COLUMN)) {
      return BY_COLUMN;
    }
    return null;
  }
}
