package org.jfree.chart.imagemap;





























public class StandardToolTipTagFragmentGenerator
  implements ToolTipTagFragmentGenerator
{
  public StandardToolTipTagFragmentGenerator() {}
  



























  public String generateToolTipFragment(String toolTipText)
  {
    return " title=\"" + ImageMapUtilities.htmlEscape(toolTipText) + "\" alt=\"\"";
  }
}
