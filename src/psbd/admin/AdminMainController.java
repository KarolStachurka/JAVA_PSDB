package psbd.admin;

import com.mysql.jdbc.StringUtils;
import psbd.models.CurrentSession;
import psbd.utils.Messages;
import psbd.utils.UserEnum;
import psbd.utils.DatabaseConnector;
import psbd.models.User;

import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/*
 todo: remove test button
 */
public class AdminMainController {
    private AdminMainView view;
    private Messages messages;

    public AdminMainController(){}

    public AdminMainController(AdminMainView view)
    {
        this.view = view;
        setUserTypes();
        setCompaniesList();
        updateList();
        view.getUsersTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                view.getAccountTypeBox().setSelectedItem(view.getUsersTable().getValueAt(
                        view.getUsersTable().getSelectedRow(), 0)
                );
                view.getLoginTextInput().setText(view.getUsersTable().getValueAt(
                        view.getUsersTable().getSelectedRow(), 1).toString()
                );
                view.getNameTextInput().setText(view.getUsersTable().getValueAt(
                        view.getUsersTable().getSelectedRow(), 2).toString()
                );
                view.getSurnameTextInput().setText(view.getUsersTable().getValueAt(
                        view.getUsersTable().getSelectedRow(), 3).toString()
                );
                view.getEmailTextInput().setText(view.getUsersTable().getValueAt(
                        view.getUsersTable().getSelectedRow(), 4).toString()
                );
                view.getPhoneNumberTextInput().setText(view.getUsersTable().getValueAt(
                        view.getUsersTable().getSelectedRow(), 5).toString()
                );
                view.getCompanyNameBox().setSelectedItem(view.getUsersTable().getValueAt(
                        view.getUsersTable().getSelectedRow(), 6)
                );
                try {
                    view.getPeselTextInput().setText(view.getUsersTable().getValueAt(
                            view.getUsersTable().getSelectedRow(), 7).toString()
                    );
                }
                catch (NullPointerException e)
                {
                    view.getPeselTextInput().setText( "");
                }
            }
        });

        view.getCreateAccountButton().addActionListener(e-> {
            if(createAccount(createUser()))
            {
                this.view.cleanAll();
                updateList();
                setMessage(messages.ACCOUNT_CREATED);
            }
        });
        view.getEditAccountButton().addActionListener(e->{
            if(editAccountData(createUser()))
            {
                this.view.cleanAll();
                updateList();
                setMessage(messages.ACCOUNT_EDITED);
            }

        });
        view.getRemoveThisAccountButton().addActionListener(e->{
            if(removeAccount(createUser()))
            {
                this.view.cleanAll();
                updateList();
                setMessage(messages.ACCOUNT_REMOVED);
            }
        });
    }

    public AdminMainView getView() {
        return view;
    }

    public void login()
    {
        CurrentSession session = CurrentSession.getInstance();
        String text = "Hello " + session.getLoggedUser().getName() + " " + session.getLoggedUser().getSurname();
        view.getUserDataLabel().setText(text);
    }

    public void logout()
    {
        CurrentSession session = CurrentSession.getInstance();
        session.setLoggedUser(null);
    }

    private User createUser()
    {

        String name = view.getNameTextInput().getText();
        String surname= view.getSurnameTextInput().getText();
        String login = view.getLoginTextInput().getText();
        String password = view.getPasswordTextInput().getText();
        String email = view.getEmailTextInput().getText();
        String pesel = view.getPeselTextInput().getText();
        String phoneNumber = view.getPhoneNumberTextInput().getText();
        String userType;
        String company;
        try {
            userType = this.view.getAccountTypeBox().getSelectedItem().toString();
        }
        catch (NullPointerException e)
        {
            userType = null;
        }
        try {
            String company_name = this.view.getCompanyNameBox().getSelectedItem().toString();
            if(company_name.equals("Empty"))
            {
                company = null;
            }
            else
            {
                company = company_name;
            }
        }
        catch (NullPointerException e)
        {
            company = null;
        }

        return new User(UserEnum.valueOf(userType),login,password,name,surname,email,pesel,phoneNumber,company);
    }

    private boolean createAccount(User user)
    {
        String confirmPassword = view.getConfirmPasswordTextInput().getText();
        String confirmEmail = view.getConfirmEmailTextInput().getText();

        if(!checkInputValues(user.getLogin(),user.getPassword(),confirmPassword,user.getName(),user.getSurname(),user.getEmail(),confirmEmail))  {
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO users(user_type, user_login, user_password, name, surname, pesel, email, phone_number, company) " +
                "VALUES(?,?, SHA2(?, 256),?,?,?,?,?,?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setString(1, user.getType().toString());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getName());
            statement.setString(5, user.getSurname());
            statement.setString(6, user.getPesel());
            statement.setString(7, user.getEmail());
            statement.setString(8, user.getPhoneNumber());
            statement.setString(9, user.getCompany());
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
        return  true;
    }

    private boolean editAccountData(User user)
    {
        if(!checkIfAccountExist(user.getLogin()))
        {
            setMessage(messages.NOT_EXISTS);
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE users SET user_password = SHA2(?, 256) ,name = ? ,surname = ? ,pesel = ? ,email = ? ," +
                "phone_number = ? ,company = ? WHERE user_login = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        try {
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getPesel());
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhoneNumber());
            statement.setString(7, user.getCompany());
            statement.setString(8, user.getLogin());
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

    private boolean removeAccount(User user)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "DELETE FROM users WHERE user_login = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        try {
            statement.setString(1, user.getLogin());
            database.setPreparedStatement(statement);
            database.executeStatement();

        }
        catch (SQLException e)
        {
            setMessage(messages.DATABASE_ERROR);
            return false;
        }
        return !checkIfAccountExist(user.getLogin());
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


    private void setMessage(String error)
    {
        view.getMessagesLabel().setText(error);
    }

    private void setUserTypes()
    {
        for(UserEnum user: UserEnum.values())
        {
            if(!user.toString().equals("ERROR"))
                view.getAccountTypeBox().addItem(user.toString());
        }
    }
    private void setCompaniesList()
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        view.getCompanyNameBox().addItem("Empty");
        try{
            ResultSet table = database.getFullTableData("companies");
            while (table.next())
            {
                view.getCompanyNameBox().addItem(table.getString("company_name"));
            }
        }
        catch (Exception e)
        {
            // Leave list empty
        }
    }

    private boolean checkInputValues(String login, String password, String confirmPassword,
                                     String name, String surname, String email, String confirmEmail)
    {
        if(!password.equals(confirmPassword))
        {
            setMessage(messages.PASSWORD_NOT_MATCH);
            return false;
        }

//        if(!email.equals(confirmEmail))
//        {
//            setMessage(messages.EMAIL_NOT_MATCH);
//            return false;
//        }

        if(StringUtils.isEmptyOrWhitespaceOnly(login) ||
            StringUtils.isEmptyOrWhitespaceOnly(password)||
            StringUtils.isEmptyOrWhitespaceOnly(name)||
            StringUtils.isEmptyOrWhitespaceOnly(surname) ||
            StringUtils.isEmptyOrWhitespaceOnly(email))
        {
            setMessage(messages.UNFILLED_NECESSARY_FIELDS);
            return false;
        }

        if(checkIfAccountExist(login))
        {
            setMessage(messages.ALREADY_EXISTS);
            return false;
        }
        return true;
    }

    private void updateList()
    {
        String[] columnNames = messages.USER_TABLE_HEADERS;
        String [][] data = getUsersList();
        DefaultTableModel model = (DefaultTableModel) view.getUsersTable().getModel();
        model.setColumnIdentifiers(columnNames);
        if(data.length > 0)
            for(String[] row:data)
            {
                model.addRow(row);
            }
        view.getUsersTable().repaint();
    }

    private String[][] getUsersList()
    {
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();
        String[][] errorData = {{}};

        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT * FROM users";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result;
        if(statement == null)
        {
            return errorData;
        }
        try {
            database.setPreparedStatement(statement);
            result = statement.executeQuery();
        }
        catch (SQLException e)
        {
            return errorData;
        }
        try {
            while (result.next()) {
                ArrayList<String> dataRow = new ArrayList<>();
                dataRow.add(result.getString("user_type"));
                dataRow.add(result.getString("user_login"));
                dataRow.add(result.getString("name"));
                dataRow.add(result.getString("surname"));
                dataRow.add(result.getString("email"));
                dataRow.add(result.getString("phone_number"));
                dataRow.add(result.getString("company"));
                dataRow.add(result.getString("pesel"));
                dataList.add(dataRow);
            }
        }
        catch (SQLException e)
        {
            return errorData;
        }
        String [][] data = new String[dataList.size()][];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i).toArray(new String[0]);
        }

        return data;
    }


}
