package psbd.client;

import com.mysql.jdbc.StringUtils;
import psbd.models.CurrentSession;
import psbd.models.User;
import psbd.utils.DatabaseConnector;
import psbd.utils.Messages;
import psbd.utils.UserEnum;

import javax.jws.soap.SOAPBinding;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EditClientDataController {
    private EditClientDataView view;
    private Messages messages;

    public EditClientDataController(EditClientDataView view)
    {
        this.view = view;
        view.getConfirmButton().addActionListener(e->{
            if(changeUserData(createUser()))
            {
                view.cleanAll();
                setDataFields();
                setMessage(messages.ACCOUNT_EDITED);
            }
        });
        view.getBackButton().addActionListener(e->{
            view.cleanAll();
        });

        view.getAddNewAddressButton().addActionListener(e->{
            String address = view.getAddressTextInput().getText();
            if(addNewAddress(address))
            {
                view.cleanAll();
                updateList();
                setMessage(messages.ACCOUNT_EDITED);
                setDataFields();
            }
            else
            {
                setMessage(messages.INVALID_INPUT);
            }

        });

        view.getRemoveAddressButton().addActionListener(e->{
            String address = view.getAddressTable().getValueAt(view.getAddressTable().getSelectedRow(),0).toString();
            if(removeAddress(address))
            {
                view.cleanAll();
                updateList();
                setMessage(messages.ACCOUNT_EDITED);
                setDataFields();
            }
            else
            {
                setMessage(messages.DATABASE_ERROR);
            }
        });
    }

    public EditClientDataView getView() {
        view.cleanAll();
        updateList();
        setDataFields();
        return view;
    }

    private void updateList()
    {
        Object [][] data = getAddressList();
        DefaultTableModel model = (DefaultTableModel) view.getAddressTable().getModel();
        if(data.length > 0)
            for(Object[] row:data)
            {
                model.addRow(row);
            }
        view.getAddressTable().repaint();
    }

    private boolean addNewAddress(String address)
    {
        CurrentSession session = CurrentSession.getInstance();
        String login = session.getLoggedUser().getLogin();
        if(StringUtils.isEmptyOrWhitespaceOnly(address))
        {
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO client_addresses(`address`, `client_id`) " +
                "VALUES(?,(SELECT user_id FROM users WHERE user_login = ?))";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setString(1,address);
            statement.setString(2, login);
            database.setPreparedStatement(statement);
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        if(!database.executeStatement())
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }

        return true;
    }

    private boolean removeAddress(String address)
    {
        CurrentSession session = CurrentSession.getInstance();
        String login = session.getLoggedUser().getLogin();
        if(StringUtils.isEmptyOrWhitespaceOnly(address))
        {
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "DELETE FROM client_addresses WHERE `address` = ? AND `client_id` = (SELECT user_id FROM users WHERE user_login = ?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setString(1,address);
            statement.setString(2, login);
            database.setPreparedStatement(statement);
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        if(!database.executeStatement())
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }

        return true;
    }

    private Object[][] getAddressList() {
        ArrayList<ArrayList<Object>> dataList = new ArrayList<>();
        Object[][] errorData = {{}};
        String login;

        CurrentSession session = CurrentSession.getInstance();
        try
        {
            login = session.getLoggedUser().getLogin();
        }
        catch (NullPointerException e)
        {
            return errorData;
        }

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM client_addresses WHERE `client_id` = (SELECT user_id FROM users WHERE user_login = ?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return errorData;
        }
        try {
            statement.setString(1, login);
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        }
        catch (SQLException e)
        {
            return errorData;
        }
        try {
            while (result.next()) {
                ArrayList<Object> dataRow = new ArrayList<>();
                dataRow.add(result.getString("address"));
                dataList.add(dataRow);
            }
        }
        catch (SQLException e)
        {
            return errorData;
        }
        Object [][] data = new Object[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i).toArray(new Object[0]);
        }
        return data;
    }

    private boolean changeUserData(User user)
    {

        DatabaseConnector database = DatabaseConnector.getInstance();
        if(user.getPassword() != null)
        {
            if(!changePassword(user))
            {
                return false;
            }
        }
        String sqlQuery = "UPDATE users SET name = ? ,surname = ? ,email = ? , phone_number = ?  WHERE user_login = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getLogin());
            database.setPreparedStatement(statement);
            database.executeStatement();
            CurrentSession.getInstance().setLoggedUser(user);
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        return true;
    }

    private boolean changePassword(User user)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        //verification
        if(!verifyPassword(user))
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        String sqlQuery = "UPDATE users SET user_password = SHA2(?, 256)  WHERE user_login = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getLogin());
            database.setPreparedStatement(statement);
            database.executeStatement();
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        return true;
    }

    private boolean verifyPassword(User user)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        ResultSet result;
        String SQLQuery = "SELECT * FROM users WHERE user_login = ? AND user_password = SHA2(?, 256)" ;
        PreparedStatement statement = database.getPreparedStatement(SQLQuery);
        try {
            statement.setString(1, user.getLogin());
            statement.setString(2,view.getCurrentPasswordTextInput().getText());
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
            return result.next();
        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
    }

    private User createUser() {
        CurrentSession session = CurrentSession.getInstance();
        String name = view.getNameTextInput().getText();
        String surname = view.getSurnameTextInput().getText();
        String login = session.getLoggedUser().getLogin();
        String password;
        if(view.getNewPasswordTextInput().getText().equals(view.getConfirmNewPasswordTextInput().getText()) &&
                !StringUtils.isEmptyOrWhitespaceOnly(view.getNewPasswordTextInput().getText()))
        {
            password = view.getNewPasswordTextInput().getText();
        }
        else
        {
            password = null;
            if(!StringUtils.isEmptyOrWhitespaceOnly(view.getNewPasswordTextInput().getText())||
                    !StringUtils.isEmptyOrWhitespaceOnly(view.getConfirmNewPasswordTextInput().getText()))
            {
                setMessage(messages.PASSWORD_NOT_MATCH);
            }
        }
        String email;
        if(view.getEmailTextInput().getText().equals(view.getConfirmEmailTextInput().getText())) {
            email = view.getEmailTextInput().getText();
        }
        else
        {
          email = session.getLoggedUser().getEmail();
        }
        String phoneNumber = view.getPhoneTextInput().getText();
        String company = session.getLoggedUser().getCompany();
        UserEnum userType = UserEnum.CLIENT;
        return new User(userType, login, password, name, surname, email, null, phoneNumber, company);
    }


        private void setDataFields()
    {
        try {
            CurrentSession session = CurrentSession.getInstance();
            view.getNameTextInput().setText(session.getLoggedUser().getName());
            view.getSurnameTextInput().setText(session.getLoggedUser().getSurname());
            view.getPhoneTextInput().setText(session.getLoggedUser().getPhoneNumber());
            view.getEmailTextInput().setText(session.getLoggedUser().getEmail());
        }
        catch (NullPointerException e)
        {

        }
    }

    private void setMessage(String message)
    {
        view.getMessagesLabel().setText(message);
    }

}
