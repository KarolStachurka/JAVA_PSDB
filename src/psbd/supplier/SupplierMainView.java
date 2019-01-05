package psbd.supplier;

import psbd.utils.Messages;
import psbd.utils.MyTableModel;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SupplierMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton logoutButton;
    private JButton createNewDeliveryButton;
    private JButton StorageStateButton;
    private JButton statisticsButton;
    private JLabel userDataLabel;
    private JButton ingredientListButton;
    private JTable deliveriesTable;

    public SupplierMainView()
    {
        setWindowName(PanelEnum.SUPPLIERMAIN);
        Messages messages = Messages.getInstance();
        MyTableModel model = new MyTableModel(messages.DELIVERIES_LIST_TABLE_HEADERS,4);
        deliveriesTable.setModel(model);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        DefaultTableModel model = (DefaultTableModel) deliveriesTable.getModel();
        model.setRowCount(0);
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getCreateNewDeliveryButton() {
        return createNewDeliveryButton;
    }

    public JButton getStorageStateButton() {
        return StorageStateButton;
    }

    public JButton getStatisticsButton() {
        return statisticsButton;
    }

    public JButton getIngredientListButton() {
        return ingredientListButton;
    }

    public JLabel getUserDataLabel() {
        return userDataLabel;
    }

    public JTable getDeliveriesTable() {
        return deliveriesTable;
    }
}
