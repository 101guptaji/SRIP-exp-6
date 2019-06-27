package org.jfree.data.jdbc;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.Log;





















































































public class JDBCXYDataset
  extends AbstractXYDataset
  implements XYDataset, TableXYDataset, RangeInfo
{
  private transient Connection connection;
  private String[] columnNames = new String[0];
  

  private ArrayList rows;
  

  private double maxValue = 0.0D;
  

  private double minValue = 0.0D;
  

  private boolean isTimeSeries = false;
  



  private JDBCXYDataset()
  {
    rows = new ArrayList();
  }
  















  public JDBCXYDataset(String url, String driverName, String user, String password)
    throws SQLException, ClassNotFoundException
  {
    this();
    Class.forName(driverName);
    connection = DriverManager.getConnection(url, user, password);
  }
  






  public JDBCXYDataset(Connection con)
    throws SQLException
  {
    this();
    connection = con;
  }
  







  public JDBCXYDataset(Connection con, String query)
    throws SQLException
  {
    this(con);
    executeQuery(query);
  }
  





  public boolean isTimeSeries()
  {
    return isTimeSeries;
  }
  





  public void setTimeSeries(boolean timeSeries)
  {
    isTimeSeries = timeSeries;
  }
  










  public void executeQuery(String query)
    throws SQLException
  {
    executeQuery(connection, query);
  }
  













  public void executeQuery(Connection con, String query)
    throws SQLException
  {
    if (con == null) {
      throw new SQLException("There is no database to execute the query.");
    }
    


    ResultSet resultSet = null;
    Statement statement = null;
    try {
      statement = con.createStatement();
      resultSet = statement.executeQuery(query);
      ResultSetMetaData metaData = resultSet.getMetaData();
      
      int numberOfColumns = metaData.getColumnCount();
      int numberOfValidColumns = 0;
      int[] columnTypes = new int[numberOfColumns];
      for (int column = 0; column < numberOfColumns; column++) {
        try {
          int type = metaData.getColumnType(column + 1);
          switch (type)
          {
          case -7: 
          case -5: 
          case 2: 
          case 3: 
          case 4: 
          case 5: 
          case 6: 
          case 7: 
          case 8: 
          case 91: 
          case 92: 
          case 93: 
            numberOfValidColumns++;
            columnTypes[column] = type;
            break;
          default: 
            Log.warn("Unable to load column " + column + " (" + type + "," + metaData.getColumnClassName(column + 1) + ")");
            




            columnTypes[column] = 0;
          }
        }
        catch (SQLException e)
        {
          columnTypes[column] = 0;
          throw e;
        }
      }
      

      if (numberOfValidColumns <= 1) {
        throw new SQLException("Not enough valid columns where generated by query.");
      }
      



      columnNames = new String[numberOfValidColumns - 1];
      
      int currentColumn = 0;
      for (int column = 1; column < numberOfColumns; column++) {
        if (columnTypes[column] != 0) {
          columnNames[currentColumn] = metaData.getColumnLabel(column + 1);
          
          currentColumn++;
        }
      }
      

      if (rows != null) {
        for (int column = 0; column < rows.size(); column++) {
          ArrayList row = (ArrayList)rows.get(column);
          row.clear();
        }
        rows.clear();
      }
      

      switch (columnTypes[0]) {
      case 91: 
      case 92: 
      case 93: 
        isTimeSeries = true;
        break;
      default: 
        isTimeSeries = false;
      }
      
      


      while (resultSet.next()) {
        ArrayList newRow = new ArrayList();
        for (int column = 0; column < numberOfColumns; column++) {
          Object xObject = resultSet.getObject(column + 1);
          switch (columnTypes[column]) {
          case -5: 
          case 2: 
          case 3: 
          case 4: 
          case 5: 
          case 6: 
          case 7: 
          case 8: 
            newRow.add(xObject);
            break;
          
          case 91: 
          case 92: 
          case 93: 
            newRow.add(new Long(((Date)xObject).getTime()));
            break;
          case 0: 
            break;
          default: 
            System.err.println("Unknown data");
            columnTypes[column] = 0;
          }
          
        }
        rows.add(newRow);
      }
      

      if (rows.size() == 0) {
        ArrayList newRow = new ArrayList();
        for (int column = 0; column < numberOfColumns; column++) {
          if (columnTypes[column] != 0) {
            newRow.add(new Integer(0));
          }
        }
        rows.add(newRow);
      }
      

      if (rows.size() < 1) {
        maxValue = 0.0D;
        minValue = 0.0D;
      }
      else {
        ArrayList row = (ArrayList)rows.get(0);
        maxValue = Double.NEGATIVE_INFINITY;
        minValue = Double.POSITIVE_INFINITY;
        for (int rowNum = 0; rowNum < rows.size(); rowNum++) {
          row = (ArrayList)rows.get(rowNum);
          for (int column = 1; column < numberOfColumns; column++) {
            Object testValue = row.get(column);
            if (testValue != null) {
              double test = ((Number)testValue).doubleValue();
              
              if (test < minValue) {
                minValue = test;
              }
              if (test > maxValue) {
                maxValue = test;
              }
            }
          }
        }
      }
      
      fireDatasetChanged(); return;
    }
    finally {
      if (resultSet != null) {
        try {
          resultSet.close();
        }
        catch (Exception e) {}
      }
      

      if (statement != null) {
        try {
          statement.close();
        }
        catch (Exception e) {}
      }
    }
  }
  














  public Number getX(int seriesIndex, int itemIndex)
  {
    ArrayList row = (ArrayList)rows.get(itemIndex);
    return (Number)row.get(0);
  }
  









  public Number getY(int seriesIndex, int itemIndex)
  {
    ArrayList row = (ArrayList)rows.get(itemIndex);
    return (Number)row.get(seriesIndex + 1);
  }
  








  public int getItemCount(int seriesIndex)
  {
    return rows.size();
  }
  





  public int getItemCount()
  {
    return getItemCount(0);
  }
  







  public int getSeriesCount()
  {
    return columnNames.length;
  }
  










  public Comparable getSeriesKey(int seriesIndex)
  {
    if ((seriesIndex < columnNames.length) && (columnNames[seriesIndex] != null))
    {
      return columnNames[seriesIndex];
    }
    
    return "";
  }
  







  /**
   * @deprecated
   */
  public int getLegendItemCount()
  {
    return getSeriesCount();
  }
  





  /**
   * @deprecated
   */
  public String[] getLegendItemLabels()
  {
    return columnNames;
  }
  


  public void close()
  {
    try
    {
      connection.close();
    }
    catch (Exception e) {
      System.err.println("JdbcXYDataset: swallowing exception.");
    }
  }
  








  public double getRangeLowerBound(boolean includeInterval)
  {
    return minValue;
  }
  







  public double getRangeUpperBound(boolean includeInterval)
  {
    return maxValue;
  }
  







  public Range getRangeBounds(boolean includeInterval)
  {
    return new Range(minValue, maxValue);
  }
}
