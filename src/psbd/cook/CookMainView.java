package psbd.cook;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class CookMainView extends ViewTemplate {

    private JButton logoutButton;
    private JPanel WindowPanel;
    private JButton setOrderReadyButton;
    private JButton updateListButton;
    private JLabel userDataLabel;
    private JTable ordersTable;
    private JTable orderDetailsTable;
    private JTable dishRecipetable;

    public CookMainView()
    {
        setWindowName(PanelEnum.COOKMAIN);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

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

    public JTable getDishRecipetable() {
        return dishRecipetable;
    }

    public JTable getOrdersTable() {
        return ordersTable;
    }
}
