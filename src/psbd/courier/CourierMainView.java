package psbd.courier;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class CourierMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton logoutButton;
    private JButton updateListButton;
    private JLabel userDataLabel;
    private JTable ordersTable;

    public CourierMainView()
    {
        setWindowName(PanelEnum.COURIERMAIN);
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
