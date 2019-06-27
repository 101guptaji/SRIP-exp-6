package org.jfree.data.xml;

import java.util.Iterator;
import java.util.List;
import org.jfree.data.DefaultKeyedValues;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;




















































public class CategorySeriesHandler
  extends DefaultHandler
  implements DatasetTags
{
  private RootHandler root;
  private Comparable seriesKey;
  private DefaultKeyedValues values;
  
  public CategorySeriesHandler(RootHandler root)
  {
    this.root = root;
    values = new DefaultKeyedValues();
  }
  




  public void setSeriesKey(Comparable key)
  {
    seriesKey = key;
  }
  





  public void addItem(Comparable key, Number value)
  {
    values.addValue(key, value);
  }
  












  public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
    throws SAXException
  {
    if (qName.equals("Series")) {
      setSeriesKey(atts.getValue("name"));
      ItemHandler subhandler = new ItemHandler(root, this);
      root.pushSubHandler(subhandler);
    }
    else if (qName.equals("Item")) {
      ItemHandler subhandler = new ItemHandler(root, this);
      root.pushSubHandler(subhandler);
      subhandler.startElement(namespaceURI, localName, qName, atts);
    }
    else
    {
      throw new SAXException("Expecting <Series> or <Item> tag...found " + qName);
    }
  }
  











  public void endElement(String namespaceURI, String localName, String qName)
  {
    if ((root instanceof CategoryDatasetHandler)) {
      CategoryDatasetHandler handler = (CategoryDatasetHandler)root;
      
      Iterator iterator = values.getKeys().iterator();
      while (iterator.hasNext()) {
        Comparable key = (Comparable)iterator.next();
        Number value = values.getValue(key);
        handler.addItem(seriesKey, key, value);
      }
      
      root.popSubHandler();
    }
  }
}
