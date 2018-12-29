package psbd.user;

import com.mysql.jdbc.StringUtils;
import psbd.Communicates;
import psbd.DatabaseConnector;
import psbd.PanelEnum;
import psbd.UserEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class UserLoginController {
    private UserLoginView view;
    private Communicates communicates;

    public UserLoginController(UserLoginView view)
    {
        this.view = view;
    }

    public UserLoginView getView() {
        return view;
    }

    public boolean userLogin()
    {
        String login = view.getLoginTextInput().getText();
        String passsword = view.getPasswordTextInput().getText();

        DatabaseConnector database = DatabaseConnector.getInstance();
        try {
            if (!database.checkIfRecordExists("users", "user_login", login)) {
                setCommunicate(communicates.notExists);
                return false;
            }
        }
        catch (SQLException e)
        {
            setCommunicate(communicates.databaseError);
        }
        String SQLQuery = "SELECT * FROM users WHERE user_login = ? AND user_password = SHA2(?, 256)" ;
        PreparedStatement statement = database.getPreparedStatement(SQLQuery);

        if(statement == null)
        {
            return false;
        }
        try {
            statement.setString(1, login);
            statement.setString(2, passsword);
            ResultSet result;
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
            CurrentSession currentSession = CurrentSession.getInstance();
            result.next();
            User user = new User(UserEnum.valueOf(result.getString("user_type")),
                    result.getString("user_login"),
                    "",
                    result.getString("name"),
                    result.getString("surname"),
                    result.getString("email"),
                    result.getString("pesel"),
                    result.getString("phone_number"),
                    result.getString("company"));
            currentSession.setLoggedUser(user);
        }

        catch (SQLException e)
        {
            setCommunicate(communicates.databaseError);
            return false;
        }

        return true;
    }

    public UserEnum userType(){
        CurrentSession session = CurrentSession.getInstance();
        if(session.getLoggedUser() == null) {
            return UserEnum.ERROR;
        }
        else
            return session.getLoggedUser().getType();
    }

    private void setCommunicate(String communicate)
    {
        view.getCommunicatesLabel().setText(communicate);
    }

}
