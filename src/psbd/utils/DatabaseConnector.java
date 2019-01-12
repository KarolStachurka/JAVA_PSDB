package psbd.utils;

import java.sql.*;

public class DatabaseConnector {

    private static DatabaseConnector instance;

    private Connection connection;
    private Statement statement;
    private ResultSet result;
    private PreparedStatement preparedStatement;
    private static final String address = "jdbc:mysql://localhost:3306/psbd?useSSL=false";
    private static final String databaseLogin = "student";
    private static final String databasePassword = "student";

    private DatabaseConnector(){
        connection = null;
        statement = null;
        result = null;

        connect();
    }


    private boolean connect()
    {
        try
        {
            connection = DriverManager.getConnection(address);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static DatabaseConnector getInstance()
    {
        if( instance == null)
        {
            instance = new DatabaseConnector();
        }

        return instance;
    }

    public ResultSet getData()
    {
        try {
            result = preparedStatement.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public boolean executeStatement()
    {
        try{
            preparedStatement.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ResultSet getResult() {
        return result;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public PreparedStatement getPreparedStatement(String sqlQuery) {
        try {
            preparedStatement = connection.prepareStatement(sqlQuery);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return preparedStatement;
    }

    public void close() throws SQLException
    {
        if (result != null) {
            result.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (connection != null) {
            connection.close();
        }
    }

    public boolean checkIfRecordExists(String tableName, String columnName, String value) throws SQLException
    {
        String SQLQuery = "SELECT * FROM " + tableName +" WHERE " + columnName + " = ?";
        PreparedStatement statement = getPreparedStatement(SQLQuery);

        if(statement == null)
        {
            return false;
        }
        statement.setString(1,value);
        ResultSet result;
        setPreparedStatement(statement);
        result = getData();

        return result.next();
    }

    public ResultSet getSingleStringRecord(String tableName, String column, String value ) throws SQLException
    {
        String SQLQuery = "SELECT * FROM " + tableName +" WHERE " + column + " = ?";
        PreparedStatement statement = getPreparedStatement(SQLQuery);

        if(statement == null)
        {
            return null;
        }
        statement.setString(1,value);
        ResultSet result;
        setPreparedStatement(statement);
        result = getData();

        return result;
    }

    public ResultSet getFullTableData(String table)
    {
        String SQLQuery = "SELECT * FROM " + table;
        PreparedStatement statement = getPreparedStatement(SQLQuery);
        setPreparedStatement(statement);
        return getData();

    }
}
