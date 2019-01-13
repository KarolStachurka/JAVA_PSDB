package psbd.supplier;

import datechooser.beans.DateChooserCombo;
import psbd.utils.Messages;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CreateDeliveryView extends ViewTemplate {
    private JButton backButton;
    private JPanel WindowPanel;
    private JTextField quantityTextInput;
    private JButton addButton;
    private JButton removeButton;
    private JComboBox ingredientsComboBox;
    private JComboBox warehousesComboBox;
    private JTable deliveriesTable;
    private JButton editButton;
    private JLabel messagesLabel;
    private DateChooserCombo orderDateInput;
    private DateChooserCombo expirationDateInput;

    public CreateDeliveryView()
    {
        Messages messages = Messages.getInstance();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(messages.DELIVERIES_MANAGER_TABLE_HEADERS);
        deliveriesTable.setModel(model);
        setWindowName(PanelEnum.CREATEDELIVERY);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JTextField getQuantityTextInput() {
        return quantityTextInput;
    }

    public JComboBox getIngredientsComboBox() {
        return ingredientsComboBox;
    }

    public JComboBox getWarehousesComboBox() {
        return warehousesComboBox;
    }

    public JTable getDeliveriesTable() {
        return deliveriesTable;
    }

    public JLabel getMessagesLabel() {
        return messagesLabel;
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    public DateChooserCombo getOrderDateInput(){return orderDateInput;}

    public DateChooserCombo getExpirationDateInput(){return expirationDateInput;}


    @Override
    public void cleanAll() {
        quantityTextInput.setText("");
        DefaultTableModel model = (DefaultTableModel) deliveriesTable.getModel();
        model.setRowCount(0);
        deliveriesTable.repaint();
    }

}
