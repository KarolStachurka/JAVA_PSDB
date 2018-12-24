package psbd.admin;

import com.mysql.jdbc.StringUtils;
import psbd.Communicates;
import psbd.UserEnum;
import psbd.DatabaseConnector;
import psbd.user.User;

import javax.jws.soap.SOAPBinding;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAccountController {
    private CreateAccountView view;
    private Communicates communicates;

    public CreateAccountController(CreateAccountView view)
    {
        this.view = view;
        setUserTypes();
        view.getCreateAccountButton().addActionListener(e-> {
            if(createAccount(createUser()))
            {
                this.view.cleanAll();
                setCommunicate(communicates.accountCreated);
            }
        });
        view.getEditAccountButton().addActionListener(e->{
            if(editAccountData(createUser()))
            {
                this.view.cleanAll();
                setCommunicate(communicates.accountEdited);
            }
        });
        view.getRemoveThisAccountButton().addActionListener(e->{
            if(removeAccount(createUser()))
            {
                this.view.cleanAll();
                setCommunicate(communicates.accountRemoved);
            }
        });
    }

    public CreateAccountView getView() {
        return view;
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
            company = this.view.getCompanyNameBox().getSelectedItem().toString();
        }
        catch (NullPointerException e)
        {
            company = null;
        }
        User user = new User(UserEnum.valueOf(userType),login,password,name,surname,email,pesel,phoneNumber,company);
        return user;
    }
    /* todo :
        add company name handling
    */
    private boolean createAccount(User user)
    {
        String confirmPassword = view.getConfirmPasswordTextInput().getText();
        String confirmEmail = view.getConfirmEmailTextInput().getText();

        if(!checkInputValues(user.getLogin(),user.getPassword(),confirmPassword,user.getName(),user.getSurname(),user.getPesel(),
                user.getPhoneNumber(),user.getEmail(),confirmEmail))  {
            return false;
        }
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "INSERT INTO users(user_type, user_login, user_password, name, surname, pesel, email, phone_number, company) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setCommunicate(communicates.databaseError);
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
            setCommunicate(communicates.databaseError);
            return false;
        }
        return  database.executeStatement();
    }

    private boolean editAccountData(User user)
    {
        if(checkIfAccountNotExist(user.getLogin()))
            return false;
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "UPDATE users SET user_password = ? ,name = ? ,surname = ? ,pesel = ? ,email = ? ,phone_number = ? ,company = ? WHERE user_login = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        if(statement == null)
        {
            setCommunicate(communicates.databaseError);
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
            setCommunicate(communicates.databaseError);
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
            return false;
        }
        return !checkIfAccountNotExist(user.getLogin());
    }

    private boolean checkIfAccountNotExist(String login)
    {
        DatabaseConnector database = DatabaseConnector.getInstance();
        String sqlQuery = "SELECT FROM users WHERE user_login = ?";
        PreparedStatement statement = database.getPreparedStatement(sqlQuery);
        ResultSet result = null;
        if(statement == null)
        {
            return false;
        }
        try {
            statement.setString(1, login);
            database.setPreparedStatement(statement);
            result = statement.executeQuery();

        }
        catch (SQLException e)
        {
            return false;
        }
        return result != null;
    }


    private void setCommunicate(String error)
    {
        view.getCommunicatesLabel().setText(error);
    }

    private void setUserTypes()
    {
        for(UserEnum user: UserEnum.values())
        {
            view.getAccountTypeBox().addItem(user.toString());
        }
    }
    private boolean checkInputValues(String login, String password, String confirmPassword,
                                     String name, String surname, String pesel, String phone,
                                     String email, String confirmEmail)
    {
        if(!password.equals(confirmPassword))
        {
            setCommunicate(communicates.passwordNotMatch);
            return false;
        }

        if(!email.equals(confirmEmail))
        {
            setCommunicate(communicates.emailNotMatch);
            return false;
        }

        if(StringUtils.isEmptyOrWhitespaceOnly(login) ||
            StringUtils.isEmptyOrWhitespaceOnly(password)||
            StringUtils.isEmptyOrWhitespaceOnly(name)||
            StringUtils.isEmptyOrWhitespaceOnly(surname)||
            StringUtils.isEmptyOrWhitespaceOnly(pesel)||
            StringUtils.isEmptyOrWhitespaceOnly(phone))
        {
            setCommunicate(communicates.unfilledNecessaryFields);
            return false;
        }

        if(!checkIfAccountNotExist(login))
        {
            setCommunicate(communicates.alreadyExists);
            return false;
        }
        return true;
    }


}
