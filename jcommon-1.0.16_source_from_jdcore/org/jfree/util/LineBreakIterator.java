package org.jfree.util;

import java.util.Iterator;






















































public class LineBreakIterator
  implements Iterator
{
  public static final int DONE = -1;
  private char[] text;
  private int position;
  
  public LineBreakIterator()
  {
    setText("");
  }
  





  public LineBreakIterator(String text)
  {
    setText(text);
  }
  





  public synchronized int nextPosition()
  {
    if (text == null)
    {
      return -1;
    }
    if (position == -1)
    {
      return -1;
    }
    


    int nChars = text.length;
    int nextChar = position;
    
    for (;;)
    {
      if (nextChar >= nChars)
      {

        position = -1;
        return -1;
      }
      
      boolean eol = false;
      char c = '\000';
      


      for (int i = nextChar; i < nChars; i++)
      {
        c = text[i];
        if ((c == '\n') || (c == '\r'))
        {
          eol = true;
          break;
        }
      }
      
      nextChar = i;
      if (eol)
      {
        nextChar++;
        if (c == '\r')
        {
          if ((nextChar < nChars) && (text[nextChar] == '\n'))
          {
            nextChar++;
          }
        }
        position = nextChar;
        return position;
      }
    }
  }
  






  public int nextWithEnd()
  {
    int pos = position;
    if (pos == -1)
    {
      return -1;
    }
    if (pos == text.length)
    {
      position = -1;
      return -1;
    }
    int retval = nextPosition();
    if (retval == -1)
    {
      return text.length;
    }
    return retval;
  }
  





  public String getText()
  {
    return new String(text);
  }
  





  public void setText(String text)
  {
    position = 0;
    this.text = text.toCharArray();
  }
  







  public boolean hasNext()
  {
    return position != -1;
  }
  





  public Object next()
  {
    if (position == -1)
    {

      return null;
    }
    
    int lastFound = position;
    int pos = nextWithEnd();
    if (pos == -1)
    {

      return new String(text, lastFound, text.length - lastFound);
    }
    

    if (pos > 0)
    {
      int end = lastFound;
      while ((pos > end) && ((text[(pos - 1)] == '\n') || (text[(pos - 1)] == '\r'))) { pos--;
      }
    }
    



    return new String(text, lastFound, pos - lastFound);
  }
  















  public void remove()
  {
    throw new UnsupportedOperationException("This iterator is read-only.");
  }
}
