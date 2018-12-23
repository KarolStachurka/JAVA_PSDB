import psbd.*;

import java.sql.*;

/* todo list:
    - design aesthetic GUI view
    - name all gui elements like in login view
    - create getters for all important elements of view
    - write simple description of methods and fields of classes in comments for documentation of project
    - create idiot friendly text inputs ( use REGEX)
    - test every functionality of project
    - store encrypted passwords in database

*/
public class Main {

    public static void main(String[] args){
//        try {
//           psbd.DatabaseConnector database = psbd.DatabaseConnector.getInstance();
//           database.getData("select * from employees");
//           ResultSet myRs = database.getResult();
//           while (myRs.next()) {
//               System.out.println(myRs.getString("last_name") + ", " + myRs.getString("first_name"));
//           }
//        }
//        catch (SQLException e)
        MainWindow view = new MainWindow();
        MainController controller = new MainController(view);
        }

    }




