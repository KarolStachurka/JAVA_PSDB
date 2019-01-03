package psbd.supplier;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CreateDeliveryView extends ViewTemplate {
    private JButton backButton;
    private JPanel WindowPanel;
    private JTextField quantityTextInput;
    private JTextField expirationDateTextInput;
    private JButton addButton;
    private JButton removeButton;
    private JComboBox ingredientsComboBox;
    private JComboBox warehousesComboBox;
    private JTable deliveriesTable;
    private JTextField orderDateTextInput;
    private JButton editButton;
    private JLabel messagesLabel;

    public CreateDeliveryView()
    {
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

    public JTextField getExpirationDateTextInput() {
        return expirationDateTextInput;
    }

    public JTextField getOrderDateTextInput() {
        return orderDateTextInput;
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


    @Override
    public void cleanAll() {
        quantityTextInput.setText("");
        expirationDateTextInput.setText("");
        orderDateTextInput.setText("");
        DefaultTableModel model = (DefaultTableModel) deliveriesTable.getModel();
        model.setRowCount(0);
    }
}
