package org.jfree.data.general;

import java.io.Serializable;




















































public class DatasetGroup
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = -3640642179674185688L;
  private String id;
  
  public DatasetGroup()
  {
    id = "NOID";
  }
  




  public DatasetGroup(String id)
  {
    if (id == null) {
      throw new IllegalArgumentException("Null 'id' argument.");
    }
    this.id = id;
  }
  




  public String getID()
  {
    return id;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof DatasetGroup)) {
      return false;
    }
    DatasetGroup that = (DatasetGroup)obj;
    if (!id.equals(id)) {
      return false;
    }
    return true;
  }
}
