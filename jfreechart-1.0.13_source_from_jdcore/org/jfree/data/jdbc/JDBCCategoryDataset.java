package org.jfree.data.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import org.jfree.data.category.DefaultCategoryDataset;


























































































public class JDBCCategoryDataset
  extends DefaultCategoryDataset
{
  static final long serialVersionUID = -3080395327918844965L;
  private transient Connection connection;
  private boolean transpose = true;
  
















  public JDBCCategoryDataset(String url, String driverName, String user, String passwd)
    throws ClassNotFoundException, SQLException
  {
    Class.forName(driverName);
    connection = DriverManager.getConnection(url, user, passwd);
  }
  




  public JDBCCategoryDataset(Connection connection)
  {
    if (connection == null) {
      throw new NullPointerException("A connection must be supplied.");
    }
    this.connection = connection;
  }
  








  public JDBCCategoryDataset(Connection connection, String query)
    throws SQLException
  {
    this(connection);
    executeQuery(query);
  }
  





  public boolean getTranspose()
  {
    return transpose;
  }
  





  public void setTranspose(boolean transpose)
  {
    this.transpose = transpose;
  }
  










  public void executeQuery(String query)
    throws SQLException
  {
    executeQuery(connection, query);
  }
  












  public void executeQuery(Connection con, String query)
    throws SQLException
  {
    Statement statement = null;
    ResultSet resultSet = null;
    try {
      statement = con.createStatement();
      resultSet = statement.executeQuery(query);
      ResultSetMetaData metaData = resultSet.getMetaData();
      
      int columnCount = metaData.getColumnCount();
      
      if (columnCount < 2) {
        throw new SQLException("JDBCCategoryDataset.executeQuery() : insufficient columns returned from the database.");
      }
      



      int i = getRowCount();
      for (;;) { i--; if (i < 0) break;
        removeRow(i);
      }
      
      while (resultSet.next())
      {
        Comparable rowKey = resultSet.getString(1);
        for (int column = 2; column <= columnCount; column++)
        {
          Comparable columnKey = metaData.getColumnName(column);
          int columnType = metaData.getColumnType(column);
          
          switch (columnType) {
          case -6: 
          case -5: 
          case 2: 
          case 3: 
          case 4: 
          case 5: 
          case 6: 
          case 7: 
          case 8: 
            Number value = (Number)resultSet.getObject(column);
            if (transpose) {
              setValue(value, columnKey, rowKey);
            }
            else {
              setValue(value, rowKey, columnKey);
            }
            break;
          
          case 91: 
          case 92: 
          case 93: 
            Date date = (Date)resultSet.getObject(column);
            Number value = new Long(date.getTime());
            if (transpose) {
              setValue(value, columnKey, rowKey);
            }
            else {
              setValue(value, rowKey, columnKey);
            }
            break;
          
          case -1: 
          case 1: 
          case 12: 
            String string = (String)resultSet.getObject(column);
            try
            {
              Number value = Double.valueOf(string);
              if (transpose) {
                setValue(value, columnKey, rowKey);
              }
              else {
                setValue(value, rowKey, columnKey);
              }
            }
            catch (NumberFormatException e) {}
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
}
