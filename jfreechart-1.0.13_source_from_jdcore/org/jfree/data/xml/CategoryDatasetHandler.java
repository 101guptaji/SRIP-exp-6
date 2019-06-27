package org.jfree.data.xml;

import java.util.Stack;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;












































public class CategoryDatasetHandler
  extends RootHandler
  implements DatasetTags
{
  private DefaultCategoryDataset dataset;
  
  public CategoryDatasetHandler()
  {
    dataset = null;
  }
  




  public CategoryDataset getDataset()
  {
    return dataset;
  }
  






  public void addItem(Comparable rowKey, Comparable columnKey, Number value)
  {
    dataset.addValue(value, rowKey, columnKey);
  }
  












  public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
    throws SAXException
  {
    DefaultHandler current = getCurrentHandler();
    if (current != this) {
      current.startElement(namespaceURI, localName, qName, atts);
    }
    else if (qName.equals("CategoryDataset")) {
      dataset = new DefaultCategoryDataset();
    }
    else if (qName.equals("Series")) {
      CategorySeriesHandler subhandler = new CategorySeriesHandler(this);
      getSubHandlers().push(subhandler);
      subhandler.startElement(namespaceURI, localName, qName, atts);
    }
    else {
      throw new SAXException("Element not recognised: " + qName);
    }
  }
  











  public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException
  {
    DefaultHandler current = getCurrentHandler();
    if (current != this) {
      current.endElement(namespaceURI, localName, qName);
    }
  }
}
