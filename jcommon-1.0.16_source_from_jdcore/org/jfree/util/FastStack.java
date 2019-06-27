package org.jfree.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.EmptyStackException;





































public final class FastStack
  implements Serializable, Cloneable
{
  private Object[] contents;
  private int size;
  private int initialSize;
  
  public FastStack()
  {
    initialSize = 10;
  }
  




  public FastStack(int size)
  {
    initialSize = Math.max(1, size);
  }
  





  public boolean isEmpty()
  {
    return size == 0;
  }
  




  public int size()
  {
    return size;
  }
  




  public void push(Object o)
  {
    if (contents == null) {
      contents = new Object[initialSize];
      contents[0] = o;
      size = 1;
      return;
    }
    
    int oldSize = size;
    size += 1;
    if (contents.length == size)
    {
      Object[] newContents = new Object[size + initialSize];
      
      System.arraycopy(contents, 0, newContents, 0, size);
      contents = newContents;
    }
    contents[oldSize] = o;
  }
  




  public Object peek()
  {
    if (size == 0) {
      throw new EmptyStackException();
    }
    return contents[(size - 1)];
  }
  




  public Object pop()
  {
    if (size == 0) {
      throw new EmptyStackException();
    }
    size -= 1;
    Object retval = contents[size];
    contents[size] = null;
    return retval;
  }
  



  public Object clone()
  {
    try
    {
      FastStack stack = (FastStack)super.clone();
      if (contents != null) {
        contents = ((Object[])contents.clone());
      }
      return stack;
    }
    catch (CloneNotSupportedException cne) {
      throw new IllegalStateException("Clone not supported? Why?");
    }
  }
  


  public void clear()
  {
    size = 0;
    if (contents != null) {
      Arrays.fill(contents, null);
    }
  }
  






  public Object get(int index)
  {
    if (index >= size) {
      throw new IndexOutOfBoundsException();
    }
    return contents[index];
  }
}
