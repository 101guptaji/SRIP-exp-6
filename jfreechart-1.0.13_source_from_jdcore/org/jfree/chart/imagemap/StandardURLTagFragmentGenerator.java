package org.jfree.chart.imagemap;






























public class StandardURLTagFragmentGenerator
  implements URLTagFragmentGenerator
{
  public StandardURLTagFragmentGenerator() {}
  




























  public String generateURLFragment(String urlText)
  {
    return " href=\"" + urlText + "\"";
  }
}
