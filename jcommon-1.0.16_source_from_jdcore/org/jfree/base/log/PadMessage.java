package org.jfree.base.log;

import java.util.Arrays;






























































public class PadMessage
{
  private final Object text;
  private final int length;
  
  public PadMessage(Object message, int length)
  {
    text = message;
    this.length = length;
  }
  




  public String toString()
  {
    StringBuffer b = new StringBuffer();
    b.append(text);
    if (b.length() < length) {
      char[] pad = new char[length - b.length()];
      Arrays.fill(pad, ' ');
      b.append(pad);
    }
    return b.toString();
  }
}
