package org.jfree.chart.editor;

import org.jfree.chart.JFreeChart;

public abstract interface ChartEditorFactory
{
  public abstract ChartEditor createEditor(JFreeChart paramJFreeChart);
}
