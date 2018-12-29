package psbd.admin;

import psbd.PanelEnum;
import psbd.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminMainView extends ViewTemplate {
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
    private JButton logoutButton;
    private JTextField phoneNumberTextInput;
    private JComboBox accountTypeBox;
    private JButton removeThisAccountButton;
    private JTextField peselTextInput;
    private JLabel communicatesLabel;
    private JButton editAccountButton;
    private JTable usersTable;
    private JButton editReviewsButton;
    private JButton editDiscountsButton;
    private JLabel userDataLabel;
    private JButton testButton;

    public AdminMainView()
    {
        setWindowName(PanelEnum.ADMINMAIN);

    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
        model.setRowCount(0);
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

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getEditDiscountsButton() {
        return editDiscountsButton;
    }

    public JButton getEditReviewsButton() {
        return editReviewsButton;
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

    public JButton getTestButton() {
        return testButton;
    }

    public JLabel getUserDataLabel() {
        return userDataLabel;
    }

    public JTable getUsersTable() {
        return usersTable;
    }
}
