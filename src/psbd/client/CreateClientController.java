package psbd.client;

import com.mysql.jdbc.StringUtils;
import psbd.models.User;
import psbd.utils.Messages;
import psbd.utils.DatabaseConnector;
import psbd.utils.UserEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateClientController {
    private CreateClientView view;
    private Messages messages;

    public CreateClientController(CreateClientView view)
    {
        this.view = view;
        setCompaniesList();

        view.getConfirmButton().addActionListener(e->{
            if(createClientAccount(createUser()))
            {
                setMessage(messages.accountCreated);
            }
        });
    }
    public CreateClientView getView() {
        return view;
    }

    private void setMessage(String communicate)
    {
        view.getMessagesLabel().setText(communicate);
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

    private User createUser()
    {
        String name = view.getNameTextInput().getText();
        String surname= view.getSurnameTextInput().getText();
        String login = view.getLoginTextInput().getText();
        String password = view.getPasswordTextInput().getText();
        String email = view.getEmailTextInput().getText();
        String phoneNumber = view.getPhoneNumberTextInput().getText();
        UserEnum userType = UserEnum.CLIENT;
        String company;
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

        return new User(userType,login,password,name,surname,email,null,phoneNumber,company);
    }

    private boolean createClientAccount(User user)
    {
        String confirmPassword = view.getConfirmPasswordTextInput().getText();
        String confirmEmail = view.getConfirmEmailTextInput().getText();

        if(!checkInputValues(user.getLogin(),user.getPassword(),confirmPassword,user.getName(),user.getSurname(),user.getEmail(),confirmEmail,user.getPhoneNumber()))  {
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO users(user_type, user_login, user_password, name, surname, pesel, email, phone_number, company) " +
                "VALUES(?,?, SHA2(?, 256),?,?,?,?,?,?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setMessage(messages.databaseError);
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
            setMessage(messages.databaseError);
            return false;
        }
        if(!database.executeStatement())
        {
            setMessage(messages.databaseError);
            return false;
        }
        return true;
    }

    private boolean checkInputValues(String login, String password, String confirmPassword,
                                     String name, String surname, String email, String confirmEmail, String phoneNumber)
    {
        if(!password.equals(confirmPassword))
        {
            setMessage(messages.passwordNotMatch);
            return false;
        }

        if(!email.equals(confirmEmail))
        {
            setMessage(messages.emailNotMatch);
            return false;
        }

        if(StringUtils.isEmptyOrWhitespaceOnly(login) ||
                StringUtils.isEmptyOrWhitespaceOnly(password)||
                StringUtils.isEmptyOrWhitespaceOnly(name)||
                StringUtils.isEmptyOrWhitespaceOnly(surname) ||
                StringUtils.isEmptyOrWhitespaceOnly(email) ||
                StringUtils.isEmptyOrWhitespaceOnly(phoneNumber))
        {
            setMessage(messages.unfilledNecessaryFields);
            return false;
        }

        if(checkIfAccountExist(login))
        {
            setMessage(messages.alreadyExists);
            return false;
        }
        return true;
    }

    private boolean checkIfAccountExist(String login)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        try {
            return database.checkIfRecordExists("users", "user_login", login);
        }
        catch (SQLException e)
        {
            setMessage(messages.databaseError);
            return false;
        }
    }

}
