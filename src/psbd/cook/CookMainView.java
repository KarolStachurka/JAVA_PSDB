package psbd.cook;

import psbd.utils.Messages;
import psbd.utils.MyTableModel;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CookMainView extends ViewTemplate {

    private JButton logoutButton;
    private JPanel WindowPanel;
    private JButton setOrderReadyButton;
    private JButton updateListButton;
    private JLabel userDataLabel;
    private JTable ordersTable;
    private JTable orderDetailsTable;
    private JTable dishRecipeTable;

    public CookMainView()
    {
        setWindowName(PanelEnum.COOKMAIN);
        DefaultTableModel orderModel = new DefaultTableModel(Messages.COOK_ORDER_TABLE_HEADERS,0);
        ordersTable.setModel(orderModel);
        MyTableModel orderDetailsModel = new MyTableModel(Messages.ORDER_DETAILS_TABLE_HEADERS,1);
        orderDetailsTable.setModel(orderDetailsModel);
        DefaultTableModel dishRecipeModel = new DefaultTableModel(Messages.COOK_INGREDIENTS_TABLE_HEADERS,0);
        dishRecipeTable.setModel(dishRecipeModel);

    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        DefaultTableModel orderDetailsTableModel = (DefaultTableModel) orderDetailsTable.getModel();
        orderDetailsTableModel.setRowCount(0);
        DefaultTableModel ordersTableModel = (DefaultTableModel) ordersTable.getModel();
        ordersTableModel.setRowCount(0);
        DefaultTableModel dishRecipeTableModel = (DefaultTableModel) dishRecipeTable.getModel();
        dishRecipeTableModel.setRowCount(0);

    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getSetOrderReadyButton() {
        return setOrderReadyButton;
    }

    public JButton getUpdateListButton() {
        return updateListButton;
    }

    public JLabel getUserDataLabel() {
        return userDataLabel;
    }

    public JTable getOrderDetailsTable() {
        return orderDetailsTable;
    }

    public JTable getDishRecipeTable() {
        return dishRecipeTable;
    }

    public JTable getOrdersTable() {
        return ordersTable;
    }
}
