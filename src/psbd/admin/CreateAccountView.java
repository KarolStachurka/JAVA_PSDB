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
    private JButton createAccountButton;
    private JTextField confirmPasswordTextInput;
    private JTextField emailTextInput;
    private JTextField confirmEmailTextInput;
    private JComboBox companyNameBox;
    private JButton backButton;
    private JTextField phoneNumberTextInput;
    private JComboBox accountTypeBox;
    private JButton removeThisAccountButton;
    private JTextField peselTextInput;
    private JLabel communicatesLabel;
    private JButton editAccountButton;

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
        communicatesLabel.setText("");
    }

    public JButton getBackButton() {
        return backButton;
    }

    JButton getCreateAccountButton() {
        return createAccountButton;
    }

    JButton getRemoveThisAccountButton() {
        return removeThisAccountButton;
    }

    JButton getEditAccountButton() {
        return editAccountButton;
    }

    JTextField getNameTextInput() {
        return nameTextInput;
    }

    JTextField getSurnameTextInput() {
        return surnameTextInput;
    }

    JTextField getPeselTextInput() {
        return peselTextInput;
    }

    JTextField getConfirmEmailTextInput() {
        return confirmEmailTextInput;
    }

    JTextField getEmailTextInput() {
        return emailTextInput;
    }

    JTextField getLoginTextInput() {
        return loginTextInput;
    }

    JTextField getPasswordTextInput() {
        return passwordTextInput;
    }

    JTextField getConfirmPasswordTextInput() {
        return confirmPasswordTextInput;
    }

    JTextField getPhoneNumberTextInput() {
        return phoneNumberTextInput;
    }

    JComboBox getAccountTypeBox() {
        return accountTypeBox;
    }

    JComboBox getCompanyNameBox() {
        return companyNameBox;
    }

    JLabel getCommunicatesLabel() {
        return communicatesLabel;
    }
}
