package psbd.client;

import psbd.utils.Messages;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EditClientDataView extends ViewTemplate {
    private Messages messages;
    private JPanel WindowPanel;
    private JButton addNewAddressButton;
    private JTextField addressTextInput;
    private JTextField nameTextInput;
    private JTextField surnameTextInput;
    private JTextField emailTextInput;
    private JTextField confirmEmailTextInput;
    private JTextField phoneTextInput;
    private JTextField currentPasswordTextInput;
    private JButton backButton;
    private JButton confirmButton;
    private JTabbedPane tabbedPane1;
    private JTable addressTable;
    private JButton removeAddressButton;
    private JTextField newPasswordTextInput;
    private JTextField confirmNewPasswordTextInput;
    private JLabel messagesLabel;

    public EditClientDataView()
    {
        setWindowName(PanelEnum.EDITCLIENTDATA);
        DefaultTableModel model = new DefaultTableModel(messages.ADDRESS_TABLE_HEADERS, 0);
        addressTable.setModel(model);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        DefaultTableModel model = (DefaultTableModel) addressTable.getModel();
        model.setRowCount(0);
        nameTextInput.setText("");
        surnameTextInput.setText("");
        phoneTextInput.setText("");
        emailTextInput.setText("");
        confirmEmailTextInput.setText("");
        newPasswordTextInput.setText("");
        confirmNewPasswordTextInput.setText("");
        currentPasswordTextInput.setText("");
        addressTextInput.setText("");
        messagesLabel.setText("");

    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JButton getAddNewAddressButton() {
        return addNewAddressButton;
    }

    public JButton getRemoveAddressButton() {
        return removeAddressButton;
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

    public JTextField getAddressTextInput() {
        return addressTextInput;
    }

    public JTable getAddressTable() {
        return addressTable;
    }

    public JLabel getMessagesLabel() {
        return messagesLabel;
    }
}
