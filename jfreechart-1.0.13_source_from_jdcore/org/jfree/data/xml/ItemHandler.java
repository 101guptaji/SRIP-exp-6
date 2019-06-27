package org.jfree.data.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;






















































public class ItemHandler
  extends DefaultHandler
  implements DatasetTags
{
  private RootHandler root;
  private DefaultHandler parent;
  private Comparable key;
  private Number value;
  
  public ItemHandler(RootHandler root, DefaultHandler parent)
  {
    this.root = root;
    this.parent = parent;
    key = null;
    value = null;
  }
  




  public Comparable getKey()
  {
    return key;
  }
  




  public void setKey(Comparable key)
  {
    this.key = key;
  }
  




  public Number getValue()
  {
    return value;
  }
  




  public void setValue(Number value)
  {
    this.value = value;
  }
  












  public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
    throws SAXException
  {
    if (qName.equals("Item")) {
      KeyHandler subhandler = new KeyHandler(root, this);
      root.pushSubHandler(subhandler);
    }
    else if (qName.equals("Value")) {
      ValueHandler subhandler = new ValueHandler(root, this);
      root.pushSubHandler(subhandler);
    }
    else {
      throw new SAXException("Expected <Item> or <Value>...found " + qName);
    }
  }
  












  public void endElement(String namespaceURI, String localName, String qName)
  {
    if ((parent instanceof PieDatasetHandler)) {
      PieDatasetHandler handler = (PieDatasetHandler)parent;
      handler.addItem(key, value);
      root.popSubHandler();
    }
    else if ((parent instanceof CategorySeriesHandler)) {
      CategorySeriesHandler handler = (CategorySeriesHandler)parent;
      handler.addItem(key, value);
      root.popSubHandler();
    }
  }
}
