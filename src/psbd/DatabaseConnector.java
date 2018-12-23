package psbd;

import java.sql.*;

public class DatabaseConnector {

    private static DatabaseConnector instance;

    private Connection connection;
    private Statement statement;
    private ResultSet result;
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

    public void getData(String SQLQuery)
    {
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(SQLQuery);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ResultSet getResult() {
        return result;
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
