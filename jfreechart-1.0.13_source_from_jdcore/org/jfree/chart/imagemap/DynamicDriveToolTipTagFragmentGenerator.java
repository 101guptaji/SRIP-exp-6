package org.jfree.chart.imagemap;

















































public class DynamicDriveToolTipTagFragmentGenerator
  implements ToolTipTagFragmentGenerator
{
  protected String title = "";
  

  protected int style = 1;
  






  public DynamicDriveToolTipTagFragmentGenerator() {}
  






  public DynamicDriveToolTipTagFragmentGenerator(String title, int style)
  {
    this.title = title;
    this.style = style;
  }
  






  public String generateToolTipFragment(String toolTipText)
  {
    return " onMouseOver=\"return stm(['" + ImageMapUtilities.javascriptEscape(title) + "','" + ImageMapUtilities.javascriptEscape(toolTipText) + "'],Style[" + style + "]);\"" + " onMouseOut=\"return htm();\"";
  }
}
