package org.jfree.chart.entity;

import java.awt.Shape;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;

























































public class StandardEntityCollection
  implements EntityCollection, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 5384773031184897047L;
  private List entities;
  
  public StandardEntityCollection()
  {
    entities = new ArrayList();
  }
  




  public int getEntityCount()
  {
    return entities.size();
  }
  








  public ChartEntity getEntity(int index)
  {
    return (ChartEntity)entities.get(index);
  }
  


  public void clear()
  {
    entities.clear();
  }
  




  public void add(ChartEntity entity)
  {
    if (entity == null) {
      throw new IllegalArgumentException("Null 'entity' argument.");
    }
    entities.add(entity);
  }
  





  public void addAll(EntityCollection collection)
  {
    entities.addAll(collection.getEntities());
  }
  








  public ChartEntity getEntity(double x, double y)
  {
    int entityCount = entities.size();
    for (int i = entityCount - 1; i >= 0; i--) {
      ChartEntity entity = (ChartEntity)entities.get(i);
      if (entity.getArea().contains(x, y)) {
        return entity;
      }
    }
    return null;
  }
  




  public Collection getEntities()
  {
    return Collections.unmodifiableCollection(entities);
  }
  




  public Iterator iterator()
  {
    return entities.iterator();
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if ((obj instanceof StandardEntityCollection)) {
      StandardEntityCollection that = (StandardEntityCollection)obj;
      return ObjectUtilities.equal(entities, entities);
    }
    return false;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    StandardEntityCollection clone = (StandardEntityCollection)super.clone();
    
    entities = new ArrayList(entities.size());
    for (int i = 0; i < entities.size(); i++) {
      ChartEntity entity = (ChartEntity)entities.get(i);
      entities.add(entity.clone());
    }
    return clone;
  }
}
