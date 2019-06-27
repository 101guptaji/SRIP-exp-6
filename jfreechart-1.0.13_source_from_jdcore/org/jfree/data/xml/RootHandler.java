package org.jfree.data.xml;

import java.util.Stack;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;














































public class RootHandler
  extends DefaultHandler
  implements DatasetTags
{
  private Stack subHandlers;
  
  public RootHandler()
  {
    subHandlers = new Stack();
  }
  




  public Stack getSubHandlers()
  {
    return subHandlers;
  }
  








  public void characters(char[] ch, int start, int length)
    throws SAXException
  {
    DefaultHandler handler = getCurrentHandler();
    if (handler != this) {
      handler.characters(ch, start, length);
    }
  }
  




  public DefaultHandler getCurrentHandler()
  {
    DefaultHandler result = this;
    if ((subHandlers != null) && 
      (subHandlers.size() > 0)) {
      Object top = subHandlers.peek();
      if (top != null) {
        result = (DefaultHandler)top;
      }
    }
    
    return result;
  }
  




  public void pushSubHandler(DefaultHandler subhandler)
  {
    subHandlers.push(subhandler);
  }
  




  public DefaultHandler popSubHandler()
  {
    return (DefaultHandler)subHandlers.pop();
  }
}
