package org.jfree.chart.labels;

import java.text.AttributedString;
import org.jfree.data.general.PieDataset;

public abstract interface PieSectionLabelGenerator
{
  public abstract String generateSectionLabel(PieDataset paramPieDataset, Comparable paramComparable);
  
  public abstract AttributedString generateAttributedSectionLabel(PieDataset paramPieDataset, Comparable paramComparable);
}
