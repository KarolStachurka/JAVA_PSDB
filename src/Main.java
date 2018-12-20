import psbd.*;

import java.sql.*;

public class Main {

    public static void main(String[] args){
        try {
           DatabaseConnector database = DatabaseConnector.getInstance();
           database.getData("select * from employees");
           ResultSet myRs = database.getResult();
           while (myRs.next()) {
               System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
           }
        }
        catch (SQLException e)
        {

        }
//        MainWindow view = new MainWindow();
//        MainController controller = new MainController(view);
    }
}



