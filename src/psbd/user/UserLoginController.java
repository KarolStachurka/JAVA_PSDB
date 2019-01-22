package psbd.user;

import psbd.models.CurrentSession;
import psbd.utils.Messages;
import psbd.utils.DatabaseConnector;
import psbd.utils.UserEnum;
import psbd.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginController {
    private UserLoginView view;
    private Messages messages;

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
        String password = view.getPasswordTextInput().getText();

        DatabaseConnector database = DatabaseConnector.getInstance();
        try {
            if (!database.checkIfRecordExists("users", "user_login", login)) {
                setMessage(messages.NOT_EXISTS);
                return false;
            }
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
        }
        String SQLQuery = "SELECT * FROM users WHERE user_login = ? AND user_password = SHA2(?, 256) AND active = 1" ;
        PreparedStatement statement = database.getPreparedStatement(SQLQuery);

        if(statement == null)
        {
            return false;
        }
        try {
            statement.setString(1, login);
            statement.setString(2, password);
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
            setMessage(Messages.INVALID_PASSWORD);
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

    public void setMessage(String communicate)
    {
        view.getMessagesLabel().setText(communicate);
    }

}
