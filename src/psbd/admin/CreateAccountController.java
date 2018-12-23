package psbd.admin;

import psbd.UserEnum;
import psbd.DatabaseConnector;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAccountController {
    private CreateAccountView view;

    public CreateAccountController(CreateAccountView view)
    {
        this.view = view;
        setUserTypes();
        this.view.getConfirmButton().addActionListener(e-> {
            if(createAccount())
            {
                this.view.cleanAll();
            }
            else
            {
                setErrorCommunicate();
            }

        });
    }

    public CreateAccountView getView() {
        return view;
    }
    /* todo :
        add company name handling
    */
    private boolean createAccount()
    {
        String name = view.getNameTextInput().getText();
        String surname= view.getSurnameTextInput().getText();
        String login = view.getLoginTextInput().getText();
        String password = view.getPasswordTextInput().getText();
        String confirmPassowrd = view.getConfirmPasswordTextInput().getText();
        String email = view.getEmailTextInput().getText();
        String confirmEmail = view.getConfirmEmailTextInput().getText();
        String pesel = view.getPeselTextInput().getText();
        String phoneNumber = view.getPhoneNumberTextInput().getText();
        String userType = view.getAccountTypeBox().getSelectedItem().toString();
        if(!password.equals(confirmPassowrd) || !email.equals(confirmEmail))
            return false;
        try {
            DatabaseConnector database = DatabaseConnector.getInstance();
            database.getData("select * from users");
            ResultSet myRs = database.getResult();
            while (myRs.next()) {
                System.out.println(myRs.getString("user_login") + ", " + myRs.getString("user_password"));
            }
        }
        catch (SQLException e)
        {
            return false;
        }
        return true;
    }

    private String getName()
    {
        return view.getNameTextInput().getText();
    }

    private void setErrorCommunicate()
    {

    }

    private void setUserTypes()
    {
        for(UserEnum user: UserEnum.values())
        {
            view.getAccountTypeBox().addItem(user.toString());
        }
    }
}
