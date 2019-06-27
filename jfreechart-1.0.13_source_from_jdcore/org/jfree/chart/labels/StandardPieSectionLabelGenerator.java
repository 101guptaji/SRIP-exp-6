package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.AttributedString;
import java.text.NumberFormat;
import java.util.Locale;
import org.jfree.data.general.PieDataset;
import org.jfree.util.ObjectList;
import org.jfree.util.PublicCloneable;








































































public class StandardPieSectionLabelGenerator
  extends AbstractPieItemLabelGenerator
  implements PieSectionLabelGenerator, Cloneable, PublicCloneable, Serializable
{
  private static final long serialVersionUID = 3064190563760203668L;
  public static final String DEFAULT_SECTION_LABEL_FORMAT = "{0}";
  private ObjectList attributedLabels;
  
  public StandardPieSectionLabelGenerator()
  {
    this("{0}", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
  }
  







  public StandardPieSectionLabelGenerator(Locale locale)
  {
    this("{0}", locale);
  }
  





  public StandardPieSectionLabelGenerator(String labelFormat)
  {
    this(labelFormat, NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
  }
  








  public StandardPieSectionLabelGenerator(String labelFormat, Locale locale)
  {
    this(labelFormat, NumberFormat.getNumberInstance(locale), NumberFormat.getPercentInstance(locale));
  }
  











  public StandardPieSectionLabelGenerator(String labelFormat, NumberFormat numberFormat, NumberFormat percentFormat)
  {
    super(labelFormat, numberFormat, percentFormat);
    attributedLabels = new ObjectList();
  }
  







  public AttributedString getAttributedLabel(int section)
  {
    return (AttributedString)attributedLabels.get(section);
  }
  





  public void setAttributedLabel(int section, AttributedString label)
  {
    attributedLabels.set(section, label);
  }
  







  public String generateSectionLabel(PieDataset dataset, Comparable key)
  {
    return super.generateSectionLabel(dataset, key);
  }
  



























  public AttributedString generateAttributedSectionLabel(PieDataset dataset, Comparable key)
  {
    return getAttributedLabel(dataset.getIndex(key));
  }
  






  public boolean equals(Object obj)
  {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof StandardPieSectionLabelGenerator)) {
      return false;
    }
    StandardPieSectionLabelGenerator that = (StandardPieSectionLabelGenerator)obj;
    
    if (!attributedLabels.equals(attributedLabels)) {
      return false;
    }
    if (!super.equals(obj)) {
      return false;
    }
    return true;
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }
}
