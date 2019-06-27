package org.jfree.data.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;























































public class KeyHandler
  extends DefaultHandler
  implements DatasetTags
{
  private RootHandler rootHandler;
  private ItemHandler itemHandler;
  private StringBuffer currentText;
  
  public KeyHandler(RootHandler rootHandler, ItemHandler itemHandler)
  {
    this.rootHandler = rootHandler;
    this.itemHandler = itemHandler;
    currentText = new StringBuffer();
  }
  













  public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
    throws SAXException
  {
    if (qName.equals("Key")) {
      clearCurrentText();
    }
    else {
      throw new SAXException("Expecting <Key> but found " + qName);
    }
  }
  











  public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException
  {
    if (qName.equals("Key")) {
      itemHandler.setKey(getCurrentText());
      rootHandler.popSubHandler();
      rootHandler.pushSubHandler(new ValueHandler(rootHandler, itemHandler));

    }
    else
    {
      throw new SAXException("Expecting </Key> but found " + qName);
    }
  }
  







  public void characters(char[] ch, int start, int length)
  {
    if (currentText != null) {
      currentText.append(String.copyValueOf(ch, start, length));
    }
  }
  




  protected String getCurrentText()
  {
    return currentText.toString();
  }
  


  protected void clearCurrentText()
  {
    currentText.delete(0, currentText.length());
  }
}
