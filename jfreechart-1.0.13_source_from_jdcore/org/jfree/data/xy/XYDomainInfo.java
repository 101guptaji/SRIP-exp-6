package org.jfree.data.xy;

import java.util.List;
import org.jfree.data.Range;

public abstract interface XYDomainInfo
{
  public abstract Range getDomainBounds(List paramList, boolean paramBoolean);
}
