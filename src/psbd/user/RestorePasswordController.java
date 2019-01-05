package psbd.user;

import psbd.utils.Messages;
import psbd.utils.DatabaseConnector;

import java.sql.SQLException;

public class RestorePasswordController {
    private RestorePasswordView view;
    private Messages messages;

    public RestorePasswordController(RestorePasswordView view)
    {
        this.view = view;
        view.getSendMailButton().addActionListener(e->{
            if(!restorePassword())
            {

                view.cleanAll();
                setMessage(messages.NOT_EXISTS);
            }
        });
    }

    public RestorePasswordView getView() {
        return view;
    }

    private void setMessage(String communicate)
    {
        view.getMessagesLabel().setText(communicate);
    }

    private boolean restorePassword()
    {
        String login = view.getLoginTextInput().getText();
        if(checkIfAccountExist(login))
        {
            setMessage(messages.RESTORE_PASSWORD);
            return true;
        }
        return false;
    }

    private boolean checkIfAccountExist(String login)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try {
            return database.checkIfRecordExists("users", "user_login", login);
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
    }
}
