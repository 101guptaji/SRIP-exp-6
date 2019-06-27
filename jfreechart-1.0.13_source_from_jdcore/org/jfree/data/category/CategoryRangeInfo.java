package org.jfree.data.category;

import java.util.List;
import org.jfree.data.Range;

public abstract interface CategoryRangeInfo
{
  public abstract Range getRangeBounds(List paramList, boolean paramBoolean);
}
