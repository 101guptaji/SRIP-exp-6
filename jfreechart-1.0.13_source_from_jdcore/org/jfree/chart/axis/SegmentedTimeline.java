package org.jfree.chart.axis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;






























































































































































































public class SegmentedTimeline
  implements Timeline, Cloneable, Serializable
{
  private static final long serialVersionUID = 1093779862539903110L;
  public static final long DAY_SEGMENT_SIZE = 86400000L;
  public static final long HOUR_SEGMENT_SIZE = 3600000L;
  public static final long FIFTEEN_MINUTE_SEGMENT_SIZE = 900000L;
  public static final long MINUTE_SEGMENT_SIZE = 60000L;
  /**
   * @deprecated
   */
  public static long FIRST_MONDAY_AFTER_1900;
  /**
   * @deprecated
   */
  public static TimeZone NO_DST_TIME_ZONE;
  /**
   * @deprecated
   */
  public static TimeZone DEFAULT_TIME_ZONE = ;
  




  private Calendar workingCalendarNoDST;
  



  private Calendar workingCalendar = Calendar.getInstance();
  



  private long segmentSize;
  


  private int segmentsIncluded;
  


  private int segmentsExcluded;
  


  private int groupSegmentCount;
  


  private long startTime;
  


  private long segmentsIncludedSize;
  


  private long segmentsExcludedSize;
  


  private long segmentsGroupSize;
  


  private List exceptionSegments = new ArrayList();
  






  private SegmentedTimeline baseTimeline;
  





  private boolean adjustForDaylightSaving = false;
  




  static
  {
    int offset = TimeZone.getDefault().getRawOffset();
    NO_DST_TIME_ZONE = new SimpleTimeZone(offset, "UTC-" + offset);
    


    Calendar cal = new GregorianCalendar(NO_DST_TIME_ZONE);
    cal.set(1900, 0, 1, 0, 0, 0);
    cal.set(14, 0);
    while (cal.get(7) != 2) {
      cal.add(5, 1);
    }
    

    FIRST_MONDAY_AFTER_1900 = cal.getTime().getTime();
  }
  



















  public SegmentedTimeline(long segmentSize, int segmentsIncluded, int segmentsExcluded)
  {
    this.segmentSize = segmentSize;
    this.segmentsIncluded = segmentsIncluded;
    this.segmentsExcluded = segmentsExcluded;
    
    groupSegmentCount = (this.segmentsIncluded + this.segmentsExcluded);
    segmentsIncludedSize = (this.segmentsIncluded * this.segmentSize);
    segmentsExcludedSize = (this.segmentsExcluded * this.segmentSize);
    segmentsGroupSize = (segmentsIncludedSize + segmentsExcludedSize);
    
    int offset = TimeZone.getDefault().getRawOffset();
    TimeZone z = new SimpleTimeZone(offset, "UTC-" + offset);
    workingCalendarNoDST = new GregorianCalendar(z, Locale.getDefault());
  }
  








  public static long firstMondayAfter1900()
  {
    int offset = TimeZone.getDefault().getRawOffset();
    TimeZone z = new SimpleTimeZone(offset, "UTC-" + offset);
    


    Calendar cal = new GregorianCalendar(z);
    cal.set(1900, 0, 1, 0, 0, 0);
    cal.set(14, 0);
    while (cal.get(7) != 2) {
      cal.add(5, 1);
    }
    

    return cal.getTime().getTime();
  }
  







  public static SegmentedTimeline newMondayThroughFridayTimeline()
  {
    SegmentedTimeline timeline = new SegmentedTimeline(86400000L, 5, 2);
    
    timeline.setStartTime(firstMondayAfter1900());
    return timeline;
  }
  
















  public static SegmentedTimeline newFifteenMinuteTimeline()
  {
    SegmentedTimeline timeline = new SegmentedTimeline(900000L, 28, 68);
    
    timeline.setStartTime(firstMondayAfter1900() + 36L * timeline.getSegmentSize());
    
    timeline.setBaseTimeline(newMondayThroughFridayTimeline());
    return timeline;
  }
  





  public boolean getAdjustForDaylightSaving()
  {
    return adjustForDaylightSaving;
  }
  





  public void setAdjustForDaylightSaving(boolean adjust)
  {
    adjustForDaylightSaving = adjust;
  }
  









  public void setStartTime(long millisecond)
  {
    startTime = millisecond;
  }
  





  public long getStartTime()
  {
    return startTime;
  }
  




  public int getSegmentsExcluded()
  {
    return segmentsExcluded;
  }
  





  public long getSegmentsExcludedSize()
  {
    return segmentsExcludedSize;
  }
  





  public int getGroupSegmentCount()
  {
    return groupSegmentCount;
  }
  





  public long getSegmentsGroupSize()
  {
    return segmentsGroupSize;
  }
  




  public int getSegmentsIncluded()
  {
    return segmentsIncluded;
  }
  




  public long getSegmentsIncludedSize()
  {
    return segmentsIncludedSize;
  }
  




  public long getSegmentSize()
  {
    return segmentSize;
  }
  





  public List getExceptionSegments()
  {
    return Collections.unmodifiableList(exceptionSegments);
  }
  




  public void setExceptionSegments(List exceptionSegments)
  {
    this.exceptionSegments = exceptionSegments;
  }
  




  public SegmentedTimeline getBaseTimeline()
  {
    return baseTimeline;
  }
  






  public void setBaseTimeline(SegmentedTimeline baseTimeline)
  {
    if (baseTimeline != null) {
      if (baseTimeline.getSegmentSize() < segmentSize) {
        throw new IllegalArgumentException("baseTimeline.getSegmentSize() is smaller than segmentSize");
      }
      

      if (baseTimeline.getStartTime() > startTime) {
        throw new IllegalArgumentException("baseTimeline.getStartTime() is after startTime");
      }
      
      if (baseTimeline.getSegmentSize() % segmentSize != 0L) {
        throw new IllegalArgumentException("baseTimeline.getSegmentSize() is not multiple of segmentSize");
      }
      

      if ((startTime - baseTimeline.getStartTime()) % segmentSize != 0L)
      {
        throw new IllegalArgumentException("baseTimeline is not aligned");
      }
    }
    

    this.baseTimeline = baseTimeline;
  }
  










  public long toTimelineValue(long millisecond)
  {
    long rawMilliseconds = millisecond - startTime;
    long groupMilliseconds = rawMilliseconds % segmentsGroupSize;
    long groupIndex = rawMilliseconds / segmentsGroupSize;
    long result;
    long result; if (groupMilliseconds >= segmentsIncludedSize) {
      result = toTimelineValue(startTime + segmentsGroupSize * (groupIndex + 1L));
    }
    else
    {
      Segment segment = getSegment(millisecond);
      long result; if (segment.inExceptionSegments()) {
        int p;
        while ((p = binarySearchExceptionSegments(segment)) >= 0) {
          segment = getSegment(millisecond = ((Segment)exceptionSegments.get(p)).getSegmentEnd() + 1L);
        }
        
        result = toTimelineValue(millisecond);
      }
      else {
        long shiftedSegmentedValue = millisecond - startTime;
        long x = shiftedSegmentedValue % segmentsGroupSize;
        long y = shiftedSegmentedValue / segmentsGroupSize;
        
        long wholeExceptionsBeforeDomainValue = getExceptionSegmentCount(startTime, millisecond - 1L);
        



        long result;
        



        if (x < segmentsIncludedSize) {
          result = segmentsIncludedSize * y + x - wholeExceptionsBeforeDomainValue * segmentSize;

        }
        else
        {

          result = segmentsIncludedSize * (y + 1L) - wholeExceptionsBeforeDomainValue * segmentSize;
        }
      }
    }
    



    return result;
  }
  








  public long toTimelineValue(Date date)
  {
    return toTimelineValue(getTime(date));
  }
  









  public long toMillisecond(long timelineValue)
  {
    Segment result = new Segment(startTime + timelineValue + timelineValue / segmentsIncludedSize * segmentsExcludedSize);
    


    long lastIndex = startTime;
    

    while (lastIndex <= segmentStart)
    {
      long exceptionSegmentCount;
      


      while ((exceptionSegmentCount = getExceptionSegmentCount(lastIndex, millisecond / segmentSize * segmentSize - 1L)) > 0L)
      {
        lastIndex = segmentStart;
        

        for (int i = 0; i < exceptionSegmentCount; i++) {
          do {
            result.inc();
          }
          while (result.inExcludeSegments());
        }
      }
      lastIndex = segmentStart;
      

      while ((result.inExceptionSegments()) || (result.inExcludeSegments())) {
        result.inc();
        lastIndex += segmentSize;
      }
      
      lastIndex += 1L;
    }
    
    return getTimeFromLong(millisecond);
  }
  






  public long getTimeFromLong(long date)
  {
    long result = date;
    if (adjustForDaylightSaving) {
      workingCalendarNoDST.setTime(new Date(date));
      workingCalendar.set(workingCalendarNoDST.get(1), workingCalendarNoDST.get(2), workingCalendarNoDST.get(5), workingCalendarNoDST.get(11), workingCalendarNoDST.get(12), workingCalendarNoDST.get(13));
      






      workingCalendar.set(14, workingCalendarNoDST.get(14));
      


      result = workingCalendar.getTime().getTime();
    }
    return result;
  }
  






  public boolean containsDomainValue(long millisecond)
  {
    Segment segment = getSegment(millisecond);
    return segment.inIncludeSegments();
  }
  






  public boolean containsDomainValue(Date date)
  {
    return containsDomainValue(getTime(date));
  }
  










  public boolean containsDomainRange(long domainValueStart, long domainValueEnd)
  {
    if (domainValueEnd < domainValueStart) {
      throw new IllegalArgumentException("domainValueEnd (" + domainValueEnd + ") < domainValueStart (" + domainValueStart + ")");
    }
    

    Segment segment = getSegment(domainValueStart);
    boolean contains = true;
    do {
      contains = segment.inIncludeSegments();
      if (segment.contains(domainValueEnd)) {
        break;
      }
      
      segment.inc();

    }
    while (contains);
    return contains;
  }
  










  public boolean containsDomainRange(Date dateDomainValueStart, Date dateDomainValueEnd)
  {
    return containsDomainRange(getTime(dateDomainValueStart), getTime(dateDomainValueEnd));
  }
  












  public void addException(long millisecond)
  {
    addException(new Segment(millisecond));
  }
  














  public void addException(long fromDomainValue, long toDomainValue)
  {
    addException(new SegmentRange(fromDomainValue, toDomainValue));
  }
  










  public void addException(Date exceptionDate)
  {
    addException(getTime(exceptionDate));
  }
  












  public void addExceptions(List exceptionList)
  {
    for (Iterator iter = exceptionList.iterator(); iter.hasNext();) {
      addException((Date)iter.next());
    }
  }
  









  private void addException(Segment segment)
  {
    if (segment.inIncludeSegments()) {
      int p = binarySearchExceptionSegments(segment);
      exceptionSegments.add(-(p + 1), segment);
    }
  }
  
















  public void addBaseTimelineException(long domainValue)
  {
    Segment baseSegment = baseTimeline.getSegment(domainValue);
    if (baseSegment.inIncludeSegments())
    {


      Segment segment = getSegment(baseSegment.getSegmentStart());
      while (segment.getSegmentStart() <= baseSegment.getSegmentEnd()) {
        if (segment.inIncludeSegments())
        {

          long fromDomainValue = segment.getSegmentStart();
          long toDomainValue;
          do {
            toDomainValue = segment.getSegmentEnd();
            segment.inc();
          }
          while (segment.inIncludeSegments());
          

          addException(fromDomainValue, toDomainValue);

        }
        else
        {
          segment.inc();
        }
      }
    }
  }
  











  public void addBaseTimelineException(Date date)
  {
    addBaseTimelineException(getTime(date));
  }
  











  public void addBaseTimelineExclusions(long fromBaseDomainValue, long toBaseDomainValue)
  {
    Segment baseSegment = baseTimeline.getSegment(fromBaseDomainValue);
    
    while ((baseSegment.getSegmentStart() <= toBaseDomainValue) && (!baseSegment.inExcludeSegments()))
    {
      baseSegment.inc();
    }
    


    while (baseSegment.getSegmentStart() <= toBaseDomainValue)
    {
      long baseExclusionRangeEnd = baseSegment.getSegmentStart() + baseTimeline.getSegmentsExcluded() * baseTimeline.getSegmentSize() - 1L;
      




      Segment segment = getSegment(baseSegment.getSegmentStart());
      while (segment.getSegmentStart() <= baseExclusionRangeEnd) {
        if (segment.inIncludeSegments())
        {

          long fromDomainValue = segment.getSegmentStart();
          long toDomainValue;
          do {
            toDomainValue = segment.getSegmentEnd();
            segment.inc();
          }
          while (segment.inIncludeSegments());
          

          addException(new BaseTimelineSegmentRange(fromDomainValue, toDomainValue));

        }
        else
        {
          segment.inc();
        }
      }
      

      baseSegment.inc(baseTimeline.getGroupSegmentCount());
    }
  }
  









  public long getExceptionSegmentCount(long fromMillisecond, long toMillisecond)
  {
    if (toMillisecond < fromMillisecond) {
      return 0L;
    }
    
    int n = 0;
    Iterator iter = exceptionSegments.iterator();
    while (iter.hasNext()) {
      Segment segment = (Segment)iter.next();
      Segment intersection = segment.intersect(fromMillisecond, toMillisecond);
      
      if (intersection != null) {
        n = (int)(n + intersection.getSegmentCount());
      }
    }
    
    return n;
  }
  










  public Segment getSegment(long millisecond)
  {
    return new Segment(millisecond);
  }
  













  public Segment getSegment(Date date)
  {
    return getSegment(getTime(date));
  }
  









  private boolean equals(Object o, Object p)
  {
    return (o == p) || ((o != null) && (o.equals(p)));
  }
  






  public boolean equals(Object o)
  {
    if ((o instanceof SegmentedTimeline)) {
      SegmentedTimeline other = (SegmentedTimeline)o;
      
      boolean b0 = segmentSize == other.getSegmentSize();
      boolean b1 = segmentsIncluded == other.getSegmentsIncluded();
      boolean b2 = segmentsExcluded == other.getSegmentsExcluded();
      boolean b3 = startTime == other.getStartTime();
      boolean b4 = equals(exceptionSegments, other.getExceptionSegments());
      
      return (b0) && (b1) && (b2) && (b3) && (b4);
    }
    
    return false;
  }
  





  public int hashCode()
  {
    int result = 19;
    result = 37 * result + (int)(segmentSize ^ segmentSize >>> 32);
    
    result = 37 * result + (int)(startTime ^ startTime >>> 32);
    return result;
  }
  














  private int binarySearchExceptionSegments(Segment segment)
  {
    int low = 0;
    int high = exceptionSegments.size() - 1;
    
    while (low <= high) {
      int mid = (low + high) / 2;
      Segment midSegment = (Segment)exceptionSegments.get(mid);
      

      if ((segment.contains(midSegment)) || (midSegment.contains(segment))) {
        return mid;
      }
      
      if (midSegment.before(segment)) {
        low = mid + 1;
      }
      else if (midSegment.after(segment)) {
        high = mid - 1;
      }
      else {
        throw new IllegalStateException("Invalid condition.");
      }
    }
    return -(low + 1);
  }
  









  public long getTime(Date date)
  {
    long result = date.getTime();
    if (adjustForDaylightSaving) {
      workingCalendar.setTime(date);
      workingCalendarNoDST.set(workingCalendar.get(1), workingCalendar.get(2), workingCalendar.get(5), workingCalendar.get(11), workingCalendar.get(12), workingCalendar.get(13));
      





      workingCalendarNoDST.set(14, workingCalendar.get(14));
      
      Date revisedDate = workingCalendarNoDST.getTime();
      result = revisedDate.getTime();
    }
    
    return result;
  }
  






  public Date getDate(long value)
  {
    workingCalendarNoDST.setTime(new Date(value));
    return workingCalendarNoDST.getTime();
  }
  





  public Object clone()
    throws CloneNotSupportedException
  {
    SegmentedTimeline clone = (SegmentedTimeline)super.clone();
    return clone;
  }
  




  public class Segment
    implements Comparable, Cloneable, Serializable
  {
    protected long segmentNumber;
    



    protected long segmentStart;
    



    protected long segmentEnd;
    



    protected long millisecond;
    




    protected Segment() {}
    



    protected Segment(long millisecond)
    {
      segmentNumber = calculateSegmentNumber(millisecond);
      segmentStart = (startTime + segmentNumber * segmentSize);
      
      segmentEnd = (segmentStart + segmentSize - 1L);
      
      this.millisecond = millisecond;
    }
    






    public long calculateSegmentNumber(long millis)
    {
      if (millis >= startTime) {
        return (millis - startTime) / segmentSize;
      }
      

      return (millis - startTime) / segmentSize - 1L;
    }
    






    public long getSegmentNumber()
    {
      return segmentNumber;
    }
    





    public long getSegmentCount()
    {
      return 1L;
    }
    




    public long getSegmentStart()
    {
      return segmentStart;
    }
    




    public long getSegmentEnd()
    {
      return segmentEnd;
    }
    





    public long getMillisecond()
    {
      return millisecond;
    }
    





    public Date getDate()
    {
      return getDate(millisecond);
    }
    








    public boolean contains(long millis)
    {
      return (segmentStart <= millis) && (millis <= segmentEnd);
    }
    









    public boolean contains(long from, long to)
    {
      return (segmentStart <= from) && (to <= segmentEnd);
    }
    







    public boolean contains(Segment segment)
    {
      return contains(segment.getSegmentStart(), segment.getSegmentEnd());
    }
    









    public boolean contained(long from, long to)
    {
      return (from <= segmentStart) && (segmentEnd <= to);
    }
    








    public Segment intersect(long from, long to)
    {
      if ((from <= segmentStart) && (segmentEnd <= to)) {
        return this;
      }
      
      return null;
    }
    








    public boolean before(Segment other)
    {
      return segmentEnd < other.getSegmentStart();
    }
    







    public boolean after(Segment other)
    {
      return segmentStart > other.getSegmentEnd();
    }
    







    public boolean equals(Object object)
    {
      if ((object instanceof Segment)) {
        Segment other = (Segment)object;
        return (segmentNumber == other.getSegmentNumber()) && (segmentStart == other.getSegmentStart()) && (segmentEnd == other.getSegmentEnd()) && (millisecond == other.getMillisecond());
      }
      



      return false;
    }
    





    public Segment copy()
    {
      try
      {
        return (Segment)clone();
      }
      catch (CloneNotSupportedException e) {}
      return null;
    }
    









    public int compareTo(Object object)
    {
      Segment other = (Segment)object;
      if (before(other)) {
        return -1;
      }
      if (after(other)) {
        return 1;
      }
      
      return 0;
    }
    






    public boolean inIncludeSegments()
    {
      if (getSegmentNumberRelativeToGroup() < segmentsIncluded)
      {
        return !inExceptionSegments();
      }
      
      return false;
    }
    





    public boolean inExcludeSegments()
    {
      return getSegmentNumberRelativeToGroup() >= segmentsIncluded;
    }
    








    private long getSegmentNumberRelativeToGroup()
    {
      long p = segmentNumber % groupSegmentCount;
      
      if (p < 0L) {
        p += groupSegmentCount;
      }
      return p;
    }
    










    public boolean inExceptionSegments()
    {
      return SegmentedTimeline.this.binarySearchExceptionSegments(this) >= 0;
    }
    





    public void inc(long n)
    {
      segmentNumber += n;
      long m = n * segmentSize;
      segmentStart += m;
      segmentEnd += m;
      millisecond += m;
    }
    



    public void inc()
    {
      inc(1L);
    }
    





    public void dec(long n)
    {
      segmentNumber -= n;
      long m = n * segmentSize;
      segmentStart -= m;
      segmentEnd -= m;
      millisecond -= m;
    }
    



    public void dec()
    {
      dec(1L);
    }
    


    public void moveIndexToStart()
    {
      millisecond = segmentStart;
    }
    


    public void moveIndexToEnd()
    {
      millisecond = segmentEnd;
    }
  }
  






  protected class SegmentRange
    extends SegmentedTimeline.Segment
  {
    private long segmentCount;
    






    public SegmentRange(long fromMillisecond, long toMillisecond)
    {
      super();
      
      SegmentedTimeline.Segment start = getSegment(fromMillisecond);
      SegmentedTimeline.Segment end = getSegment(toMillisecond);
      





      millisecond = fromMillisecond;
      segmentNumber = calculateSegmentNumber(fromMillisecond);
      segmentStart = segmentStart;
      segmentEnd = segmentEnd;
      segmentCount = (end.getSegmentNumber() - start.getSegmentNumber() + 1L);
    }
    





    public long getSegmentCount()
    {
      return segmentCount;
    }
    













    public SegmentedTimeline.Segment intersect(long from, long to)
    {
      long start = Math.max(from, segmentStart);
      long end = Math.min(to, segmentEnd);
      



      if (start <= end) {
        return new SegmentRange(SegmentedTimeline.this, start, end);
      }
      
      return null;
    }
    






    public boolean inIncludeSegments()
    {
      SegmentedTimeline.Segment segment = getSegment(segmentStart);
      for (; segment.getSegmentStart() < segmentEnd; 
          segment.inc()) {
        if (!segment.inIncludeSegments()) {
          return false;
        }
      }
      return true;
    }
    




    public boolean inExcludeSegments()
    {
      SegmentedTimeline.Segment segment = getSegment(segmentStart);
      for (; segment.getSegmentStart() < segmentEnd; 
          segment.inc()) {
        if (!segment.inExceptionSegments()) {
          return false;
        }
      }
      return true;
    }
    





    public void inc(long n)
    {
      throw new IllegalArgumentException("Not implemented in SegmentRange");
    }
  }
  










  protected class BaseTimelineSegmentRange
    extends SegmentedTimeline.SegmentRange
  {
    public BaseTimelineSegmentRange(long fromDomainValue, long toDomainValue)
    {
      super(fromDomainValue, toDomainValue);
    }
  }
}
