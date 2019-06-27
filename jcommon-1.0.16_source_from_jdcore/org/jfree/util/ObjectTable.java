package org.jfree.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;














































































public class ObjectTable
  implements Serializable
{
  private static final long serialVersionUID = -3968322452944912066L;
  private int rows;
  private int columns;
  private transient Object[][] data;
  private int rowIncrement;
  private int columnIncrement;
  
  public ObjectTable()
  {
    this(5, 5);
  }
  





  public ObjectTable(int increment)
  {
    this(increment, increment);
  }
  






  public ObjectTable(int rowIncrement, int colIncrement)
  {
    if (rowIncrement < 1)
    {
      throw new IllegalArgumentException("Increment must be positive.");
    }
    
    if (colIncrement < 1)
    {
      throw new IllegalArgumentException("Increment must be positive.");
    }
    
    rows = 0;
    columns = 0;
    this.rowIncrement = rowIncrement;
    columnIncrement = colIncrement;
    
    data = new Object[rowIncrement][];
  }
  





  public int getColumnIncrement()
  {
    return columnIncrement;
  }
  





  public int getRowIncrement()
  {
    return rowIncrement;
  }
  








  protected void ensureRowCapacity(int row)
  {
    if (row >= data.length)
    {

      Object[][] enlarged = new Object[row + rowIncrement][];
      System.arraycopy(data, 0, enlarged, 0, data.length);
      

      data = enlarged;
    }
  }
  







  public void ensureCapacity(int row, int column)
  {
    if (row < 0)
    {
      throw new IndexOutOfBoundsException("Row is invalid. " + row);
    }
    if (column < 0)
    {
      throw new IndexOutOfBoundsException("Column is invalid. " + column);
    }
    
    ensureRowCapacity(row);
    
    Object[] current = data[row];
    if (current == null)
    {
      Object[] enlarged = new Object[Math.max(column + 1, columnIncrement)];
      
      data[row] = enlarged;
    }
    else if (column >= current.length)
    {
      Object[] enlarged = new Object[column + columnIncrement];
      System.arraycopy(current, 0, enlarged, 0, current.length);
      data[row] = enlarged;
    }
  }
  





  public int getRowCount()
  {
    return rows;
  }
  





  public int getColumnCount()
  {
    return columns;
  }
  











  protected Object getObject(int row, int column)
  {
    if (row < data.length)
    {
      Object[] current = data[row];
      if (current == null)
      {
        return null;
      }
      if (column < current.length)
      {
        return current[column];
      }
    }
    return null;
  }
  











  protected void setObject(int row, int column, Object object)
  {
    ensureCapacity(row, column);
    
    data[row][column] = object;
    rows = Math.max(rows, row + 1);
    columns = Math.max(columns, column + 1);
  }
  








  public boolean equals(Object o)
  {
    if (o == null)
    {
      return false;
    }
    
    if (this == o)
    {
      return true;
    }
    
    if (!(o instanceof ObjectTable))
    {
      return false;
    }
    
    ObjectTable ot = (ObjectTable)o;
    if (getRowCount() != ot.getRowCount())
    {
      return false;
    }
    
    if (getColumnCount() != ot.getColumnCount())
    {
      return false;
    }
    
    for (int r = 0; r < getRowCount(); r++)
    {
      for (int c = 0; c < getColumnCount(); c++)
      {
        if (!ObjectUtilities.equal(getObject(r, c), ot.getObject(r, c)))
        {

          return false;
        }
      }
    }
    return true;
  }
  






  public int hashCode()
  {
    int result = rows;
    result = 29 * result + columns;
    return result;
  }
  






  private void writeObject(ObjectOutputStream stream)
    throws IOException
  {
    stream.defaultWriteObject();
    int rowCount = data.length;
    stream.writeInt(rowCount);
    for (int r = 0; r < rowCount; r++)
    {
      Object[] column = data[r];
      stream.writeBoolean(column != null);
      if (column != null)
      {
        int columnCount = column.length;
        stream.writeInt(columnCount);
        for (int c = 0; c < columnCount; c++)
        {
          writeSerializedData(stream, column[c]);
        }
      }
    }
  }
  








  protected void writeSerializedData(ObjectOutputStream stream, Object o)
    throws IOException
  {
    stream.writeObject(o);
  }
  







  private void readObject(ObjectInputStream stream)
    throws IOException, ClassNotFoundException
  {
    stream.defaultReadObject();
    int rowCount = stream.readInt();
    data = new Object[rowCount][];
    for (int r = 0; r < rowCount; r++)
    {
      boolean isNotNull = stream.readBoolean();
      if (isNotNull)
      {
        int columnCount = stream.readInt();
        Object[] column = new Object[columnCount];
        data[r] = column;
        for (int c = 0; c < columnCount; c++)
        {
          column[c] = readSerializedData(stream);
        }
      }
    }
  }
  









  protected Object readSerializedData(ObjectInputStream stream)
    throws ClassNotFoundException, IOException
  {
    return stream.readObject();
  }
  



  public void clear()
  {
    rows = 0;
    columns = 0;
    for (int i = 0; i < data.length; i++)
    {
      if (data[i] != null)
      {
        Arrays.fill(data[i], null);
      }
    }
  }
  






  protected void copyColumn(int oldColumn, int newColumn)
  {
    for (int i = 0; i < getRowCount(); i++)
    {
      setObject(i, newColumn, getObject(i, oldColumn));
    }
  }
  







  protected void copyRow(int oldRow, int newRow)
  {
    ensureCapacity(newRow, getColumnCount());
    Object[] oldRowStorage = data[oldRow];
    if (oldRowStorage == null)
    {
      Object[] newRowStorage = data[newRow];
      if (newRowStorage != null)
      {
        Arrays.fill(newRowStorage, null);
      }
    }
    else
    {
      data[newRow] = ((Object[])(Object[])oldRowStorage.clone());
    }
  }
  






  protected void setData(Object[][] data, int colCount)
  {
    if (data == null) {
      throw new NullPointerException();
    }
    if (colCount < 0) {
      throw new IndexOutOfBoundsException();
    }
    
    this.data = data;
    rows = data.length;
    columns = colCount;
  }
  





  protected Object[][] getData()
  {
    return data;
  }
}
