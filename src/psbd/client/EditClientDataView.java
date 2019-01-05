package psbd.client;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class EditClientDataView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton addNewAdressButton;
    private JTextField textField1;
    private JTextField nameTextInput;
    private JTextField surnameTextInput;
    private JTextField emailTextInput;
    private JTextField confirmEmailTextInput;
    private JTextField phoneTextInput;
    private JTextField currentPasswordTextInput;
    private JButton backButton;
    private JButton confirmButton;
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JButton removeAddressButton;
    private JTextField newPasswordTextInput;
    private JTextField confirmNewPasswordTextInput;
    private JLabel messagesLabel;

    public EditClientDataView()
    {
        setWindowName(PanelEnum.EDITCLIENTDATA);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        nameTextInput.setText("");
        surnameTextInput.setText("");
        phoneTextInput.setText("");
        emailTextInput.setText("");
        confirmEmailTextInput.setText("");
        newPasswordTextInput.setText("");
        confirmNewPasswordTextInput.setText("");
        currentPasswordTextInput.setText("");
        messagesLabel.setText("");

    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getAddNewAdressButton() {
        return addNewAdressButton;
    }

    public JTextField getNameTextInput() {
        return nameTextInput;
    }

    public JTextField getSurnameTextInput() {
        return surnameTextInput;
    }

    public JTextField getPhoneTextInput() {
        return phoneTextInput;
    }

    public JTextField getConfirmEmailTextInput() {
        return confirmEmailTextInput;
    }

    public JTextField getEmailTextInput() {
        return emailTextInput;
    }

    public JTextField getCurrentPasswordTextInput() {
        return currentPasswordTextInput;
    }

    public JTextField getNewPasswordTextInput() {
        return newPasswordTextInput;
    }

    public JTextField getConfirmNewPasswordTextInput() {
        return confirmNewPasswordTextInput;
    }

    public JLabel getMessagesLabel() {
        return messagesLabel;
    }
}
