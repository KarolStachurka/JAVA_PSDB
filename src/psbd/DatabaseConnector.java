package psbd;

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
            connection = DriverManager.getConnection(address, databaseLogin, databasePassword);
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

    public void getData()
    {
        try {
            result = preparedStatement.executeQuery();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean executeStatement()
    {
        try{
            preparedStatement.execute();
        }
        catch (SQLException e)
        {
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
}
