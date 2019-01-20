package psbd.courier;

import psbd.utils.Messages;
import psbd.utils.MyTableModel;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CourierMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton logoutButton;
    private JButton updateListButton;
    private JLabel userDataLabel;
    private JTable ordersTable;

    public CourierMainView()
    {
        setWindowName(PanelEnum.COURIERMAIN);
        MyTableModel orderModel = new MyTableModel(Messages.COOK_ORDER_TABLE_HEADERS,2);
        ordersTable.setModel(orderModel);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        DefaultTableModel ordersTableModel = (DefaultTableModel) ordersTable.getModel();
        ordersTableModel.setRowCount(0);

    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getUpdateListButton() {
        return updateListButton;
    }

    public JLabel getUserDataLabel() {
        return userDataLabel;
    }

    public JTable getOrdersTable() {
        return ordersTable;
    }
}
