package org.jfree.data.time;

import java.io.PrintStream;
import javax.swing.table.AbstractTableModel;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
























































public class TimeSeriesTableModel
  extends AbstractTableModel
  implements SeriesChangeListener
{
  private TimeSeries series;
  private boolean editable;
  private RegularTimePeriod newTimePeriod;
  private Number newValue;
  
  public TimeSeriesTableModel()
  {
    this(new TimeSeries("Untitled"));
  }
  




  public TimeSeriesTableModel(TimeSeries series)
  {
    this(series, false);
  }
  





  public TimeSeriesTableModel(TimeSeries series, boolean editable)
  {
    this.series = series;
    this.series.addChangeListener(this);
    this.editable = editable;
  }
  





  public int getColumnCount()
  {
    return 2;
  }
  






  public Class getColumnClass(int column)
  {
    if (column == 0) {
      return String.class;
    }
    
    if (column == 1) {
      return Double.class;
    }
    
    return null;
  }
  









  public String getColumnName(int column)
  {
    if (column == 0) {
      return "Period:";
    }
    
    if (column == 1) {
      return "Value:";
    }
    
    return null;
  }
  







  public int getRowCount()
  {
    return series.getItemCount();
  }
  








  public Object getValueAt(int row, int column)
  {
    if (row < series.getItemCount()) {
      if (column == 0) {
        return series.getTimePeriod(row);
      }
      
      if (column == 1) {
        return series.getValue(row);
      }
      
      return null;
    }
    


    if (column == 0) {
      return newTimePeriod;
    }
    
    if (column == 1) {
      return newValue;
    }
    
    return null;
  }
  











  public boolean isCellEditable(int row, int column)
  {
    if (editable) {
      if ((column == 0) || (column == 1)) {
        return true;
      }
      
      return false;
    }
    

    return false;
  }
  








  public void setValueAt(Object value, int row, int column)
  {
    if (row < series.getItemCount())
    {

      if (column == 1) {
        try {
          Double v = Double.valueOf(value.toString());
          series.update(row, v);
        }
        catch (NumberFormatException nfe)
        {
          System.err.println("Number format exception");
        }
        
      }
    }
    else if (column == 0)
    {
      newTimePeriod = null;
    }
    else if (column == 1) {
      newValue = Double.valueOf(value.toString());
    }
  }
  






  public void seriesChanged(SeriesChangeEvent event)
  {
    fireTableDataChanged();
  }
}
