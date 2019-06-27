package org.jfree.util;

import java.util.Iterator;




















































public class ReadOnlyIterator
  implements Iterator
{
  private Iterator base;
  
  public ReadOnlyIterator(Iterator it)
  {
    if (it == null) {
      throw new NullPointerException("Base iterator is null.");
    }
    base = it;
  }
  






  public boolean hasNext()
  {
    return base.hasNext();
  }
  





  public Object next()
  {
    return base.next();
  }
  


  public void remove()
  {
    throw new UnsupportedOperationException();
  }
}
