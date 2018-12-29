import psbd.*;

import java.sql.*;
/* todo important list:
    - test every functionality in admin view
    - remove email confirmation necessity and its input field
*/


/* todo list:
    - design aesthetic GUI view
    - name all important gui elements like in admin view
    - create getters for all important elements of view
    - write simple description of methods and fields of classes in comments for documentation of project
    - create idiot friendly text inputs ( use REGEX)
    - test every functionality of project

*/
public class Main {

    public static void main(String[] args){
        MainWindow view = new MainWindow();
        MainController controller = new MainController(view);
        }
    }




