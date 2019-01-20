package psbd.client;

import psbd.utils.Messages;
import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClientMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton createNewOrderButton;
    private JButton createDishReviewButton;
    private JButton logoutButton;
    private JButton editUserDataButton;
    private JLabel userDataLabel;
    private JTable clientOrderTable;
    private JTable orderDetailsTable;
    private JLabel discountInfoLabel;

    public ClientMainView()
    {
        setWindowName(PanelEnum.CLIENTMAIN);
        DefaultTableModel orderModel = new DefaultTableModel(Messages.CLIENT_ORDER_TABLE_HEADERS,0);
        clientOrderTable.setModel(orderModel);
        DefaultTableModel detailsModel = new DefaultTableModel(Messages.ORDER_DETAILS_TABLE_HEADERS,0);
        orderDetailsTable.setModel(detailsModel);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {
        DefaultTableModel menuModel = (DefaultTableModel) clientOrderTable.getModel();
        menuModel.setRowCount(0);
        DefaultTableModel orderModel = (DefaultTableModel) orderDetailsTable.getModel();
        orderModel.setRowCount(0);

    }

    public JButton getCreateDishReviewButton() {
        return createDishReviewButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getCreateNewOrderButton() {
        return createNewOrderButton;
    }

    public JButton getEditUserDataButton() {
        return editUserDataButton;
    }

    public JLabel getUserDataLabel() {
        return userDataLabel;
    }

    public JTable getClientOrderTable() {
        return clientOrderTable;
    }

    public JTable getOrderDetailsTable() {
        return orderDetailsTable;
    }

    public JLabel getDiscountInfoLabel() {
        return discountInfoLabel;
    }
}
