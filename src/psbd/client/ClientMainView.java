package psbd.client;

import psbd.utils.PanelEnum;
import psbd.utils.ViewTemplate;

import javax.swing.*;

public class ClientMainView extends ViewTemplate {
    private JPanel WindowPanel;
    private JButton createNewOrderButton;
    private JButton createDishReviewButton;
    private JButton logoutButton;
    private JButton editUserDataButton;
    private JLabel userDataLabel;
    private JTable clientOrderTable;

    public ClientMainView()
    {
        setWindowName(PanelEnum.CLIENTMAIN);
    }

    public JPanel getWindowPanel() {
        return WindowPanel;
    }

    @Override
    public void cleanAll() {

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
}
