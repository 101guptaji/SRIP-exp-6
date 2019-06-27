package org.jfree.data.xy;

import java.util.List;
import org.jfree.data.Range;

public abstract interface XYRangeInfo
{
  public abstract Range getRangeBounds(List paramList, Range paramRange, boolean paramBoolean);
}
