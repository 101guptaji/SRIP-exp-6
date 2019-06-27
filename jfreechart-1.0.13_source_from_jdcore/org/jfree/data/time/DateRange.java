package org.jfree.data.time;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import org.jfree.data.Range;























































public class DateRange
  extends Range
  implements Serializable
{
  private static final long serialVersionUID = -4705682568375418157L;
  private long lowerDate;
  private long upperDate;
  
  public DateRange()
  {
    this(new Date(0L), new Date(1L));
  }
  





  public DateRange(Date lower, Date upper)
  {
    super(lower.getTime(), upper.getTime());
    lowerDate = lower.getTime();
    upperDate = upper.getTime();
  }
  






  public DateRange(double lower, double upper)
  {
    super(lower, upper);
    lowerDate = (lower);
    upperDate = (upper);
  }
  







  public DateRange(Range other)
  {
    this(other.getLowerBound(), other.getUpperBound());
  }
  






  public Date getLowerDate()
  {
    return new Date(lowerDate);
  }
  








  public long getLowerMillis()
  {
    return lowerDate;
  }
  






  public Date getUpperDate()
  {
    return new Date(upperDate);
  }
  








  public long getUpperMillis()
  {
    return upperDate;
  }
  




  public String toString()
  {
    DateFormat df = DateFormat.getDateTimeInstance();
    return "[" + df.format(getLowerDate()) + " --> " + df.format(getUpperDate()) + "]";
  }
}
