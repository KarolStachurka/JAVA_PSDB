package psbd.admin;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;

public class CreateAccountView extends ViewTemplate {
    private JPanel WindowPanel;
    private JTextField nameTextInput;
    private JTextField loginTextInput;
    private JTextField passwordTextInput;
    private JTextField surnameTextInput;
    private JButton confirmButton;
    private JTextField confirmPasswordTextInput;
    private JTextField emailTextInput;
    private JTextField confirmEmailTextInput;
    private JComboBox companyNameBox;
    private JButton backButton;
    private JTextField phoneNumberTextInput;
    private JComboBox accountTypeBox;
    private JButton removeThisAccountButton;
    private JTextField peselTextInput;

    public CreateAccountView()
    {
        setWindowName(PanelEnum.CREATEACCOUNT);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        nameTextInput.setText("");
        surnameTextInput.setText("");
        loginTextInput.setText("");
        passwordTextInput.setText("");
        peselTextInput.setText("");
        confirmPasswordTextInput.setText("");
        emailTextInput.setText("");
        confirmEmailTextInput.setText("");
        phoneNumberTextInput.setText("");
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getRemoveThisAccountButton() {
        return removeThisAccountButton;
    }

    public JTextField getNameTextInput() {
        return nameTextInput;
    }

    public JTextField getSurnameTextInput() {
        return surnameTextInput;
    }

    public JTextField getPeselTextInput() {
        return peselTextInput;
    }

    public JTextField getConfirmEmailTextInput() {
        return confirmEmailTextInput;
    }

    public JTextField getEmailTextInput() {
        return emailTextInput;
    }

    public JTextField getLoginTextInput() {
        return loginTextInput;
    }

    public JTextField getPasswordTextInput() {
        return passwordTextInput;
    }

    public JTextField getConfirmPasswordTextInput() {
        return confirmPasswordTextInput;
    }

    public JTextField getPhoneNumberTextInput() {
        return phoneNumberTextInput;
    }

    public JComboBox getAccountTypeBox() {
        return accountTypeBox;
    }

    public JComboBox getCompanyNameBox() {
        return companyNameBox;
    }
}
