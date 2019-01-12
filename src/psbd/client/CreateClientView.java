package psbd.client;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class CreateClientView extends ViewTemplate {

    private JPanel WindowPanel;
    private JTextField nameTextInput;
    private JTextField surnameTextInput;
    private JTextField loginTextInput;
    private JTextField passwordTextInput;
    private JButton confirmButton;
    private JTextField confirmPasswordTextInput;
    private JTextField emailTextInput;
    private JTextField confirmEmailTextInput;
    private JComboBox companyNameBox;
    private JButton backButton;
    private JTextField phoneNumberTextInput;
    private JLabel messagesLabel;

    public CreateClientView()
    {
        setWindowName(PanelEnum.CREATECLIENACCOUNT);

    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JTextField getLoginTextInput() {
        return loginTextInput;
    }

    public JTextField getPasswordTextInput() {
        return passwordTextInput;
    }

    public JTextField getPhoneNumberTextInput() {
        return phoneNumberTextInput;
    }

    public JTextField getConfirmPasswordTextInput() {
        return confirmPasswordTextInput;
    }

    public JTextField getEmailTextInput() {
        return emailTextInput;
    }

    public JTextField getConfirmEmailTextInput() {
        return confirmEmailTextInput;
    }

    public JTextField getSurnameTextInput() {
        return surnameTextInput;
    }

    public JTextField getNameTextInput() {
        return nameTextInput;
    }

    public JComboBox getCompanyNameBox() {
        return companyNameBox;
    }

    public JLabel getMessagesLabel() {
        return messagesLabel;
    }

    @Override
    public void cleanAll() {
        loginTextInput.setText("");
        passwordTextInput.setText("");
        confirmPasswordTextInput.setText("");
        nameTextInput.setText("");
        surnameTextInput.setText("");
        emailTextInput.setText("");
        confirmEmailTextInput.setText("");
        phoneNumberTextInput.setText("");
        messagesLabel.setText("");
    }
}
